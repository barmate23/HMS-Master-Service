package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.FloorRequest;

public interface FloorService {
    StandardResponse<?> createFloor(FloorRequest request);
    StandardResponse<?> updateFloor(Long id, FloorRequest request);
    StandardResponse<?> getFloorById(Long id);
    StandardResponse<?> getAllFloors();
    StandardResponse<?> deleteFloor(Long id);
}
