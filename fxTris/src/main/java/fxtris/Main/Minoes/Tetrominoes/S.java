package fxtris.Main.Minoes.Tetrominoes;

import fxtris.Main.Minoes.Tetromino;
import javafx.scene.paint.Color;

import static fxtris.Main.Others.GlobalValues.TILE;

public class S extends Tetromino {

    public S() {
        super(Color.LIGHTGREEN);
    }

    @Override
    public void update() {

        switch (this.rotationIndex){
            case 1:
                minoA.setY(minoCentral.getY());
                minoA.setX(minoCentral.getX() - TILE);

                minoB.setY(minoCentral.getY() - TILE);
                minoB.setX(minoCentral.getX());

                minoC.setY(minoCentral.getY() - TILE);
                minoC.setX(minoCentral.getX() + TILE);
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
