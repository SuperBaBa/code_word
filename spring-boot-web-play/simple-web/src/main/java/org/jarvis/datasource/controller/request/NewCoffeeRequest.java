package org.jarvis.consumer.controller.request;

import lombok.Data;
import lombok.ToString;
import org.joda.money.Money;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author marcus
 * @date 2020/8/24-9:56
 */
@ToString
@Data
public class NewCoffeeRequest {
    @NotEmpty
    private String name;
    @NotNull
    private Money price;

}
