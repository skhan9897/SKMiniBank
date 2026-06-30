<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Deposit Money</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-5">

            <div class="card shadow">
                <div class="card-header bg-success text-white text-center">
                    <h3>Deposit Money</h3>
                </div>

                <div class="card-body">

                    <form action="DepositServlet" method="post">

                        <div class="mb-3">
                            <label>Account Number</label>
                            <input type="text" name="accountNumber" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label>Deposit Amount</label>
                            <input type="number" name="amount" class="form-control" required>
                        </div>

                        <button class="btn btn-success w-100">
                            Deposit
                        </button>

                    </form>

                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>