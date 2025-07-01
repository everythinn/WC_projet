package src.dao;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import src.dao.impl.bdd.DaoBddHelper;
import src.dao.impl.bdd.IDao;
import src.exceptions.DaoException;
import src.models.Outsider;

public class OutsiderDaoBdd implements IDao<Outsider> {

	private final DaoBddHelper bdd;
	
	public OutsiderDaoBdd() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	@Override
	public Outsider createObject(Outsider out) throws DaoException {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().persist(out);
			this.bdd.commitTransaction();
			return out;
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Cannot create Outsider", e);
		}
	}

	@Override
	public Outsider readObject(int id) throws DaoException {
		final TypedQuery<Outsider> query = this.bdd.getEntityManager().createNamedQuery("Outsider.findById", Outsider.class);
		query.setParameter("id", id);
		final List<Outsider> ret = query.getResultList();
		if(ret.size()>0) {
			return ret.get(0);
		}
		return null;
	}

	@Override
	public List<Outsider> readAllObject() throws DaoException {
		final TypedQuery<Outsider> query = this.bdd.getEntityManager().createNamedQuery("Outsider.findAll", Outsider.class);
		return query.getResultList();
	}

	@Override
	public void updateObject(Outsider out) throws DaoException {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().merge(out);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Cannot update Outsider", e);
		}
	}

	@Override
	public void deleteObject(Outsider out) throws DaoException {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().remove(out);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Cannot delete Outsider", e);
		}
	}

}
