package game.utils;

public class UtilMethods {

    private static final String POSSIBLE_FILES = "ABCDEFGH";

    public static int[] getWindowPos(FileC file, RankC rank, int width, int height) {
        if (file == null || rank == null) {
            return(new int[]{-1,-1});
        }
        int x = (rank.rank()-1)*width;
        int y = ((int) POSSIBLE_FILES.charAt(POSSIBLE_FILES.indexOf(file.file())))*height;
        return(new int[]{x,y});
    }

    public static String getGamePos(int x, int y, int width, int height) {
        //if (x == null || y == null) {
            //return("");
        //}
        RankC rank = RankC.values()[(int) x/width];
        FileC file = FileC.values()[(int) y/height];
        return(String.format("%s%i", file.file(), rank.rank()));
    }

}
