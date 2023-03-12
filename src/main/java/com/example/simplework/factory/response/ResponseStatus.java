package com.example.simplework.factory.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseStatus implements Serializable {

    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("displayMessage")
    private String displayMessage;

    public ResponseStatus(String code, boolean setMessageImplicitly) {
        setCode(code, setMessageImplicitly);
    }

    /**
     * Set the code
     *
     * @param code
     */
    public void setCode(String code, boolean setMessageImplicitly) {
        this.code = code;
        this.displayMessage = this.message;
    }

    public void replaceMessage(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            map.forEach((k, v) -> message = message.replaceAll("%%" + k + "%%", v));
            displayMessage = message;
        }
    }

    public String getCode() {
        return code;
    }

    /**
     * Set the code. this will implicitly set the message based on the locale
     *
     * @param code
     */
    public void setCode(String code) {
        setCode(code, true);
    }
}
