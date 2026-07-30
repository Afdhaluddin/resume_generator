package com.resumegen.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String fromEmail;

    @Value("${app.name:ResumeForge}")
    private String appName;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPaymentReceipt(String toEmail, String amount, String receiptUrl, String sessionId) {
        if (fromEmail == null || fromEmail.isBlank()) {
            System.out.println("[Email] SMTP not configured. Would send receipt to: " + toEmail);
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, appName);
            helper.setTo(toEmail);
            helper.setSubject("Your " + appName + " Unlimited Receipt — Thank You!");
            helper.setText(buildReceiptHtml(toEmail, amount, receiptUrl, sessionId), true);

            mailSender.send(message);
            System.out.println("[Email] Receipt sent to: " + toEmail);
        } catch (MessagingException e) {
            System.err.println("[Email] Failed to send receipt: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[Email] Unexpected error: " + e.getMessage());
        }
    }

    private String buildReceiptHtml(String email, String amount, String receiptUrl, String sessionId) {
        String now = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a")
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background: #f8fafc; margin: 0; padding: 20px; }
                    .container { max-width: 600px; margin: 0 auto; background: #fff; border-radius: 16px; overflow: hidden; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1); }
                    .header { background: linear-gradient(135deg, #2563eb, #1d4ed8); padding: 40px 30px; text-align: center; }
                    .header h1 { color: #fff; margin: 0; font-size: 24px; }
                    .header p { color: #bfdbfe; margin: 8px 0 0; font-size: 14px; }
                    .content { padding: 30px; }
                    .receipt-box { background: #f0fdf4; border: 1px solid #bbf7d0; border-radius: 12px; padding: 20px; margin: 20px 0; }
                    .receipt-box h2 { color: #166534; margin: 0 0 12px; font-size: 18px; }
                    .detail { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid #e2e8f0; }
                    .detail:last-child { border-bottom: none; }
                    .label { color: #64748b; font-size: 14px; }
                    .value { color: #1e293b; font-weight: 600; font-size: 14px; }
                    .total { font-size: 28px; font-weight: 700; color: #166534; text-align: center; margin: 20px 0; }
                    .btn { display: inline-block; background: #2563eb; color: #fff; padding: 12px 24px; border-radius: 8px; text-decoration: none; font-weight: 600; margin: 10px 0; }
                    .footer { padding: 20px 30px; background: #f8fafc; text-align: center; color: #94a3b8; font-size: 12px; }
                    .unlimited-badge { display: inline-flex; align-items: center; gap: 6px; background: #fef3c7; color: #92400e; padding: 6px 14px; border-radius: 20px; font-size: 13px; font-weight: 600; margin: 10px 0; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Payment Confirmed!</h1>
                        <p>Your ResumeForge Unlimited access is now active</p>
                    </div>
                    <div class="content">
                        <div style="text-align: center;">
                            <div class="unlimited-badge">&#9989; Unlimited Access Activated</div>
                        </div>

                        <div class="receipt-box">
                            <h2>Receipt</h2>
                            <div class="detail">
                                <span class="label">Plan</span>
                                <span class="value">ResumeForge Unlimited</span>
                            </div>
                            <div class="detail">
                                <span class="label">Email</span>
                                <span class="value">%s</span>
                            </div>
                            <div class="detail">
                                <span class="label">Date</span>
                                <span class="value">%s</span>
                            </div>
                            <div class="detail">
                                <span class="label">Payment Method</span>
                                <span class="value">Stripe</span>
                            </div>
                            <div class="detail">
                                <span class="label">Transaction ID</span>
                                <span class="value" style="font-size: 11px;">%s</span>
                            </div>
                        </div>

                        <div class="total">%s</div>

                        <p style="text-align: center; color: #475569; line-height: 1.6;">
                            You now have unlimited resume generation. Create as many professional resumes as you need, edit them anytime, and download in PDF format.
                        </p>

                        <div style="text-align: center; margin: 24px 0;">
                            <a href="%s" class="btn">View Stripe Receipt</a>
                        </div>

                        <p style="text-align: center; color: #64748b; font-size: 13px; margin-top: 20px;">
                            Questions? Reply to this email and we'll help you out.
                        </p>
                    </div>
                    <div class="footer">
                        <p>ResumeForge &middot; Build Professional Resumes Online</p>
                        <p>This is an automated receipt. Please keep it for your records.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(email, now, sessionId, amount, receiptUrl);
    }
}
