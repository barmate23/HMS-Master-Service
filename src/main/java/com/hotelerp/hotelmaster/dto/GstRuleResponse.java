package com.hotelerp.hotelmaster.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GstRuleResponse {

    private Long id;
    private Long hotelId;
    private String hotelName;
    private String displayId;        // e.g. "GST-1"
    private String serviceCategory;
    private String hsnSacCode;
    private BigDecimal cgstRate;
    private BigDecimal sgstRate;
    private BigDecimal igstRate;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
