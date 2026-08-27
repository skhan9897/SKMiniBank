<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SK Mini Bank | Initialize</title>

<style>
    body {
        margin: 0;
        padding: 0;
        width: 100%;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        background: #000;
        overflow: hidden;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    /* Premium RGB Animated Background */
    .bg-animation {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(45deg, #0f0c29, #302b63, #24243e);
        z-index: -1;
    }

    /* CSS Waves with RGB effect */
    .wave {
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 100px;
        background: url('https://i.imgur.com/G5qE7U0.png');
        background-size: 1000px 100px;
    }

    .wave1 {
        animation: animate 30s linear infinite;
        z-index: 1000;
        opacity: 1;
        bottom: 0;
        filter: hue-rotate(0deg);
    }

    .wave2 {
        animation: animate2 15s linear infinite;
        z-index: 999;
        opacity: 0.5;
        bottom: 10px;
        filter: hue-rotate(90deg);
    }

    .wave3 {
        animation: animate 10s linear infinite;
        z-index: 998;
        opacity: 0.2;
        bottom: 15px;
        filter: hue-rotate(180deg);
    }

    .wave4 {
        animation: animate2 5s linear infinite;
        z-index: 997;
        opacity: 0.7;
        bottom: 20px;
        filter: hue-rotate(270deg);
    }

    @keyframes animate {
        0% { background-position-x: 0; }
        100% { background-position-x: 1000px; }
    }

    @keyframes animate2 {
        0% { background-position-x: 0; }
        100% { background-position-x: -1000px; }
    }

    /* Content Styling */
    .content {
        text-align: center;
        z-index: 10;
        color: white;
    }

    .logo-box {
        background: rgba(255, 255, 255, 0.1);
        backdrop-filter: blur(10px);
        padding: 30px;
        border-radius: 25px;
        border: 1px solid rgba(255, 255, 255, 0.2);
        box-shadow: 0 15px 35px rgba(0,0,0,0.5);
        margin-bottom: 20px;
        animation: fadeInDown 1s ease-out;
    }

    .bank-name {
        font-size: 42px;
        font-weight: 800;
        letter-spacing: 2px;
        margin: 0;
        background: linear-gradient(to right, #fff, #8e9eab);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }

    .tagline {
        font-size: 14px;
        text-transform: uppercase;
        letter-spacing: 5px;
        color: #00d2ff;
        margin-top: 5px;
    }

    /* Modern Loader */
    .loader-container {
        width: 250px;
        height: 4px;
        background: rgba(255,255,255,0.1);
        border-radius: 10px;
        margin: 40px auto 20px;
        overflow: hidden;
        position: relative;
    }

    .loader-bar {
        width: 0%;
        height: 100%;
        background: linear-gradient(to right, #00d2ff, #3a7bd5, #00d2ff);
        background-size: 200% 100%;
        animation: loading 2s ease-in-out forwards, shimmer 1s linear infinite;
    }

    @keyframes loading {
        0% { width: 0%; }
        100% { width: 100%; }
    }

    @keyframes shimmer {
        0% { background-position: -200% 0; }
        100% { background-position: 200% 0; }
    }

    .status-text {
        font-size: 12px;
        color: #aaa;
        font-style: italic;
        animation: pulse 1.5s infinite;
    }

    /* Developer Credit */
    .footer {
        position: absolute;
        bottom: 50px;
        width: 100%;
        text-align: center;
        color: rgba(255,255,255,0.5);
        font-size: 14px;
        font-weight: 600;
        letter-spacing: 1px;
    }

    .footer span {
        color: #ff6b6b;
        animation: colorCycle 4s linear infinite;
    }

    @keyframes colorCycle {
        0% { color: #ff6b6b; }
        33% { color: #51cf66; }
        66% { color: #339af0; }
        100% { color: #ff6b6b; }
    }

    @keyframes fadeInDown {
        from { opacity: 0; transform: translateY(-30px); }
        to { opacity: 1; transform: translateY(0); }
    }

    @keyframes pulse {
        0%, 100% { opacity: 0.5; }
        50% { opacity: 1; }
    }
</style>

<script>
    // Redirect after 2 seconds
    setTimeout(function() {
        window.location.href = "<%=request.getContextPath()%>/SKMiniBankadmin-login.jsp";
    }, 2000);
</script>

</head>
<body>

    <div class="bg-animation"></div>

    <!-- RGB Waves -->
    <div class="wave wave1"></div>
    <div class="wave wave2"></div>
    <div class="wave wave3"></div>
    <div class="wave wave4"></div>

    <div class="content">
        <div class="logo-box">
            <h1 class="bank-name">SK MINI BANK</h1>
            <div class="tagline">The Digital Era</div>
        </div>

        <div class="loader-container">
            <div class="loader-bar"></div>
        </div>
        <div class="status-text">Connecting to Secure Server...</div>
    </div>

    <div class="footer">
        DEVELOPED BY <span>SAJID KHAN</span>
    </div>

</body>
</html>