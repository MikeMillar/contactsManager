package com.mikemillar.contactsmanager;

public class Contact {
    
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String notes;
    
    Contact() {
        firstName = null;
        lastName = null;
        phoneNumber = null;
        notes = null;
    }
    
    Contact(String fName, String lName, String number, String note) {
        firstName = fName;
        lastName = lName;
        phoneNumber = number;
        notes = note;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
