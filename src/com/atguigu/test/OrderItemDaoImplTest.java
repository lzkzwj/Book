package com.atguigu.test;

import com.atguigu.dao.OrderItemDao;
import com.atguigu.dao.impl.OrderItemDaoImpl;
import com.atguigu.pojo.OrderItem;
import com.atguigu.utils.JdbcUtils;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderItemDaoImplTest {

    @Test
    public void saveOrderItem() {
        OrderItemDao orderItemDao = new OrderItemDaoImpl();
        orderItemDao.saveOrderItem(new OrderItem(null,"java从入门到精通",1,new BigDecimal(100),new BigDecimal(100),"123456"));
        orderItemDao.saveOrderItem(new OrderItem(null,"番茄炒蛋",2,new BigDecimal(50),new BigDecimal(100),"123456"));
    }

    @Test
    public void queryOrderItemByOrderId(){
        OrderItemDao orderItemDao = new OrderItemDaoImpl();
        System.out.println(orderItemDao.queryOrderItemByOrderId("16709394534621"));
        JdbcUtils.commitAndClose();
    }
}