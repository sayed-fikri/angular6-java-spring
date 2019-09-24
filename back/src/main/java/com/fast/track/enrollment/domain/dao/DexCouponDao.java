package com.irichment.enrollment.domain.dao;


import com.irichment.core.domain.GenericDao;
import com.irichment.enrollment.domain.model.DexCoupon;

import java.util.List;

public interface DexCouponDao extends GenericDao<Long, DexCoupon> {

    // ====================================================================================================
    // FINDER
    // ====================================================================================================

    DexCoupon findByCode(String code);

    DexCoupon findAvailableCouponByCode(String code);

    List<DexCoupon> findAllCoupons();

    List<DexCoupon> findCoupons(String filter, Integer offset, Integer limit);

    // ====================================================================================================
    // HELPER
    // ====================================================================================================

    Integer count(String filter);

}
