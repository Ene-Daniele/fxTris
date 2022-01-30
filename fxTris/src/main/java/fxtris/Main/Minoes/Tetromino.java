package fxtris.Main.Minoes;

import fxtris.Main.Main;
import fxtris.Main.Others.Matrix;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.*;

public class Tetromino {

    protected Color color;
    protected int rotationIndex;
    protected boolean active;
    private boolean collided;

    public boolean isCollided() {
        return collided;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public Tetromino(Tetromino other){
        this.rotationIndex = other.rotationIndex;
        this.minoCentral = new Rectangle(other.getMinoCentral().getX(), other.getMinoCentral().getY(), TILE, TILE);
        this.minoA = new Rectangle(other.getMinoA().getX(), other.getMinoA().getY(), TILE, TILE);
        this.minoB = new Rectangle(other.getMinoB().getX(), other.getMinoB().getY(), TILE, TILE);
        this.minoC = new Rectangle(other.getMinoC().getX(), other.getMinoC().getY(), TILE, TILE);
        this.getMinoCentral().setFill(Color.DARKSLATEGRAY);
        this.getMinoA().setFill(Color.DARKSLATEGRAY);
        this.getMinoB().setFill(Color.DARKSLATEGRAY);
        this.getMinoC().setFill(Color.DARKSLATEGRAY);
    }

    protected Rectangle minoA;
    protected Rectangle minoB;
    protected Rectangle minoC;
    protected Rectangle minoCentral;

    private int oneSlide = 60;
    private int fourSlide = 240;

    public Tetromino(){}

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

    protected Tetromino(Color color){
        this.rotationIndex = 1;
        this.collided = false;
        this.active = false;
        this.minoA = new Rectangle(TILE, TILE, color);
        this.minoB = new Rectangle(TILE, TILE, color);
        this.minoC = new Rectangle(TILE, TILE, color);
        this.minoCentral = new Rectangle(TILE, TILE, color);
        minoCentral.setY(100);
        minoCentral.setX(TILE * 5);
    }

    public void rotationCCW(){
        this.rotationIndex--;
        if (this.rotationIndex < 1){
            this.rotationIndex = 4;
        }
    }
    public void rotationCW(){
        this.rotationIndex++;
        if (this.rotationIndex > 4){
            this.rotationIndex = 1;
        }
    }
    public void rotation180(){
        switch (this.rotationIndex){
            case 1:
                this.rotationIndex = 3;
            case 2:
                this.rotationIndex = 4;
            case 3:
                this.rotationIndex = 1;
            case 4:
                this.rotationIndex = 2;
        }
    }

    private boolean generalCollision(Rectangle mino, Rectangle deadMino){
        return
                mino.getY() + TILE == deadMino.getY()
                && mino.getX() == deadMino.getX();
    }

    public boolean isColliding(){
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


    public boolean isntCollisingHorizontally(int sign){
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

    public final boolean canRotate(Tetromino tetromino, int newIndex){
        boolean temp = false;
        tetromino.rotationIndex = newIndex;
        //TODO Check if you can rotate with the new index, do this After having done all rotation indexes
        return temp;
    }
}
