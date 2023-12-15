package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PC;
import model.Report;

public class ReportView {
	private Stage primaryStage;
	private TableView<Report> table;
	private Integer uid;
	private Button backButton;

	public ReportView(Stage primaryStage, Integer uid) {
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
	
	private TableView<Report> createPCDataTable() {
        TableView<Report> table = new TableView<>();
        
        TableColumn<Report, Number> RidColumn = new TableColumn<>("ReportID");
        RidColumn.setCellValueFactory(new PropertyValueFactory<>("Report_ID")); //HARUS NAMA PROPERTI MODEL

        TableColumn<Report, String> UserColumn = new TableColumn<>("UserRole");
        UserColumn.setCellValueFactory(new PropertyValueFactory<>("UserRole"));
        
        TableColumn<Report, Number> PidColumn = new TableColumn<>("PC ID");
        PidColumn.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
        
        TableColumn<Report, String> noteColumn = new TableColumn<>("ReportNote");
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("ReportNote"));
        
        TableColumn<Report, String> reportColumn = new TableColumn<>("ReportDate");
        reportColumn.setCellValueFactory(new PropertyValueFactory<>("ReportDate"));

        table.getColumns().add(RidColumn);
		table.getColumns().add(UserColumn);
		table.getColumns().add(PidColumn);
		table.getColumns().add(noteColumn);
		table.getColumns().add(reportColumn);
		
        return table;
    }
	
	private GridPane createPCForm(TableView<Report> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);

        backButton = new Button("Back");
        form.add(backButton, 0, 1);


        return form;
    }
	public ReportView() {
		// TODO Auto-generated constructor stub
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public TableView<Report> getTable() {
		return table;
	}

	public void setTable(TableView<Report> table) {
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
