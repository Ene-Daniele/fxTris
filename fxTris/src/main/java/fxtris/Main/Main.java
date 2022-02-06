package fxtris.Main;

import fxtris.Main.Controls.Controller;
import fxtris.Main.GameEvents.Events;
import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Minoes.Tetrominoes.S;
import fxtris.Main.Others.Matrix;
import fxtris.Main.Stages.Settings;
import fxtris.Main.Queue.Queue;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;

import static fxtris.Main.GameEvents.Events.*;
import static fxtris.Main.Others.GlobalValues.*;

public class Main extends Application {

    public static Group root = new Group();

    public static Scene scene = new Scene(root, 600, 800, Color.BLACK);
    public static Settings settings;

    public static AnimationTimer frames;

    public static Tetromino currentTetromino = new Tetromino();

    public static Text clears = new Text("Tetris");
    public static Text tspin = new Text("T-Spin");
    public static Text perfectClear = new Text("Perfect\n  Clear");

    /**
     *
     * @param line line to format and add to the root
     */
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

        tspin.setFill(Color.WHITE);
        tspin.setX(35);
        tspin.setY(300);
        tspin.setFont(Font.font(40));
        tspin.setOpacity(0);

        clears.setFill(Color.WHITE);
        clears.setX(35);
        clears.setY(350);
        clears.setFont(Font.font(40));
        clears.setOpacity(0);

        perfectClear.setFill(Color.WHITE);
        perfectClear.setX(190);
        perfectClear.setY(400);
        perfectClear.setFont(Font.font(70));
        perfectClear.setOpacity(0);
        perfectClear.setFill(Color.YELLOW);

        Text holdtxt = new Text("HOLD");
        holdtxt.setFill(Color.WHITE);
        holdtxt.setX(50);
        holdtxt.setY(100);
        holdtxt.setFont(Font.font(40));

        Text nexttxt = new Text("NEXT");
        nexttxt.setFill(Color.WHITE);
        nexttxt.setX(470);
        nexttxt.setY(100);
        nexttxt.setFont(Font.font(40));

        Text settingstxt = new Text("To open settings press CTRL");
        settingstxt.setX(180);
        settingstxt.setY(780);
        settingstxt.setFont(Font.font(20));
        settingstxt.setFill(Color.WHITE);

        root.getChildren().add(holdtxt);
        root.getChildren().add(nexttxt);
        root.getChildren().add(settingstxt);
        root.getChildren().add(clears);
        root.getChildren().add(tspin);
        root.getChildren().add(perfectClear);

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

                tspin.setOpacity(tspin.getOpacity() - tspin.getOpacity() / 10);
                clears.setOpacity(clears.getOpacity() - clears.getOpacity() / 10);
                perfectClear.setOpacity(perfectClear.getOpacity() - perfectClear.getOpacity() / 10);

                if (currentTetromino.isActive()) {

                    //TODO Put the main root stuff in a separated class in the Stages package
                    //TODO Add UI stuff (score, last clear, b2b, etc)
                    //TODO Add settings, and a serialized file to save them
                    //TODO Add a leaderboard, and a serialized file to save it
                    //TODO Add icon, BPM, and SFX

                    swap();
                    gravity();
                    rotation();
                    collisions();
                    borderCheck(); //? This goes into movement(), in the Events class
                    hardDrop();

                    currentTetromino.update();
                    shadow();

                    fixOverlapBug(); //Corner collision bug, classic reoccurrence in every project of mine
                    restart();

                    if (settings.isShowing()){
                        Events.setTempGRV(0);
                    }

                    left.toFront();
                    right.toFront();
                    down.toFront();
                } else {
                    currentTetromino.update();
                    Tetromino temp = new Tetromino(currentTetromino, currentTetromino.getMinoCentral().getFill());/*
                        ? I needed a temp for this because the current tetromino wasnt getting deleted,
                        ? so i do it after i add it to the matrix, and use a temp to verify the topOut condition*/

                    placeTetromino();

                    if (topOut(temp)){
                        reset();
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
