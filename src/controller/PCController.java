package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import database.Database;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.PC;
import view.AdminPCUpdateView;
import view.AdminPCView;
import view.AssignUserToNewPCView;
import view.BookPCView;
import view.CompleteJobView;
import view.CustomerPCView;
import view.HistoryView;
import view.OperatorPCView;
import view.ReportView;
import view.StaffView;
import view.TechnicianJobView;
import view.TechnicianPCView;
import view.ViewAllJob;
import view.BookingsView;

public class PCController {
	
	private CustomerPCView customerPCView;
	private AdminPCView adminPCView;
	private AdminPCUpdateView adminPCUpdateView;
	private TechnicianPCView technicianPCView;
	private OperatorPCView operatorPCView;
	private Integer uid;
	private Stage primaryStage;
	
	public PCController(CustomerPCView customerPCView, Integer uid) {
		this.customerPCView = customerPCView;
		this.uid = uid;
		initializeCustomerHandler();
		loadTableDataCustomer();
	}
	
	public void initializeCustomerHandler() {
		customerPCView.getReportButton().setOnAction(event -> {
			primaryStage = customerPCView.getPrimaryStage();
    		ReportView custView = new ReportView(primaryStage, uid);
    		ReportController r = new ReportController(custView, uid);
		});
		
		customerPCView.getHistoryButton().setOnAction(event -> {
			primaryStage = customerPCView.getPrimaryStage();
    		HistoryView custView = new HistoryView(primaryStage, uid);
    		TransactionController r = new TransactionController(custView, uid);
		});

		customerPCView.getBookButton().setOnAction(event -> {  
			primaryStage = customerPCView.getPrimaryStage();
			BookPCView b = new BookPCView(primaryStage, uid);
        	PCBookController pb = new PCBookController(b, uid);
        });
	}

	public PCController(AdminPCView adminPCView, Integer uid) {
		this.adminPCView = adminPCView;
		this.uid = uid;
		initializeAdminHandler();
		loadTableDataAdmin();
	}
	
	
	
	public void initializeAdminHandler() {
		adminPCView.getAddButton().setOnAction(event -> {
			try {
				String id = adminPCView.getIdInput().getText();
				String condition = adminPCView.getCondInput().getText();
                AddNewPC(Integer.parseInt(id), condition);
                loadTableDataAdmin();
            } catch(RuntimeException e) {
            	showAlert("Input PC ID", "Please input new PC ID", Alert.AlertType.ERROR);
            }
		});
		
		adminPCView.getUpdateButton().setOnAction(event -> {
			primaryStage = adminPCView.getPrimaryStage();
    		AdminPCUpdateView apc = new AdminPCUpdateView(primaryStage, uid);
    		PCController p = new PCController(apc, uid);
		});

		adminPCView.getDeleteButton().setOnAction(event -> {
			try {
				String id = adminPCView.getIdInput().getText();
                DeletePC(Integer.parseInt(id));
                loadTableDataAdmin();
            } catch(RuntimeException e) {
            	showAlert("Input PC ID", "Please input new PC ID", Alert.AlertType.ERROR);
            }
        });
		
		adminPCView.getViewStaffButton().setOnAction(event -> {
			UserController uc = new UserController();
			if(uc.GetRole(uc.GetName(uid)).equals("Admin")){
        		primaryStage = adminPCView.getPrimaryStage();
        		StaffView staffView = new StaffView(primaryStage, uid);
        		UserController u = new UserController(staffView, uid);
            }
		});
		
		adminPCView.getViewAllJobButton().setOnAction(event -> {
			primaryStage = adminPCView.getPrimaryStage();
    		ViewAllJob vaj = new ViewAllJob(primaryStage, uid);
    		JobController j = new JobController(vaj, uid);
		});
	}
	
	public PCController(AdminPCUpdateView adminPCUpdateView, Integer uid) {
		this.adminPCUpdateView = adminPCUpdateView;
		this.uid = uid;
		initializeAdminUpdateHandler();
		loadTableDataAdminUpdate();
	}
	
	public void initializeAdminUpdateHandler() {

		adminPCUpdateView.getUpdateButton().setOnAction(event -> {
//			PC selectedPC = adminPCUpdateView.getTable().getSelectionModel().getSelectedItem();
            try {
                UpdatePCCondition(Integer.parseInt(adminPCUpdateView.getIdInput().getText()), adminPCUpdateView.getCondText().getText().toString());
                loadTableDataAdminUpdate();
            } catch(RuntimeException e) {
            	showAlert("Input All Fields", "Please input all fields", Alert.AlertType.ERROR);
            }
        });
		
		adminPCUpdateView.getBackButton().setOnAction(event -> {
			primaryStage = adminPCUpdateView.getPrimaryStage();
    		AdminPCView apc = new AdminPCView(primaryStage, uid);
    		PCController p = new PCController(apc, uid);
		});
	}
	
	void loadTableDataAdminUpdate() {
		ArrayList<PC> pc = GetAllPCData();
		adminPCUpdateView.getTable().getItems().setAll(pc);
	}
	
	void loadTableDataAdmin() {
		ArrayList<PC> pc = GetAllPCData();
		adminPCView.getTable().getItems().setAll(pc);
	}
	
	void loadTableDataCustomer() {
		ArrayList<PC> pc = GetAllPCData();
		customerPCView.getTable().getItems().setAll(pc);
	}
	
	public PCController(TechnicianPCView technicianPCView, Integer uid) {
		this.technicianPCView = technicianPCView;
		this.uid = uid;
		initializeTechnicianHandler();
		loadTableDataTechnician();
	}

	public void initializeTechnicianHandler() {

		technicianPCView.getViewJobButton().setOnAction(event -> {
			primaryStage = technicianPCView.getPrimaryStage();
    		TechnicianJobView tech = new TechnicianJobView(primaryStage, uid);
    		JobController p = new JobController(tech, uid);
        });
		
		technicianPCView.getFinishJobButton().setOnAction(event -> {
			primaryStage = technicianPCView.getPrimaryStage();
			CompleteJobView tech = new CompleteJobView(primaryStage, uid);
			JobController p = new JobController(tech, uid);
		});
	}
	
	void loadTableDataTechnician() {
		ArrayList<PC> pc = GetAllPCData();
		technicianPCView.getTable().getItems().setAll(pc);
	}
	
	public PCController(OperatorPCView opView, Integer uid) {
		this.operatorPCView = opView;
		this.uid = uid;
		initializeOperatorHandler();
		loadTableDataOperator();
	}

	public void initializeOperatorHandler() {

		operatorPCView.getViewBookButton().setOnAction(event -> {
			primaryStage = operatorPCView.getPrimaryStage();
			BookingsView op = new BookingsView(primaryStage, uid);
    		PCBookController p = new PCBookController(op, uid);
        });
		
		operatorPCView.getAssignUserButton().setOnAction(event -> {
			primaryStage = operatorPCView.getPrimaryStage();
    		AssignUserToNewPCView op = new AssignUserToNewPCView(primaryStage, uid);
    		PCBookController p = new PCBookController(op, uid);
		});
	}
	
	void loadTableDataOperator() {
		ArrayList<PC> pc = GetAllPCData();
		operatorPCView.getTable().getItems().setAll(pc);
	}

	public void AddNewPC(Integer PcID, String condition) {
	    if (PcID == null) {
	        showAlert("Invalid PC ID", "Please provide a valid PC ID.", Alert.AlertType.ERROR);
	        return;
	    }
	    if (IsExist(PcID)) {
	        showAlert("Duplicate PC", "A PC with the provided ID already exists.", Alert.AlertType.ERROR);
	        return;
	    }
	    
	    if (!isValidCondition(condition)) {
	        showAlert("Invalid Condition", "Condition must be either 'Usable', 'Maintenance' or 'Broken'.", Alert.AlertType.ERROR);
	        return;
	    }

	    PC pc = new PC();
	    pc.setPC_ID(PcID);
	    pc.setPC_Condition(condition); 

	    String query = "INSERT INTO PC(PC_ID, PC_Condition) VALUES (?, ?)";
	    try (Connection connection = Database.getDB().getConnection()) {
	        PreparedStatement ps = connection.prepareStatement(query);
	        ps.setInt(1, pc.getPC_ID());
	        ps.setString(2, pc.getPC_Condition());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void UpdatePCCondition(Integer PcID, String cond) {
	    if (PcID == null) {
	        showAlert("Invalid Selection", "Please choose a PC.", Alert.AlertType.ERROR);
	        return;
	    }
	    
	    if(IsExist(PcID)== false) {
	    	showAlert("Invalid PC ID", "Please input an existing PC ID.", Alert.AlertType.ERROR);
	    }

	    ArrayList<String> validConditions = new ArrayList<>(Arrays.asList("Usable", "Maintenance", "Broken"));
	    if (!validConditions.contains(cond)) {
	    	System.out.println(cond);
	        showAlert("Invalid PC Condition", "Must be either 'Usable', 'Maintenance', or 'Broken'.", Alert.AlertType.ERROR);
	        return;
	    }
	    
	   	String query = "UPDATE PC SET PC_Condition = ? WHERE PC_ID = ?";
	   	try {
	   		Connection connection = Database.getDB().getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, cond);
			ps.setInt(2, PcID);
			ps.executeUpdate();
	   	} catch (SQLException e) {
	   		e.printStackTrace();
	   	}
	}

	private boolean isValidCondition(String condition) {
	    return condition != null && (condition.equals("Usable") || condition.equals("Maintenance") || condition.equals("Broken"));
	}

	
	private boolean IsExist(Integer pcID) {
	    String query = "SELECT COUNT(*) FROM PC WHERE PC_ID = ?";
	    try (Connection connection = Database.getDB().getConnection();
	         PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, pcID);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public ArrayList<PC> GetAllPCData() {
		ArrayList<PC> pc = new ArrayList<PC>();
		String query = "SELECT * FROM PC";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("PC_ID");
				String cond = resultSet.getString("PC_Condition");
				pc.add(new PC(id, cond));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pc;
	}
	
	public PC GetPCDetail(Integer PcID) {
		PC pc = new PC();
		String query = "SELECT * FROM PC WHERE PC_ID = ?";
		try(Connection connection = Database.getDB().getConnection()){
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, PcID);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				pc.setPC_ID(resultSet.getInt("PC_ID"));
				pc.setPC_Condition(resultSet.getString("PC_Condition"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pc;
	}
	
	
	
	public void DeletePC(Integer PcID) {
		if (PcID == null) {
			showAlert("Invalid Selection", "Please choose a PC.", Alert.AlertType.ERROR);
	        return;
	    }
	
        String query = "DELETE FROM PC WHERE PC_ID = ?";
        try (Connection connection = Database.getDB().getConnection();
        		PreparedStatement ps = connection.prepareStatement(query)) {
        	ps.setInt(1, PcID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
