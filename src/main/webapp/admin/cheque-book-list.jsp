<%@page import="java.util.List"%>
<%@page import="com.bank.dao.ChequeBookRequestDAO"%>
<%@page import="com.bank.model.ChequeBookRequest"%>

<%
ChequeBookRequestDAO dao = new ChequeBookRequestDAO();
List<ChequeBookRequest> list = dao.getAllRequests();
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Cheque Book Requests</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/dashboard.css">

</head>

<body>

<div class="container mt-4">

<div class="card shadow">

<div class="card-header bg-primary text-white">

<h4 class="mb-0">Cheque Book Requests</h4>

</div>

<div class="card-body">

<table class="table table-bordered table-hover">

<thead class="table-dark">

<tr>

<th>ID</th>
<th>Account No</th>
<th>Customer Name</th>
<th>Mobile</th>
<th>Leaves</th>
<th>Address</th>
<th>Status</th>
<th>Action</th>

</tr>

</thead>

<tbody>

<%
for(ChequeBookRequest c : list){
%>

<tr>

<td><%=c.getId()%></td>

<td><%=c.getAccountNumber()%></td>

<td><%=c.getCustomerName()%></td>

<td><%=c.getMobile()%></td>

<td><%=c.getLeaves()%></td>

<td><%=c.getAddress()%></td>

<td>

<%
if("Pending".equalsIgnoreCase(c.getStatus())){
%>

<span class="badge bg-warning">Pending</span>

<%
}else if("Approved".equalsIgnoreCase(c.getStatus())){
%>

<span class="badge bg-success">Approved</span>

<%
}else{
%>

<span class="badge bg-danger">Rejected</span>

<%
}
%>

</td>

<td>

<a href="${pageContext.request.contextPath}/ChequeBookStatusServlet?id=<%=c.getId()%>&status=Approved"
class="btn btn-success btn-sm">

Approve

</a>

<a href="${pageContext.request.contextPath}/ChequeBookStatusServlet?id=<%=c.getId()%>&status=Rejected"
class="btn btn-danger btn-sm">

Reject

</a>

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