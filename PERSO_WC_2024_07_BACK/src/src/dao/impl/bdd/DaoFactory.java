package src.dao.impl.bdd;

import src.dao.ClanCatDaoBdd;
import src.dao.ClanDaoBdd;
import src.dao.OutsiderDaoBdd;
import src.exceptions.DaoException;
import src.models.*;

public class DaoFactory {
	
	private static DaoFactory instance;
	private static IDao<Clan> clanDao = null;
	private static IDao<ClanCat> clanCatDao = null;
	private static IDao<Outsider> outsiderDao = null;

	private DaoFactory() {};
	
	public static DaoFactory getInstance() throws DaoException {
		if(DaoFactory.instance == null) {
			DaoFactory.instance = new DaoFactory();
		}
		return DaoFactory.instance;
	}
	
	public static IDao<Clan> getClanDao() throws DaoException {
		if (clanDao == null) {
			clanDao = new ClanDaoBdd();
		}
		return clanDao;
	}
	
	public static IDao<ClanCat> getClanCatDao() throws DaoException {
		if (clanCatDao == null) {
			clanCatDao = new ClanCatDaoBdd();
		}
		return clanCatDao;
	}
	
	public static IDao<Outsider> getOutsiderDao() throws DaoException {
		if (outsiderDao == null) {
			outsiderDao = new OutsiderDaoBdd();
		}
		return outsiderDao;
	}
}
