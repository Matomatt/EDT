package Salles;

import java.sql.Time;
import java.util.List;
import java.util.LinkedHashMap;

import Seances.Seance;

public interface ListeSalles {
	public List<Salle> getAll();
	public List<Salle> getBySeance(Seance seance);
	public List<Salle> getBySeanceID(int ID);
	public LinkedHashMap<String, LinkedHashMap<Time[], List<Salle>>> getSallesLibresAtWeekStartingFrom(int week, java.util.Date date);
	public void add(Salle salle);
	public void update(Salle salle);
	public void delete(Salle salle);
}
