package fxtris.Main.Minoes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.tile;

public class Tetromino {

    protected Color color;
    protected int rotationIndex;

    protected Rectangle minoA;
    protected Rectangle minoB;
    protected Rectangle minoC;
    protected Rectangle minoCentral;

    public Tetromino(){}

    protected Tetromino(Color color){
        this.rotationIndex = 1;
        this.minoA = new Rectangle(tile, tile, color);
        this.minoB = new Rectangle(tile, tile, color);
        this.minoC = new Rectangle(tile, tile, color);
        this.minoCentral = new Rectangle(tile, tile, color);
    }

    private static ArrayList <Rectangle> addedMinoes = new ArrayList <Rectangle> ();
    public static final ArrayList <Rectangle> getAddedMinoes() {
        return addedMinoes;
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

    public void collision(){}
    public void update(){}

    public final boolean canRotate(Tetromino tetromino){
        boolean temp = false;
        return temp;
    }
}
