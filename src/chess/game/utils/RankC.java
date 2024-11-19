package game.utils;

public enum RankC {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    private final int rank;

    RankC(int r) {
        rank = r;
    }

    public int rank() {
        return(rank);
    }

    public String toString() {
        return(String.valueOf(rank));
    }

}
