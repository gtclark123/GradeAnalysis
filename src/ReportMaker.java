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

            // Put some calculation output here...

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

}