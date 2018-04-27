function viewPropertyTab(){
	var propertyName = document.getElementById("propertyNameId").value;
	// alert("viewPropertyTab event fired");
	if(!validateLowerCaseAndUnderScore(propertyName))
		return;
	viewProperty(viewPropertyTabCallback ,0,propertyName);
	
}
function viewPropertyTabCallback(propertyValue ,index){
	reg = /^Error[a-zA-Z\-\s]*$/;
	if(reg.test(propertyValue)){
		showSimpleModal();
		document.getElementById("simpleModalTextId").innerHTML = "<div style='color: red'>"+propertyValue+"</div>";
		return;
	}
	document.getElementById("propertyValueId").innerHTML= propertyValue ;	
}


var xmlhttp = new XMLHttpRequest();

function viewProperty(callback , index ,propertyName)
{
	// alert("viewProperty event fired");
	
	
	var url="http://localhost:8080/WebView/ActionServlet";
	url+="?command=viewProperty";
	url+="&propertyName=";
	
	url+=propertyName;
	
	xmlhttp.onreadystatechange=function(){stateChange(callback , index);};
		
	xmlhttp.open("GET",url,true);
	//alert("reached open");
	xmlhttp.send(null);
	
	//alert("reached send");
}
function stateChange(callback , index)
{
	if(xmlhttp.readyState==4){
		// alert("statechange fired with state :"+xmlhttp.readyState);
		// alert("response : "+xmlhttp.responseText);
		var doc = xmlhttp.responseXML;
		var element = doc.getElementsByTagName("Property").item(0);
		//alert("node name : "+element.nodeName);
		//alert("child node value : "+element.childNodes[0].nodeValue);
		
		callback(element.childNodes[0].nodeValue , index);	
		
	}
	//else
	//	alert("Problem retrieving XML data");
	
}



var xmlhttpViewClient = new XMLHttpRequest();

function viewClientDetails()
{
	// alert("viewClientDetails event fired");
	var url="http://localhost:8080/WebView/ActionServlet";
	url+="?command=viewClientDetails";
	
	
	xmlhttpViewClient.onreadystatechange=stateChangeViewClient;
	xmlhttpViewClient.open("GET",url,true);
	//alert("reached open");
	xmlhttpViewClient.send(null);
	
	//alert("reached send");
}
function stateChangeViewClient()
{
	if(xmlhttpViewClient.readyState==4){
		// alert("stateChangeViewClient fired with state :"+xmlhttpViewClient.readyState);
		// alert("response : "+xmlhttpViewClient.responseText);
		var doc = xmlhttpViewClient.responseXML;
		var element = doc.getElementsByTagName("Client")[0];
		//alert("node name : "+element.nodeName);
		var name = element.getElementsByTagName("name")[0];
		var type = element.getElementsByTagName("type")[0];
		var address = element.getElementsByTagName("address")[0];
		var email = element.getElementsByTagName("email")[0];
		var phone = element.getElementsByTagName("phone")[0];
		var comment = element.getElementsByTagName("comment")[0];
		
		document.getElementById("clientNameId").innerHTML= name.childNodes[0].nodeValue;
		document.getElementById("clientTypeId").innerHTML= type.childNodes[0].nodeValue;
		document.getElementById("clientAddressId").innerHTML= address.childNodes[0].nodeValue;
		document.getElementById("clientEmailId").innerHTML= email.childNodes[0].nodeValue;
		document.getElementById("clientPhoneId").innerHTML= phone.childNodes[0].nodeValue;
		document.getElementById("clientCommentId").innerHTML= comment.childNodes[0].nodeValue;
		
	}
	//else
	//	alert("Problem retrieving XML data");
		
}
var xmlhttpViewClientUpdate = new XMLHttpRequest();

function viewClientDetailsForUpdate(){
	// alert("viewClientDetailsForUpdate event fired");
	var url="http://localhost:8080/WebView/ActionServlet";
	url+="?command=viewClientDetails";
	
	
	xmlhttpViewClientUpdate.onreadystatechange=stateChangeViewClientUpdate;
	xmlhttpViewClientUpdate.open("GET",url,true);
	//alert("reached open");
	xmlhttpViewClientUpdate.send(null);
	
	//alert("reached send");
}
function stateChangeViewClientUpdate(){
	if(xmlhttpViewClientUpdate.readyState==4){
		// alert("stateChangeViewClientUpdate fired with state :"+xmlhttpViewClientUpdate.readyState);
		// alert("response : "+xmlhttpViewClientUpdate.responseText);
		var doc = xmlhttpViewClientUpdate.responseXML;
		var element = doc.getElementsByTagName("Client")[0];
		//alert("node name : "+element.nodeName);
		//var name = element.getElementsByTagName("name")[0];
		//var password = element.getElementsByTagName("password")[0];
		var address = element.getElementsByTagName("address")[0];
		var email = element.getElementsByTagName("email")[0];
		var phone = element.getElementsByTagName("phone")[0];
		//var comment = element.getElementsByTagName("comment")[0];
		
		//document.getElementById("nameToUpdateId").value= name.childNodes[0].nodeValue;
		//document.getElementById("passwordToUpdateId").value= password.childNodes[0].nodeValue;
		document.getElementById("addressToUpdateId").value= address.childNodes[0].nodeValue;
		document.getElementById("emailToUpdateId").value= email.childNodes[0].nodeValue;
		document.getElementById("phoneToUpdateId").value= phone.childNodes[0].nodeValue;
		//document.getElementById("commentToUpdateId").value= comment.childNodes[0].nodeValue;
			
	}
	//else
	//	alert("Problem retrieving XML data");
	
}


var xmlhttpUpdateClient = new XMLHttpRequest();

function updateClientDetails()
{
	// alert("updateClientDetails event fired");
	var url="http://localhost:8080/WebView/ActionServlet";
	
	//var name = document.getElementById("nameToUpdateId").value;
	//var password = document.getElementById("passwordToUpdateId").value;
	var address = document.getElementById("addressToUpdateId").value;
	var email = document.getElementById("emailToUpdateId").value;
	var phone = document.getElementById("phoneToUpdateId").value;
	//var comment = document.getElementById("commentToUpdateId").value;
	
	if(!validateStringIncludeNums(address))
		return;
	if(!validateEmail(email))
		return;
	if(!validatePhone(phone))
		return;
	var xmlText = "<?xml version='1.0'?>"+
					"<root>"+
						"<Command>"+"updateClientDetails"+"</Command>"+
						"<Client>" +
								"<name>"     + "dummyName" +     "</name>"+
								"<password>" + "dummyPassword" + "</password>"+
								"<address>"  + address +  "</address>"+
								"<email>"    + email +    "</email>"+
								"<phone>"    + phone +    "</phone>"+
								"<comment>"  + "dummyComment" +  "</comment>"+
						"</Client>"+
					"</root>";
	
	xmlhttpUpdateClient.onreadystatechange=stateChangeUpdateClient;
	xmlhttpUpdateClient.open("POST",url,true);
	//alert("reached open");
	xmlhttpUpdateClient.setRequestHeader("Content-Type", "text/xml");
	xmlhttpUpdateClient.send(xmlText);
	
	//alert("reached send");
 

}
function stateChangeUpdateClient()
{
	if(xmlhttpUpdateClient.readyState==4){
		// alert("stateChangeUpdateClient fired with state :"+xmlhttpUpdateClient.readyState);
		// alert("response : "+xmlhttpUpdateClient.responseText);
		var doc = xmlhttpUpdateClient.responseXML;
		var element = doc.getElementsByTagName("Answer").item(0);
		//alert("node name : "+element.nodeName);
		//alert("child node value : "+element.childNodes[0].nodeValue);
		
		
		hideModal();
		showSimpleModal();
		var ans;
		if(element.childNodes[0].nodeValue == "true"){
			ans = "Action Succeeded"
		}else{
			ans = "Action Failed"
		}
		document.getElementById("simpleModalTextId").innerHTML = ans;
		viewClientDetails();
		
	}
	//else
	//	alert("Problem retrieving XML data");

}

var xmlhttpCreateDeposit = new XMLHttpRequest();

function createDeposit()
{
	// alert("createDeposit event fired");
	var url="http://localhost:8080/WebView/ActionServlet";
	
	var amount = document.getElementById("balanceCreateId").value;
	var year = document.getElementById("yearCreateId").value;
	var month = document.getElementById("monthCreateId").value;
	var day = document.getElementById("dayCreateId").value;
	
	if(!validateDouble(amount))
		return;
	if(!validateDate(year, month, day))
		return;
	
	//reset values of text boxes :
	document.getElementById("balanceCreateId").value = "";
	document.getElementById("yearCreateId").value = "";
	document.getElementById("monthCreateId").value = "";
	document.getElementById("dayCreateId").value = "";
	
	var xmlText = "<?xml version='1.0'?>"+
					"<root>"+
						"<Command>"+"createDeposit"+"</Command>"+
						"<Deposit>" +
								"<amount>"  + amount +  "</amount>"+
								"<closingDate>"    + 
										"<year>"+year+"</year>"+    
										"<month>"+month+"</month>"+ 
										"<day>"+day+"</day>"+ 
								"</closingDate>"+
						"</Deposit>"+
					"</root>";
	
	xmlhttpCreateDeposit.onreadystatechange=stateChangeCreateDeposit;
	xmlhttpCreateDeposit.open("POST",url,true);
	//alert("reached open");
	xmlhttpCreateDeposit.setRequestHeader("Content-Type", "text/xml");
	xmlhttpCreateDeposit.send(xmlText);
	
	//alert("reached send");
 

}

function stateChangeCreateDeposit()
{
	if(xmlhttpCreateDeposit.readyState==4){
		// alert("stateChangeCreateDeposit fired with state :"+xmlhttpCreateDeposit.readyState);
		// alert("response : "+xmlhttpCreateDeposit.responseText);
		var doc = xmlhttpCreateDeposit.responseXML;
		var element = doc.getElementsByTagName("Answer").item(0);
		//alert("node name : "+element.nodeName);
		//alert("child node value : "+element.childNodes[0].nodeValue);
		
		
		hideModal();
		showSimpleModal();
		var ans;
		if(element.childNodes[0].nodeValue == "true"){
			ans = "Action Succeeded"
		}else{
			ans = "Action Failed"
		}
		document.getElementById("simpleModalTextId").innerHTML = ans;
		
		viewClientDeposits();
		viewActivities();
	}
	//else
	//	alert("Problem retrieving XML data");

}

var xmlhttpViewClientDeposits = new XMLHttpRequest();

function viewClientDeposits()
{
	// alert("viewClientDeposits event fired");
	var url="http://localhost:8080/WebView/ActionServlet";
	url+="?command=viewClientDeposits";
	
	
	xmlhttpViewClientDeposits.onreadystatechange=stateChangeViewClientDeposits;
	xmlhttpViewClientDeposits.open("GET",url,true);
	//alert("reached open");
	xmlhttpViewClientDeposits.send(null);
	
	//alert("reached send");
}

function stateChangeViewClientDeposits()
{
	if(xmlhttpViewClientDeposits.readyState==4){
		// alert("stateChangeViewClientDeposits fired with state :"+xmlhttpViewClientDeposits.readyState);
		// alert("response : "+xmlhttpViewClientDeposits.responseText);
		var doc = xmlhttpViewClientDeposits.responseXML;
		var root = doc.getElementsByTagName("root")[0];
		
		var tableHtmlStr = "<table class='depositTableOuter'>"+
						"<tr class='depositTable'>"+
						"	<th class='depositTable'>deposit balance</th>"+
						"	<th class='depositTable'>deposit type</th>"+
						"	<th class='depositTable'>deposit estimated balance</th>"+
						"	<th class='depositTable'> opening date</th>"+
						"	<th class='depositTable'>closing date </th>"+
						"	<th class='depositTable'> pre open</th>"+
						"</tr>";
		
		
		//alert("node name : "+root.nodeName);
		var elements = root.getElementsByTagName("Deposit");
		for(var i =0;i < elements.length; i++){
			var balance = elements[i].getElementsByTagName("balance")[0];
			var type = elements[i].getElementsByTagName("type")[0];
			var estimatedBalance = elements[i].getElementsByTagName("estimated_balance")[0];
			var openingDate = elements[i].getElementsByTagName("opening_date")[0];
			var closingDate = elements[i].getElementsByTagName("closing_date")[0];
			
			tableHtmlStr+= "<tr class='depositTable'>";
			
			tableHtmlStr+= "<td class='depositTable' id='depositBalanceId"+i+"'>"+balance.childNodes[0].nodeValue;+"</td>";
			tableHtmlStr+= "<td class='depositTable'>"+type.childNodes[0].nodeValue;+"</td>";
			tableHtmlStr+= "<td class='depositTable'>"+estimatedBalance.childNodes[0].nodeValue;+"</td>";
			tableHtmlStr+= "<td class='depositTable'>"+openingDate.childNodes[0].nodeValue;+"</td>";
			tableHtmlStr+= "<td class='depositTable'>"+closingDate.childNodes[0].nodeValue;+"</td>";
			tableHtmlStr+= "<td class='depositTable'>";
			
						if(type.childNodes[0].nodeValue=="LONG"){
							tableHtmlStr+="<input type='button' value='open' onclick='displaypreOpenModal(this)'>"+
										  "<div  class='depositTableIndex'>"+i+"</div>";
						}	
							
				
			tableHtmlStr+="</td>";
		
			
			tableHtmlStr+= "</tr>";
		}
		tableHtmlStr+="</table>";
		//alert(tableHtmlStr);
		document.getElementById("depositsTableHolderId").innerHTML = tableHtmlStr;
		
		
	}//else
	//	alert("Problem retrieving XML data");
		
}

var xmlhttpPreOpenDeposit = new XMLHttpRequest();

function preOpenDeposit(){
	// alert("preOpenDeposit event fired");
	
	var url="http://localhost:8080/WebView/ActionServlet";
	
	var depositIndex = document.getElementById("depositIndexId").innerHTML;
	
	var xmlText = "<?xml version='1.0'?>"+
					"<root>"+
						"<Command>"+"preOpenDeposit"+"</Command>"+
						"<DepositIndex>" + depositIndex +"</DepositIndex>"+
					"</root>";
	
	xmlhttpPreOpenDeposit.onreadystatechange=stateChangePreOpenDeposit;
	xmlhttpPreOpenDeposit.open("POST",url,true);
	//alert("reached open");
	xmlhttpPreOpenDeposit.setRequestHeader("Content-Type", "text/xml");
	xmlhttpPreOpenDeposit.send(xmlText);
	
	//alert("reached send");
	
}
function stateChangePreOpenDeposit()
{
	if(xmlhttpPreOpenDeposit.readyState==4){
		// alert("stateChangePreOpenDeposit fired with state :"+xmlhttpPreOpenDeposit.readyState);
		// alert("response : "+xmlhttpPreOpenDeposit.responseText);
		var doc = xmlhttpPreOpenDeposit.responseXML;
		var element = doc.getElementsByTagName("Answer").item(0);
		//alert("node name : "+element.nodeName);
		//alert("child node value : "+element.childNodes[0].nodeValue);
		
		
		hideModal();
		showSimpleModal();
		var ans;
		if(element.childNodes[0].nodeValue == "true"){
			ans = "Action Succeeded"
		}else{
			ans = "Action Failed"
		}
		document.getElementById("simpleModalTextId").innerHTML = ans;
		
		viewClientDeposits();
		viewAccountDetails();
		viewActivities();
	}
	//else
	//	alert("Problem retrieving XML data");

}

var xmlhttpViewAccountDetails = new XMLHttpRequest();

function viewAccountDetails()
{
	// alert("viewAccountDetails event fired");
	var url="http://localhost:8080/WebView/ActionServlet";
	url+="?command=viewAccountDetails";
	
	
	xmlhttpViewAccountDetails.onreadystatechange=stateChangeViewAccountDetails;
	xmlhttpViewAccountDetails.open("GET",url,true);
	//alert("reached open");
	xmlhttpViewAccountDetails.send(null);
	
	//alert("reached send");
}
function stateChangeViewAccountDetails()
{
	if(xmlhttpViewAccountDetails.readyState==4){
		// alert("stateChangeViewAccountDetails fired with state :"+xmlhttpViewAccountDetails.readyState);
		// alert("response : "+xmlhttpViewAccountDetails.responseText);
		var doc = xmlhttpViewAccountDetails.responseXML;
		
		var element = doc.getElementsByTagName("Account")[0];
		//alert("node name : "+element.nodeName);
		var balance = element.getElementsByTagName("balance")[0];
		var comment = element.getElementsByTagName("comment")[0];
		var creditLimit = element.getElementsByTagName("credit_limit")[0];
		
		
		document.getElementById("accountBalanceId").innerHTML= balance.childNodes[0].nodeValue;
		document.getElementById("accountCommentId").innerHTML= comment.childNodes[0].nodeValue;
		document.getElementById("accountCreditLimitId").innerHTML= creditLimit.childNodes[0].nodeValue;
		
		
	}
	//else
	//	alert("Problem retrieving XML data");
		
}


var xmlhttpViewActivities = new XMLHttpRequest();

function viewActivities()
{
	// alert("viewActivities event fired");
	var url="http://localhost:8080/WebView/ActionServlet";
	url+="?command=viewActivities";
	
	
	xmlhttpViewActivities.onreadystatechange=stateChangeViewActivities;
	xmlhttpViewActivities.open("GET",url,true);
	//alert("reached open");
	xmlhttpViewActivities.send(null);
	
	//alert("reached send");
}
function stateChangeViewActivities()
{
	if(xmlhttpViewActivities.readyState==4){
		// alert("stateChangeViewActivities fired with state :"+xmlhttpViewActivities.readyState);
		// alert("response : "+xmlhttpViewActivities.responseText);
		var doc = xmlhttpViewActivities.responseXML;
		var root = doc.getElementsByTagName("root")[0];
		
		var tableHtmlStr = "<table class='depositTableOuter'>"+
								"<tr class='depositTable'>"+
									"<th class='depositTable'>amount</th>"+
									"<th class='depositTable'>activity date</th>"+
									"<th class='depositTable'>commision</th>"+
									"<th class='depositTable'>description</th>"+
								"<tr>";
		
		
		//alert("node name : "+root.nodeName);
		var elements = root.getElementsByTagName("Activity");
		for(var i =0;i < elements.length; i++){
			var amount = elements[i].getElementsByTagName("amount")[0];
			var activityDate = elements[i].getElementsByTagName("activity_date")[0];
			var commision = elements[i].getElementsByTagName("commision")[0];
			var description = elements[i].getElementsByTagName("description")[0];
			
			tableHtmlStr+= "<tr class='depositTable'>";
			
			tableHtmlStr+= "<td class='depositTable'>"+amount.childNodes[0].nodeValue;+"</td>";
			tableHtmlStr+= "<td class='depositTable'>"+activityDate.childNodes[0].nodeValue;+"</td>";
			tableHtmlStr+= "<td class='depositTable'>"+commision.childNodes[0].nodeValue;+"</td>";
			tableHtmlStr+= "<td class='depositTable'>"+description.childNodes[0].nodeValue;+"</td>";
			
		
			tableHtmlStr+= "</tr>";
		}
		tableHtmlStr+="</table>";
		//alert(tableHtmlStr);
		document.getElementById("activitiesTableHolderId").innerHTML = tableHtmlStr;
		
		
	}//else
	//	alert("Problem retrieving XML data");
		
}
var xmlhttpDepositToAccount = new XMLHttpRequest();

function depositToAccount(){
	// alert("depositToAccount event fired");
	
	var url="http://localhost:8080/WebView/ActionServlet";
	
	var amountToDeposit = document.getElementById("amountToDepositInputId").value;
	if(!validateDouble(amountToDeposit))
		return;
	document.getElementById("amountToDepositInputId").value = "";
	
	var xmlText = "<?xml version='1.0'?>"+
					"<root>"+
						"<Command>"+"depositToAccount"+"</Command>"+
						"<amountToDeposit>" + amountToDeposit +"</amountToDeposit>"+
					"</root>";
	
	xmlhttpDepositToAccount.onreadystatechange=stateChangeDepositToAccount;
	xmlhttpDepositToAccount.open("POST",url,true);
	//alert("reached open");
	xmlhttpDepositToAccount.setRequestHeader("Content-Type", "text/xml");
	xmlhttpDepositToAccount.send(xmlText);
	
	//alert("reached send");
	
}
function stateChangeDepositToAccount()
{
	if(xmlhttpDepositToAccount.readyState==4){
		// alert("stateChangeDepositToAccount fired with state :"+xmlhttpDepositToAccount.readyState);
		// alert("response : "+xmlhttpDepositToAccount.responseText);
		var doc = xmlhttpDepositToAccount.responseXML;
		var element = doc.getElementsByTagName("Answer").item(0);
		//alert("node name : "+element.nodeName);
		//alert("child node value : "+element.childNodes[0].nodeValue);
		
		
		hideModal();
		showSimpleModal();
		var ans;
		if(element.childNodes[0].nodeValue == "true"){
			ans = "Action Succeeded"
		}else{
			ans = "Action Failed"
		}
		document.getElementById("simpleModalTextId").innerHTML = ans;
		
		viewAccountDetails();
		viewActivities();
	}
	//else
	//	alert("Problem retrieving XML data");

}

var xmlhttpWithdrawFromAccount = new XMLHttpRequest();

function withdrawFromAccount(){
	// alert("withdrawFromAccount event fired");
	
	var url="http://localhost:8080/WebView/ActionServlet";
	
	var amountToWithdraw = document.getElementById("amountToWithdrawInputId").value;
	if(!validateDouble(amountToWithdraw))
		return;
	document.getElementById("amountToWithdrawInputId").value = "";
	
	var xmlText = "<?xml version='1.0'?>"+
					"<root>"+
						"<Command>"+"withdrawFromAccount"+"</Command>"+
						"<amountToWithdraw>" + amountToWithdraw +"</amountToWithdraw>"+
					"</root>";
	
	xmlhttpWithdrawFromAccount.onreadystatechange=stateChangeWithdrawFromAccount;
	xmlhttpWithdrawFromAccount.open("POST",url,true);
	//alert("reached open");
	xmlhttpWithdrawFromAccount.setRequestHeader("Content-Type", "text/xml");
	xmlhttpWithdrawFromAccount.send(xmlText);
	
	//alert("reached send");
	
}
function stateChangeWithdrawFromAccount()
{
	if(xmlhttpWithdrawFromAccount.readyState==4){
		// alert("stateChangeWithdrawFromAccount fired with state :"+xmlhttpWithdrawFromAccount.readyState);
		// alert("response : "+xmlhttpWithdrawFromAccount.responseText);
		var doc = xmlhttpWithdrawFromAccount.responseXML;
		var element = doc.getElementsByTagName("Answer").item(0);
		//alert("node name : "+element.nodeName);
		//alert("child node value : "+element.childNodes[0].nodeValue);
		
		
		hideModal();
		showSimpleModal();
		var ans;
		if(element.childNodes[0].nodeValue == "true"){
			ans = "Action Succeeded"
		}else{
			ans = "Action Failed"
		}
		document.getElementById("simpleModalTextId").innerHTML = ans;
		
		viewAccountDetails();
		viewActivities();
	}
	//else
	//	alert("Problem retrieving XML data");

}



