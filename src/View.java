import javafx.scene.Node;
import javafx.scene.layout.VBox;

public abstract class View {
    protected Data data;
    public String viewName;

    public View(Data data, String viewName) {
        this.data = data;
        this.viewName = viewName;
    }

    // implement this to create the view
    public abstract Node createView();

    // implement this to do anything when mounted again
    public abstract void onMount();

    // implement this to undo any data or other.
    public abstract void onDismount();

    // implement this to update your view when data changes
    public abstract void onDataUpdate();

}
