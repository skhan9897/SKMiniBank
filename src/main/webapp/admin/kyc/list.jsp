<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.bank.model.KYCRequest"%>

<%
List<KYCRequest> kycList =
(List<KYCRequest>) request.getAttribute("kycList");

if (kycList == null) {
    kycList = new ArrayList<>();
}
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>KYC Requests</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container-fluid mt-4">

<div class="card shadow">

<div class="card-header bg-primary text-white">

<h3>KYC Requests</h3>

</div>

<div class="card-body">

<input type="text"
class="form-control mb-3"
id="searchBox"
placeholder="Search Customer / Account / Aadhaar">

<table class="table table-bordered table-hover">

<thead class="table-dark">

<tr>

<th>KYC ID</th>
<th>Customer ID</th>
<th>Account No</th>
<th>Aadhaar</th>
<th>PAN</th>
<th>Status</th>
<th>Request Date</th>
<th>Action</th>

</tr>

</thead>

<tbody id="kycTable">

<%
for(KYCRequest k : kycList){
%>

<tr>

<td><%=k.getKycId()%></td>

<td><%=k.getCustomerId()%></td>

<td><%=k.getAccountNumber()%></td>

<td><%=k.getAadhaarNumber()%></td>

<td><%=k.getPanNumber()%></td>

<td>

<%
String status = k.getStatus();

if("PENDING".equalsIgnoreCase(status)){
%>

<span class="badge bg-warning text-dark">
PENDING
</span>

<%
}else if("VERIFIED".equalsIgnoreCase(status)){
%>

<span class="badge bg-success">
VERIFIED
</span>

<%
}else{
%>

<span class="badge bg-danger">
REJECTED
</span>

<%
}
%>

</td>

<td><%=k.getRequestDate()%></td>

<td>

<a href="<%=request.getContextPath()%>/AdminKYCViewServlet?customerId=<%=k.getCustomerId()%>"
   class="btn btn-primary btn-sm">
    View
</a>

</td>

</tr>

<%
}
%>

</tbody>

</table>

</div>

</div>

</div>

<script>

document.getElementById("searchBox")
.addEventListener("keyup", function(){

let value=this.value.toLowerCase();

let rows=document.querySelectorAll("#kycTable tr");

rows.forEach(function(row){

row.style.display=row.innerText.toLowerCase().includes(value)
?"":"none";

});

});

</script>

</body>

</html>