<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ViehicleR</title>

<script>


function getVehicles(){
	$.get( "getVehiclesAjax", function( data ) {
		data = data.replace(/&quot;/g, '"');
		var json = jQuery.parseJSON( data );
		for(var i = 0 ; i < json.Vehicles.length ; i++)
			$("#vehicleType").append(new Option(json.Vehicles[i], json.Vehicles[i]));
	});
}


function getVehiclesOptions(v){

	$("#options").empty();

	$.get( "getVehicleOptionsAjax?vehicle=" + v, function( data ) {
		data = data.replace(/&quot;/g, '"');
		
		var json = jQuery.parseJSON( data );
		
		var z = 0;
		
		if(v!="ferrari")
		for(var i = 0 ; i < json.options.length ; i++){

			z++;
			
			$("#options").append("<br /><br />");
			
			$("#options").append(document.createTextNode(json.options[i]["label"] + ": "));
		
			if(json.options[i]["type"] == "input"){
				var input = document.createElement("input");
				input.id = "_"+json.options[i]["index"];
				input.className = "command"; 
				input.type = "text";
				$("#options").append(input);
			}else
				if(json.options[i]["type"] == "select"){
					var selectList = document.createElement("select");
					selectList.id = "_"+json.options[i]["index"];
					selectList.className = "command";
					$("#options").append(selectList);
					
					for(var k = 0 ; k < json.options[i]["vals"].length ; k++)
						$("#_"+json.options[i]["index"]).append(new Option(json.options[i]["vals"][k], json.options[i]["vals"][k]));
					
				}
		
		}
		
		$("#options").append("<br /><br />");
		
		var input = document.createElement("input");
		input.type = 'submit';	
		input.value = 'Submit';
		
		input.onclick = function () {
			makeOrder(z);
		};
		
		$("#options").append(input);
		
	});
}


function makeOrder(k){
	var command = $("#vehicleType").val();
	
	for(var i = 1 ; i <= k ; i++)
		command += " " + $("#_"+i).val();

	$("#commandField").val(command);

	$("#submitCommandForm").submit();

}





getVehicles();

</script>

</head>


<body style="margin:30px" >

   <a href="resetPaint" >Reset paint warehouse</a> <br /><br />
   
<ul class="nav nav-tabs" role="tablist" id="myTab">
  <li class="active"><a href="#command" role="tab" data-toggle="tab">Command line</a></li>
  <li><a href="#ui" role="tab" data-toggle="tab">UI</a></li>
</ul>

<div class="tab-content">
  <div class="tab-pane active" id="command">

   <h2>Place an order:</h2>
   <s:form action="process" method="post" id="submitCommandForm" >
   <s:textfield label="Command" name="command" id="commandField" size="50" />
   <s:submit value="Place" />
   </s:form>
   
   <s:iterator value="o.errors">
  		<p style="color:red"><s:property/></p>
	</s:iterator>
   
   
   <h2>Examples:</h2>
   <p>[cancel|delete] car blue 6 1 </p>
   <p>[cancel|delete] motorcycle yellow f </p>
   <p>[cancel|delete] boat red yes </p>
   <p>[cancel|delete] boat red no 1 </p>
   <p>[cancel|delete] ferrari </p>
   <br />

  
		
  </div>
  
  <div class="tab-pane" id="ui">

   <h2>Place an order:</h2>
   
   <p>Select Vehicle Type</p>
   <select id="vehicleType" onchange="getVehiclesOptions(this.value)"><option></option></select>    
   
   <div id="options">
   
   
   </div>

  </div>
</div>




<br /><hr />
   
   <h2>Order History:</h2>
   <s:iterator value="orders">
   
   		<s:if test="status.name().equals('inprogress')">
   			<p> 
   				[<a href="cancelOrder?orderId=<s:property value='id' />" >Cancel</a>]
  				[<a href="deleteOrder?orderId=<s:property value='id' />" >Delete</a>]
  				[<s:property value="status"/>] 
  				<s:property value="viehicle"/>
  			</p>
   		</s:if>
   		<s:else>
   			<p> 
   				[<a href="deleteOrder?orderId=<s:property value='id' />" >Delete</a>]
  				[<s:property value="status"/>] 
  				<s:property value="viehicle"/>
  			</p>
   		</s:else>
  		
	</s:iterator>


</body>
</html>