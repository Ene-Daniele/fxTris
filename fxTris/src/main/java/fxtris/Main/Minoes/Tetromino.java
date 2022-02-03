package fxtris.Main.Minoes;

import fxtris.Main.Minoes.Tetrominoes.*;
import fxtris.Main.Others.Matrix;
import fxtris.Main.GameEvents.SuperRotationSystem;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.*;

public class Tetromino {

    protected Color color;
    protected int rotationIndex;

    public void setRotationIndex(int rotationIndex) {
        this.rotationIndex = rotationIndex;
    }
    public int getRotationIndex() {
        return rotationIndex;
    }

    protected int lastIndex = 1;

    public int getLastIndex() {
        return lastIndex;
    }
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

    public Tetromino(Color color){
        this.rotationIndex = 1;
        this.collided = false;
        this.active = false;
        this.minoA = new Rectangle(TILE, TILE, color);
        this.minoB = new Rectangle(TILE, TILE, color);
        this.minoC = new Rectangle(TILE, TILE, color);
        this.minoCentral = new Rectangle(TILE, TILE, color);

        minoCentral.setY(TILE * GROUND + (TILE * 3));
        minoCentral.setX(TILE * 20);

        this.minoA.setStroke(Color.BLACK);
        this.minoB.setStroke(Color.BLACK);
        this.minoC.setStroke(Color.BLACK);
        this.minoCentral.setStroke(Color.BLACK);
    }

    public void rotationCCW(){
        int temp = this.rotationIndex;
        temp--;
        if (temp < 1){
            temp = 4;
        }
        SuperRotationSystem.rotation(this, temp);
    }
    public void rotationCW(){
        int temp = this.rotationIndex;
        temp++;
        if (temp > 4){
            temp = 1;
        }
        SuperRotationSystem.rotation(this, temp);
    }
    public void rotation180(){
        int temp = this.rotationIndex;

        if (!(temp + 2 > 4)){
            temp += 2;
        } else {
            temp -= 2;
        }

        SuperRotationSystem.rotation(this, temp);
    }


    public static Tetromino getID(Tetromino tetromino){
        Tetromino temp = null;

        switch (tetromino.tetrominoID){
            case 1:
                temp = new I();
                break;
            case 2:
                temp = new J();
                break;
            case 3:
                temp = new L();
                break;
            case 4:
                temp = new O();
                break;
            case 5:
                temp = new S();
                break;
            case 6:
                temp = new T();
                break;
            case 7:
                temp = new Z();
                break;
        }
        return temp;
    }

    private boolean generalCollision(Rectangle mino, Rectangle deadMino){
        return
                mino.getY() + TILE == deadMino.getY()
                && mino.getX() == deadMino.getX();
    }

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

    private final boolean groundCheck(){
        return this.minoCentral.getY() + TILE == GROUND * TILE
                || this.minoA.getY() + TILE == GROUND * TILE
                || this.minoB.getY() + TILE == GROUND * TILE
                || this.minoC.getY() + TILE == GROUND * TILE;
    }

    public void update(){/* Override in subclasses */}
    public boolean isI(){
        return false;
    }
    public boolean isO(){
        return false;
    }
}
