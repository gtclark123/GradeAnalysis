import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;

public class ErrorView extends View {
    TableView errorTable;
    VBox viewContainer;

    public ErrorView(Data data) {
        super(data, "Errors");
    }

    @Override
    public Node createView() {
        viewContainer = new VBox();

        viewContainer.getChildren().add(errorTable = new TableView());

        TableColumn<String, Data.Error> column1 = new TableColumn<>("Error Type");
        column1.setCellValueFactory(new PropertyValueFactory<>("errorType"));
        TableColumn<String, Data.Error> column2 = new TableColumn<>("Message");
        column2.setCellValueFactory(new PropertyValueFactory<>("errorMessage"));
        errorTable.getColumns().addAll(column1, column2);

        return viewContainer;
    }

    private void updateErrorText() {
        errorTable.getItems().clear();
        errorTable.getItems().addAll(data.getErrors());
    }

    @Override
    public void onMount() {
        updateErrorText();
    }

    @Override
    public void onDismount() {
    }

    @Override
    public void onDataUpdate() {
        updateErrorText();
    }
}