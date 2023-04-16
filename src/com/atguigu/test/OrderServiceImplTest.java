package com.atguigu.test;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.CartItem;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderServiceImplTest {

    @Test
    public void createOrder() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"abc",1,new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(1,"abc",1,new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(2,"数据结构",1,new BigDecimal(100),new BigDecimal(100)));

        OrderService orderService = new OrderServiceImpl();
        System.out.println(orderService.createOrder(cart, 1));
    }

    @Test
    public void testPage(){
        OrderServiceImpl os = new OrderServiceImpl();
    }
}