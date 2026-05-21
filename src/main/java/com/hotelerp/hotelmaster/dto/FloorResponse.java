package com.hotelerp.hotelmaster.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FloorResponse {
    private Long id;
    private Long hotelId;
    private String hotelName;
    private String floorNumber;
    private Integer noOfRooms;
    private String telephone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
}
