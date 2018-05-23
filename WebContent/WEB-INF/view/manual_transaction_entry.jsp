<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Manual Toll Transaction Entry Form</title>

	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/all_style.css">
	
	<script type="text/javascript">
	//getting lane and shift
	
	if (localStorage.status==undefined) window.location.href='${pageContext.request.contextPath}/index/logout'; //logout user incase status is undefined
	
	var xmlHttp;

	function showLanesCodes(plazaId) {
		document.getElementById("message").innerHTML = "";
		document.getElementById("smessage").innerHTML = "";
		
		//clear the rate field
		var str = "<option value=''></option>";
        document.getElementById("tollAmt").innerHTML = str;
        document.getElementById("vehicleclass").value = "";
        
		
		//Sending request
 		if (typeof XMLHttpRequest != "undefined") {
	        xmlHttp = new XMLHttpRequest();
	    } else if (window.ActiveXObject) {
	        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	    } else if(xmlHttp==null) {
	        alert("Browser does not support XMLHTTP Request");
	        return;
	    }

		//bring lanes
		var url="${pageContext.request.contextPath}/lane/listLaneCodes?plazaId="+plazaId;
		xmlHttp.onreadystatechange = stateChange1;
	    xmlHttp.open("GET", url, true);
	    xmlHttp.send(null); 
	}
	
	function stateChange1() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			 var str = xmlHttp.responseText;
			 
			 //fill the select with this
			 document.getElementById("hidenDiv").innerHTML = str;
			 
			 //set lanes
			 var lanes = document.getElementById("ln").innerHTML;
			 document.getElementById("lanes").innerHTML = lanes;
			 document.getElementById("slanes").innerHTML = lanes;
			 
			 //set shifts
			 var shift = document.getElementById("sft").innerHTML;
			 document.getElementById("shifts").innerHTML = shift;
			 document.getElementById("sshifts").innerHTML = shift;
			 
			 if(!/Mobile/i.test(navigator.userAgent) && /ipad/i.test(navigator.userAgent) ){
				 MyFunction();
			    } else {
			    	sMyFunction();
			    }
		 }
	}
	
	//for mobile
	function sMyFunction() {
		document.getElementById("vehicleclass").value=document.getElementById("svehicleclass").value;
		//journey types
		if (document.getElementById("sr1").checked==true) {
			document.getElementById("r1").checked=true;
		} else {
			document.getElementById("r2").checked=true;
		}
		
		
		//concession types scash,sexempted,spass
		if (document.getElementById("scash").checked==true) {
			document.getElementById("cash").checked=true;
		} else if(document.getElementById("sexempted").checked==true) {
			document.getElementById("exempted").checked=true;
		} else {
			document.getElementById("pass").cheked=true;
		}
		document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value;
		
		if(!document.getElementById("sconsession").disabled) {
			document.getElementById("consession").value=document.getElementById("sconsession").value;
		}
		
		MyFunction();
	}

	//following script is for getting rate from rate.jsp using ajax
	 function MyFunction() {
			showAmount(document.getElementById("vehicleclass").value);
		}
	
	 var xmlHttp;
	function showAmount(str) {
		
		document.getElementById("message").innerHTML = "";
		document.getElementById("smessage").innerHTML = "";
		
		var jType ="";
        var cashtype ="";
        var plazaId = "";
        
        var chx = document.getElementsByName("journeyType");
        for (var i=0; i<chx.length; i++) {
            if(chx[i].checked) jType = chx[i].value;
        } 
        
        var chx1 = document.getElementsByName("concessionType");
		for (var i=0; i<chx1.length; i++) {
            if(chx1[i].checked) cashtype = chx1[i].value;
        } 
		
		
		if (jType=="" || jType==  null) {
            var str = "<option value=''></option>";
            document.getElementById("tollAmt").innerHTML = str;
            return;
       }
		
		
		if(str=="" || str==null) {
			var str = "<option value=''></option>";
	        document.getElementById("tollAmt").innerHTML = str;
			return;
		}
		
		plazaId = document.getElementById("tollPlazas").value;
		if (plazaId == null || plazaId == "") {
			var str = "<option value=''></option>";
	        document.getElementById("tollAmt").innerHTML = str;
			return;
		}
		
		 if (typeof XMLHttpRequest != "undefined") {
	            xmlHttp = new XMLHttpRequest();
	        } else if (window.ActiveXObject) {
	            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	        } else if(xmlHttp==null) {
	            alert("Browser does not support XMLHTTP Request");
	            return;
	        }
		 

		 var url="${pageContext.request.contextPath}/tollTransaction/rate";
		 url += "?vehicle="+str+"&jType="+jType+"&plazaId="+plazaId;
		 xmlHttp.onreadystatechange = stateChange;
         xmlHttp.open("GET", url, true);
         xmlHttp.send(null);
	}
	
	function stateChange() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			 var str = xmlHttp.responseText;
			 //remove first and last characters of string as string is like {vehicleClassId=tollAmt}
			 str=str.trim();
			 str = str.slice(1,-1);
			 //split when there us =
			 strs = str.split("=");
			 document.getElementById("vehicleclassid").value=strs[0];
			 //finally add consession before adding amount
			 var amt = strs[1];
			 if(!document.getElementById("consession").disabled) {
				 if(document.getElementById("consession").value!=null && document.getElementById("consession").value!="") {
					 amt = strs[1]-(strs[1]*document.getElementById("consession").value/100);
				 }
			 }
			 document.getElementById("tollAmt").innerHTML = "<option value='"+amt+"'>"+amt+"</option>";
			 document.getElementById("stollAmt").innerHTML = "<option value='"+amt+"'>"+amt+"</option>";
		 
			 //in case of exempted or pass
			 var cashtype ="";
			 
			 var chx1 = document.getElementsByName("concessionType");
				for (var i=0; i<chx1.length; i++) {
		            if(chx1[i].checked) cashtype = chx1[i].value;
		        } 
				
			 
			 if (cashtype!="Cash") {
	            var str = "<option value='"+0.00+"'>0.00</option>";
	            document.getElementById("tollAmt").innerHTML = str;
	            document.getElementById("stollAmt").innerHTML = str;
	            return;
	       	 }
		 }
	}
	
	</script>
	
</head>
<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
	<%-- Hi ${userFromSession.userFirstName} ${userFromSession.userLastName}<br> --%>
	
	<div class="container nopadding hidden-xs">
			<!-- header -->
			<div class="row">
					<div class="col-sm-12 header">
						<div class="row">
							<div class="col-xs-2" style="margin-left: 1%">
								<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
							</div>
							<div class="col-xs-5">
								<h4 class="heading" style="font-size: 0.9em">Manual Transaction Entry</h4>
								<div></div>
							</div>
							<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
								<!-- <span style="margin-right: 10px;">Contact: 9999999999 </span> -->
								<input type="button" value="Signout" class="signout" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
			
					<!-- Side bar -->
					<div class="col-sm-2 sidebar" style="min-height: 720px;">
						<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: 10%">Back</span></a>
					</div>
						
						<!-- Main content -->
					<div class="col-sm-10 maincontent">
						<div class="row">
							<form action="saveManualTransaction" method="POST" id="form1">
								<div class="col-sm-12 form">
									<span class="message" id="message">${message}</span>
									${errors}
									
									<input name="vcid" value="0" id="vehicleclassid" hidden/>
									<div style="margin-left:55%">
										<label>DATE: </label>
										<input type="date" name="entryDate" id="entryDate" style="background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"/>
										<span id="sp_entryDate" class="error"><br>Please enter a valid date</span>
									</div> 
									
									<div>
										<label>SHIFT: </label>
										<select name="shiftId" id="shifts" class="inp" onchange="clearForm()">
											<c:forEach var="tempShift" items="${allShifts}">
												<option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option>
											</c:forEach>
										</select>
										<span class="error" id="sp_shhifts">Please configure shifts to this tollPlaza</span>
									</div>
									
									<div>
										<label>PLAZA: </label>
										<select name="plazaId" class="inp" id="tollPlazas" onchange="showLanesCodes(this.value)" style="width: 31%; padding-left: 10%">
										<c:forEach var="tempTollPlaza" items="${allTollPlazas}">
												<option value="${tempTollPlaza.tollPlazaId}">${tempTollPlaza.tollPlazaName}</option>
											</c:forEach>
										</select>
										<span class="error" id="sp_tollPlazas">Please add TollPlazas first</span>
					
										<label>LANE: </label>
										<select name="laneId" id="lanes" class="inp" onchange="clearForm()" style="width: 31%; padding-left: 10%">
											<c:forEach var="tempLane" items="${allLanes}">
												<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
											</c:forEach>
										</select>
										<span class="error" id="sp_lanes">Please add lanes to the TollPlaza</span>
									</div>
									
									<div>
										<label>VEHICLE NUMBER:</label>
										<input class="inp" name="vehicleNumber" id="vnumber" style="width:20%"/>
										

										<label style="margin-left: 3px">VEHICLE CLASS:</label>
										<select onchange="showAmount(this.value)" class="inp" name="vehicleClassId" id="vehicleclass" class="inp" style="width:30%; padding-left: 4%">
											<option value=''>Select a Vehicle Class</option>
											<option value='CAR/ JEEP'>CAR/ JEEP</option>
											<option value='LCV'>LCV</option>
											<option value='BUS/ TRUCK'>BUS/ TRUCK</option>
											<option value='3 AXEL'>3 AXEL</option>
											<option value='HCM/ MAV'>HCM/ MAV</option>
											<option value="OVERSIZED">OVERSIZED</option>
										</select>
									</div>
									
									<div>
										<span id = "sp_vnum" class="error">Please enter a valid vehicle number<br></span>
										<span id = "sp_vclass" class="error" style="margin-left: 70%">Please select a vehicle class</span>
									</div>
									
									<div class="black" style=" padding-bottom: 3%; margin-left: 10%">
										<h4>Journey Type</h4>
		
		
										<div style="margin-left: 25%">
											<input id = "r1" class="rad" type="radio" class="inp" name="journeyType" value="S" onclick="MyFunction()" style="margin-left: 10%; margin-right: 0px" checked>
										    <label style="margin-left: 0px">Single</label>
										    
										    <input id = "r2" class="rad" type="radio" name="journeyType" value="R" onclick="MyFunction()" style="margin-left: 10%; margin-right: 0px">
											<label style="margin-left: 0px">Return</label>
										</div>
		
										<div style="margin-left: 10%">
											 <input id="cash" type="radio" class="square" name="concessionType" value="Cash"  checked onclick="MyFunction()" style="margin-left: 10%; margin-right: 0px">
									     	 <label style="margin-left: 0px">Cash</label>
									     	 
									     	 <input id="exempted" type="radio" class="square" name="concessionType" value="Exempted"  onclick="MyFunction()" style="margin-left: 10%; margin-right: 0px"> 
									    	 <label style="margin-left: 0px">Exempted</label>
									    	
									    	 <input id="pass" type="radio" class="square" name="concessionType" value="Pass"  onclick="MyFunction()" style="margin-left: 10%; margin-right: 0px"> 
											 <label style="margin-left: 0px">Pass</label>
										</div>
									</div>		
															
									<div>
										<label>CONCESSION:</label>
										<select class="inp" id="consession" name="concession" onchange="changeDiscount()" style="width: 22%; padding-left: 10%">
											<option value="">0%</option>
											<option value="50">50%</option>
											<option value="25">25%</option>
											<option value="75">75%</option>
											<option value="100">100%</option>
											<option value="10">10%</option>
											<option value="15">15%</option>
											<option value="20">20%</option>
										</select>
										
										<label>AMOUNT:</label>
										<select name="tollAmt" id="tollAmt" class="inp" style="width: 22%; padding-left: 10%">				
											<option value='0.00'>0.00</option> 
										</select>
									</div>
									
									<div>
										<label>PAYMENT METHOD:</label>
										<select id="ptype" class="inp" name="paymentMethod">
											<option value="Cash">Cash</option>
											<option value="POS">POS</option>
											<option value="Wallet">Wallet</option>
											<option value="RFID">RFID</option>
										</select>
									</div>
									
									<div style="margin-left: 15%; margin-bottom: 3%;">
										
										<input id="btn" class="inp1" class="button" type="button" value = "Create" onclick = "submit1()"/>
										
										<input id="btn1" class="inp1" class="button" type="button" value = "Clear" onclick = "clr()"/>
										
										<input type="button" value="Cancel" class="inp1"
										onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
										class="add-button"
										/>
										
						            	
						            </div>		
								</div>
							</form>
						</div>
					</div>
				</div>
				
				<!-- footer -->
				<div class="row" >
					<div class="col-sm-12 footer" style="position: relative; left: -10%; min-width: 117.5%; min-height: 49.2px">
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
			
			
				
		<div class="visible-xs container" style="margin: 0px; padding: 0px">
		
				<div class="row">
					<div class="col-xs-12" style="background-color: #ee9620">
						<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' style="margin-left: 15%; margin-bottom: 10%" width="60%"/>
						<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28" style="margin-left: 15%" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
						<br><span style="margin-left:20%">Manual Transaction Entry</span>
					</div>
				</div>
		</div>	
	
		
		<div class="row black_header visible-xs" style=" margin: 0px; min-height: 70px; border-radius: 10px; background-color: #474747; text-align: center; font-size: large; margin-left: 1%; color: #ffffff;">
			<div class="col_xs_12">
				<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -40%; margin-top:25%; color: #474747">;&nbsp Back ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Back</a>
			</div>
			
			<span class="message" id="smessage">${message}</span>
			${errors}
		
			<div class="col-xs-12">
				<label>DATE: </label>
				<input type="date" name="entryDate" id="sentryDate" style="background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"/>
				<span id="ssp_entryDate" class="error"><br>Please enter a valid date</span>
			</div>
		
			<div class="col-xs-12">
				<label>SHIFT: </label>
				<select name="shiftId" id="sshifts" class="inp" onchange="clearForm()">
					<c:forEach var="tempShift" items="${allShifts}">
						<option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option>
					</c:forEach>
				</select>
				<span class="error" id="ssp_shhifts">Please configure shifts to this tollPlaza</span>
			</div>
			
			<div class="col-xs-12">
				<label>PLAZA: </label>
				<select name="plazaId" class="inp" id="stollPlazas" onchange="showLanesCodes(this.value)" style="">
					<c:forEach var="tempTollPlaza" items="${allTollPlazas}">
						<option value="${tempTollPlaza.tollPlazaId}">${tempTollPlaza.tollPlazaName}</option>
					</c:forEach>
				</select>
				<span class="error" id="ssp_tollPlazas">Please add TollPlazas first</span>
					

			</div>
			
			<div class="col-xs-12">
				<label>LANE: </label>
				<select name="laneId" id="slanes" class="inp" onchange="clearForm()" style="">
					<c:forEach var="tempLane" items="${allLanes}">
						<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
					</c:forEach>
				</select>
				<span class="error" id="ssp_lanes">Please add lanes to the TollPlaza</span>
			</div>
			
			<div class="col-xs-12">
				<label>VEHICLE NUMBER:</label>
				<input class="inp" name="vehicleNumber" id="svnumber" style="width: 40%"/>
			</div>
			
			<div class="col-xs-12">
				<label style="margin-left: 3px">VEHICLE CLASS:</label>
				<select onchange="sMyFunction()" class="inp" name="vehicleClassId" id="svehicleclass" class="inp" style="width:40%; padding-left:0%">
					<option value=''>Select a Vehicle Class</option>
					<option value='CAR/ JEEP'>CAR/ JEEP</option>
					<option value='LCV'>LCV</option>
					<option value='BUS/ TRUCK'>BUS/ TRUCK</option>
					<option value='3 AXEL'>3 AXEL</option>
					<option value='HCM/ MAV'>HCM/ MAV</option>
					<option value="OVERSIZED">OVERSIZED</option>
				</select>
			</div>
			
			<div class="col-xs-12 black">
				
				<h4>JOURNEY TYPE</h4>
				
				<div class="row">
					<div class="col-xs-5">
						<input id = "sr1" class="rad" type="radio" class="inp" value="S" name="sjourneyType" onclick="sMyFunction()" style="margin-left: 10%; margin-right: 0px" checked>
						<label style="margin-left: 0px">Single</label>
					</div>
					
					<div class="col-xs-5">    
						<input id = "sr2" class="rad" type="radio" value="R" name="sjourneyType" onclick="sMyFunction()" style="margin-left: 10%; margin-right: 0px">
						<label style="margin-left: 0px">Return</label>
					</div>
				</div>
				
				<div class="row">
					<div class="col-xs-5">
						<input id="scash" type="radio" class="square" name="sconcessionType" value="Cash"  checked onclick="sMyFunction()" style="margin-left: 10%; margin-right: 0px">
						<label style="margin-left: 0px">Cash</label>	 
					</div>
					
					<div class="col-xs-7">
						<input id="sexempted" type="radio" class="square" name="sconcessionType" value="Exempted"  onclick="sMyFunction()" style="margin-left: 10%; margin-right: 0px"> 
						<label style="margin-left: 0px">Exempted</label>
					</div>
										
					<div class="col-xs-12">
						<input id="spass" type="radio" class="square" name="sconcessionType" value="Pass"  onclick="sMyFunction()" style="margin-left: 10%; margin-right: 0px"> 
						<label style="margin-left: 0px">Pass</label>
					</div>
				</div>
				
			</div>
			
			<div class="col-xs-12">
				<label>CONCESSION:</label>
				<select class="inp" id="sconsession" name="concession" onchange="schangeDiscount()" style="width: 50%">
					<option value="">0%</option>
					<option value="50">50%</option>
					<option value="25">25%</option>
					<option value="75">75%</option>
					<option value="100">100%</option>
					<option value="10">10%</option>
					<option value="15">15%</option>
					<option value="20">20%</option>
				</select>
			</div>
			
			<div class="col-xs-12">
				<label>AMOUNT:</label>
				<select name="tollAmt" id="stollAmt" class="inp" style="width: 50%">				
					<option value='0.00'>0.00</option> 
				</select>
			</div>
			
			<div class="col-xs-12">
				<label>PAYMENT METHOD:</label>
				<select id="sptype" class="inp" name="paymentMethod" style="width: 40%; padding-left: 0%">
					<option value="Cash">Cash</option>
					<option value="POS">POS</option>
					<option value="Wallet">Wallet</option>
					<option value="RFID">RFID</option>
				</select>
			</div>
			
			<div class="row">
				<div class="col-xs-4">							
					<input id="sbtn" class="inp1" class="button" type="button" value = "Create" onclick = "ssubmit1()" style="max-width: 90%"/>
				</div>
				
				<div class="col-xs-4">	
					<input id="sbtn1" class="inp1" class="button" type="button" value = "Clear" onclick = "clr()" style="max-width: 90%"/>
				</div>
				
				<div class="col-xs-4">	
					<input type="button" value="Cancel" class="inp1"
					onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
					class="add-button"
					style="max-width: 90%"
					/>
				</div>
			</div>
		</div>
			
			<div id="hidenDiv" style="display: none;"></div>
		<script src="${pageContext.request.contextPath}/resources/js/manual_get_time.js"></script>
		
		<script>
			function clr() {
				document.getElementById("vnumber").value = "";
				document.getElementById("vehicleclass").value="";
				document.getElementById("r1").checked = true;
				document.getElementById("cash").checked = true;
				document.getElementById("consession").value="";
				document.getElementById("ptype").value="Cash";
				
				document.getElementById("svnumber").value = "";
				document.getElementById("svehicleclass").value="";
				document.getElementById("sr1").checked = true;
				document.getElementById("scash").checked = true;
				document.getElementById("sconsession").value="";
				document.getElementById("sptype").value="Cash";
			}
		</script>
</body>
</html>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<%-- <div class="inputData">
	${errors}

		<form action="saveManualTransaction" method="POST" id="form1">
			<input name="vcid" value="0" id="vehicleclassid" hidden/>
			<div>
				<label>DATE: </label>
				<input class="inp" type="date" name="entryDate" id="entryDate"/>
				<span id="sp_entryDate" class="error">Please enter a valid date</span>
			</div> 
			
			
			<div>
				<label>TOLL PLAZA: </label>
				<select name="plazaId" class="inp" id="tollPlazas" onchange="showLanesCodes(this.value)">
				<c:forEach var="tempTollPlaza" items="${allTollPlazas}">
						<option value="${tempTollPlaza.tollPlazaId}">${tempTollPlaza.tollPlazaName}</option>
					</c:forEach>
				</select>
				<span class="error" id="sp_tollPlazas">Please add TollPlazas first</span>
			</div>
			
			<div>
				<label>LANE: </label>
				<select name="laneId" id="lanes" class="inp" onchange="clearForm()">
					<c:forEach var="tempLane" items="${allLanes}">
						<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
					</c:forEach>
				</select>
				<span class="error" id="sp_lanes">Please add lanes to the TollPlaza</span>
			</div>
			
			<div>
				<label>SHIFT: </label>
				<select name="shiftId" id="shifts" class="inp" onchange="clearForm()">
					<c:forEach var="tempShift" items="${allShifts}">
						<option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option>
					</c:forEach>
				</select>
				<span class="error" id="sp_shhifts">Please configure shifts to this tollPlaza</span>
			</div>
			
			
			<div>
				<label>VEHICLE NUMBER:</label>
				<input class="inp" name="vehicleNumber" id="vnumber"/>
				<span id = "sp_vnum" class="error">Please enter a valid vehicle number</span>
			</div>
			
			<div>
				<label>VEHICLE CLASS:</label>
				<select onchange="showAmount(this.value)" class="inp" name="vehicleClassId" id="vehicleclass" class="inp">
					<option value=''>Select a vehicle</option>
					<option value='CAR/ JEEP'>CAR/ JEEP</option>
					<option value='LMV'>LMV</option>
					<option value='BUS/ TRUCK'>BUS/ TRUCK</option> 
					<option value='3 AXEL'>3 AXEL</option>
					<option value='MAV'>MAV</option>
					<option value='HCM'>HCM</option>
					<option value='AUTO'>AUTO</option>
				</select>
				<span id = "sp_vclass" class="error">Please select a vehicle class</span>
			</div>
			
			<div>
				<label>JOURNEY TYPE:</label>
				<input id = "r1" class="inp" type="radio" class="inp" name="journeyType" value="S" onclick="MyFunction()" checked> Single</input><br/>
			    <input id = "r2" class="inp" type="radio" name="journeyType" value="R" onclick="MyFunction()" > Return</input>
			</div>
			
			<div>
				 <input id="cash" type="radio" name="concessionType" value="Cash" style='display: inline;' checked onclick="MyFunction()"> Cash (F8)
			     <input id="exempted" type="radio" name="concessionType" value="Exempted" style='display: inline;' onclick="MyFunction()"> Exempted (F9)
			     <input id="pass" type="radio" name="concessionType" value="Pass" style='display: inline;' onclick="MyFunction()"> Pass (F10)
			</div>
			
			<div>
				<label>CONCESSION:</label>
				<select class="inp" id="consession" name="concession" onchange="changeDiscount()">
					<option value="">0%</option>
					<option value="50">50%</option>
					<option value="25">25%</option>
					<option value="75">75%</option>
					<option value="100">100%</option>
					<option value="10">10%</option>
					<option value="15">15%</option>
					<option value="20">20%</option>
				</select>
			</div>
			
			<div>
				<label>AMOUNT:</label>
				<select name="tollAmt" id="tollAmt" class="inp">				
					<option value='0.00'>0.00</option> 
				</select>
			</div>
			
			<div>
				<label>PAYMENT METHOD:</label>
				<select id="ptype" class="inp" name="paymentMethod">
					<option value="Cash">Cash</option>
					<option value="POS">POS</option>
					<option value="Wallet">Wallet</option>
					<option value="RFID">RFID</option>
				</select>
			</div>
			
			<div>
				
				<input type="button" value="Back" class="inp1"
				onclick="window.location.href='../lane/list?plazaId=${tollPlazaID}'; return false"
				class="add-button"
				/>
				
            	<input id="btn" class="inp1" class="button" type="button" value = "Submit" onclick = "submit1()"/>
            </div>	
		</form>
	</div> --%>
<%-- 		<div id="hidenDiv" style="display: none;"></div>
		<script src="${pageContext.request.contextPath}/resources/js/manual_get_time.js"></script>
</body>
</html> --%>



















