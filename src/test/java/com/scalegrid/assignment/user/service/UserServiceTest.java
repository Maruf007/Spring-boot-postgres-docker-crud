package com.scalegrid.assignment.user.service;

import com.scalegrid.assignment.exception.ResourceNotFoundException;
import com.scalegrid.assignment.user.model.User;
import com.scalegrid.assignment.user.model.dto.UserDto;
import com.scalegrid.assignment.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * this class tests  {@link UserService} class functionalities
 */
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUser_success() {
        // given
        Optional<User> user = Optional.of(getUser());

        // when
        when(userRepository.findByEmail(eq("maruf.cse.cou@gmail.com"))).thenReturn(user);

        // then
        assertEquals(user.get(), userService.getUser("maruf.cse.cou@gmail.com"));

        // verify
        verify(userRepository).findByEmail(eq("maruf.cse.cou@gmail.com"));
    }

    @Test
    public void testGetUser_resourceNotFoundException() {
        // given
        Optional<User> user = Optional.empty();

        // when
        when(userRepository.findByEmail(eq("maruf.cse.cou@gmail.com"))).thenReturn(user);

        // then`
        assertThrows(ResourceNotFoundException.class
                , () -> userService.getUser("maruf.cse.cou@gmail.com"));

        // verify
        verify(userRepository).findByEmail(eq("maruf.cse.cou@gmail.com"));
    }

    @Test
    public void testGetUserList() {
        // given
        List<User> userList = new ArrayList<>();
        userList.add(getUser());
        Page page = new PageImpl(userList);
        Pageable pageable = PageRequest.of(0, 1);

        // when
        when(userRepository.findAll(pageable)).thenReturn(page);

        // then
        List<User> fetchedUserList = userService.getUserList(pageable);
        assertNotNull(fetchedUserList);
        assertEquals(userList, fetchedUserList);

        // verify
        verify(userRepository).findAll(pageable);
    }

    @Test
    public void testCreateUser_success() {
        // given
        User user = getUser();

        // when
        when(userRepository.save(any(User.class))).thenReturn(user);

        // then
        User savedUser = userService.createUser(getUserDto());
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals(user, savedUser);

        // verify
        verify(userRepository).save(any(User.class));
    }


    @Test
    public void testModifyUser_success() {
        // given
        Optional<User> user = Optional.of(getUser());
        Long userId = user.get().getId();

        // when
        when(userRepository.findById(eq(userId))).thenReturn(user);
        when(userRepository.save(eq(user.get()))).thenReturn(user.get());

        // then
        User updatedUser = userService.modifyUser(getUserDto(), userId);
        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getId());
        assertEquals(user.get(), updatedUser);

        // verify
        verify(userRepository).findById(eq(userId));
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testModifyUser_resourceNotFoundException() {
        // given
        Long userId = 1l;

        // when
        when(userRepository.findById(eq(userId))).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> userService.modifyUser(getUserDto(), userId));

        // verify
        verify(userRepository).findById(eq(userId));
    }

    @Test
    public void testRemoveUser_success() {
        // given
        Optional<User> user = Optional.of(getUser());
        Long userId = user.get().getId();

        // when
        when(userRepository.findById(eq(userId))).thenReturn(user);

        // then
        assertDoesNotThrow(() -> userService.removeUser(userId));

        // verify
        verify(userRepository).findById(eq(userId));
    }

    @Test
    public void removeUserResourceNotFoundExceptionTest() {
        /// given
        Long userId = 1l;

        // when
        when(userRepository.findById(eq(userId))).thenThrow(ResourceNotFoundException.class);

        // then
        assertThrows(ResourceNotFoundException.class, () -> userService.removeUser(userId));

        // verify
        verify(userRepository).findById(eq(userId));
    }

    private User getUser() {
        User user = new User("Maruf", "maruf.cse.cou@gmail.com"
                , LocalDate.of(1994, 2, 10));
        user.setId(1L);
        return user;
    }

    private UserDto getUserDto() {
        return new UserDto("maruf", "maruf.cse.cou@gmail.com"
                , LocalDate.of(1994, 2, 10));
    }
}
