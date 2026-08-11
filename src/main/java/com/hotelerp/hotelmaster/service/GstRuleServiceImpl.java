package com.hotelerp.hotelmaster.service;

import com.hotelerp.hotelmaster.common.StandardResponse;
import com.hotelerp.hotelmaster.config.LoginUser;
import com.hotelerp.hotelmaster.dto.GstRuleRequest;
import com.hotelerp.hotelmaster.dto.GstRuleResponse;
import com.hotelerp.hotelmaster.entity.GstRule;
import com.hotelerp.hotelmaster.entity.Hotel;
import com.hotelerp.hotelmaster.repository.CommonMasterRepository;
import com.hotelerp.hotelmaster.repository.GstRuleRepository;
import com.hotelerp.hotelmaster.repository.HotelRepository;
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
public class GstRuleServiceImpl implements GstRuleService {

    /** CommonMaster category key for GST service categories */
    private static final String GST_CATEGORY_KEY = "GST_SERVICE_CATEGORY";

    private final GstRuleRepository gstRuleRepository;
    private final CommonMasterRepository commonMasterRepository;
    private final HotelRepository hotelRepository;
    private final LoginUser loginUser;

    // ─────────────────────────── CREATE ────────────────────────────────────

    @Override
    @Transactional
    public StandardResponse<?> createGstRule(GstRuleRequest request) {
        log.info("Request to create GST rule for category: {}", request.getServiceCategory());
        try {
            Long hotelId = (loginUser != null) ? loginUser.getHotelId() : null;

            if (hotelId == null) {
                return StandardResponse.error(
                        "Hotel not found. Please create a hotel first before creating a GST rule",
                        "HOTEL_NOT_FOUND", "hotelId", "Hotel ID is missing in token");
            }

            Optional<Hotel> hotelOpt = hotelRepository.findById(hotelId);
            if (hotelOpt.isEmpty()) {
                return StandardResponse.error(
                        "Hotel not found. Please create a hotel first before creating a GST rule",
                        "HOTEL_NOT_FOUND", "hotelId", "No hotel exists for ID: " + hotelId);
            }

            // Prevent duplicate active rule for the same service category within the hotel
            if (gstRuleRepository.existsByServiceCategoryIgnoreCaseAndHotelIdAndIsActiveTrue(request.getServiceCategory(), hotelId)) {
                return StandardResponse.error(
                        "An active GST rule already exists for category: " + request.getServiceCategory(),
                        "DUPLICATE_CATEGORY", "serviceCategory", null);
            }

            GstRule rule = GstRule.builder()
                    .hotel(hotelOpt.get())
                    .serviceCategory(request.getServiceCategory())
                    .hsnSacCode(request.getHsnSacCode())
                    .cgstRate(request.getCgstRate())
                    .sgstRate(request.getSgstRate())
                    .igstRate(request.getCgstRate().add(request.getSgstRate()))
                    .description(request.getDescription())
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            GstRule saved = gstRuleRepository.save(rule);
            return StandardResponse.success(mapToResponse(saved), "GST rule created successfully");
        } catch (Exception e) {
            log.error("Error creating GST rule: ", e);
            return StandardResponse.error("Failed to create GST rule", "CREATE_ERROR", e.getMessage());
        }
    }

    // ─────────────────────────── UPDATE ────────────────────────────────────

    @Override
    @Transactional
    public StandardResponse<?> updateGstRule(Long id, GstRuleRequest request) {
        log.info("Request to update GST rule ID: {}", id);
        try {
            Optional<GstRule> existingOpt = gstRuleRepository.findById(id);
            if (existingOpt.isEmpty()) {
                return StandardResponse.error("GST rule not found", "NOT_FOUND", "id", null);
            }
            GstRule rule = existingOpt.get();

            Long targetHotelId = (loginUser != null) ? loginUser.getHotelId() : null;
            if (targetHotelId != null && (rule.getHotel() == null || !rule.getHotel().getId().equals(targetHotelId))) {
                Optional<Hotel> hotelOpt = hotelRepository.findById(targetHotelId);
                if (hotelOpt.isPresent()) {
                    rule.setHotel(hotelOpt.get());
                }
            }

            // Allow update if same record or no other active rule with that category
            boolean categoryChanged = !rule.getServiceCategory().equalsIgnoreCase(request.getServiceCategory());
            Long currentHotelId = rule.getHotel() != null ? rule.getHotel().getId() : targetHotelId;
            if (categoryChanged && currentHotelId != null && gstRuleRepository.existsByServiceCategoryIgnoreCaseAndHotelIdAndIsActiveTrue(request.getServiceCategory(), currentHotelId)) {
                return StandardResponse.error(
                        "An active GST rule already exists for category: " + request.getServiceCategory(),
                        "DUPLICATE_CATEGORY", "serviceCategory", null);
            }

            rule.setServiceCategory(request.getServiceCategory());
            rule.setHsnSacCode(request.getHsnSacCode());
            rule.setCgstRate(request.getCgstRate());
            rule.setSgstRate(request.getSgstRate());
            rule.setIgstRate(request.getCgstRate().add(request.getSgstRate()));
            rule.setDescription(request.getDescription());
            rule.setUpdatedAt(LocalDateTime.now());

            GstRule updated = gstRuleRepository.save(rule);
            return StandardResponse.success(mapToResponse(updated), "GST rule updated successfully");
        } catch (Exception e) {
            log.error("Error updating GST rule: ", e);
            return StandardResponse.error("Failed to update GST rule", "UPDATE_ERROR", e.getMessage());
        }
    }

    // ─────────────────────────── READ ──────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getGstRuleById(Long id) {
        log.info("Fetching GST rule ID: {}", id);
        try {
            return gstRuleRepository.findById(id)
                    .map(rule -> StandardResponse.success(mapToResponse(rule), "GST rule fetched successfully"))
                    .orElse(StandardResponse.error("GST rule not found", "NOT_FOUND", "id", null));
        } catch (Exception e) {
            log.error("Error fetching GST rule: ", e);
            return StandardResponse.error("Failed to fetch GST rule", "FETCH_ERROR", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getAllGstRules(String searchText, int page, int size) {
        Long hotelId = (loginUser != null) ? loginUser.getHotelId() : null;
        log.info("Fetching all GST rules | search: {}, hotelId: {}, page: {}, size: {}", searchText, hotelId, page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<GstRule> resultPage = gstRuleRepository.searchGstRules(searchText, hotelId, pageable);

            List<GstRuleResponse> responses = resultPage.getContent().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());

            StandardResponse.ResponseMetadata metadata = StandardResponse.ResponseMetadata.builder()
                    .totalRecords(resultPage.getTotalElements())
                    .currentPage(resultPage.getNumber())
                    .pageSize(resultPage.getSize())
                    .totalPages(resultPage.getTotalPages())
                    .build();

            return StandardResponse.success(responses, "GST rules fetched successfully", metadata);
        } catch (Exception e) {
            log.error("Error fetching GST rules: ", e);
            return StandardResponse.error("Failed to fetch GST rules", "FETCH_ERROR", e.getMessage());
        }
    }

    // ─────────────────────────── DELETE (soft) ─────────────────────────────

    @Override
    @Transactional
    public StandardResponse<?> deleteGstRule(Long id) {
        log.info("Soft-deleting GST rule ID: {}", id);
        try {
            Optional<GstRule> opt = gstRuleRepository.findById(id);
            if (opt.isEmpty()) {
                return StandardResponse.error("GST rule not found", "NOT_FOUND", "id", null);
            }
            GstRule rule = opt.get();
            rule.setIsActive(false);
            rule.setUpdatedAt(LocalDateTime.now());
            gstRuleRepository.save(rule);
            return StandardResponse.success("GST rule deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting GST rule: ", e);
            return StandardResponse.error("Failed to delete GST rule", "DELETE_ERROR", e.getMessage());
        }
    }

    // ─────────────────────────── CATEGORIES ────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public StandardResponse<?> getGstCategories() {
        log.info("Fetching GST service categories from CommonMaster");
        try {
            List<String> categories = commonMasterRepository
                    .findByCategoryAndIsActiveTrue(GST_CATEGORY_KEY)
                    .stream()
                    .map(cm -> cm.getValue())
                    .collect(Collectors.toList());
            return StandardResponse.success(categories, "GST categories fetched successfully");
        } catch (Exception e) {
            log.error("Error fetching GST categories: ", e);
            return StandardResponse.error("Failed to fetch GST categories", "FETCH_ERROR", e.getMessage());
        }
    }

    // ─────────────────────────── MAPPER ────────────────────────────────────

    private GstRuleResponse mapToResponse(GstRule rule) {
        return GstRuleResponse.builder()
                .id(rule.getId())
                .hotelId(rule.getHotel() != null ? rule.getHotel().getId() : null)
                .hotelName(rule.getHotel() != null ? rule.getHotel().getName() : null)
                .displayId("GST-" + rule.getId())
                .serviceCategory(rule.getServiceCategory())
                .hsnSacCode(rule.getHsnSacCode())
                .cgstRate(rule.getCgstRate())
                .sgstRate(rule.getSgstRate())
                .igstRate(rule.getIgstRate())
                .description(rule.getDescription())
                .isActive(rule.getIsActive())
                .createdAt(rule.getCreatedAt())
                .updatedAt(rule.getUpdatedAt())
                .build();
    }
}
