<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>

<script>
	//prevents right click
	//document.addEventListener('contextmenu', event => event.preventDefault());
	for(i=0;i<100;i++)history.pushState({}, null, "loginUser"); //encrypting url, also takes care of logout functionality
</script>

<title>Admin - Home</title>

<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">

<link type="text/css"
	  rel = "stylesheet"
	  href="${pageContext.request.contextPath}/resources/css/lobby_style.css"
	/>
<link type="text/css"
	  rel = "stylesheet"
	  href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css"
	/>
	
<style>

.leftHalf {
  background-color: red;
   width: 50%;
   position: absolute;
   left: 0px;
   height: 100%;
}
.rightHalf {
   background-color: black;
   width: 50%;
   position: absolute;
   right: 0px;
   height: 100%;
}




	#vtab > div > ul > li.selected {
		min-width: 191px;
        z-index: 10;
       /* background-image: url(${pageContext.request.contextPath}/resources/images/TS_SelectedButton.png); */
        background: transparent url(${pageContext.request.contextPath}/resources/images/TS_SelectedButton.png) repeat 0 0;
		background-size: contain;
		background-color: transparent;
   
    }
    
    ul {
	  list-style-type: none;
	}
</style>
	
<script src="${pageContext.request.contextPath}/resources/js/jQuery.js"></script>
    
<script type="text/javascript">

    $(window).on('load resize',function(){
        if($(window).width() < 768){
            window.location = "${pageContext.request.contextPath}/index/mobilehome";
        }
    });
    
	 /* var isMobile = false; //initiate as false
	 // device detection
	 if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent) 
	     || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(navigator.userAgent.substr(0,4))) isMobile = true;
	    
	 if (isMobile) {
		 window.location = "${pageContext.request.contextPath}/index/mobilehome";
	 } */
	 
	 if(/Mobile/i.test(navigator.userAgent) && !/ipad/i.test(navigator.userAgent) ){
		 window.location = "${pageContext.request.contextPath}/index/mobilehome";
	    } 
	 
</script>
    
</head>

<body onload="checkAndSetSession()" style="background-image: url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg)" class="visible-sm visible-lg visible-md hidden-xs">
	<div class="container nopadding">
		
		<div class="row">
			<div class="col-sm-12 header" style="min-height: 80px; position:relative; left: -5.1%; min-width: 111.3%;">
					<div class="row">
						<div class="col-xs-3">
							<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png" class="pull-left" height="52" width="225"/></a>
						</div>
						<div class="col-xs-5">
							<!--Write something here to get it in middle of the header-->
							<div></div>
						</div>
						<div class="col-xs-4" style="position: relative; top: 25px; left: 40px;">
							<span style="margin-right: 10px;"><!--Contact: 9999999999 --></span>
							<input type="button" value="Signout" class="signout" style="margin-left: 15%;" onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
						</div>
					</div>
				</div>
		  </div>
		  
		  
		  <div id="vtab" class="row">
				<div class="col-md-2 sidebar" style="margin-left: -7%; width: 192px;">
					<ul>
			            <li class="home selected" style="padding-top: 50%; color: white; min-height: 166.6px; min-width: 191px;">System Configuration</li>
			            <li class="login" style="padding-top: 50%; color: white; min-height: 166.6px;  min-width: 191px;"> Analysis</li>
			            <li class="support" style="padding-top: 50%; color: white; min-height: 166.6px;  min-width: 191px;">Validation</li>
			        </ul>
				</div>	
				
				<div class="col-sm-10 maincontent">
						<div class="row">
							<div class="col-md-12">
					            <div class="row">
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/tollPlaza/showFormForAdd" class="button">Plaza</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/lane/showFormForAdd" class="button">Lane</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/shift/shiftConfigure" class="button">Shift</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/tollConfig/showFormForUpdate" class="button">Vehicle Class</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/pass/configurePass" class="button">Pass</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/index/registerUser" class="button">Employee</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/floatAmountDetails/showForm" class="button">Assign Lane/ Shift</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/cashup/declareCash" class="button">Cash up</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/tollTransaction/manualEntries" class="button">Manual Entry</a>
					            			</li>
					            		</ul>
					            	</div>
					            </div>
					        </div>
					        <!-- <div class="col-md-12" style="position: absolute; top: -25px"> -->
					        <div class="col-sm-12">
					            <div class="row report">
					            	<div class="col-sm-4 innermost" >
							            <ul>
							           		<li>
							           		<a href="#" class="button">Revenue & Traffic Analysis</a>
												<%-- <a href="${pageContext.request.contextPath}/report/consolidatedTrafficAndRevenue" class="button">Revenue & Traffic Analysis</a> --%>
							           			<%-- <a href="${pageContext.request.contextPath}/report/consolidatedTrafficAndRevenue" class="button" style="background: transparent; border:0; background-image: url(${pageContext.request.contextPath}/resources/images/but1.png); background-size: cover;">Revenue And Traffic <br/>Analysis</a> --%>
							            		<!--<a href="#" class="button" style="background: transparent; border:0; background-image: url(${pageContext.request.contextPath}/resources/images/but1.png); background-size: cover;">Revenue And Traffic <br/>Analysis</a>-->
							            			<!-- <ul style="position: absolute; top: 80px; left: 238px"> -->
							            			<ul style="position: absolute; top: 40%; left: 77%">
							            				<li>	
							            					<!-- add padding here to change the dimension
							            					style="padding:0;" -->
							            					<a href="${pageContext.request.contextPath}/jasper/consolidatedTrafficRevenue" style="padding:25px; border-radius:10px 10px 0px 0px"> Consolidated Traffic & Revenue Report </a>
							            				</li>
							            				
							            				<li>
							            					<a href="${pageContext.request.contextPath}/jasper/cashupSummary" style="padding:25px;"> Cashup Summary Report </a>
							            				</li>
							            				
							            				<li>
							            					<a href="${pageContext.request.contextPath}/jasper/cancelTicketsTransaction" style="padding:25px; border-radius:0px 0px 10px 10px"> Cancel Tickets Transaction Report </a>
							            				</li>							            				
							            			</ul>
							            	</li>
							            </ul>
					            	</div>
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="#" class="button">Pass Analysis</a>
					            				<%-- <a href="${pageContext.request.contextPath}/pass/passAnalysis" class="button" style="background: transparent; border:0; background-image: url(${pageContext.request.contextPath}/resources/images/but1.png) ; background-size: cover; padding-top: 40">Pass Analysis <br/>&nbsp;</a> --%>
					            				<%-- <a href="${pageContext.request.contextPath}/pass/passAnalysis" class="button">Pass Analysis</a> --%>
					            				<ul style="position: absolute; top: 40%; left: 77%">
					            					<li><a href="${pageContext.request.contextPath}/jasper/PassRevenue" style="padding:25px; border-radius:10px 10px 0px 0px"/>Monthly Pass Revenue Report</a></li>
					             					<li><a href="${pageContext.request.contextPath}/jasper/monthlyPassDetail" style="padding:25px; border-radius:0px 0px 10px 10px"/>Monthly Pass Detail Report</a></li>
					            				</ul>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost col-sm-offset-1 notordinary1">
					            		<ul>
					            			<li> 				
					            				<%-- <a href="#" class="button" style="background: transparent; border:0; background-image: url(${pageContext.request.contextPath}/resources/images/but1.png) ; background-size: cover; padding-top: 40">Revenue Analysis <br/> &nbsp;</a> --%>
					            				<a href="#" class="button">Revenue Analysis</a>
					            				<ul style="position: absolute; top: 40%; left: 77%">
					            					<li><a href="${pageContext.request.contextPath}/jasper/DatewiseLanewiseRevenue" style="padding:25px; border-radius:10px 10px 0px 0px">Datewise/ Lanewise Revenue Report</a></li>
					            					<li><a href="${pageContext.request.contextPath}/jasper/DatewiseRevenue" style="padding:25px;">Datewise Revenue</a></li>
					            					<li><a href="${pageContext.request.contextPath}/jasper/LanewiseRevenue" style="padding:25px; border-radius:0px 0px 10px 10px">Lanewise Revenue</a></li>
					            					<%-- <li><a href="${pageContext.request.contextPath}/jasper/PassRevenue" style="padding:25px; border-radius:0px 0px 10px 10px">Monthly Pass Revenue Report</a></li> --%>
					            				</ul>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost notordinary1">
					            		<ul>
					            			<li>
					            				<%-- <a href="#" class="button" style="background: transparent; border:0; background-image: url(${pageContext.request.contextPath}/resources/images/but1.png) ; background-size: cover;">Login Logout <br/> Analysis</a> --%>
					            				<a href="#" class="button">Login/ Logout Analysis</a>
					            				<ul style="position: absolute; top: 40%; left: 77%">
					            					<li><a href="${pageContext.request.contextPath}/jasper/loginLogoutSample" style="padding:25px; border-radius:10px 10px 10px 10px">Login/ Logout Report</a></li>
					            				</ul>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<%-- <a href="#" class="button" style="background: transparent; border:0; background-image: url(${pageContext.request.contextPath}/resources/images/but1.png) ; background-size: cover;">traffic Analysis<br/>&nbsp;</a> --%>
					            				<a href="#" class="button">traffic Analysis</a>
					            				<ul style="position: absolute; top: -200%; left: 77%;">
					            					<li><a href="${pageContext.request.contextPath}/jasper/consolidatedTraffic" style="padding:25px; border-radius:10px 10px 0px 0px">Consolidated Traffic Report</a></li>
					            					<li><a href="${pageContext.request.contextPath}/jasper/DatewiseLanewiseTraffic" style="padding:25px;">Datewise/ Lanewise Traffic Report</a></li>
					            					<li><a href="${pageContext.request.contextPath}/jasper/DatewiseTraffic" style="padding:25px;">Datewise Traffic Report</a></li>
					            					<li><a href="${pageContext.request.contextPath}/jasper/LanewiseTraffic" style="padding:25px; border-radius:0px 0px 10px 10px">Lanewise Traffic Report</a></li>
					            					<!-- <li><a href="#" style="padding:25px;">Traffic Report By Journey Type</a></li> -->
					            					<li><a href="${pageContext.request.contextPath}/jasper/exemptedTraffic" style="padding:25px; border-radius: 0px 0px 10px 10px">Exempted Traffic Report</a></li>	
					            					<li><a href="${pageContext.request.contextPath}/jasper/journeyTypeClassification" style="padding:25px; border-radius: 0px 0px 10px 10px">Journey Type Classification Report</a></li>		
					            				</ul>
					            			
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-sm-4 innermost col-sm-offset-1 notordinary1">
					            		<ul>
					            			<li>
					            				<%-- <a href="#" class="button" style="background: transparent; border:0; background-image: url(${pageContext.request.contextPath}/resources/images/but1.png) ; background-size: cover;">Short/ Excess<br/> Report</a> --%>
					            				<a href="#" class="button">Short/ Excess Report</a>
					            					<ul style="position: absolute; top: 40%; left: 77%">
					            						<li><a href="${pageContext.request.contextPath}/jasper/shortExcess" style="padding:25px; border-radius:10px 10px 10px 10px;">Shiftwise Short/ Excess Revenue Report</a></li>
					            					</ul>
					            			</li>
					            		</ul>
					            	</div> 
					            </div>
					        </div>
					        <div class="col-sm-12">
					           <div class="row">
					            	<%-- <div class="col-md-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="#" class="button">Before Validation</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-md-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="#" class="button">Overload Vehicle</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-md-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/pass/newPass" class="button">Generate Pass</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-md-4 innermost col-sm-offset-1 notordinary">
					            		<ul>
					            			<li>
					            				<a href="#" class="button">After Validation</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-md-4 innermost notordinary">
					            		<ul>
					            			<li>
					            				<a href="#" class="button">Missed Vehicle</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-md-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/tollTransaction/vehicleSearch" class="button">Vehicle Search</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-md-4 innermost" style="position:absolute; top:33%; left: 67%">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/pass/newPass" class="button">Pass</a>
					            			</li>
					            		</ul>
					            	</div>
					            	<div class="col-md-4 col-sm-offset-1 innermost notordinary">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/tollTransaction/cancelTicket" class="button">Cancel Ticket</a>
					            			</li>
					            		</ul>
					            	</div> --%>
					            	
					            	<div class="col-sm-4 innermost">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/tollTransaction/vehicleSearch" class="button">Vehicle Search</a>
					            			</li>
					            		</ul>
					            	</div>
					            	
					            	<div class="col-sm-4 col-sm-offset-5 innermost" style="position: relative; left:-42%">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/pass/newPass" class="button">Generate Pass</a>
					            			</li>
					            		</ul>
					            	</div>
					            	
					            	<div class="col-sm-4 col-sm-offset-5 innermost" style="position: relative; left:-42%">
					            		<ul>
					            			<li>
					            				<a href="${pageContext.request.contextPath}/tollTransaction/cancelTicket" class="button">Cancel Ticket</a>
					            			</li>
					            		</ul>
					            	</div>
					            	
					            </div>
					        </div>    
				    </div>
					</div>
				</div>
				

				
				<div class="col-sm-12 footer  visible-lg" style="position: relative; left: -10%; min-width: 117.5%; min-height: 70px">
					<div class="col-sm-5">
				
					</div>
						<div class="col-sm-4">
							<div style="position: relative; top:20px;">Copy rights 2017 @ TollSecure India Pvt. Ltd</div>
						</div>
					<div class="col-sm-3">
						
					</div>			
				</div>
		</div>
		  
		  
		  				<div hidden id="tabnumber">${tabNumber}</div>
	</body>
				
	<script type="text/javascript">
		var tab_no = document.getElementById("tabnumber").innerHTML;
        $(function() {
            var $items = $('#vtab>div>ul>li');
            $items.mouseover(function() {
                $items.removeClass('selected');
                $(this).addClass('selected');

                var index = $items.index($(this));
                $('#vtab>div>div>div').hide().eq(index).show();
            }).eq(tab_no).mouseover();
        });
    </script>
    
    <script>
    	//handling user logging using localStorage
    	localStorage.setItem("status", true);
    </script>
    
</html>
































