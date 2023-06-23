package com.coinverse.api.common.errors;

import com.coinverse.api.common.exceptions.ApiException;
import com.coinverse.api.core.utils.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;

public class ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OffsetDateTime timeStamp;
    private final int code;
    private final String message;
    @JsonIgnore
    private final int httpStatusCode;

    private static final Logger logger = LoggerFactory.getLogger(ErrorResponse.class);

    public ErrorResponse() {
        this(new ApiException());
    }

    public ErrorResponse(@NotNull final ApiException apiException) {
        this(apiException, true);
    }

    public ErrorResponse(@NotNull final ApiException apiException, boolean showTimeStamp) {
        if (showTimeStamp) {
            timeStamp = DateTimeUtil.getCurrentTimeStamp();
        }
        code = apiException.getApiErrorCode().value();
        message = apiException.getMessage();
        httpStatusCode = apiException.getHttpStatus().value();
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String toJson() throws JsonProcessingException{
        ObjectMapper mapper = JsonMapper
                .builder()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .addModule(new JavaTimeModule())
                .build();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            logger.error(ex.getLocalizedMessage());
            throw ex;
        }
    }
}
