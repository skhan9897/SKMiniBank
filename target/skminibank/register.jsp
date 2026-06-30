<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Customer Registration | SK Mini Bank</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" href="css/style.css">

</head>

<body class="bg-light">

<div class="container mt-5">

<div class="row justify-content-center">

<div class="col-lg-8">

<div class="card shadow-lg">

<div class="card-header bg-primary text-white text-center">

<h2>SK Mini Bank</h2>

<h5>Customer Registration Form</h5>

</div>

<div class="card-body">

    <form action="RegisterServlet" method="post">
<div class="row">

<div class="col-md-6 mb-3">
<label class="form-label">Full Name</label>
<input type="text" class="form-control"
name="fullname" required>
</div>

<div class="col-md-6 mb-3">
<label class="form-label">Father Name</label>
<input type="text" class="form-control"
name="fathername" required>
</div>

<div class="col-md-6 mb-3">
<label class="form-label">Date of Birth</label>
<input type="date" class="form-control"
name="dob" required>
</div>

<div class="col-md-6 mb-3">
<label class="form-label">Gender</label>
<select class="form-control" name="gender" required>
<option value="">Select Gender</option>
<option value="Male">Male</option>
<option value="Female">Female</option>
<option value="Other">Other</option>
</select>
</div>

<div class="col-md-6 mb-3">
<label class="form-label">Mobile Number</label>
<input type="text" class="form-control"
name="mobile" maxlength="10" required>
</div>

<div class="col-md-6 mb-3">
<label class="form-label">Email Address</label>
<input type="email" class="form-control"
name="email" required>
</div>

</div>
    <div class="row">

    <div class="col-md-6 mb-3">
        <label class="form-label">Aadhaar Number</label>
        <input type="text"
               class="form-control"
               name="aadhaar"
               maxlength="12"
               required>
    </div>

    <div class="col-md-6 mb-3">
        <label class="form-label">PAN Number</label>
        <input type="text"
               class="form-control"
               name="pan"
               maxlength="10"
               required>
    </div>

    <div class="col-md-12 mb-3">
        <label class="form-label">Address</label>
        <textarea class="form-control"
                  name="address"
                  rows="3"
                  required></textarea>
    </div>

    <div class="col-md-4 mb-3">
        <label class="form-label">City</label>
        <input type="text"
               class="form-control"
               name="city"
               required>
    </div>

    <div class="col-md-4 mb-3">
        <label class="form-label">State</label>
        <input type="text"
               class="form-control"
               name="state"
               required>
    </div>

    <div class="col-md-4 mb-3">
        <label class="form-label">Pincode</label>
        <input type="text"
               class="form-control"
               name="pincode"
               maxlength="6"
               required>
    </div>

    <div class="col-md-6 mb-3">
        <label class="form-label">Password</label>
        <input type="password"
               class="form-control"
               name="password"
               required>
    </div>

    <div class="col-md-6 mb-3">
        <label class="form-label">Confirm Password</label>
        <input type="password"
               class="form-control"
               name="confirmPassword"
               required>
    </div>

</div>
    <div class="row">

    <div class="col-md-6 mb-3">
        <label class="form-label">Account Type</label>
        <select class="form-control" name="accountType" required>
            <option value="SAVING">Saving Account</option>
            <option value="CURRENT">Current Account</option>
        </select>
    </div>

    <div class="col-md-6 mb-3">
        <label class="form-label">Branch</label>
        <input type="text"
               class="form-control"
               name="branch"
               value="Bareilly Main Branch"
               readonly>
    </div>

    <div class="col-md-6 mb-3">
        <label class="form-label">IFSC Code</label>
        <input type="text"
               class="form-control"
               name="ifscCode"
               value="SKMB0001001"
               readonly>
    </div>

    <div class="col-md-6 mb-3">
        <label class="form-label">Opening Balance</label>
        <input type="number"
               class="form-control"
               name="balance"
               value="5000"
               readonly>
    </div>

    <div class="col-md-6 mb-3">
        <label class="form-label">Status</label>
        <input type="text"
               class="form-control"
               name="status"
               value="ACTIVE"
               readonly>
    </div>

    <div class="col-md-12 text-center mt-4">

        <button type="submit"
                class="btn btn-primary btn-lg">
            Register Now
        </button>

        <a href="login.jsp"
           class="btn btn-success btn-lg">
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
