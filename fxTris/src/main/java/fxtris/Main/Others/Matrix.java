package fxtris.Main.Others;

import fxtris.Main.GameEvents.Events;
import fxtris.Main.GameEvents.SuperRotationSystem;
import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Queue.Queue;
import fxtris.Main.Stages.GameStage;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.TILE;

/**
 * Class that handles the matrix
 */
public class Matrix {

    private static final ArrayList <ArrayList<Rectangle>> matrixGrid = new ArrayList<>();

    public static ArrayList<ArrayList<Rectangle>> getMatrixGrid() {
        return matrixGrid;
    }

    public static int score = 0;

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
        GameStage.root.getChildren().remove(tetromino.getMinoCentral());
        GameStage.root.getChildren().remove(tetromino.getMinoA());
        GameStage.root.getChildren().remove(tetromino.getMinoB());
        GameStage.root.getChildren().remove(tetromino.getMinoC());
    }

    /**
     * Adds a tetromino to the root
     * @param tetromino Tetromino being added
     */
    public static void addToRoot(Tetromino tetromino){
        GameStage.root.getChildren().add(tetromino.getMinoCentral());
        GameStage.root.getChildren().add(tetromino.getMinoA());
        GameStage.root.getChildren().add(tetromino.getMinoB());
        GameStage.root.getChildren().add(tetromino.getMinoC());
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

        switch (temp) {
            case 1 -> {
                GameStage.clears.setText("Single");
                GameStage.clears.setOpacity(100);
                if (!SuperRotationSystem.tspinning) {
                    score += 40;
                } else {
                    score += 800;
                    SuperRotationSystem.tspinning = false;
                }
                GameStage.scoretxt.setText(String.valueOf(score));
            }
            case 2 -> {
                GameStage.clears.setText("Double");
                GameStage.clears.setOpacity(100);
                if (!SuperRotationSystem.tspinning) {
                    score += 100;
                } else {
                    score += 1200;
                    SuperRotationSystem.tspinning = false;
                }
                GameStage.scoretxt.setText(String.valueOf(score));
            }
            case 3 -> {
                GameStage.clears.setText("Triple");
                GameStage.clears.setOpacity(100);
                if (!SuperRotationSystem.tspinning) {
                    score += 300;
                } else {
                    score += 1600;
                    SuperRotationSystem.tspinning = false;
                }
                GameStage.scoretxt.setText(String.valueOf(score));
            }
            case 4 -> {
                GameStage.clears.setText("Tetris");
                GameStage.clears.setOpacity(100);
                score += 1200;
                GameStage.scoretxt.setText(String.valueOf(score));
            }
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
                GameStage.root.getChildren().add(mino);
            }
        }
    }

    /**
     * Clears a row if its size is 10
     * @param row row being cleared
     */
    private static void clearRow(int row){

        for (int j = 0; j < 10; j++){ //Removing from root
            GameStage.root.getChildren().remove(matrixGrid.get(row).get(j));
        }
        matrixGrid.remove(row);
        matrixGrid.add(0, new ArrayList<>());

        for (int i = row; i > -1; i--){
            for (int j = 0; j < matrixGrid.get(i).size(); j++){

                matrixGrid.get(i).get(j).setY((i + 1) * TILE);
            }
        }
    }

    /**
     * Resets matrix, queue, held tetromino, and bag
     */
    public static void reset(){

        score = 0;
        GameStage.scoretxt.setText(String.valueOf(score));

        for (Tetromino i : Queue.getList()){
            removeFromRoot(i);
        }
        for (ArrayList<Rectangle> i : matrixGrid) {
            for (Rectangle deadMino : i) {
                GameStage.root.getChildren().remove(deadMino);
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
