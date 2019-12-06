import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class AnalysisView extends View {

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
                new Label("Entry Count:"), entryCount,
                new Label("High:"), high,
                new Label("Low:"), low,
                new Label("Mean:"), mean,
                new Label("Median:"), median,
                new Label("Mode:"), mode
        );

        return viewContainer;
    }

    private void updateAnalysisText() {

        ArrayList<Float> gradesList = new ArrayList<>(data.getParsedGrades());

        entryCount.setText(Integer.toString(data.getAllEntries().size()));

        float total = 0;
        float min = 100;
        float max = 0;
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
                if (count > maxCount) {
                    maxCount = count;
                    maxValue = gradesList.get(i);
                    System.out.println(maxValue);
                }

            middle = entries/2;
            if(entries%2 == 1){
                med = gradesList.get(middle);
            }

            else{
                med = (data.getParsedGrades().get(middle-1) + data.getParsedGrades().get(middle))/2;
            }

        }

        high.setText(Float.toString(max));
        low.setText(Float.toString(min));
        mean.setText(Float.toString(total/entries));
        median.setText(Float.toString(med));
        mode.setText(Float.toString(maxValue));

    }


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
