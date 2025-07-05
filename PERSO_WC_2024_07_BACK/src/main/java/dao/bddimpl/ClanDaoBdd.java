package dao.bddimpl;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import dao.interfaces.IClanDao;
import models.Clan;

public class ClanDaoBdd implements IClanDao {
	
	private final DaoBddHelper bdd;
	
	public ClanDaoBdd() throws Exception {
		this.bdd = DaoBddHelper.getInstance();
	}
	
	@Override
	public Clan getClanById(int idClan) {
		final TypedQuery<Clan> query = this.bdd.getEntityManager().createNamedQuery("Clan.findById", Clan.class);
		query.setParameter("id", idClan);
		if (query.getResultList().size() > 0) {
			return query.getResultList().getFirst();
		}
		return null;
	}

	@Override
	public List<Clan> getAllClans() {
		final TypedQuery<Clan> query = this.bdd.getEntityManager().createNamedQuery("Clan.findAll", Clan.class);
		return query.getResultList();
	}

	@Override
	public Clan createClan(Clan clan) {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().persist(clan);
			this.bdd.commitTransaction();
			return clan;
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new PersistenceException("Impossible de créer le Clan.", e);
		}
	}

	@Override
	public void updateClan(Clan clan) {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().merge(clan);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new PersistenceException("Impossible de modifier le Clan.", e);
		}
	}

	@Override
	public void deleteClan(int id) {
		try {
			final Clan s = getClanById(id);
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().remove(s);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new PersistenceException("Impossible de supprimer le Clan.", e);
		}
	}

}
