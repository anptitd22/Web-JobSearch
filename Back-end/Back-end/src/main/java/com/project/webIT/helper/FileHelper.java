package com.project.webIT.helper;

import org.springframework.web.multipart.MultipartFile;

public class FileHelper {
    public static boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.endsWith("/jpeg") || contentType.endsWith("/png") || contentType.endsWith("/webp"));
}

public static boolean isValidDocument(MultipartFile file) {
    String contentType = file.getContentType();
    return contentType != null && (contentType.endsWith(("/pdf")) ||
            contentType.endsWith("/doc") || // .doc
            contentType.endsWith("/docx"));
}
}
