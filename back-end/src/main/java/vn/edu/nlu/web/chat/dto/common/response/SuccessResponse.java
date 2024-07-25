package vn.edu.nlu.web.chat.dto.common.response;


import org.springframework.http.HttpStatus;

/**
 * Represents a failure response with an HTTP status code and a message.
 * Inherits from ResponseSuccess, as failure responses can use the same structure as success responses.
 */
public class SuccessResponse extends ApiResponse {

    /**
     * Constructs a ResponseFailure object with the given HTTP status and message.
     *
     * @param status  The HTTP status code indicating the failure. Must not be null.
     *                Examples: {@link org.springframework.http.HttpStatus#BAD_REQUEST},
     *                {@link org.springframework.http.HttpStatus#NOT_FOUND},
     *                {@link org.springframework.http.HttpStatus#FORBIDDEN}.
     * @param message A descriptive message explaining the failure. Must not be null or empty.
     */
    public SuccessResponse(HttpStatus status, String message) {
        super(status, message);
    }

    public SuccessResponse(String message) {
        super(HttpStatus.OK, message);
    }
}