package controller;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Report;
import view.AdminPCView;
import view.CustomerPCView;
import view.MakeReportView;
import view.ReportView;

public class ReportController {
	private MakeReportView repView;
	private ReportView apc;
	private Integer uid;
	private Stage primaryStage;
	private Report reportModel = new Report();
	
	public ReportController(MakeReportView repView, Integer uid) {
        this.repView = repView;
        this.uid = uid;
        initializeAddReport();
	}

	public ReportController(ReportView apc, Integer uid) {
        this.apc = apc;
        this.uid = uid;
		initializeAdminUpdateHandler();
		loadTableDataAdminUpdate();
	}
	public void initializeAdminUpdateHandler() {
		apc.getBackButton().setOnAction(event -> {
			primaryStage = apc.getPrimaryStage();
    		AdminPCView apc = new AdminPCView(primaryStage, uid);
    		PCController p = new PCController(apc, uid);
		});
	}
	
	void loadTableDataAdminUpdate() {
		ArrayList<Report> reports = GetAllReportData();
		apc.getTable().getItems().setAll(reports);
	}

	public void initializeAddReport() {
		repView.getSubmitButton().setOnAction(event -> {
            Integer pcId = Integer.parseInt(repView.getPcIdTxt().getText());
            String reportNote = repView.getReportNoteTxt().getText();

        	long millis=System.currentTimeMillis();  
    	    java.sql.Date date = new java.sql.Date(millis);    
            		UserController userCon = new UserController();
            		String role = userCon.GetRole(userCon.GetName(uid));
                	AddNewReport(role, pcId, reportNote);
                    showAlert("Added Successful", "Added successfully!", Alert.AlertType.INFORMATION);
        });
		repView.getBackButton().setOnAction(event -> {
			primaryStage = repView.getPrimaryStage();
          CustomerPCView customerPcView = new CustomerPCView(primaryStage, uid);
          PCController pcController = new PCController(customerPcView, uid);
		});
	}
	
	public void AddNewReport(String UserRole, Integer PcID, String ReportNote) {
        reportModel.AddNewReport(UserRole, PcID, ReportNote);
	}
	
	public ArrayList<Report> GetAllReportData() {
		return reportModel.GetAllReportData();
	}
	
	private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
