<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Objects"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>KYC Verified | SK Mini Bank</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand fw-bold" href="dashboard.jsp">SK Mini Bank Admin</a>
            <a href="../LogoutServlet" class="btn btn-light">Logout</a>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="card shadow-lg border-0">
            <div class="card-header bg-success text-white py-3">
                <h3 class="mb-0"><i class="fa-solid fa-circle-check me-2"></i>KYC VERIFIED SUCCESSFULLY</h3>
            </div>
            <div class="card-body p-4">
                <table class="table table-striped table-hover border">
                    <tr>
                        <th width="30%">Customer ID</th>
                        <td><%= Objects.toString(request.getAttribute("customerId"), "---") %></td>
                    </tr>
                    <tr>
                        <th>Customer Name</th>
                        <td class="fw-bold"><%= Objects.toString(request.getAttribute("customerName"), "---") %></td>
                    </tr>
                    <tr>
                        <th>Mobile</th>
                        <td><%= Objects.toString(request.getAttribute("mobile"), "---") %></td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td><%= Objects.toString(request.getAttribute("email"), "---") %></td>
                    </tr>
                    <tr>
                        <th>Aadhaar</th>
                        <td><%= Objects.toString(request.getAttribute("aadhaar"), "---") %></td>
                    </tr>
                    <tr>
                        <th>PAN</th>
                        <td><%= Objects.toString(request.getAttribute("pan"), "---") %></td>
                    </tr>
                    <tr>
                        <th>KYC Status</th>
                        <td><span class="badge bg-success"><%= Objects.toString(request.getAttribute("kycStatus"), "VERIFIED") %></span></td>
                    </tr>
                    <tr>
                        <th>Verification Date</th>
                        <td><%= Objects.toString(request.getAttribute("verificationDate"), "Just now") %></td>
                    </tr>
                </table>

                <div class="text-center mt-4 gap-2 d-flex justify-content-center">
                    <a href="SKMiniBank-System.jsp" class="btn btn-primary px-4">Admin Dashboard</a>
                    <a href="customer-list.jsp" class="btn btn-outline-success px-4">View All Customers</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
