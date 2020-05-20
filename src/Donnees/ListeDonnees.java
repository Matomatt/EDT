package Donnees;

import java.util.List;

import Filters.Filter;

public interface ListeDonnees {
	public List<Donnee> getAll();
	public List<Donnee> getFilteredBy(Filter[] filters);
	
	public boolean Update(Donnee d);
	public Donnee GetByID(int ID);
}
