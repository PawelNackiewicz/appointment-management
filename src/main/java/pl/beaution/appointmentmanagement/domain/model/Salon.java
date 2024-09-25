package pl.beaution.appointmentmanagement.domain.model;

import jakarta.persistence.*;
import pl.beaution.appointmentmanagement.domain.model.security.RoleAssignment;

import java.util.Set;

@Entity
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private Set<RoleAssignment> roleAssignments;
}
