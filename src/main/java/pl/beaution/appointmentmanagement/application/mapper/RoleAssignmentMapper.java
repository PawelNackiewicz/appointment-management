package pl.beaution.appointmentmanagement.application.mapper;

import pl.beaution.appointmentmanagement.application.dto.user.RoleAssignmentDto;
import pl.beaution.appointmentmanagement.domain.model.security.RoleAssignment;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleAssignmentMapper {

    public static RoleAssignmentDto toDto(RoleAssignment roleAssignment) {
        RoleAssignmentDto dto = new RoleAssignmentDto();
        dto.setId(roleAssignment.getId());
        dto.setUserId(roleAssignment.getUser().getId());
        dto.setSalonId(roleAssignment.getSalon().getId());
        dto.setRole(roleAssignment.getRole());
        dto.setPermissions(roleAssignment.getPermissions());
        return dto;
    }

    public static Set<RoleAssignmentDto> toDtoSet(Set<RoleAssignment> roleAssignments) {
        return roleAssignments.stream()
                .map(RoleAssignmentMapper::toDto)
                .collect(Collectors.toSet());
    }
}
