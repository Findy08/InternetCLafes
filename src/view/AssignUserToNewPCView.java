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
import model.PCBook;

public class AssignUserToNewPCView {
	
	private Stage primaryStage;
	private TableView<PCBook> table;
	private Integer uid;
	private Label idLbl, pcLbl;
	private TextField idInput, pcInput;
	private Button backButton, updateButton;
	
	public AssignUserToNewPCView(Stage primaryStage, Integer uid) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
		this.uid = uid;
		
		VBox vbox = new VBox();
		table = createBookingDataTable();
		
		GridPane form = createAssignForm(table);
		VBox.setMargin(form, new Insets(20));
		vbox.getChildren().addAll(table, form);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Assign User");
		primaryStage.show();
	}

	private TableView<PCBook> createBookingDataTable() {
		TableView<PCBook> table = new TableView<>();
        
        TableColumn<PCBook, Number> idColumn = new TableColumn<>("Book ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("BookID"));

        TableColumn<PCBook, Number> pidColumn = new TableColumn<>("PC ID");
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
        
        TableColumn<PCBook, Number> uidColumn = new TableColumn<>("User ID");
        uidColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        
        TableColumn<PCBook, String> dateColumn = new TableColumn<>("Booked Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("BookedDate"));

        table.getColumns().add(idColumn);
        table.getColumns().add(pidColumn);
        table.getColumns().add(uidColumn);
        table.getColumns().add(dateColumn);
		
        return table;
	}

	private GridPane createAssignForm(TableView<PCBook> table2) {
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        idLbl = new Label("Target Book ID");
        idInput = new TextField();
        pcLbl = new Label("New PC to assign");
        pcInput = new TextField();
        
        updateButton = new Button("Update");
        backButton = new Button("Back");
        
        form.add(idLbl, 0, 0);
        form.add(idInput, 1, 0);
        form.add(pcLbl, 0, 1);
        form.add(pcInput, 1, 1);
        form.add(updateButton, 0, 2);
        form.add(backButton, 1, 2);

        return form;
	}

	public final Stage getPrimaryStage() {
		return primaryStage;
	}

	public final void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public final TableView<PCBook> getTable() {
		return table;
	}

	public final void setTable(TableView<PCBook> table) {
		this.table = table;
	}

	public final Integer getUid() {
		return uid;
	}

	public final void setUid(Integer uid) {
		this.uid = uid;
	}

	public final Label getIdLbl() {
		return idLbl;
	}

	public final void setIdLbl(Label idLbl) {
		this.idLbl = idLbl;
	}

	public final Label getPcLbl() {
		return pcLbl;
	}

	public final void setPcLbl(Label pcLbl) {
		this.pcLbl = pcLbl;
	}

	public final TextField getIdInput() {
		return idInput;
	}

	public final void setIdInput(TextField idInput) {
		this.idInput = idInput;
	}

	public final TextField getPcInput() {
		return pcInput;
	}

	public final void setPcInput(TextField pcInput) {
		this.pcInput = pcInput;
	}

	public final Button getBackButton() {
		return backButton;
	}

	public final void setBackButton(Button backButton) {
		this.backButton = backButton;
	}

	public final Button getUpdateButton() {
		return updateButton;
	}

	public final void setUpdateButton(Button updateButton) {
		this.updateButton = updateButton;
	}
	
	

}
