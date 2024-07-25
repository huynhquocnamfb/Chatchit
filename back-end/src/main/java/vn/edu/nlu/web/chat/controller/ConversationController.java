package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.service.AuthenticationService;
import vn.edu.nlu.web.chat.service.ConversationService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/conversations")
@Tag(name = "Contact Controller", description = "Endpoints for Contact")
public class ConversationController {
    private final ConversationService conversationService;
    private final AuthenticationService authenticationService;
    @Operation(summary = "Get Conversation by Contact ID", description = "API to get a conversation by contact ID.")
    @GetMapping(path = "/{contactId}")
    public ApiResponse<?> getConversationByContactId(@PathVariable Long contactId) {
        log.info("Request to get conversation by contact ID: {}", contactId);
        System.err.println(authenticationService.getCurrentUserId());
        var conversation = conversationService.getConversationByContactId(contactId);
        try {
            log.info("Conversation found with size : {}", conversation.getMessages().size());
            return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("conversation.get.success"), conversation);
        } catch (Exception e) {
            log.info("No conversation found for contact ID: {}", contactId);
            throw e;
        }
    }
}
