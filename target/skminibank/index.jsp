<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>SK Mini Bank</title>

<meta name="viewport"
content="width=device-width, initial-scale=1">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">

<link rel="stylesheet"
href="css/style.css">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

</head>

<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">

<div class="container">

<a class="navbar-brand fw-bold" href="#">

🏦 SK MINI BANK

</a>

<button class="navbar-toggler"
type="button"
data-bs-toggle="collapse"
data-bs-target="#menu">

<span class="navbar-toggler-icon"></span>

</button>

<div class="collapse navbar-collapse"
id="menu">

<ul class="navbar-nav ms-auto">

<li class="nav-item">

<a class="nav-link"
href="index.jsp">

Home

</a>

</li>

<li class="nav-item">

<a class="nav-link"
href="about.jsp">

About

</a>

</li>

<li class="nav-item">

<a class="nav-link"
href="contact.jsp">

Contact

</a>

</li>

<li class="nav-item">

<a class="nav-link"
href="login.jsp">

Login

</a>

</li>

<li class="nav-item">

<a class="nav-link"
href="register.jsp">

Register

</a>

</li>

</ul>

</div>

</div>

</nav>

<section class="bg-primary text-white text-center p-5">

<div class="container">

<h1 class="display-4">

Welcome to SK Mini Bank

</h1>

<p>

Safe • Secure • Digital Banking

</p>


</a>

<a href="admin-login.jsp"
class="btn btn-light btn-lg">

Admin Login 

</a>

</div>

</section>
    <section class="container mt-5">

<div class="row">

<div class="col-md-3 mb-4">

<div class="card shadow text-center">

<div class="card-body">

<h1>💰</h1>

<h4>Savings Account</h4>

<p>Open your savings account instantly.</p>

<a href="register.jsp" class="btn btn-primary">
Open Now
</a>

</div>

</div>

</div>

<div class="col-md-3 mb-4">

<div class="card shadow text-center">

<div class="card-body">

<h1>💳</h1>

<h4>Credit Card</h4>

<p>Apply for lifetime free credit card.</p>

<a href="customer/dashboard.jsp" class="btn btn-success">
Apply
</a>

</div>

</div>

</div>

<div class="col-md-3 mb-4">

<div class="card shadow text-center">

<div class="card-body">

<h1>🏦</h1>

<h4>Personal Loan</h4>

<p>Instant loan approval with low interest.</p>

<a href="customer/dashboard.jsp" class="btn btn-warning">
Apply
</a>

</div>

</div>

</div>

<div class="col-md-3 mb-4">

<div class="card shadow text-center">

<div class="card-body">

<h1>📈</h1>

<h4>Fixed Deposit</h4>

<p>Earn higher interest with FD.</p>

<a href="customer/dashboard.jsp" class="btn btn-danger">
Open FD
</a>

</div>

</div>

</div>

</div>

<div class="row">

<div class="col-md-3 mb-4">

<div class="card shadow text-center">

<div class="card-body">

<h1>🚗</h1>

<h4>Car Loan</h4>

<p>Finance your dream car easily.</p>

</div>

</div>

</div>

<div class="col-md-3 mb-4">

<div class="card shadow text-center">

<div class="card-body">

<h1>🏠</h1>

<h4>Home Loan</h4>

<p>Affordable housing loan plans.</p>

</div>

</div>

</div>

<div class="col-md-3 mb-4">

<div class="card shadow text-center">

<div class="card-body">

<h1>📱</h1>

<h4>Mobile Banking</h4>

<p>Manage your bank account anywhere.</p>

</div>

</div>

</div>

<div class="col-md-3 mb-4">

<div class="card shadow text-center">

<div class="card-body">

<h1>🛡</h1>

<h4>Secure Banking</h4>

<p>100% Safe & Secure Transactions.</p>

</div>

</div>

</div>

</div>

</section>
    <!-- Banner Slider Start -->

<div id="bankSlider" class="carousel slide" data-bs-ride="carousel">

    <div class="carousel-indicators">

        <button type="button" data-bs-target="#bankSlider" data-bs-slide-to="0" class="active"></button>

        <button type="button" data-bs-target="#bankSlider" data-bs-slide-to="1"></button>

        <button type="button" data-bs-target="#bankSlider" data-bs-slide-to="2"></button>

        <button type="button" data-bs-target="#bankSlider" data-bs-slide-to="3"></button>

    </div>

    <div class="carousel-inner">

        <div class="carousel-item active">

            <img src="images/splash.png"
                 class="d-block w-100"
                 height="500">

            <div class="carousel-caption">

                <h1>Welcome to SK Mini Bank</h1>

                <p>Safe • Secure • Smart Banking</p>

            </div>

        </div>

        <div class="carousel-item">

            <img src="images/splash.png"
                 class="d-block w-100"
                 height="500">

            <div class="carousel-caption">

                <h1>Credit Card Offers</h1>

                <p>Lifetime Free Credit Cards</p>

            </div>

        </div>

        <div class="carousel-item">

            <img src="images/splash.png"
                 class="d-block w-100"
                 height="500">

            <div class="carousel-caption">

                <h1>Personal & Home Loan</h1>

                <p>Fast Approval with Low Interest</p>

            </div>

        </div>

        <div class="carousel-item">

            <img src="images/splash.png"
                 class="d-block w-100"
                 height="500">

            <div class="carousel-caption">

                <h1>Fixed Deposit</h1>

                <p>Maximum Returns on Your Savings</p>

            </div>

        </div>

    </div>

    <button class="carousel-control-prev"
            type="button"
            data-bs-target="#bankSlider"
            data-bs-slide="prev">

        <span class="carousel-control-prev-icon"></span>

    </button>

    <button class="carousel-control-next"
            type="button"
            data-bs-target="#bankSlider"
            data-bs-slide="next">

        <span class="carousel-control-next-icon"></span>

    </button>

</div>

<!-- Banner Slider End -->