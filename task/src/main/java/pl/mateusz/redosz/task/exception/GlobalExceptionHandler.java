package pl.mateusz.redosz.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.mateusz.redosz.task.exception.exceptions.UserRepositoriesNotFound;
import pl.mateusz.redosz.task.model.ErrorDto;


@RestControllerAdvice()
public class GlobalExceptionHandler {
    @ExceptionHandler(UserRepositoriesNotFound.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(404, e.getMessage()));
    }
}
