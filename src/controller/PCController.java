package controller;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.PC;
import view.AdminHistoryView;
import view.AdminPCUpdateView;
import view.AdminPCView;
import view.AssignUserToNewPCView;
import view.BookPCView;
import view.CompleteJobView;
import view.CustomerHistoryView;
import view.CustomerPCView;
import view.OperatorPCView;
import view.MakeReportView;
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
	private PC pcModel = new PC();
	
	public PCController(CustomerPCView customerPCView, Integer uid) {
		this.customerPCView = customerPCView;
		this.uid = uid;
		initializeCustomerHandler();
		loadTableDataCustomer();
	}
	
	public void initializeCustomerHandler() {
		customerPCView.getReportButton().setOnAction(event -> {
			primaryStage = customerPCView.getPrimaryStage();
    		MakeReportView custView = new MakeReportView(primaryStage, uid);
    		ReportController r = new ReportController(custView, uid);
		});
		
		customerPCView.getHistoryButton().setOnAction(event -> {
			primaryStage = customerPCView.getPrimaryStage();
    		CustomerHistoryView custView = new CustomerHistoryView(primaryStage, uid);
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
		
		adminPCView.getReportButton().setOnAction(event -> {
			primaryStage = adminPCView.getPrimaryStage();
    		ReportView apc = new ReportView(primaryStage, uid);
    		ReportController p = new ReportController(apc, uid);
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
		
		adminPCView.getHistoryButton().setOnAction(event -> {
			primaryStage = adminPCView.getPrimaryStage();
    		AdminHistoryView custView = new AdminHistoryView(primaryStage, uid);
    		TransactionController r = new TransactionController(custView, uid);
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
		pcModel.AddNewPC(PcID, condition);
	}
	
	public void UpdatePCCondition(Integer PcID, String cond) {
	    pcModel.UpdatePCCondition(PcID, cond);
	}
	
	public ArrayList<PC> GetAllPCData() {
		return pcModel.GetAllPCData();
	}
	
	public PC GetPCDetail(Integer PcID) {
		return pcModel.GetPCDetail(PcID);
	}
	
	public void DeletePC(Integer PcID) {
		pcModel.DeletePC(PcID);
    }
	
	private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
