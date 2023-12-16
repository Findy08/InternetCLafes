package view;

import java.util.ArrayList;

import controller.TransactionController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.TransactionDetail;
import model.TransactionHeader;

public class AdminHistoryView {
	
	private Stage primaryStage;
	private TableView<TransactionHeader> thTable;
	private TableView<TransactionDetail> tdTable;
	private Integer uid;
	private Button backButton;
	
	public AdminHistoryView(Stage primaryStage, Integer uid) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
		this.uid = uid;
		VBox vbox = new VBox();
		thTable = createTransactionHeaderDataTable();

		GridPane form = createTransactionHeaderForm(thTable);
		VBox.setMargin(form, new Insets(20));
		vbox.getChildren().addAll(thTable, form);
		thTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Admin History View");
		primaryStage.show();
	}
	
	private TableView<TransactionHeader> createTransactionHeaderDataTable() {
	    TableView<TransactionHeader> table = new TableView<>();

	    TableColumn<TransactionHeader, Number> idColumn = new TableColumn<>("Transaction ID");
	    idColumn.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));

	    TableColumn<TransactionHeader, Number> sidColumn = new TableColumn<>("Staff ID");
	    sidColumn.setCellValueFactory(new PropertyValueFactory<>("StaffID"));

	    TableColumn<TransactionHeader, String> staffColumn = new TableColumn<>("Staff Name");
	    staffColumn.setCellValueFactory(new PropertyValueFactory<>("StaffName"));

	    TableColumn<TransactionHeader, String> dateColumn = new TableColumn<>("Transaction Date");
	    dateColumn.setCellValueFactory(new PropertyValueFactory<>("TransactionDate"));
	    
	    table.getColumns().add(idColumn);
	    table.getColumns().add(sidColumn);
	    table.getColumns().add(staffColumn);
		table.getColumns().add(dateColumn);

	    return table;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public TableView<TransactionHeader> getThTable() {
		return thTable;
	}

	public void setThTable(TableView<TransactionHeader> thTable) {
		this.thTable = thTable;
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

	private GridPane createTransactionHeaderForm(TableView<TransactionHeader> table) {
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        backButton = new Button("Back");
        form.add(backButton, 0, 0);

        return form;
	}
}
