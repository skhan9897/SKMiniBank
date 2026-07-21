<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bank.model.KYCRequest"%>

<%
KYCRequest kyc = (KYCRequest) request.getAttribute("kyc");

if (kyc == null) {
%>
<h3>No KYC Record Found</h3>
<%
    return;
}
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>KYC Details</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container mt-4">

<div class="card shadow">

<div class="card-header bg-primary text-white">

<h3>KYC Verification</h3>

</div>

<div class="card-body">

<div class="row">

<div class="col-md-6">

<table class="table table-bordered">

<tr>
<th>KYC ID</th>
<td><%=kyc.getKycId()%></td>
</tr>

<tr>
<th>Customer ID</th>
<td><%=kyc.getCustomerId()%></td>
</tr>

<tr>
<th>Account Number</th>
<td><%=kyc.getAccountNumber()%></td>
</tr>

<tr>
<th>Aadhaar Number</th>
<td><%=kyc.getAadhaarNumber()%></td>
</tr>

<tr>
<th>PAN Number</th>
<td><%=kyc.getPanNumber()%></td>
</tr>

<tr>
<th>Status</th>
<td><%=kyc.getStatus()%></td>
</tr>

<tr>
<th>Request Date</th>
<td><%=kyc.getRequestDate()%></td>
</tr>

</table>

</div>

<div class="col-md-6">

<label><b>Customer Photo</b></label><br>

<img src="<%=kyc.getCustomerPhoto()%>"
width="180"
height="180"
class="img-thumbnail">

<br><br>

<label><b>Signature</b></label><br>

<img src="<%=kyc.getSignatureImage()%>"
width="180"
height="80"
class="img-thumbnail">

</div>

</div>

<hr>

<div class="row">

<div class="col-md-6">

<h5>Aadhaar Front</h5>

<img src="<%=kyc.getAadhaarFront()%>"
class="img-fluid img-thumbnail">

</div>

<div class="col-md-6">

<h5>Aadhaar Back</h5>

<img src="<%=kyc.getAadhaarBack()%>"
class="img-fluid img-thumbnail">

</div>

</div>

<hr>

<div class="row">

<div class="col-md-12">

<h5>PAN Card</h5>

<img src="<%=kyc.getPanImage()%>"
class="img-fluid img-thumbnail">

</div>

</div>

<hr>

<form action="<%=request.getContextPath()%>/AdminKYCActionServlet"
      method="post">

    <input type="hidden" name="kycId"
           value="<%=kyc.getKycId()%>">

    <div class="mb-3">
        <label>Remarks</label>

        <textarea class="form-control"
                  name="remarks"
                  rows="3"></textarea>
    </div>

    <button type="submit"
            name="action"
            value="approve"
            class="btn btn-success">
        Approve
    </button>

    <button type="submit"
            name="action"
            value="reject"
            class="btn btn-danger">
        Reject
    </button>

    <a href="<%=request.getContextPath()%>/AdminKYCServlet"
       class="btn btn-secondary">
        Back
    </a>

</form>
</div>

</div>

</div>

</body>

</html>