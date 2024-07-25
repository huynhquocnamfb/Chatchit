package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.dto.chat.request.ChatCreateRequest;
import vn.edu.nlu.web.chat.dto.chat.request.ChatUpdateRequest;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.service.ChatService;
import vn.edu.nlu.web.chat.service.MessageService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/chats")
@Tag(name = "Chat Controller", description = "Endpoints for managing chats")
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;


    @Operation(summary = "Create Chat", description = "API to create a new chat.")
    @PostMapping
    public ApiResponse<?> create(@RequestBody @Valid ChatCreateRequest request) {
        log.info("Request to create a new chat: {}", request);
        var chat = chatService.create(request);
        log.info("Chat created successfully with ID: {}", chat.getId());
        return new ApiResponse<>(HttpStatus.CREATED, "Chat created successfully", chat);
    }

    @Operation(summary = "Get Chat by ID", description = "API to get a chat by ID.")
    @GetMapping(path = "/{id}")
    public ApiResponse<?> getDetails(@PathVariable("id") Long id) {
        log.info("Request to get chat by ID: {}", id);
        var chat = chatService.getChatDetailsById(id);
        log.info("Chat found: {}", chat);
        return new ApiResponse<>(HttpStatus.OK, "Chat retrieved successfully", chat);
    }

    @Operation(summary = "Update Chat", description = "API to update an existing chat.")
    @PutMapping(path = "/{id}")
    public ApiResponse<?> updateChat(@PathVariable("id") Long id, @RequestBody @Valid ChatUpdateRequest request) {
        log.info("Request to update chat with ID {}: {}", id, request);
        chatService.update(id, request);
        log.info("Chat with ID {} updated successfully", id);
        return new ApiResponse<>(HttpStatus.OK, "Chat updated successfully");
    }

    @Operation(summary = "Delete Chat", description = "API to delete a chat by ID.")
    @DeleteMapping(path = "/{id}")
    public ApiResponse<?> deleteChat(@PathVariable("id") Long id) {
        log.info("Request to delete chat with ID: {}", id);
        chatService.delete(id);
        log.info("Chat with ID {} deleted successfully", id);
        return new ApiResponse<>(HttpStatus.OK, "Chat deleted successfully");
    }
    @Operation(summary = "Update message status", description = "API to update message status when user read.")
    @PatchMapping ("/{chatId}/messages/seen")
    public ApiResponse<?> seen(@PathVariable("chatId") Long chatId, @RequestBody @Valid ChatUpdateRequest request) throws Exception {
        log.info("Request to update message status with chatID {}: {}", chatId, request);
        messageService.seen(chatId);
        log.info("message status with ID {} updated successfully", chatId);
        return new ApiResponse<>(HttpStatus.OK, "message status updated successfully");
    }
}
