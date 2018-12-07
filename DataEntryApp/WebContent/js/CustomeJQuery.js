//Added By Matiur
//=====================================================================

$(document).bind("click dblclick mousedown mouseenter mouseleave keyup", fnAccessValidation);
var alertCount=0; 
function fnAccessValidation(e){
   if(document.getElementById("ajaxScript")){
       eval(document.getElementById("ajaxScript").innerHTML);
   }
}    	