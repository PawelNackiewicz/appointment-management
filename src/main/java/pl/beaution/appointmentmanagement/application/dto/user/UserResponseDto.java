package pl.beaution.appointmentmanagement.application.dto.user;

import pl.beaution.appointmentmanagement.domain.model.security.UserStatus;

import java.util.Set;

public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private UserStatus status;
    private Set<RoleAssignmentDto> roleAssignments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Set<RoleAssignmentDto> getRoleAssignments() {
        return roleAssignments;
    }

    public void setRoleAssignments(Set<RoleAssignmentDto> roleAssignments) {
        this.roleAssignments = roleAssignments;
    }
}
