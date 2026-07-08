<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Internet Banking Request</title>

<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<style>

body{
background:#f4f7fc;
font-family:Arial,sans-serif;
}

.container{
max-width:900px;
margin:40px auto;
}

.card{
border:none;
border-radius:12px;
box-shadow:0 0 20px rgba(0,0,0,.15);
}

.card-header{
background:#0d6efd;
color:#fff;
font-size:24px;
font-weight:bold;
padding:18px;
}

.btn-save{
background:#0d6efd;
color:white;
}

.btn-save:hover{
background:#0b5ed7;
color:white;
}

</style>

</head>

<body>

<div class="container">

<div class="card">

<div class="card-header">

<i class="fa fa-globe"></i>

Internet Banking Registration

</div>

<div class="card-body">

<form action="<%=request.getContextPath()%>/InternetBankingServlet"
method="post">

<div class="row">

<div class="col-md-6 mb-3">

<label>Customer ID</label>

<input type="number"
name="customerId"
class="form-control"
required>

</div>

<div class="col-md-6 mb-3">

<label>Account Number</label>

<input type="text"
name="accountNumber"
class="form-control"
required>

</div>

<div class="col-md-6 mb-3">

<label>Customer Name</label>

<input type="text"
name="customerName"
class="form-control"
required>

</div>

<div class="col-md-6 mb-3">

<label>Mobile Number</label>

<input type="text"
name="mobile"
class="form-control"
required>

</div>
    <div class="col-md-6 mb-3">

<label>Email</label>

<input type="email"
name="email"
class="form-control"
required>

</div>

<div class="col-md-6 mb-3">

<label>Username</label>

<input type="text"
name="username"
class="form-control"
required>

</div>

<div class="col-md-6 mb-3">

<label>Password</label>

<input type="password"
name="password"
class="form-control"
required>

</div>

<div class="col-md-6 mb-3">

<label>Confirm Password</label>

<input type="password"
name="confirmPassword"
class="form-control"
required>

</div>

</div>

<div class="text-center mt-4">

<button type="submit"
class="btn btn-save btn-lg">

<i class="fa fa-paper-plane"></i>

Submit Request

</button>

<a href="internet-banking-request.jsp"
class="btn btn-secondary btn-lg">

<i class="fa fa-arrow-left"></i>

Back

</a>

</div>

</form>

</div>

</div>

</div>

</body>

</html>