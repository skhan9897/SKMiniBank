<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bank.model.Customer"%>
<%
    Customer customer = (Customer) request.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <meta name="theme-color" content="#071b3a">
    <title>SK Mini Bank | Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
</head>
<body class="dashboard-page">
    <nav class="topbar">
        <div class="brand">
            <span class="brand-mark"><i class="fa-solid fa-building-columns"></i></span>
            <span>SK <small>MINI BANK</small></span>
        </div>
        <div class="welcome">
            <span>Welcome, <strong><%=customer.getFullName()%></strong></span>
            <a href="<%=request.getContextPath()%>/LogoutServlet" class="logout-btn">
                <i class="fa-solid fa-arrow-right-from-bracket"></i><span>Logout</span>
            </a>
        </div>
    </nav>

    <main class="dashboard-shell">
        <section class="hero-row">
            <div>
                <p class="eyebrow">PERSONAL BANKING</p>
                <h1>Your money, <span>made simple.</span></h1>
                <p class="hero-copy">Manage your account securely and keep track of every transaction.</p>
            </div>
            <div class="account-chip">
                <i class="fa-solid fa-shield-halved"></i>
                <div><small>ACCOUNT STATUS</small><strong class="status-pill"><i class="fa-solid fa-circle-check"></i><%=customer.getStatus()%></strong></div>
            </div>
        </section>

        <section class="summary-grid">
            <article class="balance-card">
                <div class="card-label"><span>AVAILABLE BALANCE</span><i class="fa-solid fa-wallet"></i></div>
                <strong class="balance-value">₹ <%=customer.getBalance()%></strong>
                <div class="account-number">A/C •••• <%=customer.getAccountNumber()%></div>
            </article>
            <article class="stat-card">
                <i class="fa-solid fa-building-columns stat-icon blue"></i>
                <small>ACCOUNT TYPE</small><strong><%=customer.getAccountType()%></strong>
                <span><%=customer.getBranch()%> branch</span>
            </article>
            <article class="stat-card">
                <i class="fa-solid fa-id-card stat-icon gold"></i>
                <small>IFSC CODE</small><strong><%=customer.getIfscCode()%></strong>
                <span>Secure banking access</span>
            </article>
        </section>

        <section class="section-block">
            <div class="section-heading"><div><p class="eyebrow">QUICK ACCESS</p><h2>Banking services</h2></div></div>
            <div class="services-grid">
                <a href="balance.jsp" class="service-card"><span class="service-icon blue-bg"><i class="fa-solid fa-wallet"></i></span><strong>Balance</strong><small>View your balance</small></a>
                <a href="<%=request.getContextPath()%>/admin/transfer.jsp" class="service-card"><span class="service-icon purple-bg"><i class="fa-solid fa-arrow-right-arrow-left"></i></span><strong>Fund Transfer</strong><small>Send money securely</small></a>
                <a href="passbook.jsp" class="service-card"><span class="service-icon green-bg"><i class="fa-solid fa-book-open"></i></span><strong>Passbook</strong><small>Review your account</small></a>
                <a href="../TransactionServlet?accountNumber=<%=customer.getAccountNumber()%>" class="service-card"><span class="service-icon orange-bg"><i class="fa-solid fa-receipt"></i></span><strong>Transactions</strong><small>Track your activity</small></a>
                <a href="<%=request.getContextPath()%>/customer/atm-request.jsp" class="service-card"><span class="service-icon pink-bg"><i class="fa-solid fa-credit-card"></i></span><strong>ATM Card</strong><small>Manage your card</small></a>
                <a href="../admin/loan.jsp" class="service-card"><span class="service-icon navy-bg"><i class="fa-solid fa-landmark"></i></span><strong>Loan</strong><small>Explore lending options</small></a>
                <a href="../admin/fd.jsp" class="service-card"><span class="service-icon teal-bg"><i class="fa-solid fa-chart-line"></i></span><strong>Fixed Deposit</strong><small>Grow your savings</small></a>
                <a href="profile.jsp" class="service-card"><span class="service-icon slate-bg"><i class="fa-solid fa-user"></i></span><strong>My Profile</strong><small>Update your details</small></a>
            </div>
        </section>

        <section class="section-block request-card">
            <div class="section-heading"><div><p class="eyebrow">CARD SERVICES</p><h2>ATM card request</h2></div><span class="pending-badge">Pending</span></div>
            <div class="request-row"><span class="service-icon blue-bg"><i class="fa-solid fa-credit-card"></i></span><div><strong>Debit Card</strong><small>Request date · 24 Jun 2026</small></div><i class="fa-solid fa-chevron-right arrow"></i></div>
        </section>
    </main>
    <footer>© 2026 SK Mini Bank <span>•</span> Banking made better</footer>
</body>
</html>
