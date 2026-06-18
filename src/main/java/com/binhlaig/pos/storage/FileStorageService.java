//
//package com.binhlaig.pos.storage;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.UUID;
//
//@Service
//public class FileStorageService {
//
//    private final Path rootDir;
//
//    public FileStorageService(@Value("${app.upload-dir:uploads}") String uploadDir) {
//        this.rootDir = Paths.get(uploadDir).toAbsolutePath().normalize();
//    }
//
//    public String saveProductImage(MultipartFile file) throws IOException {
//        return save(file, "products");
//    }
//
//    public String saveAvatarImage(MultipartFile file) throws IOException {
//        return save(file, "avatars");
//    }
//
//    private String save(MultipartFile file, String folder) throws IOException {
//        if (file == null || file.isEmpty()) return null;
//
//        Path targetDir = rootDir.resolve(folder);
//        Files.createDirectories(targetDir);
//
//        String original = StringUtils.cleanPath(
//                file.getOriginalFilename() == null ? "image" : file.getOriginalFilename()
//        );
//
//        String ext = "";
//        int dot = original.lastIndexOf('.');
//        if (dot >= 0) {
//            ext = original.substring(dot);
//        }
//
//        String filename = UUID.randomUUID() + ext;
//        Path target = targetDir.resolve(filename);
//
//        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
//
//        return "/uploads/" + folder + "/" + filename;
//    }
//}


























package com.binhlaig.pos.storage;

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
public class FileStorageService {

    private static final Set<String> ALLOWED_IMAGE_EXTENSIONS =
            Set.of(".jpg", ".jpeg", ".png", ".webp");
    private static final Map<String, String> CONTENT_TYPE_EXTENSIONS = Map.of(
            "image/jpeg", ".jpg",
            "image/png", ".png",
            "image/webp", ".webp"
    );
    private static final long MAX_FILE_SIZE_BYTES = 5L * 1024L * 1024L;

    private final Path rootDir;

    public FileStorageService(@Value("${app.upload.dir:./uploads}") String uploadDir) {
        this.rootDir = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public String saveProductImage(MultipartFile file) throws IOException {
        return save(file, "products");
    }

    public String saveAvatarImage(MultipartFile file) throws IOException {
        return save(file, "avatars");
    }

    public String saveStaffImage(MultipartFile file) throws IOException {
        return save(file, "staff");
    }

    private String save(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        Path targetDir = rootDir.resolve(folder);
        Files.createDirectories(targetDir);

        String original = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
        String ext = validateImage(file, original);

        String filename = UUID.randomUUID() + ext;
        Path target = targetDir.resolve(filename);

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + folder + "/" + filename;
    }

    public boolean deleteFile(String fileUrl) throws IOException {
        if (fileUrl == null || fileUrl.isBlank()) {
            return false;
        }

        String normalized = fileUrl.replace("\\", "/");

        if (!normalized.startsWith("/uploads/")) {
            return false;
        }

        String relativePath = normalized.substring("/uploads/".length());
        Path target = rootDir.resolve(relativePath).normalize();

        if (!target.startsWith(rootDir)) {
            return false;
        }

        return Files.deleteIfExists(target);
    }

    private String getExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        if (dot < 0) {
            return "";
        }
        return filename.substring(dot).toLowerCase();
    }

    private String validateImage(MultipartFile file, String originalFilename) throws IOException {
        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new IllegalArgumentException("Image file is too large");
        }

        String contentType = file.getContentType() == null ? "" : file.getContentType().toLowerCase(Locale.ROOT);
        String expectedExt = CONTENT_TYPE_EXTENSIONS.get(contentType);
        if (expectedExt == null) {
            throw new IllegalArgumentException("Only image/jpeg, image/png, and image/webp files are allowed");
        }

        String originalExt = getExtension(originalFilename);
        validateImageExtension(originalExt);
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

    private void validateImageExtension(String ext) {
        if (ext.isBlank() || !ALLOWED_IMAGE_EXTENSIONS.contains(ext)) {
            throw new IllegalArgumentException("Only image files are allowed: jpg, jpeg, png, webp");
        }
    }
}
