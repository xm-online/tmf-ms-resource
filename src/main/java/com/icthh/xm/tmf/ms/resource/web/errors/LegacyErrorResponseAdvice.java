package com.icthh.xm.tmf.ms.resource.web.errors;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Keeps the response to an unmapped path the service had before the migration (Spring 5), ahead of the
 * xm-commons {@code ExceptionTranslator}: Spring 6.1+ throws {@link NoResourceFoundException}, which the
 * xm-commons translator turned into 500 {@code error.internalServerError}; before, the servlet container
 * answered 404.
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LegacyErrorResponseAdvice {

    @ExceptionHandler(NoResourceFoundException.class)
    public void processNoResourceFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}
