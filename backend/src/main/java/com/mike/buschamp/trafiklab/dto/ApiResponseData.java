package com.mike.buschamp.trafiklab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponseData<T> {

    @JsonProperty("Version")
    private String version;

    @JsonProperty("type")
    private String type;

    @JsonProperty("Result")
    private T result;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
