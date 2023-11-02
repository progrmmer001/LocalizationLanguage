package org.example.service;

import org.example.model.Permission;
import org.example.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> permissions(Long role_id) {
        List<Permission> permissions = permissionRepository.getPermissions(role_id);
        if (permissions.isEmpty()) throw new NullPointerException("No found permissions yet!");
        else return permissions;
    }
}
