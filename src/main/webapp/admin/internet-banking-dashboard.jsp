<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bank.dao.InternetBankingDAO"%>

<%
InternetBankingDAO dao = new InternetBankingDAO();

int totalRequests = dao.getTotalRequests();
int approvedRequests = dao.getApprovedRequests();
int pendingRequests = dao.getPendingRequests();
int rejectedRequests = dao.getRejectedRequests();
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Internet Banking Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>

body{
background:#eef3fb;
font-family:Arial,Helvetica,sans-serif;
}

.header{

background:linear-gradient(90deg,#0d6efd,#003b8e);

padding:20px;

color:white;

box-shadow:0 5px 15px rgba(0,0,0,.20);

}

.dashboard{

padding:30px;

}

.card-box{

border:none;

border-radius:18px;

box-shadow:0 5px 15px rgba(0,0,0,.15);

transition:.3s;

color:white;

}

.card-box:hover{

transform:translateY(-6px);

}

.icon{

font-size:45px;

margin-bottom:10px;

}

.blue{

background:#0d6efd;

}

.green{

background:#198754;

}

.orange{

background:#ffc107;

color:black;

}

.red{

background:#dc3545;

}

</style>

</head>

<body>

<div class="header">

<div class="container-fluid d-flex justify-content-between">

<h2>

<i class="fa-solid fa-globe"></i>

Internet Banking Dashboard

</h2>

<a href="dashboard.jsp"

class="btn btn-light">

<i class="fa fa-arrow-left"></i>

Admin Dashboard

</a>

</div>

</div>

<div class="container-fluid dashboard">

<div class="row">

<div class="col-lg-3 col-md-6 mb-4">

<div class="card card-box blue">

<div class="card-body text-center">

<i class="fa fa-users icon"></i>

<h5>Total Requests</h5>

<h2><%=totalRequests%></h2>

</div>

</div>

</div>

<div class="col-lg-3 col-md-6 mb-4">

<div class="card card-box green">

<div class="card-body text-center">

<i class="fa fa-check-circle icon"></i>

<h5>Approved</h5>

<h2><%=approvedRequests%></h2>

</div>

</div>

</div>

<div class="col-lg-3 col-md-6 mb-4">

<div class="card card-box orange">

<div class="card-body text-center">

<i class="fa fa-clock icon"></i>

<h5>Pending</h5>

<h2><%=pendingRequests%></h2>

</div>

</div>

</div>

<div class="col-lg-3 col-md-6 mb-4">

<div class="card card-box red">

<div class="card-body text-center">

<i class="fa fa-times-circle icon"></i>

<h5>Rejected</h5>

<h2><%=rejectedRequests%></h2>

</div>

</div>

</div>

</div>
<!-- Internet Banking Services -->

<div class="row mt-4">

<div class="col-12">

<h3 class="fw-bold text-primary mb-4">

<i class="fa-solid fa-globe"></i>

Internet Banking Services

</h3>

</div>

<div class="col-lg-3 col-md-6 mb-4">

<div class="card shadow border-0 h-100">

<div class="card-body text-center">

<i class="fa-solid fa-user-plus fa-4x text-primary mb-3"></i>

<h4>New Request</h4>

<p>Apply Internet Banking</p>

<a href="internet-banking-form.jsp"

class="btn btn-primary w-100">

Apply Now

</a>

</div>

</div>

</div>

<div class="col-lg-3 col-md-6 mb-4">

<div class="card shadow border-0 h-100">

<div class="card-body text-center">

<i class="fa-solid fa-list fa-4x text-success mb-3"></i>

<h4>Request List</h4>

<p>View All Requests</p>

<a href="<%=request.getContextPath()%>/InternetBankingListServlet"

class="btn btn-success w-100">

Open List

</a>

</div>

</div>

</div>

<div class="col-lg-3 col-md-6 mb-4">

<div class="card shadow border-0 h-100">

<div class="card-body text-center">

<i class="fa-solid fa-print fa-4x text-danger mb-3"></i>

<h4>Print Report</h4>

<p>Print Dashboard</p>

<button onclick="window.print()"

class="btn btn-danger w-100">

Print

</button>

</div>

</div>

</div>

<div class="col-lg-3 col-md-6 mb-4">

<div class="card shadow border-0 h-100">

<div class="card-body text-center">

<i class="fa-solid fa-rotate fa-4x text-warning mb-3"></i>

<h4>Refresh</h4>

<p>Reload Dashboard</p>

<button onclick="location.reload()"

class="btn btn-warning w-100">

Refresh

</button>

</div>

</div>

</div>

</div>

<!-- Quick Summary -->

<div class="card shadow border-0 mt-4">

<div class="card-header bg-primary text-white">

<h4>

<i class="fa fa-chart-line"></i>

Internet Banking Summary

</h4>

</div>

<div class="card-body">

<div class="row text-center">

<div class="col-md-3">

<h2 class="text-primary">

<%=totalRequests%>

</h2>

<p>Total Requests</p>

</div>

<div class="col-md-3">

<h2 class="text-success">

<%=approvedRequests%>

</h2>

<p>Approved</p>

</div>

<div class="col-md-3">

<h2 class="text-warning">

<%=pendingRequests%>

</h2>

<p>Pending</p>

</div>

<div class="col-md-3">

<h2 class="text-danger">

<%=rejectedRequests%>

</h2>

<p>Rejected</p>

</div>

</div>

</div>

</div>
<%@page import="java.util.List"%>
<%@page import="com.bank.model.InternetBanking"%>

<%
List<InternetBanking> requestList = dao.getAllRequests();
%>

<!-- Recent Internet Banking Requests -->

<div class="card shadow border-0 mt-4">

<div class="card-header bg-primary text-white">

<h4>

<i class="fa fa-list"></i>

Recent Internet Banking Requests

</h4>

</div>

<div class="card-body">

<div class="row mb-3">

<div class="col-md-8">

<input type="text"

id="searchRequest"

class="form-control form-control-lg"

placeholder="Search Customer / Account Number..."

onkeyup="searchRequest()">

</div>

<div class="col-md-4">

<a href="internet-banking-form.jsp"

class="btn btn-primary btn-lg w-100">

<i class="fa fa-plus"></i>

New Internet Banking

</a>

</div>

</div>

<div class="table-responsive">

<table class="table table-bordered table-hover"

id="requestTable">

<thead class="table-dark">

<tr>

<th>ID</th>

<th>Customer</th>

<th>Account</th>

<th>Username</th>

<th>Mobile</th>

<th>Status</th>

<th>Date</th>

<th>Action</th>

</tr>

</thead>

<tbody>

<%
if(requestList!=null){

for(InternetBanking ib : requestList){
%>

<tr>

<td><%=ib.getIbId()%></td>

<td><%=ib.getCustomerName()%></td>

<td><%=ib.getAccountNumber()%></td>

<td><%=ib.getUsername()%></td>

<td><%=ib.getMobile()%></td>

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
}
%>

</tbody>

</table>

</div>

</div>

</div>
<script>

function searchRequest(){

    let input =
    document.getElementById("searchRequest").value.toUpperCase();

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

<br>

<div class="row">

<div class="col-md-4 mb-3">

<button onclick="window.print()"
class="btn btn-danger w-100">

<i class="fa fa-print"></i>

Print Dashboard

</button>

</div>

<div class="col-md-4 mb-3">

<button onclick="location.reload()"
class="btn btn-warning w-100">

<i class="fa fa-rotate"></i>

Refresh Dashboard

</button>

</div>

<div class="col-md-4 mb-3">

<a href="<%=request.getContextPath()%>/InternetBankingListServlet"
class="btn btn-success w-100">

<i class="fa fa-list"></i>

View Complete List

</a>

</div>

</div>

<hr>

<div class="card shadow border-0 mt-4">

<div class="card-body text-center">

<h3 class="text-primary">

<i class="fa-solid fa-globe"></i>

SK Mini Bank Internet Banking

</h3>

<p>

Secure • Fast • Digital Banking Services

</p>

<p>

Dashboard Generated :

<%= new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
.format(new java.util.Date()) %>

</p>

</div>

</div>

</div>

<script>

setTimeout(function(){

location.reload();

},30000);

</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>

