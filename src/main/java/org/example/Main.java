package org.example;

import javafx.application.Application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.frontend.pages.MainPage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainPage mainPage = new MainPage();
        Scene scene = new Scene((Parent) mainPage.getRoot(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Restaurant Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}