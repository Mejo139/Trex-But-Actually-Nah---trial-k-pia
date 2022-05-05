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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button playButton, helpButton, exitButton, menuButton;

    @FXML
    private AnchorPane mainScene, slideMenu, playScene;

    @FXML
    private Rectangle shade;

    @FXML
    private ImageView trex, trexLogo;

    @FXML
    private Image trexRight,trexLeft;

    private boolean b, faceLeft, faceRight = true;
    private boolean right, left = false;
    TranslateTransition transition;
    ArrayList<Character> trexArray = new ArrayList<>();

    private Stage stage;
    private Scene scene;
    private Parent root;



    private ImageView[] meteors;
    private ImageView[] meats;
    private Random random=new Random();

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
    //metoda, ktora nacita pauseScreen.fxml (pause menu)
//    public void onPause(ActionEvent event) throws IOException{
//        if (event.getSource() == menuButton){
//            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pauseScreen.fxml")));
//            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//            scene = new Scene(root);
//            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toString());
//            stage.setScene(scene);
//            stage.show();
//        }
//    }

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
    public void onMove(KeyEvent keyEvent) throws FileNotFoundException {
        if (trex.isVisible()){
            if (keyEvent.getCode() == KeyCode.A){
                InputStream stream = new FileInputStream("src/main/resources/com/example/trex_but_actually_nah/trex.png");
                Image mirkoToRobil = new Image(stream);
                trex.setImage(mirkoToRobil);
                trex.setFitHeight(130);
                trex.setFitWidth(140);
                left = true;
                right = false;
            }
            if (keyEvent.getCode() == KeyCode.D){
                InputStream stream = new FileInputStream("src/main/resources/com/example/trex_but_actually_nah/trexRight.png");
                Image mirkoToRobil = new Image(stream);
                trex.setImage(mirkoToRobil);
                trex.setFitHeight(130);
                trex.setFitWidth(140);
                right = true;
                left = false;
            }
        }
    }

//    public void onStoj(KeyEvent keyEvent){
//        System.out.println("som gay");
//        if (keyEvent.getCode() == KeyCode.D){
//            System.out.println("Prešlo ti debil");
//            right = false;
//        }
//        if (keyEvent.getCode() == KeyCode.A){
//            System.out.println("Prešlo ti debil dolava");
//            left = false;
//
//        }
//    }
    private void createElements() throws FileNotFoundException {
        meteors=new ImageView[5];
        for (int i = 0; i < meteors.length; i++) {
            Image image = new Image(getClass().getResourceAsStream("meteor.png"));
            meteors[i] = new ImageView(image);
            mainScene.getChildren().add(meteors[i]);
            setElementsPosition(meteors[i]);


        }

        meats=new ImageView[3];
        for (int i = 0; i < meats.length; i++) {
            Image image = new Image(getClass().getResourceAsStream("meat.png"));
            meats[i] = new ImageView(image);
            mainScene.getChildren().add(meats[i]);
            setElementsPosition(meats[i]);


        }
    }

    private void makeElementsMove(){
        for (int i = 0; i < meteors.length ; i++) {
            meteors[i].setLayoutY(meteors[i].getLayoutY()+5);
        }
        for (int i = 0; i < meats.length ; i++) {
            meats[i].setLayoutY(meats[i].getLayoutY()+5);
        }

    }

    private void checkIfElementsBehindTheTRex(){
        for (int i = 0; i < meteors.length ; i++) {
            if (meteors[i].getLayoutY() > 800){
                setElementsPosition(meteors[i]);
            }
        }

        for (int i = 0; i < meats.length ; i++) {
            if (meats[i].getLayoutY() > 800){
                setElementsPosition(meats[i]);
            }
        }

    }

    private void setElementsPosition(ImageView image){
        image.setLayoutX(random.nextInt(555));
        image.setLayoutY(-(random.nextInt(2000)));
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        //pohyb hracej postavy
        try {
            createElements();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                makeElementsMove();
                checkIfElementsBehindTheTRex();
                if (right) {
                    if (trex.getX() + 10 < mainScene.getWidth() - 345) {
                        trex.setX(trex.getX() + 10);
                    }
                }
                if (left) {
                    if (trex.getX() - 10 >= -215) {
                        trex.setX(trex.getX() - 10);
                    }
                }
            }
        }.start();
    }
}