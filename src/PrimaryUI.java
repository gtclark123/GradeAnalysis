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

        Tab enterDataTab = new Tab("Enter Data");
        enterDataTab.setClosable(false);


        tabPane.getTabs().add(enterDataTab);
        HBox enterBox = new HBox(10);
        TextArea enterTextArea = new TextArea("");
        Button enterButton = new Button("Enter Data");

        enterButton.setOnAction(event -> {
            data.addManualEntry(enterTextArea.getText().toString());
        });

        enterBox.getChildren().addAll(
                new Label("Enter Data Here:"),
                enterTextArea,
                enterButton
        );

        enterDataTab.setContent(enterBox);

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


        Tab graphTab = new Tab("Bar Chart");
        graphTab.setClosable(false);

        Button createGraphButton = new Button("Create Graph");

        createGraphButton.setOnAction(event -> {
            graphTab.setContent(data.createBarChart());
        });

        graphTab.setContent(createGraphButton);

        graphTab.setOnSelectionChanged(event -> {
            graphTab.setContent(createGraphButton);
        });

        Tab createDistributionTab = new Tab("Distribution");
        createDistributionTab.setClosable(false);

        Button createDistributionButton = new Button();
        createDistributionButton.setOnAction(event -> {
            createDistributionTab.setContent(data.distributionChart());
        });


        tabPane.getTabs().add(graphTab);


        //Create error log Tab
        Tab errorTab = new Tab("Error Log");
        errorTab.setClosable(false);

        //add to tabPane
        tabPane.getTabs().add(errorTab);

        //Create analysis Tab
        Tab analysisTab = new Tab("Analysis");
        analysisTab.setClosable(false);

        HBox analysisBox = new HBox(10);
        TextArea text = new TextArea();
        text.setEditable(false);
        analysisBox.getChildren().addAll(
                new Label("Analysis"),
                text
        );

        analysisTab.setContent(analysisBox);

        //add to tabPane
        tabPane.getTabs().add(analysisTab);



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


