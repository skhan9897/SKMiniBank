<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDateTime"%>

<!DOCTYPE html>
<html>
<head>
    <title>ATM Card Request Status</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

    <style>
        body{
            background:#f4f7fc;
        }

        .card-box{
            max-width:900px;
            margin:30px auto;
        }

        .header{
            background:#0d6efd;
            color:white;
            padding:15px;
            text-align:center;
        }

        .status{
            font-size:18px;
            font-weight:bold;
            color:green;
        }

        .bank-title{
            text-align:center;
            margin:20px;
            color:#0d6efd;
            font-weight:bold;
        }
    </style>
</head>

<body>

<div class="container">

    <h2 class="bank-title">
        SK Mini Bank ATM Request Details
    </h2>

    <div class="card shadow card-box">

        <div class="header">
            ATM Card Request Submitted Successfully
        </div>

        <div class="card-body">

            <table class="table table-bordered">

                <tr>
                    <th>Customer ID</th>
                    <td>${customer.customerId}</td>
                </tr>

                <tr>
                    <th>Customer Name</th>
                    <td>${customer.fullName}</td>
                </tr>

                <tr>
                    <th>Email</th>
                    <td>${customer.email}</td>
                </tr>

                <tr>
                    <th>Mobile</th>
                    <td>${customer.mobile}</td>
                </tr>

                <tr>
                    <th>Address</th>
                    <td>${customer.address}</td>
                </tr>

                <tr>
                    <th>Account Number</th>
                    <td>${customer.accountNumber}</td>
                </tr>

                <tr>
                    <th>Card Type</th>
                    <td>${cardType}</td>
                </tr>

                <tr>
                    <th>Request Date</th>
                    <td>${requestDate}</td>
                </tr>

                <tr>
                    <th>Request Time</th>
                    <td>${requestTime}</td>
                </tr>

               <tr>
    <th>Status</th>
    <td style="color:red;font-weight:bold;font-size:18px;">
        PENDING
    </td>
</tr>

                <tr>
                    <th>Dispatch Status</th>
                    <td>Processing</td>
                </tr>

                <tr>
                    <th>Expected Delivery</th>
                    <td>Within 7 Working Days</td>
                </tr>

            </table>

            <div class="text-center">

                <a href="${pageContext.request.contextPath}/admin/dashboard.jsp"
   class="btn back">
   Back Dashboard
</a><a href="${pageContext.request.contextPath}/LogoutServlet"
   class="btn logout">
   Logout
</a>

            </div>

        </div>

    </div>

</div>

</body>
</html>