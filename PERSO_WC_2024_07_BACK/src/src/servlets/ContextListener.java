package src.servlets;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import src.dao.impl.bdd.DaoBddHelper;
import src.exceptions.DaoException;

public class ContextListener implements ServletContextListener {

	private static EntityManager em;
	
	public void contextInitialzed(final ServletContextEvent sce) {
		final EntityManagerFactory emf = Persistence.createEntityManagerFactory("");
		ContextListener.em = emf.createEntityManager();
	}
	
	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		EntityManager em = null;
		try {
			em = DaoBddHelper.getInstance().getEm();
			em.getEntityManagerFactory().close();
		} catch (final DaoException e) {
			e.printStackTrace();
		}
	}
	
	public static EntityManager getEntityManager() {
		return ContextListener.em;
	}
}
