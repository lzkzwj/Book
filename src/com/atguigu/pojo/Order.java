package com.atguigu.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

    private String orderId;
    private Date createTime;
    private BigDecimal prices;
    //0未发货，1已发货，2已签收
    private Integer status = 0;
    private Integer userId;

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", createTime=" + createTime +
                ", prices=" + prices +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }

    public Order(String orderId, Date createTime, BigDecimal prices, Integer status, Integer userId) {
        this.orderId = orderId;
        this.createTime = createTime;
        this.prices = prices;
        this.status = status;
        this.userId = userId;
    }

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal prices) {
        this.prices = prices;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
