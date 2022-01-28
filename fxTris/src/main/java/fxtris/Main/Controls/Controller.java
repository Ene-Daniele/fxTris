package fxtris.Main.Controls;

import fxtris.Main.Main;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.security.Key;

public class Controller {

    public static void loadController(){

        Main.scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT -> {
                        Keyboard.setRight(true);

                    }
                    case LEFT -> {
                        Keyboard.setLeft(true);

                    }
                    case DOWN -> {
                        Keyboard.setSoftDrop(true);

                    }
                    case A -> {
                        Keyboard.setRotateCCW(true);

                    }
                    case D -> {
                        Keyboard.setRotateCW(true);

                    }
                    case S -> {
                        Keyboard.setHardDrop(true);

                    }
                    case W -> {
                        Keyboard.setRotate180(true);

                    }
                    case F -> {
                        Keyboard.setSwap(true);

                    }
                }
            }
        });
        Main.scene.setOnKeyReleased(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT -> {
                        Keyboard.setRight(false);

                    }
                    case LEFT -> {
                        Keyboard.setLeft(false);

                    }
                    case DOWN -> {
                        Keyboard.setSoftDrop(false);

                    }
                    case A -> {
                        Keyboard.setRotateCCW(false);

                    }
                    case D -> {
                        Keyboard.setRotateCW(false);

                    }
                    case S -> {
                        Keyboard.setHardDrop(false);

                    }
                    case W -> {
                        Keyboard.setRotate180(false);

                    }
                    case F -> {
                        Keyboard.setSwap(false);

                    }
                }
            }
        });
    }
}
