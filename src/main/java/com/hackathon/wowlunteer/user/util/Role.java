package com.hackathon.wowlunteer.user.util;

public enum Role {
    COMPANY, VOLUNTEER;

    public String authority() {
        return "ROLE_" + this.name();
    }

    public String getName() {
        return this.name();
    }
}
