package fxtris.Main;

import fxtris.Main.Controls.Controller;
import fxtris.Main.Controls.Keyboard;
import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Minoes.Tetrominoes.S;
import fxtris.Main.Others.Matrix;
import fxtris.Main.Queue.Queue;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import static fxtris.Main.Others.GlobalValues.*;

public class Main extends Application {

    public static Group root = new Group();
    static Group settings = new Group();

    public static Scene scene = new Scene(root, 800, 800, Color.BLACK);

    public static Tetromino currentTetromino = new Tetromino();
    public static Tetromino shadow = new Tetromino();
    public static Tetromino hold = new Tetromino();
    public static Tetromino save = new Tetromino();

    public static boolean swapped = false;
    public static boolean firstSwap = true;
    public static boolean swapping = false;

    private static int tempGRV = 0;
    private static int tempARR = 0;
    private static int tempDAS = 0;

    private static boolean singleTap = true;
    public static boolean hardDropped = false;
    public static boolean cwed = false;
    public static boolean ccwed = false;
    public static boolean one80ed = false;

    public static void resetXMovement(){
        tempARR = 0;
        tempDAS = 0;
        singleTap = true;
    }

    static void addLine(Line line){

        line.setStroke(Color.WHITE);
        line.setStrokeWidth(2);
        root.getChildren().add(line);
    }

    @Override
    public void start(Stage stage) {

        Line left = new Line(TILE * LEFTWALL + TILE, TILE * 5, TILE * LEFTWALL + TILE, GROUND * TILE);
        Line right = new Line(TILE * RIGHTWALL, TILE * 5, TILE * RIGHTWALL, TILE * GROUND);
        Line down = new Line(TILE * RIGHTWALL + (TILE * 6), TILE * GROUND, TILE, GROUND * TILE);
        Line top = new Line(TILE * RIGHTWALL + (TILE * 6), TILE * 5, TILE, TILE * 5);

        addLine(left);
        addLine(right);
        addLine(down);
        addLine(top);

        Controller.loadController();
        Matrix.loadMatrix();
        Queue.loadFirstQueue();

        //* Placeholder tetromino needed to start cycling through the queue, it will change as soon as handle() starts.
        currentTetromino = new S();

        AnimationTimer frames = new AnimationTimer() { //AnimationTimer my beloved <3

            @Override
            public void handle(long l) {

                if (currentTetromino.isActive()) {
                    //TODO Add SRS
                    //TODO Add UI stuff (score, last clear, b2b, etc)
                    //TODO Add reset method to reset if needed
                    //TODO Add settings, and a serialized file to save them
                    //TODO Add a leaderboeard, and a serialized file to save it
                    //TODO Add icon and sounds effects

                    swap();
                    gravity();
                    collisions();
                    rotation();
                    borderCheck(); //? This goes into movement()
                    hardDrop();

                    currentTetromino.update();
                    shadow();

                    left.toFront();
                    right.toFront();
                    down.toFront();
                } else {
                    currentTetromino.update();
                    placeTetromino();
                }
            }

            private static void placeTetromino(){

                //Removing old tetromino
                Matrix.removeFromRoot(currentTetromino);
                //Replacing it with an inactive one
                Matrix.addTetromino(currentTetromino);

                currentTetromino = Queue.getList().get(0);
                currentTetromino.getMinoCentral().setY(TILE * 3);
                currentTetromino.getMinoCentral().setX(TILE * 11);
                currentTetromino.setActive(true);
                swapped = false;

                //Removing old shadow
                Matrix.removeFromRoot(shadow);

                shadow = new Tetromino(currentTetromino, Color.DARKSLATEGRAY);

                Queue.cycleList();
            }
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
                } else { //? Dont know if i should make a function for barely 2 lines
                    if (tempGRV > GRAVITY) {
                        tempGRV = 0;
                        currentTetromino.getMinoCentral().setY(currentTetromino.getMinoCentral().getY() + TILE);
                    }
                }
            }
            private static void borderCheck(){
                currentTetromino.update();
                if (
                    currentTetromino.getMinoCentral().getX() - TILE != LEFTWALL * TILE
                    && currentTetromino.getMinoA().getX() - TILE != LEFTWALL * TILE
                    && currentTetromino.getMinoB().getX() - TILE != LEFTWALL * TILE
                    && currentTetromino.getMinoC().getX() - TILE != LEFTWALL * TILE
                    && currentTetromino.isntCollisingHorizontally(-1)
                ){
                    if (Keyboard.isLeft()) {
                        movement(-1); //* Negative

                        //? It mutliplies TILE by the given parameter, so it checks left or right, -1 or 1
                    }
                } else {
                    tempDAS = 0;
                }
                if (
                    currentTetromino.getMinoCentral().getX() + TILE != RIGHTWALL * TILE
                    && currentTetromino.getMinoA().getX() + TILE != RIGHTWALL * TILE
                    && currentTetromino.getMinoB().getX() + TILE != RIGHTWALL * TILE
                    && currentTetromino.getMinoC().getX() + TILE != RIGHTWALL * TILE
                    && currentTetromino.isntCollisingHorizontally(1)
                ){
                    if (Keyboard.isRight()) {
                        movement(1); //* Positive
                    }
                } else {
                    tempDAS = 0;
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
            private static void hardDrop(){
                if (Keyboard.isHardDrop()) {
                    if (!hardDropped){
                        hardDropped = true;
                        while (!currentTetromino.isColliding()) {
                            currentTetromino.getMinoCentral().setY(currentTetromino.getMinoCentral().getY() + TILE);
                            currentTetromino.update();
                        }
                        currentTetromino.setActive(false);
                    }
                } else {
                    hardDropped = false;
                }

            }
            private static void shadow(){

                //Bringing the shadow to the new position
                shadow.getMinoCentral().setY(currentTetromino.getMinoCentral().getY());
                shadow.getMinoA().setY(currentTetromino.getMinoA().getY());
                shadow.getMinoB().setY(currentTetromino.getMinoB().getY());
                shadow.getMinoC().setY(currentTetromino.getMinoC().getY());
                shadow.getMinoCentral().setX(currentTetromino.getMinoCentral().getX());
                shadow.getMinoA().setX(currentTetromino.getMinoA().getX());
                shadow.getMinoB().setX(currentTetromino.getMinoB().getX());
                shadow.getMinoC().setX(currentTetromino.getMinoC().getX());

                //Pushing the shadow down
                while (!shadow.isColliding()) {
                    shadow.getMinoCentral().setY(shadow.getMinoCentral().getY() + TILE);
                    shadow.getMinoA().setY(shadow.getMinoA().getY() + TILE);
                    shadow.getMinoB().setY(shadow.getMinoB().getY() + TILE);
                    shadow.getMinoC().setY(shadow.getMinoC().getY() + TILE);
                    shadow.update();
                }
                //Add the shadow
                if (!root.getChildren().contains(shadow.getMinoCentral())){
                    root.getChildren().add(shadow.getMinoCentral());
                    root.getChildren().add(shadow.getMinoA());
                    root.getChildren().add(shadow.getMinoB());
                    root.getChildren().add(shadow.getMinoC());
                }
                //Dont overwrite the tetromino with the shadow
                currentTetromino.getMinoCentral().toFront();
                currentTetromino.getMinoA().toFront();
                currentTetromino.getMinoB().toFront();
                currentTetromino.getMinoC().toFront();

            }
            private static void rotation(){
                if (Keyboard.isRotateCW() && !cwed) {
                    cwed = true;
                    currentTetromino.rotationCW();
                } else if (!Keyboard.isRotateCW()){cwed = false;}

                if (Keyboard.isRotateCCW() && !ccwed) {
                    ccwed = true;
                    currentTetromino.rotationCCW();
                } else if (!Keyboard.isRotateCCW()){ccwed = false;}
                
                if (Keyboard.isRotate180() && !one80ed) {
                    one80ed = true;
                    currentTetromino.rotation180();
                } else if (!Keyboard.isRotate180()){one80ed = false;}
            }
            private static void swap(){
                if (Keyboard.isSwap()) {
                    if (!swapping){
                        swapping = true;
                        if (!swapped){
                            if (!firstSwap){
                                Matrix.removeFromRoot(currentTetromino);
                                Tetromino temp = Tetromino.getID(currentTetromino);

                                currentTetromino = Tetromino.getID(hold);
                                hold = temp;

                                currentTetromino.getMinoCentral().setY(TILE * 3);
                                currentTetromino.getMinoCentral().setX(TILE * 11);
                                currentTetromino.setActive(true);
                                Matrix.removeFromRoot(shadow);
                                shadow = new Tetromino(currentTetromino, Color.DARKSLATEGRAY);
                                currentTetromino.update();
                                shadow.update();
                                Matrix.addToRoot(currentTetromino);

                                Matrix.removeFromRoot(save);
                                save = Tetromino.getID(hold);
                                save.getMinoCentral().setX(TILE * 3);
                                save.getMinoCentral().setY(TILE * 7);
                                save.paint(hold.getMinoCentral().getFill());
                                save.update();
                                Matrix.addToRoot(save);
                            } else {
                                hold = Tetromino.getID(currentTetromino);
                                Matrix.removeFromRoot(currentTetromino);
                                currentTetromino = Queue.getList().get(0);
                                currentTetromino.getMinoCentral().setY(TILE * 3);
                                currentTetromino.getMinoCentral().setX(TILE * 11);
                                currentTetromino.update();
                                currentTetromino.setActive(true);
                                Matrix.removeFromRoot(shadow);
                                shadow = new Tetromino(currentTetromino, Color.DARKSLATEGRAY);
                                Queue.cycleList();
                                firstSwap = false;

                                Matrix.removeFromRoot(save);
                                save = Tetromino.getID(hold);
                                save.getMinoCentral().setX(TILE * 3);
                                save.getMinoCentral().setY(TILE * 7);
                                save.paint(hold.getMinoCentral().getFill());
                                save.update();
                                Matrix.addToRoot(save);
                            }
                            swapped = true;
                        }
                    }
                } else {
                    swapping = false;
                }
            }
        };

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
