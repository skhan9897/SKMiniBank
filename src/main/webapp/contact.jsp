<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us - SK Mini Bank</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
</head>

<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">

        <a class="navbar-brand fw-bold" href="index.jsp">
            SK Mini Bank
        </a>

        <button class="navbar-toggler" type="button"
                data-bs-toggle="collapse"
                data-bs-target="#menu">

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
                    <a class="nav-link active" href="contact.jsp">Contact</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="login.jsp">Login</a>
                </li>

            </ul>

        </div>

    </div>
</nav>

<!-- Contact -->

<div class="container py-5">

    <div class="text-center mb-5">

        <h2 class="text-primary fw-bold">
            Contact SK Mini Bank
        </h2>

        <p class="text-muted">
            We'd love to hear from you.
        </p>

    </div>

    <div class="row">

        <!-- Contact Form -->

        <div class="col-md-7">

            <div class="card shadow">

                <div class="card-body">

                    <form>

                        <div class="mb-3">

                            <label>Full Name</label>

                            <input type="text"
                                   class="form-control"
                                   placeholder="Enter Full Name">

                        </div>

                        <div class="mb-3">

                            <label>Email</label>

                            <input type="email"
                                   class="form-control"
                                   placeholder="Enter Email">

                        </div>

                        <div class="mb-3">

                            <label>Mobile Number</label>

                            <input type="text"
                                   class="form-control"
                                   placeholder="Enter Mobile Number">

                        </div>

                        <div class="mb-3">

                            <label>Message</label>

                            <textarea class="form-control"
                                      rows="5"
                                      placeholder="Write your message"></textarea>

                        </div>

                        <button class="btn btn-primary">
                            Send Message
                        </button>

                    </form>

                </div>

            </div>

        </div>

        <!-- Contact Details -->

        <div class="col-md-5">

            <div class="card shadow">

                <div class="card-body">

                    <h4 class="text-primary">
                        Contact Information
                    </h4>

                    <hr>

                    <p>
                        <i class="fa-solid fa-location-dot"></i>
                        Bareilly, Uttar Pradesh, India
                    </p>

                    <p>
                        <i class="fa-solid fa-phone"></i>
                        +91 9876543210
                    </p>

                    <p>
                        <i class="fa-solid fa-envelope"></i>
                        support@skminibank.com
                    </p>

                    <p>
                        <i class="fa-solid fa-clock"></i>
                        Mon - Sat : 9:00 AM - 6:00 PM
                    </p>

                </div>

            </div>

        </div>

    </div>

</div>

<!-- Footer -->

<footer class="bg-dark text-white text-center p-3 mt-5">

    © 2026 SK Mini Bank | All Rights Reserved

</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>