package com.irichment.identity.domain.model;


import javax.persistence.*;


/**
 * @author canang technologies
 */
@Entity(name = "DexInstructor")
@Table(name = "DEX_INST")
public class DexInstructorImpl extends DexActorImpl implements  DexInstructor{

    public DexInstructorImpl() {
        super();
        setActorType(DexActorType.INSTRUCTOR);
    }

    public Class<?> getInterfaceClass() {
        return DexInstructor.class;
    }


}
