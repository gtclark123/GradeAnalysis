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

        Button loadFile = new Button("LoadFile");
        loadFile.setOnAction(new LoadFileOperation());


        Button button1 = new Button("Load File");
        HBox hbox1 = new HBox(button1);

        button1.setOnAction(new LoadFileOperation());
        hbox1.setSpacing(10);




        Button button2 = new Button("Append Data Set");
        hbox1.getChildren().add(button2);

        Button button3 = new Button("Export Report");
        hbox1.getChildren().add(button3);

        grid.getChildren().add(hbox1);



        TextField option = new TextField("Options");
        option.setAlignment(Pos.CENTER);
        option.setDisable(true);
        option.setText("option");
        option.setMaxWidth(100);
        VBox optionTextVbox = new VBox(option);
        grid.getChildren().add(optionTextVbox);


//        Button Data = new Button("Data");
//        HBox dataHbox = new HBox(Data);
//
//        Button Graph = new Button("Graph");
//        HBox graphHBox = new HBox(Graph);
//
//        Button Analysis = new Button("Analysis");
//        HBox analysisHBox = new HBox(Analysis);
//
//
//
//        dataHbox.setPadding(new Insets(40,20,20,200));
//        graphHBox.setPadding(new Insets(40,20,20,250));
//        analysisHBox.setPadding(new Insets(40,20,20,309));
//
//
//
//        grid.getChildren().add(dataHbox);
//
//        grid.getChildren().add(graphHBox);
//
//        grid.getChildren().add(analysisHBox);

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Data", new Label("Show all planes available"));
        tab1.setClosable(false);
        Tab tab2 = new Tab("Graph"  , new Label("Show all cars available"));
        tab2.setClosable(false);
        Tab tab3 = new Tab("Analysis" , new Label("Show all boats available"));
        tab2.setClosable(false);
        Tab tab4 = new Tab("Err Log", new Label("Show all planes available"));
        tab4.setClosable(false);


        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);


        VBox tabVBox = new VBox(tabPane);

        tabVBox.setPadding(new Insets(35,50,50,150));

        grid.getChildren().add(tabVBox);
        
        optionTextVbox.setPadding(new Insets(40, 0, 10, 0));


        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 0));

//        Button button = new Button("Click to Load text File");
//        HBox loadFileHBox = new HBox(button);
//        button.setOnAction(new LoadFileOperation());

        Button appendDataButton = new Button("Click to append Data");
        HBox appendDataHBox = new HBox(appendDataButton);

        appendDataButton.setOnAction(new AppendDataOperation());


//        loadFileHBox.setPadding(new Insets(10, 0, 10, 0));
        appendDataHBox.getChildren().add(appendDataTextField);

//        vBox.getChildren().add(loadFileHBox);
        vBox.getChildren().add(textArea);
        vBox.getChildren().add(appendDataHBox);
//
//        vBox.setPadding(new Insets(50, 10, 10, 10));

        tab1.setContent(vBox);
//        grid.getChildren().add(loadFileHBox);

        Scene scene = new Scene(grid);


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
        this.dataInSet += dataToAdd;

        System.out.println(dataInSet);

        textArea.setText(this.dataInSet);
    }
}
