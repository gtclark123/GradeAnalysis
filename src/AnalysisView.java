import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

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

        entryCount.setText(Integer.toString(data.getAllEntries().size()));

        float total = 0;
        float min = 100;
        float max = 0;
        float maxValue = 0;
        int maxCount = 0;
        int middle = 0;
        int entries = 0;
        float med = 0;

        entries = data.getParsedGrades().size();

        for(int i = 0; i < data.getParsedGrades().size(); i++) {
            total = total + data.getParsedGrades().get(i);

            if(max < data.getParsedGrades().get(i)){
                max = data.getParsedGrades().get(i);
            }
            if(min > data.getParsedGrades().get(i)){
                min = data.getParsedGrades().get(i);
            }

            int count = 0;
            for (int j = 0; j < data.getParsedGrades().size(); j++) {
                if (data.getParsedGrades().get(j) == data.getParsedGrades().get(i)) {
                    count++;
                }
            }
                if (count > maxCount) {
                    maxCount = count;
                    maxValue = data.getParsedGrades().get(i);
                }

            middle = entries/2;
            if(entries%2 == 1){
                med = data.getParsedGrades().get(middle);
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
