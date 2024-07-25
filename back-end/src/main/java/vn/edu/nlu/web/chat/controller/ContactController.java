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

import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.contact.request.ContactAddRequest;
import vn.edu.nlu.web.chat.dto.contact.request.ContactUnRequest;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.service.ContactService;
import vn.edu.nlu.web.chat.service.UserService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/contacts")
@Tag(name = "Contact Controller", description = "Endpoints for Contact")
public class ContactController {
    private final ContactService contactService;
    private final UserService userService;

    @Operation(summary = "Add new Contact", description = "API add a new Contact.")
    @PostMapping
    public ApiResponse<?> add(@RequestBody @Valid ContactAddRequest request) {
        log.info("Request add new Contact: {}", request);
        try {
            var res = contactService.addContact(request);
            log.info("Contact with id: {} successfully, wait for acceptance", res.getId());
            return new ApiResponse<>(HttpStatus.CREATED, Translator.toLocale("contact.add.success"), res);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), request.getEmail());
            throw new ApiRequestException(e.getMessage());
        } catch (Exception e) {
            log.error("Error process", e);
            throw new ApiRequestException(e.getMessage());
        }
    }

    @Operation(summary = "cancel Contact", description = "API cancel a Contact.")
    @DeleteMapping
    public ApiResponse<?> unAdd(@RequestBody @Valid ContactUnRequest request) {
        log.info("Request cancel Contact: {}", request);
        try {
            contactService.unContact(request);
            log.info("Cancel Contact successfully");
            return new ApiResponse<>(HttpStatus.NO_CONTENT, Translator.toLocale("Cancel.Contact.success"));
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), request.getIdContact());
            throw new ApiRequestException(e.getMessage());
        } catch (Exception e) {
            log.error("Error process", e);
            throw new ApiRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Get list Contact", description = "API Get list  Contact.")
    @GetMapping
    public ApiResponse<?> list(@RequestParam(defaultValue = "0", required = false) int pageNo,
                               @Min(1) @RequestParam(defaultValue = "20", required = false) int pageSize,
                               @RequestParam(required = false) String sortBy) {
        log.info("Request get all of contact");
        try {
            var res = contactService.list(pageNo, pageSize, sortBy);
            log.info("Contact successfully retrieved with total: {} ", res.getTotal());
            return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("contact.list.success"), res);
        } catch (ResourceNotFoundException re) {
            log.error(re.getMessage());
            throw new ApiRequestException(re.getMessage());
        } catch (Exception e) {
            log.error("Error process", e);
            throw new ApiRequestException(e.getMessage());
        }

    }

    @Operation(summary = "search Contact", description = "API search Contact.")
    @GetMapping(path = "/search/")
    public ApiResponse<?> search(@Min(0) @RequestParam(defaultValue = "0", required = false) int pageNo,
                                 @Min(1) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) String sortBy) {

        log.info("Request search with paging and sorting");
        try {
            PageResponse<?> response = contactService.search(pageNo, pageSize, search, sortBy);
            log.info("Contact matching the query '{}' successfully retrieved", pageNo, pageSize, search, sortBy);
            return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("Contact.search.success"), response);
        } catch (ResourceNotFoundException re) {
            log.error(re.getMessage());
            throw new ApiRequestException(re.getMessage());
        } catch (Exception e) {
            log.error("Error process", e);
            throw new ApiRequestException(e.getMessage());
        }

    }
//    @Operation(summary = "Get list Contact", description = "API Get list  Contact.")
//    @GetMapping(path =  "/dummy")
//    public ApiResponse<?> dummy() {
//        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("contact.list.success"), MockDto.getContactDto());
//    }

    @Operation(summary = "Get Conversation by Contact ID", description = "API to get a conversation by contact ID.")
    @GetMapping(path = "/{contactId}/conversation")
    public ApiResponse<?> getConversationByContactId(@PathVariable Long contactId) {
        log.info("Request to get conversation by contact ID: {}", contactId);
        var conversation = contactService.getMessagesByContactId(contactId);
        try {
            log.info("Conversation found with size : {}", conversation.getSize());
            return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("conversation.get.success"), conversation);
        } catch (Exception e) {
            log.info("No conversation found for contact ID: {}", contactId);
            throw e;
        }
    }
}
