<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<!-- for image highlight -->
<style>
#img1 {
    border-radius: 5px;
    cursor: pointer;
    transition: 0.3s;
}

#img1:hover {opacity: 0.7;}

#simg1 {
    border-radius: 5px;
    cursor: pointer;
    transition: 0.3s;
}

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

<style>
	/*Styles for cancel and search modals*/
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
	<title>Cancel Ticket</title>
	
	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/cancel_ticket.css">

	<script>
	
		if (localStorage.status==undefined) window.location.href='localStorage.clear();${pageContext.request.contextPath}/index/logout'; //logout user incase status is undefined
	
		//prevents right click
		document.addEventListener('contextmenu', event = event.preventDefault());
	
		//function isNormalInteger(str) {
	    // 	return /^\+?(0|[1-9]\d*)$/.test(str);
		//}
		
		var xmlHttp;
		
		function ckeckAndSearch() {
			
			var ticketNo = document.getElementById("tno").value;
			
			if (ticketNo=="") {
				document.getElementById("sterror").removeAttribute("class");
				document.getElementById("sterror").setAttribute("class", "error_show");
				
				document.getElementById("terror").removeAttribute("class");
				document.getElementById("terror").setAttribute("class", "error_show");
				
				return;
			} else {
				document.getElementById("sterror").removeAttribute("class");
				document.getElementById("sterror").setAttribute("class", "error");
				
				document.getElementById("terror").removeAttribute("class");
				document.getElementById("terror").setAttribute("class", "error_show");
			}
			
			var url = "${pageContext.request.contextPath}/tollTransaction/checkIfCashUpIsDone?transactionCode="+ticketNo;
			
			if (typeof XMLHttpRequest != "undefined") {
		        xmlHttp = new XMLHttpRequest();
		    } else if (window.ActiveXObject) {
		        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		    } else if(xmlHttp==null) {
		        alert("Browser does not support XMLHTTP Request");
		        return;
		    }
			
			xmlHttp.onreadystatechange = stateChange1;
	    	xmlHttp.open("GET", url, true);
			xmlHttp.send(null); 
		}
		
		function stateChange1() {
			if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
				 var str = xmlHttp.responseText;
				 str = str.trim().split("-");
				 if (str[0] == "false") {
					 //show the modal that cashup declaration is already done for this toll transaction
					 document.getElementById("search-modal-content").innerHTML=str[1];
					 var modal = document.getElementById('myModal1');
					 modal.style.display = "block";
				 } else {
					 if (str == "logged_out") {
						 localStorage.clear();
						 window.location.href = "${pageContext.request.contextPath}/index/logout";
					 }
					 else searchTicket();
				 }
			 } 
		}
		
		function f1() {
			var modal = document.getElementById('myModal1');
			 modal.style.display = "none";
		}
		
		function ssearchTicket() {
			var tnoForSearch = document.getElementById("stno").value;
			var terror1 = false;
			//isNormalInteger(tnoForSearch) && parseInt(tnoForSearch)>=1000000
			if (tnoForSearch !="" && tnoForSearch!= null) {
		
				terror1 = false;
				document.getElementById("sterror").removeAttribute("class");
				document.getElementById("sterror").setAttribute("class", "error");
			} else {
				terror1 = true;
				document.getElementById("sterror").removeAttribute("class");
				document.getElementById("sterror").setAttribute("class", "error_show");
			}
			
			if (!terror1) {
				document.getElementById("tno").value = document.getElementById("stno").value;
				ckeckAndSearch();
			}
		}
		
		function searchTicket() {
			
			document.getElementById("message").innerHTML = "";
			document.getElementById("er").innerHTML = "";
			
			document.getElementById("smessage").innerHTML = "";
			document.getElementById("ser").innerHTML = "";
			
			var tnoForSearch = document.getElementById("tno").value;
			
			var terror1 = false;
			//isNormalInteger(tnoForSearch) && parseInt(tnoForSearch)>=1000000
			if (tnoForSearch !="" && tnoForSearch!= null) {
		
				terror1 = false;
				document.getElementById("terror").removeAttribute("class");
				document.getElementById("terror").setAttribute("class", "error");
			} else {
				terror1 = true;
				document.getElementById("terror").removeAttribute("class");
				document.getElementById("terror").setAttribute("class", "error_show");
			}
			
			if (!terror1) {
				//Sending request
				//window.location.href = '${pageContext.request.contextPath}/tollTransaction/cancelTicket?transactionId='+tnoForSearch;
		 		if (typeof XMLHttpRequest != "undefined") {
			        xmlHttp = new XMLHttpRequest();
			    } else if (window.ActiveXObject) {
			        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			    } else if(xmlHttp==null) {
			        alert("Browser does not support XMLHTTP Request");
			        return;
			    }

				//bring lanes
				var url="${pageContext.request.contextPath}/tollTransaction/searchTicket?ticketNo="+tnoForSearch;

				xmlHttp.onreadystatechange = stateChange;
		    	xmlHttp.open("GET", url, true);
		   		xmlHttp.send(null); 
		   		
		   		//i am not using ajax for getting photo
		   		
		 		//window.location.href = '${pageContext.request.contextPath}/tollTransaction/cancelTicket?transactionId='+tnoForSearch;
		   		
			}
		}
		function stateChange() {
			 if (xmlHttp.readyState==4||xmlHttp.readyState=="complete") {
				 var str = xmlHttp.responseText;
				 str=str.trim();
				 
				 if (str=="Did not find transaction in database") {
					 document.getElementById("vnum").value="";
					 document.getElementById("tnum").value="";
					 document.getElementById("user").value="";
					 document.getElementById("lane").value="";
					 document.getElementById("amt").value="";
					 document.getElementById("journey").value="";
					 document.getElementById("img").src="";
					 document.getElementById("img1").src="";
					 
					 //for mobile
					 document.getElementById("svnum").value="";
					 document.getElementById("stnum").value="";
					 document.getElementById("suser").value="";
					 document.getElementById("slane").value="";
					 document.getElementById("samt").value="";
					 document.getElementById("sjourney").value="";
					 document.getElementById("simg1").src="";
					 
					 document.getElementById("sp_notFound").removeAttribute("class");
					 document.getElementById("sp_notFound").setAttribute("class", "error_show");
					 
					 document.getElementById("ssp_notFound").removeAttribute("class");
					 document.getElementById("ssp_notFound").setAttribute("class", "error_show");
					 return; 
				 } else {
					 document.getElementById("sp_notFound").removeAttribute("class");
					 document.getElementById("sp_notFound").setAttribute("class", "error");
					 
					 document.getElementById("ssp_notFound").removeAttribute("class");
					 document.getElementById("ssp_notFound").setAttribute("class", "error");
				 }
				 
				 document.getElementById("hidden_content").innerHTML = str;

				 //insert the values into form
				 document.getElementById("vnum").value=document.getElementById("v").innerHTML;
				 document.getElementById("tnum").value=document.getElementById("t").innerHTML;
				 document.getElementById("user").value=document.getElementById("u").innerHTML;
				 document.getElementById("lane").value=document.getElementById("l").innerHTML;
				 document.getElementById("amt").value=document.getElementById("a").innerHTML;
				 document.getElementById("journey").value=document.getElementById("j").innerHTML;
				 var ur = document.getElementById("i").innerHTML;

				 document.getElementById("svnum").value=document.getElementById("v").innerHTML;
				 document.getElementById("stnum").value=document.getElementById("t").innerHTML;
				 document.getElementById("suser").value=document.getElementById("u").innerHTML;
				 document.getElementById("slane").value=document.getElementById("l").innerHTML;
				 document.getElementById("samt").value=document.getElementById("a").innerHTML;
				 document.getElementById("sjourney").value=document.getElementById("j").innerHTML;
				 
// 		vehicleImages		 <img id="img" alt="imageUbuntu" src="<c:url value="/Wallpapers/${tollTransaction.imageAddress}" />" style="max-width:100%;">
 				 //document.getElementById("img").src = '"<c:url value="/Wallpapers/'+ur+'"/>"';
 				 document.getElementById("img").src = '${pageContext.request.contextPath}/Wallpapers/'+ur;
 				 document.getElementById("img1").src = '${pageContext.request.contextPath}/vehicleImages/'+ur;
 				 document.getElementById("simg1").src = '${pageContext.request.contextPath}/vehicleImages/'+ur;
			 }
		}
		
		
		function scancelTic() {
			document.getElementById("tnum").value = document.getElementById("stnum").value;
			document.getElementById("comment").value = document.getElementById("scomment").value;
			cancelTic()
		}
		
		function cancelTic() {
			var terror1 = false;
			var ticketNumber = document.getElementById("tnum").value.trim();
			
			if (ticketNumber!="" && ticketNumber != null) {
				terror1 = false;
				document.getElementById("terror").removeAttribute("class");
				document.getElementById("terror").setAttribute("class", "error"); 
				
				document.getElementById("sterror").removeAttribute("class");
				document.getElementById("sterror").setAttribute("class", "error"); 
			} else {
				terror1 = true;
				document.getElementById("terror").removeAttribute("class");
				document.getElementById("terror").setAttribute("class", "error_show"); 
				
				document.getElementById("sterror").removeAttribute("class");
				document.getElementById("sterror").setAttribute("class", "error_show"); 
			}
			
			var commentError = false;
			var remark = document.getElementById("comment").value.trim();
			
			if (remark=="") {
				commentError = true;
				document.getElementById("sp_remark").removeAttribute("class");
				document.getElementById("sp_remark").setAttribute("class", "error_show");
				
				document.getElementById("ssp_remark").removeAttribute("class");
				document.getElementById("ssp_remark").setAttribute("class", "error_show");
			} else {
				commentError = false;
				document.getElementById("sp_remark").removeAttribute("class");
				document.getElementById("sp_remark").setAttribute("class", "error");
				
				document.getElementById("ssp_remark").removeAttribute("class");
				document.getElementById("ssp_remark").setAttribute("class", "error");
			}
			
			if (!terror1 && !commentError) {
				var modal = document.getElementById('myModal2');
				modal.style.display = "block";
			} 
		}
		
		function f2() {
			var ticketNumber = document.getElementById("tnum").value.trim();
			var remark = document.getElementById("comment").value.trim();
			window.location.href = "${pageContext.request.contextPath}/tollTransaction/cancelTicket1?tno="+ticketNumber+"&comments="+remark;
		}
		
		function f3() {
			var modal = document.getElementById('myModal2');
			modal.style.display = "none";
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
							<h4 class="heading" style="font-size: 0.9em">Cancel A Transaction</h4>
						<div></div>
					</div>
					<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
						<span style="margin-right: 10px;"><!--Contact: 9999999999 --></span>
						<input type="button" value="Signout" class="signout" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
					</div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col-sm-12 black_header">
				<div class="col-md-3">
					<c:choose>
					    <c:when test="${empty tollTransaction}">
					       <span style="margin-left: -40%"><a href='${pageContext.request.contextPath}/index/h0me?tabNumber=2' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;"><span style="margin-left: -25%">Back</span></a></span>
					    </c:when>
					    <c:otherwise>
					    	<a href='javascript:history.back()' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;">Back</a>
					    </c:otherwise>
					</c:choose>
			
				</div>
				
				<div class="col-md-4">
					
				</div>

				<div class="col-md-5">
					<label>Ticket Number. </label><input type="text" value="${tollTransaction.ticketCode}" id="tno" class="head_inp" name="">
					<input type="button" class="inp1" value="Search" onclick="ckeckAndSearch()">
					<div class="error" id="terror">Please enter a valid ticket number</div>
				</div>
			</div>
		</div>

		<div class="row" style="min-height: 450px">
			<div id="demo" class="col-md-10 maincontent">
				<div class="row">
					<div class="col-md-8 form" style="width: 60%; margin-bottom: 20px; padding-bottom: 20px">
							<div class="message" id="message">${message}</div>
							<div>
								<label>Vehicle No.</label>
								<input type="text" id="vnum" value="${tollTransaction.vehicleNumber}" class="inp" disabled="disabled" />

								<label>Ticket No.</label>
								<input type="text" id="tnum" value="${tollTransaction.ticketCode}" class="inp" disabled="disabled" />
							</div>

							<div>
								<label>User.</label>
								<input type="text" id="user" value="${tollTransaction.createUserID}" class="inp" disabled="disabled" />

								<label style="margin-left: 110px">Lane.</label>
								<input type="text" id="lane" value="${lane.laneCode}" class="inp" disabled="disabled" />
							</div>

							<div>
								<label>Remarks.</label>
								<textarea class="inp" id="comment" name="comment" placeholder="Comments reqired"></textarea>
							</div>


							<div>
								<label>Amount.</label>
								<input type="text" id="amt" value="${tollTransaction.tollAmt}" class="inp" disabled="disabled" />

								<label>Journey.</label>
								<input type="text" id="journey" value="${tollTransaction.journeyType}" class="inp" disabled="disabled" />
							</div>

							<div style="margin-top: 15px;">
								<span id="er" class="error_show" style="margin-left: 25%;">${error}</span>
								<span id="sp_remark" class="error">Please enter Remarks before clicking on cancel ticket</span>
							</div>

							<div>
								<span id="sp_notFound" class="error" style="margin-left: 20%">Ticket Not Found</span>
							</div>
							
							<div>
								<input type="button" class="inp1" name="" onclick="cancelTic()" value="Cancel Ticket" style="margin-left: 45%;">
							</div>
					</div>

					<!-- photo -->
					<div class="col-sm-4" style="margin-bottom: 20px; ">
						<div class="cctv" style="">
							<!-- <span style="position: relative; top: 80px; left: 100px; color: #fff; font-size: 10em">CCTV</span> -->
							<!-- <img src="https://thumbs.dreamstime.com/z/cat-deep-blue-eyes-2995129.jpg"/> -->
							<%-- <img src="${pageContext.request.contextPath}/resources/vehicleImages/${tollTransaction.imageAddress}" id="img"/> --%>
						
							<!-- for windows -->
							<img id="img1" src="<c:url value="/vehicleImages/${tollTransaction.imageAddress}" />" style="max-width:100%;">
							
							<!-- for ubuntu -->
							<img id="img" alt="" src="<c:url value="/Wallpapers/${tollTransaction.imageAddress}" />" style="max-width:100%;">
						
						</div>
					</div>

				</div>
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
		
		<%-- hidden content --%>
		<div id="hidden_content" style="display: none">

		</div>
		
		
		
		<div class="visible-xs container" style="margin-right: 0px; min-width: 100%">
			
			<div class="row">
				<div class="col-xs-12" style="background-color: #ee9620">
					<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' style="margin-left: 15%; margin-bottom: 5%" width="60%"/>
					<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28" style="margin-left: 15%" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
					<br><span style="margin-left:25%">Cancel Ticket</span>
				</div>
			</div>
		</div>
		
		<div class="row black_header visible-xs">
		
			<div class="col-xs-12" style="">
				<a href='javascript:history.back()' style="background: url(${pageContext.request.contextPath}/resources/images/left.png) 0;background-repeat: no-repeat; margin-left: -50%; margin-top: 0px; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px"> </a>
			</div>

		</div>
		
		<div class="row black_header visible-xs" style="margin-top: -5%">
			<div class="col-xs-12">
				<label>Ticket Number. </label><input type="text" value="${tollTransaction.ticketCode}" id="stno" class="head_inp" name="">
				<input type="button" class="inp1" value="Search" onclick="ssearchTicket()">
				<div class="error" id="sterror">Please enter a valid ticket number</div>
				<span id="ssp_notFound" class="error" style="margin-left: 20%">Ticket Not Found</span>			
			</div>
		</div>
		
		<div class="row visible-xs">
			<div class="col-xs-12" style="background-color: #6f7070; max-width:90%; margin:5%; margin-top:2%; border-radius: 20px; min-height: 220px;">
				<img id="simg1" src="<c:url value="/vehicleImages/${tollTransaction.imageAddress}" />" style="max-width:100%;">
			</div>
		</div>
		
		<div class="row form visible-xs" style="min-height: 220px; width: 90%">
			
			<div class="col-xs-12">
				<div class="message" id="smessage">${message}</div>
			</div>
			
			<div class="col-xs-12">
				<label>Vehicle No.</label>
				<input type="text" id="svnum" value="${tollTransaction.vehicleNumber}" class="inp" disabled="disabled" size="15"/>
			</div>
			
			<div class="col-xs-12">
				<label>Ticket No.</label>
				<input type="text" id="stnum" value="${tollTransaction.ticketCode}" class="inp" disabled="disabled" size="20"/>
			</div>
			
			<div class="col-xs-12">
				<label>User.</label>
				<input type="text" id="suser" value="${user.userFirstName} ${user.userLastName}" class="inp" disabled="disabled" size="20"/>
			</div>
			
			<div class="col-xs-12">
				<label>Lane.</label>
				<input type="text" id="slane" value="${lane.laneCode}" class="inp" disabled="disabled" size="10"/>
			</div>
			
			<div class="col-xs-12">
				<label>Remarks.</label>
				<textarea class="inp" id="scomment" name="comment" placeholder="Comments reqired" style="max-width: 150px; max-height: 50px"></textarea>
			</div>
			
			<div class="col-xs-12">
				<label>Amount.</label>
				<input type="text" id="samt" value="${tollTransaction.tollAmt}" class="inp" disabled="disabled" />
			</div>
			
			<div class="col-xs-12">
				<label>Journey.</label>
				<input type="text" id="sjourney" value="${tollTransaction.journeyType}" class="inp" disabled="disabled" />
			</div>
			
			<div class="col-xs-12">
				<span id="ser" class="error_show" style="margin-left: 25%;">${error}</span>
				<span id="ssp_remark" class="error">Please enter Remarks before clicking on cancel ticket</span>
			</div>
			
			<div class="col-xs-12">
				<input type="button" class="inp1" name="" onclick="scancelTic()" value="Cancel Ticket" style="margin-left: 30%; margin-top:-2%">
			</div>
		</div>
		
		<!-- The Modal -->
<div id="myModal" class="modal">
  <span class="close">&times;</span>
  <img class="modal-content" id="img01" width="700" height="500">
  <div id="caption"></div>
</div>


<!-- modal for search button -->
	<!-- The Modal -->
		<div id="myModal1" class="modal" style="">
		
		  <!-- Modal content -->
		  <div class="modal-content" style="position:relative; top:25%;left: -50px; max-width: 300px; margin-left: 35%; background-color: #b9b9b9">
		  	<!-- uncomment this if you want cross on the top right -->
		    <!-- <span class="close">&times;</span> -->
		    <div id="search-modal-content"></div> 
		    	
		    <div class="row">				
				<div class="col-xs-5" style="margin-left: 30%">
					<input type="button" class="inp1" value="OK" id="" onclick="f1()" style="max-width: 80%"/>
				</div>
				
			</div>
		  </div>
		
		</div>
		
<!-- modal for Cancel button -->
	<!-- The Modal -->
		<div id="myModal2" class="modal" style="">
		
		  <!-- Modal content -->
		  <div class="modal-content" style="position:relative; top:25%;left: -50px; max-width: 300px; margin-left: 30%; background-color: #b9b9b9">
		  	<!-- uncomment this if you want cross on the top right -->
		    <!-- <span class="close">&times;</span> -->
		    <p>Are you sure. Do you want to cancel?</p>
		    	
		    <div class="row">				
				<div class="col-xs-5">
					<input type="button" class="inp1" value="Yes" id="yes" onclick="f2()" style="max-width: 80%"/>
				</div>
				
				<div class="col-xs-5">
					<input type="button" class="inp1" value="No" id="No" onclick="f3()" style="max-width: 80%; background-color: #6f7070;"/>
				</div>
				
			</div>
		  </div>
		
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
</body>