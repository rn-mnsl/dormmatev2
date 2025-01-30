package com.dormmatev2.dormmatev2.controller;

import com.dormmatev2.dormmatev2.model.Payment;
import com.dormmatev2.dormmatev2.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestParam Long tenantId, @RequestBody Payment payment) {
        try {
           Payment savedPayment = paymentService.savePayment(payment, tenantId);
            return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
     public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.findAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.findPaymentById(id);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

     @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<Payment>> getPaymentsByTenantId(@PathVariable Long tenantId) {
          try {
              List<Payment> payments = paymentService.getPaymentsByTenantId(tenantId);
             return new ResponseEntity<>(payments, HttpStatus.OK);
          } catch (IllegalArgumentException e) {
               return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable Long id) {
          try {
            paymentService.deletePayment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPayment(
            @RequestParam("proofOfPayment") MultipartFile file,
            @RequestParam("tenantId") Long tenantId,
            @RequestParam("amount") Double amount,
            @RequestParam("paymentDate") String paymentDate,
            @RequestParam("dueDate") String dueDate) {
        try {
            Payment payment = paymentService.createPayment(file, tenantId, amount, paymentDate, dueDate);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long id,
            @RequestBody Payment payment) throws Exception {
        try {
            Payment updatedPayment = paymentService.updatePayment(id, payment);
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    
}