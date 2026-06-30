<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>

<title>Fund Transfer | SK Mini Bank</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"/>

<style>

body{
    background:#eef3f8;
    font-family:Arial,sans-serif;
}

.transfer-box{
    max-width:700px;
    margin:60px auto;
}

.card{
    border:none;
    border-radius:15px;
    overflow:hidden;
    box-shadow:0 10px 30px rgba(0,0,0,.15);
}

.card-header{
    background:linear-gradient(90deg,#0d6efd,#0099ff);
    color:white;
    text-align:center;
    padding:20px;
}

.card-header h3{
    margin:0;
    font-weight:bold;
}

.card-body{
    padding:30px;
}

.form-label{
    font-weight:bold;
}

.form-control{
    height:50px;
    border-radius:10px;
}

.btn-transfer{
    height:50px;
    width:100%;
    border:none;
    border-radius:10px;
    background:linear-gradient(90deg,#0d6efd,#0099ff);
    color:white;
    font-size:18px;
    font-weight:bold;
}

.btn-transfer:hover{
    background:linear-gradient(90deg,#0056d2,#007bff);
}

.icon{
    color:#0d6efd;
    margin-right:8px;
}

</style>

</head>

<body>

<div class="container">

<div class="transfer-box">

<div class="card">

<div class="card-header">

<h3>
<i class="fa-solid fa-money-bill-transfer"></i>
Fund Transfer
</h3>

<p class="mb-0">
Transfer Money Securely
</p>

</div>

<div class="card-body">

<form action="<%=request.getContextPath()%>/TransferServlet" method="post">
<div class="mb-3">

<label class="form-label">
<i class="fa-solid fa-building-columns icon"></i>
From Account Number
</label>

<input type="text"
name="fromAccount"
class="form-control"
placeholder="Enter Sender Account Number"
required>

</div>

<div class="mb-3">

<label class="form-label">
<i class="fa-solid fa-building-columns icon"></i>
To Account Number
</label>

<input type="text"
name="toAccount"
class="form-control"
placeholder="Enter Receiver Account Number"
required>

</div>

<div class="mb-4">

<label class="form-label">
<i class="fa-solid fa-indian-rupee-sign icon"></i>
Transfer Amount
</label>

<input type="number"
name="amount"
class="form-control"
placeholder="Enter Amount"
required>

</div>

<button type="submit" class="btn-transfer">

<i class="fa-solid fa-paper-plane"></i>

Transfer Money

</button>

</form>

</div>

</div>

</div>

</div>

</body>
</html>