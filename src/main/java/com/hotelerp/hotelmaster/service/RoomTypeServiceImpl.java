package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.RoomTypeRequest;
import com.hotelerp.hotelmaster.dto.RoomTypeResponse;
import com.hotelerp.common.entity.Hotel;
import com.hotelerp.common.entity.RoomType;
import com.hotelerp.hotelmaster.repository.HotelRepository;
import com.hotelerp.hotelmaster.repository.RoomTypeRepository;
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
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository repository;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional
    public StandardResponse<?> createRoomType(RoomTypeRequest request) {
        log.info("Request received to create room type: {}", request.getName());
        try {
            Optional<Hotel> hotelOpt = hotelRepository.findById(request.getHotelId());
            if (hotelOpt.isEmpty()) {
                return StandardResponse.error("Hotel not found", "NOT_FOUND", "hotelId", null);
            }

            RoomType roomType = RoomType.builder()
                    .hotel(hotelOpt.get())
                    .name(request.getName())
                    .capacity(request.getCapacity())
                    .basePricePerNight(request.getBasePricePerNight())
                    .area(request.getArea())
                    .description(request.getDescription())
                    .imageUrl(request.getImageUrl())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .isActive(true)
                    .build();
            
            RoomType saved = repository.save(roomType);
            return StandardResponse.success(mapToResponse(saved), "Room type created successfully");
        } catch (Exception e) {
            log.error("Error creating room type: ", e);
            return StandardResponse.error("Failed to create room type", "CREATE_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional
    public StandardResponse<?> updateRoomType(Long id, RoomTypeRequest request) {
        log.info("Request received to update room type ID: {}", id);
        try {
            Optional<RoomType> existingOpt = repository.findById(id);
            if (existingOpt.isEmpty()) {
                return StandardResponse.error("Room type not found", "NOT_FOUND", "id", null);
            }
            RoomType roomType = existingOpt.get();
            
            if (!roomType.getHotel().getId().equals(request.getHotelId())) {
                Optional<Hotel> hotelOpt = hotelRepository.findById(request.getHotelId());
                if (hotelOpt.isEmpty()) {
                    return StandardResponse.error("Hotel not found", "NOT_FOUND", "hotelId", null);
                }
                roomType.setHotel(hotelOpt.get());
            }

            roomType.setName(request.getName());
            roomType.setCapacity(request.getCapacity());
            roomType.setBasePricePerNight(request.getBasePricePerNight());
            roomType.setArea(request.getArea());
            roomType.setDescription(request.getDescription());
            roomType.setImageUrl(request.getImageUrl());
            roomType.setUpdatedAt(LocalDateTime.now());
            
            RoomType updated = repository.save(roomType);
            return StandardResponse.success(mapToResponse(updated), "Room type updated successfully");
        } catch (Exception e) {
            log.error("Error updating room type: ", e);
            return StandardResponse.error("Failed to update room type", "UPDATE_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getRoomTypeById(Long id) {
        log.info("Fetching room type ID: {}", id);
        try {
            return repository.findById(id)
                    .map(rt -> StandardResponse.success(mapToResponse(rt), "Room type fetched successfully"))
                    .orElse(StandardResponse.error("Room type not found", "NOT_FOUND", "id", null));
        } catch (Exception e) {
            log.error("Error fetching room type: ", e);
            return StandardResponse.error("Failed to fetch room type", "FETCH_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getAllRoomTypes(String searchText, Long hotelId, int page, int size) {
        log.info("Fetching all room types with searchText: {}, hotelId: {}, page: {}, size: {}", 
                searchText, hotelId, page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<RoomType> roomTypePage = repository.searchRoomTypes(searchText, hotelId, pageable);
            
            List<RoomTypeResponse> responses = roomTypePage.getContent().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());

            StandardResponse.ResponseMetadata metadata = StandardResponse.ResponseMetadata.builder()
                    .totalRecords(roomTypePage.getTotalElements())
                    .currentPage(roomTypePage.getNumber())
                    .pageSize(roomTypePage.getSize())
                    .totalPages(roomTypePage.getTotalPages())
                    .build();

            return StandardResponse.success(responses, "Room types fetched successfully", metadata);
        } catch (Exception e) {
            log.error("Error fetching room types: ", e);
            return StandardResponse.error("Failed to fetch room types", "FETCH_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional
    public StandardResponse<?> deleteRoomType(Long id) {
        log.info("Deleting room type ID: {}", id);
        try {
            Optional<RoomType> opt = repository.findById(id);
            if (opt.isEmpty()) {
                return StandardResponse.error("Room type not found", "NOT_FOUND", "id", null);
            }
            RoomType rt = opt.get();
            rt.setIsActive(false);
            rt.setUpdatedAt(LocalDateTime.now());
            repository.save(rt);
            return StandardResponse.success("Room type deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting room type: ", e);
            return StandardResponse.error("Failed to delete room type", "DELETE_ERROR", e.getMessage());
        }
    }

    private RoomTypeResponse mapToResponse(RoomType rt) {
        return RoomTypeResponse.builder()
                .id(rt.getId())
                .hotelId(rt.getHotel().getId())
                .hotelName(rt.getHotel().getName())
                .name(rt.getName())
                .capacity(rt.getCapacity())
                .basePricePerNight(rt.getBasePricePerNight())
                .area(rt.getArea())
                .description(rt.getDescription())
                .imageUrl(rt.getImageUrl())
                .createdAt(rt.getCreatedAt())
                .updatedAt(rt.getUpdatedAt())
                .isActive(rt.getIsActive())
                .build();
    }
}
