import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class DISTView extends View {

    VBox viewContainer;

    public DISTView(Data data) {
        super(data, "Distribution");
    }

    @Override
    public Node createView() {
        //box.getChildren().add();
        viewContainer = new VBox();
        return viewContainer;
    }

    public void distributionChart() {

        viewContainer.getChildren().clear();

        float A = 0;
        int aCount = 0;
        float B = 0;
        int bCount = 0;
        float C = 0;
        int cCount = 0;
        float D = 0;
        int dCount = 0;
        float F = 0;
        int fCount = 0;
        ArrayList<Float> parsedGrades = data.getParsedGrades();


        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final BarChart<Number, String> bc = new BarChart<Number, String>(xAxis, yAxis);


        xAxis.setTickLabelRotation(90);

        bc.setTitle("Grade Summary");
        xAxis.setLabel("Grade");
        yAxis.setLabel("Score");

        XYChart.Series series1 = new XYChart.Series();

        series1.setName("Amount of Each Grade");

        System.out.println(parsedGrades.size());

        for (int i = 0; i < parsedGrades.size(); i++) {
            if (parsedGrades.get(i) >= 90 && parsedGrades.get(i) <= 100) {
                A += parsedGrades.get(i);
                aCount++;
            }
            if (parsedGrades.get(i) >= 80 && parsedGrades.get(i) < 90) {
                B += parsedGrades.get(i);
                bCount++;
            }
            if (parsedGrades.get(i) >= 70 && parsedGrades.get(i) < 80) {
                C += parsedGrades.get(i);
                cCount++;
            }
            if (parsedGrades.get(i) >= 60 && parsedGrades.get(i) < 70) {
                D += parsedGrades.get(i);
                dCount++;
            }
            if (parsedGrades.get(i) < 60) {
                F += parsedGrades.get(i);
                fCount++;
            }
        }

        series1.getData().add(new XYChart.Data(A / aCount, "A"));
        series1.getData().add(new XYChart.Data(B / bCount, "B"));

        series1.getData().add(new XYChart.Data(C / cCount, "C"));

        series1.getData().add(new XYChart.Data(D / dCount, "D"));
        series1.getData().add(new XYChart.Data(F / fCount, "F"));


        bc.getData().addAll(series1);

        viewContainer.getChildren().add(bc);

    }

    @Override
    public void onMount() {
        distributionChart();
    }

    @Override
    public void onDismount() {

    }

    @Override
    public void onDataUpdate() {

        distributionChart();

    }
}

