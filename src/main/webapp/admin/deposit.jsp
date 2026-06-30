<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Deposit Money | SK Mini Bank</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<link rel="stylesheet" href="../css/dashboard.css">

</head>

<body style="background:#f4f6f9;">

<!-- Navbar -->

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">

<div class="container-fluid">

<a class="navbar-brand fw-bold" href="dashboard.jsp">

<i class="fa-solid fa-building-columns"></i>

SK Mini Bank

</a>

<div>

<a href="customer-list.jsp" class="btn btn-light me-2">

Dashboard

</a>

<a href="../login.jsp" class="btn btn-danger">

Logout

</a>

</div>

</div>

</nav>

<!-- Deposit Form -->

<div class="container mt-5">

<div class="row justify-content-center">

<div class="col-md-7">

<div class="card shadow-lg">

<div class="card-header bg-success text-white">

<h3>

<i class="fa-solid fa-money-bill-wave"></i>

Deposit Money

</h3>

</div>

<div class="card-body">

<%
String msg=request.getParameter("msg");

if("success".equals(msg)){
%>

<div class="alert alert-success">

Money Deposited Successfully

</div>

<%
}

if("accountnotfound".equals(msg)){
%>

<div class="alert alert-danger">

Account Not Found

</div>

<%
}

if("error".equals(msg)){
%>

<div class="alert alert-warning">

Something Went Wrong

</div>

<%
}
%>

<form action="../DepositServlet" method="post">

<div class="mb-3">

<label class="form-label">

Account Number

</label>

<input
type="text"
name="accountNumber"
class="form-control"
placeholder="Enter Account Number"
required>

</div>

<div class="mb-3">

<label class="form-label">

Deposit Amount

</label>

<input
type="number"
name="amount"
class="form-control"
placeholder="Enter Amount"
required>

</div>

<div class="text-center">

<button
type="submit"
class="btn btn-success btn-lg">

<i class="fa-solid fa-circle-plus"></i>

Deposit Money

</button>

<button
type="reset"
class="btn btn-secondary btn-lg">

Reset

</button>

</div>

</form>

</div>

</div>

</div>

</div>

</div>

<!-- Footer -->

<footer class="bg-dark text-white text-center p-3 mt-5">

© 2026 SK Mini Bank | Deposit Module

</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>