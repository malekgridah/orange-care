package com.billcom.authentication.services;

import com.billcom.authentication.utils.CMSException;
import com.billcom.connectionpools.pools.ServerConnectionPoolInitializer;
import com.lhs.ccb.cfw.cda.servicelayer.CommandExecutionException;
import com.lhs.ccb.cfw.cda.servicelayer.SecurityRuntimeException;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.ConnectionPoolUser;
import com.lhs.ccb.cfw.cda.session.*;
import com.lhs.ccb.cfw.cda.utility.UserPropertiesFacade;
import com.lhs.ccb.cfw.wcs.security.User;
import com.lhs.ccb.common.soi.SVLObject;
import com.lhs.ws.beans.BaseSOIBean;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


import java.util.Locale;

import static com.billcom.authentication.utils.SOIUtils.*;

@Log4j2
@Service
public class CMSConnection {

    public BaseSOIBean loginAndExecuteCommand(String command, BaseSOIBean baseSOIBeanIn, Class<?> baseSOIBeanOut) throws ConnectionFailedException {
        ModelContainer container = null;
        User user = new User();
        user.setUserName("ADMX");
        user.setBSCSPassword("ADMX");
        BaseSOIBean soiBeanResult;
        try {
            DataExchangeException ex;
            try {
                ContainerFactory contFactory;
                if (DomainlayerFacade.instance().isInitialized()) {
                    log.info("Domain layer initialized");
                    contFactory = DomainlayerFacade.instance().login(user.getUserName(), user.getBSCSPassword());
                } else {
                    log.info("Domain layer is not initialized");
                    contFactory = ServerFacade.instance().login(user.getUserName(), user.getBSCSPassword(), null);
                }
                Locale locale = UserPropertiesFacade.instance().getUserLocale();
                container = contFactory.getReferenceConnection();

                SVLObject result = container.execute(command, locale, getSecuritySOIName(), getSecuritySOIVersion(), convertToSVLObject(baseSOIBeanIn));
                log.info(result);
                soiBeanResult = convertToSOIBean(result, baseSOIBeanOut);
            } catch (ConnectionFailedException var15) {
                log.info("Connection failed", var15);
                throw new ConnectionFailedException(var15.getLocalizedMessage());
            } catch (SecurityRuntimeException var16) {
                log.info("Command execution failed", var16);
                throw new ConnectionFailedException(var16.getLocalizedMessage());
            } catch (CommandExecutionException var17) {
                log.info("Command execution failed", var17);
                throw new ConnectionFailedException(var17.getLocalizedMessage());
            } catch (DataExchangeException var18) {
                ex = var18;
                log.info("Command execution failed", ex);
                throw new ConnectionFailedException(ex.getLocalizedMessage());
            } catch (CMSException e) {
                throw new RuntimeException(e);
            }
        } finally {
            if (ServerConnectionPoolInitializer.isInitialized() && container != null) {
                ((ConnectionPoolUser)container).releaseConnection();
            }
        }

        return soiBeanResult;
    }
}
