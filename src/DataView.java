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

    private void updateDataText(){

        ArrayList<String> gradesList = new ArrayList<>(data.getAllEntries());

        Collections.sort(gradesList, Collections.reverseOrder());
        String join = "";
        int counter = 0;
        int count = 0;
        int size = gradesList.size();
        int amount = size/4;
        int rem = size%4;
        int num = 0;

        //add another row if the remainder isn't 0
        if(rem != 0){
            num = amount + 1;
        }

        for(int i = 0; i < num; i++){
            counter = count;


            if((rem !=0) && ((i+1) == num)){

                for(int k = 0; k < rem; k++){

                    join = join + gradesList.get(counter) + " ";
                    counter = counter + amount;
                }

            }

            else {
                for (int j = 0; j < 4; j++) {
                    join = join + gradesList.get(counter) + " ";
                    counter = counter + amount;
                }
                count++;
                join += "\n";
            }
        }

        if (gradesList.isEmpty()) {
            join = "No data has been entered.";
        }

        column.setText(join);

    }

    @Override
    public void onMount() {  updateDataText(); }

    @Override
    public void onDismount() {}

    @Override
    public void onDataUpdate() { updateDataText(); }
}