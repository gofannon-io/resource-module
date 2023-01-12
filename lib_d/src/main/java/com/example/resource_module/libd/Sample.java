package com.example.resource_module.libd;

import java.io.IOException;

public class Sample {

    private static String toExternalForm(String resourcePath) {
        return Sample.class.getResource(resourcePath).toExternalForm();
    }


    public static final String ROOT_RESOURCE =  toExternalForm("/resource-0.txt");
    public static final String EXPORTED_RESOURCE =  toExternalForm("/com/example/resource_module/libd/resource-1.txt");
    public static final String INTERNAL_RESOURCE =  toExternalForm("/com/example/resource_module/libd/internal/resource-2.txt");
    public static final String OPENED_RESOURCE =  toExternalForm("/com/example/resource_module/libd/opened/resource-3.txt");

    public void doStuff() {
        System.out.println("Lib D check:");
        printRootResource();
        printExportedResource();
        printInternalResource();
        printOpenedResource();
        System.out.println("---------------");
    }

    public static void printRootResource() {
        try {
            String content = ResourceReader2.loadAsString("/resource-0.txt");
            System.out.println("Root content: " + content);
        } catch (IOException ex) {
            System.err.println("Root content: failure");
        }
    }

    public static void printExportedResource() {
        try {
            String content = ResourceReader2.loadAsString("/com/example/resource_module/libd/resource-1.txt");
            System.out.println("Exported content: " + content);
        } catch (IOException ex) {
            System.err.println("Exported content: failure");
        }
    }

    public static void printInternalResource() {
        try {
            String content = ResourceReader2.loadAsString("/com/example/resource_module/libd/internal/resource-2.txt");
            System.out.println("Internal content: " + content);
        } catch (IOException ex) {
            System.err.println("Internal content: failure");
        }
    }

    public static void printOpenedResource() {
        try {
            String content = ResourceReader2.loadAsString("/com/example/resource_module/libd/opened/resource-3.txt");
            System.out.println("Opened content: " + content);
        } catch (IOException ex) {
            System.err.println("Opened content: failure");
        }
    }
}
