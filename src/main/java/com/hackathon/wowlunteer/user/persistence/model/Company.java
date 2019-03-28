package com.hackathon.wowlunteer.user.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Company extends ApplicationUser {

    private String name;
    private String description;
}
