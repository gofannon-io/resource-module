package com.example.resource_module.theapp;

import com.example.resource_module.lib_b.Sample;

import java.io.IOException;

public class HelloAppD {
    public static void main(String[] args) throws IOException {
        new Sample();

        checkResource("Root package", "/resource-0.txt");
        checkResource("Exported package", "/com/example/resource_module/lib_b/resource-1.txt");
        checkResource("Internal package", "/com/example/resource_module/lib_b/internal/resource-2.txt");
        checkResource("Opened package", "/com/example/resource_module/lib_b/opened/resource-3.txt");

        checkResource("Exported directory", "/com/example/resource_module/lib_b/dir/resource-4.txt");
        checkResource("Internal directory", "/com/example/resource_module/lib_b/dir/internal/resource-5.txt");
        checkResource("Opened directory", "/com/example/resource_module/lib_b/dir/opened/resource-6.txt");
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