<%@page import="cn.zanezz.book.domain.Book"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>图书列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/book/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
    <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/js/book/list.js'/>"></script>
	
  </head>
  
  <body>
<div>
<table class="ta"  >
  <tr class="tr">
  		<td class="test">书名</td>
  		<td class="test">定价</td>
  		<td class="test">作者</td>
  		<td class="test">出版社</td>
  		<td class="test" id="testright">出版时间</td>
  
  </tr>
<c:forEach items="${pb.beanlist }" var="book">


  

   <tr class="tr">
   		<c:url value="/Bookservlet" var="authorUrl">
		<c:param name="method" value="findByAuthor"></c:param>
		<c:param name="author" value="${book.author }"></c:param>
	</c:url>
	<c:url value="/Bookservlet" var="pressUrl">
		<c:param name="method" value="findByPress"></c:param>
		<c:param name="press" value="${book.press}"></c:param>
	</c:url>
   		<td class="bookna" ><a title="${book.bname }" href="<c:url value='/Bookservlet?method=loadByBid&bid=${book.bid }'/>">${book.bname }</a></td>
   		<td class="td"><p class="price"><span class="price_n">${book.price }</span></p></td>
   		<td class="td"><a href="${authorUrl }" id="author" name='P_zz' title='${book.author }'> ${book.author }</a></td>
		<td class="td"><p class="publishing"><a href="${pressUrl}">${book.press }</a></p></td>
		<td class="tdpub" ><p class="publishing_time">${book.publishtime }</p></td>
		
   </tr>
   
	


</c:forEach>

  


  </table>
</div>

<div style="float:left; width: 100%; text-align: center;">
	<hr/>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
</div>

  </body>
 
</html>

