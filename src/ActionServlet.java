

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.LogDelegator;

import dtoIfc.AccountDTOIfc;
import dtoIfc.ActivityDTOIfc;
import dtoIfc.ClientDTOIfc;
import dtoIfc.DepositDTOIfc;
import dtoIfc.PropertyDTOIfc;
import dtos.ClientDTO;
import dtos.DepositDTO;
import actionsIfc.ClientActionIfc;

/**
 * Servlet implementation class ActionServlet
 */
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private LogDelegator logDelegatorAction = null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionServlet() {
        super();
        logDelegatorAction = new LogDelegator();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		ClientActionIfc clientAction = (ClientActionIfc) session.getAttribute("clientAction");
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		
		
		String command = request.getParameter("command");
		System.out.println(command);
		
		switch (command) {
		case "viewProperty":
			
			String propertyName = request.getParameter("propertyName");
			PropertyDTOIfc pdto;
			String propertyValue;
			synchronized (clientAction) {
				synchronized (username) {
					synchronized (password) {
						pdto =  clientAction.viewSystemProperty(propertyName);
						logDelegatorAction.sendToQueueWrapper
								(clientAction.viewClientDetails(username, password).getClient_id(), 
										"client:"+username+" | Action: View System Property - "+propertyName);
					}
				}
			}
			if(pdto == null){
				propertyValue="Error - No Such Property Name";
			}else{
				propertyValue = pdto.getProp_value();
			}
		
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.newDocument();
				Element root = doc.createElement("Property");
				Text propertyData = doc.createTextNode(propertyValue);
				root.appendChild(propertyData);
				doc.appendChild(root);
				
				String output = transformDocToStr(doc);
				writeResponse(response, output);
				
				return;
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case "viewClientDetails":
			
			ClientDTOIfc clientDTO;
			synchronized (clientAction) {
				synchronized (username) {
					synchronized (password) {
						clientDTO = clientAction.viewClientDetails(username, password);
						logDelegatorAction.sendToQueueWrapper
						(clientDTO.getClient_id(), 
								"client:"+username+" | Action: View Client Details");
					}
				}
			}
			
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.newDocument();
				Element root = doc.createElement("Client");
				
				Element nameElement = doc.createElement("name");
				Element passwordElement = doc.createElement("password");
				Element typeElement = doc.createElement("type");
				Element addressElement = doc.createElement("address");
				Element emailElement = doc.createElement("email");
				Element phoneElement = doc.createElement("phone");
				Element commentElement = doc.createElement("comment");
				
				Text nameData = doc.createTextNode(clientDTO.getClient_name());
				Text passwordData = doc.createTextNode(clientDTO.getPassword());
				Text typeData = doc.createTextNode(clientDTO.getType());
				Text addressData = doc.createTextNode(clientDTO.getAddress());
				Text emailData = doc.createTextNode(clientDTO.getEmail());
				Text phoneData = doc.createTextNode(clientDTO.getPhone());
				Text commentData = doc.createTextNode(clientDTO.getComment());
				
				nameElement.appendChild(nameData);
				passwordElement.appendChild(passwordData);
				typeElement.appendChild(typeData);
				addressElement.appendChild(addressData);
				emailElement.appendChild(emailData);
				phoneElement.appendChild(phoneData);
				commentElement.appendChild(commentData);
				
				root.appendChild(nameElement);
				root.appendChild(passwordElement);
				root.appendChild(typeElement);
				root.appendChild(addressElement);
				root.appendChild(emailElement);
				root.appendChild(phoneElement);
				root.appendChild(commentElement);
				
				doc.appendChild(root);
				
				String output = transformDocToStr(doc);
				writeResponse(response, output);
				
				
				return;
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		
		case "viewClientDeposits":
			DepositDTOIfc[] deposits;
			synchronized (clientAction) {
				synchronized (username) {
					synchronized (password) {
						deposits = clientAction.viewClientDeposits(username, password);
						logDelegatorAction.sendToQueueWrapper
						(clientAction.viewClientDetails(username, password).getClient_id(), 
								"client:"+username+" | Action: View Client Deposits");
						
					}
				}
			}
			
			
			
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.newDocument();
				Element root = doc.createElement("root");
				
				for(int i = 0; i < deposits.length; i++){
						Element depositElement =  doc.createElement("Deposit");
						
						Element balanceElement = doc.createElement("balance");
						Element typeElement = doc.createElement("type");
						Element estimatedBalanceElement = doc.createElement("estimated_balance");
						Element openingDateElement = doc.createElement("opening_date");
						Element closingDateElement = doc.createElement("closing_date");
						
						Text balanceData = doc.createTextNode(String.valueOf(deposits[i].getBalance()));
						Text typeData = doc.createTextNode(deposits[i].getType());
						Text estimatedBalanceData = doc.createTextNode(String.valueOf(deposits[i].getEstimated_balance()));
						Text openingDateData = doc.createTextNode(deposits[i].getOpening_date().toString());
						Text closingDateData = doc.createTextNode(deposits[i].getClosing_date().toString());
						
						
						balanceElement.appendChild(balanceData);
						typeElement.appendChild(typeData);
						estimatedBalanceElement.appendChild(estimatedBalanceData);
						openingDateElement.appendChild(openingDateData);
						closingDateElement.appendChild(closingDateData);
						
						
						depositElement.appendChild(balanceElement);
						depositElement.appendChild(typeElement);
						depositElement.appendChild(estimatedBalanceElement);
						depositElement.appendChild(openingDateElement);
						depositElement.appendChild(closingDateElement);
						
						root.appendChild(depositElement);
						
				}
				
				doc.appendChild(root);
				
				String output = transformDocToStr(doc);
				writeResponse(response, output);
				
				return;
			}catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
			break;
		case "viewAccountDetails" :
			
			AccountDTOIfc account;	
			synchronized (clientAction) {
				synchronized (username) {
					synchronized (password) {
						account = clientAction.viewAccountDetails(username, password)[0];
						logDelegatorAction.sendToQueueWrapper
						(clientAction.viewClientDetails(username, password).getClient_id(), 
								"client:"+username+" | Action: View Account Details");
					}
				}
			}
			
			
			
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.newDocument();
				Element root = doc.createElement("Account");
				
				Element balanceElement = doc.createElement("balance");
				Element commentElement = doc.createElement("comment");
				Element creditLimitElement = doc.createElement("credit_limit");
				
				Text balanceData = doc.createTextNode(String.valueOf(account.getBalance()));
				Text commentData = doc.createTextNode(account.getComment());
				Text creditLimitData = doc.createTextNode(String.valueOf(account.getCredit_limit()));
				
				balanceElement.appendChild(balanceData);
				commentElement.appendChild(commentData);
				creditLimitElement.appendChild(creditLimitData);
				
				root.appendChild(balanceElement);
				root.appendChild(commentElement);
				root.appendChild(creditLimitElement);
				
				doc.appendChild(root);
				
				String output = transformDocToStr(doc);
				writeResponse(response, output);
				
				return;
				
			}catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		case "viewActivities":
			ActivityDTOIfc[] activities;
			synchronized (clientAction) {
				synchronized (username) {
					synchronized (password) {
						activities = clientAction.viewClientActivities(username, password);
						logDelegatorAction.sendToQueueWrapper
						(clientAction.viewClientDetails(username, password).getClient_id(), 
								"client:"+username+" | Action: View Activities");
					}
				}
			}
			
			
			
			
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.newDocument();
				Element root = doc.createElement("root");
				
				for(int i = 0; i < activities.length; i++){
						Element activityElement =  doc.createElement("Activity");
						
						Element amountElement = doc.createElement("amount");
						Element activityDateElement = doc.createElement("activity_date");
						Element commisionElement = doc.createElement("commision");
						Element descriptionElement = doc.createElement("description");
						
						
						Text amountData = doc.createTextNode(String.valueOf(activities[i].getAmount()));
						Text activityDateData = doc.createTextNode(activities[i].getActivity_date().toString());
						Text commisionData = doc.createTextNode(String.valueOf(activities[i].getCommision()));
						Text descriptionData = doc.createTextNode(activities[i].getDescription());
						
						
						amountElement.appendChild(amountData);
						activityDateElement.appendChild(activityDateData);
						commisionElement.appendChild(commisionData);
						descriptionElement.appendChild(descriptionData);
						
						
						
						activityElement.appendChild(amountElement);
						activityElement.appendChild(activityDateElement);
						activityElement.appendChild(commisionElement);
						activityElement.appendChild(descriptionElement);
						
						root.appendChild(activityElement);
						
				}
				
				doc.appendChild(root);
				
				String output = transformDocToStr(doc);
				writeResponse(response, output);
				
				return;
			}catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
			break;
		case "logout":	
			synchronized (clientAction) {
				synchronized (username) {
					synchronized (password) {
						logDelegatorAction.sendToQueueWrapper
						(clientAction.viewClientDetails(username, password).getClient_id(), 
								"client:"+username+" | Action:logout");
					}
				}
			}
			
			session.invalidate();
			String welcomeUrl = "http://localhost:8080/WebView/index.html";
			response.sendRedirect(response.encodeRedirectURL(welcomeUrl));
			return;
			
		default:
			break;
		}	
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		ClientActionIfc clientAction = (ClientActionIfc) session.getAttribute("clientAction");
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		
		
		Document doc;
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(request.getInputStream());
			transformDocToStr(doc);
			
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		doc.getDocumentElement().normalize();
		String command = doc.getDocumentElement().getElementsByTagName("Command").item(0).getTextContent();
		System.out.println(command);
		
		switch (command) {
		case "updateClientDetails":
			
			try {
				
				Element clientElement = (Element) doc.getDocumentElement().getElementsByTagName("Client").item(0);
				
				//String nameUpdate = clientElement.getElementsByTagName("name").item(0).getTextContent();
				//String passwordUpdate = clientElement.getElementsByTagName("password").item(0).getTextContent();
				String addressUpdate = clientElement.getElementsByTagName("address").item(0).getTextContent();
				String emailUpdate = clientElement.getElementsByTagName("email").item(0).getTextContent();
				String phoneUpdate = clientElement.getElementsByTagName("phone").item(0).getTextContent();
				//String commentUpdate = clientElement.getElementsByTagName("comment").item(0).getTextContent();
				
				String typeUpdate;
				String commentUpdate;
				synchronized (clientAction) {
					synchronized (username) {
						synchronized (password) {
							typeUpdate = clientAction.viewClientDetails(username, password).getType();
							commentUpdate = clientAction.viewClientDetails(username, password).getComment();
						}
					}
				}
				
				
				
				
				ClientDTOIfc clientDetailsToUpdate = new ClientDTO();
				
				//fields that can change :
				clientDetailsToUpdate.setAddress(addressUpdate);
				clientDetailsToUpdate.setEmail(emailUpdate);
				clientDetailsToUpdate.setPhone(phoneUpdate);
				//clientDetailsToUpdate.setComment(commentUpdate);
				
				//unchangeable fields :
				clientDetailsToUpdate.setClient_name(username);
				clientDetailsToUpdate.setPassword(password);
				clientDetailsToUpdate.setType(typeUpdate);
				clientDetailsToUpdate.setComment(commentUpdate);
				
				boolean answer;
				synchronized (clientAction) {
					synchronized (username) {
						synchronized (password) {
							answer = clientAction.updateClientDetails(clientDetailsToUpdate);
							logDelegatorAction.sendToQueueWrapper
							(clientAction.viewClientDetails(username, password).getClient_id(), 
									"client:"+username+" | Action: Update Client Details");
						}
					}
				}
				
				
				
				
				String output = createStrXmlAnswer(answer);
				writeResponse(response, output);
				
				return;
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			break;
			
		case "createDeposit":	
			try{
				Element depositElement = (Element) doc.getDocumentElement().getElementsByTagName("Deposit").item(0);
				
				
				String balanceStr = depositElement.getElementsByTagName("amount").item(0).getTextContent();
				Element closingDateElement  = (Element) depositElement.getElementsByTagName("closingDate").item(0);
				
				String yearStr = closingDateElement.getElementsByTagName("year").item(0).getTextContent();
				String monthStr = closingDateElement.getElementsByTagName("month").item(0).getTextContent();
				String dayStr = closingDateElement.getElementsByTagName("day").item(0).getTextContent();
				
				
				double balance = Double.parseDouble(balanceStr);
				int year = Integer.parseInt(yearStr);
				int month = Integer.parseInt(monthStr);
				int day = Integer.parseInt(dayStr);
				
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.DATE, day);
				
				
				DepositDTOIfc depositToCreate = new DepositDTO();
				
				//fields that will be used :
				depositToCreate.setBalance(balance);
				depositToCreate.setClosing_date(new Timestamp(cal.getTimeInMillis()));
				
				boolean answer;
				synchronized (clientAction) {
					synchronized (username) {
						synchronized (password) {
							answer = clientAction.createNewDeposit(username, password, depositToCreate);
							logDelegatorAction.sendToQueueWrapper
							(clientAction.viewClientDetails(username, password).getClient_id(), 
									"client:"+username+" | Action: Create new deposit");
						}
					}
				}
				
				
				String output = createStrXmlAnswer(answer);
				writeResponse(response, output);
				
				return;
			}catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			break;
		
		case "preOpenDeposit":
			try {
				
				String depositIndexStr = doc.getDocumentElement().getElementsByTagName("DepositIndex").item(0).getTextContent();
				
				int depositIndex = Integer.parseInt(depositIndexStr);
				
				boolean answer;
				synchronized (clientAction) {
					synchronized (username) {
						synchronized (password) {
							answer = clientAction.preOpenDeposit(username, password, depositIndex, 0);
							logDelegatorAction.sendToQueueWrapper
							(clientAction.viewClientDetails(username, password).getClient_id(), 
									"client:"+username+" | Action: Pre Open Deposit");
						}
					}
				}
				
				
				
				
				String output = createStrXmlAnswer(answer);
				writeResponse(response, output);
				
				return;
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			break;
			
		case "depositToAccount":
			try {
				
				String amountToDepositStr = doc.getDocumentElement().getElementsByTagName("amountToDeposit").item(0).getTextContent();
				
				double amountToDeposit = Double.parseDouble(amountToDepositStr);
				
				boolean answer;
				synchronized (clientAction) {
					synchronized (username) {
						synchronized (password) {
							answer = clientAction.depositToAccount(username, password, 0, amountToDeposit);
							logDelegatorAction.sendToQueueWrapper
							(clientAction.viewClientDetails(username, password).getClient_id(), 
									"client:"+username+" | Action: Deposit to account");
						}
					}
				}
				
				
				String output = createStrXmlAnswer(answer);
				writeResponse(response, output);
				
				return;
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			break;
			
		case "withdrawFromAccount":
			
			try {
				
				String amountToWithdrawStr = doc.getDocumentElement().getElementsByTagName("amountToWithdraw").item(0).getTextContent();
				
				double amountToWithdraw = Double.parseDouble(amountToWithdrawStr);
				
				boolean answer;
				synchronized (clientAction) {
					synchronized (username) {
						synchronized (password) {
							answer = clientAction.withdrawFromAccount(username, password, 0, amountToWithdraw);
							logDelegatorAction.sendToQueueWrapper
							(clientAction.viewClientDetails(username, password).getClient_id(), 
									"client:"+username+" | Action: Withdraw from account");
						}
					}
				}
				
				String output = createStrXmlAnswer(answer);
				writeResponse(response, output);
				
				return;
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			break;
		default:
			break;
		}	
	}
	private String transformDocToStr(Document doc) throws TransformerException{
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		System.out.println(writer.getBuffer().toString());
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		return output;
	}
	private String createStrXmlAnswer(boolean answer) throws ParserConfigurationException, TransformerException{
		
		String answerStr ="";
		if(answer){
			answerStr = "true";
		}else{
			answerStr = "false";
		}
		
		DocumentBuilderFactory dbfToReply = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbToReply = dbfToReply.newDocumentBuilder();
		Document docToReply = dbToReply.newDocument();
		Element rootToReply = docToReply.createElement("Answer");
		Text answerData = docToReply.createTextNode(answerStr);
		rootToReply.appendChild(answerData);
		docToReply.appendChild(rootToReply);
		
		String output = transformDocToStr(docToReply);
		
		return output;
	}
	private void writeResponse(HttpServletResponse response , String output) throws IOException{
		response.setContentType("text/xml;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(output);
		out.flush();
		out.close();
	}
}
