package com.hackathon.wowlunteer.user.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Volunteer extends ApplicationUser {

    private String firstName;
    private String lastName;
    private String profession;
    private String age;
    private Boolean isLooking;
}
