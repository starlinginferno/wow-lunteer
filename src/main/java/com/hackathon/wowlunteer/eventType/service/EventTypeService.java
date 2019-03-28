package com.hackathon.wowlunteer.eventType.service;

import com.hackathon.wowlunteer.eventType.persistence.model.EventType;
import com.hackathon.wowlunteer.eventType.persistence.repository.EventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeService {

    private EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeService(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    public void save(EventType eventType) {
        eventTypeRepository.save(eventType);
    }

    public List<EventType> findAll() {
        return eventTypeRepository.findAll();
    }
}
