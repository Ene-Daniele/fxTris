package fxtris.Main;

import fxtris.Main.Controls.Controller;
import fxtris.Main.Controls.Keyboard;
import fxtris.Main.GameEvents.Events;
import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Minoes.Tetrominoes.I;
import fxtris.Main.Minoes.Tetrominoes.S;
import fxtris.Main.Others.Matrix;
import fxtris.Main.Stages.GameStage;
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
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
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

    public static Settings settings;

    public static AnimationTimer frames;

    public static Tetromino currentTetromino = new Tetromino();

    @Override
    public void start(Stage stage) {

        settings = new Settings();

        //!Make more of these for the sfx and pass the as parameter to the respective function
        AudioClip bgm = new AudioClip(this.getClass().getResource("/Audios/BGM.mp3").toString());

        Controller.loadController();
        Matrix.loadMatrix();
        Queue.loadFirstQueue();

        //* Placeholder tetromino needed to start cycling through the queue, it will change as soon as handle() starts.
        currentTetromino = new S();

        frames = new AnimationTimer() { //AnimationTimer my beloved <3

            @Override
            public void handle(long l) {

                GameStage.tspin.setOpacity(GameStage.tspin.getOpacity() - GameStage.tspin.getOpacity() / 10);
                GameStage.clears.setOpacity(GameStage.clears.getOpacity() - GameStage.clears.getOpacity() / 10);
                GameStage.perfectClear.setOpacity(GameStage.perfectClear.getOpacity() - GameStage.perfectClear.getOpacity() / 10);

                if (currentTetromino.isActive()) {

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
                    if (!bgm.isPlaying() && GameStage.musicOn){
                        bgm.play();
                    }

                    GameStage.left.toFront();
                    GameStage.right.toFront();
                    GameStage.down.toFront();
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
        stage.setScene(GameStage.getTheScene());
        stage.getIcons().add(new Image("ICON.ico"));
        stage.show();
        stage.setResizable(false);

        frames.start();

        //Sound togglers
        //? I can make an ImageView in a static context, so i make it here instead
        ImageView musicIcon = new ImageView(new Image(this.getClass().getResource("/Images/musicIcon.png").toString()));
        musicIcon.setFitHeight(30);
        musicIcon.setPreserveRatio(true);
        ImageView musicIconOff = new ImageView(new Image(this.getClass().getResource("/Images/musicIconOff.png").toString()));
        musicIconOff.setFitHeight(30);
        musicIconOff.setPreserveRatio(true);
        GameStage.toggleMusic.setGraphic(musicIcon);
        GameStage.toggleMusic.setOnMouseClicked(mouseEvent -> {
            if (GameStage.musicOn) {
                bgm.stop();
                GameStage.musicOn = false;
                GameStage.toggleMusic.setGraphic(musicIconOff);
            } else {
                bgm.play();
                GameStage.musicOn = true;
                GameStage.toggleMusic.setGraphic(musicIcon);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
