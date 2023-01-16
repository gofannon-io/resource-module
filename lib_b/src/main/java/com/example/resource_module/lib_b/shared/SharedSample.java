package com.example.resource_module.lib_b.shared;

import java.io.InputStream;
import java.net.URL;

public class SharedSample {

    public static final String SHARED_RESOURCE = "/com/example/resource_module/lib_b/shared/resource-7.txt";

    public URL getResourceURL() {
        return SharedSample.class.getResource(SHARED_RESOURCE);
    }

    public InputStream getResourceAsStream() {
        return SharedSample.class.getResourceAsStream(SHARED_RESOURCE);
    }

    public ClassLoader getClassLoader() {
        return SharedSample.class.getClassLoader();
    }
}
