package com.example.resource_module.theapp;

import java.io.IOException;

public class HelloApp {
    public static void main(String[] args) {
        printRootResource();
        printExportedResource();
        printInternalResource();
        printOpenedResource();
    }

    public static void printRootResource() {
        try {
            String content = ResourceReader.loadAsString("/resource-0-theapp.txt");
            System.out.println("Root content: " + content);
        } catch (IOException ex) {
            System.err.println("Root content: failure");
        }
    }

    public static void printExportedResource() {
        try {
            String content = ResourceReader.loadAsString("/com/example/resource_module/theapp/resource-1-theapp.txt");
            System.out.println("Exported content: " + content);
        } catch (IOException ex) {
            System.err.println("Exported content: failure");
        }
    }

    public static void printInternalResource() {
        try {
            String content = ResourceReader.loadAsString("/com/example/resource_module/theapp/internal/resource-2-theapp.txt");
            System.out.println("Internal content: " + content);
        } catch (IOException ex) {
            System.err.println("Internal content: failure");
        }
    }

    public static void printOpenedResource() {
        try {
            String content = ResourceReader.loadAsString("/com/example/resource_module/theapp/opened/resource-3-theapp.txt");
            System.out.println("Opened content: " + content);
        } catch (IOException ex) {
            System.err.println("Opened content: failure");
        }
    }

}
