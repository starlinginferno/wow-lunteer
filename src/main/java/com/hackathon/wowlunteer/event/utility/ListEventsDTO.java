package com.hackathon.wowlunteer.event.utility;

import com.hackathon.wowlunteer.event.persistence.model.Event;
import lombok.Data;

import java.util.List;

@Data
public class ListEventsDTO {
    private List<Event> events;
}
