package fxtris.Main.Controls;

import fxtris.Main.GameEvents.Events;
import fxtris.Main.Main;

import static javafx.scene.input.KeyCode.ESCAPE;

public class Controller {

    public static void loadController(){

        Main.scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case RIGHT -> Keyboard.setRight(true);
                case LEFT -> Keyboard.setLeft(true);
                case DOWN -> Keyboard.setSoftDrop(true);
                case A -> Keyboard.setRotateCCW(true);
                case D -> Keyboard.setRotateCW(true);
                case S -> Keyboard.setHardDrop(true);
                case W -> Keyboard.setRotate180(true);
                case F -> Keyboard.setSwap(true);
                case X -> Keyboard.setRestart(true);
            }
        });
        Main.scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case RIGHT -> {
                    Keyboard.setRight(false);
                    Events.resetXMovement();

                }
                case LEFT -> {
                    Keyboard.setLeft(false);
                    Events.resetXMovement();

                }
                case DOWN -> Keyboard.setSoftDrop(false);
                case A -> Keyboard.setRotateCCW(false);
                case D -> Keyboard.setRotateCW(false);
                case S -> Keyboard.setHardDrop(false);
                case W -> Keyboard.setRotate180(false);
                case F -> Keyboard.setSwap(false);
                case CONTROL -> {
                    if (!Keyboard.isLeft() && !Keyboard.isRight() && !Keyboard.isSoftDrop()) {
                        Main.settings.openSettings();
                    }
                }
                case X -> Keyboard.setRestart(false);
            }
        });
        Main.settings.getSettingsScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(ESCAPE)){
                Main.settings.getSettingsStage().close();
            }
        });
    }
}
