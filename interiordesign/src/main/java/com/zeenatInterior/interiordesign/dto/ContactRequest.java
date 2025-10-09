package com.zeenatInterior.interiordesign.dto;

import java.util.List;

public class ContactRequest {
    private String email;
    private String phone;
    private String address;
    private List<ContactFieldRequest> additionalFields;

    // Constructors, Getters and Setters
    public ContactRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<ContactFieldRequest> getAdditionalFields() { return additionalFields; }
    public void setAdditionalFields(List<ContactFieldRequest> additionalFields) {
        this.additionalFields = additionalFields;
    }
}
