<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Activation Successful | SK Mini Bank</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
    body { background: #f0f4f8; font-family: 'Segoe UI', sans-serif; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }
    .receipt-card { background: white; width: 450px; border-radius: 30px; box-shadow: 0 20px 50px rgba(0,0,0,0.1); overflow: hidden; animation: slideUp 0.5s ease-out; }
    .header { background: #102a43; color: white; padding: 40px 20px; text-align: center; }
    .success-icon { font-size: 60px; color: #22c55e; background: white; width: 100px; height: 100px; display: flex; align-items: center; justify-content: center; border-radius: 50%; margin: 0 auto 20px; }
    .content { padding: 40px; }
    .detail-row { display: flex; justify-content: space-between; margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px dashed #d9e2ec; }
    .label { color: #627d98; font-size: 13px; font-weight: 600; text-transform: uppercase; }
    .value { color: #102a43; font-weight: 700; font-family: 'Courier New', monospace; }
    .creds-box { background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 15px; padding: 20px; margin-top: 20px; text-align: center; }
    @keyframes slideUp { from { transform: translateY(50px); opacity: 0; } to { transform: translateY(0); opacity: 1; } }
</style>
</head>
<body>

<div class="receipt-card">
    <div class="header">
        <div class="success-icon shadow-sm">
            <i class="fas fa-check-circle"></i>
        </div>
        <h3 class="fw-bold m-0">Activation Complete</h3>
        <p class="mb-0 opacity-75 mt-2"><%= request.getParameter("type") %></p>
    </div>

    <div class="content">
        <div class="detail-row">
            <span class="label">Account Number</span>
            <span class="value"><%= request.getParameter("acc") %></span>
        </div>
        <div class="detail-row">
            <span class="label">Status</span>
            <span class="value text-success">FULLY ACTIVATED</span>
        </div>

        <div class="creds-box">
            <div class="small text-muted mb-2">GENERATED CREDENTIALS</div>
            <div class="h5 fw-bold text-primary mb-0">
                <%= request.getParameter("creds") %>
            </div>
            <small class="text-danger d-block mt-2 fst-italic">* Share these details securely with the customer.</small>
        </div>

        <div class="text-center mt-5">
            <button onclick="window.print()" class="btn btn-outline-dark rounded-pill px-4 me-2">Print Receipt</button>
            <a href="AdminAllRequestServlet" class="btn btn-primary rounded-pill px-4">Done</a>
        </div>
    </div>
</div>

</body>
</html>
