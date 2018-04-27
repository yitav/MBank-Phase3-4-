<%@page import="dtoIfc.*"%>
<%@page import="ifc.*"%>
<%@page import="java.rmi.*"%>
<%@page import="actionsIfc.*"%>
<%@page import="com.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main Page</title>

<link type="text/css" href="TabsStyle.css" rel="Stylesheet" />
<link type="text/css" href="LoginStyle.css" rel="Stylesheet" />
<link type="text/css" href="Modal.css" rel="Stylesheet" />
<link type="text/css" href="SimpleModal.css" rel="Stylesheet" />
<!-- <link type="text/css" href="Colors.css" rel="Stylesheet" />  -->

<style>
				.depositTable,.depositTableOuter {
				    border: 1px solid black;
				    text-align:center;
				}
				.depositTableOuter {
					width:100%;
					
				}
				.depositTableIndex{
					display:none;
				}
				
</style>
</head>
<body>

<%

	ClientActionIfc clientAction = (ClientActionIfc)session.getAttribute("clientAction");
	String username = (String) session.getAttribute("username");
	String password = (String) session.getAttribute("password");
	
	LogDelegator logDelagatorMainPage = new LogDelegator();
	
%>

<ul class="tab">
  <li><a href="#" class="tablinks" onclick="openCity(event, 'MainView')">MainView</a></li>
  <li><a href="#" class="tablinks" onclick="openCity(event, 'Activities')">Activities</a></li>
   <li><a href="#" class="tablinks" onclick="openCity(event, 'Properties')">Properties</a></li>
</ul>

<div id="MainView" class="tabcontent">
		<%	String logoutStr = "<div style='height: 5%;width: 100%;'>"+
									"<div style='float:left;height: 100%;width: 95%;text-align: right;' class='gray'>"+
										"<a href='http://localhost:8080/WebView/ActionServlet?command=logout'><b><i>logout</i></b></a>"+
									"</div>"+
								"</div>";
		%>
		<%=logoutStr %>
		<div style="height:52%;width:100%;border-bottom: 1px solid #ccc;border-top : 1px solid #ccc;">
			<!-- main left div -->
			<div style="height:100%; width:56%;float:left;border-right: 1px solid #ccc;">
				
				<div style="height: 100%;width:20%;float:left;">
					<div style="height: 10%;width:100%;" class="red"><!-- dummy volume div --></div>
					<div style="height: 35%;width:100%">
							<div style="float:left;height: 100%;width: 49%;" class="aqua"></div>
							<div style="float:right;height: 100%;width: 50%;" class="Khaki">
									<table>
										<tr>
											<td style="text-align:center;">
												<input id="depositToAccountBtnId" type="image" src="./offermoney.jpg" style="height: 30px;width:30px;" title="deposit to account">
											</td>
										</tr>
										<tr>
											<td style="text-align:center;">
												<input id="withdrawBtnId" type="image" src="./atm.jpg" style="height: 30px;width:30px%;" title="withdraw from account">
											<td>
										</tr>
									</table>
							</div>
								
					</div>
					<div style="height: 50%;width:100%;" class="LightGreen" ><!-- dummy volume div --></div>
				
				</div>
				<div style="height: 100%;width: 59%;float:left;" class="yellow">
						<%
							ClientDTOIfc client;
							AccountDTOIfc[] accounts;
							synchronized (clientAction) {
								synchronized (username) {
									synchronized (password) {
										client = clientAction.viewClientDetails(username, password);
										accounts = clientAction.viewAccountDetails(username, password);
										
										if(client == null ||accounts == null || accounts.length == 0){
											response.sendError(HttpServletResponse.SC_BAD_REQUEST);
											return;
										}
										logDelagatorMainPage.sendToQueueWrapper(client.getClient_id(), "client:"+username+" | Action: View Client Details");
										logDelagatorMainPage.sendToQueueWrapper(client.getClient_id(), "client:"+username+" | Action: View Account Details");
									}
								}
							}
						
						
							
							
						%>
						<div style="height:4% ;width: 100%"><!-- dummy volume div --></div>
						<div style="display: table;width:100%; height: 25%;">
							<div style="display: table-cell;vertical-align: bottom; text-align: center; font-size: 250%;" id="accountBalanceId"><%=accounts[0].getBalance() %>$ </div>
						</div>
						<div style="display: block; height: 8%;" align="center" > Balance </div>
						<div style="height:4% ;width: 100%"><!-- dummy volume div --></div>
						<div  style ="height:30%;border:1px dashed #ccc;"> <div style="width:68px;margin-top:-10px;margin-left:5px;background:white;">
																			comment
																			</div>
																			<div id="accountCommentId">
																			<%=accounts[0].getComment() %>
																			</div>
						</div>
						<div style="display: table;width:100%; height: 15%;">
							<div style="display: table-cell;vertical-align: bottom; text-align: center;font-size: 140%;" id="accountCreditLimitId">
							<%=accounts[0].getCredit_limit()%>
							</div>
						</div>
						<div style="display: block;height: 10%;" align="center">credit limit</div>
						
						
				</div>
				<div style="height: 100%;width: 20%;float:right;" class="green">
				</div>
			</div>
			<!-- main right div -->
			<div style="height:100%;width:40%;float:right;"> <!-- border-left: 1px solid #ccc; -->
				<div style="height: 13%;width:100%;" class="blue">Hello &nbsp;<span id="clientNameId" style="text-decoration: underline;"><%=client.getClient_name() %></span></div>
				<div style="height: 42%;width: :100%;"> 
						<div style="height:100%;width:80%;float:left;" class="fuchsia">
								<table>
								  <tr>
								    <td>type:</td>
								    <td id="clientTypeId"><%=client.getType() %></td>
								  </tr>
								  <tr>
								    <td>address:</td>
								    <td id="clientAddressId"><%=client.getAddress() %></td>
								  </tr>
								  <tr>
								    <td>email:</td>
								    <td id="clientEmailId"><%=client.getEmail() %></td>
								  </tr>
								  <tr>
								    <td>phone:</td>
								    <td id="clientPhoneId"><%=client.getPhone() %></td>
								  </tr>
								</table>
						
						</div>
						<div style="height:100%;width:19%;float: left;" class="purple"></div>
				</div>
				
				<div style="height: 35%;width: :100%;"> 
						<div style="height:100%;width:80%;float:left;" class="gray">
								<div  style ="height:100%;border:1px dashed #ccc;"> <div style="width:68px;margin-top:-10px;margin-left:5px;background:white;">
																						comment
																						</div>
																						<div id="clientCommentId">
																						<%=client.getComment() %>
																						</div>
								</div>
						
						
						</div>
						<div style="height:100%;width:19%;float: left;" class="orange"></div>
				</div>
							
				<input type="button" value="change" id="changeBtnId">
				
			</div>
			
		</div>
		<!-- main bottom div -->
		<div style="height:43%;width=100%;border-top: 1px solid #ccc;">
			<div style="height:100%;width:4%; float:left;" class="green"></div>
			<div style="height:100%;width:92%;float:left;">
				<div style="height:8%; width: 100%;" class="lime"></div>
				<div id="depositsTableHolderId" style="overflow: auto;height:78%; width: 100%;" class="navy">
					<table class="depositTableOuter">
						<tr class="depositTable">
							<th class="depositTable">deposit balance</th>
							<th class="depositTable">deposit type</th>
							<th class="depositTable">deposit estimated balance</th>
							<th class="depositTable"> opening date</th>
							<th class="depositTable">closing date </th>
							<th class="depositTable"> pre open</th>
						</tr>
						<% 	
							DepositDTOIfc[] deposits;
							synchronized (clientAction) {
								synchronized (username) {
									synchronized (password) {
										deposits = clientAction.viewClientDeposits(username, password);
										logDelagatorMainPage.sendToQueueWrapper(client.getClient_id(), "client:"+username+" | Action: View Client Deposits");
									}
								}
							}
						
							
						
							for(int i = 0; i < deposits.length; i++){
								out.print("<tr class='depositTable'>");
									
									out.print("<td class='depositTable' id='depositBalanceId"+i+"'>");
									out.print(deposits[i].getBalance());
									out.print("</td>");
									
									out.print("<td class='depositTable'>");
									out.print(deposits[i].getType());
									out.print("</td>");
	
									out.print("<td class='depositTable'>");
									out.print(deposits[i].getEstimated_balance());
									out.print("</td>");
									
									out.print("<td class='depositTable'>");
									out.print(deposits[i].getOpening_date());
									out.print("</td>");
									
									out.print("<td class='depositTable'>");
									out.print(deposits[i].getClosing_date());
									out.print("</td>");
									
									out.print("<td class='depositTable'>");
									if(deposits[i].getType().equals("LONG")){
										out.print("<input type='button' value='open' onclick='displaypreOpenModal(this)'>");
										out.print("<div  class='depositTableIndex'>"+i+"</div>");
									}
									
									out.print("</td>");
									
								out.print("</tr>");
							}
						
						%>
						
					</table>
				</div >
				<div style="height:12%; width: 100%;">
						<div style="float:left;height: 100%;width: 90%;display:block;text-align:right;" class="olive">
							<input type="button" value="create" id="createBtnId" title="create new deposit">
						</div>
						
				</div>
			</div>
			<div style="height:100%;width:3%;float:right;" class="silver"></div>
		</div>
 
</div>

<div id="Activities" class="tabcontent">
		<%=logoutStr %>
		<div style="float:left;height: 95%;width: 10%;" class="blue"></div>
		<div style="float:left;height: 95%;width: 79%;">
		
			<div style="height: 15%" class="gray"></div>
			<div id="activitiesTableHolderId" style="overflow:auto; height: 70%;" class="DarkGray">
				<table class="depositTableOuter">
					<tr class="depositTable">
						<th class="depositTable">amount</th>
						<th class="depositTable">activity date</th>
						<th class="depositTable">commision</th>
						<th class="depositTable">description</th>
					<tr>
					<%	
						ActivityDTOIfc[] activities;
						synchronized (clientAction) {
							synchronized (username) {
								synchronized (password) {
									activities = clientAction.viewClientActivities(username, password);
								}
							}
						}
						
						
					
						for (int i = 0; i < activities.length; i++) {
							out.print("<tr class='depositTable'>");

							out.print("<td class='depositTable'>");
							out.print(activities[i].getAmount());
							out.print("</td>");

							out.print("<td class='depositTable'>");
							out.print(activities[i].getActivity_date());
							out.print("</td>");

							out.print("<td class='depositTable'>");
							out.print(activities[i].getCommision());
							out.print("</td>");

							out.print("<td class='depositTable'>");
							out.print(activities[i].getDescription());
							out.print("</td>");

							out.print("</tr>");
						}
					%>
				
				</table>
			</div>
			<div style="height: 14%;" class="DimGray"></div>
		</div>
		<div style="float:right;height: 95%;width: 10%;" class="navy"></div>
</div>

<div id="Properties" class="tabcontent">
	<%=logoutStr %>
	<div style="float:left;height:94%;width:35%;"></div>
	<div style="float:left;height:94%;width:33%;">
		<h2> property name: </h2>
		<input id="propertyNameId" type="text" onkeyup="validatePropertyName()">
		
		<div id="wrongPropertyNameInputId" style="color: red"></div>
		
		<br>
		<br>
		<input type="button" value="view" onclick="viewPropertyTab()">
		<br>
		<br>
		<div  style ="height:30%;border:1px solid #ccc;"> <h2 style="width:170px;margin-top:-15px;margin-left:5px;background:white;">
																property value
															</h2>	
															<h1 id="propertyValueId" style="color:blue;text-align:center;"></h1>	
		</div>
		
	</div>
	<div style="float:right;height:94%;width:30%;"></div>
</div>

<!-- The Modal -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="close">×</span>
      <!--  <h2>Modal Header</h2>  -->
    </div>
    <div class="modal-body">

		<div>Details to Update :</div>
		<table>
			<!--    
			<tr>
				<td>name :</td>
				<td> <input type="text" id="nameToUpdateId"> </td>
			</tr>
			
			<tr>
				<td>password :</td>
				<td> <input type="password" id="passwordToUpdateId"> </td>
			</tr>
			-->
			<tr>
				<td>address :</td>
				<td> <input type="text" id="addressToUpdateId" onkeyup="validateAddress()" > </td>
			</tr>
			<tr>
	  			<td colspan="2" style="color: red" id="wrongAddressToUpdateInputId"> </td>
	  		</tr>
	  		
			<tr>
				<td>email :</td>
				<td> <input type="text" id="emailToUpdateId" onkeyup="validateEmailWrapper()"> </td>
			</tr>
			<tr>
	  			<td colspan="2" style="color: red" id="wrongEmailToUpdateInputId"> </td>
	  		</tr>
			
			<tr>
				<td>phone :</td>
				<td> <input type="text" id="phoneToUpdateId" onkeyup="validatePhoneWrapper()"> </td>
			</tr>
			<tr>
	  			<td colspan="2" style="color: red" id="wrongPhoneToUpdateInputId"> </td>
	  		</tr>
	  		
			<!--  
			<tr>
				<td>comment :</td>
				<td> <input type="text" id="commentToUpdateId"> </td>
			</tr>
			-->
		</table>
		<br>
		<input type="button" value="update" onclick="updateClientDetails()"> 
	</div>
    <div class="modal-footer">
      <!--   <h3>Modal Footer</h3> -->
    </div>
  </div>

</div>

<!-- The Modal -->
<div id="myCreateModal" class="createModal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="closeCreate">×</span>
      
    </div>
    <div class="modal-body">
      <h4>Details to Create :</h4>
		<table>
			<tr>
				<td>amount :</td>
				<td> <input type="text" id="balanceCreateId" onkeyup="validateBalanceCreate()"> </td>
			</tr>
			<tr>
	  			<td colspan="2" style="color: red" id="wrongBalanceCreateInputId"> </td>
	  		</tr>
			
			<tr> 
				<td colspan="2" > Closing Date : </td>
			</tr> 
			<tr>
				<td>year</td>
				<td> <input type="text" id="yearCreateId" onkeyup="validateDateWrapper()"> </td>
			</tr>
			<tr>
				<td>month</td>
				<td> <input type="text" id="monthCreateId" onkeyup="validateDateWrapper()"> </td>
			</tr>
			<tr>
				<td>day</td>
				<td> <input type="text" id="dayCreateId" onkeyup="validateDateWrapper()"> </td>
			</tr>
			<tr>
	  			<td colspan="2" style="color: red" id="wrongClosingDateInputId"> </td>
	  		</tr>
			
		</table>
		<br>
		<input type="button" value="create now" onclick="createDeposit()">
    </div>
    <div class="modal-footer">
      
    </div>
  </div>

</div>
<!-- The Modal -->
<div id="myPreOpenModal" class="preOpenModal">

  <!-- Modal content -->
  <div class="small-modal-content">
    <div class="modal-header">
      <span class="closePreOpen">×</span>
    </div>
    <div class="modal-body">
      <p>Are you sure you want to open this Deposit ?</p>
      <br>
      <table>
      		<tr>
      			<td>commision:</td>
      			<td id="preOpenFeeId"></td>
      		</tr>
      </table>
      <input type="button" value="OK" onclick="preOpenDeposit()">
      <div  class='depositTableIndex' id="depositIndexId" ></div>
    </div>
    <div class="modal-footer">
     
    </div>
  </div>

</div>
<!-- The Modal -->
<div id="myDepositToAccountModal" class="depositToAccountModal">

  <!-- Modal content -->
  <div class="small-modal-content">
    <div class="modal-header">
      <span class="closeDepositToAccount">×</span>
      
    </div>
    <div class="modal-body">
    	<div style="height: 85%">
	      Please enter amount to deposit to account
	      <br>
	      <br>
		  <table>
		  		<tr>
		  			<td> amount: </td>
		  			<td> <input type="text" id="amountToDepositInputId" onkeyup="calcDepositToAccountCommision()"> <td>
		  		</tr>
		  		<tr>
		  			<td colspan="2" style="color: red" id="wrongDepositInputId"> </td>
		  		</tr>
		  		<tr>
		  			<td>commison: </td>
		  			<td id="depositToAccountCommisionId"></td>
		  		</tr>
		  </table>
	  </div>
	  <div style="height: 14%">
	  	<input type="button" value="deposit now" onclick="depositToAccount()">  
	  </div>    
    </div>
    <div class="modal-footer">
      
    </div>
  </div>

</div>
<!-- The Modal -->
<div id="myWithdrawModal" class="WithdrawModal">

  <!-- Modal content -->
  <div class="small-modal-content">
    <div class="modal-header">
      <span class="closeWithdraw">×</span>
    </div>
    <div class="modal-body">
      <div style="height: 85%">
	      Please enter amount to withdraw from account
	      <br>
	      <br>
		  <table>
		  		<tr>
		  			<td> amount: </td>
		  			<td> <input type="text" id="amountToWithdrawInputId" onkeyup="calcWithdrawCommision()"> <td>
		  		</tr>
		  		<tr>
		  			<td colspan="2" style="color: red" id="wrongWithdrawInputId"> </td>
		  		</tr>
		  		<tr>
		  			<td>commison: </td>
		  			<td id="withdrawCommisionId"></td>
		  		</tr>
		  </table>
	  </div>
	  <div style="height: 14%">
	  	<input type="button" value="withdraw now" onclick="withdrawFromAccount()">  
	  </div>    
    </div>
    <div class="modal-footer">
    </div>
  </div>

</div>

<!-- The Modal -->
<div id="mySimpleModal" class="simple-modal">

  <!-- Modal content -->
  <div class="simple-modal-content">
    <span class="simple-close">×</span>
    <p id="simpleModalTextId">Some text in the Modal..</p>
  </div>

</div>

<script src="tabsScripts.js">
</script>
<script src="startMainViewTab.js">
</script>
<script>

//Get the modal
var simpleModal = document.getElementById('mySimpleModal');

// Get the <span> element that closes the modal
var spanSimple = document.getElementsByClassName("simple-close")[0];

// When the user clicks the button, open the modal
function showSimpleModal() {
	simpleModal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
spanSimple.onclick = function() {
	simpleModal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
//window.onclick = function(event) {
//    if (event.target == simpleModal) {
//    	simpleModal.style.display = "none";
//    }
//}



// Get the modal
var modal = document.getElementById('myModal');
var createModal = document.getElementById('myCreateModal');
var preOpenModal = document.getElementById('myPreOpenModal');
var depositToAccountModal = document.getElementById('myDepositToAccountModal');
var withdrawModal = document.getElementById('myWithdrawModal');

// Get the button that opens the modal
var changeBtn = document.getElementById("changeBtnId");
var createBtn = document.getElementById("createBtnId");
//pre open modal button event is defined in their creation
var depositToAccountBtn = document.getElementById("depositToAccountBtnId");
var withdrawBtn = document.getElementById("withdrawBtnId");


// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];
var spanCreate = document.getElementsByClassName("closeCreate")[0];
var spanPreOpen = document.getElementsByClassName("closePreOpen")[0];
var spanDepositToAccount = document.getElementsByClassName("closeDepositToAccount")[0];
var spanWithdraw = document.getElementsByClassName("closeWithdraw")[0];

// When the user clicks the button, open the modal
changeBtn.onclick = function() {
	viewClientDetailsForUpdate();
    modal.style.display = "block";
}
createBtn.onclick = function() {
    createModal.style.display = "block";
}
function displaypreOpenModal(eventSrc){
	preOpenModal.style.display = "block";
	var depositIndex = eventSrc.parentElement.getElementsByTagName("div")[0].innerHTML;
	var depositElement = document.getElementById("depositIndexId");
	depositElement.innerHTML = depositIndex;
	calcPreOpenFee(depositIndex);
}
depositToAccountBtn.onclick = function() {
	depositToAccountModal.style.display = "block";
}
withdrawBtn.onclick = function() {
	withdrawModal.style.display = "block";
}
// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}
spanCreate.onclick = function() {
    createModal.style.display = "none";
}
spanPreOpen.onclick = function() {
    preOpenModal.style.display = "none";
}
spanDepositToAccount.onclick = function() {
	depositToAccountModal.style.display = "none";
}
spanWithdraw.onclick = function() {
	withdrawModal.style.display = "none";
}
// When the user clicks anywhere outside of the modal, close it

window.onclick = function(event) {

	if ((event.target == modal) || 
			(event.target == createModal)|| 
			(event.target == preOpenModal)|| 
			(event.target == depositToAccountModal)|| 
			(event.target == withdrawModal)||
			(event.target == simpleModal)) {
		hideModal();
	}

}
function hideModal(){
	modal.style.display = "none";
	createModal.style.display = "none";
	preOpenModal.style.display = "none";
	depositToAccountModal.style.display = "none";
	withdrawModal.style.display = "none";
	simpleModal.style.display = "none";
}
</script>
<script src="actions.js"></script>
<script src="commisionAndValidation.js"></script>

</body>

</html>