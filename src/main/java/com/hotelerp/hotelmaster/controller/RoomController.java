package com.hotelerp.hotelmaster.controller;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.constants.ServiceConstants;
import com.hotelerp.hotelmaster.dto.RoomRequest;
import com.hotelerp.hotelmaster.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ServiceConstants.ROOM_BASE_URL)
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    @PostMapping(ServiceConstants.CREATE_ROOM)
    public ResponseEntity<StandardResponse<?>> createRoom(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(service.createRoom(request));
    }

    @PutMapping(ServiceConstants.UPDATE_ROOM)
    public ResponseEntity<StandardResponse<?>> updateRoom(@PathVariable Long id,
            @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(service.updateRoom(id, request));
    }

    @GetMapping(ServiceConstants.GET_ROOM_BY_ID)
    public ResponseEntity<StandardResponse<?>> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRoomById(id));
    }

    @GetMapping(ServiceConstants.GET_ALL_ROOMS)
    public ResponseEntity<StandardResponse<?>> getAllRooms(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) Long statusId,
            @RequestParam(required = false) Long floorId,
            @RequestParam(required = false) Long roomTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllRooms(searchText, statusId, floorId, roomTypeId, page, size));
    }

    @DeleteMapping(ServiceConstants.DELETE_ROOM)
    public ResponseEntity<StandardResponse<?>> deleteRoom(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteRoom(id));
    }
}
