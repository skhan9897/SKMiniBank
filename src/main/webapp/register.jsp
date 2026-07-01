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

<!-- Personal Details -->
<h4 class="text-primary mb-3">Personal Details</h4>

Full Name *
Father Name *
Mother Name
Date of Birth *
Gender *
Marital Status
Occupation

<hr>

<!-- Contact Details -->
<h4 class="text-primary mb-3">Contact Details</h4>

Mobile Number *
[Send OTP]

Enter Mobile OTP
[Verify OTP]

Email Address *
[Send OTP]

Enter Email OTP
[Verify OTP]

Alternate Mobile Number

<hr>

<!-- Identity Details -->
<h4 class="text-primary mb-3">Identity Details</h4>

Aadhaar Number *
PAN Number *

<hr>

<!-- Address -->
<h4 class="text-primary mb-3">Address Details</h4>

Address *
City *
State *
Pincode *

<hr>

<!-- Nominee -->
<h4 class="text-primary mb-3">Nominee Details</h4>

Nominee Name
Relationship
Nominee Mobile Number

<hr>

<!-- Bank Details -->
<h4 class="text-primary mb-3">Bank Details</h4>

Account Type
(Saving / Current)

Branch
(Bareilly Main Branch)

IFSC
(SKMB0001001)

Opening Balance
₹5000

<hr>

<!-- Security -->
<h4 class="text-primary mb-3">Security Details</h4>

Password *
Confirm Password *

4 Digit Transaction PIN *
Confirm Transaction PIN *

<hr>

<!-- Declaration -->

☑ I hereby declare that all the information provided by me is true and correct.

<hr>

[ Register Now ]
[ Reset ]
[ Login ]

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
