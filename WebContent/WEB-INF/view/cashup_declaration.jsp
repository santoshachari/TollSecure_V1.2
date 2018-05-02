<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Cashup Declaration Form</title>

	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/all_style.css">

<!-- modal style -->
	<style>
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
	//whenever you want to nullify the user in field
	function delUser() {

		//if tollPlaza/ Lane/ Shift is changed keep operator ""
		document.getElementById("eid").innerHTML="<option name='emp_id' value=''></option>";
		document.getElementById("seid").innerHTML="<option name='emp_id' value=''></option>";
		
		
		//for big screen
		var digs = document.getElementsByClassName("digs");
		
		for (i=0;i<digs.length;i++) {
			digs[i].value="0";
		}

		 document.getElementById("C2000").value = "0";

		 document.getElementById("c2000").value = "0";
		 document.getElementById("C1000").value = "0";
		 document.getElementById("c1000").value = "0";
		 document.getElementById("C500").value = "0";
		 document.getElementById("c500").value = "0";
		 document.getElementById("C200").value = "0";
		 document.getElementById("c200").value = "0";
		 document.getElementById("C100").value = "0";
		 document.getElementById("c100").value = "0";
		 document.getElementById("C50").value = "0";
		 document.getElementById("c50").value = "0";
		 document.getElementById("C20").value = "0";
		 document.getElementById("c20").value = "0";
		 document.getElementById("C10").value = "0";
		 document.getElementById("c10").value = "0";
		 document.getElementById("C5").value = "0";
		 document.getElementById("c5").value = "0";
		 document.getElementById("C2").value = "0";
		 document.getElementById("c2").value = "0";
		 document.getElementById("C1").value = "0";
		 document.getElementById("c1").value = "0";
		 
		 
		 document.getElementById("Total").value = "0";
		 document.getElementById("total").value = "0";
		
		//for mobile
		var digs = document.getElementsByClassName("digs");
		
		for (i=0;i<digs.length;i++) {
			digs[i].value="0";
		}

		 document.getElementById("sC2000").value = "0";

		 document.getElementById("sc2000").value = "0";
		 document.getElementById("sC1000").value = "0";
		 document.getElementById("sc1000").value = "0";
		 document.getElementById("sC500").value = "0";
		 document.getElementById("sc500").value = "0";
		 document.getElementById("sC200").value = "0";
		 document.getElementById("sc200").value = "0";
		 document.getElementById("sC100").value = "0";
		 document.getElementById("sc100").value = "0";
		 document.getElementById("sC50").value = "0";
		 document.getElementById("sc50").value = "0";
		 document.getElementById("sC20").value = "0";
		 document.getElementById("sc20").value = "0";
		 document.getElementById("sC10").value = "0";
		 document.getElementById("sc10").value = "0";
		 document.getElementById("sC5").value = "0";
		 document.getElementById("sc5").value = "0";
		 document.getElementById("sC2").value = "0";
		 document.getElementById("sc2").value = "0";
		 document.getElementById("sC1").value = "0";
		 document.getElementById("sc1").value = "0";
		 
		 
		 document.getElementById("sTotal").value = "0";
		 document.getElementById("stotal").value = "0";
	}
	
	//on change option for tollPlaza list
	var xmlHttp;

	function showLanesCodes(plazaId) {
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
			 document.getElementById("lanes").innerHTML = "<option value=''>Select a lane</option>"+lanes;
			 document.getElementById("slanes").innerHTML = "<option value=''>Select a lane</option>"+lanes;
			 
			 //set shifts
			 var shift = document.getElementById("sft").innerHTML;
			 document.getElementById("sid").innerHTML = "<option value=''>Select a shift</option>"+shift; 
			 document.getElementById("ssid").innerHTML = "<option value=''>Select a shift</option>"+shift; 
			 
		 }
	}
	
	//for mobile
	
	function sshowUser() {
		document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value;
		document.getElementById("lanes").value=document.getElementById("slanes").value;
		document.getElementById("sid").value=document.getElementById("ssid").value;
		document.getElementById("cdate").value=document.getElementById("scdate").value;
		showUser();
	}
	
	var xmlHttp;
	function showUser() {
		var plazaId = document.getElementById("tollPlazas").value;
		var laneId = document.getElementById("lanes").value;
		var shiftId = document.getElementById("sid").value;
		var checkDate = document.getElementById("cdate").value;

		if(plazaId=="" || plazaId==null || laneId=="" || laneId==null || shiftId=="" || shiftId==null || checkDate=="" || checkDate==null) {
			document.getElementById("sp_showuser").removeAttribute("class");
 			document.getElementById("sp_showuser").setAttribute("class","error_show");
			document.getElementById("eid").innerHTML = '<option name="emp_id" value=""></option>';
			
			document.getElementById("ssp_showuser").removeAttribute("class");
 			document.getElementById("ssp_showuser").setAttribute("class","error_show");
			document.getElementById("seid").innerHTML = '<option name="emp_id" value=""></option>';
 			return;
		}
		
		document.getElementById("sp_showuser").removeAttribute("class");
 		document.getElementById("sp_showuser").setAttribute("class","error");
 		
		document.getElementById("ssp_showuser").removeAttribute("class");
 		document.getElementById("ssp_showuser").setAttribute("class","error");
 			
		if (typeof XMLHttpRequest != "undefined") {
	        xmlHttp = new XMLHttpRequest();
	    } else if (window.ActiveXObject) {
	        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	    } else if(xmlHttp==null) {
	        alert("Browser does not support XMLHTTP Request");
	        return;
	    }
		
		var url="${pageContext.request.contextPath}/floatAmountDetails/getOperator";
		url=url+"?plazaId="+plazaId+"&laneId="+laneId+"&shiftId="+shiftId+"&checkDate="+checkDate;
		
		xmlHttp.onreadystatechange = stateChange1;
        xmlHttp.open("GET", url, true);
        xmlHttp.send(null);
        
        
        if(document.getElementById("tollPlazas").value!="" && document.getElementById("lanes").value!="" && 
           document.getElementById("sid").value!="" && document.getElementById("cdate").value !="") {
    		document.getElementById("sp_update").removeAttribute("class");
     		document.getElementById("sp_update").setAttribute("class","error");
     		
    		document.getElementById("ssp_update").removeAttribute("class");
     		document.getElementById("ssp_update").setAttribute("class","error");
        }
        
	}
	
	function stateChange1() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			 var str = xmlHttp.responseText;

				str = str.trim();
				strs=str.split("=");
 				if (strs[0]==""||strs.length<2||strs==null) {
					 document.getElementById("sp_showuser").removeAttribute("class");
					 document.getElementById("sp_showuser").setAttribute("class", "error_show");
					 
					 document.getElementById("ssp_showuser").removeAttribute("class");
					 document.getElementById("ssp_showuser").setAttribute("class", "error_show");
					 
					 var str = "<option value=''>No Assignee found</option>";
		             document.getElementById("eid").innerHTML = str;
		             
		             document.getElementById("seid").innerHTML = str;
		             return;
				} else {
					 document.getElementById("sp_showuser").removeAttribute("class");
					 document.getElementById("sp_showuser").setAttribute("class", "error");
					 
					 document.getElementById("ssp_showuser").removeAttribute("class");
					 document.getElementById("ssp_showuser").setAttribute("class", "error");
				}
				document.getElementById("sp_showuser").removeAttribute("class");
				document.getElementById("sp_showuser").setAttribute("class", "error");
				
				document.getElementById("ssp_showuser").removeAttribute("class");
				document.getElementById("ssp_showuser").setAttribute("class", "error");
				
				var user = "<option value='"+strs[0]+"'>"+strs[1]+""+strs[2]+"</option>"
				document.getElementById("eid").innerHTML = user;
				document.getElementById("seid").innerHTML = user;
				
				//set floating amount
				document.getElementById("floatingAmount").value=strs[3];
		 }
	}
	
	//for mobile update
	function supdate() {
		document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value;
		document.getElementById("lanes").value=document.getElementById("slanes").value;
		document.getElementById("sid").value=document.getElementById("ssid").value;
		document.getElementById("cdate").value=document.getElementById("scdate").value;
		update();
	}
	
	
	//get the details if already exist
	function update() {
		var plazaId = document.getElementById("tollPlazas").value;
		var laneId = document.getElementById("lanes").value;
		var shiftId = document.getElementById("sid").value;
		var checkDate = document.getElementById("cdate").value;

		if(plazaId=="" || plazaId==null || laneId=="" || laneId==null || shiftId=="" || shiftId==null || checkDate=="" || checkDate==null) {
			document.getElementById("sp_update").removeAttribute("class");
 			document.getElementById("sp_update").setAttribute("class","error_show");
 			
 			
			document.getElementById("ssp_update").removeAttribute("class");
 			document.getElementById("ssp_update").setAttribute("class","error_show");
			document.getElementById("eid").innerHTML = '<option name="emp_id" value=""></option>';
			document.getElementById("seid").innerHTML = '<option name="emp_id" value=""></option>';
 			return;
		}
		
		document.getElementById("sp_update").removeAttribute("class");
 		document.getElementById("sp_update").setAttribute("class","error");
 		
		document.getElementById("ssp_update").removeAttribute("class");
 		document.getElementById("ssp_update").setAttribute("class","error");
 		
		if (typeof XMLHttpRequest != "undefined") {
	        xmlHttp = new XMLHttpRequest();
	    } else if (window.ActiveXObject) {
	        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	    } else if(xmlHttp==null) {
	        alert("Browser does not support XMLHTTP Request");
	        return;
	    }
		
		var url="${pageContext.request.contextPath}/cashup/getValuesForUpdate"
		url = url + "?plazaId="+plazaId+"&laneId="+laneId+"&shiftId="+shiftId+"&checkDate="+checkDate;
		
		xmlHttp.onreadystatechange = stateChange2;
        xmlHttp.open("GET", url, true);
        xmlHttp.send(null);
        
        if(document.getElementById("tollPlazas").value!="" && document.getElementById("lanes").value!="" && 
           document.getElementById("sid").value!="" && document.getElementById("cdate").value !="") {
    		document.getElementById("sp_update").removeAttribute("class");
     		document.getElementById("sp_update").setAttribute("class","error");
     		document.getElementById("sp_showuser").removeAttribute("class");
     		document.getElementById("sp_showuser").setAttribute("class","error");
     		

    		document.getElementById("ssp_update").removeAttribute("class");
     		document.getElementById("ssp_update").setAttribute("class","error");
     		document.getElementById("ssp_showuser").removeAttribute("class");
     		document.getElementById("ssp_showuser").setAttribute("class","error");
        }
	}
	
	function stateChange2() {
		 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
			var str = xmlHttp.responseText;
			 //fill the select with this
			 document.getElementById("hidenDiv").innerHTML = str;
			 
			 document.getElementById("d2000").value = document.getElementById("dtwothousand").innerHTML;
			 document.getElementById("c2000").value = document.getElementById("d2000").value*2000;
			 document.getElementById("C2000").value = document.getElementById("c2000").value;
			 
			 document.getElementById("d1000").value = document.getElementById("dthousand").innerHTML;
			 document.getElementById("c1000").value = document.getElementById("d1000").value*1000;
			 document.getElementById("C1000").value = document.getElementById("c1000").value;
			 
			 document.getElementById("d500").value = document.getElementById("dfivehundred").innerHTML;
			 document.getElementById("c500").value = document.getElementById("d500").value*500;
			 document.getElementById("C500").value = document.getElementById("c500").value;
			 
			 document.getElementById("d200").value = document.getElementById("dtwohundred").innerHTML;
			 document.getElementById("c200").value = document.getElementById("d200").value*200;
			 document.getElementById("C200").value = document.getElementById("c200").value;
			 
			 document.getElementById("d100").value = document.getElementById("dhundred").innerHTML;
			 document.getElementById("c100").value = document.getElementById("d100").value*100;
			 document.getElementById("C100").value = document.getElementById("c100").value;

			 document.getElementById("d50").value = document.getElementById("dfifty").innerHTML;
			 document.getElementById("c50").value = document.getElementById("d50").value*50;
			 document.getElementById("C50").value = document.getElementById("c50").value;
			 
			 document.getElementById("d20").value = document.getElementById("dtwenty").innerHTML;
			 document.getElementById("c20").value = document.getElementById("d20").value*20;
			 document.getElementById("C20").value = document.getElementById("c20").value;
			 
			 document.getElementById("d10").value = document.getElementById("dten").innerHTML;
			 document.getElementById("c10").value = document.getElementById("d10").value*10;
			 document.getElementById("C10").value = document.getElementById("c10").value;
			 
			 document.getElementById("d5").value = document.getElementById("dfive").innerHTML;
			 document.getElementById("c5").value = document.getElementById("d5").value*5;
			 document.getElementById("C5").value = document.getElementById("c5").value;
			 
			 document.getElementById("d2").value = document.getElementById("dtwo").innerHTML;
			 document.getElementById("c2").value = document.getElementById("d2").value*2;
			 document.getElementById("C2").value = document.getElementById("c2").value;
			 
			 document.getElementById("d1").value = document.getElementById("done").innerHTML;
			 document.getElementById("c1").value = document.getElementById("d1").value*1;
			 document.getElementById("C1").value = document.getElementById("c1").value;
			 
			 var oid = document.getElementById("operatorid").innerHTML;
			 var ofname =  document.getElementById("operatorfirstname").innerHTML;
			 var olname =  document.getElementById("operatorlastname").innerHTML;
			 document.getElementById("eid").innerHTML = "<option value='"+oid+"'>"+ofname+""+olname+"</option>"
			 document.getElementById("floatingAmount").value="0.00";
			 
			 var totalAmount =
				 parseInt(document.getElementById("C2000").value)+
				 parseInt(document.getElementById("C1000").value)+
				 parseInt(document.getElementById("C500").value)+
				 parseInt(document.getElementById("C200").value)+
				 parseInt(document.getElementById("C100").value)+
				 parseInt(document.getElementById("C50").value)+
				 parseInt(document.getElementById("C20").value)+
				 parseInt(document.getElementById("C10").value)+
				 parseInt(document.getElementById("C5").value)+
				 parseInt(document.getElementById("C2").value)+
				 parseInt(document.getElementById("C1").value);
			 
			 document.getElementById("total").value=totalAmount;
			 document.getElementById("Total").value=totalAmount;
			 
			 
			 
			 //for mobile
			 document.getElementById("hidenDiv").innerHTML = str;
			 
			 document.getElementById("sd2000").value = document.getElementById("dtwothousand").innerHTML;
			 document.getElementById("sc2000").value = document.getElementById("d2000").value*2000;
			 document.getElementById("sC2000").value = document.getElementById("c2000").value;
			 
			 document.getElementById("sd1000").value = document.getElementById("dthousand").innerHTML;
			 document.getElementById("sc1000").value = document.getElementById("d1000").value*1000;
			 document.getElementById("sC1000").value = document.getElementById("c1000").value;
			 
			 document.getElementById("sd500").value = document.getElementById("dfivehundred").innerHTML;
			 document.getElementById("sc500").value = document.getElementById("d500").value*500;
			 document.getElementById("sC500").value = document.getElementById("c500").value;
			 
			 document.getElementById("sd200").value = document.getElementById("dtwohundred").innerHTML;
			 document.getElementById("sc200").value = document.getElementById("d200").value*200;
			 document.getElementById("sC200").value = document.getElementById("c200").value;
			 
			 document.getElementById("sd100").value = document.getElementById("dhundred").innerHTML;
			 document.getElementById("sc100").value = document.getElementById("d100").value*100;
			 document.getElementById("sC100").value = document.getElementById("c100").value;

			 document.getElementById("sd50").value = document.getElementById("dfifty").innerHTML;
			 document.getElementById("sc50").value = document.getElementById("d50").value*50;
			 document.getElementById("sC50").value = document.getElementById("c50").value;
			 
			 document.getElementById("sd20").value = document.getElementById("dtwenty").innerHTML;
			 document.getElementById("sc20").value = document.getElementById("d20").value*20;
			 document.getElementById("sC20").value = document.getElementById("c20").value;
			 
			 document.getElementById("sd10").value = document.getElementById("dten").innerHTML;
			 document.getElementById("sc10").value = document.getElementById("d10").value*10;
			 document.getElementById("sC10").value = document.getElementById("c10").value;
			 
			 document.getElementById("sd5").value = document.getElementById("dfive").innerHTML;
			 document.getElementById("sc5").value = document.getElementById("d5").value*5;
			 document.getElementById("sC5").value = document.getElementById("c5").value;
			 
			 document.getElementById("sd2").value = document.getElementById("dtwo").innerHTML;
			 document.getElementById("sc2").value = document.getElementById("d2").value*2;
			 document.getElementById("sC2").value = document.getElementById("c2").value;
			 
			 document.getElementById("sd1").value = document.getElementById("done").innerHTML;
			 document.getElementById("sc1").value = document.getElementById("d1").value*1;
			 document.getElementById("sC1").value = document.getElementById("c1").value;
			 
			 var oid = document.getElementById("operatorid").innerHTML;
			 var ofname =  document.getElementById("operatorfirstname").innerHTML;
			 var olname =  document.getElementById("operatorlastname").innerHTML;
			 document.getElementById("seid").innerHTML = "<option value='"+oid+"'>"+ofname+""+olname+"</option>"
			 document.getElementById("floatingAmount").value="0.00";
			 
			 var totalAmount =
				 parseInt(document.getElementById("sC2000").value)+
				 parseInt(document.getElementById("sC1000").value)+
				 parseInt(document.getElementById("sC500").value)+
				 parseInt(document.getElementById("sC200").value)+
				 parseInt(document.getElementById("sC100").value)+
				 parseInt(document.getElementById("sC50").value)+
				 parseInt(document.getElementById("sC20").value)+
				 parseInt(document.getElementById("sC10").value)+
				 parseInt(document.getElementById("sC5").value)+
				 parseInt(document.getElementById("sC2").value)+
				 parseInt(document.getElementById("sC1").value);
			 
			 document.getElementById("stotal").value=totalAmount;
			 document.getElementById("sTotal").value=totalAmount;
			 
			 
			 if (document.getElementById("dtwothousand").innerHTML=="") {
				document.getElementById("sp_operator").removeAttribute("class");
		 		document.getElementById("sp_operator").setAttribute("class","error_show");
		 			
		 			
				document.getElementById("ssp_operator").removeAttribute("class");
		 		document.getElementById("ssp_operator").setAttribute("class","error_show");
			 } else {
					document.getElementById("sp_operator").removeAttribute("class");
			 		document.getElementById("sp_operator").setAttribute("class","error");
		     		document.getElementById("sp_showuser").removeAttribute("class");
		     		document.getElementById("sp_showuser").setAttribute("class","error");
			 			
					document.getElementById("ssp_operator").removeAttribute("class");
			 		document.getElementById("ssp_operator").setAttribute("class","error");
		     		document.getElementById("ssp_showuser").removeAttribute("class");
		     		document.getElementById("ssp_showuser").setAttribute("class","error");
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
			<div class="col-sm-12 header" style=" min-width: 102%;">
				<div class="row">
					<div class="col-xs-2" style="margin-left: 1%">
						<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
					</div>
					<div class="col-xs-5">
						<h4 class="heading" style="font-size: 0.9em">Cashup Declaration</h4>
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
		<!-- side bar -->
		<div class="col-sm-2 sidebar" style="min-height: 800px;">
			<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: 10%">Back</span></a>
		</div>
		
		<!-- main content -->
		<div id="demo" class="col-sm-10 maincontent">
			<div class="row">
				<form action="showBalance" id="form" method="POST">
				<input type="text" id="floatingAmount" name="floatingAmount" hidden/> 
					<div class="col-sm-12 form" style="width: 60%;">
						<span class="error_show">${error}</span>
						
						<div style="margin-left: 50%">
						<label>DATE: </label><input onchange="delUser()" type="date" name="checkingDate" value="${today}" id="cdate" onblur="showUser()" style="background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"/>
						<span class="error" id="sp_date">Please select a date</span><br>
						</div>
						
						<div>
							<label>TOLL PLAZA: </label>
							<select class="inp" name="tollPlazaId" id="tollPlazas" onchange="showLanesCodes(this.value)">
								<c:forEach var="tempPlaza" items="${allTollPlazas}">
									<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
								</c:forEach>
							</select>
							<span class="error" id="sp_plazas">Please Select a plaza</span>
						</div>
							
								
						<div>
							<label>LANE: </label>
							<select class="inp" onchange="delUser()"  name="laneId" id="lanes">
							<option value=''>Select a lane</option>
								<c:forEach var="tempLane" items="${allLanes}">
									<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
								</c:forEach>
							</select>
							<span class="error" id="sp_lanes">Please Select a Lane</span>
						</div>
						
						<div>	
						<label>SHIFT: </label>
						<select  name="shiftId" onchange="delUser()" id="sid" class="inp">
							<option value="">Select a shift</option>
							<c:forEach var="tempShift" items="${allShifts}">
								<option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option>
							</c:forEach>							
						</select>
						<span class="error_show">${shiftError}</span>
						<span class="error" id="sp_shifts">Please Select a shift</span>
				
						</div>
							
						<div style="margin-left: 40%">
							<input type="button" class="inp1" value="Get Operator" onclick="showUser()"/>
							<input type="button" class="inp1" value="Update" onclick="update()"/>	
						</div>
						
						<div>
							<span class="error" id="sp_showuser">Select a date, lane and a shift before clicking me</span>
							<span class="error" id="sp_update">Select a date, a lane and a shift before clicking me</span>
						</div>
						
						<div>				  
						<label>Operator: </label> 
						<select  name="userId" id="eid" class="inp">
							<option name="emp_id" value=""></option>
						</select>
						<span class="error" id="sp_operator">Please Set FloatAmountDetails before declaring cashup</span>
						</div>
						
						<div style="margin-left: 15%;">
							<table>
								
								<tr class="table">
									<td width="100px">2000</td><td><input class="digs" value="0" type="number" name="d2000" id="d2000" min="0"/></td><td><input name="c2000"  id="c2000" class="digs1" disabled/><input name="C2000"  id="C2000" value="0" hidden/></td>
								</tr>
								
								<tr class="table">
									<td width="100px">1000</td><td><input class="digs" value="0" type="number" name="d1000" id="d1000" min="0"/></td><td><input name="c1000" id="c1000" class="digs1" disabled/><input name="C1000" id="C1000" value="0" hidden/></td>
								</tr>
								
								<tr class="table">
									<td width="100px">500</td><td><input class="digs" value="0" type="number" name="d500" id="d500" min="0"/></td><td><input name="c500" id="c500" class="digs1" disabled/><input name="C500" id="C500" value="0" hidden/></td>
								</tr>
								
								<tr class="table">
									<td width="100px">200</td><td><input class="digs" value="0" type="number" name="d200" id="d200" min="0"/></td><td><input name="c200" id="c200" class="digs1" disabled/><input name="C200" id="C200" value="0" hidden/></td>
								</tr>
								
								<tr class="table">
									<td width="100px">100</td><td><input class="digs" value="0" type="number" name="d100" id="d100" min="0"/></td><td><input name="c100" id="c100" class="digs1" disabled/><input name="C100" id="C100" value="0" hidden/></td>
								</tr>
								
								<tr class="table">
									<td width="100px">50</td><td><input class="digs" value="0" type="number" name="d50" id="d50" min="0"/></td><td><input name="c50" id="c50" class="digs1" disabled/><input name="C50" id="C50" value="0" hidden/></td>
								</tr>
								
								<tr class="table">
									<td width="100px">20</td><td><input class="digs" value="0" type="number" name="d20" id="d20" min="0"/></td><td><input name="c20" id="c20" class="digs1" disabled/><input name="C20" id="C20" value="0" hidden/></td>
								</tr>
								
								<tr class="table">
									<td width="100px">10</td><td><input class="digs" value="0" type="number" name="d10"  id="d10" min="0"/></td><td><input name="c10" id="c10" class="digs1" disabled/><input name="C10" id="C10" value="0" hidden/></td>
								</tr>
								
								<tr class="table">
									<td width="100px">5</td><td><input class="digs" value="0" type="number" name="d5" id="d5" min="0"/></td><td><input name="c5" id="c5" class="digs1" disabled/><input name="C5" id="C5" value="0" hidden/></td>
								</tr>
								
								<tr class="table">
									<td width="100px">2</td><td><input class="digs" value="0" type="number" name="d2" id="d2" min="0"/></td><td><input name="c2" id="c2" class="digs1" disabled/><input name="C2" id="C2" value="0" hidden/></td>
								</tr>
								
								<tr class="table">
									<td width="100px">1</td><td><input class="digs" value="0" type="number" name="d1" id="d1" min="0"/></td><td><input name="c1" id="c1" class="digs1" disabled/><input name="C1" id="C1" value="0" hidden/></td>
								</tr>
								
								<tr class="table">
									<td></td><td>TOTAL: </td><td><input name="total" id="total" value="0" class="digs1" disabled/><input name="Total" id="Total" value="0" hidden/></td>
								</tr>
								<span id="sp_digs" class="error">Please enter digits only</span>
							</table>
						</div>
						
							<div style="margin-bottom: 3%; margin-left: 10%">
									<input type="button" id="btn" class="inp1" onclick="checkOnce()" value="Create"> 

									<input type="button" id="btn" class="inp1" onclick="clearForm()" value="Clear"> 
									
									<input type="button" id="btn" class="inp1"  value="Cancel" onclick="window.location.href='${pageContext.request.contextPath}/index/h0me';"> 
									
							</div>
					</div>
				</form>
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
	</div>
	<div id="hidenDiv" style="display: none;"></div>
	<input id="response" value="" style="display: none;"/>
	
		<!-- The Modal -->
	<div id="myModal" class="modal" style="">
	
	  <!-- Modal content -->
	  <div class="modal-content" style="position:relative; top:25%;left: -50px; max-width: 500px; margin-left: 25%; background-color: #b9b9b9">
	  	<!-- uncomment this if you want cross on the top right -->
	    <!-- <span class="close">&times;</span> -->
	    <p>Are you sure! Do you want to submit?</p>
	    	
	    <div class="row">
	    	<div class="col-xs-5">
	    		<input type="button" class="inp1" value="No" id="no" onclick="f2()" style="background-color: #474747; max-width: 80%" />
			</div>
			
			<div class="col-xs-5">
				<input type="button" class="inp1" value="Yes" id="yes" onclick="f1()" style="max-width: 80%"/>
			</div>
			
		</div>
	  </div>
	
	</div>
	
	
	<div class="visible-xs container" style="margin: 0px; padding: 0px">
		<div class="row">
			<div class="col-xs-12" style="background-color: #ee9620">
				<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' style="margin-left: 15%; margin-bottom: 10%" width="60%"/>
				<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28" style="margin-left: 15%" onclick="window.location.href='${pageContext.request.contextPath}/index/logout';">
				<br><span style="margin-left:20%">Cashup Declaration</span>
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
			<label>DATE: </label><input onchange="delUser()" type="date" name="checkingDate" value="${today}" id="scdate" onblur="showUser()" style="background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"/>
			<span class="error" id="ssp_date">Please select a date</span><br>
		</div>
	
		<div class="col-xs-12">
			<label>TOLL PLAZA: </label>
			<select class="inp" name="tollPlazaId" id="stollPlazas" onchange="showLanesCodes(this.value)" style="padding-left: 0%">
				<c:forEach var="tempPlaza" items="${allTollPlazas}">
					<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
				</c:forEach>
			</select>
			<span class="error" id="ssp_plazas">Please Select a plaza</span>
		</div>
		
		<div class="col-xs-12">
			<label>LANE: </label>
			<select class="inp" onchange="delUser()"  name="laneId" id="slanes" style="padding-left: 0%">
				<option value=''>Select a lane</option>
					<c:forEach var="tempLane" items="${allLanes}">
						<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
					</c:forEach>
			</select>
			<span class="error" id="ssp_lanes">Please Select a Lane</span>
		</div>
		
		<div class="col-xs-12">
			<label>SHIFT: </label>
			<select  name="shiftId" onchange="delUser()" id="ssid" class="inp" style="padding-left: 0%">
				<option value="">Select a shift</option>
				<c:forEach var="tempShift" items="${allShifts}">
					<option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option>
				</c:forEach>							
			</select>
			<span class="error_show">${shiftError}</span>
			<span class="error" id="ssp_shifts">Please Select a shift</span>
		</div>
		
		<div class="col-xs-12">
			<input type="button" class="inp1" value="Get Operator" onclick="sshowUser()"/>
			<input type="button" class="inp1" value="Update" onclick="supdate()"/>	
		</div>
		
		<div class="col-xs-12">
			<span class="error" id="ssp_showuser">Select a date, lane and a shift before clicking me</span>
			<span class="error" id="ssp_update">Select a date, a lane and a shift before clicking me</span>
		</div>
		
		<div class="col-xs-12">
			<label>Operator: </label> 
			<select  name="userId" id="seid" class="inp">
				<option name="emp_id" value=""></option>
			</select>
			<span class="error" id="ssp_operator">Please Set FloatAmountDetails before declaring cashup</span>
		</div>
		
		<div class="col-xs-12">
			<table>
				<tr class="table">
					<td width="100px">2000</td><td width="100px"><input class="digs" value="0" type="number" name="d2000" id="sd2000" min="0" style="max-width:100px"/></td><td><input name="c2000"  id="sc2000" class="digs1" disabled style="max-width:100px"/><input name="C2000"  id="sC2000" value="0" hidden/></td>
				</tr>
							
				<tr class="table">
					<td width="100px">1000</td><td width="100px"><input class="digs" value="0" type="number" name="d1000" id="sd1000" min="0" style="max-width:100px"/></td><td><input name="c1000" id="sc1000" class="digs1" disabled style="max-width:100px"/><input name="C1000" id="sC1000" value="0" hidden/></td>
				</tr>
								
				<tr class="table">
					<td width="100px">500</td><td width="100px"><input class="digs" value="0" type="number" name="d500" id="sd500" min="0" style="max-width:100px"/></td><td><input name="c500" id="sc500" class="digs1" disabled style="max-width:100px"/><input name="C500" id="sC500" value="0" hidden/></td>
				</tr>
								
				<tr class="table">
					<td width="100px">200</td><td width="100px"><input class="digs" value="0" type="number" name="d200" id="sd200" min="0" style="max-width:100px"/></td><td><input name="c200" id="sc200" class="digs1" disabled style="max-width:100px"/><input name="C200" id="sC200" value="0" hidden/></td>
				</tr>
								
				<tr class="table">
					<td width="100px">100</td><td width="100px"><input class="digs" value="0" type="number" name="d100" id="sd100" min="0" style="max-width:100px"/></td><td><input name="c100" id="sc100" class="digs1" disabled style="max-width:100px"/><input name="C100" id="sC100" value="0" hidden/></td>
				</tr>
								
				<tr class="table">
					<td width="100px">50</td><td width="100px"><input class="digs" value="0" type="number" name="d50" id="sd50" min="0" style="max-width:100px"/></td><td><input name="c50" id="sc50" class="digs1" disabled style="max-width:100px"/><input name="C50" id="sC50" value="0" hidden/></td>
				</tr>
								
				<tr class="table">
					<td width="100px">20</td><td width="100px"><input class="digs" value="0" type="number" name="d20" id="sd20" min="0" style="max-width:100px"/></td><td><input name="c20" id="sc20" class="digs1" disabled style="max-width:100px"/><input name="C20" id="sC20" value="0" hidden/></td>
				</tr>
								
				<tr class="table">
					<td width="100px">10</td><td width="100px"><input class="digs" value="0" type="number" name="d10"  id="sd10" min="0" style="max-width:100px"/></td><td><input name="c10" id="sc10" class="digs1" disabled style="max-width:100px"/><input name="C10" id="sC10" value="0" hidden/></td>
				</tr>
								
				<tr class="table">
					<td width="100px">5</td><td width="100px"><input class="digs" value="0" type="number" name="d5" id="sd5" min="0" style="max-width:100px"/></td><td><input name="c5" id="sc5" class="digs1" disabled style="max-width:100px"/><input name="C5" id="sC5" value="0" hidden/></td>
				</tr>
								
				<tr class="table">
					<td width="100px">2</td><td width="100px"><input class="digs" value="0" type="number" name="d2" id="sd2" min="0" style="max-width:100px"/></td><td><input name="c2" id="sc2" class="digs1" disabled style="max-width:100px"/><input name="C2" id="sC2" value="0" hidden/></td>
				</tr>
								
				<tr class="table">
					<td width="100px">1</td><td width="100px"><input class="digs" value="0" type="number" name="d1" id="sd1" min="0" style="max-width:100px"/></td><td><input name="c1" id="sc1" class="digs1" disabled style="max-width:100px"/><input name="C1" id="sC1" value="0" hidden/></td>
				</tr>
								
				<tr class="table">
					<td width="100px"></td><td width="100px">TOTAL: </td><td><input name="total" id="stotal" value="0" class="digs1" disabled style="max-width:100px"/><input name="Total" id="sTotal" value="0" hidden/></td>
				</tr>
				<span id="ssp_digs" class="error">Please enter digits only</span>
			</table>
		</div>
		
		<div class="row">
			<div class="col-xs-4">
				<input type="button" id="btn" class="inp1" onclick="scheckOnce()" value="Create" style="max-width: 90%"> 
			</div>
			
			<div class="col-xs-4">
				<input type="button" id="btn" class="inp1" onclick="clearForm()" value="Clear" style="max-width: 90%"> 
			</div>					
			
			<div class="col-xs-4">
				<input type="button" id="btn" class="inp1"  value="Cancel" onclick="window.location.href='${pageContext.request.contextPath}/index/h0me';" style="max-width: 90%"> 
			</div>
	</div>

	<script src="${pageContext.request.contextPath}/resources/js/cashup.js"></script>
</body>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<%-- <div class="inputData">
		<span class="error_show">${error}</span>

		<form action="showBalance" id="form" method="POST">
		
		<label>DATE: </label><input class="inp" onchange="delUser()" type="date" name="checkingDate" id="cdate" onblur="showUser()"/>
		<span class="error" id="sp_date">Please select a date</span><br>
		<div>
			<label>TOLL PLAZAS: </label>
			<select class="inp" name="tollPlazaId" id="tollPlazas" onchange="showLanesCodes(this.value)">
				<c:forEach var="tempPlaza" items="${allTollPlazas}">
					<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
				</c:forEach>
			</select>
			<span class="error" id="sp_plazas">Please Select a plaza</span>
		</div>
			
				
		<div>
			<label>LANES: </label>
			<select class="inp" onchange="delUser()"  name="laneId" id="lanes">
			<option value=''>Select a lane</option>
				<c:forEach var="tempLane" items="${allLanes}">
					<option value="${tempLane.laneId}">${tempLane.laneCode}</option>
				</c:forEach>
			</select>
			<span class="error" id="sp_lanes">Please Select a Lane</span>
		</div>
		
		<div>	
		<label>SHIFT: </label>
		<select  name="shiftId" onchange="delUser()" id="sid" class="inp">
			<option value="">Select a shift</option>
			<c:forEach var="tempShift" items="${allShifts}">
				<option value="${tempShift.shiftId}">${tempShift.shiftDesc}</option>
			</c:forEach>							
		</select>
		<span class="error_show">${shiftError}</span>
		<span class="error" id="sp_shifts">Please Select a shift</span>

		</div>
			
		<div>
			<input type="button" class="inp1" value="GetOperator" onclick="showUser()"/>
			<span class="error" id="sp_showuser">Select a date, lane and a shift before clicking me</span>
			<input type="button" class="inp1" value="Update" onclick="update()"/>
			<span class="error" id="sp_update">Select a date, a lane and a shift before clicking me</span>
		</div>
			
		<div>				  
		<label>Operator: </label> 
		<select  name="userId" id="eid" class="inp">
			<option name="emp_id" value=""></option>
		</select>
		<span class="error" id="sp_operator">Please Set FloatAmountDetails before declaring cashup</span>
		</div>
		
		
			<table>
				
				<tr>
					<td>2000</td><td><input class="digs" value="0" type="number" name="d2000" id="d2000" min="0"/></td><td><input name="c2000"  id="c2000" disabled/><input name="C2000"  id="C2000" value="0" hidden/></td>
				</tr>
				
				<tr>
					<td>1000</td><td><input class="digs" value="0" type="number" name="d1000" id="d1000" min="0"/></td><td><input name="c1000" id="c1000" disabled/><input name="C1000" id="C1000" value="0" hidden/></td>
				</tr>
				
				<tr>
					<td>500</td><td><input class="digs" value="0" type="number" name="d500" id="d500" min="0"/></td><td><input name="c500" id="c500" disabled/><input name="C500" id="C500" value="0" hidden/></td>
				</tr>
				
				<tr>
					<td>200</td><td><input class="digs" value="0" type="number" name="d200" id="d200" min="0"/></td><td><input name="c200" id="c200" disabled/><input name="C200" id="C200" value="0" hidden/></td>
				</tr>
				
				<tr>
					<td>100</td><td><input class="digs" value="0" type="number" name="d100" id="d100" min="0"/></td><td><input name="c100" id="c100" disabled/><input name="C100" id="C100" value="0" hidden/></td>
				</tr>
				
				<tr>
					<td>50</td><td><input class="digs" value="0" type="number" name="d50" id="d50" min="0"/></td><td><input name="c50" id="c50" disabled/><input name="C50" id="C50" value="0" hidden/></td>
				</tr>
				
				<tr>
					<td>20</td><td><input class="digs" value="0" type="number" name="d20" id="d20" min="0"/></td><td><input name="c20" id="c20" disabled/><input name="C20" id="C20" value="0" hidden/></td>
				</tr>
				
				<tr>
					<td>10</td><td><input class="digs" value="0" type="number" name="d10"  id="d10" min="0"/></td><td><input name="c10" id="c10" disabled/><input name="C10" id="C10" value="0" hidden/></td>
				</tr>
				
				<tr>
					<td>5</td><td><input class="digs" value="0" type="number" name="d5" id="d5" min="0"/></td><td><input name="c5" id="c5" disabled/><input name="C5" id="C5" value="0" hidden/></td>
				</tr>
				
				<tr>
					<td>2</td><td><input class="digs" value="0" type="number" name="d2" id="d2" min="0"/></td><td><input name="c2" id="c2" disabled/><input name="C2" id="C2" value="0" hidden/></td>
				</tr>
				
				<tr>
					<td>1</td><td><input class="digs" value="0" type="number" name="d1" id="d1" min="0"/></td><td><input name="c1" id="c1" disabled/><input name="C1" id="C1" value="0" hidden/></td>
				</tr>
				
				<tr>
					<td></td><td>TOTAL: </td><td><input name="total" id="total" value="0" disabled/><input name="Total" id="Total" value="0" hidden/></td>
				</tr>
				<span id="sp_digs" class="error">Please enter digits only</span>
			</table>
			<tr><td></td>
				<td>	<input type="button" id="btn" class="inp1" onclick="checkOnce()" value="Submit"> </td>
				<td>	<input type="button" id="btn" class="inp1" onclick="clearForm()" value="Clear"> </td>
				<td>	<input type="button" id="btn" class="inp1"  value="Back"> </td>
				<td></td>
			</tr>
		</form>
	</div>
	<div id="hidenDiv" style="display: none;"></div>
	<script src="${pageContext.request.contextPath}/resources/js/cashup.js"></script>
</body> --%>
</html>