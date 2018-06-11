<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page session="true" %>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<style>

<!-- for image highlight -->

#img1 {
    border-radius: 5px;
    cursor: pointer;
    transition: 0.3s;
}

#simg1 {
    border-radius: 5px;
    cursor: pointer;
    transition: 0.3s;
}

#img1:hover {opacity: 0.7;}
#simg1:hover {opacity: 0.7;}


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
    background-color: rgba(0,0,0,0.9); /* Black w/ opacity */
}

/* Modal Content (image) */
.modal-content {
    margin: auto;
    display: block;
    width: 80%;
    max-width: 700px;
}

/* Caption of Modal Image */
#caption {
    margin: auto;
    display: block;
    width: 80%;
    max-width: 700px;
    text-align: center;
    color: #ccc;
    padding: 10px 0;
    height: 150px;
}

/* Add Animation */
.modal-content, #caption {    
    -webkit-animation-name: zoom;
    -webkit-animation-duration: 0.6s;
    animation-name: zoom;
    animation-duration: 0.6s;
}

@-webkit-keyframes zoom {
    from {-webkit-transform:scale(0)} 
    to {-webkit-transform:scale(1)}
}

@keyframes zoom {
    from {transform:scale(0)} 
    to {transform:scale(1)}
}

/* The Close Button */
.close {
    position: absolute;
    top: 15px;
    right: 35px;
    color: #f1f1f1;
    font-size: 40px;
    font-weight: bold;
    transition: 0.3s;
}

.close:hover,
.close:focus {
    color: #bbb;
    text-decoration: none;
    cursor: pointer;
}

/* 100% Image Width on Smaller Screens */
@media only screen and (max-width: 700px){
    .modal-content {
        width: 100%;
    }
}
</style>

	<title>Vehicle Details</title>
	
	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/cancel_ticket.css">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">

	<script>
	if (localStorage.status==undefined) window.location.href='${pageContext.request.contextPath}/index/logout'; //logout user incase status is undefined
	</script>

</head>

<body style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)">
	<%! String journeyType = ""; %>
 	<c:choose>
	    <c:when test="${!transaction[10].equals('0')}">
	    	<c:choose>
		    	<c:when test="${transaction[10].equals('LOCAL')}">
		    		<% journeyType="LOCAL PASS"; %>
		    	</c:when>
		    	<c:when test="${transaction[10].equals('MONTHLY')}">
		    		<% journeyType="MONTHLY PASS"; %>
		    	</c:when>
		    	<c:otherwise>
		    		<% journeyType="LOCAL VIP PASS"; %>
		    	</c:otherwise>
		    </c:choose>
	    </c:when>
	  <c:when test="${transaction[11]!=0}">
	  	<% journeyType="EXEMPTED"; %>
	  </c:when>
	  <c:when test="${transaction[12]!=0}">
	  	<% journeyType="CONCESSION"; %>
	  </c:when>  
	  <c:when test="${transaction[9]=='S'}">
	  <% journeyType="SINGLE"; %>
	  </c:when>  
	  	<c:otherwise>
	  	<% journeyType="RETURN"; %>
	  </c:otherwise>              
	</c:choose> 

	
	<div class="container nopadding hidden-xs">

		<!-- header -->
		<div class="row">
			<div class="col-sm-12 header" style=" min-width: 102%;">
				<div class="row">
					<div class="col-xs-2" style="margin-left: 1%">
						<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
					</div>
					<div class="col-xs-5">
							<h4 class="heading" style="font-size: 0.9em">Vehicle Details</h4>
						<div></div>
					</div>
					<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
						<span style="margin-right: 10px;" ><!-- Contact: 9999999999 --></span>
						<input type="button" value="Signout" class="signout" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
					</div>
				</div>
			</div>
		</div>

				<div class="row">
			<div class="col-sm-12 black_header">
				<div class="col-md-3">
					<!--<a href='${pageContext.request.contextPath}/index/h0me' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;">Back</a>-->
					<span style="margin-left: -40%"><a href='javascript:history.back()' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: -25%">Back</span></a></span>
				</div>

				<div class="col-md-4">
					
				</div>

				<div class="col-md-5">

				</div>
			</div>
		</div>

		<div class="row" style="min-height: 520px">
			<div id="demo" class="col-md-10 maincontent">
				<div class="row">
					<div class="col-md-8 form" style="width: 60%; margin-bottom: 20px; padding-bottom: 20px; margin-top: 30px">
							<div class="message" id="message">${message}</div>
							<div>
								<label>Ticket No. </label>
								<input id="ticketNo" value="${transaction[0]}" class="inp" disabled="disabled" style="max-width: 80%"/>
								
								<label style="margin-left:10px">Time Stamp. </label>
								<input id="time" value="${transaction[1]}" class="inp" disabled="disabled" />
							</div>

							<div>
								<label>Vehicle No.</label>
								<input id="vno" type="text" id="user" value="${transaction[2]}" class="inp" disabled="disabled" />

								<label style="margin-left: 10px">User.</label>
								<input id="usr" type="text" id="lane" value="${transaction[3]} ${transaction[4]}" class="inp" disabled="disabled" />
							</div>

							<div>
								<label>Shift.</label>
								<input id="shift" type="text" id="amt" value="${transaction[5]}" class="inp" disabled="disabled" />

								<label>Journey Type</label>
								<input id="jType" type="text" value="<%=journeyType %>" class="inp" disabled="disabled" style="max-width:150px"/>
							</div>

							<div>
								<label>Vehicle Class</label>
								<input id="vclass" type="text" value="${transaction[8]}" class="inp" disabled="disabled"/>
							
								<label style="margin-left:30px">Amount.</label>
								<input id="amt" type="text" id="journey" value="${transaction[6]}" class="inp" disabled="disabled" style="max-width: 100px"/>
							</div>
							<div style="margin-top: 15px;">
								<span id="er" class="error_show" style="margin-left: 25%;">${error}</span>
								<span id="sp_remark" class="error">Please enter Remarks before clicking on cancel ticket</span>
							</div>

							<div>
								<span id="sp_notFound" class="error" style="margin-left: 20%">Ticket Not Found</span>
							</div>
					</div>

					<!-- photo -->
					<div class="col-sm-4" style="margin-bottom: 20px; ">
						<div class="cctv" style="">
							<!-- <span style="position: relative; top: 80px; left: 100px; color: #fff; font-size: 10em">CCTV</span> -->
							<!-- <img src="https://thumbs.dreamstime.com/z/cat-deep-blue-eyes-2995129.jpg"/> -->
							
							<!-- for windows -->
<%-- 							<img id="dimg1" src="<c:url value="/vehicleImages/${transaction[7]}" />" style="max-width:100%;">  --%>
							
							<!-- for ubuntu -->
							<%-- <img id="img" alt="" src="<c:url value="/Wallpapers/${transaction[7]}" />" style="max-width:100%;"> --%>
						
							<!-- from db -->
							<img id="img1" src="<c:url value="/myImage/imageDisplay?code=${transaction[0]}" />" onerror='this.src="<c:url value="/vehicleImages/${transaction[7]}" />"' style="max-width:100%;"/>
							
						</div>
					</div>
					
					<div class="col-sm-04" style="position: relative; top: -30px; left: -10%; "> 
						<input type="image" id="prev" src="${pageContext.request.contextPath}/resources/images/Human-go-previous.png" onclick="getPreviousOne()" alt="Previous" style="margin-left: 200px; position: relative; top: -100px"  height="10%" width="10%"/> 
						<input type="image" id="next" src="${pageContext.request.contextPath}/resources/images/Human-go-next.png" onclick="getNextOne()" alt="Next" style="margin-left: 200px; position: relative; top: -100px"  height="10%" width="10%"/> 
					</div> 
					
				</div>
			</div>
		</div>

		 <%!
       		int count = 0;
   		 %>
   		 <c:forEach items="${tollTransactions}" var="tempTransaction">
			<input class="prev_next" hidden value="${tempTransaction[0]}#${tempTransaction[1]}#${tempTransaction[2]}#${tempTransaction[3]}#${tempTransaction[4]}#${tempTransaction[5]}#${tempTransaction[6]}#${tempTransaction[7]}#${tempTransaction[8]}#${tempTransaction[9]} ${tempTransaction[10]}#${tempTransaction[11]}#${tempTransaction[12]}#${tempTransaction[13]}" id="<%=count %>"/>
			<%count = count + 1; 
			  /* out.print(count); */
			%> 
		 </c:forEach>
		 <input hidden id="htotalcount" value="<%=count %>"/>
		 <%
		 	count=0;
		 %>
		 
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
		
		<div class="visible-xs container" style="margin-right: 0px; min-width: 100%">
			
			<div class="row">
				<div class="col-xs-12" style="background-color: #ee9620">
					<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' style="margin-left: 15%; margin-bottom: 5%" width="60%"/>
					<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28" style="margin-left: 15%" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
					<br><span style="margin-left:25%">Vehicle Details</span>
				</div>
			</div>
		</div>
		
		<div class="row black_header visible-xs">
		
			<div class="col-xs-12" style="">
				<a href='javascript:history.back()' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -50%; margin-top: 0px; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px"> </a>
			</div>
		
		</div>

		<div class="row visible-xs">
			<div class="col-xs-12 form" style="width: 90%; padding-bottom: 10px; margin-top: 2%; margin-left: 5%">
				<div class="row">
					<div class="col-xs-12">
						<label>Ticket No. </label>
						<input id="sticketNo" value="${transaction[0]}" class="inp"  size="21" disabled="disabled" style="max-width: 80%"/>
					</div>
					
					<div class="col-xs-12">
						<label style="margin-left:10px">Time Stamp. </label>
						<input id="stime" value="${transaction[1]}" size="21" class="inp" disabled="disabled" />	
					</div>
					
					<div class="col-xs-12">
						<label>Vehicle No.</label>
						<input id="svno" type="text" size="21" value="${transaction[2]}" class="inp" disabled="disabled" />
					</div>
					
					<div class="col-xs-12">
						<label style="">User.</label>
						<input id="susr" type="text" size="21" value="${transaction[3]} ${transaction[4]}" class="inp" disabled="disabled" />
					</div>
					
					<div class="col-xs-12">
						<label>Shift.</label>
						<input id="sshift" type="text" size="21" value="${transaction[5]}" class="inp" disabled="disabled" />
					</div>
					
					<div class="col-xs-12">
						<label>Vehicle Class</label>
						<input id="svclass" type="text" value="${transaction[8]}" class="inp" disabled="disables"/>
					</div>
					
					<div class="col-xs-12">
						<label>Journey Type</label>
						<input id="sjType" type="text" value="<%=journeyType %>" class="inp" disabled="disables"/>
					</div>
									
					<div class="col-xs-12">
						<label style="margin-left:50px">Amount.</label>
						<input id="samt" type="text" size="21" value="${transaction[6]}" class="inp" disabled="disabled" />
					</div>
				</div>
				
			</div>
			
			<div class="col-xs-12" style="background-color: #6f7070; max-width:90%; margin:5%; margin-top:2%; border-radius: 20px; min-height: 220px;">
<%-- 				<img id="sdimg1" src="<c:url value="/vehicleImages/${transaction[7]}" />" style="max-width:100%;">  --%>
				<img id="simg1" src="<c:url value="/myImage/imageDisplay?code=${transaction[0]}" />" onerror='this.src="<c:url value="/vehicleImages/${transaction[7]}" />"' style="max-width:100%;"/>
			</div>
		</div>
		
		<div class="row visible-xs" style="position: absolute; top: 105%; display: inline-block;">
			<div class="col-xs-12" style="position: relative; top: -30px; left: -10%; "> 
				<input type="image" id="sprev" src="${pageContext.request.contextPath}/resources/images/Human-go-previous.png" onclick="getPreviousOne()" alt="Previous" style="margin-left: 200px; position: relative; left: -10%; top: 38px"  height="10%" width="10%"/> 
				<input type="image" id="snext" src="${pageContext.request.contextPath}/resources/images/Human-go-next.png" onclick="getNextOne()" alt="Next" style="margin-left: 200px; position: relative; top: -5px; left: 10%;"  height="10%" width="10%"/> 
			</div> 
		</div>

		
		<%-- hidden content --%>
		<div id="hidden_content" style="display: none">

		</div>
		
		<!-- The Modal -->
<div id="myModal" class="modal">
  <span class="close">&times;</span>
  <img class="modal-content" id="img01" width="700" height="500">
  <div id="caption"></div>
</div>

<script>
// Get the modal
var modal = document.getElementById('myModal');

// Get the image and insert it inside the modal - use its "alt" text as a caption
var img = document.getElementById('img1');
if(/Mobile/i.test(navigator.userAgent) && !/ipad/i.test(navigator.userAgent) ){
	img = document.getElementById('simg1');
}
var modalImg = document.getElementById("img01");
var captionText = document.getElementById("caption");
img.onclick = function(){
    modal.style.display = "block";
    modalImg.src = this.src;
    captionText.innerHTML = this.alt;
}

// Get the <span> element that closes the modal
//var span = document.getElementsByClassName("close")[0];
var span = document.getElementById("myModal");

// When the user clicks on <span> (x), close the modal
span.onclick = function() { 
    modal.style.display = "none";
}
</script>
<script src="${pageContext.request.contextPath}/resources/js/vehicle_details.js"></script>

<script>
	function setImageSource(str, str1) {
		//these are from db
		document.getElementById("img1").src="";
		document.getElementById("simg1").src="";
		document.getElementById("img1").src = "${pageContext.request.contextPath}/myImage/imageDisplay?code="+str1;
		document.getElementById("simg1").src = "${pageContext.request.contextPath}/myImage/imageDisplay?code="+str1;
	
		//remove alternate if he presses previous or next
		document.getElementById('img1').removeAttribute('onerror');
		document.getElementById('simg1').removeAttribute('onerror');
		
		document.getElementById("img1").addEventListener("error", setImageSorrceFromDrive(str)); 
		document.getElementById("simg1").addEventListener("error", setImageSorrceFromDrive(str));
	}

	function setImageSorrceFromDrive(str) {
		document.getElementById("img1").src = "${pageContext.request.contextPath}/vehicleImages/"+str;
		document.getElementById("simg1").src = "${pageContext.request.contextPath}/vehicleImages/"+str;	
	}
</script>


</body>