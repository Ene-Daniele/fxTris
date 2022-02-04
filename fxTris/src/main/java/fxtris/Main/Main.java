package fxtris.Main;

import fxtris.Main.Controls.Controller;
import fxtris.Main.GameEvents.Events;
import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Minoes.Tetrominoes.S;
import fxtris.Main.Others.Matrix;
import fxtris.Main.Stages.Settings;
import fxtris.Main.Queue.Queue;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static fxtris.Main.GameEvents.Events.*;
import static fxtris.Main.Others.GlobalValues.*;

public class Main extends Application {

    public static Group root = new Group();

    public static Scene scene = new Scene(root, 800, 800, Color.BLACK);
    public static Settings settings;

    public static AnimationTimer frames;

    public static Tetromino currentTetromino = new Tetromino();

    static void addLine(Line line) {
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

        Text holdtxt = new Text("HOLD");
        Text nexttxt = new Text("NEXT");
        Text settingstxt = new Text("To open settings press CTRL");
        holdtxt.setFill(Color.WHITE);
        holdtxt.setX(50);
        holdtxt.setY(100);
        holdtxt.setFont(Font.font(40));
        nexttxt.setFill(Color.WHITE);
        nexttxt.setX(470);
        nexttxt.setY(100);
        nexttxt.setFont(Font.font(40));
        settingstxt.setFill(Color.WHITE);
        settingstxt.setX(180);
        settingstxt.setY(780);
        settingstxt.setFont(Font.font(20));
        root.getChildren().add(holdtxt);
        root.getChildren().add(nexttxt);
        root.getChildren().add(settingstxt);

        settings = new Settings();

        addLine(left);
        addLine(right);
        addLine(down);
        addLine(top);

        Controller.loadController();
        Matrix.loadMatrix();
        Queue.loadFirstQueue();

        //* Placeholder tetromino needed to start cycling through the queue, it will change as soon as handle() starts.
        currentTetromino = new S();

        frames = new AnimationTimer() { //AnimationTimer my beloved <3

            @Override
            public void handle(long l) {

                if (currentTetromino.isActive()) {

                    //TODO Put the main root stuff in a separated class in the Stages package
                    //TODO Add UI stuff (score, last clear, b2b, etc)
                    //TODO Add settings, and a serialized file to save them
                    //TODO Add a leaderboard, and a serialized file to save it
                    //TODO Add icon and sounds effects

                    swap();
                    gravity();
                    collisions();
                    rotation();
                    borderCheck(); //? This goes into movement(), in the Events class
                    hardDrop();

                    settings.openSettings();
                    currentTetromino.update();
                    shadow();

                    fixOverlapBug(); //Corner collision bug, classic reoccurrence in every project of mine

                    left.toFront();
                    right.toFront();
                    down.toFront();
                } else {
                    currentTetromino.update();
                    Tetromino temp = new Tetromino(currentTetromino, currentTetromino.getMinoCentral().getFill());
                    placeTetromino();

                    if (topOut(temp)){/*
                        ? I needed a temp for this because the current tetromino wasnt getting deleted,
                        ? so i do it after i add it to the matrix, and use a temp to verify the topOut condition*/
                        Matrix.reset();
                        Matrix.removeFromRoot(currentTetromino);

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
