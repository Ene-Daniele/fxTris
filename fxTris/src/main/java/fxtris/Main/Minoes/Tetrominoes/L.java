package fxtris.Main.Minoes.Tetrominoes;

import fxtris.Main.Minoes.Tetromino;
import javafx.scene.paint.Color;

import static fxtris.Main.Others.GlobalValues.TILE;

public class L extends Tetromino {

    public L() {
        super(Color.ORANGE);
    }

    @Override
    public void update() {

        switch (this.rotationIndex){
            case 1:
                minoA.setY(minoCentral.getY());
                minoA.setX(minoCentral.getX() + TILE);

                minoB.setY(minoCentral.getY() - TILE);
                minoB.setX(minoCentral.getX() + TILE);

                minoC.setY(minoCentral.getY());
                minoC.setX(minoCentral.getX() - TILE);
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
