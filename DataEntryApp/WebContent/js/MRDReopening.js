	var isPopUp=false;
	var lastKNO="";
	var meterInstalDate="";
	var zeroReadReopening ="";
	var currentMeterReadDateReopening ="";
	var currentMeterRegisterReadReopening ="";
	var newAverageConsumptionReopening="";
	var oldMeterRegisterReadReopening ="";
	var oldMeterAverageReadReopening ="";
	var knoToResetReopening ="";
	var billRoundToResetReopening ="";
	function doReopeningAjaxCall(){
		alert('Not Implemeted');
		return;
		if(validateReopeningDetails()){
			document.getElementById('reopeningMessage').innerHTML="";
			document.getElementById('reopeningMessage').title="Server Message.";
			document.getElementById('hidActionReopening').value="saveReopeningDetails";
			var url= "callReopeningAJax.action?hidAction="+document.getElementById('hidActionReopening').value;			
			url+="&billRound="+document.getElementById('billRoundReopening').value;
			url+="&kno="+document.getElementById('knoReopening').value;	
			url+="&zone="+document.getElementById('zoneReopening').value;
			url+="&mrNo="+document.getElementById("mrNoReopening").value;
			url+="&area="+document.getElementById("areaReopening").value;
			url+="&wcNo="+document.getElementById('wcNoReopening').value;
			url+="&seqNo="+document.getElementById('slNoReopening').value;
			url+="&name="+document.getElementById('nameReopening').value;
			url+="&consumerType="+document.getElementById('consumerTypeReopening').value;
			url+="&consumerCategory="+document.getElementById('consumerCategoryReopening').value;
			url+="&currentMeterReadDate="+currentMeterReadDateReopening;
			url+="&currentMeterReadRemarkCode="+document.getElementById('currentMeterReadRemarkCode').value;
			url+="&currentMeterRegisterRead="+currentMeterRegisterReadReopening;
			url+="&newAverageConsumption="+newAverageConsumptionReopening;
			//alert('url>>'+url);     
			document.getElementById('btnSaveReopening').disabled=true;
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
				//hideReopeningScreen();
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
							document.getElementById('btnSaveReopening').disabled=false;
							document.getElementById('reopeningMessage').innerHTML="<font color='red' size='2'>Unable to process your last Request. Please Try again.</font>";
							showScreen();
							document.body.style.overflow = 'auto';
							if(isPopUp){
								document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='red' size='2'>Unable to process your last Request. Please Try again.</font></div>";
								popupReopeningScreen();
							}
						}else{
							showScreen();
							document.body.style.overflow = 'auto';
							if(isPopUp){
								//clearToUpdateVar();					 
								hideReopeningScreen();
								document.getElementById('ajax-result').innerHTML = httpBowserObject.responseText;
								var fieldId="consumerStatus"+currentPage;
								document.getElementById(fieldId).value="<%=ConsumerStatusLookup.DISCONNECTED.getStatusCode() %>";
								fieldId="hidConStatus"+currentPage;
								document.getElementById(fieldId).value="<%=ConsumerStatusLookup.DISCONNECTED.getStatusCode() %>";
								currentMeterReadDateReopening=currentMeterReadDateReopening.toString();//-   DD/MM/YYYY
								//alert(currentMeterReadDateReopening+'<>'+currentMeterReadDateReopening.length);
								if(currentMeterReadDateReopening.length==10){
									fieldId="meterReadDD"+currentPage;
									document.getElementById(fieldId).value=currentMeterReadDateReopening.substr(0,2);
									fieldId="meterReadMM"+currentPage;
									document.getElementById(fieldId).value=currentMeterReadDateReopening.substr(3,2);
									fieldId="meterReadYYYY"+currentPage;
									document.getElementById(fieldId).value=currentMeterReadDateReopening.substr(6,4);
								}
								fieldId="meterReadStatus"+currentPage;
								document.getElementById(fieldId).value=Trim(document.getElementById('currentMeterReadRemarkCode').value);
								fieldId="hidMeterStatus"+currentPage;
								document.getElementById(fieldId).value=Trim(document.getElementById('currentMeterReadRemarkCode').value);							
								fieldId="currentMeterRead"+currentPage;
								document.getElementById(fieldId).value=currentMeterRegisterReadReopening;
								fieldId="newAverageConsumption"+currentPage;
								document.getElementById(fieldId).value=newAverageConsumptionReopening;
								setOnLoadValue();
							}
							document.getElementById('reopeningMessage').innerHTML="<font color='green' size='2'><b>Meter Replacement Details for KNO: "+ document.getElementById('knoReopening').value +" Saved Successfully.</b></font>";
						}   
					}     
				};  
				httpBowserObject.send(null);
			} 
		}
	}
	function fnGetConsumerDetailsByKNO(){
		if(Trim(document.getElementById('knoReopening').value).length==10){
			knoToResetReopening=Trim(document.getElementById('knoReopening').value);
			billRoundToResetReopening=Trim(document.getElementById('billRoundReopening').value);
			document.getElementById('reopeningMessage').innerHTML="";
			document.getElementById('reopeningMessage').title="Server Message.";
			document.getElementById('hidActionReopening').value="getConsumerDetailsByKNO";
			var url= "callReopeningAJax.action?hidAction="+document.getElementById('hidActionReopening').value;
			url+="&billRound="+document.getElementById('billRoundReopening').value;
			url+="&kno="+document.getElementById('knoReopening').value;	
			url+="&zone="+document.getElementById('zoneReopening').value;
			url+="&mrNo="+document.getElementById("mrNoReopening").value;
			url+="&area="+document.getElementById("areaReopening").value;
			url+="&wcNo="+document.getElementById('wcNoReopening').value;		
			
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
				//hideReopeningScreen();
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
							document.getElementById('reopeningMessage').innerHTML="<font color='red' size='2'><b>No Details found for Selected Bill Round & KNO to Proceed. Contact Syatem Administrator.</b></font>";
							document.getElementById('reopeningMessage').title="This is may be due to ReopeningD being not Populated for the Bill Round. You may Contact System Administrator to Get it Checked.";
							showScreen();
							document.body.style.overflow = 'auto';
							document.getElementById('zoneReopening').value="";
							document.getElementById('zoneReopening').disabled=false;
							document.getElementById("mrNoReopening").value="";
							document.getElementById('mrNoReopening').disabled=false;
							document.getElementById("areaReopening").value="";
							document.getElementById('areaReopening').disabled=false;
							document.getElementById("wcNoReopening").value="";
							document.getElementById('wcNoReopening').disabled=false;
							document.getElementById("nameReopening").value="";
							document.getElementById("consumerTypeReopening").value="";
							document.getElementById("consumerCategoryReopening").value="";
							document.getElementById("consumerStatusReopening").value="";
							
						}else{
							showScreen();
							document.body.style.overflow = 'auto';
							var zoneString=responseString.substring(responseString.indexOf("<ZONE>")+6,responseString.indexOf("</ZONE>"));
							if(null!=zoneString && 'null'!=zoneString &&'undefined'!=zoneString &&''!=zoneString){
								document.getElementById('zoneReopening').value=zoneString;
								document.getElementById('zoneReopening').disabled=true;
							}
							//alert(responseString);
							var mrNoString=responseString.substring(responseString.indexOf("<MRNO>")+6,responseString.indexOf("</MRNO>"));
							//alert(mrNoString);
							if(null!=mrNoString && 'null'!=mrNoString &&'undefined'!=mrNoString &&''!=mrNoString){
								var select = document.getElementById("mrNoReopening");
								select.options[select.options.length] = new Option(mrNoString,mrNoString); 
								document.getElementById("mrNoReopening").value=mrNoString;
								document.getElementById('mrNoReopening').disabled=true;
							}
							var areaString=responseString.substring(responseString.indexOf("<AREA>")+6,responseString.indexOf("</AREA>"));
							//alert(areaString);
							if(null!=areaString && 'null'!=areaString &&'undefined'!=areaString &&''!=areaString){
								var select = document.getElementById("areaReopening");
								select.options[select.options.length] = new Option(areaString,areaString); 
								document.getElementById("areaReopening").value=areaString;
								document.getElementById('areaReopening').disabled=true;
							}
							var wcNoString=responseString.substring(responseString.indexOf("<WCNO>")+6,responseString.indexOf("</WCNO>"));
							//alert(wcNoString);
							if(null!=wcNoString && 'null'!=wcNoString &&'undefined'!=wcNoString &&''!=wcNoString){
								document.getElementById("wcNoReopening").value=wcNoString;
								document.getElementById('wcNoReopening').disabled=true;
							}
							var nameString=responseString.substring(responseString.indexOf("<NAME>")+6,responseString.indexOf("</NAME>"));
							//alert(nameString);
							if(null!=nameString && 'null'!=nameString &&'undefined'!=nameString &&''!=nameString){
								document.getElementById("nameReopening").value=nameString;
								document.getElementById('nameReopening').disabled=true;
							}
							var consumerTypeString=responseString.substring(responseString.indexOf("<CTYP>")+6,responseString.indexOf("</CTYP>"));
							//alert(consumerTypeString);
							if(null!=consumerTypeString && 'null'!=consumerTypeString &&'undefined'!=consumerTypeString &&''!=consumerTypeString){
								document.getElementById("consumerTypeReopening").value=Trim(consumerTypeString);								
							}
							var consumerCategoryReopening=responseString.substring(responseString.indexOf("<CCAT>")+6,responseString.indexOf("</CCAT>"));
							if(null!=consumerCategoryReopening && 'null'!=consumerCategoryReopening &&'undefined'!=consumerCategoryReopening &&''!=consumerCategoryReopening){
								document.getElementById("consumerCategoryReopening").value=Trim(consumerCategoryReopening);
							}
						}   
					}
					knoToResetReopening=Trim(document.getElementById('knoReopening').value);
					billRoundToResetReopening=Trim(document.getElementById('billRoundReopening').value);
				};  
				httpBowserObject.send(null); 
			}  
		}
	}  
	
	function fnGetConsumerDetailsByZMAW(){
		if(Trim(document.getElementById('billRoundReopening').value).length!=0 && Trim(document.getElementById('zoneReopening').value).length!=0 && Trim(document.getElementById('mrNoReopening').value).length!=0 && Trim(document.getElementById('areaReopening').value).length!=0 && Trim(document.getElementById('wcNoReopening').value).length!=0){
			document.getElementById('reopeningMessage').innerHTML="";
			document.getElementById('reopeningMessage').title="Server Message";
			document.getElementById('hidActionReopening').value="getConsumerDetailsByZMAW";
			var url= "callReopeningAJax.action?hidAction="+document.getElementById('hidActionReopening').value;
			url+="&billRound="+document.getElementById('billRoundReopening').value;
			url+="&kno="+document.getElementById('knoReopening').value;	
			url+="&zone="+document.getElementById('zoneReopening').value;
			url+="&mrNo="+document.getElementById("mrNoReopening").value;
			url+="&area="+document.getElementById("areaReopening").value;
			url+="&wcNo="+document.getElementById('wcNoReopening').value;		
			
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
				//hideReopeningScreen();				
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
							document.getElementById('reopeningMessage').innerHTML="<font color='red' size='2'><b>No Details found for Selected Bill Round, Zone, Reopening, Area & WC No to Proceed. Contact Syatem Administrator.</b></font>";
							document.getElementById('reopeningMessage').title="This is may be due to ReopeningD being not Populated for the Bill Round. You may Contact System Administrator to Get it Checked.";
							showScreen();
							document.body.style.overflow = 'auto';
							/*document.getElementById('zoneReopening').value="";
							document.getElementById('zoneReopening').disabled=false;
							document.getElementById("mrNoReopening").value="";
							document.getElementById('mrNoReopening').disabled=false;
							document.getElementById("areaReopening").value="";
							document.getElementById('areaReopening').disabled=false;
							document.getElementById("wcNoReopening").value="";
							document.getElementById('wcNoReopening').disabled=false;*/
							document.getElementById("knoReopening").value="";
							document.getElementById("nameReopening").value="";
							document.getElementById("consumerTypeReopening").value="";							
							document.getElementById("consumerCategoryReopening").value="";
							document.getElementById("consumerStatusReopening").value="";
							
						}else{
							showScreen();
							document.body.style.overflow = 'auto';
							var knoString=responseString.substring(responseString.indexOf("<CKNO>")+6,responseString.indexOf("</CKNO>"));
							//alert(knoString);
							if(null!=knoString && 'null'!=knoString &&'undefined'!=knoString &&''!=knoString){
								document.getElementById('knoReopening').value=knoString;
								//document.getElementById('knoReopening').disabled=true;
							}				
							var nameString=responseString.substring(responseString.indexOf("<NAME>")+6,responseString.indexOf("</NAME>"));
							//alert(nameString);
							if(null!=nameString && 'null'!=nameString &&'undefined'!=nameString &&''!=nameString){
								document.getElementById("nameReopening").value=nameString;
								document.getElementById('nameReopening').disabled=true;
							}
							var consumerTypeString=responseString.substring(responseString.indexOf("<CTYP>")+6,responseString.indexOf("</CTYP>"));
							//alert(consumerTypeString);
							if(null!=consumerTypeString && 'null'!=consumerTypeString &&'undefined'!=consumerTypeString &&''!=consumerTypeString){
								document.getElementById("consumerTypeReopening").value=Trim(consumerTypeString);								
							}
							var consumerCategoryReopening=responseString.substring(responseString.indexOf("<CCAT>")+6,responseString.indexOf("</CCAT>"));
							document.getElementById("consumerCategoryReopening").value=Trim(consumerCategoryReopening);
						}   
					}
					knoToResetReopening=Trim(document.getElementById('knoReopening').value);
					billRoundToResetReopening=Trim(document.getElementById('billRoundReopening').value);
				}; 
				httpBowserObject.send(null);    
			}  
		}
	}  

	function popupReopeningScreen(){
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
			//document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
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
			document.getElementById('billRoundReopening').value=toUpdateBillRound;
			document.getElementById('billRoundReopening').disabled=true;
			document.getElementById('zoneReopening').value=searchZone;
			document.getElementById('zoneReopening').disabled=true;
			
			var select = document.getElementById("mrNoReopening");
			select.options[select.options.length] = new Option(searchReopeningNo,searchReopeningNo); 
			document.getElementById("mrNoReopening").value=searchReopeningNo;
			document.getElementById('mrNoReopening').disabled=true;
			select = document.getElementById("areaReopening");
			select.options[select.options.length] = new Option(searchArea,searchAreaCode); 
			document.getElementById("areaReopening").value=searchAreaCode;
			document.getElementById('areaReopening').disabled=true;
			document.getElementById('slNo').value=toUpdateSeqNO;
			document.getElementById('knoReopening').value=toUpdateKNO;
			lastKNO=document.getElementById('knoReopening').value;
			document.getElementById('knoReopening').disabled=true;
			var spanId="consumerNameSpan"+currentPage;
			document.getElementById('nameReopening').value=Trim(document.getElementById(spanId).innerHTML);
			//document.getElementById('nameReopening').disabled=true;
			spanId="consumerWcNoSpan"+currentPage;
			document.getElementById('wcNoReopening').value=Trim(document.getElementById(spanId).innerHTML);
			document.getElementById('wcNoReopening').disabled=true;
			spanId="consumerCategory"+currentPage;
			document.getElementById('consumerCategoryReopening').value=Trim(document.getElementById(spanId).innerHTML);
			spanId="consumerType"+currentPage;
			document.getElementById('consumerTypeReopening').value=Trim(document.getElementById(spanId).innerHTML);
			if(document.getElementById('consumerTypeReopening').value=='METERED'){
				document.getElementById('saType').value="NA";
				document.getElementById('saType').disabled=true;
				document.getElementById('saTypeID').title="Only applicable for Unmetered Consumer";
			}
			document.getElementById('waterConnectionSizeReopening').value="15";
			document.getElementById('zeroReadReopening').value="0";
			document.getElementById('zeroReadConfirmReopening').value="0";
			document.getElementById('consumerStatusReopening').value=toUpdateConsumerStatus;
			spanId="currentAverageConsumption"+currentPage;
			document.getElementById('currentAverageConsumptionReopening').value=Trim(document.getElementById(spanId).value);
			document.getElementById('currentAverageConsumptionReopening').disabled=true;
			knoToResetReopening=Trim(document.getElementById('knoReopening').value);
			billRoundToResetReopening=Trim(document.getElementById('billRoundReopening').value);
			document.getElementById('btnCloseSpan').innerHTML="<input type='button'  value=' Close ' class='groovybutton' onclick='javascript:fnCancelMeterReplacementDetails();' title='Click to go back to previous screen.'/>";
			document.getElementById('meterReaderName').focus();
			document.getElementById('meterReaderName').focus();
		}
	}
	function displayReopeningScreen(){
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
	function hideReopeningScreen(){
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
		
		hideReopeningScreen();
	}
	function fnSaveReopeningDetails(){
		doReopeningAjaxCall();
	}
	
	function setMeterInstalDate(){
		meterInstalDate=Trim(document.getElementById('meterInstalDate').value);		
		if(meterInstalDate.length>0){
			if(!validateDateString(meterInstalDate)){
				document.getElementById('meterInstalDate').focus();
				return;
			}
			//Validation: Future Date is not Allowed !.
			if(compareDates(todaysDateReopening,meterInstalDate) && todaysDateReopening!=meterInstalDate){
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
	function setZeroReadReopening(){
		zeroReadReopening=Trim(document.getElementById('zeroReadReopening').value);
		if(zeroReadReopening.length>0 && zeroReadReopening.toString()!="0"){
			IsPositiveNumber(document.getElementById('zeroReadReopening'));
			document.getElementById('zeroReadReopening').value="**********";
		}
	}
	function resetZeroReadReopening(){
		if(document.getElementById('zeroReadReopening').value!="0"){
		document.getElementById('zeroReadReopening').value=zeroReadReopening;
		}
	}
	function setCurrentMeterReadDateReopening(){
		currentMeterReadDateReopening=Trim(document.getElementById('currentMeterReadDateReopening').value);
		if(currentMeterReadDateReopening.length>0){
			if(!validateDateString(currentMeterReadDateReopening)){
				document.getElementById('currentMeterReadDateReopening').focus();
				return;
			}
			//Validation: Future Date is not Allowed !.
			if(compareDates(todaysDateReopening,currentMeterReadDateReopening) && todaysDateReopening!=currentMeterReadDateReopening){
				alert('Future Date is not Allowed !.');
				document.getElementById('currentMeterReadDateReopening').focus();
				return;
			}
			document.getElementById('currentMeterReadDateReopening').value="**********";
		}
	}
	function resetCurrentMeterReadDateReopening(){
		document.getElementById('currentMeterReadDateReopening').value=currentMeterReadDateReopening;
	}
	function setCurrentMeterRegisterReadReopening(){
		currentMeterRegisterReadReopening=Trim(document.getElementById('currentMeterRegisterReadReopening').value);
		if(currentMeterRegisterReadReopening.length>0){
			IsPositiveNumber(document.getElementById('currentMeterRegisterReadReopening'));
			document.getElementById('currentMeterRegisterReadReopening').value="**********";
		}
	}
	function resetCurrentMeterRegisterReadReopening(){
		document.getElementById('currentMeterRegisterReadReopening').value=currentMeterRegisterReadReopening;
	}
	function setNewAverageConsumptionReopening(){
		newAverageConsumptionReopening=Trim(document.getElementById('newAverageConsumptionReopening').value);
		if(newAverageConsumptionReopening.length>0){
			IsPositiveNumber(document.getElementById('newAverageConsumptionReopening'));
			document.getElementById('newAverageConsumptionReopening').value="**********";
		}
	}
	function resetNewAverageConsumptionReopening(){
		document.getElementById('newAverageConsumptionReopening').value=newAverageConsumptionReopening;
	}
	function setOldMeterRegisterReadReopening(){
		oldMeterRegisterReadReopening=Trim(document.getElementById('oldMeterRegisterReadReopening').value);
		if(oldMeterRegisterReadReopening.length>0){
			IsPositiveNumber(document.getElementById('oldMeterRegisterReadReopening'));
			document.getElementById('oldMeterRegisterReadReopening').value="**********";
		}
	}
	function resetOldMeterRegisterReadReopening(){
		document.getElementById('oldMeterRegisterReadReopening').value=oldMeterRegisterReadReopening;
	}
	//Added on 11-12-2012 by Matiur Rahman
	function setOldMeterAverageReadReopening(){
		oldMeterAverageReadReopening=Trim(document.getElementById('oldMeterAverageReadReopening').value);
		if(oldMeterAverageReadReopening.length>0){
			IsPositiveNumber(document.getElementById('oldMeterAverageReadReopening'));
			document.getElementById('oldMeterAverageReadReopening').value="**********";
		}
	}
	function resetOldMeterAverageReadReopening(){
		document.getElementById('oldMeterAverageReadReopening').value=oldMeterAverageReadReopening;
	}
	//============================================
	function fnSetwaterConnectionUseReopeningTitle(obj){
		document.getElementById('waterConnectionUseReopeningId').title=obj.value;
	}
	function fnResetReopeningDetails(){	
		knoToResetReopening=Trim(document.getElementById('knoReopening').value);
		billRoundToResetReopening=Trim(document.getElementById('billRoundReopening').value);
		if(!isPopUp){
			document.getElementById('billRoundReopening').value="";
			document.getElementById('zoneReopening').value="";
			document.getElementById('zoneReopening').disabled=false;
			document.getElementById('mrNoReopening').value="";
			document.getElementById('mrNoReopening').disabled=false;
			document.getElementById('areaReopening').value="";
			document.getElementById('areaReopening').disabled=false;
			document.getElementById('wcNoReopening').value="";
			document.getElementById('wcNoReopening').disabled=false;
			document.getElementById('knoReopening').value="";
			document.getElementById('knoReopening').disabled=false;
			document.getElementById('nameReopening').value="";
		}
		document.getElementById('currentMeterReadDateReopening').value="";
		document.getElementById('currentMeterReadRemarkCode').value="";
		document.getElementById('currentMeterReadDateConfirmReopening').value="";
		document.getElementById('currentMeterRegisterReadReopening').value="";
		document.getElementById('currentMeterRegisterReadConfirmReopening').value="";
		document.getElementById('newAverageConsumptionReopening').value="";
		document.getElementById('newAverageConsumptionConfirmReopening').value="";
		currentMeterReadDateReopening ="";
		currentMeterRegisterReadReopening ="";
		document.getElementById('reopeningMessage').innerHTML="";
		document.getElementById('reopeningMessage').title="Server Message.";
		document.getElementById('btnSaveReopening').disabled=false;
		if(lastKNO.toString()==toUpdateKNO.toString()){
			//resetMeterReplacementAjaxCall();	
		}		
	}
	function validateReopeningDetails(){		
		if(Trim(document.getElementById('billRoundReopening').value).length==0){
			document.getElementById('billRoundReopening').focus();
			document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('zoneReopening').value).length==0){
			document.getElementById('zoneReopening').focus();
			document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('mrNoReopening').value).length==0){
			document.getElementById('mrNoReopening').focus();
			document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('areaReopening').value).length==0){
			document.getElementById('areaReopening').focus();
			document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('wcNoReopening').value).length==0){
			document.getElementById('wcNoReopening').focus();
			document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('knoReopening').value).length==0){
			document.getElementById('knoReopening').focus();
			document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		/*if(Trim(document.getElementById('nameReopening').value).length==0){
			document.getElementById('nameReopening').focus();
			document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}*/		
		
		var confirmedValue="";		
		if(currentMeterReadDateReopening.length==0){
			document.getElementById('currentMeterReadDateReopening').focus();
			document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		if(Trim(document.getElementById('currentMeterReadDateConfirmReopening').value).length==0){
			document.getElementById('currentMeterReadDateConfirmReopening').focus();
			document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		confirmedValue=Trim(document.getElementById('currentMeterReadDateConfirmReopening').value);
		if(currentMeterReadDateReopening.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('currentMeterReadDateConfirmReopeningSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('currentMeterReadDateConfirmReopeningSpan').title="Doesn't Match";
			document.getElementById('currentMeterReadDateConfirmReopening').focus();
			return false;
		}else{
			document.getElementById('currentMeterReadDateConfirmReopeningSpan').innerHTML="";
			document.getElementById('currentMeterReadDateConfirmReopeningSpan').title="OK";
		}
		if(Trim(document.getElementById('currentMeterReadRemarkCode').value).length==0){
			document.getElementById('currentMeterReadRemarkCode').focus();
			document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			return false;
		}
		var readType=fnGetReadTypeReopening(Trim(document.getElementById('currentMeterReadRemarkCode').value));		
		if(readType.toString()=='Volumetric Billing'){
			document.getElementById('currentMeterRegisterReadReopening').disabled=false;				
			document.getElementById('currentMeterRegisterReadConfirmReopening').disabled=false;
			if(Trim(document.getElementById('currentMeterRegisterReadReopening').value).length==0){
				document.getElementById('currentMeterRegisterReadReopening').focus();
				document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
				return false;
			}
			if(Trim(document.getElementById('currentMeterRegisterReadConfirmReopening').value).length==0){
				document.getElementById('reopeningMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";				
				document.getElementById('currentMeterRegisterReadConfirmReopening').focus();
				return false;
			}
			confirmedValue=Trim(document.getElementById('currentMeterRegisterReadConfirmReopening').value);
			if(currentMeterRegisterReadReopening.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
				document.getElementById('currentMeterRegisterReadConfirmReopeningSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
				document.getElementById('currentMeterRegisterReadConfirmReopeningSpan').title="Doesn't Match";
				document.getElementById('currentMeterRegisterReadConfirmReopening').focus();
				return false;
			}else{
				document.getElementById('currentMeterRegisterReadConfirmReopeningSpan').innerHTML="";
				document.getElementById('currentMeterRegisterReadConfirmReopeningSpan').title="OK";
			}
		}else{
			document.getElementById('currentMeterRegisterReadReopening').disabled=true;
			document.getElementById('currentMeterRegisterReadConfirmReopening').disabled=true;
		}
		confirmedValue=Trim(document.getElementById('newAverageConsumptionConfirmReopening').value);
		if((newAverageConsumptionReopening.toString()!=confirmedValue.toString() && confirmedValue.length > 0)||(newAverageConsumptionReopening.length > 0 && confirmedValue.length == 0)){			
			document.getElementById('newAverageConsumptionConfirmReopeningSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('newAverageConsumptionConfirmReopeningSpan').title="Doesn't Match";
			document.getElementById('newAverageConsumptionConfirmReopening').focus();
			return false;
		}else{
			document.getElementById('newAverageConsumptionConfirmReopeningSpan').innerHTML="";
			document.getElementById('newAverageConsumptionConfirmReopeningSpan').title="OK";
		}		
		return true;
	}
	function fnCheckCurrentMeterReadDateReopeningConfirm(){
		var confirmedValue=Trim(document.getElementById('currentMeterReadDateConfirmReopening').value);
		if(currentMeterReadDateReopening.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('currentMeterReadDateConfirmReopeningSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('currentMeterReadDateConfirmReopeningSpan').title="Doesn't Match";
			document.getElementById('currentMeterReadDateConfirmReopening').focus();
		}else{
			document.getElementById('currentMeterReadDateConfirmReopeningSpan').innerHTML="";
			document.getElementById('currentMeterReadDateConfirmReopeningSpan').title="OK";
		}
	}
	function fnCheckCurrentMeterRegisterReadReopeningConfirm(){
		var confirmedValue=Trim(document.getElementById('currentMeterRegisterReadConfirmReopening').value);
		if(currentMeterRegisterReadReopening.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('currentMeterRegisterReadConfirmReopeningSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('currentMeterRegisterReadConfirmReopeningSpan').title="Doesn't Match";
			document.getElementById('currentMeterRegisterReadConfirmReopening').focus();
		}else{
			document.getElementById('currentMeterRegisterReadConfirmReopeningSpan').innerHTML="";
			document.getElementById('currentMeterRegisterReadConfirmReopeningSpan').title="OK";
		}
	}
	function fnCheckNewAverageConsumptionReopeningConfirm(){
		var confirmedValue=Trim(document.getElementById('newAverageConsumptionConfirmReopening').value);
		if(newAverageConsumptionReopening.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('newAverageConsumptionConfirmReopeningSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('newAverageConsumptionConfirmReopeningSpan').title="Doesn't Match";
			document.getElementById('newAverageConsumptionConfirmReopening').focus();
		}else{
			document.getElementById('newAverageConsumptionConfirmReopeningSpan').innerHTML="";
			document.getElementById('newAverageConsumptionConfirmReopeningSpan').title="OK";
		}
	}
	function fnCheckOldMeterRegisterReadReopeningConfirm(){
		var confirmedValue=Trim(document.getElementById('oldMeterRegisterReadConfirmReopening').value);
		if(oldMeterRegisterReadReopening.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('oldMeterRegisterReadConfirmReopeningSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('oldMeterRegisterReadConfirmReopeningSpan').title="Doesn't Match";
			document.getElementById('oldMeterRegisterReadConfirmReopening').focus();
		}else{
			document.getElementById('oldMeterRegisterReadConfirmReopeningSpan').innerHTML="";
			document.getElementById('oldMeterRegisterReadConfirmReopeningSpan').title="OK";
		}
	}
	//Added on 11-12-2012 by Matiur Rahman
	function fnCheckOldMeterAverageReadReopeningConfirm(){
		var confirmedValue=Trim(document.getElementById('oldMeterAverageReadConfirmReopening').value);
		if(oldMeterAverageReadReopening.toString()!=confirmedValue.toString() && confirmedValue.length > 0){			
			document.getElementById('oldMeterAverageReadConfirmReopeningSpan').innerHTML="<font size='2' color='red'><b>X</b></font>";
			document.getElementById('oldMeterAverageReadConfirmReopeningSpan').title="Doesn't Match";
			document.getElementById('oldMeterAverageReadConfirmReopening').focus();
		}else{
			document.getElementById('oldMeterAverageReadConfirmReopeningSpan').innerHTML="";
			document.getElementById('oldMeterAverageReadConfirmReopeningSpan').title="OK";
		}
	}
	//============================================================
	function fnMakeTwoDigit(obj){
		obj.value=Trim(obj.value);
		IsNumber(obj);
		obj.value=makeTwoDigit(obj.value);
	}
	function setMeterReadStatusReopening(obj){
		fnCheckMeterReadStatusReopening(obj);
		fnSetReadTypeReopening(obj);			
		
	}
	function fnCheckMeterReadStatusReopening(obj){
		var meterReadStatus=Trim(obj.value);
		//3. Current Meter Reading field should only be enabled for (OK, PUMP).
		if(meterReadStatus.toString()=='OK' || meterReadStatus.toString()=='PUMP'){ 	 											
			var fieldId="currentMeterRegisterReadConfirmReopening";
			document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=false; 
			fieldId="currentMeterRegisterReadReopening";
			//document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=false; 
			//document.getElementById(fieldId).focus(); 
		}else{
			var fieldId="currentMeterRegisterReadConfirmReopening";
			document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=true; 								
			fieldId="currentMeterRegisterReadReopening";
			document.getElementById(fieldId).value="";
			document.getElementById(fieldId).disabled=true; 
		}
		//4. In case of (NUW, PLUG, DEM) Current Meter Reading should always be equal to Previous Meter Reading.
		/*if(meterReadStatus.toString()=='NUW' || meterReadStatus.toString()=='PLUG'|| meterReadStatus.toString()=='DEM'){
			var fieldId="hidPrevMeterRead";
			var prevMeterRead=Trim(document.getElementById(fieldId).value);		
			if(prevMeterRead!='NA'){
				fieldId="currentMeterRegisterReadReopening";
				document.getElementById(fieldId).value=prevMeterRead;
				document.getElementById(fieldId).disabled=true; 
				fieldId="currentMeterRegisterReadConfirmReopening";
				document.getElementById(fieldId).value=prevMeterRead;
				document.getElementById(fieldId).disabled=true; 
				toUpdateCurrentMeterReadConfirm=prevMeterRead;
				toUpdateCurrentMeterRead=prevMeterRead;
			}
		}*/
	}	
	function fnSetReadTypeReopening(obj)
	{
		var meterReadStatus=Trim(obj.value);
		var readType=fnGetReadTypeReopening(meterReadStatus);
		var meterReadTypeSpanId="meterReadTypeSpanReopening";
		var billTypeSpanId="billTypeSpanReopening";	
		var currentMeterReadId="currentMeterRegisterReadReopening";			
		var currentMeterReadConfirmId="currentMeterRegisterReadConfirmReopening";
		document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
		document.getElementById(billTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";		
		if(readType=='Volumetric Billing'){
			//if(document.getElementById('consumerTypeReopening').value=='METERED'&& (meterReadStatus=='OK'||meterReadStatus=='PUMP')){
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
			//alert(readType+'<>'+document.getElementById('consumerTypeReopening').value+'<>'+meterReadStatus);
			//if(document.getElementById('consumerTypeReopening').value=='METERED' && meterReadStatus!=''){	
			if(meterReadStatus!=''){	//Removed Consumer Type Consideration by Matiur As per Discussion with Yash on 12-12-2012
				document.getElementById(currentMeterReadId).value="0";
				currentMeterRegisterReadReopening="0";
				document.getElementById(currentMeterReadConfirmId).value="0";
				document.getElementById(currentMeterReadId).disabled=true;				
				document.getElementById(currentMeterReadConfirmId).disabled=true;
				document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='red'><b>No Read</b></font>";
				document.getElementById(billTypeSpanId).innerHTML = "<font color='red'><b>"+readType+"</b></font>";
			}
			//if(document.getElementById('consumerTypeReopening').value!='METERED' ||meterReadStatus==''){
			if(meterReadStatus==''){//Removed Consumer Type Consideration by Matiur As per Discussion with Yash on 12-12-2012
				document.getElementById(currentMeterReadId).disabled=true;				
				document.getElementById(currentMeterReadConfirmId).disabled=true;
				document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
				document.getElementById(billTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
			}
		}
	}
	function fnGetReadTypeReopening(meterReaderRemarkCode){
		var readType="No Billing";
		if(Trim(meterReaderRemarkCode).length!=0){
			if(regularReadTypeReopening.indexOf(meterReaderRemarkCode)>-1){
				readType="Volumetric Billing";
				return readType;
			}
			if(averageReadTypeReopening.indexOf(meterReaderRemarkCode)>-1){
				readType="Average Billing";
				return readType;
			}
			if(provisionalReadTypeReopening.indexOf(meterReaderRemarkCode)>-1){
				readType="Provisional Billing";
				return readType;
			}
			if(noBillingReadTypeReopening.indexOf(meterReaderRemarkCode)>-1){
				readType="No Billing";
				return readType;
			}
		}
		return readType;
	}
	function fnCheckOldMeterReadRemarkCode(obj){
		var readType=fnGetReadTypeReopening(Trim(obj.value));
		if(readType.toString()!='Volumetric Billing'){
			/*if(document.getElementById('consumerTypeReopening').value=='METERED'&& (meterReadStatus=='OK'||meterReadStatus=='PUMP')){
				document.getElementById('oldMeterRegisterReadReopening').disabled=false;
				document.getElementById('oldMeterRegisterReadConfirmReopening').disabled=false;
			}else{*/
				document.getElementById('oldMeterRegisterReadReopening').disabled=true;				
				document.getElementById('oldMeterRegisterReadConfirmReopening').disabled=true;
			//}
		}else{
			document.getElementById('oldMeterRegisterReadReopening').disabled=false;
			document.getElementById('oldMeterRegisterReadConfirmReopening').disabled=false;
		}
	
	}
	function fnCheckBillRound(){
		if(Trim(document.getElementById('billRoundReopening').value).length==0){
			document.getElementById('reopeningMessage').innerHTML="<font color='red' size='2'><b>Please Select Bill Round First</b></font>";
			if(!isPopUp){
				document.getElementById('zoneReopening').value="";
				//document.getElementById('zoneReopening').disabled=true;
			}
			document.getElementById('billRoundReopening').focus();
		}else{
			document.getElementById('reopeningMessage').innerHTML="<font color='red' size='2'><b></b></font>";
		}
	}
	function fnSetDefaultValue(){
		if(Trim(document.getElementById('zeroReadReopening').value)=='0'){
			zeroReadReopening="0";
		}
	}
	function fnResetDeatils(){
		document.getElementById('zoneReopening').value="";
		document.getElementById('zoneReopening').disabled=false;
		document.getElementById('mrNoReopening').value="";
		document.getElementById('mrNoReopening').disabled=false;
		document.getElementById('areaReopening').value="";
		document.getElementById('areaReopening').disabled=false;
		document.getElementById('wcNoReopening').value="";
		document.getElementById('wcNoReopening').disabled=false;
		document.getElementById('knoReopening').value="";
		document.getElementById('knoReopening').disabled=false;
		document.getElementById('nameReopening').value="";
	}
	function fnEnableSaveButton(){
		document.getElementById('btnSaveReopening').disabled=false;
	}
	//Start- Added by Matiur Rahman on 30-11-2012 as per JTrac Issue # DJB-720
	function fnCheckModel(obj){		
		if(Trim(document.getElementById('manufacturerReopening').value)=='N/A' && Trim(document.getElementById('modelReopening').value)!='N/A W'){
			document.getElementById('modelReopening').value="N/A W";
			document.getElementById('modelReopening').disabled=true;
		}else{
			document.getElementById('modelReopening').value="";
			document.getElementById('modelReopening').disabled=false;
		}
	}
	//End- Added by Matiur Rahman on 30-11-2012 as per JTrac Issue # DJB-720
	
	//Start-Added By Matiur Rahman on 12-12-2012 as per discussion with Yash
	//Reset Meter Replacement Details in data base	
	function resetMeterReplacementAjaxCall(){		
		if(knoToResetReopening!='' && billRoundToResetReopening!=''){		
			if(confirm("Click OK to Reset from the Data Base as wel, else Cancel")){
				document.getElementById('reopeningMessage').innerHTML="";
				document.getElementById('reopeningMessage').title="Server Message.";
				document.getElementById('hidActionReopening').value="resetReopeningDetails";
				var url= "callMeterReplacementAJax.action?hidAction=resetReopeningDetails&seqNo="+document.getElementById('slNo').value;
				url+="&meterReaderName="+document.getElementById('meterReaderName').value+"&billRound="+billRoundToResetReopening;
				url+="&zone="+document.getElementById('zoneReopening').value+"&mrNo="+document.getElementById("mrNoReopening").value+"&area="+document.getElementById("areaReopening").value+"&wcNo="+document.getElementById('wcNoReopening').value+"&kno="+knoToResetReopening+"&name="+document.getElementById('nameReopening').value;
				url+="&waterConnectionSize="+document.getElementById('waterConnectionSizeReopening').value+"&waterConnectionUse="+document.getElementById("waterConnectionUseReopening").value;
				url+="&meterType="+document.getElementById('meterType').value+"&badgeNo="+document.getElementById("badgeNoReopening").value+"&manufacturer="+document.getElementById("manufacturerReopening").value+"&model="+document.getElementById('modelReopening').value;
				url+="&saType="+document.getElementById('saType').value;
				url+="&consumerType="+document.getElementById('consumerTypeReopening').value;
				url+="&consumerCategory="+document.getElementById('consumerCategoryReopening').value;
				url+="&isLNTServiceProvider="+document.getElementById('isLNTServiceProvider').value;
				url+="&meterInstalDate="+meterInstalDate+"&zeroRead="+zeroReadReopening;
				url+="&currentMeterReadDate="+currentMeterReadDateReopening+"&currentMeterRegisterRead="+currentMeterRegisterReadReopening+"&newAverageConsumption="+newAverageConsumptionReopening;
				url+="&currentMeterReadRemarkCode="+document.getElementById('currentMeterReadRemarkCode').value+"&oldMeterRegisterRead="+oldMeterRegisterReadReopening;
				url+="&oldMeterReadRemarkCode="+document.getElementById('oldMeterReadRemarkCode').value;
				url+="&oldMeterAverageRead="+oldMeterAverageReadReopening;
				//alert('url>>'+url);     
				document.getElementById('btnSaveReopening').disabled=true;
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
					//hideReopeningScreen();
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
								document.getElementById('btnSaveReopening').disabled=false;
								document.getElementById('reopeningMessage').innerHTML="<font color='red' size='2'>Unable to process your last Request. Please Try again.</font>";
								showScreen();
								document.body.style.overflow = 'auto';
								if(isPopUp){
									document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='red' size='2'>Unable to process your last Request. Please Try again.</font></div>";
									popupReopeningScreen();
								}
							}else{
								showScreen();
								document.body.style.overflow = 'auto';
								if(isPopUp){
									clearToUpdateVar();					 
									hideReopeningScreen();
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
								document.getElementById('reopeningMessage').innerHTML="<font color='green' size='2'><b>Meter Replacement Details for KNO: "+ knoToResetReopening +" Reset Successfully.</b></font>";
							}   
						}     
					};  
					httpBowserObject.send(null);
				}
			}
		}
	}
	//End-Added By Matiur Rahman on 12-12-2012