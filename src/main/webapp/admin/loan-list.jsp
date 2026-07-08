<%@ page import="java.util.List" %>
<%@ page import="com.bank.dao.LoanDAO" %>
<%@ page import="com.bank.model.Loan" %>

<%
LoanDAO dao = new LoanDAO();
List<Loan> loanList = dao.getAllLoans();
%>
<h3>Total Loans: <%= loanList.size() %></h3>
<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>SK Mini Bank | Loan List</title>

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<style>

*{
margin:0;
padding:0;
box-sizing:border-box;
font-family:Arial,sans-serif;
}

body{
background:#eef3fb;
}

.container{
width:96%;
margin:25px auto;
}

.header{
background:linear-gradient(90deg,#0d6efd,#003b8e);
color:white;
padding:18px;
border-radius:10px;
margin-bottom:25px;
}

.header h2{
font-size:28px;
}

.searchBox{

width:350px;
padding:12px;
border:1px solid #ccc;
border-radius:6px;
margin-bottom:20px;

}

table{

width:100%;
border-collapse:collapse;
background:white;
box-shadow:0 3px 10px rgba(0,0,0,.15);

}

th{

background:#0d6efd;
color:white;
padding:12px;

}

td{

padding:12px;
text-align:center;
border-bottom:1px solid #ddd;

}

tr:hover{

background:#f8f9fa;

}

.approve{

background:#198754;
color:white;
padding:8px 14px;
border-radius:5px;
text-decoration:none;

}

.reject{

background:#dc3545;
color:white;
padding:8px 14px;
border-radius:5px;
text-decoration:none;

}

.delete{

background:#6c757d;
color:white;
padding:8px 14px;
border-radius:5px;
text-decoration:none;

}

.badge{

padding:6px 12px;
border-radius:20px;
color:white;
font-size:13px;

}

.pending{

background:#fd7e14;

}

.approved{

background:#198754;

}

.rejected{

background:#dc3545;

}

</style>

</head>

<body>

<div class="container">

<div class="header">

<h2>

<h2>
    <i class="fas fa-file-invoice-dollar"></i>
    Loan Management
</h2>

</h2>

</div>

<input
type="text"
id="searchLoan"
class="searchBox"
placeholder="Search Account Number..."
onkeyup="searchLoan()">

<table id="loanTable">

<thead>

<tr>

<th>ID</th>
<th>Customer</th>
<th>Account</th>
<th>Loan Type</th>
<th>Amount</th>
<th>Interest</th>
<th>Years</th>
<th>Status</th>
<th>Apply Date</th>
<th>Action</th>

</tr>

</thead>

<tbody>
    <%
for(Loan loan : loanList){
%>

<tr>

<td><%=loan.getLoanId()%></td>

<td><%=loan.getCustomerName()%></td>

<td><%=loan.getAccountNumber()%></td>

<td><%=loan.getLoanType()%></td>

<td>&#8377; <%=loan.getLoanAmount()%></td>

<td><%=loan.getInterestRate()%> %</td>

<td><%=loan.getDurationYear()%> Years</td>

<td>

<%
if("Approved".equalsIgnoreCase(loan.getStatus())){
%>

<span class="badge approved">

Approved

</span>

<%
}else if("Rejected".equalsIgnoreCase(loan.getStatus())){
%>

<span class="badge rejected">

Rejected

</span>

<%
}else{
%>

<span class="badge pending">

Pending

</span>

<%
}
%>

</td>

<td><%=loan.getApplyDate()%></td>

<td>

<a class="approve"
href="../ApproveLoanServlet?id=<%=loan.getLoanId()%>">

<i class="fa fa-check"></i>

Approve

</a>

&nbsp;

<a class="reject"
href="../RejectLoanServlet?id=<%=loan.getLoanId()%>">

<i class="fa fa-times"></i>

Reject

</a>

&nbsp;

<a class="delete"
href="../DeleteLoanServlet?id=<%=loan.getLoanId()%>"
onclick="return confirm('Delete this loan?');">

<i class="fa fa-trash"></i>

Delete

</a>

</td>

</tr>

<%
}
%>

</tbody>

</table>

<br><br>

<div style="text-align:center;color:#666;">

SK Mini Bank Loan Management System

</div>
<script>

function searchLoan(){

    let input =
    document.getElementById("searchLoan").value.toUpperCase();

    let table =
    document.getElementById("loanTable");

    let tr =
    table.getElementsByTagName("tr");

    for(let i=1;i<tr.length;i++){

        let td =
        tr[i].getElementsByTagName("td")[2];

        if(td){

            let txt =
            td.textContent || td.innerText;

            if(txt.toUpperCase().indexOf(input)>-1){

                tr[i].style.display="";

            }else{

                tr[i].style.display="none";

            }

        }

    }

}

</script>

<br>

<div style="display:flex;gap:15px;justify-content:center;">

<button onclick="window.print()"
style="background:#0d6efd;
color:white;
padding:12px 25px;
border:none;
border-radius:6px;
cursor:pointer;">

<i class="fa fa-print"></i>

Print Report

</button>

<button
style="background:#198754;
color:white;
padding:12px 25px;
border:none;
border-radius:6px;
cursor:pointer;">

<i class="fa fa-file-excel"></i>

Export Excel

</button>

<button
onclick="location.href='loan-apply.jsp'"
style="background:#fd7e14;
color:white;
padding:12px 25px;
border:none;
border-radius:6px;
cursor:pointer;">

<i class="fa fa-plus"></i>

Apply New Loan

</button>

</div>

<script>

setTimeout(function(){

location.reload();

},30000);

</script>

<footer
style="margin-top:40px;
background:#0d6efd;
padding:20px;
text-align:center;
color:white;">

<h3>

? SK Mini Bank

</h3>

<p>

Loan Management System

</p>

<p>

Generated :
<%= new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
.format(new java.util.Date()) %>

</p>

</footer>

</div>

</body>

</html>