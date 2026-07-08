<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.bank.model.InternetBanking"%>

<%
List<InternetBanking> requestList =
(List<InternetBanking>)request.getAttribute("requestList");

if(requestList==null){
    requestList=new ArrayList<InternetBanking>();
}
%>

<%
out.println("RequestList = " + request.getAttribute("requestList"));
out.println("<br>Size = " + requestList.size());
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>SK Mini Bank | Internet Banking List</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>

body{

background:#eef3fb;

font-family:Arial,sans-serif;

}

.container{

width:96%;

margin:20px auto;

}

.header{

background:linear-gradient(90deg,#0d6efd,#004aad);

padding:18px;

color:#fff;

border-radius:10px 10px 0 0;

}

.table-box{

background:#fff;

padding:20px;

box-shadow:0 4px 15px rgba(0,0,0,.15);

border-radius:0 0 10px 10px;

}

.search{

margin-bottom:20px;

}

.badge-approved{

background:#198754;

color:#fff;

padding:6px 12px;

border-radius:20px;

}

.badge-pending{

background:#ffc107;

color:#000;

padding:6px 12px;

border-radius:20px;

}

.badge-rejected{

background:#dc3545;

color:#fff;

padding:6px 12px;

border-radius:20px;

}

.action a{

margin-right:5px;

}

.footer{

margin-top:25px;

text-align:center;

font-size:14px;

color:#555;

}

</style>

</head>

<body>

<div class="container">

<div class="header d-flex justify-content-between align-items-center">

<div>

<h3>

<i class="fa-solid fa-globe"></i>

Internet Banking Requests

</h3>

<p class="mb-0">

Manage Internet Banking Applications

</p>

</div>

<div>

<a href="internet-banking-dashboard.jsp"

class="btn btn-light">

<i class="fa fa-home"></i>

Dashboard

</a>

</div>

</div>

<div class="table-box">

<div class="row search">

<div class="col-md-6">

<input type="text"

id="search"

class="form-control"

placeholder="Search Customer / Account / Username...">

</div>

<div class="col-md-6 text-end">

<a href="internet-banking-form.jsp"

class="btn btn-primary">

<i class="fa fa-plus"></i>

New Request

</a>

</div>

</div>

<table class="table table-bordered table-hover"

id="requestTable">

<thead class="table-primary">

<tr>

<th>ID</th>

<th>Customer</th>

<th>Account</th>

<th>Mobile</th>

<th>Email</th>

<th>Username</th>

<th>Status</th>

<th>Date</th>

<th>Action</th>

</tr>

</thead>

<tbody>
    
    <%
for(InternetBanking ib : requestList){
%>

<tr>

<td><%=ib.getIbId()%></td>

<td><%=ib.getCustomerName()%></td>

<td><%=ib.getAccountNumber()%></td>

<td><%=ib.getMobile()%></td>

<td><%=ib.getEmail()%></td>

<td><%=ib.getUsername()%></td>

<td>

<%
if("Approved".equalsIgnoreCase(ib.getStatus())){
%>

<span class="badge-approved">
Approved
</span>

<%
}else if("Rejected".equalsIgnoreCase(ib.getStatus())){
%>

<span class="badge-rejected">
Rejected
</span>

<%
}else{
%>

<span class="badge-pending">
Pending
</span>

<%
}
%>

</td>

<td><%=ib.getCreatedDate()%></td>

<td class="action">

<a href="<%=request.getContextPath()%>/ApproveInternetBankingServlet?id=<%=ib.getIbId()%>"
class="btn btn-success btn-sm">

<i class="fa fa-check"></i>

Approve

</a>

<a href="<%=request.getContextPath()%>/RejectInternetBankingServlet?id=<%=ib.getIbId()%>"
class="btn btn-warning btn-sm">

<i class="fa fa-times"></i>

Reject

</a>

<a href="<%=request.getContextPath()%>/DeleteInternetBankingServlet?id=<%=ib.getIbId()%>"
class="btn btn-danger btn-sm"
onclick="return confirm('Delete this request?');">

<i class="fa fa-trash"></i>

Delete

</a>

</td>

</tr>

<%
}
%>

</tbody>

</table>

<div class="row mt-4">

<div class="col-md-4">

<button onclick="window.print()"
class="btn btn-primary w-100">

<i class="fa fa-print"></i>

Print Report

</button>

</div>

<div class="col-md-4">

<a href="<%=request.getContextPath()%>/InternetBankingDashboardServlet"
class="btn btn-success w-100">

<i class="fa fa-chart-bar"></i>

Dashboard

</a>

</div>

<div class="col-md-4">

<a href="<%=request.getContextPath()%>/admin/internet-banking-form.jsp"
   class="btn btn-primary">
    <i class="fa fa-plus"></i> New Request
</a>

</div>

</div>
</div>

<div class="footer">

<hr>

<h5 class="text-primary">

<i class="fa-solid fa-building-columns"></i>

SK Mini Bank Internet Banking Management System

</h5>

<p>

Generated :

<%= new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
.format(new java.util.Date()) %>

</p>

<p>

&copy; 2026 SK Mini Bank | Developed By Sajid Khan

</p>

</div>

</div>

<script>

document.getElementById("search").addEventListener("keyup", function(){

let value=this.value.toLowerCase();

let rows=document.querySelectorAll("#requestTable tbody tr");

rows.forEach(function(row){

let text=row.innerText.toLowerCase();

row.style.display=text.indexOf(value)>-1?"":"none";

});

});

</script>

<script>

setInterval(function(){

location.reload();

},30000);

</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>