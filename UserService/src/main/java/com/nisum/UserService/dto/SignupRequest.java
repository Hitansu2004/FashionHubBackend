package com.nisum.UserService.dto;

import jakarta.validation.constraints.*;

public class SignupRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String phoneNumber;

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignupRequest that = (SignupRequest) o;
        return java.util.Objects.equals(firstName, that.firstName) &&
                java.util.Objects.equals(lastName, that.lastName) &&
                java.util.Objects.equals(email, that.email) &&
                java.util.Objects.equals(password, that.password) &&
                java.util.Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(firstName, lastName, email, password, phoneNumber);
    }
}
