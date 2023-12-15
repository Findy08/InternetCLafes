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

public class CompleteJobView {

	private Stage primaryStage;
	private TableView<Job> table;
	private TextField idInput;
	private Label idLbl;
	private Button backButton, completeButton;
	private Integer uid;

	public CompleteJobView(Stage primaryStage, Integer uid) {
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
        
        idLbl = new Label("PC ID to Complete");
        idInput = new TextField();
        
        backButton = new Button("Back");
        completeButton = new Button("Complete Job");
        
        form.add(idLbl, 0, 0);
        form.add(idInput, 1, 0);
        form.add(completeButton, 0, 1);
        form.add(backButton, 1, 1);

        return form;
    }

	public TextField getIdInput() {
		return idInput;
	}

	public void setIdInput(TextField idInput) {
		this.idInput = idInput;
	}

	public Label getIdLbl() {
		return idLbl;
	}

	public void setIdLbl(Label idLbl) {
		this.idLbl = idLbl;
	}

	public Button getCompleteButton() {
		return completeButton;
	}

	public void setCompleteButton(Button completeButton) {
		this.completeButton = completeButton;
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
