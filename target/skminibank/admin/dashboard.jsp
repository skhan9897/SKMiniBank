<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>SK Mini Bank Admin Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/dashboard.css">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

</head>

<body>

<!-- Top Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow">
    <div class="container-fluid">

        <a class="navbar-brand fw-bold" href="#">
            <i class="fas fa-university"></i>
            SK Mini Bank Admin
        </a>

        <div class="d-flex align-items-center">

    <span class="me-3">
        <i class="fa fa-user"></i>
        Welcome Admin
    </span>

    <a href="<%=request.getContextPath()%>/AdminLogoutServlet"
       class="btn btn-danger btn-sm">
        <i class="fa fa-sign-out-alt"></i>
        Logout
    </a>

</div>

    </div>
</nav>

<!-- Sidebar -->

<div class="sidebar">

    <div class="logo-section">
        <h4>🏦 SK Mini Bank</h4>
        <p>Admin Panel</p>
    </div>

    <a href="${pageContext.request.contextPath}/admin/dashboard.jsp" class="active">
        🏠 Dashboard
    </a>

    <a href="${pageContext.request.contextPath}/admin/customer-list.jsp">
        👥 Customers
    </a>

    <a href="${pageContext.request.contextPath}/admin/edit-customer.jsp">
        ➕ Add Customer
    </a>

    <a href="${pageContext.request.contextPath}/admin/open-account.jsp">
        🏦 Open Account
    </a>

    <a href="${pageContext.request.contextPath}/admin/deposit.jsp">
        💰 Deposit
    </a>

    <a href="${pageContext.request.contextPath}/admin/withdraw.jsp">
        💵 Withdraw
    </a>

    <a href="${pageContext.request.contextPath}/admin/transfer.jsp">
        🔄 Fund Transfer
    </a>

    <a href="${pageContext.request.contextPath}/admin/transaction.jsp">
        📄 Transactions
    </a>

    <a href="${pageContext.request.contextPath}/admin/cheque-book-request.jsp">
        📦 Cheque Book Request
    </a>

    <a href="${pageContext.request.contextPath}/admin/internet-banking-request.jsp">
        🌐 Internet Banking
    </a>

    <a href="${pageContext.request.contextPath}/admin/atm-request.jsp">
        💳 ATM Request
    </a>

    <a href="${pageContext.request.contextPath}/admin/kyc.jsp">
        🪪 KYC Verification
    </a>

    <a href="${pageContext.request.contextPath}/admin/fixed-deposit.jsp">
        📦 Fixed Deposit
    </a>

    <a href="${pageContext.request.contextPath}/admin/loan.jsp">
        🏠 Loan
    </a>

    <a href="${pageContext.request.contextPath}/admin/reports.jsp">
        📊 Reports
    </a>

    <a href="<%=request.getContextPath()%>/AdminLogoutServlet"
   class="btn btn-danger">
    <i class="fa fa-sign-out-alt"></i> Logout
</a>

</div>

<!-- Main Content -->

<div class="main-content">

<h2 class="fw-bold mb-4">Dashboard</h2>

<div class="row">

<div class="col-md-3 mb-4">
<div class="card bg-primary text-white shadow">
<div class="card-body text-center">

<i class="fas fa-users fa-3x"></i>

<h2 class="mt-3">${totalCustomers}</h2>

<h5>Total Customers</h5>

</div>
</div>
</div>

<div class="col-md-3 mb-4">
<div class="card bg-success text-white shadow">
<div class="card-body text-center">

<i class="fas fa-wallet fa-3x"></i>

<h2 class="mt-3">${totalAccounts}</h2>

<h5>Total Accounts</h5>

</div>
</div>
</div>
<div class="col-md-3 mb-4">
    <div class="card bg-warning shadow">
        <div class="card-body text-center">

            <i class="fas fa-money-bill-wave fa-3x"></i>

            <h2 class="mt-3">${totalBalance}</h2>

            <h5>Total Balance</h5>

        </div>
    </div>
</div>

<div class="col-md-3 mb-4">
    <div class="card bg-danger text-white shadow">
        <div class="card-body text-center">

            <i class="fas fa-exchange-alt fa-3x"></i>

            <h2 class="mt-3">${totalTransactions}</h2>

            <h5>Transactions</h5>

        </div>
    </div>
</div>

</div>

</div>
<!-- Main Content End -->

<!-- Footer -->

<footer class="footer">
    &copy; 2026 SK Mini Bank Management System | Developed By Sajid Khan
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>