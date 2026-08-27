<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.bank.model.ServiceRequest"%>

<%
List<ServiceRequest> requestList = (List<ServiceRequest>)request.getAttribute("requestList");
String ctx = request.getContextPath();
if ("/".equals(ctx)) ctx = "";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Service Requests | Admin</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
    body { background-color: #f8f9fa; font-family: 'Segoe UI', sans-serif; }
    .status-badge { font-weight: bold; text-transform: uppercase; font-size: 11px; padding: 5px 10px; border-radius: 20px; }
    .table-container { background: white; padding: 20px; border-radius: 15px; box-shadow: 0 5px 20px rgba(0,0,0,0.05); }
    .page-header { background: #003366; color: white; padding: 20px; border-radius: 0 0 20px 20px; margin-bottom: 30px; }
</style>
</head>
<body>

<div class="page-header shadow">
    <div class="container-fluid d-flex justify-content-between align-items-center">
        <div>
            <h2 class="mb-0"><i class="fas fa-clipboard-list me-2"></i> Service Requests</h2>
            <p class="mb-0 opacity-75 small">Manage ATM, Loan, and Banking requests from customers</p>
        </div>
        <div>
            <a href="<%=ctx%>/DashboardServlet" class="btn btn-outline-light btn-sm me-2"><i class="fa fa-arrow-left"></i> Dashboard</a>
            <a href="<%=ctx%>/AdminAllRequestServlet" class="btn btn-light btn-sm text-primary fw-bold"><i class="fas fa-sync"></i> Refresh List</a>
        </div>
    </div>
</div>

<div class="container-fluid">

    <%
    String msg = request.getParameter("msg");
    if(msg != null) {
        String alertClass = msg.equals("success") ? "alert-success" : (msg.equals("error") ? "alert-danger" : "alert-warning");
        String icon = msg.equals("success") ? "fa-check-circle" : "fa-circle-exclamation";
        String text = msg.equals("success") ? "Request processed successfully!" : (msg.equals("error") ? "Error: " + request.getParameter("error") : "Status: " + msg);
    %>
    <div class="alert <%=alertClass%> alert-dismissible fade show shadow-sm mb-4">
        <i class="fas <%=icon%> me-2"></i> <%=text%>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <% } %>

    <div class="table-container shadow-sm">
        <table class="table table-hover align-middle">
            <thead class="table-light">
                <tr>
                    <th>ID</th>
                    <th>Customer & Account</th>
                    <th>Service Type</th>
                    <th>Request Details</th>
                    <th>Requested On</th>
                    <th>Status</th>
                    <th style="width: 300px;">Actions & Updates</th>
                </tr>
            </thead>
            <tbody>
            <%
            if(requestList != null && !requestList.isEmpty()){
                for(ServiceRequest r : requestList){
            %>
            <tr>
                <td class="fw-bold">#<%=r.getRequestId()%></td>
                <td>
                    <div class="fw-bold text-primary"><%=r.getAccountNumber()%></div>
                    <small class="text-muted">CID: <%=r.getCustomerId()%></small>
                </td>
                <td><span class="badge bg-dark px-3 py-2"><%=r.getRequestType()%></span></td>
                <td>
                    <div class="small text-truncate" style="max-width: 250px;" title="<%=r.getRequestDetails()%>">
                        <%=r.getRequestDetails()%>
                    </div>
                </td>
                <td class="small"><%=r.getRequestDate()%></td>
                <td>
                    <%
                    String status = r.getStatus() != null ? r.getStatus().toUpperCase() : "PENDING";
                    String badgeClass = status.equals("APPROVED") ? "bg-success" : (status.equals("PENDING") ? "bg-warning text-dark" : "bg-danger");
                    if(status.equals("DISPATCHED")) badgeClass = "bg-info text-white";
                    if(status.equals("DELIVERED")) badgeClass = "bg-primary";
                    %>
                    <span class="badge <%=badgeClass%> status-badge"><%=status%></span>
                </td>
                <td>
                    <form action="<%=ctx%>/AdminRequestServlet" method="post" class="p-2 border rounded bg-light border-secondary-subtle">
                        <input type="hidden" name="requestId" value="<%=r.getRequestId()%>">

                        <div class="mb-2">
                            <select name="remarks" class="form-select form-select-sm">
                                <option value="Verified and Approved">Verified and Approved</option>
                                <option value="Documents Verified">Documents Verified</option>
                                <option value="Request Processed Successfully">Request Processed Successfully</option>
                                <option value="Item Sent for Dispatch">Item Sent for Dispatch</option>
                                <option value="Incomplete Information">Incomplete Information</option>
                                <option value="Application Rejected">Application Rejected</option>
                                <option value="Policy Violation">Policy Violation</option>
                            </select>
                        </div>

                        <% if("PENDING".equalsIgnoreCase(r.getStatus()) || "APPROVED".equalsIgnoreCase(r.getStatus())) { %>
                        <div class="mb-2">
                            <label class="small fw-bold opacity-75">Expected Delivery (Optional):</label>
                            <input type="date" name="expectedDelivery" class="form-control form-control-sm">
                        </div>
                        <% } %>

                        <div class="d-flex flex-wrap gap-1">
                            <% if("PENDING".equalsIgnoreCase(r.getStatus())) { %>
                                <button type="submit" class="btn btn-success btn-sm flex-fill" name="action" value="APPROVE"><i class="fa fa-check"></i> Approve</button>
                                <button type="submit" class="btn btn-danger btn-sm flex-fill" name="action" value="REJECT"><i class="fa fa-times"></i> Reject</button>
                            <% } else if("APPROVED".equalsIgnoreCase(r.getStatus())) { %>
                                <button type="submit" class="btn btn-info btn-sm text-white w-100" name="action" value="DISPATCH"><i class="fa fa-truck"></i> Dispatch Item</button>
                            <% } else if("DISPATCHED".equalsIgnoreCase(r.getStatus())) { %>
                                <button type="submit" class="btn btn-primary btn-sm w-100" name="action" value="DELIVER"><i class="fa fa-hand-holding"></i> Mark Delivered</button>
                            <% } else { %>
                                <div class="text-center w-100 small text-muted fst-italic">No further actions</div>
                            <% } %>
                        </div>
                    </form>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="7" class="text-center py-5">
                    <i class="fas fa-inbox fa-3x text-muted mb-3"></i>
                    <h5 class="text-muted">No Service Requests Found</h5>
                    <p class="small">When customers apply for services, they will appear here.</p>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
