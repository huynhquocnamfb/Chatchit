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
import vn.edu.nlu.web.chat.dto.user.request.UserCreateRequest;
import vn.edu.nlu.web.chat.dto.user.request.UserUpdateRequest;
import vn.edu.nlu.web.chat.service.UserService;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
@Tag(name = "User Controller")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Add new User", description = "API create a new user.")
    @PostMapping
    public ApiResponse<?> create(@RequestBody @Valid UserCreateRequest request) {
        log.info("Request add new User: {}", request);
        var res = userService.create(request);
        log.info("User successfully created with ID: {}", res.getId());
        return new ApiResponse<>(HttpStatus.CREATED, Translator.toLocale("user.add.success"), res);

    }

    @Operation(summary = "Get list of users per pageNo", description = "Send a request via this API to get user list by pageNo and pageSize")
    @GetMapping
    public ApiResponse<?> searchAllUserDetails(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                               @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                               @RequestParam(required = false) String sortBy) {
        log.info("Request get all of users");
        var res = userService.getAllUsersWithPagingAndSorting(pageNo, pageSize, sortBy);
        log.info("Users successfully retrieved with total: {} ", res.getTotal());
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("user.list.success"), res);
    }

    @Operation(summary = "Delete User", description = "API delete an existing user.")
    @DeleteMapping(path = "/{userId}")
    public ApiResponse<?> delete(@PathVariable("userId") long userId) {
        log.info("Request to delete User with ID: {}", userId);
        userService.delete(userId);
        log.info("User with ID {} successfully deleted", userId);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("user.delete.success"));
    }


    @Operation(summary = "Update User", description = "API update an existing user.")
    @PutMapping(path = "/{userId}")
    public ApiResponse<?> update(@PathVariable("userId") long userId, @RequestBody @Valid UserUpdateRequest request) {
        log.info("Request to update User with ID: {}", userId);
        userService.update(userId, request);
        log.info("User with ID {} successfully updated", userId);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("user.update.success"));
    }

    @Operation(summary = "Get User", description = "API to retrieve user details.")
    @GetMapping(path = "/{userId}")
    public ApiResponse<?> getDetails(@PathVariable("userId") long userId) {
        log.info("Request to retrieve User with ID: {}", userId);
        var res = userService.getUserDetailsById(userId);
        log.info("User with ID {} successfully retrieved", userId);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("user.get.success"), res);
    }


    @Operation(summary = "Search Users", description = "API to search for users based on criteria.")
    @GetMapping(path = "/search")
    public ApiResponse<?> searchAllUserDetails(@Min(0) @RequestParam(defaultValue = "0", required = false) int pageNo,
                                               @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                               @RequestParam(required = false) String sortBy,
                                               @RequestParam(required = true) String query) {

        log.info("Request get list of users and search with paging and sorting");
        PageResponse<?> response = userService.getAllUsersAndSearchWithPagingAndSorting(pageNo, pageSize, query, sortBy);
        log.info("Users matching the query '{}' successfully retrieved", query, pageNo, pageSize, sortBy);
        return new ApiResponse<>(HttpStatus.OK, Translator.toLocale("user.list.success"), response);
    }


    @Operation(summary = "Search Users", description = "API to search for users based on criteria.")
    @GetMapping(path = "/{userId}/profile")
    public ApiResponse<?> getProfileByUserId(@PathVariable(value = "userId") @Min(1) Long userId) {
        log.info("Request to get user profile with user ID: {}", userId);
        var res = userService.getProfileById(userId);
        log.info("Profile with user ID {} successfully retrieved", userId);
        return new ApiResponse<>(HttpStatus.OK, "profile.get.success",res);
    }

    @GetMapping(path = "/me/profile")
    public ApiResponse<?> getProfileByMe(){
        return new ApiResponse<>(HttpStatus.OK, "profile.get.success",userService.getMyProfile());
    }
    @GetMapping(path = "/me")
    public ApiResponse<?> getByMe(){
        return new ApiResponse<>(HttpStatus.OK, "profile.get.success",userService.getMe());
    }



}
