<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bank.model.Loan"%>

<%
Loan loan = (Loan)request.getAttribute("loan");

if(loan==null){

response.sendRedirect("loan-list.jsp");

return;

}
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Loan Profile | SK MINI BANK</title>

<meta name="viewport"
content="width=device-width, initial-scale=1">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">

<style>

body{

background:#eef3f8;

}

.container-box{

width:95%;

margin:auto;

margin-top:25px;

}

.profile-card{

background:white;

border-radius:15px;

box-shadow:0 0 15px rgba(0,0,0,.15);

overflow:hidden;

}

.profile-header{

background:#0b4f8a;

padding:20px;

color:white;

}

.profile-header h3{

margin:0;

font-weight:bold;

}

.photo{

width:120px;

height:120px;

border-radius:50%;

background:#f2f2f2;

margin:auto;

display:flex;

align-items:center;

justify-content:center;

font-size:55px;

color:#0b4f8a;

border:4px solid white;

}

.table th{

width:35%;

background:#f7f7f7;

}

.badge{

font-size:15px;

padding:8px 14px;

}

</style>

</head>

<body>
    <%
String success = request.getParameter("success");
String error = request.getParameter("error");
%>

<% if(success != null){ %>

<div class="alert alert-success alert-dismissible fade show m-3">

    <%= success %>

    <button type="button"
            class="btn-close"
            data-bs-dismiss="alert"></button>

</div>

<% } %>

<% if(error != null){ %>

<div class="alert alert-danger alert-dismissible fade show m-3">

    <%= error %>

    <button type="button"
            class="btn-close"
            data-bs-dismiss="alert"></button>

</div>

<% } %>

<div class="container-box">

<div class="profile-card">

<div class="profile-header">

<h3>

🏦 SK MINI BANK

</h3>

<h5>

Loan Application Profile

</h5>

</div>

<div class="row p-4">

<div class="col-md-3 text-center">

<div class="photo">

<i class="fa fa-user"></i>

</div>

<h4 class="mt-3">

<%=loan.getCustomerName()%>

</h4>

<p>

Account No :

<b>

<%=loan.getAccountNumber()%>

</b>

</p>

</div>

<div class="col-md-9">

<table class="table table-bordered">
    
    <tr>
    <th>Loan ID</th>
    <td>LN-<%=loan.getLoanId()%></td>
</tr>

<tr>
    <th>Customer ID</th>
    <td><%=loan.getCustomerId()%></td>
</tr>

<tr>
    <th>Customer Name</th>
    <td><%=loan.getCustomerName()%></td>
</tr>

<tr>
    <th>Account Number</th>
    <td><%=loan.getAccountNumber()%></td>
</tr>

<tr>
    <th>Loan Type</th>
    <td>
        <span class="badge bg-primary">
            <%=loan.getLoanType()%>
        </span>
    </td>
</tr>

<tr>
    <th>Loan Amount</th>
    <td>
        <strong class="text-success">
            ₹ <%=String.format("%,.2f", loan.getLoanAmount())%>
        </strong>
    </td>
</tr>

<tr>
    <th>Interest Rate</th>
    <td>
        <%=loan.getInterestRate()%> %
    </td>
</tr>

<tr>
    <th>Loan Duration</th>
    <td>
        <%=loan.getDurationYear()%> Years
    </td>
</tr>

<tr>
    <th>Apply Date</th>
    <td>
        <%=loan.getApplyDate()%>
    </td>
</tr>

<tr>
    <th>Loan Status</th>

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
            Pending Approval
        </span>

    <%
        }
    %>

    </td>

</tr>

</table>
   <!-- ================= ACTION BUTTONS ================= -->

<div class="text-center mt-4 mb-4">

    <!-- Customer Profile -->
    <a href="<%=request.getContextPath()%>/CustomerProfileServlet?customerId=<%=loan.getCustomerId()%>"
       class="btn btn-primary me-2">
        <i class="fa fa-user"></i> Customer Profile
    </a>

    <!-- Loan Documents -->
    <a href="<%=request.getContextPath()%>/LoanDocumentsServlet?loanId=<%=loan.getLoanId()%>"
       class="btn btn-info me-2">
        <i class="fa fa-folder-open"></i> Loan Documents
    </a>

    <!-- Verify Documents -->
    <a href="<%=request.getContextPath()%>/LoanDocumentVerifyServlet?loanId=<%=loan.getLoanId()%>"
       class="btn btn-success me-2"
       onclick="return confirm('Verify all loan documents?');">
        <i class="fa fa-check-circle"></i> Verify Documents
    </a>

    <!-- Approve Loan -->
    <a href="<%=request.getContextPath()%>/LoanApproveServlet?loanId=<%=loan.getLoanId()%>"
       class="btn btn-success me-2"
       onclick="return confirm('Approve this loan and transfer amount to customer account?');">
        <i class="fa fa-check"></i> Approve Loan
    </a>

    <!-- Reject Loan -->
    <a href="<%=request.getContextPath()%>/LoanRejectServlet?loanId=<%=loan.getLoanId()%>"
       class="btn btn-danger me-2"
       onclick="return confirm('Reject this loan application?');">
        <i class="fa fa-times"></i> Reject Loan
    </a>

    <!-- Print -->
    <button onclick="window.print()"
            class="btn btn-secondary me-2">
        <i class="fa fa-print"></i> Print
    </button>

    <!-- Download PDF -->
    <a href="<%=request.getContextPath()%>/LoanPdfServlet?loanId=<%=loan.getLoanId()%>"
       class="btn btn-warning me-2">
        <i class="fa fa-file-pdf"></i> Download PDF
    </a>

    <!-- Back -->
    <a href="<%=request.getContextPath()%>/LoanListServlet"
       class="btn btn-dark">
        <i class="fa fa-arrow-left"></i> Back
    </a>

</div>

<!-- ================= END ACTION BUTTONS ================= -->

<hr>

<div class="alert alert-info mt-4">

    <h5>
        <i class="fa fa-circle-info"></i>
        Loan Approval Process
    </h5>

    <ul class="mb-0">

        <li>Verify customer details before approval.</li>

        <li>Verify documents and KYC status.</li>

        <li>After approval, the loan amount will be credited to the customer's account.</li>

        <li>A transaction entry will be created automatically.</li>

        <li>The customer will receive a notification about the loan decision.</li>

    </ul>

</div>

</div>

</div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>