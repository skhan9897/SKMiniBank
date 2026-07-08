<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bank.dao.LoanDAO"%>

<%
LoanDAO dao = new LoanDAO();

int totalLoans = dao.getTotalLoans();
int approvedLoans = dao.getApprovedLoans();
int pendingLoans = dao.getPendingLoans();
int rejectedLoans = dao.getRejectedLoans();
double totalAmount = dao.getTotalLoanAmount();
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Loan Management Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>

body{
background:#eef3fb;
font-family:Arial,Helvetica,sans-serif;
}

.topbar{

background:linear-gradient(90deg,#0d6efd,#003b8e);

padding:18px;

color:white;

box-shadow:0 3px 10px rgba(0,0,0,.2);

}

.topbar h2{

margin:0;

font-weight:bold;

}

.dashboard{

padding:30px;

}

.summary-card{

border:none;

border-radius:15px;

box-shadow:0 5px 15px rgba(0,0,0,.15);

transition:.3s;

color:white;

}

.summary-card:hover{

transform:translateY(-6px);

}

.bg1{

background:#0d6efd;

}

.bg2{

background:#198754;

}

.bg3{

background:#ffc107;

color:black;

}

.bg4{

background:#dc3545;

}

.bg5{

background:#6f42c1;

}

.icon{

font-size:45px;

margin-bottom:10px;

}

</style>

</head>

<body>

<div class="topbar">

<div class="container-fluid d-flex justify-content-between align-items-center">

<h2>

🏦 SK Mini Bank Loan Management Dashboard

</h2>

<a href="${pageContext.request.contextPath}/admin/SKMiniBank-System.jsp">
    
   
    <i class="fa fa-home"></i></i> Back to Admin
</a>







</a>

</div>

</div>

<div class="container-fluid dashboard">

<div class="row g-4">

<div class="col-lg-3 col-md-6">

<div class="card summary-card bg1">

<div class="card-body text-center">

<i class="fa fa-file-invoice-dollar icon"></i>

<h5>Total Loans</h5>

<h2><%=totalLoans%></h2>

</div>

</div>

</div>

<div class="col-lg-3 col-md-6">

<div class="card summary-card bg2">

<div class="card-body text-center">

<i class="fa fa-circle-check icon"></i>

<h5>Approved Loans</h5>

<h2><%=approvedLoans%></h2>

</div>

</div>

</div>

<div class="col-lg-3 col-md-6">

<div class="card summary-card bg3">

<div class="card-body text-center">

<i class="fa fa-clock icon"></i>

<h5>Pending Loans</h5>

<h2><%=pendingLoans%></h2>

</div>

</div>

</div>

<div class="col-lg-3 col-md-6">

<div class="card summary-card bg4">

<div class="card-body text-center">

<i class="fa fa-circle-xmark icon"></i>

<h5>Rejected Loans</h5>

<h2><%=rejectedLoans%></h2>

</div>

</div>

</div>

<div class="col-lg-12">

<div class="card summary-card bg5">

<div class="card-body text-center">

<i class="fa fa-indian-rupee-sign icon"></i>

<h5>Total Loan Amount</h5>

<h1>₹ <%=totalAmount%></h1>

</div>

</div>

</div>
<!-- Loan Services -->

<div class="row mt-4">

    <div class="col-12">

        <h3 class="fw-bold text-primary mb-4">

            <i class="fa-solid fa-landmark"></i>

            Loan Services

        </h3>

    </div>

    <div class="col-lg-3 col-md-6 mb-4">

        <div class="card shadow border-0 h-100">

            <div class="card-body text-center">

                <i class="fa-solid fa-house fa-4x text-primary mb-3"></i>

                <h4>Home Loan</h4>

                <p>Dream Home Finance</p>

                <a href="loan-apply.jsp?type=Home Loan"
                   class="btn btn-primary w-100">

                    Apply Now

                </a>

            </div>

        </div>

    </div>

    <div class="col-lg-3 col-md-6 mb-4">

        <div class="card shadow border-0 h-100">

            <div class="card-body text-center">

                <i class="fa-solid fa-car fa-4x text-success mb-3"></i>

                <h4>Vehicle Loan</h4>

                <p>Car & Bike Finance</p>

                <a href="loan-apply.jsp?type=Vehicle Loan"
                   class="btn btn-success w-100">

                    Apply Now

                </a>

            </div>

        </div>

    </div>

    <div class="col-lg-3 col-md-6 mb-4">

        <div class="card shadow border-0 h-100">

            <div class="card-body text-center">

                <i class="fa-solid fa-user fa-4x text-warning mb-3"></i>

                <h4>Personal Loan</h4>

                <p>Instant Personal Loan</p>

                <a href="loan-apply.jsp?type=Personal Loan"
                   class="btn btn-warning w-100">

                    Apply Now

                </a>

            </div>

        </div>

    </div>

    <div class="col-lg-3 col-md-6 mb-4">

        <div class="card shadow border-0 h-100">

            <div class="card-body text-center">

                <i class="fa-solid fa-briefcase fa-4x text-danger mb-3"></i>

                <h4>Business Loan</h4>

                <p>Grow Your Business</p>

                <a href="loan-apply.jsp?type=Business Loan"
                   class="btn btn-danger w-100">

                    Apply Now

                </a>

            </div>

        </div>

    </div>

    <div class="col-lg-3 col-md-6 mb-4">

        <div class="card shadow border-0 h-100">

            <div class="card-body text-center">

                <i class="fa-solid fa-graduation-cap fa-4x text-info mb-3"></i>

                <h4>Education Loan</h4>

                <p>Study Without Limits</p>

                <a href="loan-apply.jsp?type=Education Loan"
                   class="btn btn-info w-100">

                    Apply Now

                </a>

            </div>

        </div>

    </div>

    <div class="col-lg-3 col-md-6 mb-4">

        <div class="card shadow border-0 h-100">

            <div class="card-body text-center">

                <i class="fa-solid fa-coins fa-4x text-secondary mb-3"></i>

                <h4>Gold Loan</h4>

                <p>Gold Against Cash</p>

                <a href="loan-apply.jsp?type=Gold Loan"
                   class="btn btn-secondary w-100">

                    Apply Now

                </a>

            </div>

        </div>

    </div>

    <div class="col-lg-3 col-md-6 mb-4">

        <div class="card shadow border-0 h-100">

            <div class="card-body text-center">

                <i class="fa-solid fa-seedling fa-4x text-success mb-3"></i>

                <h4>Agriculture Loan</h4>

                <p>Support For Farmers</p>

                <a href="loan-apply.jsp?type=Agriculture Loan"
                   class="btn btn-success w-100">

                    Apply Now

                </a>

            </div>

        </div>

    </div>

    <div class="col-lg-3 col-md-6 mb-4">

        <div class="card shadow border-0 h-100">

            <div class="card-body text-center">

                <i class="fa-solid fa-building fa-4x text-dark mb-3"></i>

                <h4>Property Loan</h4>

                <p>Commercial Property Loan</p>

                <a href="loan-apply.jsp?type=Property Loan"
                   class="btn btn-dark w-100">

                    Apply Now

                </a>

            </div>

        </div>

    </div>

</div>
<!-- Loan Analytics -->

<div class="row mt-4">

<div class="col-lg-8">

<div class="card shadow border-0">

<div class="card-header bg-primary text-white">

<h4>

<i class="fa-solid fa-chart-line"></i>

Loan Analytics

</h4>

</div>

<div class="card-body">

<div class="row text-center">

<div class="col-md-3">

<h2 class="text-primary"><%=totalLoans%></h2>

<p>Total Loans</p>

</div>

<div class="col-md-3">

<h2 class="text-success"><%=approvedLoans%></h2>

<p>Approved</p>

</div>

<div class="col-md-3">

<h2 class="text-warning"><%=pendingLoans%></h2>

<p>Pending</p>

</div>

<div class="col-md-3">

<h2 class="text-danger"><%=rejectedLoans%></h2>

<p>Rejected</p>

</div>

</div>

<hr>

<h5>

Total Loan Amount

</h5>

<div class="progress" style="height:30px;">

<div class="progress-bar bg-success progress-bar-striped progress-bar-animated"

style="width:100%;">

₹ <%=totalAmount%>

</div>

</div>

</div>

</div>

</div>

<div class="col-lg-4">

<div class="card shadow border-0">

<div class="card-header bg-dark text-white">

<h4>

<i class="fa-solid fa-bolt"></i>

Quick Actions

</h4>

</div>

<div class="card-body d-grid gap-3">

<a href="loan-apply.jsp"

class="btn btn-primary">

<i class="fa fa-plus-circle"></i>

Apply New Loan

</a>

<a href="loan-list.jsp"

class="btn btn-success">

<i class="fa fa-list"></i>

Loan List

</a>

<a href="loan-report.jsp"

class="btn btn-warning">

<i class="fa fa-chart-bar"></i>

Loan Report

</a>

<button onclick="window.print()"

class="btn btn-danger">

<i class="fa fa-print"></i>

Print Dashboard

</button>

</div>

</div>

</div>

</div>

<!-- Loan Search -->

<div class="card shadow border-0 mt-4">

<div class="card-body">

<div class="row">

<div class="col-md-8">

<input type="text"

id="searchLoan"

class="form-control form-control-lg"

placeholder="Search Customer / Account Number..."

onkeyup="searchLoan()">

</div>

<div class="col-md-4">

<a href="loan-apply.jsp"

class="btn btn-primary btn-lg w-100">

<i class="fa fa-plus"></i>

Apply Loan

</a>

</div>

</div>

</div>

</div>

<!-- Loan Applications -->

<div class="card shadow border-0 mt-4">

<div class="card-header bg-primary text-white">

<h4>

<i class="fa fa-table"></i>

Live Loan Applications

</h4>

</div>

<div class="card-body">

<table class="table table-bordered table-hover"

id="loanTable">

<thead class="table-dark">

<tr>

<th>ID</th>

<th>Customer</th>

<th>Account</th>

<th>Loan Type</th>

<th>Amount</th>

<th>Status</th>

<th>Action</th>

</tr>

</thead>

<tbody>
    <%@page import="java.util.List"%>
<%@page import="com.bank.model.Loan"%>

<%
List<Loan> loanList = dao.getAllLoans();

if (loanList != null) {

    for (Loan loan : loanList) {
%>

<tr>

<td><%=loan.getLoanId()%></td>

<td><%=loan.getCustomerName()%></td>

<td><%=loan.getAccountNumber()%></td>

<td><%=loan.getLoanType()%></td>

<td>

₹ <%=loan.getLoanAmount()%>

</td>

<td>

<%

if("Approved".equalsIgnoreCase(loan.getStatus())){

%>

<span class="badge bg-success">

Approved

</span>

<%

}else if("Rejected".equalsIgnoreCase(loan.getStatus())){

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

<td>

<a href="<%=request.getContextPath()%>/ApproveLoanServlet?id=<%=loan.getLoanId()%>"

class="btn btn-success btn-sm">

<i class="fa fa-check"></i>

</a>

<a href="<%=request.getContextPath()%>/RejectLoanServlet?id=<%=loan.getLoanId()%>"

class="btn btn-warning btn-sm">

<i class="fa fa-times"></i>

</a>

<a href="<%=request.getContextPath()%>/DeleteLoanServlet?id=<%=loan.getLoanId()%>"

class="btn btn-danger btn-sm"

onclick="return confirm('Delete this loan?');">

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

<script>

function searchLoan(){

let input=document.getElementById("searchLoan").value.toUpperCase();

let table=document.getElementById("loanTable");

let tr=table.getElementsByTagName("tr");

for(let i=1;i<tr.length;i++){

let td=tr[i].getElementsByTagName("td")[2];

if(td){

let txt=td.textContent||td.innerText;

tr[i].style.display=

txt.toUpperCase().indexOf(input)>-1?"":"none";

}

}

}

</script>

<div class="card shadow border-0 mt-4">

<div class="card-body text-center">

<h5>

🏦 SK Mini Bank Loan Management System

</h5>

<p>

Professional Loan Dashboard

</p>

<p>

Generated :

<%=new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date())%>

</p>

</div>

</div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>