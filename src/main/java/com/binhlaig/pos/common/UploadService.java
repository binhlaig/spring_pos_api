package com.binhlaig.pos.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class UploadService {

    private static final long MAX_FILE_SIZE_BYTES = 5L * 1024L * 1024L;
    private static final Set<String> ALLOWED_IMAGE_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".webp");
    private static final Map<String, String> CONTENT_TYPE_EXTENSIONS = Map.of(
            "image/jpeg", ".jpg",
            "image/png", ".png",
            "image/webp", ".webp"
    );

    private final Path rootDir;

    public UploadService(@Value("${app.upload.dir:./uploads}") String dir) {
        this.rootDir = Paths.get(dir).toAbsolutePath().normalize();
    }

    public String saveAvatar(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String ext = validateImage(file);

        Path avatarDir = rootDir.resolve("avatars");
        Files.createDirectories(avatarDir);

        String filename = UUID.randomUUID() + ext;
        Path target = avatarDir.resolve(filename);

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        // URL path (we will map /uploads/**)
        return "/uploads/avatars/" + filename;
    }

    private String validateImage(MultipartFile file) throws IOException {
        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new IllegalArgumentException("Image file is too large");
        }

        String contentType = file.getContentType() == null ? "" : file.getContentType().toLowerCase(Locale.ROOT);
        String expectedExt = CONTENT_TYPE_EXTENSIONS.get(contentType);
        if (expectedExt == null) {
            throw new IllegalArgumentException("Only image/jpeg, image/png, and image/webp files are allowed");
        }

        String original = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
        String originalExt = getExtension(original);
        if (!ALLOWED_IMAGE_EXTENSIONS.contains(originalExt)) {
            throw new IllegalArgumentException("Only image files are allowed: jpg, jpeg, png, webp");
        }
        if ("image/jpeg".equals(contentType) && !Set.of(".jpg", ".jpeg").contains(originalExt)) {
            throw new IllegalArgumentException("Image extension does not match content type");
        }
        if (!"image/jpeg".equals(contentType) && !expectedExt.equals(originalExt)) {
            throw new IllegalArgumentException("Image extension does not match content type");
        }

        validateImageBytes(file, contentType);

        return expectedExt;
    }

    private void validateImageBytes(MultipartFile file, String contentType) throws IOException {
        if ("image/webp".equals(contentType)) {
            byte[] header = new byte[12];
            try (InputStream in = file.getInputStream()) {
                if (in.read(header) < header.length
                        || header[0] != 'R' || header[1] != 'I' || header[2] != 'F' || header[3] != 'F'
                        || header[8] != 'W' || header[9] != 'E' || header[10] != 'B' || header[11] != 'P') {
                    throw new IllegalArgumentException("Invalid image file");
                }
            }
            return;
        }

        BufferedImage decoded = ImageIO.read(file.getInputStream());
        if (decoded == null || decoded.getWidth() <= 0 || decoded.getHeight() <= 0) {
            throw new IllegalArgumentException("Invalid image file");
        }
    }

    private String getExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        return dot < 0 ? "" : filename.substring(dot).toLowerCase(Locale.ROOT);
    }
}
