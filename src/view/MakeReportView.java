package view;

import controller.ReportController;
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

public class MakeReportView {
	
	private Stage primaryStage;
	private Label pcIdLbl, reportNoteLbl;
	private Button submitButton, backButton;
	private TextField pcIdTxt, reportNoteTxt;
	private VBox pcidPane, reportPane;
	private HBox textPane;
	private Integer uid;
	
	public Button getBackButton() {
		return backButton;
	}

	public void setBackButton(Button backButton) {
		this.backButton = backButton;
	}

	public MakeReportView(Stage PrimaryStage, Integer uid) {
		this.primaryStage = PrimaryStage;
		this.uid = uid;
        pcIdLbl = new Label("PCID");
        pcIdTxt = new TextField();

        reportNoteLbl = new Label("Report Note");
        reportNoteTxt = new TextField();

        pcidPane = new VBox();
        pcidPane.getChildren().addAll(pcIdLbl, pcIdTxt);

        reportPane = new VBox();
        reportPane.getChildren().addAll(reportNoteLbl, reportNoteTxt);

        submitButton = new Button("Submit");
        backButton = new Button("back");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(pcidPane, reportPane, submitButton, backButton);
        vbox.setPadding(new Insets(20, 25, 25, 25));
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setTitle("Report View");
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Label getPcIdLbl() {
		return pcIdLbl;
	}

	public void setPcIdLbl(Label pcIdLbl) {
		this.pcIdLbl = pcIdLbl;
	}

	public Label getReportNoteLbl() {
		return reportNoteLbl;
	}

	public void setReportNoteLbl(Label reportNoteLbl) {
		this.reportNoteLbl = reportNoteLbl;
	}

	public Button getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(Button submitButton) {
		this.submitButton = submitButton;
	}

	public TextField getPcIdTxt() {
		return pcIdTxt;
	}

	public void setPcIdTxt(TextField pcIdTxt) {
		this.pcIdTxt = pcIdTxt;
	}

	public TextField getReportNoteTxt() {
		return reportNoteTxt;
	}

	public void setReportNoteTxt(TextField reportNoteTxt) {
		this.reportNoteTxt = reportNoteTxt;
	}

	public VBox getPcidPane() {
		return pcidPane;
	}

	public void setPcidPane(VBox pcidPane) {
		this.pcidPane = pcidPane;
	}

	public VBox getReportPane() {
		return reportPane;
	}

	public void setReportPane(VBox reportPane) {
		this.reportPane = reportPane;
	}

	public HBox getTextPane() {
		return textPane;
	}

	public void setTextPane(HBox textPane) {
		this.textPane = textPane;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	

	

}
