import java.io.*;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private ArrayList<Integer> parsedGrades;

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
    public List<Integer> getParsedGrades() {  return Collections.unmodifiableList(parsedGrades); }
    public List<String> getAllEntries() {  return Collections.unmodifiableList(entries); }
    public List<Error> getErrors() {  return Collections.unmodifiableList(errorHistory); }
    public List<String> getInteractionHistory() {  return Collections.unmodifiableList(interactionHistory); }


    public Data(EventDispatcher dispatcher) {
        parsedGrades = new ArrayList<>();
        entries = new ArrayList<>();
        errorHistory = new ArrayList<>();
        interactionHistory = new ArrayList<>();
        this.dispatcher = dispatcher;
    }

    // Opens a file-browser, gets file contents, parses
    public void openLoadFile() {
        System.out.println("opening browser to load file...");
        // clear everything...

        // open file and pass in path
        parseFile("/Users/gannonclark/Desktop/JavaPrograms/360_Final/src/Test");

        // only if file existed
        dispatcher.dataUpdated(this);
    }

    // Opens a file-browser, gets file contents and appens
    public void openAppendFile() {
        // open file and parse
        parseFile("/Users/gannonclark/Desktop/JavaPrograms/360_Final/src/Test");

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

    private void addFullEntry(String entry){
        entries.add(entry);
        try {
            parsedGrades.add(parseEntry(entry));
        }
        catch (Exception ex) {
            System.out.println("error..");
            errorHistory.add(new Error(ErrorType.NAN, "Not a number"));
        }
    }

    private int parseEntry(String entry) {

        // throw on error
        return 0;
    }

    private void parseFile(String filePath) {
        // on error add error type?

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));


            String line = bufferedReader.readLine();

            while (line != null) {
                System.out.println(line);
                addFullEntry(line);
                line = bufferedReader.readLine();
            }

        }
        catch (FileNotFoundException e){
            System.out.println("no file found for this path:" + filePath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
