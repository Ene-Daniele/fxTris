package fxtris.Main.Minoes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Tetromino {

    Color color;

    private static ArrayList <Rectangle> addedMinoes = new ArrayList <Rectangle> ();
    public static ArrayList <Rectangle> getAddedMinoes() {
        return addedMinoes;
    }

    protected void ccw(){}
    protected void cw(){}
    protected void collision(){}
}
