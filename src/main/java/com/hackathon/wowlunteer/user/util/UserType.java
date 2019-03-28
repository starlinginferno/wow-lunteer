package com.hackathon.wowlunteer.user.util;

import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import com.hackathon.wowlunteer.user.persistence.model.Company;
import com.hackathon.wowlunteer.user.persistence.model.Volunteer;

public enum UserType {

    COMPANY {
        @Override
        public ApplicationUser createUser() {
            return new Company();
        }
    },
    VOLUNTEER{
        @Override
        public ApplicationUser createUser() {
            return new Volunteer();
        }
    };

    public abstract ApplicationUser createUser();

    public String getName() {
        return this.name();
    }
}
