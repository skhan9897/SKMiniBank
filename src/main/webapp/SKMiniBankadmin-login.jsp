<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>SK Mini Bank | Admin Login</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">

<style>

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:Segoe UI,sans-serif;
}

body{

    height:100vh;

    background:linear-gradient(rgba(0,50,120,.65),rgba(0,80,180,.65)),
    url("images/banner.png");

    background-size:cover;
    background-position:center;

    display:flex;
    justify-content:center;
    align-items:center;
}

.login-box{

    width:430px;

    background:#fff;

    border-radius:18px;

    padding:35px;

    box-shadow:0 15px 35px rgba(0,0,0,.35);

}

.logo{

    text-align:center;
    margin-bottom:15px;

}

.logo i{

    font-size:55px;
    color:#0d6efd;

}

.logo h2{

    color:#0d6efd;
    font-weight:bold;
    margin-top:10px;

}

.logo p{

    color:#777;
    margin-bottom:20px;

}

.form-control{

    height:48px;
    border-radius:10px;

}

.input-group-text{

    border-radius:10px 0 0 10px;

}

.btn-login{

    height:50px;
    border-radius:10px;
    font-size:18px;
    font-weight:bold;

}

.footer{

    margin-top:20px;
    text-align:center;
    color:#666;
    font-size:14px;

}

</style>

</head>

<body>

<div class="login-box">

<div class="logo">

<i class="fas fa-university"></i>

<h2>SK Mini Bank</h2>

<p>Administrator Login</p>

</div>

<%
String error=(String)request.getAttribute("error");
if(error!=null){
%>

<div class="alert alert-danger text-center">
<%=error%>
</div>

<%
}
%>

<form action="<%=request.getContextPath()%>/AdminLoginServlet" method="post">

<div class="mb-3">

<label class="fw-bold">Admin ID</label>

<div class="input-group">

<span class="input-group-text">

<i class="fa fa-user-shield"></i>

</span>

<input
type="text"
name="adminId"
class="form-control"
placeholder="Enter Admin ID"
required>

</div>

</div>

<div class="mb-3">

<label class="fw-bold">Password</label>

<div class="input-group">

<span class="input-group-text">

<i class="fa fa-lock"></i>

</span>

<input
type="password"
name="password"
id="password"
class="form-control"
placeholder="Enter Password"
required>

<button
class="btn btn-outline-secondary"
type="button"
onclick="togglePassword()">

<i class="fa fa-eye"></i>

</button>

</div>

</div>

<div class="form-check mb-3">

<input
class="form-check-input"
type="checkbox"
id="remember">

<label
class="form-check-label"
for="remember">

Remember Me

</label>

</div>

<button
type="submit"
class="btn btn-primary w-100 btn-login">

<i class="fa fa-right-to-bracket"></i>

Admin Login

</button>

<div class="mt-3 text-center">

<a href="SKMiniBank.jsp" class="btn btn-outline-secondary">

<i class="fa fa-house"></i>

Back To Home

</a>

</div>

</form>

<div class="footer">

© 2026 SK Mini Bank<br>

Secure Admin Panel

</div>

</div>

<script>

function togglePassword(){

var x=document.getElementById("password");

if(x.type==="password"){

x.type="text";

}else{

x.type="password";

}

}

</script>

</body>
</html>