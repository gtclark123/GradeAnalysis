import java.io.PrintWriter;
import javafx.scene.chart.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class ReportMaker {

    File file;
    PrintWriter writer;


    public ReportMaker(ArrayList<String> interactions) {

        try

        {

            file = new File("report.txt");
            writer = new PrintWriter(file, "UTF-8");
            writeReport(interactions);

        }

        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        //writeReport(interactions);

    }

    public void writeReport(ArrayList<String> interactions)
    {
        for (int i = 0; i < interactions.size(); i++)
            this.writer.println(interactions.get(i));

    System.out.println("done");
    writer.close();
    }

}