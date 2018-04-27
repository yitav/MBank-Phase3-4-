package com.impl;

import java.util.Properties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.LogEntityBean;
import com.LogMessage;
import com.StatelessPersistent;

/**
 * Message-Driven Bean implementation class for: MessageDrivenBean
 */
@MessageDriven(name = "MessageMDBImpl", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/MyQueue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")},
		messageListenerInterface = MessageListener.class)

public class MDBean implements MessageListener, MessageDrivenBean {

	private static final long serialVersionUID = 1L;
	
	Properties props;
	InitialContext ctx;
	public MDBean() {
		
	   
	      props = new Properties();
	      try {
	      
	    	  props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
	    	  props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
	    	  props.put(Context.PROVIDER_URL, "jnp://localhost:1199");
	         
	         
	         
	      } catch (Exception ex) {
	         ex.printStackTrace();
	      }
	      try {
	         ctx = new InitialContext(props);            
	      } catch (NamingException ex) {
	         ex.printStackTrace();
	      }
	     
	  
	}

	public void ejbRemove() {
		// TODO Auto-generated method stub
	}

	public void setMessageDrivenContext(MessageDrivenContext arg0) {
		this.setMessageDrivenContext(arg0);
	}

	public void onMessage(Message mess) {
		
		try {
			StatelessPersistent storeLogSLBean =
		       		 (StatelessPersistent)ctx.lookup("StatelessPersistentBeanRemote");
				
			ObjectMessage message=(ObjectMessage)mess;
			LogMessage object = (LogMessage) message.getObject();
			
			LogEntityBean logEntity = new LogEntityBean();
			logEntity.setDescription(object.getDescription());
			logEntity.setClient_id(object.getClient_id());
			logEntity.setLog_date(object.getLog_date());
            
			storeLogSLBean.addLog(logEntity);   
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}