function fnGetSearchResult(){
	var location = Trim(document.getElementById('location').options[document.getElementById('location').selectedIndex].value);
	var zoneNo = Trim(document.getElementById('zoneNo').options[document.getElementById('zoneNo').selectedIndex].value);
	httpObject = null;
	httpObject=getHTTPObjectForBrowser(); 
	var url= "newConnectionSearchDAFAJax.action?hidAction=getSearchResult&location="+location+"&zoneNo="+zoneNo; 	
	//alert(url);
	if( (location == '' && zoneNo == '' )){
		//alert("Please choose Zoneno/Location or Both");
		document.getElementById('newConMessage').innerHTML="<font color='red'>Please choose Zone/Location or Both</font>";
	}
	else{
	if (httpObject != null) {
		hideScreen();									
		httpObject.onreadystatechange = function(){         
			if(httpObject.readyState == 4){
				var  responseString= httpObject.responseText;
				if(null!=responseString && responseString.indexOf("ERROR:")==-1){
					document.getElementById('searchResultDiv').innerHTML=responseString; 
					document.getElementById('newConMessage').innerHTML="";			
				}else{
					//alert('Sorry ! \nThere was a problem');		
					document.getElementById('newConMessage').innerHTML=responseString;				
				}
				showScreen();     
			}			
		};    
		httpObject.open("POST", url, true);            
		httpObject.send(null);			
	} 
	}
}

function fnGetDetails(seq){
	document.forms[0].action= "newConnectionDAF.action";
	document.forms[0].hidAction.value="populateNewConnectionDAFDetails";
	document.forms[0].dafSequence.value=seq;
	hideScreen();
	//alert(''+document.forms[0].dafSequence.value);
	document.getElementById("searchForm").submit();
}