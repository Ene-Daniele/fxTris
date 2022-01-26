package fxtris.Main.Others;

public class Keyboard {
    private static boolean left = false;
    public static boolean isLeft() {
        return left;
    }
    public static void setLeft(boolean left) {
        Keyboard.left = left;
    }

    private static boolean right = false;
    public static boolean isRight() {
        return right;
    }
    public static void setRight(boolean right) {
        Keyboard.right = right;
    }

    private static boolean softDrop = false;
    public static boolean isSoftDrop() {
        return softDrop;
    }
    public static void setSoftDrop(boolean softDrop) {
        Keyboard.softDrop = softDrop;
    }

    private static boolean hardDrop = false;
    public static boolean isHardDrop() {
        return hardDrop;
    }
    public static void setHardDrop(boolean hardDrop) {
        Keyboard.hardDrop = hardDrop;
    }

    private static boolean rotateCCW = false;
    public static boolean isRotateCCW() {
        return rotateCCW;
    }
    public static void setRotateCCW(boolean rotateCCW) {
        Keyboard.rotateCCW = rotateCCW;
    }

    private static boolean rotateCW = false;
    public static boolean isRotateCW() {
        return rotateCW;
    }
    public static void setRotateCW(boolean rotateCW) {
        Keyboard.rotateCW = rotateCW;
    }

    private static boolean rotate180 = false;
    public static boolean isRotate180() {
        return rotate180;
    }
    public static void setRotate180(boolean rotate180) {
        Keyboard.rotate180 = rotate180;
    }

    private static boolean swap = false;
    public static boolean isSwap() {
        return swap;
    }
    public static void setSwap(boolean swap) {
        Keyboard.swap = swap;
    }

    private static boolean restart = false;
    public static boolean isRestart() {
        return restart;
    }
    public static void setRestart(boolean restart) {
        Keyboard.restart = restart;
    }
}
