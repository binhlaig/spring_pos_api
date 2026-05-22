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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Set<String> ALLOWED_IMAGE_EXTENSIONS =
            Set.of(".jpg", ".jpeg", ".png", ".gif", ".webp");

    private final Path rootDir;

    public FileStorageService(@Value("${app.upload-dir:uploads}") String uploadDir) {
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

        String original = StringUtils.cleanPath(
                file.getOriginalFilename() == null ? "image" : file.getOriginalFilename()
        );

        String ext = getExtension(original);
        validateImageExtension(ext);

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

    private void validateImageExtension(String ext) {
        if (ext.isBlank() || !ALLOWED_IMAGE_EXTENSIONS.contains(ext)) {
            throw new IllegalArgumentException("Only image files are allowed: jpg, jpeg, png, gif, webp");
        }
    }
}