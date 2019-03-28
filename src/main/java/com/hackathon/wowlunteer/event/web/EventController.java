package com.hackathon.wowlunteer.event.web;

import com.hackathon.wowlunteer.event.persistence.model.Event;
import com.hackathon.wowlunteer.event.service.EventService;
import com.hackathon.wowlunteer.event.utility.CreateEventDTO;
import com.hackathon.wowlunteer.event.utility.ListEventsDTO;
import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import com.hackathon.wowlunteer.user.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private EventService eventService;
    private ApplicationUserService applicationUserService;

    @Autowired
    public EventController(EventService eventService, ApplicationUserService applicationUserService) {
        this.eventService = eventService;
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
        listEventsDTO.setEvents(eventService.findAllByUser(applicationUser));
        return listEventsDTO;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void createEvent(@RequestBody CreateEventDTO createEventDTO, Principal principal) {
        ApplicationUser applicationUser = applicationUserService.findByPrincipal(principal);
        Event event = new Event();
        event.setTitle(createEventDTO.getTitle());
        event.setDescription(createEventDTO.getDescription());
        applicationUser.getEvents().add(event);
        event.getUser().add(applicationUser);
        eventService.save(event);
    }
}
