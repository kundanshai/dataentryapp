<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Biometric Enrollment - Revenue Management System, Delhi Jal Board</title>
<script language='javascript'>

        function capture() {
        	var err, payload;
			try {
				opener.document.getElementById('onscreenMessage').title ="Server Message";
				opener.document.getElementById('biometricCapture').style.display='none';
				opener.document.getElementById('biometricScanSuccess').style.display='none';
				opener.document.getElementById('biometricScanning').style.display='block';
				// Exception handling
                // Open device. [AUTO_DETECT]
                // You must open device before enroll.
                DEVICE_FDU01 = 2;
                DEVICE_FDU11 = 4;
                DEVICE_AUTO_DETECT = 255;
                var objDevice = document.objNBioBSP.Device;
                if(objDevice){
	                var objExtraction = document.objNBioBSP.Extraction;
	                objDevice.Open(DEVICE_AUTO_DETECT);
	                err = objDevice.ErrorCode; // Get error code
	                if (err != 0) // Device open failed
	                {
	                    //alert('Device open failed !\nPlease make sure the device is plugged in to the system.');
	                    objDevice.Close(DEVICE_AUTO_DETECT);
	                    objExtraction = 0;
	                    objDevice = 0;
	                    opener.document.getElementById('FIRTextData').value ="";
	                    opener.document.getElementById('biometricScanning').style.display='none';
	                    opener.document.getElementById('biometricScanSuccess').style.display='none';
	                    opener.document.getElementById('biometricCapture').style.display='block';
	                    opener.document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>Failed to Open Device! Please Plug in the Biometric Device properly to the System and then Try again.</b></font>";
	                    opener.focus();
		    			self.close();
	                    return (false);
	                }
	                // Enroll user's fingerprint.
	                objExtraction.Enroll(payload);
	                err = objExtraction.ErrorCode; // Get error code
	                if (err != 0) // Enroll failed
	                {
	                    //alert('Scanning failed ! Error Number : [' + err + ']');
	                    objDevice.Close(DEVICE_AUTO_DETECT);
	                    objExtraction = 0;
	                    objDevice = 0;
	                    opener.document.getElementById('FIRTextData').value ="";
	                    opener.document.getElementById('biometricScanning').style.display='none';
	                    opener.document.getElementById('biometricScanSuccess').style.display='none';
	                    opener.document.getElementById('biometricCapture').style.display='block';
	                    opener.document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>There was a Problem while scanning, Please Try again.</b></font>";
	                    opener.focus();
		    			self.close();                    
	                    return (false);
	                }else{ // Enroll success
	                    // Get text encoded FIR data from NBioBSP module.
	                    opener.document.getElementById('FIRTextData').value = objExtraction.TextEncodeFIR;
	                    opener.document.getElementById('biometricScanning').style.display='none';
	                    opener.document.getElementById('biometricScanSuccess').style.display='block';
	                    //alert('FingerPrint Captured !');
	                    opener.document.getElementById('onscreenMessage').innerHTML = "";
	                    // Close device. [AUTO_DETECT]
		                objDevice.Close(DEVICE_AUTO_DETECT);
		                objExtraction = 0;
		                objDevice = 0;
	                    opener.focus();
	    		       	self.close();
	    		       	return (true);   
	                }
	                // Close device. [AUTO_DETECT]
	                objDevice.Close(DEVICE_AUTO_DETECT);
	                objExtraction = 0;
	                objDevice = 0;
                }else{
                	 alert('It seems the browser does not support this feature\nor\nBiometric Device is not installed to the System!\n\nPlease Install Biometric Device or Use Internet Explorer.');
                     opener.focus();
                     //self.close(); 
                     return (false);
                }
            }
            catch (e) {
                //alert(e.message);
                objDevice.Close(DEVICE_AUTO_DETECT);
                objExtraction = 0;
                objDevice = 0;
                opener.document.getElementById('FIRTextData').value ="";
                opener.document.getElementById('biometricScanning').style.display='none';
                opener.document.getElementById('biometricScanSuccess').style.display='none';
                opener.document.getElementById('biometricCapture').style.display='block';
                opener.document.getElementById('onscreenMessage').title =e.message;
                opener.document.getElementById('onscreenMessage').innerHTML = "<font color='red'><b>There was a Problem while scanning, Please Try again.</b></font>";
    			self.close();      
                return (false);
            }
            return (true);
        }
        function fnOnload(){
        	capture();
        }        
    </script>
</head>
<body onload="fnOnload();">
<object classid="CLSID:F66B9251-67CA-4d78-90A3-28C2BFAE89BF" height="0"
	width="0" id="objNBioBSP" name="objNBioBSP"> </object>

<center>
<form action='javascript:void(0);' name='MainForm' method='post'
	onsubmit='return capture();'><input type="hidden"
	name='FIRTextData' />
</form>

</center>
</body>
</html>