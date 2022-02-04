package fxtris.Main.Minoes.Tetrominoes;

import fxtris.Main.Minoes.Tetromino;
import javafx.scene.paint.Color;

import static fxtris.Main.Others.GlobalValues.TILE;

public class I extends Tetromino {

    public I() {
        super(Color.CYAN);
        this.tetrominoID = 1;
    }

    int centerX; //? The I tetromino doesnt rotate on a central mino like the others, it has a center of rotation.
    int centerY;

    /**
     * The I tetromino has special behaviours
     * @return If the current tetromino is an I
     */
    @Override
    public boolean isI() { //? Needed for SRS
        return true;
    }

    @Override
    public void update() {

        switch (this.rotationIndex){
            case 1:

                if (lastIndex == rotationIndex){
                    centerY = (int) (minoCentral.getY() + minoCentral.getHeight());
                    centerX = (int) (minoCentral.getX() + minoCentral.getWidth());
                } else {
                    lastIndex = rotationIndex;
                }

                minoA.setY(centerY - TILE);
                minoA.setX(centerX - TILE * 2);

                minoCentral.setY(centerY - TILE);
                minoCentral.setX(centerX - TILE);

                minoB.setY(centerY - TILE);
                minoB.setX(centerX);

                minoC.setY(centerY - TILE);
                minoC.setX(centerX + TILE);
                break;
            case 2:

                if (lastIndex == rotationIndex){
                    centerY = (int) (minoCentral.getY() + minoCentral.getHeight());
                    centerX = (int) (minoCentral.getX());
                } else {
                    lastIndex = rotationIndex;
                }

                minoA.setY(centerY - TILE * 2);
                minoA.setX(centerX);

                minoCentral.setY(centerY - TILE);
                minoCentral.setX(centerX);

                minoB.setY(centerY);
                minoB.setX(centerX);

                minoC.setY(centerY + TILE);
                minoC.setX(centerX);
                break;
            case 3:

                if (lastIndex == rotationIndex){
                    centerY = (int) (minoCentral.getY());
                    centerX = (int) (minoCentral.getX());
                } else {
                    lastIndex = rotationIndex;
                }

                minoA.setY(centerY);
                minoA.setX(centerX - TILE * 2);

                minoCentral.setY(centerY);
                minoCentral.setX(centerX);

                minoB.setY(centerY);
                minoB.setX(centerX - TILE);

                minoC.setY(centerY);
                minoC.setX(centerX + TILE);

                break;
            case 4:

                if (lastIndex == rotationIndex){
                    centerY = (int) (minoCentral.getY());
                    centerX = (int) (minoCentral.getX() + minoCentral.getWidth());
                } else {
                    lastIndex = rotationIndex;
                }

                minoA.setY(centerY - TILE * 2);
                minoA.setX(centerX - TILE);

                minoB.setY(centerY - TILE);
                minoB.setX(centerX - TILE);

                minoCentral.setY(centerY);
                minoCentral.setX(centerX - TILE);

                minoC.setY(centerY + TILE);
                minoC.setX(centerX - TILE);
                break;
        }
    }
}
