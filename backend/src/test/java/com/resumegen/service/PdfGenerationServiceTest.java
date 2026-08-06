package com.resumegen.service;

import com.resumegen.dto.request.*;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PdfGenerationServiceTest {

    private final PdfGenerationService service = new PdfGenerationService();

    @Test
    void testAllTemplates() throws Exception {
        String[] templates = {"modern", "classic", "professional", "minimal", "executive", "creative", "academic"};

        for (String template : templates) {
            ResumeRequest request = createSampleRequest(template);
            byte[] pdf = service.generatePdf(request);

            assertNotNull(pdf, "PDF should not be null for template: " + template);
            assertTrue(pdf.length > 100, "PDF should have content for template: " + template);

            String path = "/tmp/test_" + template + ".pdf";
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(pdf);
            }
            System.out.println(template + " template: " + pdf.length + " bytes -> " + path);
        }
    }

    private ResumeRequest createSampleRequest(String template) {
        PersonalInfoRequest personal = new PersonalInfoRequest();
        personal.setFullName("John Doe");
        personal.setJobTitle("Senior Software Engineer");
        personal.setEmail("john@example.com");
        personal.setPhone("+1 234 567 8900");
        personal.setCity("New York");
        personal.setCountry("USA");
        personal.setLinkedIn("linkedin.com/in/johndoe");
        personal.setWebsite("johndoe.dev");
        personal.setTwitter("@johndoe");
        personal.setGithub("johndoe");
        personal.setOrcid("0000-0000-0000-0000");

        ExperienceRequest exp = new ExperienceRequest();
        exp.setCompany("Tech Corp");
        exp.setPosition("Software Engineer");
        exp.setStartDate("Jan 2020");
        exp.setEndDate("Dec 2023");
        exp.setDescription("Led a team of 5 developers building microservices.");
        exp.setAchievements(List.of("Reduced API latency by 40%", "Migrated monolith to Kubernetes"));

        EducationRequest edu = new EducationRequest();
        edu.setInstitution("MIT");
        edu.setDegree("Bachelor of Science");
        edu.setFieldOfStudy("Computer Science");
        edu.setStartDate("2015");
        edu.setEndDate("2019");
        edu.setDescription("Graduated with honors, GPA 3.9");

        ResumeRequest request = new ResumeRequest();
        request.setTemplate(template);
        request.setPersonalInfo(personal);
        request.setSummary("Passionate software engineer with 5+ years of experience building scalable web applications.");
        request.setExperience(List.of(exp));
        request.setEducation(List.of(edu));
        request.setSkills(List.of("Java", "Spring Boot", "Vue.js", "Docker", "AWS", "PostgreSQL"));
        request.setLanguages(List.of("English", "Spanish"));

        // Academic-specific fields
        if (template.equals("academic")) {
            request.setLifePhilosophy("Always be learning and building things that matter.");

            ProjectRequest proj = new ProjectRequest();
            proj.setName("AI Research Initiative");
            proj.setFundingAgency("NSF Grant #12345");
            proj.setDuration("Jan 2021 - Dec 2023");
            proj.setDescription("Developed novel deep learning architectures for natural language processing.");
            request.setProjects(List.of(proj));

            ProudOfRequest po = new ProudOfRequest();
            po.setTitle("Published 3 Papers at NeurIPS");
            po.setDetails("First-author on all three publications covering transformer optimization.");
            request.setProudOf(List.of(po));

            PublicationRequest pub = new PublicationRequest();
            pub.setBooks(List.of("J. Doe, Deep Learning Fundamentals. Academic Press, 2023."));
            pub.setJournalArticles(List.of(
                "J. Doe and A. Smith, Efficient Transformers, Journal of ML, vol. 15, 2022.",
                "J. Doe, Attention Mechanisms Survey, AI Review, vol. 8, 2021."
            ));
            pub.setConferenceProceedings(List.of(
                "J. Doe et al., Fast Attention, Proceedings of NeurIPS, 2022."
            ));
            request.setPublications(pub);

            RefereeRequest ref = new RefereeRequest();
            ref.setName("Prof. Alan Turing");
            ref.setInstitute("Cambridge University");
            ref.setEmail("turing@cam.ac.uk");
            ref.setAddressLine1("Computer Science Department");
            ref.setAddressLine2("Cambridge CB3 0FD, UK");
            request.setReferees(List.of(ref));
        }

        return request;
    }
}
