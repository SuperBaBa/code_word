package org.jarvis.consumer.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * author:tennyson  date:2020/8/8
 */
@Table(name = "T_ORDER")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CoffeeOrder extends org.jarvis.consumer.model.BaseEntity implements Serializable {
    private String customer;
    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
    @OrderBy("id")
    private List<org.jarvis.consumer.model.Coffee> items;
    @Enumerated
    @Column(nullable = false)
    private org.jarvis.consumer.model.OrderState state;
}
