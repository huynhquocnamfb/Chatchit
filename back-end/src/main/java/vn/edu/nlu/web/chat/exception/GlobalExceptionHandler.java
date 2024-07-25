package vn.edu.nlu.web.chat.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.edu.nlu.web.chat.config.locale.Translator;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the exception when a bad request occurs.
     *
     * @param ex      The ApiRequestException that was thrown.
     * @param request The WebRequest representing the HTTP request.
     * @return An ErrorResponse object containing details about the error.
     */
    @ExceptionHandler(ApiRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "400 Response",
                                    summary = "Handle exception when a bad request occurs",
                                    value = """
                                            {
                                              "timestamp": "2023-10-19T06:07:35.321+00:00",
                                              "status": 400,
                                              "path": "/api/v1/...",
                                              "error": "Bad Request",
                                              "message": "Invalid input data"
                                            }
                                            """
                            ))})
    })
    public ErrorResponse handleBadRequestException(ApiRequestException ex, WebRequest request) {
        return ErrorResponse.builder()
                .timestamp(new Date())
                .path(request.getDescription(false).replace("uri=", ""))
                .status(BAD_REQUEST.value())
                .error(BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }


    /**
     * Handles the exception when the requested resource is not found.
     *
     * @param e       The ResourceNotFoundException that was thrown.
     * @param request The WebRequest representing the HTTP request.
     * @return An ErrorResponse object containing details about the error.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Bad Request",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "404 Response",
                                    summary = "Handle exception when resource not found",
                                    value = """
                                            {
                                              "timestamp": "2023-10-19T06:07:35.321+00:00",
                                              "status": 404,
                                              "path": "/api/v1/...",
                                              "error": "Not Found",
                                              "message": "{data} not found"
                                            }
                                            """
                            ))})
    })
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        return ErrorResponse.builder()
                .timestamp(new Date())
                .path(request.getDescription(false).replace("uri=", ""))
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }


    /**
     * Handle exception when validate data
     *
     * @param e
     * @param request
     * @return errorResponse
     */
    @ExceptionHandler({ConstraintViolationException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class})
    @ResponseStatus(BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Handle exception when the data invalid. (@RequestBody, @RequestParam, @PathVariable)",
                                    summary = "Handle Bad Request",
                                    value = """
                                            {
                                                 "timestamp": "2024-04-07T11:38:56.368+00:00",
                                                 "status": 400,
                                                 "path": "/api/v1/...",
                                                 "error": "Invalid Payload",
                                                 "message": "{data} must be not blank"
                                             }
                                            """
                            ))})
    })
    public ErrorResponse handleValidationException(Exception e, WebRequest request) {
        String errorType = determineErrorType(e);
        String errorMessage = determineErrorMessage(e);
        log.error("Validation Exception occurred: {}", e.getMessage()); // Log message the exception
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(BAD_REQUEST.value()) // error 400
                .path(request.getDescription(false).replace("uri=", ""))
                .error(errorType)
                .message(errorMessage)
                .build();
    }

    private String determineErrorType(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            return Translator.toLocale("error.invalid.payload");
        } else if (e instanceof MissingServletRequestParameterException || e instanceof ConstraintViolationException) {
            return Translator.toLocale("error.invalid.parameter");
        } else {
            return "error.invalid.data";
        }
    }

    private String determineErrorMessage(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            return extractErrorMessage((MethodArgumentNotValidException) e);
        } else if (e instanceof HttpMessageNotReadableException) {
            return e.getClass().getSimpleName();
        } else if (e instanceof ConstraintViolationException || e instanceof MissingServletRequestParameterException) {
            return e.getMessage();
        } else {
            return "TO-DO Handle exception";
        }
    }

    private String extractErrorMessage(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof org.springframework.validation.FieldError) {
                org.springframework.validation.FieldError fieldError = (org.springframework.validation.FieldError) error;
                errorMessage.append("Field '").append(fieldError.getField()).append("': ").append(fieldError.getDefaultMessage()).append("; ");
            } else {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            }
        });
        return errorMessage.toString();
    }

}
