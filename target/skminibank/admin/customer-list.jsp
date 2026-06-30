<%@page import="java.util.List"%>
<%@page import="com.bank.dao.CustomerDAO"%>
<%@page import="com.bank.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Customer List</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

<link rel="stylesheet" href="../css/dashboard.css">

</head>

<body>

<nav class="navbar navbar-dark bg-primary">

<div class="container-fluid">

<a class="navbar-brand fw-bold" href="dashboard.jsp">

SK Mini Bank

</a>

<a href="../login.jsp" class="btn btn-light">

Logout

</a>

</div>

</nav>

<div class="container mt-4">

<div class="card shadow">

<div class="card-header bg-success text-white">

<h3>

<i class="fa-solid fa-users"></i>

Customer List

</h3>

</div>

<div class="card-body">

<table class="table table-bordered table-hover">

<thead class="table-dark">

<tr>

<th>ID</th>
<th>Name</th>
<th>Father</th>
<th>Mobile</th>
<th>Email</th>
<th>Gender</th>
<th>City</th>
<th>State</th>
<th>Action</th>

</tr>

</thead>

<tbody>

<%

CustomerDAO dao = new CustomerDAO();

List<Customer> list = dao.getAllCustomers();

for(Customer c : list){

%>

<tr>

<td><%=c.getCustomerId()%></td>

<td><%=c.getFullName()%></td>

<td><%=c.getFatherName()%></td>

<td><%=c.getMobile()%></td>

<td><%=c.getEmail()%></td>

<td><%=c.getGender()%></td>

<td><%=c.getCity()%></td>

<td><%=c.getState()%></td>

<td>

<a href="edit-customer.jsp?id=<%=c.getCustomerId()%>"
   class="btn btn-warning btn-sm">
    <i class="fa-solid fa-pen"></i>
</a>

<a href="../CustomerProfileServlet?customerId=<%=c.getCustomerId()%>"
   class="btn btn-info btn-sm">
    <i class="fa-solid fa-eye"></i>
</a>

<a href="../DeleteCustomerServlet?id=<%=c.getCustomerId()%>"
   class="btn btn-danger btn-sm"
   onclick="return confirm('Delete this customer?');">
    <i class="fa-solid fa-trash"></i>
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>