package Utilisateurs;

import Donnees.ListeDonnees;
import Groupes.ListeGroupes;
import Salles.ListeSalles;
import Seances.ListeSeances;

public interface User {
	public enum UserType { Etudiant, Enseignant, Referent_pedagogique, Admin, none; 
		public String toString()
		{
			switch (this)
			{
			case Etudiant : return "Etudiant";
			case Enseignant : return "Enseignant";
			case Referent_pedagogique : return "R�f�rant p�dagogique";
			case Admin : return "Admin";
			default: return "none";
			}
		}; };
	public Utilisateur getUtilisateurConnecte();
	
	public ListeSeances ListeSeances();
	public ListeUtilisateurs ListeUtilisateurs();
	public ListeGroupes ListeGroupes();
	public ListeSalles ListeSalles();
	
	public ListeDonnees ListeType_cours();
	public ListeDonnees ListeCours();
	public ListeDonnees ListeSite();
	public ListeDonnees ListePromotion();
}
