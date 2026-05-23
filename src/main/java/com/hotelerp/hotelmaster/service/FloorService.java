package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.FloorRequest;

public interface FloorService {
    StandardResponse<?> createFloor(FloorRequest request);
    StandardResponse<?> updateFloor(Long id, FloorRequest request);
    StandardResponse<?> getFloorById(Long id);
    StandardResponse<?> getAllFloors(String searchText, Long hotelId, int page, int size);
    StandardResponse<?> deleteFloor(Long id);
}
