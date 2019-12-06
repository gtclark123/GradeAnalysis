import com.sun.tools.doclets.internal.toolkit.Content;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PrimaryUI {

    private Data data;
    private View activeView = null;
    private TabPane viewPane = null;
    private Stage primaryStage;


    public void updateMountedView() {
        Platform.runLater(() -> {
            activeView.onDataUpdate();
            viewPane.requestLayout();
        });
    }

    public void mountUI(Stage primaryStage, Data data) {
        this.data = data;
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Grade Analysis");

        VBox mainView = new VBox();
        mainView.setPadding(new Insets(10, 10, 10, 10));

        mainView.getChildren().addAll(
                createToolbar(),
                createBoundsSection(),
                viewPane = createOptionsSection()
        );

        Scene scene = new Scene(mainView);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinWidth(640);
        primaryStage.setMinHeight(480);
    }

    private ToolBar createToolbar() {

        FileChooser fileChooser = new FileChooser();
        Button buttonLoad = new Button("Select File");

        buttonLoad.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            data.openLoadFile(selectedFile.toString());
            data.getAllEntries().forEach(System.out::println);

        });

        FileChooser appendFileChooser = new FileChooser();
        Button appendButtonLoad = new Button("Append File");

        appendButtonLoad.setOnAction(event -> {
            File appendSelectedFile = appendFileChooser.showOpenDialog(primaryStage);
            data.openAppendFile(appendSelectedFile.toString());
            data.getAllEntries().forEach(System.out::println);
        });



//        Button buttonAppend = new Button("Append");

        Button buttonExportReport = new Button("Export Report");
        buttonExportReport.setOnAction(ev -> data.writeReportToFile());


        // File toolbar
        ToolBar toolbar = new ToolBar();
        toolbar.getItems().addAll(
                buttonLoad,
                appendButtonLoad,
                buttonExportReport
        );

        return toolbar;
    }

    private TabPane createOptionsSection() {
        TabPane tabPane = new TabPane();
        View[] views = ViewFactory.createViews(data);
        for (View view : views) {
            Tab viewTab = new Tab(view.viewName, view.createView());
            viewTab.setClosable(false);
            tabPane.getTabs().add(viewTab);
        }

        Tab moreViewsTab = new Tab("<Add more views to ViewFactor>");
        moreViewsTab.setClosable(false);


        tabPane.getTabs().add(moreViewsTab);

        //Create delete Tab
        Tab deleteTab = new Tab("Delete");
        deleteTab.setClosable(false);

        //Add create text box and label
        HBox deleteBox = new HBox(10);
        deleteBox.setPadding(new Insets(10,0,10,0));

        Button deleteButton = new Button("Click to delete Button");

        TextField deleteTextField = new TextField("");

        deleteButton.setOnAction(event -> {
            data.deleteEntry(deleteTextField.getText());
            data.getParsedGrades().forEach(System.out::println);

        });
        deleteBox.getChildren().addAll(new Label("Enter a number to delete "),deleteTextField, deleteButton);

        deleteTab.setContent(deleteBox);

        //add to tabPane
        tabPane.getTabs().add(deleteTab);


        Tab graphTab = new Tab("Graph");
        graphTab.setClosable(false);

        Button createGraphButton = new Button("Create Graph");

        createGraphButton.setOnAction(event -> {
            graphTab.setContent(data.createBarChart());
        });




        graphTab.setContent(createGraphButton);

        tabPane.getTabs().add(graphTab);

        tabPane.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldIndex, newIndex) -> {
                    if (activeView != null) activeView.onDismount();
                    activeView = null;
                    if (newIndex.intValue() < views.length && newIndex.intValue() >= 0) {
                        this.activeView = views[newIndex.intValue()];
                        activeView.onMount();
                    }
                });

        // Init the active view stuff
        activeView = views[0];
        activeView.onMount();

        return tabPane;
    }

    private HBox createBoundsSection() {
        HBox boundsContainer = new HBox(5);
        boundsContainer.getChildren().addAll(
                new Label("min-bound:"),
                new TextField("0"),
                new Separator(),
                new Label("max-bound:"),
                new TextField("100")
        );
        return boundsContainer;
    }
}

//try {
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile));
//
//        String line = bufferedReader.readLine();
//        int i = 0;
//        while (line != null) {
//        entries.add(i, line);
//        System.out.println(line);
//        line = bufferedReader.readLine();
//        i++;
//        }
//
//        } catch (IOException ex) {
//        System.out.println("no file found for this path:" + ex);
//        }

//
//
//        series1.getData().add(new XYChart.Data("brazil", 20148.82));
//        series1.getData().add(new XYChart.Data("France", 10000));
//        series1.getData().add(new XYChart.Data("Italy", 35407.15));
//        series1.getData().add(new XYChart.Data("USA", 12000));
//
//        XYChart.Series series2 = new XYChart.Series();
//        series2.setName("2004");
//        series2.getData().add(new XYChart.Data("australia", 57401.85));
//        series2.getData().add(new XYChart.Data("brazil", 41941.19));
//        series2.getData().add(new XYChart.Data("France", 45263.37));
//        series2.getData().add(new XYChart.Data("Italy", 117320.16));
//        series2.getData().add(new XYChart.Data("USA", 14845.27));
//

