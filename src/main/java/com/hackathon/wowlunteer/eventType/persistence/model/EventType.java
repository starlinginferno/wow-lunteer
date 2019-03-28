package com.hackathon.wowlunteer.eventType.persistence.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hackathon.wowlunteer.event.persistence.model.Event;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "event_types")
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    @Column(length=5000)
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "eventType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> eventList;

    public EventType() {
    }

    public EventType(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
