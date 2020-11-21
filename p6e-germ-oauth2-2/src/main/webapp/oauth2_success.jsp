<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>neirong ru xia !!!</h1>
<script>
    //以函数方式
    function setcookie(name,value,days){
        var d= new Date();
        d.setTime(d.getTime()+(days*50*60*1000));
        var expires = d.toGMTString();
        document.cookie = name+"="+value+";expires="+expires;
    }

    const d = JSON.parse('<%= request.getAttribute("content")%>');
    setcookie('P6E_OAUTH2_AUTH_TOKEN', d.token, 1);

    alert(decodeURIComponent(d.redirectUri));
</script>
</body>
</html>
