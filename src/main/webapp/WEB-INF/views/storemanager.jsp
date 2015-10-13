<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>
<body id="body">
<input type="hidden" name="token" value="<%=request.getParameter("token")%>"/>
<h1>
	Store Manager
</h1>

<div id="flavors">

</div>

<h1>Add Flavor</h1>
<div id="addFlavor">
	<form name="addFlavorForm" action="admin/icecream/add">
		<input type="hidden" name="AuthToken" value="<%=request.getParameter("token")%>"/>
		<input type="hidden" name="userName" value="admin"/>
		Name: <input name="name" type="text"/>
		Price: <input name="price" type="text"/>
		Inventory: <input name="inventory" type="text"/>
		<input type="submit"/>	
	</form>

</div>

<h1>Sales</h1>
Order Number | Order Value
<div id="totalSales">


</div>
<div id="totalRevenue"></div>


<script>

$( document ).ready(function() {
	var token = $("input[name=token]").val();
	getFlavors();
	$.getJSON( "admin/order/allOrders?userName=admin&AuthToken=" + token, function( data ) {
		  var items = [];
		  $.each( data, function( key, val ) {
		    items.push('<div><span>' + val.orderNumber + '</span>&nbsp;$<span>' + val.total + '</span></div>')
		  });
		  $("#totalSales").html(items.join(""));
	});
	$.getJSON( "admin/order/totalSales?userName=admin&AuthToken=" + token, function( data ) {		  
		  $("#totalRevenue").html('Total: $' + data);
	});
	$( "form[name=addFlavorForm]" ).submit(function( event ) {		
		event.preventDefault();
		var params = $('form[name=addFlavorForm]').serialize();               
        $.ajax({
            type: "GET",
            url: $('form[name=addFlavorForm]').attr("action"),
            data: params,
            success: function(data) {
            	getFlavors();
            }
        });
        return false;
	});
});

function getFlavors() {
	$.getJSON( "icecream/flavors", function( data ) {
		  var items = [];
		  $.each( data, function( key, val ) {
		    items.push('<div><span>' + val.flavorName + '</span><input onchange="updateInventory(this,'+val.id+');" name="inventory" value="'+val.inventory+'"/><input name="price" onchange="updatePrice(this,'+val.id+');" value="'+val.price+'"/>&nbsp;<a href="javascript:remove('+val.id+');">remove</a></div>')
		  });
		  $("#flavors").html(items.join(""));
	});
}

function updatePrice(eml,fid) {
	var token = $("input[name=token]").val();
	var price = $(eml).val();
	$.getJSON( "admin/icecream/price/" + fid + "?price=" + price + "&userName=admin&AuthToken=" + token, function( data ) {
		  
	});
}

function updateInventory(elm, fid) {	
	var token = $("input[name=token]").val();
	var inv = $(elm).val();	
	$.getJSON( "admin/icecream/inventory/" + fid + "/" + inv + "?userName=admin&AuthToken=" + token, function( data ) {
		  
	});
}

function remove( fid) {	
	var token = $("input[name=token]").val();
	$.getJSON( "admin/icecream/remove/" + fid+ "?userName=admin&AuthToken=" + token, function( data ) {
		getFlavors();
	});
}



</script>


</body>
</html>
