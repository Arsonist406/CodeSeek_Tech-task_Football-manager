package dev.footballmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

public record TeamDTO(

        Long id,

        @NotBlank(message = "Can't be blank")
        @Length(message = "Max length is 255 symbols", max = 255)
        String name,

        @NotNull(message = "Can't be null")
        @Range(message = "Must be in range from 0 to 10", min = 0, max = 10)
        double transferFeeInPercent,

        @NotNull(message = "Can't be null")
        @PositiveOrZero(message = "Must be 0 or bigger")
        double account,

        Set<PlayerShortDTO> players

) implements Serializable {
        public record PlayerShortDTO(
                Long id,
                String name,
                String surname,
                LocalDate birthDate,
                int experienceInMonths
        ) implements Serializable {}
}