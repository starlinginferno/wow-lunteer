package com.hackathon.wowlunteer.user.persistence.model;

import com.hackathon.wowlunteer.eventType.persistence.model.EventType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Volunteer extends ApplicationUser {

    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String profession = "";
    private Integer age;
    private Boolean isLooking = false;
}
