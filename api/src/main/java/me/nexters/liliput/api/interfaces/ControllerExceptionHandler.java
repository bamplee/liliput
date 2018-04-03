package me.nexters.liliput.api.interfaces;

import lombok.extern.slf4j.Slf4j;
import me.nexters.liliput.api.exception.NoResultFoundException;
import me.nexters.liliput.api.interfaces.v1.dto.response.V1Response;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.net.BindException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public V1Response<?> unknownException(Exception ex, WebRequest req) {
        log.error(ex.getMessage(), ex);
        V1Response v1Response = new V1Response<>(ex.toString());
        v1Response.setCode(1500);
        v1Response.setStatus(500);
        v1Response.setMessage("UncaughtException");
        return v1Response;
    }

    @ExceptionHandler(value = {BeanCreationNotAllowedException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public V1Response<?> beanCreationNotAllowedException(Exception ex, WebRequest req) {
        log.warn(ex.getMessage(), ex);
        V1Response v1Response = new V1Response<>(ex.toString());
        v1Response.setCode(1000);
        v1Response.setStatus(503);
        v1Response.setMessage("BeanCreationNotAllowedException");
        return v1Response;
    }

    @ExceptionHandler(value = {
        NoResultFoundException.class,
        NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public V1Response<?> notFoundException(Exception ex, WebRequest req) {
        log.warn(ex.getMessage());
        V1Response v1Response = new V1Response<>(ex.toString());
        v1Response.setCode(2500);
        v1Response.setStatus(404);
        v1Response.setMessage(ex.getMessage());
        return v1Response;
    }

    @ExceptionHandler(value = {
        MethodArgumentTypeMismatchException.class,
        BindException.class,
        ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public V1Response<?> methodArgumentTypeMismatchException(Exception ex, WebRequest req) {
        log.warn(ex.getMessage(), ex);
        V1Response v1Response = new V1Response<>(ex.toString());
        v1Response.setCode(1400);
        v1Response.setStatus(400);
        v1Response.setMessage("Method Argument Type Mismatch");
        return v1Response;
    }
}
