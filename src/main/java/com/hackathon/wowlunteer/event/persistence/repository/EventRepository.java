package com.hackathon.wowlunteer.event.persistence.repository;

import com.hackathon.wowlunteer.event.persistence.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
