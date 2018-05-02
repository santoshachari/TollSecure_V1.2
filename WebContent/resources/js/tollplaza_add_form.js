//prevents right click
//document.addEventListener('contextmenu', event => event.preventDefault());

//make the latest tollPlaza as default 
if (document.getElementById("identify").value=="true") {
	var x=document.getElementById("dplaza").value;
	document.getElementById("tollPlazas").value=x;
	document.getElementById("stollPlazas").value=x;
} else {
	var x=document.getElementById("dplaza").value;
	document.getElementById("tollPlazas").selectedIndex=x-1;
	document.getElementById("stollPlazas").selectedIndex=x-1;
}

function ssubmit1() {
	document.getElementById("tpn").value = document.getElementById("stpn").value;
	submit1();
}


function submit1() { 
	var error = false;
	var direction = document.getElementById("tpn").value;

	direction = direction.trim()
	if (direction==null || direction == "") {
		document.getElementById("sp_plazaName").removeAttribute("class");
		document.getElementById("sp_plazaName").setAttribute("class","error_show");
		
		document.getElementById("ssp_plazaName").removeAttribute("class");
		document.getElementById("ssp_plazaName").setAttribute("class","error_show");
		error = true;
	} else {
		document.getElementById("sp_plazaName").removeAttribute("class");
		document.getElementById("sp_plazaName").setAttribute("class","error");
		
		document.getElementById("ssp_plazaName").removeAttribute("class");
		document.getElementById("ssp_plazaName").setAttribute("class","error");
		error = false;
	}
	if (! error) {  
		document.getElementById("form").submit()
	}
}


//disable the add lanes button if the tollPlazas are empty
if (x == 0) {
	//then disable add lanes button
	 document.getElementById("adlane").disabled=true;
}


function supdate() {
	document.getElementById("tollPlazas").value = document.getElementById("stollPlazas").value;
	update();
}

//this is when user clicks on the 
function update() {
	
	//hide the create button and place an update button there
	document.getElementById("cte").value="Update";
	document.getElementById("scte").value="Update";
	
	//get the plazaId and text from the dropsown
	var plaza = document.getElementById("tollPlazas");
	var tollPlazaName = plaza.options[plaza.selectedIndex].text;
	var tollPlazaId = plaza.value;
	document.getElementById("tollPlazaId").value=tollPlazaId;
	
	//remove the single error if present
	document.getElementById("sp_plazaName").removeAttribute("class");
	document.getElementById("sp_plazaName").setAttribute("class","error");
	
	document.getElementById("ssp_plazaName").removeAttribute("class");
	document.getElementById("ssp_plazaName").setAttribute("class","error");

	//fill the second form with the values
	document.getElementById("tpn").value=tollPlazaName;
	document.getElementById("stpn").value=tollPlazaName;

}


function clearForm() {
	//clear the form and hidden values
	document.getElementById("tpn").value="";
	document.getElementById("tollPlazaId").value="";
	
	document.getElementById("stpn").value="";
	document.getElementById("stollPlazaId").value="";
	
	//change the button value to Create again
	document.getElementById("cte").value="Create";
	document.getElementById("scte").value="Create";
}
















