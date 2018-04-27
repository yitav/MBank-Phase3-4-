package com;

import javax.ejb.Remote;

@Remote
public interface LogSL {
	public void sendToQueue(LogMessage logMessage);
}
