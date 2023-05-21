package com.scalegrid.assignment.user.service;

import com.scalegrid.assignment.exception.ResourceNotFoundException;
import com.scalegrid.assignment.user.model.User;
import com.scalegrid.assignment.user.model.dto.UserDto;
import com.scalegrid.assignment.user.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>This service is responsible for user related operations</p>
 */
@Service
public class UserService implements IUserService {
    private static final String USER_NOT_FOUND_MODIFY_ID = "While modifying user not found with ID: ";
    private static final String USER_NOT_FOUND_DELETE_ID = "While deleting user not found with ID: ";
    private static final String USER_NOT_FOUND_EMAIL = "User not found with email: ";
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method creates new user. For duplicate email it will throw
     * {@link ConstraintViolationException}
     *
     * @param userDto {@link UserDto}
     * @return {@link User}
     */
    @Override
    public User createUser(UserDto userDto) {
        return userRepository.save(convertDtoToEntity(userDto));
    }

    /**
     * This method modifies user info based on user id.
     *
     * @param userDto {@link UserDto}
     * @param id      {@link Long}
     * @return {@link User}
     * @throws ResourceNotFoundException
     */
    @Override
    public User modifyUser(UserDto userDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MODIFY_ID.concat(String.valueOf(id))));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setBirthDate(userDto.getDateOfBirth());
        return userRepository.save(user);
    }

    /**
     * This method deletes user info based on user id.
     *
     * @param id {@link Long}
     * @throws ResourceNotFoundException
     */
    @Override
    public void removeUser(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_DELETE_ID.concat(String.valueOf(id))));
        userRepository.delete(user);
    }


    /**
     * This method gets user info based on email.
     *
     * @param email {@link String}
     * @return {@link User}
     * @throws ResourceNotFoundException
     */
    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_EMAIL.concat(String.valueOf(email))));
    }


    /**
     * This method gets user list using paging.
     *
     * @param pageable {@link Pageable}
     * @return {@link List<User>}
     */
    @Override
    public List<User> getUserList(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    /**
     * This method converts user dto to entity
     *
     * @param userDto {@link UserDto}
     * @return {@link User}
     */
    @Override
    public User convertDtoToEntity(UserDto userDto) {
        if (userDto == null) return null;
        return new User(userDto.getName(), userDto.getEmail(), userDto.getDateOfBirth());
    }
}
