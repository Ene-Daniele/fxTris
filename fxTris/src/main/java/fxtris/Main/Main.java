package fxtris.Main;

import fxtris.Main.Controls.Controller;
import fxtris.Main.Controls.Keyboard;
import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Minoes.Tetrominoes.S;
import fxtris.Main.Others.Matrix;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.*;

public class Main extends Application {

    public static Group root = new Group();
    static Group settings = new Group();

    public static Scene scene = new Scene(root, 800, 800, Color.BLACK);

    public static Tetromino currentTetromino = new Tetromino();

    private static int tempGRV = 0;
    private static int tempSDF = 0;
    private static int tempARR = 0;
    private static int tempDAS = 0;
    private static boolean singleTap = true;

    public static void resetXMovement(){
        tempARR = 0;
        tempDAS = 0;
        singleTap = true;
    }

    @Override
    public void start(Stage stage) throws IOException {

        Controller.loadController();
        Matrix.loadMatrix();

        currentTetromino = new S();

        AnimationTimer frames = new AnimationTimer() {

            @Override
            public void handle(long l) {

                if (currentTetromino.isActive()) {

                    gravity();
                    collisions();
                    borderCheck(); //? This goes into movement()

                    currentTetromino.getMinoCentral().setFill(Color.RED);
                    currentTetromino.update();
                } else {
                    currentTetromino.getMinoCentral().setFill(Color.GRAY);
                    //TODO Cycle queue and get new tetromino
                    placeTetromino();
                }
            }
            private static void placeTetromino(){

                //Removing old tetromino
                root.getChildren().remove(currentTetromino.getMinoCentral());
                root.getChildren().remove(currentTetromino.getMinoA());
                root.getChildren().remove(currentTetromino.getMinoB());
                root.getChildren().remove(currentTetromino.getMinoC());
                //Replacing it with an inactive one
                Matrix.addMinoes(currentTetromino.getMinoCentral());
                Matrix.addMinoes(currentTetromino.getMinoC());
                Matrix.addMinoes(currentTetromino.getMinoB());
                Matrix.addMinoes(currentTetromino.getMinoA());

                currentTetromino = new S();
            }
            //TODO Horizontal collisions
            private static void collisions(){

                if (currentTetromino.isColliding()){
                    currentTetromino.setOneSlide(currentTetromino.getOneSlide() -1);
                    currentTetromino.setFourSlide(currentTetromino.getFourSlide() -1);
                    tempGRV = 0;
                } else if (currentTetromino.isCollided()){
                    currentTetromino.setOneSlide(60);
                }

                if (currentTetromino.getOneSlide() < 0 || currentTetromino.getFourSlide() < 0) {
                    currentTetromino.setActive(false);
                }
            }
            private static void gravity(){

                tempGRV++;
                if (Keyboard.isSoftDrop()) {
                    if (tempGRV > GRAVITY / getSdf()) {
                        tempGRV = 0;
                        currentTetromino.getMinoCentral().setY(currentTetromino.getMinoCentral().getY() + TILE);
                    }
                } else {
                    if (tempGRV > GRAVITY) {
                        tempGRV = 0;
                        currentTetromino.getMinoCentral().setY(currentTetromino.getMinoCentral().getY() + TILE);
                    }
                }
            }
            private static void borderCheck(){
                if ((
                    currentTetromino.getMinoCentral().getX() - TILE != LEFTWALL // * TILE
                    && currentTetromino.getMinoA().getX() - TILE != LEFTWALL
                    && currentTetromino.getMinoB().getX() - TILE != LEFTWALL
                    && currentTetromino.getMinoC().getX() - TILE != LEFTWALL
                ) && Keyboard.isLeft()){
                    movement(-1); //* Negative
                }
                if ((
                    currentTetromino.getMinoCentral().getX() + TILE != RIGHTWALL * TILE
                    && currentTetromino.getMinoA().getX() + TILE != RIGHTWALL * TILE
                    && currentTetromino.getMinoB().getX() + TILE != RIGHTWALL * TILE
                    && currentTetromino.getMinoC().getX() + TILE != RIGHTWALL * TILE
                ) && Keyboard.isRight()){
                    movement(1); //* Positive
                }
            }
            private static void movement(int sign){

                if (tempDAS < getDas()){
                    tempDAS++;
                    if (singleTap){
                        singleTap = false;
                        currentTetromino.getMinoCentral().setX(currentTetromino.getMinoCentral().getX() + (TILE * sign));
                    }

                } else {
                    tempARR++;
                    if (tempARR > getArr()){
                        tempARR = 0;
                        currentTetromino.getMinoCentral().setX(currentTetromino.getMinoCentral().getX() + (TILE * sign));
                    }
                }
            }
        };

        /*Rectangle dsa = new Rectangle(50,50,Color.WHITE);
        ArrayList<Rectangle> qwe = new ArrayList<>();
        qwe.add(dsa);
        root.getChildren().add(qwe.get(0));
        Button asd = new Button();
        asd.setOnAction(actionEvent -> {
            qwe.get(0).setY(100);
        });
        root.getChildren().add(asd);*/

        stage.setTitle("fxTris");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        frames.start();
    }

    public static void main(String[] args) {
        launch();
    }
}