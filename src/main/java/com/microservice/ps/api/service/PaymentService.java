package com.microservice.ps.api.service;

import com.microservice.ps.api.entity.Payment;
import com.microservice.ps.api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment doPayment(Payment payment){
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    public Payment findPaymentHistoryByOrderId(int orderId) {
        Payment payment=paymentRepository.findByOrderId(orderId);
     //   logger.info("paymentService findPaymentHistoryByOrderId : {}",new ObjectMapper().writeValueAsString(payment));
        return payment ;
    }

    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }

    public String paymentProcessing(){
        return new Random().nextBoolean()?"Success":"false";
    }
}
