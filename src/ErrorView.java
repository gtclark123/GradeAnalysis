import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;

public class ErrorView extends View {

    VBox viewContainer;
    Label error;

    public ErrorView(Data data) {
        super(data, "Error");
    }

    @Override
    public Node createView() {
        //box.getChildren().add();
        viewContainer = new VBox(new Label("Error View"));

        error = new Label();
        viewContainer.getChildren().add(error);

        return viewContainer;
    }

    private void updateErrorText(){

        error.setText(data.getErrors().toString());
        error.setText("test");
    }

    @Override
    public void onMount() {
        // mounted ...
        updateErrorText();
        viewContainer.getChildren().add(new Label("mounted on to view"));
    }

    @Override
    public void onDismount() {
        // do something if necessary
        // data.removeXYZ
        updateErrorText();
        viewContainer.getChildren().add(new Label("removed from view"));
    }

    @Override
    public void onDataUpdate() {
        // for data.getAllEntries() ...
        // update columns?
        updateErrorText();

        viewContainer.getChildren().addAll(new Label("data was updated!"));
    }
}
