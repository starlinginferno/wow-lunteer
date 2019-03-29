package com.hackathon.wowlunteer.event.service;

import com.hackathon.wowlunteer.event.persistence.model.Event;
import com.hackathon.wowlunteer.event.persistence.repository.EventRepository;
import com.hackathon.wowlunteer.event.utility.EventDTO;
import com.hackathon.wowlunteer.eventType.persistence.model.EventType;
import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Event> findAllByType(String type) {
        return eventRepository.findAll().stream().filter(e -> e.getEventType().getType().toUpperCase().equals(type.toUpperCase())).collect(Collectors.toList());
    }

    public EventDTO findById(Long id) throws IllegalArgumentException {
        ModelMapper modelMapper = new ModelMapper();
        Event event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Event not found: " + id));
        return modelMapper.map(event, EventDTO.class);
    }

    public List<EventDTO> mapEventDTOList(List<Event> eventList) {
        return eventList
                .stream()
                .map(e -> new EventDTO(e.getEventType().getType(), e.getTitle(), e.getDescription(),
                        e.getAddress(), e.getStart(), e.getFinish()))
                .collect(Collectors.toList());
    }

    public void save(Event event) {
        eventRepository.save(event);
    }

    public List<String> applyToEvent(Long id, ApplicationUser applicationUser) {
        List<String> list = new ArrayList<>();
        Event event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Event not found: " + id));
        event.getUsers().add(applicationUser);
        applicationUser.getEvents().add(event);
        list.add(applicationUser.getEmail());
        list.add(event.getTitle());
        save(event);
        return list;
    }
}
