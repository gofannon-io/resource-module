package com.example.resource_module.theapp;

import java.io.IOException;

public class HelloApp {
    public static void main(String[] args) throws IOException {
        printRootResource();
        printExportedResource();
        printInternalResource();
        printOpenedResource();
    }

    public static void printRootResource() throws IOException {
        String content = ResourceReader.loadAsString("/resource-0-theapp.txt");
        System.out.println("Root content: " + content);
    }

    public static void printExportedResource() throws IOException {
        String content = ResourceReader.loadAsString("/com/example/resource_module/theapp/resource-1-theapp.txt");
        System.out.println("Exported content: " + content);
    }

    public static void printInternalResource() throws IOException {
        String content = ResourceReader.loadAsString("/com/example/resource_module/theapp/internal/resource-2-theapp.txt");
        System.out.println("Internal content: " + content);
    }

    public static void printOpenedResource() throws IOException {
        String content = ResourceReader.loadAsString("/com/example/resource_module/theapp/opened/resource-3-theapp.txt");
        System.out.println("Opened content: " + content);
    }

}
