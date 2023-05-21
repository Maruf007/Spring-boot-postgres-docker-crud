package com.scalegrid.assignment.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * <p>ErrorResponse class return errors.</p>
 */
@Data
public class ErrorResponse {
    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createdAt;
    private String message;

    /**
     * Constructs a new {@link ErrorResponse} instance
     *
     * @param builder {@link Builder}
     */
    public ErrorResponse(Builder builder) {
        this.httpStatus = builder.httpStatus;
        this.createdAt = builder.createdAt;
        this.message = builder.message;
    }

    /**
     * This class is responsible for creating {@link ErrorResponse} object
     */
    static class Builder {
        private HttpStatus httpStatus;
        private String message;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private LocalDateTime createdAt;

        public Builder() {
        }

        public Builder withHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder withCreatedAt() {
            this.createdAt = LocalDateTime.now();
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }

    }
}
