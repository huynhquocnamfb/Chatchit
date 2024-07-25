package vn.edu.nlu.web.chat.dto.details;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDTO {
    private BasicDetailsDTO basicDetails;
    private String status;

}