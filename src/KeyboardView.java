import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;

public class KeyboardView extends View {

    VBox viewContainer;
    Button enterButton;
    TextArea enterTextArea;

    public KeyboardView(Data data) {
        super(data, "Keyboard Input");
    }

    @Override
    public Node createView() {
        //box.getChildren().add();
        viewContainer = new VBox(new Label("Keyboard View"));

        enterButton = new Button("Enter Data:");
        enterTextArea = new TextArea();

        enterButton.setOnAction(event -> {
            data.addManualEntry(enterTextArea.getText().toString());
        });

        viewContainer.getChildren().addAll(enterTextArea, enterButton);

        return viewContainer;
    }

    private void updateKeyboardText(){

    }

    @Override
    public void onMount() {
        // mounted ...
        updateKeyboardText();
        viewContainer.getChildren().add(new Label("mounted on to view"));
    }

    @Override
    public void onDismount() {
        // do something if necessary
        // data.removeXYZ
        updateKeyboardText();
        viewContainer.getChildren().add(new Label("removed from view"));
    }

    @Override
    public void onDataUpdate() {
        // for data.getAllEntries() ...
        // update columns?
        updateKeyboardText();

        viewContainer.getChildren().addAll(new Label("data was updated!"));
    }
}