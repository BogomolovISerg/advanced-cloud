package com.geekbrains.coreservice.services;

import com.geekbrains.coreservice.entities.Order;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayPalService {

    private final OrderService orderService;

    @Transactional
    public OrderRequest createOrderRequest(Long orderId){

        Order order = orderService.findById(orderId);

        if(order.getpaid()){
            return null;
        }

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("Spring Web Market")
                .landingPage("BILLING")
                .shippingPreference("SET_PROVIDED_ADDRESS");

        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString())
                .description("Spring Web Market Order")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("RUB").value(String.valueOf(order.getTotalPrice()))
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("RUB").value(String.valueOf(order.getTotalPrice())))))
               .items(order.getItems().stream()
                .map(orderItem -> new Item()
                    .name(orderItem.getProduct().getTitle())
                        .unitAmount(new Money().currencyCode("RUB").value(String.valueOf(orderItem.getPrice())))
                        .quantity(String.valueOf(orderItem.getQuantity())))
                       .collect(Collectors.toList()))
                .shippingDetail(new ShippingDetail().name(new Name().fullName(order.getUsername()))
                        .addressPortable(new AddressPortable().addressLine1(order.getAddressLine1()).addressLine2(order.getAddressLine2())
                                .adminArea2(order.getAdminArea2()).adminArea1(order.getAdminArea2()).postalCode(order.getPostalCode()).countryCode(order.getCountryCode())));

        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);

        return orderRequest;
    }
}
