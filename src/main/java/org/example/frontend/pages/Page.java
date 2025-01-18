package org.example.frontend.pages;
/**
 * @author <Butter>
 */
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.example.frontend.components.Component;

public class Page extends Component {
    // Each Page contains a root Node, which specifies how the page should be drawn.
    // This class is mainly used for layouts. If you want to add functionality, consider using Component instead.
    // Override the getRoot() method to customize layout for each Page


    public Page() {
        super();
    }

    public Page(Node root) {
        super(root);
    }


}