package org.example;

import lombok.Getter;
import org.example.model.AuthUser;
import org.example.model.Permission;
import org.example.model.Role;
import org.example.service.PermissionService;
import org.example.service.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserServiceBean implements UserDetails {
    private final RoleService roleService;
    private final PermissionService permissionService;
    @Getter
    private final AuthUser authUser;

    public UserServiceBean(RoleService roleService, PermissionService permissionService, AuthUser authUser) {
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.authUser = authUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        try {
            List<Role> roles = roleService.roles(authUser.getId());
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
                List<Permission> permissions = permissionService.permissions(role.getId());
                for (Permission permission : permissions) {
                    authorities.add(new SimpleGrantedAuthority(permission.getPermissionName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !authUser.getIsBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
