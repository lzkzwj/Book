package com.atguigu.dao.impl;

import com.atguigu.dao.OrderDao;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.Page;

import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public int savaOrder(Order order) {
        String sql = "insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`) values(?,?,?,?,?)";
        return update(sql, order.getOrderId(), order.getCreateTime(), order.getPrices(), order.getStatus(), order.getUserId());
    }

    @Override
    public List<Order> queryOrders() {
        String sql = "select `order_id` orderId , `create_time` createTime, `price` prices, `status`, `user_id` userId from t_order";
        return queryForList(Order.class,sql);
    }

    @Override
    public int changeOrderStatus(String orderId) {
        //判断如果此时status为0，则将其变为1，为1则变成2
        String querySql = "select `status` from t_order where `order_id` = ?";
        //注意这里不能用Integer接受数字，因为Integer没有无参构造方法
        //Integer status = queryForOne(Integer.class, querySql,orderId);
        Order status = queryForOne(Order.class, querySql,orderId);
        if (0==status.getStatus()) {
            String sql = "update t_order set `status`=1 where `order_id` = ?";
            return update(sql,orderId);
        }else if (1==status.getStatus()){
            String sql = "update t_order set `status`=2 where `order_id` = ?";
            return update(sql,orderId);
        }
        return 0;
    }

    @Override
    public List<Order> queryOrdersByUserId(Integer userId) {
        String sql = "select `order_id` orderId , `create_time` createTime, `price` prices, `status`, `user_id` userId from t_order where user_id = ?";
        return queryForList(Order.class,sql,userId);
    }

    @Override
    public Integer queryForPageTotalCount() {
        String sql = "select count(*) from t_order";
        Number count = (Number) queryForSingleValue(sql);
        return count.intValue();
    }

    @Override
    public List<Order> queryForPageOrders(int begin, int pageSize) {
        String sql = "select `order_id` orderId , `create_time` createTime, `price` prices, `status`, `user_id` userId from t_order limit ?,?";
        return queryForList(Order.class,sql,begin,pageSize);
    }
}
