package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainView extends VBox{
	
	private Button loginButton, regisButton;
	private Stage primaryStage;
	private Label usernameLbl, passwordLbl, ageLbl, confirmLbl, regisLbl, textLbl;
	private TextField usernameText, ageTxt, confirmText;
	private PasswordField passwordText;
	private HBox textPane;
	private VBox usernamePane, passwordPane;
	
	public MainView(Stage PrimaryStage) {
		this.primaryStage = PrimaryStage;
		usernameLbl = new Label("Username");
		usernameText = new TextField();
		passwordLbl = new Label("Password");
		passwordText = new PasswordField();
		
		usernamePane = new VBox();
		usernamePane.getChildren().addAll(usernameLbl, usernameText);
		
		passwordPane = new VBox();
		passwordPane.getChildren().addAll(passwordLbl, passwordText);
		
		loginButton = new Button("Login");
		
		textLbl = new Label("Don't have an account? ");
		regisLbl = new Label("Register");
		regisLbl.setUnderline(true);
		regisLbl.setTextFill(Color.BLUE);
		regisLbl.setOnMouseClicked(e -> RegisView());
		
		textPane = new HBox();
		textPane.getChildren().addAll(textLbl, regisLbl);
		
		VBox vbox = new VBox();
		
		vbox.getChildren().addAll(usernamePane, passwordPane, loginButton, textPane);
        vbox.setPadding(new Insets(20, 25, 25, 25));
        vbox.setSpacing(10);
		
		Scene scene = new Scene(vbox, 400, 400);
		primaryStage.setTitle("InternetCLafes: Login");
		primaryStage.setScene(scene);
        primaryStage.show();
	}

	private void RegisView() {
		RegisView regisView = new RegisView(primaryStage);
		UserController userController = new UserController(regisView);
	}

	public TextField getUsernameText() {
		return usernameText;
	}

	public Button getRegisButton() {
		return regisButton;
	}

	public void setRegisButton(Button regisButton) {
		this.regisButton = regisButton;
	}

	public void setUsernameText(TextField usernameText) {
		this.usernameText = usernameText;
	}

	public TextField getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(PasswordField passwordText) {
		this.passwordText = passwordText;
	}

	public TextField getAgeTxt() {
		return ageTxt;
	}

	public void setAgeTxt(TextField ageTxt) {
		this.ageTxt = ageTxt;
	}

	public TextField getConfirmText() {
		return confirmText;
	}

	public void setConfirmText(TextField confirmText) {
		this.confirmText = confirmText;
	}

	public void setPasswordTxt(PasswordField passwordTxt) {
		this.passwordText = passwordTxt;
	}
	
	public Button getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(Button loginButton) {
		this.loginButton = loginButton;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
}