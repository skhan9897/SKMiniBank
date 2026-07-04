<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.bank.model.Account"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Account List | SK Mini Bank</title>

<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>

body{
    background:#f4f7fb;
    font-family:Arial,Helvetica,sans-serif;
}

.header{
    background:#0d6efd;
    color:white;
    padding:15px 25px;
    font-size:24px;
    font-weight:bold;
    box-shadow:0 2px 8px rgba(0,0,0,.2);
}

.card{
    margin:30px;
    border:none;
    border-radius:12px;
    box-shadow:0 3px 12px rgba(0,0,0,.15);
}

.table thead{
    background:#0d6efd;
    color:white;
}

.table tbody tr:hover{
    background:#eef5ff;
}

.btn-back{
    background:#0d6efd;
    color:#fff;
}

.btn-back:hover{
    background:#084db5;
    color:white;
}

.badge-active{
    background:#198754;
}

.badge-inactive{
    background:#dc3545;
}

</style>

</head>

<body>

<div class="header">
    <i class="fa-solid fa-wallet"></i>
    SK Mini Bank - Account List
</div>

<div class="container-fluid">

<div class="card">

<div class="card-header bg-white">

<div class="d-flex justify-content-between">

<h4>
<i class="fa-solid fa-wallet text-primary"></i>
All Bank Accounts
</h4>

<a href="<%=request.getContextPath()%>/admin/dashboard.jsp"
class="btn btn-back">

<i class="fa fa-arrow-left"></i>
Dashboard

</a>

</div>

</div>

<div class="card-body">

<table class="table table-bordered table-hover align-middle">

<thead>

<tr>

<th>ID</th>

<th>Account Number</th>

<th>Customer Name</th>

<th>Account Type</th>

<th>Balance</th>

<th>Status</th>

</tr>

</thead>

<tbody>

<%

List<Account> accountList =
(List<Account>)request.getAttribute("accountList");

if(accountList!=null){

for(Account a : accountList){

%>

<tr>

<td><%=a.getId()%></td>

<td><%=a.getAccountNumber()%></td>

<td><%=a.getCustomerName()%></td>

<td><%=a.getAccountType()%></td>

<td>
₹ <%=String.format("%,.2f",a.getBalance())%>
</td>

<td>

<%

if("ACTIVE".equalsIgnoreCase(a.getStatus())){

%>

<span class="badge badge-active">
Active
</span>

<%

}else{

%>

<span class="badge badge-inactive">
Inactive
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

<td colspan="6" class="text-center text-danger">

No Account Found

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

</body>
</html>