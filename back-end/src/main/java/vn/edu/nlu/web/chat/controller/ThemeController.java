package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.chat.request.ChatUpdateRequest;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.dto.theme.request.ThemeUpdateRequest;
import vn.edu.nlu.web.chat.model.Theme;
import vn.edu.nlu.web.chat.service.ChatService;
import vn.edu.nlu.web.chat.service.ThemeService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/themes")
public class ThemeController {
    private final ThemeService themeService;

    @Operation(summary = "Search Themes with specific chat id ", description = "API to search for Themes based on id.")
    @GetMapping(path = "/{themeId}")
    public ApiResponse<?> searchThemeDetails(@PathVariable(name = "themeId") Long themeId) {
        Theme res = themeService.Search(themeId);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("themes.search.success"), res);
    }

    @Operation(summary = "Update Themes", description = "API to update an existing User Themes.")
    @PutMapping(path = "/{id}")
    public ApiResponse<?> updateChat(@PathVariable("id") Long id, @RequestBody @Valid ThemeUpdateRequest request) {
        log.info("Request to update Theme with ID {}: {}", id, request);
        themeService.update(id, request);
        log.info("Theme with ID {} updated successfully", id);
        return new ApiResponse<>(HttpStatus.OK, "Theme updated successfully");
    }
    @Operation(summary = "Delete Theme", description = "API to delete a Theme by ID.")
    @DeleteMapping(path = "/themes/{id}")
    public ApiResponse<?> delete(@PathVariable(name = "id") Long id) {
        log.info("Request to delete Theme with ID: {}", id);
        themeService.delete(id);
        log.info("Theme with ID {} successfully deleted", id);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("themes.delete.success"));
    }
}
