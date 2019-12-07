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

// counter++;
//
//         if (counter == 4) {
//         join = join + System.lineSeparator();
//         counter = 0;
//         }