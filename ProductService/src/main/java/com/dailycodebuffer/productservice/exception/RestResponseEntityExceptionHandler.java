package com.dailycodebuffer.productservice.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handleProductServiceException(ProductServiceCustomException exception) {
        return new ResponseEntity<>(new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return HttpStatus.NOT_FOUND;
            }

            @Override
            public ProblemDetail getBody() {
                // You create and return the actual data container here
                ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        exception.getMessage()
                );
                pd.setTitle("Product Service Error");
                pd.setProperty("errorCode", exception.getErrorCode()); // Adding your custom field

                return pd;
            }
        }, HttpStatus.NOT_FOUND);

    }
}
