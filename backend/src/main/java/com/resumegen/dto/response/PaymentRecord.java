package com.resumegen.dto.response;

import java.time.Instant;

public class PaymentRecord {
    private String email;
    private Instant paidAt;
    private String amount;
    private String sessionId;

    public PaymentRecord(String email, Instant paidAt, String amount, String sessionId) {
        this.email = email;
        this.paidAt = paidAt;
        this.amount = amount;
        this.sessionId = sessionId;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Instant getPaidAt() { return paidAt; }
    public void setPaidAt(Instant paidAt) { this.paidAt = paidAt; }
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
}
