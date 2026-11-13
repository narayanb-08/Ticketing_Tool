package com.sumpositive.ticketing.common.dto;

import java.time.OffsetDateTime;

public class ApiErrorResponse {
    private int status;
    private String error;
    private String message;
    private OffsetDateTime timestamp;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(
            int status,
            String error,
            String message,
            OffsetDateTime timestamp) {

        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
