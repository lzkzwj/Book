<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑图书</title>

    <%-- 静态包含 base标签、css样式、jQuery文件 --%>
    <%@ include file="/pages/common/head.jsp"%>


    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }

        h1 a {
            color:red;
        }

        input {
            text-align: center;
        }

        #main{
            width: 1250px;
        }
    </style>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" >
    <span class="wel_word">编辑图书</span>

    <%-- 静态包含 manager管理模块的菜单  --%>
    <%@include file="/pages/common/manager_menu.jsp"%>


</div>

<div id="main">
    <form action="manager/bookServlet?action=add" method="post" enctype="multipart/form-data">
        <input type="hidden" name="pageNo" value="${param.pageNo}">
        <input type="hidden" name="id" value="${ requestScope.book.id }" />
        <table>
            <tr>
                <td>名称</td>
                <td>价格</td>
                <td>作者</td>
                <td>销量</td>
                <td>库存</td>
                <td>图片</td>
                <td>操作</td>
            </tr>
            <tr>
                <td><input name="name" type="text"/></td>
                <td><input name="price" type="text"/></td>
                <td><input name="author" type="text"/></td>
                <td><input name="sales" type="text"/></td>
                <td><input name="stock" type="text"/></td>
                <td><input name="imgPath" type="file"></td>
                <td><input type="submit" value="提交"/></td>
            </tr>
        </table>
    </form>
</div>

<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp"%>

</body>
</html>