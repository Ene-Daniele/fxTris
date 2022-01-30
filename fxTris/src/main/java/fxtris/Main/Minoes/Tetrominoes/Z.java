package fxtris.Main.Minoes.Tetrominoes;

import fxtris.Main.Minoes.Tetromino;
import javafx.scene.paint.Color;

import static fxtris.Main.Others.GlobalValues.TILE;

public class Z extends Tetromino {

    public Z() {
        super(Color.RED);
    }

    @Override
    public void update() {

        switch (this.rotationIndex){
            case 1:
                minoA.setY(minoCentral.getY() + TILE);
                minoA.setX(minoCentral.getX());

                minoB.setY(minoCentral.getY());
                minoB.setX(minoCentral.getX() - TILE);

                minoC.setY(minoCentral.getY() + TILE);
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
