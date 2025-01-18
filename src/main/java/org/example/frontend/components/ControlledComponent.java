package org.example.frontend.components;

import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;

public abstract class ControlledComponent<T extends ComponentController> extends Component{
    // A ControlledComponent is any component in the UI with a controller class.
    // T is the type of the controller
    // T must implement the ComponentController interface.

    protected URL fxmlURL;
    protected T controller;

//    public ControlledComponent() {
//    }

    public ControlledComponent(URL fxmlURL) {
        try {
            if (fxmlURL == null) {
                throw new IllegalStateException("FXML file URL is null.");
            }
            this.fxmlURL = fxmlURL;
            System.out.println("Loading FXML: " + fxmlURL); // Debug log
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            System.out.println("Error loading FXML: " + fxmlURL);
            throw new RuntimeException(e);
        }
    }

    public T getController() {
        return controller;
    }
}