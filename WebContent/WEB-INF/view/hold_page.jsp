<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>


<title>Hold Page</title>

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
</head>
<body>
<body onLoad="noBack();" style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
		<div class="container  no-padding" style="width:100%">
		
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
						<div style=""><!-- background-color: #6f7070; -->
							<h1 style="color: #ee9620; position: relative; top: 75px; display:inline;">Hold Mode</h1>
						</div>
				</div>
				<div class="col-sm-5 col-sm-offset-3" style="margin-top: 10%; margin-left: 30%">
					<div class="loginForm">
					<span class="error_show">${shiftNotFoundError}</span>
					${error}
						<form:form id="form2" modelAttribute="user" action="home" method="POST" style="margin-left: 5%">
						
							<div>
								<label>UserName: </label>
								<input type="text" id="name" class="inp" placeholder="FirstName LastName"/>
								<span id = "sp_fname" class="error"><br>Please enter a valid user name 'first_name[space]last_name'</span>
							</div>
							
							<div>
								<input hidden id="fname" name="userFirstName" value=""/>
								<input hidden id="lname" name="userLastName" value=""/>
							</div> 
							
							<div>
								<label>Password: </label>
								<input type="password" id="pass" class="inp" name="userPassword" placeholder="***************************"/>
								<span id = "sp_pass" class="error"><br>Please enter a valid password</span>
							</div>
							
							<div>
								<input type="button" value="Cancel" class="inp1" style="background-color: #b9b9b9" onclick = "cancel()"/>
								<input class="inp1" type="button" value="Release" onclick = "submit2()"/>	
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
		<script src="${pageContext.request.contextPath}/resources/js/hold_page.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		var nam = document.getElementById("name"); 
		nam.focus();
		nam.select();
	</script>			
	
	<script>
	(function (global) { 

	    if(typeof (global) === "undefined") {
	        throw new Error("window is undefined");
	    }

	    var _hash = "!";
	    var noBackPlease = function () {
	        global.location.href += "#";

	        // making sure we have the fruit available for juice (^__^)
	        global.setTimeout(function () {
	            global.location.href += "!";
	        }, 50);
	    };

	    global.onhashchange = function () {
	        if (global.location.hash !== _hash) {
	            global.location.hash = _hash;
	        }
	    };

	    global.onload = function () {            
	        noBackPlease();

	        // disables backspace on page except on input fields and textarea..
	        document.body.onkeydown = function (e) {
	            var elm = e.target.nodeName.toLowerCase();
	            if (e.which === 8 && (elm !== 'input' && elm  !== 'textarea')) {
	                e.preventDefault();
	            }
	            // stopping event bubbling up the DOM tree..
	            e.stopPropagation();
	        };          
	    }

	})(window);
	</script>					
	</body>
</html>







	
	
	
		
	

		

		
		

