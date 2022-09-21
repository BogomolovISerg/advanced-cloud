package com.geekbrains.coreservice.controllers;

import com.geekbrains.coreservice.services.OrderService;
import com.geekbrains.coreservice.services.PayPalService;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Order;
import com.paypal.orders.OrderCaptureRequest;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.http.HttpResponse;

@Controller
@RequestMapping("/api/v1/paypal")
@RequiredArgsConstructor
public class PayPalController {

    private final PayPalHttpClient payPalClient;
    private final PayPalService payPalService;
    private final OrderService orderService;

    @PostMapping("/create/{orderId}")
    public ResponseEntity<?> createOrder(@PathVariable Long orderId) throws IOException {
            OrdersCreateRequest request = new OrdersCreateRequest();
            request.prefer("return=representation");
            request.requestBody(payPalService.createOrderRequest(orderId));
            HttpResponse<Order> response = payPalClient.execute(request);
            return new ResponseEntity<>(response.result().id(), HttpStatus.valueOf(response.statusCode()));
    }

    @PostMapping("/capture/payPalId")
    public ResponseEntity<?> captureOrder(@PathVariable String payPalId) throws IOException{
        OrderCaptureRequest request = new OrderCaptureRequest(payPalId);
        request.requestBody(new OrderRequest());

        HttpResponse<Order> response = payPalClient.execute(request);
        Order payPalOrder = request.result();

        if("COMPLETED".equals(payPalOrder.status())){
            long orderId = Long.parseLong(payPalOrder.purchaseUnits().get(0).referenceID());
            com.geekbrains.coreservice.entities.Order orderGb = orderService.findById(orderId);
            orderGb.setPaid(true);
            return new ResponseEntity<>("Order completed", HttpStatus.valueOf(response.statusCode()));
        }else if("PENDING".equals(payPalOrder.status())){
            return new ResponseEntity<>("PayPal проверяет транзакцию", HttpStatus.valueOf(response.statusCode()));
        }else if("UNCLAIMED".equals(payPalOrder.status())){
            return new ResponseEntity<>("Получатель не принял или не получил ваш платеж", HttpStatus.valueOf(response.statusCode()));
        }else if("DENIED".equals(payPalOrder.status())){
            return new ResponseEntity<>("Платеж отклонен", HttpStatus.valueOf(response.statusCode()));
        }
        return new ResponseEntity<>(payPalOrder,HttpStatus.valueOf(response.statusCode()));
    }
}
