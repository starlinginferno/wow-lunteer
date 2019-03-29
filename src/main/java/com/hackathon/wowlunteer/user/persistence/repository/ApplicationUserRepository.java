package com.hackathon.wowlunteer.user.persistence.repository;

import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import com.hackathon.wowlunteer.user.persistence.model.ConfirmationToken;
import com.hackathon.wowlunteer.user.persistence.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ApplicationUserRepository extends UserBaseRepository<ApplicationUser> {
//    Optional<ApplicationUser> findByEmail(String email);
//
//    ApplicationUser findByEmailIgnoreCase(String email);
//
//    Boolean existsByEmail(String email);
//
//    void deleteByConfirmationToken(ConfirmationToken confirmationToken);

}
