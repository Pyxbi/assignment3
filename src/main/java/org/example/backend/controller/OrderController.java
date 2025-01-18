<?xml version="1.0" encoding="UTF-8"?>
        <?import javafx.scene.control.*?>
        <?import javafx.scene.layout.*?>

<VBox spacing="10" alignment="CENTER" xmlns:fx="http://javafx.com/fxml" fx:controller="org.example.frontend.controller.BookingController">
<Label text="Booking Management" style="-fx-font-size: 18px; -fx-font-weight: bold;" />

<!-- Input Fields -->
<HBox spacing="10">
<TextField fx:id="userIdField" promptText="User ID" />
<TextField fx:id="showDetailsField" promptText="Show Details" />
<TextField fx:id="reservedSeatsField" promptText="Reserved Seats" />
</HBox>

<!-- Buttons -->
<HBox spacing="10">
<Button text="Add Booking" onAction="#handleAddBooking" />
<Button text="Update Booking" onAction="#handleUpdateBooking" />
</HBox>

<!-- Search and Table -->
<HBox spacing="10">
<TextField fx:id="searchField" promptText="Search by Show Details" />
<Button text="Search" onAction="#handleSearchBookings" />
</HBox>

<TableView fx:id="bookingTable" prefHeight="200" prefWidth="400">
<columns>
<TableColumn fx:id="idColumn" text="ID" prefWidth="50" />
<TableColumn fx:id="userIdColumn" text="User ID" prefWidth="100" />
<TableColumn fx:id="showDetailsColumn" text="Show Details" prefWidth="150" />
<TableColumn fx:id="reservedSeatsColumn" text="Seats" prefWidth="100" />
</columns>
</TableView>
</VBox>



        package org.example.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.backend.model.Booking;
import org.example.backend.model.User;
import org.example.backend.service.BookingService;

import java.util.List;

public class BookingController {

    @FXML private TextField userIdField;
    @FXML private TextField showDetailsField;
    @FXML private TextField reservedSeatsField;
    @FXML private TextField searchField;
    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, Integer> idColumn;
    @FXML private TableColumn<Booking, Integer> userIdColumn;
    @FXML private TableColumn<Booking, String> showDetailsColumn;
    @FXML private TableColumn<Booking, String> reservedSeatsColumn;

    private final BookingService bookingService = new BookingService();
    private final ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        userIdColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getUser().getId()));
        showDetailsColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getShowDetails()));
        reservedSeatsColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getReservedSeats()));
        bookingTable.setItems(bookingList);

        loadAllBookings();
    }

    private void loadAllBookings() {
        List<Booking> bookings = bookingService.searchBookings("");
        bookingList.setAll(bookings);
    }

    @FXML
    public void handleAddBooking() {
        try {
            Integer userId = Integer.parseInt(userIdField.getText());
            String showDetails = showDetailsField.getText();
            String reservedSeats = reservedSeatsField.getText();

            if (showDetails.isEmpty() || reservedSeats.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Show details and reserved seats cannot be empty!");
                return;
            }

            User user = new User();
            user.setId(userId);

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setShowDetails(showDetails);
            booking.setReservedSeats(reservedSeats);

            bookingService.addBooking(booking);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Booking added successfully!");
            loadAllBookings();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "User ID must be a valid integer!");
        }
    }

    @FXML
    public void handleUpdateBooking() {
        Booking selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a booking to update.");
            return;
        }

        try {
            Integer userId = Integer.parseInt(userIdField.getText());
            String showDetails = showDetailsField.getText();
            String reservedSeats = reservedSeatsField.getText();

            if (showDetails.isEmpty() || reservedSeats.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Show details and reserved seats cannot be empty!");
                return;
            }

            User user = new User();
            user.setId(userId);

            selectedBooking.setUser(user);
            selectedBooking.setShowDetails(showDetails);
            selectedBooking.setReservedSeats(reservedSeats);

            bookingService.updateBooking(selectedBooking);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Booking updated successfully!");
            loadAllBookings();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "User ID must be a valid integer!");
        }
    }

    @FXML
    public void handleSearchBookings() {
        String query = searchField.getText();
        List<Booking> bookings = bookingService.searchBookings(query);
        bookingList.setAll(bookings);
    }

    private void clearFields() {
        userIdField.clear();
        showDetailsField.clear();
        reservedSeatsField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
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

public class BookingApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/booking_management.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Booking Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
