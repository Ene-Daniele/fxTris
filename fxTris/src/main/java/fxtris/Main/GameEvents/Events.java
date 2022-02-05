package fxtris.Main.GameEvents;

import fxtris.Main.Controls.Keyboard;
import fxtris.Main.Main;
import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Others.Matrix;
import fxtris.Main.Queue.Queue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.lang.annotation.Documented;
import java.util.ArrayList;

import static fxtris.Main.Main.currentTetromino;
import static fxtris.Main.Others.GlobalValues.*;

/**
 * Main events happening during the game, and an array of variables relative to those
 */
public class Events {

    public static Tetromino shadow = new Tetromino();
    public static Tetromino hold = new Tetromino();
    public static Tetromino save = new Tetromino();

    private static int tempARR = 0;
    private static int tempDAS = 0;
    private static int tempGRV = 0;
    public static void setTempGRV(int tempGRV) {
        Events.tempGRV = tempGRV;
    }

    public static boolean swapped = false;
    public static boolean firstSwap = true;
    public static boolean swapping = false;
    public static boolean cwed = false;
    public static boolean ccwed = false;
    public static boolean one80ed = false;
    private static boolean singleTap = true;
    public static boolean hardDropped = false;
    public static boolean hitWall = false;
    public static boolean restarted = false;

    /**
     * Increases tempGRV by 1 and if its above GRAVITY (or GRAVITY / sdf if Keyboard.isSoftDrop) push the tetromino down by one tile and reset tempGRV
     */
    public static void gravity() {

        tempGRV++;
        if (Keyboard.isSoftDrop()) {
            if (!currentTetromino.verticalCollision()) {
                if (tempGRV > GRAVITY / getSdf()) {
                    tempGRV = 0;
                    currentTetromino.getMinoCentral().setY(currentTetromino.getMinoCentral().getY() + TILE);
                }
            }
        } else { //? Dont know if i should make a function for barely 2 lines
            if (tempGRV > GRAVITY) {
                tempGRV = 0;
                currentTetromino.getMinoCentral().setY(currentTetromino.getMinoCentral().getY() + TILE);
            }
        }
    }

    /**
     * Checks for collisions and sets a tetromino inactive need there be
     */
    public static void collisions(){

        if (currentTetromino.verticalCollision()){
            currentTetromino.setOneSlide(currentTetromino.getOneSlide() -1);
            currentTetromino.setFourSlide(currentTetromino.getFourSlide() -1);
            tempGRV = 0;
        } else if (currentTetromino.isCollided()){
            currentTetromino.setOneSlide(60);
        }

        if (currentTetromino.getOneSlide() < 0 || currentTetromino.getFourSlide() < 0) {
            currentTetromino.setActive(false);
        }
    }

    /**
     * Swaps the tetromino with the one being held, for the first cyccle it just replaces an empty held tetromino instead
     */
    public static void swap(){
        if (Keyboard.isSwap()) {
            if (!swapping){
                swapping = true;
                if (!swapped){
                    if (!firstSwap){
                        Matrix.removeFromRoot(currentTetromino);
                        Tetromino temp = Tetromino.getID(currentTetromino);

                        currentTetromino = Tetromino.getID(hold);
                        hold = temp;

                        currentTetromino.getMinoCentral().setY(TILE * 3);
                        currentTetromino.getMinoCentral().setX(TILE * 11);
                        currentTetromino.setActive(true);
                        Matrix.removeFromRoot(shadow);
                        shadow = new Tetromino(currentTetromino, Color.DARKSLATEGRAY);
                        currentTetromino.update();
                        shadow.update();
                        Matrix.addToRoot(currentTetromino);

                        Matrix.removeFromRoot(save);
                        save = Tetromino.getID(hold);
                        save.getMinoCentral().setX(TILE * 3);
                        save.getMinoCentral().setY(TILE * 7);
                        save.paint(hold.getMinoCentral().getFill());
                        save.update();
                        Matrix.addToRoot(save);
                    } else {
                        hold = Tetromino.getID(currentTetromino);
                        Matrix.removeFromRoot(currentTetromino);
                        currentTetromino = Queue.getList().get(0);
                        currentTetromino.getMinoCentral().setY(TILE * 3);
                        currentTetromino.getMinoCentral().setX(TILE * 11);
                        currentTetromino.update();
                        currentTetromino.setActive(true);
                        Matrix.removeFromRoot(shadow);
                        shadow = new Tetromino(currentTetromino, Color.DARKSLATEGRAY);
                        Queue.cycleList();
                        firstSwap = false;

                        Matrix.removeFromRoot(save);
                        save = Tetromino.getID(hold);
                        save.getMinoCentral().setX(TILE * 3);
                        save.getMinoCentral().setY(TILE * 7);
                        save.paint(hold.getMinoCentral().getFill());
                        save.update();
                        Matrix.addToRoot(save);
                    }
                    swapped = true;
                }
            }
        } else {
            swapping = false;
        }
    }

    /**
     * Places the tetromino and Cycles the list
     */
    public static void placeTetromino(){

        //Removing old tetromino
        Matrix.removeFromRoot(currentTetromino);
        //Replacing it with an inactive one
        Matrix.addTetromino(currentTetromino);

        currentTetromino = Queue.getList().get(0);
        currentTetromino.getMinoCentral().setY(TILE * 3);
        currentTetromino.getMinoCentral().setX(TILE * 11);
        currentTetromino.setActive(true);
        swapped = false;

        //Removing old shadow
        Matrix.removeFromRoot(shadow);

        shadow = new Tetromino(currentTetromino, Color.DARKSLATEGRAY);

        Queue.cycleList();
    }

    /**
     * Refreshes the shadow to be under the tetromino at all times
     */
    public static void shadow(){

        //Bringing the shadow to the new position, for some reason shadow.update() does not work
        shadow.getMinoCentral().setY(currentTetromino.getMinoCentral().getY());
        shadow.getMinoA().setY(currentTetromino.getMinoA().getY());
        shadow.getMinoB().setY(currentTetromino.getMinoB().getY());
        shadow.getMinoC().setY(currentTetromino.getMinoC().getY());
        shadow.getMinoCentral().setX(currentTetromino.getMinoCentral().getX());
        shadow.getMinoA().setX(currentTetromino.getMinoA().getX());
        shadow.getMinoB().setX(currentTetromino.getMinoB().getX());
        shadow.getMinoC().setX(currentTetromino.getMinoC().getX());

        //Pushing the shadow down
        while (!shadow.verticalCollision()) {
            shadow.getMinoCentral().setY(shadow.getMinoCentral().getY() + TILE);
            shadow.getMinoA().setY(shadow.getMinoA().getY() + TILE);
            shadow.getMinoB().setY(shadow.getMinoB().getY() + TILE);
            shadow.getMinoC().setY(shadow.getMinoC().getY() + TILE);
            shadow.update();
        }
        //Add the shadow
        if (!Main.root.getChildren().contains(shadow.getMinoCentral())){
            Matrix.addToRoot(shadow);
        }
        //Dont overwrite the tetromino with the shadow
        currentTetromino.getMinoCentral().toFront();
        currentTetromino.getMinoA().toFront();
        currentTetromino.getMinoB().toFront();
        currentTetromino.getMinoC().toFront();

    }

    /**
     * Calculates the new rotation index, then calls the Super Rotation System with it
     */
    public static void rotation() {
        if (Keyboard.isRotateCW() && !cwed) {
            cwed = true;
            currentTetromino.rotationCW();
            currentTetromino.setOneSlide(60);
        } else if (!Keyboard.isRotateCW()) {
            cwed = false;
        }

        if (Keyboard.isRotateCCW() && !ccwed) {
            ccwed = true;
            currentTetromino.rotationCCW();
            currentTetromino.setOneSlide(60);
        } else if (!Keyboard.isRotateCCW()) {
            ccwed = false;
        }

        if (Keyboard.isRotate180() && !one80ed) {
            one80ed = true;
            currentTetromino.rotation180();
            currentTetromino.setOneSlide(60);
        } else if (!Keyboard.isRotate180()) {
            one80ed = false;
        }
    }

    /**
     * Checks if the tetromino isnt giong to collide horizontally
     */
    public static void borderCheck() {
        currentTetromino.update();
        if (
                currentTetromino.getMinoCentral().getX() - TILE != LEFTWALL * TILE
                        && currentTetromino.getMinoA().getX() - TILE != LEFTWALL * TILE
                        && currentTetromino.getMinoB().getX() - TILE != LEFTWALL * TILE
                        && currentTetromino.getMinoC().getX() - TILE != LEFTWALL * TILE
                        && currentTetromino.horizontalCollision(-1)
        ) {
            if (Keyboard.isLeft()) {
                movement(-1); //* Negative
                //? It mutliplies TILE by the given parameter, so it checks left or right, -1 or 1
            } else {
                hitWall = false;
            }
        } else {
            if (!hitWall){
                hitWall = true;
                tempDAS = 0;
            }
        }
        if (
                currentTetromino.getMinoCentral().getX() + TILE != RIGHTWALL * TILE
                        && currentTetromino.getMinoA().getX() + TILE != RIGHTWALL * TILE
                        && currentTetromino.getMinoB().getX() + TILE != RIGHTWALL * TILE
                        && currentTetromino.getMinoC().getX() + TILE != RIGHTWALL * TILE
                        && currentTetromino.horizontalCollision(1)
        ) {
            if (Keyboard.isRight()) {
                movement(1); //* Positive
            } else {
                hitWall = false;
            }
        } else {
            if (!hitWall){
                hitWall = true;
                tempDAS = 0;
            }
        }
    }

    /**
     * Resets the matrix when pressing the bound key
     */
    public static void restart(){
        if (Keyboard.isRestart() && !restarted){
            reset();
            restarted = true;
        } else if (!Keyboard.isRestart()){
            restarted = false;
        }
    }

    /**
     * Moves the tetromino in the direction given by the method above
     * @param sign Direction
     */
    private static void movement(int sign) {

        if (tempDAS < getDas()) {
            tempDAS++;
            if (singleTap) {
                singleTap = false;
                currentTetromino.getMinoCentral().setX(currentTetromino.getMinoCentral().getX() + (TILE * sign));
            }

        } else {
            tempARR++;
            if (tempARR > getArr()) {
                tempARR = 0;
                currentTetromino.getMinoCentral().setX(currentTetromino.getMinoCentral().getX() + (TILE * sign));
            }
        }
    }

    /**
     * Hard drops the tetromino with a while loop
     */
    public static void hardDrop() {
        if (Keyboard.isHardDrop()) {
            if (!hardDropped) {
                hardDropped = true;
                while (!currentTetromino.verticalCollision()) {
                    currentTetromino.getMinoCentral().setY(currentTetromino.getMinoCentral().getY() + TILE);
                    currentTetromino.update();
                }
                currentTetromino.setActive(false);
            }
        } else {
            hardDropped = false;
        }

    }

    /**
     * Needed to reset DAS after releasing a key
     */
    public static void resetXMovement() {
        tempARR = 0;
        tempDAS = 0;
        singleTap = true;
    }

    /**
     * @param mino1 Dead mino checking on the active mino
     * @param mino2 Actice mino being checked
     * @return If the 2 minos are overlapping
     */
    private static boolean overlap(Rectangle mino1, Rectangle mino2){
        return mino1.getY() == mino2.getY() && mino1.getX() == mino2.getX();
    }

    /**
     * Needed to fix corner collisions and a weird I tetromino bug where it would fly out of the matrix
     */
    public static void fixOverlapBug(){

        for (ArrayList<Rectangle> i : Matrix.getMatrixGrid()) {
            for (Rectangle deadMino : i) {
                if (
                        overlap(deadMino, currentTetromino.getMinoCentral())
                                || overlap(deadMino, currentTetromino.getMinoA())
                                || overlap(deadMino, currentTetromino.getMinoB())
                                || overlap(deadMino, currentTetromino.getMinoC())
                )
                {
                    currentTetromino.getMinoCentral().setY(currentTetromino.getMinoCentral().getY() - TILE);
                }
            }
        }

        if (    currentTetromino.getMinoCentral().getX() >= (double) RIGHTWALL * TILE
                || currentTetromino.getMinoA().getX() >= (double) RIGHTWALL * TILE
                || currentTetromino.getMinoB().getX() >= (double) RIGHTWALL * TILE
                || currentTetromino.getMinoC().getX() >= (double) RIGHTWALL * TILE

                || currentTetromino.getMinoCentral().getX() <= (double) LEFTWALL * TILE
                || currentTetromino.getMinoA().getX() <= (double) LEFTWALL * TILE
                || currentTetromino.getMinoB().getX() <= (double) LEFTWALL * TILE
                || currentTetromino.getMinoC().getX() <= (double) LEFTWALL * TILE

                || currentTetromino.getMinoCentral().getY() >= (double) GROUND * TILE
                || currentTetromino.getMinoA().getY() >= (double) GROUND * TILE
                || currentTetromino.getMinoB().getY() >= (double) GROUND * TILE
                || currentTetromino.getMinoC().getY() >= (double) GROUND * TILE
        )
        {
            if (currentTetromino.isI()){
                currentTetromino.getMinoCentral().setX(TILE * 11);
                currentTetromino.update();
            }
        }
    }

    /**
     * Checks if youre topping out
     * @param currentTetromino Tetromino that may cause to top out
     * @return If the given tetromino is causing a top out
     */
    public static boolean topOut(Tetromino currentTetromino){
        boolean temp = false;
        if
        ((
            currentTetromino.getMinoCentral().getY() < CEILING * TILE
            && currentTetromino.getMinoA().getY() < CEILING * TILE
            && currentTetromino.getMinoB().getY() < CEILING * TILE
            && currentTetromino.getMinoC().getY() < CEILING * TILE
        ) && !(Matrix.getMatrixGrid().get(Matrix.getMatrixGrid().size() - 2)).isEmpty())
        {
            temp = true;
        }
        return temp;
    }

    /**
     * Resets the game to a new state, also through the use of the Matrix.reset() method
     */
    public static void reset(){
        Matrix.reset();
        Matrix.removeFromRoot(currentTetromino);

        currentTetromino = Queue.getList().get(0);
        currentTetromino.getMinoCentral().setY(TILE * 3);
        currentTetromino.getMinoCentral().setX(TILE * 11);
        currentTetromino.setActive(true);
        swapped = false;

        //Removing old shadow
        Matrix.removeFromRoot(shadow);

        shadow = new Tetromino(currentTetromino, Color.DARKSLATEGRAY);

        Queue.cycleList();
    }
}
