package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Job;

public class TechnicianJobView {

	private Stage primaryStage;
	private TableView<Job> table;
	private Button backButton;
	private Integer uid;

	public TechnicianJobView(Stage primaryStage, Integer uid) {
		this.primaryStage = primaryStage;
		this.uid = uid;		
		VBox vbox = new VBox();
		table = createAllJobTable();
		
		GridPane form = createAllJobForm(table);
		VBox.setMargin(form, new Insets(20));
		vbox.getChildren().addAll(table, form);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("View Technician Job");
		primaryStage.show();
	}
	
	private TableView<Job> createAllJobTable() {
        TableView<Job> table = new TableView<>();
        
        TableColumn<Job, Number> idColumn = new TableColumn<>("PC ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));

        TableColumn<Job, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("JobStatus"));

        table.getColumns().add(idColumn);
        table.getColumns().add(statusColumn);
		
        return table;
    }

	private GridPane createAllJobForm(TableView<Job> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        backButton = new Button("Back");
        
        form.add(backButton, 0, 0);

        return form;
    }

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public TableView<Job> getTable() {
		return table;
	}

	public void setTable(TableView<Job> table) {
		this.table = table;
	}

	public Button getBackButton() {
		return backButton;
	}

	public void setBackButton(Button backButton) {
		this.backButton = backButton;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

}
