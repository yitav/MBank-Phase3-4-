package com;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.naming.*;
import javax.jms.*;
import java.util.Properties;

/**
 * Session Bean implementation class LogSLBean
 */
@Stateless
@Remote(LogSL.class)
public class LogSLBean implements LogSL {

	QueueConnection queueConnection = null;
	QueueConnectionFactory queueFactory = null;
	QueueSession queueSession = null;
	Queue queue = null;
	QueueSender queueSender = null;
	ObjectMessage objectMessage = null;
	
    /**
     * Default constructor. 
     */
    public LogSLBean() {
    	try {
			
			final Properties initialContextProperties = new Properties();
		    initialContextProperties.put("java.naming.factory.initial",
		            "org.jnp.interfaces.NamingContextFactory");
		    initialContextProperties.put("java.naming.provider.url",
		            "jnp://localhost:1199");

		    //

		    final InitialContext ic = new InitialContext(initialContextProperties);

		    final QueueConnectionFactory qcf = (QueueConnectionFactory) ic
		            .lookup("XAConnectionFactory");
		   
			
			//InitialContext context = new InitialContext();
			//queueFactory = (QueueConnectionFactory) context
			//		.lookup("/ConnectionFactory");
			//queueConnection = queueFactory.createQueueConnection();
			//queueSession = queueConnection.createQueueSession(false,
			//		javax.jms.Session.AUTO_ACKNOWLEDGE);
			//queue = (Queue) context.lookup("/queue/MyQueue");
			
			/** **/
			queueConnection = qcf.createQueueConnection();
			queueSession = queueConnection.createQueueSession(false,
					javax.jms.Session.AUTO_ACKNOWLEDGE);
			
			queue = (Queue) ic.lookup("/queue/MyQueue");
			 
			/** **/
			
			
			System.out.println("Configuration For Bean 'LogSLBean' Done");

		} catch (Exception ex) {
			System.out.println("Error:" + ex.getMessage());
		}
    }

	@Override
	public void sendToQueue(LogMessage logMessage) {
		try {
			queueSender = queueSession.createSender(queue);
			objectMessage = queueSession.createObjectMessage();
			
			//LogMessage object = new LogMessage();
			//object.setDescription();
			//object.setClient_id();
			//object.setLog_date();
		
			objectMessage.setObject(logMessage);
			//queueSender.send(message);
			queueSender.send(objectMessage);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
	}

}
