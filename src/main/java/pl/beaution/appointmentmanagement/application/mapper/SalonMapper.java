package pl.beaution.appointmentmanagement.application.mapper;

import pl.beaution.appointmentmanagement.application.dto.salon.SalonResponseDto;
import pl.beaution.appointmentmanagement.domain.model.Salon;

public class SalonMapper {
    public static SalonResponseDto toDto(Salon salon) {
        SalonResponseDto dto = new SalonResponseDto();
        dto.setId(salon.getId());
        dto.setName(salon.getName());
        dto.setLocation(salon.getLocation());
        return dto;
    }
}
