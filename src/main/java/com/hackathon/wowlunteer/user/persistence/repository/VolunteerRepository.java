package com.hackathon.wowlunteer.user.persistence.repository;

import com.hackathon.wowlunteer.user.persistence.model.Volunteer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface VolunteerRepository extends UserBaseRepository<Volunteer> {

    List<Volunteer> findAllByIsLooking(Boolean isLooking);
}
