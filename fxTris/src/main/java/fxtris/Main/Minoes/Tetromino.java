package fxtris.Main.Minoes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Tetromino {

    protected Color color;
    protected int rotationIndex;

    private static ArrayList <Rectangle> addedMinoes = new ArrayList <Rectangle> ();
    public static final ArrayList <Rectangle> getAddedMinoes() {
        return addedMinoes;
    }

    public void rotationCCW(){}
    public void rotationCW(){}
    public void rotation180(){}

    public void collision(){}
    public void update(){}
}
