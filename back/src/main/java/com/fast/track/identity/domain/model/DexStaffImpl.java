package com.irichment.identity.domain.model;

import com.irichment.enrollment.domain.model.DexChild;
import com.irichment.enrollment.domain.model.DexChildImpl;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * @author canang technologies
 */
@Entity(name = "DexStaff")
@Table(name = "DEX_STAF")
public class DexStaffImpl extends DexActorImpl implements DexStaff {

    public DexStaffImpl() {
        super();
        setActorType(DexActorType.STAFF);
    }

    public Class<?> getInterfaceClass() {
        return DexGuardian.class;
    }
}
