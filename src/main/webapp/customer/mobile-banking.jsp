<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mobile Banking Registration</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<div class="container mt-5">

    <div class="card">

        <div class="card-header bg-primary text-white">
            <h3>Mobile Banking Registration</h3>
        </div>

        <div class="card-body">

            <form action="<%=request.getContextPath()%>/MobileBankingServlet" method="post">

                <div class="mb-3">
                    <label>Customer ID</label>
                    <input type="number" name="customerId" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label>Account Number</label>
                    <input type="text" name="accountNumber" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label>Customer Name</label>
                    <input type="text" name="customerName" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label>Mobile Number</label>
                    <input type="text" name="mobile" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label>Email</label>
                    <input type="email" name="email" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label>Username</label>
                    <input type="text" name="username" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label>Password</label>
                    <input type="password" name="password" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-success">
                    Apply Mobile Banking
                </button>

            </form>

        </div>

    </div>

</div>

</body>
</html>