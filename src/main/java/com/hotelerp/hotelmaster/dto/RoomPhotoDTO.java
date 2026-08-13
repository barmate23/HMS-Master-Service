package com.hotelerp.hotelmaster.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomPhotoDTO {
    private Long id;
    private String fileName;
    private String fileType;
    private byte[] photoData;
}
