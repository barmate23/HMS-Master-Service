package com.hotelerp.hotelmaster.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    @JsonAlias({"logoUrl"})
    private String logoUrl;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
}
