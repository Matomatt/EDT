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
			case Referent_pedagogique : return "Référant pédagogique";
			case Admin : return "Admin";
			default: return "none";
			}
		}
		public int toInt() {
			switch (this)
			{
			case Etudiant : return 4;
			case Enseignant : return 3;
			case Referent_pedagogique : return 2;
			case Admin : return 1;
			default: return 0;
			}
		}; 
	};
	
	public Utilisateur getUtilisateurConnecte();
	public UserType getUserType();
	
	public ListeSeances ListeSeances();
	public ListeUtilisateurs ListeUtilisateurs();
	public ListeGroupes ListeGroupes();
	public ListeSalles ListeSalles();
	
	public ListeDonnees ListeType_cours();
	public ListeDonnees ListeCours();
	public ListeDonnees ListeSite();
	public ListeDonnees ListePromotion();

	
}
