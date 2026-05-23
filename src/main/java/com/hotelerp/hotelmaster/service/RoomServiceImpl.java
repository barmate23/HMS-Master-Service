package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.RoomRequest;
import com.hotelerp.hotelmaster.dto.RoomResponse;
import com.hotelerp.hotelmaster.entity.Floor;
import com.hotelerp.hotelmaster.entity.Room;
import com.hotelerp.hotelmaster.entity.RoomType;
import com.hotelerp.hotelmaster.repository.FloorRepository;
import com.hotelerp.hotelmaster.repository.RoomRepository;
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
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;
    private final FloorRepository floorRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    @Transactional
    public StandardResponse<?> createRoom(RoomRequest request) {
        log.info("Request received to create room: {}", request.getRoomNumber());
        try {
            Optional<Floor> floorOpt = floorRepository.findById(request.getFloorId());
            if (floorOpt.isEmpty()) {
                return StandardResponse.error("Floor not found", "NOT_FOUND", "floorId", null);
            }

            Optional<RoomType> typeOpt = roomTypeRepository.findById(request.getRoomTypeId());
            if (typeOpt.isEmpty()) {
                return StandardResponse.error("Room Type not found", "NOT_FOUND", "roomTypeId", null);
            }

            Room room = Room.builder()
                    .roomNumber(request.getRoomNumber())
                    .floor(floorOpt.get())
                    .roomType(typeOpt.get())
                    .status(request.getStatus() != null ? request.getStatus() : Room.RoomStatus.VACANT)
                    .maxOccupancy(request.getMaxOccupancy())
                    .telephone(request.getTelephone())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .isActive(true)
                    .build();
            
            Room saved = repository.save(room);
            return StandardResponse.success(mapToResponse(saved), "Room created successfully");
        } catch (Exception e) {
            log.error("Error creating room: ", e);
            return StandardResponse.error("Failed to create room", "CREATE_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional
    public StandardResponse<?> updateRoom(Long id, RoomRequest request) {
        log.info("Request received to update room ID: {}", id);
        try {
            Optional<Room> existingOpt = repository.findById(id);
            if (existingOpt.isEmpty()) {
                return StandardResponse.error("Room not found", "NOT_FOUND", "id", null);
            }
            Room room = existingOpt.get();
            
            if (!room.getFloor().getId().equals(request.getFloorId())) {
                Optional<Floor> floorOpt = floorRepository.findById(request.getFloorId());
                if (floorOpt.isEmpty()) {
                    return StandardResponse.error("Floor not found", "NOT_FOUND", "floorId", null);
                }
                room.setFloor(floorOpt.get());
            }

            if (!room.getRoomType().getId().equals(request.getRoomTypeId())) {
                Optional<RoomType> typeOpt = roomTypeRepository.findById(request.getRoomTypeId());
                if (typeOpt.isEmpty()) {
                    return StandardResponse.error("Room Type not found", "NOT_FOUND", "roomTypeId", null);
                }
                room.setRoomType(typeOpt.get());
            }

            room.setRoomNumber(request.getRoomNumber());
            room.setStatus(request.getStatus());
            room.setMaxOccupancy(request.getMaxOccupancy());
            room.setTelephone(request.getTelephone());
            room.setUpdatedAt(LocalDateTime.now());
            
            Room updated = repository.save(room);
            return StandardResponse.success(mapToResponse(updated), "Room updated successfully");
        } catch (Exception e) {
            log.error("Error updating room: ", e);
            return StandardResponse.error("Failed to update room", "UPDATE_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getRoomById(Long id) {
        log.info("Fetching room ID: {}", id);
        try {
            return repository.findById(id)
                    .map(room -> StandardResponse.success(mapToResponse(room), "Room fetched successfully"))
                    .orElse(StandardResponse.error("Room not found", "NOT_FOUND", "id", null));
        } catch (Exception e) {
            log.error("Error fetching room: ", e);
            return StandardResponse.error("Failed to fetch room", "FETCH_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getAllRooms(String searchText, Room.RoomStatus status, Long floorId, Long roomTypeId, int page, int size) {
        log.info("Fetching all rooms with filters - searchText: {}, status: {}, floorId: {}, roomTypeId: {}, page: {}, size: {}", 
                searchText, status, floorId, roomTypeId, page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Room> roomPage = repository.searchRooms(searchText, status, floorId, roomTypeId, pageable);
            
            List<RoomResponse> responses = roomPage.getContent().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());

            StandardResponse.ResponseMetadata metadata = StandardResponse.ResponseMetadata.builder()
                    .totalRecords(roomPage.getTotalElements())
                    .currentPage(roomPage.getNumber())
                    .pageSize(roomPage.getSize())
                    .totalPages(roomPage.getTotalPages())
                    .build();

            return StandardResponse.success(responses, "Rooms fetched successfully", metadata);
        } catch (Exception e) {
            log.error("Error fetching rooms: ", e);
            return StandardResponse.error("Failed to fetch rooms", "FETCH_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional
    public StandardResponse<?> deleteRoom(Long id) {
        log.info("Deleting room ID: {}", id);
        try {
            Optional<Room> opt = repository.findById(id);
            if (opt.isEmpty()) {
                return StandardResponse.error("Room not found", "NOT_FOUND", "id", null);
            }
            Room room = opt.get();
            room.setIsActive(false);
            room.setUpdatedAt(LocalDateTime.now());
            repository.save(room);
            return StandardResponse.success("Room deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting room: ", e);
            return StandardResponse.error("Failed to delete room", "DELETE_ERROR", e.getMessage());
        }
    }

    private RoomResponse mapToResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .floorId(room.getFloor().getId())
                .floorNumber(room.getFloor().getFloorNumber())
                .roomTypeId(room.getRoomType().getId())
                .roomTypeName(room.getRoomType().getName())
                .status(room.getStatus())
                .maxOccupancy(room.getMaxOccupancy())
                .telephone(room.getTelephone())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .isActive(room.getIsActive())
                .build();
    }
}
