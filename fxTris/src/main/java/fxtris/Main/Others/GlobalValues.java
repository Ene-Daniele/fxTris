package fxtris.Main.Others;

public class GlobalValues {

    private static int das = 7; //Frames before the mino starts dasing
    public static int getDas() {
        return das;
    }
    public static void setDas(int das) {
        GlobalValues.das = das;
    }

    public static int arr = 3; //Frames in between the tetrominoes movements after the das
    public static int getArr() {
        return arr;
    }
    public static void setArr(int arr) {
        GlobalValues.arr = arr;
    }

    public static int sdf = 5; //Divide gravity by this when soft dropping;
    public static int getSdf() {
        return sdf;
    }
    public static void setSdf(int sdf) {
        GlobalValues.sdf = sdf;
    }

    public static final int gravity = 60; //Decrease by 1 each frame until this is 0, then let the tetromino fall by 1 tile and reset to 60
    public static int getGravity() {
        return gravity;
    }

    public static final int tile = 50; //Size of the tiles in the grip
    public static int getTile() {
        return tile;
    }

    public static final int leftWall = 0;
    public static int getLeftWall() {
        return leftWall;
    }

    public static final int rightWall = 11;
    public static int getRightWall() {
        return rightWall;
    }

    public static final int ground = 25;
    public static int getGround() {
        return ground;
    }

    public static final int ceiling = 5;
    public static int getCeiling() {
        return ceiling;
    }
}
