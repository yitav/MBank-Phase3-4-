package com;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.LogMessage;
import com.LogSL;

public class LogDelegator {
	Properties props;
	InitialContext ctx;
	LogSL queueBeanStub;
	public LogDelegator(){
		
		props = new Properties();
		try {
			
			props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			props.put(Context.PROVIDER_URL, "jnp://localhost:1199");//changed from 1099 so that 2 servers could run in parallel


		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			ctx = new InitialContext(props); 
			queueBeanStub = (LogSL)ctx.lookup("LogSLBeanRemote");
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
	}
	
	public synchronized void sendToQueueWrapper(long clientId , String description){
		
		LogMessage logMessage = new LogMessage();
        
		logMessage.setDescription(description);
        logMessage.setClient_id(clientId);
        logMessage.setLog_date( new Timestamp(new Date().getTime()) );
        
		queueBeanStub.sendToQueue(logMessage);
		
	}
	
}
