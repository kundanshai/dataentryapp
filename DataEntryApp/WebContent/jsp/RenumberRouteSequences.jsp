<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.tcs.djb.model.*"%>
<%@ page language="java" import="com.tcs.djb.util.*"%>
<html>
<%
	try {
%>
<head>
<title>Renumbering Route Sequences Page - Revenue Management System, Delhi
Jal Board</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="description"
	content="Revenue Management System,Delhi Jal Board ">
<meta name="copyright" content="&copy;2012 Delhi Jal Board">
<meta name="author" content="Tata Consultancy Services">
<meta name="keywords"
	content="djb,DJB,Revenue Management System,Delhi Jal Board">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta name="googlebot" content="noarchive">
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/custom.css"/>" />
<script type="text/javascript"
	src="<s:url value="/js/UtilityScripts.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/jquery-1.3.2.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<s:url value="/css/menu.css"/>" />
<script language=javascript>
	function disableBack() {
		window.history.forward(1);
	}
	disableBack();
	function fnGetZoneMRAreaByMRKEY(){
		var selectedMRKEY = Trim(document.getElementById('selectedMRKEY').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		var url = "renumberRouteSequencesAJax.action?hidAction=populateZoneMRAreaByMRKEY&selectedMRKEY="+ selectedMRKEY;
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
			document.getElementById('selectedZone').value = "";
			document.getElementById('selectedZone').disabled=true;
			document.getElementById('selectedMRNo').value = "";
			document.getElementById('selectedMRNo').disabled=true;
			document.getElementById('selectedArea').value = "";
			document.getElementById('selectedArea').disabled = true;
			showAjaxLoading(document.getElementById('imgSelectedZone'));
			showAjaxLoading(document.getElementById('imgSelectedMRNo'));
			showAjaxLoading(document.getElementById('imgSelectedArea'));
			httpBowserObject.open("POST", url, true);
			httpBowserObject.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			httpBowserObject.setRequestHeader("Connection", "close");
			httpBowserObject.onreadystatechange = function() {
				if (httpBowserObject.readyState == 4) {
					var responseString = httpBowserObject.responseText;
					if (null == responseString
							|| responseString.indexOf("ERROR:") > -1) {
						document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";
					} else {
						document.getElementById('selectedZone').value = Trim(responseString.substring(responseString.indexOf("<ZONE>") + 6, responseString.indexOf("</ZONE>")));	
						document.getElementById('mrNoTD').innerHTML = Trim(responseString.substring(responseString.indexOf("<MRNO>") + 6, responseString.indexOf("</MRNO>")));
						document.getElementById('areaTD').innerHTML = Trim(responseString.substring(responseString.indexOf("<AREA>") + 6, responseString.indexOf("</AREA>")));				
					}
				}
				hideAjaxLoading(document
						.getElementById('imgSelectedZone'));
				hideAjaxLoading(document
						.getElementById('imgSelectedMRNo'));
				hideAjaxLoading(document
						.getElementById('imgSelectedArea'));
				document.getElementById('selectedZone').disabled=false;
			};
			httpBowserObject.send(null);
		}
	}
	function fnGetMRNo() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		document.getElementById('selectedMRKEY').value="";
		if (selectedZone != '') {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "renumberRouteSequencesAJax.action?hidAction=populateMRNo&selectedZone="
					+ selectedZone;
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
				showAjaxLoading(document.getElementById('imgSelectedZone'));
				document.getElementById('selectedMRNo').value = "";
				document.getElementById('selectedMRNo').disabled=true;
				document.getElementById('selectedArea').value = "";
				document.getElementById('selectedArea').disabled = true;
				document.getElementById('selectedMRKEY').disabled = true;
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No MR No found corresponding to the zone. </b></font>";
						} else {
							document.getElementById('mrNoTD').innerHTML = responseString;							
						}
						hideAjaxLoading(document
								.getElementById('imgSelectedZone'));
						document.getElementById('selectedMRKEY').disabled = false;
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnGetArea() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		document.getElementById('selectedMRKEY').value="";
		if ('' != selectedZone && '' != selectedMRNo) {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "renumberRouteSequencesAJax.action?hidAction=populateArea&selectedZone="
					+ selectedZone + "&selectedMRNo=" + selectedMRNo;
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
				showAjaxLoading(document.getElementById('imgSelectedMRNo'));
				document.getElementById('selectedArea').value = "";
				document.getElementById('selectedArea').disabled = true;
				document.getElementById('selectedMRKEY').disabled = true;
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: No Area found corresponding to the Zone and Mr No. </b></font>";
							document.getElementById('onscreenMessage').title = "";
						} else {
							document.getElementById('areaTD').innerHTML = responseString;
						}
						hideAjaxLoading(document
								.getElementById('imgSelectedMRNo'));
						document.getElementById('selectedMRKEY').disabled = false;
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnGetMRKEY() {
		var selectedZone = Trim(document.getElementById('selectedZone').value);
		var selectedMRNo = Trim(document.getElementById('selectedMRNo').value);
		var selectedArea = Trim(document.getElementById('selectedArea').value);
		document.getElementById('onscreenMessage').innerHTML = "";
		document.getElementById('selectedMRKEY').value="";
		if ('' != selectedZone && '' != selectedMRNo && ''!=selectedArea) {
			document.getElementById('onscreenMessage').innerHTML = "";
			var url = "renumberRouteSequencesAJax.action?hidAction=populateMRKEY&selectedZone="
					+ selectedZone + "&selectedMRNo=" + selectedMRNo+"&selectedArea="+selectedArea;
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
				showAjaxLoading(document.getElementById('imgSelectedArea'));
				document.getElementById('selectedMRKEY').disabled = true;
				httpBowserObject.open("POST", url, true);
				httpBowserObject.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				httpBowserObject.setRequestHeader("Connection", "close");
				httpBowserObject.onreadystatechange = function() {
					if (httpBowserObject.readyState == 4) {
						var responseString = httpBowserObject.responseText;
						if (null == responseString
								|| responseString.indexOf("ERROR:") > -1) {
							document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>ERROR: There was a problem, Please retry. </b></font>";
							document.getElementById('onscreenMessage').title = "";
						} else {
							document.getElementById('selectedMRKEY').value=responseString;
						}
						hideAjaxLoading(document
								.getElementById('imgSelectedArea'));
						document.getElementById('selectedMRKEY').disabled = false;
					    //fnGetServiceCycle();
					}
				};
				httpBowserObject.send(null);
			}
		}
	}
	function fnCheckZone() {
		if (document.forms[0].selectedZone.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Zone First</b></font>";
			if (!(document.forms[0].selectedZone.disabled)) {
				document.forms[0].selectedZone.focus();
			}
			return;
		}
	}
	function fnCheckZoneMRNo() {
		if (document.forms[0].selectedZone.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select Zone First</b></font>";
			if (!(document.forms[0].selectedZone.disabled)) {
				document.forms[0].selectedZone.focus();
			}
			return;
		}
		if (document.forms[0].selectedMRNo.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select MR No. First</b></font>";
			if (!(document.forms[0].selectedMRNo.disabled)) {
				document.forms[0].selectedMRNo.focus();
			}
			return;
		}
	}
	function fnCheckMRKEY() {
		if (document.forms[0].selectedMRKEY.value == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red' size='2'><b>Please Select MRKEY or Zone MR Area Combination First.</b></font>";
			if (!(document.forms[0].selectedMRKEY.disabled)) {
				document.forms[0].selectedMRKEY.focus();
			}
			return;
		}
	}
	function gotoSearch(){
 		if (Trim(document.forms[0].selectedMRKEY.value) == '') {
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Please Select a MRKEY or Zone MR Area Combination.</b></font>";
			document.forms[0].selectedMRKEY.focus();
			return false;
		}
		document.getElementById("selectedSPID").value="";
	 	fnSearch();
 	}
 	function fnSearch(){
 		document.forms[0].action = "renumberRouteSequences.action";
		document.forms[0].hidAction.value = "search";
	    document.getElementById('onscreenMessage').innerHTML = "";		
		hideScreen();
		document.forms[0].submit();
 	}	
 	function fnGoToRenumberRouteSequences() {
 		if(fnCheckForDuplicateBeforeSubmission()&& duplicateChecked){
 	 	 	if (Trim(document.forms[0].selectedMRKEY.value) == '') {
				document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Please Select a MRKEY or Zone MR Area Combination.</b></font>";
				document.forms[0].selectedMRKEY.focus();
				return false;
			}
	 		if (document.getElementById('searchResult')) {
	 	 		var spSelected=0;
				var rows = document.getElementById('searchResult').rows;
				var spIDValue;
				var newRouteSequencesValue;
				var oldRouteSequencesValue;
				for ( var i = 1; i <= rows.length; i++) {
					spId = "trSPID" + i;
					oldRouteSequencesId= "trOldRouteSequences" + i ;
					newRouteSequencesId= "trNewRouteSequences" + i ;
					if(document.getElementById(newRouteSequencesId)&&document.getElementById(oldRouteSequencesId)&&document.getElementById(spId)){
						spIDValue = document.getElementById(spId).value;
						newRouteSequencesValue = document.getElementById(newRouteSequencesId).value;
						oldRouteSequencesValue = document.getElementById(oldRouteSequencesId).value;
	                    if(newRouteSequencesValue!=null && newRouteSequencesValue!=''&&!(isNaN(newRouteSequencesValue)) && (newRouteSequencesValue).length<=8 && parseInt(newRouteSequencesValue)>0){
							if(oldRouteSequencesValue!=newRouteSequencesValue){
								//alert('length'+rows.length);
								spSelected++;
							    if((document.getElementById("selectedSPID").value).indexOf(document.getElementById(spId).value)==-1){
								    document.getElementById("selectedSPID").value=document.getElementById("selectedSPID").value+document.getElementById(spId).value+"-"+newRouteSequencesValue+",";
								}
							}
						}else{
						    document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Invalid New Route Sequence Value "+ newRouteSequencesValue +" At Row No. "+i+".</b></font>";
						    window.scrollTo(0,0);
						    return;
					   	}
				    }
				}
				//alert('spSelected'+document.getElementById("selectedSPID").value);
				fnRenumberRouteSequences();
			}
	 	 }else{
	 	 	return;
	 	}
	 }
 	var duplicateChecked=false;
 	function fnCheckForDuplicateBeforeSubmission(){
 		duplicateChecked=true;
 		var isValid=true;
		hideScreen();
		var tableLength = (document.getElementById('searchResult').rows).length;
		var sequenceId;
 		var sequenceValue;
 		var sequenceArray=new Array();	
 		for ( var i = 1; i <= tableLength; i++) { 	 		
 			sequenceId = "trNewRouteSequences" + i;
 			sequenceValue = document.getElementById(sequenceId).value;
 			//alert(sequenceId+'<>'+sequenceValue);
 			sequenceArray.push(sequenceValue);
 		}
 		sequenceArray.sort();
 		var last = sequenceArray[0];
 		for (var i=1; i<sequenceArray.length; i++) {
 	 		if (sequenceArray[i] == last){
 	 	 		isValid=false;
 	 		  	document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>You cannot proceed with Duplicate Route Sequence : " +last+" </b></font>";
 	 		    window.scrollTo(0,0);
 	 		  	break;
 	 		}
 		   	last = sequenceArray[i]; 		 
 		}
 		showScreen();
 		return isValid;
	}
 	function fnRenumberRouteSequences(){
		if(confirm('Are You Sure You Want to Process Old Route Sequences(s)?\nClick OK to continue else Cancel.')){
			document.forms[0].action = "renumberRouteSequences.action";
			document.forms[0].hidAction.value = "procesRouteSequences";
		    hideScreen();
			document.forms[0].submit();
	  	}else{
	  		document.getElementById("selectedSPID").value="";
	  		return;
	  	}	
	}
	function fnEnableField(obj){
		obj.disabled=false;
	}
	function fnDisableField(obj){
		obj.disabled=true;
	}
	function checkDuplicate(obj,rowNum){
		duplicateChecked=false;
		if(obj.value!=null && obj.value!='' && !(isNaN(obj.value)) && (obj.value).length<=8 && parseInt(obj.value)>0){
			    obj.value=Number(obj.value); 
				var count=0 ;
				var rows = document.getElementById('searchResult').rows;
				var newRouteSequencesID,oldRouteSequencesID,oldRouteSequencesValue,newRouteSequencesValue; 	
				oldRouteSequencesID= "tempOldRouteSequences" + rowNum ;	
				oldRouteSequencesValue = document.getElementById(oldRouteSequencesID).value;
				for ( var i = 1; i <= rows.length; i++) {
					count=0 ;
					newRouteSequencesID = "trNewRouteSequences" + i;	
					newRouteSequencesValue = document.getElementById(newRouteSequencesID).value;
					//alert(obj.value+'<>'+rowNum+'<>'+oldRouteSequencesID+'<>'+oldRouteSequencesValue+"<>"+newRouteSequencesID+'<>'+newRouteSequencesValue);	
					//document.getElementById('textArea').value=oldRouteSequencesValue+"<>"+newRouteSequencesValue;
					if(newRouteSequencesValue==obj.value && oldRouteSequencesValue!=obj.value){
						count++;
						if(count>0){
							alert('Duplicate New Route Sequence ' +  obj.value );
							break;	
				        }	
					}
			   	}
				tempSequenceID="tempNewRouteSequences"+rowNum;
				originalSequenceID="trNewRouteSequences"+rowNum;
				tempSequenceValue=document.getElementById(tempSequenceID).value;
				//alert(tempSequenceID+'<>'+tempSequenceValue+'<>'+originalSequenceID+'<>'+originalSequenceValue);
				document.getElementById(originalSequenceID).value=tempSequenceValue;
				//alert(document.getElementById(originalSequenceID).value);
				fnToCheckEditedRows();
		}else{
			tempSequenceID="tempNewRouteSequences"+rowNum;
			originalSequenceID="trNewRouteSequences"+rowNum;
			tempSequenceValue=document.getElementById(tempSequenceID).value;
			originalSequenceValue=document.getElementById(originalSequenceID).value;
			document.getElementById(originalSequenceID).value=tempSequenceValue;
			fnToCheckEditedRows();
			document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Invalid New Route Sequence Value " + tempSequenceValue +"  At Row No. "+ rowNum +" </b></font>";
			fnDisableField(document.getElementById('process'));
			window.scrollTo(0,0);
			return;
		}
	}
	function checkVal(e){
	    try {
	        var unicode=e.charCode? e.charCode : e.keyCode;
	        if (unicode!=8){ 
	        	if (unicode<48||unicode>57) 
	        return false ;
	         	}
	        }
	     catch (e) {
	    }
	}
	function fnGoToSequencing(){
        var startSequenceNo=document.getElementById('startSequenceFrom').value;
        var incrementalNo=document.getElementById('incrementBy').value;
        if(startSequenceNo=='' || parseInt(startSequenceNo)==0){
        	document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Please Provide Valid Start Sequencing From Value. </b></font>";
			window.scrollTo(0,0);
			return;
   		}
        if(incrementalNo==''){
        	document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Please Provide Valid Increment By Value. </b></font>";
			window.scrollTo(0,0);
			return;
   		}
        var rows = document.getElementById('searchResult').rows;
        var newRouteSequencesID,incrementedSequenceValue;
        incrementedSequenceValue=startSequenceNo;
        var firstId=0;
        for ( var i = 1; i <= rows.length; i++) {
        	newRouteSequencesID = "tempNewRouteSequences" + i;
        	originalSequenceID = "trNewRouteSequences" + i;
        	if(document.getElementById(newRouteSequencesID)){
        		document.getElementById(newRouteSequencesID).value=startSequenceNo;        	
        		document.getElementById(originalSequenceID).value=startSequenceNo;
        		firstId=i;
        		break;
        	}
		}
		for ( var i = 1; i <= rows.length; i++) {  
            if(i==firstId){
                continue;
            }      	
        	newRouteSequencesID = "tempNewRouteSequences" + i;
        	originalSequenceID = "trNewRouteSequences" + i;
        	if(document.getElementById(newRouteSequencesID)){
        		incrementedSequenceValue=parseInt(incrementedSequenceValue)+parseInt(incrementalNo);
        		document.getElementById(newRouteSequencesID).value=incrementedSequenceValue;        	
        		document.getElementById(originalSequenceID).value=incrementedSequenceValue;
        	}
		}
        fnToCheckEditedRows();
	}
    function fnToCheckEditedRows(){
        
    	var noOfRecords=0;
    	var rows = document.getElementById('searchResult').rows;
    	var newRouteSequencesID,oldRouteSequencesID,oldRouteSequencesValue,newRouteSequencesValue;  
    	for ( var i = 1; i <= rows.length; i++) {
			newRouteSequencesID = "trNewRouteSequences" + i;
			displayID="tempNewRouteSequences" + i
			oldRouteSequencesID= "trOldRouteSequences" + i ;
			if(document.getElementById(oldRouteSequencesID) &&document.getElementById(newRouteSequencesID)){
				 oldRouteSequencesValue = document.getElementById(oldRouteSequencesID).value;
				 newRouteSequencesValue = document.getElementById(newRouteSequencesID).value;
				// alert(oldRouteSequencesValue+'<>'+newRouteSequencesValue);
				if(newRouteSequencesValue!=null&&newRouteSequencesValue!=''&&parseInt(newRouteSequencesValue)!=0){
					if(oldRouteSequencesValue!=newRouteSequencesValue){
						//alert(oldRouteSequencesValue+'<>'+newRouteSequencesValue);
						if(document.getElementById(displayID)){
							document.getElementById(displayID).style.backgroundColor = 'orange';
						}else{
							document.getElementById(newRouteSequencesID).style.backgroundColor = 'orange';
						}
			        }
			        if(oldRouteSequencesValue==newRouteSequencesValue){
			        	if(document.getElementById(displayID)){
							document.getElementById(displayID).style.backgroundColor = 'white';
						}else{
							document.getElementById(newRouteSequencesID).style.backgroundColor = 'white';
						}
			       	}					
			        if(oldRouteSequencesValue!=newRouteSequencesValue){
			        	noOfRecords++;
			        }
			     }else{
				     document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Invalid Route Sequence " + newRouteSequencesValue +" At Row No. " + i + " </b></font>";
				     fnDisableField(document.getElementById('process'));
					 window.scrollTo(0,0);
					 return;
				 }
			}
	   	}
		document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Total Number Of Records Changed = " + noOfRecords + " </b></font>";
		if(parseInt(noOfRecords)>0){
	  	    fnEnableField(document.getElementById('process'));
		} else {
			fnDisableField(document.getElementById('process'));
		}
	}
    function showRecordsInRange(){
    	var routSequenceId,routSequenceValue;        
        var recordFrom=parseInt(document.getElementById('showSequenceFrom').value);
        var recordTo=parseInt(document.getElementById('showSequenceTo').value);
        var rows = document.getElementById('searchResult').rows;
        if(isNaN(recordFrom)||recordFrom==''){
        	recordFrom=parseInt(document.getElementById('trOldRouteSequences1').value);
        	document.getElementById('showSequenceFrom').value=recordFrom;
        }
        if(isNaN(recordTo)||recordTo==''){
            var oldSeqId="trOldRouteSequences"+(rows.length);
        	recordTo=parseInt(document.getElementById(oldSeqId).value);
        	document.getElementById('showSequenceTo').value=recordTo;
        }
        if(recordFrom==0&&parseInt(document.getElementById('trOldRouteSequences1').value)!=0){
            document.getElementById('onscreenMessage').innerHTML= "<font color='red'><b>Invalid Sequence Range From Value.</b></font>";
            window.scrollTo(0,0);
    		return;
		}
        if(recordTo==0){
        	var lastSeqId="trOldRouteSequences"+(rows.length);
        	if(parseInt(document.getElementById(lastSeqId).value)!=0){
        		document.getElementById('onscreenMessage').innerHTML= "<font color='red'><b>Invalid Sequence Range To Value.</b></font>";
                window.scrollTo(0,0);
        		return;
			}
        }
        if(!isNaN(recordFrom)||recordFrom!=''){
        	var startSequenceValue=document.getElementById('trOldRouteSequences1').value;
            if(parseInt(recordFrom)<parseInt(document.getElementById('trOldRouteSequences1').value)){
            	document.getElementById('onscreenMessage').innerHTML= "<font color='red'><b>Sequence Range From Value Should Always Be Greater Than OR Equal To Starting Old Route Sequence Value "+startSequenceValue+".</b></font>";
            	window.scrollTo(0,0);
    			return;
			}
		}
        if(!isNaN(recordTo)||recordTo!=''){
        	var lastSeqId="trOldRouteSequences"+(rows.length);
        	var lastSequenceValue=document.getElementById(lastSeqId).value;
            if(parseInt(recordTo)>parseInt(document.getElementById(lastSeqId).value)){
            	document.getElementById('onscreenMessage').innerHTML= "<font color='red'><b>Sequence Range To Value Should Always Be Lesser Than OR Equal To Last Old Route Sequence Value "+lastSequenceValue+".</b></font>";
            	window.scrollTo(0,0);
    			return;
			}
		}
        if(recordFrom>recordTo){
        	document.getElementById('onscreenMessage').innerHTML= "<font color='red'><b>Sequence Range To Value Should Always Be Greater Than Sequence Range From Value.</b></font>";
        	window.scrollTo(0,0);
			return;
        }
        var rowId;
    	var htmlCode="<table class=\"table-grid\" id=\"tempSearchResult\">";
    	var innerhtmlCode="";
    	for ( var i = 1; i <= rows.length; i++) {
    		rowId = "tr" + i;
    		routSequenceId="trOldRouteSequences"+i;
    		routSequenceValue=parseInt(document.getElementById(routSequenceId).value);
    		if(!(isNaN(recordFrom))&&!(isNaN(recordTo))){
	    		if(routSequenceValue>=recordFrom &&routSequenceValue<=recordTo){
	    			innerhtmlCode=document.getElementById(rowId).innerHTML;
	    			htmlCode+="<TR>"+replaceAll(innerhtmlCode,"tr", "temp")+"</TR>";
	    		}
    		}else if(!(isNaN(recordFrom))&&(isNaN(recordTo))){
    			if(routSequenceValue>=recordFrom ){
    				innerhtmlCode=document.getElementById(rowId).innerHTML;
    				htmlCode+="<TR>"+replaceAll(innerhtmlCode,"tr", "temp")+"</TR>";
	    		}
    		}else if((isNaN(recordFrom))&&!(isNaN(recordTo))){
    			if(routSequenceValue<=recordTo ){
    				innerhtmlCode=document.getElementById(rowId).innerHTML;
    				htmlCode+="<TR>"+replaceAll(innerhtmlCode,"tr", "temp")+"</TR>";
	    		}
    		}else if((isNaN(recordFrom))&&(isNaN(recordTo))){
    			innerhtmlCode=document.getElementById(rowId).innerHTML;
    			htmlCode+="<TR>"+replaceAll(innerhtmlCode,"tr", "temp")+"</TR>";
    		}
	   	}
    	htmlCode+="</table>";
    	document.getElementById('visibleDiv').innerHTML=htmlCode;
    	fnToCheckEditedRows();
    	//alert(htmlCode);
    }
    function replaceAll(txt, replace, with_this) {
    	 return txt.replace(new RegExp(replace, 'g'),with_this);
    }
</script>
</head>
<body>
<%@include file="../jsp/CommonOverlay.html"%>
<table class="djbmain" id="djbmaintable">
	<tr>
		<td align="center" valign="top">
		<table class="djbpage">
			<tr height="20px">
				<td align="center" valign="top"><%@include
					file="../jsp/Header.html"%></td>
			</tr>
			<tr height="20px">
				<td align="center" valign="top"><%@include
					file="../jsp/TopMenu.html"%></td>
			</tr>
			<tr>
				<td valign="top">
				<div class="message" title="Server Message"><font size="2"><span
					id="onscreenMessage"><font color="red" size="2"><s:actionerror /></font><%=(null == session.getAttribute("AJAX_MESSAGE"))
						? ""
						: session.getAttribute("AJAX_MESSAGE")%><%=(null == session.getAttribute("SERVER_MESSAGE"))
						? ""
						: session.getAttribute("SERVER_MESSAGE")%></span></font>&nbsp;</div>
				<s:form method="post" action="javascript:void(0);" theme="simple"
					onsubmit="return false;">
					<s:hidden name="hidAction" id="hidAction" />
					<s:hidden name="selectedSPID" id="selectedSPID" />
					<s:hidden name="selectedSPIDList" id="selectedSPIDList" />
					<s:hidden name="totalRecords" id="totalRecords" />
					<s:if
						test="hidAction==null||hidAction==\"search\"||hidAction==\"procesRouteSequences\"">
						<table width="100%" border="0">
							<tr>
								<td>
								<fieldset><legend>Search Parameter</legend>
								<table width="98%" align="center" border="0">
									<tr>
										<td align="right" width="10%"><label>Zone</label><font
											color="red"><sup>*</sup></font></td>
										<td align="left" width="30%" title="Zone" nowrap><s:select
											list="#session.ZoneListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox-long"
											theme="simple" name="selectedZone" id="selectedZone"
											onchange="fnGetMRNo();" /></td>
										<td align="left" width="10%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedZone" /></td>
										<td align="right" width="10%"><label>MR No</label><font
											color="red"><sup>*</sup></font></td>
										<td align="left" width="15%" title="MR No" id="mrNoTD"><s:select
											list="#session.MRNoListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox"
											theme="simple" name="selectedMRNo" id="selectedMRNo"
											onchange="fnGetArea();" onfocus="fnCheckZone();" /></td>
										<td align="left" width="25%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedMRNo" /></td>
									</tr>
									<tr>
										<td align="right" width="10%"><label>Area</label><font color="red"><sup>*</sup></font></td>
										<td align="left" title="Area" id="areaTD"><s:select
											list="#session.AreaListMap" headerKey=""
											headerValue="Please Select" cssClass="selectbox-long"
											theme="simple" name="selectedArea" id="selectedArea"
											onfocus="fnCheckZoneMRNo();" onchange="fnGetMRKEY();" /></td>
										<td align="left" width="10%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedArea" /></td>
										<td align="right"><label>MRKEY</label><font color="red"><sup>*</sup></font></td>
										<td align="left"><s:select name="selectedMRKEY"
											id="selectedMRKEY" headerKey="" headerValue=""
											list="#session.MRKEYListMap" cssClass="selectbox"
											theme="simple" onclick="fnEnableField(this);"
											onchange="fnGetZoneMRAreaByMRKEY();" onfocus="" /></td>
											<td align="left" width="10%"><img
											src="/DataEntryApp/images/load.gif" width="25" border="0"
											title="Processing" style="display: none;"
											id="imgSelectedMRKey" /></td>
											
										<td align="left">&nbsp;</td>
									</tr>
									<tr>
									    <td align="left" colspan="2">&nbsp;</td>
									    <td align="right">&nbsp;&nbsp;
									    <s:submit key="label.search" cssClass="groovybutton" theme="simple" /></td>
									    
									</tr>
									<tr>
									    <td align="left" colspan="3">&nbsp;</td>
									    <td align="right" colspan="3"><font color="red"><sup>*</sup></font>
										marked fields are mandatory.</td>
									</tr>
								</table>
								</fieldset>
								</td>
							</tr>
							<s:if test="renumberRouteSequencesDetailsList.size() > 0">
								<tr>
									<td align="center" valign="top" id="searchBox">
									<fieldset><legend>Search Result</legend>									
									<table class="table-grid" width="99%">
									    <tr>
											<th align="left" colspan="8" width="70%">
											<label>Show Sequencing From</label>
											<s:textfield id="showSequenceFrom" name="showSequenceFrom" onkeypress="return checkVal(event)" 
											maxlength="8"  size="4" onchange="javascript:showRecordsInRange();" cssClass="textbox"/>
											&nbsp;&nbsp;To											
											<s:textfield id="showSequenceTo" name="showSequenceTo" maxlength="4" size="4" onkeypress="return checkVal(event)"  onchange="javascript:showRecordsInRange();" cssClass="textbox"/>
										    &nbsp;&nbsp;<label>Start Sequencing From</label><font color="red"><sup>*</sup></font>
											<s:textfield id="startSequenceFrom" name="startSequenceFrom" onkeypress="return checkVal(event)" 
											maxlength="8"  size="4" cssClass="textbox"/>
											&nbsp;&nbsp;&nbsp;
											<label>&nbsp;&nbsp;&nbsp;Increment By</label><font color="red"><sup>*</sup></font>
											<s:textfield id="incrementBy" name="incrementBy" maxlength="8" size="4" onkeypress="return checkVal(event)" cssClass="textbox"/>
											&nbsp;&nbsp;
											<input type="button" value="Renumber" class="groovybutton" id="renumber"
											onclick="javascript:fnGoToSequencing();" /></th>
										</tr>
										<tr>
											<th align="center" colspan="2" width="10%">
										    </th>
											<th align="center" colspan="5" width="80%"> <<<   Showing Total  <s:property
												value="totalRecords" /> Number Of Records   >>> </th>
											<th align="right" colspan="1" width="10%">
											</th>
										</tr>
										<tr>
										   <th align="center" width="5%">SL</th>
											<th align="center" width="10%">Old Route Sequence</th>
											<th align="center" width="10%">KNO</th>
											<th align="center" width="10%">WC No</th>
											<th align="center" width="20%">NAME</th>
											<th align="center" width="30%">ADDRESS</th>
											<th align="center" width="10%">CATEGORY</th>
											<th align="center" width="10%">New Route Sequence</th>
										</tr>
										</table>
										<div id="visibleDiv" style="display:block;"></div>
										<div id="hiddenDiv" style="display:none">
										<table id="searchResult" class="table-grid" width="99%">
										<s:iterator value="renumberRouteSequencesDetailsList" status="row">
											<tr bgcolor="white"
												onMouseOver="javascript:this.bgColor= 'yellow'"
												onMouseOut="javascript:this.bgColor='white'" id="tr<s:property value="#row.count" />">
										   		<td align="center" width="5%"><s:property value="#row.count" /></td> 
												<td align="center" width="10%"><s:property value="oldRouteSequences" />
												    <input type="hidden" id="trOldRouteSequences<s:property value="#row.count" />" name="trOldRouteSequences<s:property value="#row.count" />"
													value="<s:property value="oldRouteSequences" />" />
												    <input type="hidden" id="trSPID<s:property value="#row.count" />" name="trSPID<s:property value="#row.count" />"
													value="<s:property value="spID" />" />	</td>
												<td align="center" width="10%"><s:property value="kno" />
												    <input type="hidden" id="trKNO<s:property value="#row.count" />" name="trKNO<s:property value="#row.count" />"
													value="<s:property value="kno" />"/></td>
												<td align="center" width="10%" nowrap><s:property value="wcNo" />&nbsp;</td>
												<td align="left" width="20%"><s:property value="name" />&nbsp;</td>
												<td align="left" width="30%"><s:property value="address" />&nbsp;</td>
												<td align="center" width="10%" nowrap><s:property value="category" />&nbsp;</td>
												<td align="center" width="10%" >
												    <input type="text" id="trNewRouteSequences<s:property value="#row.count" />" name="trNewRouteSequences<s:property value="#row.count" />" size="8"
													value="<s:property value="oldRouteSequences"  />" maxlength="8" onchange="javascript:checkDuplicate(this,'<s:property value="#row.count" />');" onkeypress="return checkVal(event)" class="textbox" style="text-align:right;"/></td>
											</tr>
										</s:iterator>
									</table>
									</div>
									<table>
									      <tr>
											<td align="center" width="10%"><input type="button"
												value="Process" class="groovybutton" id="process"
												onclick="javascript:fnGoToRenumberRouteSequences();" disabled/>
										    </td>
										 </tr>
									</table>
									</fieldset>
									<script type="text/javascript">
										showRecordsInRange();
									</script>
									</td>
								</tr>
							</s:if>
							<s:else>
							      <s:if test="renumberRouteSequencesDetailsList.size() == 0">
							      	 <tr>
							      	    <td align="center" valign="top" id="noResultSearch"><fieldset><legend>Search Result</legend>
									        <table id="noResultFound" class="table-grid">
									            <tr>
										      	    <th align="center" ><font size="3" color="white"><b> <<<   No Record Found    >>> </b></font></th>
												</tr>
											 </table>   
									     </fieldset>
									  </td>
								   </tr>
							   </s:if>   		
							</s:else>
						</table>
					</s:if>
				</s:form></td>
			</tr>
			<tr height="20px">
				<td align="center" valign="bottom"><%@include
					file="../jsp/Footer.html"%></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
$("form").submit(function(e) {
	gotoSearch();
});
$('input[type="text"]').focus(function() {
	$(this).addClass("textboxfocus");
});
$('input[type="text"]').blur(function() {
	$(this).removeClass("textboxfocus");
	$(this).addClass("textbox");
});
</script>
<%
	} catch (Exception e) {
	}
%>
</html>