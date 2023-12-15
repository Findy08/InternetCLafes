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

public class BookPCView {

	private Stage primaryStage;
	private TableView<PCBook> table;
	private Label pidLbl, dateLbl;
	private TextField pidInput, dateInput;
	private Integer uid;
	private Button bookButton, backButton;

	public BookPCView(Stage primaryStage, Integer uid) {
		this.primaryStage = primaryStage;
		this.uid = uid;
		VBox vbox = new VBox();
		table = createPCBookDataTable();
		
		GridPane form = createPCBookForm(table);
		VBox.setMargin(form, new Insets(20));
		vbox.getChildren().addAll(table, form);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("PC Booking");
		primaryStage.show();
	}
	
	private TableView<PCBook> createPCBookDataTable() {
        TableView<PCBook> table = new TableView<>();
        
        TableColumn<PCBook, Number> idColumn = new TableColumn<>("Book ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        
        TableColumn<PCBook, Number> pidColumn = new TableColumn<>("PC ID");
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));

        TableColumn<PCBook, String> dateColumn = new TableColumn<>("Booked Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("BookedDate"));

        table.getColumns().add(idColumn);
        table.getColumns().add(pidColumn);
		table.getColumns().add(dateColumn);
		
        return table;
    }
	
	private GridPane createPCBookForm(TableView<PCBook> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        pidLbl = new Label("PC ID");
        pidInput = new TextField();
        
        dateLbl = new Label("Booked Date (yyyy-mm-dd)");
        dateInput = new TextField();

        bookButton = new Button("Book");
        backButton = new Button("Back");
        
        form.add(pidLbl, 0, 0);
        form.add(pidInput, 1, 0);
        form.add(dateLbl, 0, 1);
        form.add(dateInput, 1, 1);
        form.add(bookButton, 0, 2);
        form.add(backButton, 1, 2);

        return form;
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

	public Label getPidLbl() {
		return pidLbl;
	}

	public void setPidLbl(Label pidLbl) {
		this.pidLbl = pidLbl;
	}

	public Label getDateLbl() {
		return dateLbl;
	}

	public void setDateLbl(Label dateLbl) {
		this.dateLbl = dateLbl;
	}

	public TextField getPidInput() {
		return pidInput;
	}

	public void setPidInput(TextField pidInput) {
		this.pidInput = pidInput;
	}

	public TextField getDateInput() {
		return dateInput;
	}

	public void setDateInput(TextField dateInput) {
		this.dateInput = dateInput;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Button getBookButton() {
		return bookButton;
	}

	public void setBookButton(Button bookButton) {
		this.bookButton = bookButton;
	}

	public Button getBackButton() {
		return backButton;
	}

	public void setBackButton(Button backButton) {
		this.backButton = backButton;
	}

}
