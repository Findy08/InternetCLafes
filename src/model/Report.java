package model;

import java.sql.Date;

public class Report {
	
	private Integer Report_ID, PC_ID;
	private String UserRole, ReportNote;
	private Date ReportDate;

	public Report(Integer report_ID, Integer pC_ID, String userRole, String reportNote, Date reportDate) {
		super();
		Report_ID = report_ID;
		PC_ID = pC_ID;
		UserRole = userRole;
		ReportNote = reportNote;
		ReportDate = reportDate;
	}

	public Report() {
		// TODO Auto-generated constructor stub
	}

	public Integer getReport_ID() {
		return Report_ID;
	}

	public void setReport_ID(Integer report_ID) {
		Report_ID = report_ID;
	}

	public Integer getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}

	public String getReportNote() {
		return ReportNote;
	}

	public void setReportNote(String reportNote) {
		ReportNote = reportNote;
	}

	public Date getReportDate() {
		return ReportDate;
	}

	public void setReportDate(Date reportDate) {
		ReportDate = reportDate;
	}

}
