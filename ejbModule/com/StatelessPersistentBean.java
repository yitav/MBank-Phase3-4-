
package com;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class StatelessPersistentBean
 */
@Stateless
@Remote(StatelessPersistent.class)
public class StatelessPersistentBean implements StatelessPersistent {

    /**
     * Default constructor. 
     */
    public StatelessPersistentBean() {
        // TODO Auto-generated constructor stub
    }

    @PersistenceContext(unitName="EjbComponentPU")
    private EntityManager entityManager;         

    
	@Override
	public void addLog(LogEntityBean logEntityBean) {
		
		System.out.println("client id : "+logEntityBean.getClient_id());
		System.out.println("log date :"+logEntityBean.getLog_date().toString());
		System.out.println("description :"+logEntityBean.getDescription());
		entityManager.persist(logEntityBean);
		
	}

	@Override
	public List<LogEntityBean> getLogs() {
		//System.out.println("getLogs() invoked");
		List<LogEntityBean> logs = entityManager.createQuery("From LogEntityBean").getResultList();
		//for(LogEntityBean log : logs){
		//	System.out.println("id : "+log.getId());
		//	System.out.println("date : "+log.getLog_date());
		//	System.out.println("client id : "+log.getClient_id());
		//	System.out.println("description : "+log.getDescription());
		//}
		return logs;
	}

}
