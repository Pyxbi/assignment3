<?xml version="1.0" encoding="UTF-8"?>
        <?import javafx.scene.control.*?>
        <?import javafx.scene.layout.*?>

<TabPane xmlns:fx="http://javafx.com/fxml" fx:controller="org.example.frontend.controller.TheaterScreenController">
<tabs>
<!-- Theater Tab -->
<Tab text="Theater Management">
<VBox spacing="10" alignment="CENTER" style="-fx-padding: 10;">
<Label text="Theater Management" style="-fx-font-size: 18px; -fx-font-weight: bold;" />

<!-- Input Fields -->
<HBox spacing="10">
<TextField fx:id="theaterNameField" promptText="Theater Name" />
<TextField fx:id="theaterAddressField" promptText="Theater Address" />
</HBox>

<!-- Buttons -->
<HBox spacing="10">
<Button text="Add Theater" onAction="#handleAddTheater" />
<Button text="Update Theater" onAction="#handleUpdateTheater" />
</HBox>

<!-- Search and Table -->
<HBox spacing="10">
<TextField fx:id="theaterSearchField" promptText="Search by Name/Address" />
<Button text="Search" onAction="#handleSearchTheater" />
</HBox>

<TableView fx:id="theaterTable" prefHeight="200">
<columns>
<TableColumn fx:id="theaterIdColumn" text="ID" prefWidth="50" />
<TableColumn fx:id="theaterNameColumn" text="Name" prefWidth="150" />
<TableColumn fx:id="theaterAddressColumn" text="Address" prefWidth="200" />
</columns>
</TableView>
</VBox>
</Tab>

<!-- Screen Tab -->
<Tab text="Screen Management">
<VBox spacing="10" alignment="CENTER" style="-fx-padding: 10;">
<Label text="Screen Management" style="-fx-font-size: 18px; -fx-font-weight: bold;" />

<!-- Input Fields -->
<HBox spacing="10">
<TextField fx:id="screenMovieNameField" promptText="Movie Name" />
<TextField fx:id="screenShowTimingField" promptText="Show Timing" />
<TextField fx:id="screenSeatAvailabilityField" promptText="Seat Availability" />
</HBox>

<!-- Buttons -->
<HBox spacing="10">
<Button text="Add Screen" onAction="#handleAddScreen" />
<Button text="Update Screen" onAction="#handleUpdateScreen" />
</HBox>

<!-- Search and Table -->
<HBox spacing="10">
<TextField fx:id="screenSearchField" promptText="Search by Movie Name" />
<Button text="Search" onAction="#handleSearchScreen" />
</HBox>

<TableView fx:id="screenTable" prefHeight="200">
<columns>
<TableColumn fx:id="screenIdColumn" text="ID" prefWidth="50" />
<TableColumn fx:id="screenMovieNameColumn" text="Movie Name" prefWidth="150" />
<TableColumn fx:id="screenShowTimingColumn" text="Show Timing" prefWidth="100" />
<TableColumn fx:id="screenSeatAvailabilityColumn" text="Seats" prefWidth="100" />
</columns>
</TableView>
</VBox>
</Tab>
</tabs>
</TabPane>


        package org.example.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.backend.model.Screen;
import org.example.backend.model.Theater;
import org.example.backend.service.ScreenService;
import org.example.backend.service.TheaterService;

import java.util.List;

public class TheaterScreenController {

    // Theater Components
    @FXML private TextField theaterNameField;
    @FXML private TextField theaterAddressField;
    @FXML private TextField theaterSearchField;
    @FXML private TableView<Theater> theaterTable;
    @FXML private TableColumn<Theater, Integer> theaterIdColumn;
    @FXML private TableColumn<Theater, String> theaterNameColumn;
    @FXML private TableColumn<Theater, String> theaterAddressColumn;

    // Screen Components
    @FXML private TextField screenMovieNameField;
    @FXML private TextField screenShowTimingField;
    @FXML private TextField screenSeatAvailabilityField;
    @FXML private TextField screenSearchField;
    @FXML private TableView<Screen> screenTable;
    @FXML private TableColumn<Screen, Integer> screenIdColumn;
    @FXML private TableColumn<Screen, String> screenMovieNameColumn;
    @FXML private TableColumn<Screen, String> screenShowTimingColumn;
    @FXML private TableColumn<Screen, String> screenSeatAvailabilityColumn;

    // Services
    private final TheaterService theaterService = new TheaterService();
    private final ScreenService screenService = new ScreenService();

    // Observable Lists
    private final ObservableList<Theater> theaterList = FXCollections.observableArrayList();
    private final ObservableList<Screen> screenList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize Theater Table Columns
        theaterIdColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        theaterNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        theaterAddressColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAddress()));
        theaterTable.setItems(theaterList);

        // Initialize Screen Table Columns
        screenIdColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        screenMovieNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMovieName()));
        screenShowTimingColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getShowTiming()));
        screenSeatAvailabilityColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSeatAvailability()));
        screenTable.setItems(screenList);

        // Load initial data
        loadAllTheaters();
        loadAllScreens();
    }

    private void loadAllTheaters() {
        List<Theater> theaters = theaterService.searchTheaters(""); // Fetch all theaters
        theaterList.setAll(theaters);
    }

    private void loadAllScreens() {
        List<Screen> screens = screenService.searchScreens(""); // Fetch all screens
        screenList.setAll(screens);
    }

    @FXML
    public void handleAddTheater() {
        String name = theaterNameField.getText();
        String address = theaterAddressField.getText();

        if (name.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Theater name and address cannot be empty!");
            return;
        }

        Theater theater = new Theater();
        theater.setName(name);
        theater.setAddress(address);

        theaterService.addTheater(theater);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Theater added successfully!");
        loadAllTheaters();
        clearTheaterFields();
    }

    @FXML
    public void handleUpdateTheater() {
        Theater selectedTheater = theaterTable.getSelectionModel().getSelectedItem();
        if (selectedTheater == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a theater to update.");
            return;
        }

        String name = theaterNameField.getText();
        String address = theaterAddressField.getText();

        if (name.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Theater name and address cannot be empty!");
            return;
        }

        selectedTheater.setName(name);
        selectedTheater.setAddress(address);
        theaterService.updateTheater(selectedTheater);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Theater updated successfully!");
        loadAllTheaters();
        clearTheaterFields();
    }

    @FXML
    public void handleSearchTheater() {
        String query = theaterSearchField.getText();
        List<Theater> theaters = theaterService.searchTheaters(query);
        theaterList.setAll(theaters);
    }

    @FXML
    public void handleAddScreen() {
        String movieName = screenMovieNameField.getText();
        String showTiming = screenShowTimingField.getText();
        String seatAvailability = screenSeatAvailabilityField.getText();

        if (movieName.isEmpty() || showTiming.isEmpty() || seatAvailability.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
            return;
        }

        Screen screen = new Screen();
        screen.setMovieName(movieName);
        screen.setShowTiming(showTiming);
        screen.setSeatAvailability(seatAvailability);

        screenService.addScreen(screen);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Screen added successfully!");
        loadAllScreens();
        clearScreenFields();
    }

    @FXML
    public void handleUpdateScreen() {
        Screen selectedScreen = screenTable.getSelectionModel().getSelectedItem();
        if (selectedScreen == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a screen to update.");
            return;
        }

        String movieName = screenMovieNameField.getText();
        String showTiming = screenShowTimingField.getText();
        String seatAvailability = screenSeatAvailabilityField.getText();

        if (movieName.isEmpty() || showTiming.isEmpty() || seatAvailability.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
            return;
        }

        selectedScreen.setMovieName(movieName);
        selectedScreen.setShowTiming(showTiming);
        selectedScreen.setSeatAvailability(seatAvailability);
        screenService.updateScreen(selectedScreen);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Screen updated successfully!");
        loadAllScreens();
        clearScreenFields();
    }

    @FXML
    public void handleSearchScreen() {
        String query = screenSearchField.getText();
        List<Screen> screens = screenService.searchScreens(query);
        screenList.setAll(screens);
    }

    private void clearTheaterFields() {
        theaterNameField.clear();
        theaterAddressField.clear();
    }

    private void clearScreenFields() {
        screenMovieNameField.clear();
        screenShowTimingField.clear();
        screenSeatAvailabilityField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


