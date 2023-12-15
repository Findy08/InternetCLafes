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
import model.PC;

public class AdminPCView {
	private Stage primaryStage;
	private TableView<PC> table;
	private TextField idInput, condInput;
	private Label idLbl, condLbl;
	private Integer uid;
	private Button addButton, deleteButton, updateButton, reportButton;

	public AdminPCView(Stage primaryStage, Integer uid) {
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
        
        idLbl = new Label("PC ID");
        idInput = new TextField();

        addButton = new Button("Add");
        deleteButton = new Button("Delete");
        updateButton = new Button("Update");
        reportButton = new Button("Report");
        
        form.add(idLbl, 0, 0);
        form.add(idInput, 1, 0);
        form.add(addButton, 0, 1);
        form.add(deleteButton, 1, 1);
        form.add(updateButton, 10, 1);
        form.add(reportButton, 11, 1);
        return form;
    }

	public Button getReportButton() {
		return reportButton;
	}

	public void setReportButton(Button reportButton) {
		this.reportButton = reportButton;
	}

	public Button getUpdateButton() {
		return updateButton;
	}

	public void setUpdateButton(Button updateButton) {
		this.updateButton = updateButton;
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

	public TextField getIdInput() {
		return idInput;
	}

	public void setIdInput(TextField idInput) {
		this.idInput = idInput;
	}

	public TextField getCondInput() {
		return condInput;
	}

	public void setCondInput(TextField condInput) {
		this.condInput = condInput;
	}

	public Label getIdLbl() {
		return idLbl;
	}

	public void setIdLbl(Label idLbl) {
		this.idLbl = idLbl;
	}

	public Label getCondLbl() {
		return condLbl;
	}

	public void setCondLbl(Label condLbl) {
		this.condLbl = condLbl;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Button getAddButton() {
		return addButton;
	}

	public void setAddButton(Button addButton) {
		this.addButton = addButton;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(Button deleteButton) {
		this.deleteButton = deleteButton;
	}


}
