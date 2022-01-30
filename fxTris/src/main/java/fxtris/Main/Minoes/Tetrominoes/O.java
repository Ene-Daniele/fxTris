package fxtris.Main.Minoes.Tetrominoes;

import fxtris.Main.Minoes.Tetromino;
import javafx.scene.paint.Color;

import static fxtris.Main.Others.GlobalValues.TILE;

public class O extends Tetromino {

    public O() {
        super(Color.YELLOW);
    }

    @Override
    public void update() {

        minoA.setY(minoCentral.getY() + TILE);
        minoA.setX(minoCentral.getX() + TILE);

        minoB.setY(minoCentral.getY());
        minoB.setX(minoCentral.getX() + TILE);

        minoC.setY(minoCentral.getY() + TILE);
        minoC.setX(minoCentral.getX());
    }
}
