package vn.edu.nlu.web.chat.config.cloud.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

/**
 * This interface defines the contract for interacting with a storage provider.
 */
public interface StorageProvider {


    /**
     * Saves the given file to the storage provider.
     *
     * @param file The file to be saved.
     * @return A unique identifier (e.g., file ID or URL) representing the stored file.
     */
    String saveFile(MultipartFile file);

    /**
     * Retrieves the content of the file identified by the given fileId from the storage provider.
     *
     * @param fileId The unique identifier of the file to be retrieved.
     * @return An InputStream providing access to the content of the file.
     */
    InputStream getFile(String fileId);

    /**
     * Deletes the file identified by the given fileId from the storage provider.
     *
     * @param fileId The unique identifier of the file to be deleted.
     */
    void deleteFile(String fileId);

    /**
     * Updates the content of the file identified by the given fileId with the provided file.
     *
     * @param fileId The unique identifier of the file to be updated.
     * @param file   The new content of the file.
     */
    void updateFile(String fileId, MultipartFile file);

    /**
     * Retrieves the metadata associated with the file identified by the given fileId.
     *
     * @param fileId The unique identifier of the file.
     * @return A map containing metadata key-value pairs.
     */
    Map<String, String> getFileMetadata(String fileId);

    /**
     * Sets or updates the metadata associated with the file identified by the given fileId.
     *
     * @param fileId   The unique identifier of the file.
     * @param metadata A map containing metadata key-value pairs.
     */
    void setFileMetadata(String fileId, Map<String, String> metadata);

    /**
     * Deletes the metadata associated with the file identified by the given fileId.
     *
     * @param fileId The unique identifier of the file.
     */
    void deleteFileMetadata(String fileId);
}
