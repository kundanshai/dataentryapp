var isPopUp = false;
var lastKNO = "";
var meterInstalDate = "";
var zeroReadMR = "";
var currentMeterReadDateMR = "";
var currentMeterRegisterReadMR = "";
var newAverageConsumptionMR = "";
var oldMeterRegisterReadMR = "";
var oldMeterAverageReadMR = "";
var knoToResetMR = "";
var billRoundToResetMR = "";
var noOfFloorsMR = "";

var lastReadUpdateRemarks = "";
var lastMRUpdateRemarks = "";
var defaultZeroRead = "0", defaultCurrentRead = "0", defaultNewAverageConsumption = "0";
var meterInstalDateToGetPrevReadDetails="",knoToGetPrevReadDetails="";
function fnProcessGetConsumerDetailsResponse(caller, responseString) {
	// alert(responseString);
	lastReadUpdateRemarks = "";
	lastMRUpdateRemarks = "";
	//change added by Rajib as per Jtrac DJB-2024
	//alert("response String:"+responseString);
	var alertString="Zone Access Denied";
	if(alertString == responseString){
		//alert("Inside if");
		document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'><b>Access Denied!You mayn't have access to the Zone where this KNO belongs to.</b></font>";
		document.getElementById('mrMessage').title = "You mayn't have access to the Zone where this KNO belongs to";
		showScreen();
		document.body.style.overflow = 'auto';
	}
	else
	{
		if (null == responseString || responseString.indexOf("ERROR:") > -1
			|| responseString.length > 1000) {
		document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'><b>No Details found for Selected Bill Round, Zone, MR, Area & WC No to Proceed. Contact System Administrator.</b></font>";
		document.getElementById('mrMessage').title = "This is may be due to MRD being not Populated for the Bill Round. You may Contact System Administrator to Get it Checked.";
		if (!isPopUp) {
			showScreen();
			document.body.style.overflow = 'auto';
		}
		document.body.style.overflow = 'auto';
		document.getElementById('zoneMR').value = "";
		document.getElementById('zoneMR').disabled = false;
		document.getElementById("mrNoMR").value = "";
		document.getElementById('mrNoMR').disabled = false;
		document.getElementById("areaMR").value = "";
		document.getElementById('areaMR').disabled = false;
		document.getElementById("wcNoMR").value = "";
		document.getElementById('wcNoMR').disabled = false;
		document.getElementById("knoMR").value = "";
		document.getElementById('knoMR').disabled = false;
		document.getElementById("nameMR").value = "";
		document.getElementById("billRoundMR").value = "";
		document.getElementById("consumerTypeMR").value = "";
		document.getElementById("consumerCategoryMR").value = "";
		document.getElementById("consumerStatusMR").value = "";
		document.getElementById('saType').value = "";
		document.getElementById('saType').disabled = false;
		document.getElementById('saTypeID').title = "Select SA Type";
		document.getElementById('oldSATypeMR').value = "";
		document.getElementById("oldMeterAverageReadMR").value = "";
		document.getElementById("oldMeterAverageReadConfirmMR").value = "";
		document.getElementById("currentAverageConsumptionMR").value = "";
		document.getElementById("lastActiveMeterInstalDate").value = "";
		document.getElementById("lastActiveMeterRemarkCode").value = "";
		document.getElementById("lastActiveMeterRead").value = "";
		document.getElementById("oldSATypeMR").value = "";
		document.getElementById("oldSAStartDate").value = "";
	} else {
		if (!isPopUp) {
			showScreen();
			document.body.style.overflow = 'auto';
		}
		if (isPopUp) {
			displayMRScreen();
			isPopUp = true;
		}
		if ('fnGetConsumerDetailsByZMAW' == caller) {
			var knoString = responseString.substring(responseString
					.indexOf("<CKNO>") + 6, responseString.indexOf("</CKNO>"));
			// alert(knoString);
			if (null != knoString && 'null' != knoString
					&& 'undefined' != knoString && '' != knoString) {
				document.getElementById('knoMR').value = knoString;
				document.getElementById('knoMR').disabled = true;
			}
		} else {
			var zoneString = responseString.substring(responseString
					.indexOf("<ZONE>") + 6, responseString.indexOf("</ZONE>"));
			if (null != zoneString && 'null' != zoneString
					&& 'undefined' != zoneString && '' != zoneString) {
				document.getElementById('zoneMR').value = zoneString;
				document.getElementById('zoneMR').disabled = true;
			} else {
				document.getElementById('zoneMR').value = "";
				document.getElementById('zoneMR').disabled = false;
			}
			// alert(responseString);
			var mrNoString = responseString.substring(responseString
					.indexOf("<MRNO>") + 6, responseString.indexOf("</MRNO>"));
			// alert(mrNoString);
			if (null != mrNoString && 'null' != mrNoString
					&& 'undefined' != mrNoString && '' != mrNoString) {
				var select = document.getElementById("mrNoMR");
				select.options[select.options.length] = new Option(mrNoString,
						mrNoString);
				document.getElementById("mrNoMR").value = mrNoString;
				document.getElementById('mrNoMR').disabled = true;
			} else {
				document.getElementById('mrNoMR').value = "";
				document.getElementById('mrNoMR').disabled = false;
			}
			var areaString = responseString.substring(responseString
					.indexOf("<AREA>") + 6, responseString.indexOf("</AREA>"));
			// alert(areaString);
			if (null != areaString && 'null' != areaString
					&& 'undefined' != areaString && '' != areaString) {
				var select = document.getElementById("areaMR");
				select.options[select.options.length] = new Option(areaString,
						areaString);
				document.getElementById("areaMR").value = areaString;
				document.getElementById('areaMR').disabled = true;
			} else {
				document.getElementById('areaMR').value = "";
				document.getElementById('areaMR').disabled = false;
			}
			var wcNoString = responseString.substring(responseString
					.indexOf("<WCNO>") + 6, responseString.indexOf("</WCNO>"));
			// alert(wcNoString);
			if (null != wcNoString && 'null' != wcNoString
					&& 'undefined' != wcNoString && '' != wcNoString) {
				document.getElementById("wcNoMR").value = wcNoString;
				document.getElementById('wcNoMR').disabled = true;
			} else {
				document.getElementById('wcNoMR').value = "";
				document.getElementById('wcNoMR').disabled = false;
			}
		}
		var billRoundString = responseString.substring(responseString
				.indexOf("<CBIR>") + 6, responseString.indexOf("</CBIR>"));
		// alert(nameString);
		if (null != billRoundString && 'null' != billRoundString
				&& 'undefined' != billRoundString && '' != billRoundString) {
			document.getElementById("billRoundMR").value = billRoundString;
			document.getElementById('billRoundMR').disabled = true;
		}
		var nameString = responseString.substring(responseString
				.indexOf("<NAME>") + 6, responseString.indexOf("</NAME>"));
		// alert(nameString);
		if (null != nameString && 'null' != nameString
				&& 'undefined' != nameString && '' != nameString) {
			document.getElementById("nameMR").value = nameString;
			document.getElementById('nameMR').disabled = true;
		}
		var oldSATypeMRString = responseString.substring(responseString
				.indexOf("<SATP>") + 6, responseString.indexOf("</SATP>"));
		if (null != oldSATypeMRString && 'null' != oldSATypeMRString
				&& 'undefined' != oldSATypeMRString && '' != oldSATypeMRString) {
			document.getElementById("oldSATypeMR").value = oldSATypeMRString;
			document.getElementById("oldSATypeMR").title = oldSATypeMRString;
		} else {
			document.getElementById("oldSATypeMR").value = "NA";
			document.getElementById("oldSATypeMR").title = "";
		}
		var consumerTypeString = responseString.substring(responseString
				.indexOf("<CTYP>") + 6, responseString.indexOf("</CTYP>"));
		// alert(consumerTypeString);
		var consumerTypeTitle = "Consumer Type is:\t\t\t";
		document.getElementById("consumerTypeMR").title = "";
		if (null != consumerTypeString && 'null' != consumerTypeString
				&& 'undefined' != consumerTypeString
				&& '' != consumerTypeString) {
			document.getElementById("consumerTypeMR").value = Trim(consumerTypeString);
			consumerTypeTitle += "\n" + Trim(consumerTypeString);
			if (document.getElementById('consumerTypeMR').value == 'METERED') {
				document.getElementById('saType').value = "NA";
				document.getElementById('saType').disabled = true;
				document.getElementById('saTypeID').title = "Only applicable for Unmetered Consumer";
			} else {
				document.getElementById('saType').value = "SAWATSEW";
				document.getElementById('saType').disabled = false;
				document.getElementById('saTypeID').title = "Select SA Type";
			}
		}
		var consumerCategoryMR = responseString.substring(responseString
				.indexOf("<CCAT>") + 6, responseString.indexOf("</CCAT>"));
		document.getElementById("consumerCategoryMR").value = Trim(consumerCategoryMR);
		consumerTypeTitle += "\n" + Trim(consumerCategoryMR);
		// Added on 14-12-2012 by Matiur Rahman
		oldMeterAverageReadMR = responseString.substring(responseString
				.indexOf("<OAVG>") + 6, responseString.indexOf("</OAVG>"));
		if (null != oldMeterAverageReadMR && 'null' != oldMeterAverageReadMR
				&& 'undefined' != oldMeterAverageReadMR
				&& '' != oldMeterAverageReadMR) {
			document.getElementById("oldMeterAverageReadMR").value = Trim(oldMeterAverageReadMR);
			document.getElementById("oldMeterAverageReadConfirmMR").value = Trim(oldMeterAverageReadMR);
			document.getElementById("currentAverageConsumptionMR").value = Trim(oldMeterAverageReadMR);
		} else {
			document.getElementById("oldMeterAverageReadMR").value = "";
			document.getElementById("oldMeterAverageReadConfirmMR").value = "";
			document.getElementById("currentAverageConsumptionMR").value = "";
		}
		// Start:Added on 12-03-2013 by Matiur Rahman
		if (document.getElementById("oldMeterAverageReadMR").value != '') {
			document.getElementById("oldMeterAverageReadMR").disabled = true;
			document.getElementById("oldMeterAverageReadConfirmMR").disabled = true;
		} else {
			document.getElementById("oldMeterAverageReadMR").disabled = false;
			document.getElementById("oldMeterAverageReadConfirmMR").disabled = false;
		}
		var oldSATypeMR = responseString.substring(responseString
				.indexOf("<OSAT>") + 6, responseString.indexOf("</OSAT>"));
		if (null != oldSATypeMR && 'null' != oldSATypeMR
				&& 'undefined' != oldSATypeMR && '' != oldSATypeMR) {
			document.getElementById("oldSATypeMR").value = Trim(oldSATypeMR);
		} else {
			document.getElementById("oldSATypeMR").value = "";
		}
		var oldSAStartDate = responseString.substring(responseString
				.indexOf("<OSAD>") + 6, responseString.indexOf("</OSAD>"));
		if (null != oldSAStartDate && 'null' != oldSAStartDate
				&& 'undefined' != oldSAStartDate && '' != oldSAStartDate) {
			document.getElementById("oldSAStartDate").value = Trim(oldSAStartDate);
			document.getElementById("oldSAStartDate").title=Trim(oldSAStartDate);
		} else {
			document.getElementById("oldSAStartDate").value = "";
			document.getElementById("oldSAStartDate").title="Disconnected Consumer";
			consumerTypeTitle += "\nDisconnected";
		}
		// var
		// oldMeterInstalDate=responseString.substring(responseString.indexOf("<LAMD>")+6,responseString.indexOf("</LAMD>"));
		// document.getElementById("oldMeterInstalDate").value=Trim(oldMeterInstalDate);
		var lastActiveMeterInstalDate = responseString.substring(responseString
				.indexOf("<LAMD>") + 6, responseString.indexOf("</LAMD>"));
		if (null != lastActiveMeterInstalDate
				&& 'null' != lastActiveMeterInstalDate
				&& 'undefined' != lastActiveMeterInstalDate
				&& '' != lastActiveMeterInstalDate) {
			document.getElementById("lastActiveMeterInstalDate").value = Trim(lastActiveMeterInstalDate);
			document.getElementById("nextValidMeterInstalldate").value = shiftDate(
					document.getElementById("lastActiveMeterInstalDate").value,
					document.getElementById("meterInstalmDateRange").value);
		} else {
			document.getElementById("lastActiveMeterInstalDate").value = "NA";
			document.getElementById("nextValidMeterInstalldate").value = "NA";
		}
		var lastActiveMeterRead = responseString.substring(responseString
				.indexOf("<LAMR>") + 6, responseString.indexOf("</LAMR>"));
		if (null != lastActiveMeterRead && 'null' != lastActiveMeterRead
				&& 'undefined' != lastActiveMeterRead
				&& '' != lastActiveMeterRead) {
			document.getElementById("lastActiveMeterRead").value = Trim(lastActiveMeterRead);
		} else {
			document.getElementById("lastActiveMeterRead").value = "NA";
		}
		var lastActiveMeterRemarkCode = responseString.substring(responseString
				.indexOf("<LAMS>") + 6, responseString.indexOf("</LAMS>"));
		if (null != lastActiveMeterRemarkCode
				&& 'null' != lastActiveMeterRemarkCode
				&& 'undefined' != lastActiveMeterRemarkCode
				&& '' != lastActiveMeterRemarkCode) {
			document.getElementById("lastActiveMeterRemarkCode").value = Trim(lastActiveMeterRemarkCode);
		} else {
			document.getElementById("lastActiveMeterRemarkCode").value = "";
		}
		var currentReadDetails = responseString.substring(responseString
				.indexOf("<CRDT>") + 6, responseString.indexOf("</CRDT>"));
		// if(null!=currentReadDetails && 'null'!=currentReadDetails
		// &&'undefined'!=currentReadDetails &&''!=currentReadDetails){
		processCurrentReadDetails(currentReadDetails);
		// }
		var currentMeterReplacementDetails = responseString.substring(
				responseString.indexOf("<MRDT>") + 6, responseString
						.indexOf("</MRDT>"));
		// if(null!=currentMeterReplacementDetails &&
		// 'null'!=currentMeterReplacementDetails
		// &&'undefined'!=currentMeterReplacementDetails
		// &&''!=currentMeterReplacementDetails){
		processCurrentMeterReplacementDetails(currentMeterReplacementDetails);
		// }
		if (document.getElementById("consumerTypeMR").value == 'UNMETERED') {
			document.getElementById("oldMeterReadRemarkCode").disabled = true;
			oldMeterRegisterReadMR = "";
			document.getElementById("oldMeterRegisterReadMR").value = oldMeterRegisterReadMR;
			document.getElementById("oldMeterRegisterReadConfirmMR").value = oldMeterRegisterReadMR;
			document.getElementById("oldMeterRegisterReadMR").disabled = true;
			document.getElementById("oldMeterRegisterReadConfirmMR").disabled = true;
			document.getElementById("oldMeterAverageReadMR").disabled = true;
			document.getElementById("oldMeterAverageReadConfirmMR").disabled = true;
		} else {
			document.getElementById("oldMeterReadRemarkCode").disabled = false;
			fnCheckOldMeterReadStatusMROnLoad(document
					.getElementById('oldMeterReadRemarkCode'));
			if (document.getElementById("oldMeterAverageReadMR").value == '') {
				document.getElementById("oldMeterAverageReadMR").disabled = false;
				document.getElementById("oldMeterAverageReadConfirmMR").disabled = false;
			}
		}
		document.getElementById("consumerTypeMR").title = consumerTypeTitle;
		document.getElementById('mrMessage').innerHTML = "<font color='blue' size='2'><b>"
				+ lastReadUpdateRemarks + lastMRUpdateRemarks + "</b></font>";
		// End:Added on 12-03-2013 by Matiur Rahman
		if(lastReadUpdateRemarks.indexOf("65") > -1 ||lastReadUpdateRemarks.indexOf("70") > -1||lastReadUpdateRemarks.indexOf("130") > -1){
			disableForm(document.getElementById("MR"));
			enableSearchCriteria(caller);
		}else{
			enableForm(document.getElementById("MR"));
			enableSearchCriteria(caller);
			disableDefault(document.getElementById("MR"));
		}
		fnResetPreviousMeterReadDetails();
	}
	//change by rajib 
}
	
}
function disableForm(theform) {
	/*if (document.all || document.getElementById) {alert(theform);
		for (i = 0; i < theform.length; i++) {
		var formElement = theform.elements[i];alert(formElement.type);
			if (formElement.type=='input') {
				formElement.disabled = true;
			}
		}
	}*/
	$('#mrbox :input').attr('disabled', true);
	var titleMsg="----------------------------------------------------------------------------------------------------------------------------------------------------";
	titleMsg+="\n\n\n\n\n\nInfo: Record has been Already Processed !\n\n\n\n\n\n";
	titleMsg+="----------------------------------------------------------------------------------------------------------------------------------------------------";
	document.getElementById("mrbox").title=titleMsg;
	setStatusBarMessage('Info: Record has been Already Processed !');
	/*var inputs=document.getElementsByTagName('input');
	for(i=0;i<inputs.length;i++){
	    inputs[i].disabled=true;
	} 
	var selects=document.getElementsByTagName('select');
	for(i=0;i<selects.length;i++){
		selects[i].disabled=true;
	} */
}
function enableForm(theform) {
	/*if (document.all || document.getElementById) {
		for (i = 0; i < theform.length; i++) {
		var formElement = theform.elements[i];
			if (true) {
				formElement.disabled = true;
			}
		}
	}*/
	 $('#mrbox :input').removeAttr('disabled');
	 document.getElementById("mrbox").title="";
	 setStatusBarMessage('');
	/*var inputs=document.getElementsByTagName('input');
	for(i=0;i<inputs.length;i++){
	    inputs[i].disabled=false;
	} 
	var selects=document.getElementsByTagName('select');
	for(i=0;i<selects.length;i++){
		selects[i].disabled=false;
	} */
}
function disableDefault(theform) {
	document.getElementById('billRoundMR').disabled = true;
	document.getElementById('nameMR').disabled = true;
	document.getElementById('consumerTypeMR').disabled = true;
	document.getElementById('lastActiveMeterInstalDate').disabled = true;
	document.getElementById('lastActiveMeterRead').disabled = true;
	document.getElementById('lastActiveMeterRemarkCode').disabled = true;
	document.getElementById('zeroReadRemarkCode').disabled = true;
	document.getElementById('meterReplaceStageID').disabled = true;
	document.getElementById('currentAverageConsumptionMR').disabled = true;
	document.getElementById('oldSATypeMR').disabled = true;
	document.getElementById('oldSAStartDate').disabled = true;
	document.getElementById('oldMeterReadDate').disabled = true;
	document.getElementById('oldMeterAverageReadMR').disabled = true;
	if (document.getElementById('consumerTypeMR').value == 'METERED') {
		document.getElementById('saType').value = "NA";
		document.getElementById('saType').disabled = true;
		document.getElementById('saTypeID').title = "Only applicable for Unmetered Consumer";
	} else {
		document.getElementById('saType').value = "SAWATSEW";
		document.getElementById('saType').disabled = false;
		document.getElementById('saTypeID').title = "Select SA Type";
	}
	if(isPopUp){
		document.getElementById('knoMR').disabled = true;
	}
	if (document.getElementById("consumerTypeMR").value == 'UNMETERED') {
		document.getElementById("oldMeterReadRemarkCode").disabled = true;
		oldMeterRegisterReadMR = "";
		document.getElementById("oldMeterRegisterReadMR").value = oldMeterRegisterReadMR;
		document.getElementById("oldMeterRegisterReadConfirmMR").value = oldMeterRegisterReadMR;
		document.getElementById("oldMeterRegisterReadMR").disabled = true;
		document.getElementById("oldMeterRegisterReadConfirmMR").disabled = true;
		document.getElementById("oldMeterAverageReadMR").disabled = true;
		document.getElementById("oldMeterAverageReadConfirmMR").disabled = true;
	} else {
		document.getElementById("oldMeterReadRemarkCode").disabled = false;
		fnCheckOldMeterReadStatusMROnLoad(document
				.getElementById('oldMeterReadRemarkCode'));
		if (document.getElementById("oldMeterAverageReadMR").value == '') {
			document.getElementById("oldMeterAverageReadMR").disabled = false;
			document.getElementById("oldMeterAverageReadConfirmMR").disabled = false;
		}
	}
}
function enableSearchCriteria(caller){
	if ('fnGetConsumerDetailsByZMAW' == caller) {		
		document.getElementById('knoMR').disabled = true;
		document.getElementById('zoneMR').disabled = false;
		document.getElementById('mrNoMR').disabled = false;
		document.getElementById('areaMR').disabled = false;
		document.getElementById('wcNoMR').disabled = false;
	} else {
		document.getElementById('knoMR').disabled = false;
		document.getElementById('zoneMR').disabled = true;
		document.getElementById('mrNoMR').disabled = true;
		document.getElementById('areaMR').disabled = true;
		document.getElementById('wcNoMR').disabled = true;
	}
	if(document.getElementById('btnClose')){
		document.getElementById('btnClose').disabled = false;
	}
}

function processCurrentReadDetails(inputString) {
	var inputStringArray = inputString.split('|'); // split the string and
													// remove the last one
	// alert(inputString+'\n'+inputStringArray);
	var i = 0;
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		currentMeterReadDateMR = Trim(inputStringArray[i]);
		document.getElementById("currentMeterReadDateMR").value = currentMeterReadDateMR;
		document.getElementById("currentMeterReadDateConfirmMR").value = currentMeterReadDateMR;
	} else {
		currentMeterReadDateMR = "";
		document.getElementById("currentMeterReadDateMR").value = currentMeterReadDateMR;
		document.getElementById("currentMeterReadDateConfirmMR").value = currentMeterReadDateMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		currentMeterReadRemarkCode = Trim(inputStringArray[i]);
		document.getElementById("currentMeterReadRemarkCode").value = currentMeterReadRemarkCode;
	} else {
		currentMeterReadRemarkCode = "";
		document.getElementById("currentMeterReadRemarkCode").value = currentMeterReadRemarkCode;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		currentMeterRegisterReadMR = Trim(inputStringArray[i]);
		document.getElementById("currentMeterRegisterReadMR").value = currentMeterRegisterReadMR;
		document.getElementById("currentMeterRegisterReadConfirmMR").value = currentMeterRegisterReadMR;
	} else {
		currentMeterRegisterReadMR = "";
		document.getElementById("currentMeterRegisterReadMR").value = currentMeterRegisterReadMR;
		document.getElementById("currentMeterRegisterReadConfirmMR").value = currentMeterRegisterReadMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		currentAverageConsumptionMR = Trim(inputStringArray[i]);
		document.getElementById("currentAverageConsumptionMR").value = currentAverageConsumptionMR;
	} else {
		currentAverageConsumptionMR = "";
		document.getElementById("currentAverageConsumptionMR").value = currentAverageConsumptionMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		newAverageConsumptionMR = Trim(inputStringArray[i]);
		document.getElementById("newAverageConsumptionMR").value = newAverageConsumptionMR;
		document.getElementById("newAverageConsumptionConfirmMR").value = newAverageConsumptionMR;
	} else {
		newAverageConsumptionMR = "";
		document.getElementById("newAverageConsumptionMR").value = newAverageConsumptionMR;
		document.getElementById("newAverageConsumptionConfirmMR").value = newAverageConsumptionMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		noOfFloorsMR = Trim(inputStringArray[i]);
		document.getElementById("noOfFloorsMR").value = noOfFloorsMR;
		document.getElementById("noOfFloorsConfirmMR").value = noOfFloorsMR;
	} else {
		noOfFloorsMR = "";
		document.getElementById("noOfFloorsMR").value = noOfFloorsMR;
		document.getElementById("noOfFloorsConfirmMR").value = noOfFloorsMR;
	}
	i++;
	if (null != inputStringArray[0] && 'undefined' != inputStringArray[0]
			&& 'null' != inputStringArray[0] && '10' != inputStringArray[0]) {
		lastReadUpdateRemarks = "<ul><li>Meter Read Details was updated "
				+ Trim(inputStringArray[i]) + " with Status Code "
				+ Trim(inputStringArray[0]) + "</li></ul>";
		if(document.getElementById("btnSaveMR")){
			document.getElementById("btnSaveMR").disabled = true;
		}
	}
	fnCheckMeterReadStatusMROnload(document
			.getElementById("currentMeterReadRemarkCode"));
	fnSetReadTypeMR(document.getElementById("currentMeterReadRemarkCode"));

}
function processCurrentMeterReplacementDetails(inputString) {
	var inputStringArray = inputString.split('|'); // split the string and
													// remove the last one
	// alert(inputString+'\n'+inputStringArray);
	var i = 0, meterReplaceStageID = "";
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var waterConnectionUseMR = Trim(inputStringArray[i]);
		document.getElementById("waterConnectionUseMR").value = waterConnectionUseMR;
	} else {
		var waterConnectionUseMR = "";
		document.getElementById("waterConnectionUseMR").value = waterConnectionUseMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var waterConnectionSizeMR = Trim(inputStringArray[i]);
		document.getElementById("waterConnectionSizeMR").value = waterConnectionSizeMR;
	} else {
		var waterConnectionSizeMR = "15";
		document.getElementById("waterConnectionSizeMR").value = waterConnectionSizeMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var manufacturerMR = Trim(inputStringArray[i]);
		document.getElementById("manufacturerMR").value = manufacturerMR;
	} else {
		var manufacturerMR = "";
		document.getElementById("manufacturerMR").value = manufacturerMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var modelMR = Trim(inputStringArray[i]);
		document.getElementById("modelMR").value = modelMR;
	} else {
		var modelMR = "";
		document.getElementById("modelMR").value = modelMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var meterType = Trim(inputStringArray[i]);
		document.getElementById("meterType").value = meterType;
	} else {
		var meterType = "";
		document.getElementById("meterType").value = meterType;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]&&''!=Trim(inputStringArray[i])) {
		var badgeNoMR = Trim(inputStringArray[i]);
		document.getElementById("badgeNoMR").value = badgeNoMR;
	} else {
		var badgeNoMR = "NA";
		document.getElementById("badgeNoMR").value = badgeNoMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var saTypeID = Trim(inputStringArray[i]);
		document.getElementById("saTypeID").value = saTypeID;
	} else {
		var saTypeID = "";
		document.getElementById("saTypeID").value = saTypeID;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var meterReaderName = Trim(inputStringArray[i]);
		document.getElementById("meterReaderName").value = meterReaderName;
	} else {
		var meterReaderName = "";
		document.getElementById("meterReaderName").value = meterReaderName;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		meterInstalDate = Trim(inputStringArray[i]);
		document.getElementById("meterInstalDate").value = meterInstalDate;
		document.getElementById("meterInstalDateConfirm").value = meterInstalDate;
	} else {
		meterInstalDate = "";
		document.getElementById("meterInstalDate").value = meterInstalDate;
		document.getElementById("meterInstalDateConfirm").value = meterInstalDate;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var zeroReadRemarkCode = Trim(inputStringArray[i]);
		document.getElementById("zeroReadRemarkCode").value = zeroReadRemarkCode;
	} else {
		var zeroReadRemarkCode = "OK";
		document.getElementById("zeroReadRemarkCode").value = zeroReadRemarkCode;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		meterReplaceStageID = Trim(inputStringArray[i]);
		document.getElementById("meterReplaceStageID").value = meterReplaceStageID;
	} else {
		meterReplaceStageID = "";
		document.getElementById("meterReplaceStageID").value = meterReplaceStageID;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		zeroReadMR = Trim(inputStringArray[i]);
		document.getElementById("zeroReadMR").value = zeroReadMR;
		document.getElementById("zeroReadConfirmMR").value = zeroReadMR;
	} else {
		zeroReadMR = "0";
		document.getElementById("zeroReadMR").value = zeroReadMR;
		document.getElementById("zeroReadConfirmMR").value = zeroReadMR;
	}
	i++;/*
		 * if(null!=inputStringArray[i]&&'undefined'!=inputStringArray[i]&&
		 * 'null'!=inputStringArray[i]){ var
		 * oldSATypeMR=Trim(inputStringArray[i]);
		 * document.getElementById("oldSATypeMR").value=oldSATypeMR; }else{ var
		 * oldSATypeMR="";
		 * document.getElementById("oldSATypeMR").value=oldSATypeMR; }
		 */
	i++;/*
		 * if(null!=inputStringArray[i]&&'undefined'!=inputStringArray[i]&&
		 * 'null'!=inputStringArray[i]){ var
		 * oldSAStartDate=Trim(inputStringArray[i]);
		 * document.getElementById("oldSAStartDate").value=oldSAStartDate;
		 * }else{ var oldSAStartDate="";
		 * document.getElementById("oldSAStartDate").value=oldSAStartDate; }
		 */
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var oldMeterReadRemarkCode = Trim(inputStringArray[i]);
		document.getElementById("oldMeterReadRemarkCode").value = oldMeterReadRemarkCode;
	} else {
		var oldMeterReadRemarkCode = "";
		document.getElementById("oldMeterReadRemarkCode").value = oldMeterReadRemarkCode;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var oldMeterReadDate = Trim(inputStringArray[i]);
		document.getElementById("oldMeterReadDate").value = oldMeterReadDate;
	} else {
		var oldMeterReadDate = "";
		document.getElementById("oldMeterReadDate").value = oldMeterReadDate;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		oldMeterRegisterReadMR = Trim(inputStringArray[i]);
		document.getElementById("oldMeterRegisterReadMR").value = oldMeterRegisterReadMR;
		document.getElementById("oldMeterRegisterReadConfirmMR").value = oldMeterRegisterReadMR;
	} else {
		oldMeterRegisterReadMR = "";
		document.getElementById("oldMeterRegisterReadMR").value = oldMeterRegisterReadMR;
		document.getElementById("oldMeterRegisterReadConfirmMR").value = oldMeterRegisterReadMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		oldMeterAverageReadMR = Trim(inputStringArray[i]);
		document.getElementById("oldMeterAverageReadMR").value = oldMeterAverageReadMR;
		document.getElementById("oldMeterAverageReadConfirmMR").value = oldMeterAverageReadMR;
	}/*
		 * else{ oldMeterAverageReadMR="";
		 * document.getElementById("oldMeterAverageReadMR").value=oldMeterAverageReadMR;
		 * document.getElementById("oldMeterAverageReadConfirmMR").value=oldMeterAverageReadMR; }
		 */
	if ('' != meterReplaceStageID
			&& null != inputStringArray[inputStringArray.length - 1]
			&& 'undefined' != inputStringArray[inputStringArray.length - 1]
			&& 'null' != inputStringArray[inputStringArray.length - 1]) {
		lastMRUpdateRemarks = "<ul><li>Meter Replacement Details was "
				+ Trim(inputStringArray[inputStringArray.length - 1])
				+ "</li></ul>";
		if(document.getElementById("btnSaveMR")){
			document.getElementById("btnSaveMR").disabled = true;
		}
	}
}

function popupMRScreen() {
	isPopUp = true;
	if (null != document.getElementById('mrbox')
			&& 'null' != document.getElementById('mrbox')) {
		if (null != document.getElementById('djbmaintable')
				&& 'null' != document.getElementById('djbmaintable')) {
			var theTable = document.getElementById('djbmaintable');
			theTable.className = "djbmainhidden";
		}
		var thediv = document.getElementById('mrbox');
		thediv.style.display = "block";
		/* ================================================ */
		// Added by Matiur Rahman on 12-12-2012
		// document.getElementById('mrMessage').innerHTML="<font
		// color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		var fieldId = "seqNo" + currentPage;
		var fieldValue = Trim(document.getElementById(fieldId).value);
		toUpdateSeqNO = fieldValue;

		fieldId = "kno" + currentPage;
		fieldValue = Trim(document.getElementById(fieldId).value);
		toUpdateKNO = fieldValue;

		fieldId = "billRound" + currentPage;
		fieldValue = Trim(document.getElementById(fieldId).value);
		toUpdateBillRound = fieldValue;

		var ddId = "meterReadDD" + currentPage;
		var mmId = "meterReadMM" + currentPage;
		var yyyyId = "meterReadYYYY" + currentPage;
		document.getElementById(ddId).value = makeTwoDigit(Trim(document
				.getElementById(ddId).value));
		document.getElementById(mmId).value = makeTwoDigit(Trim(document
				.getElementById(mmId).value));
		var meterReadDate = document.getElementById(ddId).value + "/"
				+ document.getElementById(mmId).value + "/"
				+ document.getElementById(yyyyId).value;
		toUpdateMeterReadDate = meterReadDate;
		toUpdateMeterReadDateConfirm = meterReadDate;

		fieldId = "meterReadStatus" + currentPage;
		fieldValue = Trim(document.getElementById(fieldId).value);
		toUpdateMeterReadStatus = fieldValue;

		if (currentMeterReadText != '') {
			toUpdateCurrentMeterRead = currentMeterReadText;
			toUpdateCurrentMeterReadConfirm = currentMeterReadText;
		} else {
			fieldId = "currentMeterRead" + currentPage;
			fieldValue = Trim(document.getElementById(fieldId).value);
			toUpdateCurrentMeterRead = fieldValue;
			toUpdateCurrentMeterReadConfirm = fieldValue;
		}
		if (newAvgConsumptionText != '') {
			toUpdateNewAvgConsumption = newAvgConsumptionText;
			toUpdateNewAvgConsumptionConfirm = newAvgConsumptionText;
		} else {
			fieldId = "newAverageConsumption" + currentPage;
			fieldValue = Trim(document.getElementById(fieldId).value);
			toUpdateNewAvgConsumption = fieldValue;
			toUpdateNewAvgConsumptionConfirm = fieldValue;
		}
		if (newNoOfFloorText != '') {
			toUpdateNewNoOfFloor = newNoOfFloorText;
			toUpdateNewNoOfFloorConfirm = newNoOfFloorText;
		} else {
			fieldId = "newNoOfFloor" + currentPage;
			fieldValue = Trim(document.getElementById(fieldId).value);
			toUpdateNewNoOfFloor = fieldValue;
			toUpdateNewNoOfFloorConfirm = fieldValue;
		}
		fieldId = "consumerStatus" + currentPage;
		fieldValue = Trim(document.getElementById(fieldId).value);
		toUpdateConsumerStatus = fieldValue;
		// alert('toUpdateSeqNO>>'+toUpdateSeqNO+'>>toUpdateKNO>>'+toUpdateKNO+'>>toUpdateBillRound>>'+toUpdateBillRound);
		/* ================================================ */
		if (lastKNO.toString() != toUpdateKNO.toString()) {
			fnResetMeterReplacementDetails();
		}
		// document.body.style.overflow = 'hidden';
		document.getElementById('billRoundMR').value = toUpdateBillRound;
		document.getElementById('billRoundMR').disabled = true;
		document.getElementById('zoneMR').value = searchZone;
		document.getElementById('zoneMR').disabled = true;

		var select = document.getElementById("mrNoMR");
		select.options[select.options.length] = new Option(searchMRNo,
				searchMRNo);
		document.getElementById("mrNoMR").value = searchMRNo;
		document.getElementById('mrNoMR').disabled = true;
		select = document.getElementById("areaMR");
		select.options[select.options.length] = new Option(searchArea,
				searchAreaCode);
		document.getElementById("areaMR").value = searchAreaCode;
		document.getElementById('areaMR').disabled = true;
		document.getElementById('slNo').value = toUpdateSeqNO;
		document.getElementById('knoMR').value = toUpdateKNO;
		lastKNO = document.getElementById('knoMR').value;
		document.getElementById('knoMR').disabled = true;
		var spanId = "consumerNameSpan" + currentPage;
		document.getElementById('nameMR').value = Trim(document
				.getElementById(spanId).innerHTML);
		// document.getElementById('nameMR').disabled=true;
		spanId = "consumerWcNoSpan" + currentPage;
		document.getElementById('wcNoMR').value = Trim(document
				.getElementById(spanId).innerHTML);
		document.getElementById('wcNoMR').disabled = true;
		spanId = "consumerCategory" + currentPage;
		document.getElementById('consumerCategoryMR').value = Trim(document
				.getElementById(spanId).innerHTML);
		spanId = "consumerType" + currentPage;
		document.getElementById('consumerTypeMR').value = Trim(document
				.getElementById(spanId).innerHTML);
		if (document.getElementById('consumerTypeMR').value == 'METERED') {
			document.getElementById('saType').value = "NA";
			document.getElementById('saType').disabled = true;
			document.getElementById('saTypeID').title = "Only applicable for Unmetered Consumer";
		}
		document.getElementById('waterConnectionSizeMR').value = "15";
		document.getElementById('zeroReadMR').value = "0";
		document.getElementById('zeroReadConfirmMR').value = "0";
		document.getElementById('consumerStatusMR').value = toUpdateConsumerStatus;
		spanId = "currentAverageConsumption" + currentPage;
		var currentAverageConsumptionTemp = Trim(document
				.getElementById(spanId).value);
		document.getElementById('currentAverageConsumptionMR').value = currentAverageConsumptionTemp;
		document.getElementById('currentAverageConsumptionMR').disabled = true;
		// Added By Matiur Rahman on 14-12-2012
		if (currentAverageConsumptionTemp != 'NA') {
			document.getElementById("oldMeterAverageReadMR").value = currentAverageConsumptionTemp;
			document.getElementById("oldMeterAverageReadConfirmMR").value = currentAverageConsumptionTemp;
			oldMeterAverageReadMR = currentAverageConsumptionTemp;
		}
		knoToResetMR = Trim(document.getElementById('knoMR').value);
		billRoundToResetMR = Trim(document.getElementById('billRoundMR').value);
		document.getElementById('btnCloseSpan').innerHTML = "<input type='button' id='btnClose' value=' Close ' class='groovybutton' onclick='javascript:fnCancelMeterReplacementDetails();' title='Click to go back to previous screen.'/>";
		fnGetConsumerDetailsByKNO();

	}
}
function displayMRScreen() {
	isPopUp = false;
	if (null != document.getElementById('mrbox')
			&& 'null' != document.getElementById('mrbox')) {
		if (null != document.getElementById('djbmaintable')
				&& 'null' != document.getElementById('djbmaintable')) {
			var theTable = document.getElementById('djbmaintable');
			theTable.className = "djbmainhidden";
		}
		var thediv = document.getElementById('mrbox');
		thediv.style.display = "block";
		// document.getElementById('meterReaderName').focus();
	}
}
function hideMRScreen() {
	if (null != document.getElementById('mrbox')
			&& 'null' != document.getElementById('mrbox')) {

		var thediv = document.getElementById('mrbox');
		thediv.style.display = "none";
		// document.body.style.overflow = 'auto';
		if (null != document.getElementById('djbmaintable')
				&& 'null' != document.getElementById('djbmaintable')) {
			var theTable = document.getElementById('djbmaintable');
			theTable.className = "djbmain";
		}
	}

}
function fnCancelMeterReplacementDetails() {

	hideMRScreen();
}
function fnSaveMeterReplacementDetails() {
	doMeterReplacementAjaxCall();
}

function setMeterInstalDate() {
	meterInstalDate = Trim(document.getElementById('meterInstalDate').value);
	if (meterInstalDate.length > 0) {
		if (!validateDateString(meterInstalDate)) {
			document.getElementById('meterInstalDate').focus();
			return;
		}
		// Future Date Validation: Provide a meter installation date in future->
		/*
		 * if(compareDates(todaysDateMR,meterInstalDate) &&
		 * todaysDateMR!=meterInstalDate){ if(!confirm('Meter Installation Date
		 * Should not be a Future Date\n Are You Sure You Want to
		 * Continue?\n\nPress Ok to Continue else Cancel.')){
		 * meterInstalDate="";
		 * document.getElementById('meterInstalDate').value="";
		 * fnSetOldMeterReadDate(document.getElementById('meterInstalDate'));
		 * document.getElementById('meterInstalDate').focus(); return; } }
		 */
		// Validation: If Provided Meter Installation date is before 01 JAN 2010
		// then System should show a soft warning stating the record will not be
		// processed
		// as Meter Installation date is before 1 Jan 2010.
		// In this scenario the status in Meter Replacement staging table for
		// the record should be updated to 915.
		/*
		 * if(compareDates(meterInstalDate,"01/01/2010")&&
		 * '01/01/2010'!=meterInstalDate){ if(!confirm('The record will not be
		 * processed as Meter Installation date is before 1 JAN 2010.\n Are You
		 * Sure You Want to Continue?\n\nPress Ok to Continue else Cancel.')){
		 * meterInstalDate="";
		 * document.getElementById('meterInstalDate').value="";
		 * fnSetOldMeterReadDate(document.getElementById('meterInstalDate'));
		 * document.getElementById('meterInstalDate').focus(); return; } }
		 */
		fnSetOldMeterReadDate(document.getElementById('meterInstalDate'));
		document.getElementById('meterInstalDate').value = "**********";
		document.getElementById('meterInstalDateConfirm').focus();
	}
}
function resetMeterInstalDate() {
	document.getElementById('meterInstalDate').value = meterInstalDate;
}
function setZeroReadMR() {
	zeroReadMR = Trim(document.getElementById('zeroReadMR').value);
	if (zeroReadMR.length > 0 && parseFloat(zeroReadMR.toString()) != 0) {
		IsPositiveNumber(document.getElementById('zeroReadMR'));
		//zeroReadMR=roundOffDecimal(zeroReadMR,2);
		document.getElementById('zeroReadMR').value = "**********";
	}
}
function resetZeroReadMR() {
	if (document.getElementById('zeroReadMR').value == "**********") {
		document.getElementById('zeroReadMR').value = zeroReadMR;
	} else {
		document.getElementById('zeroReadMR').value = "";
		zeroReadMR = "";
	}
}
function resetZeroReadConfirmMR() {
	if (document.getElementById('zeroReadMR').value != "**********") {
		document.getElementById('zeroReadConfirmMR').value = "";
	}
}
function setCurrentMeterReadDateMR() {
	currentMeterReadDateMR = Trim(document
			.getElementById('currentMeterReadDateMR').value);
	if (currentMeterReadDateMR.length > 0) {
		if (!validateDateString(currentMeterReadDateMR)) {
			document.getElementById('currentMeterReadDateMR').focus();
			return;
		}
		// Validation: Future Date is not Allowed !.
		if (compareDates(todaysDateMR, currentMeterReadDateMR)
				&& todaysDateMR != currentMeterReadDateMR) {
			alert('Future Date is not Allowed !.');
			currentMeterReadDateMR = "";
			document.getElementById('currentMeterReadDateMR').value = "";
			document.getElementById('currentMeterReadDateMR').focus();
			return;
		}
		document.getElementById('currentMeterReadDateMR').value = "**********";
	}
}
function resetCurrentMeterReadDateMR() {
	document.getElementById('currentMeterReadDateMR').value = currentMeterReadDateMR;
}
function setCurrentMeterRegisterReadMR() {
	currentMeterRegisterReadMR = Trim(document
			.getElementById('currentMeterRegisterReadMR').value);
	if (currentMeterRegisterReadMR.length > 0) {
		IsPositiveNumber(document.getElementById('currentMeterRegisterReadMR'));
		//currentMeterRegisterReadMR=roundOffDecimal(currentMeterRegisterReadMR,2);
		document.getElementById('currentMeterRegisterReadMR').value = "**********";
	}
}
function resetCurrentMeterRegisterReadMR() {
	if (document.getElementById('currentMeterRegisterReadMR').value == '**********') {
		document.getElementById('currentMeterRegisterReadMR').value = currentMeterRegisterReadMR;
	} else {
		currentMeterRegisterReadMR = "";
		document.getElementById('currentMeterRegisterReadMR').value = currentMeterRegisterReadMR;
	}
}
function resetCurrentMeterRegisterReadConfirmMR() {
	if (document.getElementById('currentMeterRegisterReadMR').value != "**********") {
		document.getElementById('currentMeterRegisterReadConfirmMR').value = "";
	}
}
function setNewAverageConsumptionMR() {
	newAverageConsumptionMR = Trim(document
			.getElementById('newAverageConsumptionMR').value);
	if (newAverageConsumptionMR.length > 0) {
		IsPositiveNumber(document.getElementById('newAverageConsumptionMR'));
		//newAverageConsumptionMR=roundOffDecimal(newAverageConsumptionMR,2);
		document.getElementById('newAverageConsumptionMR').value = "**********";
	}
}
function resetNewAverageConsumptionMR() {
	if (document.getElementById('newAverageConsumptionMR').value == "**********") {
		document.getElementById('newAverageConsumptionMR').value = newAverageConsumptionMR;
	} else {
		newAverageConsumptionMR = "";
		document.getElementById('newAverageConsumptionMR').value = newAverageConsumptionMR;
	}
}
function resetNewAverageConsumptionConfirmMR() {
	if (document.getElementById('newAverageConsumptionMR').value != "**********") {
		document.getElementById('newAverageConsumptionConfirmMR').value = "";
	}
}
function setNoOfFloorsMR() {
	noOfFloorsMR = Trim(document.getElementById('noOfFloorsMR').value);
	if (noOfFloorsMR.length > 0) {
		IsPositiveNumber(document.getElementById('noOfFloorsMR'));
		document.getElementById('noOfFloorsMR').value = "**********";
	}
}
function resetNoOfFloorsMR() {
	document.getElementById('noOfFloorsMR').value = noOfFloorsMR;
}
function setOldMeterRegisterReadMR() {
	oldMeterRegisterReadMR = Trim(document
			.getElementById('oldMeterRegisterReadMR').value);
	if (oldMeterRegisterReadMR.length > 0) {
		IsPositiveNumber(document.getElementById('oldMeterRegisterReadMR'));
		//oldMeterRegisterReadMR=roundOffDecimal(oldMeterRegisterReadMR,2);
		document.getElementById('oldMeterRegisterReadMR').value = "**********";
	}
}
function resetOldMeterRegisterReadMR() {
	document.getElementById('oldMeterRegisterReadMR').value = oldMeterRegisterReadMR;
}
// Added on 11-12-2012 by Matiur Rahman
function setOldMeterAverageReadMR() {
	oldMeterAverageReadMR = Trim(document
			.getElementById('oldMeterAverageReadMR').value);	
	if (oldMeterAverageReadMR.length > 0) {
		IsPositiveNumber(document.getElementById('oldMeterAverageReadMR'));
		//oldMeterAverageReadMR=roundOffDecimal(oldMeterAverageReadMR,2);
		document.getElementById('oldMeterAverageReadMR').value = "**********";
	}
}
function resetOldMeterAverageReadMR() {
	document.getElementById('oldMeterAverageReadMR').value = oldMeterAverageReadMR;
}
// ============================================
function fnSetwaterConnectionUseMRTitle(obj) {
	document.getElementById('waterConnectionUseMRId').title = obj.value;
}
function fnResetMeterReplacementDetails() {
	fnResetPreviousMeterReadDetails();
	knoToResetMR = Trim(document.getElementById('knoMR').value);
	billRoundToResetMR = Trim(document.getElementById('billRoundMR').value);
	document.getElementById('meterReaderName').value = "";
	if (!isPopUp) {
		document.getElementById('billRoundMR').value = "";
		document.getElementById('zoneMR').value = "";
		document.getElementById('zoneMR').disabled = false;
		document.getElementById('mrNoMR').value = "";
		document.getElementById('mrNoMR').disabled = false;
		document.getElementById('areaMR').value = "";
		document.getElementById('areaMR').disabled = false;
		document.getElementById('wcNoMR').value = "";
		document.getElementById('wcNoMR').disabled = false;
		document.getElementById('knoMR').value = "";
		document.getElementById('knoMR').disabled = false;
		document.getElementById('nameMR').value = "";
		document.getElementById('saType').disabled = false;
	}
	document.getElementById('waterConnectionSizeMR').value = "15";
	document.getElementById('waterConnectionUseMR').value = "";
	document.getElementById('meterType').value = "";
	document.getElementById('badgeNoMR').value = "NA";
	document.getElementById('manufacturerMR').value = "";
	document.getElementById('modelMR').value = "";
	if (document.getElementById('consumerTypeMR').value != 'METERED') {
		document.getElementById('saType').value = "";
	}
	// document.getElementById('isLNTServiceProvider').value="";
	document.getElementById('meterInstalDate').value = "";
	document.getElementById('meterInstalDateConfirm').value = "";
	document.getElementById('zeroReadMR').value = "0";
	document.getElementById('zeroReadConfirmMR').value = "0";
	document.getElementById('currentMeterReadDateMR').value = "";
	document.getElementById('currentMeterReadRemarkCode').value = "";
	document.getElementById('currentMeterReadDateConfirmMR').value = "";
	document.getElementById('currentMeterRegisterReadMR').value = "";
	document.getElementById('currentMeterRegisterReadConfirmMR').value = "";
	document.getElementById('newAverageConsumptionMR').value = "";
	document.getElementById('newAverageConsumptionConfirmMR').value = "";
	document.getElementById('oldMeterRegisterReadMR').value = "";
	document.getElementById('oldMeterRegisterReadConfirmMR').value = "";
	document.getElementById('oldMeterReadRemarkCode').value = "";
	document.getElementById('oldMeterAverageReadMR').value = "";
	document.getElementById('oldMeterAverageReadConfirmMR').value = "";
	document.getElementById('oldSATypeMR').value = "";
	meterInstalDate = "";
	zeroReadMR = "";
	currentMeterReadDateMR = "";
	currentMeterRegisterReadMR = "";
	oldMeterRegisterReadMR = "";
	oldMeterAverageReadMR = "";
	document.getElementById('mrMessage').innerHTML = "";
	document.getElementById('mrMessage').title = "Server Message.";
	document.getElementById('btnSaveMR').disabled = false;
	if (lastKNO.toString() == toUpdateKNO.toString()) {
		resetMeterReplacementAjaxCall();
	}
}

//Added by Rajib as suggested by Asha with Jtrac: DJb-2020

function fnAddMoreMeterReplacementDetails() {
	fnResetPreviousMeterReadDetails();
	knoToResetMR = Trim(document.getElementById('knoMR').value);
	billRoundToResetMR = Trim(document.getElementById('billRoundMR').value);
	document.getElementById('meterReaderName').value = "";
	if (!isPopUp) {
		document.getElementById('billRoundMR').value = "";
		document.getElementById('zoneMR').value = "";
		document.getElementById('zoneMR').disabled = false;
		document.getElementById('mrNoMR').value = "";
		document.getElementById('mrNoMR').disabled = false;
		document.getElementById('areaMR').value = "";
		document.getElementById('areaMR').disabled = false;
		document.getElementById('wcNoMR').value = "";
		document.getElementById('wcNoMR').disabled = false;
		document.getElementById('knoMR').value = "";
		document.getElementById('knoMR').disabled = false;
		document.getElementById('nameMR').value = "";
		document.getElementById('saType').disabled = false;
	}
	document.getElementById('waterConnectionSizeMR').value = "15";
	document.getElementById('waterConnectionUseMR').value = "";
	document.getElementById('meterType').value = "";
	document.getElementById('badgeNoMR').value = "NA";
	document.getElementById('manufacturerMR').value = "";
	document.getElementById('modelMR').value = "";
	if (document.getElementById('consumerTypeMR').value != 'METERED') {
		document.getElementById('saType').value = "";
	}
	// document.getElementById('isLNTServiceProvider').value="";
	document.getElementById('meterInstalDate').value = "";
	document.getElementById('meterInstalDateConfirm').value = "";
	document.getElementById('zeroReadMR').value = "0";
	document.getElementById('zeroReadConfirmMR').value = "0";
	document.getElementById('currentMeterReadDateMR').value = "";
	document.getElementById('currentMeterReadRemarkCode').value = "";
	document.getElementById('currentMeterReadDateConfirmMR').value = "";
	document.getElementById('currentMeterRegisterReadMR').value = "";
	document.getElementById('currentMeterRegisterReadConfirmMR').value = "";
	document.getElementById('newAverageConsumptionMR').value = "";
	document.getElementById('newAverageConsumptionConfirmMR').value = "";
	document.getElementById('oldMeterRegisterReadMR').value = "";
	document.getElementById('oldMeterRegisterReadConfirmMR').value = "";
	document.getElementById('oldMeterReadRemarkCode').value = "";
	document.getElementById('oldMeterAverageReadMR').value = "";
	document.getElementById('oldMeterAverageReadConfirmMR').value = "";
	document.getElementById('oldSATypeMR').value = "";
	meterInstalDate = "";
	zeroReadMR = "";
	currentMeterReadDateMR = "";
	currentMeterRegisterReadMR = "";
	oldMeterRegisterReadMR = "";
	oldMeterAverageReadMR = "";
	document.getElementById('mrMessage').innerHTML = "";
	document.getElementById('mrMessage').title = "Server Message.";
	document.getElementById('btnSaveMR').disabled = false;
}
function validateOldMeterRead(){
	var textValue;	
	var textConfirm;
	var fieldId;
	var fieldValue;
	fieldId="oldMeterReadDate";
	fieldValue=Trim(document.getElementById(fieldId).value);	
	//Validation: Future Date is not Allowed !.
	if(compareDates(todaysDate,fieldValue) && todaysDate!=fieldValue){
		alert('Future Date is not Allowed !.');
		return false;
	}
	var prevMeterReadDateMRId="prevMeterReadDateMR";
	var prevMeterReadDateMR=Trim(document.getElementById(prevMeterReadDateMRId).value);
	//Validation:1.Current Meter Reading Date should always be greater than Last Meter Read Date.
	if(fieldValue.length==10 && prevMeterReadDateMR.length==10){
		if(compareDates(fieldValue,prevMeterReadDateMR)){
			alert('Old Meter Reading Date should always be greater than Previous Meter Reading Date '+prevMeterReadDateMR+'.');
			return false;
		}
	}
	fieldId="prevActMeterReadMR";
	var prevActMeterReadMR=Trim(document.getElementById(fieldId).value);
	fieldId="oldMeterRegisterReadMR";
	fieldValue=Trim(document.getElementById(fieldId).value);
	textValue=Trim(oldMeterRegisterReadMR);	
	var oldMeterReadRemarkCodeId="oldMeterReadRemarkCode";
	var oldMeterReadRemarkCode=Trim(document.getElementById(oldMeterReadRemarkCodeId).value);
	textValue=textValue.length==0?"0":textValue;	
	/**6.In case Current Meter Read Remark is any of Regular Read Remark (OK, PUMP), Current Meter Reading should always be greater than Actual Last Registered Reading.*/
	if((oldMeterReadRemarkCode=='OK' ||oldMeterReadRemarkCode=='PUMP') && prevActMeterReadMR!='' && prevActMeterReadMR!='NA' && textValue !='' && parseFloat(textValue)<=parseFloat(prevActMeterReadMR) ){
		alert('Old Meter Reading should always be greater than Previous Meter Reading '+prevActMeterReadMR+' in case of Regular Read Type.');		
		document.getElementById(fieldId).focus();
		return false;					
	}
	/**In case of (NUW, PLUG, DEM) Current Meter Reading validations.*/
	if(oldMeterReadRemarkCode=='NUW' || oldMeterReadRemarkCode=='PLUG'|| oldMeterReadRemarkCode=='DEM'){
		/**11.In case of Current Meter Read Remark is any of Regular Read Remark (NUW, PLUG, DEM) and Previous Meter Read Remark is any of Average Read Remark (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) then Current Meter Reading should always be equal to Last Registered Reading.*/
		if(averageReadTypeMR.indexOf(Trim(document.getElementById("prevMeterReadRemarkMR").value)) > -1){
			var fieldId="prevMeterReadMR";
			var prevMeterReadMR=Trim(document.getElementById(fieldId).value);		
			if(prevMeterReadMR!='NA'&&prevMeterReadMR!=''){
				fieldId="oldMeterRegisterReadMR";
				document.getElementById(fieldId).value=prevMeterReadMR;
				document.getElementById(fieldId).disabled=true; 
				fieldId="oldMeterRegisterReadConfirmMR";
				document.getElementById(fieldId).value=prevMeterReadMR;
				document.getElementById(fieldId).disabled=true;
				oldMeterRegisterReadMR=prevMeterReadMR;
			}
		}		
		/**12.In case of Current Meter Read Remark is any of Regular Read Remark (NUW, PLUG, DEM) and Previous Meter Read Remark is any of Regular Read Remark (OK, PUMP, NUW, PLUG, DEM) then Current Meter Reading should always be equal to Last Registered Reading.*/
		if(regularReadTypeMR.indexOf(Trim(document.getElementById("prevMeterReadRemarkMR").value)) > -1){
			var fieldId="prevMeterReadMR";
			var prevMeterReadMR=Trim(document.getElementById(fieldId).value);		
			if(prevMeterReadMR!='NA'&&prevMeterReadMR!=''){
				fieldId="oldMeterRegisterReadMR";
				document.getElementById(fieldId).value=prevMeterReadMR;
				document.getElementById(fieldId).disabled=true; 
				fieldId="oldMeterRegisterReadConfirmMR";
				document.getElementById(fieldId).value=prevMeterReadMR;
				document.getElementById(fieldId).disabled=true;
				oldMeterRegisterReadMR=prevMeterReadMR;
			}
		}
		/**13.In case of Current Meter Read Remark is any of Regular Read Remark (NUW, PLUG, DEM) and Previous Meter Read Remark is any of Provisional Read Type (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) then Current Meter Reading should always be equal to Actual Last Registered Reading.*/
		if(provisionalReadTypeMR.indexOf(Trim(document.getElementById("prevMeterReadRemarkMR").value)) > -1){
			var fieldId="prevActMeterReadMR";
			var prevActMeterReadMR=Trim(document.getElementById(fieldId).value);		
			if(prevActMeterReadMR!='NA'&&prevActMeterReadMR!=''){
				fieldId="oldMeterRegisterReadMR";
				document.getElementById(fieldId).value=prevActMeterReadMR;
				document.getElementById(fieldId).disabled=true; 
				fieldId="oldMeterRegisterReadConfirmMR";
				document.getElementById(fieldId).value=prevActMeterReadMR;
				document.getElementById(fieldId).disabled=true;
				oldMeterRegisterReadMR=prevActMeterReadMR;
			}
		}
	}
	if(oldMeterReadRemarkCode=='DFMT'){
		fieldId="oldMeterRegisterReadMR";
		document.getElementById(fieldId).value="0";
		document.getElementById(fieldId).disabled=true; 
		fieldId="oldMeterRegisterReadConfirmMR";
		document.getElementById(fieldId).value="0";
		document.getElementById(fieldId).disabled=true;
		oldMeterRegisterReadMR="0";
	}
	return true;
}
function validateMRReplacementDetails() {
	/*
	 * if(Trim(document.getElementById('meterReaderName').value).length==0){
	 * document.getElementById('meterReaderName').focus();
	 * document.getElementById('mrMessage').innerHTML="<font color='red'>Please
	 * Enter All Mandatory Fields to Proceed.</font>"; return false; }
	 */
	/*if (Trim(document.getElementById('billRoundMR').value).length == 0) {
		document.getElementById('billRoundMR').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}*/
	if (Trim(document.getElementById('zoneMR').value).length == 0) {
		document.getElementById('zoneMR').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	if (Trim(document.getElementById('mrNoMR').value).length == 0) {
		document.getElementById('mrNoMR').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	if (Trim(document.getElementById('areaMR').value).length == 0) {
		document.getElementById('areaMR').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	if (Trim(document.getElementById('wcNoMR').value).length == 0) {
		document.getElementById('wcNoMR').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	if (Trim(document.getElementById('knoMR').value).length == 0) {
		document.getElementById('knoMR').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	/*
	 * if(Trim(document.getElementById('nameMR').value).length==0){
	 * document.getElementById('nameMR').focus();
	 * document.getElementById('mrMessage').innerHTML="<font color='red'>Please
	 * Enter All Mandatory Fields to Proceed.</font>"; return false; }
	 */
	if (Trim(document.getElementById('waterConnectionSizeMR').value).length == 0) {
		document.getElementById('waterConnectionSizeMR').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	/*
	 * if(Trim(document.getElementById('waterConnectionUseMR').value).length==0){
	 * document.getElementById('waterConnectionUseMR').focus();
	 * document.getElementById('mrMessage').innerHTML="<font color='red'>Please
	 * Enter All Mandatory Fields to Proceed.</font>"; return false; }
	 */
	if (Trim(document.getElementById('meterType').value).length == 0) {
		document.getElementById('meterType').disabled = false;
		document.getElementById('meterType').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	// Start- Added by Matiur Rahman on 30-11-2012 as per JTrac Issue # DJB-720
	if (Trim(document.getElementById('manufacturerMR').value) == 'N/A'
			&& Trim(document.getElementById('modelMR').value) != 'N/A W') {
		document.getElementById('modelMR').value = "N/A W";
		document.getElementById('modelMR').disabled = true;
	} else {
		document.getElementById('modelMR').disabled = false;
	}
	// End- Added by Matiur Rahman on 30-11-2012 as per JTrac Issue # DJB-720
	/*
	 * if(Trim(document.getElementById('badgeNoMR').value).length==0){
	 * document.getElementById('badgeNoMR').focus();
	 * document.getElementById('mrMessage').innerHTML="<font color='red'>Please
	 * Enter All Mandatory Fields to Proceed.</font>"; return false; }
	 * if(Trim(document.getElementById('manufacturerMR').value).length==0){
	 * document.getElementById('manufacturerMR').focus();
	 * document.getElementById('mrMessage').innerHTML="<font color='red'>Please
	 * Enter All Mandatory Fields to Proceed.</font>"; return false; }
	 * if(Trim(document.getElementById('modelMR').value).length==0){
	 * document.getElementById('modelMR').focus();
	 * document.getElementById('mrMessage').innerHTML="<font color='red'>Please
	 * Enter All Mandatory Fields to Proceed.</font>"; return false; }
	 */
	/*
	 * alert(Trim(document.getElementById('saType').value)+'<>'+document.getElementById('consumerTypeMR').value);
	 * if(document.getElementById('consumerTypeMR').value!='METERED'){ if(
	 * (Trim(document.getElementById('saType').value).length==0||Trim(document.getElementById('saType').value=='NA'))){
	 * //document.getElementById('saType').focus();
	 * //document.getElementById('mrMessage').innerHTML="<font
	 * color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
	 * //return false; alert(Trim(document.getElementById('saType').value)+'<>'+document.getElementById('consumerTypeMR').value);
	 * document.getElementById('saType').value="SAWATSEW"; } }
	 */
	/*
	 * if(Trim(document.getElementById('isLNTServiceProvider').value).length==0){
	 * document.getElementById('isLNTServiceProvider').focus();
	 * document.getElementById('mrMessage').innerHTML="<font color='red'>Please
	 * Enter All Mandatory Fields to Proceed.</font>"; return false; }
	 */
	if(Trim(document.getElementById('saType').value) ==''){
		document.getElementById('saType').value="SAWATSEW";
	}
	if (meterInstalDate.length == 0) {
		document.getElementById('meterInstalDate').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	if (Trim(document.getElementById('meterInstalDateConfirm').value).length == 0) {
		document.getElementById('meterInstalDateConfirm').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	var confirmedValue = Trim(document.getElementById('meterInstalDateConfirm').value);
	if (meterInstalDate.toString() != confirmedValue.toString()) {
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Confirmed value doesn't match.</font>";
		document.getElementById('meterInstalDateConfirmSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('meterInstalDateConfirmSpan').title = "Doesn't Match";
		document.getElementById('meterInstalDateConfirm').value = "";
		document.getElementById('meterInstalDateConfirm').focus();
		return false;
	} else {
		document.getElementById('meterInstalDateConfirmSpan').innerHTML = "";
		document.getElementById('meterInstalDateConfirmSpan').title = "OK";
	}
	if (zeroReadMR.length == 0) {
		document.getElementById('zeroReadMR').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	if (Trim(document.getElementById('zeroReadConfirmMR').value).length == 0) {
		document.getElementById('zeroReadConfirmMR').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	confirmedValue = Trim(document.getElementById('zeroReadConfirmMR').value);
	if (parseFloat(zeroReadMR.toString()) != parseFloat(confirmedValue
			.toString())
			&& confirmedValue.length > 0) {
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Confirmed value doesn't match.</font>";
		document.getElementById('zeroReadConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('zeroReadConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('zeroReadConfirmMR').value = "";
		document.getElementById('zeroReadConfirmMR').focus();
		return false;
	} else {
		document.getElementById('zeroReadConfirmMRSpan').innerHTML = "";
		document.getElementById('zeroReadConfirmMRSpan').title = "OK";
	}
	if (currentMeterReadDateMR.length == 0) {
		document.getElementById('currentMeterReadDateMR').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	if (Trim(document.getElementById('currentMeterReadDateConfirmMR').value).length == 0) {
		document.getElementById('currentMeterReadDateConfirmMR').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	confirmedValue = Trim(document
			.getElementById('currentMeterReadDateConfirmMR').value);
	if (currentMeterReadDateMR.toString() != confirmedValue.toString()
			&& confirmedValue.length > 0) {
		document.getElementById('currentMeterReadDateConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('currentMeterReadDateConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('currentMeterReadDateConfirmMR').value = "";
		document.getElementById('currentMeterReadDateConfirmMR').focus();
		return false;
	} else {
		document.getElementById('currentMeterReadDateConfirmMRSpan').innerHTML = "";
		document.getElementById('currentMeterReadDateConfirmMRSpan').title = "OK";
	}
	if (Trim(document.getElementById('currentMeterReadRemarkCode').value).length == 0) {
		document.getElementById('currentMeterReadRemarkCode').focus();
		document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		return false;
	}
	var readType = fnGetReadTypeMR(Trim(document
			.getElementById('currentMeterReadRemarkCode').value));
	if (readType.toString() == 'Volumetric Billing') {
		if(Trim(document
				.getElementById('currentMeterReadRemarkCode').value)=='NUW' || Trim(document
						.getElementById('currentMeterReadRemarkCode').value)=='PLUG'|| Trim(document
								.getElementById('currentMeterReadRemarkCode').value)=='DEM'){				
			if(zeroReadMR!=''){ 
				  fieldId="currentMeterRegisterReadMR";
				  document.getElementById(fieldId).value=zeroReadMR;
				  document.getElementById(fieldId).disabled=true;
				  fieldId="currentMeterRegisterReadConfirmMR";
				  document.getElementById(fieldId).value=zeroReadMR;
				  document.getElementById(fieldId).disabled=true;
				  toUpdateCurrentMeterReadConfirm=zeroReadMR;
				  toUpdateCurrentMeterRead=zeroReadMR;
				  currentMeterRegisterReadMR=zeroReadMR;
			 }
		  }else{
			document.getElementById('currentMeterRegisterReadMR').disabled = false;
			document.getElementById('currentMeterRegisterReadConfirmMR').disabled = false;
			if (Trim(document.getElementById('currentMeterRegisterReadMR').value).length == 0) {
				document.getElementById('currentMeterRegisterReadMR').focus();
				document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
				return false;
			}
		}
		confirmedValue = Trim(document
				.getElementById('currentMeterRegisterReadConfirmMR').value);
		 //alert(confirmedValue+'<>'+currentMeterRegisterReadMR+'<>'+document.getElementById('currentMeterRegisterReadConfirmMR').value);
		if (Trim(document.getElementById('currentMeterRegisterReadConfirmMR').value).length == 0) {
			document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			document.getElementById('currentMeterRegisterReadConfirmMR')
					.focus();
			return false;
		}
		confirmedValue = Trim(document
				.getElementById('currentMeterRegisterReadConfirmMR').value);
		// alert(confirmedValue+'<>'+currentMeterRegisterReadMR);
		if (parseFloat(currentMeterRegisterReadMR.toString()) != parseFloat(confirmedValue
				.toString())
				&& confirmedValue.length > 0) {
			document.getElementById('currentMeterRegisterReadConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
			document.getElementById('currentMeterRegisterReadConfirmMRSpan').title = "Doesn't Match";
			document.getElementById('currentMeterRegisterReadConfirmMR').value = "";
			document.getElementById('currentMeterRegisterReadConfirmMR')
					.focus();
			return false;
		} else {
			document.getElementById('currentMeterRegisterReadConfirmMRSpan').innerHTML = "";
			document.getElementById('currentMeterRegisterReadConfirmMRSpan').title = "OK";
		}
	} else {
		document.getElementById('currentMeterRegisterReadMR').disabled = true;
		document.getElementById('currentMeterRegisterReadConfirmMR').disabled = true;
	}
	/**
	 * Validation as per Apurb Sinha 21-03-2013: Following validations
	 * for Meter Replacement Meter Read Data, other validation will be switched
	 * off: 1. Current Meter Reading Date should always be greater than Last
	 * Meter Read Date i.e The new meter read date should be greater than Meter
	 * Installation Date. 2. In case Current Meter Read Remark is any of Regular
	 * Read Type (OK, PUMP, NUW, PLUG, DEM) the Meter Read Type will be Regular
	 * else it will be no read. 3. Current meter read should always be zero if
	 * reader remark code is not in OK, PUMP, NUW, PLUG, DEM. 4. Current Meter
	 * Reading field should only be enabled for Regular Read Remark (OK, PUMP).
	 * 5. In case Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP), New Meter Read should always be greater than Zero Read. 6. If
	 * current meter read remark is NUW, PLUG, DEM then new meter read should be
	 * equal to zero read.
	 */
	/**Validation 1. Current Meter Reading Date should always be greater than Last
	 * Meter Read Date i.e The new meter read date should be greater than Meter
	 * Installation Date.*/
	if(Trim(meterInstalDate.toString()).length==10 && compareDates(currentMeterReadDateMR,Trim(meterInstalDate.toString()))){
		alert('Current Meter Reading Date should always be greater than Meter Installation Date.');
		document.getElementById('currentMeterReadDateMR').focus();
		return false;
	}
	/**Validation 2. In case Current Meter Read Remark is any of Regular
	 * Read Type (OK, PUMP, NUW, PLUG, DEM) the Meter Read Type will be Regular
	 * else it will be no read.*/
	//This is done at back end
	/**Validation 3. Current meter read should always be zero if
	 * reader remark code is not in OK, PUMP, NUW, PLUG, DEM.*/
	//Already Done Above
	/**Validation 4. Current Meter Reading field should only be enabled for Regular Read Remark (OK, PUMP).*/
	//Already Done Above
	/**Validation 5. In case Current Meter Read Remark is any of Regular Read Remark (OK,
	 * PUMP), New Meter Read should always be greater than Zero Read.*/	
	if(zeroReadMR!='' && (Trim(document
			.getElementById('currentMeterReadRemarkCode').value)=='OK' || Trim(document
					.getElementById('currentMeterReadRemarkCode').value)=='PUMP')&& parseFloat(currentMeterRegisterReadMR)<=parseFloat(zeroReadMR)){				
		  alert('Current Meter Read Remark is any of Regular Read Remark (OK,PUMP), New Meter Read should always be greater than Zero Read.');
		  fieldId="currentMeterRegisterReadConfirmMR";
		  document.getElementById(fieldId).value="";
		  document.getElementById(fieldId).disabled=false;
		  fieldId="currentMeterRegisterReadMR";
		  document.getElementById(fieldId).value="";
		  document.getElementById(fieldId).disabled=false;
		  toUpdateCurrentMeterReadConfirm="";
		  toUpdateCurrentMeterRead="";
		  currentMeterRegisterReadMR="";
		  document.getElementById(fieldId).focus();
		  return false;
	  }
	/**Validation 6. If
	 * current meter read remark is NUW, PLUG, DEM then new meter read should be
	 * equal to zero read.
	 */
	//Already Done Above
	confirmedValue = Trim(document
			.getElementById('newAverageConsumptionConfirmMR').value);
	if ((parseFloat(newAverageConsumptionMR.toString()) != parseFloat(confirmedValue
			.toString()) && confirmedValue.length > 0)
			|| (newAverageConsumptionMR.length > 0 && confirmedValue.length == 0)) {
		document.getElementById('newAverageConsumptionConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('newAverageConsumptionConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('newAverageConsumptionConfirmMR').value = "";
		document.getElementById('newAverageConsumptionConfirmMR').focus();
		return false;
	} else {
		document.getElementById('newAverageConsumptionConfirmMRSpan').innerHTML = "";
		document.getElementById('newAverageConsumptionConfirmMRSpan').title = "OK";
	}
	confirmedValue = Trim(document.getElementById('noOfFloorsConfirmMR').value);
	if (noOfFloorsMR.toString() != confirmedValue.toString()
			||( noOfFloorsMR.length > 0&& confirmedValue.length == 0)) {
		document.getElementById('noOfFloorsConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('noOfFloorsConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('noOfFloorsConfirmMR').value = "";
		document.getElementById('noOfFloorsConfirmMR').focus();
		return false;
	} else {
		document.getElementById('noOfFloorsConfirmMRSpan').innerHTML = "";
		document.getElementById('noOfFloorsConfirmMRSpan').title = "OK";
	}
	var consumerCategoryMR = Trim(document.getElementById('consumerCategoryMR').value);
	// Added by Matiur Rahman on 14-12-2012 as email sent by Ayan
	var currentAverageConsumptionMR = Trim(document
			.getElementById('currentAverageConsumptionMR').value);	
	if (consumerCategoryMR.toString() != 'CAT I') {
		var newReadType = fnGetReadTypeMR(Trim(document
				.getElementById('currentMeterReadRemarkCode').value));
		if (newReadType.toString() != 'Volumetric Billing') {
			if ((currentAverageConsumptionMR == 'NA' || parseFloat(currentAverageConsumptionMR) < 1 ||currentAverageConsumptionMR.length == 0)
					&& (newAverageConsumptionMR.length == 0 || parseFloat(newAverageConsumptionMR) < 1)) {
				document.getElementById('mrMessage').innerHTML = "<font  color='red'>For a Non Domestic Consumer New Average Consumption Should be greater than 0 as it is not in the System for Bill Generation.</font>";
				document.getElementById('newAverageConsumptionMR').focus();
				return false;
			}
		}
	}
	// ==================================
	if (Trim(document.getElementById('oldMeterReadRemarkCode').value).length == 0) {
		// document.getElementById('oldMeterReadRemarkCode').focus();
		// document.getElementById('mrMessage').innerHTML="<font
		// color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
		// return false;
		document.getElementById('oldMeterReadRemarkCode').value = "DFMT";
	}
	readType = fnGetReadTypeMR(Trim(document
			.getElementById('oldMeterReadRemarkCode').value));
	if (readType.toString() == 'Volumetric Billing') {
		document.getElementById('oldMeterRegisterReadMR').disabled = false;
		document.getElementById('oldMeterRegisterReadConfirmMR').disabled = false;
		if (oldMeterRegisterReadMR.length == 0) {
			oldMeterRegisterReadMR = "0";
			document.getElementById('oldMeterRegisterReadMR').value = oldMeterRegisterReadMR;
			document.getElementById('oldMeterRegisterReadConfirmMR').value = oldMeterRegisterReadMR;
			// document.getElementById('oldMeterRegisterReadMR').focus();
			// document.getElementById('mrMessage').innerHTML="<font
			// color='red'>Please Enter All Mandatory Fields to
			// Proceed.</font>";
			// return false;
		}
		if (Trim(document.getElementById('oldMeterRegisterReadMR').value).length != 0&&Trim(document.getElementById('oldMeterRegisterReadConfirmMR').value).length == 0) {
			document.getElementById('mrMessage').innerHTML = "<font  color='red'>Please Enter All Mandatory Fields to Proceed.</font>";
			document.getElementById('oldMeterRegisterReadConfirmMR').focus();
			return false;
		}
		confirmedValue = Trim(document
				.getElementById('oldMeterRegisterReadConfirmMR').value);
		if (parseFloat(oldMeterRegisterReadMR.toString()) != parseFloat(confirmedValue
				.toString())
				&& confirmedValue.length > 0) {
			document.getElementById('oldMeterRegisterReadConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
			document.getElementById('oldMeterRegisterReadConfirmMRSpan').title = "Doesn't Match";
			document.getElementById('oldMeterRegisterReadConfirmMR').value = "";
			document.getElementById('oldMeterRegisterReadConfirmMR').focus();
			return false;
		} else {
			document.getElementById('oldMeterRegisterReadConfirmMRSpan').innerHTML = "";
			document.getElementById('oldMeterRegisterReadConfirmMRSpan').title = "OK";
		}

	} else {
		document.getElementById('oldMeterRegisterReadMR').disabled = true;
		document.getElementById('oldMeterRegisterReadConfirmMR').disabled = true;
	}
	// Added by Matiur Rahman on 11-12-2012
	confirmedValue = Trim(document
			.getElementById('oldMeterAverageReadConfirmMR').value);
	if ((parseFloat(oldMeterAverageReadMR.toString()) != parseFloat(confirmedValue
			.toString()))
			|| (oldMeterAverageReadMR.length > 0 && confirmedValue.length == 0)) {
		document.getElementById('oldMeterAverageReadConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('oldMeterAverageReadConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('oldMeterAverageReadConfirmMR').value = "";
		document.getElementById('oldMeterAverageReadConfirmMR').focus();
		return false;
	} else {
		document.getElementById('oldMeterAverageReadConfirmMRSpan').innerHTML = "";
		document.getElementById('oldMeterAverageReadConfirmMRSpan').title = "OK";
	}
	// Logic modified as per discussion Yash Jtrac#DJB-819
	if (consumerCategoryMR.toString() != 'CAT I') {
		var oldReadType = fnGetReadTypeMR(Trim(document
				.getElementById('oldMeterReadRemarkCode').value));
		if (oldReadType.toString() != 'Volumetric Billing') {
			if ((currentAverageConsumptionMR == 'NA' || parseFloat(currentAverageConsumptionMR) < 1 ||currentAverageConsumptionMR.length == 0)
					&& (oldMeterAverageReadMR.length == 0 || parseFloat(oldMeterAverageReadMR) < 1)) {
				document.getElementById('mrMessage').innerHTML = "<font  color='red'>For a Non Domestic Consumer Old Average Consumption Should be greater than 0 as it is not in the System for Bill Generation.</font>";
				document.getElementById('oldMeterAverageReadMR').disabled=false;
				document.getElementById('oldMeterAverageReadConfirmMR').disabled=false;
				oldMeterAverageReadMR="";
				document.getElementById('oldMeterAverageReadMR').value=oldMeterAverageReadMR;
				document.getElementById('oldMeterAverageReadConfirmMR').value=oldMeterAverageReadMR;
				document.getElementById('oldMeterAverageReadMR').focus();
				return false;
			}else{
				oldMeterAverageReadMR=document.getElementById('oldMeterAverageReadConfirmMR').value;
			}
		}
	}
	if(!validateOldMeterRead()){
		return false;
	}
	/**Start: Added on 03-04-2013 By Matiur Rahman*/
	// Validation: Consumers with old SA Start Date before 01-Jan-2010 must be parked from being processed by Meter Replacement.
	// In this scenario the status in Meter Replacement staging table for the
	// record should be updated to 915.		
	var oldSAStartDate=Trim(document.getElementById('oldSAStartDate').value);
	if (oldSAStartDate.length==10 && compareDates(oldSAStartDate, "01/01/2010")	&& '01/01/2010' != oldSAStartDate) {
		/*Start:JTrac ID#DJB-2024:28-02-2014:Commented by Matiur Rahman*/
		/*if (confirm('The record will not be processed as Old SA Start Date before 01-Jan-2010.\n Are You Sure You Want to Continue?\n\nPress Ok to Continue else Cancel.')){
			document.getElementById('meterReplaceStageID').value = "915";
		}else{
			return false;
		}*/
		/*End:JTrac ID#DJB-2024:28-02-2014:Commented by Matiur Rahman*/
		/*Start:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
		alert('Consumer is disconnected.');
		return false;
		/*End:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
	}
	// Validation: System should show a soft warning stating the record will not
	// be processed as Disconnected consumers must be parked from Meter Replacement.
	// In this scenario the status in Meter Replacement staging table for the
	// record should be updated to 915.
	var oldSATypeMR=Trim(document.getElementById('oldSATypeMR').value);
	if (oldSATypeMR.length==0||oldSATypeMR==''||oldSAStartDate.length!=10) {
		/*Start:JTrac ID#DJB-2024:28-02-2014:Commented by Matiur Rahman*/
		/*if (confirm('The record will not be processed as this is a Disconnected consumer.\n Are You Sure You Want to Continue?\n\nPress Ok to Continue else Cancel.')){
			document.getElementById('meterReplaceStageID').value = "915";
		}else{
			return false;
		}*/
		/*End:JTrac ID#DJB-2024:28-02-2014:Commented by Matiur Rahman*/
		/*Start:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
		alert('Consumer is disconnected.');
		return false;
		/*End:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
	}
	/**End: Added on 03-04-2013 By Matiur Rahman*/
	// Validation: If meter installation date in future- System should show a
	// soft warning stating the 'Meter Installation Date can not be in future'
	// and status for the record should be updated to 1200 in Meter Replacement
	// staging table.Data should be saved in system.
	if (meterInstalDate.length == 10
			&& compareDates(todaysDateMR, meterInstalDate)
			&& todaysDateMR != meterInstalDate) {
		/*Start:JTrac ID#DJB-2024:28-02-2014:Commented by Matiur Rahman*/
		/*if (!confirm('The record will not be processed as Meter Installation Date Should not be a Future Date\n Are You Sure You Want to Continue?\n\nPress Ok to Continue else Cancel.')) {
			meterInstalDate = "";
			document.getElementById('meterInstalDate').value = "";
			fnSetOldMeterReadDate(document.getElementById('meterInstalDate'));
			document.getElementById('meterInstalDate').focus();
			return false;
		} else {
			document.getElementById('meterReplaceStageID').value = "1200";
		}*/
		/*End:JTrac ID#DJB-2024:28-02-2014:Commented by Matiur Rahman*/
		/*Start:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
		alert('Meter Replacement Date cannot be in future.');
		return false;
		/*End:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
	} else
	// Validation: If Provided Meter Installation date is before 01 JAN 2010
	// then System should show a soft warning stating the record will not be
	// processed
	// as Meter Installation date is before 1 Jan 2010.
	// In this scenario the status in Meter Replacement staging table for the
	// record should be updated to 915.
	if (compareDates(meterInstalDate, "01/01/2010")
			&& '01/01/2010' != meterInstalDate) {
		/*Start:JTrac ID#DJB-2024:28-02-2014:Commented by Matiur Rahman*/
		/*if (!confirm('The record will not be processed as Meter Installation date is before 1 JAN 2010.\n Are You Sure You Want to Continue?\n\nPress Ok to Continue else Cancel.')) {
			meterInstalDate = "";
			document.getElementById('meterInstalDate').value = "";
			fnSetOldMeterReadDate(document.getElementById('meterInstalDate'));
			document.getElementById('meterInstalDate').focus();
			return false;
		} else {
			document.getElementById('meterReplaceStageID').value = "915";
		}*/
		/*End:JTrac ID#DJB-2024:28-02-2014:Commented by Matiur Rahman*/
		/*Start:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
		alert('Meter Installation Date cannot be before 1 Jan 2010.');
		return false;
		/*End:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
	} else
	// Validation: System should show a soft warning stating the record will not
	// be processed as Meter Installation date is same as Old Meter Installation
	// Date.
	// In this scenario the status in Meter Replacement staging table for the
	// record should be updated to 960.
	// Validation: System should show a soft warning stating the record will not
	// be processed as Meter Installation date lies within previous one month of
	// Old Meter Installation Date..
	// In this scenario the status in Meter Replacement staging table for the
	// record should be updated to 960.
	if ('NA' != document.getElementById('lastActiveMeterInstalDate').value
			&& meterInstalDate.toString()== document
					.getElementById('lastActiveMeterInstalDate').value) {
		/**
		 * Commented as to ignore this validation as per discussion with
		 * Apurb Sinha on 30-03-2013.
		 */
		 /* compareDates(meterInstalDate, document
					.getElementById('lastActiveMeterInstalDate').value)*/
		//if (!confirm('The record will not be processed as Meter Installation date is same/earlier than Old Meter Installation Date.\n Are You Sure You Want to Continue?\n\nPress Ok to Continue else Cancel.')) {
		/*Start:JTrac ID#DJB-2024:28-02-2014:Commented by Matiur Rahman*/
		/*if (!confirm('The record will not be processed as Meter Installation date is same as Old Meter Installation Date.\n Are You Sure You Want to Continue?\n\nPress Ok to Continue else Cancel.')) {
			meterInstalDate = "";
			document.getElementById('meterInstalDate').value = "";
			fnSetOldMeterReadDate(document.getElementById('meterInstalDate'));
			document.getElementById('meterInstalDate').focus();
			return false;
		} else {
			document.getElementById('meterReplaceStageID').value = "960";
		}*/
		/*End:JTrac ID#DJB-2024:28-02-2014:Commented by Matiur Rahman*/
		/*Start:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
		alert('Meter Installation date cannot be same as Old Meter Installation Date.');
		return false;
		/*End:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
	}
	/**
	 * Commented as to ignore this validation as per discussion with
	 * Apurb Sinha on 30-03-2013.
	 */
	// else
	// Validation: System should show a soft warning stating the record will not
	// be processed as Meter Installation Date lies within next one month of Old
	// Meter Installation Date.
	// In this scenario the status in Meter Replacement staging table for the
	// record should be updated to 960.	
	/*alert(document.getElementById('nextValidMeterInstalldate').value+'<>'+meterInstalDate+'<>'+compareDates( document
			.getElementById('nextValidMeterInstalldate').value,meterInstalDate.toString())+'<>'+compareDates( meterInstalDate.toString(),document
					.getElementById('nextValidMeterInstalldate').value));	
	if ((document.getElementById('nextValidMeterInstalldate').value).length == 10
			&& (compareDates(document
					.getElementById('nextValidMeterInstalldate').value,meterInstalDate) || meterInstalDate == document
					.getElementById('nextValidMeterInstalldate').value)) {
		if (!confirm('The record will not be processed as Meter Installation date lies within next one month of Old Meter Installation Date.\n Are You Sure You Want to Continue?\n\nPress Ok to Continue else Cancel.')) {
			meterInstalDate = "";
			document.getElementById('meterInstalDate').value = "";
			fnSetOldMeterReadDate(document.getElementById('meterInstalDate'));
			document.getElementById('meterInstalDate').focus();
			return false;
		} else {
			document.getElementById('meterReplaceStageID').value = "960";
		}
	}
	*/
	/*Start:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
	if ((meterInstalDate.toString()).length == 10
			&& (document.getElementById('lastActiveMeterInstalDate').value).length == 10
			&& (compareDates(meterInstalDate.toString(), document
					.getElementById('lastActiveMeterInstalDate').value))) {
		alert('Meter Installation date is prior to Last Meter Installation Date.');
		fnSetOldMeterReadDate(document.getElementById('meterInstalDate'));
		document.getElementById('meterInstalDate').focus();
		return false;
	}
	if ((document.getElementById('nextValidMeterInstalldate').value).length == 10
			&& (compareDates(meterInstalDate.toString(), document
					.getElementById('nextValidMeterInstalldate').value) || meterInstalDate == document
					.getElementById('nextValidMeterInstalldate').value)) {
		if (!confirm('Meter Installation Date lies within next one month of Old Meter Installation Date.\n Are You Sure You Want to Continue?\n\nPress Ok to Continue else Cancel.')) {
			meterInstalDate = "";
			document.getElementById('meterInstalDate').value = "";
			fnSetOldMeterReadDate(document.getElementById('meterInstalDate'));
			document.getElementById('meterInstalDate').focus();
			return false;
		}
	}
	/*End:JTrac ID#DJB-2024:28-02-2014:Added by Matiur Rahman*/
	/*Start:Added By Matiur Rahman on 01-05-2013 as per JTrac#DJB-1438*/
	if(Trim(document.getElementById("prevMeterReadRemarkMR").value).length!=0 && averageReadTypeMR.indexOf(Trim(document.getElementById("prevMeterReadRemarkMR").value)) > -1){
		document.getElementById("prevMeterReadRemarkMR").value="DFMT";
		document.getElementById("prevMeterReadRemarkMR").disabled=true;
		document.getElementById("prevMeterReadRemarkMR").title="Set to DFMT as Previous Meter Read for Old Meter is Office Estimated.";
		var fieldId="oldMeterRegisterReadMR";
		document.getElementById(fieldId).value="0";
		document.getElementById(fieldId).disabled=true; 
		fieldId="oldMeterRegisterReadConfirmMR";
		document.getElementById(fieldId).value="0";
		document.getElementById(fieldId).disabled=true;
		oldMeterRegisterReadMR="0";
	}
	/*End:Added By Matiur Rahman on 01-05-2013 as per JTrac#DJB-1438*/
	return true;
}

function fnCheckMeterInstalDateConfirm() {
	var confirmedValue = Trim(document.getElementById('meterInstalDateConfirm').value);
	if (meterInstalDate.toString() != confirmedValue.toString()
			&& confirmedValue.length > 0) {
		document.getElementById('meterInstalDateConfirmSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('meterInstalDateConfirmSpan').title = "Doesn't Match";
		document.getElementById('meterInstalDateConfirm').value = "";
		document.getElementById('meterInstalDateConfirm').focus();
	} else {
		document.getElementById('meterInstalDateConfirmSpan').innerHTML = "";
		document.getElementById('meterInstalDateConfirmSpan').title = "OK";
		if(meterInstalDate.toString()!=meterInstalDateToGetPrevReadDetails.toString() || Trim(document.getElementById('knoMR').value)!=knoToGetPrevReadDetails.toString() ){
			fnGetPreviousMeterReadDetails();
		}
	}
}
function fnCheckMeterInstalDate() {
	if (meterInstalDate.length != 10) {
		document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'><b>Enter Meter Installtion Date First.</b></font>";
		document.getElementById('meterInstalDate').focus();
		return;
	}
	fnGetPreviousMeterReadDetails();
	if(Trim(document.getElementById("prevMeterReadRemarkMR").value).length!=0 && averageReadTypeMR.indexOf(Trim(document.getElementById("prevMeterReadRemarkMR").value)) > -1){
		document.getElementById("prevMeterReadRemarkMR").value="DFMT";
		document.getElementById("prevMeterReadRemarkMR").disabled=true;
		document.getElementById("prevMeterReadRemarkMR").title="Set to DFMT as Previous Meter Read for Old Meter is Office Estimated.";
		var fieldId="oldMeterRegisterReadMR";
		document.getElementById(fieldId).value="0";
		document.getElementById(fieldId).disabled=true; 
		fieldId="oldMeterRegisterReadConfirmMR";
		document.getElementById(fieldId).value="0";
		document.getElementById(fieldId).disabled=true;
		oldMeterRegisterReadMR="0";
	}
}

function fnCheckZeroReadMRConfirm() {
	var confirmedValue = Trim(document.getElementById('zeroReadConfirmMR').value);
	if (zeroReadMR.toString() != confirmedValue.toString()
			&& confirmedValue.length > 0) {
		document.getElementById('zeroReadConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('zeroReadConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('zeroReadConfirmMR').value = "";
		document.getElementById('zeroReadConfirmMR').focus();
	} else {
		document.getElementById('zeroReadConfirmMRSpan').innerHTML = "";
		document.getElementById('zeroReadConfirmMRSpan').title = "OK";
	}
}
function fnCheckCurrentMeterReadDateMRConfirm() {
	var confirmedValue = Trim(document
			.getElementById('currentMeterReadDateConfirmMR').value);
	if (currentMeterReadDateMR.toString() != confirmedValue.toString()
			&& confirmedValue.length > 0) {
		document.getElementById('currentMeterReadDateConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('currentMeterReadDateConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('currentMeterReadDateConfirmMR').value = "";
		document.getElementById('currentMeterReadDateConfirmMR').focus();
	} else {
		document.getElementById('currentMeterReadDateConfirmMRSpan').innerHTML = "";
		document.getElementById('currentMeterReadDateConfirmMRSpan').title = "OK";
	}
}
function fnCheckCurrentMeterRegisterReadMRConfirm() {
	var confirmedValue = Trim(document
			.getElementById('currentMeterRegisterReadConfirmMR').value);
	if (confirmedValue.length > 0 && parseFloat(currentMeterRegisterReadMR.toString()) != parseFloat(confirmedValue.toString())) {
		document.getElementById('currentMeterRegisterReadConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('currentMeterRegisterReadConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('currentMeterRegisterReadConfirmMR').value = "";
		document.getElementById('currentMeterRegisterReadConfirmMR').focus();
	} else {
		document.getElementById('currentMeterRegisterReadConfirmMRSpan').innerHTML = "";
		document.getElementById('currentMeterRegisterReadConfirmMRSpan').title = "OK";
	}
}
function fnCheckNewAverageConsumptionMRConfirm() {
	var confirmedValue = Trim(document
			.getElementById('newAverageConsumptionConfirmMR').value);
	if (confirmedValue.length > 0 && parseFloat(newAverageConsumptionMR.toString()) != parseFloat(confirmedValue.toString())) {
		document.getElementById('newAverageConsumptionConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('newAverageConsumptionConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('newAverageConsumptionConfirmMR').value = "";
		document.getElementById('newAverageConsumptionConfirmMR').focus();
	} else {
		document.getElementById('newAverageConsumptionConfirmMRSpan').innerHTML = "";
		document.getElementById('newAverageConsumptionConfirmMRSpan').title = "OK";
	}
}
function fnCheckNoOfFloorsMRConfirm() {
	var confirmedValue = Trim(document.getElementById('noOfFloorsConfirmMR').value);
	if (noOfFloorsMR.toString() != confirmedValue.toString()
			&& confirmedValue.length > 0) {
		document.getElementById('noOfFloorsConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('noOfFloorsConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('noOfFloorsConfirmMR').value = "";
		document.getElementById('noOfFloorsConfirmMR').focus();
	} else {
		document.getElementById('noOfFloorsConfirmMRSpan').innerHTML = "";
		document.getElementById('noOfFloorsConfirmMRSpan').title = "OK";
	}
}
function fnCheckOldMeterRegisterReadMRConfirm() {
	var confirmedValue = Trim(document
			.getElementById('oldMeterRegisterReadConfirmMR').value);
	if (confirmedValue.length > 0 && parseFloat(oldMeterRegisterReadMR.toString()) != parseFloat(confirmedValue.toString())) {
		document.getElementById('oldMeterRegisterReadConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('oldMeterRegisterReadConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('oldMeterRegisterReadConfirmMR').value = "";
		document.getElementById('oldMeterRegisterReadConfirmMR').focus();
	} else {
		document.getElementById('oldMeterRegisterReadConfirmMRSpan').innerHTML = "";
		document.getElementById('oldMeterRegisterReadConfirmMRSpan').title = "OK";
	}
}
// Added on 11-12-2012 by Matiur Rahman
function fnCheckOldMeterAverageReadMRConfirm() {
	var confirmedValue = Trim(document
			.getElementById('oldMeterAverageReadConfirmMR').value);
	if ( confirmedValue.length > 0 && parseFloat(oldMeterAverageReadMR.toString()) != parseFloat(confirmedValue.toString())
			) {
		document.getElementById('oldMeterAverageReadConfirmMRSpan').innerHTML = "<font size='2' color='red'><b>X</b></font>";
		document.getElementById('oldMeterAverageReadConfirmMRSpan').title = "Doesn't Match";
		document.getElementById('oldMeterAverageReadConfirmMR').value = "";
		document.getElementById('oldMeterAverageReadConfirmMR').focus();
	} else {
		document.getElementById('oldMeterAverageReadConfirmMRSpan').innerHTML = "";
		document.getElementById('oldMeterAverageReadConfirmMRSpan').title = "OK";
	}
}
// ============================================================
function fnMakeTwoDigit(obj) {
	obj.value = Trim(obj.value);
	IsNumber(obj);
	obj.value = makeTwoDigit(obj.value);
}
function setMeterReadStatusMR(obj) {
	fnCheckMeterReadStatusMR(obj);
	fnSetReadTypeMR(obj);

}
function setOldMeterReadStatusMR(obj) {
	//fnCheckOldMeterReadStatusMR(obj);
	// fnSetReadTypeMR(obj);
	if(meterInstalDate.toString()!=meterInstalDateToGetPrevReadDetails.toString() || Trim(document.getElementById('knoMR').value)!=knoToGetPrevReadDetails.toString() ){
		fnGetPreviousMeterReadDetails();
	}else{
		fnCheckOldMeterReadStatusMR(obj);
	}

}
function fnCheckMeterReadStatusMR(obj) {
	var meterReadStatus = Trim(obj.value);
	// 3. Current Meter Reading field should only be enabled for (OK, PUMP).
	if (meterReadStatus.toString() == 'OK'
			|| meterReadStatus.toString() == 'PUMP') {
		var fieldId = "currentMeterRegisterReadConfirmMR";
		document.getElementById(fieldId).value = "";
		document.getElementById(fieldId).disabled = false;
		fieldId = "currentMeterRegisterReadMR";
		document.getElementById(fieldId).value = "";
		currentMeterRegisterReadMR = "";
		document.getElementById(fieldId).disabled = false;
		document.getElementById(fieldId).focus();
	} else {
		var fieldId = "currentMeterRegisterReadConfirmMR";
		document.getElementById(fieldId).value = "";
		document.getElementById(fieldId).disabled = true;
		fieldId = "currentMeterRegisterReadMR";
		document.getElementById(fieldId).value = "";
		document.getElementById(fieldId).disabled = true;
	}
	// 4. In case of (NUW, PLUG, DEM) Current Meter Reading should always be
	// equal to Zero Reading.
	
	 if(meterReadStatus.toString()=='NUW' ||
	  meterReadStatus.toString()=='PLUG'|| meterReadStatus.toString()=='DEM'){
	
	  if(zeroReadMR!=''){ 
		  fieldId="currentMeterRegisterReadMR";
		  document.getElementById(fieldId).value=zeroReadMR;
		  document.getElementById(fieldId).disabled=true;
		  fieldId="currentMeterRegisterReadConfirmMR";
		  document.getElementById(fieldId).value=zeroReadMR;
		  document.getElementById(fieldId).disabled=true;
		  toUpdateCurrentMeterReadConfirm=zeroReadMR;
		  toUpdateCurrentMeterRead=zeroReadMR; 
		  }
	  }
	
}
function fnCheckMeterReadStatusMROnload(obj) {
	var meterReadStatus = Trim(obj.value);
	// 3. Current Meter Reading field should only be enabled for (OK, PUMP).
	if (meterReadStatus.toString() == 'OK'
			|| meterReadStatus.toString() == 'PUMP') {
		var fieldId = "currentMeterRegisterReadConfirmMR";
		// document.getElementById(fieldId).value="";
		document.getElementById(fieldId).disabled = false;
		fieldId = "currentMeterRegisterReadMR";
		// document.getElementById(fieldId).value="";
		// currentMeterRegisterReadMR="";
		document.getElementById(fieldId).disabled = false;
		// document.getElementById(fieldId).focus();
	} else {
		var fieldId = "currentMeterRegisterReadConfirmMR";
		// document.getElementById(fieldId).value="";
		document.getElementById(fieldId).disabled = true;
		fieldId = "currentMeterRegisterReadMR";
		// document.getElementById(fieldId).value="";
		document.getElementById(fieldId).disabled = true;
	}
}
function fnSetOldMeterLastReadDetailsRowTitle(){
	var oldMeterLastReadDetailsRowTitle="-------------------------------------------------------------------------------------";
	oldMeterLastReadDetailsRowTitle+="\nPrevious Meter Read Details:";
	oldMeterLastReadDetailsRowTitle+="\nLast Meter Read Date: "+ document.getElementById("prevMeterReadDateMR").value;
	oldMeterLastReadDetailsRowTitle+="\nLast Meter Read Status: "+ document.getElementById("prevMeterReadRemarkMR").value;
	oldMeterLastReadDetailsRowTitle+="\nLast Registered Reading: "+ document.getElementById("prevMeterReadMR").value;
	oldMeterLastReadDetailsRowTitle+="\nActual Last Meter Read Date: "+ document.getElementById("prevActMeterReadDateMR").value;
	oldMeterLastReadDetailsRowTitle+="\nActual Last Registered Reading: "+ document.getElementById("prevActMeterReadMR").value;
	oldMeterLastReadDetailsRowTitle+="\n-------------------------------------------------------------------------------------";
	document.getElementById("oldMeterLastReadDetailsRowId").title=oldMeterLastReadDetailsRowTitle;
}
function fnCheckOldMeterReadStatusMR(obj) {
	fnSetOldMeterLastReadDetailsRowTitle();
	/**Validation added on 03-04-2013 as per email by Apurb Sinha as
	 * When previous Meter Read is for Old Meter is Office Estimate i.e. average Read Type, than system should not allow user to enter Old Meter Last Read Details it should be set to default DFMT.
	 * */
	if(Trim(document.getElementById("prevMeterReadRemarkMR").value).length!=0 && averageReadTypeMR.indexOf(Trim(document.getElementById("prevMeterReadRemarkMR").value)) > -1){
		obj.value="DFMT";
		obj.disabled=true;
		obj.title="Set to DFMT as Previous Meter Read for Old Meter is Office Estimated.";
		var fieldId="oldMeterRegisterReadMR";
		document.getElementById(fieldId).value="0";
		document.getElementById(fieldId).disabled=true; 
		fieldId="oldMeterRegisterReadConfirmMR";
		document.getElementById(fieldId).value="0";
		document.getElementById(fieldId).disabled=true;
		oldMeterRegisterReadMR="0";
	}else{
		obj.title="";
		var oldMeterReadRemarkCode = Trim(obj.value);
		// 3. Old Meter Reading field should only be enabled for (OK, PUMP).
		if (oldMeterReadRemarkCode.toString() == 'OK'
				|| oldMeterReadRemarkCode.toString() == 'PUMP') {
			var fieldId = "oldMeterRegisterReadConfirmMR";
			document.getElementById(fieldId).value = "";
			document.getElementById(fieldId).disabled = false;
			fieldId = "oldMeterRegisterReadMR";
			document.getElementById(fieldId).value = "";
			oldMeterRegisterReadMR = "";
			document.getElementById(fieldId).disabled = false;
			document.getElementById(fieldId).focus();
		} else {
			/*var fieldId = "oldMeterRegisterReadConfirmMR";
			document.getElementById(fieldId).value = "";
			document.getElementById(fieldId).disabled = true;
			fieldId = "oldMeterRegisterReadMR";
			document.getElementById(fieldId).value = "";
			document.getElementById(fieldId).disabled = true;*/
			/**In case of (NUW, PLUG, DEM) Current Meter Reading validations.*/
			if(oldMeterReadRemarkCode=='NUW' || oldMeterReadRemarkCode=='PLUG'|| oldMeterReadRemarkCode=='DEM'){
				/**11.In case of Current Meter Read Remark is any of Regular Read Remark (NUW, PLUG, DEM) and Previous Meter Read Remark is any of Average Read Remark (TEST, DFMT, STP, MTTM, MREV, REP, UNMT, BYPS) then Current Meter Reading should always be equal to Last Registered Reading.*/
				if(averageReadTypeMR.indexOf(Trim(document.getElementById("prevMeterReadRemarkMR").value)) > -1){
					var fieldId="prevMeterReadMR";
					var prevMeterReadMR=Trim(document.getElementById(fieldId).value);		
					if(prevMeterReadMR!='NA'&&prevMeterReadMR!=''){
						fieldId="oldMeterRegisterReadMR";
						document.getElementById(fieldId).value=prevMeterReadMR;
						document.getElementById(fieldId).disabled=true; 
						fieldId="oldMeterRegisterReadConfirmMR";
						document.getElementById(fieldId).value=prevMeterReadMR;
						document.getElementById(fieldId).disabled=true;
						oldMeterRegisterReadMR=prevMeterReadMR;
					}
				}		
				/**12.In case of Current Meter Read Remark is any of Regular Read Remark (NUW, PLUG, DEM) and Previous Meter Read Remark is any of Regular Read Remark (OK, PUMP, NUW, PLUG, DEM) then Current Meter Reading should always be equal to Last Registered Reading.*/
				if(regularReadTypeMR.indexOf(Trim(document.getElementById("prevMeterReadRemarkMR").value)) > -1){
					var fieldId="prevMeterReadMR";
					var prevMeterReadMR=Trim(document.getElementById(fieldId).value);		
					if(prevMeterReadMR!='NA'&&prevMeterReadMR!=''){
						fieldId="oldMeterRegisterReadMR";
						document.getElementById(fieldId).value=prevMeterReadMR;
						document.getElementById(fieldId).disabled=true; 
						fieldId="oldMeterRegisterReadConfirmMR";
						document.getElementById(fieldId).value=prevMeterReadMR;
						document.getElementById(fieldId).disabled=true;
						oldMeterRegisterReadMR=prevMeterReadMR;
					}
				}
				/**13.In case of Current Meter Read Remark is any of Regular Read Remark (NUW, PLUG, DEM) and Previous Meter Read Remark is any of Provisional Read Type (PLOC, NRES, MLOC, MBUR, VMMT, DUST, BJAM, ADF, RDDT, NS) then Current Meter Reading should always be equal to Actual Last Registered Reading.*/
				if(provisionalReadTypeMR.indexOf(Trim(document.getElementById("prevMeterReadRemarkMR").value)) > -1){
					var fieldId="prevActMeterReadMR";
					var prevActMeterReadMR=Trim(document.getElementById(fieldId).value);		
					if(prevActMeterReadMR!='NA'&&prevActMeterReadMR!=''){
						fieldId="oldMeterRegisterReadMR";
						document.getElementById(fieldId).value=prevActMeterReadMR;
						document.getElementById(fieldId).disabled=true; 
						fieldId="oldMeterRegisterReadConfirmMR";
						document.getElementById(fieldId).value=prevActMeterReadMR;
						document.getElementById(fieldId).disabled=true;
						oldMeterRegisterReadMR=prevActMeterReadMR;
					}
				}
			}
		}
	}
	// 4. In case of (NUW, PLUG, DEM) Current Meter Reading should always be
	// equal to Previous Meter Reading.
	/*
	 * if(meterReadStatus.toString()=='NUW' ||
	 * meterReadStatus.toString()=='PLUG'|| meterReadStatus.toString()=='DEM'){
	 * var fieldId="hidPrevMeterRead"; var
	 * prevMeterRead=Trim(document.getElementById(fieldId).value);
	 * if(prevMeterRead!='NA'){ fieldId="currentMeterRegisterReadMR";
	 * document.getElementById(fieldId).value=prevMeterRead;
	 * document.getElementById(fieldId).disabled=true;
	 * fieldId="currentMeterRegisterReadConfirmMR";
	 * document.getElementById(fieldId).value=prevMeterRead;
	 * document.getElementById(fieldId).disabled=true;
	 * toUpdateCurrentMeterReadConfirm=prevMeterRead;
	 * toUpdateCurrentMeterRead=prevMeterRead; } }
	 */
}
function fnCheckOldMeterReadStatusMROnLoad(obj) {
	var meterReadStatus = Trim(obj.value);
	// 3. Old Meter Reading field should only be enabled for (OK, PUMP).
	if (meterReadStatus.toString() == 'OK'
			|| meterReadStatus.toString() == 'PUMP') {
		var fieldId = "oldMeterRegisterReadConfirmMR";
		document.getElementById(fieldId).disabled = false;
		fieldId = "oldMeterRegisterReadMR";
		document.getElementById(fieldId).disabled = false;
	} else {
		var fieldId = "oldMeterRegisterReadConfirmMR";
		document.getElementById(fieldId).disabled = true;
		fieldId = "oldMeterRegisterReadMR";
		document.getElementById(fieldId).disabled = true;
	}
}
function fnSetReadTypeMR(obj) {
	var meterReadStatus = Trim(obj.value);
	var readType = fnGetReadTypeMR(meterReadStatus);
	var meterReadTypeSpanId = "meterReadTypeSpanMR";
	var billTypeSpanId = "billTypeSpanMR";
	var currentMeterReadId = "currentMeterRegisterReadMR";
	var currentMeterReadConfirmId = "currentMeterRegisterReadConfirmMR";
	document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
	document.getElementById(billTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
	if (readType == 'Volumetric Billing') {
		// if(document.getElementById('consumerTypeMR').value=='METERED'&&
		// (meterReadStatus=='OK'||meterReadStatus=='PUMP')){
		if (meterReadStatus == 'OK' || meterReadStatus == 'PUMP') {
			document.getElementById(currentMeterReadId).disabled = false;
			document.getElementById(currentMeterReadConfirmId).disabled = false;
		} else {
			document.getElementById(currentMeterReadId).disabled = true;
			document.getElementById(currentMeterReadConfirmId).disabled = true;
		}
		document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='green'><b>Regular</b></font>";
		document.getElementById(billTypeSpanId).innerHTML = "<font color='green'><b>Volumetric Billing</b></font>";
	} else {
		// alert(readType+'<>'+document.getElementById('consumerTypeMR').value+'<>'+meterReadStatus);
		// if(document.getElementById('consumerTypeMR').value=='METERED' &&
		// meterReadStatus!=''){
		if (meterReadStatus != '') { // Removed Consumer Type Consideration
										// by Matiur As per Discussion with Yash
										// on 12-12-2012
			document.getElementById(currentMeterReadId).value = "0";
			currentMeterRegisterReadMR = "0";
			document.getElementById(currentMeterReadConfirmId).value = "0";
			document.getElementById(currentMeterReadId).disabled = true;
			document.getElementById(currentMeterReadConfirmId).disabled = true;
			document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='red'><b>No Read</b></font>";
			document.getElementById(billTypeSpanId).innerHTML = "<font color='red'><b>"
					+ readType + "</b></font>";
		}
		// if(document.getElementById('consumerTypeMR').value!='METERED'
		// ||meterReadStatus==''){
		if (meterReadStatus == '') {// Removed Consumer Type Consideration by
									// Matiur As per Discussion with Yash on
									// 12-12-2012
			document.getElementById(currentMeterReadId).disabled = true;
			document.getElementById(currentMeterReadConfirmId).disabled = true;
			document.getElementById(meterReadTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
			document.getElementById(billTypeSpanId).innerHTML = "<font color='#000'><b>NA</b></font>";
		}
	}
}
function fnGetReadTypeMR(meterReaderRemarkCode) {
	var readType = "No Billing";
	if (Trim(meterReaderRemarkCode).length != 0) {
		if (regularReadTypeMR.indexOf(meterReaderRemarkCode) > -1) {
			readType = "Volumetric Billing";
			return readType;
		}
		if (averageReadTypeMR.indexOf(meterReaderRemarkCode) > -1) {
			readType = "Average Billing";
			return readType;
		}
		if (provisionalReadTypeMR.indexOf(meterReaderRemarkCode) > -1) {
			readType = "Provisional Billing";
			return readType;
		}
		if (noBillingReadTypeMR.indexOf(meterReaderRemarkCode) > -1) {
			readType = "No Billing";
			return readType;
		}
	}
	return readType;
}
function fnCheckOldMeterReadRemarkCode(obj) {
	var readType = fnGetReadTypeMR(Trim(obj.value));
	if (readType.toString() != 'Volumetric Billing') {
		/*
		 * if(document.getElementById('consumerTypeMR').value=='METERED'&&
		 * (meterReadStatus=='OK'||meterReadStatus=='PUMP')){
		 * document.getElementById('oldMeterRegisterReadMR').disabled=false;
		 * document.getElementById('oldMeterRegisterReadConfirmMR').disabled=false;
		 * }else{
		 */
		document.getElementById('oldMeterRegisterReadMR').disabled = true;
		document.getElementById('oldMeterRegisterReadConfirmMR').disabled = true;
		// }
	} else {
		document.getElementById('oldMeterRegisterReadMR').disabled = false;
		document.getElementById('oldMeterRegisterReadConfirmMR').disabled = false;
	}

}
function fnCheckBillRound() {
	if (Trim(document.getElementById('billRoundMR').value).length == 0) {
		document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'><b>Please Select Bill Round First</b></font>";
		if (!isPopUp) {
			document.getElementById('zoneMR').value = "";
			// document.getElementById('zoneMR').disabled=true;
		}
		document.getElementById('billRoundMR').focus();
	} else {
		document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'><b></b></font>";
	}
}
function fnSetDefaultValue() {
	if (Trim(document.getElementById('zeroReadMR').value) == '0') {
		zeroReadMR = "0";
	}
}
function fnResetDeatils() {
	document.getElementById('zoneMR').value = "";
	document.getElementById('zoneMR').disabled = false;
	document.getElementById('mrNoMR').value = "";
	document.getElementById('mrNoMR').disabled = false;
	document.getElementById('areaMR').value = "";
	document.getElementById('areaMR').disabled = false;
	document.getElementById('wcNoMR').value = "";
	document.getElementById('wcNoMR').disabled = false;
	document.getElementById('knoMR').value = "";
	document.getElementById('knoMR').disabled = false;
	document.getElementById('nameMR').value = "";
	document.getElementById('saType').value = "";
	document.getElementById('saType').disabled = false;
}
function fnEnableSaveButton() {
	if(document.getElementById('btnSaveMR')){
		document.getElementById('btnSaveMR').disabled = false;
	}
}
// Start- Added by Matiur Rahman on 30-11-2012 as per JTrac Issue # DJB-720
function fnCheckModel(obj) {
	fnGetModel();
	if (Trim(document.getElementById('manufacturerMR').value) == 'N/A'
			&& Trim(document.getElementById('modelMR').value) != 'N/A W') {
		document.getElementById('modelMR').value = "N/A W";
		document.getElementById('modelMR').disabled = true;
	}/*
		 * else{ document.getElementById('modelMR').value="";
		 * document.getElementById('modelMR').disabled=false; }
		 */
	if (Trim(document.getElementById('manufacturerMR').value) == 'ITRON') {
		document.getElementById('meterType').value = "MTR_GOVT_O";
		document.getElementById('meterType').disabled = true;
	} else {
		document.getElementById('meterType').value = "";
		document.getElementById('meterType').disabled = false;
	}
}
// End- Added by Matiur Rahman on 30-11-2012 as per JTrac Issue # DJB-720
function fnCheckSAType() {
	if (Trim(document.getElementById('saType').value) == 'UNMTRD') {
		document.getElementById('saType').value = "";
	}
}
function fnCheckManufacturer() {
	if (Trim(document.getElementById('manufacturerMR').value) == '') {
		document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'><b>Please Select Manufacturer First</b></font>";
		document.getElementById('manufacturerMR').focus();
	}
}
function fnSetOldMeterReadDate(obj) {
	if (Trim(obj.value).length == 10) {
		document.getElementById('oldMeterReadDate').value = shiftDate(
				obj.value, "-1");
	} else {
		document.getElementById('oldMeterReadDate').value = "";
	}
}
/* Save Meter replacement details. */
function doMeterReplacementAjaxCall() {
	if (validateMRReplacementDetails()) {
		document.getElementById('mrMessage').innerHTML = "";
		document.getElementById('mrMessage').title = "Server Message.";
		document.getElementById('hidActionMR').value = "saveDetails";
		var url = "callMeterReplacementAJax.action?hidAction="
				+ document.getElementById('hidActionMR').value + "&seqNo="
				+ document.getElementById('slNo').value;
		// Meter Reader Field Visit Remarks
		url += "&meterReaderName="
				+ document.getElementById('meterReaderName').value;
		url += "&billRound=" + document.getElementById('billRoundMR').value;
		// Customer Information
		url += "&zone=" + document.getElementById('zoneMR').value;
		url += "&mrNo=" + document.getElementById("mrNoMR").value;
		url += "&area=" + document.getElementById("areaMR").value;
		url += "&wcNo=" + document.getElementById('wcNoMR').value;
		url += "&kno=" + document.getElementById('knoMR').value;
		url += "&name=" + document.getElementById('nameMR').value;
		// Property Details
		url += "&waterConnectionSize="
				+ document.getElementById('waterConnectionSizeMR').value;
		url += "&waterConnectionUse="
				+ document.getElementById("waterConnectionUseMR").value;
		// Last Active Meter Installation Details
		url += "&lastActiveMeterInstalDate="
				+ document.getElementById('lastActiveMeterInstalDate').value;
		url += "&lastActiveMeterRead="
				+ document.getElementById("lastActiveMeterRead").value;
		url += "&lastActiveMeterRemarkCode="
				+ document.getElementById("lastActiveMeterRemarkCode").value;
		url += "&nextValidMeterInstalldate="
				+ document.getElementById("nextValidMeterInstalldate").value;
		// New Meter Details
		url += "&meterType=" + document.getElementById('meterType').value;
		url += "&badgeNo=" + Trim(document.getElementById("badgeNoMR").value);
		url += "&manufacturer="
				+ document.getElementById("manufacturerMR").value;
		url += "&model=" + document.getElementById('modelMR').value;
		url += "&saType=" + document.getElementById('saType').value;
		url += "&consumerType="
				+ document.getElementById('consumerTypeMR').value;
		url += "&consumerCategory="
				+ document.getElementById('consumerCategoryMR').value;
		// url+="&isLNTServiceProvider="+document.getElementById('isLNTServiceProvider').value;
		url += "&meterInstalDate=" + meterInstalDate;
		url += "&zeroRead=" + roundOffDecimal(Trim(zeroReadMR),2);
		url += "&zeroReadRemarkCode="
				+ Trim(document.getElementById('zeroReadRemarkCode').value);
		// New Meter Read Details
		url += "&currentMeterReadDate=" + currentMeterReadDateMR;
		url += "&currentMeterReadRemarkCode="
				+ Trim(document.getElementById('currentMeterReadRemarkCode').value);
		url += "&currentMeterRegisterRead=" + roundOffDecimal(currentMeterRegisterReadMR,2);
		url += "&newAverageConsumption=" + roundOffDecimal(newAverageConsumptionMR,2);
		url += "&noOfFloors=" + noOfFloorsMR;
		// Old Meter Last Read Details
		url += "&oldSAType="
				+ Trim(document.getElementById('oldSATypeMR').value);
		url += "&oldSAStartDate="
				+ document.getElementById('oldSAStartDate').value;
		url += "&oldMeterReadDate="
				+ document.getElementById('oldMeterReadDate').value;
		url += "&oldMeterReadRemarkCode="
				+ Trim(document.getElementById('oldMeterReadRemarkCode').value);
		url += "&oldMeterRegisterRead=" + roundOffDecimal(oldMeterRegisterReadMR,2);
		url += "&oldMeterAverageRead="+ roundOffDecimal(oldMeterAverageReadMR,2);
		//alert('url>>'+url);
		document.getElementById('btnSaveMR').disabled = true;
		var httpBowserObject = null;
		if (window.ActiveXObject) {
			httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			httpBowserObject = new XMLHttpRequest();
		} else {
			alert("Browser does not support AJAX...........");
			return;
		}
		// httpBowserObject = getHTTPObjectForBrowser();
		if (httpBowserObject != null) {
			if (!isPopUp) {
				window.focus();
				// window.scrollTo(0,0);
				hideScreen();
				// hideMRScreen();
				document.body.style.overflow = 'hidden';
			}
			if (isPopUp) {
				document.getElementById('ajax-result').innerHTML = "<div class='search-option'><font color='green' size='2'>Data Submited</font></div>";
			}
			httpBowserObject.open("POST", url, true);
			httpBowserObject.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			httpBowserObject.setRequestHeader("Connection", "close");
			httpBowserObject.onreadystatechange = function() {
				if (httpBowserObject.readyState == 4) {
					var responseString = httpBowserObject.responseText;
					// alert(responseString.length);
					if (null != responseString
							&& responseString.indexOf("ERROR:") > -1) {
						document.getElementById('btnSaveMR').disabled = false;
						document.getElementById('mrMessage').innerHTML = responseString;
						if (!isPopUp) {
							showScreen();
							document.body.style.overflow = 'auto';
						}
						if (isPopUp) {
							document.getElementById('ajax-result').innerHTML = "<div class='search-option'><font color='red' size='2'>Unable to process your last Request. Please Try again.</font></div>";
							popupMRScreen();
						}else{
							showScreen();
						}
						document.getElementById('meterReplaceStageID').value = "";
						document.getElementById('mrMessage').innerHTML = responseString;
						setStatusBarMessage('ERROR: Failed to save for KNO: ' + document
								.getElementById('knoMR').value + ' !');
					} else if (responseString.length < 1000) {
						if (!isPopUp) {
							showScreen();
							document.body.style.overflow = 'auto';
						}
						if (isPopUp) {
							// clearToUpdateVar();
							hideMRScreen();
							if (document.getElementById('ajax-result')) {
								document.getElementById('ajax-result').innerHTML = "<div class='search-option'>"
										+ httpBowserObject.responseText
										+ "</div>";
							}
							var fieldId = "consumerStatus" + currentPage;
							document.getElementById(fieldId).value = "30";
							fieldId = "hidConStatus" + currentPage;
							document.getElementById(fieldId).value = "30";
							currentMeterReadDateMR = currentMeterReadDateMR
									.toString();// - DD/MM/YYYY
							// alert(currentMeterReadDateMR+'<>'+currentMeterReadDateMR.length);
							if (currentMeterReadDateMR.length == 10) {
								fieldId = "meterReadDD" + currentPage;
								document.getElementById(fieldId).value = currentMeterReadDateMR
										.substr(0, 2);
								fieldId = "meterReadMM" + currentPage;
								document.getElementById(fieldId).value = currentMeterReadDateMR
										.substr(3, 2);
								fieldId = "meterReadYYYY" + currentPage;
								document.getElementById(fieldId).value = currentMeterReadDateMR
										.substr(6, 4);
							}
							fieldId = "meterReadStatus" + currentPage;
							document.getElementById(fieldId).value = Trim(document
									.getElementById('currentMeterReadRemarkCode').value);
							fieldId = "hidMeterStatus" + currentPage;
							document.getElementById(fieldId).value = Trim(document
									.getElementById('currentMeterReadRemarkCode').value);
							fieldId = "currentMeterRead" + currentPage;
							document.getElementById(fieldId).value = currentMeterRegisterReadMR;
							fieldId = "newAverageConsumption" + currentPage;
							document.getElementById(fieldId).value = newAverageConsumptionMR;
							fieldId = "newNoOfFloor" + currentPage;
							document.getElementById(fieldId).value = noOfFloorsMR;
							// setOnLoadValue();
						}
						document.getElementById('mrMessage').innerHTML = responseString;
						setStatusBarMessage('For KNO: ' + document
								.getElementById('knoMR').value + ' Saved Successfully.');
					} else {
						document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'><b>There was a problem while processing. Please Retry.</b></font>";
					}
				}
			};
			httpBowserObject.send(null);
		}
	}
}
// Start-Added By Matiur Rahman on 12-12-2012 as per discussion with Yash
// Reset Meter Replacement Details in data base
function resetMeterReplacementAjaxCall() {
	if (knoToResetMR != '' && billRoundToResetMR != '') {
		if (confirm("Reset will delete data from Database as well\nClick OK to Reset, else Cancel")) {
			document.getElementById('mrMessage').innerHTML = "";
			document.getElementById('mrMessage').title = "Server Message.";
			document.getElementById('hidActionMR').value = "resetMRDetails";
			var url = "callMeterReplacementAJax.action?hidAction=resetMRDetails&seqNo="
					+ document.getElementById('slNo').value;
			url += "&meterReaderName="
					+ document.getElementById('meterReaderName').value
					+ "&billRound=" + billRoundToResetMR;
			url += "&zone=" + document.getElementById('zoneMR').value
					+ "&mrNo=" + document.getElementById("mrNoMR").value
					+ "&area=" + document.getElementById("areaMR").value
					+ "&wcNo=" + document.getElementById('wcNoMR').value
					+ "&kno=" + knoToResetMR + "&name="
					+ document.getElementById('nameMR').value;
			url += "&waterConnectionSize="
					+ document.getElementById('waterConnectionSizeMR').value
					+ "&waterConnectionUse="
					+ document.getElementById("waterConnectionUseMR").value;
			url += "&meterType=" + document.getElementById('meterType').value
					+ "&badgeNo=" + document.getElementById("badgeNoMR").value
					+ "&manufacturer="
					+ document.getElementById("manufacturerMR").value
					+ "&model=" + document.getElementById('modelMR').value;
			url += "&saType=" + document.getElementById('saType').value;
			url += "&consumerType="
					+ document.getElementById('consumerTypeMR').value;
			url += "&consumerCategory="
					+ document.getElementById('consumerCategoryMR').value;
			url += "&isLNTServiceProvider="
					+ document.getElementById('isLNTServiceProvider').value;
			url += "&meterInstalDate=" + meterInstalDate + "&zeroRead="
					+ zeroReadMR;
			url += "&currentMeterReadDate=" + currentMeterReadDateMR
					+ "&currentMeterRegisterRead=" + currentMeterRegisterReadMR
					+ "&newAverageConsumption=" + newAverageConsumptionMR;
			url += "&currentMeterReadRemarkCode="
					+ document.getElementById('currentMeterReadRemarkCode').value
					+ "&oldMeterRegisterRead=" + oldMeterRegisterReadMR;
			url += "&oldMeterReadRemarkCode="
					+ document.getElementById('oldMeterReadRemarkCode').value;
			url += "&oldMeterAverageRead=" + oldMeterAverageReadMR;
			// alert('url>>'+url);
			document.getElementById('btnSaveMR').disabled = true;
			var httpBowserObject = null;
			if (window.ActiveXObject) {
				httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
			} else if (window.XMLHttpRequest) {
				httpBowserObject = new XMLHttpRequest();
			} else {
				alert("Browser does not support AJAX...........");
				return;
			}
			// httpBowserObject = getHTTPObjectForBrowser();
			if (httpBowserObject != null) {
				if (!isPopUp) {
					window.focus();
					// window.scrollTo(0,0);
					hideScreen();
					// hideMRScreen();
					document.body.style.overflow = 'hidden';
				}
				if (isPopUp) {
					document.getElementById('ajax-result').innerHTML = "<div class='search-option'><font color='green' size='2'>Data Submited</font></div>";
				}
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						// alert(httpBowserObject.responseText);
						var responseString = httpBowserObject.responseText;
						if (null != responseString
								&& responseString.indexOf("ERROR:") > -1) {
							document.getElementById('btnSaveMR').disabled = false;
							document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'>Unable to process your last Request. Please Try again.</font>";
							if (!isPopUp) {
								showScreen();
								document.body.style.overflow = 'auto';
							}
							if (isPopUp) {
								document.getElementById('ajax-result').innerHTML = "<div class='search-option'><font color='red' size='2'>Unable to process your last Request. Please Try again.</font></div>";
								popupMRScreen();
							}
							setStatusBarMessage('ERROR: Failed to Reset Details for KNO: ' + knoToResetMR + ' !');
						} else {
							if (!isPopUp) {
								showScreen();
								document.body.style.overflow = 'auto';
							}
							if (isPopUp) {
								clearToUpdateVar();
								hideMRScreen();
								document.getElementById('ajax-result').innerHTML = "<div class='search-option'>"
										+ httpBowserObject.responseText
										+ "</div>";
								var fieldId = "consumerStatus" + currentPage;
								document.getElementById(fieldId).value = "60";
								fieldId = "hidConStatus" + currentPage;
								document.getElementById(fieldId).value = "60";
								fieldId = "meterReadDD" + currentPage;
								document.getElementById(fieldId).value = "";
								fieldId = "meterReadMM" + currentPage;
								document.getElementById(fieldId).value = "";
								fieldId = "meterReadYYYY" + currentPage;
								document.getElementById(fieldId).value = "";
								fieldId = "meterReadStatus" + currentPage;
								document.getElementById(fieldId).value = "";
								fieldId = "hidMeterStatus" + currentPage;
								document.getElementById(fieldId).value = "";
								fieldId = "currentMeterRead" + currentPage;
								document.getElementById(fieldId).value = "";
								fieldId = "newAverageConsumption" + currentPage;
								document.getElementById(fieldId).value = "";
								fieldId = "newNoOfFloor" + currentPage;
								document.getElementById(fieldId).value = "";
								setOnLoadValue();
							}
							document.getElementById('mrMessage').innerHTML = responseString;
							setStatusBarMessage('For KNO: ' + knoToResetMR + ' Successfully Reset.');
							clearAllFields();
							if (document
									.getElementById('waterConnectionSizeMR')) {
								document
										.getElementById('waterConnectionSizeMR').value = "15";
							}
							if (document.getElementById('badgeNoMR')) {
								document.getElementById('badgeNoMR').value = "NA";
							}
							if (document.getElementById('zeroReadMR')) {
								document.getElementById('zeroReadMR').value = "0";
							}
							if (document.getElementById('zeroReadConfirmMR')) {
								document.getElementById('zeroReadConfirmMR').value = "0";
							}
							if (document.getElementById('zeroReadRemarkCode')) {
								document.getElementById('zeroReadRemarkCode').value = "OK";
								document.getElementById('zeroReadRemarkCode').disabled = true;
							}
						}
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
}
// End-Added By Matiur Rahman on 12-12-2012

/* Retrieve Consumer Details By KNO. */
function fnGetConsumerDetailsByKNO() {
	if (Trim(document.getElementById('knoMR').value).length == 10) {
		document.getElementById('mrMessage').innerHTML = "";
		document.getElementById('mrMessage').title = "Server Message.";
		document.getElementById('hidActionMR').value = "getConsumerDetailsByKNO";
		var url = "callMeterReplacementAJax.action?hidAction="
				+ document.getElementById('hidActionMR').value;
		url += "&kno=" + document.getElementById('knoMR').value;
		var httpBowserObject = null;
		if (window.ActiveXObject) {
			httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			httpBowserObject = new XMLHttpRequest();
		} else {
			alert("Browser does not support AJAX...........");
			return;
		}
		if (httpBowserObject != null) {
			/*
			 * if(isPopUp){ popupMRScreen(); }
			 */
			if (!isPopUp) {
				window.focus();
				// window.scrollTo(0,0);
				hideScreen();
				// hideMRScreen();
				document.body.style.overflow = 'hidden';
			}
			if (isPopUp) {
				document.getElementById('ajax-result').innerHTML = "<div class='search-option'><font color='green' size='2'>&nbsp;</font></div>";
			}
			// alert('url>>'+url);
			httpBowserObject.open("POST", url, true);
			httpBowserObject.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			httpBowserObject.setRequestHeader("Connection", "close");
			httpBowserObject.setRequestHeader("Expires", "0");
			httpBowserObject.setRequestHeader("Pragma", "cache");
			httpBowserObject.setRequestHeader("Cache-Control", "private");
			httpBowserObject.onreadystatechange = function() {
				if (httpBowserObject.readyState == 4) {
					var responseString = httpBowserObject.responseText;
					//alert(httpBowserObject.responseText);
					fnProcessGetConsumerDetailsResponse(
							"fnGetConsumerDetailsByKNO", responseString);
				}
				knoToResetMR = Trim(document.getElementById('knoMR').value);
				billRoundToResetMR = Trim(document
						.getElementById('billRoundMR').value);
			};
			httpBowserObject.send(null);
		}
	}
}
/* Retrieve Consumer Details By Zone MR Area WC No. */
function fnGetConsumerDetailsByZMAW() {
	if (Trim(document.getElementById('zoneMR').value).length != 0
			&& Trim(document.getElementById('mrNoMR').value).length != 0
			&& Trim(document.getElementById('areaMR').value).length != 0
			&& Trim(document.getElementById('wcNoMR').value).length != 0) {
		document.getElementById('mrMessage').innerHTML = "";
		document.getElementById('mrMessage').title = "Server Message";
		document.getElementById('hidActionMR').value = "getConsumerDetailsByZMAW";
		var url = "callMeterReplacementAJax.action?hidAction="
				+ document.getElementById('hidActionMR').value + "&seqNo="
				+ document.getElementById('slNo').value;
		url += "&meterReaderName="
				+ document.getElementById('meterReaderName').value
				+ "&billRound=" + document.getElementById('billRoundMR').value;
		url += "&zone=" + document.getElementById('zoneMR').value + "&mrNo="
				+ document.getElementById("mrNoMR").value + "&area="
				+ document.getElementById("areaMR").value + "&wcNo="
				+ document.getElementById('wcNoMR').value + "&kno="
				+ document.getElementById('knoMR').value + "&name="
				+ document.getElementById('nameMR').value;
		url += "&waterConnectionSize="
				+ document.getElementById('waterConnectionSizeMR').value
				+ "&waterConnectionUse="
				+ document.getElementById("waterConnectionUseMR").value;
		url += "&meterType=" + document.getElementById('meterType').value
				+ "&badgeNo=" + document.getElementById("badgeNoMR").value
				+ "&manufacturer="
				+ document.getElementById("manufacturerMR").value + "&model="
				+ document.getElementById('modelMR').value;
		url += "&saType=" + document.getElementById('saType').value;
		// url+="&isLNTServiceProvider="+document.getElementById('isLNTServiceProvider').value;
		url += "&meterInstalDate=" + meterInstalDate + "&zeroRead="
				+ zeroReadMR;
		url += "&currentMeterReadDate=" + currentMeterReadDateMR
				+ "&currentMeterRegisterRead=" + currentMeterRegisterReadMR
				+ "&newAverageConsumption=" + newAverageConsumptionMR;
		url += "&currentMeterReadRemarkCode="
				+ document.getElementById('currentMeterReadRemarkCode').value
				+ "&oldMeterRegisterRead=" + oldMeterRegisterReadMR;
		url += "&oldMeterReadRemarkCode="
				+ document.getElementById('oldMeterReadRemarkCode').value;
		var httpBowserObject = null;
		if (window.ActiveXObject) {
			httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			httpBowserObject = new XMLHttpRequest();
		} else {
			alert("Browser does not support AJAX...........");
			return;
		}
		// httpBowserObject = getHTTPObjectForBrowser();
		if (httpBowserObject != null) {
			/*
			 * if(isPopUp){ popupMRScreen(); }
			 */
			if (!isPopUp) {
				window.focus();
				// window.scrollTo(0,0);
				hideScreen();
				// hideMRScreen();
				document.body.style.overflow = 'hidden';
			}
			if (isPopUp) {
				document.getElementById('ajax-result').innerHTML = "<div class='search-option'><font color='green' size='2'>&nbsp;</font></div>";
			}
			// alert('url>>'+url);
			httpBowserObject.open("POST", url, true);
			httpBowserObject.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			httpBowserObject.setRequestHeader("Connection", "close");
			httpBowserObject.onreadystatechange = function() {
				if (httpBowserObject.readyState == 4) {
					var responseString = httpBowserObject.responseText;
					// alert(httpBowserObject.responseText);
					fnProcessGetConsumerDetailsResponse(
							"fnGetConsumerDetailsByZMAW", responseString);
				}
				knoToResetMR = Trim(document.getElementById('knoMR').value);
				billRoundToResetMR = Trim(document
						.getElementById('billRoundMR').value);
			};
			httpBowserObject.send(null);
		}
	}
}
function fnGetModel() {
	document.getElementById('mrMessage').innerHTML = "";
	document.getElementById('mrMessage').title = "Server Message";
	document.getElementById('hidActionMR').value = "getModel";
	var url = "callMeterReplacementAJax.action?hidAction="
			+ document.getElementById('hidActionMR').value + "&manufacturer="
			+ document.getElementById("manufacturerMR").value;
	var httpBowserObject = null;
	if (window.ActiveXObject) {
		httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		httpBowserObject = new XMLHttpRequest();
	} else {
		alert("Browser does not support AJAX...........");
		return;
	}
	if (httpBowserObject != null) {
		/*
		 * if(isPopUp){ popupMRScreen(); }
		 */
		if (!isPopUp) {
			window.focus();
			// window.scrollTo(0,0);
			hideScreen();
			// hideMRScreen();
			document.body.style.overflow = 'hidden';
		}
		// alert('url>>'+url);
		httpBowserObject.open("POST", url, true);
		httpBowserObject.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		httpBowserObject.setRequestHeader("Connection", "close");
		httpBowserObject.onreadystatechange = function() {
			if (httpBowserObject.readyState == 4) {
				var responseString = httpBowserObject.responseText;
				// alert(httpBowserObject.responseText);
				if (null == responseString) {
					document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No Model found corresponding to the Manufracturer. </b></font>";
					document.getElementById('mrMessage').title = "";
					if (!isPopUp) {
						showScreen();
						document.body.style.overflow = 'auto';
					}
				} else {
					if (!isPopUp) {
						showScreen();
						document.body.style.overflow = 'auto';
					}
					document.getElementById('mrModelTD').innerHTML = responseString;
				}
			}
		};
		httpBowserObject.send(null);
	}
}
function fnGetMRNo() {
	var zoneMR = Trim(document.getElementById('zoneMR').value);
	if (zoneMR != '') {
		var url = "callMeterReplacementAJax.action?hidAction=populateMRNo&zone="
				+ zoneMR;
		var httpBowserObject = null;
		if (window.ActiveXObject) {
			httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			httpBowserObject = new XMLHttpRequest();
		} else {
			alert("Browser does not support AJAX...........");
			return;
		}
		if (httpBowserObject != null) {
			/*
			 * if(isPopUp){ popupMRScreen(); }
			 */
			if (!isPopUp) {
				window.focus();
				// window.scrollTo(0,0);
				hideScreen();
				// hideMRScreen();
				document.body.style.overflow = 'hidden';
			}
			// alert('url>>'+url);
			httpBowserObject.open("POST", url, true);
			httpBowserObject.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			httpBowserObject.setRequestHeader("Connection", "close");
			httpBowserObject.onreadystatechange = function() {
				if (httpBowserObject.readyState == 4) {
					var responseString = httpBowserObject.responseText;
					// alert(httpBowserObject.responseText);
					if (null == responseString
							|| responseString.indexOf("ERROR:") > -1) {
						document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No MR No found corresponding to the zone. </b></font>";
						document.getElementById('mrMessage').title = "";
						if (!isPopUp) {
							showScreen();
							document.body.style.overflow = 'auto';
						}
					} else {
						if (!isPopUp) {
							showScreen();
							document.body.style.overflow = 'auto';
						}
						document.getElementById('mrNoTD').innerHTML = responseString;
						document.getElementById('areaMR').value = "";
						document.getElementById('areaMR').disabled = true;
					}
				}
			};
			httpBowserObject.send(null);
		}
	}
}
function fnGetArea() {
	var zoneMR = Trim(document.getElementById('zoneMR').value);
	var mrNoMR = Trim(document.getElementById('mrNoMR').value);
	var url = "callMeterReplacementAJax.action?hidAction=populateArea&zone="
			+ zoneMR + "&mrNo=" + mrNoMR;
	var httpBowserObject = null;
	if (window.ActiveXObject) {
		httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		httpBowserObject = new XMLHttpRequest();
	} else {
		alert("Browser does not support AJAX...........");
		return;
	}
	if (httpBowserObject != null) {
		if (!isPopUp) {
			window.focus();
			hideScreen();
			// hideMRScreen();
			document.body.style.overflow = 'hidden';
		}
		// alert('url>>'+url);
		httpBowserObject.open("POST", url, true);
		httpBowserObject.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		httpBowserObject.setRequestHeader("Connection", "close");
		httpBowserObject.onreadystatechange = function() {
			if (httpBowserObject.readyState == 4) {
				var responseString = httpBowserObject.responseText;
				// alert(httpBowserObject.responseText);
				if (null == responseString
						|| responseString.indexOf("ERROR:") > -1) {
					document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No Area found corresponding to the Zone and Mr No. </b></font>";
					document.getElementById('mrMessage').title = "";
					if (!isPopUp) {
						showScreen();
						document.body.style.overflow = 'auto';
					}
				} else {
					if (!isPopUp) {
						showScreen();
						document.body.style.overflow = 'auto';
					}
					document.getElementById('areaTD').innerHTML = responseString;
				}
			}
		};
		httpBowserObject.send(null);
	}
}
function fnResetPreviousMeterReadDetails(){	
	document.getElementById("prevMeterReadDateMR").value = "";	
	document.getElementById("prevMeterReadMR").value = "";	
	document.getElementById("prevMeterReadRemarkMR").value = "";
	document.getElementById("prevActMeterReadDateMR").value = "";
	document.getElementById("prevActMeterReadMR").value = "";
	document.getElementById("prevActMeterReadRemarkMR").value = "";
	document.getElementById("oldMeterLastReadDetailsRowId").title="Previous Meter Read Details populated After Entering Meter Installation Date/Old Meter Reader Remark Code.";
}
//Start:Added on 01-04-2013 by Matiur Rahman
function fnGetPreviousMeterReadDetails() {
	var knoMR = Trim(document.getElementById('knoMR').value);
	var oldSAStartDate = Trim(document.getElementById('oldSAStartDate').value);
	if(knoMR.length==10 &&meterInstalDate.length==10&& oldSAStartDate.length==10){
		fnDisableButtons();
		knoToGetPrevReadDetails=knoMR;
		meterInstalDateToGetPrevReadDetails=meterInstalDate;
		var url = "callMeterReplacementAJax.action?hidAction=populatePreviousMeterReadDetails&kno="
				+ knoMR + "&meterInstalDate="+meterInstalDate+"&oldSAStartDate=" + oldSAStartDate;
		var httpBowserObject = null;
		if (window.ActiveXObject) {
			httpBowserObject = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			httpBowserObject = new XMLHttpRequest();
		} else {
			alert("Browser does not support AJAX...........");
			return;
		}
		if (httpBowserObject != null) {			
			//alert('url>>'+url);
			httpBowserObject.open("POST", url, true);
			httpBowserObject.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			httpBowserObject.setRequestHeader("Connection", "close");
			httpBowserObject.onreadystatechange = function() {
				if (httpBowserObject.readyState == 4) {
					var responseString = httpBowserObject.responseText;
					// alert(httpBowserObject.responseText);
					if (null == responseString
							|| responseString.indexOf("ERROR:") > -1) {
						document.getElementById('mrMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No Details found corresponding to the KNO.</b></font>";
						document.getElementById('mrMessage').title = "";						
					} else {			
						var prevMeterReadDetails = responseString.substring(
									responseString.indexOf("<PMRD>") + 6, responseString
											.indexOf("</PMRD>"));
						processPrevMeterReadDetails(prevMeterReadDetails);
						var prevActualMeterReadDetails = responseString.substring(
								responseString.indexOf("<PAMR>") + 6, responseString
										.indexOf("</PAMR>"));
						processPrevActualMeterReadDetails(prevActualMeterReadDetails);		
						
					}
					fnCheckOldMeterReadStatusMR(document.getElementById('oldMeterReadRemarkCode'));
					fnEnableButtons();
				}
			};
			httpBowserObject.send(null);
		}
	}
}
function processPrevMeterReadDetails(inputString) {
	/** split the string and remove the last one*/
	var inputStringArray = inputString.split('|'); 
	//alert('processPrevMeterReadDetails\n'+inputString+'\n'+inputStringArray);
	var i = 0;
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var prevMeterReadDateMR = Trim(inputStringArray[i]);
		document.getElementById("prevMeterReadDateMR").value = prevMeterReadDateMR;
	} else {
		var prevMeterReadDateMR = "";
		document.getElementById("prevMeterReadDateMR").value = prevMeterReadDateMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var prevMeterReadMR = Trim(inputStringArray[i]);
		document.getElementById("prevMeterReadMR").value = prevMeterReadMR;
	} else {
		var prevMeterReadMR = "";
		document.getElementById("prevMeterReadMR").value = prevMeterReadMR;
	}
	i++;
	//Read Type
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var prevMeterReadRemarkMR = Trim(inputStringArray[i]);
		document.getElementById("prevMeterReadRemarkMR").value = prevMeterReadRemarkMR;
	} else {
		var prevMeterReadRemarkMR = "";
		document.getElementById("prevMeterReadRemarkMR").value = prevMeterReadRemarkMR;
	}
	//alert('prevMeterReadDateMR::'+document.getElementById("prevMeterReadDateMR").value+'::prevMeterReadMR::'+document.getElementById("prevMeterReadMR").value+'prevMeterReadRemarkMR::'+document.getElementById("prevMeterReadRemarkMR").value);
}
function processPrevActualMeterReadDetails(inputString) {
	/** split the string and remove the last one*/
	var inputStringArray = inputString.split('|'); 
	//alert('processPrevActualMeterReadDetails\n'+inputString+'\n'+inputStringArray);
	var i = 0;
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var prevActMeterReadDateMR = Trim(inputStringArray[i]);
		document.getElementById("prevActMeterReadDateMR").value = prevActMeterReadDateMR;
	} else {
		var prevActMeterReadDateMR = "";
		document.getElementById("prevActMeterReadDateMR").value = prevActMeterReadDateMR;
	}
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var prevActMeterReadMR = Trim(inputStringArray[i]);
		document.getElementById("prevActMeterReadMR").value = prevActMeterReadMR;
	} else {
		var prevActMeterReadMR = "";
		document.getElementById("prevActMeterReadMR").value = prevActMeterReadMR;
	}
	i++;
	//Read Type
	i++;
	if (null != inputStringArray[i] && 'undefined' != inputStringArray[i]
			&& 'null' != inputStringArray[i]) {
		var prevActMeterReadRemarkMR = Trim(inputStringArray[i]);
		document.getElementById("prevActMeterReadRemarkMR").value = prevActMeterReadRemarkMR;
	} else {
		var prevActMeterReadRemarkMR = "";
		document.getElementById("prevActMeterReadRemarkMR").value = prevActMeterReadRemarkMR;
	}
	//alert('prevActMeterReadDateMR::'+document.getElementById("prevActMeterReadDateMR").value+'::prevActMeterReadMR::'+document.getElementById("prevActMeterReadMR").value+'prevActMeterReadRemarkMR::'+document.getElementById("prevActMeterReadRemarkMR").value);
}
function fnDisableButtons(){
	if(document.getElementById('btnSaveMR')){
		document.getElementById('btnSaveMR').disabled=true;
	}
	if(document.getElementById('btnResetMR')){
		document.getElementById('btnResetMR').disabled=true;
	}
}
function fnEnableButtons(){
	if(document.getElementById('btnSaveMR')){
		document.getElementById('btnSaveMR').disabled=false;
	}
	if(document.getElementById('btnResetMR')){
		document.getElementById('btnResetMR').disabled=false;
	}
}
//End:Added on 01-04-2013 by Matiur Rahman