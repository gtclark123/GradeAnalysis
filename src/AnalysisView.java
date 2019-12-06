import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
<<<<<<< HEAD
=======
import javafx.scene.layout.HBox;
>>>>>>> f418204512d37f62bfa4c70e15f00e8f4e849b51
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Collections;

public class AnalysisView extends View {

=======

/***
 * @author Shannon
 */
public class AnalysisView extends View {

    VBox calculations;
    Label noData;
>>>>>>> f418204512d37f62bfa4c70e15f00e8f4e849b51
    VBox viewContainer;
    Label entryCount;
    Label high;
    Label low;
    Label mean;
    Label median;
    Label mode;

    public AnalysisView(Data data) {
        super(data, "Analysis");
    }

<<<<<<< HEAD
=======
    private HBox h(Node ...nodes) { return new HBox(nodes); }

>>>>>>> f418204512d37f62bfa4c70e15f00e8f4e849b51
    @Override
    public Node createView() {;
        viewContainer = new VBox();

        entryCount = new Label();
        high = new Label();
        low = new Label();
        mean = new Label();
        median = new Label();
        mode = new Label();
        viewContainer.getChildren().addAll(
<<<<<<< HEAD
                new Label("Entry Count:"), entryCount,
                new Label("High:"), high,
                new Label("Low:"), low,
                new Label("Mean:"), mean,
                new Label("Median:"), median,
                new Label("Mode:"), mode
=======
                noData = new Label("No data has been loaded"),
                calculations = new VBox(
                    h(new Label("Entry Count:\t"), entryCount),
                    h(new Label("High:\t"), high),
                    h(new Label("Low:\t"), low),
                    h(new Label("Mean:\t"), mean),
                    h(new Label("Median:\t"), median),
                    h(new Label("Mode:\t"), mode)
                )
>>>>>>> f418204512d37f62bfa4c70e15f00e8f4e849b51
        );

        return viewContainer;
    }

    private void updateAnalysisText() {

        ArrayList<Float> gradesList = new ArrayList<>(data.getParsedGrades());
<<<<<<< HEAD
        Collections.sort(gradesList);
=======

        if (gradesList.isEmpty()) {
            calculations.setVisible(false);
        } else {
            calculations.setVisible(true);
            noData.setManaged(false);
            noData.setVisible(false);
        }
>>>>>>> f418204512d37f62bfa4c70e15f00e8f4e849b51

        entryCount.setText(Integer.toString(data.getAllEntries().size()));

        float total = 0;
<<<<<<< HEAD
        float min = gradesList.get(0);
        float max = gradesList.get(0);
=======
        float min = 100;
        float max = 0;
>>>>>>> f418204512d37f62bfa4c70e15f00e8f4e849b51
        float maxValue = 0;
        int maxCount = 0;
        int middle = 0;
        int entries = 0;
        float med = 0;

        entries = gradesList.size();

        for(int i = 0; i < gradesList.size(); i++) {
            total = total + gradesList.get(i);

            if(max < gradesList.get(i)){
                max = gradesList.get(i);
            }
            if(min > gradesList.get(i)){
                min = gradesList.get(i);
            }

            int count = 0;
            for (int j = 0; j < gradesList.size(); j++) {
                if ((gradesList.get(j) - gradesList.get(i)) == 0) {
                    count++;
                }
            }
<<<<<<< HEAD
                if (count > maxCount) {
                    maxCount = count;
                    maxValue = gradesList.get(i);
                    System.out.println(maxValue);
                }
=======
            if (count > maxCount) {
                maxCount = count;
                maxValue = gradesList.get(i);
                System.out.println(maxValue);
            }
>>>>>>> f418204512d37f62bfa4c70e15f00e8f4e849b51

            middle = entries/2;
            if(entries%2 == 1){
                med = gradesList.get(middle);
            }

            else{
<<<<<<< HEAD
                med = (gradesList.get(middle-1) + gradesList.get(middle))/2;
=======
                med = (data.getParsedGrades().get(middle-1) + data.getParsedGrades().get(middle))/2;
>>>>>>> f418204512d37f62bfa4c70e15f00e8f4e849b51
            }

        }

        high.setText(Float.toString(max));
        low.setText(Float.toString(min));
        mean.setText(Float.toString(total/entries));
        median.setText(Float.toString(med));
        mode.setText(Float.toString(maxValue));

    }


<<<<<<< HEAD
    public void onDismount(){
        updateAnalysisText();

    }

    @Override
    public void onMount() {
        updateAnalysisText();
    }

    @Override
    public void onDataUpdate() {
        updateAnalysisText();
    }
}
=======
    public void onDismount(){}

    @Override
    public void onMount() { updateAnalysisText(); }

    @Override
    public void onDataUpdate() { updateAnalysisText(); }
}
>>>>>>> f418204512d37f62bfa4c70e15f00e8f4e849b51
