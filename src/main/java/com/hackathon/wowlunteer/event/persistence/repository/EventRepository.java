package com.hackathon.wowlunteer.event.persistence.repository;

import com.hackathon.wowlunteer.event.persistence.model.Event;
import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByUser(ApplicationUser applicationUser);
}
