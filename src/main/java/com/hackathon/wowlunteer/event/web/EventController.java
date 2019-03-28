package com.hackathon.wowlunteer.event.web;

import com.hackathon.wowlunteer.event.persistence.model.Event;
import com.hackathon.wowlunteer.event.service.EventService;
import com.hackathon.wowlunteer.event.utility.CreateEventDTO;
import com.hackathon.wowlunteer.event.utility.ListEventsDTO;
import com.hackathon.wowlunteer.eventType.exception.EventTypeNotFoundException;
import com.hackathon.wowlunteer.eventType.persistence.model.EventType;
import com.hackathon.wowlunteer.eventType.service.EventTypeService;
import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import com.hackathon.wowlunteer.user.service.ApplicationUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private EventService eventService;
    private EventTypeService eventTypeService;
    private ApplicationUserService applicationUserService;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public EventController(EventService eventService, EventTypeService eventTypeService, ApplicationUserService applicationUserService) {
        this.eventService = eventService;
        this.eventTypeService = eventTypeService;
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ListEventsDTO getAll() {
        ListEventsDTO listEventsDTO = new ListEventsDTO();
        listEventsDTO.setEvents(eventService.findAll());
        return listEventsDTO;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ListEventsDTO getUsersEvents(Principal principal) {
        ListEventsDTO listEventsDTO = new ListEventsDTO();
        ApplicationUser applicationUser = applicationUserService.findByPrincipal(principal);
        listEventsDTO.setEvents(applicationUser.getEvents());
        return listEventsDTO;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void createEvent(@RequestBody @Valid CreateEventDTO createEventDTO, Principal principal)
            throws EventTypeNotFoundException {
        ApplicationUser applicationUser = applicationUserService.findByPrincipal(principal);
        Event event = modelMapper.map(createEventDTO, Event.class);
        EventType eventType = eventTypeService.findById(createEventDTO.getTypeId());
        eventType.getEventList().add(event);
        event.setEventType(eventType);
        applicationUser.getEvents().add(event);
        event.getUsers().add(applicationUser);
        eventService.save(event);
    }
}
