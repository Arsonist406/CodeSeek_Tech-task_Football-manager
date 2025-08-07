package dev.footballmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

public record PlayerDTO(

        Long id,

        @NotBlank(message = "Can't be blank")
        @Length(message = "Max length is 255 symbols", max = 255)
        String name,

        @NotBlank(message = "Can't be blank")
        @Length(message = "Max length is 255 symbols", max = 255)
        String surname,

        @NotNull(message = "Can't be null")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,

        @PositiveOrZero(message = "Must be 0 or bigger")
        int experienceInMonths,

        TeamShortDTO team

) implements Serializable {
        public record TeamShortDTO(
                Long id,
                String name,
                double transferFeeInPercent,
                double account
        ) implements Serializable {}
}