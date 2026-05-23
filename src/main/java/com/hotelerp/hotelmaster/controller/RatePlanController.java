package com.hotelerp.hotelmaster.controller;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.constants.ServiceConstants;
import com.hotelerp.hotelmaster.dto.RatePlanRequest;
import com.hotelerp.hotelmaster.service.RatePlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ServiceConstants.RATE_PLAN_BASE_URL)
@RequiredArgsConstructor
public class RatePlanController {

    private final RatePlanService service;

    @PostMapping(ServiceConstants.CREATE_RATE_PLAN)
    public ResponseEntity<StandardResponse<?>> createRatePlan(@Valid @RequestBody RatePlanRequest request) {
        return ResponseEntity.ok(service.createRatePlan(request));
    }

    @PutMapping(ServiceConstants.UPDATE_RATE_PLAN)
    public ResponseEntity<StandardResponse<?>> updateRatePlan(@PathVariable Long id,
            @Valid @RequestBody RatePlanRequest request) {
        return ResponseEntity.ok(service.updateRatePlan(id, request));
    }

    @GetMapping(ServiceConstants.GET_RATE_PLAN_BY_ID)
    public ResponseEntity<StandardResponse<?>> getRatePlanById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRatePlanById(id));
    }

    @GetMapping(ServiceConstants.GET_ALL_RATE_PLANS)
    public ResponseEntity<StandardResponse<?>> getAllRatePlans(
            @RequestParam(required = false) String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllRatePlans(searchText, page, size));
    }

    @DeleteMapping(ServiceConstants.DELETE_RATE_PLAN)
    public ResponseEntity<StandardResponse<?>> deleteRatePlan(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteRatePlan(id));
    }
}
