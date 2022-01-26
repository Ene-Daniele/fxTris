package fxtris.Main.Minoes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Tetromino {

    protected Color color;
    protected int rotationIndex;

    protected Rectangle minoA;
    protected Rectangle minoB;
    protected Rectangle minoC;
    protected Rectangle minoCentral;

    protected Tetromino(Color color){
        this.minoA = new Rectangle();
        this.minoB = new Rectangle();
        this.minoC = new Rectangle();
        this.minoCentral = new Rectangle();

        this.minoA.setFill(color);
        this.minoB.setFill(color);
        this.minoC.setFill(color);
        this.minoCentral.setFill(color);
    }

    private static ArrayList <Rectangle> addedMinoes = new ArrayList <Rectangle> ();
    public static final ArrayList <Rectangle> getAddedMinoes() {
        return addedMinoes;
    }

    public void rotationCCW(){}
    public void rotationCW(){}
    public void rotation180(){}

    public void collision(){}
    public void update(){}

    public final boolean canRotate(Tetromino tetromino){
        boolean temp = false;
        return temp;
    }
}
