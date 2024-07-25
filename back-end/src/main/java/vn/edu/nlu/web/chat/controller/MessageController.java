package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.message.request.MessageCreateRequest;
import vn.edu.nlu.web.chat.dto.message.request.MessageUpdateRequest;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.service.MessageService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
@Tag(name = "Message Controller")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Add new Message", description = "API to add a new message.")
    @PostMapping(path = "/chats/{chatId}/messages")
    public ApiResponse<?> create(@PathVariable(name = "chatId") Long chatId,
                                 @RequestBody @Valid MessageCreateRequest request) {

        log.info("Request to add a new Message: {}", request);
        var res = messageService.create(chatId, request);
        log.info("Message successfully created with ID: {}", res.getId());
        return new ApiResponse<>(HttpStatus.CREATED, Translator.toLocale("message.add.success"), res);
    }

    @Operation(summary = "Update Message", description = "API to update an existing message.")
    @PutMapping(path = "/messages/{id}")
    public ApiResponse<?> update(@PathVariable("id") Long id,
                                 @RequestBody @Valid MessageUpdateRequest request) {
        log.info("Request to update Message with ID {}: {}", id, request);
        var res = messageService.update(id, request);
        log.info("Message with ID {} successfully updated", id);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("message.update.success"), res);
    }

    @Operation(summary = "Search Messages with specific chat id ", description = "API to search for messages based on criteria.")
    @GetMapping(path = "/chats/{chatId}/messages")
    public ApiResponse<?> searchAllMessageDetails(@PathVariable(name = "chatId") Long chatId,
                                                  @Min(0) @RequestParam(defaultValue = "0", required = false) int pageNo,
                                                  @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                                  @RequestParam(required = false) String sortBy,
                                                  @RequestParam(required = false) String query) {

        var res = messageService.searchMessagesWithPaginationAndSorting(chatId, pageNo, pageSize, query, sortBy);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("message.search.success"), res);
    }

    @Operation(summary = "Delete Message", description = "API to delete a message by ID.")
    @DeleteMapping(path = "/messages/{id}")
    public ApiResponse<?> delete(@PathVariable(name = "id") Long id) {
        log.info("Request to delete Message with ID: {}", id);
        messageService.delete(id);
        log.info("Message with ID {} successfully deleted", id);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("message.delete.success"));
    }

    @Operation(summary = "Get Message by ID", description = "API to get a message by ID.")
    @GetMapping("/messages/{id}")
    public ApiResponse<?> getDetailsById(@PathVariable("id") Long id) {
        log.info("Request to get Message with ID: {}", id);
        var response = messageService.getDetails(id);
        log.info("Message with ID {} found: {}", id, response);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("message.get.success"), response);
    }
}