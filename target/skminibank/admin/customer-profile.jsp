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

String status = c.getStatus();
String kycStatus = c.getKycStatus();

if (status == null) status = "ACTIVE";
if (kycStatus == null) kycStatus = "Pending";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SK MINI BANK | Customer Profile</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/dashboard.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">

<style>

body{
    background:#eef3f8;
    font-family:'Segoe UI',sans-serif;
}

.sidebar{
    position:fixed;
    left:0;
    top:0;
    width:250px;
    height:100vh;
    background:#003366;
    color:white;
    padding-top:20px;
    box-shadow:0 0 20px rgba(0,0,0,.25);
}

.sidebar h3{
    text-align:center;
    font-weight:bold;
    margin-bottom:30px;
}

.sidebar a{
    display:block;
    color:white;
    text-decoration:none;
    padding:15px 25px;
    transition:.3s;
}

.sidebar a:hover{
    background:#0d6efd;
    padding-left:35px;
}

.main{
    margin-left:260px;
    padding:30px;
}

.topbar{
    background:white;
    padding:18px;
    border-radius:15px;
    box-shadow:0 4px 15px rgba(0,0,0,.1);
    margin-bottom:25px;
}

.info-card{
    background:white;
    border-radius:18px;
    padding:20px;
    box-shadow:0 5px 18px rgba(0,0,0,.12);
}

.summary{
    border-radius:15px;
    color:white;
    padding:20px;
}

.bg1{
    background:linear-gradient(135deg,#0052D4,#4364F7);
}

.bg2{
    background:linear-gradient(135deg,#11998e,#38ef7d);
}

.bg3{
    background:linear-gradient(135deg,#f7971e,#ffd200);
    color:black;
}

.bg4{
    background:linear-gradient(135deg,#8E2DE2,#4A00E0);
}

.bank-card{
    margin-top:20px;
    border-radius:22px;
    padding:28px;
    color:white;
    background:linear-gradient(135deg,#005bea,#00c6fb);
    box-shadow:0 15px 30px rgba(0,0,0,.25);
}

.action-btn{
    width:100%;
    margin-bottom:10px;
}

</style>

</head>

<body>

<div class="sidebar">

<h3 class="text-white fw-bold">
    <i class="fa-solid fa-building-columns me-2"></i>
    SK MINI BANK
</h3>

<a href="${pageContext.request.contextPath}/admin/dashboard.jsp">
    <i class="fa fa-gauge"></i> Dashboard
</a>

<a href="${pageContext.request.contextPath}/CustomerServlet">
    <i class="fa fa-user"></i> Customer
</a>

<a href="${pageContext.request.contextPath}/admin/deposit.jsp">
    <i class="fa fa-money-bill"></i> Deposit
</a>

<a href="${pageContext.request.contextPath}/admin/withdraw.jsp">
    <i class="fa fa-wallet"></i> Withdraw
</a>

<a href="${pageContext.request.contextPath}/admin/transfer.jsp">
    <i class="fa fa-right-left"></i> Transfer
</a>


    
    <a href="<%= request.getContextPath() %>/KYCServlet?customerId=<%= c.getCustomerId() %>">
    <i class="fa fa-id-card"></i> KYC
</a>

<a href="${pageContext.request.contextPath}/admin/fixed-deposit.jsp">
    <i class="fa fa-building-columns"></i> Fixed Deposit
</a>

<a href="${pageContext.request.contextPath}/TransactionServlet?customerId=${customer.customerId}">
    Transactions
</a>
<a href="${pageContext.request.contextPath}/LogoutServlet">
    <i class="fa fa-right-from-bracket"></i> Logout
</a>
</div>

<div class="main">
    
    <div class="container-fluid">

<div class="row">

<div class="col-lg-14">
    

<div class="topbar d-flex justify-content-between">

<div>

<h3>Customer Banking Profile</h3>

Welcome,
<b><%=c.getFullName()%></b>

</div>

<div>

<span class="badge bg-success p-3">
<%=status%>
</span>

</div>

</div>

               <div class="container-fluid mt-3">

    <div class="row">

        <!-- LEFT SIDE -->
        <div class="col-lg-4">
    
  <div class="card shadow-lg border-0 rounded-4 mb-4">

    <div class="card-header bg-success text-white py-3">

        <h5 class="mb-0">
            <i class="fa-solid fa-wallet"></i>
            Account Summary
        </h5>

    </div>

    <div class="card-body">

        <table class="table table-borderless">

            <tr>
                <th>Account Number</th>
                <td><%=c.getAccountNumber()%></td>
            </tr>

            <tr>
                <th>IFSC Code</th>
                <td><%=c.getIfscCode()%></td>
            </tr>

            <tr>
                <th>Account Type</th>
                <td><%=c.getAccountType()%></td>
            </tr>

            <tr>
                <th>Branch</th>
                <td><%=c.getBranch()%></td>
            </tr>

            <tr>
                <th>Current Balance</th>
                <td class="fw-bold text-success">
                    &#8377; <%=c.getBalance()%>
                </td>
            </tr>

            <tr>
                <th>KYC Status</th>
                <td>
                    <span class="badge bg-success">
                        <%=kycStatus%>
                    </span>
                </td>
            </tr>

            <tr>
                <th>Account Status</th>
                <td>
                    <span class="badge bg-primary">
                        <%=status%>
                    </span>
                </td>
            </tr>

        </table>

        <hr>

        <h6 class="mb-3">
            <i class="fa-solid fa-gears"></i>
            Account Controls
        </h6>

        <div class="d-grid gap-2">

            <a href="${pageContext.request.contextPath}/BlockAccountServlet?customerId=<%=c.getCustomerId()%>"
               class="btn btn-danger">
                <i class="fa fa-ban"></i> Block Account
            </a>

            <a href="${pageContext.request.contextPath}/FreezeAccountServlet?customerId=<%=c.getCustomerId()%>"
               class="btn btn-warning">
                <i class="fa fa-lock"></i> Freeze Account
            </a>

            <a href="${pageContext.request.contextPath}/UnblockAccountServlet?customerId=<%=c.getCustomerId()%>"
               class="btn btn-success">
                <i class="fa fa-unlock"></i> Unblock Account
            </a>

            <a href="${pageContext.request.contextPath}/UnfreezeAccountServlet?customerId=<%=c.getCustomerId()%>"
               class="btn btn-primary">
                <i class="fa fa-lock-open"></i> Unfreeze Account
            </a>

        </div>

    </div>

</div>  
    
</div>
            <!-- Account Summary -->
            

        <!-- RIGHT SIDE -->
        <div class="col-lg-8">

<div class="row">            

                <!-- Personal Details -->
                <div class="col-md-6">

                    <div class="card shadow mb-4">

                        <div class="card-header bg-primary text-white">
                            <i class="fa fa-user"></i> Personal Details
                        </div>

                        <div class="card-body">

                            <table class="table table-borderless">

                                <tr>
                                    <th>Name</th>
                                    <td><%=c.getFullName()%></td>
                                </tr>

                                <tr>
                                    <th>Father</th>
                                    <td><%=c.getFatherName()%></td>
                                </tr>

                                <tr>
                                    <th>Mobile</th>
                                    <td><%=c.getMobile()%></td>
                                </tr>

                                <tr>
                                    <th>Email</th>
                                    <td><%=c.getEmail()%></td>
                                </tr>

                                <tr>
                                    <th>DOB</th>
                                    <td><%=c.getDob()%></td>
                                </tr>

                                <tr>
                                    <th>Gender</th>
                                    <td><%=c.getGender()%></td>
                                </tr>

                            </table>

                        </div>

                    </div>

                </div>
                               
                                    
                                    <!-- Virtual Debit Card -->
    <div class="col-lg-4">

    <div class="card shadow">

        <div class="card-header bg-primary text-white">
            <i class="fa fa-credit-card"></i> Virtual Debit Card
        </div>

        <div class="card-body text-center">

        <%
        if(card != null){
        %>

            <div class="bank-card">

                <div class="chip"></div>

                <div class="contactless">
                    <i class="fa fa-wifi"></i>
                </div>

                <div class="bank-name">
                    SK MINI BANK
                </div>

                <div class="card-number">
                    <%= card.getCardNumber() %>
                </div>

                <div class="card-footer">

                    <div>
                        <small>VALID THRU</small><br>
                        <b><%= card.getExpiryDate() %></b>
                    </div>

                    <div>
                        <small>CVV</small><br>
                        <b><%= card.getCvv() %></b>
                    </div>

                </div>

                <div class="holder">
                    <%= card.getCustomerName() %>
                </div>

                <div class="visa">
                    VISA
                </div>

            </div>

        <%
        }else{
        %>

            <div class="alert alert-warning">
                Debit Card Not Available
            </div>

        <%
        }
        %>

        </div>

    </div>

</div>
        <c:if test="${fd != null}">
<div class="card shadow">
    <div class="card-header bg-success text-white">
        Fixed Deposit
    </div>

    <div class="card-body">
        <p><b>FD ID :</b> ${fd.fdId}</p>
<p><b>Account Number :</b> ${fd.accountNumber}</p>
<p><b>Customer Name :</b> ${fd.customerName}</p>
<p><b>FD Amount :</b> ?${fd.fdAmount}</p>
<p><b>Interest Rate :</b> ${fd.interestRate}%</p>
<p><b>Duration :</b> ${fd.durationYear} Year</p>
<p><b>Maturity Amount :</b> ?${fd.maturityAmount}</p>
<p><b>Open Date :</b> ${fd.openDate}</p>
<p><b>Maturity Date :</b> ${fd.maturityDate}</p>
<p><b>Status :</b> ${fd.status}</p>
    </div>
</div>
</c:if>