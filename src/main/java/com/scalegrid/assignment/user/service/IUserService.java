package com.scalegrid.assignment.user.service;

import com.scalegrid.assignment.exception.ResourceNotFoundException;
import com.scalegrid.assignment.user.model.User;
import com.scalegrid.assignment.user.model.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>Interface for managing user service related operations.</p>
 */
public interface IUserService {
    /**
     * This method creates new user. For duplicate email it will throw
     * {@link org.hibernate.exception.ConstraintViolationException}
     *
     * @param userDto {@link UserDto}
     * @return {@link User}
     */
    User createUser(UserDto userDto);

    /**
     * This method modifies user info based on user id.
     *
     * @param userDto {@link UserDto}
     * @param id      {@link Long}
     * @return {@link User}
     * @throws ResourceNotFoundException
     */
    User modifyUser(UserDto userDto, Long id);

    /**
     * This method deletes user info based on user id.
     *
     * @param id {@link Long}
     * @throws ResourceNotFoundException
     */
    void removeUser(Long id);

    /**
     * This method gets user info based on email.
     *
     * @param email {@link String}
     * @return {@link User}
     * @throws ResourceNotFoundException
     */
    User getUser(String email);

    /**
     * This method gets user list using paging.
     *
     * @param pageable {@link Pageable}
     * @return {@link List<User>}
     */
    List<User> getUserList(Pageable pageable);

    /**
     * This method converts user dto to entity
     *
     * @param userDto {@link UserDto}
     * @return {@link User}
     */
    User convertDtoToEntity(UserDto userDto);
}
