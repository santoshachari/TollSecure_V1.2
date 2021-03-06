<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<head>
	<title>Datewise Revenue Report</title>
	
	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/cancel_ticket.css">
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
	<script>
		//prevents right click
		//document.addEventListener('contextmenu', event = event.preventDefault());
		//set latest toll plaza by default
		
		if (localStorage.status==undefined) window.location.href='${pageContext.request.contextPath}/index/logout'; //logout user incase status is undefined
		
		var action = "DatewiseRevenueReport";
		
		function showLanesDirections(plazaId) {

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
				 document.getElementById("laneId").innerHTML = "<option value=''>ALL</option>"+lanes;
				 document.getElementById("slaneId").innerHTML = "<option value=''>ALL</option>"+lanes;
				 
				 //set shifts
				 var shift = document.getElementById("sft").innerHTML;
				 document.getElementById("shiftId").innerHTML = "<option value=''>ALL</option>"+shift; 
				 document.getElementById("sshiftId").innerHTML = "<option value=''>ALL</option>"+shift;
				 
				 //set users
				 var user = document.getElementById("usr").innerHTML;
				 document.getElementById("userId").innerHTML = "<option value=''>ALL</option>"+user;
				 document.getElementById("suserId").innerHTML = "<option value=''>ALL</option>"+user;
			 }
		}
		
	
		function getReport() {
			var from_error = false;
			var to_error = false;
			
			var from = document.getElementById("from").value;
			var to = document.getElementById("to").value;
			
			if (from == "") {
				document.getElementById("sp_from").removeAttribute("class");
				document.getElementById("sp_from").setAttribute("class", "error_show");
				from_error = true;
				
			} else {
				document.getElementById("sp_from").removeAttribute("class");
				document.getElementById("sp_from").setAttribute("class", "error");
				from_error = false;
			}
			
			if (to == "") {
				document.getElementById("sp_to").removeAttribute("class");
				document.getElementById("sp_to").setAttribute("class", "error_show");
				to_error = true;
			} else {
				document.getElementById("sp_to").removeAttribute("class");
				document.getElementById("sp_to").setAttribute("class", "error");
				to_error = false;
			}
			
			var t = new Date(to);
			var f = new Date(from);
			var date_error = false;
			
			if (f>t) {
				document.getElementById("sp_date").removeAttribute("class");
				document.getElementById("sp_date").setAttribute("class", "error_show");
				date_error = true;
			} else {
				document.getElementById("sp_date").removeAttribute("class");
				document.getElementById("sp_date").setAttribute("class", "error");
				date_error = false;
			}
			
			if (! date_error && ! from_error && ! to_error) {
				
				if (typeof XMLHttpRequest != "undefined") {
			        xmlHttp = new XMLHttpRequest();
			    } else if (window.ActiveXObject) {
			        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			    } else if(xmlHttp==null) {
			        alert("Browser does not support XMLHTTP Request");
			        return;
			    }
				
				//get all the variables needed
				var  shiftId = document.getElementById("shiftId").value;
				var laneId = document.getElementById("laneId").value;
				var userId = document.getElementById("userId").value;
				var plazaId = document.getElementById("tollPlazas").value;
				
				//bould a table and keep it in hidden table tab
				var slt = document.getElementById("shiftId");
				var tlt = document.getElementById("userId");
				var llt = document.getElementById("laneId");

				var selectedShift = slt.options[slt.selectedIndex].text;
				var selectedUser = tlt.options[tlt.selectedIndex].text;
				var selectedLane = llt.options[llt.selectedIndex].text;
				
				var table = 
					"<img src='${pageContext.request.contextPath}/resources/images/nhai-logo.jpg' align='left' width='100px'/>"+
					"<table style='margin-left: 10px'>"+
						"<tr><th>From. "+from+"</th><th>To. "+to+"</th> <th>Shift. "+selectedShift+"</th></tr>"+
						"<tr><th>GeneratedBy: "+document.getElementById("genBy").value+"</th><th>TC. "+selectedUser+"</th><th>Lane. "+selectedLane+"</th></tr>"+
					"</table>"; 
				
				document.getElementById("hiddenTable").innerHTML = table;
				
				//create an url
				var url="${pageContext.request.contextPath}/jasperReports/doGet";
				url = url + "?from="+from+"&to="+to+"&shiftId="+shiftId+"&laneId="+laneId+"&userId="+userId;
				url = url+"&tollPlazaId="+plazaId+"&action="+action;

				xmlHttp.onreadystatechange = stateChange;
		    	xmlHttp.open("GET", url, true);
		   		xmlHttp.send(null); 

			}
		}
		function stateChange() {
			 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
				 var str = xmlHttp.responseText;
				 str = str.trim();
				 document.getElementById("demo").innerHTML = str;
				 document.getElementById("loading").style.display = "none";
				 document.getElementById("sloader").style.display="none";
				 //window.open('${pageContext.request.contextPath}/Desktop/jasperIntegration/'+action+'.pdf', '_blank');
				 //window.open('${pageContext.request.contextPath}/JasperReports/'+action+'.pdf', '_blank');
				 var str = xmlHttp.responseText;
				 str = str.trim();
				 if (str=="logged_out") {
					 localStorage.clear();
					 window.location.href = "${pageContext.request.contextPath}/index/logout";
				 } else {
					 //window.open('${pageContext.request.contextPath}/jasper/iframe?action='+action, '_blank');	 
					 if(/Mobile/i.test(navigator.userAgent) && !/ipad/i.test(navigator.userAgent) ){
							window.open('${pageContext.request.contextPath}/jasper/iframe?action='+action+'&type='+'html', '_blank');	 
					    } else {
					    	window.open('${pageContext.request.contextPath}/jasper/iframe?action='+action+'&type='+'pdf', '_blank');	 
					    }	 
				 } 

			 } else {
				 document.getElementById("loading").style.display = "block";
				 document.getElementById("sloader").style.display="block";
			 }
		}
		
	</script>
</head>
<input hidden id="genBy" value="${userFromSession.userFirstName}"/>
<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
	<div class="container nopadding hidden-xs">

		<!-- header -->
		<div class="row">
			<div class="col-sm-12 header hidden-print" style=" min-width: 102%;">
				<div class="row">
					<div class="col-xs-2" style="margin-left: 1%">
						<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
					</div>
					<div class="col-xs-5">
							<h4 class="heading" style="font-size: 0.9em">Datewise Revenue Report</h4>
						<div></div>
					</div>
					<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
						<span style="margin-right: 10px;"><!--Contact: 9999999999--></span>
						<input type="button" value="Signout" class="signout" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
					</div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col-sm-12 black_header hidden-print">
				<div class="col-md-1">
					<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=1' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;">Back</a>
				</div>

				
				<div class="col-md-11">
								
					<span>
						<span class="error" id="sp_from">Please, Enter from Date</span>
						<span class="error" id="sp_to">Please, Enter to Date</span>
						<span class="error" id="sp_date">Start date is greater than end date</span>
					</span>
					<div class="row" style="margin-left: 50px">
						<div class="col-md-6">
							<label>From.</label>
							<input type="date" id="from" name="from"  max="${today}" style="background-color: #000;background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"/>
							<label style="margin-left: 100px">TO:</label>
							<input type="date" id="to" name="to" max="${today}" style="background-color: #000;background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"/>
						</div>
			
							<div class="col-md-4">
								<label style="margin-left:-50px">TOLL PLAZA: </label>
								<c:set var="i" scope="page" value="0"/>
								<select class="head_inp" name="tollPlazaId" id="tollPlazas" onchange="showLanesDirections(this.value)" style="margin-top:15px; min-width: 250px;">
									<c:forEach var="tempPlaza" items="${allTollPlazas}">
										<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
										<c:set var="i" value="${i + 1}" scope="page"/>
									</c:forEach>
								</select>
							</div>
					</div>
					
					<label style="margin-left: -200px">SHIFT: </label>
					<select name="shiftId" id="shiftId" class="head_inp" style="margin-top:15px; max-width: 100px">
						<option value="">ALL</option>
						<c:forEach items="${allShifts}" var="tempShift">
							<option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option>
						</c:forEach>
					</select>
					
					<label style="margin-left:50px">LANE: </label>
					<select name="laneId" id="laneId" class="head_inp" style="margin-top:15px; max-width: 100px">
						<option value="">ALL</option>
						<c:forEach items="${allLanes}" var="tempLane">
							<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
						</c:forEach>
					</select>
					
					<label style="margin-left: 50px">TC: </label>
					<select name="userId" id="userId" class="head_inp" style="margin-top:15px; margin-left: -5px; max-width: 100px">
						<option value="">ALL</option>
						<c:forEach items="${allOperators}" var="tempOperator">
							<option value="${tempOperator.userId}">${tempOperator.userFirstName} ${tempOperator.userLastName}</option>
						</c:forEach>
					</select>
					
					<%-- <label style="margin-left: 40px">GENERATED BY:</label>
					<span>${userFromSession.userFirstName}</span> --%><br>
					
					<input type="button" value="Submit" onClick="getReport()" class="inp1" style="margin-top:15px; margin-left: -100px"/>
				</div>
			</div>
		</div>

		<div class="row" style="min-height: 450px">
			<div class="loader" style="display : none; margin-left:50%" id="loading"></div>
			<div class="visible-print" id="hiddenTable" style="margin-left: 30px">
				
			</div>
			<div id="demo" class="col-md-10 maincontent" style="margin-left: 30px">
				
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
		
		<div id="hidenDiv" style="display: none">

		</div>
		
		 <input type="hidden" name="defaultPlaza" id="dplaza" value="${i}"/>
		 
</body>

	<div class="visible-xs container" >
		
		<div class="row">
			<div class="col-xs-12" style="background-color: #ee9620">
				<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' style="margin-left: 15%; margin-bottom: 10%" width="60%"/>
				<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28" style="margin-left: 15%" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
				<br><span style="margin-left:20%">Datewise Revenue Report</span>
			</div>
		</div>
	
	</div>	
	
	<div class="row black_header visible-xs">
		
		<div class="col-xs-12">
			<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=1' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -50%"> </a>
		</div>
	
	</div>
	
	<div class="row black_header visible-xs">
		<span>
			<span class="error" id="ssp_from">Please, Enter from Date</span>
			<span class="error" id="ssp_to">Please, Enter to Date</span>
			<span class="error" id="ssp_date">Start date is greater than end date</span>
		</span>
		
		<div class="col-xs-12" >
			<label>FROM:</label>
			<input type="date" id="sfrom" name="from"  max="${today}" style="background-color: #000;background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"/>
		</div>
	</div>
	
	<div class="row black_header visible-xs" style="margin-top: -20px">	
		<div class="col-xs-12">
			<label>TO:</label>
			<input type="date" id="sto" name="to" max="${today}" style="background-color: #000;background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ; margin-left: 5%"/>
		</div>
	</div>

	<div class="row black_header visible-xs" style="margin-top: -20px">
		<div class="col-xs-12">
			<label style="margin-left:0px">TOLL PLAZA: </label>
			<c:set var="i" scope="page" value="0"/>
			<select class="head_inp" name="tollPlazaId" id="stollPlazas" onchange="showLanesDirections(this.value)" style="margin-top:15px; min-width: 200px;">
				<c:forEach var="tempPlaza" items="${allTollPlazas}">
				<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
				<c:set var="i" value="${i + 1}" scope="page"/>
				</c:forEach>
			</select>
		</div>
	</div>
	
	<div class="row black_header visible-xs" style="margin-top: -20px">
		<div class="col-xs-12">
			<label style="margin-left: 0px">SHIFT: </label>
			<select name="shiftId" id="sshiftId" class="head_inp" style="margin-top:15px; max-width: 100px">
				<option value="">ALL</option>
				<c:forEach items="${allShifts}" var="tempShift">
					<option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class="row black_header visible-xs" style="margin-top: -20px">
		<div class="col-xs-12">
			<label style="margin-left:0px">LANE: </label>
			<select name="laneId" id="slaneId" class="head_inp" style="margin-top:15px; max-width: 100px">
				<option value="">ALL</option>
				<c:forEach items="${allLanes}" var="tempLane">
					<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
				</c:forEach>
			</select>			
		</div>
	</div>
	
	<div class="row black_header visible-xs" style="margin-top: -20px">
		<div class="col-xs-12">
			<label style="margin-left: 0px">TC: </label>
			<select name="userId" id="suserId" class="head_inp" style="margin-top:15px; margin-left: 0px; max-width: 100px">
				<option value="">ALL</option>
				<c:forEach items="${allOperators}" var="tempOperator">
					<option value="${tempOperator.userId}">${tempOperator.userFirstName} ${tempOperator.userLastName}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	
	<div class="row black_header visible-xs" style="margin-top: -20px">
		<div class="col-xs-12">
			<input type="button" value="Submit" onClick="sgetReport()" class="inp1" style="margin-top:15px;"/>
			<image id="sloader" src="${pageContext.request.contextPath}/resources/images/loader.gif" width="40" height="40" style="margin-left: 50%; display: none"/>
		</div>
	</div>
	
		
	<script>
		var x=document.getElementById("dplaza").value;
		document.getElementById("tollPlazas").selectedIndex=x-1;
		document.getElementById("stollPlazas").selectedIndex=x-1;
		
		function sgetReport() {
			var from_error = false;
			var to_error = false;
			
			var from = document.getElementById("sfrom").value;
			var to = document.getElementById("sto").value;
			
			if (from == "") {
				document.getElementById("ssp_from").removeAttribute("class");
				document.getElementById("ssp_from").setAttribute("class", "error_show");
				from_error = true;
				
			} else {
				document.getElementById("ssp_from").removeAttribute("class");
				document.getElementById("ssp_from").setAttribute("class", "error");
				from_error = false;
			}
			
			if (to == "") {
				document.getElementById("ssp_to").removeAttribute("class");
				document.getElementById("ssp_to").setAttribute("class", "error_show");
				to_error = true;
			} else {
				document.getElementById("ssp_to").removeAttribute("class");
				document.getElementById("ssp_to").setAttribute("class", "error");
				to_error = false;
			}
			
			var t = new Date(to);
			var f = new Date(from);
			var date_error = false;
			
			if (f>t) {
				document.getElementById("ssp_date").removeAttribute("class");
				document.getElementById("ssp_date").setAttribute("class", "error_show");
				date_error = true;
			} else {
				document.getElementById("ssp_date").removeAttribute("class");
				document.getElementById("ssp_date").setAttribute("class", "error");
				date_error = false;
			}
			
			if (! date_error && ! from_error && ! to_error) {
				document.getElementById("from").value = from;
				document.getElementById("to").value = to;
				
				document.getElementById("shiftId").value=document.getElementById("sshiftId").value;
				document.getElementById("laneId").value=document.getElementById("slaneId").value;
				document.getElementById("userId").value=document.getElementById("suserId").value;
				document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value;
				
				getReport();
				
			}
		}
	</script>
</html>