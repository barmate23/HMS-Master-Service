package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.GstRuleRequest;

public interface GstRuleService {

    StandardResponse<?> createGstRule(GstRuleRequest request);

    StandardResponse<?> updateGstRule(Long id, GstRuleRequest request);

    StandardResponse<?> getGstRuleById(Long id);

    StandardResponse<?> getAllGstRules(String searchText, int page, int size);

    StandardResponse<?> deleteGstRule(Long id);

    /** Returns active GST service categories from CommonMaster */
    StandardResponse<?> getGstCategories();
}
