package com.resumegen.dto.request;

public class PersonalInfoRequest {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String linkedIn;
    private String website;
    private String jobTitle;
    // Social links for academic template
    private String twitter;
    private String github;
    private String orcid;
    private String gitlab;
    private String mastodon;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getLinkedIn() { return linkedIn; }
    public void setLinkedIn(String linkedIn) { this.linkedIn = linkedIn; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public String getTwitter() { return twitter; }
    public void setTwitter(String twitter) { this.twitter = twitter; }
    public String getGithub() { return github; }
    public void setGithub(String github) { this.github = github; }
    public String getOrcid() { return orcid; }
    public void setOrcid(String orcid) { this.orcid = orcid; }
    public String getGitlab() { return gitlab; }
    public void setGitlab(String gitlab) { this.gitlab = gitlab; }
    public String getMastodon() { return mastodon; }
    public void setMastodon(String mastodon) { this.mastodon = mastodon; }
}
