package com.hackathon.wowlunteer.event.service;

import com.hackathon.wowlunteer.event.persistence.model.Event;
import com.hackathon.wowlunteer.event.persistence.repository.EventRepository;
import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> findAllByUser(ApplicationUser applicationUser) {
        return eventRepository.findAllByUser(applicationUser);
    }

    public void save(Event event) {
        eventRepository.save(event);
    }
}
