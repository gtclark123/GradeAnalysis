import javafx.scene.chart.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Data {
    public interface EventDispatcher {
        void dataUpdated(Data data);
    }

    public enum ErrorType {
        NAN("Not A Number"),
        IO("IO Error"),
        USAGE("Incorrect Use"),
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

        public String getErrorType() { return type.name; }
        public String getErrorMessage() { return message; }
    }


    static int ENTRY_UUID = 0;
    private class Entry {

        float parsedGrade = 0;
        String entry;
        boolean valid;
        boolean isNumber = true;
        int id;

        public Entry(String entry) {
            id = ENTRY_UUID ++;

            this.entry = entry;
            parseEntry(entry);
            updateValidity();
        }

        private void parseEntry(String entry) {
            try {
                parsedGrade = Float.parseFloat(entry);
            } catch (NumberFormatException e) {
                addErrorToStack(ErrorType.NAN, entry + " is not valid float value.");
                isNumber = false;
            }
        }

        public void updateValidity() {
            valid = isNumber && parsedGrade >= minBounds && parsedGrade <= maxBounds;
        }

    }

    // Contains information like entry validness or the parsed entry
    // or just the text...
    private ArrayList<Entry> entryList;

    // The error history
    private ArrayList<Error> errorHistory;
    // The interaction history
    private ArrayList<String> interactionHistory;

    // Dispatches when data has been updated or other
    private EventDispatcher dispatcher;

    // our bounds
    private float minBounds = 0;
    private float maxBounds = 100;

    public float getBoundsMax() { return maxBounds; }
    public float getBoundsMin() { return minBounds; }


    // List getters (immutable)
    public ArrayList<Float> getParsedGrades() {
        ArrayList<Float> list = new ArrayList<>();
        for (Entry entry : entryList)
            if (entry.valid) list.add(entry.parsedGrade);
        return list;
    }

    public ArrayList<String> getAllEntries() {
        ArrayList<String> list = new ArrayList<>();
        for (Entry entry : entryList) {
            if (entry.valid)
                list.add(entry.entry);
            else
                list.add(entry.entry +"[Err]");
        }
        return list;
    }

    public List<Entry> getEntryObjects() {
        return Collections.unmodifiableList(entryList);
    }

    public List<Error> getErrors() {
        return Collections.unmodifiableList(errorHistory);
    }

    public List<String> getInteractionHistory() {
        return Collections.unmodifiableList(interactionHistory);
    }


    public Data(EventDispatcher dispatcher) {
        entryList = new ArrayList<>();
        errorHistory = new ArrayList<>();
        interactionHistory = new ArrayList<>();
        this.dispatcher = dispatcher;
    }

    public void deleteEntry(int deleteID) {
        if (entryList.removeIf(x->x.id==deleteID)) dispatcher.dataUpdated(this);
    }

    public void deleteEntry(Entry deletedEntry) {
        if (entryList.remove(deletedEntry)) dispatcher.dataUpdated(this);
    }

    // Last resort...
    public void deleteEntry(String deleteString) {
        if (entryList.removeIf(x->x.entry == deleteString)) dispatcher.dataUpdated(this);
    }

    public BarChart distributionChart(){
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
        ArrayList<Float> parsedGrades = this.getParsedGrades();


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
                A += getParsedGrades().get(i);
                aCount++;
            }
            if (parsedGrades.get(i) >= 80 && parsedGrades.get(i) < 90) {
                B += getParsedGrades().get(i);
                bCount++;
            }
            if (parsedGrades.get(i) >= 70 && parsedGrades.get(i) < 80) {
                C += getParsedGrades().get(i);
                cCount++;
            }
            if (parsedGrades.get(i) >= 60 && parsedGrades.get(i) < 70) {
                D += getParsedGrades().get(i);
                dCount++;
            }
            if (parsedGrades.get(i) < 60) {
                F += getParsedGrades().get(i);
                fCount++;
            }
        }

        series1.getData().add(new XYChart.Data(A/aCount, "A"));
        series1.getData().add(new XYChart.Data(B/bCount, "B"));

        series1.getData().add(new XYChart.Data(C/cCount,"C"));

        series1.getData().add(new XYChart.Data(D/dCount,"D"));
        series1.getData().add(new XYChart.Data(F/fCount,"F"));


        bc.getData().addAll(series1);


        return bc;

    }

    private void clearSession() {
        entryList.clear();
        errorHistory.clear();
        interactionHistory.clear();
    }

    // Opens a file-browser, gets file contents, parses
    public void openLoadFile(String fileName) {
        // clear everything...
        clearSession();
        // open file and pass in path

        if (parseFile(fileName)) dispatcher.dataUpdated(this);
    }

    // Opens a file-browser, gets file contents and appens
    public void openAppendFile(String fileName) {
        // open file and parse
        if (parseFile(fileName)) dispatcher.dataUpdated(this);
    }

    // open a file and write a report ...

    public void writeReportToFile() {
        System.out.println("Writing export?");
    }

    public void addManualEntry(String entry) {
        addFullEntry(entry);
        dispatcher.dataUpdated(this);
    }

    public void addUIInteraction(String interaction) {
        interactionHistory.add(interaction);
    }

    public void updateBounds(String low, String high) {
        float newLow = minBounds, newHigh = maxBounds;
        try {
            newLow = Float.parseFloat(low);
        } catch (Exception ignore) {
            addErrorToStack(ErrorType.USAGE, "Min bound-" + low + " is not valid.");
        }
        try {
            newHigh = Float.parseFloat(high);
        } catch (Exception ignore) {
            addErrorToStack(ErrorType.USAGE, "Max bound-" + high + " is not valid.");
        }
        updateBounds(newLow, newHigh);
    }

    public void updateBounds(float low, float high) {
        // do the ol' swapparoo.
        if (low>high) {
            float temp = low;
            low = high;
            high = temp;
            addErrorToStack(ErrorType.USAGE, "Bounds shouldn't be flipped.");
        }

        minBounds = low;
        maxBounds = high;

        // Reparse ...
        validateAllEntries();
    }

    private void validateAllEntries() {
        entryList.forEach(Entry::updateValidity);
        dispatcher.dataUpdated(this);
    }

    private void addFullEntry(String entry) {
        entry = entry.trim();
        if (!entry.isEmpty()) {
            entryList.add(new Entry(entry));
        }
    }


    private void addErrorToStack(ErrorType type, String msg) { errorHistory.add(new Error(type, msg)); }

    private void parseCSV(BufferedReader reader) {
        try {
            String line = reader.readLine();
            while (line != null) {
                for(String val : line.split(",")) addFullEntry(val);
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            addErrorToStack(ErrorType.IO, "Error parsing from buffer reader.");
        }
    }

    private void parseLines(BufferedReader reader) {
        try {
            String line = reader.readLine();
            while (line != null) {
                addFullEntry(line);
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            addErrorToStack(ErrorType.IO, "Error parsing from buffer reader.");
        }
    }

    // returns true if the file was actually parsed
    private boolean parseFile(String filePath) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));

            if (filePath.endsWith(".csv"))
                parseCSV(bufferedReader);
            else
                parseLines(bufferedReader);

            bufferedReader.close();

            return true;
        } catch (FileNotFoundException e) {
            addErrorToStack(ErrorType.IO, e.getMessage());
            return false;
        } catch (IOException e) {
            addErrorToStack(ErrorType.IO, e.getMessage());
            return false;
        }
    }
}

