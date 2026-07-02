<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Open Account | SK Mini Bank</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<link rel="stylesheet" href="../css/dashboard.css">

</head>

<body>

<nav class="navbar navbar-dark bg-primary">

<div class="container-fluid">

<a class="navbar-brand fw-bold" href="dashboard.jsp">

SK Mini Bank Admin

</a>

<a href="../login.jsp" class="btn btn-light">

Logout

</a>

</div>

</nav>

<div class="container mt-5">

<div class="row justify-content-center">

<div class="col-md-8">

<div class="card shadow">

<div class="card-header bg-primary text-white">

<h3>

<i class="fa-solid fa-wallet"></i>

Open New Account

</h3>

</div>

<div class="card-body">

<form action="RegisterServlet" method="post">

<!-- Personal Details -->

<h4 class="section-title">

<i class="fas fa-user"></i>

Personal Details

</h4>
<!-- Personal Details -->

<div class="row">

    <div class="col-md-4 mb-3">
        <label>Full Name *</label>
        <input type="text" name="fullName" class="form-control" required>
    </div>

    <div class="col-md-4 mb-3">
        <label>Father Name *</label>
        <input type="text" name="fatherName" class="form-control" required>
    </div>

    <div class="col-md-4 mb-3">
        <label>Mother Name</label>
        <input type="text" name="motherName" class="form-control">
    </div>

</div>

<div class="row">

    <div class="col-md-3 mb-3">
        <label>Date of Birth *</label>
        <input type="date" name="dob" class="form-control" required>
    </div>

    <div class="col-md-3 mb-3">
        <label>Gender *</label>
        <select name="gender" class="form-select" required>
            <option value="">Select</option>
            <option>Male</option>
            <option>Female</option>
            <option>Other</option>
        </select>
    </div>

    <div class="col-md-3 mb-3">
        <label>Marital Status</label>
        <select name="maritalStatus" class="form-select">
            <option>Single</option>
            <option>Married</option>
            <option>Divorced</option>
            <option>Widow</option>
        </select>
    </div>

    <div class="col-md-3 mb-3">
        <label>Occupation</label>
        <input type="text" name="occupation" class="form-control">
    </div>

</div>

<!-- Contact Details -->

<h4 class="section-title">
    <i class="fas fa-phone"></i>
    Contact Details
</h4>

<div class="row">

    <div class="col-md-6 mb-3">
        <label>Mobile Number *</label>
        <input type="text" name="mobile" class="form-control" maxlength="10" required>
    </div>

    <div class="col-md-2 mb-3 d-grid">
        <label>&nbsp;</label>
        <button type="button" class="btn btn-primary">
            Send OTP
        </button>
    </div>

    <div class="col-md-4 mb-3">
        <label>Enter Mobile OTP</label>
        <input type="text" name="mobileOtp" class="form-control">
    </div>

</div>

<div class="row">

    <div class="col-md-6 mb-3">
        <label>Email Address *</label>
        <input type="email" name="email" class="form-control" required>
    </div>

    <div class="col-md-2 mb-3 d-grid">
        <label>&nbsp;</label>
        <button type="button" class="btn btn-success">
            Send OTP
        </button>
    </div>

    <div class="col-md-4 mb-3">
        <label>Enter Email OTP</label>
        <input type="text" name="emailOtp" class="form-control">
    </div>

</div>

<div class="row">

    <div class="col-md-6 mb-3">
        <label>Alternate Mobile Number</label>
        <input type="text" name="alternateMobile" class="form-control" maxlength="10">
    </div>

</div>
<!-- Identity Details -->

<h4 class="section-title">
    <i class="fas fa-id-card"></i>
    Identity Details
</h4>

<div class="row">

    <div class="col-md-6 mb-3">
        <label>Aadhaar Number *</label>
        <input type="text"
               name="aadhaar"
               class="form-control"
               maxlength="12"
               required>
    </div>

    <div class="col-md-6 mb-3">
        <label>PAN Number *</label>
        <input type="text"
               name="pan"
               class="form-control"
               maxlength="10"
               required>
    </div>

</div>

<!-- Address Details -->

<h4 class="section-title">
    <i class="fas fa-home"></i>
    Address Details
</h4>

<div class="row">

    <div class="col-md-12 mb-3">
        <label>Address *</label>
        <textarea name="address"
                  class="form-control"
                  rows="3"
                  required></textarea>
    </div>

</div>

<div class="row">

    <div class="col-md-4 mb-3">
        <label>City *</label>
        <input type="text"
               name="city"
               class="form-control"
               required>
    </div>

    <div class="col-md-4 mb-3">
        <label>State *</label>
        <input type="text"
               name="state"
               class="form-control"
               required>
    </div>

    <div class="col-md-4 mb-3">
        <label>Pincode *</label>
        <input type="text"
               name="pincode"
               class="form-control"
               maxlength="6"
               required>
    </div>

</div>

<!-- Nominee Details -->

<h4 class="section-title">
    <i class="fas fa-user-friends"></i>
    Nominee Details
</h4>

<div class="row">

    <div class="col-md-4 mb-3">
        <label>Nominee Name</label>
        <input type="text"
               name="nomineeName"
               class="form-control">
    </div>

    <div class="col-md-4 mb-3">
        <label>Relationship</label>
        <select name="relationship"
                class="form-select">

            <option value="">Select</option>
            <option>Father</option>
            <option>Mother</option>
            <option>Brother</option>
            <option>Sister</option>
            <option>Husband</option>
            <option>Wife</option>
            <option>Son</option>
            <option>Daughter</option>
            <option>Other</option>

        </select>
    </div>

    <div class="col-md-4 mb-3">
        <label>Nominee Mobile Number</label>
        <input type="text"
               name="nomineeMobile"
               class="form-control"
               maxlength="10">
    </div>

</div>
<!-- ========================= -->
<!-- Bank Details -->
<!-- ========================= -->

<h4 class="section-title">
    <i class="fas fa-university"></i>
    Bank Details
</h4>

<div class="row">

    <div class="col-md-3 mb-3">
        <label>Customer ID</label>
        <input type="text"
               name="customerId"
               class="form-control"
               value="${customerId}"
               readonly>
    </div>

    <div class="col-md-3 mb-3">
        <label>Account Number</label>
        <input type="text"
               name="accountNumber"
               class="form-control"
               value="${accountNumber}"
               readonly>
    </div>

    <div class="col-md-3 mb-3">
        <label>IFSC Code</label>
        <input type="text"
               name="ifscCode"
               class="form-control"
               value="SKMB0001001"
               readonly>
    </div>

    <div class="col-md-3 mb-3">
        <label>Branch</label>
        <input type="text"
               name="branch"
               class="form-control"
               value="Bareilly Main Branch"
               readonly>
    </div>

</div>

<div class="row">

    <div class="col-md-6 mb-3">
        <label>Account Type</label>
        <select name="accountType" class="form-select" required>
            <option value="">Select Account Type</option>
            <option>Saving Account</option>
            <option>Current Account</option>
        </select>
    </div>

    <div class="col-md-6 mb-3">
        <label>Opening Balance</label>
        <input type="number"
               name="balance"
               class="form-control"
               value="5000"
               required>
    </div>

</div>

<!-- ========================= -->
<!-- Security Details -->
<!-- ========================= -->

<h4 class="section-title">
    <i class="fas fa-lock"></i>
    Security Details
</h4>

<div class="row">

    <div class="col-md-6 mb-3">
        <label>Password *</label>
        <input type="password"
               name="password"
               class="form-control"
               required>
    </div>

    <div class="col-md-6 mb-3">
        <label>Confirm Password *</label>
        <input type="password"
               name="confirmPassword"
               class="form-control"
               required>
    </div>

</div>

<div class="row">

    <div class="col-md-6 mb-3">
        <label>4 Digit Transaction PIN *</label>
        <input type="password"
               name="transactionPin"
               class="form-control"
               maxlength="4"
               required>
    </div>

    <div class="col-md-6 mb-3">
        <label>Confirm Transaction PIN *</label>
        <input type="password"
               name="confirmTransactionPin"
               class="form-control"
               maxlength="4"
               required>
    </div>

</div>

<hr>

<div class="form-check mb-4">

    <input class="form-check-input"
           type="checkbox"
           required>

    <label class="form-check-label">

        I hereby declare that all the information provided by me is true and correct.

    </label>

</div>
<!-- ========================= -->
<!-- Buttons -->
<!-- ========================= -->

<hr>

<div class="row mt-4">

    <div class="col-md-4 d-grid mb-2">
        <button type="submit" class="btn btn-primary btn-lg">
            <i class="fas fa-user-plus"></i>
            Register Now
        </button>
    </div>

    <div class="col-md-4 d-grid mb-2">
        <button type="reset" class="btn btn-warning btn-lg">
            <i class="fas fa-rotate-left"></i>
            Reset
        </button>
    </div>

    <div class="col-md-4 d-grid mb-2">
        <a href="login.jsp" class="btn btn-success btn-lg">
            <i class="fas fa-right-to-bracket"></i>
            Login
        </a>
    </div>

</div>

</form>

</div>

</div>

</div>

</div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>