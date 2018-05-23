<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en" class="no-js">
	
	<head>
	
	<!-- fevicon -->
 	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Admin Home Mobile</title>
		<script src="${pageContext.request.contextPath}/resources/js/jQuery.js"></script>
		<script>
			//prevents right click
			//document.addEventListener('contextmenu', event => event.preventDefault());
			for(i=0;i<100;i++)history.pushState({}, null, "loginUser"); //encrypting url, also takes care of logout functionality
		</script>
		<script type="text/javascript">

		
		
		    $(window).on('load resize',function(){
		        if($(window).width() > 767){
		            window.location = "${pageContext.request.contextPath}/index/h0me";
		        }
		    });
		    
		 /* var isMobile = false; //initiate as false
		 // device detection
		 if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent) 
		     || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(navigator.userAgent.substr(0,4))) isMobile = true;
		    
		 if (!isMobile) {
			 window.location = "${pageContext.request.contextPath}/index/h0me";
		 } */
		 
		 if(!/Mobile/i.test(navigator.userAgent) && /ipad/i.test(navigator.userAgent) ){
			 window.location = "${pageContext.request.contextPath}/index/h0me";
		    }
		</script>
		
		<!-- for icons -->
		<style>
			@font-face {
				font-family: 'linecons';
				src:url('${pageContext.request.contextPath}/resources/fonts/linecons/linecons.eot');
				src:url('${pageContext.request.contextPath}/resources/fonts/linecons/linecons.eot?#iefix') format('embedded-opentype'),
					url('${pageContext.request.contextPath}/resources/fonts/linecons/linecons.woff') format('woff'),
					url('${pageContext.request.contextPath}/resources/fonts/linecons/linecons.ttf') format('truetype'),
					url('${pageContext.request.contextPath}/resources/fonts/linecons/linecons.svg#linecons') format('svg');
				font-weight: normal;
				font-style: normal;
			}
			
			.icon:before {
				font-family: 'linecons';
				speak: none;
				font-style: normal;
				font-weight: normal;
				font-variant: normal;
				text-transform: none;
				line-height: 1;
				display: inline-block;
				margin-right: 0.6em;
				-webkit-font-smoothing: antialiased;
			}

			.icon-arrow-left:before {
				content: "\e032";
			}
			.icon-arrow-left-2:before {
				content: "\e034";
			}
			.icon-arrow-left-3:before {
				content: "\e036";
			}
			.icon-arrow-left-4:before {
				content: "\e038";
			}

			.icon-news:before {
				content: "\e001";
			}

			.icon-wallet:before {
				content: "\e004";
			}
			
			.icon-display:before {
				content: "\e008";
			}
		
			.signout {
				width: 55px;
				height: 35px;
			    border-radius: 5px;
			    border-style: none;
			    background-color: #000000;
			    color: #ffffff;
			}
			
			html{
				min-height:500px;
			 	height:100%;
  			}
  			
  			body{
				min-height:500px;
			 	height:100%;
  			}
  			
		</style>

		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<meta name="description" content="Multi-Level Push Menu: Off-screen navigation with multiple levels"/>
		<meta name="keywords" content="multi-level, menu, navigation, off-canvas, off-screen, mobile, levels, nested, transform"/>
		<meta name="author" content="Codrops" />
		<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mobile_lobby/normalize.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mobile_lobby/demo.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mobile_lobby/icons.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mobile_lobby/component.css"/>
		<script src="${pageContext.request.contextPath}/resources/js/mobile_lobby/modernizr.custom.js"></script>
	</head>
	<body>
		<div class="container visible-xs" style="background-color: black;  background: lightblue url(${pageContext.request.contextPath}/resources/images/TS_BG_03.jpg) no-repeat fixed center;">
			<!-- Push Wrapper -->
			<div class="mp-pusher" id="mp-pusher">

				<!-- mp-menu -->
				<nav id="mp-menu" class="mp-menu" >
					<div class="mp-level"  style="background-color: #ee9620">
						<h2 class="icon icon-world" >Menu</h2>
						<ul>
							<li class="icon icon-arrow-left">
								<a class="icon icon-display" href="#" >System Configuration</a>
								<div class="mp-level" style="background-color: #ee9620">
									<h2 class="icon icon-display">System Configuration</h2>
									<ul>
										<li>
											<a class="icon" href="${pageContext.request.contextPath}/tollPlaza/showFormForAdd">Plaza</a>
										</li>
										
										<li>
											<a class="icon " href="${pageContext.request.contextPath}/lane/showFormForAdd">Lane</a>
										</li>
										
										<li>
											<a class="icon " href="${pageContext.request.contextPath}/shift/shiftConfigure">Shift</a>
										</li>

										<li>
											<a class="icon" href="${pageContext.request.contextPath}/tollConfig/showFormForUpdate">Vehicle Class</a>
										</li>

										<li>
											<a class="icon" href="${pageContext.request.contextPath}/pass/configurePass">Pass</a>
										</li>

										<li>
											<a class="icon" href="${pageContext.request.contextPath}/index/registerUser">Employee</a>
										</li>

										<li>
											<a class="icon" href="${pageContext.request.contextPath}/floatAmountDetails/showForm">Assign Lane/ Shift</a>
										</li>

										<li>
											<a class="icon" href="${pageContext.request.contextPath}/cashup/declareCash">Cashup</a>
										</li>

										<li>
											<a class="icon" href="${pageContext.request.contextPath}/tollTransaction/manualEntries">Manual Entry</a>
										</li>

									</ul>
								</div>
							</li>
							

							<li class="icon icon-arrow-left">
								<a class="icon icon-news" href="#" >Analysis</a>
								<div class="mp-level" style="background-color: #ee9620">
									<h2 class="icon icon-news">Analysis</h2>
									<ul>
										 <li class="icon icon-arrow-left"> <!--icon-phone icon-shop-->
											<a class="icon" href="#">Revenue And Traffic Analysis</a>
											 <div class="mp-level" style="background-color: #ee9620">
												<h2>Revenue And Traffic Analysis</h2>
												<ul>
													<li><a href="${pageContext.request.contextPath}/jasper/consolidatedTrafficRevenue">Consolidated Traffic And Revenue Report</a></li>
													<li><a href="${pageContext.request.contextPath}/jasper/cashupSummary">Cashup Summary Report</a></li>
													<li><a href="${pageContext.request.contextPath}/jasper/cancelTicketsTransaction">Cancel Tickets Transaction Report</a></li>
												</ul>
											</div> 
										</li>

										 <li class="icon icon-arrow-left"> 
											<a class="icon " href="#">Revenue Analysis</a>
											<div class="mp-level" style="background-color: #ee9620">
												<h2>Revenue Analysis</h2>
												<ul>
													<li><a href="${pageContext.request.contextPath}/jasper/DatewiseLanewiseRevenue">Datewise/ Lanewise Revenue Report</a></li>
													<li><a href="${pageContext.request.contextPath}/jasper/DatewiseRevenue">Datewise Revenue Report</a></li>
													<li><a href="${pageContext.request.contextPath}/jasper/LanewiseRevenue">Lanewise Revenue Report</a></li>

												</ul>
											</div> 
										</li>

										<li class="icon icon-arrow-left"> 										
											<a class="icon " href="#">Traffic Analysis</a>
											 <div class="mp-level" style="background-color: #ee9620">
												<h2>Traffic Analysis</h2>
												<ul>
													<li><a href="${pageContext.request.contextPath}/jasper/consolidatedTraffic">Consolidated Traffic Report</a></li>
													<li><a href="${pageContext.request.contextPath}/jasper/DatewiseLanewiseTraffic">Datewise/ Lanewise Traffic Report</a></li>
													<li><a href="${pageContext.request.contextPath}/jasper/DatewiseTraffic">Datewise Traffic Report</a></li>
													<li><a href="${pageContext.request.contextPath}/jasper/LanewiseTraffic">Lanewise Traffic Report</a></li>
													<li><a href="${pageContext.request.contextPath}/jasper/exemptedTraffic">Exempted Traffic Report</a></li>
													<li><a href="${pageContext.request.contextPath}/jasper/journeyTypeClassification">Journey Type Classsification Report</a></li>
												</ul>
											</div> 
										</li>

										<li class="icon icon-arrow-left">
											<a class="icon " href="#">Pass Analysis</a>
											 <div class="mp-level" style="background-color: #ee9620">
												<h2>Pass Analysis</h2>
												<ul>
													<li><a href="${pageContext.request.contextPath}/jasper/PassRevenue">Monthly Pass Revenue Report</a></li>
													<li><a href="${pageContext.request.contextPath}/jasper/monthlyPassDetail">Monthly Pass Detail Report</a></li>
												</ul>
											</div> 
										</li>

										<li class="icon icon-arrow-left">
											<a class="icon " href="#">Login/ Logout Analysis</a>
											 <div class="mp-level" style="background-color: #ee9620">
												<h2>Login/ Logout Analysis</h2>
												<ul>
													<li><a href="${pageContext.request.contextPath}/jasper/loginLogoutSample">Login/ Logout Report</a></li>
												</ul>
											</div> 
										</li>

										<li class="icon icon-arrow-left">
											<a class="icon " href="#">Short/ Excess Analysis</a>
											 <div class="mp-level" style="background-color: #ee9620">
												<h2>Short/ Excess Analysis</h2>
												<ul>
													<li><a href="${pageContext.request.contextPath}/jasper/shortExcess">Shiftwise Short/ Excess Revenue Report</a></li>
												</ul>
											</div> 
										</li>

									</ul>
								</div>
							</li>


							<li class="icon icon-arrow-left">
								<a class="icon icon-wallet" href="#">Validation</a>
								<div class="mp-level" style="background-color: #ee9620">
									<h2 class="icon icon-wallet">Validation</h2>
									<ul>
										<li>
											<a class="icon" href="${pageContext.request.contextPath}/tollTransaction/vehicleSearch">Vehicle Search</a>
										</li>
										<li>
											<a class="icon" href="${pageContext.request.contextPath}/pass/newPass">Generate Pass</a>
										</li>
										<li>
											<a class="icon" href="${pageContext.request.contextPath}/tollTransaction/cancelTicket">Cancel Ticket</a>
										</li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
				</nav>
				<!-- /mp-menu -->

				<div class="scroller"><!-- this is for emulating position fixed of the nav -->
					<div class="scroller-inner">
						<header class="codrops-header" style='width: 100%; background-color: #ee9620'>
							<div>
							<img src='${pageContext.request.contextPath}/resources/images/TS_Logo_hdpi.png' alt='TollSecure - Securing Toll Business' width="60%"/>
							<input type="image" value="" class="" style="margin-left: 15%;">
							<input type="image" src="${pageContext.request.contextPath}/resources/images/mono-logout.svg" alt="Signout" width="28" height="28"  onclick="localStorage.clear();window.location.href='${pageContext.request.contextPath}/index/logout';">
							</div>
						</header>
						<div class="content clearfix" style="margin-top: 0%; width: 100%; padding: 0%">
							<div class="block block-40 clearfix" style="background-color: #ee9620">
								<p><a href="#" id="trigger" class="" style="color: black; font-size: 0.8em; margin-left:25% "><img src="${pageContext.request.contextPath}/resources/images/menu1.png" width=10% height=10%/></a></p>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<script src="${pageContext.request.contextPath}/resources/js/mobile_lobby/classie.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/mobile_lobby/mlpushmenu.js"></script>
		<script>
			new mlPushMenu( document.getElementById( 'mp-menu' ), document.getElementById( 'trigger' ) );
		</script>
		
		<script>
	    	//handling user logging using localStorage
	    	localStorage.setItem("status", true);
    	</script>
    
	</body>
</html>