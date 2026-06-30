<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

<title>Fixed Deposit | SK Mini Bank</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<style>

body{
    background:#eef3f8;
}

.card{
    margin:40px auto;
    max-width:700px;
    border-radius:15px;
    box-shadow:0 10px 25px rgba(0,0,0,.2);
}

.card-header{
    background:#0d6efd;
    color:white;
    text-align:center;
    font-size:24px;
    font-weight:bold;
}

.btn-save{
    width:100%;
    height:50px;
    font-size:18px;
}

</style>

</head>

<body>

<div class="container">

<div class="card">

<div class="card-header">

<i class="fa-solid fa-piggy-bank"></i>

Fixed Deposit

</div>

<div class="card-body">

<form action="<%=request.getContextPath()%>/FixedDepositServlet" method="post">

<div class="mb-3">

<label>Account Number</label>

<input type="text"
name="accountNumber"
class="form-control"
required>

</div>

<div class="mb-3">

<label>FD Amount</label>

<input type="number"
name="amount"
class="form-control"
required>

</div>

<div class="mb-3">

<label>Interest Rate (%)</label>

<input type="number"
step="0.01"
name="interest"
class="form-control"
value="7.5"
required>

</div>

<div class="mb-3">

<label>Duration</label>

<select name="duration" class="form-control">

<option value="1">1 Year</option>
<option value="2">2 Years</option>
<option value="3">3 Years</option>
<option value="5">5 Years</option>

</select>

</div>

<button class="btn btn-primary btn-save">

<i class="fa-solid fa-circle-check"></i>

Create Fixed Deposit

</button>

</form>

</div>

</div>

</div>

</body>
</html>