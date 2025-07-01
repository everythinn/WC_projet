package src.dao;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import src.dao.impl.bdd.DaoBddHelper;
import src.dao.impl.bdd.IDao;
import src.exceptions.DaoException;
import src.models.Clan;

public class ClanDaoBdd implements IDao<Clan> {

	private final DaoBddHelper bdd;
	
	public ClanDaoBdd() throws DaoException {
		this.bdd = DaoBddHelper.getInstance();
	}

	@Override
	public Clan createObject(Clan clan) throws DaoException {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().persist(clan);
			this.bdd.commitTransaction();
			return clan;
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Cannot create Clan", e);
		}
	}

	@Override
	public Clan readObject(int id) throws DaoException {
		final TypedQuery<Clan> query = this.bdd.getEntityManager().createNamedQuery("Clan.findById", Clan.class);
		query.setParameter("id", id);
		final List<Clan> ret = query.getResultList();
		if(ret.size()>0) {
			return ret.get(0);
		}
		return null;
	}

	@Override
	public List<Clan> readAllObject() throws DaoException {
		final TypedQuery<Clan> query = this.bdd.getEntityManager().createNamedQuery("Clan.findAll", Clan.class);
		return query.getResultList();
	}

	@Override
	public void updateObject(Clan clan) throws DaoException {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().merge(clan);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Cannot update Clan", e);
		}
	}

	@Override
	public void deleteObject(Clan clan) throws DaoException {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().remove(clan);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new DaoException("Cannot delete Clan", e);
		}
	}
}
