package org.jarvis.datasource.util;

import com.alibaba.druid.util.DruidPasswordCallback;

/**
 * 继承并实现{@link DruidPasswordCallback#getPassword()}进行自定义的加解密方法
 *
 * @author marcus
 * @date 2020/8/9-19:10
 */
public class CustomizedPasswordCallback extends DruidPasswordCallback {
}
