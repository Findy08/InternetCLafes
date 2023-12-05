package model;

public class PCBook {
	
	private Integer BookID, PC_ID, UserID;
	private String BookedDate;
	
	public PCBook(Integer bookID, Integer pC_ID, Integer userID, String bookedDate) {
		super();
		BookID = bookID;
		PC_ID = pC_ID;
		UserID = userID;
		BookedDate = bookedDate;
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
	public String getBookedDate() {
		return BookedDate;
	}
	public void setBookedDate(String bookedDate) {
		BookedDate = bookedDate;
	}

}