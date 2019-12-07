import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;

public class DataView extends View {

    VBox viewContainer;
    Label column;

    public DataView(Data data) {
        super(data, "Data");
    }

    @Override
    public Node createView() {
        viewContainer = new VBox();

        column = new Label();
        viewContainer.getChildren().add(column);

        return viewContainer;
    }

    private void updateDataText() {

        ArrayList<String> gradesList = new ArrayList<>(data.getAllEntries());

        Collections.sort(gradesList);
        String join = "";
        int counter = 0;

        for (int i = 0; i < gradesList.size(); i++) {
            join = join + " " + gradesList.get(i);
            counter++;

            if (counter == 4) {
                join = join + System.lineSeparator();
                counter = 0;
            }
        }

        if (gradesList.isEmpty()) {
            join = "No data has been entered.";
        }

        column.setText(join);

    }

    @Override
    public void onMount() {
        updateDataText();
    }

    @Override
    public void onDismount() {
    }

    @Override
    public void onDataUpdate() {
        updateDataText();
    }
}