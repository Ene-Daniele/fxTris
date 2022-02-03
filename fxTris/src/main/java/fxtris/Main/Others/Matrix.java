package fxtris.Main.Others;

import fxtris.Main.GameEvents.Events;
import fxtris.Main.Main;
import fxtris.Main.Minoes.Tetromino;
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

    public static void removeFromRoot(Tetromino tetromino){
        Main.root.getChildren().remove(tetromino.getMinoCentral());
        Main.root.getChildren().remove(tetromino.getMinoA());
        Main.root.getChildren().remove(tetromino.getMinoB());
        Main.root.getChildren().remove(tetromino.getMinoC());
    }
    public static void addToRoot(Tetromino tetromino){
        Main.root.getChildren().add(tetromino.getMinoCentral());
        Main.root.getChildren().add(tetromino.getMinoA());
        Main.root.getChildren().add(tetromino.getMinoB());
        Main.root.getChildren().add(tetromino.getMinoC());
    }
    public static void addTetromino(Tetromino tetromino){

        addMino(tetromino.getMinoCentral());
        addMino(tetromino.getMinoA());
        addMino(tetromino.getMinoB());
        addMino(tetromino.getMinoC());

        for (int i = 0; i < matrixGrid.size(); i++){
            if (matrixGrid.get(i).size() == 10){
                clearRow(i);
            }
        }
    }
    private static void addMino(Rectangle mino){
        for (int i = 0; i < matrixGrid.size(); i++){
            if (mino.getY() == (i + 1) * TILE){
                matrixGrid.get(i).add(mino);
                Main.root.getChildren().add(mino);
            }
        }
    }
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
