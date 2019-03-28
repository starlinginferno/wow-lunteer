package com.hackathon.wowlunteer.user.persistence.repository;

import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import com.hackathon.wowlunteer.user.persistence.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByEmail(String email);

    ApplicationUser findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);

    void deleteByConfirmationToken(ConfirmationToken confirmationToken);

}
