package com.hackathon.wowlunteer.user.util;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class FormDTO {

    private String name;
    private String description;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "^[+][0-9]*$")
    private String mobileNumber;
    private String profession;
    private Integer age;
    private Boolean isLooking;
    private String eventType;
}
