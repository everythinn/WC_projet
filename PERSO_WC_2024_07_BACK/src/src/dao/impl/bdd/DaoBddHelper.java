package src.dao.impl.bdd;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.eclipse.persistence.sessions.Session;

import src.enums.PersistencyAction;
import src.exceptions.DaoException;

public class DaoBddHelper {
	
	private static DaoBddHelper instance;
	private final EntityManager em;
	
	public static final DaoBddHelper getInstance() throws DaoException {
		if (instance==null) {
			instance = new DaoBddHelper(null);
		}
		return instance;
	}
	
	private DaoBddHelper(final String persistenceUnitName) throws DaoException {
		try {
			final EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
			this.em = emf.createEntityManager();
			final Session session = this.em.unwrap(Session.class);
			System.out.println("Created EntityManager : " + session.getDatasourcePlatform().toString());
		} catch (Exception e) {
			throw new DaoException("Cannot create EntityManager", e);
		}
	}
	
	public EntityManager getEm() {
		return this.em;
	}

	public static DaoBddHelper setInstance(final String persistenceUnitName) throws DaoException {
		instance = new DaoBddHelper(persistenceUnitName);
		return instance;
	}
	
	public void makePersistencyAction(Object obj, boolean useTrans, PersistencyAction action) throws PersistenceException {
		try {
			if(useTrans) {
				beginTransaction();
			}
			switch(action) {
			case PERSIST:
				this.em.persist(obj);
				break;
			case MERGE:
				this.em.merge(obj);
				break;
			case REMOVE:
				this.em.remove(obj);
				break;
			}
			if(useTrans) {
				commitTransaction();
			}
		} catch (final PersistenceException e) {
			if(useTrans) {
				rollbackTransaction();
			}
			throw e;
		}
	}
	
	public void beginTransaction() {
		this.em.getTransaction().begin();
	}
	
    public void commitTransaction() {
        final EntityTransaction trans = this.em.getTransaction();
        if(trans.isActive()) {
            trans.commit();
        }
    }
    
    public void rollbackTransaction() {
        final EntityTransaction trans = this.em.getTransaction();
        if (trans.isActive()) {
            trans.rollback();
        }
    }
}
