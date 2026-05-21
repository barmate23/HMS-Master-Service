package com.hotelerp.hotelmaster.controller;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.constants.ServiceConstants;
import com.hotelerp.hotelmaster.dto.RoomTypeRequest;
import com.hotelerp.hotelmaster.service.RoomTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ServiceConstants.ROOM_TYPE_BASE_URL)
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService service;

    @PostMapping(ServiceConstants.CREATE_ROOM_TYPE)
    public ResponseEntity<StandardResponse<?>> createRoomType(@Valid @RequestBody RoomTypeRequest request) {
        return ResponseEntity.ok(service.createRoomType(request));
    }

    @PutMapping(ServiceConstants.UPDATE_ROOM_TYPE)
    public ResponseEntity<StandardResponse<?>> updateRoomType(@PathVariable Long id,
            @Valid @RequestBody RoomTypeRequest request) {
        return ResponseEntity.ok(service.updateRoomType(id, request));
    }

    @GetMapping(ServiceConstants.GET_ROOM_TYPE_BY_ID)
    public ResponseEntity<StandardResponse<?>> getRoomTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRoomTypeById(id));
    }

    @GetMapping(ServiceConstants.GET_ALL_ROOM_TYPES)
    public ResponseEntity<StandardResponse<?>> getAllRoomTypes() {
        return ResponseEntity.ok(service.getAllRoomTypes());
    }

    @DeleteMapping(ServiceConstants.DELETE_ROOM_TYPE)
    public ResponseEntity<StandardResponse<?>> deleteRoomType(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteRoomType(id));
    }
}
