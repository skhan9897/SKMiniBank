<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.bank.model.Transaction"%>

<%
List<Transaction> transactionList =
        (List<Transaction>) request.getAttribute("transactionList");

if (transactionList == null) {
    transactionList = new ArrayList<Transaction>();
}

Integer totalTransactions =
        (Integer) request.getAttribute("totalTransactions");

Double totalDeposit =
        (Double) request.getAttribute("totalDeposit");

Double totalWithdraw =
        (Double) request.getAttribute("totalWithdraw");

Double totalTransfer =
        (Double) request.getAttribute("totalTransfer");

if (totalTransactions == null) totalTransactions = 0;
if (totalDeposit == null) totalDeposit = 0.0;
if (totalWithdraw == null) totalWithdraw = 0.0;
if (totalTransfer == null) totalTransfer = 0.0;
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>SK Mini Bank | Reports</title>

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
background:#f4f7fb;
}

.header{

background:#0d6efd;
color:#fff;
padding:18px;
font-size:28px;
font-weight:bold;
text-align:center;

}

.container{

width:95%;
margin:20px auto;

}

.row{

display:flex;
gap:20px;
flex-wrap:wrap;

}

.card{

flex:1;
min-width:220px;
background:#fff;
border-radius:10px;
padding:20px;
box-shadow:0 2px 10px rgba(0,0,0,.15);
text-align:center;

}

.card h3{

margin-bottom:15px;
color:#555;

}

.card h1{

font-size:32px;

}

.blue{
background:#0d6efd;
color:white;
}

.green{
background:#198754;
color:white;
}

.red{
background:#dc3545;
color:white;
}

.orange{
background:#fd7e14;
color:white;
}

</style>

</head>

<body>

<div class="header">

🏦 SK Mini Bank - Live Reports Dashboard

</div>

<div class="container">

<div class="row">

<div class="card blue">

<h3>Total Transactions</h3>

<h1><%= totalTransactions %></h1>

</div>

<div class="card green">

<h3>Total Deposit</h3>

<h1>₹ <%= String.format("%.2f", totalDeposit) %></h1>

</div>

<div class="card red">

<h3>Total Withdraw</h3>

<h1>₹ <%= String.format("%.2f", totalWithdraw) %></h1>

</div>

<div class="card orange">

<h3>Total Transfer</h3>

<h1>₹ <%= String.format("%.2f", totalTransfer) %></h1>

</div>

</div>

<br>

<div class="card">

<h2 style="color:#0d6efd;">Transaction History</h2>
<table style="width:100%; border-collapse:collapse; margin-top:20px;">

    <thead style="background:#0d6efd; color:white;">

        <tr>

            <th style="padding:12px;">Account Number</th>
            <th>Customer Name</th>
            <th>Transaction Type</th>
            <th>Amount</th>
            <th>Balance</th>
            <th>Status</th>
            <th>Date & Time</th>

        </tr>

    </thead>

    <tbody>

<%
if (!transactionList.isEmpty()) {

    for (Transaction t : transactionList) {
%>

<tr>

<td style="padding:10px;"><%= t.getAccountNumber() %></td>

<td><%= t.getCustomerName() %></td>

<td>

<%
if("Deposit".equalsIgnoreCase(t.getTransactionType())){
%>

<span style="color:green;font-weight:bold;">
Deposit
</span>

<%
}else if("Withdraw".equalsIgnoreCase(t.getTransactionType())){
%>

<span style="color:red;font-weight:bold;">
Withdraw
</span>

<%
}else{
%>

<span style="color:#0d6efd;font-weight:bold;">
Transfer
</span>

<%
}
%>

</td>

<td>
₹ <%= String.format("%.2f", t.getAmount()) %>
</td>

<td>
₹ <%= String.format("%.2f", t.getBalance()) %>
</td>

<td>

<%
if("SUCCESS".equalsIgnoreCase(t.getStatus())){
%>

<span style="color:green;">
SUCCESS
</span>

<%
}else{
%>

<span style="color:red;">
FAILED
</span>

<%
}
%>

</td>

<td>
<%= t.getTransactionDate() %>
</td>

</tr>

<%
    }

} else {
%>

<tr>

<td colspan="7"
style="text-align:center;
padding:20px;
color:red;
font-weight:bold;">

No Transactions Available

</td>

</tr>

<%
}
%>

</tbody>

</table>

</div>

<br>
<table style="width:100%; border-collapse:collapse; margin-top:20px;">

    <thead style="background:#0d6efd; color:white;">

        <tr>

            <th style="padding:12px;">Account Number</th>
            <th>Customer Name</th>
            <th>Transaction Type</th>
            <th>Amount</th>
            <th>Balance</th>
            <th>Status</th>
            <th>Date & Time</th>

        </tr>

    </thead>

    <tbody>

<%
if (!transactionList.isEmpty()) {

    for (Transaction t : transactionList) {
%>

<tr>

<td style="padding:10px;"><%= t.getAccountNumber() %></td>

<td><%= t.getCustomerName() %></td>

<td>

<%
if("Deposit".equalsIgnoreCase(t.getTransactionType())){
%>

<span style="color:green;font-weight:bold;">
Deposit
</span>

<%
}else if("Withdraw".equalsIgnoreCase(t.getTransactionType())){
%>

<span style="color:red;font-weight:bold;">
Withdraw
</span>

<%
}else{
%>

<span style="color:#0d6efd;font-weight:bold;">
Transfer
</span>

<%
}
%>

</td>

<td>
₹ <%= String.format("%.2f", t.getAmount()) %>
</td>

<td>
₹ <%= String.format("%.2f", t.getBalance()) %>
</td>

<td>

<%
if("SUCCESS".equalsIgnoreCase(t.getStatus())){
%>

<span style="color:green;">
SUCCESS
</span>

<%
}else{
%>

<span style="color:red;">
FAILED
</span>

<%
}
%>

</td>

<td>
<%= t.getTransactionDate() %>
</td>

</tr>

<%
    }

} else {
%>

<tr>

<td colspan="7"
style="text-align:center;
padding:20px;
color:red;
font-weight:bold;">

No Transactions Available

</td>

</tr>

<%
}
%>

</tbody>

</table>

</div>

<br>
<div class="card" style="margin-top:25px;">

    <h2 style="color:#0d6efd;">
        Quick Report Summary
    </h2>

    <table style="width:100%;border-collapse:collapse;">

        <tr style="background:#0d6efd;color:white;">

            <th style="padding:12px;">Report</th>
            <th>Value</th>

        </tr>

        <tr>

            <td style="padding:10px;">Total Transactions</td>
            <td><%= totalTransactions %></td>

        </tr>

        <tr>

            <td style="padding:10px;">Total Deposit</td>
            <td>₹ <%= String.format("%.2f", totalDeposit) %></td>

        </tr>

        <tr>

            <td style="padding:10px;">Total Withdraw</td>
            <td>₹ <%= String.format("%.2f", totalWithdraw) %></td>

        </tr>

        <tr>

            <td style="padding:10px;">Total Transfer</td>
            <td>₹ <%= String.format("%.2f", totalTransfer) %></td>

        </tr>

    </table>

</div>

<br>

<div style="text-align:center;">

<button onclick="window.print()"
style="background:#0d6efd;
color:white;
padding:12px 25px;
border:none;
border-radius:6px;
cursor:pointer;
font-size:16px;">

<i class="fa fa-print"></i>

Print Report

</button>

&nbsp;&nbsp;

<button onclick="downloadCSV()"
style="background:#198754;
color:white;
padding:12px 25px;
border:none;
border-radius:6px;
cursor:pointer;
font-size:16px;">

<i class="fa fa-download"></i>

Export CSV

</button>

</div>

<script>

function downloadCSV(){

alert("CSV Export Module will be added in next step.");

}

setTimeout(function(){

location.reload();

},30000);

</script>

<br><br>

<div style="background:#ffffff;
padding:20px;
border-radius:10px;
box-shadow:0 2px 10px rgba(0,0,0,.10);
text-align:center;">

<h3 style="color:#0d6efd;">

SK Mini Bank

</h3>

<p>

Live Banking Report Dashboard

</p>

<p>

Generated On :

<%= new java.util.Date() %>

</p>

</div>

</div>

</body>

</html>