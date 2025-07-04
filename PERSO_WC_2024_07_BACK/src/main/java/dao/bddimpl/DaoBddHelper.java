package dao.bddimpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.eclipse.persistence.sessions.Session;

public class DaoBddHelper {
	
	private static DaoBddHelper instance;
	private final EntityManager entityManager;
	
	public static DaoBddHelper getInstance() throws Exception {
		if(instance==null) {
			instance = new DaoBddHelper("WC_projet");
		}
		return instance;
	}
	
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	private DaoBddHelper(final String persistenceUnitName) throws Exception {
		try {
			final EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
			this.entityManager = emf.createEntityManager();
			final Session session = this.entityManager.unwrap(Session.class);
			System.out.println("EntityManager créé : " + session.getDatasourcePlatform().toString());
		} catch (final Exception e) {
			throw new Exception("Impossible de créer l'EntityManager.");
		}
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