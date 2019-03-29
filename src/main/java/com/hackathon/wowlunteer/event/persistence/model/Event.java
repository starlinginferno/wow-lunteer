package com.hackathon.wowlunteer.event.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(length=5000)
    private String description;
    private String address;
    @Temporal(TemporalType.TIMESTAMP)
    private Date start; // yyyy-MM-dd HH:mm:ss
    @Temporal(TemporalType.TIMESTAMP)
    private Date finish; // yyyy-MM-dd HH:mm:ss

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "type_id")
    private EventType eventType;

    @JsonIgnore
    @ManyToMany(mappedBy = "events", cascade = CascadeType.ALL)
    private List<ApplicationUser> users = new ArrayList<>();

}
