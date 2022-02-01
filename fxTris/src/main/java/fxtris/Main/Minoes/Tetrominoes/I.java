package fxtris.Main.Minoes.Tetrominoes;

import fxtris.Main.Minoes.Tetromino;
import javafx.scene.paint.Color;

import static fxtris.Main.Others.GlobalValues.TILE;

public class I extends Tetromino {

    public I() {
        super(Color.CYAN);
    }

    int centerX; //? The I tetromino doesnt rotate on a central mino like the others, it has a center of rotation.
    int centerY;

    @Override
    public boolean isI() { //? Needed for SRS
        return true;
    }

    @Override
    public void update() {

        switch (this.rotationIndex){
            case 1:

                switch (lastIndex){
                    case 1:
                        centerY = (int) (minoCentral.getY() + minoCentral.getHeight());
                        centerX = (int) (minoCentral.getX() + minoCentral.getWidth());
                        break;
                    case 2:
                        centerY = (int) (minoCentral.getY() + minoCentral.getHeight());
                        centerX = (int) (minoCentral.getX());
                        lastIndex = rotationIndex;
                        break;
                    case 3:
                        centerY = (int) (minoCentral.getY());
                        centerX = (int) (minoCentral.getX());
                        lastIndex = rotationIndex;
                        break;
                    case 4:
                        centerY = (int) (minoCentral.getY());
                        centerX = (int) (minoCentral.getX() + minoCentral.getWidth());
                        lastIndex = rotationIndex;
                        break;
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

                switch (lastIndex){
                    case 1:
                        centerY = (int) (minoCentral.getY() + minoCentral.getHeight());
                        centerX = (int) (minoCentral.getX() + minoCentral.getWidth());
                        lastIndex = rotationIndex;
                        break;
                    case 2:
                        centerY = (int) (minoCentral.getY() + minoCentral.getHeight());
                        centerX = (int) (minoCentral.getX());
                        break;
                    case 3:
                        centerY = (int) (minoCentral.getY());
                        centerX = (int) (minoCentral.getX());
                        lastIndex = rotationIndex;
                        break;
                    case 4:
                        centerY = (int) (minoCentral.getY());
                        centerX = (int) (minoCentral.getX() + minoCentral.getWidth());
                        lastIndex = rotationIndex;
                        break;
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

                switch (lastIndex){
                    case 1:
                        centerY = (int) (minoCentral.getY() + minoCentral.getHeight());
                        centerX = (int) (minoCentral.getX() + minoCentral.getWidth());
                        lastIndex = rotationIndex;
                        break;
                    case 2:
                        centerY = (int) (minoCentral.getY() + minoCentral.getHeight());
                        centerX = (int) (minoCentral.getX());
                        lastIndex = rotationIndex;
                        break;
                    case 3:
                        centerY = (int) (minoCentral.getY());
                        centerX = (int) (minoCentral.getX());
                        break;
                    case 4:
                        centerY = (int) (minoCentral.getY());
                        centerX = (int) (minoCentral.getX() + minoCentral.getWidth());
                        lastIndex = rotationIndex;
                        break;
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

                switch (lastIndex){
                    case 1:
                        centerY = (int) (minoCentral.getY() + minoCentral.getHeight());
                        centerX = (int) (minoCentral.getX() + minoCentral.getWidth());
                        lastIndex = rotationIndex;
                        break;
                    case 2:
                        centerY = (int) (minoCentral.getY() + minoCentral.getHeight());
                        centerX = (int) (minoCentral.getX());
                        lastIndex = rotationIndex;
                        break;
                    case 3:
                        centerY = (int) (minoCentral.getY());
                        centerX = (int) (minoCentral.getX());
                        lastIndex = rotationIndex;
                        break;
                    case 4:
                        centerY = (int) (minoCentral.getY());
                        centerX = (int) (minoCentral.getX() + minoCentral.getWidth());
                        break;
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
