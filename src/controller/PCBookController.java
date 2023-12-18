package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import javafx.stage.Stage;
import model.PCBook;
import view.AssignUserToNewPCView;
import view.BookPCView;
import view.BookingsView;
import view.CustomerPCView;
import view.OperatorPCView;

public class PCBookController {

	private Integer uid;
	private Stage primaryStage;
	private BookingsView book;
	private BookPCView pc;
	private AssignUserToNewPCView ass;
	private PCBook pcb = new PCBook();

	public PCBookController(BookingsView book, Integer uid) {
		this.book = book;
		this.uid = uid;
		initializeBookings();
		loadTableDataBookings();
	}

	public void initializeBookings() {
		book.getBackButton().setOnAction(event -> {
			primaryStage = book.getPrimaryStage();
    		OperatorPCView book = new OperatorPCView(primaryStage, uid);
    		PCController p = new PCController(book, uid);
        });
		
		book.getFinishButton().setOnAction(event -> {
			Date date = Date.valueOf(book.getDateInput().getText().toString());
			ArrayList<PCBook> pcBookList = GetPcBookedByDate(date);
			FinishBook(pcBookList, date);
			loadTableDataBookings();
	    });
		
		book.getCancelButton().setOnAction(event -> {
			Integer id = null;
			id = Integer.parseInt(book.getIdInput().getText());
			DeleteBookData(id);
			loadTableDataBookings();
        });
	}
	
	void loadTableDataBookings() {
		ArrayList<PCBook> pcbook = GetAllPCBookedData();
		book.getTable().getItems().setAll(pcbook);
	}

	public PCBookController(AssignUserToNewPCView ass, Integer uid) {
		this.ass = ass;
		this.uid = uid;
		initializeAssign();
		loadTableDataAssign();
	}

	private void initializeAssign() {
		ass.getBackButton().setOnAction(event -> {
			primaryStage = ass.getPrimaryStage();
    		OperatorPCView book = new OperatorPCView(primaryStage, uid);
    		PCController p = new PCController(book, uid);
        });
		ass.getUpdateButton().setOnAction(event -> {
			Integer BookID = Integer.parseInt(ass.getIdInput().getText().toString());
			Integer PcID = Integer.parseInt(ass.getPcInput().getText().toString());
			AssignUserToNewPC(BookID, PcID);
			loadTableDataAssign();
	    });
	}
	
	void loadTableDataAssign() {
		ArrayList<PCBook> pcbook = GetAllPCBookedData();
		ass.getTable().getItems().setAll(pcbook);
	}

	public PCBookController(BookPCView pc, Integer uid) {
		this.pc = pc;
		this.uid = uid;
		initializeBook();
		loadTableDataBook();
	}

	public void initializeBook() {
		pc.getBookButton().setOnAction(event -> {
			Integer pid = Integer.parseInt(pc.getPidInput().getText());
			Date date = Date.valueOf(pc.getDateInput().getText().toString());
			AddNewBook(pid, uid, date);
			loadTableDataBook();
        });
		
		pc.getBackButton().setOnAction(event -> {
			primaryStage = pc.getPrimaryStage();
    		CustomerPCView bp = new CustomerPCView(primaryStage, uid);
    		PCController p = new PCController(bp, uid);
        });
	}
	
	void loadTableDataBook() {
		ArrayList<PCBook> pcbook = GetAllPCBookedUserData(uid);
		pc.getTable().getItems().setAll(pcbook);
	}

	private boolean IsAvailable(int pcId, Date bookedDate) {
        String query = "SELECT * FROM PCBook WHERE PC_ID = ? AND BookedDate = ?";

        try (Connection connection = Database.getDB().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, pcId);
            ps.setDate(2, new java.sql.Date(bookedDate.getTime()));

            ResultSet rs = ps.executeQuery();
            return !rs.next();  // Return true if no records found, indicating availability

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void AddNewBook(Integer pcId, Integer userId, Date bookedDate) {
    	pcb.AddNewBook(pcId, userId, bookedDate);
    }
	
    public void DeleteBookData(Integer bookID) {
    	pcb.DeleteBookData(bookID);
    }
	
	public PCBook GetPCBookedData(Integer pcID, Date date) {
	    return pcb.GetPCBookedData(pcID, date);
	}

	public void AssignUserToNewPC(Integer bookID, Integer newPCID) {
		pcb.AssignUserToNewPC(bookID, newPCID);
	}
	
	public PCBook GetPCBookedDetail(Integer bookID) {
	    return pcb.GetPCBookedDetail(bookID);
	}
	
	public void FinishBook(ArrayList<PCBook> pcBookList, Date chosenDate) {
		PCBook pcbFinishBook = new PCBook(this.uid);
		pcbFinishBook.FinishBook(pcBookList, chosenDate);
	}
	
	public ArrayList<PCBook> GetAllPCBookedData() {
	    return pcb.GetAllPCBookedData();
	}
	
	public ArrayList<PCBook> GetAllPCBookedUserData(Integer uid) {
	    return pcb.GetAllPCBookedUserData(uid);
	}
	
	public ArrayList<PCBook> GetPcBookedByDate(Date date) {
	    return pcb.GetPcBookedByDate(date);
	}

}
