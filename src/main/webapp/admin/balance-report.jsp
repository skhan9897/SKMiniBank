<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.bank.model.Account" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detailed Balance Report | Admin Panel</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
    .balance-header { background: linear-gradient(45deg, #ffc107, #b88b00); color: #333; padding: 40px 20px; border-radius: 15px; margin-bottom: 30px; }
    .table-container { background: white; padding: 25px; border-radius: 15px; box-shadow: 0 5px 20px rgba(0,0,0,0.05); }
</style>
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <a href="${pageContext.request.contextPath}/DashboardServlet" class="btn btn-outline-dark"><i class="fas fa-arrow-left"></i> Back to Dashboard</a>
        <button onclick="window.print()" class="btn btn-primary"><i class="fas fa-print"></i> Print Report</button>
    </div>

    <div class="balance-header text-center shadow">
        <i class="fas fa-money-bill-wave fa-3x mb-3"></i>
        <h1 class="fw-bold">Total Bank Deposit Balance</h1>
        <h2 class="display-4 mt-2">₹ ${String.format("%.2f", totalBalance)}</h2>
        <p class="mb-0 mt-3 opacity-75">Updated as of <%= new java.util.Date() %></p>
    </div>

    <div class="table-container">
        <h4 class="mb-4 fw-bold text-dark border-bottom pb-2">Customer-wise Balance Breakdown</h4>
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Customer Name</th>
                        <th>Account Number</th>
                        <th>Account Status</th>
                        <th class="text-end">Current Balance</th>
                        <th class="text-center">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    List<Account> balanceList = (List<Account>) request.getAttribute("balanceList");
                    if (balanceList != null && !balanceList.isEmpty()) {
                        for (Account a : balanceList) {
                    %>
                    <tr>
                        <td class="fw-bold"><%= a.getCustomerName() %></td>
                        <td><code><%= a.getAccountNumber() %></code></td>
                        <td>
                            <% if ("ACTIVE".equalsIgnoreCase(a.getStatus())) { %>
                                <span class="badge bg-success">Active</span>
                            <% } else { %>
                                <span class="badge bg-danger"><%= a.getStatus() %></span>
                            <% } %>
                        </td>
                        <td class="text-end fw-bold text-success">₹ <%= String.format("%.2f", a.getBalance()) %></td>
                        <td class="text-center">
                            <a href="${pageContext.request.contextPath}/TransactionServlet?accountNumber=<%= a.getAccountNumber() %>" class="btn btn-sm btn-info text-white">
                                <i class="fas fa-history"></i> History
                            </a>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr><td colspan="5" class="text-center py-4">No account data found.</td></tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <footer class="text-center mt-5 text-muted">
        &copy; 2026 SK Mini Bank | Confidential Admin Report
    </footer>
</div>

</body>
</html>
