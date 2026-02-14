package com.binhlaig.pos.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path rootDir;

    public FileStorageService(@Value("${app.upload-dir:uploads}") String uploadDir) {
        this.rootDir = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public String saveProductImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        Files.createDirectories(rootDir.resolve("products"));

        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "image" : file.getOriginalFilename());
        String ext = "";

        int dot = original.lastIndexOf('.');
        if (dot >= 0) ext = original.substring(dot);

        String filename = UUID.randomUUID() + ext;
        Path target = rootDir.resolve("products").resolve(filename);

        // overwrite not likely, but safe
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        // URL path (served by Spring static handler)
        return "/uploads/products/" + filename;
    }
}
