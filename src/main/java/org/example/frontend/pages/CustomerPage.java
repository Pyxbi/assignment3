package org.example.frontend.pages;

import javafx.scene.Node;
import org.example.frontend.components.customer.CustomerAdd;

public class CustomerPage extends Page {
    @Override
    public Node getRoot() {
        return new CustomerAdd().getRoot();
    }
}
