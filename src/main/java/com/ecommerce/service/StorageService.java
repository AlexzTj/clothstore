package com.ecommerce.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class);
    private final Path rootDir;

    public StorageService(@Value("#{servletContext.getRealPath('/uploads/')}") String path){
        rootDir = Paths.get(path);
    }
    public void store(String fileName, MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), rootDir.resolve(fileName));
    }

}