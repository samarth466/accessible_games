package game.utils;

public enum FileC {

    A('A'),
    B('B'),
    C('C'),
    D('D'),
    E('E'),
    F('F'),
    G('G'),
    H('H');

    private final Character file;

    FileC(char f) {
        file = f;
    }

    public char file() {
        return(file);
    }

    public String toString() {
        return(Character.toString(file));
    }

}
