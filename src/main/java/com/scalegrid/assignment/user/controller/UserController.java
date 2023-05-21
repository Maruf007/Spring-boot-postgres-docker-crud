package com.scalegrid.assignment.user.controller;

import com.scalegrid.assignment.exception.GlobalExceptionHandler;
import com.scalegrid.assignment.exception.ResourceNotFoundException;
import com.scalegrid.assignment.user.model.User;
import com.scalegrid.assignment.user.model.dto.UserDto;
import com.scalegrid.assignment.user.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * <p>Controller class for managing user related operations.</p>
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final IUserService userService;

    /**
     * Constructs a new {@link UserController} instance
     *
     * @param userService {@link IUserService}
     */
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * This end point creates new user
     *
     * @param userDto {@link UserDto}
     * @return {@link ResponseEntity}
     */
    @ApiOperation(value = "This API creates new user and for duplicate email it throws exception")
    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        LOGGER.debug("User creation started: {}", userDto.toString());
        User user = userService.createUser(userDto);
        LOGGER.debug("User creation end successfully: {}", user.toString());
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    /**
     * This end point updates user
     *
     * @param userDto {@link UserDto}
     * @param id      {@link Long}
     * @return {@link ResponseEntity}
     * @throws {@link ResourceNotFoundException} If the user is not found.
     */
    @ApiOperation(value = "This API modifies existing user and " +
            "if user not found for the given id, it throws exception")
    @PutMapping("/{id}")
    public ResponseEntity modifyUser(@RequestBody UserDto userDto, @PathVariable Long id) {
        LOGGER.debug("User with id: {} modification started", id);
        User user = userService.modifyUser(userDto, id);
        LOGGER.debug("User with id: {} modification end successfully", id);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    /**
     * This end point deletes user
     *
     * @param id {@link Long}
     * @return {@link ResponseEntity}
     * @throws {@link ResourceNotFoundException} If the user is not found.
     */
    @ApiOperation(value = "This API deletes user and" +
            " if user not found for the given id, it throws exception")
    @DeleteMapping("/{id}")
    public ResponseEntity removeUser(@PathVariable Long id) {
        LOGGER.debug("User with id: {} deletion started", id);
        userService.removeUser(id);
        LOGGER.debug("User with id: {} deletion end successfully", id);
        return new ResponseEntity(HttpEntity.EMPTY, HttpStatus.NO_CONTENT);
    }

    /**
     * This end point get user list with pagination
     *
     * @param pageable {@link Pageable}
     * @return {@link ResponseEntity}
     */
    @ApiOperation(value = "This API gets list of users with or without pagination")
    @GetMapping
    public ResponseEntity getUsers(Pageable pageable) {
        LOGGER.debug("Getting user list with pagination started");
        List<User> userList = userService.getUserList(pageable);
        LOGGER.debug("Getting user list with pagination end successfully");
        return new ResponseEntity(userList, HttpStatus.OK);
    }

    /**
     * This end point get user by email
     *
     * @param email {@link String}
     * @return {@link ResponseEntity}
     * @throws {@link ResourceNotFoundException} If the user is not found.
     */
    @ApiOperation(value = "This API get user by email and if user not found, it throws exception")
    @GetMapping("/{email}")
    public ResponseEntity getUserWithEmail(
            @Email(message = "Invalid email format")
            @PathVariable String email) {
        LOGGER.debug("Getting user with email: {} started", email);
        User user = userService.getUser(email);
        LOGGER.debug("Getting user with email: {} end successfully", email);
        return new ResponseEntity(user, HttpStatus.OK);
    }
}
