package com.hotelerp.hotelmaster.controller;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.constants.ServiceConstants;
import com.hotelerp.hotelmaster.dto.GstRuleRequest;
import com.hotelerp.hotelmaster.service.GstRuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ServiceConstants.GST_RULE_BASE_URL)
@RequiredArgsConstructor
public class GstRuleController {

    private final GstRuleService service;

    /** GET /api/masterService/v1/gstRules/categories
     *  Returns active GST service categories from CommonMaster */
    @GetMapping(ServiceConstants.GET_GST_CATEGORIES)
    public ResponseEntity<StandardResponse<?>> getGstCategories() {
        return ResponseEntity.ok(service.getGstCategories());
    }

    /** POST /api/masterService/v1/gstRules/createGstRule */
    @PostMapping(ServiceConstants.CREATE_GST_RULE)
    public ResponseEntity<StandardResponse<?>> createGstRule(@Valid @RequestBody GstRuleRequest request) {
        return ResponseEntity.ok(service.createGstRule(request));
    }

    /** PUT /api/masterService/v1/gstRules/updateGstRule/{id} */
    @PutMapping(ServiceConstants.UPDATE_GST_RULE)
    public ResponseEntity<StandardResponse<?>> updateGstRule(@PathVariable Long id,
            @Valid @RequestBody GstRuleRequest request) {
        return ResponseEntity.ok(service.updateGstRule(id, request));
    }

    /** GET /api/masterService/v1/gstRules/getGstRuleById/{id} */
    @GetMapping(ServiceConstants.GET_GST_RULE_BY_ID)
    public ResponseEntity<StandardResponse<?>> getGstRuleById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getGstRuleById(id));
    }

    /** GET /api/masterService/v1/gstRules/getAllGstRules?searchText=&page=0&size=10 */
    @GetMapping(ServiceConstants.GET_ALL_GST_RULES)
    public ResponseEntity<StandardResponse<?>> getAllGstRules(
            @RequestParam(required = false) String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(service.getAllGstRules(searchText, page, size));
    }

    /** DELETE /api/masterService/v1/gstRules/deleteGstRule/{id} */
    @DeleteMapping(ServiceConstants.DELETE_GST_RULE)
    public ResponseEntity<StandardResponse<?>> deleteGstRule(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteGstRule(id));
    }
}
