<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的订单</title>

	<%-- 静态包含 base标签、css样式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>


	<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
</style>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">我的订单</span>

		<%--静态包含，登录 成功之后的菜单 --%>
		<%@ include file="/pages/common/login_success_menu.jsp"%>
		<script type="text/javascript">
			$(function () {
				// 给发货的a标签绑定单击事件，用于发货的确认提示操作
				$("a.receive").click(function () {
					// 在事件的function函数中，有一个this对象。这个this对象，是当前正在响应事件的dom对象。
					/**
					 * confirm是确认提示框函数
					 * 参数是它的提示内容
					 * 它有两个按钮，一个确认，一个是取消。
					 * 返回true表示点击了，确认，返回false表示点击取消。
					 */
					return confirm("你确定要签收吗?");
					// return false// 阻止元素的默认行为===不提交请求
				});
			});
		</script>

	</div>
	
	<div id="main">
		
		<table>
			<tr>
				<td>日期</td>
				<td>金额</td>
				<td>详情</td>
				<td>状态</td>
			</tr>

			<c:forEach items="${requestScope.myOrders}" var="order">
				<tr>
					<td>${order.createTime}</td>
					<td>${order.prices}</td>
					<td><a href="orderServlet?action=showOrderDetail&orderId=${order.orderId}">查看详情</a></td>
						<%--如果是未发货的状态则点一下就发货物--%>
					<td><a class="receive" href="orderServlet?action=receiverOrder&status=${order.status}&orderId=${order.orderId}">
						<c:if test="${0==order.status}">未发货</c:if>
						<c:if test="${1==order.status}">已发货</c:if>
						<c:if test="${2==order.status}">已签收</c:if>
					</a></td>
				</tr>
			</c:forEach>
		</table>
		
	
	</div>


	<%--静态包含页脚内容--%>
	<%@include file="/pages/common/footer.jsp"%>


</body>
</html>