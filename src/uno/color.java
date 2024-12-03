package uno;

public enum Color {
	
	BLUE("Blue"),
	GREEN("Green"),
	ORANGE("Orange"),
	PINK("Pink"),
	PURPLE("Purple"),
	RED("Red"),
	TEAL("Teal"),
	YELLOW("Yellow"),
	UNDEFINED("");
	
	private final String colorName;
	
	Color(String cN) {
		
		colorName = cN;
		
	}
	
	public String colorName() {
		return(colorName);
	}
	
	public String toString() {
		
		return(colorName);
		
	}
	
}
