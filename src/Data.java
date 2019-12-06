import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.*;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class Data {
    public interface EventDispatcher {
        void dataUpdated(Data data);
    }

    public enum ErrorType {
        NAN("Not A Number"),
        BELOWBOUNDS("Below Bounds"),
        ABOVEBOUNDS("Above Bounds");

        private String name;

        ErrorType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static public class Error {
        ErrorType type;
        String message;

        public Error(ErrorType type, String message) {
            this.type = type;
            this.message = message;
        }
    }

    // Grades that were correctly parsed, for calculation purposes.
    private ArrayList<Float> parsedGrades;

    // A list of all entries, for rendering purposes.
    private ArrayList<String> entries;

    private ArrayList<Error> errorHistory;

    private ArrayList<String> interactionHistory;

    // Dispatches when data has been updated or other
    private EventDispatcher dispatcher;

    // our bounds
    private int minBounds = 0;
    private int maxBounds = 100;


    // List getters (immutable)
    public List<Float> getParsedGrades() {
        return Collections.unmodifiableList(parsedGrades);
    }

    public List<String> getAllEntries() {
        return Collections.unmodifiableList(entries);
    }

    public List<Error> getErrors() {
        return Collections.unmodifiableList(errorHistory);
    }

    public List<String> getInteractionHistory() {
        return Collections.unmodifiableList(interactionHistory);
    }


    public Data(EventDispatcher dispatcher) {
        parsedGrades = new ArrayList<>();
        entries = new ArrayList<>();
        errorHistory = new ArrayList<>();
        interactionHistory = new ArrayList<>();
        this.dispatcher = dispatcher;
    }

    public String analysis(){

        String analysis = "";
        int count = 0;

        for(int i = 0; i < parsedGrades.size(); i++){

            count++;

        }

        analysis = "Number of Entries: " + count + "\n";

        analysis = analysis + "test";
        return analysis;

    }

    public String createData(){

        Collections.sort(parsedGrades, Collections.reverseOrder());
        String join = "";
        int counter = 0;

        for(int i = 0; i < parsedGrades.size(); i++){
                join = join + " " + getParsedGrades().get(i) + i;
                counter++;

                if (counter == 4){
                    join = join + System.lineSeparator() ;
                    counter = 0;
                }
        }

        return join;

    }

    public String errorLog(){

        String error = "hi";

        //for(int i = 0; errorHistory.get(i)!= null; i++){

       // error = error + errorHistory.get(i) + "\n";

        //}

        return error;

    }


    public BarChart createBarChart() {

        int A = 0;
        int B = 0;
        int C = 0;
        int D = 0;
        int F = 0;

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Grade Summary");
        xAxis.setLabel("Grade");
        yAxis.setLabel("Score");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Amount of Each Grade");

        System.out.println("here");

        System.out.println(parsedGrades.size());

        for (int i = 0; i < parsedGrades.size(); i++) {
            if (getParsedGrades().get(i) >= 90 && getParsedGrades().get(i) <= 100) {
                A++;
            }
            if (getParsedGrades().get(i) >= 80 && getParsedGrades().get(i) < 90) {
                B++;

            }
            if (getParsedGrades().get(i) >= 70 && getParsedGrades().get(i) < 80) {
                C++;
            }
            if (getParsedGrades().get(i) >= 60 && getParsedGrades().get(i) < 70) {
                D++;
            }
            if (getParsedGrades().get(i) < 60) {
                F++;
            }
        }
        series1.getData().add(new XYChart.Data("A", A));
        series1.getData().add(new XYChart.Data("B", B));

        series1.getData().add(new XYChart.Data("C", C));

        series1.getData().add(new XYChart.Data("D", D));
        series1.getData().add(new XYChart.Data("F", F));


        bc.getData().addAll(series1);


        return bc;
    }


    private void clearSession() {
        parsedGrades.clear();
        entries.clear();
        errorHistory.clear();
        interactionHistory.clear();
    }

    // Opens a file-browser, gets file contents, parses
    public void openLoadFile(String fileName) {
        System.out.println("opening browser to load file...");
        // clear everything...
        clearSession();
        // open file and pass in path

        parseFile(fileName);

        // only if file existed
        dispatcher.dataUpdated(this);
    }

    // Opens a file-browser, gets file contents and appens
    public void openAppendFile(String fileName) {
        // open file and parse
        parseFile(fileName);


        // only if file existed
        dispatcher.dataUpdated(this);
    }

    // open a file and write a report ...

    public void writeReportToFile() {
        System.out.println("Writing export?");
    }

    public void addManualEntry(String entry) {

        // only if there was data to be entered
        if (!entry.isEmpty()) {
            addFullEntry(entry);
            dispatcher.dataUpdated(this);
        }
    }

    public void addUIInteraction(String interaction) {
        interactionHistory.add(interaction);
    }

    private void addFullEntry(String entry) {
        entries.add(entry);
        try {
            parsedGrades.add(parseEntry(entry));
        } catch (Exception ex) {
            System.out.println("error..");
            errorHistory.add(new Error(ErrorType.NAN, "Not a number"));
        }
    }

    public void deleteEntry(String number) {
        for (int i = 0; i < parsedGrades.size(); i++) {
            if (parsedGrades.get(i) == Float.parseFloat(number)) {
                parsedGrades.remove(i);
                break;
            }
        }
    }

    private float parseEntry(String entry) {

        float num = 0;
        // throw on error
        try {
            num = Integer.parseInt(entry);
            // is an integer!
        } catch (NumberFormatException e) {
            // not an integer!
            System.out.println("Not a number");
        }

        return num;

    }

    private void parseFile(String filePath) {

        // on error add error type?

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));

            String line = bufferedReader.readLine();

            while (line != null) {
//                System.out.println(line);
                addFullEntry(line);
                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("no file found for this path:" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
