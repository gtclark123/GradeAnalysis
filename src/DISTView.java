import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class DISTView extends View {

    VBox viewContainer;

    public DISTView(Data data) {
        super(data, "Distribution");
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
