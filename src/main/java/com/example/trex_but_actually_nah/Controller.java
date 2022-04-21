package com.example.trex_but_actually_nah;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button playButton, helpButton, exitButton, menuButton;

    @FXML
    private AnchorPane mainScene, slideMenu;

    @FXML
    private Rectangle shade;

    @FXML
    private ImageView trex, trexLogo;

    @FXML
    private Image trexRight;

    private boolean b, faceLeft, faceRight = true;
    private boolean right, left = false;
    TranslateTransition transition;
    ArrayList<Character> trexArray = new ArrayList<>();

    private Stage stage;
    private Scene scene;
    private Parent root;

    //metoda, ktora nacita playScreen.fxml (hraciu plochu)
    public void onPlay(ActionEvent event) throws IOException {
        if (event.getSource() == playButton){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("playScreen.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toString());
            stage.setScene(scene);
            stage.show();
        }
    }

    //metoda, ktora pomocou transitionu privola help tabulku
    public void onHelp(ActionEvent event){
        slideMenu.setVisible(true);
        if (!b){
            transitionLeftToRightMenu();
            transitionLeftToRightLogo();
            b = true;
        }else if (b){
            transitionRightToLeftMenu();
            transitionRightToLeftLogo();
            b = false;
        }
    }
    //metoda, ktora nacita menu.fxml (menu)
    public void onPause(ActionEvent event) throws IOException{
        if (event.getSource() == menuButton){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pauseScreen.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toString());
            stage.setScene(scene);
            stage.show();
        }
    }

    //ukoncenie aplikacie
    public void onExit(){
        Platform.exit();
    }

    //pohyb objektov pomocou transition
    public void transitionLeftToRightMenu(){
        transition = new TranslateTransition();
        transition.setNode(slideMenu);
        transition.setDuration(Duration.seconds(0.5));
        transition.setToX(mainScene.getWidth()-50);
        transition.play();
    }

    public void transitionRightToLeftMenu(){
        transition = new TranslateTransition();
        transition.setNode(slideMenu);
        transition.setDuration(Duration.seconds(0.5));
        transition.setToX(mainScene.getWidth()-576);
        transition.play();
    }

    public void transitionLeftToRightLogo(){
        transition = new TranslateTransition();
        transition.setNode(trexLogo);
        transition.setDuration(Duration.seconds(0.5));
        transition.setToX(mainScene.getWidth()-20);
        transition.play();
    }

    public void transitionRightToLeftLogo(){
        transition = new TranslateTransition();
        transition.setNode(trexLogo);
        transition.setDuration(Duration.seconds(0.5));
        transition.setToX(mainScene.getWidth()-596);
        transition.play();
    }

    //pohyb hracej postavy
    public void onMove(KeyEvent keyEvent){
        if (trex.isVisible()){
            if (keyEvent.getCode() == KeyCode.A){
                right = true;
                left = false;
            }
            if (keyEvent.getCode() == KeyCode.D){
                left = true;
                right = false;
                //trexRight = new Image("src/main/resources/com/example/trex_but_actually_nah/img/trexRight.png");
                //trex.setImage(trexRight);
            }
        }
    }

    public void onStoj(KeyEvent keyEvent){
        if (keyEvent.getCode() == KeyCode.D){
            right = true;
        }
        if (keyEvent.getCode() == KeyCode.A){
            left = true;

        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
        //pohyb hracej postavy
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if (left){
                    trex.setX(trex.getX()+10);
                }
                if (right){
                    trex.setX(trex.getX()-10);
                }
            }
        }.start();

//        for (Character trexik:trexArray) {
//            if (trex.getX()>back.getWidth()){
//                trexik.vx = -trexik.vx;
//            }
//            if (trex.getX()<0){
//                trexik.vx = -trexik.vx;
//            }
//        }
    }

//    if (gula.getCenterX()+gula.getRadius()>panel.getWidth()) bgula.vx=-bgula.vx;
//    if (gula.getCenterX()-gula.getRadius()<0) bgula.vx=-bgula.vx;
//
//    if (gula.getCenterY()+gula.getRadius()>panel.getHeight()) bgula.vy=-bgula.vy;
//    if (gula.getCenterY()-gula.getRadius()<0) bgula.vy=-bgula.vy;
}