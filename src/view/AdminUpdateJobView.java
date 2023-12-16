package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.PC;

public class AdminUpdateJobView {

    private Stage primaryStage;
    private TableView<Job> table;
    private Button backButton;
    private Integer uid;
	private Label idLbl;
	private TextField idInput;
	private Label userLbl;
	private TextField userInput;
	private Label statusLbl;
	private TextField statusInput;
	private Button updateButton;

    public AdminUpdateJobView(Stage primaryStage, Integer uid) {
        this.primaryStage = primaryStage;
        this.uid = uid;
        VBox vbox = new VBox();
        table = createJobTable();
        this.backButton = new Button("Back");        
		
		GridPane form = createJobForm(table);
		VBox.setMargin(form, new Insets(20));
		vbox.getChildren().addAll(table, form);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Update Job");
		primaryStage.show();
    }

    private TableView<Job> createJobTable() {
    	TableView<Job> table =  new TableView<>();
    	
        TableColumn<Job, Integer> jobIDColumn = new TableColumn<>("Job ID");
        jobIDColumn.setCellValueFactory(new PropertyValueFactory<>("Job_ID"));

        TableColumn<Job, Integer> userIDColumn = new TableColumn<>("User ID");
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));

        TableColumn<Job, Integer> pcIDColumn = new TableColumn<>("PC ID");
        pcIDColumn.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));

        TableColumn<Job, String> jobStatusColumn = new TableColumn<>("Job Status");
        jobStatusColumn.setCellValueFactory(new PropertyValueFactory<>("JobStatus"));

        table.getColumns().addAll(jobIDColumn, userIDColumn, pcIDColumn, jobStatusColumn);
        
        return table;
    }

    private GridPane createJobForm(TableView<Job> table) {
    	GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        idLbl = new Label("Job ID");
        idInput = new TextField();
        
        statusLbl = new Label("Job Status");
        statusInput = new TextField();

        updateButton = new Button("Update Job");
        
        form.add(idLbl, 0, 0);
        form.add(idInput, 1, 0);
        form.add(statusLbl, 0, 1);
        form.add(statusInput, 1, 1);  
        form.add(updateButton, 10, 2);
        form.add(backButton, 11, 2);

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

	public Label getIdLbl() {
		return idLbl;
	}

	public void setIdLbl(Label idLbl) {
		this.idLbl = idLbl;
	}

	public TextField getIdInput() {
		return idInput;
	}

	public void setIdInput(TextField idInput) {
		this.idInput = idInput;
	}

	public Label getUserLbl() {
		return userLbl;
	}

	public void setUserLbl(Label userLbl) {
		this.userLbl = userLbl;
	}

	public TextField getUserInput() {
		return userInput;
	}

	public void setUserInput(TextField userInput) {
		this.userInput = userInput;
	}

	public Label getStatusLbl() {
		return statusLbl;
	}

	public void setStatusLbl(Label statusLbl) {
		this.statusLbl = statusLbl;
	}

	public TextField getStatusInput() {
		return statusInput;
	}

	public void setStatusInput(TextField statusInput) {
		this.statusInput = statusInput;
	}

	public Button getUpdateButton() {
		return updateButton;
	}

	public void setUpdateButton(Button updateButton) {
		this.updateButton = updateButton;
	}
    
	
}

 
