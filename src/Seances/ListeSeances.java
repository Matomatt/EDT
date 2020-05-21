package Seances;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import Donnees.ListeDonnees;
import Salles.Salle;

public interface ListeSeances {
	public List<Seance> getAll();
	public List<Seance> getBySalle(Salle salle);
	public boolean addSeance(Seance seance);
	public boolean salleLibre(Salle salle, Time heureDebut, Time heureFin, Date date);
}
