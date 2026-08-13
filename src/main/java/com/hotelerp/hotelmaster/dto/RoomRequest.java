package com.hotelerp.hotelmaster.dto;

import lombok.*;
import java.util.List;

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
    private List<RoomPhotoDTO> photos;
}
