<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SK Mini Bank | Admin Login</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">

<style>
    body {
        margin: 0;
        padding: 0;
        width: 100%;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        background: #0f0c29;
        overflow: hidden;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    /* Animated RGB Wave Background */
    .bg-container {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(135deg, #0f0c29, #302b63, #24243e);
        z-index: -1;
    }

    .wave {
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 100px;
        background: url('https://i.imgur.com/G5qE7U0.png');
        background-size: 1000px 100px;
    }

    .wave1 { animation: animate 30s linear infinite; z-index: 1; opacity: 0.3; bottom: 0; filter: hue-rotate(0deg); }
    .wave2 { animation: animate2 15s linear infinite; z-index: 2; opacity: 0.2; bottom: 10px; filter: hue-rotate(90deg); }
    .wave3 { animation: animate 10s linear infinite; z-index: 3; opacity: 0.1; bottom: 15px; filter: hue-rotate(180deg); }

    @keyframes animate {
        0% { background-position-x: 0; }
        100% { background-position-x: 1000px; }
    }

    @keyframes animate2 {
        0% { background-position-x: 0; }
        100% { background-position-x: -1000px; }
    }

    /* Glass Login Box */
    .login-box {
        width: 450px;
        background: rgba(255, 255, 255, 0.05);
        backdrop-filter: blur(20px);
        border: 1px solid rgba(255, 255, 255, 0.1);
        border-radius: 30px;
        padding: 40px;
        box-shadow: 0 25px 50px rgba(0,0,0,0.5);
        color: white;
        z-index: 10;
        animation: slideUp 0.8s cubic-bezier(0.2, 0.8, 0.2, 1);
    }

    @keyframes slideUp {
        from { opacity: 0; transform: translateY(50px); }
        to { opacity: 1; transform: translateY(0); }
    }

    .logo-area {
        text-align: center;
        margin-bottom: 30px;
    }

    .logo-area i {
        font-size: 60px;
        background: linear-gradient(135deg, #00d2ff, #3a7bd5);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        margin-bottom: 10px;
    }

    .logo-area h2 {
        font-weight: 800;
        letter-spacing: 1px;
        margin: 0;
    }

    .logo-area p {
        color: rgba(255,255,255,0.6);
        font-size: 14px;
        text-transform: uppercase;
        letter-spacing: 2px;
    }

    .form-label {
        font-weight: 600;
        font-size: 14px;
        color: rgba(255,255,255,0.8);
    }

    .form-control {
        background: rgba(255,255,255,0.1);
        border: 1px solid rgba(255,255,255,0.1);
        height: 55px;
        border-radius: 15px;
        color: white;
        padding-left: 50px;
        transition: 0.3s;
    }

    .form-control:focus {
        background: rgba(255,255,255,0.15);
        border-color: #00d2ff;
        color: white;
        box-shadow: 0 0 15px rgba(0, 210, 255, 0.3);
    }

    .input-group {
        position: relative;
    }

    .input-group-text {
        position: absolute;
        left: 15px;
        top: 50%;
        transform: translateY(-50%);
        background: transparent;
        border: none;
        color: rgba(255,255,255,0.5);
        z-index: 10;
        font-size: 18px;
    }

    .btn-login {
        height: 55px;
        background: linear-gradient(to right, #00d2ff, #3a7bd5);
        border: none;
        border-radius: 15px;
        font-weight: 800;
        font-size: 16px;
        letter-spacing: 1px;
        margin-top: 20px;
        box-shadow: 0 10px 20px rgba(58, 123, 213, 0.3);
        transition: 0.3s;
    }

    .btn-login:hover {
        transform: translateY(-3px);
        box-shadow: 0 15px 30px rgba(58, 123, 213, 0.5);
    }

    .btn-home {
        background: transparent;
        border: 1px solid rgba(255,255,255,0.2);
        color: white;
        border-radius: 15px;
        height: 45px;
        font-weight: 600;
        margin-top: 15px;
    }

    .btn-home:hover {
        background: rgba(255,255,255,0.05);
        color: #00d2ff;
        border-color: #00d2ff;
    }

    .footer {
        position: absolute;
        bottom: 30px;
        width: 100%;
        text-align: center;
        color: rgba(255,255,255,0.3);
        font-size: 12px;
        letter-spacing: 1px;
    }
</style>
</head>
<body>

    <div class="bg-container"></div>
    <div class="wave wave1"></div>
    <div class="wave wave2"></div>
    <div class="wave wave3"></div>

    <div class="login-box">
        <div class="logo-area">
            <i class="fas fa-shield-halved"></i>
            <h2>SK MINI BANK</h2>
            <p>Admin Gateway</p>
        </div>

        <%
        String error=(String)request.getAttribute("error");
        if(error!=null){
        %>
        <div class="alert alert-danger border-0 bg-danger text-white text-center rounded-3 mb-4" style="--bs-bg-opacity: .4;">
            <i class="fa fa-circle-exclamation me-2"></i> <%=error%>
        </div>
        <%
        }
        %>

        <form action="<%=request.getContextPath()%>/AdminLoginServlet" method="post">
            <div class="mb-4">
                <label class="form-label">Administrator ID</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fa fa-user-shield"></i></span>
                    <input type="text" name="adminId" class="form-control" placeholder="Admin Username" required>
                </div>
            </div>

            <div class="mb-4">
                <label class="form-label">Security Password</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fa fa-lock"></i></span>
                    <input type="password" name="password" class="form-control" placeholder="••••••••" required>
                </div>
            </div>

            <button type="submit" class="btn btn-primary w-100 btn-login">
                AUTHORIZE ACCESS
            </button>

            <a href="SKMiniBank.jsp" class="btn btn-home w-100">
                <i class="fa fa-house-chimney me-2"></i> BACK TO TERMINAL
            </a>
        </form>
    </div>

    <div class="footer">
        © 2026 SK MINI BANK | DEVELOPED BY <b>SAJID KHAN</b>
    </div>

</body>
</html>