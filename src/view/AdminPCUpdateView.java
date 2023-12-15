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

public class AdminPCUpdateView {
	private Stage primaryStage;
	private TableView<PC> table;
	private TextField idInput, condInput, condText;
	private Label idLbl, condLbl;
	private Integer uid;
	private Button backButton, updateButton;

	public AdminPCUpdateView(Stage primaryStage, Integer uid) {
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
		primaryStage.setTitle("PC Update");
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
        
        condLbl = new Label("PC Condition");
		condText = new TextField();

        backButton = new Button("Back");
        updateButton = new Button("Update");
        
        form.add(idLbl, 0, 0);
        form.add(idInput, 1, 0);
        form.add(condLbl, 0, 1);
        form.add(condText, 1, 1);
        form.add(backButton, 0, 2);
        form.add(updateButton, 1, 2);

        return form;
    }

	public TextField getCondText() {
		return condText;
	}

	public void setCondText(TextField condText) {
		this.condText = condText;
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

	public Button getBackButton() {
		return backButton;
	}

	public void setBackButton(Button backButton) {
		this.backButton = backButton;
	}

	public Button getUpdateButton() {
		return updateButton;
	}

	public void setUpdateButton(Button updateButton) {
		this.updateButton = updateButton;
	}

}
