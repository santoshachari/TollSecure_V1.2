//prevents right click
//document.addEventListener('contextmenu', event => event.preventDefault());

var curDate1 = new Date();
curDate2 = curDate1.getFullYear()+"-"+(curDate1.getMonth()+1)+"-"+curDate1.getDate();
curDate = curDate1.getDate()+"/"+(curDate1.getMonth()+1)+"/"+curDate1.getFullYear();

document.getElementById("demo").value = curDate;
document.getElementById("sdemo").value = curDate;
document.getElementById("stdate").value = curDate2;

function fillEndDate(str) {
	
	if (str=="ntd") {
		var date = new Date();
		date.setDate(date.getDate() + 30);

		var strDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		var strs = strDate.split("-");
		
		for (i=0;i<3;i++) {
			if (strs[i].length==1) {
				strs[i]="0"+strs[i];
			}
		}
		document.getElementById("effectiveTo").value=strs[2]+"/"+strs[1]+"/"+strs[0];
		document.getElementById("efto").value=strs[0]+"-"+strs[1]+"-"+strs[2];
		document.getElementById("effectiveTo1").value=strs[0]+"-"+strs[1]+"-"+strs[2];
		document.getElementById("seffectiveTo").value=strs[2]+"/"+strs[1]+"/"+strs[0];
	} 
	
	else if (str == "cml") {
		var date = new Date();
		 y = date.getFullYear(), m = date.getMonth();
		 var lastDay1 = new Date(y, m + 1, 0);
		 
		 var strDate = lastDay1.getFullYear()+"-"+(lastDay1.getMonth()+1)+"-"+lastDay1.getDate();
			var strs = strDate.split("-");
			
			for (i=0;i<3;i++) {
				if (strs[i].length==1) {
					strs[i]="0"+strs[i];
				}
			}
			document.getElementById("effectiveTo").value=strs[2]+"/"+strs[1]+"/"+strs[0];
			document.getElementById("effectiveTo1").value=strs[0]+"-"+strs[1]+"-"+strs[2];
			document.getElementById("efto").value=strs[0]+"-"+strs[1]+"-"+strs[2];
			document.getElementById("seffectiveTo").value=strs[2]+"/"+strs[1]+"/"+strs[0];
	}
	
	else if (str == "nml") {
		var date = new Date();
		 y = date.getFullYear(), m = date.getMonth();
		 var lastDay1 = new Date(y, m + 2, 0);
		 
		 var strDate = lastDay1.getFullYear()+"-"+(lastDay1.getMonth()+1)+"-"+lastDay1.getDate();
			var strs = strDate.split("-");
			
			for (i=0;i<3;i++) {
				if (strs[i].length==1) {
					strs[i]="0"+strs[i];
				}
			}
			document.getElementById("effectiveTo").value=strs[2]+"/"+strs[1]+"/"+strs[0];
			document.getElementById("effectiveTo1").value=strs[0]+"-"+strs[1]+"-"+strs[2];
			document.getElementById("efto").value=strs[0]+"-"+strs[1]+"-"+strs[2];
			document.getElementById("seffectiveTo").value=strs[2]+"/"+strs[1]+"/"+strs[0];
	}
	
	 if(/Mobile/i.test(navigator.userAgent) && !/ipad/i.test(navigator.userAgent) ){
		   sshowAmount();
	    } else {
	    	showAmount();
	    }
	
}

function sshowAmount() {
	document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value;
	document.getElementById("vehicleclass").value=document.getElementById("svehicleclass").value;
	document.getElementById("passType").value=document.getElementById("spassType").value;
	showAmount();
}

function showAmount() {
	///gat all the required fields and create an url 
	var plazaId = document.getElementById("tollPlazas").value;
	var effectiveTo = document.getElementById("effectiveTo1").value;
	var vehicleClass = document.getElementById("vehicleclass").value;
	var passType = document.getElementById("passType").value;
	
	//return in case of any missing values
	if (plazaId == "" || effectiveTo =="" || vehicleClass == ""  || passType == ""){
		document.getElementById("amt").innerHTML = "<option value=''></option>";
		document.getElementById("samt").innerHTML = "<option value=''></option>";
		return;
	}
	
	if (passType == "LOCAL VIP") {
		document.getElementById("amt").innerHTML = "<option value='0.00'>0.00</option>";
		document.getElementById("samt").innerHTML = "<option value='0.00'>0.00</option>";
		document.getElementById("validiy").value="";
		document.getElementById("validiy").disabled = true;
		
		document.getElementById("svalidiy").value="";
		document.getElementById("svalidiy").disabled = true;
		return;
	}
	
	//call the ajax method
	nowShow(plazaId, effectiveTo, vehicleClass, passType);
}


function ssubmit1() {
	document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value;
	document.getElementById("vehicleclass").value=document.getElementById("svehicleclass").value;
	document.getElementById("passType").value=document.getElementById("spassType").value;
	document.getElementById("vnum").value=document.getElementById("svnum").value;
	document.getElementById("validiy").value=document.getElementById("svalidiy").value;
	document.getElementById("amt").value=document.getElementById("samt").value;
	submit1();
}


function submit1() {
	var dateError = false;
	var effectiveTo = document.getElementById("effectiveTo1").value;
	
	if (effectiveTo=="" || effectiveTo==null) {
		dateError = true;
		document.getElementById("sp_effectiveTo").removeAttribute("class");
		document.getElementById("sp_effectiveTo").setAttribute("class", "error_show");
		
		document.getElementById("ssp_effectiveTo").removeAttribute("class");
		document.getElementById("ssp_effectiveTo").setAttribute("class", "error_show");
	} else {
		dateError = false;
		document.getElementById("sp_effectiveTo").removeAttribute("class");
		document.getElementById("sp_effectiveTo").setAttribute("class", "error"); 
		
		document.getElementById("ssp_effectiveTo").removeAttribute("class");
		document.getElementById("ssp_effectiveTo").setAttribute("class", "error"); 
	}
	
	var vcError = false;
	var vehicleClass = document.getElementById("vehicleclass").value;
	
	if (vehicleClass=="" || vehicleClass==null) {
		vcError = true;
		document.getElementById("sp_vclass").removeAttribute("class");
		document.getElementById("sp_vclass").setAttribute("class", "error_show"); 
		
		document.getElementById("ssp_vclass").removeAttribute("class");
		document.getElementById("ssp_vclass").setAttribute("class", "error_show"); 
	} else {
		vcError = false;
		document.getElementById("sp_vclass").removeAttribute("class");
		document.getElementById("sp_vclass").setAttribute("class", "error"); 
		
		document.getElementById("ssp_vclass").removeAttribute("class");
		document.getElementById("ssp_vclass").setAttribute("class", "error"); 
	}
	
	var ptError = false;
	var passType = document.getElementById("passType").value;
	
	if (passType=="" || passType==null) {
		ptError = true;
		document.getElementById("sp_passType").removeAttribute("class");
		document.getElementById("sp_passType").setAttribute("class", "error_show"); 
		
		document.getElementById("ssp_passType").removeAttribute("class");
		document.getElementById("ssp_passType").setAttribute("class", "error_show"); 
	} else {
		ptError = false;
		document.getElementById("sp_passType").removeAttribute("class");
		document.getElementById("sp_passType").setAttribute("class", "error"); 
		
		document.getElementById("ssp_passType").removeAttribute("class");
		document.getElementById("ssp_passType").setAttribute("class", "error"); 
	}

	var amtError = false;
	var newAmt = document.getElementById("amt").value;
	
	if (newAmt=="" || newAmt == null) {
		amtError = true;
		document.getElementById("sp_newAmt").removeAttribute("class");
		document.getElementById("sp_newAmt").setAttribute("class", "error_show"); 
		
		document.getElementById("ssp_newAmt").removeAttribute("class");
		document.getElementById("ssp_newAmt").setAttribute("class", "error_show"); 
	} else {
		amtError = false;
		document.getElementById("sp_newAmt").removeAttribute("class");
		document.getElementById("sp_newAmt").setAttribute("class", "error"); 
		
		document.getElementById("ssp_newAmt").removeAttribute("class");
		document.getElementById("ssp_newAmt").setAttribute("class", "error"); 
	}
	
	var vehicleNumber = document.getElementById("vnum").value;
	var vnumError = false;
	
	//regular expression for vehicle number
	 if (!(/^[a-zA-Z]{2}[0-9]{1,2}[a-zA-Z]{1,3}[0-9]{4}$/.test(vehicleNumber))) {
		 document.getElementById('sp_vnum').removeAttribute('class');
		 document.getElementById('sp_vnum').setAttribute('class','error_show');
		 
		 document.getElementById('ssp_vnum').removeAttribute('class');
		 document.getElementById('ssp_vnum').setAttribute('class','error_show');
		 vnumError = true;
	 } else {
		 document.getElementById('sp_vnum').removeAttribute('class');
		 document.getElementById('sp_vnum').setAttribute('class','error');
		 
		 document.getElementById('ssp_vnum').removeAttribute('class');
		 document.getElementById('ssp_vnum').setAttribute('class','error');
		 vnumError = false;
	 }
	 
	 //check for number of valid trips
	 var validity = document.getElementById("validiy").value;
	 var valErr = false;
	 
	 if (passType=="MONTHLY" && !/^\d+$/.test(validity)) {
		 document.getElementById('sp_val').removeAttribute('class');
		 document.getElementById('sp_val').setAttribute('class','error_show');
		 
		 document.getElementById('ssp_val').removeAttribute('class');
		 document.getElementById('ssp_val').setAttribute('class','error_show');
		 valErr=true;
	 } else {
		 document.getElementById('sp_val').removeAttribute('class');
		 document.getElementById('sp_val').setAttribute('class','error');
		 
		 document.getElementById('ssp_val').removeAttribute('class');
		 document.getElementById('ssp_val').setAttribute('class','error');
		 valErr=false;
	 }
	
	if (!vcError && !ptError && !dateError  && !amtError && !vnumError && !valErr) {
		var plazaId = document.getElementById("tollPlazas").value;
		//save the pass and fill the receipt element and print the page
		printReceipt(plazaId, vehicleNumber, newAmt, passType, vehicleClass, effectiveTo, curDate2, validity);
	}
}



