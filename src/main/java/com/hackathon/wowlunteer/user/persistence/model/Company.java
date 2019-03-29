package com.hackathon.wowlunteer.user.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Company extends ApplicationUser {

    private String name;
    private String description;
}
