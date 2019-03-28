package com.hackathon.wowlunteer.user.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolunteerDTO {

    private String email;
    private String firstname;
    private String lastname;
    private String mobileNumber;
    private String profession;
    private Integer age;
    private Boolean isLooking;
    private String eventType;

    public VolunteerDTO() {
    }

    public VolunteerDTO(String email, String firstname, String lastname, String mobileNumber, String profession, Integer age, Boolean isLooking, String eventType) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobileNumber = mobileNumber;
        this.profession = profession;
        this.age = age;
        this.isLooking = isLooking;
        this.eventType = eventType;
    }
}
