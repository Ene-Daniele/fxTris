package fxtris.Main.GameEvents;

import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Others.Matrix;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.*;
import static fxtris.Main.Others.GlobalValues.TILE;

public class SuperRotationSystem {

    /**
     *
     * @param mino1 Current dead mino being checked
     * @param mino2 Mino from the current tetromino
     * @return if they intersect or not
     */
    public static boolean intersect(Rectangle mino1, Rectangle mino2){
        return
                mino1.getY() == mino2.getY() && mino1.getX() == mino2.getX()
                        || mino2.getX() > RIGHTWALL * TILE
                        || mino2.getX() < LEFTWALL * TILE
                        || mino2.getY() > GROUND * TILE;
    }

    /**
     *
     * @param tetromino Tetromino being checked
     * @return if the tetromino can rotate
     */
    private boolean canRotate(Tetromino tetromino){
        boolean temp = true;
        for (ArrayList<Rectangle> i : Matrix.getMatrixGrid()) {
            for (Rectangle deadMino : i) {
                if (
                        intersect(deadMino, tetromino.getMinoCentral())
                                || intersect(deadMino, tetromino.getMinoA())
                                || intersect(deadMino, tetromino.getMinoB())
                                || intersect(deadMino, tetromino.getMinoC())
                )
                {
                    temp = false;
                }
            }
        }
        return temp;
    }

    /**
     *
     * @param tetromino Tetromino to offset
     * @param x offset X
     * @param y offset Y
     */
    private void offset(Tetromino tetromino, int x, int y){
        tetromino.getMinoCentral().setX(tetromino.getMinoCentral().getX() + (x * TILE));
        tetromino.getMinoCentral().setY(tetromino.getMinoCentral().getY() + (y * TILE));
        tetromino.update();
    }

    /**
     *
     * @param tetromino Tetromino being rotated
     * @param newId Rotation id that the tetromino rotates to
     */
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
                switch (tetromino.getRotationIndex()){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            } else {
                //!COUNTERCLOCKWISE
                switch (tetromino.getRotationIndex()){
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

        } else { //* <I> Rotations

            if (oldIndex < tetromino.getRotationIndex()){
                //!CLOCKWISE
                switch (tetromino.getRotationIndex()){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            } else {
                //!COUNTERCLOCKWISE
                switch (tetromino.getRotationIndex()){
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
}
