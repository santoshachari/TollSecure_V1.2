<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<head>
	<title>Consolidated Traffic and Revenue</title>
	
	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
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
			
			if (!from_error && !date_error && !to_error ) {
				
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
				//var laneId = document.getElementById("laneId").value;
				//var userId = document.getElementById("userId").value;
				
				//bould a table and keep it in hidden table tab
				var slt = document.getElementById("shiftId");
				//var tlt = document.getElementById("userId");
				//var llt = document.getElementById("laneId");

				var selectedShift = slt.options[slt.selectedIndex].text;
				//var selectedUser = tlt.options[tlt.selectedIndex].text;
				//var selectedLane = llt.options[llt.selectedIndex].text;
				
				//var table = 
				//	"<img src='${pageContext.request.contextPath}/resources/images/nhai-logo.jpg' align='left' width='100px'/>"+
				//	"<table style='margin-left: 10px'>"+
				//		"<tr><th>From. "+from+"</th><th>To. "+to+"</th> <th>Shift. "+selectedShift+"</th></tr>"+
				//		"<tr><th>GeneratedBy: "+document.getElementById("genBy").value+"</th><th>TC. "+selectedUser+"</th><th>Lane. "+selectedLane+"</th></tr>"+
				//	"</table>"; 
				
				//document.getElementById("hiddenTable").innerHTML = table;
				
				//create an url
				//var url="${pageContext.request.contextPath}/report/generateConsolidated";
				//url = url + "?from="+from+"&to="+to+"&shiftId="+shiftId+"&laneId="+laneId+"&userId="+userId;
				
				var url = "${pageContext.request.contextPath}/pass/getPassesReport?from="+from+"&shiftId="+shiftId+"&to="+to;
				
				xmlHttp.onreadystatechange = stateChange;
		    	xmlHttp.open("GET", url, true);
		   		xmlHttp.send(null); 

			}
		}
		function stateChange() {
			 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
				 document.getElementById("loading").style.display = "none";
				 var str = xmlHttp.responseText;
				 str = str.trim();

				 document.getElementById("demo").innerHTML = str;
			 } else {
				 document.getElementById("loading").style.display = "block";
				 document.getElementById("demo").innerHTML = "";
			 }
		}
		
	</script>
</head>
<input hidden id="genBy" value="${userFromSession.userFirstName}"/>
<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
	<div class="container nopadding">

		<!-- header -->
		<div class="row">
			<div class="col-sm-12 header hidden-print" style=" min-width: 102%;">
				<div class="row">
					<div class="col-xs-2" style="margin-left: 1%">
						<img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/>
					</div>
					<div class="col-xs-5">
							<h4 class="heading">Pass Analysis</h4>
						<div></div>
					</div>
					<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
						<span style="margin-right: 10px;">Contact: 9999999999</span>
						<input type="button" value="Signout" class="signout" onclick="window.location.href='${pageContext.request.contextPath}/index/logout';">
					</div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col-sm-12 black_header hidden-print">
				<div class="col-md-1">
					<a href='${pageContext.request.contextPath}/index/h0me' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;">Back</a>
				</div>
				
				<span>
					<span class="error" id="sp_from">Please, Enter from Date</span>
					<span class="error" id="sp_to">Please, Enter to Date</span>
					<span class="error" id="sp_date">Start date is greater than end date</span>
				</span>
				
				<div class="col-md-11">
					<label>Date.</label>
					<input type="date" id="from" name="from"  max="${today}" style="background-color: #000;background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"/>
					<label style="margin-left: 100px">To.</label>
					<input type="date" id="to" name="to" max="${today}" style="background-color: #000;background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"/> 
					
					
					<label style="margin-left: 100px">Shift. </label>
					<select name="shiftId" id="shiftId" class="head_inp" style="margin-top:15px; max-width: 100px">
						<option value="">ALL</option>
						<c:forEach items="${allShifts}" var="tempShift">
							<option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option>
						</c:forEach>
					</select>
					
					<label style="margin-left: 40px">Generated by.</label>
					<span>${userFromSession.userFirstName}</span><br>
					
					<%-- <label style="margin-left: -110px">TC. </label>
					<select name="userId" id="userId" class="head_inp" style="margin-top:15px; margin-left: -10px; max-width: 300px">
						<option value="">ALL</option>
						<c:forEach items="${allOperators}" var="tempOperator">
							<option value="${tempOperator.userId}">${tempOperator.userFirstName} ${tempOperator.userLastName}</option>
						</c:forEach>
					</select>
					
					<label style="margin-left:50px">Lane. </label>
					<select name="laneId" id="laneId" class="head_inp" style="margin-top:15px; max-width: 300px">
						<option value="">ALL</option>
						<c:forEach items="${allLanes}" var="tempLane">
							<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
						</c:forEach>
					</select> --%>
					
					<input type="button" value="Submit" onClick="getReport()" class="inp1" style="margin-top:15px"/>
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
</body>