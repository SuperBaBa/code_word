package org.jarvis.consumer.seckill.entity;

/**
 * @author tennyson
 * @date 2020/10/5-15:20
 * @description 库存实体
 */
public class Stock {
    /**
     * 库存表记录id主键自增
     */
    private int id;
    /**
     * 商品名称
     */
    private String goodName;
    /**
     * 商品库存
     */
    private int stock;
    /**
     * 商品已销售量
     */
    private int sale;
    /**
     * 版本号
     */
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
