package com.hackathon.wowlunteer.eventType.persistence.model;

import com.hackathon.wowlunteer.event.persistence.model.Event;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "event_types")
public class EventType {
    private String type;

    @OneToOne(mappedBy = "eventType", targetEntity = EventType.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Event event;
}
