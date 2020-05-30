package Salles;

import java.util.List;

import Seances.Seance;

public interface ListeSalles {
	public List<Salle> getAll();
	public List<Salle> getBySeance(Seance seance);
	public List<Salle> getBySeanceID(int ID);
	public void add(Salle salle);
	public void update(Salle salle);
	public void delete(Salle salle);
}
