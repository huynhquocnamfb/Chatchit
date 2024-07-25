package vn.edu.nlu.web.chat.dto.file.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class FileUploadRequest {
    private MultipartFile file;
}
