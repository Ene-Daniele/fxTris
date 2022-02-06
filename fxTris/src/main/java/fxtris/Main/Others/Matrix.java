package fxtris.Main.Others;

import fxtris.Main.GameEvents.Events;
import fxtris.Main.Main;
import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Queue.Bag;
import fxtris.Main.Queue.Queue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.TILE;
import static fxtris.Main.Others.GlobalValues.setArr;

public class Matrix {

    private static ArrayList <ArrayList<Rectangle>> matrixGrid = new ArrayList<>();

    public static ArrayList<ArrayList<Rectangle>> getMatrixGrid() {
        return matrixGrid;
    }

    public static void loadMatrix(){
        for (int i = 0; i < 25; i++){
            matrixGrid.add(new ArrayList<>());
        }
    }

    /**
     * Removes a tetromino from the root
     * @param tetromino Tetrmonio being removed
     */
    public static void removeFromRoot(Tetromino tetromino){
        Main.root.getChildren().remove(tetromino.getMinoCentral());
        Main.root.getChildren().remove(tetromino.getMinoA());
        Main.root.getChildren().remove(tetromino.getMinoB());
        Main.root.getChildren().remove(tetromino.getMinoC());
    }

    /**
     * Adds a tetromino to the root
     * @param tetromino Tetromino being added
     */
    public static void addToRoot(Tetromino tetromino){
        Main.root.getChildren().add(tetromino.getMinoCentral());
        Main.root.getChildren().add(tetromino.getMinoA());
        Main.root.getChildren().add(tetromino.getMinoB());
        Main.root.getChildren().add(tetromino.getMinoC());
    }

    /**
     * Adds a tetromino to the matrix
     * @param tetromino Tetromino being added to the matrix
     */
    public static void addTetromino(Tetromino tetromino){

        addMino(tetromino.getMinoCentral());
        addMino(tetromino.getMinoA());
        addMino(tetromino.getMinoB());
        addMino(tetromino.getMinoC());

        int temp = 0;

        for (int i = 0; i < matrixGrid.size(); i++){
            if (matrixGrid.get(i).size() == 10){
                clearRow(i);
                temp++;
            }
        }

        switch (temp){
            case 1:
                Main.clears.setText("Single");
                Main.clears.setOpacity(100);
                break;
            case 2:
                Main.clears.setText("Double");
                Main.clears.setOpacity(100);
                break;
            case 3:
                Main.clears.setText("Triple");
                Main.clears.setOpacity(100);
                break;
            case 4:
                Main.clears.setText("Tetris");
                Main.clears.setOpacity(100);
                break;
            default:
                //TODO Make a combo system that goes to zero in this default
        }
    }

    /**
     * Adds a mino to the matrix, used from AddTetromino()
     * @param mino Mino being added
     */
    private static void addMino(Rectangle mino){
        for (int i = 0; i < matrixGrid.size(); i++){
            if (mino.getY() == (i + 1) * TILE){
                matrixGrid.get(i).add(mino);
                Main.root.getChildren().add(mino);
            }
        }
    }

    /**
     * Clears a row if its size is 10
     * @param row row being cleared
     */
    private static void clearRow(int row){

        for (int j = 0; j < 10; j++){ //Removing from root
            Main.root.getChildren().remove(matrixGrid.get(row).get(j));
        }
        matrixGrid.remove(row);
        matrixGrid.add(0, new ArrayList<>());

        for (int i = row; i > -1; i--){
            for (int j = 0; j < 10; j++){

                try {
                    matrixGrid.get(i).get(j).setY((i + 1) * TILE);
                } catch (IndexOutOfBoundsException e){}
            }
        }
    }

    /**
     * Resets matrix, queue, held tetromino, and bag
     */
    public static void reset(){

        for (Tetromino i : Queue.getList()){
            removeFromRoot(i);
        }
        for (ArrayList<Rectangle> i : matrixGrid) {
            for (Rectangle deadMino : i) {
                Main.root.getChildren().remove(deadMino);
            }
            i.clear();
        }
        Queue.getList().clear();
        Queue.loadFirstQueue();
        removeFromRoot(Events.save);
        Events.hold = new Tetromino();
        Events.save = new Tetromino();
        Events.firstSwap = true;
    }
}
