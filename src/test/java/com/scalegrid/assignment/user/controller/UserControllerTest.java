package com.scalegrid.assignment.user.controller;

import com.scalegrid.assignment.exception.ResourceNotFoundException;
import com.scalegrid.assignment.user.model.User;
import com.scalegrid.assignment.user.model.dto.UserDto;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class tests {@link UserController} class functionalities
 */
public class UserControllerTest extends AbstractControllerTest {
    private static final String BASE_URL = "/api/v1/users";

    @Test
    public void testGetUserByEmail_success() throws Exception {
        // given
        LocalDate dateOfBirth = LocalDate.of(1994, 5, 20);
        User user = new User("Maruf", "maruf.cse.cou@gmail.com", dateOfBirth);
        user.setId(1L);

        // when
        when(userService.getUser("maruf.cse.cou@gmail.com")).thenReturn(user);

        // then
        mockMvc.perform(get(BASE_URL.concat("/maruf.cse.cou@gmail.com")).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Maruf")))
                .andExpect(jsonPath("$.email", is("maruf.cse.cou@gmail.com")))
                .andExpect(jsonPath("$.birthDate", is(dateOfBirth.toString())));

        // verify
        verify(userService).getUser("maruf.cse.cou@gmail.com");
    }

    @Test
    public void testGetUserByEmail_resourceNotFoundException() throws Exception {
        // given
        String email = "rahman.cse.cou@gmail.com";

        // when
        when(userService.getUser(email))
                .thenThrow(new ResourceNotFoundException("User not found with email: ".concat(email)));

        // then
        mockMvc.perform(get(BASE_URL.concat("/").concat(email)).accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message"
                        , is("User not found with email: ".concat(email))));

        // verify
        verify(userService).getUser("rahman.cse.cou@gmail.com");
    }

    @Test
    public void testGetUserList_success() throws Exception {
        // given
        List<User> userList = new ArrayList<>();
        LocalDate dateOfBirth = LocalDate.of(1994, 5, 20);
        User user1 = new User("Maruf", "maruf.cse.cou@gmail.com", dateOfBirth);
        user1.setId(1L);
        userList.add(user1);
        User user2 = new User("Rahman", "rahman.cse.cou@gmail.com", dateOfBirth);
        user2.setId(2L);
        userList.add(user2);

        // when
        when(userService.getUserList(any())).thenReturn(userList);

        // then
        mockMvc.perform(get(BASE_URL.concat("?page=0&size=2")).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Maruf")))
                .andExpect(jsonPath("$[1].name", is("Rahman")))
                .andExpect(jsonPath("$[0].email", is("maruf.cse.cou@gmail.com")))
                .andExpect(jsonPath("$[0].birthDate", is(dateOfBirth.toString())));

        // verify
        verify(userService).getUserList(any());
    }

    @Test
    public void testCreateUser_success() throws Exception {
        UserDto newUser = createUser("Maruf", "maruf.cse.cou@gmail.com"
                , LocalDate.of(1994, 2, 10));
        User user = new User("Maruf", "maruf.cse.cou@gmail.com"
                , LocalDate.of(1994, 2, 10));
        user.setId(1L);

        // when
        when(userService.createUser(eq(newUser))).thenReturn(user);

        // then
        mockMvc.perform(post(BASE_URL)
                        .content(getUserRequestBody())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Maruf")))
                .andExpect(jsonPath("$.email", is("maruf.cse.cou@gmail.com")));

        // verify
        verify(userService).createUser(eq(newUser));
    }

    @Test
    public void testUpdateExistingUser_success() throws Exception {
        // given
        LocalDate dateOfBirth = LocalDate.of(1994, 2, 10);
        UserDto userDto = createUser("Maruf", "maruf.cse.cou@gmail.com", dateOfBirth);
        User user = new User("Maruf", "maruf.cse.cou@gmail.com", dateOfBirth);
        user.setId(1L);

        // when
        when(userService.modifyUser(eq(userDto), eq(1L))).thenReturn(user);

        // then
        mockMvc.perform(put(BASE_URL.concat("/1"))
                        .content(getUserRequestBody())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Maruf")))
                .andExpect(jsonPath("$.birthDate", is("1994-02-10")))
                .andExpect(jsonPath("$.email", is("maruf.cse.cou@gmail.com")));

        // verify
        verify(userService).modifyUser(eq(userDto), eq(1L));
    }

    @Test
    public void testUpdateExistingUser_resourceNotFoundException() throws Exception {
        // given
        UserDto userDto = createUser("Maruf", "maruf.cse.cou@gmail.com"
                , LocalDate.of(1994, 2, 10));

        // when
        when(userService.modifyUser(eq(userDto), eq(1L)))
                .thenThrow(new ResourceNotFoundException("While modifying user not found with ID: 1"));

        // then
        mockMvc.perform(put(BASE_URL.concat("/1"))
                        .content(getUserRequestBody())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("While modifying user not found with ID: 1")))
                .andExpect(status().isNotFound());

        // verify
        verify(userService).modifyUser(eq(userDto), eq(1L));
    }

    @Test
    public void testDeleteExistingUser_success() throws Exception {
        // given

        // when

        // verify

        // then
        mockMvc.perform(delete(BASE_URL.concat("/1"))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_DeleteExistingUser_resourceNotFoundException() throws Exception {
        // given

        // when
        doThrow(new ResourceNotFoundException("While deleting user not found with ID: 1"))
                .when(userService).removeUser(eq(1l));

        // then
        mockMvc.perform(delete(BASE_URL.concat("/1"))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("While deleting user not found with ID: 1")));

        // verify
        verify(userService).removeUser(1l);
    }

    private String getUserRequestBody() {
        return "{\"name\":\"Maruf\", \"email\":\"maruf.cse.cou@gmail.com\", \"dateOfBirth\":\"1994-02-10\"}";
    }

    private UserDto createUser(String name, String email, LocalDate dateOfBirth) {
        return new UserDto(name, email, dateOfBirth);
    }

}
