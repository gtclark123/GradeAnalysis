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
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;


import java.io.*;

public class Main extends Application {

    private Text actionStatus;
    int[] dataSet;

    private String dataInSet = "";

    private GridPane grid = new GridPane();

    private TextArea textArea = new TextArea();

    private TextField appendDataTextField = new TextField();



    public static void main(String[] args) {

        Main main = new Main();

        String temporary;

        System.out.println("Enter a number");

        temporary = main.LoadFile();

        System.out.println("Load File " + temporary);

        System.out.println("Append Data ");

        launch(args);

    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Welcome");

        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid);

        Button loadFile = new Button("LoadFile");
        loadFile.setOnAction(new LoadFileOperation());

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

        VBox tabPaneVBox = new VBox(tabPane);
        VBox newVBox = new VBox();
        newVBox.setPadding(new Insets(10, 10, 10, 0));


        Button appendDataButton = new Button("Click to append Data");
        HBox appendDataHBox = new HBox(appendDataButton);


        Button button = new Button("Click to Load text File");
        HBox loadFileHBox = new HBox(button);
        button.setOnAction(new LoadFileOperation());


        grid.getChildren().add(tabPaneVBox);
        grid.getChildren().add(loadFileHBox);


        loadFileHBox.setPadding(new Insets(10, 0, 10, 0));

        loadFileHBox.getChildren().add(appendDataTextField);

        newVBox.getChildren().add(loadFileHBox);
        newVBox.getChildren().add(textArea);
        newVBox.getChildren().add(appendDataHBox);


        newVBox.setSpacing(10);

        tab1.setContent(newVBox);


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

            AppendData(appendDataTextField.getText());

        }
    }

    private void AppendData(String dataToAdd) {

    }
}
