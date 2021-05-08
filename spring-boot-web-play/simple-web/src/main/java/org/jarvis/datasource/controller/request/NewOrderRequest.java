package org.jarvis.consumer.controller.request;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author marcus
 * @date 2020/8/24-9:57
 */
@Data
@ToString
public class NewOrderRequest {
    private String customer;
    private List<String> items;
}
