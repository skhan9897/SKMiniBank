<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.bank.model.Loan"%>
<%@page import="com.bank.dao.LoanDAO"%>

<%
LoanDAO dao = new LoanDAO();

List<Loan> loanList = dao.getAllLoans();

int totalLoan = dao.getTotalLoans();
int approvedLoan = dao.getApprovedLoans();
int pendingLoan = dao.getPendingLoans();
int rejectedLoan = dao.getRejectedLoans();

double totalAmount = dao.getTotalLoanAmount();
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Loan Management | SK Mini Bank</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">

<style>

*{
margin:0;
padding:0;
box-sizing:border-box;
font-family:'Segoe UI',sans-serif;
}

body{

background:#eef3f8;

}

.sidebar{

position:fixed;

top:0;

left:0;

width:250px;

height:100vh;

background:#0b4f8a;

padding-top:20px;

}

.sidebar h3{

color:white;

text-align:center;

margin-bottom:30px;

font-weight:bold;

}

.sidebar a{

display:block;

padding:14px 20px;

color:white;

text-decoration:none;

transition:.3s;

}

.sidebar a:hover{

background:#1565c0;

padding-left:28px;

}

.main{

margin-left:250px;

padding:20px;

}

.topbar{

background:white;

padding:18px;

border-radius:10px;

box-shadow:0 2px 10px rgba(0,0,0,.1);

display:flex;

justify-content:space-between;

align-items:center;

margin-bottom:20px;

}

.topbar h2{

color:#0b4f8a;

font-weight:bold;

}

.search-box{

width:300px;

}

</style>

</head>

<body>

<div class="sidebar">

<h3>🏦 SK MINI BANK</h3>

<a href="dashboard.jsp">

<i class="fa fa-home"></i>

Dashboard

</a>

<a href="customer-list.jsp">

<i class="fa fa-users"></i>

Customers

</a>

<a href="loan-list.jsp" class="active">

<i class="fa fa-money-bill-wave"></i>

Loan Management

</a>

<a href="logout.jsp">

<i class="fa fa-sign-out-alt"></i>

Logout

</a>

</div>

<div class="main">

<div class="topbar">

<h2>

Loan Management

</h2>

<input type="text"
id="searchInput"
class="form-control search-box"
placeholder="Search Loan...">

</div>
    <!-- Dashboard Cards -->

<div class="row g-4 mb-4">

    <!-- Total Loan -->
    <div class="col-lg-2 col-md-4 col-sm-6">

        <div class="card shadow border-0">

            <div class="card-body text-center">

                <i class="fa fa-file-invoice-dollar fa-2x text-primary mb-2"></i>

                <h6>Total Loans</h6>

                <h3 class="text-primary">
                    <%=totalLoan%>
                </h3>

            </div>

        </div>

    </div>

    <!-- Pending -->
    <div class="col-lg-2 col-md-4 col-sm-6">

        <div class="card shadow border-0">

            <div class="card-body text-center">

                <i class="fa fa-clock fa-2x text-warning mb-2"></i>

                <h6>Pending</h6>

                <h3 class="text-warning">
                    <%=pendingLoan%>
                </h3>

            </div>

        </div>

    </div>

    <!-- Approved -->
    <div class="col-lg-2 col-md-4 col-sm-6">

        <div class="card shadow border-0">

            <div class="card-body text-center">

                <i class="fa fa-circle-check fa-2x text-success mb-2"></i>

                <h6>Approved</h6>

                <h3 class="text-success">
                    <%=approvedLoan%>
                </h3>

            </div>

        </div>

    </div>

    <!-- Rejected -->
    <div class="col-lg-2 col-md-4 col-sm-6">

        <div class="card shadow border-0">

            <div class="card-body text-center">

                <i class="fa fa-circle-xmark fa-2x text-danger mb-2"></i>

                <h6>Rejected</h6>

                <h3 class="text-danger">
                    <%=rejectedLoan%>
                </h3>

            </div>

        </div>

    </div>

    <!-- Total Amount -->

    <div class="col-lg-4 col-md-12">

        <div class="card shadow border-0">

            <div class="card-body text-center">

                <i class="fa fa-indian-rupee-sign fa-2x text-info mb-2"></i>

                <h6>Total Loan Amount</h6>

                <h3 class="text-info">

                    &#8377; <%=String.format("%,.2f", totalAmount)%>

                </h3>

            </div>

        </div>

    </div>

</div>
                    <!-- Loan List Table -->

<div class="card shadow border-0">

    <div class="card-header bg-primary text-white">

        <h5 class="mb-0">

            <i class="fa fa-list"></i>

            Loan Application List

        </h5>

    </div>

    <div class="card-body table-responsive">

        <table class="table table-bordered table-hover align-middle"
               id="loanTable">

            <thead class="table-dark">

            <tr>

                <th>Loan ID</th>

                <th>Customer Name</th>

                <th>Account Number</th>

                <th>Loan Type</th>

                <th>Loan Amount</th>

                <th>Interest</th>

                <th>Duration</th>

                <th>Status</th>

                <th>Apply Date</th>

                <th>Action</th>

            </tr>

            </thead>

            <tbody>

            <%

                if(loanList!=null && !loanList.isEmpty()){

                for(Loan loan : loanList){

            %>

            <tr>

                <td>

                    <a href="<%=request.getContextPath()%>/LoanProfileServlet?loanId=<%=loan.getLoanId()%>"

                       class="fw-bold text-decoration-none">

                        LN-<%=loan.getLoanId()%>

                    </a>

                </td>

                <td>

                    <%=loan.getCustomerName()%>

                </td>

                <td>

                    <%=loan.getAccountNumber()%>

                </td>

                <td>

                    <%=loan.getLoanType()%>

                </td>

                <td>

                    ₹ <%=String.format("%,.2f",loan.getLoanAmount())%>

                </td>

                <td>

                    <%=loan.getInterestRate()%> %

                </td>

                <td>

                    <%=loan.getDurationYear()%> Years

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

                    <%=loan.getApplyDate()%>

                </td>

                <td>

                    <a href="<%=request.getContextPath()%>/LoanProfileServlet?loanId=<%=loan.getLoanId()%>"

                       class="btn btn-primary btn-sm">

                        <i class="fa fa-eye"></i>

                        View

                    </a>

                </td>

            </tr>

            <%

                }

                }else{

            %>

            <tr>

                <td colspan="10" class="text-center text-danger">

                    No Loan Applications Found

                </td>

            </tr>

            <%

                }

            %>

            </tbody>

        </table>

    </div>

</div>
            
                    <!-- Action Toolbar -->

<div class="row mt-4 mb-3">

    <div class="col-md-6">

        <button class="btn btn-success"
                onclick="window.location.reload();">

            <i class="fa fa-rotate"></i>

            Refresh

        </button>

        <button class="btn btn-primary"
                onclick="window.print();">

            <i class="fa fa-print"></i>

            Print

        </button>

        <button class="btn btn-danger"
                onclick="downloadPDF();">

            <i class="fa fa-file-pdf"></i>

            Export PDF

        </button>

    </div>

    <div class="col-md-6 text-end">

        <input type="text"
               id="searchLoan"
               class="form-control"
               style="max-width:320px;float:right;"
               placeholder="Search Customer / Account / Loan">

    </div>

</div>

<script>

document.getElementById("searchLoan")
.addEventListener("keyup", function(){

let filter = this.value.toUpperCase();

let table = document.getElementById("loanTable");

let tr = table.getElementsByTagName("tr");

for(let i=1;i<tr.length;i++){

let show = false;

let td = tr[i].getElementsByTagName("td");

for(let j=0;j<td.length;j++){

if(td[j]){

let txt = td[j].textContent || td[j].innerText;

if(txt.toUpperCase().indexOf(filter)>-1){

show = true;

}

}

}

tr[i].style.display = show ? "" : "none";

}

});

function downloadPDF(){

window.location.href="<%=request.getContextPath()%>/LoanReportServlet";

}

</script>

<hr>

<div class="text-center mt-3">

<h6 class="text-secondary">

SK MINI BANK • Loan Management System

</h6>

<p class="text-muted">

Manage Customer Loan Applications Efficiently

</p>

</div>

</div>

</body>

</html>