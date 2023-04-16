package com.atguigu.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();

    static {
        try {
            Properties properties = new Properties();
            // 读取 jdbc.properties属性配置文件
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            // 从流中加载数据
            properties.load(inputStream);
            // 创建 数据库连接 池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取数据库连接池中的连接
     * @return 如果返回null,说明获取连接失败<br/>有值就是获取连接成功
     */
    public static Connection getConnection(){
        Connection conn = conns.get();
        //如果此时的ThreadLocal没有保存连接,则获取连接
        if (conn==null){
            try {
                conn = dataSource.getConnection();//从数据库中获取连接
                conns.set(conn); //保存到ThreadLocal对象中，供后面的jdbc操作
                conn.setAutoCommit(false);//设置为手动管理事务
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 关闭连接，放回数据库连接池
     * @param conn
     */
//    public static void close(Connection conn){
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     * 提交事务，并关闭释放连接
     */
    public static void commitAndClose(){
        Connection conn = conns.get();
        if (conn!=null){//如果不等于null，说明使用过连接操作过数据库
            try {
                conn.commit();//提交事务
                conn.close();//关闭连接释放资源
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        conns.remove();//一定要执行remove()操作，否则会出错
    }
    /**
     * 回滚事务，并关闭释放连接
     */
    public static void rollbackAndClose(){
        Connection conn = conns.get();
        if (conn!=null){//如果不等于null，说明使用过连接操作过数据库
            try {
                conn.rollback();//回滚事务
                conn.close();//关闭连接释放资源
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        conns.remove();
    }



}