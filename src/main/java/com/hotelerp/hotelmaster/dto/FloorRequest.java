package com.hotelerp.hotelmaster.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FloorRequest {
    private Long hotelId;
    private String floorNumber;
    private Integer noOfRooms;
    private String telephone;
}
