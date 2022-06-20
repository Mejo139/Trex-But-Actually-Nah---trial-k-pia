package com.example.trex_but_actually_nah;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SmallInfoLabel extends Label {

    public SmallInfoLabel(String text) throws FileNotFoundException {
        setPrefWidth(130);
        setPrefHeight(50);
        InputStream stream = new FileInputStream("src/main/resources/com/example/trex_but_actually_nah/img/infolabel.png");
        BackgroundImage backgroundImage = new BackgroundImage(new Image(stream, 130, 50, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10, 10, 10, 10));
        setFont(Font.font("Showcard Gothic"));
        setText(text);
        setTextFill(Color.WHITE);
    }
}
