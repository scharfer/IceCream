<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Login</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>
<body id="body">
<h1>
	Enter User name and password
</h1>


<form name="login" action='admin/login' method="get">
	User Name: <input name="userName" type="text"/>
	Password: <input name="password" type="text"/>
	<input type="submit"/>
</form>

<script>

$( document ).ready(function() {
	$( "form[name=login]" ).submit(function( event ) {		
		event.preventDefault();
		var params = $('form[name=login]').serialize();               
        $.ajax({
            type: "GET",
            url: $('form[name=login]').attr("action"),
            data: params,
            success: function(data) {
               window.location = 'manager?token=' + data;               
            }
        });
        return false;
	});
});

</script>


</body>
</html>
