<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - SK Mini Bank</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/login.css">

</head>

<body>

<!-- Navbar -->

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">

    <div class="container">

        <a class="navbar-brand" href="index.jsp">
            SK Mini Bank
        </a>

        <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#menu">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="menu">

            <ul class="navbar-nav ms-auto">

                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="about.jsp">About</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="contact.jsp">Contact</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link active" href="login.jsp">Login</a>
                </li>

            </ul>

        </div>

    </div>

</nav>

<!-- Login -->

<div class="container mt-5">

    <div class="row justify-content-center">

        <div class="col-md-5">

            <div class="card shadow-lg">

                <div class="card-header bg-primary text-white text-center">

                    <h3>Customer Login</h3>

                </div>

                <div class="card-body">

                    <form action="LoginServlet" method="post">

                        <div class="mb-3">

                            <label>Account Number</label>

                            <input type="text"
                                   name="accountNumber"
                                   class="form-control"
                                   placeholder="Enter Account Number"
                                   required>

                        </div>

                        <div class="mb-3">

                            <label>Password</label>

                            <input type="password"
                                   name="password"
                                   class="form-control"
                                   placeholder="Enter Password"
                                   required>

                        </div>

                        <div class="d-grid">

                            <button class="btn btn-primary btn-lg">

                                Login

                            </button>

                        </div>

                    </form>

                    <hr>

                    <div class="text-center">

                        Don't have an account?

                        <a href="register.jsp">

                            Register Here

                        </a>

                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

<footer class="bg-dark text-white text-center p-3 mt-5">

    © 2026 SK Mini Bank | Secure Banking System

</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>