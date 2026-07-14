<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bank.model.LoanDocument"%>

<%
LoanDocument doc =
(LoanDocument)request.getAttribute("document");

if(doc==null){

response.sendRedirect("../LoanListServlet");

return;

}
%>
<!DOCTYPE html>
<html>

<head>

<title>Loan Documents | SK Mini Bank</title>

<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

<style>

body{

background:#eef3fb;

}

.card{

border-radius:15px;

box-shadow:0 3px 12px #d8d8d8;

}

.header{

background:#0056b3;

color:white;

padding:15px;

font-size:24px;

font-weight:bold;

border-radius:15px 15px 0 0;

}

</style>

</head>

<body>

<div class="container mt-4">

<div class="card">

<div class="header">

🏦 SK MINI BANK

Loan Documents Verification

</div>

<div class="card-body">
    <table class="table table-bordered">

<tr>

<th>Loan ID</th>

<td><%=doc.getLoanId()%></td>

</tr>

<tr>

<th>Status</th>

<td>

<%=doc.getVerificationStatus()%>

</td>

</tr>

<tr>

<th>Verified By</th>

<td>

<%=doc.getVerifiedBy()==null?"-":doc.getVerifiedBy()%>

</td>

</tr>

</table>
<table class="table table-striped">

<tr>

<th>Document</th>

<th>Action</th>

</tr>

<tr>

<td>Aadhaar Card</td>

<td>

<a class="btn btn-primary btn-sm"

target="_blank"

href="../uploads/<%=doc.getAadhaarFile()%>">

View

</a>

</td>

</tr>

<tr>

<td>PAN Card</td>

<td>

<a class="btn btn-primary btn-sm"

target="_blank"

href="../uploads/<%=doc.getPanFile()%>">

View

</a>

</td>

</tr>

<tr>

<td>Salary Slip</td>

<td>

<a class="btn btn-primary btn-sm"

target="_blank"

href="../uploads/<%=doc.getSalarySlipFile()%>">

View

</a>

</td>

</tr>

<tr>

<td>Bank Statement</td>

<td>

<a class="btn btn-primary btn-sm"

target="_blank"

href="../uploads/<%=doc.getBankStatementFile()%>">

View

</a>

</td>

</tr>

<tr>

<td>Address Proof</td>

<td>

<a class="btn btn-primary btn-sm"

target="_blank"

href="../uploads/<%=doc.getAddressProofFile()%>">

View

</a>

</td>

</tr>

</table>
<div class="text-center">

<a

class="btn btn-success"

href="../LoanDocumentVerifyServlet?loanId=<%=doc.getLoanId()%>">

✔ Verify Documents

</a>

<a

class="btn btn-danger"

href="../LoanDocumentRejectServlet?loanId=<%=doc.getLoanId()%>">

✘ Reject Documents

</a>

<a

class="btn btn-secondary"

href="../LoanProfileServlet?loanId=<%=doc.getLoanId()%>">

⬅ Back

</a>

</div>
</div>

</div>

</div>

</body>

</html>