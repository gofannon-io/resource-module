package com.example.resource_module.theapp;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URISyntaxException;

public class HelloApp {
    public static void main(String[] args) throws IOException, URISyntaxException {

Image image =new Image("jar:file:///D:/repositories/gradle/caches/modules-2/files-2.1/com.example.resource_module/lib_d/1.0-SNAPSHOT/b36e1ee51bd2dfa172af5f2e9cd13c66d7b5267f/lib_d-1.0-SNAPSHOT.jar!/resource-0.txt");
System.out.println("Height: "+image.getHeight());
System.out.println("Width: "+image.getWidth());
//        printRootResource0();
    }

    public static void printRootResource0() throws IOException, URISyntaxException {
        String content = ResourceReader.loadAsString("/resource-0.txt");
        System.out.println("Root content: " + content);
    }
}
