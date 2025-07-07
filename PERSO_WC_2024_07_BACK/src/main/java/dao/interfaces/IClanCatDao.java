package dao.interfaces;

import java.util.List;

import models.ClanCat;

public interface IClanCatDao {
	
	public ClanCat getClanCatById(int idClanCat);
	
	public List<ClanCat> getAllClanCats();
	
	public List<ClanCat> getAllLeaders();
	
	public List<ClanCat> getAllDeputies();
	
	public List<ClanCat> getAllMedCats();
	
	public ClanCat createClanCat(ClanCat clanCat);
	
	public void updateClanCat(ClanCat clanCat);
	
	public void deleteClanCat(int id);
}
