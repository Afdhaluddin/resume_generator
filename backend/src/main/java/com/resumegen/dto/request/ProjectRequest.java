package com.resumegen.dto.request;

public class ProjectRequest {
    private String name;
    private String fundingAgency;
    private String duration;
    private String description;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFundingAgency() { return fundingAgency; }
    public void setFundingAgency(String fundingAgency) { this.fundingAgency = fundingAgency; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
