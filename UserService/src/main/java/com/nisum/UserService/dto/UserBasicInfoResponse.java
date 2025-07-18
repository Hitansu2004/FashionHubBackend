package com.nisum.UserService.dto;

public class UserBasicInfoResponse {
    private String name;
    private String email;
    private String phoneNumber;

    public UserBasicInfoResponse(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBasicInfoResponse that = (UserBasicInfoResponse) o;
        return java.util.Objects.equals(name, that.name) &&
                java.util.Objects.equals(email, that.email) &&
                java.util.Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, email, phoneNumber);
    }
}
