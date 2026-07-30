package com.resumegen.controller;

import com.resumegen.dto.response.PaymentRecord;
import com.resumegen.service.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final StripeService stripeService;

    public AdminController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCustomers", stripeService.getTotalPaidCustomers());
        stats.put("totalRevenue", stripeService.getTotalRevenue());
        stats.put("todaysCustomers", stripeService.getTodaysCustomers());
        stats.put("todaysRevenue", stripeService.getTodaysRevenue());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentRecord>> getPayments() {
        return ResponseEntity.ok(stripeService.getAllPayments());
    }
}
