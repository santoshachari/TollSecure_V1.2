<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Toll Config Form</title>

	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/all_style.css">
	
	<script type="text/javascript">
	
	//for myFunction in mobiles
	function sMyFunction() {
		document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value;
		document.getElementById("vehicleclass").value=document.getElementById("svehicleclass").value;
		document.getElementById("stdate").value=document.getElementById("sstdate").value;
		document.getElementById("eddate").value=document.getElementById("seddate").value;
		if (document.getElementById("sr1").checked==true) {
			document.getElementById("r1").checked=true;
		} else {
			document.getElementById("r2").checked=true;
		}
		MyFunction();
	}
	
	//following script is for getting rate from rate.jsp using ajax
	 function MyFunction() {
			showAmount(document.getElementById("vehicleclass").value);
		}

		var xmlHttp;
		function showAmount(str) {
			//clear the update field if a value is in it
			document.getElementById("tollAmtNew").value="";
			var jType ="";
            var cashtype ="";
            var stdate = document.getElementById("stdate").value;
            var eddate = document.getElementById("eddate").value;
            var startDate = new Date(stdate);
            var endDate = new Date(eddate);
            
            
            if (startDate == 'Invalid Date' || endDate == 'Invalid Date') {
            	var str = "<option value=''></option>";
	            document.getElementById("tollAmt").innerHTML = str;
	            document.getElementById("stollAmt").innerHTML = str;
            	return;
            }
            
           if (startDate > endDate) {
        	   var str = "<option value=''></option>";
	           document.getElementById("tollAmt").innerHTML = str;
	           document.getElementById("stollAmt").innerHTML = str;
               return;
            } 
            
			/* var chx = document.getElementsByName("journeyType");
			
			for (var i=0; i<chx.length; i++) {
                if(chx[i].checked) {
                	jType = chx[i].value;
                	alert(chx[i].value);
                }
            }  */
            
            if (document.getElementById("r1").checked==true) {
            	jType="S";
            } else {
            	jType="R";
            }
			

			 if (jType=="" || jType==  null) {
	              var str = "<option value=''></option>";
	              document.getElementById("tollAmt").innerHTML = str;
	              document.getElementById("stollAmt").innerHTML = str;
	              return;
	         }
			 
			 if(str=="" || str==null) {
				 var str = "<option value=''></option>";
			     document.getElementById("tollAmt").innerHTML = str;
			     document.getElementById("stollAmt").innerHTML = str;
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
			 
			 var plaza = document.getElementById("tollPlazas").value;
			 var url="${pageContext.request.contextPath}/tollTransaction/rate1";
			 url += "?vehicle="+str+"&jType="+jType+"&plazaId="+plaza+"&sDate="+stdate+"&eDate="+eddate;
			 
			 xmlHttp.onreadystatechange = stateChange;
	         xmlHttp.open("GET", url, true);
	         xmlHttp.send(null);
		}
		
		function stateChange() {
			 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {

				var str = xmlHttp.responseText;
				str = str.trim();
				str = str.slice(1,-1);
				str = str.trim();
				var strs = str.split("=");
				
				//if it is empty
				if (strs[0]=="" || strs==null ||strs.length < 2) {
					var str = "<option value=''></option>";
		            document.getElementById("tollAmt").innerHTML = str;
		            document.getElementById("stollAmt").innerHTML = str;
				} else {
					var amt = strs[1];
					document.getElementById("tollAmt").innerHTML = "<option value='"+amt+"'>"+amt+"</option>";
					document.getElementById("stollAmt").innerHTML = "<option value='"+amt+"'>"+amt+"</option>";
				}
				
			 }
		}		

	</script>
	
</head>
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
								<h4 class="heading" style="font-size: 0.9em">Configure Vehicle Class</h4>
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
			
					<!-- Sidebar -->
						<div class="col-sm-2 sidebar" style="min-height: 650px;">
							<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: 10%">Back</span></a>
						</div>
						
					
					<!-- Main content -->
					<div class="col-sm-10 maincontent">
						<div class="row">
							<form method="POST" action="saveTollConfig" id="form">
								<div class="col-sm-12 form">
									<div class="message">${message}</div>
								<!-- these dates are not yet used in back end -->
									<div>
										<label>START DATE</label>
										<%-- <input type="date" id="stdate" onchange="MyFunction()" style="background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"> --%>
										<input type="text" class="inp" id="demo" style="width: 25%" disabled/>
										<input type="text" id="stdate" name="startDate" style="display: none">
										
										<label>END DATE</label>
										<input type="date" id="eddate" name="endDate" value="${date}" onchange="MyFunction()" style="background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;">
									</div>
									
									<div>
										<span class="error" id="sp_date">Start date should be smaller than end date</span>
										<span class="error" id="sp_small">Please fill the date fields</span>
									</div>
									
									<div>
										<label>TOLL PLAZA: </label>
										<select class="inp" name="plazaId" id="tollPlazas" onchange="MyFunction()">
											<c:forEach var="tempPlaza" items="${allTollPlazas}">
												<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
											</c:forEach>
										</select>
									</div>
									
									<div>
										<label>VEHICLE CLASS:</label>
										<select onchange="showAmount(this.value)"  name="vehicleClass" class="inp" id="vehicleclass">
											<option value=''>Select a Vehicle Class</option>
											<option value='CAR/ JEEP'>CAR/ JEEP</option>
											<option value='LCV'>LCV</option>
											<option value='BUS/ TRUCK'>BUS/ TRUCK</option> 
											<option value='3 AXEL'>3 AXEL</option>
											<option value='HCM/ MAV'>HCM/ MAV</option>
											<option value='OVERSIZED'>OVERSIZED</option>
										</select>
										<span class="error" id="sp_vclass">Please select a vehicle class</span>
									</div>
										
									<div class="black" style=" padding-bottom: 3%; margin-left: 10%">
										<h4>Journey Type</h4>
										<div>
											
											<input id = "r1" class="rad" type="radio" name="journeyType" value="S" onclick="MyFunction()" checked style="margin-left: 10%; margin-right: 0px">
											<label style="margin-left: 0px">Single</label>
											
											<input id = "r2" class="rad" type="radio" name="journeyType" value="R" onclick="MyFunction()" style="margin-left: 10%; margin-right: 0px"> 
											<label style="margin-left: 0px">Return</label>
			
											<label>Amount</label>
											<select id="tollAmt" class="inp" style="width: 20%; background-color: #000000; padding: 0px">			
												<option value=''></option> 
											</select>
												
										</div>
			
										<div>
											<input type="button" class="inp1" value="Update" onclick="update()" style="margin-left: 60%;"/>
											<span class="error" id="sp_update">Please select a vehicle class and press update</span>
										</div>
									</div>
					
					
									<div>
										<label>NEW AMOUNT:</label>
										<input type="number" value="" name="tollAmt" class="inp" id="tollAmtNew"></input>
									</div>
					
									<div style="margin-left: 20%;">
									   <input class="inp1" type="button" value="Create" onclick = "submit1()"/>
									
										<input class="inp1" type="button" value="Clear" onclick ="clear1()"/>
										
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
						<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28" style="margin-left: 15%" onclick="window.location.href='${pageContext.request.contextPath}/index/logout';">
						<br><span style="margin-left:20%">Vehicle Class Configuration</span>
					</div>
				</div>
			</div>	
			
			<div class="row black_header visible-xs" style=" margin: 0px; min-height: 70px; border-radius: 10px; background-color: #474747; text-align: center; font-size: large; margin-left: 1%; color: #ffffff;">
				<div class="col_xs_12">
					<a href='${pageContext.request.contextPath}/index/h0me?tabNumber=0' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -40%; margin-top:25%; color: #474747">;&nbsp Back ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Back</a>
				</div>
			
				<div class="message" class="col-xs-12">${message}</div>
				
				<div class="col-xs-12">
					<label>START DATE</label>
					<%-- <input type="date" id="stdate" onchange="MyFunction()" style="background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;"> --%>
					<input type="text" class="inp" id="sdemo" style="width: 25%" disabled/>
					<input type="text" id="sstdate" name="startDate" style="display: none">
				</div>
				
				<div class="col-xs-12"> 			
					<label>END DATE</label>
					<input type="date" id="seddate" name="endDate" value="${date}" onchange="sMyFunction()" style="background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;">
				</div>
				
				<div class="col-xs-12">
					<span class="error" id="ssp_date">Start date should be smaller than end date</span>
					<span class="error" id="ssp_small">Please fill the date fields</span>
				</div>
			
				<div class="col-xs-12">
					<label>TOLL PLAZA: </label>
					<select class="inp" name="plazaId" id="stollPlazas" onchange="sMyFunction()" style="max-width: 50%; padding-left: 0px">
						<c:forEach var="tempPlaza" items="${allTollPlazas}">
							<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
						</c:forEach>
					</select>
				</div>
									
				<div class="col-xs-12">
					<label>VEHICLE CLASS:</label>
					<select onchange="sMyFunction()"  name="vehicleClass" class="inp" id="svehicleclass" style="max-width: 50%; padding-left: 0px">
						<option value=''>Select a Vehicle Class</option>
						<option value='CAR/ JEEP'>CAR/ JEEP</option>
						<option value='LCV'>LCV</option>
						<option value='BUS/ TRUCK'>BUS/ TRUCK</option> 
						<option value='3 AXEL'>3 AXEL</option>
						<option value='HCM/ MAV'>HCM/ MAV</option>
						<option value='OVERSIZED'>OVERSIZED</option>
					</select>
					<span class="error" id="ssp_vclass">Please select a vehicle class</span>
				</div>
		
		
				<div class="black col-xs-12" style=" padding-bottom: 3%; margin-left: 10%; color: white">
					<h4>Journey Type</h4>
					<div class="col-xs-12">				
						<input id = "sr1" class="rad" type="radio" name="journeyType" value="S" onclick="sMyFunction()" checked style="margin-left: 10%; margin-right: 0px">
						<label style="margin-left: 0px">Single</label>

						<input id = "sr2" class="rad" type="radio" name="journeyType" value="R" onclick="sMyFunction()" style="margin-left: 10%; margin-right: 0px"> 
						<label style="margin-left: 0px">Return</label>
					</div>
					
					<div class="col-s-12">
						<label>Amount</label>
						<select id="stollAmt" class="inp" style="width: 20%; background-color: #000000; padding: 0px">			
							<option value=''></option> 
						</select>							
					</div>
					
					<div class="col-s-12">
						<input type="button" class="inp1" value="Update" onclick="supdate()" style="margin-left: 0%;"/>
						<span class="error" id="ssp_update">Please select a vehicle class and press update</span>
					</div>
					
				</div>
				
				<div>
					<label>NEW AMOUNT:</label>
					<input type="number" value="" name="tollAmt" class="inp" id="stollAmtNew"></input>
				</div>
				
				<div class="row">
					<div class="col-xs-4">
						<input class="inp1" type="button" value="Create" onclick = "ssubmit1()" style="max-width: 90%"/>
					</div>
					
					<div class="col-xs-4">				
						<input class="inp1" type="button" value="Clear" onclick ="clear1()" style="max-width: 90%"/>
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
			
						
			
			
			
		<script src="${pageContext.request.contextPath}/resources/js/toll_config_form.js"></script>
	</body>
</html>
















