<?xml version="1.0" encoding="UTF-8"?>
        <?import javafx.scene.control.*?>
        <?import javafx.scene.layout.*?>

<VBox spacing="10" alignment="CENTER" xmlns:fx="http://javafx.com/fxml" fx:controller="org.example.frontend.controller.ScreenController">
<Label text="Screen Management" style="-fx-font-size: 18px; -fx-font-weight: bold;" />

<!-- Input Fields -->
<HBox spacing="10">
<TextField fx:id="movieNameField" promptText="Movie Name" />
<TextField fx:id="showTimingField" promptText="Show Timing" />
<TextField fx:id="seatAvailabilityField" promptText="Seat Availability" />
</HBox>

<!-- Buttons -->
<HBox spacing="10">
<Button text="Add Screen" onAction="#handleAddScreen" />
<Button text="Update Screen" onAction="#handleUpdateScreen" />
</HBox>

<!-- Search and Table -->
<HBox spacing="10">
<TextField fx:id="searchField" promptText="Search by Movie Name" />
<Button text="Search" onAction="#handleSearchScreen" />
</HBox>

<TableView fx:id="screenTable" prefHeight="200" prefWidth="400">
<columns>
<TableColumn fx:id="idColumn" text="ID" prefWidth="50" />
<TableColumn fx:id="movieNameColumn" text="Movie Name" prefWidth="150" />
<TableColumn fx:id="showTimingColumn" text="Show Timing" prefWidth="100" />
<TableColumn fx:id="seatAvailabilityColumn" text="Seats" prefWidth="100" />
</columns>
</TableView>
</VBox>




        <?xml version="1.0" encoding="UTF-8"?>
        <?import javafx.scene.control.*?>
        <?import javafx.scene.layout.*?>

<VBox spacing="10" alignment="CENTER" xmlns:fx="http://javafx.com/fxml" fx:controller="org.example.frontend.controller.TheaterController">
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
<TextField fx:id="searchField" promptText="Search by Name or Address" />
<Button text="Search" onAction="#handleSearchTheater" />
</HBox>

<TableView fx:id="theaterTable" prefHeight="200" prefWidth="400">
<columns>
<TableColumn fx:id="idColumn" text="ID" prefWidth="50" />
<TableColumn fx:id="nameColumn" text="Name" prefWidth="150" />
<TableColumn fx:id="addressColumn" text="Address" prefWidth="200" />
</columns>
</TableView>
</VBox>


        package org.example.frontend.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.backend.model.Screen;
import org.example.backend.service.ScreenService;

import java.util.List;

public class ScreenController {

    @FXML private TextField movieNameField;
    @FXML private TextField showTimingField;
    @FXML private TextField seatAvailabilityField;
    @FXML private TextField searchField;
    @FXML private TableView<Screen> screenTable;
    @FXML private TableColumn<Screen, Integer> idColumn;
    @FXML private TableColumn<Screen, String> movieNameColumn;
    @FXML private TableColumn<Screen, String> showTimingColumn;
    @FXML private TableColumn<Screen, String> seatAvailabilityColumn;

    private final ScreenService screenService = new ScreenService();
    private final ObservableList<Screen> screenList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        movieNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMovieName()));
        showTimingColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getShowTiming()));
        seatAvailabilityColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSeatAvailability()));
        screenTable.setItems(screenList);

        loadAllScreens();
    }

    private void loadAllScreens() {
        List<Screen> screens = screenService.searchScreens("");
        screenList.setAll(screens);
    }

    @FXML
    public void handleAddScreen() {
        String movieName = movieNameField.getText();
        String showTiming = showTimingField.getText();
        String seatAvailability = seatAvailabilityField.getText();

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
        clearFields();
    }

    @FXML
    public void handleUpdateScreen() {
        Screen selectedScreen = screenTable.getSelectionModel().getSelectedItem();
        if (selectedScreen == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a screen to update.");
            return;
        }

        String movieName = movieNameField.getText();
        String showTiming = showTimingField.getText();
        String seatAvailability = seatAvailabilityField.getText();

        selectedScreen.setMovieName(movieName);
        selectedScreen.setShowTiming(showTiming);
        selectedScreen.setSeatAvailability(seatAvailability);

        screenService.updateScreen(selectedScreen);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Screen updated successfully!");
        loadAllScreens();
        clearFields();
    }

    @FXML
    public void handleSearchScreen() {
        String query = searchField.getText();
        List<Screen> screens = screenService.searchScreens(query);
        screenList.setAll(screens);
    }

    private void clearFields() {
        movieNameField.clear();
        showTimingField.clear();
        seatAvailabilityField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}



package org.example.frontend.controller;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.scene.control.*;
        import org.example.backend.model.Theater;
        import org.example.backend.service.TheaterService;

        import java.util.List;

public class TheaterController {

    @FXML private TextField theaterNameField;
    @FXML private TextField theaterAddressField;
    @FXML private TextField searchField;
    @FXML private TableView<Theater> theaterTable;
    @FXML private TableColumn<Theater, Integer> idColumn;
    @FXML private TableColumn<Theater, String> nameColumn;
    @FXML private TableColumn<Theater, String> addressColumn;

    private final TheaterService theaterService = new TheaterService();
    private final ObservableList<Theater> theaterList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        addressColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAddress()));
        theaterTable.setItems(theaterList);

        loadAllTheaters();
    }

    private void loadAllTheaters() {
        List<Theater> theaters = theaterService.searchTheaters("");
        theaterList.setAll(theaters);
    }

    @FXML
    public void handleAddTheater() {
        String name = theaterNameField.getText();
        String address = theaterAddressField.getText();

        if (name.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Name and Address cannot be empty!");
            return;
        }

        Theater theater = new Theater();
        theater.setName(name);
        theater.setAddress(address);

        theaterService.addTheater(theater);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Theater added successfully!");
        loadAllTheaters();
        clearFields();
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

        selectedTheater.setName(name);
        selectedTheater.setAddress(address);

        theaterService.updateTheater(selectedTheater);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Theater updated successfully!");
        loadAllTheaters();
        clearFields();
    }

    @FXML
    public void handleSearchTheater() {
        String query = searchField.getText();
        List<Theater> theaters = theaterService.searchTheaters(query);
        theaterList.setAll(theaters);
    }

    private void clearFields() {
        theaterNameField.clear();
        theaterAddressField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
