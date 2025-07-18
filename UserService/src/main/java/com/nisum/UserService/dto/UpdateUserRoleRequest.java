package com.nisum.UserService.dto;

public class UpdateUserRoleRequest {
    private Integer userId;
    private String roleName;

    public UpdateUserRoleRequest() {}

    public UpdateUserRoleRequest(Integer userId, String roleName) {
        this.userId = userId;
        this.roleName = roleName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateUserRoleRequest that = (UpdateUserRoleRequest) o;
        return java.util.Objects.equals(userId, that.userId) &&
                java.util.Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(userId, roleName);
    }
}
