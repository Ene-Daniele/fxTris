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
        return mino1.getY() == mino2.getY() && mino1.getX() == mino2.getX();
    }

    /**
     *
     * @param tetromino Tetromino being checked
     * @return if the tetromino can rotate
     */
    private static boolean canRotate(Tetromino tetromino){
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
        if (
                tetromino.getMinoCentral().getX() >= (double) RIGHTWALL * TILE
                || tetromino.getMinoA().getX() >= (double) RIGHTWALL * TILE
                || tetromino.getMinoB().getX() >= (double) RIGHTWALL * TILE
                || tetromino.getMinoC().getX() >= (double) RIGHTWALL * TILE

                || tetromino.getMinoCentral().getX() <= (double) LEFTWALL * TILE
                || tetromino.getMinoA().getX() <= (double) LEFTWALL * TILE
                || tetromino.getMinoB().getX() <= (double) LEFTWALL * TILE
                || tetromino.getMinoC().getX() <= (double) LEFTWALL * TILE

                || tetromino.getMinoCentral().getY() >= (double) GROUND * TILE
                || tetromino.getMinoA().getY() >= (double) GROUND * TILE
                || tetromino.getMinoB().getY() >= (double) GROUND * TILE
                || tetromino.getMinoC().getY() >= (double) GROUND * TILE
                )
        {
            temp = false;
        }
        return temp;
    }

    /**
     *
     * @param tetromino Tetromino to offset
     * @param x offset X
     * @param y offset Y
     */
    private static void offset(Tetromino tetromino, int x, int y){
        if (!canRotate(tetromino)) {
            tetromino.getMinoCentral().setX(tetromino.getMinoCentral().getX() + (x * TILE));
            tetromino.getMinoCentral().setY(tetromino.getMinoCentral().getY() + (y * TILE));
            tetromino.update();
        }
    }

    /**
     *
     * @param tetromino Tetromino being rotated
     * @param newId Rotation id that the tetromino rotates to
     */
    public static void rotation(Tetromino tetromino, int newId){
        //? https://tetris.wiki/Super_Rotation_System
        //? https://four.lol/srs/j-kicks
        //TODO Optimise this with a matrix and change x and y values in the loops accordingly

        final int oldIndex = tetromino.getRotationIndex(); //Needed for this function
        tetromino.setLastIndex(tetromino.getRotationIndex()); //Needed for I piece basic rotation
        tetromino.setRotationIndex(newId); //Update the rotation index
        tetromino.update(); //Update the tetromino to the new rotation index

        if (!tetromino.isI()){ //* <J, L, O, S, Z, T> Rotations

            if (oldIndex < tetromino.getRotationIndex() || (oldIndex == 4 && tetromino.getRotationIndex() == 1)){
                //!CLOCKWISE
                switch (tetromino.getRotationIndex()){
                    case 1: //* West to North
                        offset(tetromino, -1,  0);
                        offset(tetromino, 0,  1);
                        offset(tetromino, 1, -3);
                        offset(tetromino, -1,  0);
                        if (!canRotate(tetromino)){
                            offset(tetromino, 1, 2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 2: //* North to East
                        offset(tetromino, -1,  0);
                        offset(tetromino, 0,  -1);
                        offset(tetromino, 1, 3);
                        offset(tetromino, -1,  0);
                        if (!canRotate(tetromino)){
                            offset(tetromino, 1, -2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 3: //* East to South
                        offset(tetromino, 1,  0);
                        offset(tetromino, 0,  1);
                        offset(tetromino, -1, -3);
                        offset(tetromino, 1,  0);
                        if (!canRotate(tetromino)){
                            offset(tetromino, -1, 2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 4: //* South to West
                        offset(tetromino, 1,  0);
                        offset(tetromino, 0,  -1);
                        offset(tetromino, -1, 3);
                        offset(tetromino, 1,  0);
                        if (!canRotate(tetromino)){
                            offset(tetromino, -1, -2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                }
            }
            else {
                //!COUNTERCLOCKWISE
                switch (tetromino.getRotationIndex()){
                    case 1: //* East to North
                        offset(tetromino, 1,  0);
                        offset(tetromino, 0,  1);
                        offset(tetromino, -1, -3);
                        offset(tetromino, 1,  0);
                        if (!canRotate(tetromino)){
                            offset(tetromino, -1, 2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 2: //* North to West
                        offset(tetromino, 1,  0);
                        offset(tetromino, 0,  -1);
                        offset(tetromino, -1, 3);
                        offset(tetromino, 1,  0);
                        if (!canRotate(tetromino)){
                            offset(tetromino, -1, -2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 3: //* West to South
                        offset(tetromino, -1,  0);
                        offset(tetromino, 0,  1);
                        offset(tetromino, 1, -3);
                        offset(tetromino, -1,  0);
                        if (!canRotate(tetromino)){
                            offset(tetromino, 1, 2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 4: //* South to East
                        offset(tetromino, -1,  0);
                        offset(tetromino, 0,  -1);
                        offset(tetromino, 1, 3);
                        offset(tetromino, -1,  0);
                        if (!canRotate(tetromino)){
                            offset(tetromino, 1, -2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                }
            }

        } else { //* <I> Rotations

            if (oldIndex < tetromino.getRotationIndex() || (oldIndex == 4 && tetromino.getRotationIndex() == 1)){
                //!CLOCKWISE
                switch (tetromino.getRotationIndex()){
                    case 1:
                        offset(tetromino, 1,  0);
                        offset(tetromino, -3,  0);
                        offset(tetromino, 3, 2);
                        offset(tetromino, -3,  -3);
                        if (!canRotate(tetromino)){
                            offset(tetromino, 2, 1);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 2:
                        offset(tetromino, -2,  0);
                        offset(tetromino, 3,  0);
                        offset(tetromino, -3, 1);
                        offset(tetromino, 3,  -3);
                        if (!canRotate(tetromino)){
                            offset(tetromino, -1, 2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 3:
                        offset(tetromino, -1,  0);
                        offset(tetromino, 3,  0);
                        offset(tetromino, -3, -2);
                        offset(tetromino, 3,  3);
                        if (!canRotate(tetromino)){
                            offset(tetromino, -2, -1);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 4:
                        offset(tetromino, 2,  0);
                        offset(tetromino, -3,  0);
                        offset(tetromino, 3, -1);
                        offset(tetromino, -3,  3);
                        if (!canRotate(tetromino)){
                            offset(tetromino, 1, -2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                }
            }
            else {
                //!COUNTERCLOCKWISE
                switch (tetromino.getRotationIndex()){
                    case 1:
                        offset(tetromino, 2,  0);
                        offset(tetromino, -3,  0);
                        offset(tetromino, 3, -1);
                        offset(tetromino, -3,  3);
                        if (!canRotate(tetromino)){
                            offset(tetromino, 1, -2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 2:
                        offset(tetromino, -1,  0);
                        offset(tetromino, 3,  0);
                        offset(tetromino, -3, -2);
                        offset(tetromino, 3,  3);
                        if (!canRotate(tetromino)){
                            offset(tetromino, -2, -1);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 3:
                        offset(tetromino, -2,  0);
                        offset(tetromino, 3,  0);
                        offset(tetromino, -3, 1);
                        offset(tetromino, 3,  -3);
                        if (!canRotate(tetromino)){
                            offset(tetromino, -1, 2);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                    case 4:
                        offset(tetromino, 1,  0);
                        offset(tetromino, -3,  0);
                        offset(tetromino, 3, 2);
                        offset(tetromino, -3,  -3);
                        if (!canRotate(tetromino)){
                            offset(tetromino, 2, 1);
                            tetromino.setRotationIndex(oldIndex);
                            tetromino.update();
                        }
                        break;
                }
            }
        }
    }
}
