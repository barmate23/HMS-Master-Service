package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.HotelRequest;

public interface HotelService {
    StandardResponse<?> createHotel(HotelRequest request);
    StandardResponse<?> updateHotel(Long id, HotelRequest request);
    StandardResponse<?> getHotelById(Long id);
    StandardResponse<?> getAllHotels(String searchText, int page, int size);
    StandardResponse<?> deleteHotel(Long id);
}
