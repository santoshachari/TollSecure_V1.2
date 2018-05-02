//get the id 
var lastId = document.getElementById("htotalcount").value-1;
var loadingId=null;
var currentId = null;

//get the index of the transaction at initial loading

var list = document.getElementsByClassName("prev_next");

for (i=0; i<list.length; i++) {
	if(list[i].value.split("#")[2]==document.getElementById("ticketNo").value) {
		loadingId = i;
		break;
	}
}

//let the current id be loading id
currentId = loadingId;

//while loading if it is the first table item then do not show previous similarly for the last element

var first_ticket_no = document.getElementById("0").value.split("#")[2];
if (loadingId==0) {
	document.getElementById("prev").style.visibility = "hidden";
	document.getElementById("sprev").style.visibility = "hidden";
}

if (loadingId==lastId) {
	document.getElementById("next").style.visibility = "hidden";
	document.getElementById("snext").style.visibility = "hidden";
}


//get next one
function getNextOne() {
	currentId++;
	if(currentId==lastId) {
		document.getElementById("next").style.visibility = "hidden";
		document.getElementById("snext").style.visibility = "hidden";
	} else {
		document.getElementById("next").style.visibility = "visible";
		document.getElementById("snext").style.visibility = "visible";
	}
	if(currentId==0) {
		document.getElementById("prev").style.visibility = "hidden";
		document.getElementById("sprev").style.visibility = "hidden";
	} else {
		document.getElementById("prev").style.visibility = "visible";
		document.getElementById("sprev").style.visibility = "visible";
	}
	var transaction = document.getElementById(currentId).value.split("#");
	document.getElementById("ticketNo").value = transaction[2];
	document.getElementById("time").value = transaction[6];
	document.getElementById("vno").value = transaction[1];
	document.getElementById("usr").value = transaction[9]
	document.getElementById("shift").value = transaction[7];
	document.getElementById("amt").value = transaction[5];
	document.getElementById("vclass").value = transaction[3];
	
	if (transaction[10]=='0' && transaction[11]=='0' && transaction[12]=='0') {
		if (transaction[4]=='S') document.getElementById("jType").value = 'SINGLE';
		else document.getElementById("jType").value = 'RETURN';
	} else if(transaction[10]!='0') {
		document.getElementById("jType").value = transaction[10]+' PASS';
	} else if(transaction[11]!='0') {
		document.getElementById("jType").value = 'EXEMPTED';
	} else if(transaction[12]!='0') {
		document.getElementById("jType").value = 'CONCESSION';
	}
	
	/*alert(transaction)
	alert(transaction[10]);
	alert(transaction[11]);
	alert(transaction[12]);*/
	setImageSource(transaction[8]);
	
	document.getElementById("sticketNo").value = transaction[2];
	document.getElementById("stime").value = transaction[6];
	document.getElementById("svno").value = transaction[1];
	document.getElementById("susr").value = transaction[9]
	document.getElementById("sshift").value = transaction[7];
	document.getElementById("samt").value = transaction[5];
	document.getElementById("svclass").value = transaction[3];
	
	if (transaction[10]=='0' && transaction[11]=='0' && transaction[12]=='0') {
		if (transaction[4]=='S') document.getElementById("sjType").value = 'SINGLE';
		else document.getElementById("sjType").value = 'RETURN';
	} else if(transaction[10]!='0') {
		document.getElementById("sjType").value = transaction[10]+' PASS';
	} else if(transaction[11]!='0') {
		document.getElementById("sjType").value = 'EXEMPTED';
	} else if(transaction[12]!='0') {
		document.getElementById("sjType").value = 'CONCESSION';
	}
	
}

//get previous one
function getPreviousOne() {
	currentId--;
	if(currentId==0) {
		document.getElementById("prev").style.visibility = "hidden";
		document.getElementById("sprev").style.visibility = "hidden";
	} else {
		document.getElementById("prev").style.visibility = "visible";
		document.getElementById("sprev").style.visibility = "visible";
	}
	if(currentId==lastId) {
		document.getElementById("next").style.visibility = "hidden";
		document.getElementById("snext").style.visibility = "hidden";
	} else {
		document.getElementById("next").style.visibility = "visible";
		document.getElementById("snext").style.visibility = "visible";
	}
	var transaction = document.getElementById(currentId).value.split("#");
	document.getElementById("ticketNo").value = transaction[2];
	document.getElementById("time").value = transaction[6];
	document.getElementById("vno").value = transaction[1];
	document.getElementById("usr").value = transaction[9]
	document.getElementById("shift").value = transaction[7];
	document.getElementById("amt").value = transaction[5];
	document.getElementById("vclass").value = transaction[3];
	if (transaction[10]=='0' && transaction[11]=='0' && transaction[12]=='0') {
		if (transaction[4]=='S') document.getElementById("jType").value = 'SINGLE';
		else document.getElementById("jType").value = 'RETURN';
	} else if(transaction[10]!='0') {
		document.getElementById("jType").value = transaction[10]+' PASS';
	} else if(transaction[11]!='0') {
		document.getElementById("jType").value = 'EXEMPTED';
	} else if(transaction[12]!='0') {
		document.getElementById("jType").value = 'CONCESSION';
	}
	/*alert(transaction)
	alert(transaction[10]);
	alert(transaction[11]);
	alert(transaction[12]);*/

	
	setImageSource(transaction[8]);
	
	document.getElementById("sticketNo").value = transaction[2];
	document.getElementById("stime").value = transaction[6];	
	document.getElementById("svno").value = transaction[1];
	document.getElementById("susr").value = transaction[9]
	document.getElementById("sshift").value = transaction[7];
	document.getElementById("samt").value = transaction[5];
	document.getElementById("svclass").value = transaction[3];
	if (transaction[10]=='0' && transaction[11]=='0' && transaction[12]=='0') {
		if (transaction[4]=='S') document.getElementById("sjType").value = 'SINGLE';
		else document.getElementById("sjType").value = 'RETURN';
	} else if(transaction[10]!='0') {
		document.getElementById("sjType").value = transaction[10]+' PASS';
	} else if(transaction[11]!='0') {
		document.getElementById("sjType").value = 'EXEMPTED';
	} else if(transaction[12]!='0') {
		document.getElementById("sjType").value = 'CONCESSION';
	}
	
}









