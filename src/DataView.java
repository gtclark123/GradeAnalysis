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
        //box.getChildren().add();
        viewContainer = new VBox(new Label("Data View"));

        column = new Label();
        viewContainer.getChildren().add(column);

        return viewContainer;
    }

    private void updateDataText(){

        ArrayList<Float> gradesList = new ArrayList<>(data.getParsedGrades());

        Collections.sort(gradesList,Collections.reverseOrder());
        String join = "";
        int counter = 0;

        for(int i = 0; i < gradesList.size(); i++){
            join = join + " " + gradesList.get(i);
            counter++;

            if (counter == 4){
                join = join + System.lineSeparator() ;
                counter = 0;
            }
        }

        column.setText(join);

    }

    @Override
    public void onMount() {
        // mounted ...
        updateDataText();
        viewContainer.getChildren().add(new Label("mounted on to view"));
    }

    @Override
    public void onDismount() {
        // do something if necessary
        // data.removeXYZ
        updateDataText();
        viewContainer.getChildren().add(new Label("removed from view"));
    }

    @Override
    public void onDataUpdate() {
        // for data.getAllEntries() ...
        // update columns?
        updateDataText();

        viewContainer.getChildren().addAll(new Label("data was updated!"));
    }
}
