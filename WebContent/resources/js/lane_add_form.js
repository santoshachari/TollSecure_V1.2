//prevents right click
//document.addEventListener('contextmenu', event => event.preventDefault());

//setting the drop down list to the tollPlaza to the selected plaza on page loading
var defPlazaValue = document.getElementById("defplaza").value;
if (defPlazaValue != "" && defPlazaValue != null) document.getElementById("tollPlazas").value=defPlazaValue;

function ssubmit1() {
	document.getElementById("from").value = document.getElementById("sfrom").value;
	document.getElementById("to").value = document.getElementById("sto").value;
	document.getElementById("tollPlazas").value = document.getElementById("stollPlazas").value;
	document.getElementById("nooflanes").value = document.getElementById("snooflanes").value;
	submit1();
}

function submit1() { 
	var error = false;
	var nooflanesError = false;
	
	var from = document.getElementById("from").value.trim();
	var to = document.getElementById("to").value.trim();
	var direction = from+" To "+to;
	document.getElementById("ldir").value=direction;
	direction = direction.trim();
	if (from==null || from=="" || to=="" || to==null || direction==null || direction=="") {
		document.getElementById("sp_laneDirection").removeAttribute("class");
		document.getElementById("sp_laneDirection").setAttribute("class", "error_show"); 
		
		document.getElementById("ssp_laneDirection").removeAttribute("class");
		document.getElementById("ssp_laneDirection").setAttribute("class", "error_show"); 
		error = true;
	} else {
		error = false;
		document.getElementById("sp_laneDirection").removeAttribute("class");
		document.getElementById("sp_laneDirection").setAttribute("class", "error"); 
		
		document.getElementById("ssp_laneDirection").removeAttribute("class");
		document.getElementById("ssp_laneDirection").setAttribute("class", "error"); 
	}
	
	var nooflanes = document.getElementById("nooflanes").value;
	if (nooflanes=="" || nooflanes==null) {
		document.getElementById("sp_nooflanes").removeAttribute("class");
		document.getElementById("sp_nooflanes").setAttribute("class", "error_show");
		
		document.getElementById("ssp_nooflanes").removeAttribute("class");
		document.getElementById("ssp_nooflanes").setAttribute("class", "error_show");
		nooflanesError=true;
	} else {
		document.getElementById("sp_nooflanes").removeAttribute("class");
		document.getElementById("sp_nooflanes").setAttribute("class", "error");
		
		document.getElementById("ssp_nooflanes").removeAttribute("class");
		document.getElementById("ssp_nooflanes").setAttribute("class", "error");
		nooflanesError=false;
	}
	
	if (! error && !nooflanesError) {  
		document.getElementById("form").submit()
	}
}
		
//this needs to be updates in version 2 -- not done till now
function clearForm() {
	document.getElementById("from").value="";
	document.getElementById("to").value="";
	document.getElementById("nooflanes").value="";
	
	document.getElementById("sfrom").value="";
	document.getElementById("sto").value="";
	document.getElementById("snooflanes").value="";
	
	//change the button value to Create again
	document.getElementById("cte").value="Create";
	document.getElementById("scte").value="Create";
}













