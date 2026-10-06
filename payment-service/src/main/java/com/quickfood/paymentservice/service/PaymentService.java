package com.quickfood.paymentservice.service;

import com.quickfood.paymentservice.model.Payment;
import com.quickfood.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment create(Payment payment) { return paymentRepository.save(payment); }
    public Optional<Payment> findById(Long id) { return paymentRepository.findById(id); }
}