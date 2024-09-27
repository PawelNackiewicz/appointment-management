package pl.beaution.appointmentmanagement.application.mapper;

import pl.beaution.appointmentmanagement.application.dto.user.RoleAssignmentDto;
import pl.beaution.appointmentmanagement.application.dto.user.UserResponseDto;
import pl.beaution.appointmentmanagement.domain.model.security.Role;
import pl.beaution.appointmentmanagement.domain.model.security.RoleAssignment;
import pl.beaution.appointmentmanagement.domain.model.security.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setStatus(user.getStatus());
        Set<RoleAssignmentDto> roleAssignmentDtos = RoleAssignmentMapper.toDtoSet(user.getRoleAssignments());
        dto.setRoleAssignments(roleAssignmentDtos);
        return dto;
    }
}
