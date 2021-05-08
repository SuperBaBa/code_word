package com.lovelace.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * author:marcus date:2020/7/20
 **/
@Data
@AllArgsConstructor
public class AccessToken {
    /** token */
    private String token;

    /** 失效时间 */
    private Date expireTime;
}
