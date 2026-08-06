package com.resumegen.dto.request;

import java.util.List;

public class PublicationRequest {
    private List<String> books;
    private List<String> journalArticles;
    private List<String> conferenceProceedings;

    public List<String> getBooks() { return books; }
    public void setBooks(List<String> books) { this.books = books; }
    public List<String> getJournalArticles() { return journalArticles; }
    public void setJournalArticles(List<String> journalArticles) { this.journalArticles = journalArticles; }
    public List<String> getConferenceProceedings() { return conferenceProceedings; }
    public void setConferenceProceedings(List<String> conferenceProceedings) { this.conferenceProceedings = conferenceProceedings; }
}
