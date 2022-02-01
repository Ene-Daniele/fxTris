package fxtris.Main.Minoes;

import fxtris.Main.Main;
import fxtris.Main.Others.Matrix;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.*;

public class Tetromino {

    protected Color color;
    protected int rotationIndex;
    protected int lastIndex = 1;
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

        //! Change this to show up in the queue instead
        minoCentral.setY(TILE * GROUND + 40);
        minoCentral.setX(TILE * 14);

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
        this.rotation(temp);
    }
    public void rotationCW(){
        int temp = this.rotationIndex;
        temp++;
        if (temp > 4){
            temp = 1;
        }
        this.rotation(temp);
    }
    public void rotation180(){
        int temp = this.rotationIndex;

        if (!(temp + 2 > 4)){
            temp += 2;
        } else {
            temp -= 2;
        }

        this.rotation(temp);
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
    public boolean isI(){
        return false;
    }
    public boolean isO(){
        return false;
    }

    //* Super Rotation System
    private boolean overlap(Rectangle mino1, Rectangle mino2){
        return
                mino1.getY() == mino2.getY() && mino1.getX() == mino2.getX()
                || mino2.getX() > RIGHTWALL * TILE
                || mino2.getX() < LEFTWALL
                || mino2.getY() > GRAVITY * TILE;
    }
    public final boolean canRotate(){
        boolean temp = true;
        for (ArrayList<Rectangle> i : Matrix.getMatrixGrid()) {
            for (Rectangle deadMino : i) {
                if (
                    overlap(deadMino, this.minoCentral)
                    || overlap(deadMino, this.minoA)
                    || overlap(deadMino, this.minoB)
                    || overlap(deadMino, this.minoC)
                )
                {
                    temp = false;
                }
            }
        }
        return temp;
    }
    public void offset(int x, int y){
        this.minoCentral.setX(this.minoCentral.getX() + (x * TILE));
        this.minoCentral.setY(this.minoCentral.getY() + (x * TILE));
        this.update();
    }

    public void rotation(int newId){
        //? https://tetris.wiki/Super_Rotation_System

        int oldIndex = this.rotationIndex;
        this.lastIndex = this.rotationIndex;
        this.rotationIndex = newId;
        this.update();

        if (!this.isI()){ //* <J, L, O, S, Z, T> Rotations
            switch (this.rotationIndex){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        } else { //* <I> Rotations
            switch (this.rotationIndex) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
    }
}
