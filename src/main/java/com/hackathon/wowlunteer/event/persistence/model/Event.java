package com.hackathon.wowlunteer.event.persistence.model;

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

    private String address;

//    @OneToOne(mappedBy = "event", targetEntity = EventType.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private EventType eventType;

    @ManyToMany(mappedBy = "events")
    private List<ApplicationUser> user = new ArrayList<>();
}
