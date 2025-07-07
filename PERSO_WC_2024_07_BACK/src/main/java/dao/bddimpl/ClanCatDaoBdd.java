package dao.bddimpl;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import dao.interfaces.IClanCatDao;
import models.ClanCat;

public class ClanCatDaoBdd implements IClanCatDao {
	
	private final DaoBddHelper bdd;
	
	public ClanCatDaoBdd() throws Exception {
		this.bdd = DaoBddHelper.getInstance();
	}
	
	@Override
	public ClanCat getClanCatById(int idClanCat) {
		final TypedQuery<ClanCat> query = this.bdd.getEntityManager().createNamedQuery("ClanCat.findById", ClanCat.class);
		query.setParameter("id", idClanCat);
		if (query.getResultList().size() > 0) {
			return query.getResultList().getFirst();
		}
		return null;
	}

	@Override
	public List<ClanCat> getAllClanCats() {
		final TypedQuery<ClanCat> query = this.bdd.getEntityManager().createNamedQuery("ClanCat.findAll", ClanCat.class);
		return query.getResultList();
	}
	
	@Override
	public List<ClanCat> getAllLeaders() {
		final TypedQuery<ClanCat> query = this.bdd.getEntityManager().createNamedQuery("ClanCat.findLeaders", ClanCat.class);
		return query.getResultList();
	}

	@Override
	public List<ClanCat> getAllDeputies() {
		final TypedQuery<ClanCat> query = this.bdd.getEntityManager().createNamedQuery("ClanCat.findDeputies", ClanCat.class);
		return query.getResultList();
	}

	@Override
	public List<ClanCat> getAllMedCats() {
		final TypedQuery<ClanCat> query = this.bdd.getEntityManager().createNamedQuery("ClanCat.findMedCats", ClanCat.class);
		return query.getResultList();
	}

	@Override
	public ClanCat createClanCat(ClanCat clanCat) {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().persist(clanCat);
			this.bdd.commitTransaction();
			return clanCat;
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new PersistenceException("Impossible de créer le chat.", e);
		}
	}

	@Override
	public void updateClanCat(ClanCat clanCat) {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().merge(clanCat);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new PersistenceException("Impossible de modifier le chat.", e);
		}
	}

	@Override
	public void deleteClanCat(int id) {
		try {
			final ClanCat s = getClanCatById(id);
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().remove(s);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new PersistenceException("Impossible de supprimer le chat.", e);
		}
	}

}
