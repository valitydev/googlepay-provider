package com.rbkmoney.provider.googlepay.service;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
public class GPKeyStore {

    private final URI path;

    public List<String> getKeys() throws IOException {
        return Files.readAllLines(Paths.get(path));
    }
}
