/*
 * 
 *@history validation is changed due to adding two new catregories by Diksha Mukherjee on 08-Apr-2016 as per Jtrac DJB-4211 
 * 
 * 
 **/
var invalidPIN=false;
var pageSubmited=false;
function fnSetLocality(){
	var pinCode = Trim(document.getElementById('pinCode').value); 
	if(pinCode.length==6){		
 		httpObject = null;
		httpObject=getHTTPObjectForBrowser(); 
		var url= "newConnectionDAFAJax.action?hidAction=populateLocality&pinCode="+pinCode; 		
		if (httpObject != null) {
			hideScreen();									
			httpObject.onreadystatechange = function(){         
				if(httpObject.readyState == 4){
					var  responseString= httpObject.responseText;
					if(null!=responseString && responseString.indexOf("ERROR:")==-1){
						document.getElementById('tdLocality').innerHTML=responseString; 
						document.getElementById('newConMessage').innerHTML="";	
						invalidPIN=false;		
					}else{
						var html="<select name=\"locality\" id=\"locality\" class=\"selectbox-long\" disabled=\"true\" onchange=\"fnSetSubLocality();\">";
						html=html+"<option value=''>Locality</option>";
						html=html+"</select>";
						document.getElementById('tdLocality').innerHTML=html; 
						html="<select name=\"subLocality\" id=\"subLocality\" class=\"selectbox-long\" disabled=\"true\">";
						html=html+"<option value=''>Sub Locality</option>";
						html=html+"</select>";
						document.getElementById('tdSubLocality').innerHTML=html; 
						document.getElementById('newConMessage').innerHTML=responseString;
						//invalidPIN=true;						
					}
				}
				showScreen();     
			};    
			httpObject.open("POST", url, true);            
			httpObject.send(null);			
		} 
	}else{
		var html="<select name=\"locality\" id=\"locality\" class=\"selectbox-long\" disabled=\"true\" onchange=\"fnSetSubLocality();\">";
		html=html+"<option value=''>Locality</option>";
		html=html+"</select>";
		document.getElementById('tdLocality').innerHTML=html; 
		html="<select name=\"subLocality\" id=\"subLocality\" class=\"selectbox-long\" disabled=\"true\">";
		html=html+"<option value=''>Sub Locality</option>";
		html=html+"</select>";
		document.getElementById('tdSubLocality').innerHTML=html;
		invalidPIN=true;
	}
}

function fnSetSubLocality(){
	var locality = Trim(document.getElementById('locality').options[document.getElementById('locality').selectedIndex].value);	
	httpObject = null;
	httpObject=getHTTPObjectForBrowser(); 
	var url= "newConnectionDAFAJax.action?hidAction=populateSubLocality&locality="+locality; 		
	if (httpObject != null) {
		hideScreen();									
		httpObject.onreadystatechange = function(){         
			if(httpObject.readyState == 4){
				var  responseString= httpObject.responseText;
				if(null!=responseString && responseString.indexOf("ERROR:")==-1){
					document.getElementById('tdSubLocality').innerHTML=responseString; 
					document.getElementById('newConMessage').innerHTML="";			
				}else{
					//alert('Sorry ! \nThere was a problem');		
					document.getElementById('newConMessage').innerHTML=responseString;				
				}
			}
			showScreen();     
		};    
		httpObject.open("POST", url, true);            
		httpObject.send(null);			
	} 
}	
 	
function fnSetMrNo(){
	var zoneNo = Trim(document.getElementById('zoneNo').value); 
	httpObject = null;
	httpObject=getHTTPObjectForBrowser(); 
	var url= "newConnectionDAFAJax.action?hidAction=populateMrNo&zoneNo="+zoneNo; 		
	if (httpObject != null) {
		hideScreen();									
		httpObject.onreadystatechange = function(){         
			if(httpObject.readyState == 4){
				var  responseString= httpObject.responseText;
				if(null!=responseString && responseString.indexOf("ERROR:")==-1){
					document.getElementById('tdMrNo').innerHTML=responseString; 
					document.getElementById('newConMessage').innerHTML="";			
				}else{
					//alert('Sorry ! \nThere was a problem'+responseString);	
					document.getElementById('newConMessage').innerHTML=responseString;					
				}
			}
			showScreen();     
		};    
		httpObject.open("POST", url, true);            
		httpObject.send(null);			
	} 
}

function fnSetAreaNo(){
	var zoneNo = Trim(document.getElementById('zoneNo').value);	
	var mrNo = Trim(document.getElementById('mrNo').value);	
	httpObject = null;
	httpObject=getHTTPObjectForBrowser(); 
	var url= "newConnectionDAFAJax.action?hidAction=populateAreaNo&zoneNo="+zoneNo+"&mrNo="+mrNo; 		
	if (httpObject != null) {
		hideScreen();									
		//alert('url::'+url); 
		httpObject.onreadystatechange = function(){         
			if(httpObject.readyState == 4){
				var  responseString= httpObject.responseText;
				//alert('responseString::\n'+responseString);
				if(null!=responseString && responseString.indexOf("ERROR:")==-1){
					document.getElementById('tdAreaNo').innerHTML=responseString; 	
					document.getElementById('newConMessage').innerHTML="";	
				}else{
					//alert('Sorry ! \nThere was a problem');	
					document.getElementById('newConMessage').innerHTML=responseString;					
				}
			}
			showScreen();     
		};    
		httpObject.open("POST", url, true);            
		httpObject.send(null);			
	} 
}
//Added by Matiur Rahman on 18-12-2012
function fnPopulateMallCineplexOption(){	
	httpObject = null;
	httpObject=getHTTPObjectForBrowser(); 
	var url= "newConnectionDAFAJax.action?hidAction=populateMallCineplexOption"; 		
	if (httpObject != null) {
		hideScreen();									
		httpObject.onreadystatechange = function(){         
			if(httpObject.readyState == 4){
				var  responseString= httpObject.responseText;
				if(null!=responseString && responseString.indexOf("ERROR:")==-1){
					document.getElementById('mallCineplexSpan').style.display='block';
					document.getElementById('mallCineplexSpan').innerHTML=responseString; 
					document.getElementById('newConMessage').innerHTML="";
					document.getElementById('mallCineplex').value="1";
				}else{
					var html="<select name=\"mallCineplex\" id=\"mallCineplex\" class=\"selectbox\" disabled=\"true\">";
					html=html+"<option value='1'>Default</option>";
					html=html+"</select>";
					document.getElementById('mallCineplexSpan').style.display='block';
					document.getElementById('mallCineplexSpan').innerHTML=html; 
					document.getElementById('newConMessage').innerHTML=responseString;
				}
			}
			showScreen();     
		};    
		httpObject.open("POST", url, true);            
		httpObject.send(null);			
	} 
}
	
function fnCheckWaterConnectionType(){
 	var waterConnectionType=Trim(document.forms[0].waterConnectionType.value);
 	var waterConnectionUse=Trim(document.forms[0].waterConnectionUse.value);
 	if(waterConnectionType=='CAT I' && waterConnectionUse=='Group Housing Society'){
 		document.forms[0].noOfOccDwellUnits.disabled=false;
 		document.forms[0].noOfUnoccDwellUnits.disabled=false;
 		document.forms[0].noOfOccDwellUnits.focus();
 		
 	}else{
 		document.forms[0].noOfOccDwellUnits.disabled=true;
 		document.forms[0].noOfUnoccDwellUnits.disabled=true;
 	}
}
function fnCheckPropertyType(){
	var propertyType=Trim(document.forms[0].propertyType.value);
	//alert(propertyType);
	if(propertyType=='Hotel/Guest Houses' || propertyType=='Dharamshalas/Hostels'){
		document.getElementById('noOfRoomsSpan').style.display='block';
		document.getElementById('noOfBedsSpan').style.display='none';		
		document.getElementById('noOfFunctionSiteSpan').style.display='none';
		document.getElementById('mallCineplexSpan').style.display='none';
	}else if(propertyType=='Hospital/Nursing Home'){
		document.getElementById('noOfRoomsSpan').style.display='none';
		document.getElementById('noOfBedsSpan').style.display='block';
		document.getElementById('noOfFunctionSiteSpan').style.display='none';
		document.getElementById('mallCineplexSpan').style.display='none';
	}else if(propertyType=='Banquet Hall'){
		document.getElementById('noOfRoomsSpan').style.display='none';
		document.getElementById('noOfBedsSpan').style.display='none';
		document.getElementById('noOfFunctionSiteSpan').style.display='block';
		document.getElementById('mallCineplexSpan').style.display='none';
	}else if(propertyType=='Mall/Cineplex'){
		document.getElementById('noOfRoomsSpan').style.display='none';
		document.getElementById('noOfBedsSpan').style.display='none';
		document.getElementById('noOfFunctionSiteSpan').style.display='none';
		fnPopulateMallCineplexOption();			
	}else{
		document.getElementById('noOfRoomsSpan').style.display='none';
		document.getElementById('noOfBedsSpan').style.display='none';
		document.getElementById('noOfFunctionSiteSpan').style.display='none';
		document.getElementById('mallCineplexSpan').style.display='none';
	} 		
}
function fnSubmit(){
	//Start-Commented by Matiur Rahman on 13-06-2013 as per JTrac #DJB-1574
	//document.forms[0].hidAction.value="saveNewConnectionDAFDetails";
 	//document.forms[0].action="newConnectionDAF.action";
 	//document.forms[0].submit();
	//End-Commented by Matiur Rahman on 13-06-2013 as per JTrac #DJB-1574
	//Start-Added by Matiur Rahman on 13-06-2013 as per JTrac #DJB-1574
	fnSaveNewConnectionDAFDetails();
	//End-Added by Matiur Rahman on 13-06-2013 as per JTrac #DJB-1574
}
function validate(){
	//Start-Added by Diksha Mukherjee on 08-04-2016  as per JTrac #DJB-4211
	var waterConnectionTypeVal = document.getElementById("waterConnectionType").value;
	//Ended-Added by Diksha Mukherjee on 08-04-2016  as per JTrac #DJB-4211
	if(Trim(document.forms[0].dafId.value)==''){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>DAF ID is mandatory</b></font>";
 		document.forms[0].dafId.focus();
 		return false;
 	} 	
 	if(Trim(document.forms[0].entityName.value)==''){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Name is mandatory</b></font>";
 		document.forms[0].entityName.focus();
 		return false;
 	} 	
 	if(Trim(document.forms[0].fatherHusbandName.value)==''){
 		//document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Name of Father/Mother/Husband is mandatory</b></font>";
 		//document.forms[0].fatherHusbandName.focus();
 		//return false;
 		document.forms[0].fatherHusbandName.value="NA";
 	} 
 //	if(Trim(document.forms[0].personIdType.value)==''){
 		//document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Person ID Type is mandatory</b></font>";
 		//document.forms[0].personIdType.focus();
 		//return false;
 	//} 
 	if(Trim(document.forms[0].personIdNumber.value)==''){
 		document.forms[0].personIdNumber.value="NA";
 	}	 
 	if(Trim(document.forms[0].houseNumber.value)==''||Trim(document.forms[0].houseNumber.value)=='House No *'){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>House No is mandatory</b></font>";
 		document.forms[0].houseNumber.focus();
 		return false;
 	} 
 	if(Trim(document.forms[0].pinCode.value)==''||Trim(document.forms[0].pinCode.value)=='PIN Code *'){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>PIN Code is mandatory</b></font>";
 		document.forms[0].pinCode.focus();
 		return false;
 	}
 	
 	if(parseInt(Trim(document.forms[0].pinCode.value))<100000||invalidPIN){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Please Enter A valid PIN Code.</b></font>";
 		document.forms[0].pinCode.focus();
 		return false;
 	}
 	//Start:  Diksha Mukherjee on 22nd Apr ,2016 as per Jtrac DJB-4211
 	if(Trim(document.forms[0].locality.value)==''||Trim(document.forms[0].locality.value)=='Locality'){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Locality is mandatory</b></font>";
 		document.forms[0].locality.focus();
 		return false;
 	}/*Start : Atanu Commented the Validation For Sub locality as it is not mandatory according jTrac DJB-4211*/
 	/*
 	if(Trim(document.forms[0].subLocality.value)==''||Trim(document.forms[0].subLocality.value)=='Sub Locality'){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Sub Locality is mandatory</b></font>";
 		document.forms[0].subLocality.focus();
 		return false;
 	}*/
 	/*End : Atanu Commented the Validation For Sub locality as it is not mandatory according jTrac DJB-4211*/
 	//End:  Diksha Mukherjee on 22nd Apr ,2016 as per Jtrac DJB-4211
 	if(Trim(document.forms[0].propertyType.value)==''){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Property Type is mandatory</b></font>";
 		document.forms[0].propertyType.focus();
 		return false;
 	}
	
 	//Start- Changed by Diksha on 08-04-2016 as per Jtrac #DJB-4211
 	//if(Trim(document.forms[0].noOfFloors.value)==''||Trim(document.forms[0].noOfFloors.value)=='No of Floors *'){
 	//alert('inside');
	if('CATIIIA' != (Trim(waterConnectionTypeVal)) && 'CATIIIB' != (Trim(waterConnectionTypeVal))){
		//alert('Inside NOT 3A and NOT 3B');
	//Ended- Changed by Diksha on 08-04-2016 as per Jtrac #DJB-4211
		if(Trim(document.forms[0].noOfFloors.value)==''||Trim(document.forms[0].noOfFloors.value)=='No of Floors *'){
			document.forms[0].noOfFloors.value="1";
		}
 	//if(Trim(document.forms[0].noOfRooms.value)==''||Trim(document.forms[0].noOfRooms.value)=='No of Rooms *'){
 	//	document.forms[0].noOfRooms.value="1";
 	//}
		
 	 	var noOfFloors=Trim(document.forms[0].noOfFloors.value);
 	 	if(''!=noOfFloors ){
 	 	if (parseInt(noOfFloors)<1){
 	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No of Floor Should be Greater than 0.</b></font>";
 	 		document.forms[0].noOfFloors.focus();
 	 		return false;
 	 	}
 	 	if( parseInt(noOfFloors)>=parseInt(maxFloorValue)){
 	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No of Floor Should be Less than "+maxFloorValue+".</b></font>";
 	 		document.forms[0].noOfFloors.focus();
 	 		return false;
 	 	}
 	 //Started- Changed by Diksha on 08-04-2016 as per Jtrac #DJB-4211
 	}else{
 		document.forms[0].noOfFloors.value="NA";
 	}
    //End- Changed by Diksha on 08-04-2016 as per Jtrac #DJB-4211 	
 }
//Start : Atanu Added the Below Validation on 06-05-2016
	else{
	 if(Trim(document.forms[0].noOfBorewells.value)==''||Trim(document.forms[0].noOfBorewells.value)=='No of Borewells *'){
			document.forms[0].noOfBorewells.value="1";
		}
	 var noOfBorewells=Trim(document.forms[0].noOfBorewells.value);
	 	if(''!=noOfBorewells ){
	 	if (parseInt(noOfBorewells)<1){
	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No of Borewell Should be Greater than 0.</b></font>";
	 		document.forms[0].noOfBorewells.focus();
	 		return false;
	 	}
	 	if (parseInt(noOfBorewells)>maxNoOfBorewells){
	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No of Borewell Should not be Greater than 10.</b></font>";
	 		document.forms[0].noOfBorewells.focus();
	 		return false;
	 	}
	}else{
		document.forms[0].noOfBorewells.value="1";
	}
	 
 }
//End : Atanu Added the Below Validation on 06-05-2016
	
 	var propertyType=Trim(document.forms[0].propertyType.value);
	if(propertyType=='Hotel/Guest Houses' || propertyType=='Dharamshalas/Hostels'){
		if ((Trim(document.forms[0].noOfRooms.value)==''||Trim(document.forms[0].noOfRooms.value)=='No of Rooms *') || (parseInt(Trim(document.forms[0].noOfRooms.value))<1)){
 	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No of Rooms Should be Greater than 0.</b></font>";
 	 		document.forms[0].noOfRooms.focus();
 	 		return false;
 	 	}
		if ((Trim(document.forms[0].noOfRooms.value)!=''&& Trim(document.forms[0].noOfRooms.value)!='No of Rooms *') && (parseInt(Trim(document.forms[0].noOfRooms.value))>1000)){
 	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No of Rooms Should be Less than equal 1000.</b></font>";
 	 		document.forms[0].noOfRooms.focus();
 	 		return false;
 	 	}
	}
	if(propertyType=='Hospital/Nursing Home'){
		if ((Trim(document.forms[0].noOfBeds.value)==''||Trim(document.forms[0].noOfBeds.value)=='No of Beds *')|| (parseInt(Trim(document.forms[0].noOfBeds.value))<1)){
 	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No of Beds Should be Greater than 0.</b></font>";
 	 		document.forms[0].noOfBeds.focus();
 	 		return false;
 	 	}
		if ((Trim(document.forms[0].noOfBeds.value)!=''&& Trim(document.forms[0].noOfBeds.value)!='No of Beds *')&& (parseInt(Trim(document.forms[0].noOfBeds.value))>1000)){
 	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No of Beds Should be Less than equal 1000.</b></font>";
 	 		document.forms[0].noOfBeds.focus();
 	 		return false;
 	 	}
	}
	if(propertyType=='Banquet Hall'){
		if ((Trim(document.forms[0].functionSite.value)==''||Trim(document.forms[0].functionSite.value)=='Function Site *') || (parseInt(Trim(document.forms[0].functionSite.value))<1)){
			document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Function Site Should be Greater than 0.</b></font>";
 	 		document.forms[0].functionSite.focus();
 	 		return false;
 	 	}
		if ((Trim(document.forms[0].functionSite.value)!=''&& Trim(document.forms[0].functionSite.value)!='Function Site *') && (parseInt(Trim(document.forms[0].functionSite.value))>7)){
			document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Function Site Should be Less than equal 7.</b></font>";
 	 		document.forms[0].functionSite.focus();
 	 		return false;
 	 	}
	}
	//Changed by Bency As per the requirement provided by Amit -- Start
 	//if(Trim(document.forms[0].plotArea.value)==''||Trim(document.forms[0].plotArea.value)=='Plot Area *'){
 	//	document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Plot Area is mandatory</b></font>";
 	//	document.forms[0].plotArea.focus();
 	//	return false;
 	//}
	if(Trim(document.forms[0].plotArea.value)=='' ||Trim(document.forms[0].plotArea.value)=='Plot Area'){ 	 		
 		document.forms[0].plotArea.value= 0;
 	}
 	if(parseInt(Trim(document.forms[0].plotArea.value))<0){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Plot Area Should be Greater Than Equal to 0</b></font>";
 		document.forms[0].plotArea.focus();
 		return false;
 	}
	//Changed by Bency As per the requirement provided by Amit -- End
	//Changed by Bency As per the requirement provided by Amit -- Start
 	//if(Trim(document.forms[0].builtUpArea.value)==''||Trim(document.forms[0].builtUpArea.value)=='Built Up Area *'){
 	//	document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Built Up Area is mandatory</b></font>";
 	//	document.forms[0].builtUpArea.focus();
 	//	return false;
 	//} 
 	if(Trim(document.forms[0].builtUpArea.value)=='' ||Trim(document.forms[0].builtUpArea.value)=='Built Up Area'){ 	 		
 		document.forms[0].builtUpArea.value= 0;
 	}
 	if(parseInt(Trim(document.forms[0].builtUpArea.value))<0){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Built Up Area Should be Greater Than Equal To 0</b></font>";
 		document.forms[0].builtUpArea.focus();
 		return false;
 	} 
	//Changed by Bency As per the requirement provided by Amit -- End
 	if(parseInt(Trim(document.forms[0].builtUpArea.value))>parseInt(Trim(document.forms[0].plotArea.value))){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Built Up Area Should be Less than or Equal to Plot Area</b></font>";
 		document.forms[0].builtUpArea.focus();
 		return false;
 	} 
 	if(Trim(document.forms[0].waterConnectionType.value)==''){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Water Connection Type is mandatory</b></font>";
 		document.forms[0].waterConnectionType.focus();
 		return false;
 	}
 	if(Trim(document.forms[0].waterConnectionUse.value)==''){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Water Connection Use is mandatory</b></font>";
 		document.forms[0].waterConnectionUse.focus();
 		return false;
 	}
 	var waterConnectionType=Trim(document.forms[0].waterConnectionType.value);
 	var waterConnectionUse=Trim(document.forms[0].waterConnectionUse.value);
 	if(waterConnectionType=='CAT I' && waterConnectionUse=='Group Housing Society'){
 		if(Trim(document.forms[0].noOfOccDwellUnits.value)==''){
 			document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No of Dwelling Units Occupied is mandatory if Water Connection Type is Domestic and Water Connection Use is Group Housing Society </b></font>";
 	 		document.forms[0].noOfOccDwellUnits.focus();
 	 		return false;
 		}
 		if(Trim(document.forms[0].noOfUnoccDwellUnits.value)==''){
 			document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No of Dwelling Units Unoccupied is mandatory if Water Connection Type is Domestic and Water Connection Use is Group Housing Society </b></font>";
 	 		document.forms[0].noOfUnoccDwellUnits.focus();
 	 		return false;
 		}	 		
 	}
 	if(Trim(document.forms[0].noOfOccDwellUnits.value)==''){
 		document.forms[0].noOfOccDwellUnits.value="NA";
 	}
 	if(Trim(document.forms[0].noOfUnoccDwellUnits.value)==''){
 		document.forms[0].noOfUnoccDwellUnits.value="NA";
 	} 	 	
 	if(Trim(document.forms[0].noOfChildren.value)==''){ 	 		
 		document.forms[0].noOfChildren.value="0"; 	 		
 	}
	if(Trim(document.forms[0].noOfAdults.value)==''){ 	 		
 		document.forms[0].noOfAdults.value="1";
 	}
	if(Trim(document.forms[0].noOfChildren.value).length>noOfChildMaxLength){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No Of Children Should have Max. of "+noOfChildMaxLength+" digits</b></font>";
 		document.forms[0].noOfChildren.focus();
 		return false;
 	}
	if(parseInt(Trim(document.forms[0].noOfAdults.value))<1){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No Of Adults Should be Greater Than 0</b></font>";
 		document.forms[0].noOfAdults.focus();
 		return false;
 	}
	if(Trim(document.forms[0].noOfAdults.value).length>noOfAdultMaxLength){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>No Of Adults Should have Max. of "+noOfAdultMaxLength+" digits</b></font>";
 		document.forms[0].noOfAdults.focus();
 		return false;
 	}
	
	//Start:Changes by Diksha Mukherjee on 30th Mar 2016 jtrac DJB-4211
		//alert(Trim(waterConnectionTypeVal));
		 if('CATIIIA' != (Trim(waterConnectionTypeVal)) && 'CATIIIB' != (Trim(waterConnectionTypeVal))){
			// alert("1");
			//End:Changes by Diksha Mukherjee on 30th Mar 2016 DJB-4211
			 if(Trim(document.forms[0].sizeOfMeter.value)==''){
   			 document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Size of Meter is mandatory</b></font>";
   			 document.forms[0].sizeOfMeter.focus();
   			 return false;
   		 	}
			 //alert("3");
   	 	if(Trim(document.forms[0].typeOfMeter.value)==''){
   	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Type of Meter is mandatory</b></font>";
   	 		document.forms[0].typeOfMeter.focus();
   	 		return false;
   	 		}
   	// alert("2");
   	 	if(Trim(document.forms[0].initialRegisterReadDate.value)==''||Trim(document.forms[0].initialRegisterReadDate.value)=='DD/MM/YYYY'){
   	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Initial Register Read Date is mandatory</b></font>";
   	 		document.forms[0].initialRegisterReadDate.focus();
   	 		return false;
   	 	}else{
   	 		if(!validateDateString(Trim(document.forms[0].initialRegisterReadDate.value))){
 	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Please Enter a valid Date</b></font>";
 	 		document.forms[0].initialRegisterReadDate.focus();
 	 		return false;
 	 	}
 	}
 	//Validation: Future Date is not Allowed !.
   		
   	 	if(compareDates(todaysDate,Trim(document.forms[0].initialRegisterReadDate.value)) && todaysDate!=Trim(document.forms[0].initialRegisterReadDate.value)){
   	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Future Date is not Allowed</b></font>";
   	 		document.forms[0].initialRegisterReadDate.focus();		
   	 		return false;
   	 	}
   	 	if(Trim(document.forms[0].initialRegisterReadRemark.value)==''){
   	 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Initial Register Read Remark is mandatory</b></font>";
   	 		document.forms[0].initialRegisterReadRemark.focus();
   	 		return false;
   	 	}
   	 	if(Trim(document.forms[0].initialRegisetrRead.value)==''){
   	 			document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Initial Register Read is mandatory</b></font>";
   	 			document.forms[0].initialRegisetrRead.focus();
 			return false;
   	 		}
   	//Started- Changed by Diksha on 08-04-2016 as per Jtrac #DJB-4211
	}else{
		document.forms[0].sizeOfMeter.value='';
	    document.forms[0].typeOfMeter.value='';
	    document.forms[0].initialRegisterReadDate.value='';
	 }
	//Ended- Changed by Diksha on 08-04-2016 as per Jtrac #DJB-4211
 	/*if(waterConnectionType!='CAT I'){
 		if(Trim(document.forms[0].averageConsumption.value)==''){
 			document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Average Consumption is mandatory if Water Connection Type is Non Domestic </b></font>";
 	 		document.forms[0].averageConsumption.focus();
 	 		return false;
 		} 		
 	}*/
 	//if(Trim(document.forms[0].averageConsumption.value)==''){
 	//		document.forms[0].averageConsumption.value="NA";
 	//} 
 	if(Trim(document.forms[0].zoneNo.value)==''){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Zone is mandatory</b></font>";
 		document.forms[0].zoneNo.focus();
 		return false;
 	}
 	if(Trim(document.forms[0].mrNo.value)==''){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>MR No is mandatory</b></font>";
 		document.forms[0].mrNo.focus();
 		return false;
 	}
 	if(Trim(document.forms[0].areaNo.value)==''){
 		document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Area is mandatory</b></font>";
 		document.forms[0].areaNo.focus();
 		return false;
 	}
 	if(Trim(document.forms[0].openingBalance.value)==''){
 		document.forms[0].openingBalance.value="0";
 	}
 	//if(Trim(document.forms[0].applicationPurpose.value)==''){
 		//document.getElementById('newConMessage').innerHTML="<font color='red' size='2'><b>Application Purpose is mandatory</b></font>";
 		//document.forms[0].applicationPurpose.focus();
 		//return false;
 	//}
 	document.getElementById('newConMessage').innerHTML="&nbsp;";
 	return true;
}
function fnGotoNewConnectionDAFScreen(){
	hideScreen();
	document.location.href="newConnectionDAF.action?hidAction=";
}