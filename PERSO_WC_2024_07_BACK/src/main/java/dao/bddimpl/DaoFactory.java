package dao.bddimpl;

import dao.interfaces.IClanCatDao;
import dao.interfaces.IClanDao;
import dao.interfaces.IOutsiderDao;

public class DaoFactory {
	
	private static DaoFactory instance;
	private IClanDao clanDao;
	private IClanCatDao clanCatDao;
	private IOutsiderDao outsiderDao;
	
	public static DaoFactory getInstance() throws Exception {
		if (instance == null) {
			instance = new DaoFactory();
		}
		return instance;
	}
	
	private DaoFactory() {};
	
	public IClanDao getClanDao() throws Exception {
		if (this.clanDao == null) {
			this.clanDao = new ClanDaoBdd();
		}
		return this.clanDao;
	}
	
	public IClanCatDao getClanCatDao() throws Exception {
		if (this.clanCatDao == null) {
			this.clanCatDao = new ClanCatDaoBdd();
		}
		return this.clanCatDao;
	}
	
	public IOutsiderDao getOutsiderDao() throws Exception {
		if (this.outsiderDao == null) {
			this.outsiderDao = new OutsiderDaoBdd();
		}
		return this.outsiderDao;
	}

}