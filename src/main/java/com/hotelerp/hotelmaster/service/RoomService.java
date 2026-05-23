package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.RoomRequest;
import com.hotelerp.hotelmaster.entity.Room;

public interface RoomService {
    StandardResponse<?> createRoom(RoomRequest request);
    StandardResponse<?> updateRoom(Long id, RoomRequest request);
    StandardResponse<?> getRoomById(Long id);
    StandardResponse<?> getAllRooms(String searchText, Room.RoomStatus status, Long floorId, Long roomTypeId, int page, int size);
    StandardResponse<?> deleteRoom(Long id);
}
