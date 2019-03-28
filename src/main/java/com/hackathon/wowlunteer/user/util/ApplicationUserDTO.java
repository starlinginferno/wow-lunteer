package com.hackathon.wowlunteer.user.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationUserDTO {

    Long id;

    @NotBlank
    String password;
    @NotBlank
    String email;

    List<Role> roles;

}