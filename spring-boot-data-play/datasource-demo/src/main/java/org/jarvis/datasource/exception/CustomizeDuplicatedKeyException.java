package org.jarvis.datasource.exception;

import org.springframework.dao.DuplicateKeyException;

/**
 * 自定义主键冲突异常
 * author:tennyson date:2020/7/7
 **/
public class CustomizeDuplicatedKeyException extends DuplicateKeyException {
    public CustomizeDuplicatedKeyException(String msg) {
        super("主键冲突");
    }

    public CustomizeDuplicatedKeyException(String msg, Throwable cause) {
        super("主键冲突", cause);
    }
}
