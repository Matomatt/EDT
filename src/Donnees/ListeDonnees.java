package Donnees;

import java.util.List;

import Filters.Filter;

public interface ListeDonnees {
	public List<Donnee> getAll();
	public List<Donnee> getFilteredBy(Filter[] filters);
	
	public void add(Donnee donnee);
	public boolean Update(Donnee d);
	public void delete(Donnee donnee);
	
	public Donnee GetByID(int ID);
	public Donnee getByNom(String nom);
	
}
