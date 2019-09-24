package com.irichment.identity.api.vo;
public class Therapist extends Actor {

    private String hospital;
    private String profession;

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
