package com.hotelerp.hotelmaster.dto;

import com.hotelerp.common.entity.Room;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {
    private String roomNumber;
    private Long floorId;
    private Long roomTypeId;
    private Long statusId;
    private Long hkStatusId;
    private Integer maxOccupancy;
    private String telephone;
}
