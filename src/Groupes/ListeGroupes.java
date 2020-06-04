package Groupes;

import java.util.List;

import Donnees.Donnee;
import Filters.Filter;
import Seances.Seance;

public interface ListeGroupes {
	public List<Groupe> getAll();
	public List<Groupe> getByPromotion(Donnee promotion);
	public List<Groupe> getBySeance(Seance seance);
	public List<Groupe> getBySeanceID(int ID);
	public List<Groupe> getFilteredBy(Filter[] filters);
	public Groupe getByID(int ID);
	
	public int getNombreEtudiants(Groupe groupe);
	
	public void add(Groupe groupe);
	public boolean Update(Groupe groupe);
	public void delete(Groupe groupe);
}
