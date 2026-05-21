package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.FloorRequest;
import com.hotelerp.hotelmaster.dto.FloorResponse;
import com.hotelerp.hotelmaster.entity.Floor;
import com.hotelerp.hotelmaster.entity.Hotel;
import com.hotelerp.hotelmaster.repository.FloorRepository;
import com.hotelerp.hotelmaster.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FloorServiceImpl implements FloorService {

    private final FloorRepository repository;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional
    public StandardResponse<?> createFloor(FloorRequest request) {
        log.info("Request received to create floor: {}", request.getFloorNumber());
        try {
            Optional<Hotel> hotelOpt = hotelRepository.findById(request.getHotelId());
            if (hotelOpt.isEmpty()) {
                return StandardResponse.error("Hotel not found", "NOT_FOUND", "hotelId", null);
            }

            Floor floor = Floor.builder()
                    .hotel(hotelOpt.get())
                    .floorNumber(request.getFloorNumber())
                    .noOfRooms(request.getNoOfRooms())
                    .telephone(request.getTelephone())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .isActive(true)
                    .build();
            
            Floor saved = repository.save(floor);
            return StandardResponse.success(mapToResponse(saved), "Floor created successfully");
        } catch (Exception e) {
            log.error("Error creating floor: ", e);
            return StandardResponse.error("Failed to create floor", "CREATE_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional
    public StandardResponse<?> updateFloor(Long id, FloorRequest request) {
        log.info("Request received to update floor ID: {}", id);
        try {
            Optional<Floor> existingOpt = repository.findById(id);
            if (existingOpt.isEmpty()) {
                return StandardResponse.error("Floor not found", "NOT_FOUND", "id", null);
            }
            Floor floor = existingOpt.get();
            
            if (!floor.getHotel().getId().equals(request.getHotelId())) {
                Optional<Hotel> hotelOpt = hotelRepository.findById(request.getHotelId());
                if (hotelOpt.isEmpty()) {
                    return StandardResponse.error("Hotel not found", "NOT_FOUND", "hotelId", null);
                }
                floor.setHotel(hotelOpt.get());
            }

            floor.setFloorNumber(request.getFloorNumber());
            floor.setNoOfRooms(request.getNoOfRooms());
            floor.setTelephone(request.getTelephone());
            floor.setUpdatedAt(LocalDateTime.now());
            
            Floor updated = repository.save(floor);
            return StandardResponse.success(mapToResponse(updated), "Floor updated successfully");
        } catch (Exception e) {
            log.error("Error updating floor: ", e);
            return StandardResponse.error("Failed to update floor", "UPDATE_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getFloorById(Long id) {
        log.info("Fetching floor ID: {}", id);
        try {
            return repository.findById(id)
                    .map(floor -> StandardResponse.success(mapToResponse(floor), "Floor fetched successfully"))
                    .orElse(StandardResponse.error("Floor not found", "NOT_FOUND", "id", null));
        } catch (Exception e) {
            log.error("Error fetching floor: ", e);
            return StandardResponse.error("Failed to fetch floor", "FETCH_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getAllFloors() {
        log.info("Fetching all floors");
        try {
            List<Floor> list = repository.findAll();
            List<FloorResponse> responses = list.stream().map(this::mapToResponse).collect(Collectors.toList());
            return StandardResponse.success(responses, "Floors fetched successfully");
        } catch (Exception e) {
            log.error("Error fetching all floors: ", e);
            return StandardResponse.error("Failed to fetch floors", "FETCH_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional
    public StandardResponse<?> deleteFloor(Long id) {
        log.info("Deleting floor ID: {}", id);
        try {
            Optional<Floor> opt = repository.findById(id);
            if (opt.isEmpty()) {
                return StandardResponse.error("Floor not found", "NOT_FOUND", "id", null);
            }
            Floor floor = opt.get();
            floor.setIsActive(false);
            floor.setUpdatedAt(LocalDateTime.now());
            repository.save(floor);
            return StandardResponse.success("Floor deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting floor: ", e);
            return StandardResponse.error("Failed to delete floor", "DELETE_ERROR", e.getMessage());
        }
    }

    private FloorResponse mapToResponse(Floor floor) {
        return FloorResponse.builder()
                .id(floor.getId())
                .hotelId(floor.getHotel().getId())
                .hotelName(floor.getHotel().getName())
                .floorNumber(floor.getFloorNumber())
                .noOfRooms(floor.getNoOfRooms())
                .telephone(floor.getTelephone())
                .createdAt(floor.getCreatedAt())
                .updatedAt(floor.getUpdatedAt())
                .isActive(floor.getIsActive())
                .build();
    }
}
