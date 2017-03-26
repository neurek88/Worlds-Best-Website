<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="CSS/Style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>W.B.S.W.</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
	</script>
		<script>
		$(document).ready(function () {
			
			function confirmFunction() {
				var firstName = $("firstName").val();
				var lastName = $("lastName").val();
				var total = $("total").val();
				var shippingAddress = $("shippingAddress").val();
				var billAddress = $("billAddress").val();
				var creditNumber = $("creditNumber").val();
				var creditBrand = $("creditBrand").val();
				var CVV = $("CVV").val();
				console.log(total);
				var expirationDate = $("expirationDate").val();
				
				$.post("CustomerTransactionConfirmation", {firstName:$("firstName").val(), lastName:$("lastName").val(), total:total, shippingAddress:shippingAddress, billAddress:billAddress, creditNumber:creditNumber, creditBrand:creditBrand, CVV:CVV, expirationDate:expirationDate }, function(data,status) {
				    
			    		
			    	 // Following data values are received from the "FormjQueryResponse" app
			    		if(data == 00) {	    			
			    			alert("FROM BANKING APP: Your credit card does not have enough balance or invalid");
			    		}
			    		
			    		if(data == 11) {	
					        $("#CartOrder").load("OrderSuccess.txt");
					        $("#response").html(status);
					        function place_order_function (){
								 $.post("PlaceOrder", {firstName:firstName, lastName:lastName, sum:sum, shippingAddress:shippingAddress, billAddress:billAddress, creditNumber:creditNumber }, function(data,status) {
									 alert("Order Processed!");
								 });
								}
					}
			    	
			    			
			    });
			  }
		});
		</script>
</head>
<body>
<div id=navigation><ul>
<li><a href="#">${sessionScope.userName}</a></li>
<li><a href="CustomerHomePage.jsp"> Home Page </a></li>
<li><a href="Login.jsp"> Login out </a></li>
<li><form action = ViewOrders method = "post"><input type=submit name="submit" value="View Orders"></form></li>
</ul> </div>

<h1> World's Best Shopping Website</h1><br>
<h2>Your Shopping Cart</h2>
<h2>Here are the items in your cart:</h2>
<div id="cartOrder">
<table width="700px" align="center" id='countit'
               style="border:1px solid #000000;" >
            <tr>
                <td colspan=4 align="center"
                    style="background-color:teal">
                    <b>Shopping Cart</b></td>
            </tr>
            <tr style="background-color:lightgrey;">
                <td><b>Product Name</b></td>
                <td><b>Seller Name</b></td>
                <td><b>Quantity</b></td>
                <td><b>Price</b></td>
            </tr>
      
 <c:forEach items="${cart}" var="list">
           <tr>
                <td>${list.getProductName()}</td>
                <td>${list.getSellerId()}</td>
                <td>${list.getAvailableQuantity()}</td>
                <td class="count-me"> ${list.getPrice()}</td>
                
            </tr>
           </c:forEach> 
           </table>
 
           <script language="javascript" type="text/javascript">
            var tds = document.getElementById('countit').getElementsByTagName('td');
            var sum = 0;
            for(var i = 0; i < tds.length; i ++) {
                if(tds[i].className == 'count-me') {
                    sum += isNaN(tds[i].innerHTML) ? 0 : parseInt(tds[i].innerHTML);
                }
            }
            document.getElementById('countit').innerHTML += '<tr><td></td><td colspan=2>Total Cost:</td><td>' + sum + '</td><input type="hidden" id="total" name="total" value="""'+ sum +'"></tr>';
        </script>
         </div>
        <div id="response"></div>	
       
<br>
<form name=creditCheck method="post" onsubmit="return confirmFunction();">
First Name:<input type=text id="firstName" value="${userBean.getFirstName()}"><br>
Last Name:<input type=text id="lastName" value="${userBean.getLastName()}"><br>
Shipping Address:<input type=text id="shippingAddress" value=""><br>
Billing Address:<input type=text id="billAddress" value=""><br>
 <select name="creditBrand" id="creditBrand">
    <option value="AMEX">AMEX</option>
    <option value="Visa">Visa</option>
    <option value="MasterCard">Master Card</option>
  </select>
Card Number: <input type=text id="creditNumber" value=""><br>
Security Code: <input type=text id="CVV" value=""><br>
Expiration Date: <input type=text id="expirationDate" value="">
<br>
<input type="submit" name="submit" value="Submit Order"> <br> 
</form>
<br>
<br>


</body>
</html>