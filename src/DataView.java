import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;

public class DataView extends View {

    HBox viewContainer;

    public DataView(Data data) {
        super(data, "Data");
    }

    @Override
    public Node createView() {
        viewContainer = new HBox();

        return viewContainer;
    }

    private void updateDataText(){

        ArrayList<Float> gradesList = new ArrayList<>(data.getParsedGrades());

        Collections.sort(gradesList);
        Collections.reverse(gradesList);

        int maxLen = (int) Math.floor((float)gradesList.size() / 4f);
        maxLen = Math.max(maxLen, 1);

        viewContainer.getChildren().clear();

        int remainder = gradesList.size() > 4? gradesList.size() % 4 : 0;
        System.out.println(remainder);

        int index = 0;
        for(int i = 0; i < 4; i ++ ) {
            VBox col = new VBox();
            viewContainer.getChildren().add(col);

            int shift = ((remainder > 0)? Math.min(1, remainder) : 0);
            if (remainder>0) remainder --;

            for(int j = 0;
                    j < maxLen + shift
                    && index < gradesList.size(); j ++) {
                col.getChildren().add(
                  new Label(gradesList.get(index) + "," )
                );
                index ++;
            }
        }

    }

    @Override
    public void onMount() {  updateDataText(); }

    @Override
    public void onDismount() {}

    @Override
    public void onDataUpdate() { updateDataText(); }
}