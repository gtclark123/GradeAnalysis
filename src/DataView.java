import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class DataView extends View {

    VBox viewContainer;

    public DataView(Data data) {
        super(data, "Data");
    }

    @Override
    public Node createView() {
        //box.getChildren().add();
        viewContainer = new VBox(new Label("Data View"));
        return viewContainer;
    }

    @Override
    public void onMount() {
        // mounted ...
        viewContainer.getChildren().add(new Label("mounted on to view"));
    }

    @Override
    public void onDismount() {
        // do something if necessary
        // data.removeXYZ
        viewContainer.getChildren().add(new Label("removed from view"));
    }

    @Override
    public void onDataUpdate() {
        // for data.getAllEntries() ...
        // update columns?
        TextArea text = new TextArea(data.createData());
        text.setEditable(false);

        viewContainer.getChildren().addAll(new Label("data was updated!"), text);
    }
}
