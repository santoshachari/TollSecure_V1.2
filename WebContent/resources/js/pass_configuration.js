//prevents right click
//document.addEventListener('contextmenu', event => event.preventDefault());

var curDate1 = new Date()  
curDate2 = curDate1.getFullYear()+"-"+(curDate1.getMonth()+1)+"-"+curDate1.getDate();
curDate = curDate1.getDate()+"/"+(curDate1.getMonth()+1)+"/"+curDate1.getFullYear();

document.getElementById("demo").value = curDate;
document.getElementById("stdate").value = curDate2;

document.getElementById("sdemo").value = curDate;
document.getElementById("sstdate").value = curDate2;

function sshowAmount() {
	document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value;
	document.getElementById("effectiveTo").value=document.getElementById("seffectiveTo").value;
	document.getElementById("vehicleclass").value=document.getElementById("svehicleclass").value;
	document.getElementById("passType").value=document.getElementById("spassType").value;
	showAmount();
}

function showAmount() {
	///gat all the required fields and create an url 
	var plazaId = document.getElementById("tollPlazas").value;
	var effectiveTo = document.getElementById("effectiveTo").value;
	var vehicleClass = document.getElementById("vehicleclass").value;
	var passType = document.getElementById("passType").value;
	
	//return in case of any missing values
	if (plazaId == "" || effectiveTo =="" || vehicleClass == "" || passType == "") return;
	
	//call the ajax method
	nowShow(plazaId, effectiveTo, vehicleClass, passType);
}

function ssubmit1() {
	document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value
	document.getElementById("effectiveTo").value=document.getElementById("seffectiveTo").value;
	document.getElementById("vehicleclass").value=document.getElementById("svehicleclass").value;
	document.getElementById("passType").value=document.getElementById("spassType").value;
	document.getElementById("newAmt").value=document.getElementById("snewAmt").value;
	submit1();
}

function submit1() {
	var dateError = false;
	var effectiveTo = document.getElementById("effectiveTo").value;
	
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
	
	var dateError1 = false;
	var today = new Date();
	var effectiveTo1 = new Date(effectiveTo);
	
	if (today > effectiveTo1) {
		dateError1 = true;
		document.getElementById("sp_effectiveTo1").removeAttribute("class");
		document.getElementById("sp_effectiveTo1").setAttribute("class", "error_show");
		
		document.getElementById("ssp_effectiveTo1").removeAttribute("class");
		document.getElementById("ssp_effectiveTo1").setAttribute("class", "error_show");
	} else {
		dateError1 = false;
		document.getElementById("sp_effectiveTo1").removeAttribute("class");
		document.getElementById("sp_effectiveTo1").setAttribute("class", "error"); 
		
		document.getElementById("ssp_effectiveTo1").removeAttribute("class");
		document.getElementById("ssp_effectiveTo1").setAttribute("class", "error"); 
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
	var newAmt = document.getElementById("newAmt").value;
	
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
	
	if (!vcError && !ptError && !dateError && !dateError1 && !amtError) {
		document.getElementById("form").submit();
	}
}

function supdate() {
	document.getElementById("amt").value=document.getElementById("samt").value;
	update();
}

function update() {
	var oldAmt = document.getElementById("amt").value;
	
	if (oldAmt=="") return;
	
	document.getElementById("newAmt").value = oldAmt;
	document.getElementById("sub").value="Update";
	
	document.getElementById("snewAmt").value = oldAmt;
	document.getElementById("ssub").value="Update";
}

function clear1() {
	document.getElementById("passType").value = "";
	document.getElementById("amt").value="";
	document.getElementById("vehicleclass").value = "";
	document.getElementById("newAmt").value="";
	document.getElementById("sub").value="Submit";
	
	document.getElementById("spassType").value = "";
	document.getElementById("samt").value="";
	document.getElementById("svehicleclass").value = "";
	document.getElementById("snewAmt").value="";
	document.getElementById("ssub").value="Submit";
}










