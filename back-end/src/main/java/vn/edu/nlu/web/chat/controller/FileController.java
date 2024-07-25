package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.dto.file.request.FileUploadRequest;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.service.FileService;


@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/files")
@Slf4j
@Tag(name = "File Controller", description = "Endpoints for managing files")
public class FileController {
    private final FileService fileService;


    @Operation(summary = "Upload File", description = "API to upload a file.")
    @PostMapping
    public ApiResponse<?> upload(@RequestParam(name = "file") MultipartFile file) {
        var request = new FileUploadRequest(file);
        log.info("Request to upload file: {}", request);
        fileService.upload(request);
        log.info("Upload file success: {}", request.getFile().getName());
        return new ApiResponse<>(HttpStatus.CREATED, Translator.toLocale("file.upload.success"));
    }

    @Operation(summary = "Download File", description = "API to download a file by ID.")
    @GetMapping(path = "/{id}/download")
    public ResponseEntity<?> download(@PathVariable Long id) {
        log.info("Request to download file with ID: {}", id);
        var response = fileService.download(id);
        log.info("Download file success for ID: {}", id);
        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", response.getFileName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ByteArrayResource(response.getData()));
    }

    @Operation(summary = "Delete File", description = "API to delete a file.")
    @DeleteMapping(path = "/{id}")
    public ApiResponse<?> delete(@PathVariable(name = "id") Long id) {
        log.info("Request to delete file: {}", id);
        var isDelete = fileService.delete(id);
        log.info("File {} deleted successfully", id);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("file.delete.success"), isDelete);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get File by ID", description = "Retrieve details of a file by ID")
    public ResponseEntity<?> getDetails(@PathVariable(name = "id") Long id) {
        try {
            log.info("Request to retrieve file: {}", id);
            var response = fileService.getDetails(id);
            log.info("File {} retrieved successfully", id);
            return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("file.get.success"), response);
        } catch (ResourceNotFoundException e) {
            // Log the error and return a 404 response
            log.error("File with ID {} not found", id);
            return new ApiResponse<>(HttpStatus.NOT_FOUND, Translator.toLocale("file.get.fail"));
        } catch (Exception e) {
            // Log the error and return a 500 response
            log.error("Error retrieving file with ID {}", id, e);
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("file.get.fail"));
        }
    }

    @GetMapping(path = "/search")
    @Operation(summary = "Get List of Files", description = "Retrieve a list of all files")
    public ApiResponse<?> getList(Pageable pageable, String fileName) {
        try {
            log.info("Request to retrieve list of files");
            var pageResponse = fileService.search(pageable, fileName);
            log.info("List of files retrieved successfully");
            return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("file.list.success"), pageResponse);
        } catch (Exception e) {
            // Log the error and return a 500 response
            log.error("Error retrieving list of files", e);
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("file.list.fail"));
        }
    }

}