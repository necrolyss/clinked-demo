package lv.dev.clinked.demo.infra;

import lv.dev.clinked.demo.payloads.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
class ApplicationExceptionHandler {
    private static final String UNIDENTIFIED_ERROR = "exception.unidentified";
    private static final String GENERIC_REST_ERROR = "exception.generic-rest";
    private static final String ILLEGAL_ARGUMENT_ERROR = "exception.illegal-argument";

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgument(Locale locale) {
        String errorMessage = messageSource.getMessage(ILLEGAL_ARGUMENT_ERROR, null, locale);
        return new ResponseEntity<>(new ApiResponse(false, errorMessage), HttpStatus.OK);
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ApiResponse> handleRestException(RestException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(GENERIC_REST_ERROR, new String[]{ex.getMessage()}, locale);
        return new ResponseEntity<>(new ApiResponse(false, errorMessage), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleExceptions(Exception ex, Locale locale) {
        logger.error("Unidentified exception occurred", ex);
        String errorMessage = messageSource.getMessage(UNIDENTIFIED_ERROR, null, locale);
        return new ResponseEntity<>(new ApiResponse(false, errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
