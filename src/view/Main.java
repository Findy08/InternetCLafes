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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.User;

public class Main extends Application {
    private TextField nameInput = new TextField();
    private TextField passwordInput = new TextField();
    private TextField ageInput = new TextField();
    private TextField newRoleInput = new TextField();
    private ObservableList<User> user = FXCollections.observableArrayList();
    private TableView<User> table, table2;
    private UserController controller = new UserController();
    private Button detailButton = new Button("See Details");

    void loadData() {
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

    private TableView<User> createUserTable() {
        TableView<User> table = new TableView<>();

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));

        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("UserPassword"));

        TableColumn<User, Number> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("UserAge"));

        table.getColumns().addAll(nameColumn, passwordColumn, ageColumn);

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
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("UserRole"));

        table.getColumns().addAll(idColumn, nameColumn, passwordColumn, ageColumn, roleColumn);

        return table;
    }

    private GridPane createUserForm(TableView<User> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button techButton = new Button("Technician");

        addButton.setOnAction(event -> {
            String name = nameInput.getText();
            String pw = passwordInput.getText();
            Integer age = Integer.parseInt(ageInput.getText());

            controller.AddNewUser(name, pw, age);
            loadData();
        });

        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String name = nameInput.getText();
                    Integer id = controller.GetID(name);
                    String newRole = newRoleInput.getText();
                    controller.ChangeUserRole(id, newRole);
                    loadData();

                    nameInput.clear();
                    ageInput.clear();
                    passwordInput.clear();
                } catch (NumberFormatException e) {
                	showAlert("Input Error", "Invalid username or password", Alert.AlertType.ERROR);

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

        return form;
    }

    private VBox createLoginRoot(Stage primaryStage) {
        VBox loginRoot = new VBox();

        loginRoot.setSpacing(10);
        loginRoot.setPadding(new Insets(20));

        Label loginLbl = new Label("Login");
        loginLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");

        Button loginBtn = new Button("Login");

        loginBtn.setOnAction(event -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();

            if (controller.login(username, password)) {
                primaryStage.setScene(createHomepageScene());
            } else {
                showAlert("Input Error", "Incorrect username or password!", Alert.AlertType.ERROR);
            }
        });

        Button registerBtn = new Button("Register");
        registerBtn.setOnAction(event -> {
            primaryStage.setScene(createRegisterScene(primaryStage));
        });

        loginRoot.getChildren().addAll(loginLbl, usernameInput, passwordInput, loginBtn, registerBtn);

        return loginRoot;
    }

    private Scene createRegisterScene(Stage primaryStage) {
        VBox registerRoot = new VBox();

        registerRoot.setSpacing(10);
        registerRoot.setPadding(new Insets(20));

        Label registerLbl = new Label("Register");
        registerLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");

        PasswordField confirmPasswordInput = new PasswordField();
        confirmPasswordInput.setPromptText("Confirm Password");

        TextField ageInput = new TextField();
        ageInput.setPromptText("Age");

        Button registerBtn = new Button("Register");

        registerBtn.setOnAction(event -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String confirmPassword = confirmPasswordInput.getText();

            try {
                Integer age = Integer.parseInt(ageInput.getText());

                controller.AddNewUser(username, confirmPassword, age);
                showAlert("Registration Successful", "Account registered successfully!", Alert.AlertType.INFORMATION);

                primaryStage.setScene(createLoginScene(primaryStage));

            } catch (NumberFormatException e) {
                showAlert("Input Error", "Invalid age format. Please provide a valid age.", Alert.AlertType.ERROR);
            }
        });

        Button backBtn = new Button("Back to Login");
        backBtn.setOnAction(event -> {
            primaryStage.setScene(createLoginScene(primaryStage));
        });

        registerRoot.getChildren().addAll(registerLbl, usernameInput, passwordInput,
                confirmPasswordInput, ageInput, registerBtn, backBtn);

        return new Scene(registerRoot, 400, 300);
    }


    private Scene createHomepageScene() {
        VBox root = new VBox();

        table = createUserTable();
        loadData();

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

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(createLoginScene(primaryStage));
        primaryStage.setTitle("User Management");
        primaryStage.show();
    }

    private Scene createLoginScene(Stage primaryStage) {
        VBox root = createLoginRoot(primaryStage);
        return new Scene(root, 400, 250);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
