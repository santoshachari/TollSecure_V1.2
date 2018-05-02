//prevents right click
//document.addEventListener('contextmenu', event => event.preventDefault());

function ssubmit1() {
	document.getElementById("date").value=document.getElementById("sdate").value;
	document.getElementById("tollPlazas").value=document.getElementById("stollPlazas").value;
	document.getElementById("lanes").value=document.getElementById("slanes").value;
	document.getElementById("shifts").value=document.getElementById("sshifts").value;
	document.getElementById("users").value=document.getElementById("susers").value;
	document.getElementById("ftamt").value=document.getElementById("sftamt").value;
	
	submit1();
}

//submit button
function submit1() {

	var dateError = true;
	var tollPlazaError = true;
	var laneError = true;
	var shiftError = true;
	var userError = true;
	var amtError = true;
	
	var date = document.getElementById("date").value;
	var plaza = document.getElementById("tollPlazas").value;
	var lane = document.getElementById("lanes").value;
	var shift = document.getElementById("shifts").value;
	var user = document.getElementById("users").value;
	var amt=document.getElementById("ftamt").value;

	
	if (date=null||date=="") {
		dateError=true;
		document.getElementById("sp_date").removeAttribute("class");
		document.getElementById("sp_date").setAttribute("class", "error_show");
		
		document.getElementById("ssp_date").removeAttribute("class");
		document.getElementById("ssp_date").setAttribute("class", "error_show");
	} else {
		dateError=false;
		document.getElementById("sp_date").removeAttribute("class");
		document.getElementById("sp_date").setAttribute("class", "error"); 
		
		document.getElementById("ssp_date").removeAttribute("class");
		document.getElementById("ssp_date").setAttribute("class", "error"); 
	}

	if (plaza==null||plaza=="") {
		tollPlazaError=true;
		document.getElementById("sp_tollPlazas").removeAttribute("class");
		document.getElementById("sp_tollPlazas").setAttribute("class", "error_show"); 
		
		document.getElementById("ssp_tollPlazas").removeAttribute("class");
		document.getElementById("ssp_tollPlazas").setAttribute("class", "error_show"); 
	} else {
		tollPlazaError=false;
		document.getElementById("sp_tollPlazas").removeAttribute("class");
		document.getElementById("sp_tollPlazas").setAttribute("class", "error"); 
		
		document.getElementById("ssp_tollPlazas").removeAttribute("class");
		document.getElementById("ssp_tollPlazas").setAttribute("class", "error"); 
	}
	
	if (lane==null||lane=="") {
		laneError=true;
		document.getElementById("sp_lanes").removeAttribute("class");
		document.getElementById("sp_lanes").setAttribute("class", "error_show"); 
		
		document.getElementById("ssp_lanes").removeAttribute("class");
		document.getElementById("ssp_lanes").setAttribute("class", "error_show"); 
	} else {
		laneError=false;
		document.getElementById("sp_lanes").removeAttribute("class");
		document.getElementById("sp_lanes").setAttribute("class", "error"); 
		
		document.getElementById("ssp_lanes").removeAttribute("class");
		document.getElementById("ssp_lanes").setAttribute("class", "error"); 
	}
	
	if (shift==null||shift=="") {
		shiftError=true;
		document.getElementById("sp_shhifts").removeAttribute("class");
		document.getElementById("sp_shhifts").setAttribute("class", "error_show"); 
		
		document.getElementById("ssp_shhifts").removeAttribute("class");
		document.getElementById("ssp_shhifts").setAttribute("class", "error_show"); 
	} else {
		shiftError=false;
		document.getElementById("sp_shhifts").removeAttribute("class");
		document.getElementById("sp_shhifts").setAttribute("class", "error"); 
		
		document.getElementById("ssp_shhifts").removeAttribute("class");
		document.getElementById("ssp_shhifts").setAttribute("class", "error"); 
	}
	
	if (user==null||user=="") {
		userError=true;
		document.getElementById("sp_users").removeAttribute("class");
		document.getElementById("sp_users").setAttribute("class", "error_show"); 
		
		document.getElementById("ssp_users").removeAttribute("class");
		document.getElementById("ssp_users").setAttribute("class", "error_show"); 
	} else {
		userError=false;
		document.getElementById("sp_users").removeAttribute("class");
		document.getElementById("sp_users").setAttribute("class", "error");
		
		document.getElementById("ssp_users").removeAttribute("class");
		document.getElementById("ssp_users").setAttribute("class", "error");
	}
	
	if (amt==null || amt == "" || !(/^\d*\.?\d*$/.test(amt))) {
		document.getElementById("sp_ftamt").removeAttribute("class");
		document.getElementById("sp_ftamt").setAttribute("class", "error_show"); 
		
		document.getElementById("ssp_ftamt").removeAttribute("class");
		document.getElementById("ssp_ftamt").setAttribute("class", "error_show"); 
		amtError = true;
	} else {
		amtError = false;
		document.getElementById("sp_ftamt").removeAttribute("class");
		document.getElementById("sp_ftamt").setAttribute("class", "error");
		
		document.getElementById("ssp_ftamt").removeAttribute("class");
		document.getElementById("ssp_ftamt").setAttribute("class", "error");
	}

	if (!dateError && !tollPlazaError && !laneError && !shiftError && !userError && !amtError) {
		document.getElementById("form").submit();

	} 
}


//clear the form
function clearForm() {

	document.getElementById("cte").value="Create";
	document.getElementById("users").value="";
	document.getElementById("ftamt").value="";
	
	document.getElementById("scte").value="Create";
	document.getElementById("susers").value="";
	document.getElementById("sftamt").value="";
}














