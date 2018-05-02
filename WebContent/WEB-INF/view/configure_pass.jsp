<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<title>Configure Pass</title>

	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/all_style.css">

	<script>
	
		var xmlHttp;
		function nowShow(plazaId, effectiveTo, vehicleClass, passType) {
			
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
					document.getElementById("amt").innerHTML = "<option value=''></option>"
					document.getElementById("samt").innerHTML = "<option value=''></option>"
					return;
				}
				
				document.getElementById("amt").innerHTML = "<option value='"+str+"'>"+str+"</option>";
				document.getElementById("samt").innerHTML = "<option value='"+str+"'>"+str+"</option>";
			 }
		}
	</script>
</head>

<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
	<div class="container nopadding hidden-xs">

		<!-- header -->
		<div class="row">
			<div class="col-sm-12 header" style=" min-width: 102%;">
				<div class="row">
					<div class="col-xs-2" style="margin-left: 1%">
						<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
					</div>
					<div class="col-xs-5">
							<h4 class="heading" style="font-size: 0.9em">Configure Pass</h4>
						<div></div>
					</div>
					<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
						<span style="margin-right: 10px;"><!--Contact: 9999999999 --></span>
						<input type="button" value="Signout" class="signout" onclick="window.location.href='${pageContext.request.contextPath}/index/logout';">
					</div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col-sm-2 sidebar" style="min-height: 530px">
			    	<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: 10%">Back</span></a>
			</div>

			<div class="col-sm-10 maincontent">
				<div class="row">
					<div class="col-md-12 form col-sm-12" style="width: 60%; margin-bottom: 20px; padding-bottom: 20px">
							<form method="POST" action="savePassConfig" id="form">
							<div class="message" id="message">${message}</div>
														
							<div class="inpp">
								<label>Effective From:</label>
								<input type="text" class="inp" id="demo" style="width: 25%" disabled/>
								<input type="text" id="stdate" name="effectiveFrom" style="display: none">
								
								<label>Effective To:</label>
								<input type="date" name="effectiveTo" id="effectiveTo" onchange="showAmount()" style="background: url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat; margin-top:1%">
								<span class="error" id="sp_effectiveTo" style="margin-left:50%"> Please, select an end date</span>
								<span class="error" id="sp_effectiveTo1" style="margin-left:50%">Effective to date should be greater than effective from date</span>
							</div>
							
							<div class="inpp">
								<label>TOLL PLAZA:</label>
								<select name="tollPlazaId" onchange="showAmount()" id="tollPlazas" class="inp" style="max-width:20%; padding-left: 0%">
									<c:forEach items="${tollPlazas}" var="tempTollPlaza">
										<option value="${tempTollPlaza.tollPlazaId}">${tempTollPlaza.tollPlazaName}</option>
									</c:forEach>
								</select>
								
								
								<label style="margin-left: 10px">VEHICLE CLASS:</label>
								<select onchange="showAmount()"  name="vehicleClass" class="inp" id="vehicleclass" style="max-width: 30%; padding-left: 1%">
									<option value=''>Select a Vehicle Class</option>
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
								<label>Pass Type:</label>
								<select onchange="showAmount()" name="passType" class="inp" id="passType" style="padding-left: 0%; max-width: 25%">
									<option value="">Select a pass type</option>
									<option value="LOCAL">Local</option>
									<option value="MONTHLY">Monthly</option>
								</select>
								<span class="error" id="sp_passType">Please, enter a pass type</span>
								
								<label>AMOUNT:</label>
								<select id="amt" class="inp" style="max-width: 20%; padding-left: 0%;">
									<option></option>
								</select>
							</div>
							
							<div class="inpp">
								<input type="button" class="inp1" value="Update" onclick="update()" style="margin-left:40%"/>
							</div>
							
							<div>
								<label>NEW AMOUNT:</label>
								<input type="number" id="newAmt" value="" name="newAmt" class="inp" style="width: 20%;"/>
								<span class="error" id="sp_newAmt">Please, fill all the fields and click on Update/ Submit</span>
							</div>

							<div style="margin-left: 10%">
								<input class="inp1" type="button" id="sub" value="Create" onclick = "submit1()"/>
							
								<input class="inp1" type="button" value="Clear" onclick="clear1()"/>
							
								<input type="button" value="Cancel" class="inp1"
										onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
										class="Re-Configure Shifts"
										/>
								
							</div>
					</div>
				<form>
					<!-- photo -->
					<!-- <div class="col-sm-4" style="margin-bottom: 20px; ">
						<div class="cctv" style="">
							<span style="position: relative; top: 80px; left: 100px; color: #fff; font-size: 10em">CCTV</span>
						</div>
					</div>
 						-->
 						
				</div>
			</div>

		<!-- footer -->
			<div class="row" >
				<div class="col-sm-12 footer" style="position: relative; left: -10%; min-width: 110%;min-height: 49.2px">
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
	</div>
	
	
	<div class="visible-xs container" style="margin: 0px; padding: 0px">
		
				
		<div class="row">
			<div class="col-xs-12" style="background-color: #ee9620">
				<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' style="margin-left: 15%; margin-bottom: 10%" width="60%"/>
				<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28" style="margin-left: 15%" onclick="window.location.href='${pageContext.request.contextPath}/index/logout';">
				<br><span style="margin-left:25%">Configure Pass</span>
			</div>
		</div>
		
	</div>
	
	<div class="row black_header visible-xs" style=" margin: 0px; min-height: 70px; border-radius: 10px; background-color: #474747; text-align: center; font-size: large; margin-left: 1%; color: #ffffff;">
		<div class="col_xs_12">
			<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -40%; margin-top:25%; color: #474747">;&nbsp Back ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Back</a>
		</div>	
		
		<div class="col-xs-12">
			<div class="message" id="message">${message}</div>
		</div>
		
		<div class="col-xs-12">
			<label>Effective From:</label>
			<input type="text" class="inp" id="sdemo" style="width: 25%" disabled/>
			<input type="text" id="sstdate" name="effectiveFrom" style="display: none">
		</div>
			
		<div class="col-xs-12">
			<label>Effective To:</label>
			<input type="date" name="effectiveTo" id="seffectiveTo" onchange="sshowAmount()" style="background: url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat; margin-top:1%">
			<span class="error" id="ssp_effectiveTo" style="margin-left:10%"> Please, select an end date</span>
			<span class="error" id="ssp_effectiveTo1" style="margin-left:10%">Effective to date should be greater than effective from date</span>
		</div>
		
		<div class="col-xs-12">
			<label>TOLL PLAZA:</label>
			<select name="tollPlazaId" onchange="sshowAmount()" id="stollPlazas" class="inp" style="max-width:50%; padding-left: 0%">
				<c:forEach items="${tollPlazas}" var="tempTollPlaza">
					<option value="${tempTollPlaza.tollPlazaId}">${tempTollPlaza.tollPlazaName}</option>
				</c:forEach>
			</select>
		</div>
		
		<div class="col-xs-12">
			<label style="">VEHICLE CLASS:</label>
			<select onchange="sshowAmount()"  name="vehicleClass" class="inp" id="svehicleclass" style="max-width: 40%; padding-left: 0%">
				<option value=''>Select a Vehicle Class</option>
				<option value='CAR/ JEEP'>CAR/ JEEP</option>
				<option value='LCV'>LCV</option>
				<option value='BUS/ TRUCK'>BUS/ TRUCK</option>
				<option value='3 AXEL'>3 AXEL</option>
				<option value='HCM/ MAV'>HCM/ MAV</option>
				<option value="OVERSIZED">OVERSIZED</option>
			</select>
			<span class="error" id="ssp_vclass" style="margin-left:50%"><br>Please select a vehicle class</span>
		</div>
		
		<div class="col-xs-12">
			<label>Pass Type:</label>
			<select onchange="sshowAmount()" name="passType" class="inp" id="spassType" style="padding-left: 0%; max-width: 30%">
				<option value="">Select a pass type</option>
				<option value="LOCAL">Local</option>
				<option value="MONTHLY">Monthly</option>
			</select>
			<span class="error" id="ssp_passType">Please, enter a pass type</span>
		</div>
		
		<div class="col-xs-12">
			<label>AMOUNT:</label>
			<select id="samt" class="inp" style="max-width: 20%; padding-left: 0%;">
				<option></option>
			</select>
		</div>
		
		<div class="col-xs-12">
			<input type="button" class="inp1" value="Update" onclick="supdate()" style="margin-left:40%"/>
		</div>
		
		<div class="col-xs-12">
			<label>NEW AMOUNT:</label>
			<input type="number" id="snewAmt" value="" name="newAmt" class="inp" style="width: 20%;"/>
			<span class="error" id="ssp_newAmt">Please, fill all the fields and click on Update/ Submit</span>
		</div>
		
		<div class="row">
			<div class="col-xs-4">
					
				<input class="inp1" type="button" id="sub" value="Create" onclick = "ssubmit1()" style="max-width: 90%"/>
				
			</div>
				
			<div class="col-xs-4">		
				<input class="inp1" type="button" value="Clear" onclick="clear1()" style="max-width: 90%"/>
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
	
	<script src="${pageContext.request.contextPath}/resources/js/pass_configuration.js"></script>
</body>
</html>





















