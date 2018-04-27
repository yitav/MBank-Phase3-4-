function calcDepositToAccountCommision(){
	
	if(validateDouble(document.getElementById("amountToDepositInputId").value)){
			document.getElementById("wrongDepositInputId").innerHTML = "";
			
			viewProperty(calcDepositToAccountCommisionCallback , 0 ,"commission_rate");
	}else{
		document.getElementById("depositToAccountCommisionId").innerHTML = "";
		document.getElementById("wrongDepositInputId").innerHTML = "Illegal Input !";
	}

}
function calcDepositToAccountCommisionCallback(propertyValue ,index){
	
	document.getElementById("depositToAccountCommisionId").innerHTML = propertyValue;
}
function calcWithdrawCommision(){
	
	if(validateDouble(document.getElementById("amountToWithdrawInputId").value)){
		document.getElementById("wrongWithdrawInputId").innerHTML = "";
			viewProperty(calcWithdrawCommisionCallback , 0 , "commission_rate");
	}else{
		document.getElementById("withdrawCommisionId").innerHTML = "";
		document.getElementById("wrongWithdrawInputId").innerHTML = "Illegal Input !";
	}
	
}
function calcWithdrawCommisionCallback(propertyValue ,index){
	
	document.getElementById("withdrawCommisionId").innerHTML = propertyValue;
}
function calcPreOpenFee(index){
		viewProperty(calcPreOpenFeeCallback ,index,"pre_open_fee");
}	
function calcPreOpenFeeCallback(propertyName , index){
		
		var feeStr = propertyName.substring(0,propertyName.length);
		//alert("feeStr: "+feeStr); 
		var feePercentage = parseFloat(feeStr)/100;
		//alert("feePercentage: "+feePercentage);
		var balance = document.getElementById("depositBalanceId"+index).innerHTML;
		//alert("balance: "+balance);
		var fee = feePercentage*balance;
		//alert("fee: "+fee);
		document.getElementById("preOpenFeeId").innerHTML = fee+"$";
}
function validateAddress(){
	
		if(validateStringIncludeNums(document.getElementById("addressToUpdateId").value)){
			document.getElementById("wrongAddressToUpdateInputId").innerHTML = "";
		}else{
			document.getElementById("wrongAddressToUpdateInputId").innerHTML = "Illegal Input";
		}
		
}
function validateEmailWrapper(){
	if(validateEmail(document.getElementById("emailToUpdateId").value)){
		document.getElementById("wrongEmailToUpdateInputId").innerHTML = "";
	}else{
		document.getElementById("wrongEmailToUpdateInputId").innerHTML = "Illegal Input";
	}
}
function validatePhoneWrapper(){
	if(validatePhone(document.getElementById("phoneToUpdateId").value)){
		document.getElementById("wrongPhoneToUpdateInputId").innerHTML = "";
	}else{
		document.getElementById("wrongPhoneToUpdateInputId").innerHTML = "Illegal Input";
	}
}
function validateBalanceCreate(){
	
	if(validateDouble(document.getElementById("balanceCreateId").value)){
		document.getElementById("wrongBalanceCreateInputId").innerHTML = "";
	}else{
		document.getElementById("wrongBalanceCreateInputId").innerHTML = "Illegal Input";
	}
	
}
function validateDateWrapper(){
	var year =	document.getElementById("yearCreateId").value;
	var month = document.getElementById("monthCreateId").value;
	var day = document.getElementById("dayCreateId").value;
	
	if((year=="")||(month=="")||(day=="")){
		return;
	}
	if(validateDate(year,month,day)){
		document.getElementById("wrongClosingDateInputId").innerHTML = "";
	}else{
		document.getElementById("wrongClosingDateInputId").innerHTML = "Illegal Input";
	}
}

function validateDate(year,month,day){
	
	if(!validateNum(year))
		return false;
	if(!validateNum(month))
		return false;
	if(!validateNum(day))
		return false;
	var dateNow = new Date();
	var dateInput = new Date();
	dateInput.setDate(day);
	dateInput.setMonth((month-1));
	dateInput.setFullYear(year);
	if((dateInput.getTime())<(dateNow.getTime()))
		return false;
	
	var timestamp=Date.parse((""+year+"/"+month+"/"+day));

	return (!(isNaN(timestamp)))
	
}
function validatePropertyName(){
	var propertyName = document.getElementById("propertyNameId").value;
	if(validateLowerCaseAndUnderScore(propertyName)){
		document.getElementById("wrongPropertyNameInputId").innerHTML = "";
	}else{
		document.getElementById("wrongPropertyNameInputId").innerHTML = "Illegal Input";
	}
}
function validateDouble(str){
	
	var reg = /^\d+\.?\d*$/;
	return reg.test(str);
}
function validateStringIncludeNums(str){
	var reg = /^[a-zA-Z\s\d\/]+$/;
	return reg.test(str);
}
function validateEmail(str){
	var reg1 = /^[a-zA-Z\d]+@[a-zA-Z]+\.[a-zA-Z]+$/;
	var reg2 = /^[a-zA-Z\d]+@[a-zA-Z]+\.[a-zA-Z]+\.[a-zA-Z]+$/
	return (reg1.test(str)||reg2.test(str));
}
function validatePhone(str){
	var reg = /^[\d]+-[\d]+$/;
	return reg.test(str);
}
function validateNum(str){
	var reg = /^[\d]+$/;
	return reg.test(str);
}
function validateLowerCaseAndUnderScore(str){
	var reg = reg = /^[a-z_]+$/;
	return reg.test(str);
}