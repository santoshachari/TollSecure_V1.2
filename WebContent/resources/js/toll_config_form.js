//prevents right click
//document.addEventListener('contextmenu', event => event.preventDefault());

function supdate() {
	document.getElementById("vehicleclass").value=document.getElementById("svehicleclass").value;
	document.getElementById("tollAmt").value = document.getElementById("stollAmt").value;
	update();
}

function update() {
	if(document.getElementById("vehicleclass").value!="") {
		document.getElementById("tollAmtNew").value=document.getElementById("tollAmt").value;
		document.getElementById("stollAmtNew").value=document.getElementById("stollAmt").value;
	} else {
    	document.getElementById("tollAmtNew").value="";
    	document.getElementById("stollAmtNew").value="";
	}
}


	var curDate1 = new Date()  
    curDate2 = curDate1.getFullYear()+"-"+(curDate1.getMonth()+1)+"-"+curDate1.getDate();
	curDate = curDate1.getDate()+"/"+(curDate1.getMonth()+1)+"/"+curDate1.getFullYear();
    //curDate = curDate+" "+curDate1.getHours()+":"+curDate1.getMinutes()+":"+curDate1.getSeconds();
    //"<div style='display: inline; position:absolute;'>Date: </div>"+"<div style='display: inline; margin-left:200px;'>"+curDate+"</div>";
    document.getElementById("demo").value = curDate;
    document.getElementById("sdemo").value = curDate;
    document.getElementById("stdate").value = curDate2;
    document.getElementById("sstdate").value = curDate2;
    //document.getElementById("demo1").innerHTML = curDate;
//    alert(document.getElementById("stdate").value+"hiall")

function clear1() {
	document.getElementById("tollAmtNew").value="";
	document.getElementById("vehicleclass").value="";
	document.getElementById("tollAmt").innerHTML = "<option value=''></option>"
	document.getElementById("eddate").value="";
	document.getElementById("r1").checked=true;
	
	document.getElementById("stollAmtNew").value="";
	document.getElementById("svehicleclass").value="";
	document.getElementById("stollAmt").innerHTML = "<option value=''></option>"
	document.getElementById("seddate").value="";
	document.getElementById("sr1").checked=true;
}


function ssubmit1() {
	document.getElementById("tollAmtNew").value=document.getElementById("stollAmtNew").value;
	document.getElementById("stdate").value=document.getElementById("sstdate").value;
	document.getElementById("eddate").value=document.getElementById("seddate").value;
	document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value;
	
	if (document.getElementById("sr1").checked==true) {
		document.getElementById("r1").checked=true;
	} else {
		document.getElementById("r2").checked=true;
	}
	
	submit1();
}

//may be need to update this method
function submit1() { 
	var error = false;
	var newAmount = document.getElementById("tollAmtNew").value;
	newAmount = newAmount.trim();
	var newError = false;
	
	if (newAmount==null || newAmount == "" || !(/^\d*\.?\d*$/.test(newAmount))) {
		//$('#sp_laneDirection').removeClass('error').addClass('error_show');
		document.getElementById("sp_update").removeAttribute("class");
		document.getElementById("sp_update").setAttribute("class", "error_show"); 
		newError = true;
	} else {
		newError = false;
		document.getElementById("sp_update").removeAttribute("class");
		document.getElementById("sp_update").setAttribute("class", "error"); 
	}
	
	var startDate = new Date(document.getElementById("stdate").value);
    var endDate = new Date(document.getElementById("eddate").value);
    var smallDateError = false;
    var noDateError = false;
    
    if (startDate == 'Invalid Date' || endDate == 'Invalid Date') {
    	noDateError = true;
    	document.getElementById("sp_date").removeAttribute("class");
    	document.getElementById("sp_date").setAttribute("class", "error");
    	
    	document.getElementById("ssp_date").removeAttribute("class");
    	document.getElementById("ssp_date").setAttribute("class", "error");
    } else {
    	noDateError = false;
    	document.getElementById("sp_date").removeAttribute("class");
    	document.getElementById("sp_date").setAttribute("class", "error_show");
    	
    	document.getElementById("ssp_date").removeAttribute("class");
    	document.getElementById("ssp_date").setAttribute("class", "error_show");
    }
    
   if (startDate > endDate) {
	   smallDateError = true;
   		document.getElementById("sp_small").removeAttribute("class");
   		document.getElementById("sp_small").setAttribute("class", "error");
   		
   		document.getElementById("ssp_small").removeAttribute("class");
   		document.getElementById("ssp_small").setAttribute("class", "error");
    } else {
    	smallDateError = false;
    	document.getElementById("sp_small").removeAttribute("class");
    	document.getElementById("sp_small").setAttribute("class", "error_show");
    	
    	document.getElementById("ssp_small").removeAttribute("class");
    	document.getElementById("ssp_small").setAttribute("class", "error_show");
    }

	if (!error && !newError & !noDateError & !smallDateError) {  
		document.getElementById("form").submit();
	}
}














