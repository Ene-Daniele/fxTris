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
        this.active = true;
        this.minoA = new Rectangle(TILE, TILE, color);
        this.minoB = new Rectangle(TILE, TILE, color);
        this.minoC = new Rectangle(TILE, TILE, color);
        this.minoCentral = new Rectangle(TILE, TILE, color);
        minoCentral.setY(100);
        minoCentral.setX(100);

        Main.root.getChildren().add(minoCentral);
        Main.root.getChildren().add(minoC);
        Main.root.getChildren().add(minoB);
        Main.root.getChildren().add(minoA);
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
