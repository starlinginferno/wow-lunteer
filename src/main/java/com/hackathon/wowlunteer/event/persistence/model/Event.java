package com.hackathon.wowlunteer.event.persistence.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hackathon.wowlunteer.eventType.persistence.model.EventType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;
    @Temporal(TemporalType.TIMESTAMP)
    private Date finish;

    private String address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_type_id")
    private EventType eventType;
}
