package vn.edu.nlu.web.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.nlu.web.chat.config.locale.Translator;
import vn.edu.nlu.web.chat.dto.chat.request.ChatCreateRequest;
import vn.edu.nlu.web.chat.dto.color.request.ColorCreateRequest;
import vn.edu.nlu.web.chat.dto.color.request.ColorDetailsRequest;
import vn.edu.nlu.web.chat.dto.color.request.ColorUpdateRequest;
import vn.edu.nlu.web.chat.dto.color.response.ColorDetailsResponse;
import vn.edu.nlu.web.chat.dto.color.response.ColorUpdateResponse;
import vn.edu.nlu.web.chat.dto.common.response.ApiResponse;
import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.model.Color;
import vn.edu.nlu.web.chat.service.ColorService;

import java.util.List;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/colors")
public class ColorController {
    private final ColorService colorService;

    @Operation(summary = "Create Color", description = "API to create a new Color.")
    @PostMapping
    public ApiResponse<?> create(@RequestBody @Valid ColorCreateRequest request) {
        log.info("Request to create a new Color: {}", request);
        var color = colorService.create(request);
        log.info("Color created successfully with ID: {}", color.getId());
        return new ApiResponse<>(HttpStatus.CREATED, "Chat created successfully", color);
    }
    @Operation(summary = "Search Color", description = "API to Search a Color by ID.")
    @GetMapping
    public ApiResponse<?> search(){
        List<ColorDetailsResponse> res = colorService.search();
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("color.search.success"), res);
    }
    @Operation(summary = "getDetail Color", description = "API to getDetail a Color by ID.")
    @GetMapping(path = "/{id}")
    public ApiResponse<?> getDetail(@PathVariable(name = "id") Long id){
        ColorDetailsResponse res = colorService.getColor(id);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("color.detail.success"), res);
    }
    @Operation(summary = "Update Color", description = "API to update a Color by ID.")
    @PutMapping(path = "/{id}")
    public ApiResponse<?> update(@PathVariable(name = "id") Long id,@RequestBody ColorUpdateRequest value){
        colorService.update(id, value);
       return new ApiResponse<>(HttpStatus.OK, "Color updated successfully");
    }
    @Operation(summary = "Delete Color", description = "API to delete a Color by ID.")
    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable(name = "id") Long id) {
        log.info("Request to delete Color with ID: {}", id);
        colorService.delete(id);
        log.info("Color with ID {} successfully deleted", id);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("color.delete.success"));
    }
}
