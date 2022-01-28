package fxtris.Main.Others;

import fxtris.Main.Main;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.TILE;

public class Matrix { //Take inactive pieces from this matrixGrid as rectangles one by one

    private static ArrayList <ArrayList<Rectangle>> matrixGrid = new ArrayList<>();

    public static ArrayList<ArrayList<Rectangle>> getMatrixGrid() {
        return matrixGrid;
    }

    public static void loadMatrix(){
        for (int i = 0; i < 20; i++){
            matrixGrid.add(new ArrayList<>());
        }
    }

    public static void addMinoes(Rectangle mino){

        for (int i = 0; i < 20; i++){
            if (mino.getY() == (i + 5) * TILE){
                matrixGrid.get(i).add(mino);
                Main.root.getChildren().add(matrixGrid.get(i).get(matrixGrid.get(i).indexOf(mino)));
                if (matrixGrid.get(i).size() == 10){
                    clearRow(i);
                }
            }
        }
    }

    private static void clearRow(int row){

        for (int j = 0; j < 10; j++){ //Removing from root
            Main.root.getChildren().remove(matrixGrid.get(row).get(j));
        }
        matrixGrid.remove(row); //Deleting row
        matrixGrid.add(new ArrayList<>()); //Adding new row

        for (int i = 0; i < matrixGrid.size(); i++){
            for (int j = 0; j < matrixGrid.get(i).size(); j++){

                matrixGrid.get(i).get(j).setY((i + 5) * TILE);
            }
        }
    }
}
