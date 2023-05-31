package org.lykov.crm.CRM.models;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class Issue {
    private int issue_id;

    private int person_id;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 30, message = "Title should be between 2-30 symbols")
    private String title;

    @NotEmpty(message = "Description should not be empty")
    @Size(min = 5, max = 300, message = "Description should be between 5-300 symbols")
    private String description;

    @NotNull(message = "Starttime should not be empty")
    private LocalDateTime starttime;
    private LocalDateTime endtime;

    public Issue() {
        this.starttime = LocalDateTime.now();
    }

    public Issue(int issue_id, int person_id, String title, String description) {
        this.issue_id = issue_id;
        this.person_id = person_id;
        this.title = title;
        this.description = description;
        this.starttime = LocalDateTime.now();
        this.endtime = null;
    }

    public int getIssue_id() {
        return issue_id;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStarttime() {
        return starttime;
    }

    public LocalDateTime getEndtime() {
        return endtime;
    }

    public void setIssue_id(int issue_id) {
        this.issue_id = issue_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStarttime(LocalDateTime starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(LocalDateTime endtime) {
        this.endtime = endtime;
    }
}