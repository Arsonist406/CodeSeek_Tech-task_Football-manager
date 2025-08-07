package dev.footballmanager.controller;

import dev.footballmanager.dto.ErrorDTO;
import dev.footballmanager.exception.NotEnoughMoneyOnAccountException;
import dev.footballmanager.exception.NotFoundException;
import dev.footballmanager.exception.PlayerAlreadyInTeamException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    private ErrorDTO buildErrorDTO(
            HttpStatus status,
            WebRequest request,
            Set<ErrorDTO.ErrorDetail> details
    ) {
        return ErrorDTO
                .builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.name())
                .path(request.getDescription(false))
                .details(details)
                .build();
    }

    private Set<ErrorDTO.ErrorDetail> buildErrorDetails(
            Exception e
    ) {
        return Set.of(ErrorDTO.ErrorDetail
                .builder()
                .value(null)
                .message(e.getMessage())
                .build());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDTO handleNotFoundException(
            NotFoundException e,
            WebRequest request
    ) {
        Set<ErrorDTO.ErrorDetail> details = buildErrorDetails(e);
        return buildErrorDTO(HttpStatus.NOT_FOUND, request, details);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            NotEnoughMoneyOnAccountException.class,
            PlayerAlreadyInTeamException.class,
            DateTimeException.class
    })
    public ErrorDTO handleBadRequest(
            RuntimeException e,
            WebRequest request
    ) {
        Set<ErrorDTO.ErrorDetail> details = buildErrorDetails(e);
        return buildErrorDTO(HttpStatus.BAD_REQUEST, request, details);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorDTO handleConstraintViolationException(
            ConstraintViolationException e,
            WebRequest request
    ) {
        Set<ErrorDTO.ErrorDetail> details = e.getConstraintViolations()
                .stream()
                .map(violation ->
                        ErrorDTO.ErrorDetail
                                .builder()
                                .value(violation.getPropertyPath().toString())
                                .message(violation.getMessage())
                                .build())
                .collect(Collectors.toSet());

        return buildErrorDTO(HttpStatus.BAD_REQUEST, request, details);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            WebRequest request
    ) {
        Set<ErrorDTO.ErrorDetail> details = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError ->
                        ErrorDTO.ErrorDetail
                                .builder()
                                .value(fieldError.getField())
                                .message(fieldError.getDefaultMessage())
                                .build())
                .collect(Collectors.toSet());

        return buildErrorDTO(HttpStatus.BAD_REQUEST, request, details);
    }
}
