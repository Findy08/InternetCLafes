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

public class BookingsView {
	
	private Stage primaryStage;
	private TableView<PCBook> table;
	private Label idLbl, dateLbl;
	private TextField idInput, dateInput;
	private Button backButton, finishButton, cancelButton;
	private Integer uid;

	public BookingsView(Stage primaryStage, Integer uid) {
		this.primaryStage = primaryStage;
		this.uid = uid;		
		VBox vbox = new VBox();
		table = createAllPCBookTable();
		
		GridPane form = createAllPCBookForm(table);
		VBox.setMargin(form, new Insets(20));
		vbox.getChildren().addAll(table, form);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("View Bookings");
		primaryStage.show();
	}
	
	private TableView<PCBook> createAllPCBookTable() {
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

	private GridPane createAllPCBookForm(TableView<PCBook> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        idLbl = new Label("Book ID to Cancel");
        idInput = new TextField();
        dateLbl = new Label("Book Date to Finish");
        dateInput = new TextField();
        
        finishButton = new Button("Finish Booking");
        cancelButton = new Button("Cancel Booking");
        backButton = new Button("Back");
        
        form.add(idLbl, 0, 0);
        form.add(idInput, 1, 0);
        form.add(dateLbl, 0, 1);
        form.add(dateInput, 1, 1);
        form.add(finishButton, 0, 2);
        form.add(cancelButton, 1, 2);
        form.add(backButton, 2, 2);

        return form;
    }

	public Label getIdLbl() {
		return idLbl;
	}

	public void setIdLbl(Label idLbl) {
		this.idLbl = idLbl;
	}

	public Label getDateLbl() {
		return dateLbl;
	}

	public void setDateLbl(Label dateLbl) {
		this.dateLbl = dateLbl;
	}

	public TextField getDateInput() {
		return dateInput;
	}

	public void setDateInput(TextField dateInput) {
		this.dateInput = dateInput;
	}

	public TextField getIdInput() {
		return idInput;
	}

	public void setIdInput(TextField idInput) {
		this.idInput = idInput;
	}

	public Button getFinishButton() {
		return finishButton;
	}

	public void setFinishButton(Button finishButton) {
		this.finishButton = finishButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public TableView<PCBook> getTable() {
		return table;
	}

	public void setTable(TableView<PCBook> table) {
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
