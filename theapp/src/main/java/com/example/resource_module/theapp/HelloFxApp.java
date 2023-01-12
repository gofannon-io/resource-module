package com.example.resource_module.theapp;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class HelloFxApp extends Application {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("jar:file:///D:/repositories/gradle/caches/modules-2/files-2.1/com.example.resource_module/lib_d/1.0-SNAPSHOT/6603a7cc7a87513f797d7d3cf0518a2395ecabc6/lib_d-1.0-SNAPSHOT.jar!/sample2.png");
        ImageView imageView = new ImageView(image);
        VBox box = new VBox();
        box.getChildren().add(imageView);
        Scene scene = new Scene(box);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
