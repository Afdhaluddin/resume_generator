package com.resumegen.controller;

import com.resumegen.dto.request.ResumeRequest;
import com.resumegen.dto.response.UsageResponse;
import com.resumegen.exception.LimitExceededException;
import com.resumegen.exception.ResumeNotFoundException;
import com.resumegen.service.IpLimitService;
import com.resumegen.service.PdfGenerationService;
import com.resumegen.service.ResumeCacheService;
import com.resumegen.service.StripeService;
import com.resumegen.util.IpAddressUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final IpLimitService ipLimitService;
    private final PdfGenerationService pdfGenerationService;
    private final ResumeCacheService resumeCacheService;
    private final StripeService stripeService;

    public ResumeController(IpLimitService ipLimitService,
                            PdfGenerationService pdfGenerationService,
                            ResumeCacheService resumeCacheService,
                            StripeService stripeService) {
        this.ipLimitService = ipLimitService;
        this.pdfGenerationService = pdfGenerationService;
        this.resumeCacheService = resumeCacheService;
        this.stripeService = stripeService;
    }

    private boolean isPaid(HttpServletRequest request) {
        String email = request.getHeader("X-Customer-Email");
        return email != null && stripeService.isCustomerPaid(email);
    }

    @GetMapping("/check-limit")
    public ResponseEntity<UsageResponse> checkLimit(HttpServletRequest request) {
        String ipAddress = IpAddressUtil.getClientIp(request);
        boolean paid = isPaid(request);
        return ResponseEntity.ok(new UsageResponse(
                ipLimitService.canGenerate(ipAddress, paid),
                ipLimitService.getRemaining(ipAddress, paid),
                ipLimitService.getUsage(ipAddress),
                paid ? Integer.MAX_VALUE : 2
        ));
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateResume(
            @RequestBody ResumeRequest resumeRequest,
            HttpServletRequest request) throws Exception {

        String ipAddress = IpAddressUtil.getClientIp(request);
        boolean paid = isPaid(request);

        if (!ipLimitService.canGenerate(ipAddress, paid)) {
            throw new LimitExceededException("You have used your 2 free resume generations. Please upgrade to continue.");
        }

        String resumeId = resumeCacheService.saveResume(resumeRequest);
        byte[] pdfBytes = pdfGenerationService.generatePdf(resumeRequest);
        ipLimitService.recordGeneration(ipAddress);
        int remaining = ipLimitService.getRemaining(ipAddress, paid);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "resume.pdf");
        headers.add("X-Resume-Id", resumeId);
        headers.add("X-Remaining", String.valueOf(remaining));

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/preview")
    public ResponseEntity<byte[]> previewResume(@RequestBody ResumeRequest resumeRequest) throws Exception {
        String resumeId = resumeCacheService.saveResume(resumeRequest);
        byte[] pdfBytes = pdfGenerationService.generatePdf(resumeRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("X-Resume-Id", resumeId);

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeRequest> getResume(@PathVariable String id) {
        ResumeRequest resume = resumeCacheService.getResume(id);
        if (resume == null) {
            throw new ResumeNotFoundException("Resume not found: " + id);
        }
        return ResponseEntity.ok(resume);
    }

    @PutMapping("/{id}")
    public ResponseEntity<byte[]> updateResume(
            @PathVariable String id,
            @RequestBody ResumeRequest resumeRequest,
            HttpServletRequest request) throws Exception {

        if (!resumeCacheService.exists(id)) {
            throw new ResumeNotFoundException("Resume not found: " + id);
        }

        resumeCacheService.updateResume(id, resumeRequest);

        String ipAddress = IpAddressUtil.getClientIp(request);
        boolean paid = isPaid(request);
        byte[] pdfBytes = pdfGenerationService.generatePdf(resumeRequest);
        int remaining = ipLimitService.getRemaining(ipAddress, paid);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "resume.pdf");
        headers.add("X-Resume-Id", id);
        headers.add("X-Remaining", String.valueOf(remaining));

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
