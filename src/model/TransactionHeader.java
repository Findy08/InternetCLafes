package model;

public class TransactionHeader {
	
	private Integer TransactionID, StaffID;
	private String StaffName, TransactionDate;

	public Integer getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(Integer transactionID) {
		TransactionID = transactionID;
	}

	public Integer getStaffID() {
		return StaffID;
	}

	public void setStaffID(Integer staffID) {
		StaffID = staffID;
	}



	public String getStaffName() {
		return StaffName;
	}

	public void setStaffName(String staffName) {
		StaffName = staffName;
	}


	public String getTransactionDate() {
		return TransactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		TransactionDate = transactionDate;
	}

	public TransactionHeader(Integer transactionID, Integer staffID, String staffName, String transactionDate) {
		super();
		TransactionID = transactionID;
		StaffID = staffID;
		StaffName = staffName;
		TransactionDate = transactionDate;
	}

	public TransactionHeader() {
		// TODO Auto-generated constructor stub
	}

}
