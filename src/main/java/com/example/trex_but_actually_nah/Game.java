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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class Game implements Initializable {
    @FXML
    private Button playButton, menuButton, restartButton;
    @FXML
    private AnchorPane mainScene, slideMenu;
    @FXML
    private ImageView trex, trexLogo;
    @FXML
    private Label scoreText;

    private boolean bool, faceLeft, faceRight = true;
    private boolean right, left = false;
    TranslateTransition transition;
    ArrayList<Character> trexArray = new ArrayList<>();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private SmallInfoLabel pointsLabel;
    private ImageView[] trexLifes, trexEmptyLifes;
    private int trexLife;
    private int points = 0;
    private ImageView[] meteors;
    private ImageView[] meats;
    private ImageView meat;
    private Random random = new Random();
    private static final int MEAT_RADIUS = 20;
    private static final int METEOR_RADIUS = 20;
    private static final int TREX_RADIUS = 55;



    //metoda, ktora nacita playScreen.fxml (hraciu plochu)
    public void onPlay(ActionEvent event) throws IOException {
        if (event.getSource() == playButton || event.getSource() == restartButton) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("layout/playScreen.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("layout/Style.css")).toString());
            stage.setScene(scene);
            stage.show();
        }
    }

    //metoda, ktora nas vrati do menu
    public void onBackToMenu(ActionEvent event) throws IOException {
        if (event.getSource() == menuButton) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("layout/menu.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("layout/Style.css")).toString());
            stage.setScene(scene);
            stage.show();
        }
    }

    //metoda, ktora pomocou transitionu privola help tabulku
    public void onHelp(ActionEvent event) {
        slideMenu.setVisible(true);
        if (!bool) {
            transitionLeftToRightMenu();
            transitionLeftToRightLogo();
            bool = true;
        } else if (bool) {
            transitionRightToLeftMenu();
            transitionRightToLeftLogo();
            bool = false;
        }
    }

    //metoda, ktora nacita scoreScreen po skonceni hry
    private void GameOver() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("layout/scoreScreen.fxml")));
        stage = (Stage) mainScene.getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("layout/Style.css")).toString());
        scoreText = (Label)scene.lookup("#scoreText");
        scoreText.setText("SCORE: "+ String.valueOf(points));
        stage.setScene(scene);
        stage.show();
    }


    //ukoncenie aplikacie
    public void onExit() {
        Platform.exit();
    }

    //pohyb objektov pomocou transition
    public void transitionLeftToRightMenu() {
        transition = new TranslateTransition();
        transition.setNode(slideMenu);
        transition.setDuration(Duration.seconds(0.5));
        transition.setToX(mainScene.getWidth() - 50);
        transition.play();
    }

    public void transitionRightToLeftMenu() {
        transition = new TranslateTransition();
        transition.setNode(slideMenu);
        transition.setDuration(Duration.seconds(0.5));
        transition.setToX(mainScene.getWidth() - 576);
        transition.play();
    }

    public void transitionLeftToRightLogo() {
        transition = new TranslateTransition();
        transition.setNode(trexLogo);
        transition.setDuration(Duration.seconds(0.5));
        transition.setToX(mainScene.getWidth() - 20);
        transition.play();
    }

    public void transitionRightToLeftLogo() {
        transition = new TranslateTransition();
        transition.setNode(trexLogo);
        transition.setDuration(Duration.seconds(0.5));
        transition.setToX(mainScene.getWidth() - 596);
        transition.play();
    }

    //pohyb hracej postavy
    public void onMove(KeyEvent keyEvent) throws FileNotFoundException {
        try {
            if (trex.isVisible()) {
                if (keyEvent.getCode() == KeyCode.A) {
                    InputStream stream = new FileInputStream("src/main/resources/com/example/trex_but_actually_nah/img/trexLeft.png");
                    Image image = new Image(stream);
                    trex.setImage(image);
                    trex.setFitHeight(130);
                    trex.setFitWidth(140);
                    left = true;
                    right = false;
                }
                if (keyEvent.getCode() == KeyCode.D) {
                    InputStream stream = new FileInputStream("src/main/resources/com/example/trex_but_actually_nah/img/trexRight.png");
                    Image image = new Image(stream);
                    trex.setImage(image);
                    trex.setFitHeight(130);
                    trex.setFitWidth(140);
                    right = true;
                    left = false;
                }
            }
        } catch (NullPointerException e) {
        }
    }

    //vytvorenie elementov (meteoritov, mäsa..)
    private void createElements() throws FileNotFoundException {
        trexLife = 2;
        pointsLabel = new SmallInfoLabel("POINTS: 0");
        pointsLabel.setLayoutX(460);
        pointsLabel.setLayoutY(20);
        mainScene.getChildren().add(pointsLabel);


        trexLifes = new ImageView[3];
        for (int i = 0; i < trexLifes.length; i++) {
            Image image = new Image(getClass().getResourceAsStream("img/emptylife.png"));
            trexLifes[i] = new ImageView(image);
            trexLifes[i].setLayoutX(465 + (i * 40));
            trexLifes[i].setLayoutY(80);
            mainScene.getChildren().add(trexLifes[i]);
        }

        for (int i = 0; i < 3; i++) {
            Image image = new Image(getClass().getResourceAsStream("img/fulllife.png"));
            trexLifes[i] = new ImageView(image);
            trexLifes[i].setLayoutX(465 + (i * 40));
            trexLifes[i].setLayoutY(80);
            mainScene.getChildren().add(trexLifes[i]);
        }


        meteors = new ImageView[5];
        for (int i = 0; i < meteors.length; i++) {
            Image image = new Image(getClass().getResourceAsStream("img/meteor.png"));
            meteors[i] = new ImageView(image);
            mainScene.getChildren().add(meteors[i]);
            setElementsPosition(meteors[i]);
        }

        meats = new ImageView[2];
        for (int i = 0; i < meats.length; i++) {
            Image image = new Image(getClass().getResourceAsStream("img/meat.png"));
            meats[i] = new ImageView(image);
            mainScene.getChildren().add(meats[i]);
            setElementsPosition(meats[i]);
        }
    }

    //pohyb elementov (meteoritov, mäsa..)
    private void makeElementsMove() {
        if (trexLife >= 0) {
            if (points >= 0 && points < 10) {
                for (int i = 0; i < meteors.length; i++) {
                    meteors[i].setLayoutY(meteors[i].getLayoutY() + 5);
                }
                for (int i = 0; i < meats.length; i++) {
                    meats[i].setLayoutY(meats[i].getLayoutY() + 5);
                }

            } else if (points >= 10 && points < 20) {
                for (int i = 0; i < meteors.length; i++) {
                    meteors[i].setLayoutY(meteors[i].getLayoutY() + 7);
                }
                for (int i = 0; i < meats.length; i++) {
                    meats[i].setLayoutY(meats[i].getLayoutY() + 7);
                }

            } else if (points >= 20 && points < 30) {
                for (int i = 0; i < meteors.length; i++) {
                    meteors[i].setLayoutY(meteors[i].getLayoutY() + 10);
                }
                for (int i = 0; i < meats.length; i++) {
                    meats[i].setLayoutY(meats[i].getLayoutY() + 10);
                }

            } else if (points >= 30) {
                for (int i = 0; i < meteors.length; i++) {
                    meteors[i].setLayoutY(meteors[i].getLayoutY() + 12);
                }
                for (int i = 0; i < meats.length; i++) {
                    meats[i].setLayoutY(meats[i].getLayoutY() + 12);
                }
            }
        } else {
            for (int i = 0; i < meteors.length; i++) {
                meteors[i].setLayoutY(10);
            }
            for (int i = 0; i < meats.length; i++) {
                meats[i].setLayoutY(10);
            }
        }
    }

    //kontrola elementov, aby sa mohli respawnut
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

    //nastavenie pozicie elementov
    private void setElementsPosition(ImageView image){
        image.setLayoutX(random.nextInt(555));
        image.setLayoutY(-(random.nextInt(2000)));
    }

    //zistovanie, ci sa elementy narazili s trexom
    private void checkIfElementsCollide(){
        for (int i = 0; i < meteors.length; i++) {
            if (TREX_RADIUS + METEOR_RADIUS > calculateDistance(trex.getX() + 55, meteors[i].getLayoutX() + 20, trex.getY() + 50, meteors[i].getLayoutY() + 20 )){
                removeLife();
                setElementsPosition(meteors[i]);
            }
        }

        for (int i = 0; i < meats.length ; i++) {
            if (TREX_RADIUS + MEAT_RADIUS > calculateDistance(trex.getX() + 55, meats[i].getLayoutX() + 20, trex.getY() + 50, meats[i].getLayoutY() + 20 )){
                setElementsPosition(meats[i]);

                points++;
                String textToSet = "POINTS: ";
                pointsLabel.setText(textToSet + points);
            }
        }
    }

    //vypocet vzdialenosti elementov s trexom
    private double calculateDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }

    //odcitanie hracich zivotov
    private void removeLife(){
        try{
            mainScene.getChildren().remove(trexLifes[trexLife]);
            trexLife--;
            if (trexLife < 0){
                GameOver();

            }
        } catch ( NullPointerException e){
        } catch (IOException e) {
        }
    }

    //pohyb hracej postavy, "constructor" v uvodzvokach
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            trex.getLayoutY();
            createElements();
            new AnimationTimer() {
                @Override
                public void handle(long l) {
                    makeElementsMove();
                    checkIfElementsBehindTheTRex();
                    checkIfElementsCollide();
                    if (right) {
                        if (trex.getX() + 7 < mainScene.getWidth() - trex.getFitWidth()) {
                            trex.setX(trex.getX() + 7);
                        }
                }
                    if (left) {
                        if (trex.getX() - 7 >= 0) {
                            trex.setX(trex.getX() - 7);
                        }
                    }
                }
            }.start();
        } catch (FileNotFoundException | NullPointerException e) {
        }
    }
}
