package com.resumegen.service;

import com.resumegen.dto.response.PaymentRecord;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StripeService {

    @Value("${stripe.price-id}")
    private String priceId;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    // Store paid customer records in memory (in production, use a database)
    private final Map<String, PaymentRecord> paidCustomers = new ConcurrentHashMap<>();

    public Session createCheckoutSession(String customerEmail) throws Exception {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(frontendUrl + "/payment/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendUrl + "/payment/cancel")
                .setCustomerEmail(customerEmail)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPrice(priceId)
                                .build()
                )
                .build();

        return Session.create(params);
    }

    public void markCustomerAsPaid(String email, String amount, String sessionId) {
        if (email != null && !email.isBlank()) {
            String normalized = email.toLowerCase().trim();
            paidCustomers.put(normalized, new PaymentRecord(normalized, Instant.now(), amount, sessionId));
        }
    }

    public void markCustomerAsPaid(String email) {
        markCustomerAsPaid(email, "$9.99", "unknown");
    }

    public boolean isCustomerPaid(String email) {
        return email != null && paidCustomers.containsKey(email.toLowerCase().trim());
    }

    public void removeCustomer(String email) {
        if (email != null) {
            paidCustomers.remove(email.toLowerCase().trim());
        }
    }

    public int getTotalPaidCustomers() {
        return paidCustomers.size();
    }

    public String getTotalRevenue() {
        int count = paidCustomers.size();
        return String.format("$%.2f", count * 9.99);
    }

    public int getTodaysCustomers() {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        return (int) paidCustomers.values().stream()
                .filter(r -> r.getPaidAt() != null && r.getPaidAt().atZone(ZoneId.systemDefault()).toLocalDate().equals(today))
                .count();
    }

    public String getTodaysRevenue() {
        int count = getTodaysCustomers();
        return String.format("$%.2f", count * 9.99);
    }

    public List<PaymentRecord> getAllPayments() {
        List<PaymentRecord> list = new ArrayList<>(paidCustomers.values());
        list.sort((a, b) -> b.getPaidAt().compareTo(a.getPaidAt()));
        return list;
    }
}
