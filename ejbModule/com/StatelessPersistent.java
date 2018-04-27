package com;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface StatelessPersistent{
	
	void addLog(LogEntityBean logEntityBean);
	List<LogEntityBean> getLogs();
	
}
