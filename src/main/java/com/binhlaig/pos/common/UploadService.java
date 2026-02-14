package com.binhlaig.pos.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class UploadService {

    private final Path rootDir;

    public UploadService(@Value("${app.upload.dir:./uploads}") String dir) {
        this.rootDir = Paths.get(dir).toAbsolutePath().normalize();
    }

    public String saveAvatar(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "avatar" : file.getOriginalFilename());
        String ext = "";

        int dot = original.lastIndexOf('.');
        if (dot >= 0) ext = original.substring(dot);

        Path avatarDir = rootDir.resolve("avatars");
        Files.createDirectories(avatarDir);

        String filename = UUID.randomUUID() + ext;
        Path target = avatarDir.resolve(filename);

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        // URL path (we will map /uploads/**)
        return "/uploads/avatars/" + filename;
    }
}
