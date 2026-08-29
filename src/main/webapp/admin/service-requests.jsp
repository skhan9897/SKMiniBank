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
<title>Service Requests | Admin Console</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
    body { background-color: #f0f4f8; font-family: 'Segoe UI', sans-serif; }
    .table-container { background: white; padding: 25px; border-radius: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.05); }
    .status-badge { font-weight: 700; font-size: 10px; text-transform: uppercase; padding: 6px 12px; border-radius: 30px; letter-spacing: 0.5px; }
    .page-hero { background: linear-gradient(135deg, #102a43, #243b53); color: white; padding: 30px 0; border-radius: 0 0 40px 40px; margin-bottom: 40px; }
</style>
</head>
<body>

<div class="page-hero shadow-sm">
    <div class="container-fluid px-5 d-flex justify-content-between align-items-center">
        <div>
            <h2 class="fw-bold mb-0">Customer Service Hub</h2>
            <p class="mb-0 opacity-75">End-to-end management of all banking requests</p>
        </div>
        <div class="d-flex gap-2">
            <a href="<%=ctx%>/DashboardServlet" class="btn btn-outline-light btn-sm rounded-pill px-3"><i class="fa fa-home"></i></a>
            <a href="<%=ctx%>/AdminAllRequestServlet" class="btn btn-light btn-sm rounded-pill text-primary fw-bold px-4">Refresh Board</a>
        </div>
    </div>
</div>

<div class="container-fluid px-5">

    <% String msg = request.getParameter("msg"); if(msg != null) { %>
    <div class="alert alert-<%= msg.equals("success") ? "success" : "danger" %> rounded-4 shadow-sm border-0 mb-4">
        <i class="fas fa-info-circle me-2"></i> System Message: <%= msg.toUpperCase() %>
    </div>
    <% } %>

    <div class="table-container">
        <table class="table table-hover align-middle">
            <thead class="text-muted small uppercase fw-bold">
                <tr>
                    <th>Ref ID</th>
                    <th>Identity</th>
                    <th>Module</th>
                    <th>Configuration</th>
                    <th>Timeline</th>
                    <th>Current State</th>
                    <th style="width: 320px;">Terminal Control</th>
                </tr>
            </thead>
            <tbody>
            <% if(requestList != null && !requestList.isEmpty()){
                for(ServiceRequest r : requestList){
                    String type = r.getRequestType();
                    String status = r.getStatus() != null ? r.getStatus().toUpperCase() : "PENDING";
            %>
            <tr style="border-bottom: 1px solid #f0f4f8;">
                <td class="fw-bold text-muted">#<%=r.getRequestId()%></td>
                <td>
                    <div class="fw-bold" style="color: #102a43;"><%=r.getAccountNumber()%></div>
                    <div class="small opacity-50">CID: <%=r.getCustomerId()%></div>
                </td>
                <td><span class="badge bg-primary bg-opacity-10 text-primary px-3 py-2"><%=type%></span></td>
                <td>
                    <div class="small text-muted" style="max-width: 200px;">
                        <%
                        String details = r.getRequestDetails();
                        if (details != null && details.contains("|")) {
                            String[] parts = details.split("\\|");
                            for(String p : parts) {
                                out.print("<div>" + p + "</div>");
                            }
                        } else {
                            out.print(details);
                        }
                        %>
                    </div>
                </td>
                <td class="small text-muted"><%=r.getRequestDate()%></td>
                <td>
                    <%
                    String badge = "bg-warning text-dark";
                    if(status.contains("APPROVED") || status.equals("ACTIVATED") || status.equals("DISBURSED") || status.equals("DELIVERED") || status.equals("VERIFIED")) badge = "bg-success text-white";
                    else if(status.contains("VERIFICATION") || status.equals("DISPATCHED")) badge = "bg-info text-white";
                    else if(status.equals("REJECTED")) badge = "bg-danger text-white";
                    %>
                    <span class="badge <%=badge%> status-badge"><%=status%></span>
                </td>
                <td>
                    <form action="<%=ctx%>/AdminRequestServlet" method="post" class="bg-light p-3 rounded-4 border border-white">
                        <input type="hidden" name="requestId" value="<%=r.getRequestId()%>">
                        <input type="hidden" name="accountNumber" value="<%=r.getAccountNumber()%>">
                        <input type="hidden" name="customerId" value="<%=r.getCustomerId()%>">
                        <input type="hidden" name="requestType" value="<%=type%>">

                        <%
                        String submittedAadhaar = "";
                        String submittedPan = "";
                        if ("KYC_UPDATE".equalsIgnoreCase(type) && details != null && details.contains("|")) {
                            for(String p : details.split("\\|")) {
                                if(p.startsWith("aadhaar:")) submittedAadhaar = p.substring(8);
                                if(p.startsWith("pan:")) submittedPan = p.substring(4);
                            }
                        }
                        %>
                        <input type="hidden" name="submittedAadhaar" value="<%=submittedAadhaar%>">
                        <input type="hidden" name="submittedPan" value="<%=submittedPan%>">

                        <div class="mb-2">
                            <select name="remarks" class="form-select form-select-sm border-0 shadow-sm">
                                <option value="Verified and Approved">Verified and Approved</option>
                                <option value="Documents Under Verification">Documents Under Verification</option>
                                <option value="Request Processed">Request Processed</option>
                                <option value="Application Rejected - Policy">Application Rejected</option>
                            </select>
                        </div>

                        <% if("LOAN".equalsIgnoreCase(type) && (status.equals("APPROVED") || status.equals("DOC_VERIFICATION"))) { %>
                            <input type="number" name="approvedAmount" class="form-control form-control-sm mb-2 shadow-sm border-0" placeholder="Approved Amount">
                        <% } %>

                        <div class="d-flex gap-1">
                            <% if(status.equals("PENDING")) { %>
                                <% if(type.equalsIgnoreCase("LOAN")) { %>
                                    <button type="submit" name="action" value="VERIFY" class="btn btn-info btn-sm text-white flex-fill">Verify</button>
                                <% } else if(type.equalsIgnoreCase("KYC_UPDATE")) { %>
                                    <button type="submit" name="action" value="VERIFY_KYC" class="btn btn-primary btn-sm flex-fill">Verify & Approve KYC</button>
                                <% } %>
                                <button type="submit" name="action" value="APPROVE" class="btn btn-success btn-sm flex-fill">Approve</button>
                                <button type="submit" name="action" value="REJECT" class="btn btn-danger btn-sm flex-fill">Reject</button>
                            <% } else if(status.equals("DOC_VERIFICATION")) { %>
                                <button type="submit" name="action" value="APPROVE" class="btn btn-success btn-sm flex-fill">Approve After Verification</button>
                            <% } else if(status.equals("APPROVED")) { %>
                                <% if(type.equalsIgnoreCase("LOAN")) { %>
                                    <button type="button" onclick="startLoanDisbursement(this.form)" class="btn btn-primary btn-sm w-100">Disburse Funds</button>
                                <% } else if(type.toUpperCase().contains("BANKING")) { %>
                                    <button type="submit" name="action" value="ACTIVATE" class="btn btn-primary btn-sm w-100">Activate Now</button>
                                <% } else if(type.equalsIgnoreCase("ATM_CARD") || type.equalsIgnoreCase("CHEQUE_BOOK")) { %>
                                    <button type="submit" name="action" value="DISPATCH" class="btn btn-info btn-sm text-white w-100"><i class="fas fa-truck me-1"></i> Dispatch</button>
                                <% } else { %>
                                    <button type="submit" name="action" value="ACTIVATE" class="btn btn-primary btn-sm w-100">Complete</button>
                                <% } %>
                            <% } else if(status.equals("DISPATCHED")) { %>
                                <button type="submit" name="action" value="DELIVER" class="btn btn-dark btn-sm w-100"><i class="fas fa-check-double me-1"></i> Confirm Delivery</button>
                            <% } else if(status.equals("REJECTED")) { %>
                                <div class="text-center w-100 small text-danger py-1 fw-bold"><i class="fas fa-times-circle me-1"></i> Rejected</div>
                            <% } else { %>
                                <div class="text-center w-100 small text-success py-1 fw-bold"><i class="fas fa-check-circle me-1"></i> Processed</div>
                            <% } %>
                        </div>
                    </form>
                </td>
            </tr>
            <% } } else { %>
            <tr><td colspan="7" class="text-center py-5 opacity-50">No operations pending in queue.</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<!-- DISBURSEMENT OVERLAY -->
<div id="disburseOverlay" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(16, 42, 67, 0.95); z-index:9999; color:white; flex-direction:column; align-items:center; justify-content:center;">
    <div class="spinner-border text-primary mb-4" style="width: 4rem; height: 4rem;" role="status"></div>
    <h3 class="fw-bold mb-2">Verifying Account & Documents...</h3>
    <p class="opacity-75" id="disburseStatus">Connecting to secure banking network</p>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function startLoanDisbursement(form) {
        const amt = form.querySelector('[name="approvedAmount"]').value;
        if(!amt || amt <= 0) {
            alert("Please enter a valid Approved Amount first.");
            return;
        }

        const overlay = document.getElementById('disburseOverlay');
        const statusTxt = document.getElementById('disburseStatus');
        overlay.style.display = 'flex';

        // 3-Second Banking Simulation
        setTimeout(() => { statusTxt.innerText = "Validation Successful. Transferring Funds..."; }, 1000);
        setTimeout(() => { statusTxt.innerText = "Updating Customer Passbook..."; }, 2000);

        setTimeout(() => {
            const actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'DISBURSE';
            form.appendChild(actionInput);
            form.submit();
        }, 3000);
    }
</script>
</body>
</html>
