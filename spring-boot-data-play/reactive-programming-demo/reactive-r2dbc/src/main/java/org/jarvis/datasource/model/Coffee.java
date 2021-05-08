package org.jarvis.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * @author marcus @date:2020/8/4
 **/
@Data
@Table("t_coffee")
@AllArgsConstructor
@NoArgsConstructor
public class Coffee {
    @Id
    private Long id;
    private String name;
    private Money price;
    private Date createTime;
    private Date updateTime;
}
