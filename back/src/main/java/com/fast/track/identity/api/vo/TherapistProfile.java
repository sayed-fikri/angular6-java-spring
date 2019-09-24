package com.irichment.identity.api.vo;

public class TherapistProfile{

    private Therapist therapist;
    private User user;

    public Therapist getTherapist() {
        return therapist;
    }

    public void setTherapist(Therapist therapist) {
        this.therapist = therapist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
