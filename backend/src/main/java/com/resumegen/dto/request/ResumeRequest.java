package com.resumegen.dto.request;

import java.util.List;

public class ResumeRequest {
    private String template;
    private PersonalInfoRequest personalInfo;
    private List<EducationRequest> education;
    private List<ExperienceRequest> experience;
    private List<String> skills;
    private List<String> languages;
    private String summary;
    // Academic template fields (optional)
    private String lifePhilosophy;
    private List<ProjectRequest> projects;
    private List<ProudOfRequest> proudOf;
    private PublicationRequest publications;
    private List<RefereeRequest> referees;

    public String getTemplate() { return template; }
    public void setTemplate(String template) { this.template = template; }
    public PersonalInfoRequest getPersonalInfo() { return personalInfo; }
    public void setPersonalInfo(PersonalInfoRequest personalInfo) { this.personalInfo = personalInfo; }
    public List<EducationRequest> getEducation() { return education; }
    public void setEducation(List<EducationRequest> education) { this.education = education; }
    public List<ExperienceRequest> getExperience() { return experience; }
    public void setExperience(List<ExperienceRequest> experience) { this.experience = experience; }
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getLifePhilosophy() { return lifePhilosophy; }
    public void setLifePhilosophy(String lifePhilosophy) { this.lifePhilosophy = lifePhilosophy; }
    public List<ProjectRequest> getProjects() { return projects; }
    public void setProjects(List<ProjectRequest> projects) { this.projects = projects; }
    public List<ProudOfRequest> getProudOf() { return proudOf; }
    public void setProudOf(List<ProudOfRequest> proudOf) { this.proudOf = proudOf; }
    public PublicationRequest getPublications() { return publications; }
    public void setPublications(PublicationRequest publications) { this.publications = publications; }
    public List<RefereeRequest> getReferees() { return referees; }
    public void setReferees(List<RefereeRequest> referees) { this.referees = referees; }
}
