package unoGameProject;

// Import Java modules
import java.util.ArrayList;
import java.util.List;

public enum CardType {
	
	LIGHT("Light", new ArrayList<Color>(List.of(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW))),
	DARK("Dark", new ArrayList<Color>(List.of(Color.ORANGE, Color.PINK, Color.PURPLE, Color.TEAL)));
	
	private final String cardTypeName;
	private final ArrayList<Color> colors;
	
	CardType(String n, ArrayList<Color> c) {
		
		cardTypeName = n;
		colors = new ArrayList<Color>(c);
		
	}
	
	public String cardTypeName() {
		return(cardTypeName);
	}
	
	public ArrayList<Color> colors() {
		return(colors);
	}
	
	public String toString() {
		return(cardTypeName);
	}
	
}
