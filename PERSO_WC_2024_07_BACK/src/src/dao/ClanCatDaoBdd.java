package src.dao;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import src.dao.impl.bdd.DaoBddHelper;
import src.dao.impl.bdd.IDao;
import src.exceptions.DaoException;
import src.models.ClanCat;

public class ClanCatDaoBdd implements IDao<ClanCat> {

	private final DaoBddHelper bdd;
	
	public ClanCatDaoBdd() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	@Override
	public ClanCat createObject(ClanCat clanCat) throws DaoException {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEm().persist(clanCat);
			this.bdd.commitTransaction();
			return clanCat;
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Cannot create ClanCat", e);
		}
	}

	@Override
	public ClanCat readObject(int id) throws DaoException {
		final TypedQuery<ClanCat> query = this.bdd.getEm().createNamedQuery("ClanCat.findById", ClanCat.class);
		query.setParameter("id", id);
		final List<ClanCat> ret = query.getResultList();
		if(ret.size()>0) {
			return ret.get(0);
		}
		return null;
	}

	@Override
	public List<ClanCat> readAllObject() throws DaoException {
		final TypedQuery<ClanCat> query = this.bdd.getEm().createNamedQuery("ClanCat.findAll", ClanCat.class);
		return query.getResultList();
	}
	
	public List<ClanCat> readAllLeaders() throws DaoException {
		final TypedQuery<ClanCat> query = this.bdd.getEm().createNamedQuery("ClanCat.findLeaders", ClanCat.class);
		return query.getResultList();
	}
	
	public List<ClanCat> readAllDeputies() throws DaoException {
		final TypedQuery<ClanCat> query = this.bdd.getEm().createNamedQuery("ClanCat.findDeputies", ClanCat.class);
		return query.getResultList();
	}
	
	public List<ClanCat> readAllMedicineCats() throws DaoException {
		final TypedQuery<ClanCat> query = this.bdd.getEm().createNamedQuery("ClanCat.findMedicineCats", ClanCat.class);
		return query.getResultList();
	}

	@Override
	public void updateObject(ClanCat clanCat) throws DaoException {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEm().merge(clanCat);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Cannot update ClanCat", e);
		}
	}

	@Override
	public void deleteObject(ClanCat clanCat) throws DaoException {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEm().remove(clanCat);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Cannot delete ClanCat", e);
		}
	}

}
