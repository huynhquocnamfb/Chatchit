package vn.edu.nlu.web.chat.dto.file.response;

import lombok.Builder;
import lombok.Getter;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;

@Builder
@Getter
public class FileDownloadResponse extends EntityResponse {
    private byte[] data;
    private String fileName;

}
