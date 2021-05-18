package org.jarvis.dto;

import java.io.Serializable;

/**
 * @author marcus
 * @date 2020/11/10-9:41
 */
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public ResponseResult(Builder<T> builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.data = builder.data;
    }

    public static ResponseResult.Builder builder() {
        return new ResponseResult.Builder();
    }

    public static class Builder<T> {
        private int code;
        private String message;
        private T data;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public ResponseResult build() {
            return new ResponseResult(this);
        }

    }
}
