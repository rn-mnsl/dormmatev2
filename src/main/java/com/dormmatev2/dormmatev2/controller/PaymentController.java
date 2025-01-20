package com.dormmatev2.dormmatev2.controller;

import com.dormmatev2.dormmatev2.model.Payment;
import com.dormmatev2.dormmatev2.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    @PostMapping("/proof")
    public ResponseEntity<Payment> uploadProof(@RequestParam("proofOfPayment") MultipartFile proofOfPayment, @RequestParam Long tenantId) {
        if (proofOfPayment.isEmpty()) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            String fileName = UUID.randomUUID().toString() + "_" + proofOfPayment.getOriginalFilename();
          byte[] bytes = proofOfPayment.getBytes();
            Path path = Paths.get("upload/" + fileName);
           Files.write(path, bytes); // Save the file

            Payment updatedPayment = paymentService.addProofOfPayment(tenantId, fileName);
             return new ResponseEntity<>(updatedPayment, HttpStatus.CREATED);
       } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}