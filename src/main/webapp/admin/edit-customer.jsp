<%@page import="com.bank.dao.CustomerDAO"%>
<%@page import="com.bank.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
int id=Integer.parseInt(request.getParameter("id"));

CustomerDAO dao=new CustomerDAO();

Customer c=dao.getCustomerById(id);
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Edit Customer</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body class="bg-light">

<div class="container mt-5">

<div class="card shadow">

<div class="card-header bg-warning">

<h3>Edit Customer</h3>

</div>

<div class="card-body">

<form action="../UpdateCustomerServlet" method="post">

<input type="hidden"
name="customerId"
value="<%=c.getCustomerId()%>">


<div class="mb-3">

<label>ID</label>

<input
type="text"
name="customerId"
value="<%=c.getCustomerId()%>"
class="form-control">

</div>



<div class="mb-3">

<label>Full Name</label>

<input
type="text"
name="fullName"
value="<%=c.getFullName()%>"
class="form-control">

</div>

<div class="mb-3">

<label>Mobile</label>

<input
type="text"
name="mobile"
value="<%=c.getMobile()%>"
class="form-control">

</div>

<div class="mb-3">

<label>Email</label>

<input
type="email"
name="email"
value="<%=c.getEmail()%>"
class="form-control">

</div>

<div class="mb-3">

<label>City</label>

<input
type="text"
name="city"
value="<%=c.getCity()%>"
class="form-control">

</div>

<div class="mb-3">

<label>State</label>

<input
type="text"
name="state"
value="<%=c.getState()%>"
class="form-control">

</div>

<button class="btn btn-success">

Update Customer

</button>

</form>

</div>

</div>

</div>

</body>

</html>