package com.example.resource_module.theapp;

import com.example.resource_module.lib_b.ExportedSample;
import com.example.resource_module.lib_b.shared.SharedSample;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.example.resource_module.lib_b.shared.SharedSample.SHARED_RESOURCE;

public class HelloAppF {
    public static void main(String[] args) throws IOException {
        SharedSample sharedSample = new SharedSample();
        System.out.println("Shared Instance URL: " + sharedSample.getResourceURL());
        boolean urlLoaded;
        try (InputStream ignored = sharedSample.getResourceURL().openStream()) {
            urlLoaded = true;
        } catch (Exception ex) {
            urlLoaded = false;
        }
        System.out.println("Shared URL loaded: " + urlLoaded);

        String content = IOUtils.toString(sharedSample.getResourceURL(), StandardCharsets.UTF_8);
        System.out.println("Shared URL content: " + content);


        System.out.println("Shared Class URL: " + SharedSample.class.getResource(SHARED_RESOURCE));
        System.out.println("ExportedSample Class URL: " + ExportedSample.class.getResource("/com/example/resource_module/lib_b/shared/resource-7.txt"));

        boolean loaded;
        String content2=null;
        try (InputStream in = sharedSample.getResourceAsStream()) {
            loaded = true;
            content2 = IOUtils.toString(in, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            loaded = false;
        }
        System.out.println("Shared loaded: " + loaded);
        System.out.println("Shared content loaded: " + content2);


        boolean sameClassLoader = ExportedSample.class.getClassLoader() == sharedSample.getClassLoader();
        System.out.println("Shared classloader: " + sameClassLoader);

        ClassLoader classLoader = sharedSample.getClassLoader();

        checkResource("Root package", "/resource-0.txt", classLoader);
        checkResource("Exported package", "/com/example/resource_module/lib_b/resource-1.txt", classLoader);
        checkResource("Internal package", "/com/example/resource_module/lib_b/internal/resource-2.txt", classLoader);
        checkResource("Opened package", "/com/example/resource_module/lib_b/opened/resource-3.txt", classLoader);

        checkResource("Exported directory", "/com/example/resource_module/lib_b/dir/resource-4.txt", classLoader);
        checkResource("Internal directory", "/com/example/resource_module/lib_b/dir/internal/resource-5.txt", classLoader);
        checkResource("Opened directory", "/com/example/resource_module/lib_b/dir/opened/resource-6.txt", classLoader);

        checkResource("Shared directory", SHARED_RESOURCE, classLoader);
    }


    private static void checkResource(String label, String resourcePath, ClassLoader classLoader) {
        System.out.println(label + " path : " + resourcePath);

        URL url = classLoader.getResource(resourcePath);
        System.out.println(label + " url : " + url);

        //try(InputStream in = classLoader.getResourceAsStream(resourcePath)) {
        try (InputStream in = url.openStream()) {
            //String content = IOUtils.resourceToString(resourcePath, StandardCharsets.UTF_8, classLoader);
            String content = IOUtils.toString(in, StandardCharsets.UTF_8);
            System.out.println(label + " content: " + content);
        } catch (Exception ex) {
            System.out.println(label + " content: FAILURE (" + ex.getClass().getName() + ")");
        }
    }

}