package org.example.frontend.components;
/**
 * @author <Butter>
 */
import javafx.scene.Node;

public class Component {
    // A Component contains a root reference to the FXML component.
    // This class is used to define simple components that do not need a separate controller class.
    // Unlike Page, this class should not be used to define layouts. But instead used to create small/simple components (e.g, buttons, links)

    protected Node root;

    public Component() {
    }

    public Component(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    };
}