<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SK Mini Bank Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .nav-link-custom {
            text-decoration: none;
            color: #0d6efd;
            margin-right: 15px;
            font-size: 14px;
        }
        .nav-link-custom:hover {
            text-decoration: underline;
        }
        .card {
            border-radius: 10px;
        }
        .footer {
            text-align: left;
            padding: 20px 0;
            color: #6c757d;
            font-size: 14px;
        }
    </style>
</head>
<body>

<div class="container-fluid py-3">
    <div class="d-flex align-items-center mb-1">
        <h4 class="mb-0">🏦 SK Mini Bank</h4>
    </div>
    <p class="text-muted mb-3">Admin Panel</p>

    <!-- Navigation Links -->
    <div class="mb-2">
        <a href="${pageContext.request.contextPath}/DashboardServlet" class="nav-link-custom">🏠 Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/service-requests.jsp" class="nav-link-custom">📋 Service Requests</a>
    </div>

    <div class="mb-2">
        <a href="${pageContext.request.contextPath}/AdminATMRequestServlet" class="nav-link-custom">💳 ATM Card Requests</a>
        <a href="${pageContext.request.contextPath}/AdminChequeBookListServlet" class="nav-link-custom">📒 Cheque Book Requests</a>
        <a href="${pageContext.request.contextPath}/AdminNetBankingServlet" class="nav-link-custom">🌐 Net Banking Requests</a>
        <a href="${pageContext.request.contextPath}/AdminMobileBankingServlet" class="nav-link-custom">📱 Mobile Banking Requests</a>
        <a href="${pageContext.request.contextPath}/AdminLoanRequestServlet" class="nav-link-custom">🏦 Loan Requests</a>
        <a href="${pageContext.request.contextPath}/AdminAllRequestServlet" class="nav-link-custom">📋 All Service Requests</a>
    </div>

    <div class="mb-4">
        <a href="${pageContext.request.contextPath}/admin/customer-list.jsp" class="nav-link-custom">👥 Customers</a>
        <a href="${pageContext.request.contextPath}/admin/edit-customer.jsp" class="nav-link-custom">➕ Add Customer</a>
        <a href="${pageContext.request.contextPath}/admin/open-account.jsp" class="nav-link-custom">🏦 Open Account</a>
        <a href="${pageContext.request.contextPath}/admin/deposit.jsp" class="nav-link-custom">💰 Deposit</a>
        <a href="${pageContext.request.contextPath}/admin/withdraw.jsp" class="nav-link-custom">💵 Withdraw</a>
        <a href="${pageContext.request.contextPath}/admin/transfer.jsp" class="nav-link-custom">🔄 Fund Transfer</a>
        <a href="${pageContext.request.contextPath}/admin/fixed-deposit.jsp" class="nav-link-custom">📦 Fixed Deposit</a>
        <a href="${pageContext.request.contextPath}/admin/loan-dashboard.jsp" class="nav-link-custom">🏠 Loan</a>
        <a href="${pageContext.request.contextPath}/admin/kyc.jsp" class="nav-link-custom">🪪 KYC Verification</a>
        <a href="${pageContext.request.contextPath}/NotificationServlet" class="nav-link-custom">🔔 Notifications</a>
        <a href="${pageContext.request.contextPath}/ReportServlet" class="nav-link-custom">📊 Reports</a>
        <a href="${pageContext.request.contextPath}/AdminLogoutServlet" class="nav-link-custom">🚪 Logout</a>
    </div>

    <h2 class="fw-bold mb-4">Dashboard</h2>

    <div class="row">
        <!-- Total Customers -->
        <div class="col-md-3 mb-3">
            <a href="${pageContext.request.contextPath}/CustomerListServlet" style="text-decoration:none;color:white;">
                <div class="card bg-primary shadow-sm h-100">
                    <div class="card-body text-center py-4">
                        <i class="fas fa-users fa-2x mb-2"></i>
                        <h3 class="mb-0">${totalCustomers}</h3>
                        <p class="mb-0">Total Customers</p>
                    </div>
                </div>
            </a>
        </div>

        <!-- Total Accounts -->
        <div class="col-md-3 mb-3">
            <a href="${pageContext.request.contextPath}/AccountListServlet" style="text-decoration:none;color:white;">
                <div class="card bg-success shadow-sm h-100">
                    <div class="card-body text-center py-4">
                        <i class="fas fa-wallet fa-2x mb-2"></i>
                        <h3 class="mb-0">${totalAccounts}</h3>
                        <p class="mb-0">Total Accounts</p>
                    </div>
                </div>
            </a>
        </div>

        <!-- Total Balance -->
        <div class="col-md-3 mb-3">
            <a href="${pageContext.request.contextPath}/ReportServlet" style="text-decoration:none;color:white;">
                <div class="card bg-warning shadow-sm h-100 text-dark">
                    <div class="card-body text-center py-4">
                        <i class="fas fa-money-bill-wave fa-2x mb-2"></i>
                        <h3 class="mb-0">₹ ${totalBalance}</h3>
                        <p class="mb-0">Total Balance</p>
                    </div>
                </div>
            </a>
        </div>

        <!-- Transactions -->
        <div class="col-md-3 mb-3">
            <a href="${pageContext.request.contextPath}/TransactionServlet" style="text-decoration:none;color:white;">
                <div class="card bg-danger shadow-sm h-100">
                    <div class="card-body text-center py-4">
                        <i class="fas fa-exchange-alt fa-2x mb-2"></i>
                        <h3 class="mb-0">${totalTransactions}</h3>
                        <p class="mb-0">Transactions</p>
                    </div>
                </div>
            </a>
        </div>
    </div>

    <!-- Banner -->
    <div class="row mt-3">
        <div class="col-12">
            <div class="card border-0 overflow-hidden">
                <img src="${pageContext.request.contextPath}/images/banner2.jpg" alt="SK Mini Bank Banner" class="img-fluid w-100" style="max-height: 400px; object-fit: cover;">
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer mt-4 border-top">
        &copy; 2026 SK Mini Bank Management System | Developed By Sajid Khan
    </footer>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
