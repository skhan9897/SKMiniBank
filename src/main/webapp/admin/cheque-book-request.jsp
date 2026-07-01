<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cheque Book Request | SK Mini Bank</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">

    <style>
        body{
            background:#eef3f8;
        }
        .card{
            margin-top:40px;
            border-radius:12px;
            box-shadow:0 5px 15px rgba(0,0,0,.15);
        }
        .card-header{
            background:#0d6efd;
            color:#fff;
            font-size:24px;
            font-weight:bold;
        }
        .btn-primary{
            width:100%;
        }
    </style>

</head>
<body>

<div class="container">

<div class="row justify-content-center">

<div class="col-md-8">

<div class="card">

<div class="card-header text-center">

<i class="fas fa-book"></i>

Cheque Book Request

</div>

<div class="card-body">

<%
String msg=request.getParameter("msg");

if("success".equals(msg)){
%>

<div class="alert alert-success">
Cheque Book Request Submitted Successfully.
</div>

<%
}

if("failed".equals(msg)){
%>

<div class="alert alert-danger">
Unable to Submit Request.
</div>

<%
}
%>

<form action="${pageContext.request.contextPath}/ChequeBookRequestServlet" method="post">

<div class="mb-3">

<label class="form-label">Account Number</label>

<input type="text"
name="accountNumber"
class="form-control"
placeholder="Enter Account Number"
required>

</div>

<div class="mb-3">

<label class="form-label">Customer Name</label>

<input type="text"
name="customerName"
class="form-control"
placeholder="Enter Customer Name"
required>

</div>

<div class="mb-3">

<label class="form-label">Mobile Number</label>

<input type="text"
name="mobile"
class="form-control"
placeholder="Enter Mobile Number"
required>

</div>

<div class="mb-3">

<label class="form-label">Number of Leaves</label>

<select name="leaves" class="form-select">

<option value="25">25 Leaves</option>

<option value="50">50 Leaves</option>

<option value="100">100 Leaves</option>

</select>

</div>

<div class="mb-3">

<label class="form-label">Delivery Address</label>

<textarea
name="address"
class="form-control"
rows="3"
required></textarea>

</div>

<button type="submit" class="btn btn-primary">

<i class="fas fa-paper-plane"></i>

Submit Request

</button>

</form>

</div>

</div>

</div>

</div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>