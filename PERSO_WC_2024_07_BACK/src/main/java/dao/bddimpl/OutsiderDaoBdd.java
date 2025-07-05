package dao.bddimpl;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import dao.interfaces.IOutsiderDao;
import models.Outsider;

public class OutsiderDaoBdd implements IOutsiderDao {
	
	private final DaoBddHelper bdd;
	
	public OutsiderDaoBdd() throws Exception {
		this.bdd = DaoBddHelper.getInstance();
	}

	@Override
	public Outsider getOutsiderById(int idOutsider) {
		final TypedQuery<Outsider> query = this.bdd.getEntityManager().createNamedQuery("Outsider.findById", Outsider.class);
		query.setParameter("id", idOutsider);
		if (query.getResultList().size() > 0) {
			return query.getResultList().getFirst();
		}
		return null;
	}

	@Override
	public List<Outsider> getAllOutsiders() {
		final TypedQuery<Outsider> query = this.bdd.getEntityManager().createNamedQuery("Outsider.findAll", Outsider.class);
		return query.getResultList();
	}

	@Override
	public Outsider createOutsider(Outsider outsider) {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().persist(outsider);
			this.bdd.commitTransaction();
			return outsider;
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new PersistenceException("Impossible de créer l'étranger.", e);
		}
	}

	@Override
	public void updateOutsider(Outsider outsider) {
		try {
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().merge(outsider);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new PersistenceException("Impossible de modifier l'étranger.", e);
		}
	}

	@Override
	public void deleteOutsider(int id) {
		try {
			final Outsider s = getOutsiderById(id);
			this.bdd.beginTransaction();
			this.bdd.getEntityManager().remove(s);
			this.bdd.commitTransaction();
		} catch (final PersistenceException e) {
			this.bdd.rollbackTransaction();
			throw new PersistenceException("Impossible de supprimer l'étranger.", e);
		}
	}

}
