<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<head>
<title>Generate Pass</title>
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/configure_pass.css">
<!-- modal style -->
	<style>
/*table*/

@media only screen and (max-width: 700px) { 
	  td {
	    width: calc(100% / 6); padding: 1px;
	    overflow: hidden; /*background: #bababa;*/
	    font-size: 2vw; /* <---- the viewport relative font-size */
	}	
	
	th {
		width: calc(100% / 6); padding: 1px;
	    overflow: hidden; /*background: #bababa;*/
	    font-size: 2vw; /* <---- the viewport relative font-size */
	}
}

	
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
	
	if (localStorage.status==undefined) window.location.href='${pageContext.request.contextPath}/index/logout'; //logout user incase status is undefined
	
	//cancelling pass through link
	function autoShowModal(passId) {
		document.getElementById("passIdForCancel").value = passId;
		document.getElementById("remarks").value = "";
		showModal();
	}
	
	//cancelling pass
	function showModal() {
		var modal = document.getElementById('myModal');
	    modal.style.display = "block";
	}
	
	//cancelling pass
	function f1() {
		
		var passIdForCancel = document.getElementById("passIdForCancel").value;
		var remarks = document.getElementById("remarks").value;
		
		if (passIdForCancel=="" && remarks=="") {
			alert ("Please enter Pass Id and Remarks");
		} else if (passIdForCancel=="") {
			alert("Please enter Pass ID"); 
		} else if (remarks=="") {
			alert("Please enter remarks");
		} else {
			if (typeof XMLHttpRequest != "undefined") {
		           xmlHttp = new XMLHttpRequest();
			    } else if (window.ActiveXObject) {
			        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			    } else if(xmlHttp==null) {
			       alert("Browser does not support XMLHTTP Request");
			       return;
			    }
				
				var url = "${pageContext.request.contextPath}/pass/cancelPass";
				url = url+"?remarks="+remarks+"&passId="+passIdForCancel;

				 xmlHttp.onreadystatechange = stateChange3;
		         xmlHttp.open("GET", url, true);
		         xmlHttp.send(null);
		}

	}
	
	function stateChange3() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			document.getElementById('myModal').style.display="none";
			var str = xmlHttp.responseText;
			str = str.trim();
			document.getElementById("message").innerHTML = str;
			document.getElementById("smessage").innerHTML = str;
			
		    
		    //if mobile the call ssearchPass
		    if (document.getElementById("svehnum").value != "") ssearchPass();
		    else searchPass();
		    
		 } else {
			 document.getElementById('myModal').style.display="block";
		 }
	}
	
	///search for pass mobile
	function ssearchPass() {
		document.getElementById("vehnum").value=document.getElementById("svehnum").value;
		searchPass();
	}
	
	//for searching pass
	function searchPass() {
		var vehicleNumber = document.getElementById("vehnum").value;
		if (typeof XMLHttpRequest != "undefined") {
	           xmlHttp = new XMLHttpRequest();
		    } else if (window.ActiveXObject) {
		        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		    } else if(xmlHttp==null) {
		       alert("Browser does not support XMLHTTP Request");
		       return;
		    }
			
			var url = "${pageContext.request.contextPath}/pass/passTable";
			url = url + "?vehicleNumber="+vehicleNumber;

			xmlHttp.onreadystatechange = stateChange2;
	        xmlHttp.open("GET", url, true);
	        xmlHttp.send(null);
		}
		
		function stateChange2() {
			 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
	
				var str = xmlHttp.responseText;
				str = str.trim();
				
				document.getElementById("table").innerHTML = str;
				document.getElementById("stable").innerHTML = str;
			 }
		}
	//for printing receipt
		var xmlHttp;
		function printReceipt(plazaId, vehicleNumber, newAmt, passType, vehicleClass, effectiveTo, curDate2, validity) {
			if (typeof XMLHttpRequest != "undefined") {
				
				
				
		           xmlHttp = new XMLHttpRequest();
			    } else if (window.ActiveXObject) {
			        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			    } else if(xmlHttp==null) {
			       alert("Browser does not support XMLHTTP Request");
			       return;
			    }
				
				var url = "${pageContext.request.contextPath}/pass/printReceipt";
				url = url + "?plazaId="+plazaId+"&effectiveTo="+effectiveTo+"&vehicleClass="+vehicleClass+"&passType="+passType;
				url = url + "&vehicleNumber="+vehicleNumber+"&amt="+newAmt+"&validity="+validity;

				xmlHttp.onreadystatechange = stateChange1;
		        xmlHttp.open("GET", url, true);
		        xmlHttp.send(null);
			}
			
			function stateChange1() {
				 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
		
					var str = xmlHttp.responseText;
					str = str.trim();
					document.getElementById("hidden").innerHTML = str;
					window.print();
					location.reload();
				 }
			}
	
		//for getting amount
		var xmlHttp;
		function nowShow(plazaId, effectiveTo, vehicleClass, passType) {
			
			if(passType=="MONTHLY") {
				document.getElementById("validiy").disabled = false;
				document.getElementById("validiy").value="50";
				
				document.getElementById("svalidiy").disabled = false;
				document.getElementById("svalidiy").value="50";
			} else { //it is local
				document.getElementById("validiy").value="";
				document.getElementById("validiy").disabled = true;
				
				document.getElementById("svalidiy").value="";
				document.getElementById("svalidiy").disabled = true;
			}
			
			if (typeof XMLHttpRequest != "undefined") {
	           xmlHttp = new XMLHttpRequest();
		    } else if (window.ActiveXObject) {
		        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		    } else if(xmlHttp==null) {
		       alert("Browser does not support XMLHTTP Request");
		       return;
		    }
			
			var url = "${pageContext.request.contextPath}/pass/rate";
			url = url+"?plazaId="+plazaId+"&effectiveTo="+effectiveTo+"&vehicleClass="+vehicleClass+"&passType="+passType;
		
			 xmlHttp.onreadystatechange = stateChange;
	         xmlHttp.open("GET", url, true);
	         xmlHttp.send(null);
		}
		
		function stateChange() {
			 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
	
				var str = xmlHttp.responseText;
				str = str.trim();
				
				if(str=="") {
					document.getElementById("amt").innerHTML = "<option value=''></option>";
					document.getElementById("samt").innerHTML = "<option value=''></option>";
					return;
				}
				
				document.getElementById("amt").innerHTML = "<option value='"+str+"'>"+str+"</option>";
				document.getElementById("samt").innerHTML = "<option value='"+str+"'>"+str+"</option>";
			 }
		}
		

	</script>
</head>

<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
	<div class="container nopadding hidden-print hidden-xs">

		<!-- header -->
		<div class="row">
			<div class="col-sm-12 header" style=" min-width: 102%;">
				<div class="row">
					<div class="col-xs-2" style="margin-left: 1%">
						<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
					</div>
					<div class="col-xs-5">
							<h4 class="heading" style="font-size: 0.9em">Generate Pass</h4>
						<div></div>
					</div>
					<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
						<span style="margin-right: 10px;"><!-- Contact: 9999999999 --></span>
						<input type="button" value="Signout" class="signout" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
					</div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col-sm-12 black_header">
				<div class="col-md-3">
					<span style="margin-left: -40%"><a href='${pageContext.request.contextPath}/index/h0me?tabNumber=2' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: -25%">Back</span></a></span>
				</div>

				<div class="col-md-2">
					
				</div>

				<div class="col-md-7" style="margin-top: 1%">
					
					<label>Vehicle Number. </label>
					<input type="text" class="inp" id="vehnum" style="width: 20%; color: #fff; border-color:#fff"/>
					<input type="button" class="inp1" value="Search" onclick="searchPass()"/>
				</div>
			</div>
		</div>	
		
		<div class="row" style="min-height: 450px">
			<div class="col-md-10  maincontent">
				<div class="row">
				<form method="POST" action="" id="form">
				
					<div class="col-md-12 form" style="width: 60%; margin-bottom: 20px; padding-bottom: 20px">
							<div class="message" id="message">${message}</div>
							<span id="message"></span>
							
							<div class="inpp">
								<label>Toll Plaza:</label>
								<select name="tollPlazaId" onchange="showAmount()" id="tollPlazas" class="inp" style="width:20%">
									<c:forEach items="${tollPlazas}" var="tempTollPlaza">
										<option value="${tempTollPlaza.tollPlazaId}">${tempTollPlaza.tollPlazaName}</option>
									</c:forEach>
								</select>
								
								
								<label>Vehicle Class:</label>
								<select onchange="showAmount()"  name="vehicleClass" class="inp" id="vehicleclass">
									<option value=''>Select a vehicle</option>
									<option value='CAR/ JEEP'>CAR/ JEEP</option>
									<option value='LCV'>LCV</option>
									<option value='BUS/ TRUCK'>BUS/ TRUCK</option>
									<option value='3 AXEL'>3 AXEL</option>
									<option value='HCM/ MAV'>HCM/ MAV</option>
									<option value="OVERSIZED">OVERSIZED</option>
								</select>
								<span class="error" id="sp_vclass" style="margin-left:50%"><br>Please select a vehicle class</span>
							</div>
							
							<div class="inpp">
								 <label class="radio-inline">
							      <input type="radio" name="optradio" value="cml" onchange="fillEndDate(this.value)">Current Month Last Date
							    </label>
							    <label class="radio-inline">
							      <input type="radio" name="optradio" value="ntd" onchange="fillEndDate(this.value)">Next 30 Days
							    </label>
							    <label class="radio-inline">
							      <input type="radio" name="optradio" value="nml" onchange="fillEndDate(this.value)">Next Month Last Date
							    </label>
							</div>
							
							<div class="inpp">
								<label>Effective From:</label>
								<input type="text" class="inp" id="demo" style="width: 25%" disabled/>
								<input type="text" id="stdate" name="effectiveFrom" style="display: none">
								
								<label>Effective To:</label>
								<input type="text" name="" id="effectiveTo" class="inp" style="width:20%" disabled>
								<input type="text" name="" id="effectiveTo1" hidden>
								<input type="text" name="effectiveTo" id="efto" hidden/>
								<span id="sp_effectiveTo" class="error">Please, select one of the above 3 radio boxes to fill the date fields</span>
							</div>
							
							<div class="inpp">
								<label>Pass Type:</label>
								<select onchange="showAmount()" name="passType" class="inp" id="passType" style="width: 20%;">
									<option value="">Select a pass type</option>
									<option value="LOCAL VIP">Local VIP</option>
									<option value="LOCAL">Local</option>
									<option value="MONTHLY">Monthly</option>
								</select>
								<span class="error" id="sp_passType">Please, enter a pass type</span>
								
								<label>Vehicle Number:</label>
								<input type="text" name="vehicleNumber" id="vnum" class="inp"/>
								<span id="sp_vnum" class="error" style="margin-left:50%">Please, Enter a valid vehicle number</span>
							</div>

							<div class="inpp">
								<label>Amount:</label>
								<select id="amt" name="amt" class="inp" style="min-width: 10%">
									<option></option>
								</select>
								<span id="sp_newAmt" class="error">Please, Configure pass for this date first.</span>
							</div>
							
							<div class="inpp">
								<label>Valid for </label>
								<input type="number" name="tripValidity" id="validiy" value="50" class="inp"/>
								<label style="margin-left:0px;">Trips</label>
								<span class="error" id="sp_val">Please, enter an integer value only(no decimals)</span>
							</div>
							<div>
								<input class="inp1" type="button" id="sub" value="Print Receipt" onclick = "submit1()"/>
		
								<input class="inp1" type="button" value="Clear" onclick="location.reload();"/>
								
								<input type="button" value="Cancel Pass" class="inp1"
										onclick="showModal()"
										class="Re-Configure Shifts"
										/>
								
							</div>
					</div>
				<form>
					<!-- photo -->
					 <div class="col-sm-4" style="margin-bottom: 20px; ">
						<div class="cctv" id="table">
							
						</div>
					</div>
 						
 						
				</div>
			</div>
		</div>

		<!-- footer -->
			<div class="row" >
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
		<div class="visible-print" id="hidden">
		
		</div>
		
		
		
		<div class="visible-xs container hidden-print" style="margin-right: 0px; min-width: 100%">
			
			<div class="row">
				<div class="col-xs-12" style="background-color: #ee9620">
					<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' style="margin-left: 15%; margin-bottom: 5%" width="60%"/>
					<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28" style="margin-left: 15%" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
					<br><span style="margin-left:25%">Generate Pass</span>
				</div>
			</div>
		</div>
		
		<div class="row black_header visible-xs hidden-print">
		
			<div class="col-xs-12" style="">
				<a href='javascript:history.back()' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -50%; margin-top: 0px; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px"> </a>
			</div>

		</div>

				
		<div class="row form visible-xs hidden-print" style="min-height: 220px; width: 90%">
			<span id="smessage"></span>
			<div class="col-xs-12" class="message" id="message">${message}</div>
			
			<div class="col-xs-12">
				<label>Toll Plaza:</label>
				<select name="tollPlazaId" onchange="sshowAmount()" id="stollPlazas" class="inp" style="width:50%">
					<c:forEach items="${tollPlazas}" var="tempTollPlaza">
						<option value="${tempTollPlaza.tollPlazaId}">${tempTollPlaza.tollPlazaName}</option>
					</c:forEach>
				</select>
			</div>
			
						
			<div class="col-xs-12">							
				<label>Vehicle Class:</label>
				<select onchange="sshowAmount()"  name="vehicleClass" class="inp" id="svehicleclass">
					<option value=''>Select a vehicle</option>
					<option value='CAR/ JEEP'>CAR/ JEEP</option>
					<option value='LCV'>LCV</option>
					<option value='BUS/ TRUCK'>BUS/ TRUCK</option>
					<option value='3 AXEL'>3 AXEL</option>
					<option value='HCM/ MAV'>HCM/ MAV</option>
					<option value="OVERSIZED">OVERSIZED</option>
				</select>
				<span class="error" id="ssp_vclass" style="margin-left:50%"><br>Please select a vehicle class</span>
			</div>
			
									
			<div class="row">	
			<label style="margin-left: 17%">Valid Till</label>
				<div class="col-xs-12">
					<label class="radio-inline">
					<input type="radio" name="optradio" value="cml" onchange="fillEndDate(this.value)">Current Month Last Date
					</label>
				</div>
				
				<div class="col-xs-12">
					<label class="radio-inline">
					<input type="radio" name="optradio" value="ntd" onchange="fillEndDate(this.value)">Next 30 Days
					</label>
				</div>
				
				<div class="col-xs-12">
					<label class="radio-inline">
					<input type="radio" name="optradio" value="nml" onchange="fillEndDate(this.value)">Next Month Last Date
					</label>
				</div>
			</div>
			
			<div class="col-xs-12">
				<label>Effective From:</label>
				<input type="text" class="inp" id="sdemo" style="width: 30%" disabled/>
			</div>	
				
			<div class="col-xs-12">
				<label>Effective To:</label>
				<input type="text" name="" id="seffectiveTo" class="inp" style="width:30%" disabled>
				<span id="ssp_effectiveTo" class="error">Please, select one of the above 3 radio boxes to fill the date fields</span>
			</div>
			
			<div class="col-xs-12">
				<label>Pass Type:</label>
				<select onchange="sshowAmount()" name="passType" class="inp" id="spassType" style="width: 50%;">
					<option value="">Select a pass type</option>
					<option value="LOCAL VIP">Local VIP</option>
					<option value="LOCAL">Local</option>
					<option value="MONTHLY">Monthly</option>
				</select>
				<span class="error" id="ssp_passType">Please, enter a pass type</span>
			</div>
			
			<div class="col-xs-12">
				<label>Vehicle Number:</label>
					<input type="text" name="vehicleNumber" id="svnum" class="inp" style="max-width: 50%"/>
					<span id="ssp_vnum" class="error" style="margin-left:50%">Please, Enter a valid vehicle number</span>
			</div>

			
			<div class="col-xs-12">
				<div class="inpp">
					<label>Amount:</label>
					<select id="samt" name="amt" class="inp" style="min-width: 30%">
						<option></option>
					</select>
					<span id="ssp_newAmt" class="error">Please, Configure pass for this date first.</span>
				</div>
			</div>
			
			<div class="col-xs-12">				
				<div class="inpp">
					<label>Valid for </label>
					<input type="number" name="tripValidity" id="svalidiy" value="50" class="inp" style="max-width: 50%"/>
					<label style="margin-left:0px;">Trips</label>
					<span class="error" id="ssp_val">Please, enter an integer value only(no decimals)</span>
				</div>
			</div>
			
			<div class="row">
				
				<div class="col-xs-5">
					<input class="inp1" type="button" id="sub" value="Print Receipt" onclick = "ssubmit1()"/>
				</div>
				
				<div class="col-xs-5">
					<input class="inp1" type="button" value="Clear" onclick="location.reload();"/>
				</div>
		
			</div>
			
			<div class="col-xs-12">
								
				<div class="col-xs-4">
					<input type="button" value="Cancel Pass" class="inp1"
						onclick="showModal()"
						class="Re-Configure Shifts"
						style="margin-left: 50%"
						/>
				</div>
			</div>
			
		</div>
		
		<div class="row visible-xs hidden-print">
			<div class="col-xs-12" style="background-color: #6f7070; max-width:90%; margin:5%; margin-top:2%; border-radius: 20px; min-height: 220px;">
				
					<div class="col-xs-12">
						<label style="color: #ffffff">Vehicle Number. </label>
						<input type="text" class="inp" id="svehnum" style="width: 50%; color: #fff; border-color:#fff"/>
						<input type="button" class="inp1" value="Search" onclick="ssearchPass()"/>
					</div>
					
					<div id="stable" style="margin-left: -10px">
							
					</div>
			</div>
		</div>
		
			<!-- The Modal -->
	<div id="myModal" class="modal" style="">
	
	  <!-- Modal content -->
	  <div class="modal-content" style="position:relative; top:25%;left: 15%; max-width: 500px; margin-left: 25%; background-color: #b9b9b9; margin-left: -5%">
	  	<!-- uncomment this if you want cross on the top right -->
	    <!-- <span class="close">&times;</span> -->
	    <form id="cancelForm" action="cancelPass" method="POST">
	    	<label>PASS ID</label> <input class="inp" type="text" name="passIdForCancel" id="passIdForCancel"  style="background-color: #b9b9b9"/>
	 		<br><label>Remarks</label> <textarea id="remarks" name="remarks" style="background-color: #b9b9b9; border-bottom: #ffffff"></textarea>
		    <br>
		    
		    <div class="row" style="margin-left: 10%">
			    <div class="col-xs-4">
				    <input type="button" class="inp1" value="Back" id="no" onclick="document.getElementById('myModal').style.display='none';" style="background-color: #474747; max-width: 130%"/>
				</div>
				<div class="col-xs-4">
					<input type="button" class="inp1" value="Cancel Pass" id="yes" onclick="f1()" style="max-width: 200%"/>
				</div>
			</div>
		</form>
	  </div>
	  
	  
	  	<script src="${pageContext.request.contextPath}/resources/js/pass.js"></script>
</body>
</html>













