<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Register | SK Mini Bank Premium</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>
    body {
        margin: 0;
        padding: 0;
        min-height: 100vh;
        background: #0f0c29;
        font-family: 'Segoe UI', sans-serif;
    }

    .bg-container {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(135deg, #0f0c29, #302b63, #24243e);
        z-index: -1;
    }

    /* SVG RGB Waves */
    .wave-container {
        position: fixed;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 10vh;
        z-index: 0;
    }

    .waves {
        width: 100%;
        height: 100%;
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

    .reg-container {
        width: 100%;
        max-width: 800px;
        margin: 60px auto;
        z-index: 10;
        position: relative;
        padding: 0 20px;
    }

    .glass-card {
        background: rgba(255, 255, 255, 0.05);
        backdrop-filter: blur(25px);
        border: 1px solid rgba(255, 255, 255, 0.1);
        border-radius: 40px;
        padding: 50px;
        box-shadow: 0 40px 100px rgba(0,0,0,0.5);
        color: white;
    }

    .section-header {
        color: #00d2ff;
        font-weight: 800;
        font-size: 14px;
        text-transform: uppercase;
        letter-spacing: 2px;
        margin: 40px 0 20px;
        padding-bottom: 10px;
        border-bottom: 1px solid rgba(0, 210, 255, 0.2);
    }

    .form-control, .form-select {
        background: rgba(255, 255, 255, 0.05) !important;
        border: 1px solid rgba(255, 255, 255, 0.1) !important;
        border-radius: 15px;
        color: white !important;
        padding: 15px;
        transition: 0.3s;
    }

    .form-control:focus, .form-select:focus {
        background: rgba(255, 255, 255, 0.1) !important;
        border-color: #00d2ff !important;
        box-shadow: 0 0 20px rgba(0, 210, 255, 0.2);
    }

    .form-label {
        font-weight: 700;
        font-size: 12px;
        color: rgba(255,255,255,0.6);
        margin-bottom: 10px;
        text-transform: uppercase;
    }

    .btn-premium {
        background: linear-gradient(135deg, #00d2ff 0%, #3a7bd5 100%);
        border: none;
        border-radius: 15px;
        padding: 18px;
        font-weight: 800;
        letter-spacing: 1.5px;
        box-shadow: 0 15px 35px rgba(0, 210, 255, 0.3);
        transition: 0.3s;
        margin-top: 40px;
    }

    .btn-premium:hover {
        transform: translateY(-3px);
        box-shadow: 0 20px 50px rgba(0, 210, 255, 0.5);
    }

    .logo-area { text-align: center; margin-bottom: 50px; }
    .logo-area i { font-size: 70px; color: #00d2ff; margin-bottom: 15px; filter: drop-shadow(0 0 15px rgba(0, 210, 255, 0.4)); }
    .logo-area h2 { font-weight: 900; letter-spacing: 2px; }

    .photo-input-wrap {
        width: 130px;
        height: 130px;
        border-radius: 40px;
        background: rgba(255,255,255,0.1);
        border: 2px dashed rgba(0, 210, 255, 0.5);
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        overflow: hidden;
        margin: 0 auto 20px;
    }
    .photo-input-wrap img { width: 100%; height: 100%; object-fit: cover; }
</style>
</head>
<body>

    <div class="bg-container"></div>
    <div class="wave-container">
        <svg class="waves" xmlns="http://www.w3.org/2000/svg" viewBox="0 24 150 28" preserveAspectRatio="none">
            <defs><path id="w" d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z" /></defs>
            <g class="parallax">
                <use xlink:href="#w" x="48" y="0" />
                <use xlink:href="#w" x="48" y="3" />
                <use xlink:href="#w" x="48" y="5" />
                <use xlink:href="#w" x="48" y="7" />
            </g>
        </svg>
    </div>

    <div class="reg-container">
        <div class="glass-card shadow-lg">
            <div class="logo-area">
                <i class="fas fa-university"></i>
                <h2>JOIN SK MINI BANK</h2>
                <p class="text-white-50 small">Experience the Future of Digital Wealth</p>
            </div>

            <%
            String error = request.getParameter("error");
            if(error != null) {
            %>
            <div class="alert alert-danger border-0 bg-danger text-white rounded-4 mb-5 text-center" style="--bs-bg-opacity: .5;">
                <i class="fas fa-triangle-exclamation me-2"></i> <%= java.net.URLDecoder.decode(error, "UTF-8") %>
            </div>
            <% } %>

            <form action="<%=request.getContextPath()%>/RegisterServlet" method="post" enctype="multipart/form-data">

                <div class="text-center">
                    <div class="photo-input-wrap shadow-sm" onclick="document.getElementById('photoInput').click()">
                        <img id="imgPreview" src="${pageContext.request.contextPath}/images/default_user.png">
                    </div>
                    <label class="form-label">Upload Profile Photo *</label>
                    <input type="file" name="photo" id="photoInput" class="d-none" accept="image/*" required onchange="previewImage(this)">
                </div>

                <div class="section-header">Primary Details</div>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Full Name</label>
                        <input type="text" name="fullName" class="form-control" placeholder="John Doe" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Father's Name</label>
                        <input type="text" name="fatherName" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Date of Birth</label>
                        <input type="date" name="dob" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Gender</label>
                        <select name="gender" class="form-select" required>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                </div>

                <div class="section-header">Identity Documents</div>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Aadhaar Card Number</label>
                        <input type="text" name="aadhaar" class="form-control" maxlength="12" placeholder="12 Digit Number" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">PAN Card Number</label>
                        <input type="text" name="pan" class="form-control" maxlength="10" placeholder="ABCDE1234F" style="text-transform:uppercase" required>
                    </div>
                </div>

                <div class="section-header">Contact & Security</div>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Mobile Number</label>
                        <input type="text" name="mobile" class="form-control" maxlength="10" placeholder="10 Digit Number" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Email Address</label>
                        <input type="email" name="email" class="form-control" placeholder="example@bank.com" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Create Login Password</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Set 4-Digit Transaction PIN</label>
                        <input type="password" name="transactionPin" class="form-control" maxlength="4" required>
                    </div>
                </div>

                <div class="section-header">Residential Address</div>
                <div class="row">
                    <div class="col-md-12 mb-4">
                        <label class="form-label">Full Residential Address</label>
                        <input type="text" name="address" class="form-control" required>
                    </div>
                    <div class="col-md-4 mb-4">
                        <label class="form-label">City</label>
                        <input type="text" name="city" class="form-control" required>
                    </div>
                    <div class="col-md-4 mb-4">
                        <label class="form-label">State</label>
                        <input type="text" name="state" class="form-control" required>
                    </div>
                    <div class="col-md-4 mb-4">
                        <label class="form-label">Pincode</label>
                        <input type="text" name="pincode" class="form-control" maxlength="6" required>
                    </div>
                </div>

                <input type="hidden" name="accountType" value="Saving Account">
                <input type="hidden" name="balance" value="5000">

                <button type="submit" class="btn btn-premium w-100 text-white mt-3">
                    ACTIVATE DIGITAL ACCOUNT
                </button>

                <div class="text-center mt-5 opacity-50 small">
                    Already have an account? <a href="login.jsp" class="text-info">Login Securely</a>
                </div>
            </form>
        </div>
    </div>

    <script>
        function previewImage(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('imgPreview').src = e.target.result;
                }
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</body>
</html>