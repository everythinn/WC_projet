package dao.interfaces;

import java.util.List;

import models.Clan;

public interface IClanDao {
	
	public Clan getClanById(int idClan);
	
	public List<Clan> getAllClans();
	
	public Clan createClan(Clan clan);
	
	public void updateClan(Clan clan);
	
	public void deleteClan(int id);
}
