package com.billcom.authentication.config;

import com.lhs.ccb.cfw.cda.utility.GlobalUserProperties;
import com.lhs.ccb.cfw.cda.utility.UserPropertiesFacade;
import com.lhs.ccb.cfw.wcs.security.JaasAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class SampleAuthManager implements AuthenticationManager {

    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JaasAuthenticationProvider localJaasAuthenticationProvider = new JaasAuthenticationProvider();

        try {
            String password = (String) authentication.getCredentials();
            localJaasAuthenticationProvider.authenticateUser(authentication.getName(),
                    password, new Object[]{null});
            GlobalUserProperties localGlobalUserProperties = new GlobalUserProperties();
            UserPropertiesFacade.instance().setProperties(localGlobalUserProperties);
            UserPropertiesFacade.instance().setUserAttribute("AuthenticationProvider", localJaasAuthenticationProvider);
            return new UsernamePasswordAuthenticationToken(authentication.getName(),
                    authentication.getCredentials(), AUTHORITIES);
        } catch (Exception e) {
            throw new BadCredentialsException("Bad Credentials");
        }
    }


}
