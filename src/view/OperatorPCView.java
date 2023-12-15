package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PC;

public class OperatorPCView {

	private Stage primaryStage;
	private TableView<PC> table;
	private Integer uid;
	private Button viewBookButton, cancelBookButton, finishBookButton, assignUserButton;

	public OperatorPCView(Stage primaryStage, Integer uid) {
		this.primaryStage = primaryStage;
		this.uid = uid;
		VBox vbox = new VBox();
		table = createPCDataTable();
		
		GridPane form = createPCForm(table);
		VBox.setMargin(form, new Insets(20));
		vbox.getChildren().addAll(table, form);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("PC");
		primaryStage.show();
	}
	
	private TableView<PC> createPCDataTable() {
        TableView<PC> table = new TableView<>();
        
        TableColumn<PC, Number> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));

        TableColumn<PC, String> condColumn = new TableColumn<>("Condition");
        condColumn.setCellValueFactory(new PropertyValueFactory<>("PC_Condition"));

        table.getColumns().add(idColumn);
		table.getColumns().add(condColumn);
		
        return table;
    }
	
	private GridPane createPCForm(TableView<PC> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);

        viewBookButton = new Button("View Bookings");
        cancelBookButton = new Button("Cancel Booking");
        finishBookButton = new Button("Finish Booking");
        assignUserButton = new Button("Assign User to New PC");
        
        form.add(viewBookButton, 0, 0);
        form.add(cancelBookButton, 1, 0);
        form.add(finishBookButton, 2, 0);
        form.add(assignUserButton, 3, 0);

        return form;
    }

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public TableView<PC> getTable() {
		return table;
	}

	public void setTable(TableView<PC> table) {
		this.table = table;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Button getViewBookButton() {
		return viewBookButton;
	}

	public void setViewBookButton(Button viewBookButton) {
		this.viewBookButton = viewBookButton;
	}

	public Button getCancelBookButton() {
		return cancelBookButton;
	}

	public void setCancelBookButton(Button cancelBookButton) {
		this.cancelBookButton = cancelBookButton;
	}

	public Button getFinishBookButton() {
		return finishBookButton;
	}

	public void setFinishBookButton(Button finishBookButton) {
		this.finishBookButton = finishBookButton;
	}

	public Button getAssignUserButton() {
		return assignUserButton;
	}

	public void setAssignUserButton(Button assignUserButton) {
		this.assignUserButton = assignUserButton;
	}

}
