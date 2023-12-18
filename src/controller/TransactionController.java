package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Database;
import javafx.stage.Stage;
import model.PCBook;
import model.TransactionDetail;
import model.TransactionHeader;
import view.AdminHistoryView;
import view.AdminPCView;
import view.AdminTransactionDetailView;
import view.CustomerHistoryView;
import view.CustomerPCView;

public class TransactionController {
	
	private Stage primaryStage;
	private CustomerHistoryView customerHistory;
	private AdminHistoryView adminHistory;
	AdminTransactionDetailView adminDetail;
	private Integer uid;
	private TransactionDetail td = new TransactionDetail();
	private TransactionHeader th = new TransactionHeader();
	
	public TransactionController() {
		
	}
	
	public TransactionController(CustomerHistoryView custView, Integer uid) {
		// TODO Auto-generated constructor stub
		this.customerHistory = custView;
		this.uid = uid;
		initializeCustomerTransactions();
		loadCustomerTableDataTransactions();
	}
	
	public void initializeCustomerTransactions() {
		customerHistory.getBackButton().setOnAction(event -> {
			primaryStage = customerHistory.getPrimaryStage();
    		CustomerPCView pc = new CustomerPCView(primaryStage, uid);
    		PCController p = new PCController(pc, uid);
        });
	}
	
	private void loadCustomerTableDataTransactions() {
		// TODO Auto-generated method stub
		ArrayList<TransactionDetail> td = GetUserTransactionDetail(this.uid);
		customerHistory.getTable().getItems().setAll(td);
	}
	
	public TransactionController(AdminHistoryView custView, Integer uid) {
		// TODO Auto-generated constructor stub
		this.adminHistory = custView;
		this.uid = uid;
		initializeAdminTransactions();
		loadAdminTableDataTransactions();
	}
	
	public void initializeAdminTransactions() {
		adminHistory.getBackButton().setOnAction(event -> {
			primaryStage = adminHistory.getPrimaryStage();
			AdminPCView pc = new AdminPCView(primaryStage, uid);
    		PCController p = new PCController(pc, uid);
        });
		
		adminHistory.getDetailButton().setOnAction(event -> {
			primaryStage = adminHistory.getPrimaryStage();
			AdminTransactionDetailView atdv = new AdminTransactionDetailView(primaryStage, uid);
			Integer inputValue = Integer.parseInt(adminHistory.getIdInput().getText());
			System.out.println(inputValue);
    		TransactionController tc = new TransactionController(atdv, uid, inputValue);
        });
	}
	
	private void loadAdminTableDataTransactions() {
		ArrayList<TransactionHeader> th = GetAllTransactionHeaderData();
		adminHistory.getThTable().getItems().setAll(th);
	}
	
	public TransactionController(AdminTransactionDetailView custView, Integer uid, Integer TransactionID) {
		// TODO Auto-generated constructor stub
		this.adminDetail = custView;
		this.uid = uid;
		initializeAdminDetailTransactions();
		loadAdminDetailTableDataTransactions(TransactionID);
	}
	
	private void initializeAdminDetailTransactions() {
		// TODO Auto-generated method stub
		adminDetail.getBackButton().setOnAction(event -> {
			primaryStage = adminDetail.getPrimaryStage();
			AdminHistoryView ahv = new AdminHistoryView(primaryStage, uid);
    		TransactionController tc = new TransactionController(ahv, uid);
        });
	}

	private void loadAdminDetailTableDataTransactions(Integer TransactionID) {
		// TODO Auto-generated method stub
		ArrayList<TransactionDetail> td = GetAllTransactionDetail(TransactionID);
		adminDetail.getTdTable().getItems().setAll(td);
	}

	public void AddTransaction(Integer staffID, Date transactionDate, ArrayList<PCBook> pcBooks) {
	    TransactionHeader transactionHeader = th.AddNewTransactionHeader(staffID, transactionDate);

	    if (transactionHeader != null) {
	        td.AddTransactionDetail(transactionHeader.getTransactionID(), pcBooks);
	    }
	}

	public ArrayList<TransactionHeader> GetAllTransactionHeaderData() {
		return th.GetAllTransactionHeaderData();
	}
	
	public ArrayList<TransactionDetail> GetAllTransactionDetail(Integer TransactionID) {
		return td.GetAllTransactionDetail(TransactionID);
	}
	
	public ArrayList<TransactionDetail> GetUserTransactionDetail(Integer userID) {
		return td.GetUserTransactionDetail(userID);
    }
}
