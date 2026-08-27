<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String role = (session.getAttribute("role") != null) ? session.getAttribute("role").toString() : "";
    if(!"CUSTOMER".equalsIgnoreCase(role)) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    String userAcc = (String) session.getAttribute("accountNumber");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Open Fixed Deposit | SK Mini Bank</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <style>
        body { background: #f4f7fb; font-family: 'Segoe UI', sans-serif; }
        .card { max-width: 600px; margin: 50px auto; border: none; border-radius: 20px; box-shadow: 0 15px 35px rgba(0,0,0,0.1); }
        .card-header { background: linear-gradient(135deg, #0d6efd, #004fb1); color: white; border-radius: 20px 20px 0 0 !important; padding: 25px; text-align: center; }
        .btn-submit { background: linear-gradient(135deg, #0d6efd, #004fb1); border: none; padding: 12px; font-weight: 700; border-radius: 12px; }
    </style>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="card-header">
            <i class="fa-solid fa-piggy-bank fa-2x mb-3"></i>
            <h3>Open Fixed Deposit</h3>
            <p class="mb-0 opacity-75">Grow your savings with high interest rates</p>
        </div>
        <div class="card-body p-4">
            <% String msg = request.getParameter("msg"); if(msg != null) { %>
                <div class="alert alert-info alert-dismissible fade show" role="alert">
                    <%= msg %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <% } %>

            <form action="<%=request.getContextPath()%>/FixedDepositServlet" method="post">
                <div class="mb-4">
                    <label class="form-label fw-bold">Account Number</label>
                    <input type="text" name="accountNumber" class="form-control bg-light" value="<%= userAcc %>" readonly>
                </div>
                <div class="mb-4">
                    <label class="form-label fw-bold">Deposit Amount (₹)</label>
                    <input type="number" name="amount" class="form-control" placeholder="Min. ₹5,000" min="5000" required>
                    <div class="form-text">Amount will be deducted from your savings account.</div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <label class="form-label fw-bold">Duration</label>
                        <select name="duration" class="form-select" id="durationSelect">
                            <option value="1">1 Year</option>
                            <option value="2">2 Years</option>
                            <option value="3">3 Years</option>
                            <option value="5">5 Years</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label fw-bold">Interest Rate (%)</label>
                        <input type="text" name="interest" class="form-control bg-light" value="7.5" readonly>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary btn-submit w-100 mt-2">CONFIRM & OPEN FD</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
