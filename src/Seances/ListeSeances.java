package Seances;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import Donnees.Donnee;
import Groupes.Groupe;
import Salles.Salle;
import Utilisateurs.Utilisateur;

public interface ListeSeances {
	public List<Seance> getAll();
	public List<Seance> getBySalle(Salle salle);
	public List<Seance> getBySalleAtDate(Salle salle, Date date);
	public List<Seance> getBySalleAtWeek(Salle salle, int semaine);
	public List<Seance> getByPromo(Donnee promotion);
	public List<Seance> getByPromoAtDate(Donnee promotion, Date date);
        public List<Seance> getByPromoAtWeek(Donnee promotion, int semaine);
	public List<Seance> getByGroupe(Groupe groupe);
	public List<Seance> getByGroupeAtDate(Groupe groupe, Date date);
        public List<Seance> getByGroupeAtWeek(Groupe groupe,  int semaine);
	public List<Seance> getByWeek(int week);
	public List<Seance> getByUtilisateur(Utilisateur utilisateur);
	public List<Seance> getByUtilisateurAtDate(Utilisateur utilisateur, Date date); //Format yyyy-mm-dd
	public List<Seance> getByUtilisateurAtWeek(Utilisateur utilisateur, int week);
	public Map<String, Double> getNombreHeureParCours(Utilisateur utilisateur);
	public Map<String, Double> getNombreHeureEffectueeParCours(Utilisateur utilisateur);
	public boolean addSeance(Seance seance);
	public void update(Seance seance);
	public void delete(Seance seance);
	public boolean salleLibre(Salle salle, Time heureDebut, Time heureFin, Date date);
	public boolean promoLibre(Donnee promotion, Time heureDebut, Time heureFin, Date date);
	public boolean groupeLibre(Groupe groupe, Time heureDebut, Time heureFin, Date date);
	public boolean utilisateurLibre(Utilisateur utilisateur, Time heureDebut, Time heureFin, Date date);
	public boolean seancePossible(Seance seance);
	
}