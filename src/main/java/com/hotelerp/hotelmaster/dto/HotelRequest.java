package com.hotelerp.hotelmaster.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {
    @NotBlank(message = "Hotel name is required")
    private String name;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    
    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private Integer totalRooms;
    private String currency;
}
