<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Verify OTP</title>
</head>
<body>

<h2>Verify OTP</h2>

<form action="VerifyOTPServlet" method="post">

    <input type="text"
           name="mobile"
           placeholder="Mobile Number"
           required>

    <br><br>

    <input type="text"
           name="otp"
           placeholder="Enter OTP"
           required>

    <br><br>

    <input type="submit"
           value="Verify OTP">

</form>

</body>
</html>