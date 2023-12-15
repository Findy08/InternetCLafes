package view;

import java.util.ArrayList;

import controller.UserController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.User;

public class UserView extends Application {
	private TextField nameInput = new TextField();
	private TextField passwordInput = new TextField();
	private TextField ageInput = new TextField();
	private TextField newRoleInput = new TextField();
	private ObservableList<User> user = FXCollections.observableArrayList();
	private TableView<User> table, table2;
	UserController controller = new UserController();
	Button detailButton = new Button("See Details");
	
	void loadAllData() {
		ArrayList<User> data = controller.GetAllUserData();
		table.getItems().setAll(data);
	}
	
	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameInput.setText(newSelection.getUserName());
                passwordInput.setText(String.valueOf(newSelection.getUserPassword()));
                ageInput.setText(String.valueOf(newSelection.getUserAge()));
            }
        });
    }
	
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(); 

        table = createUserTable();
        loadAllData();
        
        setupTableSelectionListener();
        GridPane form = createUserForm(table);
        
        table2 = createUserDataTable();
        
        detailButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	User selectedUser = table.getSelectionModel().getSelectedItem();
            	root.getChildren().clear();
            	root.getChildren().addAll(table2, form);
	            if (selectedUser != null) {
	                User u = controller.GetUserData(selectedUser.getUserName(), selectedUser.getUserPassword());
	                table2.getItems().setAll(u);
	            }
            }
        });
        
        VBox.setMargin(form, new Insets(20));
        root.getChildren().addAll(table, form, detailButton);  

        Scene scene = new Scene(root, 800, 600); 
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Management");
        primaryStage.show();
    }

    private TableView<User> createUserTable() {
        TableView<User> table = new TableView<>();

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));

        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("UserPassword"));

        TableColumn<User, Number> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("UserAge"));

        table.getColumns().add(nameColumn);
        table.getColumns().add(passwordColumn);
        table.getColumns().add(ageColumn);

        return table;
    }
    
    private TableView<User> createUserDataTable() {
        TableView<User> table = new TableView<>();

        TableColumn<User, String> idColumn = new TableColumn<>("ID");
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));

        TableColumn<User, Number> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));
    	
    	TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("UserPassword"));

        TableColumn<User, Number> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("UserAge"));
        
        TableColumn<User, Number> roleColumn = new TableColumn<>("Role");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("UserRole"));

        table.getColumns().add(idColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(passwordColumn);
        table.getColumns().add(ageColumn);
        table.getColumns().add(roleColumn);

        return table;
    }
    
    private GridPane createUserForm(TableView<User> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button techButton = new Button("Technician");
        
        
        // Insert data to db
        addButton.setOnAction(event -> {
        	String name = nameInput.getText();
        	String pw = passwordInput.getText();
        	Integer age = Integer.parseInt(ageInput.getText());
        	
        	controller.AddNewUser(name, pw, age);
        	loadAllData();
        });
        
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try {
                    String name = nameInput.getText();
                    Integer id = controller.GetID(name);
                    String NewRole = newRoleInput.getText();
                    controller.ChangeUserRole(id, NewRole);;  
                    loadAllData();
                    
                    nameInput.clear();
                    ageInput.clear();
                    passwordInput.clear();
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Price and Quantity must be integers.");
                    alert.showAndWait();
                }
            }
        });
        
        techButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
	        	ArrayList<User> data = controller.GetAllTechnician();
	        	table.getItems().setAll(data);
            }
        });
        
//        detailButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//            	User selectedUser = table.getSelectionModel().getSelectedItem();
//	            if (selectedUser != null) {
//	                User u = controller.GetUserData(selectedUser.getUserName(), selectedUser.getUserPassword());
//	                table2.getItems().setAll(u);
//	            }
//            }
//        });

//        form.add(new Label("Product ID:"), 0, 0);
//        idInput.setDisable(true);
//        form.add(idInput, 1, 0);
        form.add(new Label("Name:"), 0, 1);
        form.add(nameInput, 1, 1);
        form.add(new Label("Password:"), 0, 2);
        form.add(passwordInput, 1, 2);
        form.add(new Label("Age:"), 0, 3);
        form.add(ageInput, 1, 3);
        form.add(new Label("New Role:"), 2, 1);
        form.add(newRoleInput, 3, 1);
        form.add(addButton, 0, 4);
        form.add(updateButton, 2, 4);
        form.add(techButton, 1, 4);
        form.add(detailButton, 3, 4);
//        
//
        return form;
    }
    
    private Scene createHomepageScene() {
        VBox root = new VBox();

        table = createUserTable();
        loadAllData();

        setupTableSelectionListener();
        GridPane form = createUserForm(table);

        table2 = createUserDataTable();

        detailButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User selectedUser = table.getSelectionModel().getSelectedItem();
                root.getChildren().clear();
                root.getChildren().addAll(table2, form);
                if (selectedUser != null) {
                    User u = controller.GetUserData(selectedUser.getUserName(), selectedUser.getUserPassword());
                    table2.getItems().setAll(u);
                }
            }
        });

        VBox.setMargin(form, new Insets(20));
        root.getChildren().addAll(table, form, detailButton);

        return new Scene(root, 800, 600);
    }


    
//    public static void main(String[] args) {
//        launch(args);
//    }

}
