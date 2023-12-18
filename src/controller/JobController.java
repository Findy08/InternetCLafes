package controller;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Job;
import model.PC;
import view.AdminPCView;
import view.AdminUpdateJobView;
import view.CompleteJobView;
import view.TechnicianJobView;
import view.TechnicianPCView;
import view.ViewAllJob;

public class JobController {
	
	private Integer uid;
	private Stage primaryStage;
	private TechnicianJobView tech;
	private ViewAllJob vaj;
	private AdminPCView adminPCView;
	private CompleteJobView comp;
	private AdminUpdateJobView aujv;
	private Job job = new Job();
	

	public JobController(TechnicianJobView tech, Integer uid) {
		this.tech = tech;
		this.uid = uid;
		initializeJob();
		loadTableDataJob();
	}
	
	public JobController(ViewAllJob vaj, Integer uid) {
		this.vaj = vaj;
		this.uid = uid;
		initializeAdminHandler();
		loadAllJob();
	}
	
	public JobController(AdminUpdateJobView aujv, Integer uid) {
		this.aujv = aujv;
		this.uid = uid;
		initializeAdminUpdateHandler();
		loadAllJobUpdate();
	}

	public void initializeJob() {

		tech.getBackButton().setOnAction(event -> {
			primaryStage = tech.getPrimaryStage();
    		TechnicianPCView tech = new TechnicianPCView(primaryStage, uid);
    		PCController p = new PCController(tech, uid);
        });
	}
	
	public void initializeAdminUpdateHandler() {
		aujv.getBackButton().setOnAction(event -> {
			primaryStage = aujv.getPrimaryStage();
			ViewAllJob vaj = new ViewAllJob(primaryStage, uid);
    		JobController j = new JobController(vaj, uid);
        });
		
		aujv.getUpdateButton().setOnAction(event -> {
			try {
				UpdateJobStatus(Integer.parseInt(aujv.getIdInput().getText()), aujv.getStatusInput().getText().toString());
				loadAllJobUpdate();
            } catch(RuntimeException e) {
            	ShowAlert("Input All Fields", "Please input all fields", Alert.AlertType.ERROR);
            }
		});
	}
	
	public void initializeAdminHandler() {
		vaj.getBackButton().setOnAction(event -> {
			primaryStage = vaj.getPrimaryStage();
			AdminPCView apv = new AdminPCView(primaryStage, uid);
    		PCController p = new PCController(apv, uid);
        });
		
		vaj.getAddButton().setOnAction(event -> {
			try {
				String id = vaj.getIdInput().getText();
				String userid = vaj.getUserInput().getText();
				String status = vaj.getStatusInput().getText();
				
                AddNewJob(Integer.parseInt(userid), Integer.parseInt(id), status);
                loadAllJob();
            } catch(RuntimeException e) {
            	ShowAlert("Input Invalid", "Please input all fields", Alert.AlertType.ERROR);
            }
		});
		
		vaj.getUpdateButton().setOnAction(event -> {
			primaryStage = vaj.getPrimaryStage();
    		AdminUpdateJobView aujv = new AdminUpdateJobView(primaryStage, uid);
    		JobController j = new JobController(aujv, uid);
		});
	}

	void loadAllJob() {
		ArrayList<Job> job = GetAllJobData();
		vaj.getTable().getItems().setAll(job);
	}
	
	void loadAllJobUpdate() {
		ArrayList<Job> job = GetAllJobData();
		aujv.getTable().getItems().setAll(job);
	}
	
	void loadTableDataJob() {
		ArrayList<Job> job = GetAllJobData();
		tech.getTable().getItems().setAll(job);
	}

	public JobController(CompleteJobView comp, Integer uid) {
		this.comp = comp;
		this.uid = uid;
		initializeFinishJob();
		loadTableDataFinishJob();
	}

	public void initializeFinishJob() {
		comp.getCompleteButton().setOnAction(event -> {
			Integer id = Integer.parseInt(comp.getIdInput().getText());
			UpdateJobStatus(id, "Complete");
			loadTableDataFinishJob();
		});

		comp.getBackButton().setOnAction(event -> {
			primaryStage = comp.getPrimaryStage();
    		TechnicianPCView tech = new TechnicianPCView(primaryStage, uid);
    		PCController p = new PCController(tech, uid);
        });
	}
	
	void loadTableDataFinishJob() {
		ArrayList<Job> job = GetUncompleteJobData();
		comp.getTable().getItems().setAll(job);
	}

	public void AddNewJob(Integer UserID, Integer PcID, String status) {
		job.AddNewJob(UserID, PcID, status);
	}
	
	public ArrayList<Job> GetAllJobData() {
		return job.GetAllJobData();
	}
	
	public ArrayList<Job> GetUncompleteJobData() {
		return job.GetUncompleteJobData();
	}
	
	public ArrayList<Job> GetTechnicianJob(Integer UserID) {
		return job.GetTechnicianJob(UserID);
	}
	
	public void UpdateJobStatus(Integer JobID, String JobStatus) {
		job.UpdateJobStatus(JobID, JobStatus);
   }
	
	public PC GetPcOnWorkingList(Integer pcID) {
	    return job.GetPcOnWorkingList(pcID);
	}
	
	private void ShowAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
