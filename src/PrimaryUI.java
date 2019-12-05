import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class PrimaryUI {
    private Data data;
    private View activeView = null;
    private TabPane viewPane = null;
    public Stage primaryStage;

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
//        Button buttonLoad = new Button("Load");
//        buttonLoad.setOnAction(ev -> data.openLoadFile() );


        FileChooser fileChooser = new FileChooser();
        Button buttonLoad = new Button("Select File");
        buttonLoad.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(new PrimaryUI().primaryStage);

            try (Scanner scanner = new Scanner(selectedFile)) {

                while (scanner.hasNext())
                    System.out.println(scanner.next());

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        });



        Button buttonAppend = new Button("Append");
        buttonAppend.setOnAction(ev -> data.openAppendFile());

        Button buttonExportReport = new Button("Export Report");
        buttonExportReport.setOnAction(ev -> data.writeReportToFile());

        // File toolbar
        ToolBar toolbar = new ToolBar();
        toolbar.getItems().addAll(
                buttonLoad,
                buttonAppend,
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

        tabPane.getTabs().add(new Tab("<Add more views to ViewFactor>", null));

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
