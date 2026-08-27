<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Open New Account | SK Mini Bank</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>
    body {
        background-color: #f0f4f8;
        font-family: 'Segoe UI', sans-serif;
        color: #102a43;
    }

    .premium-header {
        background: linear-gradient(135deg, #102a43 0%, #243b53 100%);
        color: white;
        padding: 40px 20px;
        border-radius: 0 0 40px 40px;
        box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        margin-bottom: -50px;
        text-align: center;
    }

    .form-container {
        max-width: 1000px;
        margin: 0 auto 50px;
        background: white;
        border-radius: 30px;
        padding: 50px;
        box-shadow: 0 20px 50px rgba(0,0,0,0.05);
        position: relative;
        z-index: 10;
    }

    .section-title {
        font-weight: 800;
        font-size: 14px;
        text-transform: uppercase;
        letter-spacing: 2px;
        color: #48bbff;
        margin: 40px 0 20px;
        display: flex;
        align-items: center;
    }

    .section-title::after {
        content: "";
        flex: 1;
        height: 1px;
        background: #d9e2ec;
        margin-left: 15px;
    }

    .form-control, .form-select {
        background: #f8fafc;
        border: 1px solid #d9e2ec;
        border-radius: 12px;
        padding: 12px 15px;
        transition: 0.3s;
    }

    .form-control:focus, .form-select:focus {
        background: white;
        border-color: #48bbff;
        box-shadow: 0 0 15px rgba(72, 187, 255, 0.1);
    }

    .form-label {
        font-weight: 700;
        font-size: 13px;
        color: #334e68;
        margin-bottom: 8px;
    }

    .photo-upload {
        width: 150px;
        height: 150px;
        border-radius: 50%;
        border: 4px solid #fff;
        box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        background: #f0f4f8;
        display: flex;
        align-items: center;
        justify-content: center;
        overflow: hidden;
        cursor: pointer;
        position: relative;
        margin: 0 auto 20px;
        transition: 0.3s;
    }

    .photo-upload:hover { transform: scale(1.05); }

    .photo-upload img { width: 100%; height: 100%; object-fit: cover; }

    .photo-overlay {
        position: absolute;
        bottom: 0;
        width: 100%;
        background: rgba(0,0,0,0.5);
        color: white;
        font-size: 10px;
        padding: 5px 0;
        text-align: center;
    }

    .btn-create {
        background: linear-gradient(135deg, #38ef7d 0%, #11998e 100%);
        border: none;
        border-radius: 15px;
        padding: 18px;
        font-weight: 800;
        color: white;
        letter-spacing: 1px;
        box-shadow: 0 10px 25px rgba(17, 153, 142, 0.3);
        margin-top: 30px;
    }

    .btn-create:hover {
        transform: translateY(-3px);
        box-shadow: 0 15px 35px rgba(17, 153, 142, 0.5);
    }
</style>
</head>
<body>

    <div class="premium-header">
        <h1 class="fw-bold"><i class="fas fa-user-plus me-3"></i>Open Digital Account</h1>
        <p class="opacity-75">Register a new customer for SK Mini Bank Digital Services</p>
    </div>

    <div class="container">
        <div class="form-container">

            <%
            String error = request.getParameter("error");
            if(error != null) {
            %>
            <div class="alert alert-danger border-0 bg-danger text-white rounded-4 p-3 mb-4 d-flex align-items-center" style="--bs-bg-opacity: .8;">
                <i class="fas fa-circle-exclamation fa-2x me-3"></i>
                <div>
                    <h6 class="mb-0 fw-bold">Registration Halted</h6>
                    <small><%= java.net.URLDecoder.decode(error, "UTF-8") %></small>
                </div>
            </div>
            <% } %>

            <form action="<%=request.getContextPath()%>/RegisterServlet" method="post" enctype="multipart/form-data">

                <div class="text-center">
                    <label class="form-label d-block mb-3">Customer Profile Photo</label>
                    <div class="photo-upload" onclick="document.getElementById('photoInput').click()">
                        <img id="imgPreview" src="${pageContext.request.contextPath}/images/default_user.png">
                        <div class="photo-overlay">CHANGE</div>
                    </div>
                    <input type="file" name="photo" id="photoInput" class="d-none" accept="image/*" onchange="previewImage(this)">
                    <small class="text-muted">Click image above to upload</small>
                </div>

                <div class="section-title">Personal Credentials</div>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Full Name (Legal)</label>
                        <input type="text" name="fullName" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Father's Name</label>
                        <input type="text" name="fatherName" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Mother's Name</label>
                        <input type="text" name="motherName" class="form-control" required>
                    </div>
                    <div class="col-md-3 mb-4">
                        <label class="form-label">Date of Birth</label>
                        <input type="date" name="dob" class="form-control" required>
                    </div>
                    <div class="col-md-3 mb-4">
                        <label class="form-label">Gender</label>
                        <select name="gender" class="form-select" required>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Marital Status</label>
                        <select name="maritalStatus" class="form-select" required>
                            <option value="Single">Single</option>
                            <option value="Married">Married</option>
                            <option value="Divorced">Divorced</option>
                            <option value="Widowed">Widowed</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Occupation</label>
                        <input type="text" name="occupation" class="form-control" value="Private Job" required>
                    </div>
                </div>

                <div class="section-title">Identity & Residency</div>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Aadhaar Card Number</label>
                        <input type="text" name="aadhaar" class="form-control" maxlength="12" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">PAN Card Number</label>
                        <input type="text" name="pan" class="form-control" maxlength="10" style="text-transform:uppercase" required>
                    </div>
                    <div class="col-md-12 mb-4">
                        <label class="form-label">Current Residential Address</label>
                        <input type="text" name="address" class="form-control" required>
                    </div>
                    <div class="col-md-4 mb-4">
                        <label class="form-label">City / Town</label>
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

                <div class="section-title">Bank Assigned Details (Auto-Generated)</div>
                <div class="row">
                    <div class="col-md-3 mb-4">
                        <label class="form-label">Customer Code</label>
                        <input type="text" id="autoCustCode" name="customerCode" class="form-control bg-light" readonly>
                    </div>
                    <div class="col-md-3 mb-4">
                        <label class="form-label">CIF Number</label>
                        <input type="text" id="autoCif" name="cifNumber" class="form-control bg-light" readonly>
                    </div>
                    <div class="col-md-3 mb-4">
                        <label class="form-label">Account Number</label>
                        <input type="text" id="autoAccNo" name="accountNumber" class="form-control bg-light" readonly>
                    </div>
                    <div class="col-md-3 mb-4">
                        <label class="form-label">IFSC Code</label>
                        <input type="text" name="ifscCode" class="form-control bg-light" value="SKMB0001001" readonly>
                    </div>
                </div>

                <div class="section-title">Nominee Details</div>
                <div class="row">
                    <div class="col-md-4 mb-4">
                        <label class="form-label">Nominee Name</label>
                        <input type="text" name="nomineeName" class="form-control" required>
                    </div>
                    <div class="col-md-4 mb-4">
                        <label class="form-label">Relationship</label>
                        <select name="relationship" class="form-select" required>
                            <option value="Father">Father</option>
                            <option value="Mother">Mother</option>
                            <option value="Brother">Brother</option>
                            <option value="Sister">Sister</option>
                            <option value="Wife">Wife</option>
                            <option value="Husband">Husband</option>
                            <option value="Son">Son</option>
                            <option value="Daughter">Daughter</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                    <div class="col-md-4 mb-4">
                        <label class="form-label">Nominee Mobile</label>
                        <input type="text" name="nomineeMobile" class="form-control" maxlength="10" required>
                    </div>
                </div>

                <div class="section-title">Communication & Wealth</div>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Primary Mobile Number</label>
                        <input type="text" name="mobile" class="form-control" maxlength="10" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Alternate Mobile (Optional)</label>
                        <input type="text" name="alternateMobile" class="form-control" maxlength="10">
                    </div>
                    <div class="col-md-12 mb-4">
                        <label class="form-label">Email Address</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Account Type</label>
                        <select name="accountType" class="form-select" required>
                            <option>Savings Account</option>
                            <option>Current Account</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Opening Deposit (₹)</label>
                        <input type="number" name="balance" class="form-control" value="1000" min="500" required>
                    </div>
                </div>

                <div class="section-title">Security Layers</div>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Set Login Password</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label class="form-label">Set 4-Digit Transaction PIN</label>
                        <input type="password" name="transactionPin" class="form-control" maxlength="4" required>
                    </div>
                </div>

                <button type="submit" class="btn btn-create w-100 fw-bold">
                    EXECUTE ACCOUNT PROVISIONING
                </button>

                <div class="text-center mt-4">
                    <a href="${pageContext.request.contextPath}/DashboardServlet" class="btn btn-link text-muted text-decoration-none">
                        <i class="fa fa-arrow-left me-2"></i> ABORT AND EXIT
                    </a>
                </div>
            </form>
        </div>
    </div>

    <script>
        // Auto-generate details on load
        window.onload = function() {
            let ts = new Date().getTime();
            document.getElementById('autoCustCode').value = "SKC" + ts;
            document.getElementById('autoCif').value = "CIF" + ts;
            document.getElementById('autoAccNo').value = "SKM" + ts;
        };

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
