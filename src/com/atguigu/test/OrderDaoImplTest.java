package com.atguigu.test;

import com.atguigu.dao.OrderDao;
import com.atguigu.dao.impl.OrderDaoImpl;
import com.atguigu.pojo.Order;
import com.atguigu.utils.JdbcUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class OrderDaoImplTest {

    @Test
    public void savaOrder() {
        OrderDao orderDao = new OrderDaoImpl();
        orderDao.savaOrder(new Order("123456",new Date(),new BigDecimal(100),0,1));
    }

    @Test
    public void queryOrders(){
        OrderDao orderDao = new OrderDaoImpl();
        List<Order> orders = orderDao.queryOrders();
        for (Order or : orders){
            System.out.println(or);
        }
    }

    @Test
    public void sendOrder(){
        OrderDao orderDao = new OrderDaoImpl();
        System.out.println(orderDao.changeOrderStatus("16709394534621"));
        JdbcUtils.commitAndClose();
    }

    @Test
    public void queryOrdersByUserId(){
        OrderDaoImpl orderDao = new OrderDaoImpl();
        System.out.println(orderDao.queryOrdersByUserId(1));
        JdbcUtils.commitAndClose();
    }
}