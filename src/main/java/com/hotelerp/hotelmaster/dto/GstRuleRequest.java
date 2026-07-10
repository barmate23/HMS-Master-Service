package com.hotelerp.hotelmaster.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GstRuleRequest {

    @NotBlank(message = "Service category is required")
    private String serviceCategory;

    @NotBlank(message = "HSN/SAC code is required")
    private String hsnSacCode;

    @NotNull(message = "CGST rate is required")
    @DecimalMin(value = "0.0", message = "CGST rate must be >= 0")
    @Digits(integer = 3, fraction = 2, message = "CGST rate must have at most 3 integer digits and 2 decimal places")
    private BigDecimal cgstRate;

    @NotNull(message = "SGST rate is required")
    @DecimalMin(value = "0.0", message = "SGST rate must be >= 0")
    @Digits(integer = 3, fraction = 2, message = "SGST rate must have at most 3 integer digits and 2 decimal places")
    private BigDecimal sgstRate;

    /** Optional description */
    private String description;
}
