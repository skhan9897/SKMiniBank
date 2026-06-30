<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>KYC Verified | SK Mini Bank</title>

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

<div class="card-header bg-success text-white">

<h3>
<i class="fa-solid fa-circle-check"></i>
KYC VERIFIED SUCCESSFULLY
</h3>

</div>

<div class="card-body">

<table class="table table-bordered">

<tr>
<th>Customer ID</th>
<td><%=request.getAttribute("customerId")%></td>
</tr>

<tr>
<th>Customer Name</th>
<td><%=request.getAttribute("customerName")%></td>
</tr>

<tr>
<th>Mobile</th>
<td><%=request.getAttribute("mobile")%></td>
</tr>

<tr>
<th>Email</th>
<td><%=request.getAttribute("email")%></td>
</tr>

<tr>
<th>Aadhaar</th>
<td><%=request.getAttribute("aadhaar")%></td>
</tr>

<tr>
<th>PAN</th>
<td><%=request.getAttribute("pan")%></td>
</tr>

<tr>
<th>KYC Status</th>
<td>
<span class="badge bg-success">
<%=request.getAttribute("kycStatus")%>
</span>
</td>
</tr>

<tr>
<th>Verification Date</th>
<td><%=request.getAttribute("verificationDate")%></td>
</tr>

</table>

<div class="text-center mt-4">

<a href="<%=request.getContextPath()%>/admin/dashboard.jsp"
   class="btn btn-primary">
    Dashboard
</a>

<a href="<%=request.getContextPath()%>/admin/customer-list.jsp"
   class="btn btn-success">
    Customer List
</a>
</div>

</div>

</div>

</div>

</body>
</html>