<%@page import="java.util.List"%>
<%@page import="com.bank.dao.CustomerDAO"%>
<%@page import="com.bank.dao.FixedDepositDAO"%>
<%@page import="com.bank.dao.DebitCardDAO"%>
<%@page import="com.bank.model.Customer"%>
<%@page import="com.bank.model.FixedDeposit"%>
<%@page import="com.bank.model.DebitCard"%>
<%
String idParam = request.getParameter("customerId");
if (idParam == null || idParam.trim().isEmpty()) {
    out.println("Customer ID Missing");
    return;
}
int id = Integer.parseInt(idParam);
CustomerDAO dao = new CustomerDAO();
Customer c = dao.getCustomerById(id);
if (c == null) {
    out.println("Customer Not Found");
    return;
}
FixedDepositDAO fdDao = new FixedDepositDAO();
FixedDeposit fd = fdDao.getFDByCustomerId(c.getCustomerId());
DebitCardDAO cardDAO = new DebitCardDAO();
DebitCard card = cardDAO.getCardByCustomerId(c.getCustomerId());
String status = c.getStatus() != null ? c.getStatus() : "ACTIVE";
String kycStatus = c.getKycStatus() != null ? c.getKycStatus() : "Pending";
String role = (String) session.getAttribute("role");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= c.getFullName() %> | Premium Profile</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
<style>
    :root { --primary: #003366; --accent: #00d2ff; --glass: rgba(255, 255, 255, 0.9); }
    body { background: #f0f2f5; font-family: 'Inter', sans-serif; margin: 0; }
    
    /* Sidebar */
    .sidebar { width: 260px; height: 100vh; background: var(--primary); position: fixed; color: white; padding: 20px; transition: 0.3s; }
    .sidebar h3 { font-size: 1.2rem; font-weight: 800; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 20px; margin-bottom: 20px; }
    .sidebar a { display: flex; align-items: center; padding: 12px 15px; color: rgba(255,255,255,0.7); text-decoration: none; border-radius: 10px; margin-bottom: 5px; font-size: 0.9rem; }
    .sidebar a:hover { background: rgba(255,255,255,0.1); color: white; }
    .sidebar i { width: 25px; margin-right: 10px; }

    /* Main Content */
    .main-content { margin-left: 260px; padding: 40px; }
    
    /* Profile Header Card */
    .profile-header { background: linear-gradient(135deg, #003366, #00509d); border-radius: 24px; padding: 40px; color: white; position: relative; overflow: hidden; margin-bottom: 30px; box-shadow: 0 10px 30px rgba(0,51,102,0.2); }
    .profile-header::after { content: ''; position: absolute; top: -50px; right: -50px; width: 200px; height: 200px; background: rgba(255,255,255,0.05); border-radius: 50%; }

    .avatar-wrapper { position: relative; width: 140px; height: 140px; }
    .avatar-img { width: 100%; height: 100%; border-radius: 35px; object-fit: cover; border: 4px solid rgba(255,255,255,0.2); }
    .status-dot { position: absolute; bottom: 5px; right: 5px; width: 20px; height: 20px; background: #2ecc71; border: 4px solid #003366; border-radius: 50%; }
    
    .premium-card { background: white; border: none; border-radius: 20px; box-shadow: 0 5px 20px rgba(0,0,0,0.05); transition: 0.3s; }
    .premium-card:hover { transform: translateY(-5px); }
    .card-label { font-size: 0.75rem; font-weight: 700; color: #95a5a6; text-transform: uppercase; letter-spacing: 1px; }
    .card-value { font-size: 1.1rem; font-weight: 600; color: #2c3e50; }
    
    /* Bank Card */
    .bank-card { width: 350px; height: 220px; background: linear-gradient(135deg, #2c3e50, #000000); border-radius: 20px; padding: 25px; color: white; position: relative; box-shadow: 0 15px 35px rgba(0,0,0,0.3); }
    .card-chip { width: 50px; height: 40px; background: linear-gradient(135deg, #f1c40f, #f39c12); border-radius: 8px; margin-bottom: 20px; }
    .card-no { font-size: 1.4rem; letter-spacing: 3px; font-family: 'Courier New', monospace; margin-bottom: 20px; }
    .card-holder { text-transform: uppercase; font-size: 0.8rem; letter-spacing: 1px; opacity: 0.8; }
    
    .btn-action { border-radius: 12px; padding: 10px 20px; font-weight: 600; transition: 0.2s; }
    .badge-premium { padding: 8px 16px; border-radius: 30px; font-weight: 700; font-size: 0.7rem; letter-spacing: 1px; }
</style>
</head>
<body>

<div class="sidebar">
    <h3>🏦 SK MINI BANK</h3>
    <a href="<%=request.getContextPath()%>/DashboardServlet"><i class="fa fa-th-large"></i> Dashboard</a>
    <a href="<%=request.getContextPath()%>/admin/customer-list.jsp"><i class="fa fa-users"></i> Customers</a>
    <a href="<%=request.getContextPath()%>/TransactionServlet?accountNumber=<%=c.getAccountNumber()%>"><i class="fa fa-exchange-alt"></i> Transactions</a>
    <a href="<%=request.getContextPath()%>/AdminLoanRequestServlet"><i class="fa fa-hand-holding-usd"></i> Loan Apps</a>
    <a href="<%=request.getContextPath()%>/AdminAllRequestServlet"><i class="fa fa-tasks"></i> Service Requests</a>
    <hr style="opacity: 0.1">
    <a href="<%=request.getContextPath()%>/admin/withdraw.jsp"><i class="fa fa-minus-circle"></i> Cash Withdraw</a>
    <a href="<%=request.getContextPath()%>/admin/deposit.jsp"><i class="fa fa-plus-circle"></i> Cash Deposit</a>
    <a href="<%=request.getContextPath()%>/LogoutServlet" style="color: #ff7675; margin-top: 20px;"><i class="fa fa-power-off"></i> Sign Out</a>
</div>

<div class="main-content">

    <!-- Profile Header -->
    <div class="profile-header d-flex align-items-center">
        <div class="avatar-wrapper me-4">
            <img src="<%=request.getContextPath()%>/uploads/customer_photos/<%= c.getPhoto() != null ? c.getPhoto() : "default_user.png" %>"
                 onerror="this.src='<%=request.getContextPath()%>/images/default_user.png'" class="avatar-img shadow">
            <div class="status-dot"></div>
        </div>
        <div class="flex-grow-1">
            <div class="d-flex justify-content-between align-items-start">
                <div>
                    <h1 class="fw-bold mb-1"><%= c.getFullName() %></h1>
                    <p class="mb-0 opacity-75"><i class="fa fa-map-marker-alt me-2"></i> <%= c.getCity() %>, <%= c.getState() %></p>
                </div>
                <div class="text-end">
                    <span class="badge bg-light text-primary badge-premium mb-2"><%= status.toUpperCase() %> ACCOUNT</span><br>
                    <span class="badge bg-warning text-dark badge-premium">KYC: <%= kycStatus.toUpperCase() %></span>
                </div>
            </div>
            <div class="mt-4 d-flex gap-3">
                <% if ("ADMIN".equals(role)) { %>
                    <a href="<%=request.getContextPath()%>/BlockAccountServlet?customerId=<%=c.getCustomerId()%>" class="btn btn-danger btn-action btn-sm"><i class="fa fa-ban me-1"></i> Block</a>
                    <a href="<%=request.getContextPath()%>/FreezeAccountServlet?customerId=<%=c.getCustomerId()%>" class="btn btn-warning btn-action btn-sm text-dark"><i class="fa fa-lock me-1"></i> Freeze</a>
                    <a href="<%=request.getContextPath()%>/UnblockAccountServlet?customerId=<%=c.getCustomerId()%>" class="btn btn-success btn-action btn-sm"><i class="fa fa-unlock me-1"></i> Unblock</a>
                    <a href="<%=request.getContextPath()%>/UnfreezeAccountServlet?customerId=<%=c.getCustomerId()%>" class="btn btn-light btn-action btn-sm"><i class="fa fa-lock-open me-1"></i> Unfreeze</a>
                <% } %>
            </div>
        </div>
    </div>

    <div class="row">
        <!-- Financial Overview -->
        <div class="col-lg-8">
            <div class="row g-4 mb-4">
                <div class="col-md-6">
                    <div class="card premium-card p-4">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <span class="card-label">Available Balance</span>
                            <i class="fa fa-wallet text-success fs-4"></i>
                        </div>
                        <h2 class="fw-bold text-dark">&#8377; <%= String.format("%,.2f", c.getBalance()) %></h2>
                        <p class="small text-muted mb-0">Total liquid assets in savings</p>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card premium-card p-4">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <span class="card-label">Fixed Deposits</span>
                            <i class="fa fa-piggy-bank text-primary fs-4"></i>
                        </div>
                        <h2 class="fw-bold text-dark">&#8377; <%= fd != null ? String.format("%,.2f", fd.getFdAmount()) : "0.00" %></h2>
                        <p class="small text-muted mb-0"><%= fd != null ? "Maturing on " + fd.getMaturityDate() : "No active FDs found" %></p>
                    </div>
                </div>
            </div>

            <div class="card premium-card p-4 mb-4">
                <h5 class="fw-bold mb-4">Identity & Banking Details</h5>
                <div class="row g-4">
                    <div class="col-md-4">
                        <div class="card-label">Account Number</div>
                        <div class="card-value"><%= c.getAccountNumber() %></div>
                    </div>
                    <div class="col-md-4">
                        <div class="card-label">IFSC Code</div>
                        <div class="card-value"><%= c.getIfscCode() %></div>
                    </div>
                    <div class="col-md-4">
                        <div class="card-label">Customer ID</div>
                        <div class="card-value">#<%= c.getCustomerId() %></div>
                    </div>
                    <div class="col-md-4">
                        <div class="card-label">Aadhaar Number</div>
                        <div class="card-value"><%= c.getAadhaar() %></div>
                    </div>
                    <div class="col-md-4">
                        <div class="card-label">PAN Number</div>
                        <div class="card-value"><%= c.getPan() %></div>
                    </div>
                    <div class="col-md-4">
                        <div class="card-label">Account Type</div>
                        <div class="card-value text-primary"><%= c.getAccountType() %></div>
                    </div>
                    <div class="col-12">
                        <div class="card-label">Email Address</div>
                        <div class="card-value"><%= c.getEmail() %></div>
                    </div>
                    <div class="col-md-6">
                        <div class="card-label">Mobile Number</div>
                        <div class="card-value">+91 <%= c.getMobile() %></div>
                    </div>
                    <div class="col-md-6">
                        <div class="card-label">Father's Name</div>
                        <div class="card-value"><%= c.getFatherName() %></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Right Side: Virtual Card & Actions -->
        <div class="col-lg-4">
            <div class="mb-4">
                <h5 class="fw-bold mb-3">Active Debit Card</h5>
                <% if(card != null) { %>
                <div class="bank-card shadow-lg">
                    <div class="d-flex justify-content-between">
                        <div class="card-chip"></div>
                        <i class="fa fa-wifi fs-4 opacity-50"></i>
                    </div>
                    <div class="card-no"><%= card.getCardNumber().replaceAll(".{4}", "$0 ") %></div>
                    <div class="d-flex justify-content-between align-items-end">
                        <div>
                            <div class="card-holder"><%= card.getCustomerName() %></div>
                            <div class="small opacity-50">EXP: <%= card.getExpiryDate() %></div>
                        </div>
                        <h4 class="mb-0">VISA</h4>
                    </div>
                </div>
                <% } else { %>
                    <div class="alert alert-light border p-4 rounded-4 text-center">
                        <i class="fa fa-credit-card fs-1 opacity-20 mb-2"></i><br>
                        No active card found
                    </div>
                <% } %>
            </div>

            <div class="card premium-card p-4">
                <h5 class="fw-bold mb-3">Quick Actions</h5>
                <button class="btn btn-outline-primary btn-action w-100 mb-2" data-bs-toggle="modal" data-bs-target="#photoModal">
                    <i class="fa fa-camera me-2"></i> Update Photo
                </button>
                <a href="<%=request.getContextPath()%>/admin/edit-customer.jsp?id=<%=c.getCustomerId()%>" class="btn btn-outline-dark btn-action w-100 mb-2">
                    <i class="fa fa-edit me-2"></i> Edit Profile
                </a>
                <a href="<%=request.getContextPath()%>/TransactionPDFServlet?accountNumber=<%=c.getAccountNumber()%>" class="btn btn-outline-danger btn-action w-100">
                    <i class="fa fa-file-pdf me-2"></i> Statement PDF
                </a>
            </div>
        </div>
    </div>

    <!-- Photo Modal -->
    <div class="modal fade" id="photoModal" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 rounded-4">
          <div class="modal-header border-0 pb-0">
            <h5 class="modal-title fw-bold">Update Photo</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <form id="photoUploadForm">
              <div class="modal-body text-center p-4">
                  <input type="hidden" name="customerId" value="<%= c.getCustomerId() %>">
                  <div class="mb-4">
                      <img id="modalPreview" src="<%=request.getContextPath()%>/uploads/customer_photos/<%= c.getPhoto() != null ? c.getPhoto() : "default_user.png" %>"
                           onerror="this.src='<%=request.getContextPath()%>/images/default_user.png'"
                           style="width: 180px; height: 180px; object-fit: cover;" class="rounded-4 border shadow-sm">
                  </div>
                  <div id="uploadStatus" class="small mb-2" style="display:none;"></div>
                  <input type="file" name="photo" id="photoInput" class="form-control" accept="image/*" required onchange="previewImage(this)">
              </div>
              <div class="modal-footer border-0 pt-0">
                <button type="submit" id="uploadBtn" class="btn btn-primary btn-action w-100">Upload New Photo</button>
              </div>
          </form>
        </div>
      </div>
    </div>

    <footer class="mt-5 pt-4 text-muted border-top text-center">
        &copy; 2026 SK Mini Bank Management System | v2.0 Premium Interface
    </footer>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
function previewImage(input) {
    if (input.files && input.files[0]) {
        document.getElementById('modalPreview').src = window.URL.createObjectURL(input.files[0]);
    }
}

document.getElementById('photoUploadForm').onsubmit = function(e) {
    e.preventDefault();

    let btn = document.getElementById('uploadBtn');
    let status = document.getElementById('uploadStatus');
    let formData = new FormData(this);

    btn.disabled = true;
    btn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Uploading...';
    status.style.display = 'block';
    status.className = 'small mb-2 text-primary';
    status.innerHTML = 'Processing your image...';

    fetch('<%=request.getContextPath()%>/UpdateCustomerPhotoServlet', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if(response.redirected) {
            window.location.href = response.url;
            return;
        }
        return response.text();
    })
    .then(data => {
        // If not redirected, we reload to show success
        window.location.reload();
    })
    .catch(error => {
        console.error('Error:', error);
        btn.disabled = false;
        btn.innerHTML = 'Upload New Photo';
        status.className = 'small mb-2 text-danger';
        status.innerHTML = 'Upload failed. Try again.';
    });
};
</script>
</body>
</html>
