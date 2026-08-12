package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.config.LoginUser;
import com.hotelerp.hotelmaster.dto.HotelRequest;
import com.hotelerp.hotelmaster.dto.HotelResponse;
import com.hotelerp.hotelmaster.entity.Hotel;
import com.hotelerp.hotelmaster.entity.User;
import com.hotelerp.hotelmaster.repository.HotelRepository;
import com.hotelerp.hotelmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;
    private final UserRepository userRepository;
    private final LoginUser loginUser;

    @Override
    @Transactional
    public StandardResponse<?> createHotel(HotelRequest request) {
        log.info("Request received to create hotel: {}", request.getName());
        try {
            Hotel hotel = Hotel.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .city(request.getCity())
                    .state(request.getState())
                    .country(request.getCountry())
                    .zipCode(request.getZipCode())
                    .totalRooms(request.getTotalRooms())
                    .currency(request.getCurrency() != null ? request.getCurrency() : "USD")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .isActive(true)
                    .build();
            
            Hotel saved = repository.save(hotel);
            User user = userRepository.findById(loginUser.getUserId()).get();
            user.setProperty(hotel);
            userRepository.save(user);
            return StandardResponse.success(mapToResponse(saved), "Hotel created successfully");
        } catch (Exception e) {
            log.error("Error creating hotel: ", e);
            return StandardResponse.error("Failed to create hotel", "CREATE_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional
    public StandardResponse<?> updateHotel(Long id, HotelRequest request) {
        log.info("Request received to update hotel ID: {}", id);
        try {
            Optional<Hotel> existingOpt = repository.findById(id);
            if (existingOpt.isEmpty()) {
                return StandardResponse.error("Hotel not found", "NOT_FOUND", "id", null);
            }
            Hotel hotel = existingOpt.get();
            
            hotel.setName(request.getName());
            hotel.setEmail(request.getEmail());
            hotel.setPhone(request.getPhone());
            hotel.setAddress(request.getAddress());
            hotel.setCity(request.getCity());
            hotel.setState(request.getState());
            hotel.setCountry(request.getCountry());
            hotel.setZipCode(request.getZipCode());
            hotel.setTotalRooms(request.getTotalRooms());
            hotel.setCurrency(request.getCurrency());
            hotel.setUpdatedAt(LocalDateTime.now());
            
            Hotel updated = repository.save(hotel);
            return StandardResponse.success(mapToResponse(updated), "Hotel updated successfully");
        } catch (Exception e) {
            log.error("Error updating hotel: ", e);
            return StandardResponse.error("Failed to update hotel", "UPDATE_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getHotelById(Long id) {
        log.info("Fetching hotel ID: {}", id);
        try {
            return repository.findById(id)
                    .map(hotel -> StandardResponse.success(mapToResponse(hotel), "Hotel fetched successfully"))
                    .orElse(StandardResponse.error("Hotel not found", "NOT_FOUND", "id", null));
        } catch (Exception e) {
            log.error("Error fetching hotel: ", e);
            return StandardResponse.error("Failed to fetch hotel", "FETCH_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getAllHotels(String searchText, int page, int size) {
        log.info("Fetching all hotels with searchText: {}, page: {}, size: {}", searchText, page, size);
        try {
            if (loginUser != null && loginUser.getHotelId() != null) {
                return getHotelById(loginUser.getHotelId());
            }
            return StandardResponse.success(null, "Hotel ID is not present for login user");
        } catch (Exception e) {
            log.error("Error fetching hotels: ", e);
            return StandardResponse.error("Failed to fetch hotels", "FETCH_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional
    public StandardResponse<?> deleteHotel(Long id) {
        log.info("Deleting hotel ID: {}", id);
        try {
            Optional<Hotel> opt = repository.findById(id);
            if (opt.isEmpty()) {
                return StandardResponse.error("Hotel not found", "NOT_FOUND", "id", null);
            }
            Hotel hotel = opt.get();
            hotel.setIsActive(false);
            hotel.setUpdatedAt(LocalDateTime.now());
            repository.save(hotel);
            return StandardResponse.success("Hotel deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting hotel: ", e);
            return StandardResponse.error("Failed to delete hotel", "DELETE_ERROR", e.getMessage());
        }
    }

    private HotelResponse mapToResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .email(hotel.getEmail())
                .phone(hotel.getPhone())
                .address(hotel.getAddress())
                .city(hotel.getCity())
                .state(hotel.getState())
                .country(hotel.getCountry())
                .zipCode(hotel.getZipCode())
                .totalRooms(hotel.getTotalRooms())
                .currency(hotel.getCurrency())
                .createdAt(hotel.getCreatedAt())
                .updatedAt(hotel.getUpdatedAt())
                .isActive(hotel.getIsActive())
                .build();
    }
}
