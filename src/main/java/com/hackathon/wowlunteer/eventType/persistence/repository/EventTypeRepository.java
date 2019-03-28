package com.hackathon.wowlunteer.eventType.persistence.repository;

import com.hackathon.wowlunteer.eventType.persistence.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {
}
