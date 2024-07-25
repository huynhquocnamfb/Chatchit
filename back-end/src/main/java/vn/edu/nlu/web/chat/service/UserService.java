package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.common.response.PageResponse;
import vn.edu.nlu.web.chat.dto.details.BaseUserDTO;
import vn.edu.nlu.web.chat.dto.details.ProfileDTO;
import vn.edu.nlu.web.chat.dto.user.request.UserCreateRequest;
import vn.edu.nlu.web.chat.dto.user.request.UserUpdateRequest;
import vn.edu.nlu.web.chat.dto.user.response.UserCreateResponse;
import vn.edu.nlu.web.chat.dto.user.response.UserDetailsResponse;

/**
 * Service interface for managing users.
 */
public interface UserService {


    boolean exists(Long id);

    Long getIdByUsername(String username);

    boolean exists(String username);

    /**
     * Creates a new user.
     *
     * @param request The user creation request containing user details.
     * @return The response containing details of the created user.
     */
    UserCreateResponse create(UserCreateRequest request);


    /**
     * Retrieves all users with pagination and sorting.
     *
     * @param pageNo   The page number.
     * @param pageSize The size of each page.
     * @param sortBy   The field by which the users should be sorted.
     * @return A page response containing a list of users.
     */
    PageResponse<?> getAllUsersWithPagingAndSorting(int pageNo, int pageSize, String sortBy);


    /**
     * Deletes a user by ID.
     *
     * @param userId The ID of the user to delete.
     */
    void delete(long userId);


    /**
     * Updates details of an existing user.
     *
     * @param userId  The ID of the user to update.
     * @param request The update request containing new user details.
     */
    void update(long userId, UserUpdateRequest request);


    /**
     * Retrieves details of a user by ID.
     *
     * @param userId The ID of the user to retrieve details for.
     * @return The response containing details of the user.
     */
    UserDetailsResponse getUserDetailsById(long userId);


    /**
     * Retrieves all users with search, pagination, and sorting.
     *
     * @param pageNo   The page number.
     * @param pageSize The size of each page.
     * @param search   The search query to filter users.
     * @param sortBy   The field by which the users should be sorted.
     * @return A page response containing a list of users.
     */
    PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy);

    ProfileDTO getProfileById(Long userId);

    ProfileDTO getMyProfile();

    BaseUserDTO getMe();
}
