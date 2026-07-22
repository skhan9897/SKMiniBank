<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.bank.model.ServiceRequest"%>

<%
List<ServiceRequest> requestList =
        (List<ServiceRequest>) request.getAttribute("requestList");

String errorMessage = (String) request.getAttribute("errorMessage");
String successMessage = (String) request.getAttribute("successMessage");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Service Requests</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>

<div class="container mt-4">

<h2 class="mb-4">Service Requests</h2>

<% if(errorMessage != null){ %>
<div class="alert alert-danger">
    <%= errorMessage %>
</div>
<% } %>

<% if(successMessage != null){ %>
<div class="alert alert-success">
    <%= successMessage %>
</div>
<% } %>

<table class="table table-bordered table-hover align-middle">

<thead class="table-dark">

<tr>
    <th>ID</th>
    <th>Customer ID</th>
    <th>Account No</th>
    <th>Service</th>
    <th>Request Date</th>
    <th>Status</th>
    <th>Action</th>
</tr>

</thead>

<tbody>

<%
if(requestList != null && !requestList.isEmpty()){

for(ServiceRequest r : requestList){
%>

<tr>

<td><%=r.getRequestId()%></td>
<td><%=r.getCustomerId()%></td>
<td><%=r.getAccountNumber()%></td>
<td><%=r.getRequestType()%></td>
<td><%=r.getRequestDate()%></td>

<td>

<%
String status = r.getStatus();

if("PENDING".equalsIgnoreCase(status)){
%>
<span class="badge bg-warning text-dark">PENDING</span>
<%
}else if("APPROVED".equalsIgnoreCase(status)){
%>
<span class="badge bg-success">APPROVED</span>
<%
}else if("REJECTED".equalsIgnoreCase(status)){
%>
<span class="badge bg-danger">REJECTED</span>
<%
}else if("DISPATCHED".equalsIgnoreCase(status)){
%>
<span class="badge bg-primary">DISPATCHED</span>
<%
}else if("DELIVERED".equalsIgnoreCase(status)){
%>
<span class="badge bg-dark">DELIVERED</span>
<%
}else{
%>
<span class="badge bg-secondary"><%=status%></span>
<%
}
%>

</td>

<td>

<form action="<%=request.getContextPath()%>/AdminRequestServlet" method="post">

<input type="hidden" name="requestId" value="<%=r.getRequestId()%>">

<textarea
name="remarks"
class="form-control mb-2"
placeholder="Remarks"></textarea>

<input
type="date"
name="expectedDelivery"
class="form-control mb-2">

<div class="d-flex flex-wrap gap-1">

<button class="btn btn-success btn-sm"
type="submit"
name="action"
value="APPROVE">
Approve
</button>

<button class="btn btn-danger btn-sm"
type="submit"
name="action"
value="REJECT">
Reject
</button>

<button class="btn btn-primary btn-sm"
type="submit"
name="action"
value="DISPATCH">
Dispatch
</button>

<button class="btn btn-dark btn-sm"
type="submit"
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

<td colspan="7" class="text-center text-danger">

No Service Requests Found

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