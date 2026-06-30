<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.bank.dao.FixedDepositDAO"%>
<%@page import="com.bank.model.FixedDeposit"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fixed Deposit List</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<style>
body{
background:#f5f7fa;
}

.card{
margin:30px auto;
box-shadow:0 5px 15px rgba(0,0,0,.2);
}

.table th{
background:#0d6efd;
color:white;
}

h3{
font-weight:bold;
}
</style>

</head>

<body>

<div class="container">

<div class="card">

<div class="card-header bg-primary text-white">

<h3>
<i class="fa-solid fa-piggy-bank"></i>
Fixed Deposit List
</h3>

</div>

<div class="card-body">

<%
String msg=request.getParameter("msg");
if(msg!=null){
%>

<div class="alert alert-success">
<%=msg%>
</div>

<%
}
%>

<table class="table table-bordered table-hover">

<thead>

<tr>

<th>ID</th>
<th>Account No</th>
<th>Customer Name</th>
<th>FD Amount</th>
<th>Interest %</th>
<th>Duration</th>
<th>Maturity Amount</th>
<th>Open Date</th>
<th>Maturity Date</th>
<th>Status</th>

</tr>

</thead>

<tbody>

<%

FixedDepositDAO dao = new FixedDepositDAO();

List<FixedDeposit> list = dao.getAllFixedDeposits();

for(FixedDeposit fd : list){
%>

<tr>

<td><%=fd.getFdId()%></td>

<td><%=fd.getAccountNumber()%></td>

<td><%=fd.getCustomerName()%></td>

<td>₹ <%=fd.getFdAmount()%></td>

<td><%=fd.getInterestRate()%>%</td>

<td><%=fd.getDurationYear()%> Year</td>

<td>₹ <%=fd.getMaturityAmount()%></td>

<td><%=fd.getOpenDate()%></td>

<td><%=fd.getMaturityDate()%></td>

<td>

<span class="badge bg-success">

<%=fd.getStatus()%>

</span>

</td>

</tr>

<%

}

%>

</tbody>

</table>

<a href="dashboard.jsp" class="btn btn-primary">
<i class="fa fa-arrow-left"></i>
Back To Dashboard
</a>

</div>

</div>

</div>

</body>
</html>