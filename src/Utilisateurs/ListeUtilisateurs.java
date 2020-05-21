package Utilisateurs;

import java.util.List;

import Donnees.Donnee;

public interface ListeUtilisateurs {
	public List<Utilisateur> getAll();
	public Utilisateur getByID(int ID);
	public List<Utilisateur> getReferents();
	public List<Utilisateur> getEnseignants();
	public List<Utilisateur> getEnseignantsByCours(Donnee cours);
}
