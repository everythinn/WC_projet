package src.dao.impl.bdd;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.eclipse.persistence.sessions.Session;

import src.exceptions.DaoException;

public class DaoBddHelper {
	
	private static DaoBddHelper instance;
	private final EntityManager entityManager;
	
	public static DaoBddHelper getInstance() throws DaoException {
		if(instance==null) {
			instance = new DaoBddHelper("PERSO_WC_2024_07");
		}
		return instance;
	}
	
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	private DaoBddHelper(final String persistenceUnitName) throws DaoException {
		final EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
		this.entityManager = emf.createEntityManager();
		final Session session = this.entityManager.unwrap(Session.class);
		System.out.println("EntityManager créé : " + session.getDatasourcePlatform().toString());
	}
	
	public void beginTransaction() {
		this.entityManager.getTransaction().begin();
	}
	
	public void commitTransaction() {
		final EntityTransaction trans = this.entityManager.getTransaction();
		if (trans.isActive()){
			trans.commit();
		}
	}
	
	public void rollbackTransaction() {
		final EntityTransaction trans = this.entityManager.getTransaction();
		if (trans.isActive()) {
			trans.rollback();
		}
	}
}
