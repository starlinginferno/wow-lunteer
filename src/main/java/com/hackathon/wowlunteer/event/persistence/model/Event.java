package com.hackathon.wowlunteer.event.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hackathon.wowlunteer.eventType.persistence.model.EventType;
import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "type_id")
    private EventType eventType;

    private String address;

    @ManyToMany(mappedBy = "events")
    private List<ApplicationUser> user = new ArrayList<>();
}
