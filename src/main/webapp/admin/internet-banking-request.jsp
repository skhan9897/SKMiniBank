<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.bank.dao.InternetBankingDAO"%>
<%@page import="com.bank.model.InternetBanking"%>

<%
InternetBankingDAO dao = new InternetBankingDAO();
List<InternetBanking> internetList = dao.getAllRequests();
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Internet Banking Requests</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>

body{
background:#eef3fb;
font-family:Arial,sans-serif;
}

.card{
border:none;
border-radius:15px;
box-shadow:0 5px 15px rgba(0,0,0,.15);
}

.header{
background:linear-gradient(90deg,#0d6efd,#003b8e);
padding:18px;
color:white;
border-radius:15px 15px 0 0;
}

.table th{
background:#0d6efd;
color:white;
text-align:center;
}

.table td{
vertical-align:middle;
text-align:center;
}

.search-box{
margin:20px 0;
}

.badge-pending{
background:#ffc107;
color:#000;
}

.badge-approved{
background:#198754;
}

.badge-rejected{
background:#dc3545;
}

</style>

</head>

<body>

<div class="container-fluid mt-4">

<div class="card">

<div class="header">

<h2>

<i class="fa-solid fa-globe"></i>

Internet Banking Requests

</h2>

<p>

Manage Internet Banking Applications

</p>

</div>

<div class="card-body">

<div class="row search-box">

<div class="col-md-8">

<input type="text"
id="searchInput"
class="form-control form-control-lg"
placeholder="Search Customer / Account / Username..."
onkeyup="searchTable()">

</div>

<div class="col-md-4">

<a href="internet-banking-request.jsp"
class="btn btn-primary btn-lg w-100">

<i class="fa fa-plus"></i>

New Request

</a>

</div>

</div>

<div class="table-responsive">

<table class="table table-bordered table-hover"
id="requestTable">

<thead>

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
if(internetList != null){

    for(InternetBanking ib : internetList){
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

<span class="badge bg-success">

Approved

</span>

<%
}else if("Rejected".equalsIgnoreCase(ib.getStatus())){
%>

<span class="badge bg-danger">

Rejected

</span>

<%
}else{
%>

<span class="badge bg-warning text-dark">

Pending

</span>

<%
}
%>

</td>

<td><%=ib.getCreatedDate()%></td>

<td>

<a href="<%=request.getContextPath()%>/ApproveInternetBankingServlet?id=<%=ib.getIbId()%>"
class="btn btn-success btn-sm">

<i class="fa fa-check"></i>

</a>

<a href="<%=request.getContextPath()%>/RejectInternetBankingServlet?id=<%=ib.getIbId()%>"
class="btn btn-warning btn-sm">

<i class="fa fa-times"></i>

</a>

<a href="<%=request.getContextPath()%>/DeleteInternetBankingServlet?id=<%=ib.getIbId()%>"
class="btn btn-danger btn-sm"
onclick="return confirm('Delete this request?');">

<i class="fa fa-trash"></i>

</a>

</td>

</tr>

<%
    }

}else{
%>

<tr>

<td colspan="9" class="text-center text-danger">

No Internet Banking Request Found

</td>

</tr>

<%
}
%>

</tbody>

</table>

</div>

</div>

</div>

<script>

function searchTable(){

let input =
document.getElementById("searchInput").value.toUpperCase();

let table =
document.getElementById("requestTable");

let tr =
table.getElementsByTagName("tr");

for(let i=1;i<tr.length;i++){

let td =
tr[i].getElementsByTagName("td")[2];

if(td){

let txt =
td.textContent || td.innerText;

if(txt.toUpperCase().indexOf(input)>-1){

tr[i].style.display="";

}else{

tr[i].style.display="none";

}

}

}

}

</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>