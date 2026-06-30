<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>ATM Card Request</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body{
            background:#f4f7fb;
            font-family:Arial,sans-serif;
        }

        .card-box{
            width:500px;
            margin:60px auto;
            border-radius:15px;
            overflow:hidden;
            box-shadow:0 10px 25px rgba(0,0,0,.15);
        }

        .header{
            background:#0d6efd;
            color:#fff;
            padding:18px;
            text-align:center;
        }

        .header h3{
            margin:0;
        }

        .card-body{
            padding:30px;
            background:#fff;
        }

        .btn-bank{
            width:100%;
            background:#0d6efd;
            color:white;
            font-weight:bold;
        }

        .btn-bank:hover{
            background:#0b5ed7;
            color:white;
        }
    </style>
</head>

<body>

<div class="card-box">

    <div class="header">
        <h3>ATM Card Request</h3>
    </div>

    <div class="card-body">

        <form action="../ATMRequestServlet" method="post">

            <div class="mb-3">
                <label>Customer ID</label>
                <input type="number"
                       name="customerId"
                       class="form-control"
                       required>
            </div>

            <div class="mb-3">
                <label>Card Type</label>

                <select name="cardType" class="form-control" required>
                    <option value="">Select Card</option>
                    <option>RuPay Debit Card</option>
                    <option>Visa Debit Card</option>
                    <option>Master Debit Card</option>
                </select>
            </div>

            <button class="btn btn-bank">
                Apply ATM Card
            </button>

        </form>

    </div>

</div>

</body>
</html>