<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.bank.model.ChequeBookRequest"%>

<%
List<ChequeBookRequest> chequeList =
(List<ChequeBookRequest>)request.getAttribute("chequeList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Requests</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>

<div class="container mt-4">

<h2 class="mb-4">My Service Requests</h2>

<div class="card">

<div class="card-header bg-primary text-white">
Cheque Book Requests
</div>

<div class="card-body">

<table class="table table-bordered table-hover">

<thead class="table-dark">

<tr>

<th>ID</th>
<th>Request Date</th>
<th>Status</th>
<th>Remarks</th>
<th>Expected Delivery</th>

</tr>

</thead>

<tbody>

<%
if (chequeList != null && !chequeList.isEmpty()) {

    for (ChequeBookRequest r : chequeList) {
%>

<tr>

<td><%=r.getId()%></td>

<td><%=r.getRequestDate()%></td>

<td>

<%
if ("PENDING".equalsIgnoreCase(r.getStatus())) {
%>

<span class="badge bg-warning text-dark">Pending</span>

<%
} else if ("APPROVED".equalsIgnoreCase(r.getStatus())) {
%>

<span class="badge bg-success">Approved</span>

<%
} else {
%>

<span class="badge bg-danger">Rejected</span>

<%
}
%>

</td>

<td><%=r.getRemarks()==null?"-":r.getRemarks()%></td>

<td><%=r.getExpectedDelivery()==null?"-":r.getExpectedDelivery()%></td>

</tr>

<%
    }

} else {
%>

<tr>

<td colspan="5" class="text-center">

No Cheque Book Requests Found

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