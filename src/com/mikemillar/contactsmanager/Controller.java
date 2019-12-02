package com.mikemillar.contactsmanager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML private BorderPane mainBorderPane;
    @FXML private TableView<Contact> tableView = new TableView<>();
    @FXML private TableColumn<Contact, String> firstNameCol = new TableColumn<>("First Name");
    @FXML private TableColumn<Contact, String> lastNameCol = new TableColumn<>("Last Name");
    @FXML private TableColumn<Contact, String> phoneNumberCol = new TableColumn<>("Phone Number");
    @FXML private TableColumn<Contact, String> notesCol = new TableColumn<>("Notes");
    @FXML private ContextMenu contactContextMenu;
    @FXML private ContextMenu emptySelectContextMenu;
    
    public void initialize() {
        contactContextMenu = new ContextMenu();
        emptySelectContextMenu = new ContextMenu();
        MenuItem addMenuItem = new MenuItem("Add New Contact");
        MenuItem editMenuItem = new MenuItem("Edit Contact");
        MenuItem deleteMenuItem = new MenuItem("Delete Contact");
        MenuItem addNoSelectMenuItem = new MenuItem("Add New Contact");
        addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showNewContactDialog();
            }
        });
        editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showEditContactDialog();
            }
        });
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteContact();
            }
        });
        addNoSelectMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showNewContactDialog();
            }
        });
        
        contactContextMenu.getItems().addAll(addMenuItem, editMenuItem, deleteMenuItem);
        emptySelectContextMenu.getItems().addAll(addNoSelectMenuItem);
        tableView.setContextMenu(contactContextMenu);
        
        
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("phoneNumber"));
        notesCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("notes"));
        tableView.setItems(ContactData.getInstance().getContacts());
        
        tableView.setRowFactory(new Callback<TableView<Contact>, TableRow<Contact>>() {
            @Override
            public TableRow<Contact> call(TableView<Contact> contactTableView) {
                TableRow<Contact> row = new TableRow<>();
                row.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                row.setContextMenu(emptySelectContextMenu);
                            } else {
                                row.setContextMenu(contactContextMenu);
                            }
                        }
                );
                return row;
            };
        });
    }
    
    @FXML
    public void showNewContactDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add A New Contact");
        dialog.setHeaderText("Use this dialog to create a new contact.");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            DialogController controller = fxmlLoader.getController();
            Contact newContact = controller.processResults();
            tableView.getSelectionModel().select(newContact);
        }
    }
    
    @FXML
    public void showEditContactDialog() {
        Contact contact = tableView.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit Existing Contact");
        dialog.setHeaderText("Use this dialog to edit an existing contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
    
        DialogController controller = fxmlLoader.getController();
        controller.loadContactDetails(contact);
        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            controller.editContact(contact);
            tableView.refresh();
        }
    }
    
    @FXML
    public void deleteContact() {
        Contact contact = tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete Contact: " + contact);
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            ContactData.getInstance().deleteContact(contact);
        }
    }
    
    @FXML
    public void handleExit() {
        Platform.exit();
    }
    
}