<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<head>
	<title>Consolidated Traffic and Revenue</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/cancel_ticket.css">

	<script>
		//prevents right click
		//document.addEventListener('contextmenu', event = event.preventDefault());
	
		function getReport() {
				var user_id = document.getElementById("user").value;
				//create an url
				var url="${pageContext.request.contextPath}/jasperReports/doGet?P_tuser="+user_id+"&action=";
				url = url+"TollIntegration";
			
				if (typeof XMLHttpRequest != "undefined") {
			        xmlHttp = new XMLHttpRequest();
			    } else if (window.ActiveXObject) {
			        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			    } else if(xmlHttp==null) {
			        alert("Browser does not support XMLHTTP Request");
			        return;
			    }
				
				alert(url)
				xmlHttp.onreadystatechange = stateChange;
		    	xmlHttp.open("GET", url, true);
		   		xmlHttp.send(null); 

			//}
		}
		function stateChange() {
			 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
				// var str = xmlHttp.responseText;
				// str = str.trim();
				// document.getElementById("demo").innerHTML = str;
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
							<h4 class="heading">Sample Report</h4>
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
				
				<div class="col-md-11">
					<label>User Id.</label>
					<input type="text" id="user" name="userId" class="head_inp"/>
					
					
					<input type="button" value="Submit" onClick="getReport()" class="inp1" style="margin-top:15px"/>
				</div>
			</div>
		</div>

</body>