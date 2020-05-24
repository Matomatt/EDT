package Seances;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import Donnees.Donnee;
import Donnees.ListeDonnees;
import Groupes.Groupe;
import Salles.Salle;
import Utilisateurs.Utilisateur;

public interface ListeSeances {
	public List<Seance> getAll();
	public List<Seance> getBySalle(Salle salle);
	public List<Seance> getByPromo(Donnee promotion);
	public List<Seance> getByGroupe(Groupe groupe);
	public List<Seance> getByWeek(int week);
	public List<Seance> getByUtilisateur(Utilisateur utilisateur);
	public List<Seance> getByUtilisateurAtDate(Utilisateur utilisateur, String date); //Format yyyy-mm-dd
	public List<Seance> getByUtilisateurAtWeek(Utilisateur utilisateur, int week);
	public boolean addSeance(Seance seance);
	public boolean salleLibre(Salle salle, Time heureDebut, Time heureFin, Date date);
	public boolean promoLibre(Donnee promotion, Time heureDebut, Time heureFin, Date date);
	public boolean groupeLibre(Groupe groupe, Time heureDebut, Time heureFin, Date date);
	public boolean utilisateurLibre(Utilisateur utilisateur, Time heureDebut, Time heureFin, Date date);
	public void Delete(Seance seance);
}
