import java.io.PrintWriter;
import java.io.*;
import java.util.*;

public class ReportMaker {

    Data data;
    public ReportMaker(Data data) { this.data = data; }

    public void writeReport(String path) {
        try
        {
            File file = new File(path);
            PrintWriter writer = new PrintWriter(file, "UTF-8");

            writer.println("Session Interaction History:\n");
            for(String interaction : data.getInteractionHistory())
                writer.println(interaction);

            writer.println("\n\nLogged session errors:");
            writer.print("Type:\t|\tMessage\n\n");
            for(Data.Error e : data.getErrors()) {
                writer.print(e.type);
                writer.print("\t\t");
                writer.print(e.message);
            }

            writer.println("\n\nRecorded Bounds Min:" + data.getBoundsMax());
            writer.println("Recorded Bounds Max:" + data.getBoundsMin());

            writer.println();

            // print analysis calculations
            printAnalysis(writer);

            writer.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
            data.addErrorToStack(Data.ErrorType.IO, e.getMessage());
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
            data.addErrorToStack(Data.ErrorType.IO, e.getMessage());
        }
    }

    private void printAnalysis(PrintWriter writer) {
        // Put some calculation output here...
        Analyzer analysis = (new Analyzer(data)).compute();
        writer.println("Computations: \n");

        writer.print("entries:");
        writer.println(Integer.toString(analysis.entries));

        writer.print("max:");
        writer.println(Float.toString(analysis.max));

        writer.print("min:");
        writer.println(Float.toString(analysis.min));

        writer.print("mean:");
        writer.println(Float.toString(analysis.total/analysis.entries));

        writer.print("median:");
        writer.println(Float.toString(analysis.med));

        writer.print("mode:");
        writer.println(Float.toString(analysis.mode));
    }

}