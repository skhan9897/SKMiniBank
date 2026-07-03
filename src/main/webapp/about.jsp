<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us - SK Mini Bank</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand fw-bold" href="index.jsp">SK Mini Bank</a>

        <button class="navbar-toggler" type="button"
                data-bs-toggle="collapse"
                data-bs-target="#menu">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="menu">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link active" href="about.jsp">About</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="contact.jsp">Contact</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="login.jsp">Login</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- About Section -->
<div class="container py-5">

    <div class="text-center mb-5">
        <h1 class="fw-bold text-primary">About SK Mini Bank</h1>
        <p class="lead">
            Safe, Secure and Trusted Digital Banking.
        </p>
    </div>

    <div class="row">

        <div class="col-md-6">

            <img src="images/banner.png"
                 class="img-fluid rounded shadow"
                 alt="Bank">

        </div>

        <div class="col-md-6">

            <h3>Who We Are</h3>

            <p>
                SK Mini Bank is a banking management system developed using
                Java Servlet, JSP, JDBC and MySQL.
            </p>

            <p>
                Our objective is to provide secure banking services including
                account management, deposits, withdrawals, money transfer,
                ATM requests, fixed deposits and loan management.
            </p>

            <h4 class="mt-4">Our Services</h4>

            <ul>
                <li>Savings Account</li>
                <li>Current Account</li>
                <li>Money Transfer</li>
                <li>ATM Card</li>
                <li>Fixed Deposit</li>
                <li>Loan Services</li>
                <li>Internet Banking</li>
            </ul>

        </div>

    </div>

</div>

<!-- Footer -->
<footer class="bg-dark text-white text-center p-3">
    © 2026 SK Mini Bank | All Rights Reserved
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>