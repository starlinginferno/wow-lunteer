package com.hackathon.wowlunteer.user.persistence.repository;

import com.hackathon.wowlunteer.user.persistence.model.ApplicationUserRole;
import com.hackathon.wowlunteer.user.util.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<ApplicationUserRole, Long> {

    Optional<ApplicationUserRole> findById(Long id);

    Optional<ApplicationUserRole> findByRoleEnum(Role role);
}
