package model;

public class Job {
	
	private Integer Job_ID, UserID, PC_ID;
	private String JobStatus;

	public Job(Integer job_ID, Integer userID, Integer pC_ID, String jobStatus) {
		super();
		Job_ID = job_ID;
		UserID = userID;
		PC_ID = pC_ID;
		JobStatus = jobStatus;
	}

	public Job() {
		// TODO Auto-generated constructor stub
	}

	public Integer getJob_ID() {
		return Job_ID;
	}

	public void setJob_ID(Integer job_ID) {
		Job_ID = job_ID;
	}

	public Integer getUserID() {
		return UserID;
	}

	public void setUserID(Integer userID) {
		UserID = userID;
	}

	public Integer getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}

	public String getJobStatus() {
		return JobStatus;
	}

	public void setJobStatus(String jobStatus) {
		JobStatus = jobStatus;
	}

}
