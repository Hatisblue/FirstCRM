package org.lykov.crm.CRM.models;

import jakarta.validation.constraints.*;

public class Person {
    private int person_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @NotEmpty(message = "Organization should not be empty")
    @Size(min = 2, max = 50, message = "Organization should be between 2 and 50 characters")
    private String organization;


    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    public Person() {

    }

    public Person(int person_id, String name, String organization, String email) {
        this.person_id = person_id;
        this.name = name;
        this.organization = organization;
        this.email = email;
    }

    public int getPerson_id() {
        return person_id;
    }

    public String getName() {
        return name;
    }

    public String getOrganization() {
        return organization;
    }

    public String getEmail() {
        return email;
    }


    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
