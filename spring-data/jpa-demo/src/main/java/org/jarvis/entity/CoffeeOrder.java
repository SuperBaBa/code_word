package org.jarvis.entity;

import lombok.*;
import org.jarvis.constant.OrderState;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * author:tennyson date:2020/7/5
 **/
@Data
@ToString(callSuper = true)
@Table(name = "T_ORDER")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CoffeeOrder extends BaseEntity implements Serializable {
    private String customer;

    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
    @OrderBy("id")
    private List<Coffee> items;
    @Enumerated
    @Column(nullable = false)
    private OrderState state;
}
