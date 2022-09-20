package com.geekbrains.coreservice.controllers;

import com.geekbrains.coreservice.converters.OrderConverter;
import com.geekbrains.coreservice.dto.OrderDetailsDto;
import com.geekbrains.coreservice.dto.OrderDto;
import com.geekbrains.coreservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
        private final OrderService orderService;
        private final OrderConverter orderConverter;


    @GetMapping
    public List<OrderDto> getCurrenUrders(@RequestHeader String username){
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }


}
