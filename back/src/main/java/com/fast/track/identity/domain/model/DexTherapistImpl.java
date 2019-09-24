package com.irichment.identity.domain.model;

import com.irichment.enrollment.domain.model.DexConsultation;
import com.irichment.enrollment.domain.model.DexConsultationImpl;

import java.util.List;

import javax.persistence.*;


/**
 * @author canang technologies
 */
@Entity(name = "DexTherapist")
@Table(name = "DEX_TRPT")
public class DexTherapistImpl extends DexActorImpl implements DexTherapist {


    @OneToMany(targetEntity = DexConsultationImpl.class, mappedBy = "therapist", fetch = FetchType.EAGER)
    private List<DexConsultation> consultations;


    @Column(name = "HOSPITAL")
    private String hospital;

    @Column(name = "PROFESSION")
    private String profession;

    public List<DexConsultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<DexConsultation> consultations) {
        this.consultations = consultations;
    }

    public DexTherapistImpl() {
        super();
        setActorType(DexActorType.THERAPIST);
    }

    public Class<?> getInterfaceClass() {
        return DexTherapist.class;
    }



    @Override
    public String getHospital() {
        return hospital;
    }

    @Override
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    @Override
    public String getProfession() {
        return profession;
    }

    @Override
    public void setProfession(String profession) {
        this.profession = profession;
    }
}

