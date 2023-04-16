package com.atguigu.service;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderItem;
import com.atguigu.pojo.Page;

import java.util.List;

public interface OrderService {
    //生成订单
    public String createOrder(Cart cart,Integer userId);
    //查询所有订单
    public List<Order> showAllOrders();
    //发货
    public int sendOrder(String orderId);
    //查看订单详细
    public List<OrderItem> showOrderDetail(String orderId);
    //签收订单
    public int receiverOrder(String orderId);
    //查看我的订单
    public List<Order> showMyOrders(Integer userId);

    //分页功能
    Page<Order> page(int pageNo,int pageSize);
}
