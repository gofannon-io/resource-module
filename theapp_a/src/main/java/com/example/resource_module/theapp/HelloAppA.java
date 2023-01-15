package com.example.resource_module.theapp;

import java.io.IOException;

public class HelloAppA {
    public static void main(String[] args) throws IOException {
        checkResource("Root package", "/resource-0.txt");
        checkResource("Exported package", "/com/example/resource_module/theapp/resource-1.txt");
        checkResource("Internal package", "/com/example/resource_module/theapp/internal/resource-2.txt");
        checkResource("Opened package", "/com/example/resource_module/theapp/opened/resource-3.txt");


        checkResource("Exported directory", "/com/example/resource_module/theapp/dir/resource-4.txt");
        checkResource("Internal directory", "/com/example/resource_module/theapp/dir/internal/resource-5.txt");
        checkResource("Opened directory", "/com/example/resource_module/theapp/dir/opened/resource-6.txt");
    }


    private static void checkResource(String label, String resourcePath) {
        try {
            String content = ResourceReader.loadAsString(resourcePath);
            System.out.println(label + " content: " + content);
        } catch (Exception ex) {
            System.out.println(label + " content: FAILURE");
        }
    }
}
