package com.clinkast.qcm.services.dto;

import javax.validation.constraints.Size;

public class UserProfile {

    @Size(min=2, max=40)
    private String firstname;

    @Size(min=2, max=40)
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
