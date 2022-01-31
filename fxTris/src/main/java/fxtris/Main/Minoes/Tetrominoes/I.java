package fxtris.Main.Minoes.Tetrominoes;

import fxtris.Main.Minoes.Tetromino;
import javafx.scene.paint.Color;

import static fxtris.Main.Others.GlobalValues.TILE;

public class I extends Tetromino {

    public I() {
        super(Color.CYAN);
    }

    @Override
    public boolean isI() { //? Needed for SRS
        return true;
    }

    @Override
    public void update() {

        switch (this.rotationIndex){
            case 1:
                minoA.setY(minoCentral.getY());
                minoA.setX(minoCentral.getX() - TILE);

                minoB.setY(minoCentral.getY());
                minoB.setX(minoCentral.getX() + TILE);

                minoC.setY(minoCentral.getY());
                minoC.setX(minoCentral.getX() + TILE*2);
                break;
            case 2:
                minoA.setY(minoCentral.getY() + TILE * 3);
                minoA.setX(minoCentral.getX());

                minoB.setY(minoCentral.getY() + TILE * 2);
                minoB.setX(minoCentral.getX());

                minoC.setY(minoCentral.getY() + TILE);
                minoC.setX(minoCentral.getX());
                break;
            case 3:
                minoA.setY(minoCentral.getY());
                minoA.setX(minoCentral.getX() + TILE);

                minoB.setY(minoCentral.getY());
                minoB.setX(minoCentral.getX() - TILE);

                minoC.setY(minoCentral.getY());
                minoC.setX(minoCentral.getX() - TILE*2);
                break;
            case 4:
                minoA.setY(minoCentral.getY() + TILE * 3);
                minoA.setX(minoCentral.getX());

                minoB.setY(minoCentral.getY() + TILE * 2);
                minoB.setX(minoCentral.getX());

                minoC.setY(minoCentral.getY() + TILE);
                minoC.setX(minoCentral.getX());
                break;
        }
    }
}
