<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bank.model.Customer"%>

<%
Customer customer = (Customer) request.getAttribute("customer");

if(customer == null){
    response.sendRedirect("customer-list.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Account Opening Receipt</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<style>

body{
    background:#f4f7fb;
}

.receipt-box{
    width:900px;
    margin:30px auto;
    background:#fff;
    border-radius:10px;
    box-shadow:0 0 15px rgba(0,0,0,.15);
    padding:30px;
}

.header{
    text-align:center;
    border-bottom:3px solid #0d6efd;
    padding-bottom:15px;
    margin-bottom:20px;
}

.header h2{
    color:#0d6efd;
    font-weight:bold;
}

.table td,
.table th{
    vertical-align:middle;
}

.footer{
    text-align:center;
    margin-top:25px;
    color:#666;
}

@media print{

.btn-area{
display:none;
}

body{
background:white;
}

.receipt-box{
box-shadow:none;
width:100%;
margin:0;
}

}

</style>

</head>

<body>

<div class="receipt-box">

<div class="header">

<h2>🏦 SK MINI BANK</h2>

<h5>ACCOUNT OPENING RECEIPT</h5>

<p>Generated Date :
<%=new java.util.Date()%></p>

</div>

<h5 class="mb-3 text-primary">

Customer Details

</h5>

<table class="table table-bordered">

<tr>

<th width="30%">Customer Name</th>

<td><%=customer.getFullName()%></td>

</tr>

<tr>

<th>Customer ID</th>

<td><%=customer.getCustomerId()%></td>

</tr>

<tr>

<th>Customer Code</th>

<td><%=customer.getCustomerCode()%></td>

</tr>

<tr>

<th>CIF Number</th>

<td><%=customer.getCifNumber()%></td>

</tr>

<tr>

<th>Account Number</th>

<td><%=customer.getAccountNumber()%></td>

</tr>

<tr>

<th>Account Type</th>

<td><%=customer.getAccountType()%></td>

</tr>

<tr>

<th>Branch</th>

<td><%=customer.getBranch()%></td>

</tr>

<tr>

<th>IFSC Code</th>

<td><%=customer.getIfscCode()%></td>

</tr>
<tr>

<th>Mobile Number</th>

<td><%=customer.getMobile()%></td>

</tr>

<tr>

<th>Email Address</th>

<td><%=customer.getEmail()%></td>

</tr>

<tr>

<th>Opening Balance</th>

<td>₹ <%=customer.getBalance()%></td>

</tr>

<tr>

<th>Account Status</th>

<td>

<span class="badge bg-success">

<%=customer.getStatus()%>

</span>

</td>

</tr>

<tr>

<th>KYC Status</th>

<td>

<span class="badge bg-warning text-dark">

<%=customer.getKycStatus()%>

</span>

</td>

</tr>

</table>

<br>

<h5 class="text-primary">

Internet Banking Login Details

</h5>

<table class="table table-bordered">

<tr>

<th width="30%">Login ID</th>

<td>

<%=customer.getAccountNumber()%>

</td>

</tr>

<tr>

<th>Login Password</th>

<td>

<%=customer.getPassword()%>

</td>

</tr>

<tr>

<th>Transaction PIN</th>

<td>

<%=customer.getTransactionPin()%>

</td>

</tr>

<tr>

<th>UPI ID</th>

<td>

<%=customer.getUpiId()%>

</td>

</tr>

</table>

<div class="alert alert-info">

<b>Important :</b>

Keep this receipt safe.

Do not share your Login Password or Transaction PIN with anyone.

</div>
<!-- Action Buttons -->

<div class="btn-area text-center mt-4">

    <button class="btn btn-primary btn-lg"
            onclick="window.print();">

        <i class="fa fa-print"></i>

        Print Receipt

    </button>

    &nbsp;

    <a href="<%=request.getContextPath()%>/ReceiptPDFServlet?customerId=<%=customer.getCustomerId()%>"
       class="btn btn-danger btn-lg">

        <i class="fa fa-file-pdf"></i>

        Download PDF

    </a>

    &nbsp;

    <a href="<%=request.getContextPath()%>/admin/dashboard.jsp"
       class="btn btn-success btn-lg">

        <i class="fa fa-home"></i>

        Back To Dashboard

    </a>

</div>

<hr>

<div class="row mt-5">

    <div class="col-md-6 text-center">

        __________________________

        <br>

        Customer Signature

    </div>

    <div class="col-md-6 text-center">

        __________________________

        <br>

        Authorized Bank Officer

    </div>

</div>

<div class="footer">

    <h5 class="text-primary">

        🏦 SK MINI BANK

    </h5>

    <p>

        This is a Computer Generated Account Opening Receipt.

    </p>

    <p>

        © 2026 SK Mini Bank. All Rights Reserved.

    </p>

</div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>