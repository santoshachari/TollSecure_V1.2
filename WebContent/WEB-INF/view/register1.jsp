<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<title>Registration Page</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/all_style.css">
	
<!-- connecting to jQuery -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jQuery.js"></script>
	
	<%--For updating ajax --%>
	<script>
		function getRemainingFields() {
			//get the details of the user with user name and password
			var firstName = document.getElementById("fname").value;
			var lastName = document.getElementById("lname").value;
			var password = document.getElementById("pass").value;
			var er1 = false;
			var er2 = false;
			var er3 = false;
			
			if (firstName.trim()=="") {
				document.getElementById("sp_fname").removeAttribute("class");
				document.getElementById("sp_fname").setAttribute("class", "error_show");
				er1=true;
			} else {
				document.getElementById("sp_fname").removeAttribute("class");
				document.getElementById("sp_fname").setAttribute("class", "error");
				er1=false;
			}
			
			if (lastName.trim()=="") {
				document.getElementById("sp_lname").removeAttribute("class");
				document.getElementById("sp_lname").setAttribute("class", "error_show");
				er2=true;
			} else {
				document.getElementById("sp_lname").removeAttribute("class");
				document.getElementById("sp_lname").setAttribute("class", "error");
				er2=false;
			}
			
			if (password.trim()=="") {
				document.getElementById("sp_pass").removeAttribute("class");
				document.getElementById("sp_pass").setAttribute("class", "error_show");
				er3=true;
			} else {
				document.getElementById("sp_pass").removeAttribute("class");
				document.getElementById("sp_pass").setAttribute("class", "error");
				er3=false;
			}
			 
			if (!er1 && !er2 && !er3) {
				if (typeof XMLHttpRequest != "undefined") {
		            xmlHttp = new XMLHttpRequest();
		        } else if (window.ActiveXObject) {
		            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		        } else if(xmlHttp==null) {
		            alert("Browser does not support XMLHTTP Request");
		            return;
		        }
				
				var url = "${pageContext.request.contextPath}/index/getEmployeeForUpdate";
				url = url+"?firstName="+firstName+"&lastName="+lastName+"&password="+password;
				
				xmlHttp.onreadystatechange = stateChange3;
		        xmlHttp.open("GET", url, true);
		        xmlHttp.send(null);
			}  else return;
			
			
		}
		
		function stateChange3() {
			 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
				 var str = xmlHttp.responseText;
				 str = str.trim();
				 
				 document.getElementById("hddiv").innerHTML = str;
				 
				 var mob = document.getElementById("mob").innerHTML;
				 var jd1 = document.getElementById("jd").innerHTML; //it is returning as yyyy-MM-dd HH:mm:ss so				 
				 var dep = document.getElementById("dep").innerHTML;
				 var checkBox = document.getElementById("stat").innerHTML;
				 
				 if (mob=="" || jd=="" || dep==""){
					 alert("User Id and password did not match");
					 return;
				 }

				 var strs = jd1.split(" ");
				 var jd = strs[0];

				 document.getElementById("mnumber").value = mob;
				 document.getElementById("jndt").value = jd;
				 document.getElementById("dept").value = dep;
				 if (checkBox=="DEACTIVE") document.getElementById("status").checked=true;
				 
				 document.getElementById("hiddenDiv").style.display="block";
				 document.getElementById("btn").value="Update";
			 }
		}
	</script>
	
</head>
<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
	<div class="container nopadding">
		<!-- header -->
		<div class="row">
				<div class="col-sm-12 header">
					<div class="row">
						<div class="col-xs-2" style="margin-left: 1%">
							<img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/>
						</div>
						<div class="col-xs-5">
							<h4 class="heading">User Registration</h4>
							<div></div>
						</div>
						<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
							<span style="margin-right: 10px;">Contact: 9999999999 </span>
							<input type="button" value="Signout" class="signout" onclick="window.location.href='${pageContext.request.contextPath}/index/logout';">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<!-- Sidebar -->
				<div class="col-sm-2 sidebar" style="min-height: 650px;">
					<a href='${pageContext.request.contextPath}/index/h0me' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;">Back</a>
				</div>
				
				<!-- Main content -->
				<div class="col-sm-10 maincontent">
					<div class="row">
					<form id="form1" action="saveUser" method="POST">
						<div class="col-sm-12 form">
						
							${message}

							<div>
								<label>TOLL PLAZAS: </label>
								<select class="inp" name="plazaId" id="plazaId" onchange="showLanes(this.value)">
									<c:forEach var="tempPlaza" items="${allTollPlazas}">
										<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
									</c:forEach>
								</select>
							</div>
							
							<div>
								<label>FIRST NAME*: </label>
								<input id="fname" class="inp" name="userFirstName" required/>
								<span id = "sp_fname" class="error"><br>Please enter a valid first name</span>
							</div> 
							
							<div>
								<label>LAST NAME*: </label>
								<input id="lname" class="inp" name="userLastName" required/>
								<span id = "sp_lname" class="error"><br>Please enter a valid last name</span>
							</div>
							
							<div>
								<label>PASSWORD*: </label>
								<input id="pass" class="inp" type="password" name="userPassword" required/>
								<span id = "sp_pass" class="error"><br>Enter a valid password</span>
							</div>
							
							<div>
								<label>CONFIRM PASSWORD*: </label>
								<input id="repass" class="inp" type="password" name="" required/>
								<span id="sp_repass" class="error"><br>Passwords did not match</span>
 							</div>
 							
							<div>
								<input class="inp1" type="button" value="update" onclick="update()" style="margin-left:500px"/>
							</div>
							
							<div id="hiddenDiv" style="display: none">
								<div>
									<label>NEW PASSWORD: </label>
									<input type="password" name="newPassword" id="newpass" class="inp"/>
								</div>
								
								<div>
									<label>CONFIRM NEW PASSWORD: </label>
									<input type="password" name="newPassword" id="confnewpass" class="inp"/>
									<span id="sp_newpass" class="error"><br>Both new passwords should match</span>
								</div>
								
								<div>
									<span style="font-weight: bold; margin-left: 10%;">DEACTIVATE USER</span><input name="status" id="status" value="DEACTIVE" type="checkbox" class="inp" style="margin-left:-175px"/>
								</div>
								
							</div>
							
							<div>
								<label>MOBILE NUMBER*: </label>
								<input id="mnumber" type="number" class="inp" name="userMobileNumber" required/>
								<span id = "sp_mobi" class="error"><br>Please enter a valid mobile number</span>
							</div>
							
							<div>
								<label>DEPT. NAME*: </label>
								<select id="dept" class="inp" name="userRole" required>
									<option value="">Select Department</option>
									<option value="Operator">Operator</option>
								</select>	
								<span id = "sp_dept" class="error"><br>Please select a department</span>
							</div> 
							
							<div>
								<label>DATE OF JOIN*: </label>
								<input id="jndt" name="dateJoined" type="date" style="background:#474747 url(${pageContext.request.contextPath}/resources/images/Calendar.png)  97% 50% no-repeat ;" required/>
								<span id = "sp_jndt" class="error"><br>Please select a department</span>
							</div> 
							
							<div style="margin-left: 20%;">
							
							<input type="button" value="Cancel" class="inp1"
								onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
								class="Re-Configure Shifts"
								/>
								
								<input class="inp1"type="button" value = "Clear" onclick = "clearForm()"/>
							
								<input id="btn" class="inp1"type="button" value = "Submit" onclick = "submit1(this.value)"/>
				            </div>
							
						</div>
					</form>
				</div>
			</div>	
		</div>
	</div>
	<div id="hddiv" hidden></div>
	<input type="hidden" id="defplaza" value="${plazaId}"/>
	<script src="${pageContext.request.contextPath}/resources/js/login_register.js"></script>
</body>		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
<%-- Hi ${userFromSession.userFirstName} ${userFromSession.userLastName}
<div class="content" style="position:fixed;top: 10%;left: 25%;width:100%;height:65%">
	<div class="inputData">
		<h3 style=" text-align: center;">REGISTRATION FORM</h3>
		${passswordError}
		<form id="form1" action="saveUser" method="POST">

			<div>
				<label>TOLL PLAZAS: </label>
				<select class="inp" name="plazaId" id="plazaId" onchange="showLanes(this.value)">
					<c:forEach var="tempPlaza" items="${allTollPlazas}">
						<option value="${tempPlaza.tollPlazaId}">${tempPlaza.tollPlazaName}</option>
					</c:forEach>
				</select>
			</div>
			
			<div>
				<label>FIRST NAME*: </label>
				<input id="fname" class="inp" name="userFirstName" required/>
				<span id = "sp_fname" class="error"><br>Please enter a valid first name</span>
			</div> 
			
			<div>
				<label>LAST NAME*: </label>
				<input id="lname" class="inp" name="userLastName" required/>
				<span id = "sp_lname" class="error"><br>Please enter a valid last name</span>
			</div>
			
			<div>
				<label>MOBILE NUMBER*: </label>
				<input id="mnumber" type="number" class="inp" name="userMobileNumber" required/>
				<span id = "sp_mobi" class="error"><br>Please enter a valid mobile number</span>
			</div>
			
			<div>
				<label>PASSWORD*: </label>
				<input id="pass" class="inp" type="password" name="userPassword" required/>
				<span id = "sp_pass" class="error"><br>Enter a valid password</span>
			</div>
			
			<div>
				<label>DEPT. NAME*: </label>
				<select id="dept" class="inp" name="userRole" required>
					<option value="">Select Department</option>
					<option value="Admin">Admin</option>
					<option value="Operator">Operator</option>
				</select>	
				<span id = "sp_dept" class="error"><br>Please select a department</span>
			</div> 
			
			<div>
				<label>DATE OF JOIN*: </label>
				<input id="jndt" class="inp" type="date" name="dateJoined" required/>
				<span id = "sp_jndt" class="error"><br>Please select a department</span>
			</div> 
			
			<div>
			 	<input id="btn" class="inp1"type="button" value = "Submit" onclick = "submit1()"/>
				<input type="button" value="Cancel" class="inp1"
				onclick="window.location.href='${pageContext.request.contextPath}/index/h0me'; return false"
				class="Re-Configure Shifts"
				/>
				<input id="btn" class="inp1"type="button" value = "Clear" onclick = "clearForm()"/>
            </div>	
		</form>
	</div>
</div>
<input type="hidden" id="defplaza" value="${plazaId}"/>
<script src="${pageContext.request.contextPath}/resources/js/login_register.js"></script>
</body>
</html> --%>