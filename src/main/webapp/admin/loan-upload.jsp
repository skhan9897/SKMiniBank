<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Upload Loan Documents | SK MINI BANK</title>

<meta name="viewport"
content="width=device-width, initial-scale=1">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">

<style>

body{

background:#eef3fb;

}

.card{

margin-top:30px;

border-radius:15px;

box-shadow:0 0 15px #d5d5d5;

}

.card-header{

background:#0056b3;

color:white;

font-size:22px;

font-weight:bold;

}

</style>

</head>

<body>

<div class="container">

<div class="card">

<div class="card-header">

🏦 SK MINI BANK

Loan Document Upload

</div>

<div class="card-body">

<form action="<%=request.getContextPath()%>/LoanDocumentServlet"

method="post"

enctype="multipart/form-data">

<input type="hidden"

name="loanId"

value="<%=request.getParameter("loanId")%>">

<input type="hidden"

name="customerId"

value="<%=request.getParameter("customerId")%>">

<input type="hidden"

name="accountNumber"

value="<%=request.getParameter("accountNumber")%>">

<div class="mb-3">

<label>

Aadhaar Card

</label>

<input

type="file"

name="aadhaar"

accept=".pdf,.jpg,.jpeg,.png"

class="form-control"

required>

</div>

<div class="mb-3">

<label>

PAN Card

</label>

<input

type="file"

name="pan"

accept=".pdf,.jpg,.jpeg,.png"

class="form-control"

required>

</div>

<div class="mb-3">

<label>

Salary Slip

</label>

<input

type="file"

name="salarySlip"

accept=".pdf,.jpg,.jpeg,.png"

class="form-control"

required>

</div>

<div class="mb-3">

<label>

Bank Statement

</label>

<input

type="file"

name="bankStatement"

accept=".pdf,.jpg,.jpeg,.png"

class="form-control"

required>

</div>

<div class="mb-3">

<label>

Address Proof

</label>

<input

type="file"

name="addressProof"

accept=".pdf,.jpg,.jpeg,.png"

class="form-control"

required>

</div>

<div class="text-center">

<button

type="submit"

class="btn btn-success btn-lg">

<i class="fa fa-upload"></i>

Upload Documents

</button>

<a

href="dashboard.jsp"

class="btn btn-secondary btn-lg">

Cancel

</a>

</div>

</form>

</div>

</div>

</div>

</body>

</html>