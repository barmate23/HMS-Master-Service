package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.RatePlanRequest;

public interface RatePlanService {
    StandardResponse<?> createRatePlan(RatePlanRequest request);
    StandardResponse<?> updateRatePlan(Long id, RatePlanRequest request);
    StandardResponse<?> getRatePlanById(Long id);
    StandardResponse<?> getAllRatePlans(String searchText, int page, int size);
    StandardResponse<?> deleteRatePlan(Long id);
}
