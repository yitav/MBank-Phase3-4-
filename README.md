# MBank-Phase3-4-
Phases three and four of Mini Bank Project


DB used in the project is MSSQL.
Configurations are as follows:
--use ProjectJB
create table Clients
(
		client_id	Bigint Identity Primary Key,
		client_name nvarchar(100) Not Null,
		password	nvarchar(100) Not Null,
		type		varchar(20) Not Null,
		address		nvarchar(100),
		email		varchar(50),
		phone		varchar(20),
		comment		nvarchar(100),
)
create table Accounts
(
	account_id		Bigint Identity Primary Key ,
	client_id		Bigint Not Null ,
	balance			FLOAT Not Null ,
	credit_limit	FLOAT Not Null ,
	comment			nvarchar(100) ,
	FOREIGN KEY(client_id) REFERENCES Clients(client_id) ,
)


create table Deposits
(
		deposit_id			Bigint Identity Primary Key,
		client_id			Bigint Not Null,
		balance				FLOAT Not Null,
		type				varchar(20) Not Null,
		estimated_balance	Bigint Not Null,
		opening_date		datetime Not Null,
		closing_date		datetime Not Null,
		FOREIGN KEY(client_id) REFERENCES Clients(client_id) ,
)


create table Activity
(
		id				Bigint Identity Primary Key ,
		client_id		Bigint Not Null,
		amount			FLOAT Not Null,
		activity_date	datetime Not Null,
		commision		FLOAT Not Null,
		description		nvarchar(100),
		FOREIGN KEY(client_id) REFERENCES Clients(client_id)
)


create table Properties
(
		prop_key	varchar(50) Primary Key,
		prop_value	varchar(50) Not Null,
)

create table Logs
(	
	Id			Bigint Identity Primary Key,
	Log_date	datetime ,
	Client_id	Bigint , 
	Description nvarchar(100),

)

The web application is presented here. 
The administrative application and web application are both rely on the same core which is under directory MBankBL (MBank Business Logic ). 
This core interacts with each of the above apps view layer by RMI.
This was done in order to avoid code duplication of the core actions by both apps.
The view and controller layers of the administrative application that are under the directory MBankGui , are written in SWING.
Testing of the core was done using JUNIT – 
and is under the directory and package MBankBL/test (classes: AdminActionTest , ClientActionTest – 
Testing of the lower level classes was done manually and by testing the highest level by JUNIT ensured correctness of all levels).

The view layer and controller layers of the web application are under directory WebView.
The webservers used in deployment and testing were two JBoss AS 5.0 - 
one was used for /WebView on web port 8080.
The other was used for /LogSL , /MessageDrivenBean , /StoreLogsSL .
In general the main component in the view layer consists of /WebView/WebContent/MainPage.jsp ,
and JAVASCRIPT is used in this page according to the guidance given in the Characterization Document. 
Furthermore the controller mainly consists of   -  src/ActionServlet.java .
the requests for actions from the view are transferred by AJAX to the ActionServlet which in turn uses ClientAction by RMI to MBankBL ;
the response from ActionServlet is then turned into XML and returned to MainPage.jsp . 

The MBank Asynchronous Log System was implemented exactly according to the Characterization Document.
* Controller servlet(ActionServlet) issues a Log message(/WebView/src/com/LogMessage.java) every
   time it is servicing.
* The log goes through some synchronizer( Log Delegator (/WebView/src/com/LogDelegator.java) ) that
   hold an EJB stub
   ( LogSLBean (LogSL queueBeanStub; queueBeanStub = (LogSL)ctx.lookup("LogSLBeanRemote");) )
* The bean (/LogSL/ejbModule/com/LogSLBean.java) that receives a log message from the controller -
   generates a JMS Message 
   ( objectMessage.setObject(logMessage); queueSender.send(objectMessage); ) containing log data and 
   puts it in a JMS queue.
* A message driven bean (/MessageDrivenBean/ejbModule/com/impl/MDBean.java) consumes log 
   messages asynchronously from that queue and delegates it to 
   another stateless bean for processing 
   (  StoreLogsSLBean (/StoreLogsSL/ejbModule/com/StatelessPersistentBean.java)  )
* The stateless bean uses JPA to store the log message (entityManager.persist(logEntityBean);) 
