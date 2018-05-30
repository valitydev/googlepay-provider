package com.rbkmoney.provider.googlepay.service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by vpankrashkin on 29.05.18.
 */
public class GPKeyStore {
    private final URI path;

    public GPKeyStore(URI path) {
        this.path = path;
    }

    public List<String> getKeys() throws IOException {
        return Files.readAllLines(Paths.get(path));
    }
}
