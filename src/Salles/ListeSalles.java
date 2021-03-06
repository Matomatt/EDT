package Salles;

import java.sql.Time;
import java.util.List;
import java.util.Map;

import Donnees.Donnee;

import java.util.LinkedHashMap;

import Seances.Seance;

/**
 * Interface de la class DAO pour les salles
 */
public interface ListeSalles {
	public List<Salle> getAll();
	public List<Salle> getBySeance(Seance seance);
	public List<Salle> getBySeanceID(int ID);
	public LinkedHashMap<String, LinkedHashMap<Time[], List<Salle>>> getSallesLibresAtWeekStartingFrom(int week, java.util.Date date);
	public Map<String, Double> getProportionCapacitePourSite(Donnee site);
	public Map<String, Double> getMoyenneOccupationPourSite(Donnee site);
	public void add(Salle salle);
	public void update(Salle salle);
	public void delete(Salle salle);
}
