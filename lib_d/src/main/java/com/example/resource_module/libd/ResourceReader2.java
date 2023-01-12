package com.example.resource_module.libd;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResourceReader2 {

    public static String loadAsString(String resourcePath) throws IOException {
        try (InputStream in = ResourceReader2.class.getResourceAsStream(resourcePath);
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
