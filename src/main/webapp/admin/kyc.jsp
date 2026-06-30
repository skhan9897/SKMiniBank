<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>KYC Verification | SK Mini Bank</title>

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

        <a href="../LogoutServlet" class="btn btn-light">
            Logout
        </a>
    </div>
</nav>

<div class="container mt-5">

<div class="card shadow-lg">

<div class="card-header bg-primary text-white">

<h3>
<i class="fa-solid fa-id-card"></i>
KYC Verification
</h3>

</div>

<div class="card-body">

<form action="../KYCServlet" method="post">

<div class="row">

    <div class="col-md-6 mb-3">
        <label>Customer ID</label>
        <input type="number"
               id="customerId"
               name="customerId"
               class="form-control"
               onchange="getCustomerDetails()"
               required>
    </div>

    <div class="col-md-6 mb-3">
        <label>Customer Name</label>
        <input type="text"
               id="fullName"
               name="fullName"
               class="form-control"
               readonly>
    </div>

    <div class="col-md-6 mb-3">
        <label>Mobile Number</label>
        <input type="text"
               id="mobile"
               name="mobile"
               class="form-control"
               readonly>
    </div>

    <div class="col-md-6 mb-3">
        <label>Email</label>
        <input type="email"
               id="email"
               name="email"
               class="form-control"
               readonly>
    </div>

    <div class="col-md-6 mb-3">
        <label>Aadhaar Number</label>
        <input type="text"
               id="aadhaar"
               name="aadhaar"
               class="form-control"
               readonly>
    </div>

    <div class="col-md-6 mb-3">
        <label>PAN Number</label>
        <input type="text"
               id="pan"
               name="pan"
               class="form-control"
               readonly>
    </div>

    <div class="col-md-12 mb-3">
        <label>Address</label>
        <textarea id="address"
                  name="address"
                  class="form-control"
                  rows="3"
                  readonly></textarea>
    </div>

    <div class="col-md-6 mb-3">
        <label>KYC Status</label>
        <select name="kycStatus" class="form-select">
            <option value="Pending">Pending</option>
            <option value="Verified">Verified</option>
            <option value="Rejected">Rejected</option>
        </select>
    </div>

    <div class="col-md-6 mb-3">
        <label>Verification Date</label>
        <input type="date"
               name="verificationDate"
               class="form-control"
               required>
    </div>

</div>

<div class="text-center mt-3">
    <button type="submit"
            class="btn btn-success btn-lg">
        <i class="fa-solid fa-check"></i>
        Verify KYC
    </button>

    <button type="reset"
            class="btn btn-danger btn-lg">
        Reset
    </button>
</div>

</form>

<script>
function getCustomerDetails() {

    let customerId =
        document.getElementById("customerId").value;

    if(customerId==""){
        return;
    }

    fetch("../CustomerDetailsServlet?id=" + customerId)

    .then(response => response.json())

    .then(data => {

        document.getElementById("fullName").value =
            data.fullName || "";

        document.getElementById("mobile").value =
            data.mobile || "";

        document.getElementById("email").value =
            data.email || "";

        document.getElementById("aadhaar").value =
            data.aadhaar || "";

        document.getElementById("pan").value =
            data.pan || "";

        document.getElementById("address").value =
            data.address || "";
    })

    .catch(error => {
        console.log(error);
    });
}
</script>
    
</div>

</div>

</div>

</body>
</html>