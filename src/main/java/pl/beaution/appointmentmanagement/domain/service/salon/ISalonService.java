package pl.beaution.appointmentmanagement.domain.service.salon;

import pl.beaution.appointmentmanagement.application.dto.salon.SalonResponseDto;
import pl.beaution.appointmentmanagement.domain.model.Salon;

import java.util.List;

public interface ISalonService {
    Salon createSalon(Salon salon);

    Salon updateSalon(Salon salon) throws IllegalAccessException;

    Salon createSalonForUser(Long userId, Salon salon);

    List<SalonResponseDto> findSalonsByUserId(Long userId);
}
