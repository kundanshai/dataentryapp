window.history.forward(1);
function fnOnSubmit(){
	if(Trim(document.forms[0].username.value)==''){
		document.forms[0].username.focus(); 
		return false;
	}
	if(Trim(document.forms[0].password.value)==''){
		document.forms[0].password.focus(); 
		return false;
	}
	//Start:Added by Reshma on 28-11-2013 ,JTrac DJB-2049
	/*if(!document.getElementById('declaration').checked){
		alert("Please Agree with the Terms & Conditions.");
		return false;
	}*/
	//End:Added by Reshma on 28-11-2013 ,JTrac DJB-2049
	document.forms[0].username.value=Trim(document.forms[0].username.value);
	document.forms[0].password.value=Trim(document.forms[0].password.value);
	hideScreen();
	if(Trim(document.forms[0].userRole.value)=='Employee'){ 
		if(encriptionFlag &&encriptionFlag=='Y'){
			document.forms[0].password.value=encryptMe(Trim(document.forms[0].password.value));
		}
	}
	document.forms[0].action="login.action";	    	
	document.forms[0].submit(); 			
	return false;
}
function fnOnLoad(){
	window.focus();
	document.forms[0].username.focus();
}