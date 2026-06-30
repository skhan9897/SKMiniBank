<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bank.model.Customer"%>

<%
Customer customer=(Customer)request.getAttribute("customer");

if(customer==null){
response.sendRedirect("../login.jsp");
return;
}
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Customer Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="../css/dashboard.css">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

</head>

<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">

<div class="container-fluid">

<a class="navbar-brand">

🏦 SK MINI BANK

</a>

<div class="text-white">

Welcome

<b>

<%=customer.getFullName()%>

</b>



<a href="<%=request.getContextPath()%>/LogoutServlet"
   class="btn btn-danger btn-sm">
    Logout
</a>

</a>

</div>

</div>

</nav>

<div class="container mt-4">
    <div class="row">

    <div class="col-md-4">

        <div class="card shadow mb-3">

            <div class="card-header bg-primary text-white">

                Customer Details

            </div>

            <div class="card-body">

                <p><b>Customer ID :</b>
                    <%=customer.getCustomerId()%>
                </p>

                <p><b>Customer Name :</b>
                    <%=customer.getFullName()%>
                </p>

                <p><b>Email :</b>
                    <%=customer.getEmail()%>
                </p>

                <p><b>Mobile :</b>
                    <%=customer.getMobile()%>
                </p>

                <p><b>Branch :</b>
                    <%=customer.getBranch()%>
                </p>

            </div>

        </div>

    </div>

    <div class="col-md-4">

        <div class="card shadow mb-3">

            <div class="card-header bg-success text-white">

                Account Details

            </div>

            <div class="card-body">

                <p><b>Account Number :</b>
                    <%=customer.getAccountNumber()%>
                </p>

                <p><b>IFSC Code :</b>
                    <%=customer.getIfscCode()%>
                </p>

                <p><b>Account Type :</b>
                    <%=customer.getAccountType()%>
                </p>

                <p><b>Status :</b>
                    <span class="badge bg-success">
                        <%=customer.getStatus()%>
                    </span>
                </p>

            </div>

        </div>

    </div>

    <div class="col-md-4">

        <div class="card shadow mb-3">

            <div class="card-header bg-warning">

                Balance

            </div>

            <div class="card-body text-center">

                <h2 class="text-success">

                    ₹ <%=customer.getBalance()%>

                </h2>

                <p>Available Balance</p>

            </div>

        </div>

    </div>

</div>
                    <div class="row mt-4">

    <div class="col-md-3 mb-3">
        <a href="balance.jsp" class="text-decoration-none">
            <div class="card shadow text-center">
                <div class="card-body">
                    <h1>💰</h1>
                    <h5>Balance</h5>
                </div>
            </div>
        </a>
    </div>

    <div class="col-md-3 mb-3">
          <a href="${pageContext.request.contextPath}/admin/transfer.jsp"
            <div class="card shadow text-center">
                <div class="card-body">
                    <h1>💸</h1>
                    <h5>Fund Transfer</h5>
                </div>
            </div>
        </a>
    </div>

    <div class="col-md-3 mb-3">
        <a href="passbook.jsp" class="text-decoration-none">
            <div class="card shadow text-center">
                <div class="card-body">
                    <h1>📖</h1>
                    <h5>Passbook</h5>
                </div>
            </div>
        </a>
    </div>

    <div class="col-md-3 mb-3">
       <a href="../TransactionServlet?accountNumber=<%=customer.getAccountNumber()%>" class="text-decoration-none">
            <div class="card shadow text-center">
                <div class="card-body">
                    <h1>📜</h1>
                    <h5>Transactions</h5>
                </div>
            </div>
        </a>
    </div>

    <div class="col-md-3 mb-3">
       <a href="${pageContext.request.contextPath}/customer/atm-request.jsp" class="text-decoration-none">
            <div class="card shadow text-center">
                <div class="card-body">
                    <h1>💳</h1>
                    <h5>ATM Card</h5>
                </div>
            </div>
        </a>
    </div>

    <div class="col-md-3 mb-3">
        <a href="../admin/loan.jsp" class="text-decoration-none">
            <div class="card shadow text-center">
                <div class="card-body">
                    <h1>🏦</h1>
                    <h5>Loan</h5>
                </div>
            </div>
        </a>
    </div>
            
            
            

    <div class="col-md-3 mb-3">
        <a href="../admin/fd.jsp" class="text-decoration-none">
            <div class="card shadow text-center">
                <div class="card-body">
                    <h1>📈</h1>
                    <h5>Fixed Deposit</h5>
                </div>
            </div>
        </a>
    </div>

    <div class="col-md-3 mb-3">
        <a href="profile.jsp" class="text-decoration-none">
            <div class="card shadow text-center">
                <div class="card-body">
                    <h1>👤</h1>
                    <h5>My Profile</h5>
                </div>
            </div>
        </a>
    </div>

</div>

</div>
            
            <div class="card mt-4">
    <div class="card-header bg-primary text-white">
        ATM Card Request Status
    </div>

    <div class="card-body">
        <table class="table table-bordered">
            <tr>
                <th>Card Type</th>
                <th>Request Date</th>
                <th>Status</th>
            </tr>

            <tr>
                <td>Debit Card</td>
                <td>24-06-2026</td>
                <td>Pending</td>
            </tr>
        </table>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>