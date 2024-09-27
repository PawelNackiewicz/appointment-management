package pl.beaution.appointmentmanagement.application.dto.user;

import pl.beaution.appointmentmanagement.domain.model.security.Permission;
import pl.beaution.appointmentmanagement.domain.model.security.Role;

import java.util.Set;

public class RoleAssignmentDto {
    private Long id;
    private Long userId;
    private Long salonId;
    private Role role;
    private Set<Permission> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSalonId() {
        return salonId;
    }

    public void setSalonId(Long salonId) {
        this.salonId = salonId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
