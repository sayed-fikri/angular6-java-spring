package com.irichment.identity.domain.model;

import com.irichment.enrollment.domain.model.DexChild;
import com.irichment.enrollment.domain.model.DexChildImpl;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;


/**
 * @author canang technologies
 */
@Entity(name = "DexGuardian")
@Table(name = "DEX_GRDN")
public class DexGuardianImpl extends DexActorImpl implements DexGuardian {

    @NotNull
    @Column(name = "ONBOARDED", nullable = false)
    private Boolean onboarded = false;

    public DexGuardianImpl() {
        super();
        setActorType(DexActorType.GUARDIAN);
    }

    @OneToMany(targetEntity = DexChildImpl.class, mappedBy = "guardian", fetch = FetchType.LAZY)
    private List<DexChild> childs;

    public Boolean isOnboarded() {
        return onboarded;
    }

    @Override
    public void setOnboarded(Boolean onboarded) {
        this.onboarded = onboarded;
    }

    public List<DexChild> getChilds() {
        return childs;
    }

    public void setChilds(List<DexChild> childs) {
        this.childs = childs;
    }

    public Class<?> getInterfaceClass() {
        return DexGuardian.class;
    }


}
