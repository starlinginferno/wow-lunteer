package com.hackathon.wowlunteer.user.persistence.repository;

import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import com.hackathon.wowlunteer.user.persistence.model.ConfirmationToken;
import com.hackathon.wowlunteer.user.persistence.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserBaseRepository<T extends ApplicationUser> extends JpaRepository<T, Long> {

    Optional<T> findByEmail(String email);

    T findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);

    void deleteByConfirmationToken(ConfirmationToken confirmationToken);

//    List<Volunteer> findAllByProfession(String profession);

}
