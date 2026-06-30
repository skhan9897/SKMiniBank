<%@page import="java.util.List"%>
<%@page import="com.bank.dao.TransactionDAO"%>
<%@page import="com.bank.model.Transaction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
TransactionDAO dao = new TransactionDAO();

String account = request.getParameter("account");

List<Transaction> list;

if(account != null && !account.trim().equals("")){
    list = dao.searchTransaction(account);
}else{
    list = dao.getAllTransactions();
}
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Transactions</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

</head>

<body class="bg-light">

<div class="container mt-4">

<div class="card shadow">

<div class="card-header bg-primary text-white">

<h3>
<i class="fa-solid fa-money-bill-transfer"></i>
Transaction History
</h3>

</div>

<div class="card-body">

<form method="get" class="row mb-3">

<div class="col-md-4">

<input type="text"
name="account"
class="form-control"
placeholder="Enter Account Number">

</div>

<div class="col-md-2">

<button class="btn btn-success">

Search

</button>

</div>

<div class="col-md-2">

<a href="transaction.jsp"
class="btn btn-secondary">

Refresh

</a>

</div>

</form>

<table class="table table-bordered table-hover">

<thead class="table-dark">

<tr>

<th>ID</th>
<th>Account</th>
<th>Name</th>
<th>Type</th>
<th>Amount</th>
<th>Balance</th>
<th>Date</th>
<th>Status</th>

</tr>

</thead>

<tbody>

<%
for(Transaction t : list){
%>

<tr>

<td><%=t.getTransactionId()%></td>

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
SUCCESS
</span>

<%
}else{
%>

<span class="badge bg-danger">
FAILED
</span>

<%
}
%>

</td>

</tr>

<%
}
%>

</tbody>

</table>

<a href="dashboard.jsp"
class="btn btn-primary">

Back To Dashboard

</a>

</div>

</div>

</div>

</body>
</html>