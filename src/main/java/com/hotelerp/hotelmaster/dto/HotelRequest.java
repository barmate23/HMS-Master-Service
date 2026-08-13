package com.hotelerp.hotelmaster.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
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

    private byte[] logo;

    @JsonAlias({"starRating", "starRatingCategory"})
    private String starRatingCategory;

    @JsonAlias({"slogan", "tagline"})
    private String tagline;

    private String receptionDeskPhone;

    @JsonAlias({"website", "websiteUrl"})
    private String websiteUrl;

    @JsonAlias({"taxRegNo", "gstinTaxRegNo", "gstin"})
    private String gstin;

    @JsonAlias({"foodLicenseNo", "fssai", "fssaiNo"})
    private String fssaiNo;

    @JsonAlias({"standardCheckInTime", "checkInTime"})
    private String checkInTime;

    @JsonAlias({"standardCheckOutTime", "checkOutTime"})
    private String checkOutTime;

    private Integer totalRooms;
    private String currency;
}
