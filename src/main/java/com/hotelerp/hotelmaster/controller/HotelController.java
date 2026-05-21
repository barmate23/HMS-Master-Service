package com.hotelerp.hotelmaster.controller;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.constants.ServiceConstants;
import com.hotelerp.hotelmaster.dto.HotelRequest;
import com.hotelerp.hotelmaster.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ServiceConstants.HOTEL_BASE_URL)
@RequiredArgsConstructor
public class HotelController {

    private final HotelService service;

    @PostMapping(ServiceConstants.CREATE_HOTEL)
    public ResponseEntity<StandardResponse<?>> createHotel(@Valid @RequestBody HotelRequest request) {
        return ResponseEntity.ok(service.createHotel(request));
    }

    @PutMapping(ServiceConstants.UPDATE_HOTEL)
    public ResponseEntity<StandardResponse<?>> updateHotel(@PathVariable Long id,
            @Valid @RequestBody HotelRequest request) {
        return ResponseEntity.ok(service.updateHotel(id, request));
    }

    @GetMapping(ServiceConstants.GET_HOTEL_BY_ID)
    public ResponseEntity<StandardResponse<?>> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getHotelById(id));
    }

    @GetMapping(ServiceConstants.GET_ALL_HOTELS)
    public ResponseEntity<StandardResponse<?>> getAllHotels() {
        return ResponseEntity.ok(service.getAllHotels());
    }

    @DeleteMapping(ServiceConstants.DELETE_HOTEL)
    public ResponseEntity<StandardResponse<?>> deleteHotel(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteHotel(id));
    }
}
