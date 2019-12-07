import java.util.ArrayList;

public class Analyzer {

    float total = 0;
    float min;
    float max;
    float mode = 0;
    int maxCount = 0;
    int middle = 0;
    int entries = 0;
    float med = 0;
    Data data;

    public Analyzer(Data data) {  this.data = data; }

    public Analyzer compute() {

        ArrayList<Float> gradesList = data.getParsedGrades();

        min = gradesList.get(0);
        max = gradesList.get(0);

        entries = gradesList.size();

        for(int i = 0; i < gradesList.size(); i++) {
            total = total + gradesList.get(i);

            if(max < gradesList.get(i)){
                max = gradesList.get(i);
            }
            if(min > gradesList.get(i)){
                min = gradesList.get(i);
            }

            int count = 0;
            for (int j = 0; j < gradesList.size(); j++) {
                if ( Float.compare(gradesList.get(j), gradesList.get(i)) == 0) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mode = gradesList.get(i);
                System.out.println(mode);
            }

        }

        middle = entries/2;
        if (entries % 2 != 1) {
            middle = middle - 1;
        }
        med = gradesList.get(middle);

        return this;
    }
}
