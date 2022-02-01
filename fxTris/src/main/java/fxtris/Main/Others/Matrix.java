package fxtris.Main.Others;

import fxtris.Main.Main;
import fxtris.Main.Minoes.Tetromino;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.TILE;

public class Matrix {

    private static ArrayList <ArrayList<Rectangle>> matrixGrid = new ArrayList<>();

    public static ArrayList<ArrayList<Rectangle>> getMatrixGrid() {
        return matrixGrid;
    }

    public static void loadMatrix(){
        for (int i = 0; i < 21; i++){
            matrixGrid.add(new ArrayList<>());
        }
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
            if (mino.getY() == (i + 5) * TILE){
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
                    matrixGrid.get(i).get(j).setY((i + 5) * TILE);
                } catch (IndexOutOfBoundsException e){}
            }
        }
    }
}
