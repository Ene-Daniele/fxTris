package fxtris.Main.Others;

import fxtris.Main.Minoes.Tetromino;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.*;
import static fxtris.Main.Others.GlobalValues.TILE;

public class SuperRotationSystem {

    private boolean overlap(Rectangle mino1, Rectangle mino2){
        return
                mino1.getY() == mino2.getY() && mino1.getX() == mino2.getX()
                        || mino2.getX() > RIGHTWALL * TILE
                        || mino2.getX() < LEFTWALL * TILE
                        || mino2.getY() > GROUND * TILE;
    }
    private boolean canRotate(Tetromino tetromino){
        boolean temp = true;
        for (ArrayList<Rectangle> i : Matrix.getMatrixGrid()) {
            for (Rectangle deadMino : i) {
                if (
                        overlap(deadMino, tetromino.getMinoCentral())
                                || overlap(deadMino, tetromino.getMinoA())
                                || overlap(deadMino, tetromino.getMinoB())
                                || overlap(deadMino, tetromino.getMinoC())
                )
                {
                    temp = false;
                }
            }
        }
        return temp;
    }
    private void offset(Tetromino tetromino, int x, int y){
        tetromino.getMinoCentral().setX(tetromino.getMinoCentral().getX() + (x * TILE));
        tetromino.getMinoCentral().setY(tetromino.getMinoCentral().getY() + (x * TILE));
        tetromino.update();
    }

    public static void rotation(Tetromino tetromino, int newId){
        //? https://tetris.wiki/Super_Rotation_System
        //? https://four.lol/srs/j-kicks Make your own custom offsets with your old plan

        int oldIndex = tetromino.getRotationIndex(); //Needed for this function
        tetromino.setLastIndex(tetromino.getRotationIndex()); //Needed for I piece basic rotation
        tetromino.setRotationIndex(newId); //Update the rotation index
        tetromino.update(); //Update the tetromino to the new rotation index

        if (!tetromino.isI()){ //* <J, L, O, S, Z, T> Rotations

            if (oldIndex < tetromino.getRotationIndex()){
                //!CLOCKWISE

            } else {
                //!COUNTERLOCKWISE
            }

        } else { //* <I> Rotations

            if (oldIndex < tetromino.getRotationIndex()){
                //!CLOCKWISE
            } else {
                //!COUNTERLOCKWISE
            }
        }
    }
}
