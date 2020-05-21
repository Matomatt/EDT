package Groupes;

import java.util.List;

import Donnees.Donnee;
import Filters.Filter;

public interface ListeGroupes {
	public List<Groupe> getAll();
	public List<Groupe> getByPromotion(Donnee promotion);
	public List<Groupe> getFilteredBy(Filter[] filters);
	public boolean Update(Groupe groupe);
	public Groupe getByID(int ID);
}
