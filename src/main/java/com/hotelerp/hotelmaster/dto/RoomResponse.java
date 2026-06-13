package com.hotelerp.hotelmaster.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomNumber;
    private Long   floorId;
    private String floorNumber;
    private Long   roomTypeId;
    private String roomTypeName;
    private Long statusId;
    private String statusValue;
    private Long hkStatusId;
    private String hkStatusValue;
    private Integer maxOccupancy;
    private String telephone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
}
