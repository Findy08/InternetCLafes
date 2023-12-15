package view;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class CustomerPCView {
	
	private Stage primaryStage;
	private TableView<PC> table;
	private TextField idInput, condInput;
	private Integer uid;
	private Button historyButton, bookButton, reportButton;

	public CustomerPCView(Stage primaryStage, Integer uid) {
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

        historyButton = new Button("History");
        bookButton = new Button("Book");
        reportButton = new Button("Report");
        form.add(historyButton, 0, 1);
        form.add(bookButton, 1, 1);
        form.add(reportButton, 2, 1);

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

	public Button getHistoryButton() {
		return historyButton;
	}

	public void setHistoryButton(Button historyButton) {
		this.historyButton = historyButton;
	}

	public Button getBookButton() {
		return bookButton;
	}

	public void setBookButton(Button bookButton) {
		this.bookButton = bookButton;
	}

	public Button getReportButton() {
		return reportButton;
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

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setReportButton(Button reportButton) {
		this.reportButton = reportButton;
	}

}
