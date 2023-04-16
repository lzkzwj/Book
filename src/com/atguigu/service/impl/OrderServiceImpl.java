package com.atguigu.service.impl;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderItemDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.dao.impl.OrderDaoImpl;
import com.atguigu.dao.impl.OrderItemDaoImpl;
import com.atguigu.pojo.*;
import com.atguigu.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {
        //先保存订单
        //订单号的唯一性：
        String orderId = System.currentTimeMillis()+""+userId;
        //创建一个订单对象
        Order order = new Order(orderId,new Date(),cart.getTotalPrice(),0,userId);
        //保存订单
        orderDao.savaOrder(order);

        //遍历购物车中每一个商品项转换成为订单项保存到数据库
        for (Map.Entry<Integer,CartItem> entry:cart.getItems().entrySet()){
            CartItem cartItem = entry.getValue();
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
            //保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);

            //修改图书的销量跟库存
            Book book = bookDao.queryBookById(cartItem.getId());
            book.setSales(book.getSales()+cartItem.getCount());
            book.setStock(book.getStock()-cartItem.getCount());
            bookDao.updateBook(book);
        }

        //清空购物车
        cart.clear();
        return orderId;
    }

    @Override
    public List<Order> showAllOrders() {
        return orderDao.queryOrders();
    }

    @Override
    public int sendOrder(String orderId) {
        return orderDao.changeOrderStatus(orderId);
    }

    @Override
    public List<OrderItem> showOrderDetail(String orderId) {
        return orderItemDao.queryOrderItemByOrderId(orderId);
    }

    @Override
    public int receiverOrder(String orderId) {
        return orderDao.changeOrderStatus(orderId);
    }

    @Override
    public List<Order> showMyOrders(Integer userId) {
        return orderDao.queryOrdersByUserId(userId);
    }

    @Override
    public Page<Order> page(int pageNo, int pageSize) {
        Page<Order> page = new Page<Order>();

        //设置每页显示的数量
        page.setPageSize(pageSize);
        //设置总记录数
        Integer pageTotalCount = orderDao.queryForPageTotalCount();
        //设置总记录数
        page.setPageTotalCount(pageTotalCount);
        //求总页码
        Integer pageTotal = pageTotalCount/pageSize;
        if (pageTotalCount%pageSize > 0){
            pageTotal+=1;
        }
        //设置总页码
        page.setPageTotal(pageTotal);

        //设置当前页码
        page.setPageNo(pageNo);

        //求当前页数据的开始索引
        int begin = (page.getPageNo()-1)*pageSize;

        //获取当页的数据
        List<Order> orders = orderDao.queryForPageOrders(begin, pageSize);
        //设置当页数据
        page.setItems(orders);

        return page;
    }
}
