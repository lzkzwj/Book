package com.atguigu.web;

import com.atguigu.pojo.*;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;
import com.atguigu.utils.JdbcUtils;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderServlet extends BaseServlet {

    private OrderService orderService = new OrderServiceImpl();

    /**
     * 处理分页功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //2.获取page对象
        Page<Order> page = orderService.page(pageNo, pageSize);
        page.setUrl("orderServlet?action=page");
        //3.保存Page对象到Request域中
        req.setAttribute("page",page);
        //4.请求转发到
        req.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(req,resp);
    }

    /**
     * 生成订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.先获取Cart购物车对象和UserId
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        User loginUser = (User) req.getSession().getAttribute("user");
        Integer id = loginUser.getId();

        //2.调用orderService.createOrder(Cart,UserId)生成订单
        String orderId = orderService.createOrder(cart, id);

        req.getSession().setAttribute("orderId",orderId);

        //请求转发到/pages/cart/checkout.jsp
        //req.getRequestDispatcher("pages/cart/checkout.jsp").forward(req,resp);
        //表单多次提交问题因此该使用重定向
        resp.sendRedirect("pages/cart/checkout.jsp");
    }

    /**
     * 查询所有的订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showAllOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取所有订单
        List<Order> orders = orderService.showAllOrders();
        //2.把全部订单保存到request中
        req.setAttribute("orders",orders);
        //3.请求转发到/pages/manager/order_manager.jsp中
        req.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(req,resp);
    }

    /**
     * 发货
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void sendOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取货物的状态和订单的订单号
        String status = req.getParameter("status");
        String orderId = req.getParameter("orderId");
        if ("0".equals(status)&&orderId!=null){
            //2.发货
            orderService.sendOrder(orderId);
        }
        req.getRequestDispatcher("/orderServlet?action=showAllOrders").forward(req,resp);
    }

    /**
     * 查看订单详情
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取传入的orderId
        String orderId = req.getParameter("orderId");
        //2.查询详细信息
        List<OrderItem> items = orderService.showOrderDetail(orderId);
        //3.保存到request域中
        req.setAttribute("items",items);
        //4.请求转发到/pages/order/order_detail.jsp
        req.getRequestDispatcher("/pages/order/order_detail.jsp").forward(req,resp);
    }

    /**
     * 查看我的订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showMyOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.先获取userId
        User loginUser = (User) req.getSession().getAttribute("user");
        Integer id = loginUser.getId();
        //2.获取我的订单
        List<Order> myOrders = orderService.showMyOrders(id);
        //3.将订单保存到request中
        req.setAttribute("myOrders",myOrders);
        //4.请求转发到/pages/order/order.jsp中
        req.getRequestDispatcher("/pages/order/order.jsp").forward(req,resp);

    }

    /**
     * 签收订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void receiverOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取货物的状态和订单的订单号
        String status = req.getParameter("status");
        String orderId = req.getParameter("orderId");
        if ("1".equals(status)&&orderId!=null){
            //2.签收
            orderService.receiverOrder(orderId);
        }
        req.getRequestDispatcher("/orderServlet?action=showMyOrders").forward(req,resp);
    }
}
