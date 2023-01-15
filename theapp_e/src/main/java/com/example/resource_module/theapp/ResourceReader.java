package com.example.resource_module.theapp;

import com.example.resource_module.lib_b.Sample;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ResourceReader {
    public static String loadAsString(String resourcePath) throws IOException {
        try (InputStream in = Sample.class.getResourceAsStream(resourcePath)) {
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        }
    }

}
