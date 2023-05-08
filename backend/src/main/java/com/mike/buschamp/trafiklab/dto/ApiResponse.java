package com.mike.buschamp.trafiklab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse<T> {

    @JsonProperty("StatusCode")
    private int statusCode;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("ExecutionTime")
    private int executionTime;
    @JsonProperty("ResponseData")
    private ApiResponseData<T> responseData;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public ApiResponseData<T> getResponseData() {
        return responseData;
    }

    public void setResponseData(ApiResponseData<T> responseData) {
        this.responseData = responseData;
    }
}