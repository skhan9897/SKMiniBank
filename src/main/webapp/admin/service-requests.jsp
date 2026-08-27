<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.bank.model.ServiceRequest"%>

<%
List<ServiceRequest> requestList =
(List<ServiceRequest>)request.getAttribute("requestList");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Service Requests</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>

<div class="container-fluid mt-4">

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Service Requests (<%= requestList != null ? requestList.size() : 0 %>)</h2>
    <a href="<%=request.getContextPath()%>/AdminAllRequestServlet" class="btn btn-primary btn-sm">Refresh All</a>
</div>

<table class="table table-bordered table-hover">

<thead class="table-dark">

<tr>

<th>ID</th>
<th>Customer ID</th>
<th>Account No</th>
<th>Service</th>
<th>Details</th>
<th>Request Date</th>
<th>Status</th>
<th>Action</th>

</tr>

</thead>

<tbody>

<%
if(requestList!=null && !requestList.isEmpty()){

for(ServiceRequest r : requestList){
%>

<tr>

<td><%=r.getRequestId()%></td>

<td><%=r.getCustomerId()%></td>

<td><%=r.getAccountNumber()%></td>

<td><span class="badge bg-info text-dark"><%=r.getRequestType()%></span></td>

<td style="max-width: 200px; font-size: 11px;"><%=r.getRequestDetails()%></td>

<td><%=r.getRequestDate()%></td>

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
<% String formCtx = request.getContextPath(); if("/".equals(formCtx)) formCtx = ""; %>
<form action="<%=formCtx%>/AdminRequestServlet" method="post" class="p-2 border rounded bg-light">
    <input type="hidden" name="requestId" value="<%=r.getRequestId()%>">

    <div class="mb-2">
        <label class="form-label small fw-bold">Remarks:</label>
        <textarea name="remarks" class="form-control form-control-sm" placeholder="Add status notes..."><%= r.getRemarks() != null ? r.getRemarks() : "" %></textarea>
    </div>

    <div class="mb-2">
        <label class="form-label small fw-bold">Exp. Delivery Date:</label>
        <input type="date" name="expectedDelivery" class="form-control form-control-sm">
    </div>

    <div class="d-flex flex-wrap gap-1">
        <% if("PENDING".equalsIgnoreCase(r.getStatus())) { %>
            <button type="submit" class="btn btn-success btn-sm flex-fill" name="action" value="APPROVE">Approve</button>
            <button type="submit" class="btn btn-danger btn-sm flex-fill" name="action" value="REJECT">Reject</button>
        <% } else if("APPROVED".equalsIgnoreCase(r.getStatus())) { %>
            <button type="submit" class="btn btn-primary btn-sm w-100" name="action" value="DISPATCH">Dispatch Item</button>
        <% } else if("DISPATCHED".equalsIgnoreCase(r.getStatus())) { %>
            <button type="submit" class="btn btn-dark btn-sm w-100" name="action" value="DELIVER">Mark Delivered</button>
        <% } %>
    </div>
</form>

</td>

</tr>

<%
}

}else{
%>

<tr>

<td colspan="7" class="text-center">

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