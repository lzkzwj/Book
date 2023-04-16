package com.atguigu.dao;

import com.atguigu.pojo.Order;
import com.atguigu.pojo.Page;

import java.util.List;

public interface OrderDao {

    //保存订单
    public int savaOrder(Order order);

    //查询所有订单
    public List<Order> queryOrders();

    //发货
    public int changeOrderStatus(String orderId);

    //查询个人订单
    public List<Order> queryOrdersByUserId(Integer userId);

    //查询所有订单的数量
    public Integer queryForPageTotalCount();

    //分页查询所有订单
    public List<Order> queryForPageOrders(int begin,int pageSize);
}
