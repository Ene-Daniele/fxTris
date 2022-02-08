package fxtris.Main.Minoes;

import fxtris.Main.GameEvents.SuperRotationSystem;
import fxtris.Main.Minoes.Tetrominoes.*;
import fxtris.Main.Others.Matrix;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.GROUND;
import static fxtris.Main.Others.GlobalValues.TILE;

public class Tetromino {

    protected int rotationIndex;
    public void setRotationIndex(int rotationIndex) {
        this.rotationIndex = rotationIndex;
    }
    public int getRotationIndex() {
        return rotationIndex;
    }

    protected int lastIndex = 1;
    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    protected boolean active;
    private boolean collided;
    public int tetrominoID;

    public boolean isCollided() {
        return collided;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Clones a tetromino
     * @param other Object being cloned
     * @param color Color of the cloned object
     */
    public Tetromino(Tetromino other, Paint color){
        this.rotationIndex = other.rotationIndex;
        this.minoCentral = new Rectangle(other.getMinoCentral().getX(), other.getMinoCentral().getY(), TILE, TILE);
        this.minoA = new Rectangle(other.getMinoA().getX(), other.getMinoA().getY(), TILE, TILE);
        this.minoB = new Rectangle(other.getMinoB().getX(), other.getMinoB().getY(), TILE, TILE);
        this.minoC = new Rectangle(other.getMinoC().getX(), other.getMinoC().getY(), TILE, TILE);
        paint(color);
    }

    protected Rectangle minoA;
    protected Rectangle minoB;
    protected Rectangle minoC;
    protected Rectangle minoCentral;

    private int oneSlide = 60;
    private int fourSlide = 240;

    public Tetromino(){}

    /**
     * Paints the tetromino
     * @param color Color to paint
     */
    public void paint(Paint color){

        this.getMinoCentral().setFill(color);
        this.getMinoA().setFill(color);
        this.getMinoB().setFill(color);
        this.getMinoC().setFill(color);
    }

    public Rectangle getMinoA() {
        return minoA;
    }
    public Rectangle getMinoB() {
        return minoB;
    }
    public Rectangle getMinoC() {
        return minoC;
    }
    public Rectangle getMinoCentral() {
        return minoCentral;
    }

    public int getOneSlide() {
        return oneSlide;
    }
    public int getFourSlide() {
        return fourSlide;
    }
    public void setOneSlide(int oneSlide) {
        this.oneSlide = oneSlide;
    }
    public void setFourSlide(int fourSlide) {
        this.fourSlide = fourSlide;
    }

    /**
     * Tetromino constructor, mostly used in the sub classes
     */
    public Tetromino(Color color){
        this.rotationIndex = 1;
        this.collided = false;
        this.active = false;
        this.minoA = new Rectangle(TILE, TILE, color);
        this.minoB = new Rectangle(TILE, TILE, color);
        this.minoC = new Rectangle(TILE, TILE, color);
        this.minoCentral = new Rectangle(TILE, TILE, color);

        //Position in the bottom of the queue
        minoCentral.setY(TILE * GROUND + (TILE * 3));
        minoCentral.setX(TILE * 20);
    }

    /**
     * Calls the rotation() function giving the new rotation index calculated from decreasing the current rotation index
     */
    public void rotationCCW(){
        int temp = this.rotationIndex;
        temp--;
        if (temp < 1){
            temp = 4;
        }
        SuperRotationSystem.rotation(this, temp);
    }

    /**
     * Calls the rotation() function giving the new rotation index calculated from increasing the current rotation index
     */
    public void rotationCW(){
        int temp = this.rotationIndex;
        temp++;
        if (temp > 4){
            temp = 1;
        }
        SuperRotationSystem.rotation(this, temp);
    }

    /**
     * Calls the rotation() function giving the new rotation index calculated from increasing or decreasing the current rotation index
     */
    public void rotation180(){
        int temp = this.rotationIndex;

        if (!(temp + 2 > 4)){
            temp += 2;
        } else {
            temp -= 2;
        }

        SuperRotationSystem.rotation(this, temp);
    }

    /**
     * Factory method design pattern, returns an instance of Tetromino's subclasses depending on the current tetromino's ID
     * @param tetromino Object being identified
     * @return A new object based on the tetromino's ID
     */
    public static Tetromino getID(Tetromino tetromino){

        return switch (tetromino.tetrominoID) {
            case 1 -> new I();
            case 2 -> new J();
            case 3 -> new L();
            case 4 -> new O();
            case 5 -> new S();
            case 6 -> new T();
            case 7 -> new Z();
            default -> null;
        };
    }

    /**
     * Checks if a mino is about to intersect with another mino
     * @param mino Active mino
     * @param deadMino Dead Mino
     * @return If they are about to intersect vertically
     */
    private boolean generalCollision(Rectangle mino, Rectangle deadMino){
        return
                mino.getY() + TILE == deadMino.getY()
                && mino.getX() == deadMino.getX();
    }

    /**
     * @return If the current tetromino is colliding vertically
     */
    public boolean verticalCollision(){
        boolean temp = false;

        if (groundCheck()){
            temp = true;
            this.collided = true;
        }

        if (!temp) {
            for (ArrayList<Rectangle> i : Matrix.getMatrixGrid()) {
                for (Rectangle deadMino : i) {
                    if
                    (
                            generalCollision(minoCentral, deadMino)
                            || generalCollision(minoA, deadMino)
                            || generalCollision(minoB, deadMino)
                            || generalCollision(minoC, deadMino)
                    )
                    {
                        temp = true;
                        this.collided = true;
                    }
                }
            }
        }
        return temp;
    }

    /**
     * @param sign The direction it should check
     * @return If the current tetromino is about to collide in the specified direction
     */
    public boolean horizontalCollision(int sign){
        boolean temp = true;
        for (ArrayList<Rectangle> i : Matrix.getMatrixGrid()) {
            for (Rectangle deadMino : i) {
                if (
                    this.minoCentral.getX() + (TILE * sign) == deadMino.getX()
                            && this.minoCentral.getY() == deadMino.getY()
                    || this.minoA.getX() + (TILE * sign) == deadMino.getX()
                            && this.minoA.getY() == deadMino.getY()
                    || this.minoB.getX() + (TILE * sign) == deadMino.getX()
                            && this.minoB.getY() == deadMino.getY()
                    || this.minoC.getX() + (TILE * sign) == deadMino.getX()
                            && this.minoC.getY() == deadMino.getY()
                )
                {
                    temp = false;
                }
            }
        }
        return temp;
    }

    /**
     * @return If the current tetromino is about to collide with the ground
     */
    private boolean groundCheck(){
        return this.minoCentral.getY() + TILE == GROUND * TILE
                || this.minoA.getY() + TILE == GROUND * TILE
                || this.minoB.getY() + TILE == GROUND * TILE
                || this.minoC.getY() + TILE == GROUND * TILE;
    }

    /**
     * Updates the minos relatively to the central mino, the method is overridden in the subclasses for their specific shapes
     */
    public void update(){/* Override in subclasses */}

    /**
     * @return If the current tetromino is an I
     */
    public boolean isI(){
        return false;
    }

    /**
     * @return If the current tetromino is an I
     */
    public boolean isO(){
        return false;
    }
}
