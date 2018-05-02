<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>


<title>Login Page</title>

    <!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">

<link type="text/css"
	  rel = "stylesheet"
	  href="${pageContext.request.contextPath}/resources/css/LoginPageStyle.css"
	/>

<link type="text/css"
	  rel = "stylesheet"
	  href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css"
	/>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body onLoad="noBack();" style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg); background-repeat: repeat-y;">
		<div class="container  no-padding hidden-xs" style="width:100%">
		
			<div class="row headerDiv">
				<div class="col-sm-12">
					<div class="row">
						<div class="col-xs-3">
							<img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/>
						</div>
						<div class="col-xs-4">
							<!--Write something here to get it in middle of the header-->
							<div></div>
						</div>
						<div class="col-xs-2" style="position: relative; top: 25px; left: 40px;">
							<!-- <span style="margin-right: 10px;">Contact: 9999999999 </span> -->
						</div>
						<div class="col-xs-2" style="position: relative; top: 15px;">
							<img src="${pageContext.request.contextPath}/resources/images/fb.png" class="hidden-sm hidden-xs" height="40" width="40" style="margin-right: 10px;"/>
							<img src="${pageContext.request.contextPath}/resources/images/tweety.png" class="hidden-sm hidden-xs" height="40" width="40"/>
						</div>
					</div>
				</div>				
			</div>
			
			<div class="row mainContent" style="min-height: 532px;">	
				<div class="col-sm-12 takeHeight">
						<div>

						</div>
				</div>
				<div class="col-sm-5 col-sm-offset-3" style="margin-top: 10%; margin-left: 30%">
					<div class="loginForm">
					<span class="error_show">${shiftNotFoundError}</span>
					${error}
						<form:form id="form2" modelAttribute="user" action="home" method="POST" style="margin-left: 5%">
						
							<div>
								<label>UserName: </label>
								<input type="text" id="name" class="inp" placeholder="Username"/>
								<span id = "sp_fname" class="error"><br>Please enter a valid user name 'first_name[space]last_name'</span>
							</div>
							
							<div>
								<form:hidden id="fname" path="userFirstName" value=""/>
								<form:hidden id="lname" path="userLastName" value=""/>
							</div>
							
							<div>
								<label>Password: </label>
								<form:password id="pass" class="inp" path="userPassword" placeholder="***************************"/>
								<span id = "sp_pass" class="error"><br>Please enter a valid password</span>
							</div>
							
							<div>
								<!-- <input type="button" value="Cancel" class="inp1" style="background-color: #b9b9b9" onclick = "cancel()"/> -->
								<input class="inp1" type="button" value="Login" onclick = "submit2()" style="margin-left: 30%"/>	
							</div>
						</form:form>
					</div>
				</div>
				<div class="col-sm-12 takeHeight">

				</div>
			</div>
			<div class="row footer">
				<div class="col-sm-4">
					
				</div>
				<div class="col-sm-4">
					<div style="position: relative; top:20px;">Copy rights 2017 @ TollSecure India Pvt. Ltd</div>
				</div>
				<div class="col-sm-4">
					
				</div>
			</div>
		</div>
		
	
	<script type="text/javascript">
		var nam = document.getElementById("name"); 
		nam.focus();
		nam.select();
	</script>								
	</body>
	
	<div class="visible-xs container" >
		<div class="row">
			<div class="col-xs-12" style="background-color: #ee9620">
			<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' style="margin-left: 15%; margin-bottom: 10%" width="60%"/>
				
				<div style="position: fixed; top: 20%">	
					<span id="sp_flname" class="error" style="color: red">Please enter a valid user name 'first_name[space]last_name'</span>
					<span class="error_show">${shiftNotFoundError}</span>
					${error}
				</div>
				
				<div class="col-xs-12" style="position:fixed;top: 30%;left: 10%;">
					

					<input type="text" id="sname" placeholder="Username" size="30%" style="border: none; border-bottom: 2px solid #000000; background: transparent;"/>
					<input type="password" id="spass" placeholder="Password" size="30%" style="border: none; border-bottom: 2px solid #000000; background: transparent; margin-top:5%"/>
					
					<div>
						
						<input class="inp1" type="button" style="padding-left:30%; padding-right:50%; margin-top:5%" value="Login" onclick = "submit3()"/>	
						
					</div>
				</div>			
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/login_register.js" type="text/javascript"></script>
	<script>
	//for mobile version
	function submit3() {
		var sname = document.getElementById("sname").value;
		var strs = sname.split(" ");
		if (strs.length != 2) {
			document.getElementById("sp_flname").removeAttribute("class");
			document.getElementById("sp_flname").setAttribute("class", "error-show")
		}
		var spass = document.getElementById("spass").value;
		document.getElementById("name").value = sname;
		document.getElementById("pass").value = spass;
		submit2();
	}
	</script>
</html>







	
	
	
		
	

		

		
		

