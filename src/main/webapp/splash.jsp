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
        background: #0f0c29;
        overflow: hidden;
        font-family: 'Segoe UI', sans-serif;
    }

    /* Premium RGB Animated Background */
    .bg-animation {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(135deg, #0f0c29, #302b63, #24243e);
        z-index: -1;
    }

    /* Pure CSS RGB Waves (SVG Data URI - No external images) */
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
    .parallax > use:nth-child(1) { animation-delay: -2s; animation-duration: 7s; fill: rgba(255, 255, 255, 0.7); }
    .parallax > use:nth-child(2) { animation-delay: -3s; animation-duration: 10s; fill: rgba(0, 210, 255, 0.5); }
    .parallax > use:nth-child(3) { animation-delay: -4s; animation-duration: 13s; fill: rgba(58, 123, 213, 0.3); }
    .parallax > use:nth-child(4) { animation-delay: -5s; animation-duration: 20s; fill: #3a7bd5; }

    @keyframes move-forever {
        0% { transform: translate3d(-90px, 0, 0); }
        100% { transform: translate3d(85px, 0, 0); }
    }

    /* Content Styling */
    .content {
        text-align: center;
        z-index: 10;
        color: white;
    }

    .logo-box {
        background: rgba(255, 255, 255, 0.05);
        backdrop-filter: blur(15px);
        padding: 40px 60px;
        border-radius: 30px;
        border: 1px solid rgba(255, 255, 255, 0.1);
        box-shadow: 0 20px 50px rgba(0,0,0,0.4);
        margin-bottom: 20px;
        animation: fadeInScale 1s ease-out;
    }

    @keyframes fadeInScale {
        from { opacity: 0; transform: scale(0.9); }
        to { opacity: 1; transform: scale(1); }
    }

    .bank-name {
        font-size: 48px;
        font-weight: 900;
        letter-spacing: 3px;
        margin: 0;
        background: linear-gradient(to right, #00d2ff, #91eaff, #fff);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }

    .tagline {
        font-size: 14px;
        text-transform: uppercase;
        letter-spacing: 6px;
        color: #00d2ff;
        margin-top: 10px;
        font-weight: 600;
    }

    /* Premium Modern Loader */
    .loader-wrap {
        margin-top: 50px;
    }

    .progress-bar {
        width: 300px;
        height: 6px;
        background: rgba(255, 255, 255, 0.05);
        border-radius: 10px;
        margin: 0 auto;
        overflow: hidden;
        border: 1px solid rgba(255,255,255,0.05);
    }

    .progress-fill {
        width: 0%;
        height: 100%;
        background: linear-gradient(90deg, #00d2ff, #3a7bd5, #00d2ff);
        background-size: 200% 100%;
        animation: fill 2s ease-in-out forwards, shimmer 1.5s linear infinite;
    }

    @keyframes fill { 100% { width: 100%; } }
    @keyframes shimmer { 0% { background-position: -200% 0; } 100% { background-position: 200% 0; } }

    .status {
        margin-top: 15px;
        font-size: 13px;
        color: #8892b0;
        letter-spacing: 1px;
        animation: pulse 1.5s infinite;
    }

    /* Footer Credit */
    .footer {
        position: absolute;
        bottom: 40px;
        width: 100%;
        text-align: center;
        color: rgba(255, 255, 255, 0.4);
        font-size: 14px;
        font-weight: 700;
        letter-spacing: 2px;
        z-index: 10;
        text-transform: uppercase;
    }

    .footer b {
        color: #00d2ff;
        text-shadow: 0 0 10px rgba(0, 210, 255, 0.5);
    }

    @keyframes pulse { 0%, 100% { opacity: 0.4; } 50% { opacity: 1; } }
</style>

<script>
    // Redirect after 2 seconds to Admin Login
    setTimeout(function() {
        window.location.href = "<%=request.getContextPath()%>/SKMiniBankadmin-login.jsp";
    }, 2000);
</script>

</head>
<body>

    <div class="bg-animation"></div>

    <!-- SVG RGB Waves (Built-in, No dead links) -->
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

    <div class="content">
        <div class="logo-box">
            <h1 class="bank-name">SK MINI BANK</h1>
            <div class="tagline">Premium Digital Banking</div>
        </div>

        <div class="loader-wrap">
            <div class="progress-bar">
                <div class="progress-fill"></div>
            </div>
            <div class="status">Initializing Secure Terminal...</div>
        </div>
    </div>

    <div class="footer">
        Developed By <b>Sajid Khan</b>
    </div>

</body>
</html>