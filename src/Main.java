import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

import java.awt.event.KeyEvent;
import java.util.Scanner;

import java.io.*;

public class Main extends Application {

    private Text actionStatus;
    int[] dataSet;

    private String dataInSet;

    private GridPane grid = new GridPane();

    private TextArea textArea = new TextArea();


    public static void main(String[] args) {

        Main main = new Main();

        String temporary = " ";

        System.out.println("Enter a number");

        temporary = main.LoadFile();

        System.out.println("Load File " + temporary);

        System.out.println("Append Data ");

        launch(args);

    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Welcome");

        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid);

        Button loadFile = new Button("LoadFile");
        loadFile.setOnAction(new LoadFileOperation());

        Button appendData = new Button("Addend Data");

        ToolBar toolBar = new ToolBar();

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Load File", new Label("Show all planes available"));
        tab1.setClosable(false);
        Tab tab2 = new Tab("Append Data Set", new Label("Show all cars available"));
        tab2.setClosable(false);
        Tab tab3 = new Tab("Export Report", new Label("Show all boats available"));
        tab3.setClosable(false);


        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        VBox vBox = new VBox(tabPane);

        grid.add(vBox, 0, 0);

        GridPane newGrid = new GridPane();

        newGrid.setVgap(10);
        newGrid.setHgap(10);
        newGrid.setPadding(new Insets(25, 25, 25, 25));

        Scene newScene = new Scene(newGrid);
        Button newButton = new Button("Test");

        VBox newvBox = new VBox();
        newvBox.setPadding(new Insets(10, 10, 50, 0));
        newvBox.setSpacing(10);

        Button button = new Button("Click to Load text File");
        button.setFont(Font.font("Amble CN", FontWeight.BOLD, 12));
        button.setOnAction(new LoadFileOperation());
        newvBox.getChildren().add(button);
        newvBox.getChildren().add(textArea);



        tab1.setContent(newvBox);



//        Button button1 = new Button("Button 1");
//        button1.setOnAction(new LoadFileOperation());
//        toolBar.getItems().add(button1);
//
//        Button button2 = new Button("Button 2");
//        toolBar.getItems().add(button2);
//
//        VBox vBox = new VBox(toolBar);
//
//
//        grid.add(vBox, 0, 0);
//
//        grid.add(loadFile, 0, 2);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private class LoadFileOperation implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            LoadFile();
        }
    }

    private String LoadFile() {


        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/Users/gannonclark/Desktop/JavaPrograms/360_Final/src/Test")));


            String line = bufferedReader.readLine();

            while (line != null) {
                System.out.println(line);
                dataInSet += line;
                line = bufferedReader.readLine();

            }
            textArea.setText(dataInSet);

            return dataInSet;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    private class AppendDataOperation implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            LoadFile();

        }
    }

    private void AppendData(String dataToAdd) {

    }
}