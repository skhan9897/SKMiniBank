<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>SK Mini Bank - Loan Application</title>

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
background:#eef3f9;
}

.container{
width:90%;
margin:30px auto;
}

.card{
background:#fff;
padding:30px;
border-radius:10px;
box-shadow:0 5px 15px rgba(0,0,0,.15);
}

h2{
color:#0d6efd;
margin-bottom:20px;
}

.row{
display:flex;
gap:20px;
margin-bottom:15px;
}

.col{
flex:1;
}

label{
display:block;
font-weight:bold;
margin-bottom:5px;
}

input,select{
width:100%;
padding:12px;
border:1px solid #ccc;
border-radius:6px;
font-size:15px;
}

.loanBox{

display:grid;
grid-template-columns:repeat(3,1fr);
gap:15px;
margin-bottom:25px;

}

.loanCard{

background:#0d6efd;
color:white;
padding:20px;
border-radius:8px;
text-align:center;
font-size:18px;
font-weight:bold;

}

button{

background:#198754;
color:white;
padding:14px;
width:100%;
border:none;
font-size:18px;
border-radius:6px;
cursor:pointer;

}

button:hover{

background:#157347;

}

</style>

</head>

<body>

<div class="container">

<div class="card">

<h2>
<i class="fa-solid fa-building-columns"></i>
Loan Application
</h2>

<div class="loanBox">

<div class="loanCard">
🏠 Home Loan
</div>

<div class="loanCard">
🚗 Car Loan
</div>

<div class="loanCard">
🎓 Education Loan
</div>

<div class="loanCard">
💼 Business Loan
</div>

<div class="loanCard">
💳 Personal Loan
</div>

<div class="loanCard">
🪙 Gold Loan
</div>

</div>

<form action="../LoanServlet" method="post">

<div class="row">

<div class="col">

<label>Customer ID</label>

<input type="number"
name="customerId"
required>

</div>

<div class="col">

<label>Account Number</label>

<input type="text"
name="accountNumber"
required>

</div>

</div>

<div class="row">

<div class="col">

<label>Customer Name</label>

<input type="text"
name="customerName"
required>

</div>

<div class="col">

<label>Loan Type</label>

<select name="loanType">

<option>Home Loan</option>

<option>Personal Loan</option>

<option>Car Loan</option>

<option>Education Loan</option>

<option>Business Loan</option>

<option>Gold Loan</option>

</select>

</div>

</div>

<div class="row">

<div class="col">

<label>Loan Amount</label>

<input type="number"
name="loanAmount"
required>

</div>

<div class="col">

<label>Interest Rate (%)</label>

<input type="number"
step="0.01"
name="interestRate"
required>

</div>

</div>

<div class="row">

<div class="col">

<label>Duration (Years)</label>

<input type="number"
name="durationYear"
required>

</div>

<div class="col">

<label>Status</label>

<input type="text"
value="Pending"
readonly>

</div>

</div>

<button type="submit">

<i class="fa-solid fa-paper-plane"></i>

Apply Loan

</button>

</form>

</div>
    

</div>

</body>

</html>