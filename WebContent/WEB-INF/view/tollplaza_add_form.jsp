<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<title>Add a Tollplaza here</title>

	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/all_style.css">
</head>
	
	<script>
	
	if (localStorage.status==undefined) window.location.href='${pageContext.request.contextPath}/index/logout'; //logout user incase status is undefined
	
	function submit2() {
		var plazaId = document.getElementById("tollPlazas").value;
		window.location.href = "${pageContext.request.contextPath}/lane/showFormForAdd?tollPlazaId="+plazaId;
	}
	
	function submit3() {
		var plazaId = document.getElementById("tollPlazas").value;
		window.location.href = "${pageContext.request.contextPath}/shift/shiftConfigure?tollPlazaId="+plazaId;
	}
	
	function submit4() {
		var plazaId = document.getElementById("tollPlazas").value;
		window.location.href = "${pageContext.request.contextPath}/index/registerUser?tollPlazaId="+plazaId;
	}
	</script>
	
</head>
<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
	<div class="container nopadding hidden-xs">
		
		<!-- header -->	
			<div class="row">
				<div class="col-xs-12 header">
					<div class="row">
						<div class="col-xs-2" style="margin-left: 1%">
							<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
						</div>
						<div class="col-xs-5">
							<h4 class="heading" style="font-size: 0.9em">Configure Toll Plaza</h4>
							<div></div>
						</div>
						<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
							<span style="margin-right: 10px;"><!--Contact: 9999999999 --></span> 
							<input type="button" value="Signout" class="signout" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
						</div>
					</div>
				</div>
			</div>
	
		<!-- Side bar -->
			<div class="row">
					<div class="col-xs-2 sidebar" style="">
				    	<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: 10%">Back</span></a>
					</div>
					
			<!-- main content -->	
				<div class="col-xs-10 maincontent">
					<div class="row">
						<div class="col-xs-12 form" >
							<form id="form" action="saveTollPlaza" method="POST">
							<input type="text" name="tollPlazaId" id="tollPlazaId" hidden/>
							<span class="error_show">${error}</span>
								<div>
								<label>TOLL PLAZA: </label>
								<select class="inp" id="tollPlazas">
								<c:set var="i" scope="page" value="0"/>
									<c:forEach var="tempPlaza" items="${allTollPlazas}">
										<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
										<c:set var="i" value="${i + 1}" scope="page"/>
									</c:forEach>
								</select>
							</div>
							
							<div style="margin-left: 67%;">
								<input class="inp1" id="updateToll" type="button" value="Update" onclick = "update()"/>
							</div>
						</div>
	
						<div class="col-xs-12 form" style="margin-top:0.5%;">
								<h5>ADD PLAZA</h5>
									<div>
										<label>TOLLPLAZA NAME: </label>
										<input id="tpn" class="inp" name="tollPlazaName"/>
										<span id = "sp_plazaName" class="error"><br>Please enter a valid TollPlaza name</span>
									</div>
									
									<div style="margin-left: 22%;">
										<input class="inp1" id="cte" type="button" value="Create" onclick = "submit1()"/>
										
										<input id="btn" class="inp1"type="button" value = "Clear" onclick = "clearForm()"/>
										
										<input type="button" value="Cancel" class="inp1"
										onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
										class="Re-Configure Shifts"
										/>
									</div>
							</div>
						</form>
					</div>
					<div class="col-sm-6 col-xs-offset-3">
						<input class="inp1" type="button" value="Add Lanes" onclick = "submit2()"/>
						<input class="inp1" type="button" value="Configure Shifts" onclick = "submit3()"/>
						<input class="inp1" type="button" value="Add Employee" onclick = "submit4()"/>
					</div>
				</div>
			</div>
		
		<!-- footer -->
		<div class="row" >
			<div class="col-xs-12 footer" style="position: relative; left: -10%; min-width: 117.5%; min-height: 49.2px">
					<div class="col-xs-5">
				
					</div>
						<div class="col-xs-4">
							<div style="position: relative; top:20px;">Copy rights 2017 @ TollSecure India Pvt. Ltd</div>
						</div>
					<div class="col-xs-3">
						
					</div>			
				</div>
		</div>
			
	</div>
	
		<div class="visible-xs container" style="margin: 0px; padding: 0px">
		
				<div class="row">
					<div class="col-xs-12" style="background-color: #ee9620">
						<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' style="margin-left: 15%; margin-bottom: 10%" width="60%"/>
						<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28" style="margin-left: 15%" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
						<br><span style="margin-left:20%">Toll Plaza Add Form</span>
					</div>
				</div>
		</div>	
	
		
			<div class="row black_header visible-xs" style=" margin: 0px; min-height: 70px; border-radius: 10px; background-color: #474747; text-align: center; font-size: large; margin-left: 1%; color: #ffffff;">
				<div class="col_xs_12">
					<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -40%; margin-top:25%; color: #474747">;&nbsp Back ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Back</a>
				</div>
				
				<div class="col-xs-12">
				<span class="error_show">${error}</span>
					<label>TOLL PLAZA: </label>
					<select class="inp" id="stollPlazas">
					<c:set var="i" scope="page" value="0"/>
						<c:forEach var="tempPlaza" items="${allTollPlazas}">
							<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
							<c:set var="i" value="${i + 1}" scope="page"/>
						</c:forEach>
					</select>
					
					<div class="col-xs-12" style="margin-left: 0%;">
						<input class="inp1" id="supdateToll" type="button" value="Update" onclick = "supdate()"/>
					</div>
				</div>
				
	
			</div>
	
			<div class="row black_header visible-xs" style=" margin-top: 0px; margin-top: 10px; width:99%; min-height: 70px; background-color: #474747;  text-align: center; font-size: large; border-radius: 10px; padding: 0px; margin-left: 1%; color: #ffffff;">
				<div class="col-xs-12" style="margin-top:0.5%;">
					<h5>ADD PLAZA</h5>
						<div>
							<label>NEW NAME: </label>
							<input id="stpn" class="inp" name="tollPlazaName"/>
							<span id = "ssp_plazaName" class="error"><br>Please enter a valid TollPlaza name</span>
						</div>
				</div>
				
					
					<div class="row" style="">
						<div class="col-xs-4">
							<input class="inp1" id="scte" type="button" value="Create" onclick = "ssubmit1()" style="width: 90%; margin-left: 20px"/>
						</div>
						
						<div class="col-xs-4">
							<input id="sbtn" class="inp1" type="button" value = "Clear" onclick = "clearForm()" style="width: 90%"/>
						</div>
						
						<div class="col-xs-4">
							<input type="button" value="Cancel" class="inp1"
							onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
							class="Re-Configure Shifts"
							style="width: 90%"/>
						</div>
					</div>
			
			</div>
			
			<div class="visible-xs row" style="margin-top: 20px;  margin-left: 1%; ">
				<div class="col-xs-4">
					<input class="inp1" type="button" value="Lanes" onclick = "submit2()" style="width: 90%"/>
				</div>
				
				<div class="col-xs-4">
					<input class="inp1" type="button" value="Shifts" onclick = "submit3()" style="width: 90%"/>
				</div>
				
				<div class="col-xs-4">
					<input class="inp1" type="button" value="Employee" onclick = "submit4()" style="width: 90%"/>
				</div>
							
			</div>

		<c:choose>
		    <c:when test="${empty plazaId}">
		       <input type="hidden" id="identify" value="false"/>
		       <input type="hidden" name="defaultPlaza" id="dplaza" value="${i}"/>
		    </c:when>
		    <c:otherwise>
		    	<input type="hidden" id="identify" value="true"/>
		        <input type="hidden" name="defaultPlaza" id="dplaza" value="${plazaId}"/>
		    </c:otherwise>
		</c:choose>
	        <script src="${pageContext.request.contextPath}/resources/js/tollplaza_add_form.js"></script>
</body>