package dao.interfaces;

import java.util.List;

import models.Outsider;

public interface IOutsiderDao {

	public Outsider getOutsiderById(int idOutsider);
	
	public List<Outsider> getAllOutsiders();
	
	public Outsider createOutsider(Outsider outsider);
	
	public void updateOutsider(Outsider outsider);
	
	public void deleteOutsider(int id);

}
