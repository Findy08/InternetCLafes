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
import model.TransactionDetail;

public class CustomerHistoryView {
	
	private Stage primaryStage;
	private TableView<TransactionDetail> table;
	private Integer uid;
	private Button backButton;
	
	public CustomerHistoryView(Stage primaryStage, Integer uid) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
		this.uid = uid;
		VBox vbox = new VBox();
		table = createTransactionDetailDataTable();
		
		GridPane form = createTransactionDetailForm(table);
		VBox.setMargin(form, new Insets(20));
		vbox.getChildren().addAll(table, form);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Customer History View");
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

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public TableView<TransactionDetail> getTable() {
		return table;
	}

	public void setTable(TableView<TransactionDetail> table) {
		this.table = table;
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
	
}
