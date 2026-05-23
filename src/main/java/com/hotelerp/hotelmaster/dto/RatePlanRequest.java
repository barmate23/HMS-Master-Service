package com.hotelerp.hotelmaster.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatePlanRequest {
    private String name;
    private String description;
    private BigDecimal priceAdjustment;
    private Integer displayOrder;
}
