<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<title>Form for adding lane</title>

	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
    
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/all_style.css">
	


	<script type="text/javascript">
	
	if (localStorage.status==undefined) window.location.href='${pageContext.request.contextPath}/index/logout'; //logout user incase status is undefined
	
	//on change option for tollPlaza list
	var xmlHttp;
	function showLanesDirections(plazaId) {
		
		//clear all the fields
		document.getElementById("from").value="";
		document.getElementById("to").value="";
		document.getElementById("ldir").value="";
		document.getElementById("nooflanes").value="";
		
		document.getElementById("sfrom").value="";
		document.getElementById("sto").value="";
		//document.getElementById("sldir").value="";
		document.getElementById("snooflanes").value="";
		
		//change the button value to Create again
		document.getElementById("cte").value="Create";
		document.getElementById("scte").value="Create";
		
		//Sending request
		if (typeof XMLHttpRequest != "undefined") {
	        xmlHttp = new XMLHttpRequest();
	    } else if (window.ActiveXObject) {
	        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	    } else if(xmlHttp==null) {
	        alert("Browser does not support XMLHTTP Request");
	        return;
	    }
		
		var url="${pageContext.request.contextPath}/lane/listfromfile1?plazaId="+plazaId;
		xmlHttp.onreadystatechange = stateChange;
	    xmlHttp.open("GET", url, true);
	    xmlHttp.send(null);

	}

	function stateChange() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			 var str = xmlHttp.responseText;
			 
			 //fill the select with this
			 document.getElementById("lanes").innerHTML = str;
			 document.getElementById("slanes").innerHTML = str;
		 }
	}
	
	function supdate() {
		document.getElementById("tollPlazas").value = document.getElementById("stollPlazas").value;
		document.getElementById("lanes").value = document.getElementById("slanes").value;
		update();
	}
	
	//if he presses on update then update below three fields
	function update() {

	//update the values in the from and to
	var plaza = document.getElementById("tollPlazas");
	var direction = document.getElementById("lanes");

	var plazaId = plaza.value;
	var laneDirection = direction.value;

	var tofrom = direction.options[direction.selectedIndex];
	var to_from = "";
	var from = "";
	var to = "";
	
	if (tofrom!=undefined) {
		to_from = tofrom.text.split("TO");
		from = to_from[0];
		to = to_from[1]; 
		
		//in case of errors remove them
		document.getElementById("sp_updateLane").removeAttribute("class");
		document.getElementById("sp_updateLane").setAttribute("class", "error");
		
		document.getElementById("ssp_updateLane").removeAttribute("class");
		document.getElementById("ssp_updateLane").setAttribute("class", "error");
	} else {
		document.getElementById("sp_updateLane").removeAttribute("class");
		document.getElementById("sp_updateLane").setAttribute("class", "error_show"); 
		
		document.getElementById("ssp_updateLane").removeAttribute("class");
		document.getElementById("ssp_updateLane").setAttribute("class", "error_show"); 
		return;
	}
	
		//hide the create button and place an update button there
		document.getElementById("cte").value="Update";
		document.getElementById("scte").value="Update";
		
		
		//set all the values
		document.getElementById("from").value=from;
		document.getElementById("to").value=to;
		
		document.getElementById("sfrom").value=from;
		document.getElementById("sto").value=to;
		
		//remove errors if present
		document.getElementById("sp_laneDirection").removeAttribute("class");
		document.getElementById("sp_laneDirection").setAttribute("class", "error");
		
		document.getElementById("ssp_laneDirection").removeAttribute("class");
		document.getElementById("ssp_laneDirection").setAttribute("class", "error");
	
		//similarly for number of lanes
		document.getElementById("sp_nooflanes").removeAttribute("class");
		document.getElementById("sp_nooflanes").setAttribute("class", "error");
		
		document.getElementById("ssp_nooflanes").removeAttribute("class");
		document.getElementById("ssp_nooflanes").setAttribute("class", "error");
		
		//now just use Ajax and fill the number of lanes field
		//Sending request
		if (typeof XMLHttpRequest != "undefined") {
		    xmlHttp = new XMLHttpRequest();
		 } else if (window.ActiveXObject) {
		    xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		 } else if(xmlHttp==null) {
		     alert("Browser does not support XMLHTTP Request");
		     return;
		 }
		
		var url="${pageContext.request.contextPath}/lane/noOfLanes?plazaId="+plazaId+"&laneDirection="+laneDirection;
		xmlHttp.onreadystatechange = stateChange1;
	    xmlHttp.open("GET", url, true);
	    xmlHttp.send(null);
	}
	

	function stateChange1() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			 var str = xmlHttp.responseText;
			 str = str.trim();
			 document.getElementById("nooflanes").value=str;
			 document.getElementById("snooflanes").value=str;
		 }
	}
	
	
	</script>
	
</head>
<body>
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
			
			<input type="button" value="Back" class="inp1"
				onclick="window.location.href='${pageContext.request.contextPath}/tollPlaza/showFormForAdd'; return false"
			/>
			
		</c:otherwise>
	</c:choose> --%>
	
	
	<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
		<div class="container nopadding hidden-xs">
		
		<!-- header -->
		<div class="row">
				<div class="col-sm-12 header">
					<div class="row">
						<div class="col-xs-2" style="margin-left: 1%">
							<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
						</div>
						<div class="col-xs-5">
							<h4 class="heading" style="font-size: 0.9em">Configure Lanes</h4>
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
			<!-- Sidebar -->
				<div class="col-sm-2 sidebar" style="min-height: 650px;">
					
					
					<c:choose>
					    <c:when test="${empty plazaId}">
					       <a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: 10%">Back</span></a>
					    </c:when>
					    <c:otherwise>
					    	<a href='javascript:history.back()' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: 10%">Back</span></a>
					    </c:otherwise>
					</c:choose>
				
				
				
				</div>
			
			
			<!-- Main content -->
				<div class="col-sm-10 maincontent">
					<div class="row">
					<form id="form" action="saveLane" method="POST">
						<div class="col-sm-12 form">
							<span class="error_show">${error}</span>
								<div>
									<label>TOLL PLAZA: </label>
									<select class="inp" name="tollPlazaId" id="tollPlazas" onchange="showLanesDirections(this.value)">
										<c:forEach var="tempPlaza" items="${allTollPlazas}">
											<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
										</c:forEach>
									</select>
								</div>
								
								<div>
									<label>LANE DIRECTION: </label>
									<select class="inp" id="lanes" onchange="clearForm()">
										<c:forEach var="tempLaneDirection" items="${allLanes}">
											<option value="${tempLaneDirection.laneDirection}">${tempLaneDirection.laneDirection}</option>
										</c:forEach>
									</select>
								</div>
								
								<div style="margin-left: 65%;">
									<input class="inp1" id="updateLane" type="button" value="Update" onclick = "update()"/>
									<span class="error" id="sp_updateLane">Bad Command!</span>
								</div>
							
							</div>
							
							<div class="col-sm-12 form" style="margin-top:0.5%;">
								<h5>ADD LANES</h5>
								
								<input id="ldir" class="inp" name="laneDirection" type="hidden"/>

								<div>
								<label>FROM: </label>
								<input type="text" class="inp" value="${from}" name="unknown" id="from"/>
								</div>
								
								<div>
								<label>TO: </label>
								<input type="text" class="inp" value="${to}" name="unk" id="to"/>
								<span id = "sp_laneDirection" class="error"><br>Please enter a valid Lane name</span>
								</div>
								
								<div>
									<label>NUMBER OF LANES </label>
									<select name="nooflanes" id="nooflanes" class="inp">
										<option value="">SELECT A NUMBER</option>
									<c:forEach begin="1" end="10" var="i">
				    					<option value="${i}">${i}</option>
									</c:forEach>
									</select>
									<span class="error" id="sp_nooflanes">Please Select number of lanes in that direction</span>
								</div>
								
								<div style="margin-left: 20%;">
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
						<br><span style="margin-left:25%">Lane Add Form</span>
					</div>
				</div>
		</div>	
	
		
			<div class="row black_header visible-xs" style=" margin: 0px; min-height: 70px; border-radius: 10px; background-color: #474747; text-align: center; font-size: large; margin-left: 1%; color: #ffffff;">
				<div class="col_xs_12">
					<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -40%; margin-top:25%; color: #474747">;&nbsp Back ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Back</a>
				</div>
				
				<div class="col-xs-12">
					<span class="error_show">${error}</span>	
				</div>
				
				<div class="col-xs-12">
					<label>TOLL PLAZA: </label>
					<select class="inp" name="tollPlazaId" id="stollPlazas" onchange="showLanesDirections(this.value)">
						<c:forEach var="tempPlaza" items="${allTollPlazas}">
							<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="col-xs-12">
					<label>LANE DIRECTION: </label>
					<select class="inp" id="slanes" onchange="clearForm()" style="margin-left: 0px; padding-left: 0px; font-size: 0.8em; max-width: 50%">
						<c:forEach var="tempLaneDirection" items="${allLanes}">
							<option value="${tempLaneDirection.laneDirection}">${tempLaneDirection.laneDirection}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="col-xs-12">
					<input class="inp1" id="supdateLane" type="button" value="Update" onclick = "supdate()"/>
					<span class="error" id="ssp_updateLane">Bad Command!</span>
				</div>
	
			</div>
		
			<div class="row black_header visible-xs" style=" margin-top: 0px; margin-top: 10px; width:99%; min-height: 70px; background-color: #474747;  text-align: center; font-size: large; border-radius: 10px; padding: 0px; margin-left: 1%; color: #ffffff;">
					<h5>ADD LANES</h5>
				<div class="col-xs-12"/>
						<label>FROM: </label>
						<input type="text" class="inp" value="${from}" name="unknown" id="sfrom"/>
				</div>
				
				<div class="col-xs-12">
					<label>TO: </label>
					<input type="text" class="inp" value="${to}" name="unk" id="sto"/>
					<span id = "ssp_laneDirection" class="error"><br>Please enter a valid Lane name</span>
				</div>
				
				<div class="col-xs-12">
					<label>NUMBER OF LANES </label>
					<select name="nooflanes" id="snooflanes" class="inp" style="max-width: 20%; padding-left: 0px">
						<option value="">SELECT A NUMBER</option>
						<c:forEach begin="1" end="10" var="i">
						<option value="${i}">${i}</option>
					</c:forEach>
					</select>
					<span class="error" id="ssp_nooflanes">Please Select number of lanes in that direction</span>
				</div>
				
				<div class="row" style="">
					<div class="col-xs-4">
						<input class="inp1" type="button" id="scte" value="Create" onclick = "ssubmit1()" style="width: 90%;"/>
					</div>
					
					<div class="col-xs-4">
						<input id="sbtn" class="inp1" type="button" value = "Clear" onclick = "clearForm()" style="width: 90%;"/>
					</div>
										
					<div class="col-xs-4">					
						<input type="button" value="Cancel" class="inp1"
						onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
						class="Re-Configure Shifts"
						style="width: 90%;"
						/>
					</div>
				</div>
			</div>			
		
		
		
		
		
		<%--for selecting default tollPlaza in case he has come from tollPlazas
		as this is only needed initially while loading form this hidden value is not a threat--%>
			<input type="hidden" id="defplaza" value="${plazaId}"/>
			<script src="${pageContext.request.contextPath}/resources/js/lane_add_form.js"></script>
	</body>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	<%-- <div class="content" style="position:fixed;top: 10%;left: 25%;width:100%;height:75%">
		<div class="inputData">
			<span class="error_show">${error}</span>
			<form id="form" action="saveLane" method="POST">
				
				<div>
					<label>TOLL PLAZAS: </label>
					<select class="inp" name="tollPlazaId" id="tollPlazas" onchange="showLanesDirections(this.value)">
						<c:forEach var="tempPlaza" items="${allTollPlazas}">
							<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
						</c:forEach>
					</select>
				</div>
			

				<div>
					<label>LANE DIRECTION: </label>
					<select class="inp" id="lanes" onchange="clearForm()">
						<c:forEach var="tempLaneDirection" items="${allLanes}">
							<option value="${tempLaneDirection.laneDirection}">${tempLaneDirection.laneDirection}</option>
						</c:forEach>
					</select>
				</div>
			
				<div>
					<input class="inp1" id="updateLane" type="button" value="Update" onclick = "update()"/>
					<span class="error" id="sp_updateLane">Bad Command!</span>
				</div>
		
			<h3 style="text-align: center;">ADD/ UPDATE LANE</h3>

					<input id="ldir" class="inp" name="laneDirection" type="hidden"/>

				<div>
				<label>From: </label>
				<input type="text" class="inp" value="${from}" name="unknown" id="from"/>
				</div>
				
				<div>
				<label>To: </label>
				<input type="text" class="inp" value="${to}" name="unk" id="to"/>
				<span id = "sp_laneDirection" class="error"><br>Please enter a valid Lane name</span>
				</div>
				
				<div>
					<label>NUMBER OF LANES </label>
					<select name="nooflanes" id="nooflanes" class="inp">
						<option value="">SELECT A NUMBER</option>
					<c:forEach begin="1" end="10" var="i">
    					<option value="${i}">${i}</option>
					</c:forEach>
					</select>
					<span class="error" id="sp_nooflanes">Please Select number of lanes in that direction</span>
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
			
			for selecting default tollPlaza in case he has come from tollPlazas
			as this is only needed initially while loading form this hidden value is not a threat
			<input type="hidden" id="defplaza" value="${plazaId}"/>
<script src="${pageContext.request.contextPath}/resources/js/lane_add_form.js"></script>
</body>
</html> --%>