package unoGameProject;

public enum NumberOrAction {
	
	ONE("One",1),
	TWO("Two",2),
	THREE("Three",3),
	FOUR("Four",4),
	FIVE("Five",5),
	SIX("Six",6),
	SEVEN("Seven",7),
	EIGHT("Eight",8),
	NINE("Nine",9),
	REVERSE("Reverse",20),
	SKIP("Skip",20),
	SKIP_EVERYONE("Skip Everyone",30),
	DRAW_ONE("Draw One",10),
	DRAW_FIVE("Draw Five",20),
	WILD("Wild",40),
	WILD_DRAW_TWO("Wild Draw Two",50),
	WILD_DRAW_COLOR("Wild Draw Color",60),
	FLIP("Flip",20);
	
	private final String cardName;
	private final int score;
	
	NumberOrAction(String n, int s) {
		
		cardName = n;
		score = s;
		
	}
	
	public String cardName() {
		return(cardName);
	}
	
	public int score() {
		return(score);
	}
	
	public String toString() {
		
		return(cardName);
		
	}
	
}
