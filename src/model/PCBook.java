package model;

import java.sql.Date;

public class PCBook {
	
	private Integer BookID, PC_ID, UserID;
	private Date BookedDate;
	
	public PCBook(Integer bookID, Integer pC_ID, Integer userID, Date bookedDate) {
		super();
		BookID = bookID;
		PC_ID = pC_ID;
		UserID = userID;
		BookedDate = bookedDate;
	}
	public PCBook() {
		
	}
	public Integer getBookID() {
		return BookID;
	}
	public void setBookID(Integer bookID) {
		BookID = bookID;
	}
	public Integer getPC_ID() {
		return PC_ID;
	}
	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}
	public Integer getUserID() {
		return UserID;
	}
	public void setUserID(Integer userID) {
		UserID = userID;
	}
	public Date getBookedDate() {
		return BookedDate;
	}
	public void setBookedDate(Date bookedDate) {
		BookedDate = bookedDate;
	}

}
