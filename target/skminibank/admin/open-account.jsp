<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>SK Mini Bank | Open New Account</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/dashboard.css">

<style>

body{

background:#eef3fb;

font-family:'Segoe UI',sans-serif;

}

.main-card{

max-width:1200px;

margin:30px auto;

background:#fff;

border-radius:20px;

box-shadow:0 10px 30px rgba(0,0,0,.15);

overflow:hidden;

}

.header{

background:linear-gradient(90deg,#0d6efd,#003b8e);

padding:25px;

color:#fff;

}

.header h2{

font-weight:bold;

margin:0;

}

.header p{

margin-top:5px;

margin-bottom:0;

opacity:.9;

}

.section-title{

background:#0d6efd;

color:#fff;

padding:10px 15px;

border-radius:8px;

margin-top:25px;

margin-bottom:20px;

font-size:18px;

font-weight:bold;

}

.form-label{

font-weight:600;

}

.form-control,

.form-select{

border-radius:10px;

padding:10px;

}

.form-control:focus,

.form-select:focus{

border-color:#0d6efd;

box-shadow:0 0 8px rgba(13,110,253,.25);

}

.btn-submit{

background:#0d6efd;

color:#fff;

font-weight:bold;

padding:12px 35px;

border-radius:10px;

}

.btn-submit:hover{

background:#003b8e;

color:#fff;

}

.btn-reset{

padding:12px 35px;

border-radius:10px;

}

</style>

</head>

<body>

<div class="main-card">

<div class="header">

<h2>

<i class="fas fa-university"></i>

SK Mini Bank

</h2>

<p>

Open New Savings / Current Account

</p>

</div>

<div class="container-fluid p-4">

<form action="<%=request.getContextPath()%>/RegisterServlet"

method="post">

<div class="section-title">

<i class="fa fa-user"></i>

Personal Details

</div>

<div class="row">

<div class="col-md-4 mb-3">

<label class="form-label">

Full Name

</label>

<input type="text"

name="fullName"

class="form-control"

placeholder="Enter Full Name"

required>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Father Name

</label>

<input type="text"

name="fatherName"

class="form-control"

placeholder="Enter Father Name"

required>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Mother Name

</label>

<input type="text"

name="motherName"

class="form-control"

placeholder="Enter Mother Name"

required>

</div>
    <div class="col-md-4 mb-3">

<label class="form-label">

Date of Birth

</label>

<input type="date"

name="dob"

class="form-control"

required>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Gender

</label>

<select name="gender"

class="form-select"

required>

<option value="">Select Gender</option>

<option>Male</option>

<option>Female</option>

<option>Other</option>

</select>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Marital Status

</label>

<select name="maritalStatus"

class="form-select"

required>

<option value="">Select</option>

<option>Single</option>

<option>Married</option>

<option>Divorced</option>

<option>Widowed</option>

</select>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Occupation

</label>

<input type="text"

name="occupation"

class="form-control"

placeholder="Occupation"

required>

</div>

</div>

<!-- Contact Details -->

<div class="section-title">

<i class="fa fa-phone"></i>

Contact Details

</div>

<div class="row">

<div class="col-md-4 mb-3">

<label class="form-label">

Mobile Number

</label>

<input type="text"

name="mobile"

class="form-control"

maxlength="10"

placeholder="10 Digit Mobile Number"

required>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Alternate Mobile

</label>

<input type="text"

name="alternateMobile"

class="form-control"

maxlength="10"

placeholder="Alternate Mobile Number">

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Email Address

</label>

<input type="email"

name="email"

class="form-control"

placeholder="example@gmail.com"

required>

</div>

</div>
<!-- Identity Details -->

<div class="section-title">

<i class="fa fa-id-card"></i>

Identity Details

</div>

<div class="row">

<div class="col-md-6 mb-3">

<label class="form-label">

Aadhaar Number

</label>

<input type="text"

name="aadhaar"

class="form-control"

maxlength="12"

placeholder="Enter Aadhaar Number"

required>

</div>

<div class="col-md-6 mb-3">

<label class="form-label">

PAN Number

</label>

<input type="text"

name="pan"

class="form-control"

maxlength="10"

style="text-transform:uppercase"

placeholder="ABCDE1234F"

required>

</div>

</div>

<!-- Address Details -->

<div class="section-title">

<i class="fa fa-location-dot"></i>

Address Details

</div>

<div class="row">

<div class="col-md-12 mb-3">

<label class="form-label">

Complete Address

</label>

<textarea

name="address"

class="form-control"

rows="3"

placeholder="Enter Complete Address"

required></textarea>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

City

</label>

<input type="text"

name="city"

class="form-control"

required>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

State

</label>

<input type="text"

name="state"

class="form-control"

required>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Pincode

</label>

<input type="text"

name="pincode"

class="form-control"

maxlength="6"

required>

</div>

</div>

<!-- Nominee Details -->

<div class="section-title">

<i class="fa fa-users"></i>

Nominee Details

</div>

<div class="row">

<div class="col-md-4 mb-3">

<label class="form-label">

Nominee Name

</label>

<input type="text"

name="nomineeName"

class="form-control"

required>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Relationship

</label>

<select

name="relationship"

class="form-select"

required>

<option value="">Select Relationship</option>

<option>Father</option>

<option>Mother</option>

<option>Brother</option>

<option>Sister</option>

<option>Wife</option>

<option>Husband</option>

<option>Son</option>

<option>Daughter</option>

<option>Friend</option>

<option>Other</option>

</select>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Nominee Mobile Number

</label>

<input type="text"

name="nomineeMobile"

class="form-control"

maxlength="10"

required>

</div>

</div>
<!-- Bank Account Details -->

<div class="section-title">

<i class="fa fa-building-columns"></i>

Bank Account Details

</div>

<div class="row">

<div class="col-md-4 mb-3">

<label class="form-label">

Account Type

</label>

<select name="accountType"

class="form-select"

required>

<option value="">Select Account Type</option>

<option>Savings Account</option>

<option>Current Account</option>

<option>Salary Account</option>

<option>Student Account</option>

<option>Senior Citizen Account</option>

</select>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Opening Balance (₹)

</label>

<input type="number"

name="balance"

class="form-control"

min="500"

value="1000"

required>

</div>

<div class="col-md-4 mb-3">

<label class="form-label">

Branch

</label>

<input type="text"

class="form-control"

value="Bareilly Main Branch"

readonly>

</div>

</div>

<!-- Security Details -->

<div class="section-title">

<i class="fa fa-shield-halved"></i>

Security Details

</div>

<div class="row">

<div class="col-md-6 mb-3">

<label class="form-label">

Login Password

</label>

<input type="password"

name="password"

class="form-control"

placeholder="Create Login Password"

required>

</div>

<div class="col-md-6 mb-3">

<label class="form-label">

Transaction PIN

</label>

<input type="password"

name="transactionPin"

class="form-control"

maxlength="4"

placeholder="4 Digit PIN"

required>

</div>

</div>

<hr>

<div class="text-center mt-4">

<button type="submit"

class="btn btn-submit btn-lg">

<i class="fa fa-user-plus"></i>

Open Account

</button>

&nbsp;&nbsp;

<button type="reset"

class="btn btn-secondary btn-lg">

<i class="fa fa-rotate-left"></i>

Reset

</button>

</div>

</form>

<%

String success=request.getParameter("success");
String error=request.getParameter("error");

if(success!=null){

%>

<div class="alert alert-success mt-4">

<i class="fa fa-circle-check"></i>

<%=success%>

</div>

<%

}

if(error!=null){

%>

<div class="alert alert-danger mt-4">

<i class="fa fa-circle-xmark"></i>

<%=error%>

</div>

<%

}

%>

</div>

<footer class="text-center mt-4 mb-3">

<hr>

<h5 class="text-primary">

🏦 SK Mini Bank Management System

</h5>

<p>

Secure Banking | Digital Banking | Trusted Banking

</p>

<p>

© 2026 SK Mini Bank | Developed By Sajid Khan

</p>

</footer>

<script>

document.querySelector("form").addEventListener("submit",function(e){

let mobile=document.getElementsByName("mobile")[0].value;

let pin=document.getElementsByName("transactionPin")[0].value;

if(mobile.length!=10){

alert("Please enter a valid 10 digit Mobile Number");

e.preventDefault();

return;

}

if(pin.length!=4){

alert("Transaction PIN must be exactly 4 digits.");

e.preventDefault();

return;

}

});

</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>