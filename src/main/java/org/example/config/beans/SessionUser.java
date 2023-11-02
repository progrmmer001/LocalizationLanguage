package org.example.config.beans;

import org.example.UserServiceBean;
import org.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionUser {
    public UserServiceBean userService() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserServiceBean principal = null;
        if (authentication.getPrincipal() instanceof UserServiceBean) {
            principal = (UserServiceBean) authentication.getPrincipal();
        }
        return principal;
    }
}
