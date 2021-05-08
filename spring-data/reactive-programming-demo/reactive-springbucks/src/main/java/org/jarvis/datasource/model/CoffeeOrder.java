package org.jarvis.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author marcus
 * @date 2020/8/9-8:08
 */
@Data
@Table(value = "T_COFFEE_ORDER")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoffeeOrder implements Serializable {
    private Long id;
    private String customer;
    private org.jarvis.consumer.model.OrderState state;
    private List<org.jarvis.consumer.model.Coffee> items;
    private Date createTime;
    private Date updateTime;
}
