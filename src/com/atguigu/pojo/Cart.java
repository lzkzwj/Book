package com.atguigu.pojo;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//购物车对象
public class Cart {
//    private Integer totalCount;
//    private BigDecimal totalPrice;
    /**
     * key是商品编号
     * value是商品信息
     */
    private Map<Integer,CartItem> items = new LinkedHashMap<>();

    /**
     * 添加商品项
     * @param cartItem
     */
    public void addItem(CartItem cartItem){
        //先查看购物车中是否已经添加过此商品，如果已添加，则数量累加，总金额更新，否则直接放入集合
        CartItem item = items.get(cartItem.getId());
        if (item == null){
            //之前没有添加过此商品
            items.put(cartItem.getId(),cartItem);
        }else {
            item.setCount(item.getCount()+1);
            item.setTotalPrice(item.getTotalPrice().multiply(new BigDecimal(item.getCount())));
        }
    }

    /**
     * 删除商品项
     * @param id
     */
    public void deleteItem(Integer id){
        items.remove(id);
    }

    /**
     * 清空购物车
     */
    public void clear(){
        items.clear();
    }

    /**
     * 修改商品数量
     */
    public void updateCount(Integer id,Integer count){
        //先查看购物车中是否有此商品，如果有，则修改数量，更新总金额
        CartItem cartItem = items.get(id);
        if (cartItem!=null){
            cartItem.setCount(count);
            cartItem.setTotalPrice(cartItem.getTotalPrice().multiply(new BigDecimal(cartItem.getCount())));
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }

    public Integer getTotalCount() {
        Integer totalCount=0;
        for (Map.Entry<Integer,CartItem> entry:items.entrySet()){
            totalCount+=entry.getValue().getCount();
        }
        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Integer,CartItem> entry:items.entrySet()){
            totalPrice=totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }
    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }
}
