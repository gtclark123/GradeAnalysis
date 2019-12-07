import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;

/***A
 * @author Shannon
 */
public class AnalysisView extends View {

    VBox calculations;
    Label noData;
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

    private HBox h(Node ...nodes) { return new HBox(nodes); }
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
            noData = new Label("No data has been loaded"),
            calculations = new VBox(
                h(new Label("Entry Count:\t"), entryCount),
                h(new Label("High:\t"), high),
                h(new Label("Low:\t"), low),
                h(new Label("Mean:\t"), mean),
                h(new Label("Median:\t"), median),
                h(new Label("Mode:\t"), mode)
            )
        );

        return viewContainer;
    }

    private void updateAnalysisText() {

        ArrayList<Float> gradesList = new ArrayList<>(data.getParsedGrades());
        Collections.sort(gradesList);

        if (gradesList.isEmpty()) {
            calculations.setVisible(false);
            return;
        } else {
            calculations.setVisible(true);
            noData.setManaged(false);
            noData.setVisible(false);
        }

        Analyzer analysis = (new Analyzer(data)).compute();
        entryCount.setText(Integer.toString(analysis.entries));
        high.setText(Float.toString(analysis.max));
        low.setText(Float.toString(analysis.min));
        mean.setText(Float.toString(analysis.total/analysis.entries));
        median.setText(Float.toString(analysis.med));
        mode.setText(Float.toString(analysis.mode));

    }

    @Override
    public void onDismount(){}

    @Override
    public void onMount() { updateAnalysisText(); }

    @Override
    public void onDataUpdate() {
        updateAnalysisText();
    }
}