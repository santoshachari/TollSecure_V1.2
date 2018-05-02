<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>
<!DOCTYPE html>

<html>

<head>
<%-- <img alt="image"  src="<c:url value="/Wallpapers/1. jpeg" />">--%>

<!-- modal style -->
	<style>
/* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
}

/* The Close Button */
.close {
    color: #aaaaaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}
</style>

	<title>Toll Gate Entry Form</title>
	
	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
	<!-- connecting to jQuery -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jQuery.js"></script>
	
	<!-- Bootstrap and css -->
	<link type="text/css"
	  rel = "stylesheet"
	  href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css"
	/>
	
	<link type="text/css"
	  rel = "stylesheet"
	  href="${pageContext.request.contextPath}/resources/css/toll_transaction_style.css"
	/>

	
	<script type="text/javascript">	
	
	//get the remaining after he prints the winndow so that time is server time
	
	function refreshRemainingTime() {

		//call ajax
    	if (typeof XMLHttpRequest != "undefined") {
            xmlHttp = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        } else if(xmlHttp==null) {
            alert("Browser does not support XMLHTTP Request");
            return;
        }
    	
    	
    	var link = "${pageContext.request.contextPath}/tollTransaction/getRemainingTime";
    	xmlHttp.onreadystatechange = stateChange5;
        xmlHttp.open("GET", link, true);
        xmlHttp.send(null);
	
	}
	
	
	function stateChange5() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			 var str = xmlHttp.responseText;
			 str = str.trim();
			 document.getElementById("remainingTime").value=str;
			 getRemainingTime();
		 }
	}
	
	//for checking whether pass for thhe following vehicle number is valid
	function checkPass() {

	    var vehicleNumber = document.getElementById("vnumber").value;
	    if (!(/^[a-zA-Z]{2}[0-9]{1,2}[a-zA-Z]{1,3}[0-9]{4}$/.test(vehicleNumber))) {
	    	alert("please enter a valid vehicle number first");
	    	document.getElementById("cash").checked = true;
	    } else {
	    	
		    //clear if exempt vehicle is selected
		    document.getElementById("eNone").checked=true;
	    	
	    	//call ajax
	    	if (typeof XMLHttpRequest != "undefined") {
	            xmlHttp = new XMLHttpRequest();
	        } else if (window.ActiveXObject) {
	            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	        } else if(xmlHttp==null) {
	            alert("Browser does not support XMLHTTP Request");
	            return;
	        }
	    	
	    	
	    	var link = "${pageContext.request.contextPath}/pass/checkPassIsValid?vehicleNumber="+vehicleNumber;
	    	xmlHttp.onreadystatechange = stateChange4;
	        xmlHttp.open("GET", link, true);
	        xmlHttp.send(null);
		}
	}
	
	//when cash button is clicked
	//when cash is checked
	function checkCash() {
		document.getElementById("eNone").checked = true;
		MyFunction();
	}
	
	function stateChange4() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			 var str = xmlHttp.responseText;
			 str = str.trim();
			 
			 document.getElementById("hdivpass").innerHTML = str;
			 
			 //fill vehicle class and vehicle class id
			 document.getElementById("vehicleclassid").value=document.getElementById("vci").innerHTML;
			 document.getElementById("vehicleclass").value=document.getElementById("vc").innerHTML;
			
			 
			 //if pass is invalid do the following
			 if (document.getElementById("vci").innerHTML=="") {
				document.getElementById("bmsg").innerHTML = document.getElementById("msg").innerHTML;
				document.getElementById("badModal").style.display = "block";
			} else {
				document.getElementById("tollAmt").innerHTML = "<option value='0.00'>0.00</option>";
				document.getElementById("gmsg").innerHTML = document.getElementById("msg").innerHTML;
				document.getElementById("goodModal").style.display = "block";
			} 
		 }
	}
	
	function checkForVehicleClassAndReturnType(vnumber, laneId) {

		//alert(str);
		if (typeof XMLHttpRequest != "undefined") {
            xmlHttp = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        } else if(xmlHttp==null) {
            alert("Browser does not support XMLHTTP Request");
            return;
        }
		
		var url = "${pageContext.request.contextPath}/tollTransaction/getVclassAndReturn?vnumber="+vnumber+"&laneId="+laneId;
		
		xmlHttp.onreadystatechange = stateChange3;
        xmlHttp.open("GET", url, true);
        xmlHttp.send(null);
	}
	
	function stateChange3() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			 var str = xmlHttp.responseText;
			 str = str.trim();
			
			 strs = str.split("=");
			 
			 if (strs[0]=="" || strs[1]=="" || strs[2]=="") {
				    return;
			 } 
			
			 if (strs[2]=="false") {
				 document.getElementById("vehicleclass").innerHTML=
					 "<option value=''>Vehicle Class</option>"+
						"<option value='CAR/ JEEP'>CAR/ JEEP</option>"+
						"<option value='LCV'>LCV</option>"+
						"<option value='BUS/ TRUCK'>BUS/ TRUCK</option>"+ 
						"<option value='3 AXEL'>3 AXEL</option>"+
						"<option value='HCM/ MAV'>HCM/ MAV</option>"+
						"<option value='OVERSIZED'>OVERSIZED</option>";
						
						document.getElementById("vehicleclass").value=strs[1];
				 MyFunction();
			 } else {
				 var modal = document.getElementById('myModal');
					
				 // Get the modal
				if (document.getElementById('myModal').style.display != "block") {
					modal.style.display = "block";
				}
				 
			 }
			
		  } 
	}
	
	//for handling modal
   function f1(){
	   checkReturnStatus()
	   var modal = document.getElementById('myModal');
	   modal.style.display = "none";
	   document.getElementById("vnumber").value="";
		document.getElementById("vnumber").focus();
		document.getElementById("vnumber").select();
	} 
	
	function f2() {
		var modal = document.getElementById('myModal');
	    modal.style.display = "none";
	    
	    var vnumber = document.getElementById("vnumber").value;
		var laneId = document.getElementById("laneId").value;
		
		//if the vehicle number is not totally entered
		if (!(/^[a-zA-Z]{2}[0-9]{1,2}[a-zA-Z]{1,3}[0-9]{4}$/.test(vnumber))) {
			document.getElementById("vehicleclass").innerHTML=
				"<option value=''>Vehicle Class</option>"+
				"<option value='CAR/ JEEP'>CAR/ JEEP</option>"+
				"<option value='LCV'>LCV</option>"+
				"<option value='BUS/ TRUCK'>BUS/ TRUCK</option>"+ 
				"<option value='3 AXEL'>3 AXEL</option>"+
				"<option value='HCM/ MAV'>HCM/ MAV</option>"+
				"<option value='OVERSIZED'>OVERSIZED</option>";
			document.getElementById("vnumber").focus();
			document.getElementById("vnumber").select();
			myFunction();
			return;
		}

	} 
	
	function f3() {
		document.getElementById("badModal").style.display = "none";
		document.getElementById("goodModal").style.display = "none";
		document.getElementById("hdivpass").innerHTML = "";
		//for clearing vehicle class 
		document.getElementById("vehicleclass").innerHTML=
			"<option value=''>Vehicle Class</option>"+
			"<option value='CAR/ JEEP'>CAR/ JEEP</option>"+
			"<option value='LCV'>LCV</option>"+
			"<option value='BUS/ TRUCK'>BUS/ TRUCK</option>"+ 
			"<option value='3 AXEL'>3 AXEL</option>"+
			"<option value='HCM/ MAV'>HCM/ MAV</option>"+
			"<option value='OVERSIZED'>OVERSIZED</option>";
			
			//after getting all the values just replace them with initial values as the mozilla is not refreshing
			document.getElementById("vnumber").value = "";
			document.getElementById("vehicleclass").value = "";
			document.getElementById("tollAmt").innerHTML = "<option value=''>0.00</option>";
			document.getElementById("r2").checked = false;
			document.getElementById("cash").checked = true;
			
			//document.getElementById("vehicleclassid").value = "0";
			
			document.getElementById("ptype").value = "Cash";
			document.getElementById("consession").disabled=true;
			document.getElementById("btn").style.visibility = 'visible';
			document.getElementById("vnumber").focus();
			document.getElementById("vnumber").select();

	}
	
	function f4() {
		document.getElementById("exemptModal").style.display = "none";
		var vehs = document.getElementsByName("exemptType");
		var type= "";
		for (var i = 0; i < vehs.length; i++) {
			  if(vehs[i].checked) type=vehs[i].value;
		}
		if (type=="") {
			//check cash button and myFunction
			document.getElementById("cash").checked=true;
			MyFunction();
		} else {
			MyFunction();
		}
	}
	
	//when an exempt vehicle is selected
	function hideExemptModal() {
 		//slowly close
		 setTimeout(function () {
			 document.getElementById("exemptModal").style.display="none";
		  }, 500);
	}
	
	//for logging out
	function logout() {
		//alert("came into logout");
		window.location.href = "${pageContext.request.contextPath}/index/logout";
	}
	
	//check the return status
	var xmlHttp;
	function checkReturnStatus() {
		var vehicleNumber = document.getElementById("vnumber").value.trim();
		var laneId = document.getElementById("laneId").value;
		
		if (!(/^[a-zA-Z]{2}[0-9]{1,2}[a-zA-Z]{1,3}[0-9]{4}$/.test(vehicleNumber))) {
			alert("Please, enter a valid vehicle number");
			return;
		}
		
		//alert(str);
		if (typeof XMLHttpRequest != "undefined") {
            xmlHttp = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        } else if(xmlHttp==null) {
            alert("Browser does not support XMLHTTP Request");
            return;
        }
		
		var url = "${pageContext.request.contextPath}/tollTransaction/returnCheck?vehicleNumber="+vehicleNumber+"&laneId="+laneId;
		
		xmlHttp.onreadystatechange = stateChange2;
        xmlHttp.open("GET", url, true);
        xmlHttp.send(null);
	}
	
	function stateChange2() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			 var str = xmlHttp.responseText;
			 str = str.trim();
			 
			 if (str=="true") {
				 alert("Successfully updated");
			 } else {
				 alert("Sorry, not a valid vehicle for returning");
			 }
		 } 
	}
	
	//using ajax submit form
	var xmlHttp;
	function submitForm() {
		//get all the things to be needed to submit the form
		var vehicleNumber = document.getElementById("vnumber").value;
		var tollAmt = document.getElementById("tollAmt").value;
		var chx1 = document.getElementsByName("concessionType");
		var concessionType = "";
		for (var i=0; i<chx1.length; i++) {
            if(chx1[i].checked) concessionType = chx1[i].value;
        }
		var journeyType ="";
		var chx = document.getElementById("r2");
        if (chx.checked==true) journeyType="R";
        else journeyType = "S";
		var vcid = document.getElementById("vehicleclassid").value;
		var paymentMethod = document.getElementById("ptype").value;
		var concessionPercent = null;
		if (!document.getElementById("consession").disabled) {
			concessionPercent = document.getElementById("consession").value;
		} 
		var laneId = document.getElementById("laneId").value;
		var plazaId = document.getElementById("plazaId").value;

		//alert(str);
		if (typeof XMLHttpRequest != "undefined") {
            xmlHttp = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        } else if(xmlHttp==null) {
            alert("Browser does not support XMLHTTP Request");
            return;
        }
		
		//after getting all the values just replace them with initial values as the mozilla is not refreshing
		document.getElementById("vnumber").value = "";
		document.getElementById("vehicleclass").value = "";
		document.getElementById("tollAmt").innerHTML = "<option value=''>0.00</option>";
		document.getElementById("r2").checked = false;
		
		
		//document.getElementById("vehicleclassid").value = "0";
		
		//get exemt type
		var exemtVehs = document.getElementsByName("exemptType");
		var eType= "";
		for (var i = 0; i < exemtVehs.length; i++) {
			  if(exemtVehs[i].checked) eType=exemtVehs[i].value;
		}
		
		document.getElementById("ptype").value = "Cash";
		document.getElementById("consession").disabled=true;
		document.getElementById("btn").style.visibility = 'visible';
		document.getElementById("vnumber").focus();
		document.getElementById("vnumber").select();
		document.getElementById("eNone").checked=true;
	    
		
		
		
		var url = "${pageContext.request.contextPath}/tollTransaction/saveTollTransaction?";
		url = url + "vehicleNumber="+vehicleNumber+"&tollAmt="+tollAmt+"&concessionType="+concessionType;
		url = url + "&journeyType="+journeyType+"&vcid="+vcid+"&paymentMethod="+paymentMethod;
		url = url + "&concession="+concessionPercent+"&laneId="+laneId+"&plazaId="+plazaId;
		url = url + "&exemptType="+eType;
		
		xmlHttp.onreadystatechange = stateChange1;
        xmlHttp.open("GET", url, true);
        xmlHttp.send(null);
        
	}
	
	function stateChange1() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {

			 var str = xmlHttp.responseText;
			 //remove first and last characters of string as string is like {vehicleClassId=tollAmt}
			 str=str.trim();
			 
			 /*if (str== 'logged_out') {
				 window.location.href = "${pageContext.request.contextPath}/";
			 } else */if (str=='') {
				 //var laneId = document.getElementById("laneId").value;
				 //var plazaId = document.getElementById("plazaId").value;
				 //window.location = "${pageContext.request.contextPath}/tollTransaction/tollTransactionForm?laneId="+laneId+"&plazaId="+plazaId;
			 	 //location.reload();
			 	 alert("Shift ended")
			 	 logout();
			 	 //window.location.href = "${pageContext.request.contextPath}/index/logout";
			 } else {
				 //document.write(str);
				 document.getElementById("receipt").innerHTML = str;
				 
				 //mozilla is not getting previous values so we are doing this manually
				 var prevVno = document.getElementById("rvn").innerHTML.split(" ")[2];
				 var prevVc = document.getElementById("rvc").innerHTML.split(" ")[3];
				 var prevAmt = document.getElementById("ram").innerHTML.split(" ")[1];
				 var prevTime = document.getElementById("rti").innerHTML.split(" ")[2];
				 
				 if (prevVc=="CAR/") {
					 prevVc = "CAR/ JEEP";
				 } else if (prevVc=="BUS/") {
					 prevVc = "BUS/ TRUCK";
				 } else if (prevVc=="3") {
					 prevVc = "3 AXEL";
				 } else if (prevVc=="HCM/") {
					 prevVc = "HCM/ MAV";
				 }
				 
				 document.getElementById("prvn").innerHTML = "VehicleNumber: "+prevVno;
				 document.getElementById("prvc").innerHTML = "Vehicle Class: "+prevVc;
				 document.getElementById("pram").innerHTML = "Amount: <span style='font-weight:bold'>"+prevAmt+"</span>";
				 document.getElementById("prti").innerHTML = "Time:"+prevTime;
				 
				//if exempted or pass then make it 0 again
				 var cashtype ="";
				 var chx1 = document.getElementsByName("concessionType");
				 for (var i=0; i<chx1.length; i++) {
		            if(chx1[i].checked) cashtype = chx1[i].value;
		         } 
	
				 if (cashtype=="Cash") {
					 window.print();
					 //location.reload();
					 //after printing refresh remaining time
					 refreshRemainingTime();
				 } else {
				 	document.getElementById("cash").checked = true;
				 }
				 
				 //because mozilla is not refreshing
				 document.getElementById("exempted").disabled=false;
				 document.getElementById("pass").disabled=false;
				 
				 ///null the receipt so that he doesn't print it again and again
				 document.getElementById("receipt").innerHTML = "";
				 
				 //previously we user to reload the page but not now
				 //alert("Receipt Printed");
				 //var laneId = document.getElementById("laneId").value;
				 //var plazaId = document.getElementById("plazaId").value;
				 //window.location = "${pageContext.request.contextPath}/tollTransaction/tollTransactionForm?laneId="+laneId+"&plazaId="+plazaId;
			 	 //location.reload();
			 }
		 }
	}
	
	
	
	//following script is for getting rate from rate.jsp using ajax
	 function MyFunction() {
			//if concession is selected directly fill the amounts
			showAmount(document.getElementById("vehicleclass").value);
			
		}

		var xmlHttp;
		function showAmount(str) {
			
			var jType ="";
            var cashtype ="";
			var chx = document.getElementById("r2");
			
            if (chx.checked==true) jType="R";
            else jType = "S";
			
			var chx1 = document.getElementsByName("concessionType");
			for (var i=0; i<chx1.length; i++) {
                if(chx1[i].checked) cashtype = chx1[i].value;
            } 
			

			 if (jType=="" || jType==  null) {
	              var str = "<option value='"+0.00+"'>0.00</option>";
	              document.getElementById("tollAmt").innerHTML = str;
	              return;
	         }
			 
			 if(str=="" || str==null) {
				 var str = "<option value='"+0.00+"'>0.00</option>";
			     document.getElementById("tollAmt").innerHTML = str;
				 return;
			 }
			//alert(str);
			if (typeof XMLHttpRequest != "undefined") {
	            xmlHttp = new XMLHttpRequest();
	        } else if (window.ActiveXObject) {
	            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	        } else if(xmlHttp==null) {
	            alert("Browser does not support XMLHTTP Request");
	            return;
	        }
			 
			var plaza = document.getElementById("plazaId").value;
			var url="${pageContext.request.contextPath}/tollTransaction/rate";
			url += "?vehicle="+str+"&jType="+jType+"&plazaId="+plaza;
			
			xmlHttp.onreadystatechange = stateChange;
	        xmlHttp.open("GET", url, true);
	        xmlHttp.send(null);
		}
		
		function stateChange() {
			 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
				 
					//concession is on exempted is off and vice versa
				 	if (!document.getElementById("consession").disabled) {

				 		document.getElementById("r2").checked=false;
				 		document.getElementById("r2").disabled=true;
				 		//fill the amount based  on the vehicle class
				 		switch(document.getElementById("vehicleclass").value) {
				 
				 			case "CAR/ JEEP":
				 				document.getElementById("tollAmt").innerHTML = "<option value='25'>25.00</option>"
				 				break; 		
				 						
				 			case "LCV":
				 				document.getElementById("tollAmt").innerHTML = "<option value='35'>35.00</option>"
			 					break; 	
				 				
				 			case "BUS/ TRUCK":
				 				document.getElementById("tollAmt").innerHTML = "<option value='80'>80.00</option>"
				 				break; 		
				 						
				 			case "3 AXEL":
				 				document.getElementById("tollAmt").innerHTML = "<option value='85'>85.00</option>"
			 					break; 
				 		
				 			case "HCM/ MAV":
				 				document.getElementById("tollAmt").innerHTML = "<option value='120'>120.00</option>"
				 				break; 		
				 						
				 			case "OVERSIZED":
				 				document.getElementById("tollAmt").innerHTML = "<option value='150'>150.00</option>"
			 					break; 
				 			
				 		}
				 		return;
				 	} else {
			 			document.getElementById("r2").disabled=false;
				 	}
				 
				 
				 var str = xmlHttp.responseText;
				 //remove first and last characters of string as string is like {vehicleClassId=tollAmt}
				 str=str.trim();
				 /* if (str== 'logged_out') {
					 window.location.href = "${pageContext.request.contextPath}/";
				 } */
				 str = str.slice(1,-1);
				 //split when there us =
				 str=str.trim();
				
				 
				 strs = str.split("=");
				 //if such vehicle class does not exist
				 
				 
				 if (strs[0]=="" || strs==null ||strs.length < 2) {
					 document.getElementById("vnumber").value="";
					 var str = "<option value=''>0.00</option>";
		             document.getElementById("tollAmt").innerHTML = str;
		             return;
				 }
				 document.getElementById("vehicleclassid").value=strs[0];
					
				 
				 //finally add consession before adding amount
				 var amt = strs[1];
				 //previously we used this for concession
				 /* if(!document.getElementById("consession").disabled) {
					 if(document.getElementById("consession").value!=null && document.getElementById("consession").value!="") {
						 amt = strs[1]-(strs[1]*document.getElementById("consession").value/100);
					 }
				 } */
				 document.getElementById("tollAmt").innerHTML = "<option value='"+amt+"'>"+amt+"</option>";
			 
				 //if exempted or pass then make it 0 again
				 var cashtype ="";
				 var chx1 = document.getElementsByName("concessionType");
				 for (var i=0; i<chx1.length; i++) {
		            if(chx1[i].checked) cashtype = chx1[i].value;
		         } 
				 
				 if (cashtype!="Cash") {
					 var str = "<option value='"+0.00+"'>0.00</option>";
					 document.getElementById("tollAmt").innerHTML = str;
				 }
				 
			 }
		}
	</script>
</head>
					
<body onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="">
	<input id="shiftEndTime" value="${shiftEndTime}" hidden/>
	<!--header-->
	<div class="container no-padding hidden-print">
		<div class="row headerDiv">
				<div class="col-sm-12">
					<div class="row">
						<div class="col-sm-3">
							<a href="javascript:window.location.reload();"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"></a>
						</div>
						<div class="col-sm-5">
							<!--Write something here to get it in middle of the header-->
							<div></div>
						</div>
						<div class="col-sm-4" style="position:relative;top: 25px;">
							 <!-- Contact: 9999999999 --> 
							<input type="button" value="Hold" class="signout" style="margin-left: 200px" onclick="window.location.href='${pageContext.request.contextPath}/index/hold';"> 
						</div>
					</div>
				</div>				
			</div>
			
			<!-- video -->
			<div class="row"> 
				<div class="col-lg-6 videoColumn">
					<div class="row">
						<script src='${pageContext.request.contextPath}/resources/js/sldp-v2.3.2.min.js' type='text/javascript'></script>
						<div id='sldp_player_wrapper' style="position: relative; left: 2%; top: 5px"></div>
						<script>
						  document.addEventListener('DOMContentLoaded', initPlayer);
						  var sldpPlayer;
						  function initPlayer(){
						    sldpPlayer = SLDP.init({
						      container: 'sldp_player_wrapper',
						      stream_url: "${camAddress}",
						      height: 500,
						      width: 640,
						      autoplay: true
						    });
						  
						  };
						  
						  function removePlayer(){
						    sldpPlayer.destroy()
						  }
						</script>

						<!-- <div class="col-lg-12" style="background-color: white; min-height: 100px; max-width: 97%; position:relative; left: 1%; top: 5px">
							Signal buttons come here
						</div>
						<div class="col-lg-12" style="background-color: magenta; color: white; min-height: 50px; max-width: 97%; position:relative; left: 1%; top: 5px; border-radius: 0px 0px 10px 10px">
							Record and Snap shot
						</div> -->
					</div>
				</div>
			
			
			<!--Buttons and rest all-->
			<div class="col-lg-6 buttonsColumn">
			
				<div class="row laneDetails">
					<!--lane details columns-->
					<div class="col-sm-6">
						<div style="margin-top: 10px; margin-bottom: 10px">PlazaName: ${plazaName}</div>
						<div style="margin-top: 10px; margin-bottom: 10px">OperatorName: ${userFromSession.userFirstName} ${userFromSession.userLastName}</div>
					</div>
					<div class="col-sm-6">
						<div style="margin-top: 10px; margin-bottom: 10px">Shift: ${shift.shiftDesc}  Lane: ${laneName}</div>
						<div style="margin-top: 10px; margin-bottom: 10px">Time: <span id="demo" style="text-align: left;"></span></div>

						<span id="lastMinute" style="position: absolute; left: 200px; top: 0px; display:none">
						<img src="${pageContext.request.contextPath}/resources/images/last_minute.png" height="70" width="73"/>
						<span id="timer" style="color:#ffffff; position: absolute; left: 22px; top: 13px; font-size: 2em"></span>
						</span>						
					</div>
				</div>	
				
				<form action="saveTollTransaction" method="POST" id="form1">
					<div class="row formandbuttons">
						<span>${shiftNotFoundError}</span>
						<input name="vcid" value="0" id="vehicleclassid" hidden/>
						<input name="laneId" value="${laneId}" id="laneId" hidden/>
						
						<%-- These two are for generating ticket_code--%>
						<input id="laneCode" value="${laneName}" hidden/>
						<input id="ticketCode" name="ticketCode" hidden/>
						
						<input name="plazaId" value="${plazaId}" id="plazaId" hidden/>
						<div class="col-sm-4">
							 <input id="cash" class="cashType" type="radio" name="concessionType" value="Cash"  checked onclick="checkCash()"> <label for="cash">Cash (F8)</label>
						</div>
						
						<div class="col-sm-4">
							<input id="exempted" class="cashType" type="radio" name="concessionType" value="Exempted" onclick="field_fill_func('Exempt')"> <label for="exempted">Exempted (F9)</label>
				        </div>
				        
				         <div class="col-sm-4">
				             <input id="pass" class="cashType" type="radio" name="concessionType" value="Pass" onclick="checkPass()"> <label for="pass">Pass (F10)</label>
				        </div>
	
						<div class="col-sm-5">
							<div style="margin-left: 20px">
								<input class="text-line"  name="vehicleNumber" id="vnumber" placeholder="Vehicle Number" />
								<div>
									<span id = "sp_vnum" class="error">Please enter a valid vehicle number</span>
								</div>
							</div>
						</div>
	
						<div class="col-sm-5">
							<div style="margin-left: 45px">
								<select onchange="showAmount(this.value)" class="text-line"  name="vehicleClassId" id="vehicleclass" class="inp">
									<%-- <option value="">Vehicle Class</option>
									<c:forEach var="tempVehicleClass" items="${uniqueVehicleClasses}">
									 <option value="${tempVehicleClass.key}">${tempVehicleClass.key}</option>
									</c:forEach> --%>
									
									<option value=''>Vehicle Class</option>
									<option value='CAR/ JEEP'>CAR/ JEEP</option>
									<option value='LCV'>LCV</option>
									<option value='BUS/ TRUCK'>BUS/ TRUCK</option> 
									<option value='3 AXEL'>3 AXEL</option>
									<option value='HCM/ MAV'>HCM/ MAV</option>
									<option value='OVERSIZED'>OVERSIZED</option>
								</select>
								<div>
									<span id = "sp_vclass" class="error">Please select a vehicle class</span>
								</div>
							</div>
						</div>
						
						<!--converted radio button to check box-->
						<div class="col-sm-12 checkboxdiv" style="margin-top: 20px">
							<!-- <input id = "r1" class="inp" type="radio" class="inp" name="journeyType" value="S" onclick="MyFunction()" checked> Single</input> -->
						    <label for="r2">Return Journey</label>
						    <input id = "r2" type="checkbox" name="journeyType" value="R" onclick="MyFunction()" >
	
						   <label style="padding-right: 10px; margin-left: 120px">Consession : </label>
							<select class="text-line" id="consession" name="concession" onchange="changeDiscount()" style="margin-right: 20px; position: relative; top: -2px; left: -10px" disabled>
								<option value="yes">Yes</option>
							<%--	<option value="25">25%</option>
								<option value="75">75%</option>
								<option value="100">100%</option>
								<option value="10">10%</option>
								<option value="15">15%</option>
								<option value="20">20%</option> --%>
							</select>
						</div>
	
						<div class="col-sm-6 checkboxdiv" style="position: relative; top:20px;">
							<label>Payment Method: </label>
							<select id="ptype" class="text-line" name="paymentMethod" style="position: relative; top:-2px;">
								<option value="Cash">Cash</option>
								<option value="POS">POS</option>
								<option value="Wallet">Wallet</option>
								<option value="RFID">RFID</option>
							</select>
	
						</div>
	
						<div class="col-sm-6 checkboxdiv" style="position: relative; top:20px;">
							<label for="tollAmt">AMOUNT:</label>
							<select class="text-line"  name="tollAmt" id="tollAmt" class="inp"  style="position: relative; top:-2px;">		<option value='0.00'>0.00</option> 
							</select>
							<span class="error" id="sp_tollAmt"><br>Please Configure TollPlaza for Todays date</span>
						</div>
	
						<div class="row" style="margin-top: 215px;">
	
							<div class="col-sm-3 col-sm-offset-3">		 
					            <input class="inp1" onclick="cancelButton()" type="button" value = "Cancel" style="background-color: #6f7070"/>
							</div>
							
							<div class="col-sm-3">		 
					            <input id="btn" class="inp1" type="button" value = "Submit" onclick = "submit1()"/>
							</div>
	
							<div class="col-sm-6 col-sm-offset-3 underline">
								_______________________________________
							</div>
						</div>
	
						<div class="col-sm-3">		 
				            <input id="F1" class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = "F1 CAR/ JEEP"/>
						</div>
	
						<div class="col-sm-3">
							<input id="F2" class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = "F2 LCV">
				        </div>
				        
				         <div class="col-sm-3">
				             <input id="F3" class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = "F3 BUS/ TRUCK"/>
				        </div>
	
				        <div class="col-sm-3">
				            <input id="F4" class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = "F4 3 AXEL"/>
				        </div>
	
				        <div class="col-sm-3">		 
				            <input id="F5" class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = "F5 HCM/ MAV"/>
						</div>
	
						<div class="col-sm-3">
							<input id="F6" class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = "F6 OVERSIZED">
				        </div>
				        
				         <div class="col-sm-3">
				             <input id="F7" class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = "F7 AUTO"/>
				        </div>
	
				        <div class="col-sm-3">		 
				            <input id="S" class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = "S SINGLE"/>
						</div>
	
						<div class="col-sm-3">
							<input id="C" class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = "C CONCESSION"/>
				        </div>
				        
				         <div class="col-sm-3">
				             <input id="." class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = ". CLEAR"/>
				        </div>
	
				        <div class="col-sm-3">
				            <input id="R" class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = "R RETURN"/>
				        </div>
	
				        <div class="col-sm-3">
				            <input id="SP" class="inp1" onclick="field_fill_func(this.value)" class="button" type="button" value = "SP RET CHECK"/>
				        </div>
					</div>	
				</form>
				
				<div class="row laneDetails">
					<!--lane details columns-->
					<div class="col-sm-6">
						<div style="margin-top: 10px; margin-bottom: 10px" id="prti">Ticket Id: ${lastTransaction.ticketCode}</div>
						<div style="margin-top: 10px; margin-bottom: 10px" id="prvn">Vehicle Number: ${lastTransaction.vehicleNumber}</div>
					</div>

					<div class="col-sm-6">
						<div style="margin-top: 10px; margin-bottom: 10px" id="pram">Amount: <span style="font-weight:bold">${lastTransaction.tollAmt}</span></div>
						<div style="margin-top: 10px; margin-bottom: 10px" id="prvc">Vehicle Class: ${lastTransactionVehicleClass}</div>
					</div>

				</div>	
			</div>
			
		</div>
		
		<div class="row footer">
			<div class="col-sm-4">
				
			</div>
				<div class="col-sm-4">
					<div style="position: relative; top:20px;">Copy rights 2017 @ TollSecure India Pvt. Ltd</div>
				</div>
			<div class="col-sm-4">
				
			</div>
		</div>
	</div>
	
 	<div class="visible-print" id="receipt">
		
	</div> 
	
	<input hidden id="remainingTime" name="remainingTime" value="${remainingTime}"/>
	
	<script src="${pageContext.request.contextPath}/resources/js/get_time.js"></script>
	<script type="text/javascript">
	document.getElementById("vnumber").focus();
	document.getElementById("vnumber").select();
	</script>
	<!-- The Modal -->
	<div id="myModal" class="modal" style="">
	
	  <!-- Modal content -->
	  <div class="modal-content" style="position:relative; top:25%;left: 15%; max-width: 30%; margin-left: 25%; background-color: #b9b9b9">
	  	<!-- uncomment this if you want cross on the top right -->
	    <!-- <span class="close">&times;</span> -->
	    <p>The vehicle number is valid for return journey. Do you want to confirm?</p>
	    	
	    <input type="button" class="inp1" value="(n) No" id="no" onclick="f2()" style="background-color: #474747"/>
		<input type="button" class="inp1" value="(enter) Yes" id="yes" onclick="f1()"/>
	  </div>
	
	</div>
	
	<div id="hdivpass" hidden>
	</div>
	<!-- The good Pass Modal -->
	<div id="goodModal" class="modal" style="">
		<div class="modal-content" style="position:relative; top:25%;left: 15%; max-width: 30%; margin-left: 25%; background-color: #b9b9b9">
		  	<div id="gmsg"></div>
		    	
		    <input type="button" class="inp1" value="(n) No" id="gno" onclick="f3()" style="background-color: #474747"/>
			<input type="button" class="inp1" value="(enter) Yes" id="gyes" onclick="submit1()"/>
		</div>
	</div>
	
	<!-- The bad Pass Modal -->
	<div id="badModal" class="modal" style="">
		<div class="modal-content" style="position:relative; top:25%;left: 15%; max-width: 30%; margin-left: 25%; background-color: #b9b9b9">
		  	<div id="bmsg"></div>
			<input type="button" class="inp1" value="(enter) OK" id="byes" onclick="f3()" style="margin-left: 150px"/>
		</div>
	</div>
	
	<!-- Modal for exempt selection -->
	<div id="exemptModal" class="modal" style="">
		<div id="exempt_inner" class="modal-content" style="position:relative; top:0%;left: 15%; max-width: 20%; margin-left: 25%; background-color: #b9b9b9">
		  	<div style="margin-left:9%">
		  		<a><input type="radio" id="eNone" class="exeVeh" name="exemptType" value="" checked hidden></a><!--None (O)<br>-->
		  		<input type="radio" id="police" class="exeVeh" name="exemptType" onclick="hideExemptModal()" value="Police"> <label for="police">Police (P)</label><br>
		  		<input type="radio" id="nhai" class="exeVeh" name="exemptType" onclick="hideExemptModal()" value="NHAI executives"><label for="nhai">NHAI executives (N)</label><br>
  				<input type="radio" id="ambulance" class="exeVeh" name="exemptType" onclick="hideExemptModal()" value="Ambulance"> <label for="ambulance">Ambulance (A)</label><br>
  				<input type="radio" id="fire" class="exeVeh" name="exemptType" onclick="hideExemptModal()" value="Fire Engine"> <label for="fire">Fire brigade (F)</label><br>
  				<input type="radio" id="defence" class="exeVeh" name="exemptType" onclick="hideExemptModal()" value="Defence"> <label for="defence">Defence (D)</label><br>
  				<input type="radio" id="postal" class="exeVeh" name="exemptType" onclick="hideExemptModal()" value="Postal"><label for="postal"> Postal (T)</label><br>
  				<input type="radio" id="govt" class="exeVeh" name="exemptType" onclick="hideExemptModal()" value="Govt. Executives"> <label for="govt">Govt. Executive (G)</label><br>
  				<input type="radio" id="funeral" class="exeVeh" name="exemptType" onclick="hideExemptModal()" value="Funeral van"><label for="funeral">Funeral van (V)</label><br>
  				<input type="radio" id="crpf" class="exeVeh" name="exemptType" onclick="hideExemptModal()" value="CRPF"><label for="crpf">CRPF (C)</label><br>
		  	</div>
			<!-- <input type="button" class="inp1" value="(enter) OK" id="eyes" onclick="f4()" style="margin-left: 150px"/> -->
		</div>
	</div>
	
	
</body>
</html>