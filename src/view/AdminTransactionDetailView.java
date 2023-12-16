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
import model.TransactionDetail;
import model.TransactionHeader;

public class AdminTransactionDetailView {
	
	private Stage primaryStage;
	private TableView<TransactionDetail> tdTable;
	private Integer uid;
	private Button backButton;
	
	public AdminTransactionDetailView(Stage primaryStage, Integer uid) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
		this.uid = uid;
		VBox vbox = new VBox();
		tdTable = createTransactionDetailDataTable();

		GridPane form = createTransactionDetailForm(tdTable);
		VBox.setMargin(form, new Insets(20));
		vbox.getChildren().addAll(tdTable, form);
		tdTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Admin History View");
		primaryStage.show();
	}
	
	private TableView<TransactionDetail> createTransactionDetailDataTable() {
	    TableView<TransactionDetail> table = new TableView<>();

	    TableColumn<TransactionDetail, Number> idColumn = new TableColumn<>("Transaction ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
        
        TableColumn<TransactionDetail, Number> pidColumn = new TableColumn<>("PC ID");
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
        
        TableColumn<TransactionDetail, String> customerColumn = new TableColumn<>("Customer Name");
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));

        TableColumn<TransactionDetail, String> dateColumn = new TableColumn<>("Booked Time");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("BookedTime"));

        table.getColumns().add(idColumn);
        table.getColumns().add(pidColumn);
        table.getColumns().add(customerColumn);
		table.getColumns().add(dateColumn);
		
        return table;
	}
	
	private GridPane createTransactionDetailForm(TableView<TransactionDetail> table) {
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        backButton = new Button("Back");
        form.add(backButton, 0, 0);

        return form;
	}
	
	public final Stage getPrimaryStage() {
		return primaryStage;
	}
	public final void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public final TableView<TransactionDetail> getTdTable() {
		return tdTable;
	}
	public final void setTdTable(TableView<TransactionDetail> tdTable) {
		this.tdTable = tdTable;
	}
	public final Integer getUid() {
		return uid;
	}
	public final void setUid(Integer uid) {
		this.uid = uid;
	}
	public final Button getBackButton() {
		return backButton;
	}
	public final void setBackButton(Button backButton) {
		this.backButton = backButton;
	}
	
}
