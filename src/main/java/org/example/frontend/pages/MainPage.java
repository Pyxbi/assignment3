package org.example.frontend.pages;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainPage extends Page {
    private BorderPane root;
    private StackPane contentPane;

    public MainPage() {
        root = new BorderPane();
        contentPane = new StackPane();

        // Sidebar Navigation
        VBox menu = new VBox(10);
        Button customerBtn = new Button("Customers");
        Button orderBtn = new Button("Orders");
        Button itemBtn = new Button("Items");
        Button deliverymanBtn = new Button("Deliverymen");

        // Navigation Actions
        customerBtn.setOnAction(e -> navigateTo(new CustomerPage()));
//        orderBtn.setOnAction(e -> navigateTo(new OrderPage()));
//        itemBtn.setOnAction(e -> navigateTo(new ItemPage()));
//        deliverymanBtn.setOnAction(e -> navigateTo(new DeliverymanPage()));

        menu.getChildren().addAll(customerBtn, orderBtn, itemBtn, deliverymanBtn);
        root.setLeft(menu);
        root.setCenter(contentPane);

        // Default page
        navigateTo(new CustomerPage());
    }

    private void navigateTo(Page page) {
        contentPane.getChildren().setAll(page.getRoot());
    }

    @Override
    public Node getRoot() {
        return root;
    }
}
