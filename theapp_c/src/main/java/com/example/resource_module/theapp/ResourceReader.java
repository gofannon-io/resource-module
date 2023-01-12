package com.example.resource_module.theapp;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResourceReader {

    public static String loadAsString(String resourcePath) throws IOException {
        return IOUtils.resourceToString(resourcePath,StandardCharsets.UTF_8);
    }

    public static String loadAsStringInternal(String resourcePath) throws IOException {
        System.out.println("loadAsStringInternal("+resourcePath+")");
        try (InputStream in = ResourceReader.class.getResourceAsStream(resourcePath);
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)
        ) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[100];
            int size;
            while ((size = reader.read(buffer)) >= 0) {
                stringBuilder.append(buffer, 0, size);
            }
            return stringBuilder.toString();
        }
    }

}
