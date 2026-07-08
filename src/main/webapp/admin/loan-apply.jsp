<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>SK Mini Bank | Apply Loan</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

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
width:95%;
margin:25px auto;
}

.header{

background:linear-gradient(90deg,#0d6efd,#003b8e);
padding:18px;
border-radius:12px;
color:white;
box-shadow:0 4px 12px rgba(0,0,0,.2);

}

.header h2{
font-size:28px;
}

.loan-grid{

display:grid;
grid-template-columns:repeat(auto-fit,minmax(180px,1fr));
gap:18px;
margin-top:25px;

}

.loan-card{

background:white;
padding:20px;
border-radius:10px;
text-align:center;
box-shadow:0 3px 10px rgba(0,0,0,.12);
transition:.3s;

}

.loan-card:hover{

transform:translateY(-5px);

}

.loan-card i{

font-size:35px;
color:#0d6efd;
margin-bottom:12px;

}

.loan-card h3{

font-size:18px;

}

.form-card{

margin-top:30px;
background:white;
padding:25px;
border-radius:12px;
box-shadow:0 3px 12px rgba(0,0,0,.15);

}

.row{

display:flex;
gap:20px;
margin-bottom:18px;

}

.col{

flex:1;

}

label{

display:block;
margin-bottom:6px;
font-weight:bold;

}

input,select{

width:100%;
padding:12px;
border:1px solid #ccc;
border-radius:6px;

}

.btn{

background:#0d6efd;
color:white;
padding:14px;
border:none;
border-radius:6px;
font-size:16px;
cursor:pointer;
width:100%;

}

.btn:hover{

background:#003b8e;

}

</style>

</head>

<body>

<div class="container">

<div class="header">

<h2>

🏦 SK Mini Bank Loan Application

</h2>

<p>

Apply Home, Vehicle, Business, Personal & Other Loans

</p>

</div>

<div class="loan-grid">

<div class="loan-card">

<i class="fa-solid fa-house"></i>

<h3>Home Loan</h3>

</div>

<div class="loan-card">

<i class="fa-solid fa-car"></i>

<h3>Vehicle Loan</h3>

</div>

<div class="loan-card">

<i class="fa-solid fa-user"></i>

<h3>Personal Loan</h3>

</div>

<div class="loan-card">

<i class="fa-solid fa-briefcase"></i>

<h3>Business Loan</h3>

</div>

<div class="loan-card">

<i class="fa-solid fa-graduation-cap"></i>

<h3>Education Loan</h3>

</div>

<div class="loan-card">

<i class="fa-solid fa-coins"></i>

<h3>Gold Loan</h3>

</div>

<div class="loan-card">

<i class="fa-solid fa-seedling"></i>

<h3>Agriculture Loan</h3>

</div>

<div class="loan-card">

<i class="fa-solid fa-building"></i>

<h3>Property Loan</h3>

</div>

</div>

<div class="form-card">

<form action="../LoanServlet" method="post">
    <div class="row">

    <div class="col">
        <label>Customer ID</label>
        <input type="number"
               name="customerId"
               placeholder="Enter Customer ID"
               required>
    </div>

    <div class="col">
        <label>Account Number</label>
        <input type="text"
               name="accountNumber"
               placeholder="Enter Account Number"
               required>
    </div>

</div>

<div class="row">

    <div class="col">
        <label>Customer Name</label>
        <input type="text"
               name="customerName"
               placeholder="Enter Customer Name"
               required>
    </div>

    <div class="col">
        <label>Loan Type</label>

        <select name="loanType" required>

            <option value="">-- Select Loan Type --</option>

            <option>Home Loan</option>
            <option>Vehicle Loan</option>
            <option>Personal Loan</option>
            <option>Business Loan</option>
            <option>Education Loan</option>
            <option>Gold Loan</option>
            <option>Agriculture Loan</option>
            <option>Property Loan</option>

        </select>

    </div>

</div>

<div class="row">

    <div class="col">
        <label>Loan Amount</label>
        <input type="number"
               id="loanAmount"
               name="loanAmount"
               placeholder="Enter Loan Amount"
               onkeyup="calculateEMI()"
               required>
    </div>

    <div class="col">
        <label>Interest Rate (%)</label>
        <input type="number"
               step="0.01"
               id="interestRate"
               name="interestRate"
               placeholder="Example: 8.5"
               onkeyup="calculateEMI()"
               required>
    </div>

</div>

<div class="row">

    <div class="col">
        <label>Duration (Years)</label>
        <input type="number"
               id="durationYear"
               name="durationYear"
               placeholder="Loan Duration"
               onkeyup="calculateEMI()"
               required>
    </div>

    <div class="col">
        <label>Estimated Monthly EMI</label>
        <input type="text"
               id="emi"
               readonly
               style="background:#f8f9fa;font-weight:bold;">
    </div>

</div>

<div class="row">

    <div class="col">
        <button type="submit" class="btn">
            <i class="fa-solid fa-paper-plane"></i>
            Apply Loan
        </button>
    </div>

    <div class="col">
        <button type="reset"
                class="btn"
                style="background:#6c757d;">
            <i class="fa-solid fa-rotate"></i>
            Reset
        </button>
    </div>
    

</div>
    </form>

</div>

</div>

<script>

function calculateEMI(){

    let amount =
    parseFloat(document.getElementById("loanAmount").value);

    let rate =
    parseFloat(document.getElementById("interestRate").value);

    let years =
    parseFloat(document.getElementById("durationYear").value);

    if(isNaN(amount) || isNaN(rate) || isNaN(years)){

        document.getElementById("emi").value="";

        return;

    }

    let monthlyRate = rate / 12 / 100;

    let months = years * 12;

    let emi =
    (amount * monthlyRate * Math.pow(1 + monthlyRate, months)) /
    (Math.pow(1 + monthlyRate, months) - 1);

    if(isFinite(emi)){

        document.getElementById("emi").value =
        "₹ " + emi.toFixed(2);

    }else{

        document.getElementById("emi").value="";

    }

}

</script>

<%

String msg = request.getParameter("msg");

if("success".equals(msg)){
%>

<script>

alert("Loan Application Submitted Successfully.");

</script>

<%
}else if("failed".equals(msg)){
%>

<script>

alert("Loan Application Failed.");

</script>

<%
}else if("error".equals(msg)){
%>

<script>

alert("Something Went Wrong.");

</script>

<%
}
%>

<footer
style="margin-top:30px;
padding:20px;
text-align:center;
background:#0d6efd;
color:white;">

<h3>🏦 SK Mini Bank</h3>

<p>Loan Management System</p>

<p>
© 2026 All Rights Reserved
</p>

</footer>

</body>

</html>