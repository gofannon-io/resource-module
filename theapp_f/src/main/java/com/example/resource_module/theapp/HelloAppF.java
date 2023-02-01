package com.example.resource_module.theapp;

import com.example.resource_module.lib_b.ExportedSample;
import com.example.resource_module.lib_b.shared.SharedSample;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.example.resource_module.lib_b.shared.SharedSample.SHARED_RESOURCE;


public class HelloAppF {
    public static void main(String[] args) throws IOException {

        URL manifestURL = HelloAppF.class.getResource("/META-INF/MANIFEST.MF");
        System.out.println("Manifest URL: "+manifestURL.toExternalForm());
        String manifest = loadFromUrl(manifestURL);
        System.out.println("manifest: " + manifest);


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
        System.out.println("ExportedSample Classloader URL: " + ExportedSample.class.getClassLoader().getResource("/com/example/resource_module/lib_b/shared/resource-7.txt"));

        File basedir = new File(System.getProperty("user.dir")).getParentFile();
        String sharedUrlAsString = getJarFileResource("/lib_b/build/libs/lib_b-1.0.jar!/com/example/resource_module/lib_b/shared/resource-7.txt");
        URL sharedUrl = new URL(sharedUrlAsString);
        System.out.println("Shared URL " + sharedUrl);

        String internalUrlAsString = getJarFileResource("/lib_b/build/libs/lib_b-1.0.jar!/com/example/resource_module/lib_b/internal/resource-2.txt");
        URL internalUrl = new URL(internalUrlAsString);
        System.out.println("Internal url " + internalUrl);


        try (InputStream in = internalUrl.openStream()) {
            String internalUrlContent = IOUtils.toString(in, StandardCharsets.UTF_8);
            System.out.println("internal URL inputStream content is : " + internalUrlContent);
        } catch (Exception ex) {
            System.out.println("internal URL inputStream failed");
            ex.printStackTrace(System.out);
        }

        File file = new File(internalUrlAsString);
        String content2 = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        System.out.println("Content 2 = "+content2);

//        boolean loaded;
//        String content2=null;
//        try (InputStream in = sharedSample.getResourceAsStream()) {
//            loaded = true;
//            content2 = IOUtils.toString(in, StandardCharsets.UTF_8);
//        } catch (Exception ex) {
//            loaded = false;
//        }
//        System.out.println("Shared loaded: " + loaded);
//        System.out.println("Shared content loaded: " + content2);
//
//
//        boolean sameClassLoader = ExportedSample.class.getClassLoader() == sharedSample.getClassLoader();
//        System.out.println("Shared classloader: " + sameClassLoader);
//
//        //ClassLoader classLoader = sharedSample.getClassLoader();
//        ClassLoader classLoader = ExportedSample.class.getClassLoader();
//
//        checkResource("Root package", "/resource-0.txt", classLoader);
//        checkResource("Exported package", "/com/example/resource_module/lib_b/resource-1.txt", classLoader);
//        checkResource("Internal package", "/com/example/resource_module/lib_b/internal/resource-2.txt", classLoader);
//        checkResource("Opened package", "/com/example/resource_module/lib_b/opened/resource-3.txt", classLoader);
//
//        checkResource("Exported directory", "/com/example/resource_module/lib_b/dir/resource-4.txt", classLoader);
//        checkResource("Internal directory", "/com/example/resource_module/lib_b/dir/internal/resource-5.txt", classLoader);
//        checkResource("Opened directory", "/com/example/resource_module/lib_b/dir/opened/resource-6.txt", classLoader);
//
//        checkResource("Shared directory", SHARED_RESOURCE, classLoader);
    }

    private static String getJarFileResource(String suffix) {
        File basedir = new File(System.getProperty("user.dir")).getParentFile();
        return "jar:file:///" + basedir.getAbsolutePath().replace('\\', '/') + suffix;
    }


    private static void checkResource(String label, String resourcePath, ClassLoader classLoader) {
        System.out.println(label + " path : " + resourcePath);

        URL url = ExportedSample.class.getResource(resourcePath);
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


    private static String loadFromUrl(URL url) throws IOException {
        try (InputStream in = url.openStream()) {
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        }
    }

}