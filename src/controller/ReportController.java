package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.PC;
import model.Report;
import view.AdminPCView;
import view.CustomerPCView;
import view.MakeReportView;
import view.RegisView;
import view.ReportView;

public class ReportController {
	private MakeReportView repView;
	private ReportView apc;
	private Integer uid;
	private Stage primaryStage;
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
	public ArrayList<Report> GetAllReportData() {
		ArrayList<Report> reports = new ArrayList<Report>();
		String query = "SELECT * FROM Report";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer Pid = resultSet.getInt("PC_ID");
				Integer Rid = resultSet.getInt("ReportID");
				String role = resultSet.getString("UserRole");
				String note = resultSet.getString("ReportNote");
				Date date = resultSet.getDate("ReportDate");
				reports.add(new Report(Rid, Pid, role, note, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reports;
	}

	private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
                    
                    
//                    primaryStage = repView.getPrimaryStage();
//                    CustomerPCView customerPcView = new CustomerPCView(primaryStage, uid);
//            		PCController pcController = new PCController(customerPcView, uid);
        });
		repView.getBackButton().setOnAction(event -> {
			primaryStage = repView.getPrimaryStage();
          CustomerPCView customerPcView = new CustomerPCView(primaryStage, uid);
          PCController pcController = new PCController(customerPcView, uid);
		});
	}
	
	public void AddNewReport(String UserRole, Integer PcID, String ReportNote) {
        if (PcID == null || ReportNote == null || ReportNote.isEmpty()) {
        	ShowAlert("Input can't be empty", Alert.AlertType.ERROR);
            return;
        }
        if (!IsPCExists(PcID)) {
        	ShowAlert("PC with ID " + PcID + " does not exist", Alert.AlertType.ERROR);
            return;
        }
        Report r = new Report();
        r.setUserRole(UserRole);
        r.setPC_ID(PcID);
        r.setReportNote(ReportNote);

        String query = "INSERT INTO Report(UserRole, PC_ID, ReportNote, ReportDate) VALUES (?, ?, ?, NOW())";

        try (Connection connection = Database.getDB().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, r.getUserRole());
            ps.setInt(2, r.getPC_ID());
            ps.setString(3, r.getReportNote());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public ArrayList<Report> GetAllUserData() {
		ArrayList<Report> r = new ArrayList<Report>();
		String query = "SELECT * FROM Report";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("Report_ID");
				String role = resultSet.getString("UserRole");
				Integer pcid = resultSet.getInt("PC_ID");
				String note = resultSet.getString("ReportNote");
				Date date = resultSet.getDate("ReportDate");
				r.add(new Report(id, pcid, role, note, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	private void ShowAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("PC Booking System");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	private boolean IsPCExists(Integer pcID) {
	    String query = "SELECT COUNT(*) FROM PC WHERE PC_ID = ?";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, pcID);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count > 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; 
	}

}
