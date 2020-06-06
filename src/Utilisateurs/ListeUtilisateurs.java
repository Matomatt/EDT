package Utilisateurs;

import java.util.List;

import Donnees.Donnee;
import Seances.Seance;

/**
 * Interface de la class DAO pour les utilisateurs
 */
public interface ListeUtilisateurs {
	public List<Utilisateur> getAll();
	public Utilisateur getByID(int ID);
	public List<Utilisateur> getReferents();
	public List<Utilisateur> getEnseignants();
	public List<Utilisateur> getEtudiants();
	public List<Utilisateur> getEnseignantsByCours(Donnee cours);
	public List<Utilisateur> getEnseignantsBySeance(Seance seance);
	public List<Utilisateur> getEnseignantsBySeanceID(int ID);
	public int getHighestStudentNumber();
	public void Delete(Utilisateur utilisateur);
	public void addUtilisateur(Utilisateur utilisateur);
	public void update(Utilisateur utilisateur);
}
