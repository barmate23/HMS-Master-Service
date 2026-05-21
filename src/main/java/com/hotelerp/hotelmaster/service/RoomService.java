package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.RoomRequest;

public interface RoomService {
    StandardResponse<?> createRoom(RoomRequest request);
    StandardResponse<?> updateRoom(Long id, RoomRequest request);
    StandardResponse<?> getRoomById(Long id);
    StandardResponse<?> getAllRooms();
    StandardResponse<?> deleteRoom(Long id);
}
