package vn.edu.nlu.web.chat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.file.request.FileUpdateRequest;
import vn.edu.nlu.web.chat.dto.file.request.FileUploadRequest;
import vn.edu.nlu.web.chat.dto.file.response.FileDetailsResponse;
import vn.edu.nlu.web.chat.dto.file.response.FileDownloadResponse;

import java.io.FileNotFoundException;

public interface FileService {

    /**
     * Uploads a file.
     *
     * @param request The request containing file details.
     */
    void upload(FileUploadRequest request);

    /**
     * Downloads a file by its ID.
     *
     * @param id The ID of the file to download.
     * @return The downloaded file as a byte array.
     */
    FileDownloadResponse download(Long id);

    /**
     * Deletes a file by its ID.
     *
     * @param id The ID of the file to delete.
     * @return true if the file is successfully deleted, false otherwise.
     */
    boolean delete(Long id);

    /**
     * Updates a file.
     * @param id The ID of the file to delete.
     * @param request The request containing updated file details.
     * @throws FileNotFoundException if the file with the specified ID is not found.
     */
    void update(Long id, FileUpdateRequest request) throws FileNotFoundException;

    /**
     * Retrieves details of a file by its ID.
     *
     * @param id The ID of the file to retrieve details for.
     * @return Details of the requested file.
     * @throws FileNotFoundException if the file with the specified ID is not found.
     */
    FileDetailsResponse getDetails(Long id) throws FileNotFoundException;

    /**
     * Searches for files based on the provided criteria.
     *
     * @param pageable Pagination information.
     * @param fileName The name of the file to search for.
     * @return A page containing the search results.
     */
    PageResponse<?> search(Pageable pageable, String fileName);
}
