<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Withdraw Money | SK Mini Bank</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>

body{
    background:#eef3f8;
    font-family:Arial,sans-serif;
}

.navbar{
    background:#0d6efd;
}

.card{
    border:none;
    border-radius:15px;
}

.card-header{
    background:linear-gradient(90deg,#0d6efd,#0dcaf0);
    color:white;
    text-align:center;
    padding:20px;
}

.form-control{
    height:45px;
}

.btn-danger{
    height:45px;
    font-size:18px;
    font-weight:bold;
}

.info-box{
    background:#fff3cd;
    border-left:5px solid orange;
    padding:10px;
    border-radius:8px;
    margin-bottom:20px;
}

.footer{
    margin-top:30px;
    text-align:center;
    color:gray;
    font-size:14px;
}

</style>

</head>

<body>

<nav class="navbar navbar-dark">
<div class="container-fluid">

<span class="navbar-brand">
🏦 SK MINI BANK
</span>

<a href="customer/dashboard.jsp" class="btn btn-light">
Dashboard
</a>

</div>
</nav>

<div class="container mt-5">

<div class="row justify-content-center">

<div class="col-lg-6">

<div class="card shadow-lg">

<div class="card-header">

<h2>Withdraw Money</h2>

<p class="mb-0">
Secure Banking Transaction
</p>

</div>

<div class="card-body">

<div class="info-box">

<b>Important:</b>
Please check your balance before withdrawing money.

</div>

<form action="../WithdrawServlet" method="post">

<div class="mb-3">

<label class="form-label">
Account Number
</label>

<input type="text"
name="accountNumber"
class="form-control"
placeholder="Enter Account Number"
required>

</div>

<div class="mb-3">

<label class="form-label">
Withdraw Amount (₹)
</label>

<input type="number"
name="amount"
class="form-control"
placeholder="Enter Amount"
required>

</div>

<div class="d-grid">

<button class="btn btn-danger">

💸 Withdraw Money

</button>

</div>

</form>

</div>

</div>

<div class="footer">

© 2026 SK Mini Bank | Secure Banking Portal

</div>

</div>

</div>

</div>

</body>
</html>