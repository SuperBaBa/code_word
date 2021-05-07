package org.jarvis.consumer.seckill.entity;

import java.util.Date;

/**
 * @author tennyson
 * @date 2020/10/5-15:35
 */
public class StockOrder {
    private int id;
    private int stockId;
    private String goodName;
    private Date CreateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }
}
