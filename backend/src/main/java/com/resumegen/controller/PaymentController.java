package com.resumegen.controller;

import com.resumegen.service.EmailService;
import com.resumegen.service.StripeService;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final StripeService stripeService;
    private final EmailService emailService;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    public PaymentController(StripeService stripeService, EmailService emailService) {
        this.stripeService = stripeService;
        this.emailService = emailService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is required"));
        }

        try {
            com.stripe.model.checkout.Session session = stripeService.createCheckoutSession(email);
            Map<String, String> response = new HashMap<>();
            response.put("sessionId", session.getId());
            response.put("url", session.getUrl());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
            if (session != null && session.getCustomerEmail() != null) {
                String email = session.getCustomerEmail();
                long amountTotal = session.getAmountTotal() != null ? session.getAmountTotal() : 999;
                String amount = String.format("$%.2f", amountTotal / 100.0);
                String receiptUrl = session.getUrl();
                String sessionId = session.getId();

                stripeService.markCustomerAsPaid(email, amount, sessionId);
                emailService.sendPaymentReceipt(email, amount, receiptUrl, sessionId);
            }
        }

        return ResponseEntity.ok("Received");
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> checkPaymentStatus(@RequestParam String email) {
        boolean isPaid = stripeService.isCustomerPaid(email);
        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("isPaid", isPaid);
        return ResponseEntity.ok(response);
    }
}
