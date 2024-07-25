package vn.edu.nlu.web.chat.dto.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * Represents a successful response with an HTTP status code, a message, and optional data.
 * Extends ResponseEntity to provide compatibility with Spring MVC.
 */
public class ApiResponse<T> extends ResponseEntity<ApiResponse.Payload<T>> {


    public ApiResponse(HttpStatus status, String message, HttpHeaders headers, T data) {
        super(new Payload(status.value(), message), headers, HttpStatus.OK);
    }

    /**
     * Constructs a ResponseSuccess object with the given HTTP status and message.
     *
     * @param status  The HTTP status code indicating the success. Must not be null.
     * @param message A descriptive message explaining the success. Must not be null.
     */
    public ApiResponse(HttpStatus status, String message) {
        super(new Payload(status.value(), message), HttpStatus.OK);
    }

    /**
     * Constructs a ResponseSuccess object with the given HTTP status, message, and data.
     *
     * @param status  The HTTP status code indicating the success. Must not be null.
     * @param message A descriptive message explaining the success. Must not be null.
     * @param data    Optional data to be included in the response. Can be null.
     */
    public ApiResponse(HttpStatus status, String message, T data) {
        super(new Payload(status.value(), message, data), status);
    }

    /**
     * Constructs a ResponseSuccess object with the given payload and HTTP status.
     *
     * @param body   The payload containing status, message, and optional data.
     * @param status The HTTP status code indicating the success. Must not be null.
     */
    public ApiResponse(Payload body, HttpStatus status) {
        super(body, status);
    }

    /**
     * Constructs a ResponseSuccess object with the given headers and HTTP status.
     *
     * @param headers The headers to be included in the response.
     * @param status  The HTTP status code indicating the success. Must not be null.
     */
    public ApiResponse(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    /**
     * Constructs a ResponseSuccess object with the given payload, headers, and raw HTTP status.
     *
     * @param payload   The payload containing status, message, and optional data.
     * @param headers   The headers to be included in the response.
     * @param rawStatus The raw HTTP status code indicating the success.
     */
    public ApiResponse(Payload payload, MultiValueMap<String, String> headers, int rawStatus) {
        super(payload, headers, rawStatus);
    }

    /**
     * Constructs a ResponseSuccess object with the given payload, headers, and HTTP status.
     *
     * @param payload The payload containing status, message, and optional data.
     * @param headers The headers to be included in the response.
     * @param status  The HTTP status code indicating the success. Must not be null.
     */
    public ApiResponse(Payload payload, MultiValueMap<String, String> headers, HttpStatus status) {
        super(payload, headers, status);
    }

    /**
     * Represents the payload of a successful response.
     */
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Payload<T> {
        private final int status;
        private final String message;
        @JsonInclude(JsonInclude.Include.NON_NULL) // show if it isn't null value
        private T data;


    }
}