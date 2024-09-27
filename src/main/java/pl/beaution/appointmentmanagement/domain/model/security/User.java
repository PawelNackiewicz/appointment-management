package pl.beaution.appointmentmanagement.domain.model.security;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.PENDING;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<RoleAssignment> roleAssignments;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<RoleAssignment> getRoleAssignments() {
        return roleAssignments;
    }

    public void setRoleAssignments(Set<RoleAssignment> roleAssignments) {
        this.roleAssignments = roleAssignments;
    }
}
