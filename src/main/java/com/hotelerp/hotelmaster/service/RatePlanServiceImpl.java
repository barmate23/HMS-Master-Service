package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.dto.RatePlanRequest;
import com.hotelerp.hotelmaster.dto.RatePlanResponse;
import com.hotelerp.hotelmaster.entity.RatePlan;
import com.hotelerp.hotelmaster.config.LoginUser;
import com.hotelerp.hotelmaster.entity.Hotel;
import com.hotelerp.hotelmaster.repository.HotelRepository;
import com.hotelerp.hotelmaster.repository.RatePlanRepository;
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
public class RatePlanServiceImpl implements RatePlanService {

    private final RatePlanRepository repository;
    private final HotelRepository hotelRepository;
    private final LoginUser loginUser;

    @Override
    @Transactional
    public StandardResponse<?> createRatePlan(RatePlanRequest request) {
        log.info("Request to create rate plan: {}", request.getName());
        try {
            Long hotelId = (loginUser != null) ? loginUser.getHotelId() : null;

            if (hotelId == null) {
                return StandardResponse.error("Hotel not found. Please create a hotel first before creating a rate plan", "HOTEL_NOT_FOUND", "hotelId", "Hotel ID is missing in token");
            }

            Optional<Hotel> hotelOpt = hotelRepository.findById(hotelId);
            if (hotelOpt.isEmpty()) {
                return StandardResponse.error("Hotel not found. Please create a hotel first before creating a rate plan", "HOTEL_NOT_FOUND", "hotelId", "No hotel exists for ID: " + hotelId);
            }

            RatePlan ratePlan = RatePlan.builder()
                    .hotel(hotelOpt.get())
                    .name(request.getName())
                    .description(request.getDescription())
                    .priceAdjustment(request.getPriceAdjustment())
                    .displayOrder(request.getDisplayOrder())
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            RatePlan saved = repository.save(ratePlan);
            return StandardResponse.success(mapToResponse(saved), "Rate plan created successfully");
        } catch (Exception e) {
            log.error("Error creating rate plan: ", e);
            return StandardResponse.error("Failed to create rate plan", "CREATE_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional
    public StandardResponse<?> updateRatePlan(Long id, RatePlanRequest request) {
        log.info("Request to update rate plan ID: {}", id);
        try {
            Optional<RatePlan> existingOpt = repository.findById(id);
            if (existingOpt.isEmpty()) {
                return StandardResponse.error("Rate plan not found", "NOT_FOUND", "id", null);
            }
            RatePlan ratePlan = existingOpt.get();
            ratePlan.setName(request.getName());
            ratePlan.setDescription(request.getDescription());
            ratePlan.setPriceAdjustment(request.getPriceAdjustment());
            ratePlan.setDisplayOrder(request.getDisplayOrder());
            ratePlan.setUpdatedAt(LocalDateTime.now());

            RatePlan updated = repository.save(ratePlan);
            return StandardResponse.success(mapToResponse(updated), "Rate plan updated successfully");
        } catch (Exception e) {
            log.error("Error updating rate plan: ", e);
            return StandardResponse.error("Failed to update rate plan", "UPDATE_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getRatePlanById(Long id) {
        log.info("Fetching rate plan ID: {}", id);
        try {
            return repository.findById(id)
                    .map(rp -> StandardResponse.success(mapToResponse(rp), "Rate plan fetched successfully"))
                    .orElse(StandardResponse.error("Rate plan not found", "NOT_FOUND", "id", null));
        } catch (Exception e) {
            log.error("Error fetching rate plan: ", e);
            return StandardResponse.error("Failed to fetch rate plan", "FETCH_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getAllRatePlans(String searchText, int page, int size) {
        Long hotelId = (loginUser != null) ? loginUser.getHotelId() : null;
        log.info("Fetching all rate plans with searchText: {}, hotelId: {}, page: {}, size: {}", searchText, hotelId, page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<RatePlan> ratePlanPage = repository.searchRatePlans(searchText, hotelId, pageable);

            List<RatePlanResponse> responses = ratePlanPage.getContent().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());

            StandardResponse.ResponseMetadata metadata = StandardResponse.ResponseMetadata.builder()
                    .totalRecords(ratePlanPage.getTotalElements())
                    .currentPage(ratePlanPage.getNumber())
                    .pageSize(ratePlanPage.getSize())
                    .totalPages(ratePlanPage.getTotalPages())
                    .build();

            return StandardResponse.success(responses, "Rate plans fetched successfully", metadata);
        } catch (Exception e) {
            log.error("Error fetching rate plans: ", e);
            return StandardResponse.error("Failed to fetch rate plans", "FETCH_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional
    public StandardResponse<?> deleteRatePlan(Long id) {
        log.info("Deleting rate plan ID: {}", id);
        try {
            Optional<RatePlan> opt = repository.findById(id);
            if (opt.isEmpty()) {
                return StandardResponse.error("Rate plan not found", "NOT_FOUND", "id", null);
            }
            RatePlan ratePlan = opt.get();
            ratePlan.setIsActive(false);
            ratePlan.setUpdatedAt(LocalDateTime.now());
            repository.save(ratePlan);
            return StandardResponse.success("Rate plan deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting rate plan: ", e);
            return StandardResponse.error("Failed to delete rate plan", "DELETE_ERROR", e.getMessage());
        }
    }

    private RatePlanResponse mapToResponse(RatePlan rp) {
        return RatePlanResponse.builder()
                .id(rp.getId())
                .name(rp.getName())
                .description(rp.getDescription())
                .priceAdjustment(rp.getPriceAdjustment())
                .displayOrder(rp.getDisplayOrder())
                .isActive(rp.getIsActive())
                .hotelId(rp.getHotel() != null ? rp.getHotel().getId() : null)
                .hotelName(rp.getHotel() != null ? rp.getHotel().getName() : null)
                .createdAt(rp.getCreatedAt())
                .updatedAt(rp.getUpdatedAt())
                .build();
    }
}
