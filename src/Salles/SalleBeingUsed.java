package Salles;

import java.sql.Time;

public class SalleBeingUsed {
	public Salle salle = null;
	public Time heureDebut = null;
	public Time heureFin = null;
	
	public SalleBeingUsed(Salle _salle, Time _heureDebut, Time _heureFin) {
		salle = _salle;
		heureDebut = _heureDebut;
		heureFin = _heureFin;
	}
}
