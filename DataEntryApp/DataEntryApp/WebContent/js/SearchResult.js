
		//Added By Matiur Rahman
		//=======================For Ajax Call Start=================================
		//var httpObject = null;
		// Get the HTTP Object
		/*function getHTTPObjectForBrowser(){
			if (window.ActiveXObject){                
				return new ActiveXObject("Microsoft.XMLHTTP");   
			}else if (window.XMLHttpRequest){ 
				return new XMLHttpRequest();  
			}else {                 
				alert("Browser does not support AJAX...........");             
				return null;     
			}    
		}*/
		//Ajax Call       
		function doAjaxCall(seqNo,kno,billRound,meterReadDate,meterReadStatus,currentMeterRead,newAvgConsumption,newNoOfFloor,consumerStatus,resetFlag,remarks){             
			
			httpObject = getHTTPObjectForBrowser();    
			var url= "callAJax.action?seqNo="+seqNo+"&kno="+kno+"&billRound="+billRound+"&meterReadDate="+meterReadDate+"&meterReadStatus="+meterReadStatus+"&currentMeterRead="+currentMeterRead+"&newAvgConsumption="+newAvgConsumption+"&newNoOfFloor="+newNoOfFloor+"&consumerStatus="+consumerStatus+"&resetFlag="+resetFlag+"&remarks="+remarks;  
				//alert(httpObject);      
			if (httpObject != null) {
				hideScreen();
				setAll();
				//document.getElementById('data_submited').innerHTML ="<font color='green' size='2'>Data Submited</font>";	
				document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='green' size='2'>Data Submited</font></div>";
				//alert('url::'+url);                 
				httpObject.open("POST", url, true);            
				httpObject.send(null);    
				httpObject.onreadystatechange = setAjaxOutput;  
			}   
		}  
		// Set ajax-result div     
		function setAjaxOutput(){         
			if(httpObject.readyState == 4){
				var  responseString= httpObject.responseText;
				//alert(document.getElementById('ajax-result')+'<>'+responseString);
				if(null!=responseString && responseString.indexOf("ERROR:")>-1){			
					isValid=false;
					alert('Current Meter Read Should be more than Previouse Actual Meter Read.');
					showScreen(); 
				}else if(null!=responseString && responseString.indexOf("ERROR :")>-1){			
					isValid=false;
					alert('\n\nSorry ! Last record could not be saved successfully, Please see the Error message.\n\n');
					showScreen(); 
				}else{
					isValid=true;
					showScreen(); 
				}
				document.getElementById('ajax-result').innerHTML = httpObject.responseText; 
				//serverMSG= httpObject.responseText;      
			}     
		}
		//=======================Ajax Call End=================================
		//Disable Going Back
		window.history.forward(1);
		//Global Variables
		var isValid=true;
		var toUpdateSeqNO="";
		var toUpdateKNO="";
		var toUpdateBillRound="";
		var toUpdateConsumerStatus="";	
		var toUpdateMeterReadDate="";
		var toUpdateMeterReadDateConfirm="";
		var toUpdateMeterReadStatus="";
		var toUpdateCurrentMeterRead="";
		var toUpdateCurrentMeterReadConfirm="";
		var toUpdateNewAvgConsumption="";
		var toUpdateNewAvgConsumptionConfirm="";
		var toUpdateNewNoOfFloor="";
		var toUpdateNewNoOfFloorConfirm="";
		var toUpdateRemarks="";
		var dataSaved=false;
 		var isPopUpWindow=false;	
		//var serverMSG="";
		function clearToUpdateVar(){
			toUpdateSeqNO="";
			toUpdateKNO="";
			toUpdateBillRound="";
			toUpdateMeterReadDate="";
			toUpdateMeterReadDateConfirm="";			
			toUpdateMeterReadStatus="";
			toUpdateCurrentMeterRead="";
			toUpdateCurrentMeterReadConfirm="";
			toUpdateNewAvgConsumption="";
			toUpdateNewAvgConsumptionConfirm="";
			toUpdateNewNoOfFloor="";
			toUpdateNewNoOfFloorConfirm="";
			toUpdateConsumerStatus="";
			toUpdateRemarks="";
			setStatusBarMessage('');
		}	
		//Update Record
		function fnUpdateRecord(page){	
			var curPG=currentPage;
			isValid=true;
			dataSaved=false;
			var resetFlag="";
			//alert('toUpdateSeqNO>>'+toUpdateSeqNO+'>>toUpdateKNO>>'+toUpdateKNO+'>>toUpdateBillRound>>'+toUpdateBillRound);
			if(toUpdateConsumerStatus!='60'){
				//===============================Added Remarks Validation on 16-11-12===by Tuhin=================================
				if(toUpdateConsumerStatus=='15' && toUpdateRemarks==''){
					alert('For Consumer Status in Invalid Data Remarks is Mandatory .');
					var fieldId="remarks"+currentPage; 
				    document.getElementById(fieldId).focus();
			        isValid=false;
				}else{
				//=============================================================================================		
		 	 		
					var fieldId="hidConStatus"+currentPage;
					document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);
					if(toUpdateKNO!='' && toUpdateBillRound!=''){
						getAll();
						dataSaved=true;
						doAjaxCall(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDate,toUpdateMeterReadStatus,toUpdateCurrentMeterRead,toUpdateNewAvgConsumption,toUpdateNewNoOfFloor,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
					}
					currentPage=page;
				}
			}else{//alert('2');
				if(validate()){
					var fieldId="hidMeterStatus"+currentPage; 
					document.getElementById(fieldId).value=Trim(toUpdateMeterReadStatus);
					fieldId="hidConStatus"+currentPage;
					document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);
					//alert(isValid && toUpdateKNO!=''&& toUpdateBillRound!='' && toUpdateMeterReadDate.length==10);
					if(isValid && toUpdateKNO!=''&& toUpdateBillRound!='' && toUpdateMeterReadDate.length==10 ){
						getAll();
						dataSaved=true;
						doAjaxCall(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDate,toUpdateMeterReadStatus,toUpdateCurrentMeterReadConfirm,toUpdateNewAvgConsumptionConfirm,toUpdateNewNoOfFloorConfirm,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
					}
					currentPage=page;
				}
			}
			if(!dataSaved && isValid){
		    	if(!confirm('No Data to Save.\nAre you sure you want to move away without Saving Data?')){
		    		isValid=false;
		    		currentPage=curPG;
			    }
			}
		}
		function fnUpdateLastRecord(page){	//alert();		
			isValid=true;
			dataSaved=false;
			var resetFlag="";
			if(toUpdateConsumerStatus!='60'){
				//===============================Added Remarks Validation on 16-11-12===by Tuhin=================================
				if(toUpdateConsumerStatus=='15' && toUpdateRemarks==''){
					alert('For Consumer Status in Invalid Data Remarks is Mandatory .');
					var fieldId="remarks"+currentPage; 
				    document.getElementById(fieldId).focus();
			        isValid=false;
				}else{
				//=============================================================================================		
		 	 		var fieldId="hidConStatus"+currentPage;
					document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);		
					if(toUpdateKNO!='' && toUpdateBillRound!=''){
						getAll();
						dataSaved=true;
						doAjaxCall(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDate,toUpdateMeterReadStatus,toUpdateCurrentMeterRead,toUpdateNewAvgConsumption,toUpdateNewNoOfFloor,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
						
					}
				}
			}else{
				if(validate()){
					var fieldId="hidMeterStatus"+currentPage; 
					document.getElementById(fieldId).value=Trim(toUpdateMeterReadStatus);
					fieldId="hidConStatus"+currentPage;
					document.getElementById(fieldId).value=Trim(toUpdateConsumerStatus);
					if(isValid && toUpdateKNO!=''&& toUpdateBillRound!=''){
						getAll();
						dataSaved=true;
						doAjaxCall(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDateConfirm,toUpdateMeterReadStatus,toUpdateCurrentMeterReadConfirm,toUpdateNewAvgConsumptionConfirm,toUpdateNewNoOfFloorConfirm,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
						
					}
				}
			}
			if(!dataSaved && isValid){
		    	alert('No Data to Save.');
		    	isValid=false;
			}
				
		}

		function setConsumerStatus(obj){
			toUpdateConsumerStatus=Trim(obj.value);			
			disableFields(obj);
		}
		function disableFields(obj){			
			var ddId="meterReadDD"+currentPage;
			var mmId="meterReadMM"+currentPage;
			var ddIdConfirm="meterReadDDConfirm"+currentPage;
			var mmIdConfirm="meterReadMMConfirm"+currentPage;
			var meterReadStatusId="meterReadStatus"+currentPage;
			var currentMeterReadId="currentMeterRead"+currentPage;
			var currentMeterReadConfirmId="currentMeterReadConfirm"+currentPage;
			var newAverageConsumptionId="newAverageConsumption"+currentPage;
			var newAverageConsumptionConfirmId="newAverageConsumptionConfirm"+currentPage;
			var newNoOfFloorId="newNoOfFloor"+currentPage;
			var newNoOfFloorConfirmId="newNoOfFloorConfirm"+currentPage;
			if(obj.value!='60'){
				document.getElementById(ddId).disabled=true;
				document.getElementById(mmId).disabled=true;
				document.getElementById(ddIdConfirm).disabled=true;
				document.getElementById(mmIdConfirm).disabled=true;
				document.getElementById(meterReadStatusId).disabled=true;
				document.getElementById(currentMeterReadId).disabled=true;
				document.getElementById(currentMeterReadConfirmId).disabled=true;
				document.getElementById(newAverageConsumptionId).disabled=true;
				document.getElementById(newAverageConsumptionConfirmId).disabled=true;				
				document.getElementById(newNoOfFloorId).disabled=true;
				document.getElementById(newNoOfFloorConfirmId).disabled=true;	
				toUpdateMeterReadDate="";
				toUpdateMeterReadDateConfirm="";			
				toUpdateMeterReadStatus="";
				toUpdateCurrentMeterRead="";
				toUpdateCurrentMeterReadConfirm="";
				toUpdateNewAvgConsumption="";
				toUpdateNewAvgConsumptionConfirm="";
				toUpdateNewNoOfFloor="";
				toUpdateNewNoOfFloorConfirm="";
				toUpdateConsumerStatus=obj.value;
			}else{
				document.getElementById(ddId).disabled=false;
				document.getElementById(mmId).disabled=false;
				document.getElementById(ddIdConfirm).disabled=false;
				document.getElementById(mmIdConfirm).disabled=false;
				document.getElementById(meterReadStatusId).disabled=false;
				document.getElementById(currentMeterReadId).disabled=false;
				document.getElementById(currentMeterReadConfirmId).disabled=false;
				document.getElementById(newAverageConsumptionId).disabled=false;
				document.getElementById(newAverageConsumptionConfirmId).disabled=false;
				document.getElementById(newNoOfFloorId).disabled=false;
				document.getElementById(newNoOfFloorConfirmId).disabled=false;
				toUpdateConsumerStatus=obj.value;
			}
			//fnSetReadType();	
		}
		function disableFieldsOnConsumerType(){
			var consumerStatusId="consumerStatus"+currentPage;
			var ddId="meterReadDD"+currentPage;
			var mmId="meterReadMM"+currentPage;
			var ddIdConfirm="meterReadDDConfirm"+currentPage;
			var mmIdConfirm="meterReadMMConfirm"+currentPage;
			var meterReadStatusId="meterReadStatus"+currentPage;
			var currentMeterReadId="currentMeterRead"+currentPage;
			var currentMeterReadConfirmId="currentMeterReadConfirm"+currentPage;
			document.getElementById(meterReadStatusId).disabled=true;
			document.getElementById(currentMeterReadId).disabled=true;
			document.getElementById(currentMeterReadConfirmId).disabled=true;			
			//clearToUpdateVar();	
			//fnSetReadType();
		}
		function resetMeterReadDate(){
			var ddId="meterReadDDConfirm"+currentPage;
			var mmId="meterReadMMConfirm"+currentPage;
			document.getElementById(ddId).value="";
			document.getElementById(mmId).value="";
			ddId="meterReadDD"+currentPage;
			mmId="meterReadMM"+currentPage;
			document.getElementById(ddId).value="";
			document.getElementById(mmId).value="";
			document.getElementById(ddId).focus();
			document.getElementById(ddId).focus();
			toUpdateMeterReadDate="";
			toUpdateMeterReadDateConfirm="";
		}		
		function resetCurrentMeterRead(){
			var fieldValue="";			
			var fieldId="currentMeterReadConfirm"+currentPage;
			document.getElementById(fieldId).value=fieldValue;	
			fieldId="currentMeterRead"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			document.getElementById(fieldId).focus();
			
		}
		function resetNewAvgConsumption(){
			var fieldValue="";
			fieldId="newAverageConsumptionConfirm"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			fieldId="newAverageConsumption"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			document.getElementById(fieldId).focus();
		}
		function resetAll(){
			if(confirm("Are You Sure You want to Reset ?")){
				var fieldId="seqNo"+currentPage;
				var fieldValue="";
				/*document.getElementById(fieldId).value=fieldValue;			
				
				fieldId="kno"+currentPage;
				document.getElementById(fieldId).value=fieldValue;
				
				fieldId="billRound"+currentPage;
				document.getElementById(fieldId).value=fieldValue;
				*/
				var ddId="meterReadDD"+currentPage;
				var mmId="meterReadMM"+currentPage;
				document.getElementById(ddId).value="";
				document.getElementById(mmId).value="";
				ddId="meterReadDDConfirm"+currentPage;
				mmId="meterReadMMConfirm"+currentPage;
				document.getElementById(ddId).value="";
				document.getElementById(mmId).value="";
							
				fieldId="meterReadStatus"+currentPage;
				document.getElementById(fieldId).value=fieldValue;	
				
				fieldId="currentMeterRead"+currentPage;
				document.getElementById(fieldId).value=fieldValue;
				fieldId="currentMeterReadConfirm"+currentPage;
				document.getElementById(fieldId).value=fieldValue;			
				
				fieldId="newAverageConsumption"+currentPage;
				document.getElementById(fieldId).value=fieldValue;
				fieldId="newAverageConsumptionConfirm"+currentPage;
				document.getElementById(fieldId).value=fieldValue;
				//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
				fieldId="newNoOfFloor"+currentPage;
				document.getElementById(fieldId).value=fieldValue;
				fieldId="newNoOfFloorConfirm"+currentPage;
				document.getElementById(fieldId).value=fieldValue;
				//=========================================================			
				fieldId="consumerStatus"+currentPage;			
				document.getElementById(fieldId).value="60";
				setAll();
				var resetFlag="Y";
				doAjaxCall(toUpdateSeqNO,toUpdateKNO,toUpdateBillRound,toUpdateMeterReadDateConfirm,toUpdateMeterReadStatus,toUpdateCurrentMeterReadConfirm,toUpdateNewAvgConsumptionConfirm,toUpdateNewNoOfFloorConfirm,toUpdateConsumerStatus,resetFlag,toUpdateRemarks);
			}
		}
		function resetCorrespondingToStatus(){	
			
			var ddId="meterReadDD"+currentPage;
			var mmId="meterReadMM"+currentPage;
			document.getElementById(ddId).value="";
			document.getElementById(mmId).value="";
			ddId="meterReadDDConfirm"+currentPage;
			mmId="meterReadMMConfirm"+currentPage;
			document.getElementById(ddId).value="";
			document.getElementById(mmId).value="";
			var fieldValue="";		
			var fieldId="meterReadStatus"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			fieldId="currentMeterRead"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			fieldId="currentMeterReadConfirm"+currentPage;
			document.getElementById(fieldId).value=fieldValue;		
			fieldId="newAverageConsumption"+currentPage;
			document.getElementById(fieldId).value=fieldValue;
			fieldId="newAverageConsumptionConfirm"+currentPage;
			document.getElementById(fieldId).value=fieldValue;	
		}
		function setMeterReadDate(){
			setAll();
			var errorMsg="none";
			var ddId="meterReadDD"+currentPage;
			var mmId="meterReadMM"+currentPage;
			var yyyyId="meterReadYYYY"+currentPage;
			document.getElementById(ddId).value=makeTwoDigit(Trim(document.getElementById(ddId).value));
			document.getElementById(mmId).value=makeTwoDigit(Trim(document.getElementById(mmId).value));
			var meterReadDate=document.getElementById(ddId).value+"/"+document.getElementById(mmId).value+"/"+document.getElementById(yyyyId).value;
			if(meterReadDate.length==10){
				errorMsg = getDateErrorMessageString(meterReadDate);
			}
			if(meterReadDate.length==10 &&errorMsg!='none'){		
					document.getElementById(ddId).value="";
					document.getElementById(mmId).value="";
					alert('PLEASE ENTER A VALID DATE.');
					document.getElementById(ddId).focus();
					return;
				
			}else{
				toUpdateMeterReadDate=meterReadDate;
				toUpdateMeterReadDateConfirm="";
			}			
		}
		function setMeterReadStatus(obj){
			setAll();
			toUpdateMeterReadStatus=Trim(obj.value);
			fnCheckMeterReadStatus();
			fnSetReadType();			
			
		}
		function setCurrentMeterRead(obj){
			setAll();
			toUpdateCurrentMeterRead=Trim(obj.value);
			toUpdateCurrentMeterReadConfirm="";			
		}
		function setNewAvgConsumption(obj){
			setAll();
			toUpdateNewAvgConsumption=Trim(obj.value);
			toUpdateNewAvgConsumptionConfirm="";
		}
		//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
		function setNewNoOfFloor(obj){
			setAll();
			toUpdateNewNoOfFloor=Trim(obj.value);
			toUpdateNewNoOfFloorConfirm="";
		}
		//====================================================
		//Added for Remarks on 16-11-2012 by Tuhin============= 
		function setRemarks(obj){
			setAll();
			toUpdateRemarks=Trim(obj.value);
		}
		//====================================================	
		
		function enableMeterReadDate(){				
			var ddId="meterReadDD"+currentPage;
			var mmId="meterReadMM"+currentPage;
			var yyyyId="meterReadYYYY"+currentPage;
			var meterReadDateSpanId="meterReadDateSpan"+currentPage;				
			var html="<input type='text' name='"+ddId+"' id='"+ddId+"' size='1' maxlength='2' class='textbox'  style='text-align:center;'  onchange='setMeterReadDate();'onkeyup='fnSetFocusMM(this);' value='"+document.getElementById(ddId).value+"'/>&nbsp;/&nbsp;";
			html=html+"<input type='text' name='"+mmId+"' id='"+mmId+"' size='1' maxlength='2' class='textbox'  style='text-align:center;'  onchange='setMeterReadDate();' onkeyup='fnSetFocusDDConfirm(this);' value='"+document.getElementById(mmId).value+"'/>&nbsp;/&nbsp;";
			document.getElementById(meterReadDateSpanId).innerHTML=html;			
			var ddIdConfirm="meterReadDDConfirm"+currentPage;
			var mmIdConfirm="meterReadMMConfirm"+currentPage;
			document.getElementById(ddIdConfirm).value="";
			document.getElementById(mmIdConfirm).value="";
			var confirmDateId="confirmDate"+currentPage;			
			document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";
			document.getElementById('data_submited').innerHTML ="&nbsp;";			
			  
		}

		function disableMeterReadDate(){
			var ddId="meterReadDD"+currentPage;
			var mmId="meterReadMM"+currentPage;
			var yyyyId="meterReadYYYY"+currentPage;
			var meterReadDateSpanId="meterReadDateSpan"+currentPage;				
			var html="<input type='password' name='"+ddId+"' id='"+ddId+"' size='2' maxlength='2' class='textbox'  style='text-align:center;'  onfocus='enableMeterReadDate();' onchange='setMeterReadDate();' value='"+document.getElementById(ddId).value+"'/>&nbsp;/&nbsp;";
			html=html+"<input type='password' name='"+mmId+"' id='"+mmId+"' size='2' maxlength='2' class='textbox'  style='text-align:center;'  onfocus='enableMeterReadDate();' onchange='setMeterReadDate();' value='"+document.getElementById(mmId).value+"'/>&nbsp;/&nbsp;";
			document.getElementById(meterReadDateSpanId).innerHTML=html;
		}

		function checkMeterReadDate(){			
			var ddId="meterReadDD"+currentPage;
			var mmId="meterReadMM"+currentPage;
			var yyyyId="meterReadYYYY"+currentPage;
			var ddValue=document.getElementById(ddId).value;
			var mmValue=document.getElementById(mmId).value;
			var yyyyValue=document.getElementById(yyyyId).value;
			var meterReadDate=ddValue+"/"+mmValue+"/"+yyyyValue;					
			var ddConfirmId="meterReadDDConfirm"+currentPage;
			var mmConfirmId="meterReadMMConfirm"+currentPage;
			var yyyyConfirmId="meterReadYYYYConfirm"+currentPage;	
			document.getElementById(ddConfirmId).value=makeTwoDigit(Trim(document.getElementById(ddConfirmId).value));
			document.getElementById(mmConfirmId).value=makeTwoDigit(Trim(document.getElementById(mmConfirmId).value));
			var ddConfirmValue=document.getElementById(ddConfirmId).value;
			var mmConfirmValue=document.getElementById(mmConfirmId).value;
			var yyyyConfirmValue=document.getElementById(yyyyConfirmId).value;
			var meterReadDateConfirm=ddConfirmValue+"/"+mmConfirmValue+"/"+yyyyConfirmValue;
			toUpdateMeterReadDateConfirm=Trim(meterReadDateConfirm);
			meterReadDate=Trim(meterReadDate);
			meterReadDateConfirm=Trim(meterReadDateConfirm);
			if(meterReadDate.length==10 && meterReadDateConfirm.length==10){
				var confirmDateId="confirmDate"+currentPage;
				if(parseInt(ddValue)== parseInt(ddConfirmValue) && parseInt(mmValue)==parseInt(mmConfirmValue) && parseInt(yyyyValue)== parseInt(yyyyConfirmValue)){
					//alert(meterReadDate+'<2>'+ meterReadDateConfirm);
					toUpdateMeterReadDateConfirm=meterReadDateConfirm;
					toUpdateMeterReadDate=meterReadDate;
					document.getElementById(confirmDateId).innerHTML="<font color='green'><b>OK</b></font>";	
									
				}else{
					//alert(meterReadDate+'<1>'+ meterReadDateConfirm);					
					document.getElementById(confirmDateId).innerHTML="<font color='red'>Does Not Match.</font>";
					toUpdateMeterReadDateConfirm="";
					resetMeterReadDate();				
				}				
			}			
		}
		function enableCurrentMeterRead(){
			//fnCheckMeterReadStatus();
			var consumerStatusId="consumerStatus"+currentPage;
			var consumerStatusValue=Trim(document.getElementById(consumerStatusId).value);
			//alert(consumerStatusValue);
			if(consumerStatusValue!='UNMETERED'){
				var fieldId="currentMeterRead"+currentPage;
				var fieldValue=Trim(document.getElementById(fieldId).value);
				var spanId="currentMeterReadSpan"+currentPage;				
				var html="<input type='text' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='textbox'  style='text-align:right;' onfocus='fnCheckMeterReadStatus();'  onblur='IsPositiveNumber(this);' onchange='setCurrentMeterRead(this);' value='"+fieldValue+"'/>";
				document.getElementById(spanId).innerHTML=html;
			}
			fnCheckMeterReadStatus();
		}
		function disableCurrentMeterRead(){
			fnCheckMeterReadStatus();
			var fieldId="currentMeterRead"+currentPage;
			var fieldValue=Trim(document.getElementById(fieldId).value);
			IsNumber(document.getElementById(fieldId));
			var spanId="currentMeterReadSpan"+currentPage;				
			var html="<input type='password' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='password'  style='text-align:right;'  onfocus='enableCurrentMeterRead();' onchange='setCurrentMeterRead(this);' value='"+fieldValue+"'/>";
			document.getElementById(spanId).innerHTML=html;
		}
		function checkCurrentMeterRead(){
			var textValue;	
			var textConfirm;			
			var fieldId="currentMeterReadConfirm"+currentPage;
			var fieldValue=Trim(document.getElementById(fieldId).value);
			textConfirm=Trim(fieldValue);
			fieldId="currentMeterRead"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			textValue=Trim(fieldValue);
			toUpdateCurrentMeterReadConfirm=textConfirm;			
			var confirmId="confirmCurrentMeterRead"+currentPage;
			if(textValue!='' && textConfirm!=''){
				if(parseFloat(textValue)!=parseFloat(textConfirm)){					
					document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";					
					toUpdateCurrentMeterReadConfirm="";
					//resetCurrentMeterRead();				
				}else{
					document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
					toUpdateCurrentMeterReadConfirm=textConfirm;
					toUpdateCurrentMeterRead=textValue;
				}
			}
		}	
		function enableNewAverageConsumption(){
			var fieldId="newAverageConsumption"+currentPage;
			var fieldValue=Trim(document.getElementById(fieldId).value);
			var spanId="newAverageConsumptionSpan"+currentPage;				
			var html="<input type='text' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='textbox'  style='text-align:right;' onblur='IsNumber(this);' onchange='setNewAvgConsumption(this);' value='"+fieldValue+"'/>";
			document.getElementById(spanId).innerHTML=html;	
		}
		function disableNewAverageConsumption(){
			var fieldId="newAverageConsumption"+currentPage;
			var fieldValue=Trim(document.getElementById(fieldId).value);
			var spanId="newAverageConsumptionSpan"+currentPage;				
			var html="<input type='password' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='password'  style='text-align:right;'  onfocus='enableNewAverageConsumption();' onchange='setNewAvgConsumption(this);' value='"+fieldValue+"'/>";
			document.getElementById(spanId).innerHTML=html;
		}		
		function checkNewAverageConsumption(){
			var textValue;	
			var textConfirm;				
			var fieldId="newAverageConsumptionConfirm"+currentPage;
			var fieldValue=Trim(document.getElementById(fieldId).value);
			textConfirm=fieldValue;	
			fieldId="newAverageConsumption"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			textValue=fieldValue;			
			toUpdateNewAvgConsumptionConfirm=textConfirm;			
			var confirmId="confirmNewAverageConsumption"+currentPage;
			if(textValue!='' && toUpdateNewAvgConsumptionConfirm!=''){
				if(parseFloat(textValue)!=parseFloat(textConfirm)){							
					document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";					
					toUpdateNewAvgConsumptionConfirm="";
					//resetNewAvgConsumption();			
				}else{
					document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
					toUpdateNewAvgConsumptionConfirm=textConfirm;
					toUpdateNewAvgConsumption=textValue;
				}
			}
		}
		//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
		function enableNewNoOfFloor(){
			var fieldId="newNoOfFloor"+currentPage;
			var fieldValue=Trim(document.getElementById(fieldId).value);
			var spanId="newNoOfFloorSpan"+currentPage;				
			var html="<input type='text' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='textbox'  style='text-align:right;' onblur='IsNumber(this);' onchange='setNewNoOfFloor(this);' value='"+fieldValue+"'/>";
			document.getElementById(spanId).innerHTML=html;	
		}
		function disableNewNoOfFloor(){
			var fieldId="newNoOfFloor"+currentPage;
			var fieldValue=Trim(document.getElementById(fieldId).value);
			var spanId="newNoOfFloorSpan"+currentPage;				
			var html="<input type='password' name='"+fieldId+"' id='"+fieldId+"' maxlength='15' class='password'  style='text-align:right;'  onfocus='enableNewNoOfFloor();' onchange='setNewNoOfFloor(this);' value='"+fieldValue+"'/>";
			document.getElementById(spanId).innerHTML=html;
		}
		function checkNewNoOfFloor(){
			var textValue;	
			var textConfirm;				
			var fieldId="newNoOfFloorConfirm"+currentPage;
			var fieldValue=Trim(document.getElementById(fieldId).value);
			textConfirm=fieldValue;	
			fieldId="newNoOfFloor"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			textValue=fieldValue;			
			toUpdateNewNoOfFloorConfirm=textConfirm;			
			var confirmId="confirmNewNoOfFloorConfirm"+currentPage;
			if(textValue!='' && toUpdateNewNoOfFloorConfirm!=''){
				if(parseInt(textValue)!=parseInt(textConfirm)){							
					document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";					
					toUpdateNewNoOfFloorConfirm="";
					//resetNewAvgConsumption();			
				}else{
					document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
					toUpdateNewNoOfFloorConfirm=textConfirm;
					toUpdateNewNoOfFloor=textValue;
				}
			}
		}
		//===========================================================
		function getAll(){	
			
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
			
			fieldId="meterReadStatus"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateMeterReadStatus=fieldValue;				
			
			fieldId="currentMeterRead"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateCurrentMeterRead=fieldValue;
			
			fieldId="newAverageConsumption"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateNewAvgConsumption=fieldValue;	
			//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
			fieldId="newNoOfFloor"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateNewNoOfFloor=fieldValue;	
			//=====================================================			
			fieldId="consumerStatus"+currentPage;			
			fieldValue=Trim(document.getElementById(fieldId).value);				
			toUpdateConsumerStatus=fieldValue;	
			//Added for Remarks 16-2012 by Tuhin ==================
			fieldId="remarks"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			toUpdateRemarks=fieldValue;	
			//=====================================================	
			
		}
		function setAll(){
			
			document.getElementById('ajax-result').innerHTML = "<div class='search-option'>&nbsp;</div>";
			document.getElementById('data_submited').innerHTML ="&nbsp;";	
			
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
			//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
			if(newNoOfFloorText!=''){
				toUpdateNewNoOfFloor=newNoOfFloorText;
				toUpdateNewNoOfFloorConfirm=newNoOfFloorText;
			}else{
				fieldId="newNoOfFloor"+currentPage;
				fieldValue=Trim(document.getElementById(fieldId).value);
				toUpdateNewNoOfFloor=fieldValue;
				toUpdateNewNoOfFloorConfirm=fieldValue;
			}
			
			//========================================================
			
			fieldId="consumerStatus"+currentPage;			
			fieldValue=Trim(document.getElementById(fieldId).value);				
			toUpdateConsumerStatus=fieldValue;
			
			//	alert("Invalid data :"+fieldValue);
			
			disableFields(document.getElementById(fieldId));			
			//====================Added for Remarks on 16-11-2012 by Tuhin 
			if(toUpdateConsumerStatus=='15'){
				var fieldId_remarks="remarks"+currentPage;
				//document.getElementById(fieldId_remarks).value="";
				fieldId_remarks="remarksDiv1"+currentPage;
				document.getElementById(fieldId_remarks).style.display = 'block';	
				fieldId_remarks="remarksDiv2"+currentPage;
				document.getElementById(fieldId_remarks).style.display = 'block';
			}else{
				var fieldId_remarks="remarks"+currentPage;
				document.getElementById(fieldId_remarks).value="";
				fieldId_remarks="remarksDiv1"+currentPage;
				document.getElementById(fieldId_remarks).style.display = 'none';
				fieldId_remarks="remarksDiv2"+currentPage;
				document.getElementById(fieldId_remarks).style.display = 'none';
			}
			//========================================================
			fieldId="popup"+currentPage;
			if(toUpdateConsumerStatus=='30'){
				if(document.getElementById(fieldId)){
					//document.getElementById(fieldId).innerHTML="<a href='#' onclick='clearMRMessage();popupMRScreen();'>Add Details</a>";
					//document.getElementById(fieldId).innerHTML="<a href='#' onclick='javascript:fnNotImplemented();'>Add Details</a>";
					//setAll();
					//popupMRScreen();
			  }else{
				  if(document.getElementById('popupMTR')){
					  //document.getElementById('popupMTR').innerHTML="<a href='#' onclick='popUpMeterReplacementScreen((parseInt(toUpdateSeqNO)+1),toUpdateBillRound,toUpdateKNO);'>Add Details</a>";
				  }
				  if(isPopUpWindow){
					 // alert();
					  popUpMeterReplacementScreen((parseInt(toUpdateSeqNO)+1),toUpdateBillRound,toUpdateKNO);
				  }
			  }
			}else{ 
				if(document.getElementById(fieldId)){
					document.getElementById(fieldId).innerHTML="";
				}
				if(document.getElementById('popupMTR')){
					  document.getElementById('popupMTR').innerHTML="";
				}
			}
			if(toUpdateConsumerStatus!='60'){
				resetCorrespondingToStatus();				
			}
			fieldId="hidConsumerType"+currentPage; 
			fieldValue=document.getElementById(fieldId).value;
			if(fieldValue!='METERED'){
				disableFieldsOnConsumerType();
			}
			fnSetReadType();
			//alert('toUpdateSeqNO>>'+toUpdateSeqNO+'>>toUpdateKNO>>'+toUpdateKNO+'>>toUpdateBillRound>>'+toUpdateBillRound);
				
		}
		function fnNotImplemented(){
			//alert('Not Implemented');
			 popupMRScreen();
		}
		function fnGoToPage(fromIndex,toIndex){
			document.forms[0].startPage.value=fromIndex;
			document.forms[0].lastPage.value=toIndex;
			document.forms[0].buttonClicked.value="";
			document.forms[0].action="dataEntry.action";
			hideScreen();
			document.forms[0].submit();
		}
		function fnGoToPageRange(obj){
			var fromIndex=Trim(obj.value);
			var toIndex=parseInt(fromIndex)+parseInt(MAX_RECORD_PER_PAGE)-1;	
			document.forms[0].startPage.value=fromIndex;
			document.forms[0].lastPage.value=toIndex;
			document.forms[0].buttonClicked.value="";
			document.forms[0].action="dataEntry.action";
			hideScreen();
			document.forms[0].submit();	
		}
		function fnGoTo(){
			var fromIndex=Trim(document.getElementById('goToRecNo').value);
			if(fromIndex.length!=0 && !isNaN(fromIndex)){		
				if(parseInt(fromIndex)>parseInt(totalRecords)){
					alert('Enter record no less than '+totalRecords);
					document.getElementById('goToRecNo').value="";
					document.getElementById('goToRecNo').focus();
					//return;
				}else if(parseInt(fromIndex)<1){
					alert('Enter record no greater than 1');
					document.getElementById('goToRecNo').value="";
					document.getElementById('goToRecNo').focus();
					//return;
				}else{
					var toIndex=parseInt(fromIndex)+parseInt(MAX_RECORD_PER_PAGE)-1;	
					document.forms[0].startPage.value=fromIndex;
					document.forms[0].lastPage.value=toIndex;
					document.forms[0].buttonClicked.value="";
					document.forms[0].action="dataEntry.action";
					hideScreen();
					document.forms[0].submit();
				}
			}else{
				alert('Record no should be Numeric.');
				document.getElementById('goToRecNo').value="";
				document.getElementById('goToRecNo').focus();
				//return;
			}
		}
		function fnGoToFreezePage(){
			hideScreen();
			//var answer = confirm("Are You Sure You want to Go To Freeze the MRD Screen and Review.");
			//if (answer){
				document.location.href="freeze.action";
			//}else{
				//showScreen();
			//}	
		}

		function setOnLoadValue(){	
			var fieldId="hidConsumerType"+currentPage; 
			var fieldValue=document.getElementById(fieldId).value;
			if(fieldValue!='METERED'){
				disableFieldsOnConsumerType();
			}
			fieldId="hidMeterStatus"+currentPage; 
			fieldValue=document.getElementById(fieldId).value;
			fieldId="meterReadStatus"+currentPage; 
			document.getElementById(fieldId).value=Trim(fieldValue);//alert('meterReadStatus>>'+fieldValue);
			fieldId="hidConStatus"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			//alert(fieldValue);
			fieldId="consumerStatus"+currentPage; 
			//fieldValue=Trim(document.getElementById(fieldId).value);
			//alert(fieldValue);
			var spanID="popup"+currentPage;
			if(document.getElementById(spanID)){
				document.getElementById(spanID).innerHTML="";
			}
			if(document.getElementById('popupMTR')){
				  document.getElementById('popupMTR').innerHTML="";
			}
			if(fieldValue=='15' ||fieldValue=='20'||fieldValue=='30'||fieldValue=='40'||fieldValue=='50'||fieldValue=='90'||fieldValue=='0'){
				document.getElementById(fieldId).value=Trim(fieldValue);				
				disableFields(document.getElementById(fieldId));
				spanID="popup"+currentPage;
				//alert(spanID+'<>'+document.getElementById(spanID));
				if(fieldValue=='30'){
					if(document.getElementById(spanID)){
						//document.getElementById(spanID).innerHTML="<a href='#' onclick='clearMRMessage();popupMRScreen();'>Add Details</a>";
					}else
						if(document.getElementById('popupMTR')){
							
							  //document.getElementById('popupMTR').innerHTML="<a href='#' onclick='popUpMeterReplacementScreen(parseInt("+document.getElementById('seqNO1').value+")+1,\""+document.getElementById('billRound1').value+"\",\""+document.getElementById('kno1').value+"\");'>Add Details</a>";
						  }
				}else if(fieldValue=='15'){//====================Added for Remarks on 16-11-2012 by Tuhin 	
						var fieldId_remarks="remarks"+currentPage;
						//document.getElementById(fieldId_remarks).value="";
						fieldId_remarks="remarksDiv1"+currentPage;
						document.getElementById(fieldId_remarks).style.display = 'block';	
						fieldId_remarks="remarksDiv2"+currentPage;
						document.getElementById(fieldId_remarks).style.display = 'block';					
				}else{
					var fieldId_remarks="remarks"+currentPage;
					document.getElementById(fieldId_remarks).value="";
					fieldId_remarks="remarksDiv1"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'none';
					fieldId_remarks="remarksDiv2"+currentPage;
					document.getElementById(fieldId_remarks).style.display = 'none';
					if(document.getElementById(spanID)){
						document.getElementById(spanID).innerHTML="";
					}
				}
			}
			else{
				document.getElementById(fieldId).value="60";
			}
			fieldId="consumerStatus"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			if('30'!=fieldValue){
				fnCheckMeterReadStatus();
			}
			fnSetReadType();
			fieldId="consumerStatus"+currentPage;
			document.getElementById(fieldId).focus();	

		}


		function fnSetFocusMM(obj){
			var fValue=Trim(obj.value);
			if(fValue.length==2){
				var fieldId="meterReadMM"+currentPage;
				document.getElementById(fieldId).focus();
			}
		}
		function fnSetFocusDDConfirm(obj){
			var fValue=Trim(obj.value);
			if(fValue.length==2){
				var fieldId="meterReadDDConfirm"+currentPage;
				document.getElementById(fieldId).focus();
			}
		}
		function fnSetFocusMMConfirm(obj){
			var fValue=Trim(obj.value);
			if(fValue.length==2){
				var fieldId="meterReadMMConfirm"+currentPage;
				document.getElementById(fieldId).focus();
			}
		}
		function fnSetFocusMeterReadStatus(obj){
			var fValue=Trim(obj.value);
			if(fValue.length==2){
				var fieldId="meterReadStatus"+currentPage;
				if(document.getElementById(fieldId).disabled==false){
					document.getElementById(fieldId).focus();
				}else{
					fieldId="newAverageConsumption"+currentPage;
					document.getElementById(fieldId).focus();
				}
			}
		}


		function fnCheckMeterReadStatus(){
			var meterReadStatusId="meterReadStatus"+currentPage;
			var meterReadStatus=Trim(document.getElementById(meterReadStatusId).value);
			//3. Current Meter Reading field should only be enabled for (OK, PUMP).
			if(meterReadStatus=='OK' || meterReadStatus=='PUMP'){ 	 											
				var fieldId="currentMeterReadConfirm"+currentPage;
				document.getElementById(fieldId).value="";
				document.getElementById(fieldId).disabled=false; 
				fieldId="currentMeterRead"+currentPage;
				//document.getElementById(fieldId).value="";
				document.getElementById(fieldId).disabled=false; 
				//document.getElementById(fieldId).focus(); 
			}else{
				if(!(meterReadStatus=='NUW' || meterReadStatus=='PLUG'|| meterReadStatus=='DEM')){
				var fieldId="currentMeterRead"+currentPage;
				document.getElementById(fieldId).value="";
				document.getElementById(fieldId).disabled=true; 								
				fieldId="currentMeterReadConfirm"+currentPage;
				document.getElementById(fieldId).value="";
				document.getElementById(fieldId).disabled=true; 
				}
			}
			/*Start:Commented by Matiur Rahman on 14-08-2013 as per DJB-1792*/
			//4. In case of (NUW, PLUG, DEM) Current Meter Reading should always be equal to Previous Meter Reading.
			/*if(meterReadStatus=='NUW' || meterReadStatus=='PLUG'|| meterReadStatus=='DEM'){
				//var fieldId="hidPrevMeterRead"+currentPage;
				var fieldId="hidPrevActMeterRead"+currentPage;//Changed by Previous Actual Registered Meter Read on 19-11-2012 as per discussion with with Ayan/Sabyasachi
				var prevMeterRead=Trim(document.getElementById(fieldId).value);		
				if(prevMeterRead!='NA'){
					fieldId="currentMeterRead"+currentPage;
					document.getElementById(fieldId).value=prevMeterRead;
					document.getElementById(fieldId).disabled=true; 
					fieldId="currentMeterReadConfirm"+currentPage;
					document.getElementById(fieldId).value=prevMeterRead;
					document.getElementById(fieldId).disabled=true; 
					toUpdateCurrentMeterReadConfirm=prevMeterRead;
					toUpdateCurrentMeterRead=prevMeterRead;
				}
			}*/
			/*End:Commented by Matiur Rahman on 14-08-2013 as per DJB-1792*/
			/*Start:Added by Matiur Rahman on 14-08-2013 as per DJB-1792*/			
			/*if(meterReadStatus=='NUW' || meterReadStatus=='PLUG'|| meterReadStatus=='DEM'){
				//var fieldId="hidPrevMeterRead"+currentPage;
				var fieldId="hidPrevActMeterRead"+currentPage;//Changed by Previous Actual Registered Meter Read on 19-11-2012 as per discussion with with Ayan/Sabyasachi
				var prevMeterRead=Trim(document.getElementById(fieldId).value);		
				if(prevMeterRead!='NA'){
					fieldId="currentMeterRead"+currentPage;
					document.getElementById(fieldId).value=prevMeterRead;
					document.getElementById(fieldId).disabled=false; 
					fieldId="currentMeterReadConfirm"+currentPage;
					document.getElementById(fieldId).value=prevMeterRead;
					document.getElementById(fieldId).disabled=false; 
					toUpdateCurrentMeterReadConfirm=prevMeterRead;
					toUpdateCurrentMeterRead=prevMeterRead;
				}
			}*/
			/*End:Added by Matiur Rahman on 14-08-2013 as per DJB-1792*/
			/*Start:Added by Matiur Rahman as per JTrac #DJB-873 commented by Amit on 2013-01-18 16:48:59*/			
			/*var prevMeterReadStatusId="hidPrevMeterStatus"+currentPage;
			var prevMeterReadStatus=Trim(document.getElementById(prevMeterReadStatusId).value);
			var currReadType=fnGetReadType(meterReadStatus);
			var prevReadType=fnGetReadType(prevMeterReadStatus);
			//alert(meterReadStatus+'<>'+prevMeterReadStatus+'<>'+currReadType+'<>'+prevReadType);
			if(prevReadType=='Average Billing' && currReadType=='Volumetric Billing'){
				alert('As Previous Meter Read Remark is of Average Read Type and Current Meter Read Remark is of Regular Read Type\nSo,No emtimated meter read will be created\nThis is a case of meter replacement\nPlease Enter Meter Replacement Details.');
				var fieldId="consumerStatus"+currentPage;			
				document.getElementById(fieldId).value="30";
				setAll();
			}*/
			/*End:Added by Matiur Rahman as per JTrac #DJB-873 commented by Amit on 2013-01-18 16:48:59*/			
		}	
		
		function fnGetReadType(meterReaderRemarkCode){
			var readType="No Billing";
			if(Trim(meterReaderRemarkCode).length!=0){
				if(regularReadType.indexOf(meterReaderRemarkCode)>-1){
					readType="Volumetric Billing";
					return readType;
				}
				if(averageReadType.indexOf(meterReaderRemarkCode)>-1){
					readType="Average Billing";
					return readType;
				}
				if(provisionalReadType.indexOf(meterReaderRemarkCode)>-1){
					readType="Provisional Billing";
					return readType;
				}
				if(noBillingReadType.indexOf(meterReaderRemarkCode)>-1){
					readType="No Billing";
					return readType;
				}
			}
			return readType;
		}
		function fnSetReadType()
		{
			var meterReadStatusId="meterReadStatus"+currentPage;
			var meterReadStatus=Trim(document.getElementById(meterReadStatusId).value);
			var readType=fnGetReadType(meterReadStatus);
			var meterReadTypeSpanId="meterReadTypeSpan"+currentPage;
			var billTypeSpanId="billTypeSpan"+currentPage;	
			var currentMeterReadId="currentMeterRead"+currentPage;			
			var currentMeterReadConfirmId="currentMeterReadConfirm"+currentPage;	
			var consumerTypeId="hidConsumerType"+currentPage;
			var consumerType=Trim(document.getElementById(consumerTypeId).value);	
			document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
			document.getElementById(billTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";	
			if(readType=='Volumetric Billing'){
				var consumerStatusId="consumerStatus"+currentPage;
				var consumerStatus=Trim(document.getElementById(consumerStatusId).value);
				if(consumerStatus=='60' && consumerType=='METERED'&& (meterReadStatus=='OK'||meterReadStatus=='PUMP')){
					document.getElementById(currentMeterReadId).disabled=false;
					document.getElementById(currentMeterReadConfirmId).disabled=false;
				}else{
					if(!(meterReadStatus=='NUW' || meterReadStatus=='PLUG'|| meterReadStatus=='DEM')){
					document.getElementById(currentMeterReadId).disabled=true;				
					document.getElementById(currentMeterReadConfirmId).disabled=true;
					}
				}				
				document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='green'><b>Regular</b></font>";
				document.getElementById(billTypeSpanId).innerHTML = "<font color='green'><b>Volumetric Billing</b></font>";
			}else{
				var consumerStatusId="consumerStatus"+currentPage;
				var consumerStatus=Trim(document.getElementById(consumerStatusId).value);
				//alert(consumerStatus+'<>'+meterReadStatus);
				if(consumerStatus=='60' && consumerType=='METERED' && meterReadStatus!=''){					
					document.getElementById(currentMeterReadId).value="0";
					toUpdateCurrentMeterRead="0";
					document.getElementById(currentMeterReadConfirmId).value="0";
					document.getElementById(currentMeterReadId).disabled=true;				
					document.getElementById(currentMeterReadConfirmId).disabled=true;
					document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='red'><b>No Read</b></font>";
					document.getElementById(billTypeSpanId).innerHTML = "<font color='red'><b>"+readType+"</b></font>";
				}
				if(consumerType!='METERED' ||meterReadStatus==''){
					document.getElementById(currentMeterReadId).disabled=true;				
					document.getElementById(currentMeterReadConfirmId).disabled=true;
					document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
					document.getElementById(billTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
				}
			}
		}
		//Data Entry Validations
		function validate(){
 	 		//alert('in validate()');
			var textValue;	
			var textConfirm;
			var fieldId;
			var fieldValue;
			var hidFieldId="hidMeterReadDate"+currentPage;
			var hidFieldValue=Trim(document.getElementById(hidFieldId).value);
			var ddId="meterReadDD"+currentPage;
			var mmId="meterReadMM"+currentPage;
			var yyyyId="meterReadYYYY"+currentPage;
			document.getElementById(ddId).value=Trim(document.getElementById(ddId).value);
			document.getElementById(mmId).value=Trim(document.getElementById(mmId).value);
			var meterReadDate=document.getElementById(ddId).value+"/"+document.getElementById(mmId).value+"/"+document.getElementById(yyyyId).value;

			if(meterReadDate.length!=10){
				alert('Meter Read Date/Bill Generation Date is Mandatory.');
				toUpdateMeterReadDate="";
				toUpdateMeterReadDateConfirm="";
				document.getElementById(ddId).focus();
				isValid=false;
				return false;
			}
			//Validation: Future Date is not Allowed !.
			if(compareDates(todaysDate,toUpdateMeterReadDate) && todaysDate!=toUpdateMeterReadDate){
				alert('Future Date is not Allowed !.');
				isValid=false;
				resetMeterReadDate();
				return false;
			}
			var prevMeterReadDateId="hidPrevMeterReadDate"+currentPage;
			var prevMeterReadDate=Trim(document.getElementById(prevMeterReadDateId).value);
			//prevMeterReadDate="05/05/2012";
			//Validation: 1. Current Meter Reading Date should always be greater than Previous Meter Reading Date.
			//Year by passed. Read from Property file as PREV_METER_READ_INGNORED_YEAR
			if(meterReadDate.length==10 && prevMeterReadDate.length==10 && prevMeterReadDate.indexOf(PREV_METER_READ_INGNORED_YEAR)==-1){
				if(compareDates(toUpdateMeterReadDate,prevMeterReadDate)){
					alert('Current Meter Reading Date should always be greater than Previous Meter Reading Date.');
					isValid=false;
					resetMeterReadDate();
					return false;
				}
			}
			if(hidFieldValue!=toUpdateMeterReadDate && toUpdateMeterReadDate.length==10 && toUpdateMeterReadDateConfirm.length!=10){
				alert('Re-enter Meter Read Date/Bill Generation Date.');
				toUpdateMeterReadDateConfirm="";
				fieldId="meterReadDDConfirm"+currentPage;
				document.getElementById(fieldId).focus();
				isValid=false;
				return false;
			}
			fieldId="hidPrevActMeterRead"+currentPage;
			var prevActMeterRead=Trim(document.getElementById(fieldId).value);
			fieldId="currentMeterRead"+currentPage;
			fieldValue=Trim(document.getElementById(fieldId).value);
			textValue=Trim(fieldValue);	
			var meterReadStatusId="meterReadStatus"+currentPage;
			var meterReadStatus=Trim(document.getElementById(meterReadStatusId).value);
			textValue=textValue.length==0?"0":textValue;
			//alert(meterReadStatus+'<>'+textValue+'<>'+prevActMeterRead);
			//Validation: 2. Current Meter Reading should always be greater than Previous Meter Reading in case of Regular Read Type. 
			if((meterReadStatus=='OK' ||meterReadStatus=='PUMP') && prevActMeterRead!='' && prevActMeterRead!='NA' && textValue !='' && parseFloat(textValue)<=parseFloat(prevActMeterRead) ){
				alert('Current Meter Reading should always be greater than Previous Meter Reading in case of Regular Read Type.');
				isValid=false;
				document.getElementById(fieldId).focus();
				return false;					
			}
			hidFieldId="hidCurrentMeterRead"+currentPage;
			hidFieldValue=Trim(document.getElementById(hidFieldId).value);
			//alert(parseInt(hidFieldValue)+'<>'+toUpdateCurrentMeterRead);					
			if(hidFieldValue!=toUpdateCurrentMeterRead.toString()){				
				fieldId="currentMeterReadConfirm"+currentPage;
				fieldValue=Trim(document.getElementById(fieldId).value);
				textConfirm=Trim(fieldValue);				
				toUpdateCurrentMeterReadConfirm=textConfirm;			
				var confirmId="confirmCurrentMeterRead"+currentPage;
				if(textValue!='' && textConfirm==''){
					document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";
					alert('Re-enter Current Meter Read.');
					isValid=false;
					document.getElementById(fieldId).focus();
					return false;
				}else if(textValue.length!=0 && textConfirm.length!=0 && parseFloat(textValue)!=parseFloat(textConfirm)){					
					document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";
					alert('Re-enter Current Meter Read.');
					isValid=false;
					document.getElementById(fieldId).focus();
					return false;		
				}
				else if(textValue.length!=0 && textConfirm.length!=0){
					document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
					toUpdateCurrentMeterReadConfirm=textConfirm;
					toUpdateCurrentMeterRead=textValue;
				}			
			}
			//alert(hidFieldValue+'<21>'+toUpdateNewAvgConsumption);
			hidFieldId="hidNewAverageConsumption"+currentPage;
			hidFieldValue=Trim(document.getElementById(hidFieldId).value);
			/*if(hidFieldValue==''){
				hidFieldValue="0";
			}*/
			if(hidFieldValue!=toUpdateNewAvgConsumption.toString()){
				//alert(hidFieldValue);			
				fieldId="newAverageConsumption"+currentPage;
				fieldValue=Trim(document.getElementById(fieldId).value);
				textValue=fieldValue;	
				fieldId="newAverageConsumptionConfirm"+currentPage;
				fieldValue=Trim(document.getElementById(fieldId).value);
				textConfirm=fieldValue;			
				toUpdateNewAvgConsumptionConfirm=textConfirm;			
				var confirmId="confirmNewAverageConsumption"+currentPage;
				if(textValue!='' && textConfirm==''){
					//alert(hidFieldValue+'<3>'+toUpdateNewAvgConsumption);
					document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";
					alert('Re-enter New Average Consumption .');
					isValid=false;
					document.getElementById(fieldId).focus();
					return false;
				}else if(textValue.length!=0 && textConfirm.length!=0 && parseFloat(textValue)!=parseFloat(textConfirm)){
					//alert(hidFieldValue+'<4>'+toUpdateNewAvgConsumption);							
					document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";					
					toUpdateNewAvgConsumptionConfirm="";
					alert('Re-enter New Average Consumption .');
					isValid=false;
					document.getElementById(fieldId).focus();
					return false;							
				}else if(textValue.length!=0 && textConfirm.length!=0){
					document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
					toUpdateNewAvgConsumptionConfirm=textConfirm;
					toUpdateNewAvgConsumption=textValue;
				}
			}
			//Added for new No of Floors on 15-11-2012 by Matiur Rahman 
			hidFieldId="hidNewNoOfFloor"+currentPage;
			hidFieldValue=Trim(document.getElementById(hidFieldId).value);			
			if(hidFieldValue!=toUpdateNewNoOfFloor.toString()){
				//alert(hidFieldValue);			
				fieldId="newNoOfFloor"+currentPage;
				fieldValue=Trim(document.getElementById(fieldId).value);
				textValue=fieldValue;	
				fieldId="newNoOfFloorConfirm"+currentPage;
				fieldValue=Trim(document.getElementById(fieldId).value);
				textConfirm=fieldValue;			
				toUpdateNewNoOfFloorConfirm=textConfirm;			
				var confirmId="confirmNewNoOfFloorConfirm"+currentPage;
				if(textValue!='' && textConfirm==''){
					//alert(hidFieldValue+'<3>'+toUpdateNewNoOfFloor);
					document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";
					alert('Re-enter No Of Floors.');
					isValid=false;
					document.getElementById(fieldId).focus();
					return false;
				}else if(textValue.length!=0 && textConfirm.length!=0 && parseInt(textValue)!=parseInt(textConfirm)){
					//alert(hidFieldValue+'<4>'+toUpdateNewNoOfFloor);							
					document.getElementById(confirmId).innerHTML="<font color='red'>Does Not Match.</font>";					
					toUpdateNewNoOfFloorConfirm="";
					alert('Re-enter No Of Floors.');
					isValid=false;
					document.getElementById(fieldId).focus();
					return false;							
				}else if(textValue.length!=0 && textConfirm.length!=0){
					document.getElementById(confirmId).innerHTML="<font color='green'><b>OK</b></font>";
					toUpdateNewNoOfFloorConfirm=textConfirm;
					toUpdateNewNoOfFloor=textValue;
				}
			}
			//=========================================================
			if((toUpdateNewAvgConsumption!='' || toUpdateCurrentMeterRead!='') && toUpdateMeterReadDate.length!=10 ){
				alert('Meter Read Date/Bill Generation Date is Mandatory .');
				fieldId="meterReadDD"+currentPage;		
				isValid=false;
				document.getElementById(fieldId).focus();
				return false;
			}
			fieldId="hidConsumerType"+currentPage;
			var hidConsumerType=Trim(document.getElementById(fieldId).value);
			fieldId="consumerStatus"+currentPage;
			var consumerStatus=Trim(document.getElementById(fieldId).value);
			fieldId="meterReadStatus"+currentPage;
			var meterReadStatus=Trim(document.getElementById(fieldId).value);
			//alert('ConsumerType>>'+hidConsumerType+'>>consumerStatus>>'+consumerStatus+'>>meterReadStatus>>'+meterReadStatus+'<<>>'+(hidConsumerType=='METERED' && consumerStatus=='60' && meterReadStatus==''));
			if(toUpdateMeterReadDate.length==10 && hidConsumerType=='METERED' && consumerStatus=='60' && meterReadStatus==''){
				alert('For Metered Consumer Meter Read Status is Mandatory .');
				document.getElementById(fieldId).focus();
				isValid=false;
				return false;
			}
			var conCatId="hidConCat"+currentPage;
			var conCat=Trim(document.getElementById(conCatId).value);
			//Validation: Avg Consumption should be mandatory for No Read if consumer is not domestic. Otherwise system will not esimate correctly and Bill will generate in Pending state. 
			//As per Ayan's requirement
			//Commenter after Discussion with Rajesh Da
			/*if(conCat!='CAT I' && !(meterReadStatus=='OK' ||meterReadStatus=='PUMP'||meterReadStatus=='NUW' || meterReadStatus=='PLUG'|| meterReadStatus=='DEM') && toUpdateNewAvgConsumption==''){
				alert('For Non domestic Consumer and No Read, New Average Consumption is Mandatory .');
				fieldId="newAverageConsumption"+currentPage;
				document.getElementById(fieldId).focus();
				isValid=false;
				return false;
			}*/
			
			return true;
		}
		function clearMRMessage(){
			if(document.getElementById('mrMessage')){
				document.getElementById('mrMessage').innerHTML="<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			}
		}