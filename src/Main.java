import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    ReportMaker.writeReport();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        PrimaryUI ui = new PrimaryUI();
        Data masterData = new Data(data -> ui.updateMountedView() );
        ui.mountUI(primaryStage, masterData);
    }
}

