	function popupMRScreen(){
		if(null!=document.getElementById('mrbox') && 'null'!=document.getElementById('mrbox')){
			var thediv=document.getElementById('mrbox');
			thediv.style.display = "block";
			document.getElementById('meterInstalDD').focus();
			//document.body.style.overflow = 'hidden';			
		}
	}
	function hideMRScreen(){
		if(null!=document.getElementById('mrbox') && 'null'!=document.getElementById('mrbox')){
			var thediv=document.getElementById('mrbox');		
			thediv.style.display = "none";
			//document.body.style.overflow = 'auto';
		}
		
	}
	function fnCancel(){
		
		hideMRScreen();
	}
	function fnSaveDetails(){
		//doMeterReplacementAjaxCall();
	}
	function fnResetDetails(){
		document.getElementById('meterInstalDD').value="";
		document.getElementById('meterInstalMM').value="";
		document.getElementById('meterInstalYYYY').value="";
		document.getElementById('meterNo').value="";
		document.getElementById('startMeterRead').value="";
		document.getElementById('meterType').value="";
		document.getElementById('isLNTServiceProvider').value="";
		document.getElementById('modelNo').value="";
		document.getElementById('digitLeft').value="";
		document.getElementById('digitRight').value="";
		document.getElementById('fullScale').value="";
	}
	
	function doMeterReplacementAjaxCall(){
		isValid=false;
		var url= "callMeterReplacementAJax.action?seqNo="+toUpdateSeqNO+"&kno="+toUpdateKNO+"&billRound="+toUpdateBillRound;
		var meterInstalDD=Trim(document.getElementById('meterInstalDD').value);
		if(meterInstalDD.length==0){
			document.getElementById('meterInstalDD').value="";
			document.getElementById('meterInstalDD').focus();
			return;
		}
		var meterInstalMM=Trim(document.getElementById('meterInstalMM').value);
		if(meterInstalMM.length==0){
			document.getElementById('meterInstalMM').value="";
			document.getElementById('meterInstalMM').focus();
			return;
		}
		var meterInstalYYYY=Trim(document.getElementById('meterInstalYYYY').value);
		if(meterInstalYYYY.length==0){
			document.getElementById('meterInstalYYYY').value="";
			document.getElementById('meterInstalYYYY').focus();
			return;
		}
		var meterInstalDate=meterInstalDD+"/"+meterInstalMM+"/"+meterInstalYYYY;
		var errorMsg=getDateErrorMessageString(meterInstalDate);
		if(errorMsg!='none'){
			alert('Please Enter a valid Date.');
			document.getElementById('meterInstalDD').value="";
			document.getElementById('meterInstalMM').value="";
			document.getElementById('meterInstalYYYY').value="";
			document.getElementById('meterInstalDD').focus();
			return;
		}
		if(compareDates(todaysDate,meterInstalDate) && todaysDate!=meterInstalDate){
			alert('Future Date is not Allowed !.');
			document.getElementById('meterInstalDD').value="";
			document.getElementById('meterInstalMM').value="";
			document.getElementById('meterInstalYYYY').value="";
			document.getElementById('meterInstalDD').focus();
			return;
		}
		url=url+"&meterInstallationDate="+meterInstalDate;
		var meterNo=Trim(document.getElementById('meterNo').value);
		if(meterNo.length==0){
			document.getElementById('meterNo').value="";
			document.getElementById('meterNo').focus();
			return;
		}else{
			url=url+"&meterNo="+meterNo;
		}
		var startMeterRead=Trim(document.getElementById('startMeterRead').value);
		if(startMeterRead.length==0){
			document.getElementById('startMeterRead').value="";
			document.getElementById('startMeterRead').focus();
			return;
		}else{
			url=url+"&startMeterRead="+startMeterRead;
		}
		var meterType=Trim(document.getElementById('meterType').value);
		if(meterType.length==0){
			document.getElementById('meterType').value="";
			document.getElementById('meterType').focus();
			return;
		}else{
			url=url+"&meterType="+meterType;
		}
		var isLNTServiceProvider=Trim(document.getElementById('isLNTServiceProvider').value);
		if(isLNTServiceProvider.length==0){
			document.getElementById('isLNTServiceProvider').value="";
			document.getElementById('isLNTServiceProvider').focus();
			return;
		}else{
			url=url+"&isLNTServiceProvider="+isLNTServiceProvider;
		}
		var modelNo=Trim(document.getElementById('modelNo').value);
		if(modelNo.length==0){
			document.getElementById('modelNo').value="";
			document.getElementById('modelNo').focus();
			return;
		}else{
			url=url+"&modelNo="+modelNo;
		}
		var digitLeft=Trim(document.getElementById('digitLeft').value);
		if(digitLeft.length==0){
			document.getElementById('digitLeft').value="";
			document.getElementById('digitLeft').focus();
			return;
		}else{
			url=url+"&digitLeft="+digitLeft;
		}
		var digitRight=Trim(document.getElementById('digitRight').value);
		if(digitRight.length==0){
			document.getElementById('digitRight').value="";
			document.getElementById('digitRight').focus();
			return;
		}else{
			url=url+"&digitRight="+digitRight;
		}
		var fullScale=Trim(document.getElementById('fullScale').value);
		if(fullScale.length==0){
			document.getElementById('fullScale').value="";
			document.getElementById('fullScale').focus();
			return;
		}else{
			url=url+"&fullScale="+fullScale;
		}
		//+"&meterReadDate="+meterReadDate+"&meterReadStatus="+meterReadStatus+"&currentMeterRead="+currentMeterRead+"&newAvgConsumption="+newAvgConsumption+"&consumerStatus="+consumerStatus+"&freezeFlag="+freezeFlag;  
		httpObject=null;
		httpObject = getHTTPObjectForBrowser();  
		if (httpObject != null) {
			hideScreen();
			hideMRScreen();
			document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='green' size='2'>Data Submited</font></div>";
			//alert('url>>'+url);                 
			httpObject.open("GET", url, true);            
			httpObject.send(null);    
			httpObject.onreadystatechange = setMeterReplacementAjaxOutput;  
		}   
	}  
	// Set ajax-result div      
	function setMeterReplacementAjaxOutput(){
		if(httpObject.readyState == 4){
			var  responseString= httpObject.responseText;
			//alert(httpObject.responseText);
			if(null!=responseString && responseString.indexOf("ERROR:")>-1){			
				isValid=false;
				alert(httpObject.responseText);
				document.getElementById('ajax-result').innerHTML ="<div class='search-option'><font color='red' size='2'>Unable to process your last Request. Please Try again.</font></div>";
				showScreen(); 
				popupMRScreen();
			}else{
				showScreen(); 
				hideMRScreen();
				clearToUpdateVar();
				document.getElementById('ajax-result').innerHTML = httpObject.responseText; 
			}
			
			       
		}     
	}
	function fnSetmeterInstalMMFocus(obj){
		obj.value=Trim(obj.value);		
		if(obj.value.length==2){
			document.getElementById('meterInstalMM').focus();
		}
	}
	function fnSetmeterInstalYYYYFocus(obj){
		obj.value=Trim(obj.value);		
		if(obj.value.length==2){
			document.getElementById('meterInstalYYYY').focus();
		}
	}	
	function fnSetmeterInstalMeterNoFocus(obj){
		obj.value=Trim(obj.value);		
		if(obj.value.length==4){
			document.getElementById('meterNo').focus();
		}
	}	
	function fnMakeTwoDigit(obj){
		obj.value=Trim(obj.value);
		IsNumber(obj);
		obj.value=makeTwoDigit(obj.value);
	}