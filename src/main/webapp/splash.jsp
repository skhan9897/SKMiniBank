<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SK Mini Bank - Loading...</title>

<style>
*{
    margin:0;
    padding:0;
    box-sizing:border-box;
}

body{
    width:100%;
    height:100vh;
    display:flex;
    justify-content:center;
    align-items:center;
    background:url('<%=request.getContextPath()%>/images/splash1.png') no-repeat center center;
    background-size:cover;
    overflow:hidden;
    font-family:Arial,Helvetica,sans-serif;
}
.container{
    text-align:center;
}

.logo{
    width:220px;
    animation:zoom 1.2s ease-in-out infinite alternate;
}

h1{
    color:#fff;
    margin-top:20px;
    font-size:38px;
    letter-spacing:2px;
}

p{
    color:#d9e8ff;
    margin-top:10px;
    font-size:18px;
}

.loader{
    width:320px;
    height:10px;
    background:#ffffff55;
    border-radius:20px;
    margin:35px auto;
    overflow:hidden;
}

.bar{
    width:0%;
    height:100%;
    background:#ffc107;
    animation:loading 4s linear forwards;
}

@keyframes loading{
    from{
        width:0%;
    }
    to{
        width:100%;
    }
}

@keyframes zoom{
    from{
        transform:scale(1);
    }
    to{
        transform:scale(1.08);
    }
}
</style>

<script>
window.onload = function () {
    setTimeout(function () {
        window.location.replace("<%=request.getContextPath()%>/index.jsp");
    }, 4000);
};
</script>

</head>

<body>

<div class="container">


    


<p>Safe • Secure • Digital Banking</p>

<div class="loader">
<div class="bar"></div>
</div>

</div>

</body>
</html>