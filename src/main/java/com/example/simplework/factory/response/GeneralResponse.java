package com.example.simplework.factory.response;

import com.example.simplework.entity.model.Paging;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.io.Serializable;

@Data
@NoArgsConstructor
@Slf4j
@Builder
@SuppressWarnings("squid:S1948")
public class GeneralResponse<T> implements Serializable {

    @JsonProperty("status")
    private ResponseStatus status;

    @JsonProperty("data")
    private T data;

    @JsonProperty("pagingMetadata")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Paging paging;

    public GeneralResponse(T data) {
        this.data = data;
    }

    public GeneralResponse(ResponseStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public GeneralResponse(ResponseStatus status, T data, Paging paging) {
        this.status = status;
        this.data = data;
        this.paging = paging;
    }

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            log.info("Error toString");
        }
        return "";
    }
}
