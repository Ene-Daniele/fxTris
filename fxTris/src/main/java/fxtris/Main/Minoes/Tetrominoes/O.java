package fxtris.Main.Minoes.Tetrominoes;

import fxtris.Main.Minoes.Tetromino;
import javafx.scene.paint.Color;

import static fxtris.Main.Others.GlobalValues.TILE;

public class O extends Tetromino {

    public O() {
        super(Color.YELLOW);
    }

    @Override
    public boolean isO() {
        return true;
    }

    @Override
    public void update() {

        minoA.setY(minoCentral.getY() + TILE);
        minoA.setX(minoCentral.getX() + TILE);

        minoB.setY(minoCentral.getY() + TILE);
        minoB.setX(minoCentral.getX());

        minoC.setY(minoCentral.getY());
        minoC.setX(minoCentral.getX() + TILE);
    }
}
