package com.mikemillar.contactsmanager;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogController {
    
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField phoneNumberField;
    @FXML private TextArea notesField;
    
    public Contact processResults() {
        String fName = firstNameField.getText().trim();
        String lName = lastNameField.getText().trim();
        String pNum = phoneNumberField.getText().trim();
        String note = notesField.getText().trim();
        
        Contact newContact = new Contact(fName, lName, pNum, note);
        ContactData.getInstance().addContact(newContact);
        return newContact;
    }
    
    public void loadContactDetails(Contact contact) {
        firstNameField.setText(contact.getFirstName());
        lastNameField.setText(contact.getLastName());
        phoneNumberField.setText(contact.getPhoneNumber());
        notesField.setText(contact.getNotes());
    }
    
    public Contact editContact(Contact contact) {
        contact.setFirstName(firstNameField.getText().trim());
        contact.setLastName(lastNameField.getText().trim());
        contact.setPhoneNumber(phoneNumberField.getText().trim());
        contact.setNotes(notesField.getText().trim());
        return contact;
    }

}
