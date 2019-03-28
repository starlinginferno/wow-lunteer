package com.hackathon.wowlunteer.user.util;

import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;

public class UserFactory {

    public static ApplicationUser createUser(UserType type) {
        return type.createUser();
    }
}
