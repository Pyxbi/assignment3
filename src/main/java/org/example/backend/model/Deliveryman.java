<?xml version="1.0" encoding="UTF-8"?>
        <?import javafx.scene.control.*?>
        <?import javafx.scene.layout.*?>
<VBox spacing="10" alignment="CENTER" xmlns:fx="http://javafx.com/fxml" fx:controller="org.example.frontend.controller.UserController">
<Label text="User Management" style="-fx-font-size: 18px; -fx-font-weight: bold;" />

<!-- Input Fields -->
<HBox spacing="10">
<TextField fx:id="nameField" promptText="Name" />
<TextField fx:id="contactField" promptText="Contact Information" />
</HBox>

<!-- Buttons -->
<HBox spacing="10">
<Button text="Add User" onAction="#handleAddUser" />
<Button text="Update User" onAction="#handleUpdateUser" />
</HBox>

<!-- User Search -->
<HBox spacing="10">
<TextField fx:id="searchField" promptText="Search by Name" />
<Button text="Search" onAction="#handleSearchUsers" />
</HBox>

<!-- Table View -->
<TableView fx:id="userTable" prefWidth="400">
<columns>
<TableColumn fx:id="idColumn" text="ID" prefWidth="50" />
<TableColumn fx:id="nameColumn" text="Name" prefWidth="150" />
<TableColumn fx:id="contactColumn" text="Contact Info" prefWidth="200" />
</columns>
</TableView>
</VBox>



        package org.example.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.backend.model.User;
import org.example.backend.service.UserService;

import java.util.List;

public class UserController {

    // FXML Components
    @FXML private TextField nameField;
    @FXML private TextField contactField;
    @FXML private TextField searchField;
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> contactColumn;

    // Backend Service
    private final UserService userService = new UserService();

    // Observable List for TableView
    private final ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize TableView Columns
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        contactColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getContactInformation()));

        // Bind the observable list to the TableView
        userTable.setItems(userList);

        // Load all users at startup
        loadAllUsers();
    }

    private void loadAllUsers() {
        List<User> users = userService.searchUsers(""); // Fetch all users
        userList.setAll(users);
    }

    @FXML
    public void handleAddUser() {
        String name = nameField.getText();
        String contact = contactField.getText();

        if (name.isEmpty() || contact.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Name and Contact Information cannot be empty!");
            return;
        }

        User user = new User();
        user.setName(name);
        user.setContactInformation(contact);

        userService.addUser(user);
        showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully!");

        // Refresh TableView
        loadAllUsers();

        // Clear Input Fields
        nameField.clear();
        contactField.clear();
    }

    @FXML
    public void handleUpdateUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a user to update.");
            return;
        }

        String name = nameField.getText();
        String contact = contactField.getText();

        if (name.isEmpty() || contact.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Name and Contact Information cannot be empty!");
            return;
        }

        selectedUser.setName(name);
        selectedUser.setContactInformation(contact);

        userService.updateUser(selectedUser);
        showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");

        // Refresh TableView
        loadAllUsers();

        // Clear Input Fields
        nameField.clear();
        contactField.clear();
    }

    @FXML
    public void handleSearchUsers() {
        String query = searchField.getText();

        List<User> users = userService.searchUsers(query);
        userList.setAll(users);

        if (users.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Results", "No users found for the given search query.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}



import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

public class UserManagementApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/user_management.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("User Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
