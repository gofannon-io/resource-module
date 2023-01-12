package com.example.resource_module.theapp;

import com.example.resource_module.libd.Sample;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class HelloApp {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException {

        System.out.println("--- Remote access ------");
        printRootResource();
        printExportedResource();
        printInternalResource();
        printOpenedResource();
        System.out.println("----------------------");

        new Sample().doStuff();


        System.out.println("--- Hacked remote access ------");
        printHackedRootResource();
        printHackedExportedResource();
        printHackedInternalResource();
        printHackedOpenedResource();
        System.out.println("----------------------");
    }

    public static void printRootResource() {
        try {
            String content = ResourceReader.loadAsString("/resource-0.txt");
            System.out.println("Root content: " + content);
        } catch (IOException ex) {
            System.err.println("Root content: failure");
        }
    }

    public static void printExportedResource() {
        try {
            String content = ResourceReader.loadAsString("/com/example/resource_module/libd/resource-1.txt");
            System.out.println("Exported content: " + content);
        } catch (IOException ex) {
            System.err.println("Exported content: failure");
        }
    }

    public static void printInternalResource() {
        try {
            String content = ResourceReader.loadAsString("/com/example/resource_module/libd/internal/resource-2.txt");
            System.out.println("Internal content: " + content);
        } catch (IOException ex) {
            System.err.println("Internal content: failure");
        }
    }

    public static void printOpenedResource() {
        try {
            String content = ResourceReader.loadAsString("/com/example/resource_module/libd/opened/resource-3.txt");
            System.out.println("Opened content: " + content);
        } catch (IOException ex) {
            System.err.println("Opened content: failure");
        }
    }
    public static void printHackedRootResource() {
        try {
            String content = ResourceReader.loadAsStringInternal(Sample.ROOT_RESOURCE);
            System.out.println("Hacked root content: " + content);
        } catch (Exception ex) {
            System.err.println("Hacked root content: failure");
            ex.printStackTrace();
        }
    }

    public static void printHackedExportedResource() {
        try {
            String content = ResourceReader.loadAsStringInternal(Sample.EXPORTED_RESOURCE);
            System.out.println("Hacked exported content: " + content);
        } catch (Exception ex) {
            System.err.println("Hacked exported content: failure");
            ex.printStackTrace();
        }
    }

    public static void printHackedInternalResource() {
        try {
            String content = ResourceReader.loadAsStringInternal(Sample.INTERNAL_RESOURCE);
            System.out.println("Hacked internal content: " + content);
        } catch (Exception ex) {
            System.err.println("Hacked internal content: failure");
            ex.printStackTrace();
        }
    }

    public static void printHackedOpenedResource() {
        try {
            String content = ResourceReader.loadAsStringInternal(Sample.OPENED_RESOURCE);
            System.out.println("Hacked opened content: " + content);
        } catch (Exception ex) {
            System.err.println("Hacked opened content: failure");
            ex.printStackTrace();
        }
    }

}
