package com.geekbrains.coreservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String username;
    private List<OrderItemDto> itemDtoList;
    private Integer totalPrice;
    private String addressLine1;
    private String addressLine2;
    private String adminArea1;
    private String adminArea2;
    private String postalCode;
    private String countryCode;
    private boolean paid;
    private String phone;
}
