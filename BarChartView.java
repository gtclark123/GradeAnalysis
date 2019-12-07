import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.ArrayList;

class BarChartView extends View {

    VBox viewContainer;

    public BarChartView(Data data) {
        super(data, "Bar Chart");
    }

    @Override
    public Node createView() {
        viewContainer = new VBox();
        return viewContainer;
    }

    private void updateChart(){
        viewContainer.getChildren().clear();
        float A = 0;
        float B = 0;
        float C = 0;
        float D = 0;
        float F = 0;
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

        System.out.println("here");

        System.out.println(parsedGrades.size());

        for (int i = 0; i < parsedGrades.size(); i++) {
            if (parsedGrades.get(i) >= 90 && parsedGrades.get(i) <= 100) {
                A++;
            }
            if (parsedGrades.get(i) >= 80 && parsedGrades.get(i) < 90) {
                B++;

            }
            if (parsedGrades.get(i) >= 70 && parsedGrades.get(i) < 80) {
                C++;
            }
            if (parsedGrades.get(i) >= 60 && parsedGrades.get(i) < 70) {
                D++;
            }
            if (parsedGrades.get(i) < 60) {
                F++;
            }
        }

        //
        data.interactionHistory.add("Bar Chart was generated: ========================================== ");
        String barValue = "||F: ";

        for(int iterator = 0; iterator < F;iterator++)
        {
            barValue+= "*";
        }
        barValue+= "("+F+")";
        data.interactionHistory.add(barValue);

        barValue = "||D: ";

        for(int iterator = 0; iterator < D;iterator++)
        {
            barValue+= "*";
        }
        barValue+= "("+D+")";
        data.interactionHistory.add(barValue);

        barValue = "||C: ";

        for(int iterator = 0; iterator < C;iterator++)
        {
            barValue+= "*";
        }
        barValue+= "("+C+")";
        data.interactionHistory.add(barValue);

        barValue = "||B: ";

        for(int iterator = 0; iterator < B;iterator++)
        {
            barValue+= "*";
        }
        barValue+= "("+B+")";
        data.interactionHistory.add(barValue);

        barValue = "||A: ";

        for(int iterator = 0; iterator < A;iterator++)
        {
            barValue+= "*";
        }
        barValue+= "("+A+")";
        data.interactionHistory.add(barValue);
        barValue = "";

        //

        series1.getData().add(new XYChart.Data(A, "A"));
        series1.getData().add(new XYChart.Data(B, "B"));

        series1.getData().add(new XYChart.Data(C,"C"));

        series1.getData().add(new XYChart.Data(D,"D"));
        series1.getData().add(new XYChart.Data(F,"F"));


        bc.getData().addAll(series1);

        viewContainer.getChildren().add(bc);
    }

    @Override
    public void onMount() {  updateChart(); }

    @Override
    public void onDismount() {}

    @Override
    public void onDataUpdate() { updateChart(); }
}