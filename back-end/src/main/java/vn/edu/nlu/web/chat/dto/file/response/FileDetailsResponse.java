package vn.edu.nlu.web.chat.dto.file.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import vn.edu.nlu.web.chat.dto.common.response.EntityResponse;
import vn.edu.nlu.web.chat.utils.FileUtils;

@Setter
@Getter
public class FileDetailsResponse extends EntityResponse {
    private String name;

    private String path;

    @JsonProperty(namespace = "size")
    private String formattedSize;

    private String extension;

    private boolean isDirectory;

    public void setFormattedSize(long size) {
        this.formattedSize = FileUtils.getFileSizeFormatted(size);
    }
    public void setFormattedSize(String formattedSize) {
        this.formattedSize = formattedSize;
    }

}
