package pl.beaution.appointmentmanagement.domain.service.salon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.beaution.appointmentmanagement.application.dto.salon.SalonResponseDto;
import pl.beaution.appointmentmanagement.application.mapper.SalonMapper;
import pl.beaution.appointmentmanagement.domain.model.Salon;
import pl.beaution.appointmentmanagement.domain.model.security.Role;
import pl.beaution.appointmentmanagement.domain.model.security.RoleAssignment;
import pl.beaution.appointmentmanagement.domain.model.security.User;
import pl.beaution.appointmentmanagement.domain.repository.RoleAssignmentRepository;
import pl.beaution.appointmentmanagement.domain.repository.RoleRepository;
import pl.beaution.appointmentmanagement.domain.repository.SalonRepository;
import pl.beaution.appointmentmanagement.domain.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalonService implements ISalonService {

    private final SalonRepository salonRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleAssignmentRepository roleAssignmentRepository;

    @Autowired
    public SalonService(SalonRepository salonRepository, RoleRepository roleRepository, UserRepository userRepository, RoleAssignmentRepository roleAssignmentRepository) {
        this.salonRepository = salonRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleAssignmentRepository = roleAssignmentRepository;
    }

    @Override
    public Salon createSalon(Salon salon) {
        try {
            return salonRepository.save(salon);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not create salon");
        }
    }

    @Override
    public Salon updateSalon(Salon salon) throws IllegalAccessException {
        return null;
    }

    @Override
    public Salon createSalonForUser(Long userId, Salon salon) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        ;
        Salon savedSalon = salonRepository.save(salon);
        assignOwnerRoleToUser(user, savedSalon);
        return savedSalon;
    }

    @Override
    public List<SalonResponseDto> findSalonsByUserId(Long userId) {
        List<RoleAssignment> roleAssignments = roleAssignmentRepository.findByUserId(userId);
        List<Salon> salons = roleAssignments.stream()
                .map(RoleAssignment::getSalon).distinct().toList();
        return salons.stream().map(SalonMapper::toDto).collect(Collectors.toList());
    }

    private void assignOwnerRoleToUser(User user, Salon salon) {
        Role ownerRole = roleRepository.findByRoleName("ROLE_OWNER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName("ROLE_OWNER");
                    newRole.setHierarchyLevel(1);
                    return roleRepository.save(newRole);
                });

        RoleAssignment roleAssignment = new RoleAssignment();
        roleAssignment.setUser(user);
        roleAssignment.setSalon(salon);
        roleAssignment.setRole(ownerRole);

        user.getRoleAssignments().add(roleAssignment);

        roleAssignmentRepository.save(roleAssignment);

        userRepository.save(user);
    }
}
