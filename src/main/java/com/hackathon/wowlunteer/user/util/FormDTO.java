package com.hackathon.wowlunteer.user.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormDTO {

    private String name;
    private String description;
    private String firstName;
    private String lastName;
    private String profession;
    private String age;
    private Boolean isLooking;
    private String eventType;
}
