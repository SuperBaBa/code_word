package org.jarvis.consumer.model;

import lombok.*;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author marcus
 * @date 2020/8/24-11:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "T_ORDER")
public class CoffeeOrder extends org.jarvis.consumer.model.BaseEntity implements Serializable {
    private String customer;
    @Enumerated
    @Column(nullable = false)
    private org.jarvis.consumer.model.OrderState orderState;
    private Money price;
    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
    @OrderBy(value = "id")
    private List<org.jarvis.consumer.model.Coffee> items;
}
