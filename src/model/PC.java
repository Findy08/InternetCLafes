package model;

public class PC {
	
	private Integer PC_ID;
	private String PC_Condition;
	
	public PC(Integer PC_ID, String PC_Condition) {
		super();
		this.PC_ID = PC_ID;
		this.PC_Condition = PC_Condition;
	}
	
	public PC() {
		
	}

	public Integer getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}

	public String getPC_Condition() {
		return PC_Condition;
	}

	public void setPC_Condition(String pC_Condition) {
		PC_Condition = pC_Condition;
	}

}
