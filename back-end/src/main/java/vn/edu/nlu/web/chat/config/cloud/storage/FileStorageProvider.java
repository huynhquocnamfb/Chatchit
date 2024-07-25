package vn.edu.nlu.web.chat.config.cloud.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageProvider {
    String uploadFile(MultipartFile file);

    byte[] downloadFile(String fileName);

    void deleteFile(String fileName);

}
