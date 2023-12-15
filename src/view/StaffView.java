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
import model.User;

public class StaffView {
	
	private Stage primaryStage;
	private Label idLbl, newroleLbl;
	private TextField idInput, newroleInput;
	private TableView<User> table;
	private Button backButton, changeButton;
	private Integer uid;

	public StaffView(Stage primaryStage, Integer uid) {
		this.primaryStage = primaryStage;
		this.uid = uid;		
		VBox vbox = new VBox();
		table = createAllStaffTable();
		
		GridPane form = createAllStaffForm(table);
		VBox.setMargin(form, new Insets(20));
		vbox.getChildren().addAll(table, form);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("View All Staff");
		primaryStage.show();
	}
	
	private TableView<User> createAllStaffTable() {
        TableView<User> table = new TableView<>();
        
        TableColumn<User, Number> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));

        TableColumn<User, Number> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("UserAge"));
        
        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("UserRole"));

        table.getColumns().add(idColumn);
        table.getColumns().add(nameColumn);
		table.getColumns().add(ageColumn);
		table.getColumns().add(roleColumn);
		
        return table;
    }

	private GridPane createAllStaffForm(TableView<User> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);

        changeButton = new Button("Change User Role");
        backButton = new Button("Back");
//        
        form.add(changeButton, 0, 0);
        form.add(backButton, 1, 0);

        return form;
    }

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public TextField getIdInput() {
		return idInput;
	}

	public void setIdInput(TextField idInput) {
		this.idInput = idInput;
	}

	public Label getIdLbl() {
		return idLbl;
	}

	public void setIdLbl(Label idLbl) {
		this.idLbl = idLbl;
	}

	public Label getNewroleLbl() {
		return newroleLbl;
	}

	public void setNewroleLbl(Label newroleLbl) {
		this.newroleLbl = newroleLbl;
	}

	public TextField getNewroleInput() {
		return newroleInput;
	}

	public void setNewroleInput(TextField newroleInput) {
		this.newroleInput = newroleInput;
	}

	public TableView<User> getTable() {
		return table;
	}

	public void setTable(TableView<User> table) {
		this.table = table;
	}

	public Button getBackButton() {
		return backButton;
	}

	public void setBackButton(Button backButton) {
		this.backButton = backButton;
	}

	public Button getChangeButton() {
		return changeButton;
	}

	public void setChangeButton(Button changeButton) {
		this.changeButton = changeButton;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	
}
