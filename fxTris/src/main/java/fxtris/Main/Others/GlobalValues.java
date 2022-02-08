package fxtris.Main.Others;

/**
 * A class that contains global values, used everywhere
 */
public class GlobalValues {

    private static int das = 6; //Frames before the mino starts dasing
    public static int getDas() {
        return das;
    }
    public static void setDas(int das) {
        GlobalValues.das = das;
    }

    private static int arr = 0; //Frames in between the tetrominoes movements after the das
    public static int getArr() {
        return arr;
    }
    public static void setArr(int arr) {
        GlobalValues.arr = arr;
    }

    private static int sdf = 61; //Divide gravity by this when soft dropping;
    public static int getSdf() {
        return sdf;
    }
    public static void setSdf(int sdf) {
        GlobalValues.sdf = sdf;
    }

    public static final int GRAVITY = 60;
    public static final int TILE = 25; //Size of the tiles in the grip
    public static final int LEFTWALL = 6;
    public static final int RIGHTWALL = 17;
    public static final int GROUND = 25;
    public static final int CEILING = 5;
}
