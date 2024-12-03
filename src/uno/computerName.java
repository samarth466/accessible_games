package uno;

public enum ComputerName {
	
	// Values
	JOE("Joe"),
	SARAH("Sarah"),
	MARIAN("Marian"),
	SUSAN("Susan"),
	TYLER("Tyler"),
	BOB("Bob"),
	DAVE("Dave");
	
	private final String computerName;
	
	ComputerName(String cN) {
		
		computerName = cN;
		
	}
	
	public String computerName() {
		return(computerName);
	}
	
	public String toString() {
		return(computerName);
	}
	
}
