<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
	<title>Shift Configuration Form</title>
	
	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
		<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/all_style.css">

	<script>
	
		if (localStorage.status==undefined) window.location.href='${pageContext.request.contextPath}/index/logout'; //logout user incase status is undefined
	
		var xmlHttp;
		
		function smyFunction() {
			plazaId = document.getElementById("tollPlazas").value=plazaId = document.getElementById("stollPlazas").value;
			myFunction();
		}
		
		function myFunction() {
			document.getElementById("toBeFilled").innerHTML = "";
			document.getElementById("stoBeFilled").innerHTML = "";
			setGlobalFlagToFalse();
			var plazaId = document.getElementById("tollPlazas").value;
			
			//Sending request
			if (typeof XMLHttpRequest != "undefined") {
		        xmlHttp = new XMLHttpRequest();
		    } else if (window.ActiveXObject) {
		        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		    } else if(xmlHttp==null) {
		        alert("Browser does not support XMLHTTP Request");
		        return;
		    }
			
			var url= "${pageContext.request.contextPath}/shift/fillIt?plazaId="+plazaId;
			xmlHttp.onreadystatechange = stateChange;
		    xmlHttp.open("GET", url, true);
		    xmlHttp.send(null);
		}
		
		function stateChange() {
			if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
				 var str = xmlHttp.responseText;
				 str = str.trim();
				 //fill the select with this
				 document.getElementById("toBeFilled").innerHTML = str;
				 document.getElementById("stoBeFilled").innerHTML = str;
			 }
		}
	</script>
</head>
<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)" >
	<%-- <c:choose>
		<c:when test="${empty userFromSession.userFirstName}">
						Hi all
			<% response.sendRedirect("../index/loginUser"); %> 
		</c:when>
		<c:otherwise>

		</c:otherwise>
	</c:choose>
	<a href="${pageContext.request.contextPath}/index/logout?User=${user}">logout</a>
	Hi <br>
	Name: ${userFromSession.userFirstName} ${userFromSession.userLastName}<br>
	Role: ${userFromSession.userRole}
	
	<br> --%>
	
	<div class="container nopadding hidden-xs">
		<!-- header -->
		<div class="row">
				<div class="col-sm-12 header">
					<div class="row">
						<div class="col-xs-2" style="margin-left: 1%">
							<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
						</div>
						<div class="col-xs-5">
							<h4 class="heading" style="font-size: 0.9em">Configure Shifts</h4>
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
				<!-- side bar -->
				<div class="col-sm-2 sidebar" style="">
					<c:choose>
					    <c:when test="${empty plazaId}">
					       <a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: 10%">Back</span></a>
					    </c:when>
					    <c:otherwise>
					    	<a href='${pageContext.request.contextPath}/tollPlaza/showFormForAdd' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: 10%">Back</span></a>
					    </c:otherwise>
					</c:choose>
				</div>
				
				<!-- main content -->
				<div class="col-sm-10 maincontent">
					<div class="row" style="padding-top: 3%">
					<form id="form" action="saveShiftConfiguration" method="POST">
						<div class="col-sm-12 form">
							<div>
								<label>TOLL PLAZAS: </label>
								<select class="inp" name="plazaId" id="tollPlazas" onchange="myFunction()">
									<c:forEach var="tempPlaza" items="${allTollPlazas}">
										<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
									</c:forEach>
								</select>
							</div>
							
							<div>
								<label>NO. OF SHIFTS PER DAY:*</label>
								<select id="nusfts" name="nusfts" class="inp">
									<option value="">Please select a value</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
								</select>
								<span id = "sp_nusfts" class="error">Please select number of shifts</span>
							</div>
							
							<div>
								<label>SHIFT "A" START TIME:*</label>
								<input type="time" class="inp" id="stTime" name="startTime" style="width: 15%;"/>
								<input type="button" id="btn" class="inp1" onclick="setShifts()" value="Set Shifts">
							</div>
							<span id = "sp_setsft" class="error">Please set shift by selecting on the button</span>
							<span id = "sp_sttime" class="error">Please select A Shift start time</span>
							
							<!-- filling the shift timings -->
							<div id="toBeFilled" class="black">	
								<c:forEach items="${allShifts}" var="shift">
									<div style='padding: 0% 10% 1% 10%'>
										${shift.shiftDesc} Shift start time: ${shift.startTime}
										<span class='text-right'> ${shift.shiftDesc} Shift end time: ${shift.endTime}</span>
									</div>
								</c:forEach>
							</div>
							
							<div style="margin-left: 10%">
							
								<input type="button" id="btn1" class="inp1" onclick="submin()" value="Create">
								
								<input type="button" id="btn2" class="inp1" onclick="clr()" value="Clear">
								
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
			<div class="col-sm-12 footer" style="position: relative; left: -10%; min-width: 117.5%; min-height: 70px">
					<div class="col-sm-5">
				
					</div>
						<div class="col-sm-4">
							<div style="position: relative; top:20px;">Copy rights 2017 @ TollSecure India Pvt. Ltd</div>
						</div>
					<div class="col-sm-3">
						
					</div>			
				</div>
	</div>
	
		<div class="visible-xs container" style="margin: 0px; padding: 0px">
		
			<div class="row">
				<div class="col-xs-12" style="background-color: #ee9620">
					<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' style="margin-left: 15%; margin-bottom: 10%" width="60%"/>
					<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28" style="margin-left: 15%" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
					<br><span style="margin-left:20%">Shift Configuration Form</span>
				</div>
				

			</div>
		</div>
		
		<div class="row black_header visible-xs" style=" margin: 0px; min-height: 70px; border-radius: 10px; background-color: #474747; text-align: center; font-size: large; margin-left: 1%; color: #ffffff;">
			<div class="col_xs_12">
				<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -40%; margin-top:25%; color: #474747">;&nbsp Back ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Back</a>
			</div>
			
			<div class="col-xs-12">
				<label>TOLL PLAZAS: </label>
				<select class="inp" name="plazaId" id="stollPlazas" onchange="smyFunction()">
					<c:forEach var="tempPlaza" items="${allTollPlazas}">
						<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
					</c:forEach>
				</select>
			</div>
				
 			<div class="col-xs-12">
				<label>NO. OF SHIFTS:</label>
				<select id="snusfts" name="nusfts" class="inp" style="max-width: 50%; padding-left: 0%">
					<option value="">Please select a value</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
				</select>
				<span id = "ssp_nusfts" class="error">Please select number of shifts</span>
			</div> 

			<div class="col-xs-12">
				<label>SHIFT "A" START TIME:*</label>
				<input type="time" class="inp" id="sstTime" name="startTime" style="width: 30%;"/>
				<input type="button" id="sbtn" class="inp1" onclick="ssetShifts()" value="Set Shifts">
			</div>
			
			<div class="col-xs-12">					
				<div id="stoBeFilled" class="black" style="min-width: 95%">	
					<c:forEach items="${allShifts}" var="shift">
						<div style='padding: 0% 0% 1% 1%'>
							${shift.shiftDesc} Shift start time: ${shift.startTime}<br>
							<span class='text-right' style="margin-left: 6.9%"> ${shift.shiftDesc} Shift end time: ${shift.endTime}</span>
						</div>
					</c:forEach>
				</div>
			</div>
			
			<span id = "ssp_setsft" class="error">Please set shift by selecting on the button</span>
			<span id = "ssp_sttime" class="error">Please select A Shift start time</span>
			
			<div class="row">
				<div class="col-xs-4">
					<input type="button" id="sbtn1" class="inp1" onclick="ssubmin()" value="Create" style="max-width: 90%">
				</div>			
				
				<div class="col-xs-4">
					<input type="button" id="sbtn2" class="inp1" onclick="clr()" value="Clear" style="max-width: 90%">
				</div>					
			
				<div class="col-xs-4">
					<input type="button" value="Cancel" class="inp1"
						onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
						class="Re-Configure Shifts" style="max-width: 90%"
						/>
				</div>
			</div>
		</div>
			
			
		</div>	
	<input type="hidden" id="defplaza" value="${plazaId}"/>
	<script src="${pageContext.request.contextPath}/resources/js/shift_configuration.js"></script>
</body>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
<%-- 	<div class="inputData">
		<form id="form" action="saveShiftConfiguration" method="POST">

			<div>
				<label>TOLL PLAZAS: </label>
				<select class="inp" name="plazaId" id="tollPlazas">
					<c:forEach var="tempPlaza" items="${allTollPlazas}">
						<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
					</c:forEach>
				</select>
			</div>
			
			<div>
				<label>NO. OF SHIFTS PER DAY:*</label>
				<select id="nusfts" name="nusfts" class="inp">
					<option value="">Please select a value</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
				</select>
				<span id = "sp_nusfts" class="error">Please select number of shifts</span>
			</div>
			
			<div>
				<label>A SHIFT START TIME:*</label>
				<input type="time" class="inp" id="stTime" name="startTime"/>
				<span id = "sp_sttime" class="error">Please select A Shift start time</span>
			</div>
			<span id = "sp_setsft" class="error">Please set shift by selecting on the button</span>
			<div id="toBeFilled">
				
			</div>
			<input type="button" id="btn" class="inp1" onclick="setShifts()" value="Set Shifts">
			<input type="button" id="btn1" class="inp1" onclick="submin()" value="Submit">
			<input type="button" value="Back" class="inp1"
			onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
			class="Re-Configure Shifts"
			/>
		</form>
	</div>
	<input type="hidden" id="defplaza" value="${plazaId}"/>
	<script src="${pageContext.request.contextPath}/resources/js/shift_configuration.js"></script>
</body>
</html> --%>









