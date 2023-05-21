package com.scalegrid.assignment.user.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@ApiModel("UserDto model is used to create new user")
public class UserDto implements Serializable {
    @ApiModelProperty("Name is required")
    private String name;

    @ApiModelProperty("Email must be unique and required")
    private String email;

    @ApiModelProperty("Birthdate is required")
    private LocalDate dateOfBirth;

    public UserDto() {
    }

    public UserDto(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(getEmail(), userDto.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("(name='").append(name);
        sb.append("', email='").append(email);
        sb.append("', birthDate=").append(dateOfBirth);
        sb.append(')');
        return sb.toString();
    }
}
