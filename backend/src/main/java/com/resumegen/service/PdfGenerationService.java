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
            case "minimal" -> generateMinimalTemplate(request);
            case "executive" -> generateExecutiveTemplate(request);
            case "creative" -> generateCreativeTemplate(request);
            default -> generateModernTemplate(request);
        };
    }

    // ============ MODERN TEMPLATE ============
    // Clean, bold header with blue accent. Perfect for tech, startups, creatives.
    private byte[] generateModernTemplate(ResumeRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        Color primaryColor = new Color(37, 99, 235);   // bright blue
        Color darkGray = new Color(31, 41, 55);        // slate-800
        Color midGray = new Color(75, 85, 99);         // slate-600
        Color lightGray = new Color(156, 163, 175);    // slate-400

        // Full-width colored header bar
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle headerRect = new Rectangle(0, PageSize.A4.getHeight() - 90, PageSize.A4.getWidth(), PageSize.A4.getHeight());
        headerRect.setBackgroundColor(primaryColor);
        canvas.rectangle(headerRect);

        // Name in header
        Font nameFont = new Font(Font.HELVETICA, 28, Font.BOLD, Color.WHITE);
        Paragraph name = new Paragraph(request.getPersonalInfo().getFullName(), nameFont);
        name.setAlignment(Element.ALIGN_CENTER);
        name.setSpacingBefore(25);
        document.add(name);

        // Job title in header
        Font titleFont = new Font(Font.HELVETICA, 13, Font.NORMAL, new Color(191, 219, 254));
        Paragraph jobTitle = new Paragraph(request.getPersonalInfo().getJobTitle(), titleFont);
        jobTitle.setAlignment(Element.ALIGN_CENTER);
        jobTitle.setSpacingAfter(35);
        document.add(jobTitle);

        // Contact row
        addContactRow(document, request, midGray, "  |  ");

        // Summary
        addSection(document, "PROFESSIONAL SUMMARY", primaryColor, darkGray, request.getSummary());

        // Experience
        if (hasItems(request.getExperience())) {
            addSectionHeader(document, "WORK EXPERIENCE", primaryColor);
            for (ExperienceRequest exp : request.getExperience()) {
                addModernExperience(document, exp, darkGray, midGray, lightGray);
            }
        }

        // Education
        if (hasItems(request.getEducation())) {
            addSectionHeader(document, "EDUCATION", primaryColor);
            for (EducationRequest edu : request.getEducation()) {
                addModernEducation(document, edu, darkGray, midGray, lightGray);
            }
        }

        // Skills
        addSkillTags(document, "SKILLS", primaryColor, request.getSkills());

        // Languages
        addSkillTags(document, "LANGUAGES", primaryColor, request.getLanguages());

        document.close();
        return baos.toByteArray();
    }

    // ============ CLASSIC TEMPLATE ============
    // Traditional serif typography. Ideal for law, medicine, academia.
    private byte[] generateClassicTemplate(ResumeRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 60, 60, 60, 60);
        PdfWriter.getInstance(document, baos);
        document.open();

        Color darkColor = new Color(30, 30, 30);
        Color accentColor = new Color(120, 53, 15);     // deep brown
        Color midColor = new Color(80, 80, 80);

        // Name - large, centered, serif
        Font nameFont = new Font(Font.TIMES_ROMAN, 30, Font.BOLD, darkColor);
        Paragraph name = new Paragraph(request.getPersonalInfo().getFullName(), nameFont);
        name.setAlignment(Element.ALIGN_CENTER);
        name.setSpacingAfter(4);
        document.add(name);

        // Job title
        Font titleFont = new Font(Font.TIMES_ROMAN, 12, Font.ITALIC, accentColor);
        Paragraph jobTitle = new Paragraph(request.getPersonalInfo().getJobTitle(), titleFont);
        jobTitle.setAlignment(Element.ALIGN_CENTER);
        jobTitle.setSpacingAfter(8);
        document.add(jobTitle);

        // Divider
        addDivider(document, accentColor, 100);

        // Contact info centered
        Font contactFont = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, midColor);
        String contact = buildContact(request, "  |  ");
        if (!contact.isEmpty()) {
            Paragraph contacts = new Paragraph(contact, contactFont);
            contacts.setAlignment(Element.ALIGN_CENTER);
            contacts.setSpacingAfter(20);
            document.add(contacts);
        }

        // Summary
        addClassicSection(document, "SUMMARY", accentColor);
        if (request.getSummary() != null && !request.getSummary().isEmpty()) {
            Font bodyFont = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, darkColor);
            document.add(new Paragraph(request.getSummary(), bodyFont));
            document.add(Chunk.NEWLINE);
        }

        // Experience
        if (hasItems(request.getExperience())) {
            addClassicSection(document, "EXPERIENCE", accentColor);
            for (ExperienceRequest exp : request.getExperience()) {
                addClassicExperience(document, exp, darkColor, accentColor);
            }
        }

        // Education
        if (hasItems(request.getEducation())) {
            addClassicSection(document, "EDUCATION", accentColor);
            for (EducationRequest edu : request.getEducation()) {
                addClassicEducation(document, edu, darkColor);
            }
        }

        // Skills
        if (hasItems(request.getSkills())) {
            addClassicSection(document, "SKILLS", accentColor);
            Font skillFont = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, darkColor);
            document.add(new Paragraph(String.join(", ", request.getSkills()), skillFont));
        }

        // Languages
        if (hasItems(request.getLanguages())) {
            addClassicSection(document, "LANGUAGES", accentColor);
            Font langFont = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, darkColor);
            document.add(new Paragraph(String.join(", ", request.getLanguages()), langFont));
        }

        document.close();
        return baos.toByteArray();
    }

    // ============ PROFESSIONAL TEMPLATE ============
    // Two-column with sidebar. Great for business, management, consulting.
    private byte[] generateProfessionalTemplate(ResumeRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        PdfWriter.getInstance(document, baos);
        document.open();

        Color primaryColor = new Color(13, 148, 136);    // teal-600
        Color darkColor = new Color(30, 41, 59);         // slate-800
        Color sidebarBg = new Color(240, 253, 250);      // teal-50

        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new float[]{32, 68});

        // LEFT COLUMN (sidebar)
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBackgroundColor(sidebarBg);
        leftCell.setPadding(18);
        leftCell.setBorder(Rectangle.NO_BORDER);

        // Name in sidebar
        Font nameFont = new Font(Font.HELVETICA, 15, Font.BOLD, primaryColor);
        Paragraph name = new Paragraph(request.getPersonalInfo().getFullName(), nameFont);
        leftCell.addElement(name);

        Font titleFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkColor);
        Paragraph title = new Paragraph(request.getPersonalInfo().getJobTitle(), titleFont);
        title.setSpacingAfter(18);
        leftCell.addElement(title);

        // Contact section
        addSidebarSection(leftCell, "CONTACT", primaryColor);
        Font smallFont = new Font(Font.HELVETICA, 8, Font.NORMAL, darkColor);
        addIfPresent(leftCell, request.getPersonalInfo().getEmail(), smallFont);
        addIfPresent(leftCell, request.getPersonalInfo().getPhone(), smallFont);
        addIfPresent(leftCell, formatLocation(request.getPersonalInfo()), smallFont);
        addIfPresent(leftCell, request.getPersonalInfo().getLinkedIn(), smallFont);
        addIfPresent(leftCell, request.getPersonalInfo().getWebsite(), smallFont);

        // Skills
        if (hasItems(request.getSkills())) {
            addSidebarSection(leftCell, "SKILLS", primaryColor);
            for (String skill : request.getSkills()) {
                leftCell.addElement(new Paragraph("• " + skill, smallFont));
            }
        }

        // Languages
        if (hasItems(request.getLanguages())) {
            addSidebarSection(leftCell, "LANGUAGES", primaryColor);
            for (String lang : request.getLanguages()) {
                leftCell.addElement(new Paragraph("• " + lang, smallFont));
            }
        }

        leftCell.setVerticalAlignment(Element.ALIGN_TOP);
        mainTable.addCell(leftCell);

        // RIGHT COLUMN
        PdfPCell rightCell = new PdfPCell();
        rightCell.setPadding(18);
        rightCell.setBorder(Rectangle.NO_BORDER);

        if (request.getSummary() != null && !request.getSummary().isEmpty()) {
            addMainSection(rightCell, "PROFILE", primaryColor);
            Font bodyFont = new Font(Font.HELVETICA, 9, Font.NORMAL, darkColor);
            Paragraph summary = new Paragraph(request.getSummary(), bodyFont);
            summary.setSpacingAfter(12);
            rightCell.addElement(summary);
        }

        if (hasItems(request.getExperience())) {
            addMainSection(rightCell, "EXPERIENCE", primaryColor);
            for (ExperienceRequest exp : request.getExperience()) {
                addProExperience(rightCell, exp, darkColor);
            }
        }

        if (hasItems(request.getEducation())) {
            addMainSection(rightCell, "EDUCATION", primaryColor);
            for (EducationRequest edu : request.getEducation()) {
                addProEducation(rightCell, edu, darkColor);
            }
        }

        rightCell.setVerticalAlignment(Element.ALIGN_TOP);
        mainTable.addCell(rightCell);

        document.add(mainTable);
        document.close();
        return baos.toByteArray();
    }

    // ============ MINIMAL TEMPLATE ============
    // Ultra-clean, whitespace-heavy, thin elegant lines. For designers, marketers.
    private byte[] generateMinimalTemplate(ResumeRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 65, 65, 65, 65);
        PdfWriter.getInstance(document, baos);
        document.open();

        Color darkColor = new Color(17, 24, 39);         // gray-900
        Color midColor = new Color(107, 114, 128);       // gray-500
        Color lightColor = new Color(209, 213, 219);     // gray-300

        // Name - very large, light weight
        Font nameFont = new Font(Font.HELVETICA, 32, Font.NORMAL, darkColor);
        Paragraph name = new Paragraph(request.getPersonalInfo().getFullName(), nameFont);
        name.setSpacingAfter(2);
        document.add(name);

        // Job title - subtle
        Font titleFont = new Font(Font.HELVETICA, 11, Font.NORMAL, midColor);
        Paragraph jobTitle = new Paragraph(request.getPersonalInfo().getJobTitle(), titleFont);
        jobTitle.setSpacingAfter(12);
        document.add(jobTitle);

        // Thin divider
        addThinDivider(document, lightColor);

        // Contact - minimal
        Font contactFont = new Font(Font.HELVETICA, 8, Font.NORMAL, midColor);
        String contact = buildContact(request, "  ·  ");
        if (!contact.isEmpty()) {
            Paragraph contacts = new Paragraph(contact, contactFont);
            contacts.setSpacingAfter(25);
            document.add(contacts);
        }

        // Summary
        if (request.getSummary() != null && !request.getSummary().isEmpty()) {
            addMinimalSection(document, "About", darkColor);
            Font bodyFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkColor);
            Paragraph summary = new Paragraph(request.getSummary(), bodyFont);
            summary.setSpacingAfter(20);
            document.add(summary);
        }

        // Experience
        if (hasItems(request.getExperience())) {
            addMinimalSection(document, "Experience", darkColor);
            for (ExperienceRequest exp : request.getExperience()) {
                addMinimalExperience(document, exp, darkColor, midColor);
            }
        }

        // Education
        if (hasItems(request.getEducation())) {
            addMinimalSection(document, "Education", darkColor);
            for (EducationRequest edu : request.getEducation()) {
                addMinimalEducation(document, edu, darkColor, midColor);
            }
        }

        // Skills
        if (hasItems(request.getSkills())) {
            addMinimalSection(document, "Skills", darkColor);
            Font skillFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkColor);
            Paragraph skills = new Paragraph(String.join("  ·  ", request.getSkills()), skillFont);
            document.add(skills);
        }

        // Languages
        if (hasItems(request.getLanguages())) {
            addMinimalSection(document, "Languages", darkColor);
            Font langFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkColor);
            Paragraph langs = new Paragraph(String.join("  ·  ", request.getLanguages()), langFont);
            document.add(langs);
        }

        document.close();
        return baos.toByteArray();
    }

    // ============ EXECUTIVE TEMPLATE ============
    // Bold, leadership-focused, dark navy header with gold accents. For C-suite, senior management.
    private byte[] generateExecutiveTemplate(ResumeRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        Color navy = new Color(15, 23, 42);              // slate-900
        Color gold = new Color(180, 140, 60);            // muted gold
        Color darkText = new Color(30, 30, 30);
        Color midText = new Color(100, 100, 100);

        // Dark header block
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle headerRect = new Rectangle(0, PageSize.A4.getHeight() - 100, PageSize.A4.getWidth(), PageSize.A4.getHeight());
        headerRect.setBackgroundColor(navy);
        canvas.rectangle(headerRect);

        // Name
        Font nameFont = new Font(Font.HELVETICA, 26, Font.BOLD, Color.WHITE);
        Paragraph name = new Paragraph(request.getPersonalInfo().getFullName(), nameFont);
        name.setSpacingBefore(30);
        document.add(name);

        // Title with gold accent
        Font titleFont = new Font(Font.HELVETICA, 13, Font.NORMAL, gold);
        Paragraph jobTitle = new Paragraph(request.getPersonalInfo().getJobTitle().toUpperCase(), titleFont);
        jobTitle.setSpacingAfter(40);
        document.add(jobTitle);

        // Gold accent line
        LineSeparator goldLine = new LineSeparator();
        goldLine.setLineColor(gold);
        goldLine.setPercentage(25);
        goldLine.setAlignment(Element.ALIGN_LEFT);
        document.add(new Chunk(goldLine));
        document.add(Chunk.NEWLINE);

        // Contact
        Font contactFont = new Font(Font.HELVETICA, 9, Font.NORMAL, midText);
        String contact = buildContact(request, "  |  ");
        if (!contact.isEmpty()) {
            Paragraph contacts = new Paragraph(contact, contactFont);
            contacts.setSpacingAfter(20);
            document.add(contacts);
        }

        // Summary - "Executive Profile"
        if (request.getSummary() != null && !request.getSummary().isEmpty()) {
            addExecSection(document, "EXECUTIVE PROFILE", gold, navy);
            Font bodyFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkText);
            Paragraph summary = new Paragraph(request.getSummary(), bodyFont);
            summary.setSpacingAfter(15);
            document.add(summary);
        }

        // Experience - "Leadership Experience"
        if (hasItems(request.getExperience())) {
            addExecSection(document, "LEADERSHIP EXPERIENCE", gold, navy);
            for (ExperienceRequest exp : request.getExperience()) {
                addExecExperience(document, exp, darkText, midText, gold);
            }
        }

        // Education
        if (hasItems(request.getEducation())) {
            addExecSection(document, "EDUCATION", gold, navy);
            for (EducationRequest edu : request.getEducation()) {
                addExecEducation(document, edu, darkText, midText);
            }
        }

        // Skills
        if (hasItems(request.getSkills())) {
            addExecSection(document, "CORE COMPETENCIES", gold, navy);
            Font skillFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkText);
            document.add(new Paragraph(String.join("  ·  ", request.getSkills()), skillFont));
        }

        // Languages
        if (hasItems(request.getLanguages())) {
            addExecSection(document, "LANGUAGES", gold, navy);
            Font langFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkText);
            document.add(new Paragraph(String.join("  ·  ", request.getLanguages()), langFont));
        }

        document.close();
        return baos.toByteArray();
    }

    // ============ CREATIVE TEMPLATE ============
    // Left accent bar, vibrant color, modern layout. For designers, artists, marketers.
    private byte[] generateCreativeTemplate(ResumeRequest request) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        Color accent = new Color(219, 39, 119);          // pink-600
        Color darkColor = new Color(31, 41, 55);
        Color midColor = new Color(107, 114, 128);

        // Left accent bar (full height)
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle accentBar = new Rectangle(0, 0, 8, PageSize.A4.getHeight());
        accentBar.setBackgroundColor(accent);
        canvas.rectangle(accentBar);

        // Name - large, bold
        Font nameFont = new Font(Font.HELVETICA, 30, Font.BOLD, darkColor);
        Paragraph name = new Paragraph(request.getPersonalInfo().getFullName(), nameFont);
        name.setSpacingAfter(4);
        document.add(name);

        // Job title with accent color
        Font titleFont = new Font(Font.HELVETICA, 12, Font.BOLD, accent);
        Paragraph jobTitle = new Paragraph(request.getPersonalInfo().getJobTitle(), titleFont);
        jobTitle.setSpacingAfter(10);
        document.add(jobTitle);

        // Contact
        Font contactFont = new Font(Font.HELVETICA, 9, Font.NORMAL, midColor);
        String contact = buildContact(request, "  ·  ");
        if (!contact.isEmpty()) {
            Paragraph contacts = new Paragraph(contact, contactFont);
            contacts.setSpacingAfter(20);
            document.add(contacts);
        }

        // Summary
        if (request.getSummary() != null && !request.getSummary().isEmpty()) {
            addCreativeSection(document, "ABOUT ME", accent);
            Font bodyFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkColor);
            Paragraph summary = new Paragraph(request.getSummary(), bodyFont);
            summary.setSpacingAfter(15);
            document.add(summary);
        }

        // Experience
        if (hasItems(request.getExperience())) {
            addCreativeSection(document, "EXPERIENCE", accent);
            for (ExperienceRequest exp : request.getExperience()) {
                addCreativeExperience(document, exp, darkColor, midColor, accent);
            }
        }

        // Education
        if (hasItems(request.getEducation())) {
            addCreativeSection(document, "EDUCATION", accent);
            for (EducationRequest edu : request.getEducation()) {
                addCreativeEducation(document, edu, darkColor, midColor);
            }
        }

        // Skills
        if (hasItems(request.getSkills())) {
            addCreativeSection(document, "SKILLS", accent);
            Font skillFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkColor);
            document.add(new Paragraph(String.join("  ·  ", request.getSkills()), skillFont));
        }

        // Languages
        if (hasItems(request.getLanguages())) {
            addCreativeSection(document, "LANGUAGES", accent);
            Font langFont = new Font(Font.HELVETICA, 10, Font.NORMAL, darkColor);
            document.add(new Paragraph(String.join("  ·  ", request.getLanguages()), langFont));
        }

        document.close();
        return baos.toByteArray();
    }

    // ============ SHARED HELPERS ============

    private boolean hasItems(List<?> list) {
        return list != null && !list.isEmpty();
    }

    private String buildContact(ResumeRequest request, String separator) {
        PersonalInfoRequest p = request.getPersonalInfo();
        StringBuilder sb = new StringBuilder();
        appendIfPresent(sb, p.getEmail(), separator);
        appendIfPresent(sb, p.getPhone(), separator);
        appendIfPresent(sb, formatLocation(p), separator);
        appendIfPresent(sb, p.getLinkedIn(), separator);
        appendIfPresent(sb, p.getWebsite(), separator);
        return sb.toString();
    }

    private String formatLocation(PersonalInfoRequest p) {
        if (p.getCity() != null && p.getCountry() != null) {
            return p.getCity() + ", " + p.getCountry();
        } else if (p.getCity() != null) {
            return p.getCity();
        } else if (p.getAddress() != null) {
            return p.getAddress();
        }
        return null;
    }

    private void appendIfPresent(StringBuilder sb, String value, String separator) {
        if (value != null && !value.isEmpty()) {
            if (!sb.isEmpty()) sb.append(separator);
            sb.append(value);
        }
    }

    private void addIfPresent(PdfPCell cell, String value, Font font) {
        if (value != null && !value.isEmpty()) {
            cell.addElement(new Paragraph(value, font));
        }
    }

    // Modern template helpers
    private void addContactRow(Document document, ResumeRequest request, Color color, String sep) throws DocumentException {
        String contact = buildContact(request, sep);
        if (!contact.isEmpty()) {
            Font font = new Font(Font.HELVETICA, 9, Font.NORMAL, color);
            Paragraph p = new Paragraph(contact, font);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(20);
            document.add(p);
        }
    }

    private void addSection(Document document, String title, Color titleColor, Color textColor, String text) throws DocumentException {
        if (text == null || text.isEmpty()) return;
        addSectionHeader(document, title, titleColor);
        Font font = new Font(Font.HELVETICA, 10, Font.NORMAL, textColor);
        Paragraph p = new Paragraph(text, font);
        p.setSpacingAfter(15);
        document.add(p);
    }

    private void addSectionHeader(Document document, String title, Color color) throws DocumentException {
        Font font = new Font(Font.HELVETICA, 11, Font.BOLD, color);
        Paragraph p = new Paragraph(title, font);
        p.setSpacingBefore(18);
        p.setSpacingAfter(6);
        document.add(p);

        LineSeparator line = new LineSeparator();
        line.setLineColor(color);
        line.setPercentage(100);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }

    private void addModernExperience(Document document, ExperienceRequest exp, Color dark, Color mid, Color light) throws DocumentException {
        Font companyFont = new Font(Font.HELVETICA, 11, Font.BOLD, dark);
        Font posFont = new Font(Font.HELVETICA, 10, Font.NORMAL, mid);
        Font dateFont = new Font(Font.HELVETICA, 9, Font.NORMAL, light);
        Font bodyFont = new Font(Font.HELVETICA, 10, Font.NORMAL, dark);

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);

        PdfPCell left = new PdfPCell(new Paragraph(exp.getCompany(), companyFont));
        left.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(left);

        String dates = exp.getStartDate() + " - " + (exp.getEndDate() != null ? exp.getEndDate() : "Present");
        PdfPCell right = new PdfPCell(new Paragraph(dates, dateFont));
        right.setBorder(Rectangle.NO_BORDER);
        right.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(right);

        document.add(headerTable);
        document.add(new Paragraph(exp.getPosition(), posFont));

        if (exp.getDescription() != null && !exp.getDescription().isEmpty()) {
            document.add(new Paragraph(exp.getDescription(), bodyFont));
        }
        if (exp.getAchievements() != null) {
            for (String achievement : exp.getAchievements()) {
                document.add(new Paragraph("• " + achievement, bodyFont));
            }
        }
        document.add(Chunk.NEWLINE);
    }

    private void addModernEducation(Document document, EducationRequest edu, Color dark, Color mid, Color light) throws DocumentException {
        Font instFont = new Font(Font.HELVETICA, 11, Font.BOLD, dark);
        Font detailFont = new Font(Font.HELVETICA, 10, Font.NORMAL, mid);
        Font dateFont = new Font(Font.HELVETICA, 9, Font.NORMAL, light);

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);

        PdfPCell left = new PdfPCell(new Paragraph(edu.getInstitution(), instFont));
        left.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(left);

        String dates = edu.getStartDate() + " - " + edu.getEndDate();
        PdfPCell right = new PdfPCell(new Paragraph(dates, dateFont));
        right.setBorder(Rectangle.NO_BORDER);
        right.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(right);

        document.add(headerTable);

        String degree = edu.getDegree();
        if (edu.getFieldOfStudy() != null && !edu.getFieldOfStudy().isEmpty()) {
            degree += " in " + edu.getFieldOfStudy();
        }
        document.add(new Paragraph(degree, detailFont));

        if (edu.getDescription() != null && !edu.getDescription().isEmpty()) {
            document.add(new Paragraph(edu.getDescription(), detailFont));
        }
        document.add(Chunk.NEWLINE);
    }

    private void addSkillTags(Document document, String title, Color color, List<String> items) throws DocumentException {
        if (!hasItems(items)) return;
        addSectionHeader(document, title, color);
        Font font = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(51, 51, 51));
        document.add(new Paragraph(String.join("  •  ", items), font));
    }

    // Classic template helpers
    private void addDivider(Document document, Color color, float percentage) throws DocumentException {
        LineSeparator line = new LineSeparator();
        line.setLineColor(color);
        line.setPercentage(percentage);
        document.add(new Chunk(line));
    }

    private void addClassicSection(Document document, String title, Color color) throws DocumentException {
        Font font = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, color);
        Paragraph p = new Paragraph(title, font);
        p.setSpacingBefore(14);
        p.setSpacingAfter(6);
        document.add(p);

        LineSeparator line = new LineSeparator();
        line.setLineColor(color);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }

    private void addClassicExperience(Document document, ExperienceRequest exp, Color dark, Color accent) throws DocumentException {
        Font companyFont = new Font(Font.TIMES_ROMAN, 11, Font.BOLD, dark);
        Font posFont = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, accent);
        Font bodyFont = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, dark);

        Paragraph company = new Paragraph(exp.getCompany(), companyFont);
        document.add(company);

        String posLine = exp.getPosition() + "  |  " + exp.getStartDate() + " - " + (exp.getEndDate() != null ? exp.getEndDate() : "Present");
        document.add(new Paragraph(posLine, posFont));

        if (exp.getDescription() != null && !exp.getDescription().isEmpty()) {
            document.add(new Paragraph(exp.getDescription(), bodyFont));
        }
        if (exp.getAchievements() != null) {
            for (String achievement : exp.getAchievements()) {
                document.add(new Paragraph("• " + achievement, bodyFont));
            }
        }
        document.add(Chunk.NEWLINE);
    }

    private void addClassicEducation(Document document, EducationRequest edu, Color dark) throws DocumentException {
        Font instFont = new Font(Font.TIMES_ROMAN, 11, Font.BOLD, dark);
        Font detailFont = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, dark);

        document.add(new Paragraph(edu.getInstitution(), instFont));
        String degree = edu.getDegree();
        if (edu.getFieldOfStudy() != null && !edu.getFieldOfStudy().isEmpty()) {
            degree += " in " + edu.getFieldOfStudy();
        }
        document.add(new Paragraph(degree + "  |  " + edu.getStartDate() + " - " + edu.getEndDate(), detailFont));
        document.add(Chunk.NEWLINE);
    }

    // Professional template helpers
    private void addProExperience(PdfPCell cell, ExperienceRequest exp, Color dark) {
        Font companyFont = new Font(Font.HELVETICA, 10, Font.BOLD, dark);
        Font posFont = new Font(Font.HELVETICA, 9, Font.NORMAL, new Color(100, 100, 100));
        Font bodyFont = new Font(Font.HELVETICA, 9, Font.NORMAL, dark);

        cell.addElement(new Paragraph(exp.getCompany(), companyFont));

        String posLine = exp.getPosition() + "  |  " + exp.getStartDate() + " - " + (exp.getEndDate() != null ? exp.getEndDate() : "Present");
        cell.addElement(new Paragraph(posLine, posFont));

        if (exp.getDescription() != null && !exp.getDescription().isEmpty()) {
            Paragraph desc = new Paragraph(exp.getDescription(), bodyFont);
            desc.setSpacingAfter(4);
            cell.addElement(desc);
        }
        if (exp.getAchievements() != null) {
            for (String achievement : exp.getAchievements()) {
                cell.addElement(new Paragraph("• " + achievement, bodyFont));
            }
        }
        cell.addElement(Chunk.NEWLINE);
    }

    private void addProEducation(PdfPCell cell, EducationRequest edu, Color dark) {
        Font instFont = new Font(Font.HELVETICA, 10, Font.BOLD, dark);
        Font detailFont = new Font(Font.HELVETICA, 9, Font.NORMAL, dark);

        cell.addElement(new Paragraph(edu.getInstitution(), instFont));
        String degree = edu.getDegree();
        if (edu.getFieldOfStudy() != null && !edu.getFieldOfStudy().isEmpty()) {
            degree += " in " + edu.getFieldOfStudy();
        }
        cell.addElement(new Paragraph(degree + "  |  " + edu.getStartDate() + " - " + edu.getEndDate(), detailFont));
        cell.addElement(Chunk.NEWLINE);
    }

    // Minimal template helpers
    private void addThinDivider(Document document, Color color) throws DocumentException {
        LineSeparator line = new LineSeparator();
        line.setLineColor(color);
        line.setPercentage(100);
        line.setLineWidth(0.5f);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }

    private void addMinimalSection(Document document, String title, Color color) throws DocumentException {
        Font font = new Font(Font.HELVETICA, 10, Font.BOLD, color);
        Paragraph p = new Paragraph(title.toUpperCase(), font);
        p.setSpacingBefore(22);
        p.setSpacingAfter(10);
        p.setLeading(14);
        document.add(p);
    }

    private void addMinimalExperience(Document document, ExperienceRequest exp, Color dark, Color mid) throws DocumentException {
        Font companyFont = new Font(Font.HELVETICA, 11, Font.BOLD, dark);
        Font dateFont = new Font(Font.HELVETICA, 9, Font.NORMAL, mid);
        Font bodyFont = new Font(Font.HELVETICA, 10, Font.NORMAL, dark);

        Paragraph company = new Paragraph(exp.getCompany(), companyFont);
        document.add(company);

        String posLine = exp.getPosition() + " · " + exp.getStartDate() + " - " + (exp.getEndDate() != null ? exp.getEndDate() : "Present");
        Paragraph pos = new Paragraph(posLine, dateFont);
        pos.setSpacingAfter(4);
        document.add(pos);

        if (exp.getDescription() != null && !exp.getDescription().isEmpty()) {
            document.add(new Paragraph(exp.getDescription(), bodyFont));
        }
        if (exp.getAchievements() != null) {
            for (String achievement : exp.getAchievements()) {
                document.add(new Paragraph("• " + achievement, bodyFont));
            }
        }
        document.add(Chunk.NEWLINE);
    }

    private void addMinimalEducation(Document document, EducationRequest edu, Color dark, Color mid) throws DocumentException {
        Font instFont = new Font(Font.HELVETICA, 11, Font.BOLD, dark);
        Font detailFont = new Font(Font.HELVETICA, 10, Font.NORMAL, mid);

        document.add(new Paragraph(edu.getInstitution(), instFont));
        String degree = edu.getDegree();
        if (edu.getFieldOfStudy() != null && !edu.getFieldOfStudy().isEmpty()) {
            degree += " in " + edu.getFieldOfStudy();
        }
        document.add(new Paragraph(degree + " · " + edu.getStartDate() + " - " + edu.getEndDate(), detailFont));
        document.add(Chunk.NEWLINE);
    }

    // Executive template helpers
    private void addExecSection(Document document, String title, Color gold, Color navy) throws DocumentException {
        Font font = new Font(Font.HELVETICA, 11, Font.BOLD, navy);
        Paragraph p = new Paragraph(title, font);
        p.setSpacingBefore(18);
        p.setSpacingAfter(6);
        document.add(p);

        LineSeparator line = new LineSeparator();
        line.setLineColor(gold);
        line.setPercentage(100);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }

    private void addExecExperience(Document document, ExperienceRequest exp, Color dark, Color mid, Color gold) throws DocumentException {
        Font companyFont = new Font(Font.HELVETICA, 11, Font.BOLD, dark);
        Font posFont = new Font(Font.HELVETICA, 10, Font.NORMAL, gold);
        Font bodyFont = new Font(Font.HELVETICA, 10, Font.NORMAL, dark);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        PdfPCell left = new PdfPCell(new Paragraph(exp.getCompany(), companyFont));
        left.setBorder(Rectangle.NO_BORDER);
        table.addCell(left);

        String dates = exp.getStartDate() + " - " + (exp.getEndDate() != null ? exp.getEndDate() : "Present");
        PdfPCell right = new PdfPCell(new Paragraph(dates, new Font(Font.HELVETICA, 9, Font.NORMAL, mid)));
        right.setBorder(Rectangle.NO_BORDER);
        right.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(right);

        document.add(table);
        document.add(new Paragraph(exp.getPosition(), posFont));

        if (exp.getDescription() != null && !exp.getDescription().isEmpty()) {
            document.add(new Paragraph(exp.getDescription(), bodyFont));
        }
        if (exp.getAchievements() != null) {
            for (String achievement : exp.getAchievements()) {
                document.add(new Paragraph("• " + achievement, bodyFont));
            }
        }
        document.add(Chunk.NEWLINE);
    }

    private void addExecEducation(Document document, EducationRequest edu, Color dark, Color mid) throws DocumentException {
        Font instFont = new Font(Font.HELVETICA, 11, Font.BOLD, dark);
        Font detailFont = new Font(Font.HELVETICA, 10, Font.NORMAL, mid);

        document.add(new Paragraph(edu.getInstitution(), instFont));
        String degree = edu.getDegree();
        if (edu.getFieldOfStudy() != null && !edu.getFieldOfStudy().isEmpty()) {
            degree += " in " + edu.getFieldOfStudy();
        }
        document.add(new Paragraph(degree + "  |  " + edu.getStartDate() + " - " + edu.getEndDate(), detailFont));
        document.add(Chunk.NEWLINE);
    }

    // Creative template helpers
    private void addCreativeSection(Document document, String title, Color accent) throws DocumentException {
        Font font = new Font(Font.HELVETICA, 11, Font.BOLD, accent);
        Paragraph p = new Paragraph(title, font);
        p.setSpacingBefore(18);
        p.setSpacingAfter(6);
        document.add(p);

        LineSeparator line = new LineSeparator();
        line.setLineColor(accent);
        line.setPercentage(100);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }

    private void addCreativeExperience(Document document, ExperienceRequest exp, Color dark, Color mid, Color accent) throws DocumentException {
        Font companyFont = new Font(Font.HELVETICA, 11, Font.BOLD, dark);
        Font posFont = new Font(Font.HELVETICA, 10, Font.BOLD, accent);
        Font bodyFont = new Font(Font.HELVETICA, 10, Font.NORMAL, dark);

        Paragraph company = new Paragraph(exp.getCompany(), companyFont);
        document.add(company);

        String posLine = exp.getPosition() + "  ·  " + exp.getStartDate() + " - " + (exp.getEndDate() != null ? exp.getEndDate() : "Present");
        document.add(new Paragraph(posLine, posFont));

        if (exp.getDescription() != null && !exp.getDescription().isEmpty()) {
            document.add(new Paragraph(exp.getDescription(), bodyFont));
        }
        if (exp.getAchievements() != null) {
            for (String achievement : exp.getAchievements()) {
                document.add(new Paragraph("• " + achievement, bodyFont));
            }
        }
        document.add(Chunk.NEWLINE);
    }

    private void addCreativeEducation(Document document, EducationRequest edu, Color dark, Color mid) throws DocumentException {
        Font instFont = new Font(Font.HELVETICA, 11, Font.BOLD, dark);
        Font detailFont = new Font(Font.HELVETICA, 10, Font.NORMAL, mid);

        document.add(new Paragraph(edu.getInstitution(), instFont));
        String degree = edu.getDegree();
        if (edu.getFieldOfStudy() != null && !edu.getFieldOfStudy().isEmpty()) {
            degree += " in " + edu.getFieldOfStudy();
        }
        document.add(new Paragraph(degree + "  ·  " + edu.getStartDate() + " - " + edu.getEndDate(), detailFont));
        document.add(Chunk.NEWLINE);
    }
}
