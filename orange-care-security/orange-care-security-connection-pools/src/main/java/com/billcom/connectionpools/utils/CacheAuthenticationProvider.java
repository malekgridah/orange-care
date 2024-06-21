package com.billcom.connectionpools.utils;


import com.billcom.connectionpools.config.properties.ServerConnectionSettings;
import com.lhs.ccb.cfw.cda.session.ConnectionFailedException;
import com.lhs.ccb.cfw.cda.session.ServerFacade;
import com.lhs.ccb.cfw.cda.utility.GlobalUserProperties;
import com.lhs.ccb.cfw.cda.utility.UserPropertiesFacade;
import com.lhs.ccb.cfw.wcs.security.BSCSUserPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.*;


public class CacheAuthenticationProvider implements LoginModule {
    private static Logger logger = LogManager.getLogger(CacheAuthenticationProvider.class);
    private static Map<String, DatedPassword> cache = new HashMap();
    private CallbackHandler _callbackHandler;
    private Subject _subject;
    private String _username;
    private String _password;


    public CacheAuthenticationProvider() {
    }

    private static boolean authenticate(String user, String pass, String sessionId) throws FailedLoginException {
        boolean result = false;
        Calendar now = Calendar.getInstance();
        DatedPassword dp;
        if (cache.containsKey(user)) {
            logger.debug("user <" + user + "> found in cache");

            dp = cache.get(user);
                Date date = dp.expirationDate.getTime();
                logger.debug("expiration date: " + date);

            if (now.compareTo(dp.expirationDate) <= 0) {
                logger.debug("cache hasn't expired yet ");
                if (pass.equals(dp.password)) {
                    logger.debug("password provided and password in cache match.");
                    result = true;
                }
            }
        }

        if (!result) {
            logger.info("Trying to authenticate directly from the SOI interface");

            try {
                ServerConnectionSettings config = SpringContext.getBean(ServerConnectionSettings.class);

                ServerFacade.instance().login(user, pass, sessionId);
                result = true;
                now.add(Calendar.HOUR, config.getApplication().getCacheValidityHours());
                dp = new DatedPassword(pass, now);
                cache.put(user, dp);
                logger.info("Autentication successful, password stored (valid for " + config.getApplication().getCacheValidityHours() + " hour(s))");
            } catch (ConnectionFailedException var7) {
                throw new FailedLoginException(var7.getLocalizedMessage());
            }

            ServerFacade.instance().logout(sessionId);
        }

        if (!result) {
            logger.error("Authentication has failed");
            throw new FailedLoginException("UserPasswordMismatched");
        } else {
            return result;
        }
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this._subject = subject;
        this._callbackHandler = callbackHandler;
        logger.info("initialize()");
    }

    public boolean login() throws LoginException {
        boolean result = true;
        logger.info("login()");
        String sessionId = null;
        Callback[] arrayOfCallback = new Callback[]{new NameCallback("Username"), new PasswordCallback("Password", false), new TextInputCallback("SessionId")};

        try {
            this._callbackHandler.handle(arrayOfCallback);
            this._username = ((NameCallback)arrayOfCallback[0]).getName();
            logger.info("username: " + this._username);
            char[] arrayOfChar = ((PasswordCallback)arrayOfCallback[1]).getPassword();
            if (arrayOfChar == null) {
                arrayOfChar = new char[0];
            }

            this._password = new String(arrayOfChar);
            ((PasswordCallback)arrayOfCallback[1]).clearPassword();
            sessionId = ((TextInputCallback)arrayOfCallback[2]).getText();
        } catch (IOException var5) {
            logger.error("SecuritySoi:User authentication has failed due to CallbackHandler I/O exception.");
            throw new FailedLoginException("SecuritySoi:User authentication has failed due to CallbackHandler I/O exception.");
        } catch (UnsupportedCallbackException var6) {
            logger.error("SecuritySoi:User authentication has failed because of unsupported callback.");
            throw new FailedLoginException("SecuritySoi:User authentication has failed because of unsupported callback.");
        }

        authenticate(this._username, this._password, sessionId);
        GlobalUserProperties localGlobalUserProperties = new GlobalUserProperties();
        localGlobalUserProperties.setUserLocale(Locale.FRENCH);
        UserPropertiesFacade.instance().setProperties(localGlobalUserProperties);
        UserPropertiesFacade.instance().setUserLocale(Locale.FRENCH);
        UserPropertiesFacade.instance().setUserName(this._username);
        return result;
    }

    public boolean commit() {
        logger.info("commit()");
        boolean result = true;
        BSCSUserPrincipal localBSCSUserPrincipal = new BSCSUserPrincipal(this._username);
        this._subject.getPrincipals().add(localBSCSUserPrincipal);
        this._subject.getPrivateCredentials().add(this._password);
        return result;
    }

    public boolean abort() {
        boolean result = true;
        logger.info("abort()");
        return result;
    }

    public boolean logout() {
        boolean result = true;
        logger.info("logout()");
        return result;
    }

    private static class DatedPassword {
        public String password;
        public Calendar expirationDate;

        public DatedPassword(String password, Calendar expirationDate) {
            this.password = password;
            this.expirationDate = expirationDate;
        }
    }
}
