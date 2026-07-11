<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Send OTP</title>
</head>

<body>

<h2>Mobile Verification</h2>

<form action="SendOTPServlet" method="post">

    Mobile Number

    <input type="text"
           name="mobile"
           maxlength="10"
           required>

    <br><br>

    <input type="submit"
           value="Send OTP">

</form>

</body>
</html>