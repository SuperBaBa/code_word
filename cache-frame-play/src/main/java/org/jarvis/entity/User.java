package org.jarvis.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <一句话概述功能>
 *
 * @author Marcus
 * @date 2021/4/29-10:43
 * @description this is function description
 */

@Data
@Entity
public class User implements Serializable {
    private String name;
    private Integer gender;
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
