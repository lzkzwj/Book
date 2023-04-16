package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Cart;
import com.atguigu.pojo.CartItem;
import com.atguigu.pojo.User;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartServlet extends BaseServlet{

    BookService bookService = new BookServiceImpl();

    /**
     *  加入购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //*如果此时没有登录，则跳转到登录界面

        //1.获取请求的参数，商品编号
        String i = req.getParameter("id");
        //2.调用bookService.queryBookById(id):得到图书信息
        int id = WebUtils.parseInt(i, 0);
        Book book = bookService.queryBookById(id);
        //3.把图书信息，转换成CartItem商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        //4.调用Cart.addItem(CartItem);添加商品项
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart==null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);

        //5.重定向回商品列表页面
        //*获取请求头Referer的值，即访问该程序的网址
        String referer = req.getHeader("Referer");

        //将添加的最后一本书的书名放进session中
        req.getSession().setAttribute("lastName",cartItem.getName());

        resp.sendRedirect(referer);
    }

    /**
     * 删除商品项
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = WebUtils.parseInt(req.getParameter("id"),0);

        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if (cart!=null){
            //删除了购物车商品项
            cart.deleteItem(id);
            //重定向回原来商品所在的地址页面
            resp.sendRedirect(req.getHeader("Referer"));
        }

    }

    /**
     * 清空购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //2.清空购物车
        if (cart!=null){
            cart.clear();
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    /**
     * 修改商品数量
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数，商品编号，商品数量
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        int count = WebUtils.parseInt(req.getParameter("count"),1);
        //2.获取Cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if (cart!=null){
            cart.updateCount(id,count);
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }
}
