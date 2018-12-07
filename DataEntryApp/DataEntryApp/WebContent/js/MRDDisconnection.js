	var isPopUp=false;
	var lastKNO="";
	var meterInstalDate="";
	var zeroReadDiscon ="";
	var currentMeterReadDateDiscon ="";
	var currentMeterRegisterReadDiscon ="";
	var newAverageConsumptionDiscon="";
	var oldMeterRegisterReadDiscon ="";
	var oldMeterAverageReadDiscon ="";
	var knoToResetDiscon ="";
	var billRoundToResetDiscon ="";
	function doDisconnectionAjaxCall(){
		alert('Not Implemeted');
		return;
		if(validateDisconDetails()){
			document.getElementById('disconMessage').innerHTML="";
			document.getElementById('disconMessage').title="Server Message.";
			document.getElementById('hidActionDiscon').value="saveDisconDetails";
			var url= "callDisconnectionAJax.action?hidAction="+document.getElementById('hidActionDiscon').value;			
			url+="&billRound="+document.getElementById('billRoundDiscon').value;
			url+="&kno="+document.getElementById('knoDiscon').value;	
			url+="&zone="+document.getElementById('zoneDiscon').value;
			url+="&mrNo="+document.getElementById("mrNoDiscon").value;
			url+="&area="+document.getElementById("areaDiscon").value;
			url+="&wcNo="+document.getElementById('wcNoDiscon').value;
			url+="&seqNo="+document.getElementById('slNoDiscon').value;
			url+="&name="+document.getElementById('nameDiscon').value;
			url+="&consumerType="+document.getElementById('consumerTypeDiscon').value;
			url+="&consumerCategory="+document.getElementById('consumerCategoryDiscon').value;
			url+="&currentMeterReadDate="+currentMeterReadDateDiscon;
			url+="&currentMeterReadRemarkCode="+document.getElementById('currentMeterReadRemarkCode').value;
			url+="&currentMeterRegisterRead="+currentMeterRegisterReadDiscon;
			url+="&newAverageConsumption="+newAverageConsumptionDiscon;
			//alert('url>>'+url);     
			document.getElementById('btnSaveDiscon').disabled=true;
			var httpBowserObject=null;
			if (window.ActiveXObject){                
				httpBowserObject= new ActiveXObject("Microsoft.XMLHTTP");   
			}else if (window.XMLHttpRequest){ 
				httpBowserObject= new XMLHttpRequest();  
			}else {                 
				alert("Browser does not support AJAX...........");             
				return;
			}
			if (httpBowserObject != null) {
				//window.focus();
				window.scrollTo(0,0);
				hideScreen();
				document.body.style.overflow = 'hidden';
				//hideDisconScreen();
				if(isPopUp){
					document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='green' size='2'>Data Submited</font></div>";
				}				            
				httpBowserObject.open("GET", url, true);
				httpBowserObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function(){
					if(httpBowserObject.readyState == 4){			
						var  responseString= httpBowserObject.responseText;
						alert(responseString);
						if(null!=responseString && responseString.indexOf("ERROR:")>-1){							
							document.getElementById('btnSaveDiscon').disabled=false;
							document.getElementById('disconMessage').innerHTML="<font color='red' size='2'>Unable to process your last Request. Please Try again.</font>";
							showScreen();
							document.body.style.overflow = 'auto';
							if(isPopUp){
								document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='red' size='2'>Unable to process your last Request. Please Try again.</font></div>";
								popupDisconScreen();
							}
						}else{
							showScreen();
							document.body.style.overflow = 'auto';
							if(isPopUp){
								//clearToUpdateVar();					 
								hideDisconScreen();
								document.getElementById('ajax-result').innerHTML = httpBowserObject.responseText;
								var fieldId="consumerStatus"+currentPage;
								document.getElementById(fieldId).value="<%=ConsumerStatusLookup.DISCONNECTED.getStatusCode() %>";
								fieldId="hidConStatus"+currentPage;
								document.getElementById(fieldId).value="<%=ConsumerStatusLookup.DISCONNECTED.getStatusCode() %>";
								currentMeterReadDateDiscon=currentMeterReadDateDiscon.toString();//-   DD/MM/YYYY
								//alert(currentMeterReadDateDiscon+'<>'+currentMeterReadDateDiscon.length);
								if(currentMeterReadDateDiscon.length==10){
									fieldId="meterReadDD"+currentPage;
									document.getElementById(fieldId).value=currentMeterReadDateDiscon.substr(0,2);
									fieldId="meterReadMM"+currentPage;
									document.getElementById(fieldId).value=currentMeterReadDateDiscon.substr(3,2);
									fieldId="meterReadYYYY"+currentPage;
									document.getElementById(fieldId).value=currentMeterReadDateDiscon.substr(6,4);
								}
								fieldId="meterReadStatus"+currentPage;
								document.getElementById(fieldId).value=Trim(document.getElementById('currentMeterReadRemarkCode').value);
								fieldId="hidMeterStatus"+currentPage;
								document.getElementById(fieldId).value=Trim(document.getElementById('currentMeterReadRemarkCode').value);							
								fieldId="currentMeterRead"+currentPage;
								document.getElementById(fieldId).value=currentMeterRegisterReadDiscon;
								fieldId="newAverageConsumption"+currentPage;
								document.getElementById(fieldId).value=newAverageConsumptionDiscon;
								setOnLoadValue();
							}
							document.getElementById('disconMessage').innerHTML="<font color='green' size='2'><b>Meter Replacement Details for KNO: "+ document.getElementById('knoDiscon').value +" Saved Successfully.</b></font>";
						}   
					}     
				};  
				httpBowserObject.send(null);
			} 
		}
	}
	function fnGetConsumerDetailsByKNO(){
		if(Trim(document.getElementById('knoDiscon').value).length==10){
			knoToResetDiscon=Trim(document.getElementById('knoDiscon').value);
			billRoundToResetDiscon=Trim(document.getElementById('billRoundDiscon').value);
			document.getElementById('disconMessage').innerHTML="";
			document.getElementById('disconMessage').title="Server Message.";
			document.getElementById('hidActionDiscon').value="getConsumerDetailsByKNO";
			var url= "callDisconnectionAJax.action?hidAction="+document.getElementById('hidActionDiscon').value;
			url+="&billRound="+document.getElementById('billRoundDiscon').value;
			url+="&kno="+document.getElementById('knoDiscon').value;	
			url+="&zone="+document.getElementById('zoneDiscon').value;
			url+="&mrNo="+document.getElementById("mrNoDiscon").value;
			url+="&area="+document.getElementById("areaDiscon").value;
			url+="&wcNo="+document.getElementById('wcNoDiscon').value;		
			
			var httpBowserObject=null;
			if (window.ActiveXObject){                
				httpBowserObject= new ActiveXObject("Microsoft.XMLHTTP");   
			}else if (window.XMLHttpRequest){ 
				httpBowserObject= new XMLHttpRequest();  
			}else {                 
				alert("Browser does not support AJAX...........");             
				return;
			} 
			if (httpBowserObject != null) {				
				window.focus();
				hideScreen();
				document.body.style.overflow = 'hidden';
				//hideDisconScreen();
				if(isPopUp){
					document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='green' size='2'>Data Submited</font></div>";
				}
				//alert('url>>'+url);                 
				httpBowserObject.open("GET", url, true);  
				httpBowserObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");			
				httpBowserObject.onreadystatechange = function(){
					if(httpBowserObject.readyState == 4){
						var  responseString= httpBowserObject.responseText;
						alert(httpBowserObject.responseText);
						if(null==responseString || responseString.indexOf("ERROR:")>-1){	
							document.getElementById('disconMessage').innerHTML="<font color='red' size='2'><b>No Details found for Selected Bill Round & KNO to Proceed. Contact Syatem Administrator.</b></font>";
							document.getElementById('disconMessage').title="This is may be due to DisconD being not Populated for the Bill Round. You may Contact System Administrator to Get it Checked.";
							showScreen();
							document.body.style.overflow = 'auto';
							document.getElementById('zoneDiscon').value="";
							document.getElementById('zoneDiscon').disabled=false;
							document.getElementById("mrNoDiscon").value="";
							document.getElementById('mrNoDiscon').disabled=false;
							document.getElementById("areaDiscon").value="";
							document.getElementById('areaDiscon').disabled=false;
							document.getElementById("wcNoDiscon").value="";
							document.getElementById('wcNoDiscon').disabled=false;
							document.getElementById("nameDiscon").value="";
							document.getElementById("consumerTypeDiscon").value="";
							document.getElementById("consumerCategoryDiscon").value="";
							document.getElementById("consumerStatusDiscon").value="";
							
						}else{
							showScreen();
							document.body.style.overflow = 'auto';
							var zoneString=responseString.substring(responseString.indexOf("<ZONE>")+6,responseString.indexOf("</ZONE>"));
							if(null!=zoneString && 'null'!=zoneString &&'undefined'!=zoneString &&''!=zoneString){
								document.getElementById('zoneDiscon').value=zoneString;
								document.getElementById('zoneDiscon').disabled=true;
							}
							//alert(responseString);
							var mrNoString=responseString.substring(responseString.indexOf("<MRNO>")+6,responseString.indexOf("</MRNO>"));
							//alert(mrNoString);
							if(null!=mrNoString && 'null'!=mrNoString &&'undefined'!=mrNoString &&''!=mrNoString){
								var select = document.getElementById("mrNoDiscon");
								select.options[select.options.length] = new Option(mrNoString,mrNoString); 
								document.getElementById("mrNoDiscon").value=mrNoString;
								document.getElementById('mrNoDiscon').disabled=true;
							}
							var areaString=responseString.substring(responseString.indexOf("<AREA>")+6,responseString.indexOf("</AREA>"));
							//alert(areaString);
							if(null!=areaString && 'null'!=areaString &&'undefined'!=areaString &&''!=areaString){
								var select = document.getElementById("areaDiscon");
								select.options[select.options.length] = new Option(areaString,areaString); 
								document.getElementById("areaDiscon").value=areaString;
								document.getElementById('areaDiscon').disabled=true;
							}
							var wcNoString=responseString.substring(responseString.indexOf("<WCNO>")+6,responseString.indexOf("</WCNO>"));
							//alert(wcNoString);
							if(null!=wcNoString && 'null'!=wcNoString &&'undefined'!=wcNoString &&''!=wcNoString){
								document.getElementById("wcNoDiscon").value=wcNoString;
								document.getElementById('wcNoDiscon').disabled=true;
							}
							var nameString=responseString.substring(responseString.indexOf("<NAME>")+6,responseString.indexOf("</NAME>"));
							//alert(nameString);
							if(null!=nameString && 'null'!=nameString &&'undefined'!=nameString &&''!=nameString){
								document.getElementById("nameDiscon").value=nameString;
								document.getElementById('nameDiscon').disabled=true;
							}
							var consumerTypeString=responseString.substring(responseString.indexOf("<CTYP>")+6,responseString.indexOf("</CTYP>"));
							//alert(consumerTypeString);
							if(null!=consumerTypeString && 'null'!=consumerTypeString &&'undefined'!=consumerTypeString &&''!=consumerTypeString){
								document.getElementById("consumerTypeDiscon").value=Trim(consumerTypeString);								
							}
							var consumerCategoryDiscon=responseString.substring(responseString.indexOf("<CCAT>")+6,responseString.indexOf("</CCAT>"));
							if(null!=consumerCategoryDiscon && 'null'!=consumerCategoryDiscon &&'undefined'!=consumerCategoryDiscon &&''!=consumerCategoryDiscon){
								document.getElementById("consumerCategoryDiscon").value=Trim(consumerCategoryDiscon);
							}
						}   
					}
					knoToResetDiscon=Trim(document.getElementById('knoDiscon').value);
					billRoundToResetDiscon=Trim(document.getElementById('billRoundDiscon').value);
				};  
				httpBowserObject.send(null); 
			}  
		}
	}  
	
	function fnGetConsumerDetailsByZMAW(){
		if(Trim(document.getElementById('billRoundDiscon').value).length!=0 && Trim(document.getElementById('zoneDiscon').value).length!=0 && Trim(document.getElementById('mrNoDiscon').value).length!=0 && Trim(document.getElementById('areaDiscon').value).length!=0 && Trim(document.getElementById('wcNoDiscon').value).length!=0){
			document.getElementById('disconMessage').innerHTML="";
			document.getElementById('disconMessage').title="Server Message";
			document.getElementById('hidActionDiscon').value="getConsumerDetailsByZMAW";
			var url= "callDisconnectionAJax.action?hidAction="+document.getElementById('hidActionDiscon').value;
			url+="&billRound="+document.getElementById('billRoundDiscon').value;
			url+="&kno="+document.getElementById('knoDiscon').value;	
			url+="&zone="+document.getElementById('zoneDiscon').value;
			url+="&mrNo="+document.getElementById("mrNoDiscon").value;
			url+="&area="+document.getElementById("areaDiscon").value;
			url+="&wcNo="+document.getElementById('wcNoDiscon').value;		
			
			var httpBowserObject=null;
			if (window.ActiveXObject){                
				httpBowserObject= new ActiveXObject("Microsoft.XMLHTTP");   
			}else if (window.XMLHttpRequest){ 
				httpBowserObject= new XMLHttpRequest();  
			}else {                 
				alert("Browser does not support AJAX...........");             
				return;
			}
			if (httpBowserObject != null) {
				window.focus();
				hideScreen();
				//hideDisconScreen();				
				document.body.style.overflow = 'hidden';
				if(isPopUp){
					document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='green' size='2'>Data Submited</font></div>";
				}
				//alert('url>>'+url);                 
				httpBowserObject.open("GET", url, true);
				httpBowserObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");				
				httpBowserObject.onreadystatechange = function(){
					if(httpBowserObject.readyState == 4){
						var  responseString= httpBowserObject.responseText;
						alert(httpBowserObject.responseText);
						if(null==responseString || responseString.indexOf("ERROR:")>-1){	
							document.getElementById('disconMessage').innerHTML="<font color='red' size='2'><b>No Details found for Selected Bill Round, Zone, Discon, Area & WC No to Proceed. Contact Syatem Administrator.</b></font>";
							document.getElementById('disconMessage').title="This is may be due to DisconD being not Populated for the Bill Round. You may Contact System Administrator to Get it Checked.";
							showScreen();
							document.body.style.overflow = 'auto';
							/*document.getElementById('zoneDiscon').value="";
							document.getElementById('zoneDiscon').disabled=false;
							document.getElementById("mrNoDiscon").value="";
							document.getElementById('mrNoDiscon').disabled=false;
							document.getElementById("areaDiscon").value="";
							document.getElementById('areaDiscon').disabled=false;
							document.getElementById("wcNoDiscon").value="";
							document.getElementById('wcNoDiscon').disabled=false;*/
							document.getElementById("knoDiscon").value="";
							document.getElementById("nameDiscon").value="";
							document.getElementById("consumerTypeDiscon").value="";							
							document.getElementById("consumerCategoryDiscon").value="";
							document.getElementById("consumerStatusDiscon").value="";
							
						}else{
							showScreen();
							document.body.style.overflow = 'auto';
							var knoString=responseString.substring(responseString.indexOf("<CKNO>")+6,responseString.indexOf("</CKNO>"));
							//alert(knoString);
							if(null!=knoString && 'null'!=knoString &&'undefined'!=knoString &&''!=knoString){
								document.getElementById('knoDiscon').value=knoString;
								//document.getElementById('knoDiscon').disabled=true;
							}				
							var nameString=responseString.substring(responseString.indexOf("<NAME>")+6,responseString.indexOf("</NAME>"));
							//alert(nameString);
							if(null!=nameString && 'null'!=nameString &&'undefined'!=nameString &&''!=nameString){
								document.getElementById("nameDiscon").value=nameString;
								document.getElementById('nameDiscon').disabled=true;
							}
							var consumerTypeString=responseString.substring(responseString.indexOf("<CTYP>")+6,responseString.indexOf("</CTYP>"));
							//alert(consumerTypeString);
							if(null!=consumerTypeString && 'null'!=consumerTypeString &&'undefined'!=consumerTypeString &&''!=consumerTypeString){
								document.getElementById("consumerTypeDiscon").value=Trim(consumerTypeString);								
							}
							var consumerCategoryDiscon=responseString.substring(responseString.indexOf("<CCAT>")+6,responseString.indexOf("</CCAT>"));
							document.getElementById("consumerCategoryDiscon").value=Trim(consumerCategoryDiscon);
						}   
					}
					knoToResetDiscon=Trim(document.getElementById('knoDiscon').value);
					billRoundToResetDiscon=Trim(document.getElementById('billRoundDiscon').value);
				}; 
				httpBowserObject.send(null);    
			}  
		}
	}  

	function popupDisconScreen(){
		isPopUp=true;
		if(null!=document.getElementById('mrbox') && 'null'!=document.getElementById('mrbox')){
			if(null!=document.getElementById('djbmaintable') && 'null'!=document.getElementById('djbmaintable')){
				var theTable=document.getElementById('djbmaintable');				
				theTable.className ="djbmainhidden";
			}
			var thediv=document.getElementById('mrbox');
			thediv.style.display = "block";
			/*================================================*/
			//Added  by Matiur Rahman on 12-12-2012 
			//document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			var fieldId="seqNo"+currentPage;
			var fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateSeqNO=fieldValue;
			
			fieldId="kno"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateKNO=fieldValue;
			
			fieldId="billRound"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateBillRound=fieldValue;
		
			var ddId="meterReadDD"+currentPage;
			var mmId="meterReadMM"+currentPage;
			var yyyyId="meterReadYYYY"+currentPage;
			document.getElementById(ddId).value=makeTwoDigit(Trim(document.getElementById(ddId).value));
			document.getElementById(mmId).value=makeTwoDigit(Trim(document.getElementById(mmId).value));
			var meterReadDate=document.getElementById(ddId).value+"/"+document.getElementById(mmId).value+"/"+document.getElementById(yyyyId).value;
			toUpdateMeterReadDate=meterReadDate;
			toUpdateMeterReadDateConfirm=meterReadDate;
			
			fieldId="meterReadStatus"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateMeterReadStatus=fieldValue;				
			
			if(currentMeterReadText!=''){
				toUpdateCurrentMeterRead=currentMeterReadText;
				toUpdateCurrentMeterReadConfirm=currentMeterReadText;	
			}else{
				fieldId="currentMeterRead"+currentPage;
				fieldValue=Trim(document.getElementById(fieldId).value);
				toUpdateCurrentMeterRead=fieldValue;
				toUpdateCurrentMeterReadConfirm=fieldValue;	
			}
			if(newAvgConsumptionText!=''){
				toUpdateNewAvgConsumption=newAvgConsumptionText;
				toUpdateNewAvgConsumptionConfirm=newAvgConsumptionText;
			}else{
				fieldId="newAverageConsumption"+currentPage;
				fieldValue=Trim(document.getElementById(fieldId).value);
				toUpdateNewAvgConsumption=fieldValue;
				toUpdateNewAvgConsumptionConfirm=fieldValue;
			}			
			if(newNoOfFloorText!=''){
				toUpdateNewNoOfFloor=newNoOfFloorText;
				toUpdateNewNoOfFloorConfirm=newNoOfFloorText;
			}else{
				fieldId="newNoOfFloor"+currentPage;
				fieldValue=Trim(document.getElementById(fieldId).value);
				toUpdateNewNoOfFloor=fieldValue;
				toUpdateNewNoOfFloorConfirm=fieldValue;
			}
			fieldId="consumerStatus"+currentPage;			
			fieldValue=Trim(document.getElementById(fieldId).value);				
			toUpdateConsumerStatus=fieldValue;		
			//alert('toUpdateSeqNO>>'+toUpdateSeqNO+'>>toUpdateKNO>>'+toUpdateKNO+'>>toUpdateBillRound>>'+toUpdateBillRound);
			/*================================================*/
			if(lastKNO.toString()!=toUpdateKNO.toString()){
				fnResetMeterReplacementDetails();
			}
			//document.body.style.overflow = 'hidden';
			document.getElementById('billRoundDiscon').value=toUpdateBillRound;
			document.getElementById('billRoundDiscon').disabled=true;
			document.getElementById('zoneDiscon').value=searchZone;
			document.getElementById('zoneDiscon').disabled=true;
			
			var select = document.getElementById("mrNoDiscon");
			select.options[select.options.length] = new Option(searchDisconNo,searchDisconNo); 
			document.getElementById("mrNoDiscon").value=searchDisconNo;
			document.getElementById('mrNoDiscon').disabled=true;
			select = document.getElementById("areaDiscon");
			select.options[select.options.length] = new Option(searchArea,searchAreaCode); 
			document.getElementById("areaDiscon").value=searchAreaCode;
			document.getElementById('areaDiscon').disabled=true;
			document.getElementById('slNo').value=toUpdateSeqNO;
			document.getElementById('knoDiscon').value=toUpdateKNO;
			lastKNO=document.getElementById('knoDiscon').value;
			document.getElementById('knoDiscon').disabled=true;
			var spanId="consumerNameSpan"+currentPage;
			document.getElementById('nameDiscon').value=Trim(document.getElementById(spanId).innerHTML);
			//document.getElementById('nameDiscon').disabled=true;
			spanId="consumerWcNoSpan"+currentPage;
			document.getElementById('wcNoDiscon').value=Trim(document.getElementById(spanId).innerHTML);
			document.getElementById('wcNoDiscon').disabled=true;
			spanId="consumerCategory"+currentPage;
			document.getElementById('consumerCategoryDiscon').value=Trim(document.getElementById(spanId).innerHTML);
			spanId="consumerType"+currentPage;
			document.getElementById('consumerTypeDiscon').value=Trim(document.getElementById(spanId).innerHTML);
			if(document.getElementById('consumerTypeDiscon').value=='METERED'){
				document.getElementById('saType').value="NA";
				document.getElementById('saType').disabled=true;
				document.getElementById('saTypeID').title="Only applicable for Unmetered Consumer";
			}
			document.getElementById('waterConnectionSizeDiscon').value="15";
			document.getElementById('zeroReadDiscon').value="0";
			document.getElementById('zeroReadConfirmDiscon').value="0";
			document.getElementById('consumerStatusDiscon').value=toUpdateConsumerStatus;
			spanId="currentAverageConsumption"+currentPage;
			document.getElementById('currentAverageConsumptionDiscon').value=Trim(document.getElementById(spanId).value);
			document.getElementById('currentAverageConsumptionDiscon').disabled=true;
			knoToResetDiscon=Trim(document.getElementById('knoDiscon').value);
			billRoundToResetDiscon=Trim(document.getElementById('billRoundDiscon').value);
			document.getElementById('btnCloseSpan').innerHTML="<input type='button'  value=' Close ' class='groovybutton' onclick='javascript:fnCancelMeterReplacementDetails();' title='Click to go back to previous screen.'/>";
			document.getElementById('meterReaderName').focus();
			document.getElementById('meterReaderName').focus();
		}
	}
	function displayDisconScreen(){
		isPopUp=false;
		if(null!=document.getElementById('mrbox') && 'null'!=document.getElementById('mrbox')){
			if(null!=document.getElementById('djbmaintable') && 'null'!=document.getElementById('djbmaintable')){
				var theTable=document.getElementById('djbmaintable');				
				theTable.className ="djbmainhidden";
			}
			var thediv=document.getElementById('mrbox');
			thediv.style.display = "block";			
			document.getElementById('meterReaderName').focus();
		}
	}
	function hideDisconScreen(){
		if(null!=document.getElementById('mrbox') && 'null'!=document.getElementById('mrbox')){
			
			var thediv=document.getElementById('mrbox');		
			thediv.style.display = "none";
			//document.body.style.overflow = 'auto';
			if(null!=document.getElementById('djbmaintable') && 'null'!=document.getElementById('djbmaintable')){
				var theTable=document.getElementById('djbmaintable');				
				theTable.className ="djbmain";
			}
		}
		
	}
	function fnCancelMeterReplacementDetails(){
		
		hideDisconScreen();
	}
	function fnSaveDisconnectionDetails(){
		doDisconnectionAjaxCall();
	}
	
	function setMeterInstalDate(){
		meterInstalDate=Trim(document.getElementById('meterInstalDate').value);		
		if(meterInstalDate.length>0){
			if(!validateDateString(meterInstalDate)){
				document.getElementById('meterInstalDate').focus();
				return;
			}
			//Validation: Future Date is not Allowed !.
			if(compareDates(todaysDateDiscon,meterInstalDate) && todaysDateDiscon!=meterInstalDate){
				alert('Future Date is not Allowed !.');
				document.getElementById('meterInstalDate').focus();
				return;
			}
			document.getElementById('meterInstalDate').value="**********";
		}
	}
	function resetMeterInstalDate(){
		document.getElementById('meterInstalDate').value=meterInstalDate;
	}
	function setZeroReadDiscon(){
		zeroReadDiscon=Trim(document.getElementById('zeroReadDiscon').value);
		if(zeroReadDiscon.length>0 && zeroReadDiscon.toString()!="0"){
			IsPositiveNumber(document.getElementById('zeroReadDiscon'));
			document.getElementById('zeroReadDiscon').value="**********";
		}
	}
	function resetZeroReadDiscon(){
		if(document.getElementById('zeroReadDiscon').value!="0"){
		document.getElementById('zeroReadDiscon').value=zeroReadDiscon;
		}
	}
	function setCurrentMeterReadDateDiscon(){
		currentMeterReadDateDiscon=Trim(document.getElementById('currentMeterReadDateDiscon').value);
		if(currentMeterReadDateDiscon.length>0){
			if(!validateDateString(currentMeterReadDateDiscon)){
				document.getElementById('currentMeterReadDateDiscon').focus();
				return;
			}
			//Validation: Future Date is not Allowed !.
			if(compareDates(todaysDateDiscon,currentMeterReadDateDiscon) && todaysDateDiscon!=currentMeterReadDateDiscon){
				alert('Future Date is not Allowed !.');
				document.getElementById('currentMeterReadDateDiscon').focus();
				return;
			}
			document.getElementById('currentMeterReadDateDiscon').value="**********";
		}
	}
	function resetCurrentMeterReadDateDiscon(){
		document.getElementById('currentMeterReadDateDiscon').value=currentMeterReadDateDiscon;
	}
	function setCurrentMeterRegisterReadDiscon(){
		currentMeterRegisterReadDiscon=Trim(document.getElementById('currentMeterRegisterReadDiscon').value);
		if(currentMeterRegisterReadDiscon.length>0){
			IsPositiveNumber(document.getElementById('currentMeterRegisterReadDiscon'));
			document.getElementById('currentMeterRegisterReadDiscon').value="**********";
		}
	}
	function resetCurrentMeterRegisterReadDiscon(){
		document.getElementById('currentMeterRegisterReadDiscon').value=currentMeterRegisterReadDiscon;
	}
	function setNewAverageConsumptionDiscon(){
		newAverageConsumptionDiscon=Trim(document.getElementById('newAverageConsumptionDiscon').value);
		if(newAverageConsumptionDiscon.length>0){
			IsPositiveNumber(document.getElementById('newAverageConsumptionDiscon'));
			document.getElementById('newAverageConsumptionDiscon').value="**********";
		}
	}
	function resetNewAverageConsumptionDiscon(){
		document.getElementById('newAverageConsumptionDiscon').value=newAverageConsumptionDiscon;
	}
	function setOldMeterRegisterReadDiscon(){
		oldMeterRegisterReadDiscon=Trim(document.getElementById('oldMeterRegisterReadDiscon').value);
		if(oldMeterRegisterReadDiscon.length>0){
			IsPositiveNumber(document.getElementById('oldMeterRegisterReadDiscon'));
			document.getElementById('oldMeterRegisterReadDiscon').value="**********";
		}
	}
	function resetOldMeterRegisterReadDiscon(){
		document.getElementById('oldMeterRegisterReadDiscon').value=oldMeterRegisterReadDiscon;
	}
	//Added on 11-12-2012 by Matiur Rahman
	function setOldMeterAverageReadDiscon(){
		oldMeterAverageReadDiscon=Trim(document.getElementById('oldMeterAverageReadDiscon').value);
		if(oldMeterAverageReadDiscon.length>0){
			IsPositiveNumber(document.getElementById('oldMeterAverageReadDiscon'));
			document.getElementById('oldMeterAverageReadDiscon').value="**********";
		}
	}
	function resetOldMeterAverageReadDiscon(){
		document.getElementById('oldMeterAverageReadDiscon').value=oldMeterAverageReadDiscon;
	}
	//============================================
	function fnSetwaterConnectionUseDisconTitle(obj){
		document.getElementById('waterConnectionUseDisconId').title=obj.value;
	}
	function fnResetDisconnectionDetails(){	
		knoToResetDiscon=Trim(document.getElementById('knoDiscon').value);
		billRoundToResetDiscon=Trim(document.getElementById('billRoundDiscon').value);
		if(!isPopUp){
			document.getElementById('billRoundDiscon').value="";
			document.getElementById('zoneDiscon').value="";
			document.getElementById('zoneDiscon').disabled=false;
			document.getElementById('mrNoDiscon').value="";
			document.getElementById('mrNoDiscon').disabled=false;
			document.getElementById('areaDiscon').value="";
			document.getElementById('areaDiscon').disabled=false;
			document.getElementById('wcNoDiscon').value="";
			document.getElementById('wcNoDiscon').disabled=false;
			document.getElementById('knoDiscon').value="";
			document.getElementById('knoDiscon').disabled=false;
			document.getElementById('nameDiscon').value="";
		}
		document.getElementById('currentMeterReadDateDiscon').value="";
		document.getElementById('currentMeterReadRemarkCode').value="";
		document.getElementById('currentMeterReadDateConfirmDiscon').value="";
		document.getElementById('currentMeterRegisterReadDiscon').value="";
		document.getElementById('currentMeterRegisterReadConfirmDiscon').value="";
		document.getElementById('newAverageConsumptionDiscon').value="";
		document.getElementById('newAverageConsumptionConfirmDiscon').value="";
		currentMeterReadDateDiscon ="";
		currentMeterRegisterReadDiscon ="";
		document.getElementById('disconMessage').innerHTML="";
		document.getElementById('disconMessage').title="Server Message.";
		document.getElementById('btnSaveDiscon').disabled=false;
		if(lastKNO.toString()==toUpdateKNO.toString()){
			//resetMeterReplacementAjaxCall();	
		}		
	}
	function validateDisconDetails(){		
		if(Trim(document.getElementById('billRoundDiscon').value).length==0){
			document.getElementById('billRoundDiscon').focus();
			document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('zoneDiscon').value).length==0){
			document.getElementById('zoneDiscon').focus();
			document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('mrNoDiscon').value).length==0){
			document.getElementById('mrNoDiscon').focus();
			document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('areaDiscon').value).length==0){
			document.getElementById('areaDiscon').focus();
			document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('wcNoDiscon').value).length==0){
			document.getElementById('wcNoDiscon').focus();
			document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('knoDiscon').value).length==0){
			document.getElementById('knoDiscon').focus();
			document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		/*if(Trim(document.getElementById('nameDiscon').value).length==0){
			document.getElementById('nameDiscon').focus();
			document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}*/		
		
		var confirmedValue="";		
		if(currentMeterReadDateDiscon.length==0){
			document.getElementById('currentMeterReadDateDiscon').focus();
			document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('currentMeterReadDateConfirmDiscon').value).length==0){
			document.getElementById('currentMeterReadDateConfirmDiscon').focus();
			document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		confirmedValue=Trim(document.getElementById('currentMeterReadDateConfirmDiscon').value);
		if(currentMeterReadDateDiscon.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('currentMeterReadDateConfirmDisconSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('currentMeterReadDateConfirmDisconSpan').title="Doesn't Match";
			document.getElementById('currentMeterReadDateConfirmDiscon').focus();
			return false;
		}else{
			document.getElementById('currentMeterReadDateConfirmDisconSpan').innerHTML="";
			document.getElementById('currentMeterReadDateConfirmDisconSpan').title="OK";
		}
		if(Trim(document.getElementById('currentMeterReadRemarkCode').value).length==0){
			document.getElementById('currentMeterReadRemarkCode').focus();
			document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		var readType=fnGetReadTypeDiscon(Trim(document.getElementById('currentMeterReadRemarkCode').value));		
		if(readType.toString()=='Volumetric Billing'){
			document.getElementById('currentMeterRegisterReadDiscon').disabled=false;				
			document.getElementById('currentMeterRegisterReadConfirmDiscon').disabled=false;
			if(Trim(document.getElementById('currentMeterRegisterReadDiscon').value).length==0){
				document.getElementById('currentMeterRegisterReadDiscon').focus();
				document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
				return false;
			}
			if(Trim(document.getElementById('currentMeterRegisterReadConfirmDiscon').value).length==0){
				document.getElementById('disconMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";				
				document.getElementById('currentMeterRegisterReadConfirmDiscon').focus();
				return false;
			}
			confirmedValue=Trim(document.getElementById('currentMeterRegisterReadConfirmDiscon').value);
			if(currentMeterRegisterReadDiscon.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
				document.getElementById('currentMeterRegisterReadConfirmDisconSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
				document.getElementById('currentMeterRegisterReadConfirmDisconSpan').title="Doesn't Match";
				document.getElementById('currentMeterRegisterReadConfirmDiscon').focus();
				return false;
			}else{
				document.getElementById('currentMeterRegisterReadConfirmDisconSpan').innerHTML="";
				document.getElementById('currentMeterRegisterReadConfirmDisconSpan').title="OK";
			}
		}else{
			document.getElementById('currentMeterRegisterReadDiscon').disabled=true;
			document.getElementById('currentMeterRegisterReadConfirmDiscon').disabled=true;
		}
		confirmedValue=Trim(document.getElementById('newAverageConsumptionConfirmDiscon').value);
		if((newAverageConsumptionDiscon.toString()!=confirmedValue.toString() && confirmedValue.length > 0)||(newAverageConsumptionDiscon.length > 0 && confirmedValue.length == 0)){			
			document.getElementById('newAverageConsumptionConfirmDisconSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('newAverageConsumptionConfirmDisconSpan').title="Doesn't Match";
			document.getElementById('newAverageConsumptionConfirmDiscon').focus();
			return false;
		}else{
			document.getElementById('newAverageConsumptionConfirmDisconSpan').innerHTML="";
			document.getElementById('newAverageConsumptionConfirmDisconSpan').title="OK";
		}		
		return true;
	}
	function fnCheckCurrentMeterReadDateDisconConfirm(){
		var confirmedValue=Trim(document.getElementById('currentMeterReadDateConfirmDiscon').value);
		if(currentMeterReadDateDiscon.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('currentMeterReadDateConfirmDisconSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('currentMeterReadDateConfirmDisconSpan').title="Doesn't Match";
			document.getElementById('currentMeterReadDateConfirmDiscon').focus();
		}else{
			document.getElementById('currentMeterReadDateConfirmDisconSpan').innerHTML="";
			document.getElementById('currentMeterReadDateConfirmDisconSpan').title="OK";
		}
	}
	function fnCheckCurrentMeterRegisterReadDisconConfirm(){
		var confirmedValue=Trim(document.getElementById('currentMeterRegisterReadConfirmDiscon').value);
		if(currentMeterRegisterReadDiscon.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('currentMeterRegisterReadConfirmDisconSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('currentMeterRegisterReadConfirmDisconSpan').title="Doesn't Match";
			document.getElementById('currentMeterRegisterReadConfirmDiscon').focus();
		}else{
			document.getElementById('currentMeterRegisterReadConfirmDisconSpan').innerHTML="";
			document.getElementById('currentMeterRegisterReadConfirmDisconSpan').title="OK";
		}
	}
	function fnCheckNewAverageConsumptionDisconConfirm(){
		var confirmedValue=Trim(document.getElementById('newAverageConsumptionConfirmDiscon').value);
		if(newAverageConsumptionDiscon.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('newAverageConsumptionConfirmDisconSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('newAverageConsumptionConfirmDisconSpan').title="Doesn't Match";
			document.getElementById('newAverageConsumptionConfirmDiscon').focus();
		}else{
			document.getElementById('newAverageConsumptionConfirmDisconSpan').innerHTML="";
			document.getElementById('newAverageConsumptionConfirmDisconSpan').title="OK";
		}
	}
	function fnCheckOldMeterRegisterReadDisconConfirm(){
		var confirmedValue=Trim(document.getElementById('oldMeterRegisterReadConfirmDiscon').value);
		if(oldMeterRegisterReadDiscon.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('oldMeterRegisterReadConfirmDisconSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('oldMeterRegisterReadConfirmDisconSpan').title="Doesn't Match";
			document.getElementById('oldMeterRegisterReadConfirmDiscon').focus();
		}else{
			document.getElementById('oldMeterRegisterReadConfirmDisconSpan').innerHTML="";
			document.getElementById('oldMeterRegisterReadConfirmDisconSpan').title="OK";
		}
	}
	//Added on 11-12-2012 by Matiur Rahman
	function fnCheckOldMeterAverageReadDisconConfirm(){
		var confirmedValue=Trim(document.getElementById('oldMeterAverageReadConfirmDiscon').value);
		if(oldMeterAverageReadDiscon.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('oldMeterAverageReadConfirmDisconSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('oldMeterAverageReadConfirmDisconSpan').title="Doesn't Match";
			document.getElementById('oldMeterAverageReadConfirmDiscon').focus();
		}else{
			document.getElementById('oldMeterAverageReadConfirmDisconSpan').innerHTML="";
			document.getElementById('oldMeterAverageReadConfirmDisconSpan').title="OK";
		}
	}
	//============================================================
	function fnMakeTwoDigit(obj){
		obj.value=Trim(obj.value);
		IsNumber(obj);
		obj.value=makeTwoDigit(obj.value);
	}
	function setMeterReadStatusDiscon(obj){
		fnCheckMeterReadStatusDiscon(obj);
		fnSetReadTypeDiscon(obj);			
		
	}
	function fnCheckMeterReadStatusDiscon(obj){
		var meterReadStatus=Trim(obj.value);
		//3. Current Meter Reading field should only be enabled for (OK, PUMP).
		if(meterReadStatus.toString()=='OK' || meterReadStatus.toString()=='PUMP'){ 	 											
			var fieldId="currentMeterRegisterReadConfirmDiscon";
			document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=false; 
			fieldId="currentMeterRegisterReadDiscon";
			//document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=false; 
			//document.getElementById(fieldId).focus(); 
		}else{
			var fieldId="currentMeterRegisterReadConfirmDiscon";
			document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=true; 								
			fieldId="currentMeterRegisterReadDiscon";
			document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=true; 
		}
		//4. In case of (NUW, PLUG, DEM) Current Meter Reading should always be equal to Previous Meter Reading.
		/*if(meterReadStatus.toString()=='NUW' || meterReadStatus.toString()=='PLUG'|| meterReadStatus.toString()=='DEM'){
			var fieldId="hidPrevMeterRead";
			var prevMeterRead=Trim(document.getElementById(fieldId).value);		
			if(prevMeterRead!='NA'){
				fieldId="currentMeterRegisterReadDiscon";
				document.getElementById(fieldId).value=prevMeterRead;
				document.getElementById(fieldId).disabled=true; 
				fieldId="currentMeterRegisterReadConfirmDiscon";
				document.getElementById(fieldId).value=prevMeterRead;
				document.getElementById(fieldId).disabled=true; 
				toUpdateCurrentMeterReadConfirm=prevMeterRead;
				toUpdateCurrentMeterRead=prevMeterRead;
			}
		}*/
	}	
	function fnSetReadTypeDiscon(obj)
	{
		var meterReadStatus=Trim(obj.value);
		var readType=fnGetReadTypeDiscon(meterReadStatus);
		var meterReadTypeSpanId="meterReadTypeSpanDiscon";
		var billTypeSpanId="billTypeSpanDiscon";	
		var currentMeterReadId="currentMeterRegisterReadDiscon";			
		var currentMeterReadConfirmId="currentMeterRegisterReadConfirmDiscon";
		document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
		document.getElementById(billTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";		
		if(readType=='Volumetric Billing'){
			//if(document.getElementById('consumerTypeDiscon').value=='METERED'&& (meterReadStatus=='OK'||meterReadStatus=='PUMP')){
			if(meterReadStatus=='OK'||meterReadStatus=='PUMP'){//Removed Consumer Type Consideration by Matiur As per Discussion with Yash on 12-12-2012
				document.getElementById(currentMeterReadId).disabled=false;
				document.getElementById(currentMeterReadConfirmId).disabled=false;
			}else{
				document.getElementById(currentMeterReadId).disabled=true;				
				document.getElementById(currentMeterReadConfirmId).disabled=true;
			}				
			document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='green'><b>Regular</b></font>";
			document.getElementById(billTypeSpanId).innerHTML = "<font color='green'><b>Volumetric Billing</b></font>";
		}else{
			//alert(readType+'<>'+document.getElementById('consumerTypeDiscon').value+'<>'+meterReadStatus);
			//if(document.getElementById('consumerTypeDiscon').value=='METERED' && meterReadStatus!=''){	
			if(meterReadStatus!=''){	//Removed Consumer Type Consideration by Matiur As per Discussion with Yash on 12-12-2012
				document.getElementById(currentMeterReadId).value="0";
				currentMeterRegisterReadDiscon="0";
				document.getElementById(currentMeterReadConfirmId).value="0";
				document.getElementById(currentMeterReadId).disabled=true;				
				document.getElementById(currentMeterReadConfirmId).disabled=true;
				document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='red'><b>No Read</b></font>";
				document.getElementById(billTypeSpanId).innerHTML = "<font color='red'><b>"+readType+"</b></font>";
			}
			//if(document.getElementById('consumerTypeDiscon').value!='METERED' ||meterReadStatus==''){
			if(meterReadStatus==''){//Removed Consumer Type Consideration by Matiur As per Discussion with Yash on 12-12-2012
				document.getElementById(currentMeterReadId).disabled=true;				
				document.getElementById(currentMeterReadConfirmId).disabled=true;
				document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
				document.getElementById(billTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
			}
		}
	}
	function fnGetReadTypeDiscon(meterReaderRemarkCode){
		var readType="No Billing";
		if(Trim(meterReaderRemarkCode).length!=0){
			if(regularReadTypeDiscon.indexOf(meterReaderRemarkCode)>-1){
				readType="Volumetric Billing";
				return readType;
			}
			if(averageReadTypeDiscon.indexOf(meterReaderRemarkCode)>-1){
				readType="Average Billing";
				return readType;
			}
			if(provisionalReadTypeDiscon.indexOf(meterReaderRemarkCode)>-1){
				readType="Provisional Billing";
				return readType;
			}
			if(noBillingReadTypeDiscon.indexOf(meterReaderRemarkCode)>-1){
				readType="No Billing";
				return readType;
			}
		}
		return readType;
	}
	function fnCheckOldMeterReadRemarkCode(obj){
		var readType=fnGetReadTypeDiscon(Trim(obj.value));
		if(readType.toString()!='Volumetric Billing'){
			/*if(document.getElementById('consumerTypeDiscon').value=='METERED'&& (meterReadStatus=='OK'||meterReadStatus=='PUMP')){
				document.getElementById('oldMeterRegisterReadDiscon').disabled=false;
				document.getElementById('oldMeterRegisterReadConfirmDiscon').disabled=false;
			}else{*/
				document.getElementById('oldMeterRegisterReadDiscon').disabled=true;				
				document.getElementById('oldMeterRegisterReadConfirmDiscon').disabled=true;
			//}
		}else{
			document.getElementById('oldMeterRegisterReadDiscon').disabled=false;
			document.getElementById('oldMeterRegisterReadConfirmDiscon').disabled=false;
		}
	
	}
	function fnCheckBillRound(){
		if(Trim(document.getElementById('billRoundDiscon').value).length==0){
			document.getElementById('disconMessage').innerHTML="<font color='red' size='2'><b>Please Select Bill Round First</b></font>";
			if(!isPopUp){
				document.getElementById('zoneDiscon').value="";
				//document.getElementById('zoneDiscon').disabled=true;
			}
			document.getElementById('billRoundDiscon').focus();
		}else{
			document.getElementById('disconMessage').innerHTML="<font color='red' size='2'><b></b></font>";
		}
	}
	function fnSetDefaultValue(){
		if(Trim(document.getElementById('zeroReadDiscon').value)=='0'){
			zeroReadDiscon="0";
		}
	}
	function fnResetDeatils(){
		document.getElementById('zoneDiscon').value="";
		document.getElementById('zoneDiscon').disabled=false;
		document.getElementById('mrNoDiscon').value="";
		document.getElementById('mrNoDiscon').disabled=false;
		document.getElementById('areaDiscon').value="";
		document.getElementById('areaDiscon').disabled=false;
		document.getElementById('wcNoDiscon').value="";
		document.getElementById('wcNoDiscon').disabled=false;
		document.getElementById('knoDiscon').value="";
		document.getElementById('knoDiscon').disabled=false;
		document.getElementById('nameDiscon').value="";
	}
	function fnEnableSaveButton(){
		document.getElementById('btnSaveDiscon').disabled=false;
	}
	//Start- Added by Matiur Rahman on 30-11-2012 as per JTrac Issue # DJB-720
	function fnCheckModel(obj){		
		if(Trim(document.getElementById('manufacturerDiscon').value)=='N/A' && Trim(document.getElementById('modelDiscon').value)!='N/A W'){
			document.getElementById('modelDiscon').value="N/A W";
			document.getElementById('modelDiscon').disabled=true;
		}else{
			document.getElementById('modelDiscon').value="";
			document.getElementById('modelDiscon').disabled=false;
		}
	}
	//End- Added by Matiur Rahman on 30-11-2012 as per JTrac Issue # DJB-720
	
	//Start-Added By Matiur Rahman on 12-12-2012 as per discussion with Yash
	//Reset Meter Replacement Details in data base	
	function resetMeterReplacementAjaxCall(){		
		if(knoToResetDiscon!='' && billRoundToResetDiscon!=''){		
			if(confirm("Click OK to Reset from the Data Base as wel, else Cancel")){
				document.getElementById('disconMessage').innerHTML="";
				document.getElementById('disconMessage').title="Server Message.";
				document.getElementById('hidActionDiscon').value="resetDisconDetails";
				var url= "callMeterReplacementAJax.action?hidAction=resetDisconDetails&seqNo="+document.getElementById('slNo').value;
				url+="&meterReaderName="+document.getElementById('meterReaderName').value+"&billRound="+billRoundToResetDiscon;
				url+="&zone="+document.getElementById('zoneDiscon').value+"&mrNo="+document.getElementById("mrNoDiscon").value+"&area="+document.getElementById("areaDiscon").value+"&wcNo="+document.getElementById('wcNoDiscon').value+"&kno="+knoToResetDiscon+"&name="+document.getElementById('nameDiscon').value;
				url+="&waterConnectionSize="+document.getElementById('waterConnectionSizeDiscon').value+"&waterConnectionUse="+document.getElementById("waterConnectionUseDiscon").value;
				url+="&meterType="+document.getElementById('meterType').value+"&badgeNo="+document.getElementById("badgeNoDiscon").value+"&manufacturer="+document.getElementById("manufacturerDiscon").value+"&model="+document.getElementById('modelDiscon').value;
				url+="&saType="+document.getElementById('saType').value;
				url+="&consumerType="+document.getElementById('consumerTypeDiscon').value;
				url+="&consumerCategory="+document.getElementById('consumerCategoryDiscon').value;
				url+="&isLNTServiceProvider="+document.getElementById('isLNTServiceProvider').value;
				url+="&meterInstalDate="+meterInstalDate+"&zeroRead="+zeroReadDiscon;
				url+="&currentMeterReadDate="+currentMeterReadDateDiscon+"&currentMeterRegisterRead="+currentMeterRegisterReadDiscon+"&newAverageConsumption="+newAverageConsumptionDiscon;
				url+="&currentMeterReadRemarkCode="+document.getElementById('currentMeterReadRemarkCode').value+"&oldMeterRegisterRead="+oldMeterRegisterReadDiscon;
				url+="&oldMeterReadRemarkCode="+document.getElementById('oldMeterReadRemarkCode').value;
				url+="&oldMeterAverageRead="+oldMeterAverageReadDiscon;
				//alert('url>>'+url);     
				document.getElementById('btnSaveDiscon').disabled=true;
				var httpBowserObject=null;
				if (window.ActiveXObject){                
					httpBowserObject= new ActiveXObject("Microsoft.XMLHTTP");   
				}else if (window.XMLHttpRequest){ 
					httpBowserObject= new XMLHttpRequest();  
				}else {                 
					alert("Browser does not support AJAX...........");             
					return;
				}  
				//httpBowserObject = getHTTPObjectForBrowser();  
				if (httpBowserObject != null) {
					//window.focus();
					window.scrollTo(0,0);
					hideScreen();
					document.body.style.overflow = 'hidden';
					//hideDisconScreen();
					if(isPopUp){
						document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='green' size='2'>Data Submited</font></div>";
					}				            
					httpBowserObject.open("GET", url, true);
					httpBowserObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
					httpBowserObject.setRequestHeader("Connection", "close");
					httpBowserObject.onreadystatechange = function(){
						if(httpBowserObject.readyState == 4){	
							//alert(httpBowserObject.responseText);
							var  responseString= httpBowserObject.responseText;
							if(null!=responseString && responseString.indexOf("ERROR:")>-1){								
								document.getElementById('btnSaveDiscon').disabled=false;
								document.getElementById('disconMessage').innerHTML="<font color='red' size='2'>Unable to process your last Request. Please Try again.</font>";
								showScreen();
								document.body.style.overflow = 'auto';
								if(isPopUp){
									document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='red' size='2'>Unable to process your last Request. Please Try again.</font></div>";
									popupDisconScreen();
								}
							}else{
								showScreen();
								document.body.style.overflow = 'auto';
								if(isPopUp){
									clearToUpdateVar();					 
									hideDisconScreen();
									document.getElementById('ajax-result').innerHTML = httpBowserObject.responseText; 
									var fieldId="consumerStatus"+currentPage;
									document.getElementById(fieldId).value="60";
									fieldId="hidConStatus"+currentPage;
									document.getElementById(fieldId).value="60";
									fieldId="meterReadDD"+currentPage;
									document.getElementById(fieldId).value="";
									fieldId="meterReadMM"+currentPage;
									document.getElementById(fieldId).value="";
									fieldId="meterReadYYYY"+currentPage;
									document.getElementById(fieldId).value="";
									fieldId="meterReadStatus"+currentPage;
									document.getElementById(fieldId).value="";
									fieldId="hidMeterStatus"+currentPage;
									document.getElementById(fieldId).value="";							
									fieldId="currentMeterRead"+currentPage;
									document.getElementById(fieldId).value="";
									fieldId="newAverageConsumption"+currentPage;
									document.getElementById(fieldId).value="";
									setOnLoadValue();
								}
								document.getElementById('disconMessage').innerHTML="<font color='green' size='2'><b>Meter Replacement Details for KNO: "+ knoToResetDiscon +" Reset Successfully.</b></font>";
							}   
						}     
					};  
					httpBowserObject.send(null);
				}
			}
		}
	}
	//End-Added By Matiur Rahman on 12-12-2012