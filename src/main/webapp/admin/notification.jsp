<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.bank.model.Notification"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Notifications | SK MINI BANK</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container mt-4">

<div class="card shadow">

<div class="card-header bg-primary text-white">

<h4>

<i class="fa fa-bell"></i>

Notifications

</h4>

</div>

<div class="card-body">

<h6>

Unread Notifications :

<span class="badge bg-danger">

${unreadCount}

</span>

</h6>

<hr>

<table class="table table-bordered table-hover">

<thead>

<tr>

<th>ID</th>

<th>Title</th>

<th>Message</th>

<th>Type</th>

<th>Status</th>

<th>Date</th>

</tr>

</thead>

<tbody>

<%

List<Notification> list =
(List<Notification>)request.getAttribute("notificationList");

if(list!=null){

for(Notification n:list){

%>

<tr>

<td><%=n.getNotificationId()%></td>

<td><%=n.getTitle()%></td>

<td><%=n.getMessage()%></td>

<td>

<span class="badge bg-primary">

<%=n.getNotificationType()%>

</span>

</td>

<td>

<%

if(n.getIsRead()==0){

%>

<span class="badge bg-danger">

UNREAD

</span>

<%

}else{

%>

<span class="badge bg-success">

READ

</span>

<%

}

%>

</td>

<td>

<%=n.getCreatedAt()%>

</td>

</tr>

<%

}

}

%>

</tbody>

</table>

<div class="text-center mt-3">

<a href="SKMiniBank-System.jsp"

class="btn btn-secondary">

Back Dashboard

</a>

</div>

</div>

</div>

</div>

</body>

</html>