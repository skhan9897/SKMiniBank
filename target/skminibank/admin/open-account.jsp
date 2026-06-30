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

<form action="../AccountServlet" method="post">

<div class="alert alert-primary">
    <h5>
        <i class="fa-solid fa-user"></i>
        Customer Personal Details
    </h5>
</div>

<div class="row">

    <div class="col-md-6 mb-3">
        <label>Full Name</label>
        <input type="text" name="fullName"
               class="form-control" required>
    </div>

    <div class="col-md-6 mb-3">
        <label>Father Name</label>
        <input type="text" name="fatherName"
               class="form-control" required>
    </div>

    <div class="col-md-6 mb-3">
        <label>Date Of Birth</label>
        <input type="date" name="dob"
               class="form-control" required>
    </div>

    <div class="col-md-6 mb-3">
        <label>Gender</label>
        <select name="gender" class="form-select">
            <option>Male</option>
            <option>Female</option>
            <option>Other</option>
        </select>
    </div>

    <div class="col-md-6 mb-3">
        <label>Mobile Number</label>
        <input type="text" name="mobile"
               class="form-control" required>
    </div>

    <div class="col-md-6 mb-3">
        <label>Email</label>
        <input type="email" name="email"
               class="form-control" required>
    </div>

    <div class="col-md-6 mb-3">
        <label>Aadhaar Number</label>
        <input type="text" name="aadhaar"
               class="form-control" required>
    </div>

    <div class="col-md-6 mb-3">
        <label>PAN Number</label>
        <input type="text" name="pan"
               class="form-control" required>
    </div>

    <div class="col-md-12 mb-3">
        <label>Address</label>
        <textarea name="address"
                  class="form-control"
                  rows="3"></textarea>
    </div>

    <div class="col-md-4 mb-3">
        <label>City</label>
        <input type="text" name="city"
               class="form-control" required>
    </div>

    <div class="col-md-4 mb-3">
        <label>State</label>
        <input type="text" name="state"
               class="form-control" required>
    </div>

    <div class="col-md-4 mb-3">
        <label>Pincode</label>
        <input type="text" name="pincode"
               class="form-control" required>
    </div>

</div>

<div class="alert alert-success mt-4">
    <h5>
        <i class="fa-solid fa-wallet"></i>
        Bank Account Details
    </h5>
</div>

<div class="row">

    <div class="col-md-6 mb-3">
        <label>Customer ID</label>
        <input type="number"
               name="customerId"
               class="form-control"
               required>
    </div>

    <div class="col-md-6 mb-3">
        <label>Account Type</label>
        <select name="accountType"
                class="form-select">
            <option value="Savings">
                Savings Account
            </option>
            <option value="Current">
                Current Account
            </option>
        </select>
    </div>

    <div class="col-md-6 mb-3">
        <label>Branch</label>
        <select name="branchId"
                class="form-select">

            <option value="1">
                Bareilly Main
            </option>

            <option value="2">
                Lucknow
            </option>

            <option value="3">
                Delhi
            </option>

            <option value="4">
                Noida
            </option>

            <option value="5">
                Hyderabad
            </option>

        </select>
    </div>

    <div class="col-md-6 mb-3">
        <label>Opening Balance</label>
        <input type="number"
               name="balance"
               class="form-control"
               required>
    </div>

</div>

<div class="text-center mt-4">

    <button type="submit"
            class="btn btn-success btn-lg px-5">
        <i class="fa-solid fa-folder-plus"></i>
        Open Account
    </button>

    <button type="reset"
            class="btn btn-danger btn-lg px-5">
        <i class="fa-solid fa-rotate"></i>
        Reset
    </button>

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