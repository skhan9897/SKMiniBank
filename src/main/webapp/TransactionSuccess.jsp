<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bank.dao.CustomerDAO"%>
<%@page import="com.bank.model.Customer"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%
String customerId = request.getParameter("customerId");
String amount = request.getParameter("amount");
String type = request.getParameter("type");

if(type==null) type="Transaction";
if(amount==null) amount="0.00";

Customer customer=null;

if(customerId!=null){

    CustomerDAO dao=new CustomerDAO();

    customer=dao.getCustomerById(Integer.parseInt(customerId));

}

String txnId="SKTXN"+System.currentTimeMillis();

String date=new SimpleDateFormat("dd MMM yyyy hh:mm:ss a").format(new Date());
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>SK MINI BANK | Transaction Receipt</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>

<meta http-equiv="refresh"
content="3;url=CustomerProfileServlet?customerId=<%=customerId%>">

<style>

body{

background:#eef4fb;

font-family:Segoe UI;

}

.receipt{

width:620px;

margin:40px auto;

background:#fff;

border-radius:20px;

box-shadow:0 10px 30px rgba(0,0,0,.18);

padding:35px;

}

.logo{

text-align:center;

margin-bottom:20px;

}

.logo img{

width:70px;

height:70px;

}

.bank{

font-size:28px;

font-weight:bold;

color:#0056d6;

margin-top:10px;

}

.success{

text-align:center;

font-size:65px;

color:#16a34a;

}

.title{

text-align:center;

font-size:30px;

font-weight:bold;

margin-top:10px;

}

.amount{

text-align:center;

font-size:42px;

font-weight:bold;

color:#198754;

margin:20px 0;

}

table{

width:100%;

}

td{

padding:12px;

border-bottom:1px solid #ececec;

font-size:16px;

}

.footer{

text-align:center;

margin-top:25px;

}

.btn{

padding:10px 30px;

margin:5px;

}

</style>

</head>

<body>

<div class="receipt" id="receipt">

<div class="logo">

<i class="fa-solid fa-building-columns"
style="font-size:70px;color:#0056d6;"></i>

<div class="bank">

SK MINI BANK

</div>

</div>

<div class="success">

<i class="fa-solid fa-circle-check"></i>

</div>

<div class="title">

Transaction Successful

</div>

<div class="amount">

₹ <%=amount%>

</div>

<table class="table table-borderless">

<tr>

<td><b>Customer Name</b></td>

<td><%=customer!=null?customer.getFullName():"-"%></td>

</tr>

<tr>

<td><b>Account Number</b></td>

<td><%=customer!=null?customer.getAccountNumber():"-"%></td>

</tr>

<tr>

<td><b>Available Balance</b></td>

<td>₹ <%=customer!=null?customer.getBalance():"0.00"%></td>

</tr>

<tr>

<td><b>Transaction Amount</b></td>

<td class="text-success fw-bold">

₹ <%=amount%>

</td>

</tr>

<tr>

<td><b>Transaction Type</b></td>

<td>

<%=type%>

</td>

</tr>

<tr>

<td><b>Transaction ID</b></td>

<td>

<%=txnId%>

</td>

</tr>

<tr>

<td><b>Date & Time</b></td>

<td>

<%=date%>

</td>

</tr>

<tr>

<td><b>Status</b></td>

<td class="text-success fw-bold">

SUCCESS

</td>

</tr>

</table>
<div class="footer">

<button class="btn btn-primary"
onclick="window.print()">

<i class="fa fa-print"></i>

Print Receipt

</button>

<button class="btn btn-danger"
onclick="downloadPDF()">

<i class="fa fa-download"></i>

Download PDF

</button>

</div>

<hr>

<div class="text-center mt-3">

<p style="font-size:16px;color:#555;">

Thank You For Banking With

<b style="color:#0056d6;">

SK MINI BANK

</b>

</p>

<p style="color:#28a745;font-weight:bold;">

Redirecting To Customer Profile...

</p>

<div class="spinner-border text-primary"></div>

</div>

</div>

<script>

function downloadPDF(){

var element=document.getElementById("receipt");

var opt={

margin:0.3,

filename:"SK_Mini_Bank_Receipt.pdf",

image:{type:"jpeg",quality:1},

html2canvas:{scale:2},

jsPDF:{unit:"in",format:"a4",orientation:"portrait"}

};

html2pdf().set(opt).from(element).save();

}

</script>

</body>

</html>