package com.microservice.ps.api.controller;

import com.microservice.ps.api.entity.Payment;
import com.microservice.ps.api.repository.PaymentRepository;
import com.microservice.ps.api.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    public static final String PAYMENT_SERVICE="paymentService";

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment){
       return paymentService.doPayment(payment);
    }

    @GetMapping("/{orderId}")
    public Payment findPaymentHistoryByOrderId(@PathVariable int orderId){
        return paymentService.findPaymentHistoryByOrderId(orderId);
    }

    @GetMapping("/allPayment")
    @CircuitBreaker(name=PAYMENT_SERVICE,fallbackMethod = "getAllPaymentFallback")
    public List<Payment> getAllPayment(){
        return paymentService.getAllPayments();
    }

    public List<Payment> getAllPaymentFallback(Exception e ){
        return Stream.of(
                new Payment(1,"SUCCESS","1234ACC",1,100),
                new Payment(2,"SUCCESS","3435FGG",2,200),
                new Payment(3,"SUCCESS","DS43GFG",3,300))
                        .collect(Collectors.toList());
    }


}
