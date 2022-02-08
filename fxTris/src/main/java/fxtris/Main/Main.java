package fxtris.Main;

import fxtris.Main.Controls.Controller;
import fxtris.Main.GameEvents.Events;
import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Minoes.Tetrominoes.S;
import fxtris.Main.Others.Matrix;
import fxtris.Main.Queue.Queue;
import fxtris.Main.Stages.GameStage;
import fxtris.Main.Stages.SettingsStage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.util.Objects;

import static fxtris.Main.GameEvents.Events.*;

public class Main extends Application {

    public static AnimationTimer frames;

    public static Tetromino currentTetromino = new Tetromino();

    @Override
    public void start(Stage stage) {

        //!Make more of these for the sfx and pass the as parameter to the respective function
        AudioClip bgm = new AudioClip(Objects.requireNonNull(this.getClass().getResource("/Audios/BGM.mp3")).toString());
        bgm.play();

        Controller.loadController();
        Matrix.loadMatrix();
        Queue.loadFirstQueue();
        SettingsStage.loadSettings();

        //* Placeholder tetromino needed to start cycling through the queue, it will change as soon as handle() starts.
        currentTetromino = new S();

        frames = new AnimationTimer() {

            @Override
            public void handle(long l) {

                GameStage.tspin.setOpacity(GameStage.tspin.getOpacity() - GameStage.tspin.getOpacity() / 10);
                GameStage.clears.setOpacity(GameStage.clears.getOpacity() - GameStage.clears.getOpacity() / 10);
                GameStage.perfectClear.setOpacity(GameStage.perfectClear.getOpacity() - GameStage.perfectClear.getOpacity() / 10);

                if (currentTetromino.isActive()) {

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

                    if (!bgm.isPlaying() && GameStage.musicOn){ //Loops the music
                        bgm.play();
                    }
                    if (SettingsStage.isShowing()){ //Pauses the game
                        Events.setTempGRV(0);
                    }

                    GameStage.left.toFront();
                    GameStage.right.toFront();
                    GameStage.down.toFront();
                    updateGraphics();
                } else {
                    currentTetromino.update();
                    Tetromino temp = new Tetromino(currentTetromino, currentTetromino.getMinoCentral().getFill());/*
                        ? I needed a temp for this because the current tetromino wasnt getting deleted,
                        ? so i do it after i add it to the matrix, and use a temp to verify the topOut conditio
                        */
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

        //? I cant make an ImageView in a static context, so i make it here instead
        ImageView musicIcon = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResource("/Images/musicIcon.png")).toString()));
        musicIcon.setFitHeight(30);
        musicIcon.setPreserveRatio(true);
        ImageView musicIconOff = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResource("/Images/musicIconOff.png")).toString()));
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
        ImageView settingsIcon = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResource("/Images/settingsIcon.png")).toString()));
        settingsIcon.setFitHeight(30);
        settingsIcon.setPreserveRatio(true);
        GameStage.toggleSettings.setGraphic(settingsIcon);
        GameStage.toggleSettings.setOnMouseClicked(mouseEvent -> SettingsStage.openSettings());
    }

    public static void main(String[] args) {
        launch();
    }
}
/*
 ! <Ev/>
 */