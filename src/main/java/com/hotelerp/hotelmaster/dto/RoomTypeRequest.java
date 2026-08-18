package com.hotelerp.hotelmaster.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeRequest {
    private Long hotelId;
    private String name;
    private Integer capacity;
    private BigDecimal basePricePerNight;
    private BigDecimal area;
    private String description;
}
