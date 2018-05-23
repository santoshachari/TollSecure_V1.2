<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<title>Assign Lane/ Shift</title>
 	
 	<!-- frvicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/all_style.css">
	<script type="text/javascript">
	
	if (localStorage.status==undefined) window.location.href='${pageContext.request.contextPath}/index/logout'; //logout user incase status is undefined
	
	var xmlHttp;
	
	function isLoggedIn() {
		if (typeof XMLHttpRequest != "undefined") {
	        xmlHttp = new XMLHttpRequest();
	    } else if (window.ActiveXObject) {
	        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	    } else if(xmlHttp==null) {
	        alert("Browser does not support XMLHTTP Request");
	        return;
	    }
		
		url="${pageContext.request.contextPath}/index/getVehicleTranactions";
		
	}
	
	
	function showLanesCodes(plazaId) {
		
		document.getElementById("message").innerHTML = "";
		
		//clear the form
		clearForm();
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
		xmlHttp.onreadystatechange = stateChange;
	    xmlHttp.open("GET", url, true);
	    xmlHttp.send(null); 
	}
	
	function stateChange() {
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
			 
			 //set users
			 var user = document.getElementById("usr").innerHTML;
			 document.getElementById("users").innerHTML = "<option value=''>SELECT AN OPERATOR</option>"+user;
			 document.getElementById("susers").innerHTML = "<option value=''>SELECT AN OPERATOR</option>"+user;
		 }
	}
	
	//mobile
	function supdate() {
		document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value;
		document.getElementById("lanes").value=document.getElementById("slanes").value;
		document.getElementById("date").value=document.getElementById("sdate").value;
		document.getElementById("shifts").value=document.getElementById("sshifts").value;
		
		update();
	}

	//update
	function update() {
		
		document.getElementById("message").innerHTML = "";
		
		document.getElementById("cte").value = "Update";
		document.getElementById("scte").value = "Update";
		
		//now get all the values
		var plazaId = document.getElementById("tollPlazas").value;
		var laneId = document.getElementById("lanes").value;
		var date = document.getElementById("date").value;
		var shift = document.getElementById("shifts").value;

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
		var url="${pageContext.request.contextPath}/floatAmountDetails/listUpdateDetails";
		url = url + "?plazaId="+plazaId+"&laneId="+laneId+"&date="+date+"&shiftId="+shift;
		xmlHttp.onreadystatechange = stateChange1;
	    xmlHttp.open("GET", url, true);
	    xmlHttp.send(null); 
	}
	
	function stateChange1() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			 var str = xmlHttp.responseText;
			 str = str.trim();
			 strs = str.split(" ");
			 if (strs.length<2) { 
				 document.getElementById("users").value="";
				 document.getElementById("ftamt").value="";
				 
				 document.getElementById("susers").value='';
				 document.getElementById("sftamt").value="";
				return;
			 } else {
				 document.getElementById("users").value=strs[0];
				 document.getElementById("ftamt").value=strs[1];
				 
				 document.getElementById("susers").value=strs[0];
				 document.getElementById("sftamt").value=strs[1];
			 }
		 }
	}
	
</script>

</head>
<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
	<%-- <c:choose>
		<c:when test="${empty userFromSession.userFirstName}">
						Hi all
			<% response.sendRedirect("../index/loginUser"); %> 
		</c:when>
		<c:otherwise>
			Hi <br>
			Name: ${userFromSession.userFirstName}
			${userFromSession.userLastName}<br>
			Role: ${userFromSession.userRole}
			<a href="${pageContext.request.contextPath}/index/logout?User=${user}">logout</a><br>
							
			<input type="button" value="Home" class="inp1"
				onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
			/>
			
		</c:otherwise>
	</c:choose> --%>
		
		
		
	<div class="container nopadding hidden-xs">
		<!-- header -->
		<div class="row">
				<div class="col-sm-12 header">
					<div class="row">
						<div class="col-xs-2" style="margin-left: 1%">
							<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
						</div>
						<div class="col-xs-5">
							<h4 class="heading" style="font-size: 0.9em">Assign Lane/ Shift</h4>
							<div></div>
						</div>
						<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
							<span style="margin-right: 10px;"><!--Contact: 9999999999 --></span>
							<input type="button" value="Signout" class="signout" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
						</div>
					</div>
				</div>
			</div>
			
			
			<div class="row">
				<!-- Sidebar -->
					<div class="col-sm-2 sidebar" style="min-height: 580px;">
						<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: 10%">Back</span></a>
					</div>
					
					<!-- Main content -->
					<div class="col-sm-10 maincontent">
						<div class="row">
							<form id="form" method="POST" action="saveFloatAmount">
								<div class="form">
									<span class="message" id="message">${message}</span>
									<span class="error_show">${error}</span>
									
									<div style="margin-left: 50%;">
										<label>SHIFT DATE: </label>
										<input type="date" name="date" value="${date}" id="date" style="background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;">
										<span class="error" id="sp_date">Please select a date</span>
									</div>
									
									<div>
										<label>TOLL PLAZA: </label>
										<select name="tollPlazaId" value="${plazaId}" class="inp" id="tollPlazas" onchange="showLanesCodes(this.value)">
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
													<c:choose>
													    <c:when test="${laneId==tempLane.laneId}">
													        <option value="${tempLane.laneId}" selected="selected">${tempLane.laneCode}</option> 												    
													       </c:when>    
													    <c:otherwise>
													        <option value="${tempLane.laneId}">${tempLane.laneCode}</option>
													    </c:otherwise>
													</c:choose>
											</c:forEach>
										</select>
										<span class="error" id="sp_lanes">Please add lanes to the TollPlaza</span>
									</div>
									
									<div>
										<label>SHIFT: </label>
										<select name="shiftId" id="shifts" class="inp" onchange="clearForm()">
											<c:forEach var="tempShift" items="${allShifts}">
												<c:choose>
												    <c:when test="${shift==tempShift.shiftId}">
												        <option value="${tempShift.shiftId}" selected="selected">${tempShift.shiftDesc}</option> 												    
												       </c:when>    
												    <c:otherwise>
												        <option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option> 
												    </c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<span class="error" id="sp_shhifts">Please configure shifts to this tollPlaza</span>
									</div>
									
									<div>
										<input type="button" class="inp1" value="Update" onclick="update()" style="margin-left: 40%"/>
									</div>
									
									<div>
										<label>USER: </label>
										<select name="userId" id="users" class="inp">
											<option value="">SELECT AN OPERATOR</option>
												<c:forEach var="tempUser" items="${allUsers}">
													<c:choose>
												    	<c:when test="${userId==tempUser.userId}">
												        	<option value="${tempUser.userId}" selected="selected">${tempUser.userFirstName} ${tempUser.userLastName}</option> 												    
												       	</c:when>    
												    	<c:otherwise>
												        	<option value="${tempUser.userId}">${tempUser.userFirstName} ${tempUser.userLastName}</option> 
												    	</c:otherwise>
													</c:choose>
												</c:forEach>	
										</select>
										<span class="error" id="sp_users">Please add users to the TollPlaza</span>
									</div>
									
									<div>
									<label>FLOAT AMOUNT</label>
										<input type="number" name="floatamt" value="${amount}" class="inp" id="ftamt"/>
										<span class="error" id="sp_ftamt">Please enter a valid amount</span>
									</div>
									
									<div style="margin-left: 15%">
										<input class="inp1" type="button" id="cte" value="Create" onclick = "submit1()"/>
										
										<input id="btn" class="inp1"type="button" value = "Clear" onclick = "clearForm()"/>
										
										<input type="button" value="Cancel" class="inp1"
										onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
										class="Re-Configure Shifts"
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
					<br><span style="margin-left:20%">Assign Shift/ Lane</span>
				</div>
			</div>
		</div>	
		
		<div class="row black_header visible-xs" style=" margin: 0px; min-height: 70px; border-radius: 10px; background-color: #474747; text-align: center; font-size: large; margin-left: 1%; color: #ffffff;">
			<div class="col_xs_12">
				<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -40%; margin-top:25%; color: #474747">;&nbsp Back ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Back</a>
			</div>
			
			<span class="message" id="smessage">${message}</span>
			<span class="error_show">${error}</span>
			
			<div class="col-xs-12">
				<label>SHIFT DATE: </label>
				<input type="date" name="date" value="${date}" id="sdate" style="background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;">
				<span class="error" id="ssp_date">Please select a date</span>
			</div>
			
			<div class="col-xs-12">
				<label>TOLL PLAZA: </label>
				<select name="tollPlazaId" value="${plazaId}" class="inp" id="stollPlazas" onchange="showLanesCodes(this.value)">
				<c:forEach var="tempTollPlaza" items="${allTollPlazas}">
					<option value="${tempTollPlaza.tollPlazaId}">${tempTollPlaza.tollPlazaName}</option>
				</c:forEach>
				</select>
				<span class="error" id="ssp_tollPlazas">Please add TollPlazas first</span>
			</div>
			
			<div class="col-xs-12">
				<label>LANE: </label>
				<select name="laneId" id="slanes" class="inp" onchange="clearForm()">
					<c:forEach var="tempLane" items="${allLanes}">
						<c:choose>
					    <c:when test="${laneId==tempLane.laneId}">
					       <option value="${tempLane.laneId}" selected="selected">${tempLane.laneCode}</option> 												    
					    </c:when>    
						<c:otherwise>
							<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<span class="error" id="ssp_lanes">Please add lanes to the TollPlaza</span>
			</div>
			
			<div class="col-xs-12">
				<label>SHIFT: </label>
				<select name="shiftId" id="sshifts" class="inp" onchange="clearForm()">
					<c:forEach var="tempShift" items="${allShifts}">
						<c:choose>
						    <c:when test="${shift==tempShift.shiftId}">
						        <option value="${tempShift.shiftId}" selected="selected">${tempShift.shiftDesc}</option> 												    
						       </c:when>    
						    <c:otherwise>
						        <option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option> 
						    </c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<span class="error" id="ssp_shhifts">Please configure shifts to this tollPlaza</span>
			</div>
			
			<div class="col-xs-12">
				<input type="button" class="inp1" value="Update" onclick="supdate()" style="margin-left: 0%"/>
			</div>
			
			
			<div class="col-xs-12">
				<label>USER: </label>
				<select name="userId" id="susers" class="inp" style="padding-left: 0%">
					<option value="">SELECT AN OPERATOR</option>
					<c:forEach var="tempUser" items="${allUsers}">
							<c:choose>
							    	<c:when test="${userId==tempUser.userId}">
						        	<option value="${tempUser.userId}" selected="selected">${tempUser.userFirstName} ${tempUser.userLastName}</option> 												    
							       	</c:when>    
							    	<c:otherwise>
						        	<option value="${tempUser.userId}">${tempUser.userFirstName} ${tempUser.userLastName}</option> 
							    	</c:otherwise>
								</c:choose>
							</c:forEach>	
					</select>
					<span class="error" id="ssp_users">Please add users to the TollPlaza</span>
			</div>
			
			<div class="col-xs-12">
				<label>FLOAT AMOUNT</label>
				<input type="number" name="floatamt" value="${amount}" class="inp" id="sftamt" style="max-width: 50%"/>
				<span class="error" id="ssp_ftamt">Please enter a valid amount</span>
			</div>
			
			<div class="row">
			
				<div class="col-xs-4">
					<input class="inp1" type="button" id="scte" value="Create" onclick = "ssubmit1()" style="max-width: 90%"/>
				</div>
				
				<div class="col-xs-4">		
					<input id="sbtn" class="inp1"type="button" value = "Clear" onclick = "clearForm()" style="max-width: 90%"/>
				</div>
				
				<div class="col-xs-4">	
					<input type="button" value="Cancel" class="inp1"
					onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
					class="Re-Configure Shifts"
					style="max-width: 90%"
					/>
				</div>
			</div>
			
		</div>
	<div id="hidenDiv" style="display: none;"></div>
	<script src="${pageContext.request.contextPath}/resources/js/add_float_amount.js"></script>	
</body>
		
		
		
	<%-- <div class="content" style="position:fixed;top: 10%;left: 25%;width:80%;height:65%">
		<div class="inputData">
		<span class="error_show">${error}</span>
			<h3 style="text-align: center;">DECLARE FLOAT AMOUNT</h3>
			<form id="form" method="POST" action="saveFloatAmount">
			
				<div>
					<label>SHIFT DATE: </label>
					<input type="date" name="date" class="inp" id="date">
					<span class="error" id="sp_date">Please select a date</span>
				</div>
				
				<div>
					<label>TOLL PLAZA: </label>
					<select name="tollPlazaId" class="inp" id="tollPlazas" onchange="showLanesCodes(this.value)">
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
					<input type="button" class="inp1" value="Update" onclick="update()"/>
				</div>
				
				<div>
					<label>USER: </label>
					<select name="userId" id="users" class="inp">
							<option value="">SELECT AN OPERATOR</option>
						<c:forEach var="tempUser" items="${allUsers}">
							<option value="${tempUser.userId}">${tempUser.userFirstName}${tempUser.userLastName}</option>
						</c:forEach>	
					</select>
					<span class="error" id="sp_users">Please add users to the TollPlaza</span>
				</div>
				
				<div>
				<label>FLOAT AMOUNT</label>
					<input type="number" name="floatamt" class="inp" id="ftamt"/>
					<span class="error" id="sp_ftamt">Please enter a valid amount</span>
				</div>
				
				<div>
					<input class="inp1" type="button" id="cte" value="Create" onclick = "submit1()"/>
					<input type="button" value="Cancel" class="inp1"
					onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
					class="Re-Configure Shifts"
					/>
					<input id="btn" class="inp1"type="button" value = "Clear" onclick = "clearForm()"/>
				</div>
				
			</form>
		</div>
	</div>
	
	<div id="hidenDiv" style="display: none;"></div>
		<script src="${pageContext.request.contextPath}/resources/js/add_float_amount.js"></script>
</body> --%>
</html>