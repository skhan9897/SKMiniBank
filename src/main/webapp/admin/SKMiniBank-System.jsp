<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SK Mini Bank | Admin Dashboard</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
    body { background-color: #f0f4f8; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; color: #334e68; }

    .sidebar {
        height: 100vh;
        width: 260px;
        position: fixed;
        top: 0;
        left: 0;
        background: linear-gradient(180deg, #102a43 0%, #243b53 100%);
        color: white;
        padding-top: 20px;
        overflow-y: auto;
        box-shadow: 4px 0 15px rgba(0,0,0,0.1);
        z-index: 1000;
    }

    .sidebar .logo-section {
        padding: 0 25px 25px;
        border-bottom: 1px solid rgba(255,255,255,0.05);
        margin-bottom: 20px;
    }

    .sidebar h4 { margin: 0; font-weight: 800; letter-spacing: 0.5px; color: #9fb3c8; }
    .sidebar p { margin: 0; font-size: 11px; opacity: 0.6; text-transform: uppercase; font-weight: 600; margin-top: 4px; }

    .sidebar a {
        display: flex;
        align-items: center;
        padding: 12px 25px;
        color: #bcccdc;
        text-decoration: none;
        font-size: 14px;
        font-weight: 500;
        transition: all 0.2s ease-in-out;
        margin: 4px 12px;
        border-radius: 8px;
    }

    .sidebar a:hover {
        background-color: rgba(255,255,255,0.05);
        color: white;
        transform: translateX(5px);
    }

    .sidebar a.active {
        background: #334e68;
        color: #ffffff;
        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    }

    .sidebar .nav-header {
        padding: 20px 25px 10px;
        font-size: 10px;
        text-transform: uppercase;
        letter-spacing: 1.5px;
        color: #627d98;
        font-weight: 700;
    }

    .main-content {
        margin-left: 260px;
        padding: 40px;
        min-height: 100vh;
        background: radial-gradient(at top left, #f0f4f8, #d9e2ec);
    }

    .header-bar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 40px;
    }

    .section-title { font-weight: 800; font-size: 28px; color: #102a43; margin: 0; }
    .section-subtitle { font-size: 14px; color: #627d98; margin-top: 4px; }

    .card-summary {
        border: none;
        border-radius: 20px;
        color: #fff;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        margin-bottom: 24px;
        position: relative;
        overflow: hidden;
        border: 1px solid rgba(255,255,255,0.1);
    }

    .card-summary:hover {
        transform: translateY(-8px);
        box-shadow: 0 15px 30px rgba(0,0,0,0.12);
    }

    .card-summary.blue { background: linear-gradient(135deg, #48bbff 0%, #2185d0 100%); }
    .card-summary.green { background: linear-gradient(135deg, #38ef7d 0%, #11998e 100%); }
    .card-summary.yellow { background: linear-gradient(135deg, #f7971e 0%, #ffd200 100%); color: #433000; }
    .card-summary.red { background: linear-gradient(135deg, #ff6b6b 0%, #ee5253 100%); }
    .card-summary.purple { background: linear-gradient(135deg, #a55eea 0%, #82589f 100%); }

    .card-summary .card-body { padding: 35px 25px; position: relative; z-index: 2; }
    .card-summary i {
        position: absolute;
        top: 20px;
        right: 20px;
        font-size: 50px;
        opacity: 0.15;
        transition: 0.3s;
    }
    .card-summary:hover i { transform: scale(1.1) rotate(-10deg); opacity: 0.25; }

    .card-summary h3 { font-size: 36px; font-weight: 800; margin-bottom: 8px; letter-spacing: -1px; }
    .card-summary p { font-size: 14px; margin: 0; font-weight: 600; opacity: 0.9; text-transform: uppercase; letter-spacing: 0.5px; }

    .quick-actions-panel {
        background: rgba(255,255,255,0.6);
        backdrop-filter: blur(10px);
        border-radius: 24px;
        padding: 30px;
        border: 1px solid rgba(255,255,255,0.4);
        margin-top: 10px;
    }

    .action-pill {
        background: white;
        padding: 15px 20px;
        border-radius: 15px;
        display: flex;
        align-items: center;
        text-decoration: none;
        color: #334e68;
        font-weight: 600;
        margin-bottom: 15px;
        transition: 0.2s;
        border: 1px solid #d9e2ec;
    }

    .action-pill:hover {
        background: #f0f4f8;
        border-color: #bcccdc;
        color: #102a43;
    }

    .action-pill i {
        width: 35px;
        height: 35px;
        background: #f0f4f8;
        border-radius: 10px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 15px;
        color: #48bbff;
    }
</style>
</head>
<body>

<div class="sidebar">
    <% String sidebarCtx = request.getContextPath(); if("/".equals(sidebarCtx)) sidebarCtx = ""; %>
    <div class="logo-section">
        <h4>🏦 SK MINI BANK</h4>
        <p>Advanced Management</p>
    </div>

    <div class="nav-header">Main Overview</div>
    <a href="<%=sidebarCtx%>/DashboardServlet" class="active"><i class="fas fa-th-large me-2"></i> Dashboard</a>
    <a href="<%=sidebarCtx%>/AdminAllRequestServlet"><i class="fas fa-clipboard-list me-2"></i> Service Requests</a>

    <div class="nav-header">Banking Operations</div>
    <a href="<%=sidebarCtx%>/AdminATMRequestServlet"><i class="fas fa-credit-card me-2"></i> ATM Services</a>
    <a href="<%=sidebarCtx%>/AdminChequeBookListServlet"><i class="fas fa-book me-2"></i> Cheque Books</a>
    <a href="<%=sidebarCtx%>/AdminNetBankingServlet"><i class="fas fa-globe me-2"></i> Net Banking</a>
    <a href="<%=sidebarCtx%>/AdminMobileBankingServlet"><i class="fas fa-mobile-alt me-2"></i> Mobile Banking</a>
    <a href="<%=sidebarCtx%>/AdminLoanRequestServlet"><i class="fas fa-hand-holding-usd me-2"></i> Loan Requests</a>

    <div class="nav-header">User Control</div>
    <a href="<%=sidebarCtx%>/admin/customer-list.jsp"><i class="fas fa-users me-2"></i> Customer Base</a>
    <a href="<%=sidebarCtx%>/admin/open-account.jsp"><i class="fas fa-user-plus me-2"></i> New Account</a>
    <a href="<%=sidebarCtx%>/AdminKYCServlet"><i class="fas fa-id-badge me-2"></i> KYC Verification</a>

    <div class="nav-header">Financials</div>
    <a href="<%=sidebarCtx%>/admin/deposit.jsp"><i class="fas fa-plus-circle me-2"></i> Deposit</a>
    <a href="<%=sidebarCtx%>/admin/withdraw.jsp"><i class="fas fa-minus-circle me-2"></i> Withdrawal</a>
    <a href="<%=sidebarCtx%>/admin/transfer.jsp"><i class="fas fa-exchange-alt me-2"></i> Transfer</a>
    <a href="<%=sidebarCtx%>/admin/fixed-deposit.jsp"><i class="fas fa-piggy-bank me-2"></i> Fixed Deposit</a>

    <div style="margin-top: 20px; padding: 0 12px;">
        <a href="<%=sidebarCtx%>/AdminLogoutServlet" style="color: #ff6b6b; background: rgba(255,107,107,0.1);"><i class="fas fa-power-off me-2"></i> Logout System</a>
    </div>
</div>

<div class="main-content">
    <div class="header-bar">
        <div>
            <h1 class="section-title">Admin Command Center</h1>
            <p class="section-subtitle">Manage banking operations and customer assets in real-time.</p>
        </div>
        <div class="d-flex align-items-center">
            <div class="me-4 text-end">
                <div class="fw-bold" style="font-size: 14px; color: #102a43;">System Status</div>
                <div style="font-size: 12px; color: #22c55e;"><i class="fas fa-circle me-1" style="font-size: 8px;"></i> Fully Operational</div>
            </div>
            <div class="bg-white p-2 rounded-circle shadow-sm" style="width: 45px; height: 45px; display: flex; align-items: center; justify-content: center;">
                <i class="fas fa-user-shield" style="color: #102a43;"></i>
            </div>
        </div>
    </div>

    <div class="container-fluid p-0">
        <div class="row">
            <!-- Total Customers -->
            <div class="col-md-3">
                <a href="${pageContext.request.contextPath}/CustomerListServlet" style="text-decoration:none;">
                    <div class="card card-summary blue shadow-sm">
                        <div class="card-body">
                            <i class="fas fa-users"></i>
                            <h3>${totalCustomers}</h3>
                            <p>Active Customers</p>
                        </div>
                    </div>
                </a>
            </div>

            <!-- Total Accounts -->
            <div class="col-md-3">
                <% String ctx = request.getContextPath(); if("/".equals(ctx)) ctx = ""; %>
                <a href="<%=ctx%>/AccountListServlet" style="text-decoration:none;">
                    <div class="card card-summary green shadow-sm">
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
                    <div class="card card-summary yellow shadow-sm">
                        <div class="card-body">
                            <i class="fas fa-money-bill-wave"></i>
                            <h3>₹ ${totalBalance}</h3>
                            <p>Vault Balance</p>
                        </div>
                    </div>
                </a>
            </div>

            <!-- Transactions -->
            <div class="col-md-3">
                <a href="${pageContext.request.contextPath}/TransactionServlet?view=today" style="text-decoration:none;">
                    <div class="card card-summary red shadow-sm">
                        <div class="card-body">
                            <i class="fas fa-exchange-alt"></i>
                            <h3>${totalTransactions}</h3>
                            <p>Today's Activity</p>
                        </div>
                    </div>
                </a>
            </div>
        </div>

        <div class="row mt-2">
            <div class="col-md-8">
                <div class="quick-actions-panel shadow-sm mb-4">
                    <h5 class="fw-bold mb-4" style="color: #102a43;">Quick Management Access</h5>
                    <div class="row">
                        <div class="col-md-6">
                            <a href="<%=sidebarCtx%>/admin/open-account.jsp" class="action-pill">
                                <i class="fas fa-user-plus"></i> Open Digital Account
                            </a>
                            <a href="<%=sidebarCtx%>/AdminLoanRequestServlet" class="action-pill">
                                <i class="fas fa-hand-holding-usd"></i> Review Loan Apps
                            </a>
                        </div>
                        <div class="col-md-6">
                            <a href="<%=sidebarCtx%>/ReportServlet" class="action-pill">
                                <i class="fas fa-chart-bar"></i> Financial Reports
                            </a>
                            <a href="<%=sidebarCtx%>/NotificationServlet" class="action-pill">
                                <i class="fas fa-bell"></i> System Broadcast
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Live Transaction Monitor Feed -->
                <div class="bg-white p-4 rounded-4 shadow-sm border border-white" style="background: rgba(255,255,255,0.7); backdrop-filter: blur(15px);">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h5 class="fw-bold m-0" style="color: #102a43;"><i class="fas fa-satellite-dish me-2 text-danger"></i> Live Transaction Monitor</h5>
                        <a href="<%=sidebarCtx%>/TransactionServlet" class="btn btn-primary btn-sm rounded-pill px-3 shadow-sm">View Full Audit</a>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-hover align-middle" style="font-size: 13px;">
                            <thead class="text-muted small uppercase">
                                <tr>
                                    <th>Origin / Identity</th>
                                    <th>Activity</th>
                                    <th>Amount</th>
                                    <th>Description</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                java.util.List<com.bank.model.Transaction> recent = (java.util.List<com.bank.model.Transaction>)request.getAttribute("recentTransactions");
                                if(recent != null && !recent.isEmpty()){
                                    for(com.bank.model.Transaction t : recent){
                                        String type = t.getTransactionType() != null ? t.getTransactionType().toUpperCase() : "TXN";
                                        boolean isCredit = type.contains("CREDIT") || type.contains("DEPOSIT") || type.contains("LOAN");
                                %>
                                <tr>
                                    <td>
                                        <div class="fw-bold"><%=t.getCustomerName()%></div>
                                        <div class="small opacity-50"><%=t.getAccountNumber()%></div>
                                    </td>
                                    <td><span class="badge <%=isCredit ? "bg-success" : "bg-danger"%> bg-opacity-10 <%=isCredit ? "text-success" : "text-danger"%> rounded-pill px-2 py-1" style="font-size: 9px;"><%=type%></span></td>
                                    <td class="fw-bold <%=isCredit ? "text-success" : "text-danger"%>"><%=isCredit ? "+" : "-"%> ₹<%=String.format("%,.0f", t.getAmount())%></td>
                                    <td class="text-muted small text-truncate" style="max-width: 150px;" title="<%=t.getDescription()%>"><%=t.getDescription()%></td>
                                </tr>
                                <% } } else { %>
                                    <tr><td colspan="4" class="text-center py-4 opacity-50 italic">No recent activity detected.</td></tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Pending Service Requests -->
            <div class="col-md-4">
                <a href="${pageContext.request.contextPath}/AdminAllRequestServlet" style="text-decoration:none;">
                    <div class="card card-summary purple shadow-sm h-100" style="margin-bottom: 0;">
                        <div class="card-body d-flex flex-column justify-content-center align-items-center text-center">
                            <i class="fas fa-clipboard-list" style="position: static; opacity: 0.3; margin-bottom: 20px; font-size: 60px;"></i>
                            <h3 style="font-size: 48px;">${totalPendingRequests}</h3>
                            <p style="font-size: 16px;">Action Required</p>
                            <div class="mt-3 badge rounded-pill bg-white text-dark px-3 py-2" style="font-size: 12px; font-weight: 700;">VIEW ALL PENDING</div>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>

    <footer class="mt-5 pt-4 text-muted border-top d-flex justify-content-between">
        <span>&copy; 2026 SK Mini Bank Management System</span>
        <span>Developed By Sajid Khan | v2.0 Premium</span>
    </footer>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Auto-refresh dashboard every 20 seconds to keep live feed updated
    setTimeout(function() {
        window.location.reload();
    }, 20000);
</script>
</body>
</html>
