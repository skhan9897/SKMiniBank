<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.bank.model.ChequeBookRequest"%>

<%
List<ChequeBookRequest> list =
(List<ChequeBookRequest>)request.getAttribute("list");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cheque Book Requests</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>

<div class="container mt-4">

<h2 class="mb-4">Cheque Book Requests</h2>

<table class="table table-bordered table-hover">

<thead class="table-dark">

<tr>

<th>ID</th>
<th>Customer ID</th>
<th>Customer Name</th>
<th>Account Number</th>
<th>Mobile</th>
<th>Leaves</th>
<th>Address</th>
<th>Status</th>
<th>Action</th>

</tr>

</thead>

<tbody>

<%

if(list!=null && !list.isEmpty()){

for(ChequeBookRequest r:list){

%>

<tr>

<td><%=r.getId()%></td>

<td><%=r.getCustomerId()%></td>

<td><%=r.getCustomerName()%></td>

<td><%=r.getAccountNumber()%></td>

<td><%=r.getMobile()%></td>

<td><%=r.getLeaves()%></td>

<td><%=r.getAddress()%></td>

<td>

<%

if("PENDING".equalsIgnoreCase(r.getStatus())){

%>

<span class="badge bg-warning text-dark">Pending</span>

<%

}else if("APPROVED".equalsIgnoreCase(r.getStatus())){

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

<form action="../AdminChequeBookServlet" method="post">

<input type="hidden" name="id" value="<%=r.getId()%>">

<textarea
class="form-control mb-2"
name="remarks"
placeholder="Remarks"></textarea>

<input
type="date"
name="expectedDelivery"
class="form-control mb-2">

<div class="d-flex gap-1 flex-wrap">

<button
class="btn btn-success btn-sm"
name="action"
value="APPROVE">

Approve

</button>

<button
class="btn btn-danger btn-sm"
name="action"
value="REJECT">

Reject

</button>

<button
class="btn btn-primary btn-sm"
name="action"
value="DISPATCH">

Dispatch

</button>

<button
class="btn btn-dark btn-sm"
name="action"
value="DELIVER">

Delivered

</button>

</div>

</form>

</td>

</tr>

<%

}

}else{

%>

<tr>

<td colspan="9" class="text-center">

No Cheque Book Requests Found

</td>

</tr>

<%

}

%>

</tbody>

</table>

</div>

</body>
</html>