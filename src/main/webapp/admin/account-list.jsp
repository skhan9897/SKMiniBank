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

<% String backCtx = request.getContextPath(); if("/".equals(backCtx)) backCtx = ""; %>
<a href="<%=backCtx%>/DashboardServlet"
class="btn btn-back">

<i class="fa fa-arrow-left"></i>
Dashboard

</a>

</div>

</div>

<div class="card-body">

<div class="row mb-4">
    <div class="col-md-6">
        <div class="input-group">
            <span class="input-group-text bg-white border-end-0">
                <i class="fa fa-search text-muted"></i>
            </span>
            <input type="text" id="accountSearch" class="form-control border-start-0" placeholder="Search by Name or Account Number...">
        </div>
    </div>
</div>

<table class="table table-bordered table-hover align-middle" id="accountTable">

<thead>

<tr>

<th>ID</th>

<th>Account Number</th>

<th>Customer Name</th>

<th>Account Type</th>

<th>Balance</th>

<th>Status</th>

<th>Action</th>

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

<td><%=a.getAccountId() != 0 ? a.getAccountId() : a.getCustomerId() %></td>

<td>
    <a href="../CustomerProfileServlet?customerId=<%=a.getCustomerId()%>" class="text-decoration-none fw-bold">
        <%=a.getAccountNumber()%>
    </a>
</td>

<td><%=a.getCustomerName()%></td>

<td><%=a.getAccountType()%></td>

<td>
₹ <%=String.format("%,.2f",a.getBalance())%>
</td>

<td>
    <% if("ACTIVE".equalsIgnoreCase(a.getStatus())){ %>
        <span class="badge badge-active">Active</span>
    <% }else{ %>
        <span class="badge badge-inactive">Inactive</span>
    <% } %>
</td>

<td class="text-center">
    <a href="../CustomerProfileServlet?customerId=<%=a.getCustomerId()%>" class="btn btn-sm btn-primary">
        <i class="fa fa-user"></i> Profile
    </a>
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

<script>
document.getElementById('accountSearch').addEventListener('keyup', function() {
    let filter = this.value.toLowerCase();
    let rows = document.querySelectorAll('#accountTable tbody tr');

    rows.forEach(row => {
        let text = row.innerText.toLowerCase();
        row.style.display = text.includes(filter) ? '' : 'none';
    });
});
</script>

</body>
</html>