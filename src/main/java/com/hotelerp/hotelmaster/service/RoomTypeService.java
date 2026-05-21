package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.RoomTypeRequest;

public interface RoomTypeService {
    StandardResponse<?> createRoomType(RoomTypeRequest request);
    StandardResponse<?> updateRoomType(Long id, RoomTypeRequest request);
    StandardResponse<?> getRoomTypeById(Long id);
    StandardResponse<?> getAllRoomTypes();
    StandardResponse<?> deleteRoomType(Long id);
}
