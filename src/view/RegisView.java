package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RegisView {

	private Stage primaryStage;
	public Button regisButton;
	private Label ageLbl, passwordLbl, textLbl, regisLbl, userLbl, confirmLbl;
	private TextField ageTxt, userTxt;
	private PasswordField passwordTxt, confirmTxt;
	private VBox agePane, passwordPane, userPane, confirmPane;
	private HBox textPane;
	
	public RegisView(Stage PrimaryStage) {
		this.primaryStage = PrimaryStage;
		userLbl = new Label("Username");
		userTxt = new TextField();
		
		passwordLbl = new Label("Password");
		passwordTxt = new PasswordField();
		
		confirmLbl = new Label("Confirm Password");
		confirmTxt = new PasswordField();
		
		ageLbl = new Label("Age");
		ageTxt = new TextField();
		
		userPane = new VBox();
		userPane.getChildren().addAll(userLbl, userTxt);
		
		agePane = new VBox();
		agePane.getChildren().addAll(ageLbl, ageTxt);
		
		passwordPane = new VBox();
		passwordPane.getChildren().addAll(passwordLbl, passwordTxt);
		
		confirmPane = new VBox();
		confirmPane.getChildren().addAll(confirmLbl, confirmTxt);
		
		regisButton = new Button("Register");
		
		textLbl = new Label("Already have an account? ");
		regisLbl = new Label("Login");
		regisLbl.setUnderline(true);
		regisLbl.setTextFill(Color.BLUE);
		regisLbl.setOnMouseClicked(e -> MainView());
		
		textPane = new HBox();
		textPane.getChildren().addAll(textLbl, regisLbl);
		
		VBox vbox = new VBox();
		
		vbox.getChildren().addAll(userPane, agePane, passwordPane,confirmPane, regisButton, textPane);
        vbox.setPadding(new Insets(20, 25, 25, 25));
        vbox.setSpacing(10);
		
		Scene scene = new Scene(vbox, 400, 400);
		primaryStage.setTitle("Mystic Grills: Register");
		primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void MainView() {
		MainView mainView = new MainView(primaryStage);
		UserController userController = new UserController(mainView);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Button getRegisButton() {
		return regisButton;
	}

	public void setRegisButton(Button regisButton) {
		this.regisButton = regisButton;
	}

	public Label getAgeLbl() {
		return ageLbl;
	}

	public void setAgeLbl(Label ageLbl) {
		this.ageLbl = ageLbl;
	}

	public Label getPasswordLbl() {
		return passwordLbl;
	}

	public void setPasswordLbl(Label passwordLbl) {
		this.passwordLbl = passwordLbl;
	}

	public Label getTextLbl() {
		return textLbl;
	}

	public void setTextLbl(Label textLbl) {
		this.textLbl = textLbl;
	}

	public Label getRegisLbl() {
		return regisLbl;
	}

	public void setRegisLbl(Label regisLbl) {
		this.regisLbl = regisLbl;
	}

	public Label getUserLbl() {
		return userLbl;
	}

	public void setUserLbl(Label userLbl) {
		this.userLbl = userLbl;
	}

	public Label getConfirmLbl() {
		return confirmLbl;
	}

	public void setConfirmLbl(Label confirmLbl) {
		this.confirmLbl = confirmLbl;
	}

	public TextField getAgeTxt() {
		return ageTxt;
	}

	public void setAgeTxt(TextField ageTxt) {
		this.ageTxt = ageTxt;
	}

	public TextField getUserTxt() {
		return userTxt;
	}

	public void setUserTxt(TextField userTxt) {
		this.userTxt = userTxt;
	}

	public PasswordField getPasswordTxt() {
		return passwordTxt;
	}

	public void setPasswordTxt(PasswordField passwordTxt) {
		this.passwordTxt = passwordTxt;
	}

	public PasswordField getConfirmTxt() {
		return confirmTxt;
	}

	public void setConfirmTxt(PasswordField confirmTxt) {
		this.confirmTxt = confirmTxt;
	}

	public VBox getAgePane() {
		return agePane;
	}

	public void setAgePane(VBox agePane) {
		this.agePane = agePane;
	}

	public VBox getPasswordPane() {
		return passwordPane;
	}

	public void setPasswordPane(VBox passwordPane) {
		this.passwordPane = passwordPane;
	}

	public VBox getUserPane() {
		return userPane;
	}

	public void setUserPane(VBox userPane) {
		this.userPane = userPane;
	}

	public VBox getConfirmPane() {
		return confirmPane;
	}

	public void setConfirmPane(VBox confirmPane) {
		this.confirmPane = confirmPane;
	}

	public HBox getTextPane() {
		return textPane;
	}

	public void setTextPane(HBox textPane) {
		this.textPane = textPane;
	}

}
