package com.hotelerp.hotelmaster.dto;

import com.hotelerp.hotelmaster.entity.Room;
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
    private Room.RoomStatus status;
    private Integer maxOccupancy;
    private String telephone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
}
