package com.billcom.authentication.config;

import com.lhs.ccb.cfw.cda.session.ConnectionFailedException;
import com.lhs.ccb.cfw.cda.utility.GlobalUserProperties;
import com.lhs.ccb.cfw.cda.utility.UserPropertiesFacade;
import com.lhs.ccb.cfw.wcs.security.JaasAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class ConnectionPoolsAuthenticationProvider implements AuthenticationProvider {
    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JaasAuthenticationProvider localJaasAuthProvider = new JaasAuthenticationProvider();
        try {
            String password = (String) authentication.getCredentials();
            localJaasAuthProvider.authenticateUser(authentication.getName(),
                    password, new Object[]{null});
            UserPropertiesFacade.instance().setUserAttribute("Username", authentication.getName());
            UserPropertiesFacade.instance().setUserAttribute("Password", password);
            return new UsernamePasswordAuthenticationToken(authentication.getName(),
                    authentication.getCredentials(), AUTHORITIES);
        } catch (ConnectionFailedException e) {
            throw new BadCredentialsException("Invalid Username or Password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
