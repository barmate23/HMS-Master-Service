package com.hotelerp.hotelmaster.controller;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.constants.ServiceConstants;
import com.hotelerp.hotelmaster.dto.FloorRequest;
import com.hotelerp.hotelmaster.service.FloorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ServiceConstants.FLOOR_BASE_URL)
@RequiredArgsConstructor
public class FloorController {

    private final FloorService service;

    @PostMapping(ServiceConstants.CREATE_FLOOR)
    public ResponseEntity<StandardResponse<?>> createFloor(@Valid @RequestBody FloorRequest request) {
        return ResponseEntity.ok(service.createFloor(request));
    }

    @PutMapping(ServiceConstants.UPDATE_FLOOR)
    public ResponseEntity<StandardResponse<?>> updateFloor(@PathVariable Long id,
            @Valid @RequestBody FloorRequest request) {
        return ResponseEntity.ok(service.updateFloor(id, request));
    }

    @GetMapping(ServiceConstants.GET_FLOOR_BY_ID)
    public ResponseEntity<StandardResponse<?>> getFloorById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getFloorById(id));
    }

    @GetMapping(ServiceConstants.GET_ALL_FLOORS)
    public ResponseEntity<StandardResponse<?>> getAllFloors(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) Long hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllFloors(searchText, hotelId, page, size));
    }

    @DeleteMapping(ServiceConstants.DELETE_FLOOR)
    public ResponseEntity<StandardResponse<?>> deleteFloor(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteFloor(id));
    }
}
