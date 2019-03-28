package com.hackathon.wowlunteer.user.service;

import com.hackathon.wowlunteer.user.exceptions.UserRoleNotFoundException;
import com.hackathon.wowlunteer.user.persistence.model.ApplicationUserRole;
import com.hackathon.wowlunteer.user.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public ApplicationUserRole findById(Long id) throws UserRoleNotFoundException {
        return roleRepository.findById(id).orElseThrow(() -> new UserRoleNotFoundException("User role found by this id: " + id));
    }

    public void saveRole(ApplicationUserRole applicationUserRole) {
        roleRepository.save(applicationUserRole);
    }
}
