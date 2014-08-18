<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ViehicleR</title>
</head>
<body>

   <a href="resetPaint" >Reset paint warehouse</a>


   <h2>Place an order:</h2>
   <s:form action="process" method="post">
   <s:textfield label="Command" name="command" size="50" />
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