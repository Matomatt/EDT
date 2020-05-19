package Utilisateurs;

import Utilisateurs.ConnectionViaUser.UserType;

import Donnees.ListeDonnees;
import Groupes.ListeGroupes;
import Salles.ListeSalles;
import Seances.ListeSeances;

public interface User {
	public String Name();
	public UserType Type();
	
	public ListeSeances ListeSeances();
	public ListeUtilisateurs ListeUtilisateurs();
	public ListeGroupes ListeGroupes();
	public ListeSalles ListeSalles();
	
	public ListeDonnees ListeType_cours();
	public ListeDonnees ListeCours();
	public ListeDonnees ListeSite();
	public ListeDonnees ListePromotion();
}
