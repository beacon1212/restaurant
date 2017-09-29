package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "user")
public class user extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ServletContext context=this.getServletContext();     //因为它继承自GenericServlet这个类，依据Tomcat版本不同，可能获取的方法会有些差异

    String value1=context.getInitParameter("encoding");    //通过.getInitParameter(String param)获取初始化参数值
    // JDBC 驱动名及数据库 URL
    String JDBC_DRIVER = context.getInitParameter("JDBC_DRIVER");
    String DB_URL = context.getInitParameter("DB_URL");

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "root";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context=this.getServletContext();     //因为它继承自GenericServlet这个类，依据Tomcat版本不同，可能获取的方法会有些差异

        String value1=context.getInitParameter("encoding");    //通过.getInitParameter(String param)获取初始化参数值

        System.out.println("context value1"+value1);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String title = "Servlet Mysql 测试";
        String docType = "<!DOCTYPE html>\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n");
        try{
            // 注册 JDBC 驱动器
            Class.forName("com.mysql.jdbc.Driver");

            // 打开一个连接
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行 SQL 查询
            stmt = conn.createStatement();
            String sql;
            sql = "select * from user";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String sex = rs.getInt("sex") == 1 ? "男":"女";
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                // 输出数据
                out.println("ID: " + id);
                out.println(", 姓名: " + name);
                out.println(", 年龄: " + age);
                out.println(", 性别: " + sex);
                out.println(", 电话: " + phone);
                out.println(", 邮件: " + email);
                out.println("<br />");
            }
            out.println("</body></html>");

            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch(Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 最后是用于关闭资源的块
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

    }
}
