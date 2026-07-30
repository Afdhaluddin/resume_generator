package com.resumegen.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.resumegen.dto.request.*;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfGenerationService {

    public byte[] generatePdf(ResumeRequest request) throws Exception {
        String template = request.getTemplate();
        return switch (template) {
            case "classic" -> generateClassicTemplate(request);
            case "professional" -> generateProfessionalTemplate(request);
            default -> generateModernTemplate(request);
        };
    }

    private byte[] generateModernTemplate(ResumeRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        Color primaryColor = new Color(41, 98, 255);
        Color darkGray = new Color(51, 51, 51);
        Color lightGray = new Color(120, 120, 120);

        // Header with colored background
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle headerRect = new Rectangle(0, PageSize.A4.getHeight() - 80, PageSize.A4.getWidth(), PageSize.A4.getHeight());
        headerRect.setBackgroundColor(primaryColor);
        canvas.rectangle(headerRect);

        // Name
        Font nameFont = new Font(Font.HELVETICA, 26, Font.BOLD, Color.WHITE);
        Paragraph name = new Paragraph(request.getPersonalInfo().getFullName(), nameFont);
        name.setAlignment(Element.ALIGN_CENTER);
        name.setSpacingBefore(20);
        document.add(name);

        // Job title
        Font titleFont = new Font(Font.HELVETICA, 14, Font.NORMAL, new Color(220, 220, 220));
        Paragraph jobTitle = new Paragraph(request.getPersonalInfo().getJobTitle(), titleFont);
        jobTitle.setAlignment(Element.ALIGN_CENTER);
        jobTitle.setSpacingAfter(30);
        document.add(jobTitle);

        // Contact info bar
        Font contactFont = new Font(Font.HELVETICA, 9, Font.NORMAL, darkGray);
        StringBuilder contactInfo = new StringBuilder();
        if (request.getPersonalInfo().getEmail() != null) contactInfo.append(request.getPersonalInfo().getEmail()).append("  |  ");
        if (request.getPersonalInfo().getPhone() != null) contactInfo.append(request.getPersonalInfo().getPhone()).append("  |  ");
        if (request.getPersonalInfo().getCity() != null) contactInfo.append(request.getPersonalInfo().getCity());
        if (request.getPersonalInfo().getLinkedIn() != null) contactInfo.append("  |  ").append(request.getPersonalInfo().getLinkedIn());

        Paragraph contacts = new Paragraph(contactInfo.toString(), contactFont);
        contacts.setAlignment(Element.ALIGN_CENTER);
        contacts.setSpacingAfter(20);
        document.add(contacts);

        // Summary
        if (request.getSummary() != null && !request.getSummary().isEmpty()) {
            addSectionHeader(document, "PROFESSIONAL SUMMARY", primaryColor);
            Font summaryFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkGray);
            Paragraph summary = new Paragraph(request.getSummary(), summaryFont);
            summary.setSpacingAfter(15);
            document.add(summary);
        }

        // Experience
        if (request.getExperience() != null && !request.getExperience().isEmpty()) {
            addSectionHeader(document, "WORK EXPERIENCE", primaryColor);
            for (ExperienceRequest exp : request.getExperience()) {
                addExperienceEntry(document, exp, darkGray, lightGray);
            }
        }

        // Education
        if (request.getEducation() != null && !request.getEducation().isEmpty()) {
            addSectionHeader(document, "EDUCATION", primaryColor);
            for (EducationRequest edu : request.getEducation()) {
                addEducationEntry(document, edu, darkGray, lightGray);
            }
        }

        // Skills
        if (request.getSkills() != null && !request.getSkills().isEmpty()) {
            addSectionHeader(document, "SKILLS", primaryColor);
            Font skillFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkGray);
            Paragraph skills = new Paragraph(String.join("  •  ", request.getSkills()), skillFont);
            skills.setSpacingAfter(15);
            document.add(skills);
        }

        // Languages
        if (request.getLanguages() != null && !request.getLanguages().isEmpty()) {
            addSectionHeader(document, "LANGUAGES", primaryColor);
            Font langFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkGray);
            Paragraph langs = new Paragraph(String.join("  •  ", request.getLanguages()), langFont);
            document.add(langs);
        }

        document.close();
        return baos.toByteArray();
    }

    private byte[] generateClassicTemplate(ResumeRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 60, 60, 60, 60);
        PdfWriter.getInstance(document, baos);
        document.open();

        Color darkColor = new Color(30, 30, 30);
        Color accentColor = new Color(139, 69, 19);

        // Name centered
        Font nameFont = new Font(Font.TIMES_ROMAN, 28, Font.BOLD, darkColor);
        Paragraph name = new Paragraph(request.getPersonalInfo().getFullName(), nameFont);
        name.setAlignment(Element.ALIGN_CENTER);
        document.add(name);

        // Job title
        Font titleFont = new Font(Font.TIMES_ROMAN, 12, Font.ITALIC, accentColor);
        Paragraph jobTitle = new Paragraph(request.getPersonalInfo().getJobTitle(), titleFont);
        jobTitle.setAlignment(Element.ALIGN_CENTER);
        jobTitle.setSpacingAfter(10);
        document.add(jobTitle);

        // Contact divider line
        LineSeparator line = new LineSeparator();
        line.setLineColor(accentColor);
        line.setPercentage(100);
        document.add(new Chunk(line));

        // Contact info
        Font contactFont = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, darkColor);
        StringBuilder contact = new StringBuilder();
        if (request.getPersonalInfo().getEmail() != null) contact.append(request.getPersonalInfo().getEmail()).append("  |  ");
        if (request.getPersonalInfo().getPhone() != null) contact.append(request.getPersonalInfo().getPhone()).append("  |  ");
        if (request.getPersonalInfo().getAddress() != null) contact.append(request.getPersonalInfo().getAddress());
        Paragraph contacts = new Paragraph(contact.toString(), contactFont);
        contacts.setAlignment(Element.ALIGN_CENTER);
        contacts.setSpacingAfter(15);
        document.add(contacts);

        // Summary
        if (request.getSummary() != null && !request.getSummary().isEmpty()) {
            addClassicSection(document, "SUMMARY", accentColor);
            Font bodyFont = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, darkColor);
            document.add(new Paragraph(request.getSummary(), bodyFont));
            document.add(Chunk.NEWLINE);
        }

        // Experience
        if (request.getExperience() != null && !request.getExperience().isEmpty()) {
            addClassicSection(document, "EXPERIENCE", accentColor);
            for (ExperienceRequest exp : request.getExperience()) {
                Font companyFont = new Font(Font.TIMES_ROMAN, 11, Font.BOLD, darkColor);
                Font positionFont = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, accentColor);
                Font bodyFont = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, darkColor);

                Paragraph company = new Paragraph(exp.getCompany(), companyFont);
                document.add(company);

                Paragraph position = new Paragraph(exp.getPosition() + "  |  " + exp.getStartDate() + " - " + (exp.getEndDate() != null ? exp.getEndDate() : "Present"), positionFont);
                document.add(position);

                if (exp.getDescription() != null) {
                    document.add(new Paragraph(exp.getDescription(), bodyFont));
                }
                if (exp.getAchievements() != null) {
                    for (String achievement : exp.getAchievements()) {
                        document.add(new Paragraph("• " + achievement, bodyFont));
                    }
                }
                document.add(Chunk.NEWLINE);
            }
        }

        // Education
        if (request.getEducation() != null && !request.getEducation().isEmpty()) {
            addClassicSection(document, "EDUCATION", accentColor);
            for (EducationRequest edu : request.getEducation()) {
                Font instFont = new Font(Font.TIMES_ROMAN, 11, Font.BOLD, darkColor);
                Font detailFont = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, darkColor);

                document.add(new Paragraph(edu.getInstitution(), instFont));
                document.add(new Paragraph(edu.getDegree() + " in " + edu.getFieldOfStudy() + "  |  " + edu.getStartDate() + " - " + edu.getEndDate(), detailFont));
                document.add(Chunk.NEWLINE);
            }
        }

        // Skills
        if (request.getSkills() != null && !request.getSkills().isEmpty()) {
            addClassicSection(document, "SKILLS", accentColor);
            Font skillFont = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, darkColor);
            document.add(new Paragraph(String.join(", ", request.getSkills()), skillFont));
        }

        document.close();
        return baos.toByteArray();
    }

    private byte[] generateProfessionalTemplate(ResumeRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        Color primaryColor = new Color(0, 100, 80);
        Color darkColor = new Color(40, 40, 40);
        Color sidebarColor = new Color(245, 248, 247);

        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new float[]{35, 65});

        // LEFT COLUMN (sidebar)
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBackgroundColor(sidebarColor);
        leftCell.setPadding(20);
        leftCell.setBorder(Rectangle.NO_BORDER);

        Font nameFont = new Font(Font.HELVETICA, 16, Font.BOLD, primaryColor);
        Paragraph name = new Paragraph(request.getPersonalInfo().getFullName(), nameFont);
        leftCell.addElement(name);

        Font titleFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkColor);
        Paragraph title = new Paragraph(request.getPersonalInfo().getJobTitle(), titleFont);
        title.setSpacingAfter(20);
        leftCell.addElement(title);

        addSidebarSection(leftCell, "CONTACT", primaryColor);
        Font smallFont = new Font(Font.HELVETICA, 8, Font.NORMAL, darkColor);
        if (request.getPersonalInfo().getEmail() != null) leftCell.addElement(new Paragraph(request.getPersonalInfo().getEmail(), smallFont));
        if (request.getPersonalInfo().getPhone() != null) leftCell.addElement(new Paragraph(request.getPersonalInfo().getPhone(), smallFont));
        if (request.getPersonalInfo().getCity() != null) leftCell.addElement(new Paragraph(request.getPersonalInfo().getCity(), smallFont));
        if (request.getPersonalInfo().getLinkedIn() != null) leftCell.addElement(new Paragraph(request.getPersonalInfo().getLinkedIn(), smallFont));

        if (request.getSkills() != null && !request.getSkills().isEmpty()) {
            addSidebarSection(leftCell, "SKILLS", primaryColor);
            for (String skill : request.getSkills()) {
                leftCell.addElement(new Paragraph("• " + skill, smallFont));
            }
        }

        if (request.getLanguages() != null && !request.getLanguages().isEmpty()) {
            addSidebarSection(leftCell, "LANGUAGES", primaryColor);
            for (String lang : request.getLanguages()) {
                leftCell.addElement(new Paragraph("• " + lang, smallFont));
            }
        }

        leftCell.setVerticalAlignment(Element.ALIGN_TOP);
        mainTable.addCell(leftCell);

        // RIGHT COLUMN
        PdfPCell rightCell = new PdfPCell();
        rightCell.setPadding(20);
        rightCell.setBorder(Rectangle.NO_BORDER);

        if (request.getSummary() != null && !request.getSummary().isEmpty()) {
            addMainSection(rightCell, "PROFESSIONAL SUMMARY", primaryColor);
            Font bodyFont = new Font(Font.HELVETICA, 9, Font.NORMAL, darkColor);
            Paragraph summary = new Paragraph(request.getSummary(), bodyFont);
            summary.setSpacingAfter(15);
            rightCell.addElement(summary);
        }

        if (request.getExperience() != null && !request.getExperience().isEmpty()) {
            addMainSection(rightCell, "WORK EXPERIENCE", primaryColor);
            for (ExperienceRequest exp : request.getExperience()) {
                Font companyFont = new Font(Font.HELVETICA, 10, Font.BOLD, darkColor);
                Font posFont = new Font(Font.HELVETICA, 9, Font.NORMAL, new Color(100, 100, 100));
                Font bodyFont = new Font(Font.HELVETICA, 9, Font.NORMAL, darkColor);

                Paragraph company = new Paragraph(exp.getCompany(), companyFont);
                rightCell.addElement(company);

                Paragraph pos = new Paragraph(exp.getPosition() + "  |  " + exp.getStartDate() + " - " + (exp.getEndDate() != null ? exp.getEndDate() : "Present"), posFont);
                rightCell.addElement(pos);

                if (exp.getDescription() != null) {
                    Paragraph desc = new Paragraph(exp.getDescription(), bodyFont);
                    desc.setSpacingAfter(5);
                    rightCell.addElement(desc);
                }
                if (exp.getAchievements() != null) {
                    for (String achievement : exp.getAchievements()) {
                        rightCell.addElement(new Paragraph("• " + achievement, bodyFont));
                    }
                }
                rightCell.addElement(Chunk.NEWLINE);
            }
        }

        if (request.getEducation() != null && !request.getEducation().isEmpty()) {
            addMainSection(rightCell, "EDUCATION", primaryColor);
            for (EducationRequest edu : request.getEducation()) {
                Font instFont = new Font(Font.HELVETICA, 10, Font.BOLD, darkColor);
                Font detailFont = new Font(Font.HELVETICA, 9, Font.NORMAL, darkColor);

                rightCell.addElement(new Paragraph(edu.getInstitution(), instFont));
                rightCell.addElement(new Paragraph(edu.getDegree() + " in " + edu.getFieldOfStudy(), detailFont));
                rightCell.addElement(new Paragraph(edu.getStartDate() + " - " + edu.getEndDate(), detailFont));
                rightCell.addElement(Chunk.NEWLINE);
            }
        }

        rightCell.setVerticalAlignment(Element.ALIGN_TOP);
        mainTable.addCell(rightCell);

        document.add(mainTable);
        document.close();
        return baos.toByteArray();
    }

    private void addSectionHeader(Document document, String title, Color color) throws DocumentException {
        Font sectionFont = new Font(Font.HELVETICA, 12, Font.BOLD, color);
        Paragraph section = new Paragraph(title, sectionFont);
        section.setSpacingBefore(15);
        section.setSpacingAfter(8);
        document.add(section);

        LineSeparator line = new LineSeparator();
        line.setLineColor(color);
        line.setPercentage(100);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }

    private void addExperienceEntry(Document document, ExperienceRequest exp, Color darkGray, Color lightGray) throws DocumentException {
        Font companyFont = new Font(Font.HELVETICA, 11, Font.BOLD, darkGray);
        Font dateFont = new Font(Font.HELVETICA, 9, Font.NORMAL, lightGray);
        Font bodyFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkGray);

        Paragraph company = new Paragraph(exp.getCompany(), companyFont);
        document.add(company);

        Paragraph position = new Paragraph(exp.getPosition(), new Font(Font.HELVETICA, 10, Font.BOLD, darkGray));
        document.add(position);

        Paragraph dates = new Paragraph(exp.getStartDate() + " - " + (exp.getEndDate() != null ? exp.getEndDate() : "Present"), dateFont);
        dates.setSpacingAfter(5);
        document.add(dates);

        if (exp.getDescription() != null) {
            document.add(new Paragraph(exp.getDescription(), bodyFont));
        }
        if (exp.getAchievements() != null) {
            for (String achievement : exp.getAchievements()) {
                document.add(new Paragraph("• " + achievement, bodyFont));
            }
        }
        document.add(Chunk.NEWLINE);
    }

    private void addEducationEntry(Document document, EducationRequest edu, Color darkGray, Color lightGray) throws DocumentException {
        Font instFont = new Font(Font.HELVETICA, 11, Font.BOLD, darkGray);
        Font detailFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkGray);
        Font dateFont = new Font(Font.HELVETICA, 9, Font.NORMAL, lightGray);

        document.add(new Paragraph(edu.getInstitution(), instFont));
        document.add(new Paragraph(edu.getDegree() + " in " + edu.getFieldOfStudy(), detailFont));
        document.add(new Paragraph(edu.getStartDate() + " - " + edu.getEndDate(), dateFont));
        document.add(Chunk.NEWLINE);
    }

    private void addClassicSection(Document document, String title, Color color) throws DocumentException {
        Font font = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, color);
        Paragraph p = new Paragraph(title, font);
        p.setSpacingBefore(10);
        p.setSpacingAfter(5);
        document.add(p);

        LineSeparator line = new LineSeparator();
        line.setLineColor(color);
        document.add(new Chunk(line));
    }

    private void addSidebarSection(PdfPCell cell, String title, Color color) {
        Font font = new Font(Font.HELVETICA, 9, Font.BOLD, color);
        Paragraph p = new Paragraph(title, font);
        p.setSpacingBefore(15);
        p.setSpacingAfter(5);
        cell.addElement(p);

        LineSeparator line = new LineSeparator();
        line.setLineColor(color);
        cell.addElement(new Chunk(line));
    }

    private void addMainSection(PdfPCell cell, String title, Color color) {
        Font font = new Font(Font.HELVETICA, 11, Font.BOLD, color);
        Paragraph p = new Paragraph(title, font);
        p.setSpacingBefore(10);
        p.setSpacingAfter(8);
        cell.addElement(p);

        LineSeparator line = new LineSeparator();
        line.setLineColor(color);
        cell.addElement(new Chunk(line));
        cell.addElement(Chunk.NEWLINE);
    }
}
