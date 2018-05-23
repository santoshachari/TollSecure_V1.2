<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<head>
	<title>Vehicle Search</title>
	
	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/cancel_ticket1.css">

<style>
	::-webkit-input-placeholder { /* Chrome */
	  color: #D3D3D3;
	}
	:-ms-input-placeholder { /* IE 10+ */
	  color: #D3D3D3;
	}
	::-moz-placeholder { /* Firefox 19+ */
	  color: #D3D3D3;
	  opacity: 1;
	}
	:-moz-placeholder { /* Firefox 4 - 18 */
	  color: #D3D3D3;
	  opacity: 1;
	}
</style>

<style>
	.loader {
    border: 16px solid #f3f3f3; /* Light grey */
    border-top: 16px solid #ee9620; /* Gold */
    border-radius: 50%;
    width: 120px;
    height: 120px;
    animation: spin 2s linear infinite;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}
</style>

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


	<script>
		//prevents right click
		//document.addEventListener('contextmenu', event = event.preventDefault());
		
		if (localStorage.status==undefined) logout(); //logout user incase status is undefined
		
		var g_transactionId = null;
		
		function checkTransactionAndCancel(transactionId) {
			g_transactionId = transactionId;
			var url = "${pageContext.request.contextPath}/tollTransaction/checkIfCashUpIsDone?transactionId="+transactionId;
			
			if (typeof XMLHttpRequest != "undefined") {
		        xmlHttp = new XMLHttpRequest();
		    } else if (window.ActiveXObject) {
		        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		    } else if(xmlHttp==null) {
		        alert("Browser does not support XMLHTTP Request");
		        return;
		    }
			
			xmlHttp.onreadystatechange = stateChange1;
	    	xmlHttp.open("GET", url, true);
			xmlHttp.send(null);  
		}
		
		function stateChange1() {
			 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
				 var str = xmlHttp.responseText;
				 str = str.trim().split("-");
				 
				 if (str[0] == "false") {
					 //show the modal that cashup declaration is already done for this toll transaction
					 var modal = document.getElementById('myModal');
					 modal.style.display = "block";
				 } else {
					 if (str == "logged_out") logout();
					 else window.location = "${pageContext.request.contextPath}/tollTransaction/cancelTicket?transactionId="+g_transactionId;
				 }
			 } 
		}
		
		function f1() {
			var modal = document.getElementById('myModal');
			 modal.style.display = "none";
		}
		
		
		function getReport() {
			
			if (document.getElementById("stTime").value=="" && document.getElementById("edTime").value=="" && document.getElementById("from").value==""
				&& document.getElementById("vnumber").value == "" && document.getElementById("laneId").value == "" && document.getElementById("vehicleClass").value == "" ) return;
			
			var from_error = false;
			
			var from = document.getElementById("from").value;
			//we removed this later
			if (from == "") {
				document.getElementById("sp_from").removeAttribute("class");
				document.getElementById("sp_from").setAttribute("class", "error_show");
				from_error = true;
				
			} else {
				document.getElementById("sp_from").removeAttribute("class");
				document.getElementById("sp_from").setAttribute("class", "error");
				from_error = false;
			} 
			
			var vnumber = document.getElementById("vnumber").value.trim();
			var vnumerror = false;
			if (vnumber!='') {
				//regular expression for vehicle number
				 if (!(/^[a-zA-Z]{2}[0-9]{1,2}[a-zA-Z]{1,3}[0-9]{4}$/.test(vnumber))) {
					 document.getElementById("sp_vnum").removeAttribute("class");
					 document.getElementById("sp_vnum").setAttribute("class", "error_show");
					 vnumerror = true;
				 } else {
					 document.getElementById("sp_vnum").removeAttribute("class");
					 document.getElementById("sp_vnum").setAttribute("class", "error");
					 vnumerror = false;
				 }
			}
			
			var stTime = document.getElementById("stTime").value;
			var stTimeErr = false;
			//we removed this later
			/* if (stTime=='') {
				stTimeErr =true;
				document.getElementById("sp_stTime").removeAttribute("class");
				document.getElementById("sp_stTime").setAttribute("class", "error_show");
			} else {
				stTimeErr =false;
				document.getElementById("sp_stTime").removeAttribute("class");
				document.getElementById("sp_stTime").setAttribute("class", "error");
			} */
			
			var edTime = document.getElementById("edTime").value;
			var edTimeErr = false;
			//we removed this later
			/* if (edTime=='') {
				edTimeErr =true;
				document.getElementById("sp_edTime").removeAttribute("class");
				document.getElementById("sp_edTime").setAttribute("class", "error_show");
			} else {
				edTimeErr =false;
				document.getElementById("sp_edTime").removeAttribute("class");
				document.getElementById("sp_edTime").setAttribute("class", "error");
			} */
			
			var dteErr = false;
			if (Date.parse('01/01/2011 '+stTime+':00') > Date.parse('01/01/2011 '+edTime+':00')) {
				dteErr = true;
				document.getElementById("sp_timeErr").removeAttribute("class");
				document.getElementById("sp_timeErr").setAttribute("class", "error_show");
			} else {
				dteErr = false;
				document.getElementById("sp_timeErr").removeAttribute("class");
				document.getElementById("sp_timeErr").setAttribute("class", "error");
			}
			
			if (!from_error && ! vnumerror & !stTimeErr && !edTimeErr && !dteErr) {
				
				if (typeof XMLHttpRequest != "undefined") {
			        xmlHttp = new XMLHttpRequest();
			    } else if (window.ActiveXObject) {
			        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			    } else if(xmlHttp==null) {
			        alert("Browser does not support XMLHTTP Request");
			        return;
			    }
				
				//get all the variables needed
				var laneId = document.getElementById("laneId").value;
				var vehicleClass = document.getElementById("vehicleClass").value;
				
				//create an url
				var url=""; 
				
				 if(/Mobile/i.test(navigator.userAgent) && !/ipad/i.test(navigator.userAgent) ){ //it is a mobile
					 	url="${pageContext.request.contextPath}/tollTransaction/getVehicleTranactionsForMobile";
						url = url + "?from="+from+"&startTime="+stTime+"&endTime="+edTime+"&laneId="+laneId+"&vnumber="+vnumber;
						url = url + "&vahicleClass="+vehicleClass;
				    } else {//it is a desktop
						url="${pageContext.request.contextPath}/tollTransaction/getVehicleTranactions";
						url = url + "?from="+from+"&startTime="+stTime+"&endTime="+edTime+"&laneId="+laneId+"&vnumber="+vnumber;
						url = url + "&vahicleClass="+vehicleClass;
				    }

				
				xmlHttp.onreadystatechange = stateChange;
		    	xmlHttp.open("GET", url, true);
				xmlHttp.send(null);  

			}
		}
		function stateChange() {
			 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
				 var str = xmlHttp.responseText;
				 str = str.trim();
				 
				 //if logged_out the log out
				 if (str == "logged_out") {
					 logout();
				 }
				 
				 document.getElementById("loading").style.display = "none";
				 document.getElementById("demo").innerHTML = str;
				 document.getElementById("sdemo").innerHTML = str;
				 
			 } else {
				 document.getElementById("loading").style.display = "block";
				 document.getElementById("demo").innerHTML = "";
				 document.getElementById("sdemo").innerHTML = "";
			 }
		}
		
		//for logging out
		function logout() {
			localStorage.clear();
			window.location.href = "${pageContext.request.contextPath}/index/logout";
		}
		
	</script>
</head>

<input hidden id="genBy" value="${userFromSession.userFirstName}"/>
<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
	<div class="container nopadding">

		<!-- header -->
		<div class="row hidden-xs">
		
		
			<div class="col-sm-12 header" style=" min-width: 102%;">
				<div class="row">
					<div class="col-xs-2 hidden-xs" style="margin-left: 1%">
						<a href="${pageContext.request.contextPath}/" style="z-index:100"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
					</div>
					<div class="col-xs-5">				
							<h4 class="heading " style="font-size: 0.9em">Vehicle Search</h4>
						<div></div>
					</div>
					<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
						<span style="margin-right: 10px;"><!--Contact: 9999999999 --></span>
						<input type="button" value="Signout" class="signout hidden-xs" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
					</div>
				</div>
			</div>
		</div>
				
		<div class="row visible-xs">
			<div class="col-xs-12" style="background-color: #ee9620">
				<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' style="margin-left: 15%; margin-bottom: 10%" width="60%"/>
				<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28" style="margin-left: 15%" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
				<br><span style="margin-left:25%">Vehicle Search</span>
			</div>
		</div>
		
		<div class="visible-xs container" >

		
		</div>	


		<div class="row">
						

				
			<div class="col-sm-12 black_header hidden-print">

				<div class="col-md-1 hidden-xs">
					<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=2' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0; ">Back</a>
				</div>

				
				<span>
					<!-- <span class="error" id="sp_from">Please, Enter Date</span> -->
					<span class="error" id="sp_stTime">Please, Enter start time</span>
					<span class="error" id="sp_edTime">Please, Enter end time</span>
					<span class="error" id="sp_timeErr">End time should be greater than start time</span>
				</span>
				
				
				<div class="col-md-11" style=" ">

					
					<div class="row">
							
						<div class="col-md-12 visible-xs">
							<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=1' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -50%"> </a>
						</div>
						
															
						<div class="visible-xs" style="margin-top: 10%">
					
						</div>
				
						<div class="col-md-3" style="margin-top: 10px">
							<label>Date.</label>
							<input type="date" id="from" name="from"  max="${today}" style="background-color: #000;background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"/>
							<span class="error" id="sp_from">Please, Enter Date</span>
						</div>
						
						<div class="col-md-4">
							<label >START TIME: </label>
							<input id="stTime" type="time" class="head_inp"/>
						</div>
						
						<div class="col-md-4">
							<label >END TIME: </label>
							<input id="edTime" type="time" class="head_inp"/>
						</div>
						
					</div>
					
					<div class="row">
						
						<div class="col-md-3">
							<label>LANE: </label>
							<select name="laneId" id="laneId" class="head_inp" style="margin-top:15px;">
									<option value=''>ALL</option>
								<c:forEach items="${allLanes}" var="tempLane">
									<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-md-4">
							<div style="margin-top: -3%">
								<label >VEHICLE NUMBER: </label>
								<input type="text" id="vnumber" class="head_inp" placeholder="optional"/>
								<span id="sp_vnum" class="error">Please, enter a valid vehicle number</span>
							</div>
						</div>
						
						<div class="col-md-4">
							<label>VEHICLE CLASS: </label>
							<select name="vehicleClass" id="vehicleClass" class="head_inp">
								<option value="">Optional</option>
								<c:forEach var="tempVehicleClass" items="${uniqueVehicleClasses}">
								 <option value="${tempVehicleClass.key}">${tempVehicleClass.key}</option>
								</c:forEach>
							</select>
						</div>
						
					</div>
					<!-- <input id="datetime" type="datetime-local" style="background: black" onchange="alert(this.value)"> -->
					
					<input type="button" value="Submit" onClick="getReport()" class="inp1" style="margin-top:15px; margin-left: -100px"/>
					
				</div>
			</div>
		</div>

		<div class="row" style="min-height: 450px">
			<div class="loader" style="display : none; margin-left:50%" id="loading"></div>
			<div id="demo" class="col-md-10 maincontent hidden-xs" style="margin-left: 20%">
				
			</div>
			
			<div id="sdemo" class="col-md-10 maincontent visible-xs" style="margin-left: 0px">
				
			</div>
		</div>

		<!-- footer -->
			<div class="row hidden-print" >
				<div class="col-sm-12 footer" style="position: relative; left: -10%; min-width: 110%; min-height: 49.2px">
						<div class="col-sm-5">
						
						</div>
								<div class="col-sm-4">
								<div style="position: relative; top:20px;">Copy rights 2017 @ TollSecure India Pvt. Ltd</div>
							</div>
						<div class="col-sm-3">
							
						</div>			
					</div>
			</div>
		</div>	
		
		<%-- hidden content --%>
		<div id="hidden_content" style="display: none">

		</div>
		
			<!-- The Modal -->
		<div id="myModal" class="modal" style="">
		
		  <!-- Modal content -->
		  <div class="modal-content" style="position:relative; top:25%;left: -50px; max-width: 300px; margin-left: 30%; background-color: #b9b9b9">
		  	<!-- uncomment this if you want cross on the top right -->
		    <!-- <span class="close">&times;</span> -->
		    <p>Sorry! Cashup is done for this Transaction.</p>
		    	
		    <div class="row">				
				<div class="col-xs-5" style="margin-left: 30%">
					<input type="button" class="inp1" value="OK" id="yes" onclick="f1()" style="max-width: 80%"/>
				</div>
				
			</div>
		  </div>
		
		</div>
		
		<script>
		//this happens if back button is pressed in vehicle details
		if (document.getElementById("stTime").value!="" || document.getElementById("edTime").value!="" || document.getElementById("from").value!=""
				|| document.getElementById("vnumber").value != "" || document.getElementById("laneId").value != "" || document.getElementById("vehicleClass").value != "" ) {
			getReport();
		} 
		</script>
</body>