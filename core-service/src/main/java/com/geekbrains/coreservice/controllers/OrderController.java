package com.geekbrains.coreservice.controllers;

import com.geekbrains.coreservice.converters.OrderConverter;
import com.geekbrains.coreservice.dto.OrderDto;
import com.geekbrains.coreservice.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name="OrderController", description="OrderController")
public class OrderController {
        @Schema(description = "orderService")
        private final OrderService orderService;
        @Schema(description = "orderConverter")
        private final OrderConverter orderConverter;


    @GetMapping
    @Operation(summary = "getCurrenUrders")
    public List<OrderDto> getCurrenUrders(@RequestHeader String username){
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id){
        return orderConverter.entityToDto(orderService.findById(id));
    }

}
