package com.example.resource_module.theapp;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResourceReader {

    public static String loadAsString(String resourcePath) throws IOException {
        return IOUtils.resourceToString(resourcePath,StandardCharsets.UTF_8);
    }
}