package com.dormmatev2.dormmatev2;

import com.dormmatev2.dormmatev2.model.Announcement;
import com.dormmatev2.dormmatev2.model.MaintenanceRequest;
import com.dormmatev2.dormmatev2.model.Payment;
import com.dormmatev2.dormmatev2.model.Tenant;
import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.model.User;
import com.dormmatev2.dormmatev2.repositories.AnnouncementRepository;
import com.dormmatev2.dormmatev2.repositories.MaintenanceRequestRepository;
import com.dormmatev2.dormmatev2.repositories.PaymentRepository;
import com.dormmatev2.dormmatev2.repositories.TenantRepository;
import com.dormmatev2.dormmatev2.repositories.UnitRepository;
import com.dormmatev2.dormmatev2.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class DormmateApplication {

    public static void main(String[] args) {
        SpringApplication.run(DormmateApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository, UnitRepository unitRepository, TenantRepository tenantRepository, PaymentRepository paymentRepository, AnnouncementRepository announcementRepository, MaintenanceRequestRepository maintenanceRequestRepository) {
    return args -> {
        // --- Test User Entity ---
        System.out.println("----- Testing User Entity -----");
        User adminUser = new User();
        adminUser.setUsername("adminUser");
        adminUser.setPassword("adminPass"); // Hash this in production!
        adminUser.setEmail("admin@example.com");
        adminUser.setRole("admin");
        userRepository.save(adminUser);

        User tenantUser = new User();
        tenantUser.setUsername("tenantUser");
        tenantUser.setPassword("tenantPass"); // Hash this in production!
        tenantUser.setEmail("tenant@example.com");
        tenantUser.setRole("tenant");
        userRepository.save(tenantUser);

        User foundUser = userRepository.findByUsername("adminUser");
        System.out.println("Found User: " + foundUser.getEmail());

        // --- Test Unit Entity ---
        System.out.println("----- Testing Unit Entity -----");
        Unit unit1 = new Unit();
        unit1.setUnitName("Unit A1");
        unit1.setDescription("First unit in Building A");
        unitRepository.save(unit1);

        Optional<Unit> foundUnit = unitRepository.findById(unit1.getUnitId());
        if (foundUnit.isPresent()) {
            System.out.println("Found Unit: " + foundUnit.get().getUnitName());
        } else {
            System.out.println("Unit not found.");
        }

        // --- Test Tenant Entity ---
        System.out.println("----- Testing Tenant Entity -----");
        Tenant tenant = new Tenant();
        tenant.setUser(tenantUser);
        tenant.setUnit(unit1);
        tenant.setName("Test Tenant");
        tenant.setContactNumber("1234567890");
        tenant.setMoveInDate(LocalDate.now());
        tenantRepository.save(tenant);

        Optional<Tenant> foundTenant = tenantRepository.findById(tenant.getTenantId());
        if (foundTenant.isPresent()) {
            System.out.println("Found Tenant: " + foundTenant.get().getName());
        } else {
            System.out.println("Tenant not found.");
        }

        // --- Test Payment Entity ---
        System.out.println("----- Testing Payment Entity -----");
        Payment payment = new Payment();
        payment.setTenant(tenant);
        payment.setAmount(new BigDecimal("1000.00"));
        payment.setPaymentDate(LocalDate.now());
        payment.setDueDate(LocalDate.now().plusMonths(1));
        payment.setStatus("paid");
        payment.setProofOfPayment("sample_proof.jpg");
        paymentRepository.save(payment);

        Optional<Payment> foundPayment = paymentRepository.findById(payment.getPaymentId());
        if (foundPayment.isPresent()) {
            System.out.println("Found Payment: $" + foundPayment.get().getAmount());
        } else {
            System.out.println("Payment not found.");
        }

        // --- Test Announcement Entity ---
        System.out.println("----- Testing Announcement Entity -----");
        Announcement announcement = new Announcement();
        announcement.setPostedBy(adminUser);
        announcement.setTitle("Test Announcement");
        announcement.setContent("This is a test announcement.");
        announcement.setPostDate(LocalDateTime.now());
        announcementRepository.save(announcement);

        Optional<Announcement> foundAnnouncement = announcementRepository.findById(announcement.getAnnouncementId());
        if (foundAnnouncement.isPresent()) {
            System.out.println("Found Announcement: " + foundAnnouncement.get().getTitle());
        } else {
            System.out.println("Announcement not found.");
        }

        // --- Test MaintenanceRequest Entity ---
        System.out.println("----- Testing MaintenanceRequest Entity -----");
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
        maintenanceRequest.setTenant(tenant);
        maintenanceRequest.setUnit("unit1"); // changed to string
        maintenanceRequest.setDescription("Test maintenance request.");
        maintenanceRequest.setRequestDate(LocalDateTime.now());
        maintenanceRequest.setStatus("open");
        maintenanceRequestRepository.save(maintenanceRequest);

        Optional<MaintenanceRequest> foundMaintenanceRequest = maintenanceRequestRepository.findById(maintenanceRequest.getRequestId());
        if (foundMaintenanceRequest.isPresent()) {
            System.out.println("Found Maintenance Request: " + foundMaintenanceRequest.get().getDescription());
        } else {
            System.out.println("Maintenance Request not found.");
        }
    };
}
}