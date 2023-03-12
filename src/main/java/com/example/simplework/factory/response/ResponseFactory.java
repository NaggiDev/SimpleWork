package com.example.simplework.factory.response;

import com.example.simplework.constant.ResponseStatusCodeEnum;
import com.example.simplework.entity.model.Paging;
import com.google.api.client.http.HttpStatusCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.CheckReturnValue;
import java.util.Map;

@SuppressWarnings("squid:S1874")
@Component
@Slf4j
public class ResponseFactory {

    private String replaceParams(String message, Map<String, String> params) {
        // replace params in message
        if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                message = message.replaceAll("%%" + param.getKey() + "%%", param.getValue());
            }
        }
        return message;
    }

    /**
     * Parsing status code & message to response object
     */
    public ResponseStatus parseResponseStatus(ResponseStatusCodeEnum code, Map<String, String> params) {
        ResponseStatus responseStatus = new ResponseStatus(code.getCode(), true);
        responseStatus.setMessage(replaceParams(responseStatus.getMessage(), params));
        log.debug(responseStatus.toString());

        return responseStatus;
    }

    @CheckReturnValue
    public ResponseEntity<Object> success(GeneralResponse<Object> responseObject) {
        ResponseStatus responseStatus = parseResponseStatus(ResponseStatusCodeEnum.SUCCESS, null);
        responseObject.setStatus(responseStatus);
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(responseObject);
    }

    @CheckReturnValue
    public ResponseEntity<Object> success(Object data, Paging paging) {
        ResponseStatus responseStatus = parseResponseStatus(ResponseStatusCodeEnum.SUCCESS, null);
        GeneralResponse<Object> responseObject = new GeneralResponse<>(responseStatus, data, paging);
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(responseObject);
    }

    @CheckReturnValue
    public ResponseEntity<Object> success(Object data) {
        ResponseStatus responseStatus = parseResponseStatus(ResponseStatusCodeEnum.SUCCESS, null);
        GeneralResponse<Object> responseObject = new GeneralResponse<>(responseStatus, data);
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(responseObject);
    }

    @CheckReturnValue
    public ResponseEntity<Object> successWithMessage(ResponseStatusCodeEnum code, Map<String, String> map) {
        ResponseStatus responseStatus = parseResponseStatus(code, map);
        responseStatus.setCode(ResponseStatusCodeEnum.SUCCESS.getCode(), false);
        GeneralResponse<Object> responseObject = new GeneralResponse<>(responseStatus, null);
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(responseObject);
    }

    @CheckReturnValue
    public ResponseEntity<Object> fail(Object data, Class<?> clazz, ResponseStatusCodeEnum code) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setData(clazz.cast(data));
        return fail(responseObject, code, null);
    }

    @CheckReturnValue
    public ResponseEntity<Object> fail(String errorCode) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        return fail(responseObject, errorCode, null);
    }

    @CheckReturnValue
    public ResponseEntity<Object> fail(String errorCode, Object data, Class<?> clazz) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setData(clazz.cast(data));
        return fail(responseObject, errorCode, null);
    }

    @CheckReturnValue
    public ResponseEntity<Object> fail(Object data, Class<?> clazz, ResponseStatusCodeEnum code, Map<String, String> param) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setData(clazz.cast(data));
        return fail(responseObject, code, param);
    }

    @CheckReturnValue
    public ResponseEntity<Object> fail(GeneralResponse<Object> responseObject, ResponseStatusCodeEnum code) {
        return fail(responseObject, code, null);
    }

    @CheckReturnValue
    public ResponseEntity<Object> fail(GeneralResponse<Object> responseObject, ResponseStatusCodeEnum code, Map<String, String> params) {
        ResponseStatus responseStatus = parseResponseStatus(code, params);
        responseObject.setStatus(responseStatus);
        return ResponseEntity.status(code.getHttpCode()).body(responseObject);
    }

    @CheckReturnValue
    public ResponseEntity<Object> fail(GeneralResponse<Object> responseObject, String errorCode, String errorMessage) {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setCode(errorCode);
        responseStatus.setMessage(errorMessage);
        responseStatus.setDisplayMessage(errorMessage);
        responseObject.setStatus(responseStatus);
        return ResponseEntity.status(HttpStatusCodes.STATUS_CODE_OK).body(responseObject);
    }

    @CheckReturnValue
    public ResponseEntity<Object> fail(ResponseStatusCodeEnum code) {
        return fail(null, Object.class, code);
    }


    @CheckReturnValue
    public ResponseEntity<Object> success() {
        return success(new GeneralResponse<>());
    }
}
