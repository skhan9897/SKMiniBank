<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error | Admin Panel</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="alert alert-danger shadow-sm">
            <h4 class="alert-heading">System Error Occurred</h4>
            <p>${errorMessage != null ? errorMessage : "An unexpected error happened during processing."}</p>
            <hr>
            <a href="${pageContext.request.contextPath}/DashboardServlet" class="btn btn-outline-danger">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
