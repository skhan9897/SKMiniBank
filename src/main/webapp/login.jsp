<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customer Login | SK Mini Bank</title>

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

    .bg-container {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(135deg, #0f0c29, #302b63, #24243e);
        z-index: -1;
    }

    /* SVG RGB Waves */
    .wave-container {
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 15vh;
        z-index: 1;
    }

    .waves {
        position: relative;
        width: 100%;
        height: 15vh;
        margin-bottom: -7px;
        min-height: 100px;
        max-height: 150px;
    }

    .parallax > use {
        animation: move-forever 25s cubic-bezier(.55, .5, .45, .5) infinite;
    }
    .parallax > use:nth-child(1) { animation-delay: -2s; animation-duration: 7s; fill: rgba(255, 255, 255, 0.1); }
    .parallax > use:nth-child(2) { animation-delay: -3s; animation-duration: 10s; fill: rgba(0, 210, 255, 0.1); }
    .parallax > use:nth-child(3) { animation-delay: -4s; animation-duration: 13s; fill: rgba(58, 123, 213, 0.1); }
    .parallax > use:nth-child(4) { animation-delay: -5s; animation-duration: 20s; fill: rgba(0, 210, 255, 0.3); }

    @keyframes move-forever {
        0% { transform: translate3d(-90px, 0, 0); }
        100% { transform: translate3d(85px, 0, 0); }
    }

    /* Glass Login Box */
    .login-box {
        width: 450px;
        background: rgba(255, 255, 255, 0.03);
        backdrop-filter: blur(25px);
        border: 1px solid rgba(255, 255, 255, 0.1);
        border-radius: 35px;
        padding: 50px 40px;
        box-shadow: 0 25px 60px rgba(0,0,0,0.6);
        color: white;
        z-index: 10;
        animation: slideUp 0.8s cubic-bezier(0.2, 0.8, 0.2, 1);
        text-align: center;
    }

    @keyframes slideUp {
        from { opacity: 0; transform: translateY(50px); }
        to { opacity: 1; transform: translateY(0); }
    }

    .logo-area i {
        font-size: 60px;
        background: linear-gradient(135deg, #00d2ff, #3a7bd5);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        margin-bottom: 15px;
        filter: drop-shadow(0 0 10px rgba(0, 210, 255, 0.5));
    }

    .logo-area h2 {
        font-weight: 900;
        letter-spacing: 2px;
        margin-bottom: 5px;
        text-transform: uppercase;
    }

    .logo-area p {
        color: rgba(255,255,255,0.5);
        font-size: 13px;
        text-transform: uppercase;
        letter-spacing: 3px;
        margin-bottom: 35px;
    }

    .form-label {
        font-weight: 700;
        font-size: 12px;
        color: #00d2ff;
        text-transform: uppercase;
        letter-spacing: 1px;
        display: block;
        text-align: left;
        margin-bottom: 8px;
    }

    .input-wrapper {
        position: relative;
        margin-bottom: 25px;
    }

    .form-control {
        background: rgba(255, 255, 255, 0.05) !important;
        border: 1px solid rgba(255, 255, 255, 0.1) !important;
        height: 55px;
        border-radius: 15px;
        color: white !important;
        padding-left: 50px;
        transition: 0.4s;
    }

    .form-control::placeholder {
        color: rgba(255,255,255,0.3);
    }

    .form-control:focus {
        background: rgba(255, 255, 255, 0.1) !important;
        border-color: #00d2ff !important;
        box-shadow: 0 0 20px rgba(0, 210, 255, 0.2);
    }

    .input-icon {
        position: absolute;
        left: 18px;
        top: 50%;
        transform: translateY(-50%);
        color: rgba(255,255,255,0.4);
        font-size: 18px;
        z-index: 5;
    }

    .btn-login {
        height: 55px;
        background: linear-gradient(to right, #00d2ff, #3a7bd5);
        border: none;
        border-radius: 15px;
        font-weight: 800;
        font-size: 16px;
        letter-spacing: 1.5px;
        margin-top: 15px;
        box-shadow: 0 10px 25px rgba(58, 123, 213, 0.4);
        transition: 0.3s;
        text-transform: uppercase;
    }

    .btn-login:hover {
        transform: translateY(-3px);
        box-shadow: 0 15px 35px rgba(58, 123, 213, 0.6);
        filter: brightness(1.1);
    }

    .btn-home {
        background: transparent;
        border: 1px solid rgba(255,255,255,0.15);
        color: rgba(255,255,255,0.7);
        border-radius: 15px;
        height: 48px;
        font-weight: 600;
        margin-top: 20px;
        transition: 0.3s;
        text-decoration: none;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .btn-home:hover {
        background: rgba(255, 255, 255, 0.05);
        color: #fff;
        border-color: #00d2ff;
    }

    .footer-credit {
        position: absolute;
        bottom: 35px;
        width: 100%;
        text-align: center;
        color: rgba(255,255,255,0.2);
        font-size: 12px;
        letter-spacing: 2px;
        text-transform: uppercase;
        font-weight: 700;
    }

    .footer-credit b {
        color: rgba(0, 210, 255, 0.6);
    }
</style>
</head>
<body>

    <div class="bg-container"></div>

    <div class="wave-container">
        <svg class="waves" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
        viewBox="0 24 150 28" preserveAspectRatio="none" shape-rendering="auto">
        <defs>
        <path id="gentle-wave" d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z" />
        </defs>
        <g class="parallax">
        <use xlink:href="#gentle-wave" x="48" y="0" />
        <use xlink:href="#gentle-wave" x="48" y="3" />
        <use xlink:href="#gentle-wave" x="48" y="5" />
        <use xlink:href="#gentle-wave" x="48" y="7" />
        </g>
        </svg>
    </div>

    <div class="login-box shadow">
        <div class="logo-area">
            <i class="fas fa-university"></i>
            <h2>SK MINI BANK</h2>
            <p>Customer Access</p>
        </div>

        <%
        String error=(String)request.getAttribute("error");
        if(error!=null){
        %>
        <div class="alert alert-danger border-0 bg-danger text-white text-center rounded-3 mb-4" style="--bs-bg-opacity: .3; font-size: 14px;">
            <i class="fa fa-exclamation-triangle me-2"></i> <%=error%>
        </div>
        <%
        }
        %>

        <form action="LoginServlet" method="post">
            <div class="text-start mb-1">
                <label class="form-label">Account Number</label>
            </div>
            <div class="input-wrapper">
                <i class="fa fa-user input-icon"></i>
                <input type="text" name="accountNumber" class="form-control" placeholder="Enter Account Number" required>
            </div>

            <div class="text-start mb-1">
                <label class="form-label">Login Password</label>
            </div>
            <div class="input-wrapper">
                <i class="fa fa-lock input-icon"></i>
                <input type="password" name="password" class="form-control" placeholder="••••••••" required>
            </div>

            <button type="submit" class="btn btn-primary w-100 btn-login shadow">
                SECURE LOGIN
            </button>

            <a href="register.jsp" class="btn btn-home w-100">
                <i class="fa fa-user-plus me-2"></i> JOIN NOW
            </a>

            <a href="SKMiniBank.jsp" class="btn btn-home w-100 border-0 text-white-50 small mt-2">
                <i class="fa fa-house-chimney me-2"></i> BACK TO TERMINAL
            </a>
        </form>
    </div>

    <div class="footer-credit">
        © 2026 SK MINI BANK | Developed By <b>Sajid Khan</b>
    </div>

</body>
</html>
