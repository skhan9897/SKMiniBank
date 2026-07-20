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

<title>My Cheque Book Requests</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>

<div class="container mt-4">

<h3 class="mb-4">My Cheque Book Requests</h3>

<table class="table table-bordered table-hover">

<thead class="table-dark">

<tr>

<th>ID</th>
<th>Request Date</th>
<th>Leaves</th>
<th>Status</th>
<th>Remarks</th>
<th>Expected Delivery</th>
<th>Dispatched</th>
<th>Delivered</th>

</tr>

</thead>

<tbody>

<%
if(list!=null && !list.isEmpty()){

for(ChequeBookRequest r:list){
%>

<tr>

<td><%=r.getId()%></td>

<td><%=r.getRequestDate()%></td>

<td><%=r.getLeaves()%></td>

<td>

<%
if("PENDING".equals(r.getStatus())){
%>

<span class="badge bg-warning">Pending</span>

<%
}else if("APPROVED".equals(r.getStatus())){
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

<td><%=r.getRemarks()==null?"-":r.getRemarks()%></td>

<td><%=r.getExpectedDelivery()==null?"-":r.getExpectedDelivery()%></td>

<td><%=r.getDispatchedDate()==null?"-":r.getDispatchedDate()%></td>

<td><%=r.getDeliveredDate()==null?"-":r.getDeliveredDate()%></td>

</tr>

<%
}

}else{
%>

<tr>

<td colspan="8" class="text-center">

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