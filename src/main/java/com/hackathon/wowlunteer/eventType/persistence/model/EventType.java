package com.hackathon.wowlunteer.eventType.persistence.model;

import com.hackathon.wowlunteer.event.persistence.model.Event;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "event_types")
public class EventType {
    private String type;
    private String description;

    @OneToOne(mappedBy = "eventType", targetEntity = EventType.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Event event;

    public EventType() {
    }

    public EventType(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
