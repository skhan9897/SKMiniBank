<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SK Mini Bank Admin Dashboard</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
    body { background-color: #f8f9fa; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; }

    .sidebar {
        height: 100vh;
        width: 260px;
        position: fixed;
        top: 0;
        left: 0;
        background-color: #003366;
        color: white;
        padding-top: 20px;
        overflow-y: auto;
        box-shadow: 2px 0 10px rgba(0,0,0,0.1);
    }

    .sidebar .logo-section {
        padding: 0 20px 20px;
        border-bottom: 1px solid rgba(255,255,255,0.1);
        margin-bottom: 20px;
    }

    .sidebar h4 { margin: 0; font-weight: bold; }
    .sidebar p { margin: 0; font-size: 12px; opacity: 0.7; }

    .sidebar a {
        display: block;
        padding: 12px 20px;
        color: rgba(255,255,255,0.8);
        text-decoration: none;
        font-size: 14px;
        transition: 0.3s;
    }

    .sidebar a:hover {
        background-color: #0d6efd;
        color: white;
        padding-left: 30px;
    }

    .sidebar a.active {
        background-color: #0d6efd;
        color: white;
        border-left: 4px solid white;
    }

    .main-content {
        margin-left: 260px;
        padding: 30px;
    }

    .card-summary { border: none; border-radius: 15px; color: #fff; transition: transform 0.3s; margin-bottom: 20px; }
    .card-summary:hover { transform: translateY(-5px); }
    .card-summary.blue { background: linear-gradient(45deg, #0d6efd, #004fb1); }
    .card-summary.green { background: linear-gradient(45deg, #198754, #115c38); }
    .card-summary.yellow { background: linear-gradient(45deg, #ffc107, #b88b00); color: #333; }
    .card-summary.red { background: linear-gradient(45deg, #dc3545, #9c1c2b); }

    .card-summary .card-body { padding: 30px 20px; }
    .card-summary i { font-size: 40px; margin-bottom: 15px; opacity: 0.8; }
    .card-summary h3 { font-size: 32px; font-weight: bold; margin-bottom: 5px; }
    .card-summary p { font-size: 16px; margin: 0; font-weight: 500; }

    .banner-container { margin-top: 30px; }
    .banner-img { width: 100%; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.1); }

    .section-title { font-weight: bold; margin-bottom: 25px; color: #333; }
</style>
</head>
<body>

<div class="sidebar">
    <% String sidebarCtx = request.getContextPath(); if("/".equals(sidebarCtx)) sidebarCtx = ""; %>
    <div class="logo-section">
        <h4>🏦 SK Mini Bank</h4>
        <p>Admin Panel</p>
    </div>

    <a href="<%=sidebarCtx%>/DashboardServlet" class="active">🏠 Dashboard</a>
    <a href="<%=sidebarCtx%>/AdminAllRequestServlet">📋 All Service Requests</a>

    <div style="padding: 10px 20px; font-size: 11px; text-transform: uppercase; letter-spacing: 1px; opacity: 0.5;">Banking Requests</div>
    <a href="<%=sidebarCtx%>/AdminATMRequestServlet">💳 ATM Card Requests</a>
    <a href="<%=sidebarCtx%>/AdminChequeBookListServlet">📒 Cheque Book Requests</a>
    <a href="<%=sidebarCtx%>/AdminNetBankingServlet">🌐 Net Banking Requests</a>
    <a href="<%=sidebarCtx%>/AdminMobileBankingServlet">📱 Mobile Banking Requests</a>
    <a href="<%=sidebarCtx%>/AdminLoanRequestServlet">🏦 Loan Requests</a>

    <div style="padding: 10px 20px; font-size: 11px; text-transform: uppercase; letter-spacing: 1px; opacity: 0.5;">Customer Management</div>
    <a href="<%=sidebarCtx%>/admin/customer-list.jsp">👥 Customers List</a>
    <a href="<%=sidebarCtx%>/admin/edit-customer.jsp">➕ Add New Customer</a>
    <a href="<%=sidebarCtx%>/admin/open-account.jsp">🏦 Open Account</a>
    <a href="<%=sidebarCtx%>/AdminKYCServlet">🪪 KYC Verification</a>

    <div style="padding: 10px 20px; font-size: 11px; text-transform: uppercase; letter-spacing: 1px; opacity: 0.5;">Transactions</div>
    <a href="<%=sidebarCtx%>/admin/deposit.jsp">💰 Deposit Cash</a>
    <a href="<%=sidebarCtx%>/admin/withdraw.jsp">💵 Withdraw Cash</a>
    <a href="<%=sidebarCtx%>/admin/transfer.jsp">🔄 Fund Transfer</a>
    <a href="<%=sidebarCtx%>/admin/fixed-deposit.jsp">📦 Fixed Deposit</a>
    <a href="<%=sidebarCtx%>/admin/loan-dashboard.jsp">🏠 Loan Dashboard</a>

    <div style="padding: 10px 20px; font-size: 11px; text-transform: uppercase; letter-spacing: 1px; opacity: 0.5;">System</div>
    <a href="<%=sidebarCtx%>/NotificationServlet">🔔 Notifications</a>
    <a href="<%=sidebarCtx%>/ReportServlet">📊 Reports</a>
    <a href="<%=sidebarCtx%>/AdminLogoutServlet" style="color: #ff4d4d;">🚪 Logout</a>
</div>

<div class="main-content">
    <h1 class="section-title">Dashboard Overview</h1>

    <div class="container-fluid p-0">
        <div class="row">
            <!-- Total Customers -->
            <div class="col-md-3">
                <a href="${pageContext.request.contextPath}/CustomerListServlet" style="text-decoration:none;">
                    <div class="card card-summary blue shadow text-center">
                        <div class="card-body">
                            <i class="fas fa-users"></i>
                            <h3>${totalCustomers}</h3>
                            <p>Total Customers</p>
                        </div>
                    </div>
                </a>
            </div>

            <!-- Total Accounts -->
            <div class="col-md-3">
                <% String ctx = request.getContextPath(); if("/".equals(ctx)) ctx = ""; %>
                <a href="<%=ctx%>/AccountListServlet" style="text-decoration:none;">
                    <div class="card card-summary green shadow text-center">
                        <div class="card-body">
                            <i class="fas fa-wallet"></i>
                            <h3>${totalAccounts != null ? totalAccounts : 0}</h3>
                            <p>Total Accounts</p>
                        </div>
                    </div>
                </a>
            </div>

            <!-- Total Balance -->
            <div class="col-md-3">
                <a href="${pageContext.request.contextPath}/BalanceReportServlet" style="text-decoration:none;">
                    <div class="card card-summary yellow shadow text-center">
                        <div class="card-body">
                            <i class="fas fa-money-bill-wave"></i>
                            <h3>₹ ${totalBalance}</h3>
                            <p>Total Balance</p>
                        </div>
                    </div>
                </a>
            </div>

            <!-- Transactions -->
            <div class="col-md-3">
                <a href="${pageContext.request.contextPath}/TransactionServlet" style="text-decoration:none;">
                    <div class="card card-summary red shadow text-center">
                        <div class="card-body">
                            <i class="fas fa-exchange-alt"></i>
                            <h3>${totalTransactions}</h3>
                            <p>Transactions</p>
                        </div>
                    </div>
                </a>
            </div>

            <!-- Pending Service Requests -->
            <div class="col-md-3 mt-md-0 mt-3">
                <a href="${pageContext.request.contextPath}/AdminAllRequestServlet" style="text-decoration:none;">
                    <div class="card card-summary shadow text-center" style="background: linear-gradient(45deg, #6610f2, #4b0db8);">
                        <div class="card-body">
                            <i class="fas fa-clipboard-list"></i>
                            <h3>${totalPendingRequests}</h3>
                            <p>Pending Requests</p>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>

    <div class="banner-container">
        <img src="${pageContext.request.contextPath}/images/banner2.jpg" alt="SK Mini Bank Banner" class="banner-img">
    </div>

    <footer class="mt-5 pt-4 text-muted border-top">
        &copy; 2026 SK Mini Bank Management System | Developed By Sajid Khan
    </footer>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Auto-refresh dashboard every 60 seconds to keep stats updated
    setTimeout(function() {
        window.location.reload();
    }, 60000);
</script>
</body>
</html>
