package com.billcom.authentication.services;

import com.billcom.authentication.beans.RoleBeanOut;
import com.billcom.authentication.beans.UserReadBeanIn;
import com.billcom.authentication.beans.UserReadBeanOut;
import com.lhs.ccb.cfw.cda.session.ConnectionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OidcUserInfoService {

    private final CMSConnection cmsConnection;

    @Autowired
    public OidcUserInfoService(CMSConnection cmsConnection) {
        this.cmsConnection = cmsConnection;
    }

    public OidcUserInfo loadUser(String username) {
        UserReadBeanIn userReadBeanIn = new UserReadBeanIn();
        userReadBeanIn.setName(username);
        UserReadBeanOut userReadBeanOut = new UserReadBeanOut();
        try {
            userReadBeanOut = (UserReadBeanOut) this.cmsConnection.loginAndExecuteCommand("USER.READ", userReadBeanIn, UserReadBeanOut.class);

        } catch (ConnectionFailedException e) {
            throw new RuntimeException(e);
        }

        final Map<String, Object>userInfo = new HashMap<>();
        List<Object> roles = Arrays.stream(userReadBeanOut.getUsers()[0]
                .getRoles())
                .map(RoleBeanOut::getRole)
                .collect(Collectors.toList());


        Map<String, Object> user = OidcUserInfo.builder().name(userReadBeanOut.getUsers()[0].getName())
                .locale(userReadBeanOut.getUsers()[0].getLngShdes())
                .givenName(userReadBeanOut.getUsers()[0].getFirstName())
                .claim("roles", roles)
                .name(userReadBeanOut.getUsers()[0].getDescription())
                .nickname(userReadBeanOut.getUsers()[0].getName())
                .locale(userReadBeanOut.getUsers()[0].getLngShdes())
                .locale(userReadBeanOut.getUsers()[0].getLngShdes())
                .familyName(userReadBeanOut.getUsers()[0].getLastName()).build().getClaims();
        userInfo.put("name", user);

        return new OidcUserInfo(userInfo);
    }

}
