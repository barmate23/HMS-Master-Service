package com.hotelerp.hotelmaster.dto;

import com.hotelerp.hotelmaster.entity.Room;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {
    private String roomNumber;
    private Long floorId;
    private Long roomTypeId;
    private Room.RoomStatus status;
    private Integer maxOccupancy;
    private String telephone;
}
