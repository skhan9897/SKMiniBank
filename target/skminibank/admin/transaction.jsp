<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bank.model.Transaction" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transaction History | SK Mini Bank</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{
    background:#f4f7fb;
    font-family:Arial,Helvetica,sans-serif;
}

.card{
    margin-top:25px;
    border:none;
    border-radius:12px;
    box-shadow:0 2px 10px rgba(0,0,0,.15);
}

.card-header{
    background:#0d6efd;
    color:white;
    font-size:22px;
    font-weight:bold;
}

.table th{
    background:#0d6efd;
    color:white;
    text-align:center;
}

.table td{
    text-align:center;
    vertical-align:middle;
}

.search-box{
    margin-bottom:20px;
}

.badge-success{
    background:green;
    color:white;
}

.no-print{
    display:block;
}

@media print{

    .no-print{
        display:none !important;
    }

    .card-header{
        background:#0d6efd !important;
        color:#fff !important;
        -webkit-print-color-adjust:exact;
        print-color-adjust:exact;
    }

}
</style>

</head>

<body>

<div class="container">

<div class="card">

<div class="card-header">
Transaction History
</div>

<div class="no-print">

<form action="<%=request.getContextPath()%>/TransactionServlet" method="get">

    <div class="row search-box">

        <div class="col-md-4">
            <input type="text"
                   name="accountNumber"
                   class="form-control"
                   placeholder="Enter Account Number">
        </div>

        <div class="col-md-2">
            <button class="btn btn-primary w-100">
                Search
            </button>
        </div>

        <div class="col-md-2">
            <a href="<%=request.getContextPath()%>/TransactionServlet"
               class="btn btn-success w-100">
               Refresh
            </a>
        </div>

        <div class="col-md-2">
            <a href="dashboard.jsp"
               class="btn btn-secondary w-100">
               Dashboard
            </a>
        </div>

    </div>

</form>

</div>

<div class="table-responsive">

<table class="table table-bordered table-hover">

<thead>

<tr>

<th>ID</th>
<th>Account No</th>
<th>Customer Name</th>
<th>Type</th>
<th>Amount</th>
<th>Balance</th>
<th>Date</th>
<th>Status</th>

</tr>

</thead>

<tbody>

<%

List<Transaction> list =
(List<Transaction>)request.getAttribute("transactionList");

if(list!=null && !list.isEmpty()){

for(Transaction t:list){

%>

<tr>

<td><%=t.getId()%></td>

<td><%=t.getAccountNumber()%></td>

<td><%=t.getCustomerName()%></td>

<td><%=t.getTransactionType()%></td>

<td>₹ <%=t.getAmount()%></td>

<td>₹ <%=t.getBalance()%></td>

<td><%=t.getTransactionDate()%></td>

<td>

<%

if("SUCCESS".equalsIgnoreCase(t.getStatus())){

%>

<span class="badge bg-success">
<%=t.getStatus()%>
</span>

<%

}else{

%>

<span class="badge bg-danger">
<%=t.getStatus()%>
</span>

<%

}

%>

</td>

</tr>

<%

}

}else{

%>

<tr>

<td colspan="8" class="text-center text-danger">

No Transaction Found

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

</div>


<div class="text-center mt-4 no-print">   

 <%
String accountNumber = (String) request.getAttribute("accountNumber");

if (accountNumber == null || accountNumber.trim().isEmpty()) {

    List<Transaction> txList =
        (List<Transaction>) request.getAttribute("transactionList");

    if (txList != null && !txList.isEmpty()) {
        accountNumber = txList.get(0).getAccountNumber();
    }
}
%>

<a href="<%=request.getContextPath()%>/TransactionPDFServlet?accountNumber=<%=accountNumber%>"
   class="btn btn-danger">
    <i class="fa fa-file-pdf"></i> Download PDF
</a>

<button onclick="window.print()" class="btn btn-primary">
    <i class="fa fa-print"></i> Print
</button>

<a href="<%=request.getContextPath()%>/admin/SKMiniBank-System.jsp"
   class="btn btn-secondary">
    <i class="fa fa-arrow-left"></i> Back To Dashboard
</a>
</div>


</body>
</html>
