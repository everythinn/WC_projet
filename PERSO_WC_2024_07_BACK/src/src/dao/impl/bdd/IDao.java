package src.dao.impl.bdd;

import java.util.List;

import src.exceptions.DaoException;

public interface IDao<T> {

	T createObject(final T obj) throws DaoException;
	
	T readObject(final int id) throws DaoException;
	
	List<T> readAllObject() throws DaoException;
	
	void updateObject(final T obj) throws DaoException;
	
	void deleteObject(final T obj) throws DaoException;
}
