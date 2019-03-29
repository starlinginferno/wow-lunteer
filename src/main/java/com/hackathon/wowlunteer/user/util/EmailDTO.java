package com.hackathon.wowlunteer.user.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDTO {
    String email;

    public EmailDTO(String email) {
        this.email = email;
    }
}
