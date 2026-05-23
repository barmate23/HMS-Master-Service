package com.hotelerp.hotelmaster.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatePlanResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal priceAdjustment;
    private Integer displayOrder;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
