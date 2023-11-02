package org.example.service;

import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repository;

    public List<Role> roles(Long user_id) throws Exception {
        List<Role> role = repository.getRole(user_id);
        if (role.isEmpty()) throw new NullPointerException("No found roles !");
        else return role;
    }
}
