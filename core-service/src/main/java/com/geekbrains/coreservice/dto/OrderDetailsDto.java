package com.geekbrains.coreservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailsDto {
    private String addressLine1;
    private String addressLine2;
    private String adminArea1;
    private String adminArea2;
    private String postalCode;
    private String countryCode;
    private boolean paid;
    private String phone;
}
