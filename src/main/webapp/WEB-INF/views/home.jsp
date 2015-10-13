<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>
<body id="body">
<p><a href="login">I'm an admin</a>


<input type="hidden" name="orderId" value=""/>
<h1>
	Hello! Please pick a flavor.
</h1>

<div id="flavors">

</div>

<div id="basket" style="display:none;">

	<div id="items">
	
	</div>
<a href="javascript:order();">Order</a>
</div>

<script>

$( document ).ready(function() {
	$.getJSON( "icecream/flavors", function( data ) {
		  var items = [];
		  $.each( data, function( key, val ) {
		    items.push('<div>' + val.flavorName + '&nbsp; <a href="javascript:add(' + val.id + ')">add</a></div>')
		  });
		  $("#flavors").html(items.join(""));
	});
});

function add(flavorId) {
	var orderId = $("input[name=orderId]").val();
	if (orderId == '') {
		$.getJSON( "order/create", function( data ) {
			  orderId = data.orderNumber;
			  $("input[name=orderId]").val(orderId);
			  addFlavor(orderId, flavorId);
		});
	} else {
		addFlavor(orderId, flavorId);		
	}
}

function addFlavor(orderId, flavorId) {
	$.getJSON( "order/add/" + orderId + "/" + flavorId + "/1" , function( data ) {
		  var items = [];
		  $.each( data.items, function( key, val ) {			 			  
			  items.push('<div>' + val.flavor.flavorName + ':&nbsp;scoops:'+val.scoops+'&nbsp;price:'+ val.totalItemPrice+' </div>')
		  });
		  $("#items").html(items.join(""));
		  $("#basket").show();
	});
}

function order() {
	var orderId = $("input[name=orderId]").val();
	$.getJSON( "order/" + orderId, function( data ) {
		 orderId = data.orderNumber;
		 $("input[name=orderId]").val('');
		 $("#body").html('<h1> Thanks for your order. You order number is ' + orderId + '</h1>');
	});
}

</script>


</body>
</html>
